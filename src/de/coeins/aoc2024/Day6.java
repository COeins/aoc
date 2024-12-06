package de.coeins.aoc2024;

import java.util.List;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.MapElement;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day6 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Lab> map = Layered2DMap.parseCharacters(in, 0, Lab.class);
		List<Point> start = map.findInBase(Lab.START);
		if (start.size() != 1)
			throw new RuntimeException("No start found");
		walkMap(map, start.get(0));
		log(map);
		return map.sumLayer();
	}

	private void walkMap(Layered2DMap<Lab> map, Point start) {
		Point pos = start;
		Direction dir = Direction.N;
		while (true) {
			Lab next = map.getBase(pos.applyDirection(dir), Lab.OUTSIDE);
			if (next == Lab.OUTSIDE) {
				map.setLayer(pos, 1);
				break;
			} else if (next == Lab.WALL) {
				dir = dir.rotate90();
			} else {
				map.setLayer(pos, 1);
				pos = pos.applyDirection(dir);
			}
		}
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Lab> map = Layered2DMap.parseCharacters(in, 0, Lab.class);
		List<Point> start = map.findInBase(Lab.START);
		if (start.size() != 1)
			throw new RuntimeException("No start found");
		walkMap(map, start.get(0));

		return map.iterateMap((blockedPos, base, lay, prev) -> {
			if (lay[0] < 1)
				return prev;
			Point pos = start.get(0);
			Direction dir = Direction.N;
			map.resetLayer(1);
			while (true) {
				Point nextPos = pos.applyDirection(dir);
				Lab next = map.getBase(nextPos, Lab.OUTSIDE);
				if (next == Lab.OUTSIDE) {
					return prev;
				} else if (map.getLayer(1, pos) == dir.ordinal() + 1) {
					return prev + 1;
				} else if (nextPos.equals(blockedPos) || next == Lab.WALL)
					dir = dir.rotate90();
				else {
					map.setLayer(1, pos, dir.ordinal() + 1);
					pos = pos.applyDirection(dir);
				}
			}
		}, 0);
	}

	enum Lab implements MapElement {
		EMPTY('.'), WALL('#'), START('^'), OUTSIDE('X');

		private char c;

		Lab(char c) {
			this.c = c;
		}

		@Override
		public char getParseChar() {
			return c;
		}

		@Override
		public char getOutputChar() {
			return c;
		}
	}
}
