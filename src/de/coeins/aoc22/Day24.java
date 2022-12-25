package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Day24 implements Day<Integer> {

	private static final Map<Character, Point> MOVEMENTS = new HashMap<>(4);

	static {
		MOVEMENTS.put('<', new Point(-1, 0));
		MOVEMENTS.put('>', new Point(1, 0));
		MOVEMENTS.put('^', new Point(0, -1));
		MOVEMENTS.put('v', new Point(0, 1));
	}

	@Override
	public Integer task1(String[] in) {
		List<Wind> winds = new ArrayList<>();
		Point max = parse(in, winds);
		List<Boolean[][]> maps = new ArrayList<>();
		return findPath(winds, maps, max, new Point(0, -1), new Point(max.x - 1, max.y - 1), 0) + 1;
	}

	@Override
	public Integer task2(String[] in) {
		List<Wind> winds = new ArrayList<>();
		Point max = parse(in, winds);
		List<Boolean[][]> maps = new ArrayList<>();
		int t = findPath(winds, maps, max, new Point(0, -1), new Point(max.x - 1, max.y - 1), 0) + 1;
		log("First round finished after", t);
		t = findPath(winds, maps, max, new Point(max.x - 1, max.y), new Point(0, 0), t) + 1;
		log("Second round finished after", t);
		t = findPath(winds, maps, max, new Point(0, -1), new Point(max.x - 1, max.y - 1), t) + 1;
		log("Third round finished after", t);
		return t;
	}

	private int findPath(List<Wind> winds, List<Boolean[][]> maps, Point max, Point start, Point target, int time) {
		List<Point> previousStates = List.of(start);
		for (int t = time + 1; t < time + 10000; t++) {
			generateMaps(winds, maps, max, t);
			List<Point> states = new ArrayList<>();
			states.add(start);
			Boolean[][] map = maps.get(t);
			for (Point state : previousStates) {
				checkAndAdd(map, max, states, state);
				checkAndAdd(map, max, states, new Point(state.x + 1, state.y));
				checkAndAdd(map, max, states, new Point(state.x, state.y + 1));
				checkAndAdd(map, max, states, new Point(state.x - 1, state.y));
				checkAndAdd(map, max, states, new Point(state.x, state.y - 1));
			}
			// log("time", t, "states", states.size());
			// logMap(map, max, states);
			previousStates = states;
			if (states.contains(target))
				return t;
		}
		throw new RuntimeException("No Path found");
	}

	private void checkAndAdd(Boolean[][] map, Point max, List<Point> states, Point newstate) {
		if (states.contains(newstate))
			return;
		if (newstate.x < 0 || newstate.y < 0 || newstate.x >= max.x || newstate.y >= max.y)
			return;
		if (map[newstate.x][newstate.y])
			return;
		states.add(newstate);
	}

	private void generateMaps(List<Wind> winds, List<Boolean[][]> maps, Point max, int targetTime) {
		for (int time = maps.size(); time <= targetTime; time++) {
			if (time > 0) {
				for (Wind w : winds)
					w.step();
			}
			Boolean[][] map = new Boolean[max.x][max.y];
			for (int y = 0; y < max.y; y++)
				for (int x = 0; x < max.x; x++)
					map[x][y] = winds.contains(new Wind(new Point(x, y), '>', max));
			maps.add(map);
		}
	}

	private void logMap(Boolean[][] map, Point max, List<Point> positions) {
		StringBuilder out = new StringBuilder();
		for (int y = 0; y < max.y; y++) {
			for (int x = 0; x < max.x; x++)
				if (positions != null && positions.contains(new Point(x, y)))
					out.append(map[x][y] ? "!" : "o");
				else
					out.append(map[x][y] ? "X" : "·");
			out.append("\n");
		}
		log(out);
	}

	private Point parse(String[] in, List<Wind> winds) {
		Point max = new Point(in[0].length() - 2, in.length - 2);
		for (int y = 0; y < max.y; y++)
			for (int x = 0; x < max.x; x++)
				if (in[y + 1].charAt(x + 1) != '.')
					winds.add(new Wind(new Point(x, y), in[y + 1].charAt(x + 1), max));
		return max;
	}

	private static class Point {
		final int x, y;

		private Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Point))
				return false;
			return x == ((Point) o).x && y == ((Point) o).y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	private class Wind {
		Point pos;
		final char arrow;
		final Point direction;
		final Point max;

		public Wind(Point pos, char arrow, Point max) {
			this.pos = pos;
			this.arrow = arrow;
			this.direction = MOVEMENTS.get(arrow);
			this.max = max;
		}

		void step() {
			pos = new Point((pos.x + max.x + direction.x) % max.x, (pos.y + max.y + direction.y) % max.y);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Wind))
				return false;
			return pos.equals(((Wind) o).pos);
		}

		@Override
		public int hashCode() {
			return pos.hashCode();
		}
	}
}
