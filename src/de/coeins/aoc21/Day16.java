package de.coeins.aoc21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day16 implements Day {
    public long task1(String input) {
        InputBits in = new InputBits(input);
        Pkg packets = new Pkg(in);
        System.out.println(packets.toString());
        return packets.addVersions();
    }

    public long task2(String input) {
        InputBits in = new InputBits(input);
        Pkg packets = new Pkg(in);
        System.out.println(packets.print());
        return packets.evaluate();
    }

    private static class Pkg {
        private final int version;
        private final int type;
        private final long value;
        private final List<Pkg> children;

        public Pkg(InputBits bits) {
            version = (int) bits.next(3);
            type = (int) bits.next(3);
            children = new ArrayList<>();

            if (type == 4) {
                int cont = 1;
                long v = 0;
                while (cont > 0) {
                    cont = bits.next();
                    v = (v << 4) + bits.next(4);
                }
                value = v;
            } else {
                int lengthtype = bits.next();
                int length = (int) bits.next(lengthtype > 0 ? 11 : 15);
                int curpos = bits.getPos();
                while (lengthtype == 0 && bits.getPos() < curpos + length
                        || lengthtype == 1 && children.size() < length) {
                    children.add(new Pkg(bits));
                }
                value = -1;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("[v:" + version + " t:" + type + ", ");
            if (type == 4) {
                sb.append("v:" + value);
            } else {
                sb.append("c:");
                for (Pkg child : children) {
                    sb.append(child.toString());
                }
            }
            return sb.append("] ").toString();
        }

        public String print() {
            if (type == 4)
                return Long.toString(value);

            String[] operator = new String[]{"", "", "min", "max", "#", "", "", ""};
            String[] inline = new String[]{"+", "*", ",", ",", "", ">", "<", "="};
            StringBuilder sb = new StringBuilder(operator[type]).append("(");
            boolean first = true;

            for (Pkg child : children) {
                if (first)
                    first = false;
                else
                    sb.append(inline[type]);

                sb.append(child.print());
            }

            return sb.append(")").toString();
        }

        public int addVersions() {
            int version = this.version;
            if (type != 4) {
                for (Pkg child : children) {
                    version += child.addVersions();
                }
            }
            return version;
        }

        public long evaluate() {
            long result;
            switch (type) {
                case 0:
                    result = 0;
                    for (Pkg child : children) {
                        result += child.evaluate();
                    }
                    return result;
                case 1:
                    result = 1;
                    for (Pkg child : children) {
                        result *= child.evaluate();
                    }
                    return result;
                case 2:
                    result = Integer.MAX_VALUE;
                    for (Pkg child : children) {
                        long c = child.evaluate();
                        if (c < result) result = c;
                    }
                    return result;
                case 3:
                    result = 0;
                    for (Pkg child : children) {
                        long c = child.evaluate();
                        if (c > result) result = c;
                    }
                    return result;
                case 4:
                    return value;
                case 5:
                    if (children.size() < 2)
                        throw new RuntimeException("Not enough stuff to compare");
                    return children.get(0).evaluate() > children.get(1).evaluate() ? 1 : 0;
                case 6:
                    if (children.size() < 2)
                        throw new RuntimeException("Not enough stuff to compare");
                    return children.get(0).evaluate() < children.get(1).evaluate() ? 1 : 0;
                case 7:
                    if (children.size() < 2)
                        throw new RuntimeException("Not enough stuff to compare");
                    return children.get(0).evaluate() == children.get(1).evaluate() ? 1 : 0;
                default:
                    throw new RuntimeException("Unknown packet type.");
            }
        }
    }

    private static class InputBits {
        private final String stream;
        private int current;
        private int bytepos;
        private int bitpos;
        private final int[] bitposval = new int[]{8, 4, 2, 1};

        private static final List<Character> HEX = Arrays.asList(new Character[]
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'});

        InputBits(String stream) {
            this.stream = stream;
            bytepos = 0;
            bitpos = 0;
        }

        public long next(int count) {
            long result = 0;
            for (int i = 0; i < count; i++) {
                result = (result << 1) | next();
            }
            return result;
        }

        public int next() {
            bitpos = bitpos % 4;
            if (bitpos == 0) {
                current = HEX.indexOf(stream.charAt(bytepos++));
            }
            return (current & bitposval[bitpos]) / bitposval[bitpos++];
        }

        public int getPos() {
            return 4 * bytepos + bitpos - 4;
        }
    }
}
