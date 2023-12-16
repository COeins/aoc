package de.coeins.aoc2023;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

class Layered2DMap<E extends Layered2DMap.MapElement> {
	final E[][] baseLayer;
	final Map<Point, E> overrideLayer;
	final int[][] overlay;

	Layered2DMap(E[][] baseLayer, int[][] overlay) {
		this.baseLayer = baseLayer;
		this.overrideLayer = new HashMap<>();
		this.overlay = overlay;
	}

	public static <T extends Enum<T> & MapElement> Layered2DMap<T> parseCharacters(String[] in, int startLine, Class<T> elementType) {
		T[] elements = elementType.getEnumConstants();
		@SuppressWarnings("unchecked")
		T[][] base = (T[][]) Array.newInstance(elementType, in.length - startLine, in[startLine].length());
		for (int line = 0; line < in.length - startLine; line++) {
			nectcol:
			for (int col = 0; col < in.length; col++) {
				char c = in[line - startLine].charAt(col);
				for (T e : elements)
					if (e.getParseChar() == c) {
						base[line][col] = e;
						continue nectcol;
					}
				throw new RuntimeException("Invalid character at " + line + ", " + col + ": " + c);
			}
		}
		return new Layered2DMap<>(base, new int[base.length][base[0].length]);
	}

	public boolean validPoint(Point pos) {
		return pos.x >= 0 && pos.x < baseLayer.length
				&& pos.y >= 0 && pos.y < baseLayer[0].length;
	}

	public E getBasePoint(Point pos) {
		return (E) baseLayer[pos.x][pos.y];
	}

	public E getBasePoint(Point pos, E defaultPoint) {
		if (validPoint(pos))
			return getBasePoint(pos);
		else
			return defaultPoint;
	}

	public int getOverlay(Point pos) {
		return overlay[pos.x][pos.y];
	}

	public int getOverlay(Point pos, int defaultOverlay) {
		if (validPoint(pos))
			return getOverlay(pos);
		else
			return defaultOverlay;
	}

	public void setLayer(Point pos, int i) {
		overlay[pos.x][pos.y] = i;
	}

	public void resetLayer() {
		for (int i = 0; i < height(); i++)
			for (int j = 0; j < width(); j++)
				overlay[i][j] = 0;
	}

	public int sumLayer() {
		int sum = 0;
		for (int i = 0; i < height(); i++)
			for (int j = 0; j < width(); j++)
				sum += overlay[i][j];
		return sum;
	}

	public int height() {
		return baseLayer.length;
	}

	public int width() {
		return baseLayer[0].length;
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public String toString(boolean includeOverlay) {
		if (includeOverlay)
			return toString((base, overlay) -> {
				if (overlay > 10 || overlay < 0)
					return '#';
				else if (overlay > 0)
					return Integer.toString(overlay).charAt(0);
				else
					return base.getOutputChar();
			});
		else
			return toString();
	}

	public String toString(MergeFunction func) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height(); i++) {
			for (int j = 0; j < width(); j++) {
				Point p = new Point(i, j);
				sb.append(func.merge(getBasePoint(p), getOverlay(p)));
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public interface MapElement {
		char getParseChar();

		char getOutputChar();
	}

	public interface MergeFunction {
		char merge(MapElement base, int overlay);
	}

	public enum Direction {
		N(-1, 0, null),
		S(1, 0, N),
		E(0, 1, null),
		W(0, -1, E),

		NE(-1, 1, null),
		SW(1, -1, NE),
		SE(1, 1, null),
		NW(-1, -1, SE);

		final int dx;
		final int dy;
		Direction inverse;

		Direction(int dx, int dy, Direction inv) {
			this.dx = dx;
			this.dy = dy;
			this.inverse = inv;
			if (inv != null)
				inv.inverse = this;
		}
	}

	public record Point(int x, int y) {
		Point applyDirection(Direction d) {
			return new Point(x + d.dx, y + d.dy);
		}
	}

}
