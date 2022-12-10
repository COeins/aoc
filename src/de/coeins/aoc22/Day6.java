package de.coeins.aoc22;

class Day6 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		char a = in[0].charAt(0);
		char b = in[0].charAt(1);
		char c = in[0].charAt(2);
		for (int i = 3; i < in[0].length(); i++) {
			char d = in[0].charAt(i);
			if (a != b && a != c && b != c && a != d && b != d && c != d) {
				//log(a, b, c, d, i);
				return i + 1;
			}
			a = b;
			b = c;
			c = d;
		}
		return -1;
	}

	@Override
	public Integer task2(String[] in) {
		int max = 14;
		next:
		for (int i = max; i < in[0].length(); ) {
			for (int j = i - max; j < i; j++) {
				for (int k = j + 1; k < i; k++) {
					if (in[0].charAt(j) == in[0].charAt(k)) {
						//log("at pos", i, "char", j, in[0].charAt(j), "and", k, in[0].charAt(k), "overlap");
						i = j + max + 1;
						continue next;
					}
				}
			}
			return i;
		}
		return -1;
	}
}
