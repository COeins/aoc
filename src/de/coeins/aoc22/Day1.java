package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.List;

class Day1 implements Day<Integer>  {

	@Override
	public Integer task1(String[] in) {
		int maxelf = 0;
		int curelf = 0;
		for (String i:  in) {
			if (i.length() == 0) {
				if (curelf > maxelf)
					maxelf = curelf;
				curelf = 0;
			}
			else
				curelf += Integer.parseInt(i);
		}
		if (curelf > maxelf)
			maxelf = curelf;
		return maxelf;
	}

	@Override
	public Integer task2(String[] in) {
		List<Integer> elves = new ArrayList<>();
		int curelf = 0;
		for (String i:  in) {
			if (i.length() == 0) {
				elves.add(curelf);
				curelf = 0;
			}
			else
				curelf += Integer.parseInt(i);
		}
		elves.add(curelf);
		elves.sort((i1, i2) -> i2 - i1);
		return elves.get(0) + elves.get(1) + elves.get(2);
	}
}
