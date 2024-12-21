package de.coeins.aoc2023;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Layered2DMap<E extends Layered2DMap.MapElement> {
	public static List<Direction> CARDINALS = List.of(Direction.N, Direction.E, Direction.S, Direction.W);
	public static List<Direction> DIAGONALS = List.of(Direction.NE, Direction.SE, Direction.SW, Direction.NW);

	final E[][] base;
	final Map<Point, E> overrideLayer;
	final List<int[][]> layers;

	public Layered2DMap(E[][] base) {
		this.base = base;
		this.overrideLayer = new HashMap<>();
		this.layers = new ArrayList<>();
	}

	public static <T extends Enum<T> & MapElement> Layered2DMap<T> parseCharacters(String[] in, int startLine, Class<T> elementType) {
		T[] elements = elementType.getEnumConstants();
		int endLine = in.length;
		for (int l = startLine; l < endLine; l++)
			if (in[l].isEmpty()) {
				endLine = l;
				break;
			}
		@SuppressWarnings("unchecked")
		T[][] base = (T[][]) Array.newInstance(elementType, endLine - startLine, in[startLine].length());
		for (int x = 0; x < endLine - startLine; x++) {
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
		return new Layered2DMap<>(base);
	}

	public static Layered2DMap<Digits> parseDigits(String[] in, int startLine) {
		return parseCharacters(in, startLine, Digits.class);
	}

	public static Layered2DMap<AlphaNum> parseAlphaNum(String[] in, int startLine) {
		return parseCharacters(in, startLine, AlphaNum.class);
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

	private void ensureLayer(int layer) {
		while (layer >= layers.size())
			layers.add(new int[height()][width()]);
	}

	public int getLayer(Point pos) {
		return getLayer(0, pos);
	}

	public int getLayer(int layer, Point pos) {
		ensureLayer(layer);
		return layers.get(layer)[pos.x][pos.y];
	}

	public int getLayer(Point pos, int defaultValue) {
		return getLayer(0, pos, defaultValue);
	}

	private int[] getAllLayers(Point p) {
		int[] pointLayers = new int[layers.size()];
		for (int l = 0; l < layers.size(); l++)
			pointLayers[l] = layers.get(l)[p.x][p.y];

		return pointLayers;
	}

	public int getLayer(int layer, Point pos, int defaultValue) {
		if (validPoint(pos))
			return getLayer(layer, pos);
		else
			return defaultValue;
	}

	public void setLayer(Point pos, int value) {
		setLayer(0, pos, value);
	}

	public void setLayer(int layer, Point pos, int value) {
		ensureLayer(layer);
		layers.get(layer)[pos.x][pos.y] = value;
	}

	public void resetLayer() {
		resetLayer(0);
	}

	public void resetLayer(int layer) {
		for (int x = 0; x < height(); x++)
			for (int y = 0; y < width(); y++)
				setLayer(layer, new Point(x, y), 0);
	}

	public void copyLayer(int from, int to) {
		for (int x = 0; x < height(); x++)
			for (int y = 0; y < width(); y++) {
				Point p = new Point(x, y);
				setLayer(to, p, getLayer(from, p));
			}
	}

	public void fillLayer(Point pos, int newValue) {
		if (!validPoint(pos))
			return;
		int startValue = getLayer(pos);
		if (startValue != newValue)
			fillLayer(pos, newValue, (_p, _b, layers) -> layers[0] == startValue);
	}

	public void fillLayer(Point startPos, int newValue, FillRule rule) {
		new TaskList<Point, Boolean>((tl, pos) -> {
			if (!validPoint(pos) || !rule.fill(pos, getBase(pos), getAllLayers(pos)))
				return Optional.of(false);

			setLayer(pos, newValue);
			for (Direction d : CARDINALS)
				tl.recurse(pos.applyDirection(d));

			return Optional.of(true);
		}).run(startPos);
	}

	public <T> T iterateMap(CalcFunction<T> func, T start) {
		T result = start;
		for (int x = 0; x < height(); x++)
			for (int y = 0; y < width(); y++) {
				Point p = new Point(x, y);
				result = func.calc(p, getBase(p), getAllLayers(p), result);
			}
		return result;
	}

	public int sumLayer() {
		return sumLayer(0);
	}

	public int sumLayer(int layer) {
		return iterateMap((_p, _b, layers, prev) -> layers[layer] + prev, 0);
	}

	@Override
	public String toString() {
		return toString(0);
	}

	public String toString(int layer) {
		ensureLayer(layer);
		return toString((_p, base, layers) -> {
			int overlay = layer >= 0 ? layers[layer] : 0;
			if (overlay < 0)
				return '-';
			else if (overlay > 0)
				return (char) ('0' + overlay % 10);
			else if (base != null)
				return base.getOutputChar();
			else
				return ' ';
		});
	}

	public String toString(MergeFunction func) {
		return iterateMap((pos, base, layers, sb) -> {
			sb.append(func.merge(pos, base, layers));
			if (pos.y == width() - 1)
				sb.append('\n');
			return sb;
		}, new StringBuilder()).toString();
	}

	public interface MapElement {
		char getParseChar();

		char getOutputChar();
	}

	public interface MergeFunction {
		char merge(Point pos, MapElement base, int[] layers);
	}

	public interface CalcFunction<T> {
		T calc(Point pos, MapElement base, int[] layers, T previous);
	}

	public interface FillRule {
		boolean fill(Point pos, MapElement base, int[] layers);
	}

	public enum Direction {
		N(-1, 0),
		S(1, 0),
		E(0, 1),
		W(0, -1),

		NE(-1, 1),
		SW(1, -1),
		SE(1, 1),
		NW(-1, -1);

		public final int dx;
		public final int dy;

		Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		public Direction rotate90() {
			return switch (this) {
				case N -> E;
				case E -> S;
				case S -> W;
				case W -> N;

				case NE -> SE;
				case SE -> SW;
				case SW -> NW;
				case NW -> NE;
			};
		}

		public Direction rotate90(int steps) {
			if (steps < 0)
				return rotate90(steps + 4);
			else if (steps == 0)
				return this;
			else
				return rotate90().rotate90(steps - 1);
		}

		public Direction inverse() {
			return rotate90(2);
		}
	}

	public record Point(int x, int y) {
		public Point applyDirection(Direction d) {
			return new Point(x + d.dx, y + d.dy);
		}

		public Point applyDirection(Direction d, int steps) {
			if (steps < 0)
				throw new RuntimeException("No negative steps");
			else if (steps == 0)
				return this;
			else
				return applyDirection(d).applyDirection(d, steps - 1);
		}

		public int distance(Point c) {
			return Math.abs(x - c.x) + Math.abs(y - c.y);
		}
	}

	public enum Maze implements MapElement {
		EMPTY('.', ' '), WALL('#', '▒'), START('S'), End('E'), X('X', 'X'),
		UP('^'), DOWN('v'), LEFT('<'), RIGHT('>');

		private char i;
		private char o;

		Maze(char c) {
			this(c, c);
		}

		Maze(char i, char o) {
			this.i = i;
			this.o = o;
		}

		@Override
		public char getParseChar() {
			return i;
		}

		@Override
		public char getOutputChar() {
			return o;
		}
	}

	public enum Digits implements MapElement {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

		public int getValue() {
			return ordinal();
		}

		@Override
		public char getParseChar() {
			return (char) ('0' + getValue());
		}

		@Override
		public char getOutputChar() {
			return getParseChar();
		}
	}

	public enum AlphaNum implements MapElement {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, EMPTY,
		A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
		a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;

		@Override
		public char getParseChar() {
			int ord = ordinal();
			if (ord < 10)
				return (char) ('0' + ord);
			if (ord == 10)
				return '.';
			return name().charAt(0);
		}

		@Override
		public char getOutputChar() {
			return getParseChar();
		}
	}
}
