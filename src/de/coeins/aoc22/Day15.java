package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day15 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		List<Sensor> sensors = parse(in);
		int maxX = 0, maxY = 0, minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
		for (Sensor s : sensors) {
			maxX = Math.max(maxX, s.sx + s.md);
			maxY = Math.max(maxY, s.sy + s.md);
			minX = Math.min(minX, s.sx - s.md);
			minY = Math.min(minY, s.sy - s.md);
		}
		int checkY = sensors.get(0).sx < 100 ? 10 : 2000000;
		long covered = 0;
		boolean last = true;
		xLoop:
		for (int x = minX; x <= maxX; x++) {
			for (Sensor s : sensors) {
				if (s.distance(x, checkY) <= s.md) {
					int newx = s.sx + s.md - Math.abs(s.sy - checkY);
					covered += 1 + newx - x;
					last = true;
					log(x, checkY, "covered by", s.sx, s.sy, s.md, "until", newx);
					x = newx;
					continue xLoop;
				}
			}
			if (last) {
				last = false;
				log(x, checkY, "uncovered");
			}
		}
		Set<Integer> beacons = new HashSet<>();
		for (Sensor s : sensors) {
			if (s.by == checkY)
				beacons.add(s.bx);
		}
		return covered - beacons.size();
	}

	@Override
	public Long task2(String[] in) {
		List<Sensor> sensors = parse(in);
		int minX, minY, maxX, maxY;
		if (sensors.get(0).sx < 100) {
			minX = 0;
			minY = 0;
			maxX = 20;
			maxY = 20;
		} else {
			minX = 0;
			minY = 0;
			maxX = 4000000;
			maxY = 4000000;
		}
		for (int x = minX; x <= maxX; x++) {
			yLoop:
			for (int y = minY; y <= maxY; y++) {
				for (Sensor s : sensors) {
					if (s.distance(x, y) <= s.md) {
						y = s.sy + s.md - Math.abs(s.sx - x);
						continue yLoop;
					}
				}
				log(x, y, "found uncovered");
				return 4000000L * x + y;
			}
		}
		throw new RuntimeException("No Match found");
	}

	private List<Sensor> parse(String[] in) {
		List<Sensor> results = new ArrayList<>(in.length);
		for (String l : in) {
			String[] split = l.split("[=,:]");
			results.add(new Sensor(Integer.parseInt(split[1]), Integer.parseInt(split[3]), Integer.parseInt(split[5]),
					Integer.parseInt(split[7])));
		}
		return results;
	}

	private class Sensor {
		final int sx, sy, bx, by, md;

		private Sensor(int sx, int sy, int bx, int by) {
			this.sx = sx;
			this.sy = sy;
			this.bx = bx;
			this.by = by;
			this.md = distance(bx, by);
		}

		int distance(int x, int y) {
			return Math.abs(sx - x) + Math.abs(sy - y);
		}

	}

}
