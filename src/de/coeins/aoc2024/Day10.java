package de.coeins.aoc2024;

import static de.coeins.aoc2023.Layered2DMap.CARDINALS;

import java.util.ArrayList;
import java.util.List;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.Digits;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day10 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Digits> map = Layered2DMap.parseDigits(in, 0);
		List<Point> starts = findStarts(map);
		int sum = 0;
		for (Point s : starts) {
			map.resetLayer();
			List<Point> next = new ArrayList<>();
			next.add(s);
			int score = 0;
			while (!next.isEmpty()) {
				Point p = next.remove(0);
				int curHeight = map.getBase(p).ordinal();
				if (curHeight == 9)
					score++;
				else
					for (Direction d : CARDINALS) {
						Point n = p.applyDirection(d);
						if (map.getBase(n, Digits.ZERO).ordinal() != curHeight + 1 || map.getLayer(n, 1) > 0)
							continue;
						map.setLayer(n, 1);
						next.add(n);
					}
			}
			log("Trail starting at", s, "has access to", score, "summits");
			sum += score;
		}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Digits> map = Layered2DMap.parseDigits(in, 0);
		List<Point> starts = findStarts(map);
		int sum = 0;
		for (Point s : starts) {
			map.resetLayer();
			map.setLayer(s, 1);
			List<Point> next = new ArrayList<>();
			next.add(s);
			int score = 0;
			while (!next.isEmpty()) {
				Point p = next.remove(0);
				int curHeight = map.getBase(p).ordinal();
				int curVisited = map.getLayer(p, 0);
				if (curHeight == 9) {
					log("Trail from", s, "to", p, "has", curVisited, "distinct paths");
					sum += curVisited;
				} else
					for (Direction d : CARDINALS) {
						Point n = p.applyDirection(d);
						int visited = map.getLayer(n, 0);
						if (map.getBase(n, Digits.ZERO).ordinal() != curHeight + 1)
							continue;
						map.setLayer(n, visited + curVisited);
						if (visited < 1)
							next.add(n);
					}
			}
		}
		return sum;
	}

	private List<Point> findStarts(Layered2DMap<Digits> map) {
		return map.iterateMap((pos, base, _l, list) -> {
			if (base == Digits.ZERO)
				list.add(pos);
			return list;
		}, new ArrayList<>());
	}
}
