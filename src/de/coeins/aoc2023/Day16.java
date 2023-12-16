package de.coeins.aoc2023;

import static de.coeins.aoc2023.Layered2DMap.Direction.N;
import static de.coeins.aoc2023.Layered2DMap.Direction.E;
import static de.coeins.aoc2023.Layered2DMap.Direction.S;
import static de.coeins.aoc2023.Layered2DMap.Direction.W;

import java.util.HashSet;
import java.util.Set;

class Day16 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		Layered2DMap<Mirrors> map = Layered2DMap.parseCharacters(in, 0, Mirrors.class);
		sendLight(map, new HashSet<>(), new Layered2DMap.Point(0, 0), E);
		log(map);
		return map.sumLayer();
	}

	@Override
	public Integer task2(String[] in) {
		Layered2DMap<Mirrors> map = Layered2DMap.parseCharacters(in, 0, Mirrors.class);
		int energized = 0;
		for (int l = 0; l < map.height(); l++) {
			sendLight(map, new HashSet<>(), new Layered2DMap.Point(l, 0), E);
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
			sendLight(map, new HashSet<>(), new Layered2DMap.Point(l, map.width() - 1), W);
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
		}
		for (int c = 0; c < map.width(); c++) {
			sendLight(map, new HashSet<>(), new Layered2DMap.Point(0, c), S);
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
			sendLight(map, new HashSet<>(), new Layered2DMap.Point(map.height() - 1, c), N);
			energized = Math.max(energized, map.sumLayer());
			map.resetLayer();
		}
		return energized;
	}

	record LightRay(Layered2DMap.Point pos, Layered2DMap.Direction dir) {
	}

	void sendLight(Layered2DMap<Mirrors> map, Set<LightRay> rays, Layered2DMap.Point pos, Layered2DMap.Direction dir) {
		if (!map.validPoint(pos))
			return;
		if (rays.contains(new LightRay(pos, dir)))
			return;
		rays.add(new LightRay(pos, dir));
		map.setLayer(pos, 1);
		switch (map.getBasePoint(pos)) {
			case empty:
				sendLight(map, rays, pos.applyDirection(dir), dir);
				break;
			case splitEW:
				if (dir == E || dir == W)
					sendLight(map, rays, pos.applyDirection(dir), dir);
				else {
					sendLight(map, rays, pos.applyDirection(E), E);
					sendLight(map, rays, pos.applyDirection(W), W);
				}
				break;
			case splitNS:
				if (dir == N || dir == S)
					sendLight(map, rays, pos.applyDirection(dir), dir);
				else {
					sendLight(map, rays, pos.applyDirection(N), N);
					sendLight(map, rays, pos.applyDirection(S), S);
				}
				break;
			case mirrorNE:
				switch (dir) {
					case N -> sendLight(map, rays, pos.applyDirection(E), E);
					case S -> sendLight(map, rays, pos.applyDirection(W), W);
					case E -> sendLight(map, rays, pos.applyDirection(N), N);
					case W -> sendLight(map, rays, pos.applyDirection(S), S);
				}
				break;
			case mirrorNW:
				switch (dir) {
					case N -> sendLight(map, rays, pos.applyDirection(W), W);
					case S -> sendLight(map, rays, pos.applyDirection(E), E);
					case E -> sendLight(map, rays, pos.applyDirection(S), S);
					case W -> sendLight(map, rays, pos.applyDirection(N), N);
				}
		}
	}

	enum Mirrors implements Layered2DMap.MapElement {
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
