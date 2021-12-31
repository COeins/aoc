package de.coeins.aoc21;

import java.util.HashMap;
import java.util.Map;

public class Day14 implements Day {

    public long task1(String in) {
        return compute(in, 10);
    }

    @Override
    public long task2(String in) {
        return computeEfficient(in, 40);
    }

    public static long compute(String input, int steps) {
        String[] inputlines = input.split("\n");
        String pattern = inputlines[0];

        Map<String, String> rules = new HashMap<>();

        for (int i = 2; i < inputlines.length; i++) {
            String[] split = inputlines[i].split(" -> ");
            rules.put(split[0], split[1]);
        }

        for (int s = 0; s < steps; s++) {
            StringBuilder next = new StringBuilder(pattern.substring(0, 1));
            for (int i = 1; i < pattern.length(); i++) {
                String pair = pattern.substring(i - 1, i + 1);
                if (rules.containsKey(pair))
                    next.append(rules.get(pair));
                next.append(pattern.substring(i, i + 1));
            }
            pattern = next.toString();
//            System.out.println(s + ": " + pattern.length());
        }

        Map<Integer, Long> count = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            int c = pattern.charAt(i);
            count.put(c, count.getOrDefault(c, 0L) + 1);
        }

        long max = 0;
        long min = Integer.MAX_VALUE;

        for (Integer s : count.keySet()) {
            long sc = count.get(s);
            if (sc < min) min = sc;
            if (sc > max) max = sc;
        }
        System.out.println("min " + min + ", max " + max);
        return max - min;
    }

    public static long computeEfficient(String input, int steps) {
        String[] inputlines = input.split("\n");
        Map<String, Long> pattern = new HashMap<>();
        int lastchar = inputlines[0].charAt(inputlines[0].length() - 1);
        for (int i = 1; i < inputlines[0].length(); i++) {
            String pair = inputlines[0].substring(i - 1, i + 1);
            pattern.put(pair, pattern.getOrDefault(pair, 0L) + 1);
        }

        Map<String, String> rules = new HashMap<>();

        for (int i = 2; i < inputlines.length; i++) {
            String[] split = inputlines[i].split(" -> ");
            rules.put(split[0], split[1]);
        }

        for (int s = 0; s < steps; s++) {
            Map<String, Long> next = new HashMap<>();

            for (String pair : pattern.keySet()) {
                if (rules.containsKey(pair)) {
                    String p1 = pair.substring(0, 1) + rules.get(pair);
                    String p2 = rules.get(pair) + pair.substring(1, 2);

                    next.put(p1, next.getOrDefault(p1, 0L) + pattern.get(pair));
                    next.put(p2, next.getOrDefault(p2, 0L) + pattern.get(pair));
                } else
                    next.put(pair, next.getOrDefault(pair, 0L) + pattern.get(pair));
            }
            pattern = next;
            long length = 1;
            for (String pair : pattern.keySet()) {
                //  System.out.print(pair + ":" + pattern.get(pair) + ", ");
                length += pattern.get(pair);
            }
            //System.out.println("\n" + s + ": " + length);
        }

        Map<Integer, Long> count = new HashMap<>();
        count.put(lastchar, 1L);
        for (String pair : pattern.keySet()) {
            int c = pair.charAt(0);
            count.put(c, count.getOrDefault(c, 0L) + pattern.get(pair));
        }

        long max = 0;
        long min = Long.MAX_VALUE;

        for (Integer s : count.keySet()) {
            long sc = count.get(s);
            if (sc < min) min = sc;
            if (sc > max) max = sc;
        }
        System.out.println("min " + min + ", max " + max);
        return max - min;
    }

}
