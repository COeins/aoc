package de.coeins.aoc22;

import java.util.HashMap;
import java.util.Map;

public class Inputs {
	public static Map<Integer, Day> days = new HashMap<>();
	public static Map<Integer, String[]> tasks = new HashMap<>();
	public static Map<Integer, Object[]> solutionsTask1 = new HashMap<>();
	public static Map<Integer, Object[]> solutionsTask2 = new HashMap<>();

	static {
		{
			days.put(1, new Day1());
			solutionsTask1.put(1, new Integer[] { 24000, 75622 });
			solutionsTask2.put(1, new Integer[] { 45000, 213159 });
			tasks.put(1, new String[] {
					"1000\n" +
							"2000\n" +
							"3000\n" +
							"\n" +
							"4000\n" +
							"\n" +
							"5000\n" +
							"6000\n" +
							"\n" +
							"7000\n" +
							"8000\n" +
							"9000\n" +
							"\n" +
							"10000",

					LargeInputs.DAY1 });
		} // 1
		{
			days.put(2, new Day2());
			solutionsTask1.put(2, new Integer[] { 15, 15572 });
			solutionsTask2.put(2, new Integer[] { 12, 16098 });
			tasks.put(2, new String[] {
					"A Y\n" +
							"B X\n" +
							"C Z",

					LargeInputs.DAY2 });
		} // 2
		{
			days.put(3, new Day3());
			solutionsTask1.put(3, new Integer[] { 157, 7597 });
			solutionsTask2.put(3, new Integer[] { 70, 2607 });
			tasks.put(3, new String[] {
					"vJrwpWtwJgWrhcsFMMfFFhFp\n" +
							"jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n" +
							"PmmdzqPrVvPwwTWBwg\n" +
							"wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n" +
							"ttgJtRGJQctTZtZT\n" +
							"CrZsJsPPZsGzwwsLwLmpwMDw",
					LargeInputs.DAY3
			});
		} // 3
		{
			days.put(4, new Day4());
			solutionsTask1.put(4, new Integer[] { 2, 509 });
			solutionsTask2.put(4, new Integer[] { 4, 870 });
			tasks.put(4, new String[] {
					"2-4,6-8\n" +
							"2-3,4-5\n" +
							"5-7,7-9\n" +
							"2-8,3-7\n" +
							"6-6,4-6\n" +
							"2-6,4-8",
					LargeInputs.DAY4
			});
		} // 4
		{
			days.put(5, new Day5());
			solutionsTask1.put(5, new String[] { "CMZ", "VCTFTJQCG" });
			solutionsTask2.put(5, new String[] { "MCD", "GCFGLDNJZ" });
			tasks.put(5, new String[] {
					"    [D]    \n" +
							"[N] [C]    \n" +
							"[Z] [M] [P]\n" +
							" 1   2   3 \n" +
							"\n" +
							"move 1 from 2 to 1\n" +
							"move 3 from 1 to 3\n" +
							"move 2 from 2 to 1\n" +
							"move 1 from 1 to 2",
					LargeInputs.DAY5
			});
		} // 5
		{
			days.put(6, new Day6());
			solutionsTask1.put(6, new Integer[] { 7, 5, 6, 10, 11, 1134 });
			solutionsTask2.put(6, new Integer[] { 19, 23, 23, 29, 26, 2263 });
			tasks.put(6, new String[] {
					"mjqjpqmgbljsphdztnvjfqwrcgsmlb",
					"bvwbjplbgvbhsrlpgdmjqwftvncz",
					"nppdvjthqldpwncqszvftbrmjlhg",
					"nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
					"zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw",
					LargeInputs.DAY6
			});
		} // 6
		{
			days.put(7, new Day7());
			solutionsTask1.put(7, new Integer[] { 95437, 1989474 });
			solutionsTask2.put(7, new Integer[] { 24933642, 1111607 });
			tasks.put(7, new String[] {
					"$ cd /\n" +
							"$ ls\n" +
							"dir a\n" +
							"14848514 b.txt\n" +
							"8504156 c.dat\n" +
							"dir d\n" +
							"$ cd a\n" +
							"$ ls\n" +
							"dir e\n" +
							"29116 f\n" +
							"2557 g\n" +
							"62596 h.lst\n" +
							"$ cd e\n" +
							"$ ls\n" +
							"584 i\n" +
							"$ cd ..\n" +
							"$ cd ..\n" +
							"$ cd d\n" +
							"$ ls\n" +
							"4060174 j\n" +
							"8033020 d.log\n" +
							"5626152 d.ext\n" +
							"7214296 k",
					LargeInputs.DAY7
			});
		} // 7
		{
			days.put(8, new Day8());
			solutionsTask1.put(8, new Integer[] { 21, 1827 });
			solutionsTask2.put(8, new Integer[] { 8, 335580 });
			tasks.put(8, new String[] {
					"30373\n" +
							"25512\n" +
							"65332\n" +
							"33549\n" +
							"35390",
					LargeInputs.DAY8
			});
		} // 8
		{
			days.put(9, new Day9());
			solutionsTask1.put(9, new Integer[] { 13, 88, 5907 });
			solutionsTask2.put(9, new Integer[] { 1, 36, -1 });
			tasks.put(9, new String[] {
					"R 4\n" +
							"U 4\n" +
							"L 3\n" +
							"D 1\n" +
							"R 4\n" +
							"D 1\n" +
							"L 5\n" +
							"R 2",
					"R 5\n" +
							"U 8\n" +
							"L 8\n" +
							"D 3\n" +
							"R 17\n" +
							"D 10\n" +
							"L 25\n" +
							"U 20",
					LargeInputs.DAY9
			});
		} // 9
		{
			days.put(10, new Day10());
			solutionsTask1.put(10, new Integer[] { 13140, -1 });
			solutionsTask2.put(10, new Integer[] { -1, -1 });
			tasks.put(10, new String[] {
					"addx 15\n" +
							"addx -11\n" +
							"addx 6\n" +
							"addx -3\n" +
							"addx 5\n" +
							"addx -1\n" +
							"addx -8\n" +
							"addx 13\n" +
							"addx 4\n" +
							"noop\n" +
							"addx -1\n" +
							"addx 5\n" +
							"addx -1\n" +
							"addx 5\n" +
							"addx -1\n" +
							"addx 5\n" +
							"addx -1\n" +
							"addx 5\n" +
							"addx -1\n" +
							"addx -35\n" +
							"addx 1\n" +
							"addx 24\n" +
							"addx -19\n" +
							"addx 1\n" +
							"addx 16\n" +
							"addx -11\n" +
							"noop\n" +
							"noop\n" +
							"addx 21\n" +
							"addx -15\n" +
							"noop\n" +
							"noop\n" +
							"addx -3\n" +
							"addx 9\n" +
							"addx 1\n" +
							"addx -3\n" +
							"addx 8\n" +
							"addx 1\n" +
							"addx 5\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"addx -36\n" +
							"noop\n" +
							"addx 1\n" +
							"addx 7\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"addx 2\n" +
							"addx 6\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"addx 1\n" +
							"noop\n" +
							"noop\n" +
							"addx 7\n" +
							"addx 1\n" +
							"noop\n" +
							"addx -13\n" +
							"addx 13\n" +
							"addx 7\n" +
							"noop\n" +
							"addx 1\n" +
							"addx -33\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"addx 2\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"addx 8\n" +
							"noop\n" +
							"addx -1\n" +
							"addx 2\n" +
							"addx 1\n" +
							"noop\n" +
							"addx 17\n" +
							"addx -9\n" +
							"addx 1\n" +
							"addx 1\n" +
							"addx -3\n" +
							"addx 11\n" +
							"noop\n" +
							"noop\n" +
							"addx 1\n" +
							"noop\n" +
							"addx 1\n" +
							"noop\n" +
							"noop\n" +
							"addx -13\n" +
							"addx -19\n" +
							"addx 1\n" +
							"addx 3\n" +
							"addx 26\n" +
							"addx -30\n" +
							"addx 12\n" +
							"addx -1\n" +
							"addx 3\n" +
							"addx 1\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"addx -9\n" +
							"addx 18\n" +
							"addx 1\n" +
							"addx 2\n" +
							"noop\n" +
							"noop\n" +
							"addx 9\n" +
							"noop\n" +
							"noop\n" +
							"noop\n" +
							"addx -1\n" +
							"addx 2\n" +
							"addx -37\n" +
							"addx 1\n" +
							"addx 3\n" +
							"noop\n" +
							"addx 15\n" +
							"addx -21\n" +
							"addx 22\n" +
							"addx -6\n" +
							"addx 1\n" +
							"noop\n" +
							"addx 2\n" +
							"addx 1\n" +
							"noop\n" +
							"addx -10\n" +
							"noop\n" +
							"noop\n" +
							"addx 20\n" +
							"addx 1\n" +
							"addx 2\n" +
							"addx 2\n" +
							"addx -6\n" +
							"addx -11\n" +
							"noop\n" +
							"noop\n" +
							"noop",
					LargeInputs.DAY10
			});
		} // 10
		{
			days.put(0, new Day1());
			solutionsTask1.put(0, new Integer[] { 13140, 13820 });
			solutionsTask2.put(0, new Integer[] { -1, -1 });
			tasks.put(0, new String[] {
					"",
					""
			});
		} // template

	}
}
