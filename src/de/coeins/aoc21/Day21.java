package de.coeins.aoc21;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day21 implements Day {
    public static int FIELDS = 10;
    public static int MAX_DICE = 100;
    public static int[] prop = new int[]{0, 0, 0, 1, 3, 6, 7, 6, 3, 1};

    @Override
    public long task1(String in) {
        String[] lines = in.split("\n");
        int[] startpos = new int[]{lines[0].charAt(28) - '0', lines[1].charAt(28) - '0'};
        return computeSingle(startpos, 1000);
    }

    @Override
    public long task2(String in) {
        String[] lines = in.split("\n");
        int[] startpos = new int[]{lines[0].charAt(28) - '0', lines[1].charAt(28) - '0'};
        return computeUniverses(startpos, 21);
    }

    public static int computeSingle(int[] input, int max_score) {
        int[] pos = new int[2];
        int[] score = new int[2];
        int dice = 0;
        int rolls = 0;
        pos[0] = input[0] - 1;
        pos[1] = input[1] - 1;

        while (score[0] < max_score && score[1] < max_score) {
            for (int i = 0; i < 2; i++) {
                rolls += 3;
                int roll = 3 * dice + 6;
                if (dice == 98) roll = 200;
                if (dice == 99) roll = 103;
                dice = (dice + 3) % MAX_DICE;
                pos[i] = (pos[i] + roll) % FIELDS;
                score[i] = score[i] + pos[i] + 1;
//                System.out.println("Player " + (i + 1) + " rolls " + roll + " and moves to space " + (pos[i] + 1) +
//                        " for a total score of " + score[i]);
                if (score[i] >= max_score)
                    break;
            }
        }

        return Math.min(score[0], score[1]) * rolls;
    }

    public static long computeUniverses(int[] input, int max_score) {
        Map<GameState, Long> states = new HashMap<>();
        GameState seed = new GameState(input[0] - 1, 0, input[1] - 1, 0);
        add(states, seed, 1);
        Long[] winners = new Long[]{0L, 0L};
        int round = 0;

        while (states.keySet().size() > 0) {
            long sum = 0;
            for (GameState gameState : states.keySet()) {
                sum += states.get(gameState);
            }

            System.out.println("Round " + (++round) + " (P1), States: " + states.keySet().size() + ", Total: " + sum
                    + ", Winners: " + winners[0] + "/" + winners[1]);

            Map<GameState, Long> newStates = new HashMap<>();

            for (GameState state : states.keySet()) {
                long count = states.get(state);

                if (state.winner(max_score) > 0) {
                    winners[state.winner(max_score) - 1] += count;
                } else {
                    for (int i = 3; i <= 9; i++) {
                        add(newStates, state.advanceP1(i), count * prop[i]);
                    }
                }
            }
            states = newStates;
            newStates = new HashMap<>();

            sum = 0;
            for (GameState gameState : states.keySet()) {
                sum += states.get(gameState);
            }
            System.out.println("Round " + (++round) + " (P2), States: " + states.keySet().size() + ", Total: " + sum
                    + ", Winners: " + winners[0] + "/" + winners[1]);

            for (GameState state : states.keySet()) {
                long count = states.get(state);

                if (state.winner(max_score) > 0) {
                    winners[state.winner(max_score) - 1] += count;
                } else {
                    for (int i = 3; i <= 9; i++) {
                        add(newStates, state.advanceP2(i), count * prop[i]);
                    }
                }
            }
            states = newStates;

        }

        //430229533595466
        //444356092776315

        return Math.max(winners[0], winners[1]);
    }

    private static <T> void add(Map<T, Long> map, T key, long inc) {
        if (map.containsKey(key))
            map.put(key, map.get(key) + inc);
        else
            map.put(key, inc);
    }

    private static class GameState {
        private final int p1pos;
        private final int p2pos;

        private final int p1score;
        private final int p2score;

        public GameState(int p1pos, int p1score, int p2pos, int p2score) {
            this.p1pos = p1pos;
            this.p2pos = p2pos;
            this.p1score = p1score;
            this.p2score = p2score;
        }

        public GameState advanceP1(int x) {
            int newp1 = (p1pos + x) % FIELDS;
            return new GameState(newp1, p1score + newp1 + 1, p2pos, p2score);
        }

        public GameState advanceP2(int x) {
            int newp2 = (p2pos + x) % FIELDS;
            return new GameState(p1pos, p1score, newp2, p2score + newp2 + 1);
        }

        public int winner(int max_score) {
            if (p1score >= max_score)
                return 1;
            else if (p2score >= max_score)
                return 2;
            else
                return 0;
        }

        @Override
        public String toString() {
            return "{" +
                    "p1: " + p1pos + "," + p1score +
                    " p2:" + p2pos + "," + p2score +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState gameState = (GameState) o;
            return p1pos == gameState.p1pos && p2pos == gameState.p2pos
                    && p1score == gameState.p1score && p2score == gameState.p2score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1pos, p2pos, p1score, p2score);
        }
    }
}