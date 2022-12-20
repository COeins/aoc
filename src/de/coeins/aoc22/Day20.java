package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.List;

class Day20 implements Day<Long> {
	private final static long MAGICAL_KEY = 811589153;

	@Override
	public Long task1(String[] in) {
		Number[] numberList = parse(in);
		Number zero = mixList(numberList, 1, 1);
		//logNumbers(numberList[0]);
		return (long) zero.getAfter(1000 % numberList.length).id
				+ zero.getAfter(2000 % numberList.length).id
				+ zero.getAfter(3000 % numberList.length).id;
	}

	@Override
	public Long task2(String[] in) {
		Number[] numberList = parse(in);
		Number zero = mixList(numberList, 10, MAGICAL_KEY);
		return MAGICAL_KEY * (zero.getAfter(1000 % numberList.length).id
				+ zero.getAfter(2000 % numberList.length).id
				+ zero.getAfter(3000 % numberList.length).id);
	}

	private Number mixList(Number[] list, int times, long key) {
		Number zero = null;
		for (int i = 0; i < list.length * times; i++) {
			Number candidate = list[i % list.length];
			if (candidate.id == 0)
				zero = candidate;
			int steps = (int) ((key * candidate.id) % (list.length - 1));
			Number insert = candidate.prev;
			candidate.remove();
			insert.insertAfter(steps + (steps < 0 ? list.length - 1 : 0), candidate);
		}
		return zero;
	}

	private void logNumbers(Number n) {
		List<Number> collect = new ArrayList<>();
		collect.add(n);
		for (Number c = n.next; c != n; c = c.next)
			collect.add(c);
		log(collect);
	}

	private Number[] parse(String[] in) {
		Number[] numbers = new Number[in.length];
		numbers[0] = new Number(Integer.parseInt(in[0]), null);
		for (int i = 1; i < in.length; i++)
			numbers[i] = new Number(Integer.parseInt(in[i]), numbers[i - 1]);
		numbers[0].prev = numbers[in.length - 1];
		numbers[in.length - 1].next = numbers[0];
		return numbers;
	}

	private class Number {
		final int id;
		Number prev, next;

		private Number(int id, Number prev) {
			this.id = id;
			this.prev = prev;
			if (prev != null)
				prev.next = this;
		}

		private Number getAfter(int i) {
			if (i > 0) {
				return next.getAfter(i - 1);
			}
			return this;
		}

		private void insertAfter(int i, Number ins) {
			if (i > 0) {
				next.insertAfter(i - 1, ins);
				return;
			}
			ins.prev = this;
			ins.next = next;
			next.prev = ins;
			this.next = ins;
		}

		private void remove() {
			next.prev = prev;
			prev.next = next;
			this.prev = null;
			this.next = null;
		}

		public String toString() {
			return Integer.toString(id);
		}
	}
}
