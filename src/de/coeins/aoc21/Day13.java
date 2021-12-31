package de.coeins.aoc21;

public class Day13 implements Day {
    private static int X = 'x';
    private static int Y = 'y';


    public long task1(String in) {
        return compute(in, 1);
    }


    public long task2(String in) {
        // This task requires you read any letters in the the printed output.
        return compute(in, 1000);
    }

    public static long compute(String input, int numfolds) {
        String[] lines = input.split("\n");
        int maxX = 1400;
        int maxY = 1400;
        Boolean[][] map = new Boolean[maxX][maxY];

        int i;
        for (i = 0; i < lines.length; i++) {
            if ("".equals(lines[i]))
            {
                i++;
                break;
            }
            String[] split = lines[i].split(",");
            map[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = true;
        }

        for (int f = 0; f < numfolds && f < lines.length - i; f++) {
            int result = printAndCount(map, maxX, maxY, false);
//            System.out.println(result);
            String[] split = lines[i + f].split("=");
            if (split[0].charAt(11) == X) {
                foldX(map, Integer.parseInt(split[1]));
                maxX = Integer.parseInt(split[1]);
            }
            if (split[0].charAt(11) == Y) {
                foldY(map, Integer.parseInt(split[1]));
                maxY = Integer.parseInt(split[1]);
            }
        }
        return printAndCount(map, maxX, maxY, maxX < 300);
    }


    private static int printAndCount(Boolean[][] map, int maxX, int maxY, boolean print) {
        int res = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (map[x][y] == null) map[x][y] = false;
                if (print) System.out.print(map[x][y] ? "#" : ".");
                if (map[x][y]) res++;
            }
            if (print) System.out.println();
        }
        return res;
    }

    private static void foldX(Boolean[][] map, int axis) {
        for (int x = axis; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y]) {
                    map[x][y] = false;
                    map[2 * axis - x][y] = true;
                }
            }
        }
    }

    private static void foldY(Boolean[][] map, int axis) {
        for (int x = 0; x < map.length; x++) {
            for (int y = axis; y < map[x].length; y++) {
                if (map[x][y]) {
                    map[x][y] = false;
                    map[x][2 * axis - y] = true;
                }
            }
        }
    }
}
