package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.List;

class Day19 implements Day<Integer> {
	private enum Resource {ORE, CLAY, OBSIDIAN, GEODE}

	private int counter = 0;

	@Override
	public Integer task1(String[] in) {
		int sumProduced = 0;
		for (Blueprint b : parse(in)) {
			int[] resources = new int[Resource.values().length];
			int[] robots = new int[Resource.values().length];
			robots[Resource.ORE.ordinal()] = 1;
			this.counter = 0;
			int produced = produce(b, resources, robots, 24, null);
			log("Blueprint", b.id, "produced", produced, "items after", this.counter, "tries");
			sumProduced += b.id * produced;
		}
		return sumProduced;
	}

	@Override
	public Integer task2(String[] in) {
		int prodProduced = 1;
		List<Blueprint> bps = parse(in);
		for (int i = 0; i < 3 && i < bps.size(); i++) {
			Blueprint b = bps.get(i);
			int[] resources = new int[Resource.values().length];
			int[] robots = new int[Resource.values().length];
			robots[Resource.ORE.ordinal()] = 1;
			this.counter = 0;
			int produced = produce(b, resources, robots, 32, null);
			log("Blueprint", b.id, "produced", produced, "items after", this.counter, "tries");
			prodProduced *= produced;
		}
		return prodProduced;
	}

	int produce(Blueprint b, int[] resources, int[] robots, int time, Resource saveFor) {
		if (time <= 0) {
			this.counter++;
			return resources[Resource.GEODE.ordinal()];
		}
		if (time == 1) {
			this.counter++;
			int[] newRes = new int[Resource.values().length];
			for (int res = 0; res < Resource.values().length; res++)
				newRes[res] = resources[res] + robots[res];
			return produce(b, newRes, robots, time - 1, saveFor);
		}
		int maxProduced = -1;
		boolean trySkip = true;
		int[] canAfford = new int[Resource.values().length];
		int lastIndex = (time > 3) ? 0 : Resource.GEODE.ordinal();
		for (int rob = Resource.values().length - 1; rob >= lastIndex; rob--) {
			if (saveFor != null && saveFor.ordinal() != rob)
				continue;
			if (time < 8 - 2 * rob)
				continue;
			int build = Integer.MAX_VALUE;
			for (int res = 0; res < Resource.values().length; res++)
				if (b.costs[rob][res] > 0)
					build = Math.min(build, resources[res] / b.costs[rob][res]);
			if (build > 0) {
				canAfford[rob] = build;
				int[] newRes = new int[Resource.values().length];
				for (int res = 0; res < Resource.values().length; res++)
					newRes[res] = resources[res] + robots[res] - b.costs[rob][res];
				int[] newRobots = robots.clone();
				newRobots[rob] += 1;
				maxProduced = Math.max(maxProduced, produce(b, newRes, newRobots, time - 1, null));
				if (rob == Resource.GEODE.ordinal()) {
					trySkip = false;
					break;
				}
			}
		}
		if (trySkip) {
			int[] newRes = new int[Resource.values().length];
			for (int res = 0; res < Resource.values().length; res++)
				newRes[res] = resources[res] + robots[res];
			if (saveFor == null && maxProduced >= 0) {
				sfl:
				for (Resource sf : Resource.values()) {
					if (time < 9 - 2 * sf.ordinal())
						continue;
					if (canAfford[sf.ordinal()] > 0)
						continue;
					for (int res = 0; res < Resource.values().length; res++)
						if (b.costs[sf.ordinal()][res] > 0 && (robots[res] == 0 || b.costs[sf.ordinal()][res] / robots[res] > time))
							continue sfl;
					maxProduced = Math.max(maxProduced, produce(b, newRes, robots, time - 1, sf)); // path + "S" + sf.ordinal()
				}
			} else if (maxProduced < 0 || time >= 9 - 2 * saveFor.ordinal())
				maxProduced = Math.max(maxProduced, produce(b, newRes, robots, time - 1, saveFor));// path + "> ",
		}
		return maxProduced;
	}

	private List<Blueprint> parse(String[] in) {
		List<Blueprint> result = new ArrayList<>(in.length);
		for (String l : in) {
			String[] s = l.split("[: ]");
			result.add(new Blueprint(Integer.parseInt(s[1]), Integer.parseInt(s[7]), Integer.parseInt(s[13]),
					Integer.parseInt(s[19]), Integer.parseInt(s[22]), Integer.parseInt(s[28]), Integer.parseInt(s[31])));
		}
		return result;
	}

	private class Blueprint {
		final int id;
		final int[][] costs;

		private Blueprint(int id, int oreOreCost, int clayOreCost, int obsOreCost, int obsClayCost, int geoOreCost, int geoObsCost) {
			this.id = id;
			costs = new int[Resource.values().length][Resource.values().length];
			costs[Resource.ORE.ordinal()][Resource.ORE.ordinal()] = oreOreCost;
			costs[Resource.CLAY.ordinal()][Resource.ORE.ordinal()] = clayOreCost;
			costs[Resource.OBSIDIAN.ordinal()][Resource.ORE.ordinal()] = obsOreCost;
			costs[Resource.OBSIDIAN.ordinal()][Resource.CLAY.ordinal()] = obsClayCost;
			costs[Resource.GEODE.ordinal()][Resource.ORE.ordinal()] = geoOreCost;
			costs[Resource.GEODE.ordinal()][Resource.OBSIDIAN.ordinal()] = geoObsCost;
		}
	}
}
