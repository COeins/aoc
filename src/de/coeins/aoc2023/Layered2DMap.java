package de.coeins.aoc2023;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class Layered2DMap<E extends Layered2DMap.MapElement> {

	final E[][] base;
	final Map<Point, E> overrideLayer;
	final int[][] layer;

	Layered2DMap(E[][] base, int[][] layer) {
		this.base = base;
		this.overrideLayer = new HashMap<>();
		this.layer = layer;
	}

	public static <T extends Enum<T> & MapElement> Layered2DMap<T> parseCharacters(String[] in, int startLine, Class<T> elementType) {
		T[] elements = elementType.getEnumConstants();
		@SuppressWarnings("unchecked")
		T[][] base = (T[][]) Array.newInstance(elementType, in.length - startLine, in[startLine].length());
		for (int x = 0; x < in.length - startLine; x++) {
			nectcol:
			for (int y = 0; y < in[startLine].length(); y++) {
				char c = in[x - startLine].charAt(y);
				for (T e : elements)
					if (e.getParseChar() == c) {
						base[x][y] = e;
						continue nectcol;
					}
				throw new RuntimeException("Invalid character at " + x + ", " + y + ": " + c);
			}
		}
		return new Layered2DMap<>(base, new int[base.length][base[0].length]);
	}

	public static Layered2DMap<Digits> parseDigits(String[] in, int startLine) {
		return parseCharacters(in, startLine, Digits.class);
	}

	public int height() {
		return base.length;
	}

	public int width() {
		return base[0].length;
	}

	public boolean validPoint(Point pos) {
		return pos.x >= 0 && pos.x < height()
				&& pos.y >= 0 && pos.y < width();
	}

	public E getBase(Point pos) {
		return (E) base[pos.x][pos.y];
	}

	public E getBase(Point pos, E defaultPoint) {
		if (validPoint(pos))
			return getBase(pos);
		else
			return defaultPoint;
	}

	public List<Point> findInBase(E search) {
		List<Point> found = new ArrayList<>();
		for (int x = 0; x < height(); x++)
			for (int y = 0; y < width(); y++) {
				Point p = new Point(x, y);
				if (getBase(p) == search)
					found.add(p);
			}
		return found;
	}

	public int getLayer(Point pos) {
		return layer[pos.x][pos.y];
	}

	public int getLayer(Point pos, int defaultOverlay) {
		if (validPoint(pos))
			return getLayer(pos);
		else
			return defaultOverlay;
	}

	public void setLayer(Point pos, int i) {
		layer[pos.x][pos.y] = i;
	}

	public void resetLayer() {
		for (int x = 0; x < height(); x++)
			for (int y = 0; y < width(); y++)
				layer[x][y] = 0;
	}

	public void fillLayer(Point pos, int newValue) {
		if (!validPoint(pos))
			return;
		int startValue = getLayer(pos);
		if (startValue != newValue)
			fillLayer(pos, startValue, newValue);
	}

	public void fillLayer(Point startPos, int startValue, int newValue) {
		new TaskList<Point, Boolean>((tl, pos) -> {
			if (!validPoint(pos) || getLayer(pos) != startValue)
				return Optional.of(false);

			setLayer(pos, newValue);
			for (Direction d : Direction.values())
				tl.recurse(pos.applyDirection(d));

			return Optional.of(true);
		}).run(startPos);
	}

	public int iterateMap(CalcFunction func) {
		int result = 0;
		for (int x = 0; x < height(); x++)
			for (int y = 0; y < width(); y++) {
				Point p = new Point(x, y);
				result = func.calc(p, getBase(p), getLayer(p), result);
			}
		return result;
	}

	public int sumLayer() {
		return iterateMap((_p, _b, layer, prev) -> layer + prev);
	}

	@Override
	public String toString() {
		return toString(true);
	}

	public String toString(boolean includeOverlay) {
		if (includeOverlay)
			return toString((_p, base, overlay) -> {
				if (overlay > 10 || overlay < 0)
					return '#';
				else if (overlay > 0)
					return Integer.toString(overlay).charAt(0);
				else if (base != null)
					return base.getOutputChar();
				else
					return 'X';
			});
		else
			return toString();
	}

	public String toString(MergeFunction func) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < height(); x++) {
			int l = 0;
			for (int y = 0; y < width(); y++) {
				Point p = new Point(x, y);
				char c = func.merge(p, getBase(p), getLayer(p));
				if (c > 0) {
					sb.append(c);
					l++;
				}
			}
			if (l > 0)
				sb.append('\n');
		}
		return sb.toString();
	}

	public interface MapElement {
		char getParseChar();

		char getOutputChar();
	}

	public interface MergeFunction {
		char merge(Point pos, MapElement base, int overlay);
	}

	public interface CalcFunction {
		int calc(Point pos, MapElement base, int overlay, int previous);
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
