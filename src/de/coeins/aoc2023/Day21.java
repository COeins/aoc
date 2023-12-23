package de.coeins.aoc2023;

import static de.coeins.aoc2023.Layered2DMap.CARDINALS;

import de.coeins.aoc2023.Layered2DMap.Direction;
import de.coeins.aoc2023.Layered2DMap.MapElement;
import de.coeins.aoc2023.Layered2DMap.Point;

class Day21 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		Layered2DMap<GardenPlot> map = Layered2DMap.parseCharacters(in, 0, GardenPlot.class);
		map.setLayer(0, map.findInBase(GardenPlot.Start).get(0), 1);

		int maxSteps = in.length <= 20 ? 6 : 64;

		int totalFields = 0;
		for (int step = 1; step <= maxSteps; step++) {
			int current = step;
			totalFields = map.iterateMap((pos, base, _l, previous) -> {
				boolean isReachable = false;
				if (base != GardenPlot.Rock)
					for (Direction d : CARDINALS)
						isReachable |= map.getLayer(current - 1, pos.applyDirection(d), 0) == 1;

				if (isReachable) {
					map.setLayer(current, pos, 1);
					return previous + 1;
				} else
					return previous;
			}, 0);
		}
		log(map.toString(maxSteps));
		return (long) totalFields;
	}

	@Override
	public Long task2(String[] in) {
		Layered2DMap<GardenPlot> map = Layered2DMap.parseCharacters(in, 0, GardenPlot.class);
		Point s = map.findInBase(GardenPlot.Start).get(0);
		map.iterateMap((point, base, _l, _p) -> {
			if (base == GardenPlot.Rock)
				map.setLayer(point, 1);
			return null;
		}, null);
		map.fillLayer(s, 2);
		int emptyCells = map.iterateMap((point, base, _l, prev) -> (base == GardenPlot.Rock) ? prev : prev + 1, 0);

		int[] maxSteps;
		if (in.length < 20)
			maxSteps = new int[] { 6, 10, 50, 100, 500, 1000, 5000 }; // TODO figure out differences with example
		else
			maxSteps = new int[] { 26501365 };

		long[] reachableFields = new long[maxSteps.length];

		for (int i = 0; i < maxSteps.length; i++) {

			long iteration = (maxSteps[i]) / map.width();
			long baseVal = (long) (iteration * iteration);
			long intdVal = baseVal + iteration;
			long nextVal = (long) ((iteration + 1) * (iteration + 1));
			int mod = map.width() + (maxSteps[i]) % map.width();

			map.resetLayer(1);
			for (int x = mod; x >= -mod; x -= 1) {
				for (int y = mod - Math.abs(x); y >= -(mod - Math.abs(x)); y -= 2) {
					Point p = new Point(Math.floorMod(s.x() + x, map.width()), Math.floorMod(s.y() + y, map.width()));
					map.setLayer(1, p, map.getLayer(1, p) + 1);
				}
			}

			log(iteration, baseVal, intdVal, nextVal, mod);
			log(map.toString((pos, base, layers) -> layers[0] == 0 ? 'X' : base == GardenPlot.Rock || layers[1] < 4
					? base.getOutputChar() : Integer.toString(layers[1]).charAt(0)));

			reachableFields[i] = map.iterateMap((point, base, layers, prev) -> {
				if (layers[0] != 2)
					return prev;
				if (layers[1] == 1)
					return prev + baseVal;
				if (layers[1] == 2)
					return prev + intdVal;
				if (layers[1] == 4)
					return prev + nextVal;
				else
					throw new RuntimeException("Unexpected value: " + layers[1]);

			}, 0L);

			long maxPlaces = (long) (maxSteps[i] + 1) * (maxSteps[i] + 1);
			log("steps:", maxSteps[i], "max:", maxPlaces,
					"estimated:", maxPlaces / ((long) map.width() * map.height()) * emptyCells,
					"calculated:", reachableFields[i]);
		}
		return reachableFields[reachableFields.length - 1];

	}

	enum GardenPlot implements MapElement {
		Start('S'),
		Empty('.'),
		Rock('#');

		private final char c;

		GardenPlot(char c) {
			this.c = c;
		}

		@Override
		public char getParseChar() {
			return c;
		}

		@Override
		public char getOutputChar() {
			return c;
		}
	}
}
