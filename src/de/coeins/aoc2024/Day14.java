package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.coeins.aoc2023.MathUtils;

class Day14 implements de.coeins.aoc2023.Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int gridSizeX = in.length > 12 ? 101 : 11;
		int gridSizeY = in.length > 12 ? 103 : 7;
		List<Robot> robots = parse(in);
		int[][] quadrants = new int[2][2];
		List<Robot> newRobots = step(robots, 100, gridSizeX, gridSizeY);
		int midX = gridSizeX / 2;
		int midY = gridSizeY / 2;
		for (Robot r : newRobots) {
			if (r.x == midX || r.y == midY)
				continue;
			quadrants[r.x < midX ? 0 : 1][r.y < midY ? 0 : 1]++;
		}
		logRobots(newRobots, gridSizeX, gridSizeY);
		return quadrants[0][0] * quadrants[0][1] * quadrants[1][0] * quadrants[1][1];
	}

	@Override
	public Integer task2(String[] in) {
		int gridSizeX = in.length > 12 ? 101 : 11;
		int gridSizeY = in.length > 12 ? 103 : 7;
		List<Robot> robots = parse(in);
		for (int i = 1; i <= MathUtils.lcm(gridSizeX, gridSizeY); i++) {
			robots = step(robots, 1, gridSizeX, gridSizeY);
			int row = findLongestRow(robots, gridSizeX, gridSizeY);
			if (row > 7) {
				log("Looks promising:");
				logRobots(robots, gridSizeX, gridSizeY);
				return i;
			}
		}
		log("Nothing interesting found :(");
		return 0;
	}

	private List<Robot> parse(String[] in) {
		return Arrays.stream(in).map(s -> {
			String[] split = s.split("[=, ]");
			return new Robot(Integer.parseInt(split[1]), Integer.parseInt(split[2]),
					Integer.parseInt(split[4]), Integer.parseInt(split[5]));
		}).toList();
	}

	private List<Robot> step(List<Robot> robots, int rounds, int maxX, int maxY) {
		List<Robot> newRobots = new ArrayList<>(robots.size());
		for (Robot r : robots) {
			int newX = MathUtils.mod(r.x + rounds * r.dx, maxX);
			int newY = MathUtils.mod(r.y + rounds * r.dy, maxY);
			newRobots.add(new Robot(newX, newY, r.dx, r.dy));
		}
		return newRobots;
	}

	private void logRobots(List<Robot> robots, int maxX, int maxY) {
		StringBuilder s = new StringBuilder();
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				int x1 = x;
				int y1 = y;
				long count = robots.stream().filter(r -> r.x == x1 && r.y == y1).count();
				if (count < 1)
					s.append(".");
				else if (count > 9)
					s.append("#");
				else
					s.append(count);
			}
			s.append("\n");
		}
		log(s);
	}

	private int findLongestRow(List<Robot> robots, int maxX, int maxY) {
		int maxSteak = 0;
		for (int y = 0; y < maxY; y++) {
			int currentStreak = 0;
			for (int x = 0; x < maxX; x++) {
				int x1 = x;
				int y1 = y;
				long count = robots.stream().filter(r -> r.x == x1 && r.y == y1).count();
				if (count == 1)
					currentStreak++;
				else {
					if (currentStreak > maxSteak)
						maxSteak = currentStreak;
					currentStreak = 0;
				}
			}
			if (currentStreak > maxSteak)
				maxSteak = currentStreak;
		}
		return maxSteak;
	}

	record Robot(int x, int y, int dx, int dy) {
	}
}
