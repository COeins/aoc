package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.List;

class Day13 implements de.coeins.aoc2023.Day<Long> {
	@Override
	public Long task1(String[] in) {
		List<Machine> machines = parse(in, 0);
		return calculate(machines);
	}

	@Override
	public Long task2(String[] in) {
		List<Machine> machines = parse(in, 10000000000000L);
		return calculate(machines);
	}

	private double round(double i) {
		return (double) Math.round(i * 1000) / 1000;
	}

	private List<Machine> parse(String[] in, long offset) {
		List<Machine> m = new ArrayList<>(in.length / 4);
		for (int l = 0; l < in.length; l += 4) {
			String[] a = in[l].split("[,+]");
			String[] b = in[l + 1].split("[,+]");
			String[] t = in[l + 2].split("[=,]");
			m.add(new Machine(Integer.parseInt(a[1]), Integer.parseInt(a[3]), Integer.parseInt(b[1]), Integer.parseInt(b[3]),
					Integer.parseInt(t[1]) + offset, Integer.parseInt(t[3]) + offset));
		}
		return m;
	}

	private long calculate(List<Machine> machines) {
		long solution = 0;
		for (Machine m : machines) {
			// tx = a * dx_a   +   b * dx_b
			// ty = a * dy_a   +   b * dy_b

			// tx   -   b * dx_b = a * dx_a
			// tx / dx_a   -   b * dx_b / dx_a = a

			// ty = (tx / dx_a   -   b * dx_b / dx_a) * dy_a   +   b * dy_b
			// ty = tx * dy_a / dx_a   -   b * dy_a * dx_b / dx_a   +   b * dy_b
			// ty   -   tx * dy_a / dx_a   =  b * dy_b   -   b * dy_a * dx_b / dx_a
			// ty   -   tx * dy_a / dx_a   =  b * ( dy_b * dx_a   -  dy_a * dx_b  ) / dx_a
			// ( ty * dx_a  -   tx * dy_a ) / ( dy_b * dx_a   -  dy_a * dx_b )  =  b

			double b = round((double) (m.ty * m.dx_a - m.tx * m.dy_a) / (m.dy_b * m.dx_a - m.dy_a * m.dx_b));
			double a = round((double) m.tx / m.dx_a - b * m.dx_b / m.dx_a);

			if (a % 1 == 0 && b % 1 == 0) {
				log("Machine", m, "has solution with steps: a=", a, "b=", b);
				solution += (long) a * 3 + (long) b;
			} else
				log("Machine", m, "has only non-integer solution: a=", a, "b=", b);
		}
		return solution;
	}

	record Machine(int dx_a, int dy_a, int dx_b, int dy_b, long tx, long ty) {
	}
}
