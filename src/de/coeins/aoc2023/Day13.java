package de.coeins.aoc2023;

class Day13 implements Day {
	@Override
	public Object task1(String[] in) {
		int sum = 0;
		int blockStart = 0;
		for (int i = 0; i < in.length; i++)
			if (in[i].equals("")) {
				sum += findMirror(in, blockStart, i, 0);
				blockStart = i + 1;
			}
		sum += findMirror(in, blockStart, in.length, 0);
		return sum;
	}

	@Override
	public Object task2(String[] in) {
		int sum = 0;
		int blockStart = 0;
		for (int i = 0; i < in.length; i++)
			if (in[i].equals("")) {
				sum += findMirror(in, blockStart, i, 1);
				blockStart = i + 1;
			}
		sum += findMirror(in, blockStart, in.length, 1);
		return sum;
	}

	private int findMirror(String[] in, int blockStart, int blockEnd, int errors) {
		int lines = blockEnd - blockStart;
		int cols = in[blockStart].length();

		lines:
		for (int l = 1; l < lines; l++) {
			int errorCount = 0;
			for (int c = 0; c < cols; c++)
				for (int i = 0; i < l && i < lines - l; i++) {
					if (in[blockStart + l - i - 1].charAt(c) != in[blockStart + l + i].charAt(c))
						if (errorCount++ > errors)
							continue lines;
				}
			log("Symmetry found at line", l, "with", errorCount, "errors");
			if (errorCount == errors)
				return 100 * l;
		}

		cols:
		for (int c = 1; c < cols; c++) {
			int errorCount = 0;
			for (int l = 0; l < lines; l++)
				for (int i = 0; i < c && i < cols - c; i++) {
					if (in[blockStart + l].charAt(c - i - 1) != in[blockStart + l].charAt(c + i))
						if (errorCount++ > errors)
							continue cols;
				}
			log("Symmetry found at col", c, "with", errorCount, "errors");
			if (errorCount == errors)
				return c;
		}
		return 0;
	}
}
