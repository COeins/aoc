package de.coeins.aoc22;

public interface Day <T> {

	T task1(String[] in);

	T task2(String[] in);

	default void log(Object... whatever) {
		StringBuilder sb = new StringBuilder();
		for (Object in : whatever)
			sb.append(in).append(" ");
		System.out.println(sb);
	}
}
