package de.coeins.aoc2023;

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
			solutionsTask1.put(1, new Integer[] { 142, 209, 54940 });
			solutionsTask2.put(1, new Integer[] { 142, 281, 54208 });
			tasks.put(1, new String[] {
					"1abc2\n" +
							"pqr3stu8vwx\n" +
							"a1b2c3d4e5f\n" +
							"treb7uchet",

					"two1nine\n" +
							"eightwothree\n" +
							"abcone2threexyz\n" +
							"xtwone3four\n" +
							"4nineeightseven2\n" +
							"zoneight234\n" +
							"7pqrstsixteen",

					LargeInputs.DAY1 });
		} // 1

		{
			days.put(2, new Day2());
			solutionsTask1.put(2, new Integer[] { 8, 2600 });
			solutionsTask2.put(2, new Integer[] { 2286, 86036 });
			tasks.put(2, new String[] {
					"Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
							"Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
							"Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
							"Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
							"Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",

					LargeInputs.DAY2 });
		} // 2

		{
			int day = 3;
			days.put(day, new Day3());
			solutionsTask1.put(day, new Integer[] { 4361, 551094 });
			solutionsTask2.put(day, new Integer[] { 467835, 80179647 });
			tasks.put(day, new String[] {
					"467..114..\n" +
							"...*......\n" +
							"..35..633.\n" +
							"......#...\n" +
							"617*......\n" +
							".....+.58.\n" +
							"..592.....\n" +
							"......755.\n" +
							"...$.*....\n" +
							".664.598..",

					LargeInputs.DAY3 });
		} // 3

		{
			int day = 4;
			days.put(day, new Day4());
			solutionsTask1.put(day, new Integer[] { 13, 28750 });
			solutionsTask2.put(day, new Integer[] { 30, 10212704 });
			tasks.put(day, new String[] {
					"Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
							"Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
							"Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
							"Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
							"Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
							"Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",

					LargeInputs.DAY4 });
		} // 4

		{
			int day = 5;
			days.put(day, new Day5());
			solutionsTask1.put(day, new Long[] { 35L, 424490994L });
			solutionsTask2.put(day, new Long[] { 46L, 15290096L });
			tasks.put(day, new String[] {
					"seeds: 79 14 55 13\n" +
							"\n" +
							"seed-to-soil map:\n" +
							"50 98 2\n" +
							"52 50 48\n" +
							"\n" +
							"soil-to-fertilizer map:\n" +
							"0 15 37\n" +
							"37 52 2\n" +
							"39 0 15\n" +
							"\n" +
							"fertilizer-to-water map:\n" +
							"49 53 8\n" +
							"0 11 42\n" +
							"42 0 7\n" +
							"57 7 4\n" +
							"\n" +
							"water-to-light map:\n" +
							"88 18 7\n" +
							"18 25 70\n" +
							"\n" +
							"light-to-temperature map:\n" +
							"45 77 23\n" +
							"81 45 19\n" +
							"68 64 13\n" +
							"\n" +
							"temperature-to-humidity map:\n" +
							"0 69 1\n" +
							"1 0 69\n" +
							"\n" +
							"humidity-to-location map:\n" +
							"60 56 37\n" +
							"56 93 4",

					LargeInputs.DAY5 });
		} // 5

		{
			int day = 6;
			days.put(day, new Day6());
			solutionsTask1.put(day, new Integer[] { 288, 1108800 });
			solutionsTask2.put(day, new Integer[] { 71503, 36919753 });
			tasks.put(day, new String[] {
					"Time:      7  15   30\n" +
							"Distance:  9  40  200",

					LargeInputs.DAY6 });
		} // 6

		{
			int day = 7;
			days.put(day, new Day7());
			solutionsTask1.put(day, new Integer[] { 6440, 246163188 });
			solutionsTask2.put(day, new Integer[] { 5905, 245794069 });
			tasks.put(day, new String[] {
					"32T3K 765\n" +
							"T55J5 684\n" +
							"KK677 28\n" +
							"KTJJT 220\n" +
							"QQQJA 483\n",

					LargeInputs.DAY7 });
		} // 7

		{
			int day = 8;
			days.put(day, new Day8());
			solutionsTask1.put(day, new Long[] { 2L, 6L, -1L, 19199L });
			solutionsTask2.put(day, new Long[] { 2L, 6L, 6L, 13663968099527L });
			tasks.put(day, new String[] {
					"RL\n" +
							"\n" +
							"AAA = (BBB, CCC)\n" +
							"BBB = (DDD, EEE)\n" +
							"CCC = (ZZZ, GGG)\n" +
							"DDD = (DDD, DDD)\n" +
							"EEE = (EEE, EEE)\n" +
							"GGG = (GGG, GGG)\n" +
							"ZZZ = (ZZZ, ZZZ)",
					"LLR\n" +
							"\n" +
							"AAA = (BBB, BBB)\n" +
							"BBB = (AAA, ZZZ)\n" +
							"ZZZ = (ZZZ, ZZZ)",
					"LR\n" +
							"\n" +
							"11A = (11B, XXX)\n" +
							"11B = (XXX, 11Z)\n" +
							"11Z = (11B, XXX)\n" +
							"22A = (22B, XXX)\n" +
							"22B = (22C, 22C)\n" +
							"22C = (22Z, 22Z)\n" +
							"22Z = (22B, 22B)\n" +
							"XXX = (XXX, XXX)",

					LargeInputs.DAY8 });
		} // 8

		{
			int day = 9;
			days.put(day, new Day9());
			solutionsTask1.put(day, new Integer[] { 114, 1884768153 });
			solutionsTask2.put(day, new Integer[] { 2, 1031 });
			tasks.put(day, new String[] {
					"0 3 6 9 12 15\n" +
							"1 3 6 10 15 21\n" +
							"10 13 16 21 30 45",

					LargeInputs.DAY9 });
		} // 9

		{
			int day = 10;
			days.put(day, new Day10());
			solutionsTask1.put(day, new Integer[] { 4, 8, 23, 70, 80, 6800 });
			solutionsTask2.put(day, new Integer[] { 1, 1, 4, 8, 10, 483 });
			tasks.put(day, new String[] {
					".....\n" +
							".S-7.\n" +
							".|.|.\n" +
							".L-J.\n" +
							".....",
					"..F7.\n" +
							".FJ|.\n" +
							"SJ.L7\n" +
							"|F--J\n" +
							"LJ...",
					"...........\n" +
							".S-------7.\n" +
							".|F-----7|.\n" +
							".||.....||.\n" +
							".||.....||.\n" +
							".|L-7.F-J|.\n" +
							".|..|.|..|.\n" +
							".L--J.L--J.\n" +
							"...........",
					".F----7F7F7F7F-7....\n" +
							".|F--7||||||||FJ....\n" +
							".||.FJ||||||||L7....\n" +
							"FJL7L7LJLJ||LJ.L-7..\n" +
							"L--J.L7...LJS7F-7L7.\n" +
							"....F-J..F7FJ|L7L7L7\n" +
							"....L7.F7||L7|.L7L7|\n" +
							".....|FJLJ|FJ|F7|.LJ\n" +
							"....FJL-7.||.||||...\n" +
							"....L---J.LJ.LJLJ...",
					"FF7FSF7F7F7F7F7F---7\n" +
							"L|LJ||||||||||||F--J\n" +
							"FL-7LJLJ||||||LJL-77\n" +
							"F--JF--7||LJLJ7F7FJ-\n" +
							"L---JF-JLJ.||-FJLJJ7\n" +
							"|F|F-JF---7F7-L7L|7|\n" +
							"|FFJF7L7F-JF7|JL---7\n" +
							"7-L-JL7||F7|L7F-7F7|\n" +
							"L.L7LFJ|||||FJL7||LJ\n" +
							"L7JLJL-JLJLJL--JLJ.L",
					LargeInputs.DAY10 });
		} // 10

		{
			int day = 11;
			days.put(day, new Day11());
			solutionsTask1.put(day, new Long[] { 374L, 10313550L });
			solutionsTask2.put(day, new Long[] { 82000210L, 611998089572L });
			tasks.put(day, new String[] {
					"...#......\n" +
							".......#..\n" +
							"#.........\n" +
							"..........\n" +
							"......#...\n" +
							".#........\n" +
							".........#\n" +
							"..........\n" +
							".......#..\n" +
							"#...#.....",

					LargeInputs.DAY11 });
		} // 11

		{
			int day = 12;
			days.put(day, new Day12());
			solutionsTask1.put(day, new Long[] { 6L, 21L, 6981L });
			solutionsTask2.put(day, new Long[] { 6L, 525152L, 4546215031609L });
			tasks.put(day, new String[] {
					"#.#.### 1,1,3\n" +
							".#...#....###. 1,1,3\n" +
							".#.###.#.###### 1,3,1,6\n" +
							"####.#...#... 4,1,1\n" +
							"#....######..#####. 1,6,5\n" +
							".###.##....# 3,2,1",

					"???.### 1,1,3\n" +
							".??..??...?##. 1,1,3\n" +
							"?#?#?#?#?#?#?#? 1,3,1,6\n" +
							"????.#...#... 4,1,1\n" +
							"????.######..#####. 1,6,5\n" +
							"?###???????? 3,2,1",

					LargeInputs.DAY12 });
		} // 12

		{
			int day = 13;
			days.put(day, new Day13());
			solutionsTask1.put(day, new Integer[] { 405, 35538 });
			solutionsTask2.put(day, new Integer[] { 400, -1 });
			tasks.put(day, new String[] {
					"#.##..##.\n" +
							"..#.##.#.\n" +
							"##......#\n" +
							"##......#\n" +
							"..#.##.#.\n" +
							"..##..##.\n" +
							"#.#.##.#.\n" +
							"\n" +
							"#...##..#\n" +
							"#....#..#\n" +
							"..##..###\n" +
							"#####.##.\n" +
							"#####.##.\n" +
							"..##..###\n" +
							"#....#..#",

					LargeInputs.DAY13 });
		} // 13

		{
			int day = 0;
			days.put(day, new Day1());
			solutionsTask1.put(day, new Integer[] { -1, -1 });
			solutionsTask2.put(day, new Integer[] { -1, -1 });
			tasks.put(day, new String[] {
					"XXX",

					LargeInputs.DAY25 });
		} // X

	}
}
