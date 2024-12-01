package de.coeins.aoc2024;

import java.util.HashMap;
import java.util.Map;

import de.coeins.aoc2023.Day;

public class Inputs {
	public static Map<Integer, Day> days = new HashMap<>();
	public static Map<Integer, String[]> tasks = new HashMap<>();
	public static Map<Integer, Object[]> solutionsTask1 = new HashMap<>();
	public static Map<Integer, Object[]> solutionsTask2 = new HashMap<>();

	static {
		{
			days.put(1, new Day1());
			solutionsTask1.put(1, new Integer[] { 11, 2769675 });
			solutionsTask2.put(1, new Integer[] { 31, 24643097 });
			tasks.put(1, new String[] {
					"3   4\n" +
							"4   3\n" +
							"2   5\n" +
							"1   3\n" +
							"3   9\n" +
							"3   3",

					LargeInputs.DAY1 });
		} // 1

	}
}
