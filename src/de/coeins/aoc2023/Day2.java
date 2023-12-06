package de.coeins.aoc2023;

class Day2 implements Day {

	@Override
	public Object task1(String[] in) {
		int result = 0;
		int[] max = new int[] { 12, 13, 14 };
		games:
		for (String l : in) {
			String[] outer = l.split(": ");
			String[] rounds = outer[1].split("; ");
			int game = Integer.parseInt(outer[0].split(" ")[1]);
			for (String round : rounds) {
				for (String draw : round.split(", ")) {
					String[] inner = draw.split(" ");
					if (Integer.parseInt(inner[0]) > max[color.valueOf(inner[1]).ordinal()])
						continue games;
				}
			}
			result += game;
		}
		return result;
	}

	@Override
	public Object task2(String[] in) {
		int result = 0;
		games:
		for (String l : in) {
			int[] min = new int[] { 0, 0, 0 };
			String[] outer = l.split(": ");
			String[] rounds = outer[1].split("; ");
			int game = Integer.parseInt(outer[0].split(" ")[1]);
			for (String round : rounds) {
				for (String draw : round.split(", ")) {
					String[] inner = draw.split(" ");
					if (Integer.parseInt(inner[0]) > min[color.valueOf(inner[1]).ordinal()])
						min[color.valueOf(inner[1]).ordinal()] = Integer.parseInt(inner[0]);
				}
			}
			int pow = min[0] * min[1] * min[2];
			log(game, min[0], min[1], min[2], pow);
			result += pow;
		}
		return result;
	}

	enum color {
		red, green, blue
	}
}
