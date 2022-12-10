package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day5 implements Day<String> {
	@Override
	public String task1(String[] in) {
		Map<Integer, List<Character>> stacks = new HashMap<>();
		for (int i = parseStacks(in, stacks); i < in.length; i++) {
			String[] l = in[i].split(" ");
			int count = Integer.parseInt(l[1]);
			int from = Integer.parseInt(l[3]);
			int to = Integer.parseInt(l[5]);
			for (int j = 0; j < count; j++)
				stacks.get(to).add(0, stacks.get(from).remove(0));
		}
		return topBoxes(stacks);
	}

	@Override
	public String task2(String[] in) {
		Map<Integer, List<Character>> stacks = new HashMap<>();
		for (int i = parseStacks(in, stacks); i < in.length; i++) {
			String[] l = in[i].split(" ");
			int count = Integer.parseInt(l[1]);
			int from = Integer.parseInt(l[3]);
			int to = Integer.parseInt(l[5]);
			for (int j = count - 1; j >= 0; j--)
				stacks.get(to).add(0, stacks.get(from).remove(j));
			//log("moving ", count, "from", from, "to", to);
		}
		logStacks(stacks);
		return topBoxes(stacks);
	}

	private int parseStacks(String[] in, Map<Integer, List<Character>> stacks) {
		int i = 0;
		while (in[i].trim().charAt(0) == '[') {
			int lastpos = -1;
			while ((lastpos = in[i].indexOf('[', lastpos + 1)) >= 0) {
				int stack = 1 + lastpos / 4;
				char letter = in[i].charAt(lastpos + 1);
				stacks.computeIfAbsent(stack, k -> new ArrayList<>()).add(letter);
			}
			i++;
		}
		return i + 2;
	}

	private String topBoxes(Map<Integer, List<Character>> stacks) {
		StringBuilder res = new StringBuilder();
		for (int i : stacks.keySet()) {
			res.append(stacks.get(i).get(0));
		}
		return res.toString();
	}

	private void logStacks(Map<Integer, List<Character>> stacks) {
		for (int i : stacks.keySet()) {
			log("stack", i, stacks.get(i));
		}
	}
}
