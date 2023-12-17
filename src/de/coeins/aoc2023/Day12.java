package de.coeins.aoc2023;

import java.util.Arrays;
import java.util.Optional;

class Day12 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		long sum = 0;
		for (String l : in) {
			String[] parts = l.split(" ");
			int[] clusters = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
			long pos = new TaskList<>(new PossibilityCounter(parts[0], clusters)).run(new State(0, 0, 0));
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
			long pos = new TaskList<>(new PossibilityCounter(longinput, longClusters)).run(new State(0, 0, 0));
			log(longinput, longClusters, pos);
			sum += pos;
		}
		return sum;
	}

	private static class PossibilityCounter implements TaskList.Processor<State, Long> {
		final String line;
		final int[] clusters;

		private PossibilityCounter(String line, int[] clusters) {
			this.line = line;
			this.clusters = clusters;
		}

		public Optional<Long> run(TaskList<State, Long> tl, State s) {
			if (s.linePos >= line.length()) {
				if (s.clusterEl == clusters.length || s.clusterEl == clusters.length - 1 && s.running == clusters[s.clusterEl])
					return Optional.of(1L);
				else
					return Optional.of(0L);
			}

			switch (line.charAt(s.linePos)) {
				case '.':
					if (s.running == 0)
						return tl.recurse(new State(s.linePos + 1, s.clusterEl, 0));
					if (s.running == clusters[s.clusterEl])
						return tl.recurse(new State(s.linePos + 1, s.clusterEl + 1, 0));
					else
						return Optional.of(0L);
				case '#':
					if (s.clusterEl >= clusters.length || s.running > clusters[s.clusterEl])
						return Optional.of(0L);
					return tl.recurse(new State(s.linePos + 1, s.clusterEl, s.running + 1));
				case '?':
					if (s.clusterEl >= clusters.length && s.running > 0)
						throw new RuntimeException("Invalid state");

					if (s.clusterEl >= clusters.length)
						return tl.recurse(new State(s.linePos + 1, s.clusterEl, 0));

					if (s.running == clusters[s.clusterEl])
						return tl.recurse(new State(s.linePos + 1, s.clusterEl + 1, 0));
					if (s.running > 0)
						return tl.recurse(new State(s.linePos + 1, s.clusterEl, s.running + 1));

					Optional<Long> a = tl.recurse(new State(s.linePos + 1, s.clusterEl, 0));
					Optional<Long> b = tl.recurse(new State(s.linePos + 1, s.clusterEl, 1));
					if (a.isEmpty() || b.isEmpty())
						return Optional.empty();

					return Optional.of(a.get() + b.get());

				default:
					throw new RuntimeException("Invalid character");
			}
		}
	}

	record State(int linePos, int clusterEl, int running) {
	}
}
