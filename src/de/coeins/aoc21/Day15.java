package de.coeins.aoc21;

public class Day15 implements Day {
    private static int counter;
    private static int[][] map;
    private static Integer[][] visited;

    public long task1(String in) {
        return compute(in, 1);
    }

    public long task2(String in) {
        return compute(in, 5);
    }

    public static int compute(String input, int multiple) {

        String[] lines = input.split("\n");

        int sizex = lines.length * multiple;
        int sizey = lines[0].length() * multiple;

        map = new int[sizex][sizey];
        visited = new Integer[sizex][sizey];

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                for (int k = 0; k < multiple; k++) {
                    for (int l = 0; l < multiple; l++) {
                        map[i + k * lines.length][j + l * lines[0].length()] = ((line.charAt(j) - 49) + (k + l)) % 9 + 1;
                        visited[i + k * lines.length][j + l * lines[0].length()] = 0;
                    }
                }
            }
        }

        System.out.println("");

        //int res = findPath(0, 0,sizex-1, sizey-1) - map[0][0];
        int res = findPath2(sizex - 1, sizey - 1) - map[0][0];

//        for (int x = 0; x < sizex; x++) {
//            for (int y = 0; y < sizey; y++) {
//                System.out.print(String.format("%02d", visited[x][y]) + " ");
//            }
//            System.out.println();
//        }
        return res;
    }

    private static int findPath(int posx, int posy, int tarx, int tary) {
        if (posx > tarx || posy > tary)
            return Integer.MAX_VALUE;

        if (visited[posx][posy] > 0) {
            return visited[posx][posy];
        } else {
            int res;
            if (posx == tarx && posy == tary)
                res = map[posx][posy];
            else
                res = map[posx][posy] + Math.min(
                        findPath(posx + 1, posy, tarx, tary),
                        findPath(posx, posy + 1, tarx, tary)
                );

            visited[posx][posy] = res;
            return res;
        }
    }

    private static int findPath2(int tarx, int tary) {
        for (int x = 0; x <= tarx; x++) {
            for (int y = 0; y <= tary; y++) {

                int cost_up = (x > 0) ? visited[x - 1][y] : Integer.MAX_VALUE;
                int cost_le = (y > 0) ? visited[x][y - 1] : Integer.MAX_VALUE;
                int cost = (x == 0 && y == 0) ? 0 : Math.min(cost_up, cost_le);
                cost += map[x][y];
                visited[x][y] = cost;

                if (x > 0 && cost + map[x - 1][y] < cost_up) propagate(x - 1, y);
                if (y > 0 && cost + map[x][y - 1] < cost_le) propagate(x, y - 1);
            }
        }
        return visited[tarx][tary];
    }

    private static void propagate(int x, int y) {
        if (x < 0 || y < 0 || x > map.length || y > map[0].length || visited[x][y] == 0)
            return;

        int cost_up = (x > 0) ? visited[x - 1][y] : Integer.MAX_VALUE;
        int cost_le = (y > 0) ? visited[x][y - 1] : Integer.MAX_VALUE;
        int cost_do = (x < visited.length - 1) ? visited[x + 1][y] : Integer.MAX_VALUE;
        int cost_ri = (y < visited[0].length - 1) ? visited[x][y + 1] : Integer.MAX_VALUE;

        if (cost_do == 0) cost_do = Integer.MAX_VALUE;
        if (cost_ri == 0) cost_ri = Integer.MAX_VALUE;

        int cost = Math.min(Math.min(cost_up, cost_le), Math.min(cost_do, cost_ri)) + map[x][y];

        if (cost < visited[x][y])
            visited[x][y] = cost;
        else
            return;

        if (x > 0 && cost + map[x - 1][y] < cost_up) propagate(x - 1, y);
        if (y > 0 && cost + map[x][y - 1] < cost_le) propagate(x, y - 1);
        if (x < visited.length - 1 && visited[x + 1][y] > 0 && cost + map[x + 1][y] < cost_do) propagate(x + 1, y);
        if (y < visited[0].length - 1 && visited[x][y + 1] > 0 && cost + map[x][y + 1] < cost_ri) propagate(x, y + 1);

    }

}
