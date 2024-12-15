package de.coeins.aoc2024;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.MapElement;
import de.coeins.aoc2023.Layered2DMap.Point;

public class Day15 implements de.coeins.aoc2023.Day<Integer> {
	private static final Map<Character, Direction> DIR = Map.of('^', Direction.N, 'v', Direction.S, '<', Direction.W, '>', Direction.E);

	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Boxes> map = Layered2DMap.parseCharacters(in, 0, Boxes.class);
		StringBuilder ib = new StringBuilder();
		for (int l = map.height() + 1; l < in.length; l++)
			ib.append(in[l]);
		String instructions = ib.toString();

		map.findInBase(Boxes.BOX).forEach(p -> map.setLayer(p, 1));
		Point start = map.findInBase(Boxes.START).get(0);
		Point current = start;
		log(map.toString((pos, base, layers) -> base == Boxes.WALL ? '▒' : layers[0] == 1 ? '◆' : pos.equals(start) ? '♟' : ' '));

		steps:
		for (int i = 0; i < instructions.length(); i++) {
			Direction step = DIR.get(instructions.charAt(i));
			Point next = current.applyDirection(step);
			if (map.getBase(next, Boxes.WALL) == Boxes.WALL)
				continue;
			else if (map.getLayer(next, 0) == 1) {
				Point pushInto = next;
				do {
					pushInto = pushInto.applyDirection(step);
					if (map.getBase(pushInto, Boxes.WALL) == Boxes.WALL)
						continue steps;
				} while (map.getLayer(pushInto, 0) == 1);
				map.setLayer(pushInto, 1);
				map.setLayer(next, 0);
			}
			current = next;
		}
		Point last = current;
		log(map.toString((pos, base, layers) -> base == Boxes.WALL ? '▒' : layers[0] == 1 ? '◆' : pos.equals(last) ? '♟' : ' '));
		return map.iterateMap((pos, _b, l, prev) -> prev + l[0] * (100 * pos.x() + pos.y()), 0);
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Boxes> slim = Layered2DMap.parseCharacters(in, 0, Boxes.class);
		Boxes[][] wideBase = new Boxes[slim.height()][slim.width() * 2];
		slim.iterateMap((pos, base, layers, previous) -> {
			if (base == Boxes.BOX) {
				wideBase[pos.x()][2 * pos.y()] = Boxes.BOX_L;
				wideBase[pos.x()][2 * pos.y() + 1] = Boxes.BOX_R;
			} else {
				wideBase[pos.x()][2 * pos.y()] = (Boxes) base;
				wideBase[pos.x()][2 * pos.y() + 1] = (Boxes) base;
			}
			return null;
		}, null);
		Layered2DMap<Boxes> map = new Layered2DMap<>(wideBase);

		StringBuilder ib = new StringBuilder();
		for (int l = map.height() + 1; l < in.length; l++)
			ib.append(in[l]);
		String instructions = ib.toString();

		Point start = map.findInBase(Boxes.START).get(0);
		Point current = start;
		map.findInBase(Boxes.BOX_L).forEach(p -> map.setLayer(p, 2));
		map.findInBase(Boxes.BOX_R).forEach(p -> map.setLayer(p, 1));
		log(map.toString((pos, base, layers) -> base == Boxes.WALL ? '▒' : layers[0] == 1 ? '▶' : layers[0] == 2 ? '◀' :
				pos.equals(start) ? '♟' : ' '));

		steps:
		for (int i = 0; i < instructions.length(); i++) {
			Direction step = DIR.get(instructions.charAt(i));
			Point next = current.applyDirection(step);
			if (map.getBase(next, Boxes.WALL) == Boxes.WALL)
				continue;
			else if (map.getLayer(next, 0) > 0) {
				if (step.dy != 0) { // move horizontal
					Point pushInto = next;
					do {
						pushInto = pushInto.applyDirection(step);
						if (map.getBase(pushInto, Boxes.WALL) == Boxes.WALL)
							continue steps;
					} while (map.getLayer(pushInto, 0) > 0);
					for (int y = pushInto.y(); y != next.y(); y -= step.dy)
						map.setLayer(new Point(next.x(), y), map.getLayer(new Point(next.x(), y - step.dy)));
					map.setLayer(next, 0);

				} else { // move vertical
					int pushIntoX = next.x();
					Map<Integer, Set<Integer>> pushedBlocks = new HashMap<>();

					Set<Integer> currentPushY = Set.of(next.y());
					do {
						Set<Integer> nextPushY = new HashSet<>();
						for (int y : currentPushY) {
							Point pushInto = new Point(pushIntoX, y);
							if (map.getBase(pushInto, Boxes.WALL) == Boxes.WALL)
								continue steps;
							else if (map.getLayer(pushInto, 0) == 1) {
								nextPushY.add(y - 1);
								nextPushY.add(y);
							} else if (map.getLayer(pushInto, 0) == 2) {
								nextPushY.add(y);
								nextPushY.add(y + 1);
							}
						}
						pushedBlocks.put(pushIntoX, nextPushY);
						currentPushY = nextPushY;
						pushIntoX += step.dx;
					} while (!currentPushY.isEmpty());

					for (int x = pushIntoX - step.dx; x != next.x(); x -= step.dx) {
						Set<Integer> blocksY = pushedBlocks.get(x - step.dx);
						for (int y : blocksY) {
							map.setLayer(new Point(x, y), map.getLayer(new Point(x - step.dx, y)));
							map.setLayer(new Point(x - step.dx, y), 0);
						}
					}
				}
			}
			current = next;
		}
		Point last = current;
		log(map.toString((pos, base, layers) -> base == Boxes.WALL ? '▒' : layers[0] == 1 ? '▶' : layers[0] == 2 ? '◀' :
				pos.equals(last) ? '♟' : ' '));
		return map.iterateMap((pos, _b, l, prev) -> prev + (l[0] / 2) * (100 * pos.x() + pos.y()), 0);
	}

	enum Boxes implements MapElement {
		EMPTY('.'), WALL('#'), START('@'), BOX('O'), BOX_L('['), BOX_R(']');

		private char c;

		Boxes(char c) {
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
