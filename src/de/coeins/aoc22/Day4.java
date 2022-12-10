package de.coeins.aoc22;

class Day4 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int pairs = 0;
		for (String l : in) {
			int[] e = parse(l);
			boolean overlap = (e[0] <= e[2] && e[1] >= e[3]) || (e[0] >= e[2] && e[1] <= e[3]);
			//log(e[0], "-", e[1], ",", e[2], "-", e[3], overlap);
			if (overlap)
				pairs++;
		}
		return pairs;
	}

	@Override
	public Integer task2(String[] in) {
		int pairs = 0;
		for (String l : in) {
			int[] e = parse(l);
			boolean overlap = e[1] >= e[2] && e[3] >= e[0];
			//log(e[0], "-", e[1], ",", e[2], "-", e[3], overlap);
			if (overlap)
				pairs++;
		}
		return pairs;
	}

	private int[] parse(String l) {
		int[] e = new int[4];
		String[] l1 = l.split(",");
		String[] e1 = l1[0].split("-");
		String[] e2 = l1[1].split("-");
		e[0] = Integer.parseInt(e1[0]);
		e[1] = Integer.parseInt(e1[1]);
		e[2] = Integer.parseInt(e2[0]);
		e[3] = Integer.parseInt(e2[1]);
		return e;
	}
}
