package de.coeins.aoc21;

import java.util.ArrayList;
import java.util.List;

public class Day7 implements Day {
    public long task1 (String input) {

        String[] numbers = input.split(",");

        List<Integer> positions = new ArrayList<>();

        int max = 0;
        for (String number : numbers) {
            int i = Integer.parseInt(number);
            positions.add(i);
            if (i > max) max = i;
        }

        //Integer[] costs = new Integer[max+1];
        int minCosts = Integer.MAX_VALUE;

        for (int target = 0; target < max; target++) {
            int c = 0;
            for (Integer position : positions) {
                c += Math.abs(position - target);
            }
            System.out.print((c/10000)+",");
            if (c < minCosts) minCosts = c;
        }
        System.out.println();

        return minCosts;
    }

    public long task2(String input) {

        String[] numbers = input.split(",");

        List<Integer> positions = new ArrayList<>();

        int max = 0;
        for (String number : numbers) {
            int i = Integer.parseInt(number);
            positions.add(i);
            if (i > max) max = i;
        }

        //Integer[] costs = new Integer[max+1];
        int minCosts = Integer.MAX_VALUE;

        for (int target = 0; target < max; target++) {
            int c = 0;
            for (Integer position : positions) {
                int x = Math.abs(position - target);
                c += (x*x+x)/2;
            }
            //System.out.print((c/1000000)+",");
            if (c < minCosts) minCosts = c;
        }
        System.out.println();

        return minCosts;
    }
}
