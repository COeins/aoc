package de.coeins.aoc22;

import java.util.List;

class Day25 implements Day<String> {
	private static final List<Character> digits = List.of('=', '-', '0', '1', '2');

	@Override
	public String task1(String[] in) {
		long sum = 0;
		for (String l : in) {
			sum += fromSnafu(l);
		}
		String sna = toSnafu(sum);
		log(sum, sna);
		return sna;
	}

	@Override
	public String task2(String[] in) {
		for (int i = 0; i < 21; i++) {
			String snafu = toSnafu(i);
			long decimal = fromSnafu(snafu);
			log(i, snafu, decimal);
		}
		return "";
	}

	private long fromSnafu(String in) {
		long number = 0;
		long place = 1;
		for (int i = 1; i <= in.length(); i++) {
			number += place * (digits.indexOf(in.charAt(in.length() - i)) - 2);
			place *= 5;
		}
		return number;
	}

	private String toSnafu(long in) {
		StringBuilder number = new StringBuilder();
		for (long place = 1; in > 0; place *= 5) {
			long remainder = in % (place * 5);
			if (remainder > (place * 2))
				remainder -= (place * 5);
			in -= remainder;
			number.insert(0, digits.get((int) (remainder / place) + 2));
		}
		return number.length() > 0 ? number.toString() : "0";
	}
}
