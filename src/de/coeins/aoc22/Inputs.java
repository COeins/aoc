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
			solutionsTask2.put(9, new Integer[] { 1, 36, 2303 });
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
			solutionsTask1.put(10, new Integer[] { 13140, 13820 });
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
			days.put(11, new Day11());
			solutionsTask1.put(11, new Long[] { 10605L, 66802L });
			solutionsTask2.put(11, new Long[] { 2713310158L, 21800916620L });
			tasks.put(11, new String[] {
					"Monkey 0:\n" +
							"  Starting items: 79, 98\n" +
							"  Operation: new = old * 19\n" +
							"  Test: divisible by 23\n" +
							"    If true: throw to monkey 2\n" +
							"    If false: throw to monkey 3\n" +
							"\n" +
							"Monkey 1:\n" +
							"  Starting items: 54, 65, 75, 74\n" +
							"  Operation: new = old + 6\n" +
							"  Test: divisible by 19\n" +
							"    If true: throw to monkey 2\n" +
							"    If false: throw to monkey 0\n" +
							"\n" +
							"Monkey 2:\n" +
							"  Starting items: 79, 60, 97\n" +
							"  Operation: new = old * old\n" +
							"  Test: divisible by 13\n" +
							"    If true: throw to monkey 1\n" +
							"    If false: throw to monkey 3\n" +
							"\n" +
							"Monkey 3:\n" +
							"  Starting items: 74\n" +
							"  Operation: new = old + 3\n" +
							"  Test: divisible by 17\n" +
							"    If true: throw to monkey 0\n" +
							"    If false: throw to monkey 1",
					LargeInputs.DAY11
			});
		} // 11
		{
			days.put(12, new Day12());
			solutionsTask1.put(12, new Integer[] { 31, 350 });
			solutionsTask2.put(12, new Integer[] { 29, 349 });
			tasks.put(12, new String[] {
					"Sabqponm\n" +
							"abcryxxl\n" +
							"accszExk\n" +
							"acctuvwj\n" +
							"abdefghi",
					LargeInputs.DAY12
			});
		} // 12
		{
			days.put(13, new Day13());
			solutionsTask1.put(13, new Integer[] { 13, 6272 });
			solutionsTask2.put(13, new Integer[] { 140, 22288 });
			tasks.put(13, new String[] {
					"[1,1,3,1,1]\n" +
							"[1,1,5,1,1]\n" +
							"\n" +
							"[[1],[2,3,4]]\n" +
							"[[1],4]\n" +
							"\n" +
							"[9]\n" +
							"[[8,7,6]]\n" +
							"\n" +
							"[[4,4],4,4]\n" +
							"[[4,4],4,4,4]\n" +
							"\n" +
							"[7,7,7,7]\n" +
							"[7,7,7]\n" +
							"\n" +
							"[]\n" +
							"[3]\n" +
							"\n" +
							"[[[]]]\n" +
							"[[]]\n" +
							"\n" +
							"[1,[2,[3,[4,[5,6,7]]]],8,9]\n" +
							"[1,[2,[3,[4,[5,6,0]]]],8,9]\n",
					LargeInputs.DAY13
			});
		} // 13
		{
			days.put(14, new Day14());
			solutionsTask1.put(14, new Integer[] { 24, 698 });
			solutionsTask2.put(14, new Integer[] { 93, 28594 });
			tasks.put(14, new String[] {
					"498,4 -> 498,6 -> 496,6\n" +
							"503,4 -> 502,4 -> 502,9 -> 494,9",
					LargeInputs.DAY14
			});
		} // 14
		{
			days.put(15, new Day15());
			solutionsTask1.put(15, new Long[] { 26L, 4876693L });
			solutionsTask2.put(15, new Long[] { 56000011L, 11645454855041L });
			tasks.put(15, new String[] {
					"Sensor at x=2, y=18: closest beacon is at x=-2, y=15\n" +
							"Sensor at x=9, y=16: closest beacon is at x=10, y=16\n" +
							"Sensor at x=13, y=2: closest beacon is at x=15, y=3\n" +
							"Sensor at x=12, y=14: closest beacon is at x=10, y=16\n" +
							"Sensor at x=10, y=20: closest beacon is at x=10, y=16\n" +
							"Sensor at x=14, y=17: closest beacon is at x=10, y=16\n" +
							"Sensor at x=8, y=7: closest beacon is at x=2, y=10\n" +
							"Sensor at x=2, y=0: closest beacon is at x=2, y=10\n" +
							"Sensor at x=0, y=11: closest beacon is at x=2, y=10\n" +
							"Sensor at x=20, y=14: closest beacon is at x=25, y=17\n" +
							"Sensor at x=17, y=20: closest beacon is at x=21, y=22\n" +
							"Sensor at x=16, y=7: closest beacon is at x=15, y=3\n" +
							"Sensor at x=14, y=3: closest beacon is at x=15, y=3\n" +
							"Sensor at x=20, y=1: closest beacon is at x=15, y=3",
					LargeInputs.DAY15
			});
		} // 15
		{
			days.put(16, new Day16());
			solutionsTask1.put(16, new Integer[] { 1651, 2059 });
			solutionsTask2.put(16, new Integer[] { 1707, 2790 });
			tasks.put(16, new String[] {
					"Valve AA has flow rate=0; tunnels lead to valves DD, II, BB\n" +
							"Valve BB has flow rate=13; tunnels lead to valves CC, AA\n" +
							"Valve CC has flow rate=2; tunnels lead to valves DD, BB\n" +
							"Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE\n" +
							"Valve EE has flow rate=3; tunnels lead to valves FF, DD\n" +
							"Valve FF has flow rate=0; tunnels lead to valves EE, GG\n" +
							"Valve GG has flow rate=0; tunnels lead to valves FF, HH\n" +
							"Valve HH has flow rate=22; tunnel leads to valve GG\n" +
							"Valve II has flow rate=0; tunnels lead to valves AA, JJ\n" +
							"Valve JJ has flow rate=21; tunnel leads to valve II",
					LargeInputs.DAY16
			});
		} // 16
		{
			days.put(17, new Day17());
			solutionsTask1.put(17, new Long[] { 3068L, 3081L });
			solutionsTask2.put(17, new Long[] { 1514285714288L, 1524637681145L });
			tasks.put(17, new String[] {
					">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>",
					LargeInputs.DAY17
			});
		} // 17
		{
			days.put(18, new Day18());
			solutionsTask1.put(18, new Integer[] { 10, 64, 4332 });
			solutionsTask2.put(18, new Integer[] { 10, 58, 2524 });
			tasks.put(18, new String[] {
					"1,1,1\n2,1,1,",
					"2,2,2\n" +
							"1,2,2\n" +
							"3,2,2\n" +
							"2,1,2\n" +
							"2,3,2\n" +
							"2,2,1\n" +
							"2,2,3\n" +
							"2,2,4\n" +
							"2,2,6\n" +
							"1,2,5\n" +
							"3,2,5\n" +
							"2,1,5\n" +
							"2,3,5",
					LargeInputs.DAY18
			});
		} // 18
		{
			days.put(0, new Day1());
			solutionsTask1.put(0, new Integer[] { -1, -1 });
			solutionsTask2.put(0, new Integer[] { -1, -1 });
			tasks.put(0, new String[] {
					"",
					LargeInputs.DAY1
			});
		} // template

	}
}
