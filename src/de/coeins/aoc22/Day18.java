package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.List;

class Day18 implements Day<Integer> {
	private final static int EMPTY = 0;
	private final static int OUTER = 1;
	private final static int LAVA = 2;

	@Override
	public Integer task1(String[] in) {
		List<int[]> cubes = parseCubes(in);
		int[][][] grid = prepateGrid(cubes);
		return countEmptySides(cubes, grid, EMPTY);
	}

	@Override
	public Integer task2(String[] in) {
		List<int[]> cubes = parseCubes(in);
		int[][][] grid = prepateGrid(cubes);
		floodFill(grid, 0, 0, 0, OUTER);
		return countEmptySides(cubes, grid, OUTER);
	}

	private List<int[]> parseCubes(String[] in) {
		List<int[]> cubes = new ArrayList<>(in.length);
		for (String l : in) {
			String[] split = l.split(",");
			int[] cube = new int[] { Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]) };
			cubes.add(cube);
		}
		return cubes;
	}

	private int[][][] prepateGrid(List<int[]> cubes) {
		int[] max = new int[3];
		for (int[] cube : cubes)
			max = new int[] { Math.max(max[0], cube[0]), Math.max(max[1], cube[1]), Math.max(max[2], cube[2]) };

		int[][][] grid = new int[max[0] + 3][max[1] + 3][max[2] + 3];
		for (int[] cube : cubes)
			grid[cube[0] + 1][cube[1] + 1][cube[2] + 1] = LAVA;

		return grid;
	}

	private void floodFill(int[][][] grid, int x, int y, int z, int value) {
		if (x < 0 || y < 0 || z < 0 || x >= grid.length || y >= grid[0].length || z >= grid[0][0].length)
			return;
		if (grid[x][y][z] != EMPTY)
			return;
		grid[x][y][z] = value;
		floodFill(grid, x - 1, y, z, value);
		floodFill(grid, x + 1, y, z, value);
		floodFill(grid, x, y - 1, z, value);
		floodFill(grid, x, y + 1, z, value);
		floodFill(grid, x, y, z - 1, value);
		floodFill(grid, x, y, z + 1, value);
	}

	int countEmptySides(List<int[]> cubes, int[][][] grid, int value) {
		int sides = 0;
		for (int[] cube : cubes) {
			sides += grid[cube[0] + 0][cube[1] + 1][cube[2] + 1] == value ? 1 : 0;
			sides += grid[cube[0] + 2][cube[1] + 1][cube[2] + 1] == value ? 1 : 0;
			sides += grid[cube[0] + 1][cube[1] + 0][cube[2] + 1] == value ? 1 : 0;
			sides += grid[cube[0] + 1][cube[1] + 2][cube[2] + 1] == value ? 1 : 0;
			sides += grid[cube[0] + 1][cube[1] + 1][cube[2] + 0] == value ? 1 : 0;
			sides += grid[cube[0] + 1][cube[1] + 1][cube[2] + 2] == value ? 1 : 0;
		}
		return sides;
	}
}
