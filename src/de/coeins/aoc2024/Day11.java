package de.coeins.aoc2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Day11 implements de.coeins.aoc2023.Day<Long> {
	@Override
	public Long task1(String[] in) {
		return calculate(in[0], 25);
	}

	@Override
	public Long task2(String[] in) {
		return calculate(in[0], 75);
	}

	private long calculate(String in, int rounds) {
		List<Long> stones = new LinkedList<>(Arrays.stream(in.split(" ")).map(Long::parseLong).toList());
		Map<Long, Long> sc0 = new HashMap<>();
		Arrays.stream(in.split(" ")).map(Long::parseLong).forEach(l -> add(sc0, l, 1L));
		Map<Long, Long> sc = sc0;

		for (int i = 0; i < rounds; i++) {
			Map<Long, Long> sc1 = new HashMap<>();
			for (Long s : sc.keySet()) {
				long count = sc.get(s);
				int len = ("" + s).length();
				if (s == 0) {
					add(sc1, 1L, count);
				} else if (len % 2 == 0) {
					int div = (int) Math.pow(10, len / 2);
					add(sc1, s % div, count);
					add(sc1, s / div, count);
				} else {
					add(sc1, s * 2024, count);
				}
			}
			sc = sc1;
		}
		return sc.values().stream().reduce(0L, Long::sum);
	}

	private void add(Map<Long, Long> map, Long pos, Long value) {
		map.put(pos, value + map.getOrDefault(pos, 0L));
	}
}
