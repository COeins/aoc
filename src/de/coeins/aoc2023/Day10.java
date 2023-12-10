package de.coeins.aoc2023;

class Day10 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Pos start = findPos(in, 'S');
		Pos nextPos = null;
		Direction incoming = null;
		for (Direction d : Direction.values()) {
			Pos newPos = start.applyDirection(d);
			Pipe pipe = pipeAt(charAt(in, newPos));
			if (pipe.d1 == d.inverse || pipe.d2 == d.inverse) {
				nextPos = newPos;
				incoming = d;
				break;
			}
		}
		if (nextPos == null)
			throw new RuntimeException("Nothing connected to start");

		int step = 1;
		while (true) {
			char newChar = charAt(in, nextPos);
			log(step, nextPos, newChar);
			if (newChar == 'S')
				return step / 2;

			Pipe newPipe = pipeAt(newChar);
			Direction newDirection = newPipe.d1 == incoming.inverse ? newPipe.d2 : newPipe.d1;
			nextPos = nextPos.applyDirection(newDirection);
			incoming = newDirection;
			step++;
		}
	}

	@Override
	public Integer task2(String[] in) {
		Pos start = findPos(in, 'S');
		Pos firstPos = null;
		Direction firstIncoming = null;
		for (Direction d : Direction.values()) {
			Pos newPos = start.applyDirection(d);
			Pipe pipe = pipeAt(charAt(in, newPos));
			if (pipe.d1 == d.inverse || pipe.d2 == d.inverse) {
				firstPos = newPos;
				firstIncoming = d;
				break;
			}
		}
		if (firstPos == null)
			throw new RuntimeException("Nothing connected to start");

		int[][] map = new int[in.length][in[0].length() + 1];

		// First draw boundary
		Pos nextPos = firstPos;
		Direction incomming = firstIncoming;
		while (true) {
			map[nextPos.x][nextPos.y] = 1;
			char newChar = charAt(in, nextPos);
			if (newChar == 'S')
				break;
			Pipe newPipe = pipeAt(newChar);
			Direction newDirection = newPipe.d1 == incomming.inverse ? newPipe.d2 : newPipe.d1;
			nextPos = nextPos.applyDirection(newDirection);
			incomming = newDirection;
		}

		// Now fill areas
		nextPos = firstPos;
		incomming = firstIncoming;
		while (true) {
			char newChar = charAt(in, nextPos);
			if (newChar == 'S')
				break;
			Pipe newPipe = pipeAt(newChar);
			Direction newDirection = newPipe.d1 == incomming.inverse ? newPipe.d2 : newPipe.d1;
			int reverse = newPipe.d1 != incomming.inverse ? 1 : 0;
			for (Direction d : newPipe.area1)
				floodFill(map, nextPos.applyDirection(d), 2 + reverse);
			for (Direction d : newPipe.area2)
				floodFill(map, nextPos.applyDirection(d), 3 - reverse);
			nextPos = nextPos.applyDirection(newDirection);
			incomming = newDirection;
		}

		// Finally, count inside area
		int inside = map[0][map[0].length - 1] == 2 ? 3 : 2;
		logMap(in, map, inside);
		int count = 0;
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++)
				if (map[i][j] == inside)
					count++;
		return count;
	}

	Pos findPos(String[] map, char find) {
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length(); j++)
				if (map[i].charAt(j) == find)
					return new Pos(i, j);
		throw new RuntimeException("Not Found");
	}

	Pipe pipeAt(char c) {
		for (Pipe p : Pipe.values())
			if (p.mapChar == c)
				return p;
		return Pipe.XX;
	}

	char charAt(String[] map, Pos p) {
		if (p.x < 0 || p.x >= map.length || p.y < 0 || p.y >= map[p.x].length())
			return 0;
		else
			return map[p.x].charAt(p.y);
	}

	void floodFill(int[][] map, Pos point, int fill) {
		if (point.x < 0 || point.x >= map.length || point.y < 0 || point.y >= map[0].length)
			return;
		if (map[point.x][point.y] > 1 && map[point.x][point.y] != fill)
			throw new RuntimeException("Invalid fill at " + point + ", " + fill);
		if (map[point.x][point.y] != 0)
			return;
		map[point.x][point.y] = fill;
		for (Direction d : Direction.values())
			floodFill(map, point.applyDirection(d), fill);
	}

	private void logMap(String[] in, int[][] map, int inside) {
		for (int i = 0; i < map.length; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < map[i].length; j++)
				if (map[i][j] == 1)
					s.append(pipeAt(charAt(in, new Pos(i, j))).niceChar);
				else if (map[i][j] == inside)
					s.append('I');
				else
					s.append(Pipe.XX.niceChar);
			log(s);
		}
		log();
	}

	record Pos(int x, int y) {
		Pos applyDirection(Direction d) {
			return new Pos(x + d.dx, y + d.dy);
		}
	}

	enum Direction {
		N(-1, 0, null),
		S(1, 0, N),
		E(0, 1, null),
		W(0, -1, E),

		NE(-1, 1, null),
		SW(1, -1, NE),
		SE(1, 1, null),
		NW(-1, -1, SE);

		final int dx;
		final int dy;
		Direction inverse;

		Direction(int dx, int dy, Direction inv) {
			this.dx = dx;
			this.dy = dy;
			this.inverse = inv;
			if (inv != null)
				inv.inverse = this;
		}
	}

	enum Pipe {
		XX('.', '░', null, null, new Direction[0], new Direction[0]),
		XS('S', 'S', null, null, new Direction[0], new Direction[0]),
		EW('-', '─', Direction.E, Direction.W, new Direction[] { Direction.N }, new Direction[] { Direction.S }),
		NS('|', '│', Direction.N, Direction.S, new Direction[] { Direction.W }, new Direction[] { Direction.E }),
		NE('L', '└', Direction.N, Direction.E, new Direction[] { Direction.W, Direction.SW, Direction.S }, new Direction[0]),
		NW('J', '┘', Direction.N, Direction.W, new Direction[0], new Direction[] { Direction.E, Direction.SE, Direction.S }),
		SW('7', '┐', Direction.S, Direction.W, new Direction[] { Direction.E, Direction.NE, Direction.N }, new Direction[0]),
		SE('F', '┌', Direction.S, Direction.E, new Direction[0], new Direction[] { Direction.W, Direction.NW, Direction.N });

		final char mapChar;
		final char niceChar;
		// direction coming in from in "forward direction"
		final Direction d1;
		// direction coming in from in "reverse direction"
		final Direction d2;
		// area to the right in "forward direction"
		final Direction[] area1;
		// area to the left in "forward direction"
		final Direction[] area2;

		Pipe(char mapChar, char niceChar, Direction d1, Direction d2, Direction[] area1, Direction[] area2) {
			this.mapChar = mapChar;
			this.niceChar = niceChar;
			this.d1 = d1;
			this.d2 = d2;
			this.area1 = area1;
			this.area2 = area2;
		}
	}
}
