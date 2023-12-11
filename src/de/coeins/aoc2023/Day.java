package de.coeins.aoc2023;

import java.util.Arrays;

public interface Day<T> {

	T task1(String[] in);

	T task2(String[] in);

	default void log(Object... whatever) {
		StringBuilder sb = new StringBuilder();
		for (Object in : whatever)
			sb.append(toString(in)).append(" ");
		Main.log(sb.toString());
	}

	private String toString(Object o) {
		if (o instanceof int[] a) {
			StringBuilder sb = new StringBuilder("[");
			for (int a1 : a)
				sb.append(a1).append(", ");
			if (sb.length() > 2)
				sb.setLength(sb.length() - 2);
			return sb.append("]").toString();
		} else if (o instanceof long[] a) {
			StringBuilder sb = new StringBuilder("[");
			for (long a1 : a)
				sb.append(a1).append(", ");
			if (sb.length() > 2)
				sb.setLength(sb.length() - 2);
			return sb.append("]").toString();
		} else if (o instanceof Object[] a) {
			StringBuilder sb = new StringBuilder("[");
			for (Object a1 : a)
				sb.append(toString(a1)).append(", ");
			if (sb.length() > 2)
				sb.setLength(sb.length() - 2);
			return sb.append("]").toString();
		} else if (o instanceof Iterable i) {
			StringBuilder sb = new StringBuilder("<");
			for (Object i1 : i)
				sb.append(toString(i1)).append(", ");
			if (sb.length() > 2)
				sb.setLength(sb.length() - 2);
			return sb.append(">").toString();
		} else
			return o.toString();
	}
}
