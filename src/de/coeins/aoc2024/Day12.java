package de.coeins.aoc2024;

import static de.coeins.aoc2023.Layered2DMap.CARDINALS;

import java.util.HashMap;
import java.util.Map;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.AlphaNum;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day12 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<AlphaNum> map = Layered2DMap.parseAlphaNum(in, 0);
		int regions = countUniqueRegions(map);
		int[] area = new int[regions + 1];
		int[] border = new int[regions + 1];
		map.iterateMap((pos, base, layers, previous) -> {
			area[layers[0]]++;
			for (Direction d : CARDINALS)
				if (map.getLayer(pos.applyDirection(d), 0) != layers[0])
					border[layers[0]]++;
			return null;
		}, null);
		return calculateRegionValues(border, area);
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<AlphaNum> map = Layered2DMap.parseAlphaNum(in, 0);
		int regions = countUniqueRegions(map);
		int[] area = new int[regions + 1]; // region "0" == border
		int[] border = new int[regions + 1];
		int[] corners = new int[4];
		Map<Integer, Integer> counter = new HashMap<>();
		for (int x = -1; x < map.width(); x++)
			for (int y = -1; y < map.height(); y++) {
				corners[0] = map.getLayer(new Point(x, y), 0);
				corners[1] = map.getLayer(new Point(x + 1, y), 0);
				corners[2] = map.getLayer(new Point(x, y + 1), 0);
				corners[3] = map.getLayer(new Point(x + 1, y + 1), 0);
				area[corners[3]]++;
				for (int i : corners)
					counter.merge(i, 1, Integer::sum);
				for (Map.Entry<Integer, Integer> e : counter.entrySet())
					if (e.getValue() == 1 || e.getValue() == 3) // corner piece
						border[e.getKey()]++;
					else if (e.getValue() == 2 && (corners[0] == corners[3] || corners[1] == corners[2])) // diagonal pieces
						border[e.getKey()] += 2;
				counter.clear();
			}
		return calculateRegionValues(border, area);
	}

	private int countUniqueRegions(Layered2DMap<AlphaNum> map) {
		map.resetLayer();
		return map.iterateMap((pos, base, layers, previous) -> {
			if (layers[0] > 0)
				return previous;
			map.fillLayer(pos, previous + 1, (pos1, base1, layers1) -> layers1[0] == 0 && base1 == base);
			return previous + 1;
		}, 0);
	}

	private int calculateRegionValues(int[] borders, int[] area) {
		int sum = 0;
		for (int i = 1; i < borders.length; i++) {
			log("region", i, ", area:", area[i], "* perimeter:", borders[i]);
			sum += borders[i] * area[i];
		}
		return sum;
	}
}
