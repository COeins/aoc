package de.coeins.aoc22;

import java.util.List;

class Day12 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		List<int[][]> input = parse(in);
		int[][] direction = input.get(0);
		int[][] map = input.get(1);
		logMap(map);
		int[][] cost = new int[map.length][map[0].length];
		findPath(direction[1], map, cost, direction[0][0], direction[0][1], 1);
		logMap(cost);
		return cost[direction[1][0]][direction[1][1]] - 1;
	}

	@Override
	public Integer task2(String[] in) {
		List<int[][]> input = parse(in);
		int[] target = input.get(0)[1];
		int[][] map = input.get(1);
		int minPath = Integer.MAX_VALUE;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] != 0)
					continue;
				int[][] cost = new int[map.length][map[0].length];
				findPath(target, map, cost, i, j, 1);
				int curPath = cost[target[0]][target[1]] - 1;
				log("Path from ", i, j, "has length", curPath);
				if (curPath > 0 && curPath < minPath)
					minPath = curPath;
			}
		}
		return minPath;
	}

	private void findPath(int[] target, int[][] map, int[][] cost, int x, int y, int current) {
		if (cost[x][y] != 0 && cost[x][y] <= current)
			return;
		cost[x][y] = current;
		if (x == target[0] && y == target[1])
			return;
		if (x > 0 && map[x - 1][y] <= map[x][y] + 1)
			findPath(target, map, cost, x - 1, y, current + 1);
		if (y > 0 && map[x][y - 1] <= map[x][y] + 1)
			findPath(target, map, cost, x, y - 1, current + 1);
		if (x + 1 < map.length && map[x + 1][y] <= map[x][y] + 1)
			findPath(target, map, cost, x + 1, y, current + 1);
		if (y + 1 < map[0].length && map[x][y + 1] <= map[x][y] + 1)
			findPath(target, map, cost, x, y + 1, current + 1);
	}

	private void logMap(int[][] map) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				s.append(String.format("%03d", map[i][j])).append(" ");
			}
			s.append("\n");
		}
		log(s.append("\n"));
	}

	private List<int[][]> parse(String[] input) {
		int[][] map = new int[input.length][input[0].length()];
		int[][] direction = new int[2][2];

		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length(); j++) {
				char x = input[i].charAt(j);
				if (x == 'S') {
					direction[0][0] = i;
					direction[0][1] = j;
					x = 'a';
				}
				if (x == 'E') {
					direction[1][0] = i;
					direction[1][1] = j;
					x = 'z';
				}
				map[i][j] = x - 'a';
			}
		}
		return List.of(direction, map);
	}

}
