package de.coeins.aoc2023;

import de.coeins.aoc2023.Layered2DMap.*;

class Day10 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Pipe> map = Layered2DMap.parseCharacters(in, 0, Pipe.class);
		Point start = map.findInBase(Pipe.START).get(0);
		Point nextPos = null;
		Direction incoming = null;

		for (Direction d : Direction.values()) {
			Point newPos = start.applyDirection(d);
			Pipe pipe = map.getBase(newPos, Pipe.NONE);
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
			Pipe newPipe = map.getBase(nextPos);
			map.setLayer(nextPos, 1);
			if (newPipe == Pipe.START) {
				log(map);
				return step / 2;
			}
			Direction newDirection = newPipe.d1 == incoming.inverse ? newPipe.d2 : newPipe.d1;
			nextPos = nextPos.applyDirection(newDirection);
			incoming = newDirection;
			step++;
		}
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Pipe> map = Layered2DMap.parseCharacters(in, 0, Pipe.class);
		Point start = map.findInBase(Pipe.START).get(0);
		Point firstPos = null;
		Direction firstIncoming = null;
		for (Direction d : Direction.values()) {
			Point newPos = start.applyDirection(d);
			Pipe pipe = map.getBase(newPos, Pipe.NONE);
			if (pipe.d1 == d.inverse || pipe.d2 == d.inverse) {
				firstPos = newPos;
				firstIncoming = d;
				break;
			}
		}
		if (firstPos == null)
			throw new RuntimeException("Nothing connected to start");

		// First draw boundary
		Point nextPos = firstPos;
		Direction incomming = firstIncoming;
		while (true) {
			map.setLayer(nextPos, 1);
			Pipe newPipe = map.getBase(nextPos);
			if (newPipe == Pipe.START)
				break;
			Direction newDirection = newPipe.d1 == incomming.inverse ? newPipe.d2 : newPipe.d1;
			nextPos = nextPos.applyDirection(newDirection);
			incomming = newDirection;
		}

		// Now fill areas
		nextPos = firstPos;
		incomming = firstIncoming;
		while (true) {
			Pipe newPipe = map.getBase(nextPos);
			if (newPipe == Pipe.START)
				break;
			Direction newDirection = newPipe.d1 == incomming.inverse ? newPipe.d2 : newPipe.d1;
			int reverse = newPipe.d1 != incomming.inverse ? 1 : 0;
			final Point fillPos = nextPos;
			for (Direction d : newPipe.area1) {
				if (map.getLayer(nextPos.applyDirection(d), -1) == 0)
					map.fillLayer(nextPos.applyDirection(d), 2 + reverse);
			}
			for (Direction d : newPipe.area2)
				if (map.getLayer(nextPos.applyDirection(d), -1) == 0)
					map.fillLayer(nextPos.applyDirection(d), 3 - reverse);
			nextPos = nextPos.applyDirection(newDirection);
			incomming = newDirection;
		}

		// Finally, count inside area
		int inside = map.getLayer(new Point(0, map.width() - 1)) == 2 ? 3 : 2;

		log(map.toString((_p, b, l) -> l < 2 ? b.getOutputChar() : Integer.toString(l).charAt(0)));
		return map.iterateMap((_p, _b, layer, prev) -> layer == inside ? prev + 1 : prev);
	}

	enum Pipe implements Layered2DMap.MapElement {
		NONE('.', '░', null, null, new Layered2DMap.Direction[0], new Direction[0]),
		START('S', 'S', null, null, new Direction[0], new Direction[0]),
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

		@Override
		public char getParseChar() {
			return mapChar;
		}

		@Override
		public char getOutputChar() {
			return niceChar;
		}
	}
}
