package de.coeins.aoc22;

class Day8 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Integer[][] map = parse(in);
		int maxX = map.length;
		int maxY = map[0].length;
		int visibleCount = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				boolean visible = scan(map, i, j, -1, 0, 1, 0) > 0;
				visible |= scan(map, i, j, 0, -1, 1, 0) > 0;
				visible |= scan(map, i, j, 1, 0, 1, 0) > 0;
				visible |= scan(map, i, j, 0, 1, 1, 0) > 0;
				if (visible) {
					visibleCount++;
				}
			}
		}
		return visibleCount;
	}

	@Override
	public Integer task2(String[] in) {
		Integer[][] map = parse(in);
		int maxX = map.length;
		int maxY = map[0].length;
		int maxscore = 0;
		for (int i = 1; i < map.length - 1; i++) {
			for (int j = 1; j < map[i].length - 1; j++) {
				int scocet = scan(map, i, j, -1, 0, 0, 1);
				int scorel = scan(map, i, j, 0, -1, 0, 1);
				int scoreb = scan(map, i, j, 1, 0, 0, 1);
				int scorer = scan(map, i, j, 0, 1, 0, 1);
				int total = scocet * scoreb * scorel * scorer;
				if (total > maxscore) {
					log("new candidate:", i, j, "(", map[i][j], ")", scocet, scorel, scoreb, scorer, "=", total);
					maxscore = total;
				}
			}
		}
		return maxscore;
	}

	private int scan(Integer[][] map, int x, int y, int dx, int dy, int border, int mid) {
		int score = 0;
		for (int c = 1; true; c++) {
			int xc = x + c * dx;
			int yc = y + c * dy;
			if (xc < 0 || xc >= map.length || yc < 0 || yc >= map[x].length) {
				score += border;
				break;
			} else {
				score += mid;
			}
			if (map[x][y] <= map[xc][yc])
				break;
		}
		return score;
	}

	Integer[][] parse(String[] in) {
		Integer[][] map = new Integer[in.length][];
		for (int i = 0; i < in.length; i++) {
			map[i] = new Integer[in[i].length()];
			for (int j = 0; j < in[i].length(); j++)
				map[i][j] = Integer.parseInt(in[i].substring(j, j + 1));
		}
		return map;
	}
}
