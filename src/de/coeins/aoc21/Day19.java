package de.coeins.aoc21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day19 implements Day{

    private static int X = 0;
    private static int Y = 1;
    private static int Z = 2;
    private static int POS = 1;
    private static int NEG = -1;

    private static int THRESH = 12;

    private static Orientation[] orientations;

    static {
        // this generates all orientations, regardless of chirality
//        orientations = new Orientation[48];
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 8; j++) {
//                int x = i % 3;
//                int y = (x + 1 + i / 3) % 3;
//                int z = 3 - x - y;
//                Orientation o = new Orientation(x, y, z,
//                        -1 * ((j & 1) * 2 - 1),
//                        -1 * ((j >> 1 & 1) * 2 - 1),
//                        -1 * ((j >> 2 & 1) * 2 - 1));
//                orientations[i + (6 * j)] = o;
//            }
//        }
        orientations = new Orientation[24];

        orientations[0] = new Orientation(X, Y, Z, POS, POS, POS);
        orientations[1] = new Orientation(X, Y, Z, POS, NEG, NEG);
        orientations[2] = new Orientation(X, Y, Z, NEG, POS, NEG);
        orientations[3] = new Orientation(X, Y, Z, NEG, NEG, POS);
        orientations[4] = new Orientation(X, Z, Y, POS, POS, NEG);
        orientations[5] = new Orientation(X, Z, Y, POS, NEG, POS);
        orientations[6] = new Orientation(X, Z, Y, NEG, POS, POS);
        orientations[7] = new Orientation(X, Z, Y, NEG, NEG, NEG);
        orientations[8] = new Orientation(Y, X, Z, POS, POS, NEG);
        orientations[9] = new Orientation(Y, X, Z, POS, NEG, POS);
        orientations[10] = new Orientation(Y, X, Z, NEG, POS, POS);
        orientations[11] = new Orientation(Y, X, Z, NEG, NEG, NEG);
        orientations[12] = new Orientation(Y, Z, X, POS, POS, POS);
        orientations[13] = new Orientation(Y, Z, X, POS, NEG, NEG);
        orientations[14] = new Orientation(Y, Z, X, NEG, POS, NEG);
        orientations[15] = new Orientation(Y, Z, X, NEG, NEG, POS);
        orientations[16] = new Orientation(Z, X, Y, POS, POS, POS);
        orientations[17] = new Orientation(Z, X, Y, POS, NEG, NEG);
        orientations[18] = new Orientation(Z, X, Y, NEG, POS, NEG);
        orientations[19] = new Orientation(Z, X, Y, NEG, NEG, POS);
        orientations[20] = new Orientation(Z, Y, X, POS, POS, NEG);
        orientations[21] = new Orientation(Z, Y, X, POS, NEG, POS);
        orientations[22] = new Orientation(Z, Y, X, NEG, POS, POS);
        orientations[23] = new Orientation(Z, Y, X, NEG, NEG, NEG);
    }

    public long task1(String input) {

        List<Scanner> scanners = parse(input);
        List<Transformation> transforms = new ArrayList<>(scanners.size());
        long start = System.currentTimeMillis();
        Set<Point> fixed = findDistinctPoints(scanners, THRESH, transforms);
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
        return fixed.size();
    }

    public long task2(String input) {
        List<Scanner> scanners = parse(input);
        List<Transformation> transforms = new ArrayList<>(scanners.size());
        Set<Point> fixed = findDistinctPoints(scanners, THRESH, transforms);

        int max_dist = 0;

        for (Transformation tA : transforms) {
            for (Transformation tB : transforms) {
                int dist = tA.distance(tB);
                if (dist > max_dist) {
                    max_dist = dist;
                }
            }
        }
        return max_dist;
    }

    private static Set<Point> findDistinctPoints(List<Scanner> scanners, int threshold, List<Transformation> trans) {
        Set<Point> fixed = new HashSet<>();
        Set<Integer> fixedHashes = new HashSet<>();
        fixed.addAll(scanners.get(0).getPoints());
//        fixedHashes.addAll(scanners.get(0).getHashes());

        int max = scanners.size();
        int first = 1;
        while (scanners.size() > 0 && max-- > 0) {
            List<Scanner> unsuccessful = new ArrayList<>(scanners.size());
            for (int i = first; i < scanners.size(); i++) {
                Scanner s = scanners.get(i);
                // TODO trying to optimize. Only go down if a number of distances are similar enough...
//                int machtes = intersect(fixedHashes, s.getHashes());
//                System.out.println("Comparing scanner " + s.name + ": " + machtes + " points match");
//                if (machtes < 12) {
//                    System.out.println("Skipping " + s.name + " for now. Only " + machtes + " points match");
//                    continue;
//                }
                System.out.println(fixed.size() + " Points so far. Trying to fit " + s.name);
                try {
                    Transformation t = findTransform(fixed, s, threshold);
                    System.out.println("  Found transform: " + t);
                    List<Point> tp = s.getTransformedPoints(t);
                    fixed.addAll(tp);
//                    fixedHashes.addAll(s.getHashes());
                    trans.add(t);
                } catch (OutOfOptions outOfOptions) {
                    System.out.println("  Could not find transform :( ");
                    unsuccessful.add(s);
                }
            }
            scanners = unsuccessful;
            first = 0;
        }
        return fixed;
    }

    private static <T> int intersect(Set<T> a, Set<T> b) {
        int count = 0;
        for (T elema : a) {
            for (T elemb : b) {
                if (elema.equals(elemb)) count++;
            }
        }
        return count;
    }

    private static Transformation findTransform(Set<Point> fixed, Scanner scr, int threshold) throws OutOfOptions {
        for (Orientation o : orientations) {
            //System.out.println("   trying "+ o);
            for (Point oldP : fixed) {
                for (Point newP : scr.getPoints()) {
                    Shift s = newP.findShift(oldP, o);
                    Transformation t = new Transformation(o, s);
                    //System.out.print("     candidate "+ t);

                    int similarities = 0;
                    List<Point> transformed = scr.getTransformedPoints(t);
                    for (Point transP : transformed) {
                        for (Point compP : fixed) {
                            if (transP.equals(compP) && ++similarities >= threshold) return t;
                        }
                    }
                    //System.out.println(": "+ similarities);

                }
            }
        }
        throw new OutOfOptions();
    }

    private static class OutOfOptions extends Exception {

    }

    private static List<Scanner> parse(String input) {
        List<Scanner> scanners = new ArrayList<>();

        String[] lines = input.split("\n");
        Scanner s = null;
        for (String line : lines) {
            if (line.startsWith("---")) {
                s = new Scanner(line.substring(4, line.length() - 4));
            } else if (line.length() == 0) {
                if (s != null) scanners.add(s);
                s = null;
            } else {
                String[] split = line.split(",");
                s.addPoint(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
        }
        if (s != null) scanners.add(s);

        return scanners;
    }

    private static class Transformation {
        public Orientation getO() {
            return o;
        }

        public Shift getS() {
            return s;
        }

        private final Orientation o;
        private final Shift s;

        public Transformation(Orientation o, Shift s) {
            this.o = o;
            this.s = s;
        }

        public int distance(Transformation comp) {
            int dx = Math.abs(s.amount[X] - comp.s.amount[X]);
            int dy = Math.abs(s.amount[Y] - comp.s.amount[Y]);
            int dz = Math.abs(s.amount[Z] - comp.s.amount[Z]);
            return dx + dy + dz;
        }

        public String toString() {
            return "[" + o.toString() + ", " + s.toString() + "]";
        }
    }

    private static class Point {
        final int[] pos;

        public Point(int x, int y, int z) {
            pos = new int[]{x, y, z};
        }

        public int x() {
            return pos[X];
        }

        public int y() {
            return pos[Y];
        }

        public int z() {
            return pos[Z];
        }

        public int getAxis(int axis) {
            return pos[axis];
        }

        public Point transform(Transformation t) {
            return t.getS().shift(t.getO().rotate(this));
        }

        public Shift findShift(Point fixed, Orientation orientation) {
            Point compare = orientation.rotate(this);
            return new Shift(fixed.x() - compare.x(), fixed.y() - compare.y(), fixed.z() - compare.z());
        }

        public int distance(Point p) {
            int dx = Math.abs(x() - p.x());
            int dy = Math.abs(y() - p.y());
            int dz = Math.abs(z() - p.y());
            return dx + dy + dz;
        }

        public String toString() {
            return "(" + pos[X] + "," + pos[Y] + "," + pos[Z] + ")";
        }

        public boolean equals(Object p) {
            if (!(p instanceof Point))
                return false;
            return ((Point) p).x() == x()
                    && ((Point) p).y() == y()
                    && ((Point) p).z() == z();
        }

        public int hashCode() {
            return x() + y() + z();
        }

    }

    private static class Shift {
        final int[] amount;

        public Shift(int x, int y, int z) {
            amount = new int[]{x, y, z};
        }

        public Point shift(Point in) {
            return new Point(in.x() + amount[X],
                    in.y() + amount[Y],
                    in.z() + amount[Z]);
        }

        public String toString() {
            return "s{" + amount[X] + "," + amount[Y] + "," + amount[Z] + "}";
        }
    }

    private static class Orientation {
        final int[] axes;
        final int[] orientation;

        public Orientation(int x, int y, int z, int ox, int oy, int oz) {
            axes = new int[]{x, y, z};
            orientation = new int[]{ox, oy, oz};
        }

        public Point rotate(Point in) {
            return new Point(in.getAxis(axes[X]) * orientation[X],
                    in.getAxis(axes[Y]) * orientation[Y],
                    in.getAxis(axes[Z]) * orientation[Z]);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("o{");
            for (int i = 0; i < 3; i++) {
                sb.append((orientation[i] > 0) ? "+" : "-");
                sb.append((char) (axes[i] + 120)).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.append("}").toString();
        }
    }

    private static class Scanner {
        final String name;
        List<Point> points;
        Set<Integer> hashes;

        public Scanner(String name) {
            this.name = name;
            points = new ArrayList<>();
        }

        public void addPoint(Point p) {
            points.add(p);
        }

        public List<Point> getPoints() {
            return points;
        }

        public List<Point> getTransformedPoints(Transformation t) {
            List<Point> transformed = new ArrayList<>(points.size());
            for (Point point : points) {
                transformed.add(point.transform(t));
            }
            return transformed;
        }

        // This was meant as an optimization. But does not work as expected yet...
//        public Set<Integer> getHashes() {
//            if (hashes != null) return hashes;
//            hashes = new HashSet<>();
//
//            for (int i = 0; i < points.size(); i++) {
//                for (int j = 0; j < points.size(); j++) {
//                    if (i != j)
//                        hashes.add(points.get(i).distance(points.get(j)));
//                }
//            }
//            return hashes;
//        }

        public String toString() {
            return name + " (" + points.size() + ")";
        }
    }
}
