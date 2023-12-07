package de.coeins.aoc2023;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day1 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int result = 0;
		Pattern first = Pattern.compile("^[a-z]*([0-9]).*");
		Pattern last = Pattern.compile(".*([0-9])[a-z]*$");
		for (String i : in) {
			Matcher mf = first.matcher(i);
			Matcher ml = last.matcher(i);
			if (mf.find() && ml.find()) {
				result += Integer.parseInt(mf.group(1)) * 10 + Integer.parseInt(ml.group(1));
			} else {
				log(i, "no match found, skipping");
			}
		}
		return result;
	}

	@Override
	public Integer task2(String[] in) {
		int result = 0;
		Pattern first = Pattern.compile("([0-9]|one|two|three|four|five|six|seven|eight|nine).*");
		Pattern last = Pattern.compile(".*([0-9]|one|two|three|four|five|six|seven|eight|nine)[a-z]*$");
		for (String i : in) {
			Matcher mf = first.matcher(i);
			Matcher ml = last.matcher(i);
			if (mf.find() && ml.find()) {
				int nf = mf.group(1).length() == 1 ? Integer.parseInt(mf.group(1)) : digits.valueOf(mf.group(1)).ordinal();
				int nl = ml.group(1).length() == 1 ? Integer.parseInt(ml.group(1)) : digits.valueOf(ml.group(1)).ordinal();
				log(i, nf, nl);
				result += nf * 10 + nl;
			} else {
				log(i, "not match found, skipping");
			}
		}
		return result;
	}

	enum digits {
		zero, one, two, three, four, five, six, seven, eight, nine;
	}
}
