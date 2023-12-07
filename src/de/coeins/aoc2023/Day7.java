package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

class Day7 implements Day<Integer> {
	@Override
	public Integer task1(String[] in) {
		List<Hand> hands = parseHands(in, false);
		Collections.sort(hands);
		log(hands);
		int sum = 0;
		for (int i = 0; i < hands.size(); i++) {
			log("Hand " + hands.get(i) + " rank " + (i + 1));
			sum += hands.get(i).bet * (i + 1);
		}
		return sum;
	}

	@Override
	public Integer task2(String[] in) {
		List<Hand> hands = parseHands(in, true);
		Collections.sort(hands);
		log(hands);
		int sum = 0;
		for (int i = 0; i < hands.size(); i++) {
			log("Hand " + hands.get(i) + " rank " + (i + 1));
			sum += hands.get(i).bet * (i + 1);
		}
		return sum;
	}

	private List<Hand> parseHands(String[] in, boolean useJoker) {
		List<Hand> hands = new ArrayList<>(in.length);
		for (String l : in) {
			List<Card> cards = new ArrayList<>(5);
			for (int i = 0; i < 5; i++) {
				String c = l.substring(i, i + 1);
				cards.add(Card.valueOf((useJoker && c.equals("J") ? "j" : "c") + c));
			}

			hands.add(new Hand(cards, determineType(cards), Integer.parseInt(l.split(" ")[1])));
		}
		return hands;
	}

	private HandType determineType(List<Card> cards) {
		Map<Card, Integer> count = new EnumMap<>(Card.class);
		for (Card c : cards)
			count.put(c, count.getOrDefault(c, 0) + 1);
		int jokers = count.getOrDefault(Card.jJ, 0);
		count.put(Card.jJ, 0);
		List<Integer> list = new ArrayList<>(count.values());
		Collections.sort(list, Collections.reverseOrder());
		if (list.get(0) + jokers == 5)
			return HandType.five;
		else if (list.get(0) + jokers == 4)
			return HandType.four;
		else if (list.get(0) + jokers == 3 && list.get(1) == 2)
			return HandType.full;
		else if (list.get(0) + jokers == 3)
			return HandType.tree;
		else if (list.get(0) + jokers == 2 && list.get(1) == 2)
			return HandType.twop;
		else if (list.get(0) + jokers == 2)
			return HandType.pair;
		else
			return HandType.high;
	}

	static class Hand implements Comparable<Hand> {
		final List<Card> cards;
		final HandType type;
		final int bet;

		public Hand(List<Card> cards, HandType type, int bet) {
			this.cards = cards;
			this.type = type;
			this.bet = bet;
		}

		@Override
		public int compareTo(Hand other) {
			if (this.type != other.type)
				return this.type.ordinal() - other.type.ordinal();
			for (int i = 0; i < 5; i++)
				if (this.cards.get(i) != other.cards.get(i))
					return this.cards.get(i).ordinal() - other.cards.get(i).ordinal();
			return 0;
		}

		@Override
		public String toString() {
			return cards.toString() + " (" + type.name() + ", " + bet + ")";
		}

	}

	enum Card {
		jJ, c2, c3, c4, c5, c6, c7, c8, c9, cT, cJ, cQ, cK, cA
	}

	enum HandType {
		high, pair, twop, tree, full, four, five
	}
}
