package de.coeins.aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Day12 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		long sum = 0;
		for (String l : in) {
			String[] parts = l.split(" ");
			int[] clusters = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
			long pos = posibilities(parts[0], clusters, new HashMap<>(), new State(0, 0, 0)); //, "");
			log(l, pos);
			sum += pos;
		}
		return sum;
	}

	@Override
	public Long task2(String[] in) {
		long sum = 0;
		for (int il = 0; il < in.length; il++) {
			String[] parts = in[il].split(" ");
			int[] clusters = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
			String longinput = (parts[0] + "?").repeat(5);
			longinput = longinput.substring(0, longinput.length() - 1);
			int[] longClusters = new int[clusters.length * 5];
			for (int i = 0; i < 5; i++)
				System.arraycopy(clusters, 0, longClusters, clusters.length * i, clusters.length);
			long pos = posibilities(longinput, longClusters, new HashMap<>(), new State(0, 0, 0)); //, "");
			log(longinput, longClusters, pos);
			sum += pos;
		}
		return sum;
	}

	private long posibilities(String line, int[] clusters, Map<State, Long> posibilitySpace, State s) {
		if (s.linePos >= line.length()) {
			if (s.clusterEl == clusters.length || s.clusterEl == clusters.length - 1 && s.running == clusters[s.clusterEl])
				return 1;
			else
				return 0;
		}

		switch (line.charAt(s.linePos)) {
			case '.':
				if (s.running == 0)
					return posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl, 0); //, result + '.');
				if (s.running == clusters[s.clusterEl])
					return posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl + 1, 0); //, result + '.');
				else
					return 0;
			case '#':
				if (s.clusterEl >= clusters.length || s.running > clusters[s.clusterEl])
					return 0;
				return posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl, s.running + 1); //, result + '#');
			case '?':
				if (s.clusterEl >= clusters.length && s.running > 0)
					throw new RuntimeException("Invalid state");

				if (s.clusterEl >= clusters.length)
					return posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl, 0); //, result + '.');

				if (s.running == clusters[s.clusterEl])
					return posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl + 1, 0); //, result + '.');
				if (s.running > 0)
					return posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl, s.running + 1); //, result + '#');

				long a = posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl, 0); //, result + '.');
				long b = posOrCached(line, clusters, posibilitySpace, s.linePos + 1, s.clusterEl, 1); //, result + '#');
				return a + b;

			default:
				throw new RuntimeException("Invalid character");
		}
	}

	private long posOrCached(String line, int[] clusters, Map<State, Long> posibilitySpace, int linePos, int clusterEl, int running) {
		State newS = new State(linePos, clusterEl, running);
		if (posibilitySpace.containsKey(newS))
			return posibilitySpace.get(newS);

		long newPos = posibilities(line, clusters, posibilitySpace, newS);
		posibilitySpace.put(newS, newPos);
		return newPos;
	}

	record State(int linePos, int clusterEl, int running) {
	}
}
