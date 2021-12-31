package de.coeins.aoc21;

import java.util.ArrayList;
import java.util.Collections;

public class Day10 implements Day {
    private static int ROUND = 1;
    private static int SQUARE = 2;
    private static int CURLY = 3;
    private static int ANGLE = 4;

    public long task1(String input) {

        String[] lines = input.split("\n");
        int score = 0;
        for (String line : lines) {
            Integer[] stack = new Integer[line.length()];
            int stackpos = 0;
            //System.out.println("line: "+line+ " ");
            //String depth = "";
            out:
            for (int i = 0; i < line.length(); i++) {
                //depth = depth + stackpos;
                switch (line.charAt(i)) {
                    case '(':
                        stack[stackpos++] = ROUND;
                        break;
                    case '[':
                        stack[stackpos++] = SQUARE;
                        break;
                    case '{':
                        stack[stackpos++] = CURLY;
                        break;
                    case '<':
                        stack[stackpos++] = ANGLE;
                        break;
                    case ')':
                        if (stackpos > 0 && stack[stackpos - 1] == ROUND) stackpos--;
                        else {
//                            System.out.println("line: " + line + "\nPos: " + i + " Expected " + stack[stackpos - 1] + ", found " + ROUND);
                            score += 3;
                            break out;
                        }
                        break;
                    case ']':
                        if (stackpos > 0 && stack[stackpos - 1] == SQUARE) stackpos--;
                        else {
//                            System.out.println("line: " + line + "\nPos: " + i + " Expected " + stack[stackpos - 1] + ", found " + SQUARE);
                            score += 57;
                            break out;
                        }
                        break;
                    case '}':
                        if (stackpos > 0 && stack[stackpos - 1] == CURLY) stackpos--;
                        else {
//                            System.out.println("line: " + line + "\nPos: " + i + " Expected " + stack[stackpos - 1] + ", found " + CURLY);

                            score += 1197;
                            break out;
                        }
                        break;
                    case '>':
                        if (stackpos > 0 && stack[stackpos - 1] == ANGLE) stackpos--;
                        else {
//                            System.out.println("line: " + line + "\nPos: " + i + " Expected " + stack[stackpos - 1] + ", found " + ANGLE);

                            score += 25137;
                            break out;
                        }
                        break;
                    default:
                        throw new RuntimeException("Unexpected syntax error");
                }
            }
            //System.out.println(depth);
        }
        return score;
    }

    public long task2(String input) {

        String[] lines = input.split("\n");
        ArrayList<Long> scores = new ArrayList<>();
        next:
        for (String line : lines) {
            Integer[] stack = new Integer[line.length()];
            int stackpos = 0;
            for (int i = 0; i < line.length(); i++) {
                //depth = depth + stackpos;
                switch (line.charAt(i)) {
                    case '(':
                        stack[stackpos++] = ROUND;
                        break;
                    case '[':
                        stack[stackpos++] = SQUARE;
                        break;
                    case '{':
                        stack[stackpos++] = CURLY;
                        break;
                    case '<':
                        stack[stackpos++] = ANGLE;
                        break;
                    case ')':
                        if (stackpos > 0 && stack[stackpos - 1] == ROUND) stackpos--;
                        else continue next;
                        break;
                    case ']':
                        if (stackpos > 0 && stack[stackpos - 1] == SQUARE) stackpos--;
                        else continue next;
                        break;
                    case '}':
                        if (stackpos > 0 && stack[stackpos - 1] == CURLY) stackpos--;
                        else continue next;
                        break;
                    case '>':
                        if (stackpos > 0 && stack[stackpos - 1] == ANGLE) stackpos--;
                        else continue next;
                        break;
                    default:
                        throw new RuntimeException("Unexpected syntax error");
                }
            }
            long score = 0;
            System.out.print(line + " ");
            for (int i = stackpos - 1; i >= 0; i--) {
                score = score * 5 + stack[i];
                switch (stack[i]) {
                    case 1:
//                        System.out.print(")");
                        break;
                    case 2:
//                        System.out.print("]");
                        break;
                    case 3:
//                        System.out.print("}");
                        break;
                    case 4:
//                        System.out.print(">");
                        break;
                }
            }
            scores.add(score);
            //System.out.println(" score: " + score);

        }
        Collections.sort(scores);
        return scores.get(scores.size() / 2);
    }
}
