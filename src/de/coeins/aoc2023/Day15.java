package de.coeins.aoc2023;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Day15 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		int sum = 0;
		String[] parts = in[0].split(",");
		for (String p : parts)
			sum += hashString(p);

		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		String[] parts = in[0].split(",");
		Map<Integer, List<LensCommand>> boxes = new HashMap<>();

		nextCommand:
		for (String p : parts) {
			LensCommand c = LensCommand.build(p);
			if (!boxes.containsKey(c.hash))
				boxes.put(c.hash, new LinkedList<>());
			List<LensCommand> box = boxes.get(c.hash);
			if (c.op == '=') {
				for (int i = 0; i < box.size(); i++)
					if (box.get(i).label.equals(c.label)) {
						box.remove(i);
						box.add(i, c);
						log(p, boxes);
						continue nextCommand;
					}
				box.add(c);
			} else
				for (int i = 0; i < box.size(); i++)
					if (box.get(i).label.equals(c.label))
						box.remove(i--);
			log(p, boxes);
		}

		int sum = 0;

		for (Map.Entry<Integer, List<LensCommand>> box : boxes.entrySet()) {
			for (int slot = 0; slot < box.getValue().size(); slot++) {
				int fp = (box.getKey() + 1) * (slot + 1) * box.getValue().get(slot).number;
				log(box.getValue().get(slot), fp);
				sum += fp;
			}
		}
		return sum;
	}

	static int hashString(String s) {
		int h = 0;
		for (int i = 0; i < s.length(); i++) {
			h += s.charAt(i);
			h = (17 * h) % 256;
		}
		return h;
	}

	record LensCommand(String label, char op, int number, int hash) {
		static LensCommand build(String s) {
			String[] split = new String[2];
			split[0] = "";
			split[1] = "";
			char op = 0;
			int segment = 0;
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == '=' || c == '-') {
					segment = 1;
					op = c;
				} else
					split[segment] += c;
			}
			return new LensCommand(split[0], op, split[1].length() == 0 ? 0 : Integer.parseInt(split[1]), hashString(split[0]));
		}

		@Override
		public String toString() {
			return label + " " + number;
		}
	}
}
