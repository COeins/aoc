package de.coeins.aoc22;

class Day22 implements Day<Integer> {
	private final static int BORDER = 0;
	private final static int FLOOR = 1;
	private final static int WALL = 2;
	private final static int RIGHT = 0;
	private final static int DOWN = 1;
	private final static int LEFT = 2;
	private final static int UP = 3;
	private final static int[][] DIRECTION = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	@Override
	public Integer task1(String[] in) {
		int[][] map = parse(in);
		int[] pos = new int[3];
		while (map[pos[0]][pos[1]] == 0)
			pos[1]++;

		for (int j = 0; j < in[in.length - 1].length(); j++) {
			if (in[in.length - 1].charAt(j) == 'R')
				pos[2] = (pos[2] + 1) % 4;
			else if (in[in.length - 1].charAt(j) == 'L')
				pos[2] = (pos[2] + 3) % 4;
			else {
				int steps = in[in.length - 1].charAt(j) - 0x30;
				while ((j + 1) < in[in.length - 1].length() && in[in.length - 1].charAt(j + 1) < 0x40)
					steps = 10 * steps + in[in.length - 1].charAt(++j) - 0x30;
				int skip = 1;
				for (int s = 0; s < steps; s++) {
					int y = (map.length + pos[0] + skip * DIRECTION[pos[2]][0]) % map.length;
					int x = (map[0].length + pos[1] + skip * DIRECTION[pos[2]][1]) % map[0].length;
					if (map[y][x] == WALL)
						break;
					if (map[y][x] == BORDER) {
						s--;
						skip++;
					} else {
						pos[0] = y;
						pos[1] = x;
						skip = 1;
					}
				}
			}
		}
		log(pos[0], pos[1], pos[2]);
		return (pos[0] + 1) * 1000 + (pos[1] + 1) * 4 + pos[2];
	}

	@Override
	public Integer task2(String[] in) {
		int[][][] transitions = new int[33][4][3];
		// TODO generate transitions automatically from task input
		// Transitions for input 0:
		transitions[11][UP] = new int[] { 1, 2, RIGHT };
		transitions[12][RIGHT] = new int[] { 1, 23, DOWN };
		transitions[22][DOWN] = new int[] { 1, 10, UP };

		// Transitions for input 1: (fortunately, they don't overlap)
		transitions[1][LEFT] = new int[] { 1, 20, RIGHT };
		transitions[1][UP] = new int[] { 1, 30, RIGHT };
		transitions[2][RIGHT] = new int[] { 1, 21, LEFT };
		transitions[2][DOWN] = new int[] { 1, 11, LEFT };
		transitions[2][UP] = new int[] { 1, 30, UP };
		transitions[11][RIGHT] = new int[] { 1, 2, UP };
		transitions[11][LEFT] = new int[] { 1, 20, DOWN };
		transitions[20][LEFT] = new int[] { 1, 1, RIGHT };
		transitions[20][UP] = new int[] { 1, 11, RIGHT };
		transitions[21][RIGHT] = new int[] { 1, 2, LEFT };
		transitions[21][DOWN] = new int[] { 1, 30, LEFT };
		transitions[30][RIGHT] = new int[] { 1, 21, UP };
		transitions[30][DOWN] = new int[] { 1, 2, DOWN };
		transitions[30][LEFT] = new int[] { 1, 1, DOWN };

		int[][] map = parse(in);
		int[] pos = new int[3];
		int face_len = map[0].length / (map.length > map[0].length ? 3 : 4);
		if (face_len != map.length / (map.length > map[0].length ? 4 : 3))
			throw new RuntimeException("Not a cube!");

		while (map[pos[0]][pos[1]] == 0)
			pos[1]++;

		int face = 10 * (pos[0] / face_len) + pos[1] / face_len;
		for (int j = 0; j < in[in.length - 1].length(); j++) {
			if (in[in.length - 1].charAt(j) == 'R')
				pos[2] = (pos[2] + 1) % 4;
			else if (in[in.length - 1].charAt(j) == 'L')
				pos[2] = (pos[2] + 3) % 4;
			else {
				int steps = in[in.length - 1].charAt(j) - 0x30;
				while ((j + 1) < in[in.length - 1].length() && in[in.length - 1].charAt(j + 1) < 0x40)
					steps = 10 * steps + in[in.length - 1].charAt(++j) - 0x30;
				int current_dir = pos[2];
				for (int s = 0; s < steps; s++) {
					int y = pos[0] + DIRECTION[current_dir][0];
					int x = pos[1] + DIRECTION[current_dir][1];

					if (x < 0 || y < 0 || x >= map[0].length || y >= map.length || map[y][x] == BORDER) {
						int exit_pos = -1;
						switch (current_dir) {
							case 0:
								exit_pos = pos[0] % face_len;
								break;
							case 1:
								exit_pos = face_len - 1 - pos[1] % face_len;
								break;
							case 2:
								exit_pos = face_len - 1 - pos[0] % face_len;
								break;
							case 3:
								exit_pos = pos[1] % face_len;
								break;
						}
						if (transitions[face][current_dir][0] == 0)
							throw new RuntimeException("transition " + face + ", " + current_dir + " undefined");
						int top_y = transitions[face][current_dir][1] / 10 * face_len;
						int left_x = transitions[face][current_dir][1] % 10 * face_len;
						switch (transitions[face][current_dir][2]) {
							case 0:
								y = top_y + exit_pos;
								x = left_x;
								break;
							case 1:
								y = top_y;
								x = left_x + face_len - 1 - exit_pos;
								break;
							case 2:
								y = top_y + face_len - 1 - exit_pos;
								x = left_x + face_len - 1;
								break;
							case 3:
								y = top_y + face_len - 1;
								x = left_x + exit_pos;
								break;
						}

						log("Transition from ", face, ",", current_dir, "(", exit_pos, ")  --> ",
								transitions[face][current_dir][1], ",", transitions[face][current_dir][2], "at", y, x);
						current_dir = transitions[face][current_dir][2];
					}
					if (map[y][x] == WALL)
						break;
					if (map[y][x] == FLOOR) {
						pos[0] = y;
						pos[1] = x;
						pos[2] = current_dir;
						face = 10 * (pos[0] / face_len) + pos[1] / face_len;
					}
				}
			}
		}
		log(pos[0], pos[1], pos[2], face);
		return (pos[0] + 1) * 1000 + (pos[1] + 1) * 4 + pos[2];
	}

	int[][] parse(String[] in) {
		int w = 0;
		for (int i = 0; i < in.length - 2; i++)
			if (w < in[i].length())
				w = in[i].length();
		int[][] map = new int[in.length - 2][w];
		for (int i = 0; i < in.length - 2; i++)
			for (int j = 0; j < in[i].length(); j++)
				if (in[i].charAt(j) != ' ')
					map[i][j] = in[i].charAt(j) == '#' ? WALL : FLOOR;
		return map;
	}
}
