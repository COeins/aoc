package de.coeins.aoc2023;

import static de.coeins.aoc2023.Layered2DMap.CARDINALS;
import static de.coeins.aoc2023.Layered2DMap.Digits;
import static de.coeins.aoc2023.Layered2DMap.Direction;
import static de.coeins.aoc2023.Layered2DMap.Point;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Day17 implements Day<Integer> {

	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Digits> map = Layered2DMap.parseDigits(in, 0);

		Crucible c = new Crucible(map, 0, 3);
		c.searchpath(new Movement(new Point(0, 0), Direction.NW, 0));

		Point end = new Point(map.height() - 1, map.width() - 1);
		Layered2DMap<Digits> m2 = Layered2DMap.parseDigits(in, 0);
		int minEnergy = map.getLayer(end);
		for (Movement m : c.bestPath.keySet()) {
			if (m.pos.equals(end) && c.bestEnergy.get(m) <= minEnergy) {
				m2.resetLayer();
				c.bestPath.get(m).drawOnMap(m2);
				log(m2.toString((p, b, l) -> l[0] > 0 ? '·' : b.getOutputChar()));
			}
		}
		return minEnergy;
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Digits> map = Layered2DMap.parseDigits(in, 0);

		Crucible c = new Crucible(map, 4, 10);
		c.searchpath(new Movement(new Point(0, 0), Direction.NW, 0));

		Point end = new Point(map.height() - 1, map.width() - 1);
		Layered2DMap<Digits> m2 = Layered2DMap.parseDigits(in, 0);
		int minEnergy = map.getLayer(end);
		for (Movement m : c.bestPath.keySet()) {
			if (m.pos.equals(end) && c.bestEnergy.get(m) <= minEnergy) {
				m2.resetLayer();
				c.bestPath.get(m).drawOnMap(m2);
				log(m2.toString((p, b, l) -> l[0] > 0 ? '·' : b.getOutputChar()));
			}
		}
		return minEnergy;
	}

	static class Crucible {

		final Layered2DMap<Digits> map;
		List<Movement> tasks;
		Map<Movement, Integer> bestEnergy;
		Map<Movement, Path> bestPath = new HashMap<>();
		final int minMoves;
		final int maxMoves;

		Crucible(Layered2DMap<Digits> map, int minMoves, int maxMoves) {
			this.map = map;
			tasks = new LinkedList<>();
			bestEnergy = new HashMap<>();
			this.minMoves = minMoves - 1;
			this.maxMoves = maxMoves;
		}

		void searchpath(Movement start) {
			tasks.add(start);
			bestEnergy.put(start, 0);
			while (!tasks.isEmpty()) {
				Movement t = tasks.get(0);
				tasks.remove(0);
				step(t);
			}
		}

		void step(Movement move) {

			int energy = bestEnergy.get(move);
			Path path = bestPath.get(move);
			int prevEnergy = map.getLayer(move.pos);
			if (prevEnergy == 0 || prevEnergy > energy)
				map.setLayer(move.pos, energy);

			for (Direction d : CARDINALS) {
				Point nextPos = move.pos.applyDirection(d);
				if (!map.validPoint(nextPos))
					continue;
				if (d == move.dir.inverse)
					continue;
				if (move.steps < minMoves && d != move.dir && CARDINALS.contains(move.dir))
					continue;
				int newSteps = d == move.dir ? move.steps + 1 : 0;
				if (newSteps >= maxMoves)
					continue;

				int nextEnergy = energy + map.getBase(nextPos).getValue();
				Movement nextMove = new Movement(move.pos.applyDirection(d), d, newSteps);
				if (bestEnergy.containsKey(nextMove) && bestEnergy.get(nextMove) <= nextEnergy)
					continue;

				bestEnergy.put(nextMove, nextEnergy);
				bestPath.put(nextMove, new Path(nextMove, nextEnergy, path));
				tasks.add(nextMove);
			}
		}
	}

	record Movement(Point pos, Direction dir, int steps) {
	}

	record Path(Movement current, int energy, Path prev) {
		public void drawOnMap(Layered2DMap map) {
			map.setLayer(current.pos, 1);
			if (prev != null)
				prev.drawOnMap(map);
		}
	}

}
