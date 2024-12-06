package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day5 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Puzzle p = parse(in);
		int sum = 0;
		pageLoop:
		for (List<Integer> page : p.pages()) {
			for (int idxA = 0; idxA < page.size(); idxA++) {
				int a = page.get(idxA);
				if (p.after.containsKey(page.get(idxA))) {
					List<Integer> comp = p.after.get(page.get(idxA));
					for (int idxB = 0; idxB < idxA; idxB++) {
						if (comp.contains(page.get(idxB))) {
							log("discarding list", page, "because", page.get(idxB), "comes before", a);
							continue pageLoop;
						}
					}

				}
			}
			log("accepting list", page);
			sum += page.get((page.size() - 1) / 2);
		}
		return sum;
	}

	public Integer task2(String[] in) {
		Puzzle p = parse(in);
		int sum = 0;
		pageLoop:
		for (List<Integer> pageStatic : p.pages()) {
			List<Integer> page = new ArrayList<>(pageStatic);
			int swaps = 0;
			boolean errorFound = true;
			while (errorFound) {
				errorFound = false;
				checkLoop:
				for (int idxA = 0; idxA < page.size(); idxA++) {
					int a = page.get(idxA);
					if (p.after.containsKey(page.get(idxA))) {
						List<Integer> comp = p.after.get(page.get(idxA));
						for (int idxB = 0; idxB < idxA; idxB++) {
							int b = page.get(idxB);
							if (comp.contains(b)) {
								swaps++;
								errorFound = true;
								page.set(idxB, a);
								page.set(idxA, b);
								break checkLoop;
							}
						}

					}
				}
			}
			if (swaps > 0) {
				log("fixed list", page, "with", swaps, "swaps");
				sum += page.get((page.size() - 1) / 2);
			}
		}
		return sum;
	}

	private Puzzle parse(String[] in) {
		Map<Integer, List<Integer>> after = new HashMap<>();
		List<List<Integer>> pages = new ArrayList<>();

		int line = 0;
		while (line < in.length && in[line].length() > 1) {
			String[] split = in[line].split("\\|");
			int a = Integer.parseInt(split[0]);
			int b = Integer.parseInt(split[1]);
			List<Integer> l = after.getOrDefault(a, new ArrayList<>());
			l.add(b);
			after.put(a, l);
			line++;
		}
		line++;
		while (line < in.length) {
			pages.add(Arrays.stream(in[line].split(",")).map(Integer::parseInt).toList());
			line++;
		}
		return new Puzzle(after, pages);
	}

	record Puzzle(Map<Integer, List<Integer>> after, List<List<Integer>> pages) {
	}

}
