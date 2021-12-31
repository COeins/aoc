package de.coeins.aoc21;

public class Day2 implements Day {
    public long task1(String input) {
        String[] inputlines = input.split("\n");
        int h = 0;
        int d = 0;
        for (String line : inputlines) {
            String[] parse = line.split(" ");
            switch (parse[0]) {
                case "forward":
                    h += Integer.valueOf(parse[1]);
                    break;
                case "backward":
                    h -= Integer.valueOf(parse[1]);
                    break;
                case "up":
                    d -= Integer.valueOf(parse[1]);
                    break;
                case "down":
                    d += Integer.valueOf(parse[1]);
                    break;
                default:
                    throw new RuntimeException("unknown command " + line);
            }
        }
        System.out.println("Pos d: " + d + ", h: " + h);
        return d * h;

    }

    public long task2(String input) {
        String[] inputlines = input.split("\n");
        int h = 0;
        int d = 0;
        int a = 0;
        for (String line : inputlines) {
            String[] parse = line.split(" ");
            switch (parse[0]) {
                case "forward":
                    h += Integer.valueOf(parse[1]);
                    d += a * Integer.valueOf(parse[1]);
                    break;
                case "backward":
                    h -= Integer.valueOf(parse[1]);
                    d -= a * Integer.valueOf(parse[1]);
                    break;
                case "up":
                    a -= Integer.valueOf(parse[1]);
                    break;
                case "down":
                    a += Integer.valueOf(parse[1]);
                    break;
                default:
                    throw new RuntimeException("unknown command " + line);
            }
        }
        System.out.println("Pos d: " + d + ", h: " + h);
        return d * h;
    }
}
