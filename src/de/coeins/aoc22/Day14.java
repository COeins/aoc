package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Day14 implements Day<Integer> {
	private static final int EMPTY = 0;
	private static final int WALL = 1;
	private static final int SAND = 2;
	private static final int START_POINT = 500;

	@Override
	public Integer task1(String[] in) {
		World w = parse(in);
		int[][] map = new int[w.maxX - w.minX + 1][w.maxY + 1];
		for (Line l : w.lines)
			l.draw(map, w.minX);
		return pourSand(map, w.minX);
	}

	@Override
	public Integer task2(String[] in) {
		World w = parse(in);
		w.maxY += 2;
		int bottomStart = START_POINT - w.maxY;
		int bottomEnd = START_POINT + w.maxY;
		w.minX = Math.min(w.minX, bottomStart);
		w.maxX = Math.max(w.maxX, bottomEnd);
		w.lines.add(new Line(List.of(new Integer[] { bottomStart, w.maxY }, new Integer[] { bottomEnd, w.maxY })));
		int[][] map = new int[w.maxX - w.minX + 1][w.maxY + 1];
		for (Line l : w.lines)
			l.draw(map, w.minX);
		return pourSand(map, w.minX);
	}

	private World parse(String[] in) {
		List<Line> lines = new ArrayList<>(in.length);
		int maxX = 0;
		int maxY = 0;
		int minX = Integer.MAX_VALUE;
		for (String l : in) {
			Line currentLine = new Line(Collections.emptyList());
			lines.add(currentLine);
			for (String point : l.split(" -> ")) {
				String[] coords = point.split(",");
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);
				maxX = Math.max(maxX, x);
				maxY = Math.max(maxY, y);
				minX = Math.min(minX, x);
				currentLine.addPoint(new Integer[] { x, y });
			}
		}
		return new World(lines, maxX, maxY, minX);
	}

	private int pourSand(int[][] map, int offsetX) {
		int sandCount = 0;
		try {
			while (true) {
				int x = START_POINT - offsetX;
				int y = 0;
				if (map[x][y] != EMPTY)
					return sandCount;
				boolean falling = true;
				while (falling) {
					if (map[x][y + 1] == EMPTY) {
						y++;
					} else if (map[x - 1][y + 1] == EMPTY) {
						x--;
						y++;
					} else if (map[x + 1][y + 1] == EMPTY) {
						x++;
						y++;
					} else {
						map[x][y] = SAND;
						sandCount++;
						falling = false;
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return sandCount;
		}
	}

	private void logMap(int[][] map) {
		StringBuilder s = new StringBuilder();
		for (int y = 0; y < map[0].length; y++) {
			s.append("line ").append(y).append(": ");
			for (int x = 0; x < map.length; x++)
				s.append(map[x][y]).append(" ");
			s.append("\n");
		}
		log(s);
	}

	private class World {
		List<Line> lines;
		int maxX;
		int maxY;
		int minX;

		private World(List<Line> lines, int maxX, int maxY, int minX) {
			this.lines = lines;
			this.maxX = maxX;
			this.maxY = maxY;
			this.minX = minX;
		}
	}

	private class Line {
		private final List<Integer[]> points;

		private Line(List<Integer[]> points) {
			this.points = new ArrayList<>();
			this.points.addAll(points);
		}

		private void addPoint(Integer[] point) {
			points.add(point);
		}

		private void draw(int[][] map, int offsetX) {
			Integer[] pos = points.get(0);
			map[pos[0] - offsetX][pos[1]] = WALL;
			for (int i = 1; i < points.size(); i++) {
				int dx = points.get(i)[0] - pos[0];
				int dy = points.get(i)[1] - pos[1];
				int steps = Math.max(Math.abs(dx), Math.abs(dy));
				for (int j = 1; j <= steps; j++)
					map[pos[0] - offsetX + j * dx / steps][pos[1] + j * dy / steps] = WALL;
				pos = points.get(i);
			}
		}
	}
}
