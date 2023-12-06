package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.List;

class Day4 implements Day {
	@Override
	public Object task1(String[] in) {
		int sum = 0;
		for (String l : in) {
			int matches = findMatches(l);
			log(l, matches);
			if (matches > 0)
				sum += Math.pow(2, matches - 1);
		}
		return sum;
	}

	@Override
	public Object task2(String[] in) {
		int sum = 0;
		int[] cards = new int[in.length];
		for (int c = 0; c < in.length; c++) {
			int matches = findMatches(in[c]);
			cards[c]++;
			for (int i = 1; i <= matches; i++)
				cards[c + i] += cards[c];
			sum += cards[c];
			log("card", c, "times", cards[c], "wins", matches, "sum", sum);
		}

		return sum;
	}

	private int findMatches(String line) {
		String[] l1 = line.split(":");
		String[] numbers = l1[1].split(" ");
		List<Integer> group1 = new ArrayList<>(numbers.length);
		List<Integer> group2 = new ArrayList<>(numbers.length);
		List<Integer> group = group1;
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i].equals("|"))
				group = group2;
			else if (!numbers[i].equals("")) {
				try {
					group.add(Integer.parseInt(numbers[i]));
				} catch (NumberFormatException e) {
					log(e.getMessage(), numbers[i]);
				}
			}
		}
		group1.retainAll(group2);
		return group1.size();
	}
}
