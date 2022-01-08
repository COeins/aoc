package de.coeins.aoc21;

import java.util.*;

public class Day23 implements Day {

    private static int opc = 2;

    private static int[][] transitions = new int[][]{
            //{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d, e},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 0
            {1, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0}, // 1
            {0, 2, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0}, // 2
            {0, 0, 2, 0, 2, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0}, // 3
            {0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0}, // 4
            {0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0}, // 5
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 6
            {0, 2, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // 7
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, // 8
            {0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, // 9
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, // a
            {0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // b
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, // c
            {0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1}, // d
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // e
    };

    private static int[][] transitionsMore = new int[][]{
            //{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d, e, f, g, h, i, j, k, l, m},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 0
            {1, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 1
            {0, 2, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 2
            {0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0}, // 3
            {0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0}, // 4
            {0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 6
            {0, 2, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 7
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 8
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 9
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // a
            {0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // b
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // c
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // d
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // e
            {0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // f
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0}, // g
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0}, // h
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, // i
            {0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // j
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0}, // k
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1}, // l
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // m
    };

    private static int[] targets = new int[]
            //{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, a, b, c, d, e},
            {-1, -1, -1, -1, -1, -1, -1, 0, 0, 1, 1, 2, 2, 3, 3};

    private static int[] targetsMore = new int[]
            {-1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};


    private static char[] print = new char[]
            {'.', 'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D'};

    private static char[] printMore = new char[]
            {'.', 'a', 'a', 'A', 'A', 'b', 'b', 'B', 'B', 'c', 'c', 'C', 'C', 'd', 'd', 'D', 'D'};

    private static int[] pot = new int[]
            {1, 10, 100, 1000};

    public long task1(String input) {

        Set<String> debug = new HashSet<>();
//        debug.add("{..B....|BA|CD|.C|DA}");
//        debug.add("{..B....|BA|.D|CC|DA}");
//        debug.add("{...D...|BA|.B|CC|DA}");
//        debug.add("{...D...|.A|BB|CC|DA}");
//        debug.add("{...DDA.|.A|BB|CC|..}");
//        debug.add("{.....A.|.A|BB|CC|DD}");

        return solve(parse(input, false), 100, debug);
    }

    public long task2(String input) {
        transitions = transitionsMore;
        targets = targetsMore;
        print = printMore;
        opc = 4;

        Set<String> debug = new HashSet<>();
//        debug.add("{.......|BDDA|CCBD|BBAC|DACA}");
//        debug.add("{......D|BDDA|CCBD|BBAC|.ACA}");
//        debug.add("{A.....D|BDDA|CCBD|BBAC|..CA}");
//        debug.add("{A....BD|BDDA|CCBD|.BAC|..CA}");
//        debug.add("{A...BBD|BDDA|CCBD|..AC|..CA}");
//        debug.add("{AA..BBD|BDDA|CCBD|...C|..CA}");
//        debug.add("{AA..BBD|BDDA|.CBD|..CC|..CA}");
//        debug.add("{AA..BBD|BDDA|..BD|.CCC|..CA}");
//        debug.add("{AA.BBBD|BDDA|...D|.CCC|..CA}");
//        debug.add("{AADBBBD|BDDA|....|.CCC|..CA}");
//        debug.add("{AAD...D|BDDA|.BBB|.CCC|..CA}");
//        debug.add("{AAD...D|BDDA|.BBB|CCCC|...A}");
//        debug.add("{AAD..AD|BDDA|.BBB|CCCC|....}");
//        debug.add("{AA...AD|BDDA|.BBB|CCCC|...D}");
//        debug.add("{AA...AD|.DDA|BBBB|CCCC|...D}");
//        debug.add("{AA...AD|..DA|BBBB|CCCC|..DD}");
//        debug.add("{AAD..AD|...A|BBBB|CCCC|..DD}");
//        debug.add("{..D..AD|.AAA|BBBB|CCCC|..DD}");
//        debug.add("{.....AD|.AAA|BBBB|CCCC|.DDD}");

        return solve(parse(input, true), 100, debug);
    }

    private static int solve(GameState start, int steps, Set<String> debug) {
        Map<GameState, GameState> state_list = new HashMap<>();
        Map<GameState, Integer> state_costs = new HashMap<>();
        state_costs.put(start, 0);
        state_list.put(start, start);
        Set<GameState> processed = new HashSet<>();
        Set<GameState> currentStates = new HashSet<>();
        int solution = Integer.MAX_VALUE;
        int score = Integer.MIN_VALUE;
        int sumscore = 0;
        int count = -1;

        while (state_costs.size() > processed.size() && steps-- > 0) {
            int preview = 0;

            if (count == 0) count = -1;

            currentStates.addAll(state_costs.keySet());
            System.out.println("Now processing step " + steps + " (" + currentStates.size() + " states, "
                    + (currentStates.size() - processed.size()) + " new), max score: " + score
                    + " avg: " + (sumscore / count));
            sumscore = 0;
            count = 0;
            for (GameState current_key : currentStates) {
                int cost = state_costs.get(current_key);
                GameState current = state_list.get(current_key);
                if (processed.contains(current) || cost > solution || current.solved()) {
                    processed.add(current);
                    continue;
                }

                processed.add(current);

                boolean debugFlag = false;
                if (preview > 0 && current.score() >= score || debug.contains(current.toString().toUpperCase())) {
                    System.out.println(current.toString() + " score " + current.score() + " using " + cost
                            //+ "/" + current.printHistory(false)
                            + " at step " + steps);
                    if (debug.contains(current.toString().toUpperCase())) debugFlag = true;
                    preview--;
                }

                for (int o = 0; o < print.length - 1; o++) {
                    int pos = current.getPos(o);
                    for (int target = 0; target < targets.length; target++) {
                        if (transitions[pos][target] > 0 && current.canMove(o, target)) {
                            Set<GameState> nexts = new HashSet<>();
                            Map<GameState, Integer> next_costs = new HashMap<>();
                            GameState next1 = current.move(o, target, transitions[pos][target] * pot[o / opc]);
                            int nCost1 = cost + transitions[pos][target] * pot[o / opc];
                            if (current.getPos(o) >= 7 && next1.getPos(o) < 6) {
                                nexts.add(next1);
                                next_costs.put(next1, nCost1);
                                int n2p = next1.getPos(o);
                                GameState next2 = next1;
                                int nCost2 = nCost1;
                                for (int l = n2p - 1; l >= 0; l--) {
                                    if (transitions[n2p][l] > 0 && next2.free(l)) {
                                        next2 = next2.move(o, l, transitions[n2p][l] * pot[o / opc]);
                                        nCost2 += transitions[n2p][l] * pot[o / opc];
                                        n2p = l;
                                        nexts.add(next2);
                                        next_costs.put(next2, nCost2);
                                    } else
                                        break;
                                }
                                n2p = next1.getPos(o);
                                next2 = next1;
                                nCost2 = nCost1;
                                for (int l = n2p + 1; l <= 6; l++) {
                                    if (transitions[n2p][l] > 0 && next2.free(l)) {
                                        next2 = next2.move(o, l, transitions[n2p][l] * pot[o / opc]);
                                        nCost2 += transitions[n2p][l] * pot[o / opc];
                                        n2p = l;
                                        nexts.add(next2);
                                        next_costs.put(next2, nCost2);
                                    } else
                                        break;
                                }
                            } else {
                                for (int c = next1.canCompact(o); c > 0; c = next1.canCompact(o)) {
                                    next1 = next1.compact(o, pot[o / opc]);
                                    nCost1 += c * pot[o / opc];
                                }
                                nexts.add(next1);
                                next_costs.put(next1, nCost1);
                            }

                            for (GameState next : nexts) {
                                int nCost = next_costs.get(next);
                                if (next.solved()) {
                                    System.out.println("Found solution: " + next.toString() + " using " + nCost);
                                    if (nCost < solution) solution = nCost;
                                    //next.printHistory(true);
                                }
                                int s = next.score();
                                sumscore += s;
                                count++;
                                if (s > score) {
                                    score = s;
                                }
                                if (debugFlag) System.out.println("> " + next.toString() + " using " + nCost);
                                if (putIfMin(state_costs, state_list, next, nCost))
                                    processed.remove(next);
                            }
                        }
                    }
                }
            }
        }

        return solution;
    }

    private static <T> boolean putIfMin(Map<T, Integer> map, Map<T, T> map1, T key, Integer candidate) {
        if (!map.containsKey(key)) {
            map.put(key, candidate);
            map1.put(key, key);
            return false;
        } else if (candidate < map.get(key)) {
            map.remove(key);
            map1.remove(key);
            map.put(key, candidate);
            map1.put(key, key);
            return true;
        } else
            return false;

    }

    private static GameState parse(String in, boolean fillMore) {

        String[] lines = in.split("\n");

        //0123456789012
        //############# 0
        //#01.2.3.4.56# 1
        //###7#9#b#d### 2
        //  #8#a#c#e#   3
        //  #########   4

        int a0 = -1, a1 = -1, b0 = -1, b1 = -1, c0 = -1, c1 = -1, d0 = -1, d1 = -1;

        for (int i = 0; i <= 6; i += 2) {
            for (int j = 0; j <= 1; j++) {
                char c = lines[2 + j].charAt(3 + i);
                int pos = !fillMore ? 7 + i + j
                        : 7 + 2 * i + 3 * j;
                switch (c) {
                    case 'A':
                        if (a0 == -1) a0 = pos;
                        else a1 = pos;
                        break;
                    case 'B':
                        if (b0 == -1) b0 = pos;
                        else b1 = pos;
                        break;
                    case 'C':
                        if (c0 == -1) c0 = pos;
                        else c1 = pos;
                        break;
                    case 'D':
                        if (d0 == -1) d0 = pos;
                        else d1 = pos;
                        break;
                }
            }
        }
        return new GameState(a0, a1, b0, b1, c0, c1, d0, d1, fillMore);
    }

    private static class GameState {

        // 0 a - 1 a - 2 b - 3 b - 4 c - 5 c - 6 d - 7 d
        private final int[] positions;
        private final int[] map;
        //private final int[] colormap;

        private final int hash;
        private final GameState previous;
        private final int cost;

        private GameState(int[] positions, GameState prev, int cost) {
            this.positions = positions;
            this.map = new int[targets.length];
            int[] colormap = new int[targets.length];
            for (int i = 0; i < positions.length; i++) {
                map[positions[i]] = i + 1;
                colormap[positions[i]] = (i / opc) + 1;
            }
            hash = Arrays.hashCode(colormap);
            this.previous = prev;
            this.cost = cost;
        }

        public GameState(int a0, int a1, int b0, int b1, int c0, int c1, int d0, int d1, boolean fillMore) {
            this(!fillMore ? new int[]{a0, a1, b0, b1, c0, c1, d0, d1}
                    : new int[]{a0, a1, 17, 20, b0, b1, 13, 16, c0, c1, 12, 21, d0, d1, 8, 9}, null, 0);
        }

        public boolean free(int pos) {
            return map[pos] == 0;
        }

        public boolean canMove(int object, int tar_pos) {
            if (!free(tar_pos)) return false;

            int cur_pos = positions[object];
            int cur_col = targets[positions[object]];
            int tar_col = targets[tar_pos];
            int obj_col = object / opc;

            // moving to home row
            if (cur_col == -1) return tar_pos == homePath(object);

            // moving "up" in foreign home
            if (cur_col != obj_col && tar_pos < cur_pos) return true;

            // moving "down" in home row
            if (tar_col == obj_col && tar_pos > cur_pos) return cleanBelow(tar_pos);

            // moving out of home row
            if (cur_col == obj_col && tar_pos < cur_pos) return !cleanBelow(cur_pos);

            return false;
        }

        public int canCompact(int object) {
            int cur_pos = positions[object];
            int cur_col = targets[cur_pos];
            int obj_col = object / opc;

            // moving "home"
            if (cur_col == -1) {
                int tar_pos = homePath(object);
                if (tar_pos > 0)
                    return transitions[cur_pos][tar_pos];
                else
                    return -1;
            }

            // moving "down"
            if (cur_col == obj_col && cleanBelow(cur_pos)) {
                int tar_pos = cur_pos + 1;
                if (tar_pos < targets.length && free(tar_pos) && cur_col == targets[tar_pos])
                    return 1;
                else
                    return -1;
            }

            // moving "up"
            int tar_pos = cur_pos - 1;
            if (free(tar_pos) && cur_col == targets[tar_pos])
                return 1;
            else
                return -1;
        }

        public GameState move(int object, int target, int cost) {
            int[] newpos = positions.clone();
            newpos[object] = target;
            return new GameState(newpos, this, cost);
        }

        public GameState compact(int object, int cost) {
            int cur_pos = positions[object];
            int cur_col = targets[cur_pos];
            int tar_pos = -1;
            int obj_col = object / opc;

            if (cur_col == -1)
                tar_pos = homePath(object);
            else if (cur_col == obj_col && cleanBelow(cur_pos))
                tar_pos = cur_pos + 1;
            else
                tar_pos = cur_pos - 1;

            return move(object, tar_pos, cost);
        }

        public int score() {
            int s = 0;
            for (int i = 0; i < positions.length; i++) {
                int cur_col = targets[positions[i]];
                if (cur_col == -1) continue;
                s += (cur_col == i / opc && cleanBelow(positions[i])) ? 1 : -1;
            }
            return s;
        }

        private boolean cleanBelow(int start_pos) {
            int color = targets[start_pos];
            for (int pos = start_pos + 1; pos < start_pos + opc && pos < targets.length; pos++) {
                if (targets[pos] == color && getObjectAt(pos) > 0
                        && getObjectAt(pos) / opc != color)
                    return false;
            }
            return true;
        }

        private int homePath(int object) {
            int cur_pos = positions[object];
            int obj_col = object / opc;

            int home_row = 7 + opc * obj_col;
            if (!free(home_row)) return -1;
            if (!cleanBelow(home_row)) return -1;

            int junct = -1;
            for (int i = 0; i < 7; i++) {
                if (transitions[home_row][i] > 0 && (junct < 1 || cur_pos > junct)) junct = i;
            }

            if (cur_pos == junct) return home_row;

            if (!free(junct)) return -1;
            int direct = (cur_pos < junct) ? 1 : -1;

            for (int i = cur_pos + direct; i != junct; i += direct) {
                if (!free(i)) return -1;
            }

            return cur_pos + direct;
        }

        public int getPos(int object) {
            return positions[object];
        }

        public int getObjectAt(int pos) {
            return map[pos] - 1;
        }

        public boolean solved() {
            for (int i = 0; i < positions.length; i++) {
                if (targets[positions[i]] != i / opc) return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GameState)) return false;
            GameState g = (GameState) o;
            for (int i = 0; i < map.length; i++) {
                if ((map[i] + opc - 1) / opc != (g.map[i] + opc - 1) / opc)
                    return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            for (int i = 0; i < map.length; i++) {
                sb.append(print[map[i]]);
                if (i >= 6 && (i - 6) % opc == 0)
                    sb.append("|");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.append("}").toString();
        }

        public int printHistory(boolean print) {
            int total = cost;
            if (previous != null)
                total += previous.printHistory(print);
            if (print)
                System.out.println("V " + this.toString() + " (s:" + score() + " c:" + cost + " t:" + total + ")");
            return total;
        }

        // works only for task 1
        public void printState() {
            System.out.println("#############\n" +
                    "#" + print[map[0]] + print[map[1]] + "." + print[map[2]] + "." + print[map[3]] + "."
                    + print[map[4]] + "." + print[map[5]] + print[map[6]] + "#\n" +
                    "###" + print[map[7]] + "#" + print[map[9]] + "#" + print[map[11]] + "#" + print[map[13]] + "###\n" +
                    "  #" + print[map[8]] + "#" + print[map[10]] + "#" + print[map[12]] + "#" + print[map[14]] + "#\n" +
                    "  #########");
        }
    }
}
