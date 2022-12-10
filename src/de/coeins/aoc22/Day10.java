package de.coeins.aoc22;

class Day10 implements Day<Integer> {

	@Override
	public Integer task1(String[] in) {
		int sum = 0;
		int x = 1;
		int t = 1;
		for (String l : in) {
			if ((++t - 20) % 40 == 0) {
				log(t, l, "(1)", x, sum);
				sum += t * x;
			}
			if (l.startsWith("addx")) {
				x += Integer.parseInt(l.split(" ")[1]);
				if ((++t - 20) % 40 == 0) {
					log(t, l, "(2)", x, sum);
					sum += t * x;
				}
			}
		}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		boolean[] pixles = new boolean[240];
		int x = 1;
		int t = 0;
		for (String l : in) {
			if ((t % 40) >= (x - 1) && (t % 40) <= (x + 1)) {
				pixles[t] = true;
			}
			t ++;
			if (l.startsWith("addx")) {
				if ((t % 40) >= (x - 1) && (t % 40) <= (x + 1)) {
					pixles[t] = true;
				}
				t ++;
				x += Integer.parseInt(l.split(" ")[1]);
			}
		}
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < 240; i++) {
			if (i > 0 && i % 40 == 0)
				output.append("\n");
			output.append(pixles[i] ? "▓" : "░");
		}
		log(output);
		return -1;
	}
}
