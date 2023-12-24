package de.coeins.aoc2023;

import static de.coeins.aoc2023.Day23.ForestTile.*;
import static de.coeins.aoc2023.Layered2DMap.*;
import static de.coeins.aoc2023.Layered2DMap.Direction.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class Day23 implements Day<Integer> {

	private static int maxLengthSoFar;
	private static long totalPaths;

	@Override
	public Integer task1(String[] in) {
		Layered2DMap<ForestTile> map = Layered2DMap.parseCharacters(in, 0, ForestTile.class);
		Point start = map.findInBase(Path).get(0);
		if (start.x() != 0)
			throw new RuntimeException("Invalid start pos " + start);
		SimplePathfinder pf = new SimplePathfinder(map, true);
		List<Point> finalPath = new TaskList<>(pf).run(new ArrayList<>(List.of(start)));

		log(map.toString((pos, base, layers) -> finalPath.contains(pos) ? 'o' : base.getOutputChar()));
		return finalPath.size() - 1;
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<ForestTile> map = Layered2DMap.parseCharacters(in, 0, ForestTile.class);
		Point start = map.findInBase(Path).get(0);

		if (start.x() != 0)
			throw new RuntimeException("Invalid start pos " + start);

		Map<Point, Set<Path>> connections = new HashMap<>();
		connections.put(start, new HashSet<>());
		buildGraph(map, connections, start, S);
		log(connections.keySet().size(), "nodes");
		maxLengthSoFar = 0;
		totalPaths = 0;
		int maxLen = longestPath(map, connections, new ArrayList<>(0), start, 0);
		log("Found", totalPaths, "paths in total");
		return maxLen;
	}

	private void buildGraph(Layered2DMap<ForestTile> map, Map<Point, Set<Path>> connections, Point start, Direction dir) {
		Point current = start.applyDirection(dir);
		Point last = start;
		int len = 1;

		List<Direction> turns = new ArrayList<>(4);
		while (true) {
			turns.removeAll(turns);
			if (current.x() == map.height() - 1)
				break;
			for (Direction d : CARDINALS) {
				Point next = current.applyDirection(d);
				if (last.equals(next))
					continue;
				if (map.getBase(next, Forest) == Forest)
					continue;
				turns.add(d);
			}
			if (turns.isEmpty())
				throw new RuntimeException("Road ends here " + current);
			if (turns.size() > 1)
				break;
			last = current;
			current = current.applyDirection(turns.get(0));
			len++;
		}

		Path p = new Path(start, current, len);
		Point c = current;
		connections.get(start).add(p);
		if (connections.containsKey(current))
			connections.get(current).add(p);
		else {
			Set<Path> endCon = new HashSet<>();
			endCon.add(p);
			connections.put(current, endCon);
			for (Direction d : turns) {
				buildGraph(map, connections, current, d);
			}
		}
	}

	private int longestPath(Layered2DMap<ForestTile> map, Map<Point, Set<Path>> connections, List<Point> previous, Point now, int len) {
		if (now.x() == map.height() - 1) {
			totalPaths++;
			if (len > maxLengthSoFar) {
				log("new best path length", len);
				maxLengthSoFar = len;
			}
			return 0;
		}

		List<Point> current = new ArrayList<>(previous);
		current.add(now);
		int max = -1;
		for (Path segment : connections.get(now)) {
			Point next = segment.getOtherEnd(now);
			if (previous.contains(next))
				continue;
			max = Math.max(max, longestPath(map, connections, current, next, len + segment.len()) + segment.len());
		}
		return max;
	}

	record Path(Point start, Point end, int len) {
		public Path(Point start, Point end, int len) {
			if (start.x() < end.x() || start.y() < end.y()) {
				this.start = start;
				this.end = end;
			} else {
				this.start = end;
				this.end = start;
			}
			this.len = len;
		}

		public Point getOtherEnd(Point in) {
			if (start.equals(in))
				return end;
			else if (end.equals(in))
				return start;
			else
				throw new RuntimeException("Point not in path");
		}

		public boolean containsPoint(Point p) {
			return start.equals(p) || end.equals(p);
		}
	}

	static class SimplePathfinder implements TaskList.Processor<List<Point>, List<Point>> {
		private final Layered2DMap<ForestTile> map;
		private final boolean respectSlopes;

		SimplePathfinder(Layered2DMap<ForestTile> map, boolean respectSlopes) {
			this.map = map;
			this.respectSlopes = respectSlopes;
		}

		@Override
		public Optional<List<Point>> run(TaskList<List<Point>, List<Point>> tasklist, List<Point> path) {
			Point pos = path.get(path.size() - 1);
			if (pos.x() == map.height() - 1)
				return Optional.of(path);
			List<Point> posibilities = new ArrayList<>();
			for (Direction d : CARDINALS) {
				Point newPos = pos.applyDirection(d);
				if (path.contains(newPos))
					continue;
				ForestTile newTile = map.getBase(newPos, Forest);
				if (newTile == Forest)
					continue;
				if (respectSlopes && ((newTile == SlopeN && d != N)
						|| (newTile == SlopeE && d != E)
						|| (newTile == SlopeS && d != S)
						|| (newTile == SlopeW && d != W)))
					continue;
				posibilities.add(newPos);
			}
			if (posibilities.size() == 1) {
				path.add(posibilities.get(0));
				return run(tasklist, path);
			} else {
				List<Point> maxPath = null;
				boolean allThingsConsidered = true;
				for (Point newPos : posibilities) {
					ArrayList<Point> newPath = new ArrayList<>(path);
					newPath.add(newPos);
					Optional<List<Point>> o = tasklist.recurse(newPath);
					if (o.isEmpty())
						allThingsConsidered = false;
					else if (maxPath == null || o.get().size() > maxPath.size())
						maxPath = o.get();
				}
				return !allThingsConsidered ? Optional.empty() :
						maxPath != null ? Optional.of(maxPath) : Optional.of(new ArrayList<>(0));
			}
		}
	}

	enum ForestTile implements MapElement {
		Path('.', '·'),
		Forest('#', '░'),
		SlopeN('^', '^'),
		SlopeE('>', '>'),
		SlopeS('v', 'v'),
		SlopeW('<', '<');

		private final char p;
		private final char o;

		ForestTile(char p, char o) {
			this.p = p;
			this.o = o;
		}

		@Override
		public char getParseChar() {
			return p;
		}

		@Override
		public char getOutputChar() {
			return o;
		}
	}

}
