package de.coeins.aoc21;

public class Day1 implements Day {

    public long task1(String input) {
        String[] inputlines = input.split("\n");
        int prev = Integer.MAX_VALUE;
        int increments = 0;
        for (int i = 0; i < inputlines.length; i++) {
            int cur = Integer.valueOf(inputlines[i]);
            if (cur > prev) increments++;
            prev = cur;
        }
        return increments;
    }

    public long task2(String input) {
        String[] inputlines = input.split("\n");
        int prev1 = Integer.MAX_VALUE;
        int prev2 = Integer.MAX_VALUE;
        int prev3 = Integer.MAX_VALUE;
        int increments = 0;
        for (int i = 0; i < inputlines.length; i++) {
            int cur = Integer.valueOf(inputlines[i]);
            if (cur > prev3) increments++;
            prev3 = prev2;
            prev2 = prev1;
            prev1 = cur;
        }
        return increments;
    }

}