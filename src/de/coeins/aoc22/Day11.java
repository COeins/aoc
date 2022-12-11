package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Day11 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		List<Monkey> monkeys = parseMonkeys(in, 3);
		Integer[] itemThrows = executeRounds(20, monkeys);
		log((Object[]) itemThrows);
		Arrays.sort(itemThrows);
		return (long) itemThrows[itemThrows.length - 1] * itemThrows[itemThrows.length - 2];
	}

	@Override
	public Long task2(String[] in) {
		List<Monkey> monkeys = parseMonkeys(in, 1);
		Integer[] itemThrows = executeRounds(10000, monkeys);
		log((Object[]) itemThrows);
		Arrays.sort(itemThrows);
		return (long) itemThrows[itemThrows.length - 1] * itemThrows[itemThrows.length - 2];
	}

	private List<Monkey> parseMonkeys(String[] in, int divisor) {
		if (in.length % 7 != 6)
			throw new RuntimeException("Invalid Input");
		List<Monkey> monkeys = new ArrayList<>();
		int globalModulo = 1;
		for (int i = 0; i < (in.length + 1) / 7; i++) {
			Operation op;
			if (in[7 * i + 2].charAt(23) == '+') {
				if (in[7 * i + 2].charAt(25) == 'o')
					op = x -> ((2L * x) / divisor);
				else {
					int y = Integer.parseInt(in[7 * i + 2].substring(25));
					op = x -> (((long) x + y) / divisor);
				}
			} else { // *
				if (in[7 * i + 2].charAt(25) == 'o')
					op = x -> (((long) x * x) / divisor);
				else {
					int y = Integer.parseInt(in[7 * i + 2].substring(25));
					op = x -> (((long) x * y) / divisor);
				}
			}
			int modulo = Integer.parseInt(in[7 * i + 3].substring(21));
			globalModulo *= modulo; // maybe implement GCD in the future here
			int trueMonkey = Integer.parseInt(in[7 * i + 4].substring(29));
			int falseMonkey = Integer.parseInt(in[7 * i + 5].substring(30));
			Monkey m = new Monkey(i, op, modulo, trueMonkey, falseMonkey);
			for (String item : in[7 * i + 1].substring(18).split(", "))
				m.catchItem(Integer.parseInt(item));
			monkeys.add(m);
		}
		for (Monkey m : monkeys)
			m.globalModulo = globalModulo;
		return monkeys;
	}

	private Integer[] executeRounds(int rounds, List<Monkey> monkeys) {
		Integer[] itemThrows = new Integer[monkeys.size()];
		Arrays.fill(itemThrows, 0);
		for (int r = 0; r < rounds; r++) {
			for (Monkey m : monkeys) {
				itemThrows[m.id] += m.items.size();
				m.executeTurn(monkeys);
			}
		}
		return itemThrows;
	}

	private interface Operation {
		long apply(int old);
	}

	private class Monkey {
		final List<Integer> items;
		final int id;
		final Operation op;
		int globalModulo;
		final int testModulo;
		final int monkeyTrue;
		final int monkeyFalse;

		private Monkey(int id, Operation op, int testModulo, int monkeyTrue, int monkeyFalse) {
			this.id = id;
			this.op = op;
			this.testModulo = testModulo;
			this.monkeyTrue = monkeyTrue;
			this.monkeyFalse = monkeyFalse;
			items = new ArrayList<>();
		}

		private void catchItem(Integer value) {
			items.add(value);
		}

		private void executeTurn(List<Monkey> monkeys) {
			for (Integer i : items) {
				int newvalue = (int) (op.apply(i) % globalModulo);
				monkeys.get(newvalue % testModulo == 0 ? monkeyTrue : monkeyFalse).catchItem(newvalue);
			}
			items.clear();
		}

		public String toString() {
			StringBuilder sb = new StringBuilder("Monkey ").append(id).append(": ");
			for (Integer i : items)
				sb.append(i).append(" ");
			return sb.append("\n").toString();
		}
	}
}
