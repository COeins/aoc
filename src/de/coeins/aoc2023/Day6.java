package de.coeins.aoc2023;

class Day6 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		String[] t = in[0].split(" +");
		String[] d = in[1].split(" +");

		int product = 1;
		for (int i = 1; i < t.length; i++) {
			int count = 0;
			int time = Integer.parseInt(t[i]);
			int dist = Integer.parseInt(d[i]);
			for (int j = 1; j < time; j++) {
				int travel = j * (time - j);
				if (travel > dist)
					count++;
				log("race", i, "wait", j, "travel", travel);
			}
			product *= count;
		}
		return product;
	}

	@Override
	public Integer task2(String[] in) {
		// 0 = x² -tx + d
		// l1,2 = (t ± sqrt(t² -4d)) / 2
		long t = Long.parseLong(in[0].split(":")[1].replaceAll("\\s", ""));
		long d = Long.parseLong(in[1].split(":")[1].replaceAll("\\s", ""));
		double root = Math.sqrt(t * t - 4 * d);
		int l1 = (int) (t - root) / 2;
		int l2 = (int) (t + root) / 2;
		log(l1, l2);
		return l2 - l1;
	}

	public Integer task2simple(String[] in) {
		long t = Long.parseLong(in[0].split(":")[1].replaceAll("\\s", ""));
		long d = Long.parseLong(in[1].split(":")[1].replaceAll("\\s", ""));
		long count = 0;
		for (int n = 1; n < t; n++)
			if (n * (t - n) > d)
				count++;
		return (int) count;
	}
}
