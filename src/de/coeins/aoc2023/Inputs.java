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
		} // X

		{
			int day = 6;
			days.put(day, new Day6());
			solutionsTask1.put(day, new Integer[] { 288, 1108800 });
			solutionsTask2.put(day, new Integer[] { 71503, 36919753 });
			tasks.put(day, new String[] {
					"Time:      7  15   30\n" +
							"Distance:  9  40  200",

					LargeInputs.DAY6 });
		} // X


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
