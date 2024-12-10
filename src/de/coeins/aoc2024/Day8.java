package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.AlphaNum;
import de.coeins.aoc2023.Layered2DMap.MapElement;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day8 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Layered2DMap.AlphaNum> map = Layered2DMap.parseAlphaNum(in, 0);
		Map<MapElement, List<Point>> antennas = findAntennas(map);

		for (MapElement ant : antennas.keySet()) {
			List<Point> points = antennas.get(ant);
			for (Point a : points) {
				for (Point b : points) {
					if (a.equals(b))
						continue;
					int dx = a.x() - b.x();
					int dy = a.y() - b.y();
					Point anti = new Point(b.x() + 2 * dx, b.y() + 2 * dy);
					if (map.validPoint(anti))
						map.setLayer(anti, 1);
				}
			}
		}
		log(map);
		return map.sumLayer();
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Layered2DMap.AlphaNum> map = Layered2DMap.parseAlphaNum(in, 0);
		Map<MapElement, List<Point>> antennas = findAntennas(map);

		for (MapElement ant : antennas.keySet()) {
			List<Point> points = antennas.get(ant);
			for (Point a : points) {
				for (Point b : points) {
					if (a.equals(b))
						continue;
					int dx = a.x() - b.x();
					int dy = a.y() - b.y();
					for (int distance = 1; ; distance++) {
						Point anti = new Point(b.x() + distance * dx, b.y() + distance * dy);
						if (!map.validPoint(anti))
							break;
						map.setLayer(anti, 1);
					}
				}
			}
		}
		log(map);
		return map.sumLayer();
	}

	private Map<MapElement, List<Point>> findAntennas(Layered2DMap<AlphaNum> map) {
		Map<MapElement, List<Point>> antennas = new HashMap<>();
		map.iterateMap((p, b, _l, _i) -> {
			if (b != AlphaNum.EMPTY) {
				if (!antennas.containsKey(b))
					antennas.put(b, new ArrayList<>());
				antennas.get(b).add(p);
			}
			return null;
		}, null);
		return antennas;
	}
}
