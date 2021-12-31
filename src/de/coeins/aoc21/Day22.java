package de.coeins.aoc21;

import java.util.*;

public class Day22 implements Day {
    private static Map engine;

    private final static int X = 0;
    private final static int Y = 1;
    private final static int Z = 2;

    public long task1(String in) {
        return compute(in, 50);
    }

    public long task2(String in) {
        return compute(in, Integer.MAX_VALUE);
    }

    public static long compute(String input, int limit) {
        String[] lines = input.split("\n");
        //engine = new SimpleMap();
        engine = new ComposedMap();

        int numlines = lines.length;
        for (int i = 0; i < lines.length; i++) {
            if (i % 10 == 0) System.out.println("Line " + i + " of " + numlines + " (" + engine.status() + ")");
            String[] s1 = lines[i].split(" ");
            String[] s2 = s1[1].split(",");
            String[] s2x = s2[0].split("\\.\\.");
            String[] s2y = s2[1].split("\\.\\.");
            String[] s2z = s2[2].split("\\.\\.");

            boolean on = "on".equals(s1[0]);
            int x1 = Integer.parseInt(s2x[0].split("=")[1]);
            int x2 = Integer.parseInt(s2x[1]);
            int y1 = Integer.parseInt(s2y[0].split("=")[1]);
            int y2 = Integer.parseInt(s2y[1]);
            int z1 = Integer.parseInt(s2z[0].split("=")[1]);
            int z2 = Integer.parseInt(s2z[1]);

            if (
                    Math.abs(x1) <= limit && Math.abs(x2) <= limit
                            && Math.abs(y1) <= limit && Math.abs(y2) <= limit
                            && Math.abs(z1) <= limit && Math.abs(z2) <= limit
            ) {
//                long cnum = ((long) x2 - (long) x1 + 1) * ((long) y2 - (long) y1 + 1) * ((long) z2 - (long) z1 + 1);
//                System.out.println(" Turning " + s1[0] + " " + cnum + " cubes.");

                engine.toggle(on, x1, x2, y1, y2, z1, z2);
            }
        }

        return engine.size();
    }

    private interface Map {
        void toggle(boolean on, int x1, int x2, int y1, int y2, int z1, int z2);

        long size();

        String status();
    }

    private static class SimpleMap implements Map {
        private Set<Dot> map = new HashSet<>();

        public void toggle(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {
            int xa = Math.min(x1, x2);
            int xb = Math.max(x1, x2);
            int ya = Math.min(y1, y2);
            int yb = Math.max(y1, y2);
            int za = Math.min(z1, z2);
            int zb = Math.max(z1, z2);

            for (int x = xa; x <= xb; x++) {
                for (int y = ya; y <= yb; y++) {
                    for (int z = za; z <= zb; z++) {
                        Dot c = new Dot(x, y, z);
                        if (on)
                            map.add(c);
                        else if (map.contains(c))
                            map.remove(c);
                    }
                }
            }
        }

        public long size() {
            return map.size();
        }

        public String status() {
            return size() + " dots";
        }

        public void list() {
            for (Dot dot : map) {
                System.out.println(dot);
            }
        }
    }

    private static class ComposedMap implements Map {

        private Set<Cube> cubes = new HashSet<>();

        public void toggle(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {
            intersectAll(X, x1);
            intersectAll(X, x2 + 1);
            intersectAll(Y, y1);
            intersectAll(Y, y2 + 1);
            intersectAll(Z, z1);
            intersectAll(Z, z2 + 1);

            Cube newCube = new Cube(x1, y1, z1, x2, y2, z2);

            Iterator<Cube> cubeit = cubes.iterator();

            while (cubeit.hasNext()) {
                Cube c = cubeit.next();
                if (c.contained(newCube) || c.size() == 0)
                    cubeit.remove();
            }

            if (on) cubes.add(newCube);
        }

        public long size() {
            long size = 0;
            for (Cube cube : cubes) {
                size += cube.size();
            }
            return size;
        }

        public String status() {
            return cubes.size() + " regions";
        }

        private void intersectAll(int axis, int value) {
            Iterator<Cube> cubeit = cubes.iterator();
            Set<Cube> newCubes = new HashSet<>();
            Set<Cube> tmp;
            while (cubeit.hasNext()) {
                Cube c = cubeit.next();
                tmp = c.intersect(axis, value);
                if (tmp != null)
                    newCubes.addAll(tmp);
                else
                    newCubes.add(c);
            }
            cubes = newCubes;
        }

        public SimpleMap toSimpleMap() {
            SimpleMap newmap = new SimpleMap();
            for (Cube cube : cubes) {
                newmap.toggle(true, cube.upper.get(X), cube.lower.get(X) - 1,
                        cube.upper.get(Y), cube.lower.get(Y) - 1,
                        cube.upper.get(Z), cube.lower.get(Z) - 1);
            }
            return newmap;
        }
    }

    private static class Dot {
        final int[] xyz;

        public Dot(int x, int y, int z) {
            xyz = new int[3];
            xyz[X] = x;
            xyz[Y] = y;
            xyz[Z] = z;
        }

        public Dot(int[] xyz) {
            this.xyz = xyz;
        }

        public int get(int axis) {
            return xyz[axis];
        }

        public Dot set(int axis, int val) {
            int[] xyz_new = xyz.clone();
            xyz_new[axis] = val;
            return new Dot(xyz_new);
        }

        @Override
        public String toString() {
            return "{" + xyz[X] + ", " + xyz[Y] + ", " + xyz[Z] + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Dot)) return false;
            Dot dot = (Dot) o;
            return Arrays.equals(xyz, dot.xyz);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(xyz);
        }
    }

    private static class Cube {
        final Dot upper;
        final Dot lower;
        final int hash;
        final long size;

        public Cube(Dot upper, Dot lower) {
            this.upper = upper;
            this.lower = lower;
            this.hash = Objects.hash(upper, lower);
            this.size = (long) (lower.get(X) - upper.get(X))
                    * (long) (lower.get(Y) - upper.get(Y))
                    * (long) (lower.get(Z) - upper.get(Z));
        }

        public Cube(int x1, int y1, int z1, int x2, int y2, int z2) {
            this(
                    new Dot(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2)),
                    new Dot(Math.max(x1, x2) + 1, Math.max(y1, y2) + 1, Math.max(z1, z2) + 1)
            );
        }

        public Set<Cube> intersect(int axis, int value) {
            if (size > 1 && value >= upper.get(axis) && value < lower.get(axis)) {
                Set<Cube> collector = new HashSet<>();
                collector.add(new Cube(upper, lower.set(axis, value)));
                collector.add(new Cube(upper.set(axis, value), lower));
                return collector;
            } else
                return null;
        }

        public boolean contained(Cube outer) {
            return upper.get(X) >= outer.upper.get(X)
                    && upper.get(Y) >= outer.upper.get(Y)
                    && upper.get(Z) >= outer.upper.get(Z)
                    && lower.get(X) <= outer.lower.get(X)
                    && lower.get(Y) <= outer.lower.get(Y)
                    && lower.get(Z) <= outer.lower.get(Z);
        }

        public long size() {
            return size;
        }

        @Override
        public String toString() {
            return "[" + upper + " - " + lower + "] (" + size() + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cube)) return false;
            Cube cube = (Cube) o;
            return Objects.equals(upper, cube.upper) && Objects.equals(lower, cube.lower);
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
