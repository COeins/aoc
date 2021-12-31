package de.coeins.aoc21;

public class Day18 implements Day{

    public static int LEFT = 0;
    public static int RIGHT = 2;

    public long task1(String input) {
        String[] lines = input.split("\n");
        Element sum = parse(new LineReader(lines[0]));

        for (int i = 1; i < lines.length; i++) {
            Element add = parse(new LineReader(lines[i]));
//            System.out.println("  " + sum + "\n+ " + add);

            Element newSum = new Element(new Pair(sum, add));
            reduce(newSum);
//            System.out.println("= " + newSum + "\n");

            sum = newSum;
        }

        return sum.magnitude();
    }

    public long task2(String input) {
        String[] lines = input.split("\n");

        long largest = 0;
        for (String line1 : lines) {
            for (String line2 : lines) {
                if (line1.equals(line2)) continue;
                Element a1 = parse(new LineReader(line1));
                Element a2 = parse(new LineReader(line2));
                Element sum = new Element(new Pair(a1, a2));
                reduce(sum);
                long m = sum.magnitude();
                if (m > largest) largest = m;
            }
        }

        return largest;
    }

    private static void reduce(Element p) {
        boolean action = true;
        do {
            try {
                action = p.explode(0) || p.split();
            } catch (PairExplodedException e) {
                action = true;
            }
//            System.out.println("r " + p);
        } while (action);
    }

    public static Element parse(LineReader input) {
        if (input.peakNext() == '[') {
            input.next();
            Element a = parse(input);
            if (input.next() != ',') throw new RuntimeException("Parse error");
            Element b = parse(input);
            if (input.next() != ']') throw new RuntimeException("Parse error");
            return new Element(new Pair(a, b));
        } else {
            int literal = input.next() - 48;
            if (literal < 0 || literal > 9)
                throw new RuntimeException("Parse error");
            return new Element(literal);
        }
    }

    private static class LineReader {
        String line;
        int pos;

        LineReader(String line) {
            this.line = line;
            this.pos = 0;
        }

        private char next() {
            return line.charAt(pos++);
        }

        private char peakNext() {
            return line.charAt(pos);
        }
    }

    private static class Element {
        int literal;
        Pair pair;

        public Element(int literal) {
            this.literal = literal;
            this.pair = null;
        }

        public Element(Pair p) {
            this.pair = p;
        }

        public boolean isLiteral() {
            return pair == null;
        }

        public int getLiteral() {
            if (!isLiteral())
                throw new RuntimeException("Element is pair");
            return literal;
        }

        public Pair getPair() {
            if (isLiteral())
                throw new RuntimeException("Element is literal");
            return pair;
        }

        public boolean split() {
            if (isLiteral()) {
                if (literal > 9) {
                    pair = new Pair(new Element(literal / 2), new Element(literal / 2 + literal % 2));
                    literal = -1;
                    return true;
                } else
                    return false;
            } else {
                return pair.split();
            }
        }

        public boolean explode(int level) throws PairExplodedException {
            if (!isLiteral()) {
                if (level > 3) {
                    if (!pair.getLeft().isLiteral() || !pair.getRight().isLiteral())
                        throw new RuntimeException("Unexpected Stack Height");

                    PairExplodedException e =
                            new PairExplodedException(pair.getLeft().getLiteral(), pair.getRight().getLiteral());
                    pair = null;
                    literal = 0;
                    throw e;
                }
                return pair.explode(level);
            }
            return false;
        }

        private void propagateUp(int literal, int direction) {
            if (isLiteral())
                this.literal += literal;
            else
                this.pair.propagateUp(literal, direction);
        }

        public String toString() {
            if (isLiteral()) {
                return "" + literal;
            } else
                return pair.toString();
        }

        public long magnitude() {
            if (isLiteral())
                return literal;
            else
                return pair.magnitude();
        }
    }

    private static class Pair {
        Element left;
        Element right;

        Pair(Element l, Element r) {
            left = l;
            right = r;
        }

        public boolean split() {
            return (left.split() || right.split());
        }

        public boolean explode(int level) throws PairExplodedException {
            try {
                boolean action = left.explode(level + 1);
                if (action) return true;
            } catch (PairExplodedException e) {
                if (e.hasRight()) right.propagateUp(e.getPropagateRight(), RIGHT);
                if (e.hasLeft()) throw new PairExplodedException(e.getPropagateLeft(), -1);
                return true;
            }

            try {
                return right.explode(level + 1);
            } catch (PairExplodedException e) {
                if (e.hasLeft()) left.propagateUp(e.getPropagateLeft(), LEFT);
                if (e.hasRight()) throw new PairExplodedException(-1, e.getPropagateRight());
                return true;
            }
        }

        private void propagateUp(int literal, int direction) {
            if (direction == LEFT) {
                right.propagateUp(literal, direction);
            } else {
                left.propagateUp(literal, direction);
            }
        }

        private Element getLeft() {
            return left;
        }

        private Element getRight() {
            return right;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("[")
                    .append(left)
                    .append(",")
                    .append(right)
                    .append("]");
            return sb.toString();
        }

        public long magnitude() {
            return 3*left.magnitude() + 2* right.magnitude();
        }
    }

    private static class PairExplodedException extends Exception {
        int propagateLeft;
        int propagateRight;

        public PairExplodedException(int left, int right) {
            propagateLeft = left;
            propagateRight = right;
        }

        public int getPropagateLeft() {
            return propagateLeft;
        }

        public int getPropagateRight() {
            return propagateRight;
        }

        public boolean hasLeft() {
            return propagateLeft >= 0;
        }

        public boolean hasRight() {
            return propagateRight >= 0;
        }
    }
}
