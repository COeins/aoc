package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.List;

class Day11 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		return universeSim(in, 1);
	}

	@Override
	public Long task2(String[] in) {
		return universeSim(in, 1000000 - 1);
	}

	long universeSim(String in[], int expansionRate) {
		List<long[]> galaxies = new ArrayList<>();
		List<Long> rows = new ArrayList<>();
		List<Long> cols = new ArrayList<>();
		long r_min = in.length;
		long r_max = 0;

		long c_min = in[0].length();
		long c_max = 0;

		for (int r = 0; r < in.length; r++)
			for (int c = 0; c < in[r].length(); c++)
				if (in[r].charAt(c) == '#') {
					long[] gal = new long[] { r, c };
					galaxies.add(gal);
					rows.add((long) r);
					cols.add((long) c);
					if (r < r_min)
						r_min = r;
					if (r > r_max)
						r_max = r;
					if (c < c_min)
						c_min = c;
					if (c > c_max)
						c_max = c;
				}

		log("Original galaxies");
		for (long[] g : galaxies)
			log(g);

		for (long r = r_max - 1; r > r_min; r--)
			if (!rows.contains(r))
				for (long[] g : galaxies)
					if (g[0] > r)
						g[0] += expansionRate;

		for (long c = c_max - 1; c > c_min; c--)
			if (!cols.contains(c))
				for (long[] g : galaxies)
					if (g[1] > c)
						g[1] += expansionRate;

		log("Expanded galaxies");
		for (long[] g : galaxies)
			log(g);

		long sum = 0;
		for (int i = 0; i < galaxies.size() - 1; i++)
			for (int j = i + 1; j < galaxies.size(); j++) {
				sum += Math.abs(galaxies.get(i)[0] - galaxies.get(j)[0])
						+ Math.abs(galaxies.get(i)[1] - galaxies.get(j)[1]);
			}
		return sum;
	}
}
