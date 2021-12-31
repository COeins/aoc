package de.coeins.aoc21;

public class Day6 implements Day {
    public static long compute(String input, int age) {

        Long[] fishes = new Long[9];
        for (int i = 0; i < 9; i++) {
            fishes[i] = 0l;
        }
        String[] start = input.split(",");

        for (String s : start) {
            fishes[Integer.valueOf(s)]++;
        }

        for (int a = 0; a < age; a++) {
            Long zeroes = fishes[0];
            for (int i = 0; i < 8; i++) {
                fishes[i] = fishes[i+1];
            }
            fishes[6]+=zeroes;
            fishes[8] = zeroes;
        }

        Long total = 0l;
        for (int i = 0; i < 9; i++) {
           System.out.println(i+": "+fishes[i]);
            total += fishes[i];
        }
        return total;
    }

    @Override
    public long task1(String in) {
        return compute(in, 80);
    }

    @Override
    public long task2(String in) {
        return compute(in, 256);
    }
}
