package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.coeins.aoc2023.Day;

class Day1 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		List<Integer> listA = new ArrayList<Integer>(in.length);
		List<Integer> listB = new ArrayList<Integer>(in.length);
		for (String s : in) {
			String[] n = s.split(" ");
			listA.add(Integer.parseInt(n[0]));
			listB.add(Integer.parseInt(n[3])); // 3 spaces in
		}
		listA.sort(Comparator.naturalOrder());
		listB.sort(Comparator.naturalOrder());
		int sum = 0;
		for (int i = 0; i < listA.size(); i++) {
			sum += Math.abs(listA.get(i) - listB.get(i));
		}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		Map<Integer, Integer> listA = new HashMap<>(in.length);
		Map<Integer, Integer> listB = new HashMap<>(in.length);
		for (String s : in) {
			String[] n = s.split(" ");
			int a = Integer.parseInt(n[0]);
			int b = Integer.parseInt(n[3]);
			listA.put(a, listA.getOrDefault(a, 0) + 1);
			listB.put(b, listB.getOrDefault(b, 0) + 1);
		}
		int sum = 0;
		for (Integer k : listA.keySet()) {
			sum += k * listA.get(k) * listB.getOrDefault(k, 0);
		}
		return sum;
	}
}
