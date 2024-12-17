package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.coeins.aoc2023.Layered2DMap;
import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day16 implements de.coeins.aoc2023.Day<Integer> {
	int bestCost;
	Set<Point> bestPath;

	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Maze> map = Layered2DMap.parseCharacters(in, 0, Maze.class);
		bestCost = Integer.MAX_VALUE;
		int bestCost = move(map, new GameState(map.findInBase(Maze.Start).get(0), Direction.E, 0, new ArrayList<>()), true);
		log(map.toString((pos, base, layers) -> base != Maze.Empty ? base.getOutputChar() : bestPath.contains(pos) ? '·' : ' '));
		return bestCost;
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Maze> map = Layered2DMap.parseCharacters(in, 0, Maze.class);
		bestCost = Integer.MAX_VALUE;
		move(map, new GameState(map.findInBase(Maze.Start).get(0), Direction.E, 0, new ArrayList<>()), false);
		log(map.toString((pos, base, layers) -> base != Maze.Empty ? base.getOutputChar() : bestPath.contains(pos) ? '·' : ' '));
		return bestPath.size() + 1;
	}

	private int move(Layered2DMap<Maze> map, GameState state, boolean skipSimilar) {
		int prevCost = map.getLayer(state.dir.ordinal(), state.pos);
		if (state.cost > bestCost || prevCost > 0 && (state.cost > prevCost || (skipSimilar && state.cost == prevCost)))
			return Integer.MAX_VALUE;

		map.setLayer(state.dir.ordinal(), state.pos, state.cost());

		if (map.getBase(state.pos) == Maze.End) {
			if (bestCost > state.cost) {
				log("Found a new best path, costing", state.cost);
				bestCost = state.cost;
				bestPath = new HashSet<>();
			}

			bestPath.addAll(state.path);
			return state.cost;
		}

		List<Direction> possible = new ArrayList<>(3);
		for (int i = -1; i <= 1; i++)
			if (map.getBase(state.pos.applyDirection(state.dir.rotate90(i)), Maze.Wall) != Maze.Wall)
				possible.add(state.dir.rotate90(i));

		if (possible.isEmpty())
			return Integer.MAX_VALUE;

		state.path.add(state.pos);
		if (possible.size() == 1) {
			Direction newDir = possible.get(0);
			return move(map, new GameState(state.pos.applyDirection(newDir), newDir, cost(state, newDir), state.path), skipSimilar);
		} else {
			int minPrice = Integer.MAX_VALUE;
			for (Direction newDir : possible) {
				minPrice = Math.min(minPrice, move(map,
						new GameState(state.pos.applyDirection(newDir), newDir, cost(state, newDir), new ArrayList<>(state.path)),
						skipSimilar));
			}
			return minPrice;
		}
	}

	private int cost(GameState oldState, Direction newDir) {
		return oldState.cost + (oldState.dir == newDir ? 1 : 1001);
	}

	record GameState(Point pos, Direction dir, Integer cost, List<Point> path) {
	}

	enum Maze implements Layered2DMap.MapElement {
		Empty('.', ' '), Wall('#', '▒'), Start('S', 'S'), End('E', 'E');

		private char i;
		private char o;

		Maze(char i, char o) {
			this.i = i;
			this.o = o;
		}

		@Override
		public char getParseChar() {
			return i;
		}

		@Override
		public char getOutputChar() {
			return o;
		}
	}
}
