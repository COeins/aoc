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
		{
			days.put(2, new Day2());
			solutionsTask1.put(2, new Integer[] { 2, 549 });
			solutionsTask2.put(2, new Integer[] { 4, 589 });
			tasks.put(2, new String[] {
					"7 6 4 2 1\n" +
							"1 2 7 8 9\n" +
							"9 7 6 2 1\n" +
							"1 3 2 4 5\n" +
							"8 6 4 4 1\n" +
							"1 3 6 7 9",

					LargeInputs.DAY2 });
		} // 2
		{
			days.put(3, new Day3());
			solutionsTask1.put(3, new Integer[] { 161, 168539636 });
			solutionsTask2.put(3, new Integer[] { 48, 97529391 });
			tasks.put(3, new String[] {
					"xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))",
					LargeInputs.DAY3 });
		} // 3
		{
			days.put(4, new Day4());
			solutionsTask1.put(4, new Integer[] { 18, 2685 });
			solutionsTask2.put(4, new Integer[] { 9, 2048 });
			tasks.put(4, new String[] {
					"MMMSXXMASM\n" +
							"MSAMXMSMSA\n" +
							"AMXSXMAAMM\n" +
							"MSAMASMSMX\n" +
							"XMASAMXAMM\n" +
							"XXAMMXXAMA\n" +
							"SMSMSASXSS\n" +
							"SAXAMASAAA\n" +
							"MAMMMXMMMM\n" +
							"MXMXAXMASX",
					LargeInputs.DAY4 });
		} // 4

	}
}
