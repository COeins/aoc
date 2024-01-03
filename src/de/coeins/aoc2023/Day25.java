package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day25 implements Day<Integer> {

	@Override
	public Integer task1(String[] in) {
		Map<String, List<Connection>> connMap = new HashMap<>();

		// build graph
		for (String l : in) {
			String[] split = l.split(" ");
			String a = split[0].substring(0, 3);
			if (!connMap.containsKey(a))
				connMap.put(a, new ArrayList<>());
			for (int i = 1; i < split.length; i++) {
				String b = split[i];
				if (!connMap.containsKey(b))
					connMap.put(b, new ArrayList<>());
				Connection con = new Connection(a, b);
				connMap.get(a).add(con);
				connMap.get(b).add(con);
			}
		}

		Map<Connection, Integer> heat = new HashMap<>();
		int counter = 0;
		List<Connection> blocked = new ArrayList<>();
		for (String node1 : connMap.keySet()) {

			// calculate distances
			Map<String, Integer> distances = new HashMap<>();
			distances.put(node1, 0);
			Map<String, String> path = new HashMap<>();
			List<String> next = new ArrayList(List.of(node1));

			for (int i = 0; i < next.size(); i++) {
				String cur = next.get(i);

				int cost = distances.get(cur) + 1;
				for (Connection c : connMap.get(cur)) {
					if (blocked.contains(c))
						continue;
					String candidate = c.other(cur);
					if (distances.getOrDefault(candidate, Integer.MAX_VALUE) < cost)
						continue;
					distances.put(candidate, cost);
					next.add(candidate);
					path.put(candidate, cur);
				}
			}

			// build heat map
			int unreachable = 0;
			for (String node2 : connMap.keySet()) {
				if (node2.equals(node1))
					continue;
				if (!path.containsKey(node2))
					unreachable++;
				else {
					String node3 = node2;
					do {
						String node4 = path.get(node3);
						Connection c = new Connection(node3, node4);
						heat.put(c, heat.getOrDefault(c, 0) + 1);
						node3 = node4;

					}
					while (path.containsKey(node3));
				}
			}
			if (unreachable > 0) {
				log("We've found a separation with", unreachable, "nodes and", blocked.size(), "cuts");
				return unreachable * (connMap.size() - unreachable);
			}

			// every 3 rounds, cut "hottest" connection
			if (counter++ % 3 == 0) {
				int maxHeat = 0;
				Connection maxCon = null;
				for (Map.Entry<Connection, Integer> e : heat.entrySet()) {
					if (e.getValue() > maxHeat) {
						maxHeat = e.getValue();
						maxCon = e.getKey();
					}
				}
				log("Cutting link:", maxCon);
				blocked.add(maxCon);
			}
		}

		throw new RuntimeException("No separation found");
	}

	@Override
	public Integer task2(String[] in) {
		return -1;
	}

	record Connection(String node1, String node2) {
		public Connection(String node1, String node2) {
			if (node1.compareTo(node2) < 0) {
				this.node1 = node1;
				this.node2 = node2;
			} else {
				this.node1 = node2;
				this.node2 = node1;
			}
		}

		public String other(String same) {
			if (node1.equals(same))
				return node2;
			else if (node2.equals(same))
				return node1;
			else
				throw new RuntimeException("Nope");

		}
	}
}
