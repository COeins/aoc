package de.coeins.aoc22;

import java.util.HashMap;
import java.util.Map;

class Day17 implements Day<Long> {
	private final static int[][] ROCKS = new int[][] {
			new int[] { 1, 1, 1, 1 },
			new int[] { 2, 7, 2 },
			new int[] { 1, 1, 7 },
			new int[] { 15 },
			new int[] { 3, 3 }
	};

	@Override
	public Long task1(String[] in) {
		boolean[][] stack = new boolean[7][5000];
		int patPos = 0;
		int maxHeight = 0;
		for (int r = 0; r < 2022; r++) {
			int[] result = nextRock(stack, in[0], patPos, maxHeight, r);
			patPos = result[0] % in[0].length();
			maxHeight = result[1];
		}
		return (long) maxHeight;
	}

	public Long task2(String[] in) {
		boolean[][] stack = new boolean[7][10000];
		final long rockTarget = 1000000000000L;

		int patPos = 0, maxHeight = 0, loopLength = 0, loopStartPos = 0, loopStartHeight = 0;
		Map<Integer, Integer> loopCandidates = new HashMap<>();
		for (int r = 0; true; r++) {
			if (r % ROCKS.length == 0) {
				if (loopStartPos == 0) {
					if (loopCandidates.containsKey(patPos)) {
						loopLength = r - loopCandidates.get(patPos);
						loopStartPos = (int) ((rockTarget - r) % loopLength) + r;
						if (loopStartPos == r)
							loopStartPos += loopLength;
						log("Loop detected from", loopStartPos, "to", loopStartPos + loopLength);
					} else
						loopCandidates.put(patPos, r);
				} else if (r == loopStartPos) {
					loopStartHeight = maxHeight;
				} else if (r == loopStartPos + loopLength) {
					return loopStartHeight + ((rockTarget - loopStartPos) / loopLength) * (maxHeight - loopStartHeight);
				}
			}
			int[] result = nextRock(stack, in[0], patPos, maxHeight, r);
			patPos = result[0] % in[0].length();
			maxHeight = result[1];
		}
	}

	private int[] nextRock(boolean[][] stack, String pattern, int patPos, int startHeight, int rock) {
		int y, x = 2;
		for (y = startHeight + 3; y >= 0; y--) {
			int dx = pattern.charAt(patPos++ % pattern.length()) == '>' ? 1 : -1;
			if (x + dx >= 0 && x + dx + ROCKS[rock % ROCKS.length].length <= stack.length) {
				if (!collides(ROCKS[rock % ROCKS.length], stack, x + dx, y))
					x += dx;
			}
			//log("rock move", pattern, r, "x", x, "y", y);
			if (collides(ROCKS[rock % ROCKS.length], stack, x, y - 1))
				break;
		}
		for (int w = 0; w < ROCKS[rock % ROCKS.length].length; w++) {
			for (int h = 0; h < 4; h++) {
				if ((ROCKS[rock % ROCKS.length][w] & 1 << h) > 0) {
					startHeight = Math.max(startHeight, y + h + 1);
					stack[x + w][y + h] = true;
				}
			}
		}
		//log("rock solid ", r, "x", x, "y", y, "max", visualize(stack, maxHeight + 1));
		return new int[] { patPos, startHeight };
	}

	private boolean collides(int[] rock, boolean[][] stack, int x, int y) {
		if (y < 0)
			return true;
		for (int w = 0; w < rock.length; w++) {
			for (int h = 0; h < 4; h++) {
				if ((rock[w] & 1 << h) > 0 && stack[x + w][y + h])
					return true;
			}
		}
		return false;
	}

	private String visualize(boolean[][] stack, int y) {
		StringBuilder s = new StringBuilder("\n");
		for (int i = y; i > y - 7; i--) {
			if (i < 0)
				return s.append("   |-------|").toString();
			s.append(i).append(": |");
			for (boolean[] booleans : stack)
				s.append(booleans[i] ? "#" : " ");
			s.append("|\n");
		}
		return s.toString();
	}

}
