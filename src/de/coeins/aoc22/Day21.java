package de.coeins.aoc22;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Day21 implements Day<Long> {
	private final static Map<Character, Operation> OPERATIONS = new HashMap<>();
	private final static String ROOT_ID = "root";
	private final static String HUMN_ID = "humn";

	@Override
	public Long task1(String[] in) {
		Map<String, ProxyMonkey> monkeys = parse(in);
		return monkeys.get(ROOT_ID).getNumber().get();
	}

	@Override
	public Long task2(String[] in) {
		Map<String, ProxyMonkey> monkeys = parse(in);
		CollectingMonkey human = new CollectingMonkey();
		monkeys.get(HUMN_ID).setTarget(human);
		CalculatingMonkey root = (CalculatingMonkey) monkeys.get(ROOT_ID).target;
		root.op = OPERATIONS.get('=');
		root.pushResult(0);
		return human.number;
	}

	private Map<String, ProxyMonkey> parse(String[] in) {
		Map<String, ProxyMonkey> monkeys = new HashMap<>();
		for (String l : in) {
			String[] split = l.split("[: ]+");
			ProxyMonkey proxy = monkeys.get(split[0]);
			if (proxy == null) {
				proxy = new ProxyMonkey(split[0]);
				monkeys.put(split[0], proxy);
			}

			if (split.length == 2) {
				proxy.setTarget(new LiteralMonkey(split[0], Integer.parseInt(split[1])));
			} else {
				ProxyMonkey m1 = monkeys.get(split[1]);
				if (m1 == null) {
					m1 = new ProxyMonkey(split[1]);
					monkeys.put(split[1], m1);
				}
				ProxyMonkey m2 = monkeys.get(split[3]);
				if (m2 == null) {
					m2 = new ProxyMonkey(split[3]);
					monkeys.put(split[3], m2);
				}
				proxy.setTarget(new CalculatingMonkey(monkeys.get(split[1]), monkeys.get(split[3]), OPERATIONS.get(split[2].charAt(0))));
			}
		}
		return monkeys;
	}

	private interface Monkey {
		Optional<Long> getNumber();

		void pushResult(long result);
	}

	private class ProxyMonkey implements Monkey {
		private final String name;
		private Monkey target;

		public ProxyMonkey(String name) {
			this.name = name;
		}

		private void setTarget(Monkey target) {
			this.target = target;
		}

		public String getName() {
			return name;
		}

		@Override
		public Optional<Long> getNumber() {
			return target.getNumber();
		}

		@Override
		public void pushResult(long result) {
			target.pushResult(result);
		}

		public String toString() {
			return name + ": " + (target == null ? "NULL" : target.toString());
		}
	}

	private class LiteralMonkey implements Monkey {
		private final long number;

		public LiteralMonkey(String name, long number) {
			this.number = number;
		}

		@Override
		public Optional<Long> getNumber() {
			return Optional.of(number);
		}

		@Override
		public void pushResult(long r) {
			throw new RuntimeException("No Pushing literals");
		}

		public String toString() {
			return Long.toString(number);
		}
	}

	private class CollectingMonkey implements Monkey {
		private Long number = null;

		@Override
		public Optional<Long> getNumber() {
			if (number == null)
				return Optional.empty();
			else
				return Optional.of(number);

		}

		@Override
		public void pushResult(long r) {
			this.number = r;
		}
	}

	private class CalculatingMonkey implements Monkey {
		private final ProxyMonkey[] others;
		private Operation op;
		private Long result = null;

		public CalculatingMonkey(ProxyMonkey m1, ProxyMonkey m2, Operation op) {
			this.others = new ProxyMonkey[] { m1, m2 };
			this.op = op;
		}

		@Override
		public Optional<Long> getNumber() {
			if (result == null) {
				Optional<Long> m1opt = others[0].getNumber();
				Optional<Long> m2opt = others[1].getNumber();
				if (m1opt.isPresent() && m2opt.isPresent()) {
					result = op.calculate(m1opt.get(), m2opt.get());
				} else
					return Optional.empty();
			}
			return Optional.of(result);
		}

		@Override
		public void pushResult(long r) {
			Optional<Long> m1opt = others[0].getNumber();
			Optional<Long> m2opt = others[1].getNumber();
			if (m1opt.isEmpty() && m2opt.isPresent())
				others[0].pushResult(op.getLeft(m2opt.get(), r));
			else if (m1opt.isPresent() && m2opt.isEmpty())
				others[1].pushResult(op.getRight(m1opt.get(), r));
			else
				throw new RuntimeException("Unable to push");
		}

		public String toString() {
			return (others[0] == null ? "NULL" : others[0].getName()) + " " + op.name() + " "
					+ (others[1] == null ? "NULL" : others[1].getName());
		}
	}

	private interface Op {
		long calc(long a, long b);
	}

	private static class Operation {
		private final Op result, left, right;
		private final String name;

		public Operation(Op result, Op left, Op right, String name) {
			this.result = result;
			this.left = left;
			this.right = right;
			this.name = name;
		}

		long calculate(long a, long b) {
			return result.calc(a, b);
		}

		long getLeft(long b, long r) {
			return left.calc(b, r);
		}

		long getRight(long a, long r) {
			return right.calc(a, r);
		}

		String name() {
			return this.name;
		}
	}

	static {
		OPERATIONS.put('+', new Operation((a, b) -> a + b, (b, r) -> r - b, (a, r) -> r - a, "+"));
		OPERATIONS.put('-', new Operation((a, b) -> a - b, (b, r) -> r + b, (a, r) -> a - r, "-"));
		OPERATIONS.put('*', new Operation((a, b) -> a * b, (b, r) -> r / b, (a, r) -> r / a, "*"));
		OPERATIONS.put('/', new Operation((a, b) -> a / b, (b, r) -> r * b, (a, r) -> a / r, "/"));
		OPERATIONS.put('=', new Operation((a, b) -> a == b ? 1 : 0, (b, r) -> b, (a, r) -> a, "=="));
	}

}
