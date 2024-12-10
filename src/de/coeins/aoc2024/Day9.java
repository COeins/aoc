package de.coeins.aoc2024;

import java.util.ArrayList;
import java.util.List;

class Day9 implements de.coeins.aoc2023.Day<Long> {
	@Override
	public Long task1(String[] in) {
		List<Block> blocks = parse(in[0]);

		log(printList(blocks));
		int lastMoved = blocks.size();
		int lastId = -1;
		int lastRemain = 0;
		int gapStart;
		int gapLen;
		for (int i = 0; i < lastMoved; i++) {
			Block a = blocks.get(i);
			gapStart = a.start() + a.len();
			if (i + 1 >= blocks.size()) {
				if (lastRemain > 0)
					blocks.add(new Block(gapStart, lastRemain, lastId));
				break;
			}
			Block b = blocks.get(i + 1);
			gapLen = b.start() - gapStart;
			while (gapLen > 0) {
				if (lastRemain == 0) {
					lastMoved--;
					Block l = blocks.remove(lastMoved);
					lastId = l.id();
					lastRemain = l.len();
				}
				Block n = new Block(gapStart, Math.min(lastRemain, gapLen), lastId);
				blocks.add(i + 1, n);
				i++;
				lastMoved++;
				gapStart += n.len();
				gapLen -= n.len();
				lastRemain -= n.len();
				if (i >= lastMoved - 1)
					break;
			}
		}
		log(printList(blocks));
		return sum(blocks);
	}

	@Override
	public Long task2(String[] in) {
		List<Block> blocks = parse(in[0]);
		log(printList(blocks));
		int maxId = Integer.MAX_VALUE;
		int maxSize = 9;
		nextBlock:
		for (int i = blocks.size() - 1; i >= 0; i--) {
			Block l = blocks.get(i);
			if (l.id() >= maxId || l.len() > maxSize) {
				// log(l, "skipped");
				continue;
			}
			maxId = l.id();
			for (int j = 0; j < i; j++) {
				Block a = blocks.get(j);
				Block b = blocks.get(j + 1);
				int gapStart = a.start() + a.len();
				if (b.start() - gapStart >= l.len()) {
					blocks.remove(i);
					blocks.add(j + 1, new Block(gapStart, l.len(), l.id()));
					// log(l, "moved to", gapStart);
					i++;
					continue nextBlock;
				}
			}
			// log(l, "could not move");
			maxSize = l.len() - 1;
			if (maxSize == 0)
				break;
		}

		log(printList(blocks));
		return sum(blocks);
	}

	private List<Block> parse(String in) {
		List<Block> blocks = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < in.length(); i += 2) {
			int size = (byte) in.charAt(i) - 48;
			blocks.add(new Block(count, size, i / 2));
			count += size;
			if (in.length() > i + 1)
				count += (byte) in.charAt(i + 1) - 48;
		}
		return blocks;
	}

	private Long sum(List<Block> blocks) {
		long sum = 0;
		for (Block b : blocks) {
			long s = (long) b.id() * ((long) b.start() * b.len() + (long) b.len() * (b.len() - 1) / 2);
			sum += s;
		}
		return sum;
	}

	private String printList(List<Block> blocks) {
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		for (Block b : blocks) {
			while (pos < b.start()) {
				sb.append(".");
				pos++;
			}
			while (pos < b.start() + b.len()) {
				sb.append(b.id() % 10);
				pos++;
			}
		}
		return sb.toString();
	}

	record Block(int start, int len, int id) {
	}
}
