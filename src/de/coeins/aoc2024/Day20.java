package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.Maze;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day20 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Maze> map = Layered2DMap.parseCharacters(in, 0, Maze.class);
		Point start = map.findInBase(Maze.START).get(0);
		Point end = map.findInBase(Maze.End).get(0);
		int baseDistance = calculateDistances(map, start, end, 0) - (in.length > 15 ? 99 : 0);
		log("Regular distance:", baseDistance);
		Map<Integer, Integer> saves = map.iterateMap((pos, base, _l, sav) -> {
			if (base != Maze.WALL)
				return sav;
			map.copyLayer(0, 1);
			int min = Integer.MAX_VALUE;
			for (Direction d : Layered2DMap.CARDINALS) {
				Point p = pos.applyDirection(d);
				int s = map.getLayer(0, p, 0);
				if (s < min && (s > 0 || p.equals(start)))
					min = s;
			}
			map.setLayer(1, pos, min + 1);
			int newDistance = calculateDistances(map, pos, end, 1);
			if (newDistance < baseDistance)
				sav.put(baseDistance - newDistance, sav.getOrDefault(baseDistance - newDistance, 0) + 1);
			return sav;
		}, new HashMap<>());
		log(saves);
		return saves.values().stream().reduce(0, Integer::sum);
	}

	private int progress = 0;
	private int percent = 0;

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Maze> map = Layered2DMap.parseCharacters(in, 0, Maze.class);
		Point start = map.findInBase(Maze.START).get(0);
		Point end = map.findInBase(Maze.End).get(0);
		int baseDistance = calculateDistances(map, start, end, 0) - (in.length > 15 ? 99 : 49);
		log("Regular distance:", baseDistance);
		int max = map.height() * map.width();
		progress = 0;
		Map<Integer, Integer> saves = map.iterateMap((pos, base, _l, sav) -> {
			progress++;
			if (sav.values().size() > 100 && (100 * progress) / max > percent) {
				percent = (100 * progress) / max;
				log(percent, "% completed", sav.values().stream().reduce(0, Integer::sum), "shortcuts found");
			}

			if (base == Maze.WALL)
				return sav;
			List<Integer> newDistances = considerShortcuts(map, start, end, pos);
			for (int newDistance : newDistances)
				if (newDistance < baseDistance)
					sav.put(baseDistance - newDistance, sav.getOrDefault(baseDistance - newDistance, 0) + 1);
			return sav;
		}, new HashMap<>());
		log(saves);
		return saves.values().stream().reduce(0, Integer::sum);
	}

	int calculateDistances(Layered2DMap<Maze> map, Point start, Point end, int layer) {
		Set<Point> considering = new HashSet<>();
		considering.add(start);
		while (!considering.isEmpty()) {
			Point pos = considering.iterator().next();
			considering.remove(pos);
			int steps = map.getLayer(layer, pos);
			for (Direction d : Layered2DMap.CARDINALS) {
				Point next = pos.applyDirection(d);
				if (next.equals(start) || map.getBase(next, Maze.WALL) == Maze.WALL)
					continue;
				int nextSteps = map.getLayer(layer, next);
				if (nextSteps > 0 && nextSteps <= steps + 1)
					continue;
				map.setLayer(layer, next, steps + 1);
				considering.add(next);
			}
		}
		return map.getLayer(layer, end) > 0 ? map.getLayer(layer, end) : -1;
	}

	List<Integer> considerShortcuts(Layered2DMap<Maze> map, Point start, Point end, Point shortcurStart) {
		int steps = map.getLayer(0, shortcurStart);
		if (steps == 0 && !shortcurStart.equals(start))
			return List.of();

		List<Integer> shortcuts = new ArrayList<>();
		for (int dx = -20; dx <= 20; dx++) {
			int maxy = 20 - Math.abs(dx);
			for (int dy = -maxy; dy <= maxy; dy++) {
				Point shortcutEnd = new Point(shortcurStart.x() + dx, shortcurStart.y() + dy);
				if (map.getBase(shortcutEnd, Maze.WALL) == Maze.WALL)
					continue;
				int shortcutSteps = steps + Math.abs(dx) + Math.abs(dy);
				if (map.getLayer(0, shortcutEnd) > 0 && map.getLayer(0, shortcutEnd) <= shortcutSteps)
					continue;
				map.copyLayer(0, 1);
				map.setLayer(1, shortcutEnd, shortcutSteps);
				int totalSteps = calculateDistances(map, shortcutEnd, end, 1);
				shortcuts.add(totalSteps);
			}
		}
		return shortcuts;
	}

}
