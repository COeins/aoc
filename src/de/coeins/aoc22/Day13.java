package de.coeins.aoc22;

import java.util.ArrayList;
import java.util.List;

class Day13 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		List<List<Packet>> pairs = new ArrayList<>(in.length / 3 + 1);
		int result = 0;
		for (int i = 0; i < in.length / 3 + 1; i++)
			pairs.add(List.of(parsePacket(in[3 * i]), parsePacket(in[3 * i + 1])));
		for (int i = 0; i < pairs.size(); i++) {
			int comp = pairs.get(i).get(0).compareTo(pairs.get(i).get(1));
			//log((i + 1), pairs.get(i).get(0), pairs.get(i).get(1), comp);
			if (comp <= 0)
				result += i + 1;
		}
		return result;
	}

	@Override
	public Integer task2(String[] in) {
		List<Packet> pakets = new ArrayList<>(in.length);
		for (String s : in)
			if (!s.isEmpty())
				pakets.add(parsePacket(s));
		Packet p1 = parsePacket("[[2]]");
		Packet p2 = parsePacket("[[6]]");
		pakets.addAll(List.of(p1, p2));
		pakets.sort(Packet::compareTo);
		int result = 1;
		for (int i = 0; i < pakets.size(); i++)
			if (pakets.get(i).equals(p1) || pakets.get(i).equals(p2))
				result *= (i + 1);
		return result;
	}

	private Packet parsePacket(String line) {
		StringBuilder carry = new StringBuilder();
		List<ComplexPacket> stack = new ArrayList<>();
		Packet result = null;
		for (int i = 0; i < line.length(); i++) {
			switch (line.charAt(i)) {
				case '[': {
					ComplexPacket p = new ComplexPacket();
					if (stack.size() > 0)
						stack.get(stack.size() - 1).append(p);
					else
						result = p;
					stack.add(p);
					break;
				}
				case ']': {
					if (carry.length() > 0) {
						SimplePacket p = new SimplePacket(Integer.parseInt(carry.toString()));
						stack.get(stack.size() - 1).append(p);
						carry.setLength(0);
					}
					stack.remove(stack.size() - 1);
					break;
				}
				case ',': {
					if (carry.length() > 0) {
						SimplePacket p = new SimplePacket(Integer.parseInt(carry.toString()));
						stack.get(stack.size() - 1).append(p);
						carry.setLength(0);
					}
					break;
				}
				default: // some number
					carry.append(line.charAt(i));
					break;

			}
		}
		return result;
	}

	private interface Packet {
		int compareTo(Packet o);
	}

	private class ComplexPacket implements Packet {
		private final List<Packet> subpackets;

		ComplexPacket() {
			subpackets = new ArrayList<>();
		}

		ComplexPacket(Packet in) {
			this();
			append(in);
		}

		void append(Packet in) {
			subpackets.add(in);
		}

		public String toString() {
			StringBuilder s = new StringBuilder("[");
			String comma = "";
			for (Packet p : subpackets) {
				s.append(comma).append(p.toString());
				comma = ",";
			}
			return s.append("]").toString();
		}

		@Override
		public int compareTo(Packet o) {
			ComplexPacket comp = o instanceof ComplexPacket ? (ComplexPacket) o : new ComplexPacket(o);
			for (int i = 0; i < subpackets.size(); i++) {
				if (comp.subpackets.size() < i + 1)
					return 1;
				int nextEl = subpackets.get(i).compareTo(comp.subpackets.get(i));
				if (nextEl != 0)
					return nextEl;
			}
			return subpackets.size() - comp.subpackets.size();
		}
	}

	private class SimplePacket implements Packet {
		private final int value;

		SimplePacket(int value) {
			this.value = value;
		}

		public String toString() {
			return Integer.toString(value);
		}

		@Override
		public int compareTo(Packet o) {
			if (o instanceof SimplePacket)
				return value - ((SimplePacket) o).value;
			else
				return new ComplexPacket(this).compareTo(o);
		}
	}
}
