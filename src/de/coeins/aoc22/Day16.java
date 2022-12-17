package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day16 implements Day<Integer> {

	@Override
	public Integer task1(String[] in) {
		Valve[] valves = parse(in);
		int[][] distances = calculateDistances(valves);
		return findDirectPath(valves, distances, ~0, valves[0], 0, 30);
	}

	@Override
	public Integer task2(String[] in) {
		Valve[] valves = parse(in);
		int[][] distances = calculateDistances(valves);
		int maxValue = 0;
		for (long mask = 0; mask < (1L << valves.length); mask += 4) {
			maxValue = Math.max(maxValue,
					findDirectPath(valves, distances, mask, valves[0], 0, 26)
							+ findDirectPath(valves, distances, ~mask, valves[0], 0, 26));
			if (mask % 6000 == 0)
				log(100 * mask / (1L << valves.length), "% done");
		}
		return maxValue;
	}

	private int findDirectPath(Valve[] valves, int[][] distances, long mask, Valve current, long open, int time) {
		if (time <= 0)
			return 0;
		long newOpen = open | current.mask;
		int maxresult = 0;
		for (Valve next : valves) {
			if ((mask & next.mask) > 0 && (newOpen & next.mask) == 0)
				maxresult = Math.max(maxresult,
						findDirectPath(valves, distances, mask, next, newOpen, time - 1 - distances[current.id][next.id]));
		}
		return maxresult + current.flow * time;
	}

	private int[][] calculateDistances(Valve[] valves) {
		int[][] distances = new int[valves.length][valves.length];
		for (int i = 0; i < valves.length; i++) {
			int[] di = new int[valves.length];
			Arrays.fill(di, Integer.MAX_VALUE);
			findDistances(di, valves[i], null, 0);
			distances[i] = di;
		}
		return distances;
	}

	private void findDistances(int[] dist, Valve current, Valve last, int currentDist) {
		if (current.id != -1) {
			if (dist[current.id] <= currentDist)
				return;
			dist[current.id] = currentDist;
		}
		for (Valve next : current.tunnels) {
			if (next != last)
				findDistances(dist, next, current, currentDist + 1);
		}
	}

	private Valve[] parse(String[] in) {
		Map<String, Valve> valves = new HashMap<>(in.length);
		int id = 1;
		for (String l : in) {
			String[] split = l.split("[ =;,]+");
			Valve v = new Valve(split[1].equals("AA") ? 0 : !split[5].equals("0") ? id++ : -1, split[1], Integer.parseInt(split[5]));
			for (int i = 10; i < split.length; i++) {
				if (valves.get(split[i]) != null) {
					v.tunnels.add(valves.get(split[i]));
					valves.get(split[i]).tunnels.add(v);
				}
			}
			valves.put(split[1], v);
		}
		Valve[] va = new Valve[id];
		for (Valve v : valves.values())
			if (v.id >= 0)
				va[v.id] = v;
		return va;
	}

	private class Valve {
		final int id;
		final String name;
		final int flow;
		final List<Valve> tunnels;
		final long mask;

		public Valve(int id, String name, int flow) {
			this.id = id;
			this.name = name;
			this.flow = flow;
			this.tunnels = new ArrayList<>();
			this.mask = 1L << id;
		}

		public String toString() {
			return name;
		}
	}
}
