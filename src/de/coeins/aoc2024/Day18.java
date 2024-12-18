package de.coeins.aoc2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.MapElement;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day18 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int size = in.length > 25 ? 71 : 7;
		int count = in.length > 25 ? 1024 : 12;
		Layered2DMap<MapElement> map = new Layered2DMap<>(new MapElement[size][size]);
		Point end = new Point(size - 1, size - 1);
		for (int i = 0; i < count; i++) {
			Integer[] s = Arrays.stream(in[i].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
			map.setLayer(new Point(s[1], s[0]), 1);
		}

		calculateDistances(map);
		drawPath(map, end);
		log(map.toString((pos, base, layers) -> layers[0] > 0 ? '▒' : layers[2] > 0 ? '·' : ' '));
		return map.getLayer(1, end);
	}

	@Override
	public Integer task2(String[] in) {
		int size = in.length > 25 ? 71 : 7;
		int count = in.length > 25 ? 1024 : 12;
		Layered2DMap<MapElement> map = new Layered2DMap<>(new MapElement[size][size]);
		Point end = new Point(size - 1, size - 1);
		int lastSteps = -1;
		for (int block = 0; block < in.length; block++) {
			Integer[] s = Arrays.stream(in[block].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
			map.setLayer(new Point(s[1], s[0]), 1);
			if (block < count)
				continue;
			map.resetLayer(1);
			calculateDistances(map);
			int steps = map.getLayer(1, end);
			if (steps != lastSteps) {
				log("After", block, "block, distance is", steps);
				drawPath(map, end);
				log(map.toString((pos, base, layers) -> layers[0] > 0 ? '▒' : layers[2] > 0 ? '·' : ' '));

				if (steps == 0) {
					log(in[block]);
					return block;
				}
				lastSteps = steps;
			}
		}
		return -1;
	}

	void calculateDistances(Layered2DMap<MapElement> map) {
		Set<Point> considering = new HashSet<>();
		considering.add(new Point(0, 0));
		while (!considering.isEmpty()) {
			Point pos = considering.iterator().next();
			considering.remove(pos);
			int steps = map.getLayer(1, pos);
			for (Direction d : Layered2DMap.CARDINALS) {
				Point next = pos.applyDirection(d);
				if (map.getLayer(0, next, 1) > 0)
					continue;
				int nextSteps = map.getLayer(1, next);
				if (nextSteps > 0 && nextSteps <= steps + 1)
					continue;
				map.setLayer(1, next, steps + 1);
				considering.add(next);
			}
		}
	}

	void drawPath(Layered2DMap<MapElement> map, Point end) {
		map.resetLayer(2);
		Point pos = end;
		s:
		while (true) {
			map.setLayer(2, pos, 1);
			int steps = map.getLayer(1, pos);
			for (Direction d : Layered2DMap.CARDINALS) {
				Point next = pos.applyDirection(d);
				if (map.getLayer(0, next, 1) == 0 && map.getLayer(1, next) < steps) {
					pos = next;
					continue s;
				}
			}
			return;
		}
	}
}
