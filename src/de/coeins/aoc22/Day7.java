package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Day7 implements Day<Integer> {

	private static final String DIVIDER = "/";

	@Override
	public Integer task1(String[] in) {
		Iterator<String> inIt = Arrays.stream(in).iterator();
		if (!inIt.next().equals("$ cd /"))
			throw new RuntimeException("Not starting at root");
		Dir dir = parse(inIt, "/");
		return dir.calcRecursive(new SumCalculator(), i -> (i <= 100000));
	}

	@Override
	public Integer task2(String[] in) {
		Iterator<String> inIt = Arrays.stream(in).iterator();
		if (!inIt.next().equals("$ cd /"))
			throw new RuntimeException("Not starting at root");
		Dir dir = parse(inIt, "/");
		int spaceNeeded = dir.size() - 40000000;
		log("Space required: ", spaceNeeded);
		return dir.calcRecursive(new MinCalculator(), i -> (i >= spaceNeeded));
	}

	private Dir parse(Iterator<String> in, String name) {
		Dir dir = new Dir(name);
		while (in.hasNext()) {
			String[] split = in.next().split(" ");
			if (split[1].equals("cd")) {
				if (split[2].equals(".."))
					return dir;
				else
					dir.dirs.add(parse(in, name + split[2] + DIVIDER));
			} else if (!split[1].equals("ls")) {
				if (!split[0].equals("dir"))
					dir.files.add(new File(Integer.parseInt(split[0]), split[1]));

			}
		}
		return dir;
	}

	private class File {
		final int size;
		final String name;

		private File(int size, String name) {
			this.size = size;
			this.name = name;
		}
	}

	private class Dir {
		final String path;
		final List<File> files;
		final List<Dir> dirs;
		int totalSize = -1;

		private Dir(String path) {
			this.files = new ArrayList<>();
			this.dirs = new ArrayList<>();
			this.path = path;
		}

		public int size() {
			if (totalSize < 0) {
				int newsize = 0;
				for (File f : files) {
					newsize += f.size;
				}
				for (Dir d : dirs) {
					newsize += d.size();
				}
				totalSize = newsize;
			}
			return totalSize;
		}

		public void ls() {
			log(path);
			for (File f : files) {
				log("-", f.name, f.size);
			}
			for (Dir d : dirs) {
				d.ls();
			}
		}

		public int calcRecursive(Calculator calc, Comparator comp) {
			if (comp.compare(size()))
				calc.nextValue(size());

			for (Dir d : dirs) {
				d.calcRecursive(calc, comp);
			}
			return calc.result();
		}
	}

	private interface Comparator {
		boolean compare(int i);
	}

	private abstract class Calculator {
		int value = 0;

		abstract void nextValue(int i);

		public int result() {
			return value;
		}
	}

	private class SumCalculator extends Calculator {
		@Override
		public void nextValue(int i) {
			value += i;
		}
	}

	private class MinCalculator extends Calculator {
		MinCalculator() {
			value = Integer.MAX_VALUE;
		}

		@Override
		public void nextValue(int i) {
			if (i < value)
				value = i;
		}
	}
}
