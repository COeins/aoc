package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

class Day5 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		List<Long[]> startValues = new ArrayList<>();
		for (String v : in[0].split(":")[1].trim().split(" ")) {
			long s = Long.parseLong(v);
			startValues.add(new Long[] { s, s });
		}

		Map<ValueTypes, ValueMap> maps = parseMaps(in);
		return findMinSeed(maps, startValues);
	}

	@Override
	public Long task2(String[] in) {
		List<Long[]> startRanges = new ArrayList<>();
		String[] values = in[0].split(":")[1].trim().split(" ");
		for (int i = 0; i < values.length; i += 2) {
			long s = Long.parseLong(values[i]);
			long len = Long.parseLong(values[i + 1]);
			startRanges.add(new Long[] { s, s + len - 1 });
		}

		Map<ValueTypes, ValueMap> maps = parseMaps(in);
		return findMinSeed(maps, startRanges);
	}

	private Map<ValueTypes, ValueMap> parseMaps(String[] in) {
		Map<ValueTypes, ValueMap> maps = new EnumMap<>(ValueTypes.class);
		ValueMap parseMap = null;
		for (int i = 2; i < in.length; i++) {
			if (in[i].length() == 0)
				continue;
			if (in[i].endsWith("map:")) {
				parseMap = new ValueMap();
				maps.put(ValueTypes.valueOf(in[i].split("-")[0]), parseMap);
				continue;
			}
			String[] numbers = in[i].split(" ");
			if (numbers.length == 3 && parseMap != null) {
				parseMap.addRange(Long.parseLong(numbers[0]), Long.parseLong(numbers[1]), Long.parseLong(numbers[2]));
			} else {
				log("paring error at line", i, in[i]);
			}
		}
		return maps;
	}

	private long findMinSeed(Map<ValueTypes, ValueMap> maps, List<Long[]> ranges) {
		long min = Integer.MAX_VALUE;
		List<Long[]> curRanges = new ArrayList<>(ranges);

		for (ValueTypes t : ValueTypes.values()) {
			List<Long[]> nextRanges = new ArrayList<>();
			for (Long[] r : curRanges) {
				List<Long[]> x = maps.get(t).mapsTo(r);
				log(t, r, x);
				nextRanges.addAll(x);
			}
			curRanges = nextRanges;
		}

		for (Long[] r : curRanges) {
			if (r[0] < min)
				min = r[0];
		}
		return min;
	}

	private class ValueMap {
		final List<Long[]> ranges = new ArrayList<>();

		private void addRange(long dest, long start, long len) {
			ranges.add(new Long[] { start, start + len - 1, dest - start });
		}

		private List<Long[]> mapsTo(Long[] inputRange) {

			List<Long[]> inputRanges = new ArrayList<>(Collections.singleton(inputRange));
			List<Long[]> mappedRanges = new ArrayList<>();
			List<Long[]> unmappedRanges = Collections.emptyList();

			for (Long[] r : ranges) {
				unmappedRanges = new ArrayList<>();
				for (Long[] i : inputRanges)

					if (r[0] > i[1] || r[1] < i[0]) {
						unmappedRanges.add(new Long[] { i[0], i[1], 0L });
					} else if (r[0] <= i[0] && r[1] >= i[1]) {
						mappedRanges.add(new Long[] { i[0] + r[2], i[1] + r[2], 1L });
					} else if (r[0] <= i[0] && r[1] < i[1]) {
						mappedRanges.add(new Long[] { i[0] + r[2], r[1] + r[2], 2L });
						unmappedRanges.add(new Long[] { r[1] + 1, i[1], 2L });
					} else if (r[0] > i[0] && r[1] >= i[1]) {
						unmappedRanges.add(new Long[] { i[0], r[0] - 1, 3L });
						mappedRanges.add(new Long[] { r[0] + r[2], i[1] + r[2], 3L });
					} else if (r[0] > i[0] && r[1] < i[1]) {
						unmappedRanges.add(new Long[] { i[0], r[0] - 1, 4L });
						mappedRanges.add(new Long[] { r[0] + r[2], r[1] + r[2], 4L });
						unmappedRanges.add(new Long[] { r[1] + 1, i[1], 4L });
					}
				inputRanges = unmappedRanges;
			}

			mappedRanges.addAll(unmappedRanges);
			return mappedRanges;
		}
	}

	private enum ValueTypes {
		seed, soil, fertilizer, water, light, temperature, humidity //, location
	}
}
