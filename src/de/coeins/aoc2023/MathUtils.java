package de.coeins.aoc2023;

import java.util.List;

class MathUtils {

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static long lcm(long a, long b) {
		return a / gcd(a, b) * b;
	}

	static long lcmAll(List<Long> inputs) {
		if (inputs.size() < 2)
			return inputs.get(0);
		long res = inputs.get(0);
		for (int i = 1; i < inputs.size(); i++)
			res = lcm(res, inputs.get(i));
		return res;
	}

}
