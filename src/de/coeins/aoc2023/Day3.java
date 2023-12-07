package de.coeins.aoc2023;

class Day3 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int sum = 0;
		map2D map = new map2D(in, '.');
		for (int i = 0; i < map.maxI; i++)
			for (int j = 0; j < map.maxJ; j++)
				if (map.isDigit(i, j)) {
					int k = 0;
					int n = 0;
					do {
						n = 10 * n + map.valueAt(i, j + k++);
					}
					while (j + k < map.maxJ && map.isDigit(i, j + k));

					boolean valid = map.charAt(i, j - 1) != '.' || map.charAt(i, j + k) != '.';
					for (int l = j - 1; l <= j + k; l++)
						valid |= map.charAt(i - 1, l) != '.' || map.charAt(i + 1, l) != '.';
					j += k;
					if (valid)
						sum += n;
				}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		int sum = 0;
		map2D map = new map2D(in, '.');
		for (int i = 0; i < map.maxI; i++)
			for (int j = 0; j < map.maxJ; j++)
				if (map.charAt(i, j) == '*') {
					int[] numbers = new int[2];
					int k = 0;
					for (int i1 = i - 1; i1 <= i + 1; i1++)
						for (int j1 = j - 1; j1 <= j + 1; j1++)
							if (map.isDigit(i1, j1)) {
								int n = map.findNumer(i1, j1);
								if (k == 0 || n != numbers[k - 1])
									numbers[k++] = n;
								if (k > 1)
									break;
							}

					log(in[i], i, j, numbers[0], numbers[1]);
					sum += numbers[0] * numbers[1];
				}
		return sum;
	}

	private class map2D {
		final char boundary;
		final String[] data;
		int maxI;
		int maxJ;

		map2D(String[] data, char boundary) {
			this.data = data;
			this.boundary = boundary;
			maxI = data.length;
			maxJ = data[0].length();
		}

		private char charAt(int i, int j) {
			if (i < 0 || j < 0 || i >= maxI || j >= maxJ)
				return boundary;
			else
				return data[i].charAt(j);
		}

		private boolean isDigit(int i, int j) {
			char c = charAt(i, j);
			return c >= '0' && c <= '9';
		}

		private int valueAt(int i, int j) {
			return charAt(i, j) - '0';
		}

		private int findNumer(int i, int j) {
			int k = j;
			while (isDigit(i, k - 1))
				k--;
			int number = 0;
			while (isDigit(i, k))
				number = 10 * number + valueAt(i, k++);
			return number;
		}
	}

}
