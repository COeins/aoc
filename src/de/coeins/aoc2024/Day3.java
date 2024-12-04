package de.coeins.aoc2024;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.coeins.aoc2023.Day;

class Day3 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
		int sum = 0;
		for (String i : in) {
			Matcher matcher = pattern.matcher(i);
			while (matcher.find()) {
				sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
			}
		}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		Pattern pattern = Pattern.compile("(do|don't|mul)\\(((\\d+),(\\d+))?\\)");
		int sum = 0;
		int enabled = 1;
		for (String i : in) {
			Matcher matcher = pattern.matcher(i);
			while (matcher.find()) {
				switch (matcher.group(1)) {
					case "do":
						enabled = 1;
						break;
					case "don't":
						enabled = 0;
						break;
					case "mul":
						sum += enabled * Integer.parseInt(matcher.group(3)) * Integer.parseInt(matcher.group(4));
				}
			}
		}
		return sum;
	}
}
