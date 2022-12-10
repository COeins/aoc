package de.coeins.aoc22;

import java.util.Set;
import java.util.stream.Collectors;

class Day3 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int total = 0;
		for (String i : in) {
			Set<Character> left, right, intersect;
			left = i.substring(0, i.length() / 2).chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
			right = i.substring(i.length() / 2, i.length()).chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
			intersect = left.stream().filter(right::contains).collect(Collectors.toSet());
			int value = intersect.stream().mapToInt(this::valueOf).sum();
			total += value;
			//log("l:", left, "r:", right, "i:", intersect, "v:", value);
		}
		return total;
	}

	@Override
	public Integer task2(String[] in) {
		int total = 0;
		for (int i = 0; i < in.length; i += 3) {
			Set<Character> e1, e2, e3, intersect;
			e1 = in[i].chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
			e2 = in[i + 1].chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
			e3 = in[i + 2].chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
			intersect = e1.stream().filter(e2::contains).filter(e3::contains).collect(Collectors.toSet());
			int value = intersect.stream().mapToInt(this::valueOf).sum();
			total += value;
			//log("i:", intersect, "v:", value);
		}
		return total;
	}

	private Integer valueOf(Character c) {
		return c > 96 ? c - 96 : c - 38;
	}
}
