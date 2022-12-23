package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Day23 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		List<Elf> elves = parse(in);
		for (int i = 0; i < 10; i++) {
			if (move(elves, i) == 0)
				break;
		}
		return countGrid(elves);
	}

	@Override
	public Integer task2(String[] in) {
		List<Elf> elves = parse(in);
		int i = 0;
		while (move(elves, i++) > 0) {
			if (i % 100 == 0) {
				log("round", i);
				visualize(elves, new HashMap<>());
			}
		}
		return i;
	}

	private int move(List<Elf> elves, int round) {
		Map<Position, Integer> proposed = new HashMap<>();
		for (Elf elf : elves) {
			elf.next = elf.current;
			if (!elves.contains(new Elf(elf.current.x + 1, elf.current.y))
					&& !elves.contains(new Elf(elf.current.x + 1, elf.current.y + 1))
					&& !elves.contains(new Elf(elf.current.x, elf.current.y + 1))
					&& !elves.contains(new Elf(elf.current.x - 1, elf.current.y + 1))
					&& !elves.contains(new Elf(elf.current.x - 1, elf.current.y))
					&& !elves.contains(new Elf(elf.current.x - 1, elf.current.y - 1))
					&& !elves.contains(new Elf(elf.current.x, elf.current.y - 1))
					&& !elves.contains(new Elf(elf.current.x + 1, elf.current.y - 1))
			)
				continue;
			movements:
			for (int direction = round % 4; direction < round % 4 + 4; direction++) {
				switch (direction % 4) {
					case 0: // N
						if (!elves.contains(new Elf(elf.current.x - 1, elf.current.y - 1))
								&& !elves.contains(new Elf(elf.current.x, elf.current.y - 1))
								&& !elves.contains(new Elf(elf.current.x + 1, elf.current.y - 1))) {
							elf.next = new Position(elf.current.x, elf.current.y - 1);
							break movements;
						}
						break;
					case 1: // S
						if (!elves.contains(new Elf(elf.current.x - 1, elf.current.y + 1))
								&& !elves.contains(new Elf(elf.current.x, elf.current.y + 1))
								&& !elves.contains(new Elf(elf.current.x + 1, elf.current.y + 1))) {
							elf.next = new Position(elf.current.x, elf.current.y + 1);
							break movements;
						}
						break;
					case 2: // W
						if (!elves.contains(new Elf(elf.current.x - 1, elf.current.y - 1))
								&& !elves.contains(new Elf(elf.current.x - 1, elf.current.y))
								&& !elves.contains(new Elf(elf.current.x - 1, elf.current.y + 1))) {
							elf.next = new Position(elf.current.x - 1, elf.current.y);
							break movements;
						}
						break;
					case 3: // E
						if (!elves.contains(new Elf(elf.current.x + 1, elf.current.y - 1))
								&& !elves.contains(new Elf(elf.current.x + 1, elf.current.y))
								&& !elves.contains(new Elf(elf.current.x + 1, elf.current.y + 1))) {
							elf.next = new Position(elf.current.x + 1, elf.current.y);
							break movements;
						}
				}
			}
			proposed.put(elf.next, proposed.getOrDefault(elf.next, 0) + 1);
		}
		//visualize(elves, proposed);
		int moved = 0;
		for (Elf elf : elves) {
			if (!elf.current.equals(elf.next) && proposed.get(elf.next) == 1) {
				moved++;
				elf.current = elf.next;
			}
		}
		return moved;
	}

	private int countGrid(List<Elf> elves) {
		int minx = Integer.MAX_VALUE;
		int miny = Integer.MAX_VALUE;
		int maxx = 0;
		int maxy = 0;
		for (Elf elf : elves) {
			minx = Math.min(minx, elf.current.x);
			miny = Math.min(miny, elf.current.y);
			maxx = Math.max(maxx, elf.current.x);
			maxy = Math.max(maxy, elf.current.y);
		}
		return (maxx - minx + 1) * (maxy - miny + 1) - elves.size();
	}

	private void visualize(List<Elf> elves, Map<Position, Integer> prop) {
		int minx = Integer.MAX_VALUE;
		int miny = Integer.MAX_VALUE;
		int maxx = 0;
		int maxy = 0;
		for (Elf elf : elves) {
			minx = Math.min(minx, elf.current.x);
			miny = Math.min(miny, elf.current.y);
			maxx = Math.max(maxx, elf.current.x);
			maxy = Math.max(maxy, elf.current.y);
		}
		char[][] grid = new char[maxy - miny + 3][maxx - minx + 3];
		for (Elf elf : elves) {
			grid[elf.next.y - miny + 1][elf.next.x - minx + 1] = '*';
			grid[elf.current.y - miny + 1][elf.current.x - minx + 1] = '#';
		}

		for (Position pos : prop.keySet()) {
			if (prop.get(pos) != null && prop.get(pos) > 1)
				grid[pos.y - miny + 1][pos.x - minx + 1] = '!';
		}

		StringBuilder output = new StringBuilder().append("y=").append(minx).append(", y=").append(miny).append("\n");
		for (char[] line : grid) {
			for (char c : line)
				output.append(c == 0 ? '·' : c);
			output.append("\n");
		}
		log(output);
	}

	private List<Elf> parse(String[] in) {
		List<Elf> result = new ArrayList<>();
		for (int y = 0; y < in.length; y++)
			for (int x = 0; x < in[0].length(); x++)
				if (in[y].charAt(x) == '#')
					result.add(new Elf(x, y));
		return result;
	}

	private class Elf {
		Position current, next;

		public Elf(int x, int y) {
			this.current = new Position(x, y);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Elf))
				return false;
			return current.equals(((Elf) o).current);
		}

		@Override
		public int hashCode() {
			return current.hashCode();
		}

		@Override
		public String toString() {
			return current.toString();
		}

	}

	private class Position {
		final int x, y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Position))
				return false;
			return x == ((Position) o).x && y == ((Position) o).y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ')';
		}
	}
}
