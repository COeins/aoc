package de.coeins.aoc21;

public class Day5 implements Day {
    public long task1(String input) {
        String[] inputlines = input.split("\n");
        Integer[][] map = new Integer[1000][1000];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = 0;
            }
        }

        for (String inputline : inputlines) {
            String[] points = inputline.split(" -> ");

            int pointAX = Integer.parseInt(points[0].split(",")[0]);
            int pointAY = Integer.parseInt(points[0].split(",")[1]);
            int pointBX = Integer.parseInt(points[1].split(",")[0]);
            int pointBY = Integer.parseInt(points[1].split(",")[1]);

            if (pointAX == pointBX) // horizontal
            {
                for (int y = Math.min(pointAY, pointBY); y <= Math.max(pointAY, pointBY); y++)
                    map[pointAX][y]++;
            } else if (pointAY == pointBY) // vertical
            {
                for (int x = Math.min(pointAX, pointBX); x <= Math.max(pointAX, pointBX); x++)
                    map[x][pointAY]++;
            } else // diagonal
            {
                // ignore for now
            }
        }

        int score = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
//                System.out.print(map[i][j]);
                if (map[i][j] >= 2) score++;
            }
//            System.out.println();
        }

        return score;
    }

    public long task2(String input) {
        String[] inputlines = input.split("\n");
        Integer[][] map = new Integer[1000][1000];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = 0;
            }
        }

        for (String inputline : inputlines) {
            String[] points = inputline.split(" -> ");

            int pointAX = Integer.parseInt(points[0].split(",")[0]);
            int pointAY = Integer.parseInt(points[0].split(",")[1]);
            int pointBX = Integer.parseInt(points[1].split(",")[0]);
            int pointBY = Integer.parseInt(points[1].split(",")[1]);
//            System.out.println(pointAX + "," + pointAY + "-" + pointBX + "," + pointBY);

            int steps = Math.max(Math.abs(pointAX - pointBX), Math.abs(pointAY - pointBY));
            int dX = (pointBX - pointAX) / steps;
            int dy = (pointBY - pointAY) / steps;
            for (int s = 0; s <= steps; s++) {
                map[pointAX + s * dX][pointAY + s * dy]++;
            }
        }

        int score = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
//                System.out.print(map[i][j]);
                if (map[i][j] >= 2) score++;
            }
//            System.out.println();
        }

        return score;
    }
}
