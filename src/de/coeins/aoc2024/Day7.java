package de.coeins.aoc2024;

class Day7 implements de.coeins.aoc2023.Day<Long> {
	@Override
	public Long task1(String[] in) {
		return parseAndTest(in, 2);
	}

	public Long task2(String[] in) {
		return parseAndTest(in, 3);
	}

	private long parseAndTest(String[] in, int ops) {
		long sum = 0;
		for (String l : in) {
			String[] split = l.split(" ");
			long result = Long.parseLong(split[0].split(":")[0]);
			long[] numbers = new long[split.length - 1];
			for (int s = 1; s < split.length; s++)
				numbers[s - 1] = Long.parseLong(split[s]);
			sum += testOperations(result, numbers, ops);
		}
		return sum;
	}

	private long testOperations(long result, long[] numbers, int ops) {
		options:
		for (int i = 0; i < Math.pow(ops, (numbers.length - 1)); i++) {
			long test = numbers[0];
			StringBuilder memo = new StringBuilder().append(result).append(" = ").append(numbers[0]);
			for (int j = 1; j < numbers.length; j++) {
				int op = ((int) (i / Math.pow(ops, j - 1))) % ops;
				if (op == 0) {
					memo.append(" + ").append(numbers[j]);
					test += numbers[j];
				} else if (op == 1) {
					memo.append(" * ").append(numbers[j]);
					test *= numbers[j];
				} else if (op == 2) {
					memo.append(" || ").append(numbers[j]);
					test = ((long) (test * Math.pow(10, ("" + numbers[j]).length()))) + numbers[j];
				}
				if (test > result)
					continue options;
			}
			if (test == result) {
				log("found solution", memo);
				return result;
			}
		}
		return 0;
	}
}
