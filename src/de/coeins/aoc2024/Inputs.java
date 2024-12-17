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
		{
			days.put(5, new Day5());
			solutionsTask1.put(5, new Integer[] { 143, 5747 });
			solutionsTask2.put(5, new Integer[] { 123, 5502 });
			tasks.put(5, new String[] {
					"47|53\n" +
							"97|13\n" +
							"97|61\n" +
							"97|47\n" +
							"75|29\n" +
							"61|13\n" +
							"75|53\n" +
							"29|13\n" +
							"97|29\n" +
							"53|29\n" +
							"61|53\n" +
							"97|53\n" +
							"61|29\n" +
							"47|13\n" +
							"75|47\n" +
							"97|75\n" +
							"47|61\n" +
							"75|61\n" +
							"47|29\n" +
							"75|13\n" +
							"53|13\n" +
							"\n" +
							"75,47,61,53,29\n" +
							"97,61,53,29,13\n" +
							"75,29,13\n" +
							"75,97,47,61,53\n" +
							"61,13,29\n" +
							"97,13,75,29,47",
					LargeInputs.DAY5 });
		} // 5
		{
			days.put(6, new Day6());
			solutionsTask1.put(6, new Integer[] { 41, 4758 });
			solutionsTask2.put(6, new Integer[] { 6, 1670 });
			tasks.put(6, new String[] {
					"....#.....\n" +
							".........#\n" +
							"..........\n" +
							"..#.......\n" +
							".......#..\n" +
							"..........\n" +
							".#..^.....\n" +
							"........#.\n" +
							"#.........\n" +
							"......#...",
					LargeInputs.DAY6 });
		} // 6
		{
			days.put(7, new Day7());
			solutionsTask1.put(7, new Long[] { 3749L, 1399219271639L });
			solutionsTask2.put(7, new Long[] { 11387L, 275791737999003L });
			tasks.put(7, new String[] {
					"190: 10 19\n" +
							"3267: 81 40 27\n" +
							"83: 17 5\n" +
							"156: 15 6\n" +
							"7290: 6 8 6 15\n" +
							"161011: 16 10 13\n" +
							"192: 17 8 14\n" +
							"21037: 9 7 18 13\n" +
							"292: 11 6 16 20\n",
					LargeInputs.DAY7 });
		} // 7
		{
			days.put(8, new Day8());
			solutionsTask1.put(8, new Integer[] { 14, 259 });
			solutionsTask2.put(8, new Integer[] { 34, 927 });
			tasks.put(8, new String[] {
					"............\n" +
							"........0...\n" +
							".....0......\n" +
							".......0....\n" +
							"....0.......\n" +
							"......A.....\n" +
							"............\n" +
							"............\n" +
							"........A...\n" +
							".........A..\n" +
							"............\n" +
							"............",
					LargeInputs.DAY8 });
		} // 8
		{
			days.put(9, new Day9());
			solutionsTask1.put(9, new Long[] { 1928L, 6310675819476L });
			solutionsTask2.put(9, new Long[] { 2858L, 6335972980679L });
			tasks.put(9, new String[] {
					"2333133121414131402",
					LargeInputs.DAY9 });
		} // 9
		{
			days.put(10, new Day10());
			solutionsTask1.put(10, new Integer[] { 2, 36, 644 });
			solutionsTask2.put(10, new Integer[] { 227, 81, 1366 });
			tasks.put(10, new String[] {
					"012345\n" +
							"123456\n" +
							"234567\n" +
							"345678\n" +
							"416789\n" +
							"567891",
					"89010123\n" +
							"78121874\n" +
							"87430965\n" +
							"96549874\n" +
							"45678903\n" +
							"32019012\n" +
							"01329801\n" +
							"10456732",
					LargeInputs.DAY10 });
		} // 10
		{
			days.put(11, new Day11());
			solutionsTask1.put(11, new Long[] { 55312L, 217443L });
			solutionsTask2.put(11, new Long[] { 65601038650482L, 257246536026785L });
			tasks.put(11, new String[] {
					"125 17",
					LargeInputs.DAY11 });
		} // 11
		{
			days.put(12, new Day12());
			solutionsTask1.put(12, new Integer[] { 140, 692, 1184, 1930, 1415378 });
			solutionsTask2.put(12, new Integer[] { 80, 236, 368, 1206, 862714 });
			tasks.put(12, new String[] {
					"AAAA\n" +
							"BBCD\n" +
							"BBCC\n" +
							"EEEC",
					"EEEEE\n" +
							"EXXXX\n" +
							"EEEEE\n" +
							"EXXXX\n" +
							"EEEEE\n",
					"AAAAAA\n" +
							"AAABBA\n" +
							"AAABBA\n" +
							"ABBAAA\n" +
							"ABBAAA\n" +
							"AAAAAA",
					"RRRRIICCFF\n" +
							"RRRRIICCCF\n" +
							"VVRRRCCFFF\n" +
							"VVRCCCJFFF\n" +
							"VVVVCJJCFE\n" +
							"VVIVCCJJEE\n" +
							"VVIIICJJEE\n" +
							"MIIIIIJJEE\n" +
							"MIIISIJEEE\n" +
							"MMMISSJEEE",
					LargeInputs.DAY12 });
		} // 12
		{
			days.put(13, new Day13());
			solutionsTask1.put(13, new Long[] { 480L, 36954L });
			solutionsTask2.put(13, new Long[] { 875318608908L, 79352015273424L });
			tasks.put(13, new String[] {
					"Button A: X+94, Y+34\n" +
							"Button B: X+22, Y+67\n" +
							"Prize: X=8400, Y=5400\n" +
							"\n" +
							"Button A: X+26, Y+66\n" +
							"Button B: X+67, Y+21\n" +
							"Prize: X=12748, Y=12176\n" +
							"\n" +
							"Button A: X+17, Y+86\n" +
							"Button B: X+84, Y+37\n" +
							"Prize: X=7870, Y=6450\n" +
							"\n" +
							"Button A: X+69, Y+23\n" +
							"Button B: X+27, Y+71\n" +
							"Prize: X=18641, Y=10279",
					LargeInputs.DAY13 });
		} // 13
		{
			days.put(14, new Day14());
			solutionsTask1.put(14, new Integer[] { 12, 226548000 });
			solutionsTask2.put(14, new Integer[] { 0, 7753 });
			tasks.put(14, new String[] {
					"p=0,4 v=3,-3\n" +
							"p=6,3 v=-1,-3\n" +
							"p=10,3 v=-1,2\n" +
							"p=2,0 v=2,-1\n" +
							"p=0,0 v=1,3\n" +
							"p=3,0 v=-2,-2\n" +
							"p=7,6 v=-1,-3\n" +
							"p=3,0 v=-1,-2\n" +
							"p=9,3 v=2,3\n" +
							"p=7,3 v=-1,2\n" +
							"p=2,4 v=2,-3\n" +
							"p=9,5 v=-3,-3",
					LargeInputs.DAY14 });
		} // 14
		{
			days.put(15, new Day15());
			solutionsTask1.put(15, new Integer[] { 2028, 908, 10092, 1552463 });
			solutionsTask2.put(15, new Integer[] { 1751, 618, 9021, 1554058 });
			tasks.put(15, new String[] {
					"########\n" +
							"#..O.O.#\n" +
							"##@.O..#\n" +
							"#...O..#\n" +
							"#.#.O..#\n" +
							"#...O..#\n" +
							"#......#\n" +
							"########\n" +
							"\n" +
							"<^^>>>vv<v>>v<<",
					"#######\n" +
							"#...#.#\n" +
							"#.....#\n" +
							"#..OO@#\n" +
							"#..O..#\n" +
							"#.....#\n" +
							"#######\n" +
							"\n" +
							"<vv<<^^<<^^",
					"##########\n" +
							"#..O..O.O#\n" +
							"#......O.#\n" +
							"#.OO..O.O#\n" +
							"#..O@..O.#\n" +
							"#O#..O...#\n" +
							"#O..O..O.#\n" +
							"#.OO.O.OO#\n" +
							"#....O...#\n" +
							"##########\n" +
							"\n" +
							"<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^\n" +
							"vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v\n" +
							"><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<\n" +
							"<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^\n" +
							"^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><\n" +
							"^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^\n" +
							">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^\n" +
							"<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>\n" +
							"^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>\n" +
							"v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^",
					LargeInputs.DAY15 });
		} // 15
		{
			days.put(16, new Day16());
			solutionsTask1.put(16, new Integer[] { 7036, 11048, 90440 });
			solutionsTask2.put(16, new Integer[] { 45, 64, 479 });
			tasks.put(16, new String[] {
					"###############\n" +
							"#.......#....E#\n" +
							"#.#.###.#.###.#\n" +
							"#.....#.#...#.#\n" +
							"#.###.#####.#.#\n" +
							"#.#.#.......#.#\n" +
							"#.#.#####.###.#\n" +
							"#...........#.#\n" +
							"###.#.#####.#.#\n" +
							"#...#.....#.#.#\n" +
							"#.#.#.###.#.#.#\n" +
							"#.....#...#.#.#\n" +
							"#.###.#.#.#.#.#\n" +
							"#S..#.....#...#\n" +
							"###############",
					"#################\n" +
							"#...#...#...#..E#\n" +
							"#.#.#.#.#.#.#.#.#\n" +
							"#.#.#.#...#...#.#\n" +
							"#.#.#.#.###.#.#.#\n" +
							"#...#.#.#.....#.#\n" +
							"#.#.#.#.#.#####.#\n" +
							"#.#...#.#.#.....#\n" +
							"#.#.#####.#.###.#\n" +
							"#.#.#.......#...#\n" +
							"#.#.###.#####.###\n" +
							"#.#.#...#.....#.#\n" +
							"#.#.#.#####.###.#\n" +
							"#.#.#.........#.#\n" +
							"#.#.#.#########.#\n" +
							"#S#.............#\n" +
							"#################",
					LargeInputs.DAY16 });
		} // 16

	}
}
