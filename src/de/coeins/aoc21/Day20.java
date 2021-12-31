package de.coeins.aoc21;

public class Day20 implements Day {

    public long task1(String in) {
        return compute(in, 2);
    }

    @Override
    public long task2(String in) {
        return compute(in, 50);
    }

    public static int compute(String input, int steps) {
        String[] lines = input.split("\n");
        boolean[] rules = parseRules(lines[0]);

        int size_x = lines[2].length();
        int size_y = lines.length - 2;
        int start = steps + 2;

        boolean map[][] = new boolean[size_x + 2 * start][size_y + 2 * start];

        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                map[x + start][y + start] = lines[y + 2].charAt(x) == '#';
            }
        }

        for (int i = 0; i < steps; i++) {
            start--;
            size_x += 2;
            size_y += 2;
            //System.out.println(printMap(map));

            boolean[][] newmap = new boolean[map.length][map[0].length];

            for (int x = 1; x < map.length - 1; x++) {
                for (int y = 1; y < map[0].length - 1; y++) {
                    int index = (map[x - 1][y - 1] ? 256 : 0)
                            + (map[x + 0][y - 1] ? 128 : 0)
                            + (map[x + 1][y - 1] ? 64 : 0)
                            + (map[x - 1][y + 0] ? 32 : 0)
                            + (map[x + 0][y + 0] ? 16 : 0)
                            + (map[x + 1][y + 0] ? 8 : 0)
                            + (map[x - 1][y + 1] ? 4 : 0)
                            + (map[x + 0][y + 1] ? 2 : 0)
                            + (map[x + 1][y + 1] ? 1 : 0);
                    newmap[x][y] = rules[index];
                }
            }

            for (int x = 0; x < newmap.length; x++) {
                newmap[x][0] = newmap[1][1];
                newmap[x][newmap[0].length - 1] = newmap[1][1];

            }
            for (int y = 0; y < newmap[0].length; y++) {
                newmap[0][y] = newmap[1][1];
                newmap[newmap.length - 1][y] = newmap[1][1];
            }
            map = newmap;
        }

        System.out.println(printMap(map));

        return countMap(map, start - 2);
    }

    public static String generateConvayRules() {
        StringBuilder rules = new StringBuilder();

        for (int x = 0; x < 512; x++) {
            int c = (x & 16) / 16;
            int n = (x & 1) / 1 + (x & 2) / 2 + (x & 4) / 4 + (x & 8) / 8 + (x & 32) / 32
                    + (x & 64) / 64 + (x & 128) / 128 + (x & 256) / 256;
            rules.append((n == 2 && c == 1 || n == 3) ? "#" : ".");
        }
        return rules.toString();
    }

    private static int countMap(boolean[][] map, int start) {
        int ctr = 0;
        for (int y = start; y < map[0].length - start; y++) {
            for (int x = start; x < map.length - start; x++) {
                if (map[x][y]) ctr++;
            }
        }
        return ctr;
    }

    private static String printMap(boolean[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                sb.append(map[x][y] ? "#" : ".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static boolean[] parseRules(String line) {
        boolean[] res = new boolean[line.length()];
        for (int i = 0; i < line.length(); i++) {
            res[i] = line.charAt(i) == '#';
        }
        return res;
    }

}
