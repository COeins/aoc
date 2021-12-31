package de.coeins.aoc21;

public class Day17 implements Day {

    // maybe define search area automatically some day...
    private static final int SEARCH_MIN = -300;
    private static final int SEARCH_MAX = 500;

    public long task1(String in) {
        String[] split = in.split("[xy=.,\n ]+");
        int[] coords = new int[4];
        for (int i = 0; i < 4; i++) {
            coords[i] = Integer.parseInt(split[i + 1]);
        }

        return computeSingle(coords);
    }

    public long task2(String in) {
        String[] split = in.split("[xy=.,\n ]+");
        int[] coords = new int[4];
        for (int i = 0; i < 4; i++) {
            coords[i] = Integer.parseInt(split[i + 1]);
        }

        return computeAll(coords);
    }

    public static int computeSingle(int[] input) {
        int x = (int) (Math.sqrt(8 * input[1] + 1) / 2) - 1;

        int ymax = 0;
        for (int y = 0; y < SEARCH_MAX; y++) {
            Point point = hitTarget(input, x, y);
            if (point.aim(input) != 0) continue;
            ymax = point.getMaxY();
//            System.out.println(x + ", " + y + " => " + point.toString());
        }
        return ymax;
    }

    public static int computeAll(int[] input) {

        int xmin = 1;
        int xmax = input[1];

        int count = 0;
        for (int i = xmin; i <= xmax; i++) {
//            System.out.print(i + ": ");
            boolean next = true;
            for (int j = SEARCH_MIN; j <= SEARCH_MAX; j++) {
                Point point = hitTarget(input, i, j);
                next = point.aim(input) < 1;
                if (point.aim(input) == 0) {
//                    System.out.print(j + ", "); // + " -> " + point +"; ");
                    count++;
                }

            }
//            System.out.println();
        }
        return count;
    }

    private static Point hitTarget(int[] target, int speedX, int speedY) {
        Point p = new Point(0, 0);
        while (p.aim(target) < 0) {
            p = p.step(speedX, speedY);
            if (speedX > 0) speedX--;
            speedY--;
        }
        return p;
    }

    private static class Point {
        private final int x;
        private final int y;
        private final int maxy;

        public Point(int x, int y, int maxy) {
            this.x = x;
            this.y = y;
            this.maxy = Math.max(y, maxy);
        }

        public Point(int x, int y) {
            this(x, y, 0);
        }

        public Point step(int speedX, int speedY) {
            return new Point(x + speedX, y + speedY, maxy);
        }

        /**
         * -1 : in front of target
         * 0 : target hit
         * 1 : behind target
         * ------+++
         * ---000+++
         * ---000+++
         * +++++++++
         */
        public int aim(int[] target) {
            if (x >= target[0] && x <= target[1] && y >= target[2] && y <= target[3])
                return 0;
            else if (x > target[1] || y < target[2])
                return 1;
            else
                return -1;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        public int getMaxY() {
            return maxy;
        }
    }
}
