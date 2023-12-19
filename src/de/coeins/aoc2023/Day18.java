package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.coeins.aoc2023.Layered2DMap.Point;

class Day18 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		int maxX = 0;
		int maxY = 0;
		int shiftX = 0;
		int shiftY = 0;
		int x = 0;
		int y = 0;
		List<Point> points = new ArrayList<>();
		for (String l : in) {
			String[] split = l.split(" ");
			int len = Integer.parseInt(split[1]);
			switch (split[0].charAt(0)) {
				case 'U' -> x -= len;
				case 'D' -> x += len;
				case 'L' -> y -= len;
				case 'R' -> y += len;
			}
			maxX = Math.max(maxX, x);
			maxY = Math.max(maxY, y);
			points.add(new Point(x, y));
			while (x + shiftX < 0)
				shiftX++;
			while (y + shiftY < 0)
				shiftY++;
		}

		//return simpleApproach(points, maxX, maxY, shiftX, shiftY);
		return optimizedApproach(points);
	}

	@Override
	public Long task2(String[] in) {
		int x = 0;
		int y = 0;
		List<Point> points = new ArrayList<>();
		for (String l : in) {
			String command = l.split("[()]")[1];
			int len = Integer.parseInt(command.substring(1, 6), 16);
			//log(x, y, len);
			switch (command.charAt(6)) {
				case '3' -> x -= len; // U
				case '1' -> x += len; // D
				case '2' -> y -= len; // L
				case '0' -> y += len; // R
			}
			points.add(new Point(x, y));
		}

		return optimizedApproach(points);
	}

	private long optimizedApproach(List<Point> points) {
		Set<Integer> xPoints = new HashSet<>();
		Set<Integer> yPoints = new HashSet<>();
		Set<Edge> longEdges = new HashSet<>();
		Set<Edge> shortEdges = new HashSet<>();

		// segment plain
		Point previous = new Point(0, 0);
		for (Point p : points) {
			xPoints.add(p.x());
			yPoints.add(p.y());
			longEdges.add(new Edge(previous, p));
			previous = p;
		}

		int[][] map = new int[xPoints.size() + 1][yPoints.size() + 1];
		List<Integer> xValues = xPoints.stream().sorted().toList();
		List<Integer> yValues = yPoints.stream().sorted().toList();

		// convert edges to short and split on region borders
		for (Edge e : longEdges) {
			int xStart = xValues.indexOf(e.x);
			int xEnd = xValues.indexOf(e.x + e.dx);
			int yStart = yValues.indexOf(e.y);
			int yEnd = yValues.indexOf(e.y + e.dy);
			if (e.dy == 0)
				for (int x = xStart; x < xEnd; x++)
					shortEdges.add(new Edge(x, yStart, 1, 0));
			else if (e.dx == 0)
				for (int y = yStart; y < yEnd; y++)
					shortEdges.add(new Edge(xStart, y, 0, 1));
			else
				throw new RuntimeException("Diagonals not supported");
		}

		// fill in outer region
		new TaskList<Point, Boolean>((tl, pos) -> {
			map[pos.x()][pos.y()] = 2;
			for (Layered2DMap.Direction d : Layered2DMap.CARDINALS) {
				Layered2DMap.Point pos1 = pos.applyDirection(d);
				if (pos1.x() < 0 || pos1.x() >= map.length || pos1.y() < 0 || pos1.y() >= map[pos1.x()].length
						|| map[pos1.x()][pos1.y()] != 0)
					continue;
				Edge e = null;
				switch (d) {
					case N -> e = new Edge(pos.x() - 1, pos.y() - 1, 0, 1);
					case S -> e = new Edge(pos.x(), pos.y() - 1, 0, 1);
					case E -> e = new Edge(pos.x() - 1, pos.y(), 1, 0);
					case W -> e = new Edge(pos.x() - 1, pos.y() - 1, 1, 0);
				}
				if (shortEdges.contains(e))
					continue;
				tl.recurse(pos1);
			}
			return Optional.of(true);
		}).run(new Point(0, 0));

		logMap(map);

		long sum = 0;

		for (int x = 0; x < map.length; x++)
			for (int y = 0; y < map[x].length; y++)
				if (map[x][y] == 0) {
					int edgeX = map[x + 1][y] != 0 ? 1 : 0;
					int edgeY = map[x][y + 1] != 0 ? 1 : 0;
					if (map[x][y + 1] != 0 && map[x - 1][y + 1] == 0)
						sum--;

					sum += (long) (xValues.get(x) - xValues.get(x - 1) + edgeX)
							* (yValues.get(y) - yValues.get(y - 1) + edgeY);
				}
		return sum;
	}

	private long simpleApproach(List<Point> points, int maxX, int maxY, int shiftX, int shiftY) {
		shiftX++;
		shiftY++;

		int[][] map = new int[maxX + shiftX + 2][maxY + shiftY + 2];
		int x = 0;
		int y = 0;
		for (Layered2DMap.Point p : points) {
			int newX = p.x();
			int newY = p.y();
			int len = Math.abs(newX - x) + Math.abs(newY - y);
			int dx = Integer.signum(newX - x);
			int dy = Integer.signum(newY - y);
			for (int i = 0; i < len; i++) {
				x += dx;
				y += dy;
				map[x + shiftX][y + shiftY] = 1;
			}
		}

		new TaskList<Layered2DMap.Point, Boolean>((tl, pos) -> {
			map[pos.x()][pos.y()] = 2;
			for (Layered2DMap.Direction d : Layered2DMap.CARDINALS) {
				Layered2DMap.Point pos1 = pos.applyDirection(d);
				if (pos1.x() < 0 || pos1.x() >= map.length || pos1.y() < 0 || pos1.y() >= map[pos1.x()].length
						|| map[pos1.x()][pos1.y()] != 0)
					continue;
				tl.recurse(pos1);
			}
			return Optional.of(true);
		}).run(new Layered2DMap.Point(0, 0));
		logMap(map);

		long sum = 0;
		for (int ix = 0; ix < map.length; ix++)
			for (int iy = 0; iy < map[ix].length; iy++)
				if (map[ix][iy] != 2)
					sum++;
		return sum;
	}

	private void logMap(int[][] map) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				char app;
				switch (map[x][y]) {
					case 0 -> app = '*';
					case 1 -> app = '#';
					case 2 -> app = '.';
					default -> app = 'X';
				}
				sb.append(app);
			}
			sb.append("\n");
		}
		log(sb.append("\n"));
	}

	private record Edge(int x, int y, int dx, int dy) {
		Edge(Point p1, Point p2) {
			this(Math.min(p1.x(), p2.x()), Math.min(p1.y(), p2.y()),
					Math.abs(p1.x() - p2.x()), Math.abs(p1.y() - p2.y()));
		}
	}
}
