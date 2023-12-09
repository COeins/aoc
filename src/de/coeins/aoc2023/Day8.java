package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		String instruction = in[0];
		Map<String, String[]> rules = new HashMap<>(in.length);
		for (int i = 2; i < in.length; i++) {
			String a = in[i].substring(0, 3);
			String b = in[i].substring(7, 10);
			String c = in[i].substring(12, 15);
			rules.put(a, new String[] { b, c });
		}

		String pos = "AAA";
		if (!rules.containsKey(pos))
			return -1L;

		int count = 0;
		int instPos = 0;

		while (!pos.equals("ZZZ")) {
			count++;
			rl instRL = rl.valueOf(instruction.substring(instPos, instPos + 1));
			//log(pos, instRL);
			pos = rules.get(pos)[instRL.ordinal()];
			instPos = (instPos + 1) % instruction.length();
		}
		return (long) count;
	}

	@Override
	public Long task2(String[] in) {
		String instruction = in[0];
		Map<String, String[]> rules = new HashMap<>(in.length);
		List<String> startPos = new ArrayList<>();
		for (int i = 2; i < in.length; i++) {
			String a = in[i].substring(0, 3);
			String b = in[i].substring(7, 10);
			String c = in[i].substring(12, 15);
			rules.put(a, new String[] { b, c });
			if (a.endsWith("A"))
				startPos.add(a);
		}

		Ghost[] ghosts = new Ghost[startPos.size()];
		boolean simple = true;
		for (int i = 0; i < startPos.size(); i++) {
			ghosts[i] = new Ghost(startPos.get(i), rules, instruction);
			simple &= ghosts[i].simple;
		}

		if (simple) {
			log("Simple case detected. Calculating LCM...");
			long lcm = ghosts[0].loopLen;
			for (int i = 1; i < ghosts.length; i++)
				lcm = lcm(ghosts[i].loopLen, lcm);
			return lcm;
		} else {
			log("General case detected. Simulating movements...");
			long[] testpos = ghosts[0].firstEnds;
			while (true) {
				for (long p : testpos) {
					boolean end = true;
					for (int i = 1; i < ghosts.length; i++)
						end = end && ghosts[i].isEnd(p);
					if (end)
						return p;
				}
				testpos = ghosts[0].calculateNextEnds();
			}
		}
	}

	long lcm(long a, long b) {
		return a / gcd(a, b) * b;

	}

	long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	class Ghost {
		final long loopStart;
		final long loopLen;
		final boolean simple;
		long[] firstEnds;
		long[] loopEnds;
		long currentMax;

		Ghost(String start, Map<String, String[]> rules, String instruction) {
			Map<Pos, Long> visited = new HashMap<>();
			long count = 0;
			int instPos = 0;
			visited.put(new Pos(start, instPos), count);
			String pos = start;
			List<Long> endPos = new ArrayList<>();

			while (true) {
				count++;
				rl instRL = rl.valueOf(instruction.substring(instPos, instPos + 1));
				if (!rules.containsKey(pos)) {
					throw new RuntimeException(start + " ended without loop at " + pos);
				}
				pos = rules.get(pos)[instRL.ordinal()];
				instPos = (instPos + 1) % instruction.length();
				Pos mypos = new Pos(pos, instPos);
				if (visited.containsKey(mypos)) {
					loopStart = visited.get(mypos);
					loopLen = count - visited.get(mypos);
					currentMax = count;
					log(start, "loop detected at", mypos, loopStart, loopLen, endPos);

					List<Long> le = new ArrayList<>();
					for (long l : endPos)
						if (l >= loopStart)
							le.add(l);
					simple = endPos.size() == 1 && endPos.get(0) == loopLen;
					firstEnds = endPos.stream().mapToLong(i -> i).toArray();
					loopEnds = le.stream().mapToLong(i -> i).toArray();
					return;
				}
				visited.put(mypos, count);
				if (pos.charAt(2) == 'Z')
					endPos.add(count);

			}

		}

		long[] calculateNextEnds() {
			for (int i = 0; i < loopEnds.length; i++)
				loopEnds[i] += loopLen;
			currentMax += loopLen;
			return loopEnds;
		}

		boolean isEnd(long pos) {
			if (pos < loopStart)
				return contains(firstEnds, pos);
			while (pos >= currentMax)
				calculateNextEnds();
			return contains(loopEnds, pos);
		}

		boolean contains(long[] array, long value) {
			for (long l : array)
				if (l == value)
					return true;
			return false;
		}
	}

	record Pos(String rule, int instId) {
	}

	enum rl {
		L, R
	}
}
