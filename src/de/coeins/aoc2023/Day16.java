package de.coeins.aoc2023;

import static de.coeins.aoc2023.Layered2DMap.Direction.N;
import static de.coeins.aoc2023.Layered2DMap.Direction.E;
import static de.coeins.aoc2023.Layered2DMap.Direction.S;
import static de.coeins.aoc2023.Layered2DMap.Direction.W;
import static de.coeins.aoc2023.Layered2DMap.*;

import java.util.Optional;

class Day16 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Mirrors> map = Layered2DMap.parseCharacters(in, 0, Mirrors.class);
		new TaskList<>(new SendLight(map)).run(new LightRay(new Point(0, 0), E));
		log(map.toString((_p, b, l) -> !b.equals(Mirrors.empty) || l < 1 ? b.getOutputChar() : '#'));
		return map.sumLayer();
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Mirrors> map = Layered2DMap.parseCharacters(in, 0, Mirrors.class);
		int energized = 0;
		for (int l = 0; l < map.height(); l++) {
			new TaskList<>(new SendLight(map)).run(new LightRay(new Point(l, 0), E));
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
			new TaskList<>(new SendLight(map)).run(new LightRay(new Point(l, map.width() - 1), W));
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
		}
		for (int c = 0; c < map.width(); c++) {
			new TaskList<>(new SendLight(map)).run(new LightRay(new Point(0, c), S));
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
			new TaskList<>(new SendLight(map)).run(new LightRay(new Point(map.height() - 1, c), N));
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
		}
		return energized;
	}

	record LightRay(Point pos, Direction dir) {
		public LightRay applyDirection(Direction dir) {
			return new LightRay(pos.applyDirection(dir), dir);
		}
	}

	private static class SendLight implements TaskList.Processor<LightRay, Boolean> {
		final Layered2DMap<Mirrors> map;

		private SendLight(Layered2DMap<Mirrors> map) {
			this.map = map;
		}

		public Optional<Boolean> run(TaskList<LightRay, Boolean> tl, LightRay task) {
			if (!map.validPoint(task.pos))
				return Optional.of(false);

			map.setLayer(task.pos, 1);
			switch (map.getBase(task.pos)) {
				case empty:
					run(tl, task.applyDirection(task.dir));
					break;
				case splitEW:
					if (task.dir == E || task.dir == W)
						run(tl, task.applyDirection(task.dir));
					else {
						tl.recurse(task.applyDirection(E));
						tl.recurse(task.applyDirection(W));
					}
					break;
				case splitNS:
					if (task.dir == N || task.dir == S)
						run(tl, task.applyDirection(task.dir));
					else {
						tl.recurse(task.applyDirection(N));
						tl.recurse(task.applyDirection(S));
					}
					break;
				case mirrorNE:
					switch (task.dir) {
						case N -> run(tl, task.applyDirection(E));
						case S -> run(tl, task.applyDirection(W));
						case E -> run(tl, task.applyDirection(N));
						case W -> run(tl, task.applyDirection(S));
					}
					break;
				case mirrorNW:
					switch (task.dir) {
						case N -> run(tl, task.applyDirection(W));
						case S -> run(tl, task.applyDirection(E));
						case E -> run(tl, task.applyDirection(S));
						case W -> run(tl, task.applyDirection(N));
					}
			}
			return Optional.of(true);
		}
	}

	enum Mirrors implements MapElement {
		empty('.', '·'),
		splitEW('-', '─'),
		splitNS('|', '│'),
		mirrorNW('\\', '\\'),
		mirrorNE('/', '/');

		final char parseChar;
		final char outputChar;

		Mirrors(char parseChar, char outputChar) {
			this.parseChar = parseChar;
			this.outputChar = outputChar;
		}

		@Override
		public char getParseChar() {
			return parseChar;
		}

		@Override
		public char getOutputChar() {
			return outputChar;
		}
	}
}
