package de.coeins.aoc2024;

import static de.coeins.aoc2023.Layered2DMap.DIAGONALS;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.MapElement;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day4 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<XMAS> grid = Layered2DMap.parseCharacters(in, 0, XMAS.class);
		return grid.iterateMap((pos, base, lay, prev) -> {
			int sum = 0;
			if (base == XMAS.X)
				for (Direction direction : Direction.values()) {
					if (searchMAS(grid, pos, direction)) {
						log("found XMAS at", pos, "in dir", direction);
						sum++;
					}
				}
			return prev + sum;
		}, 0);
	}

	private boolean searchMAS(Layered2DMap<XMAS> grid, Point pos, Direction direction) {
		return (grid.getBase(pos.applyDirection(direction, 1), XMAS.empty) == XMAS.M)
				&& (grid.getBase(pos.applyDirection(direction, 2), XMAS.empty) == XMAS.A)
				&& (grid.getBase(pos.applyDirection(direction, 3), XMAS.empty) == XMAS.S);
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<XMAS> grid = Layered2DMap.parseCharacters(in, 0, XMAS.class);
		return grid.iterateMap((pos, base, lay, prev) -> {
			if (base == XMAS.A)
				for (Direction direction : DIAGONALS) {
					if (searchX(grid, pos, direction)) {
						log("found X-MAS at", pos, "in dir", direction);
						return prev + 1;
					}
				}
			return prev;
		}, 0);
	}

	private boolean searchX(Layered2DMap<XMAS> grid, Point pos, Direction direction) {
		return (grid.getBase(pos.applyDirection(direction.rotate90(0)), XMAS.empty) == XMAS.M)
				&& (grid.getBase(pos.applyDirection(direction.rotate90(1)), XMAS.empty) == XMAS.M)
				&& (grid.getBase(pos.applyDirection(direction.rotate90(2)), XMAS.empty) == XMAS.S)
				&& (grid.getBase(pos.applyDirection(direction.rotate90(3)), XMAS.empty) == XMAS.S);
	}

	enum XMAS implements MapElement {
		X(), M(), A(), S(), empty();

		@Override
		public char getParseChar() {
			return this.name().charAt(0);
		}

		@Override
		public char getOutputChar() {
			return this.name().charAt(0);
		}
	}

}
