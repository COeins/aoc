package de.coeins.aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Day14 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int sum = 0;
		Overridable2DCharMap map = new Overridable2DCharMap(in);

		for (int l = 0; l < map.lines; l++) {
			int count = 0;
			col:
			for (int c = 0; c < map.cols; c++)
				for (int up = 0; up < map.lines - l; up++)
					switch (map.getCharAt(l + up, c)) {
						case '#':
							continue col;
						case 'O':
							count++;
							map.setCharAt(l + up, c, '.');
							continue col;
					}

			log("row", l, "Os", count);
			sum += count * (map.lines - l);
		}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		Overridable2DCharMap map = new Overridable2DCharMap(in);
		Map<String, Long> previous = new HashMap<>();

		long target = 1000000000L;
		boolean looped = false;
		for (long cycle = 0; cycle < target; cycle++) {
			if (!looped && previous.containsKey(map.toString())) {
				long len = cycle - previous.get(map.toString());
				log("loop found after cycle", cycle, "length", len);
				cycle += len * ((target - cycle) / len);
				looped = true;
			} else
				previous.put(map.toString(), cycle);

			// north
			for (int l = 0; l < map.lines; l++) {
				nextPos:
				for (int c = 0; c < map.cols; c++)
					for (int up = 0; up < map.lines - l; up++)
						switch (map.getCharAt(l + up, c)) {
							case '#':
								continue nextPos;
							case 'O':
								map.setCharAt(l + up, c, '.');
								map.setCharAt(l, c, 'O');
								continue nextPos;
						}
			}

			// west
			for (int c = 0; c < map.cols; c++) {
				nextPos:
				for (int l = 0; l < map.lines; l++)
					for (int up = 0; up < map.cols - c; up++)
						switch (map.getCharAt(l, c + up)) {
							case '#':
								continue nextPos;
							case 'O':
								map.setCharAt(l, c + up, '.');
								map.setCharAt(l, c, 'O');
								continue nextPos;
						}
			}

			// south
			for (int l = map.lines - 1; l >= 0; l--) {
				nextPos:
				for (int c = 0; c < map.cols; c++)
					for (int up = 0; up <= l; up++)
						switch (map.getCharAt(l - up, c)) {
							case '#':
								continue nextPos;
							case 'O':
								map.setCharAt(l - up, c, '.');
								map.setCharAt(l, c, 'O');
								continue nextPos;
						}
			}

			// east
			for (int c = map.cols - 1; c >= 0; c--) {
				nextPos:
				for (int l = 0; l < map.lines; l++)
					for (int up = 0; up <= c; up++)
						switch (map.getCharAt(l, c - up)) {
							case '#':
								continue nextPos;
							case 'O':
								map.setCharAt(l, c - up, '.');
								map.setCharAt(l, c, 'O');
								continue nextPos;
						}
			}
		}
		logMap(map);

		// count
		int sum = 0;
		for (int l = 0; l < map.lines; l++) {
			int count = 0;
			col:
			for (int c = 0; c < map.cols; c++)
				if (map.getCharAt(l, c) == 'O')
					count++;
			sum += count * (map.lines - l);
		}
		return sum;
	}

	private void logMap(Overridable2DCharMap map) {
		for (int l = 0; l < map.lines; l++) {
			StringBuilder sb = new StringBuilder();
			for (int c = 0; c < map.cols; c++)
				sb.append(map.getCharAt(l, c));
			log(sb);
		}
		log("");
	}

	static class Overridable2DCharMap {
		final String[] baseMap;
		final char[][] overrides;
		final int lines;
		final int cols;

		Overridable2DCharMap(String[] in) {
			baseMap = in;
			lines = in.length;
			cols = in[0].length();
			overrides = new char[lines][cols];
		}

		char getCharAt(int l, int c) {
			if (overrides[l][c] != 0)
				return overrides[l][c];
			return baseMap[l].charAt(c);
		}

		void setCharAt(int l, int c, char set) {
			overrides[l][c] = set;
		}

		int hashSum() {
			return Arrays.deepHashCode(overrides);
		}

		@Override
		public String toString() {
			return Arrays.deepToString(overrides);
		}
	}
}
