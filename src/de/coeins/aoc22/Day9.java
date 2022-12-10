package de.coeins.aoc22;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Day9 implements Day<Integer> {
	private static final Map<String, Pos> MOVEMENTS = new HashMap<>(4);

	static {
		MOVEMENTS.put("U", new Pos(-1, 0));
		MOVEMENTS.put("L", new Pos(0, -1));
		MOVEMENTS.put("D", new Pos(1, 0));
		MOVEMENTS.put("R", new Pos(0, 1));
	}

	@Override
	public Integer task1(String[] in) {
		Pos h = new Pos(0, 0);
		Pos t = h;
		Set<Pos> visited = new HashSet<>();
		visited.add(t);
		for (String line : in) {
			String[] l = line.split(" ");
			Pos direction = MOVEMENTS.get(l[0]);
			for (int step = 0; step < Integer.parseInt(l[1]); step++) {
				h = h.step(direction);
				t = t.follow(h);
				visited.add(t);
			}
		}
		return visited.size();
	}

	@Override
	public Integer task2(String[] in) {
		int length = 10;
		Pos[] chain = new Pos[length];
		Arrays.fill(chain, new Pos(0, 0));
		Set<Pos> visited = new HashSet<>();
		visited.add(chain[length - 1]);
		for (String line : in) {
			String[] l = line.split(" ");
			Pos direction = MOVEMENTS.get(l[0]);
			for (int step = 0; step < Integer.parseInt(l[1]); step++) {
				chain[0] = chain[0].step(direction);
				for (int i = 1; i < length; i++)
					chain[i] = chain[i].follow(chain[i - 1]);
				visited.add(chain[length - 1]);
			}
		}
		return visited.size();
	}

	private static class Pos {
		final int x;
		final int y;

		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Pos step(Pos delta) {
			return new Pos(this.x + delta.x, this.y + delta.y);
		}

		public Pos follow(Pos h) {
			if (Math.abs(h.x - x) < 2 && Math.abs(h.y - y) < 2)
				return this;
			int newX = x, newY = y;
			if (h.x > this.x)
				newX++;
			if (h.x < this.x)
				newX--;
			if (h.y > this.y)
				newY++;
			if (h.y < this.y)
				newY--;
			return new Pos(newX, newY);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Pos))
				return false;
			return this.x == ((Pos) o).x && this.y == ((Pos) o).y;
		}

		@Override
		public int hashCode() {
			return x + 100 * y;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

	}
}
