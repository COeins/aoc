package de.coeins.aoc2023;

import java.util.Arrays;

class Day9 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int sum = 0;
		for (String l : in) {
			int[] numbers = parseInts(l, " ");
			int next = nextNumer(numbers);
			log(numbers, "->", next);
			sum += next;
		}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		int sum = 0;
		for (String l : in) {
			int[] numbers = parseInts(l, " ");
			int prev = prevNumer(numbers);
			log(prev, "<-", numbers);
			sum += prev;
		}
		return sum;
	}

	int[] parseInts(String in, String divider) {
		return Arrays.stream(in.split(divider)).mapToInt(s -> {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return 0;
			}
		}).toArray();
	}

	int nextNumer(int[] numbers) {
		int[] differences = new int[numbers.length - 1];
		boolean constant = true;
		for (int i = 0; i < numbers.length - 1; i++) {
			differences[i] = numbers[i + 1] - numbers[i];
			constant &= i == 0 || differences[i] == differences[i - 1];
		}
		return numbers[numbers.length - 1] + (constant ? differences[0] : nextNumer(differences));
	}

	int prevNumer(int[] numbers) {
		int[] differences = new int[numbers.length - 1];
		boolean constant = true;
		for (int i = 0; i < numbers.length - 1; i++) {
			differences[i] = numbers[i + 1] - numbers[i];
			constant &= i == 0 || differences[i] == differences[i - 1];
		}
		return numbers[0] - (constant ? differences[0] : prevNumer(differences));
	}
}
