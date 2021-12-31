package de.coeins.aoc21;

import java.util.LinkedList;
import java.util.List;

public class Day11 implements Day {
    public long task1(String input) {
        String[] inputlines = input.split("\n");
        Octopus[][] map = new Octopus[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = new Octopus(inputlines[i].charAt(j) - 48);
                if (j > 0) map[i][j].addNeighborur(map[i][j - 1], true);
                if (i > 0) {
                    if (j > 0) map[i][j].addNeighborur(map[i - 1][j - 1], true);
                    map[i][j].addNeighborur(map[i - 1][j], true);
                    if (j < 9) map[i][j].addNeighborur(map[i - 1][j + 1], true);
                }
            }
        }

        for (int i = 0; i < 100; i++) {
//            if (i % 10 == 0) {
//                System.out.println("Step " + i);
//                printmap(map);
//            }
            stepmap(map);
        }

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sum += map[i][j].flashes;
            }
        }

        return sum;
    }


    public long task2(String input) {
        String[] inputlines = input.split("\n");
        Octopus[][] map = new Octopus[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = new Octopus(inputlines[i].charAt(j) - 48);
                if (j > 0) map[i][j].addNeighborur(map[i][j - 1], true);
                if (i > 0) {
                    if (j > 0) map[i][j].addNeighborur(map[i - 1][j - 1], true);
                    map[i][j].addNeighborur(map[i - 1][j], true);
                    if (j < 9) map[i][j].addNeighborur(map[i - 1][j + 1], true);
                }
            }
        }

        int s;
        for (s = 0; s < 1000; s++) {
            stepmap(map);
            boolean sync = true;

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    sync &= map[i][j].level > 9;
                }
            }

            if (sync) {
                return s + 1;
            }

        }
        throw new RuntimeException("No Sync after step " + s);
    }

    private static void stepmap(Octopus[][] map) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j].step();
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j].inc();
            }
        }
    }

    private static void printmap(Octopus[][] map) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print((map[i][j].level > 9 ? "0" : map[i][j].level));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static class Octopus {
        private int level;
        private int flashes = 0;
        private List<Octopus> neighbours = new LinkedList<>();

        public Octopus(int level) {
            this.level = level;
        }

        public void addNeighborur(Octopus next, boolean recursive) {
            this.neighbours.add(next);
            if (recursive) next.addNeighborur(this, false);
        }

        public void step() {
            if (level > 9) level = 0;
        }

        public void inc() {
            level++;
            if (level == 10) {
                flash();
            }
        }

        private void flash() {
            flashes++;
            for (Octopus neighbour : neighbours) {
                neighbour.inc();
            }
        }
    }
}
