package de.coeins.aoc21;

public class Day25 implements Day {
    private static final int EMPTY = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;

    private static final char[] print = new char[3];

    static {
        print[EMPTY] = '.';
        print[RIGHT] = '>';
        print[DOWN] = 'v';
    }

    public long task1(String input) {
        String[] lines = input.split("\n");
        int[][] map = new int[lines[0].length()][lines.length];

        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[y].length(); x++) {
                switch (lines[y].charAt(x)) {
                    case '.':
                        map[x][y] = EMPTY;
                        break;
                    case '>':
                        map[x][y] = RIGHT;
                        break;
                    case 'v':
                        map[x][y] = DOWN;
                        break;
                    default:
                        throw new RuntimeException("Inexpected Unput");
                }
            }
        }

        int moves;
        int steps = 0;
        int xlen = map.length;
        int ylen = map[0].length;
        int[][] newmap;
        do {
            moves = 0;
            steps++;

            // move right
            newmap = new int[xlen][ylen];
            for (int y = 0; y < ylen; y++) {
                for (int x = 0; x < xlen; x++) {
                    if (map[x][y] == RIGHT) {
                        if (map[(x + 1) % xlen][y] == EMPTY) {
                            newmap[(x + 1) % xlen][y] = RIGHT;
                            x++;
                            moves++;
                        } else
                            newmap[x][y] = RIGHT;
                    }
                }
            }

            // move down
            for (int x = 0; x < xlen; x++) {
                for (int y = 0; y < ylen; y++) {
                    if (map[x][y] == DOWN) {
                        if (newmap[x][(y + 1) % ylen] == EMPTY && map[x][(y + 1) % ylen] != DOWN) {
                            newmap[x][(y + 1) % ylen] = DOWN;
                            y++;
                            moves++;
                        } else
                            newmap[x][y] = DOWN;
                    }
                }
            }
            map = newmap;

//            System.out.println("Step: " + steps + " moves: " + moves);
        } while (moves > 0 && steps < 10000);

//        for (int y = 0; y < ylen; y++) {
//            for (int x = 0; x < xlen; x++) {
//                System.out.print(print[map[x][y]]);
//            }
//            System.out.println();
//        }

        return steps;
    }

    public long task2(String in) {
        // This day has only one task
        return 0;
    }


}
