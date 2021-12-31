package de.coeins.aoc21;

public class Day3 implements Day {

    public long task1(String input) {
        String[] inputlines = input.split("\n");
        int numbits = inputlines[0].length();
        Integer[] bitcount = new Integer[numbits];
        for (int i = 0; i < numbits; i++) {
            bitcount[i] = 0;
        }

        for (String line : inputlines) {
            for (int i = 0; i < numbits; i++) {
                if (line.charAt(i) == '1') bitcount[i]++;
                else bitcount[i]--;
            }
        }

        int g = 0;
        int e = 0;
        for (int i = 0; i < numbits; i++) {
            g = (g << 1) + (bitcount[i] > 0 ? 1 : 0);
            e = (e << 1) + (bitcount[i] < 0 ? 1 : 0);
        }
        System.out.println("g: " + g + " e: " + e);
        return g * e;
    }

    public long task2(String input) {

        String[] inputlines = input.split("\n");
        int numbits = inputlines[0].length();
        int o = 0;
        int c = 0;
        String o_prefix = "";
        String c_prefix = "";
        for (int i = 0; i < numbits; i++) {
            int bitcount_o = 0;
            int bitcount_c = 0;
            int matches_o = 0;
            int matches_c = 0;
            String last_o = null;
            String last_c = null;
            for (String line : inputlines) {
                if (line.startsWith(o_prefix)) {
                    matches_o++;
                    last_o = line;
                    if (line.charAt(i) == '1') bitcount_o++;
                    else bitcount_o--;
                }
                if (line.startsWith(c_prefix)) {
                    matches_c++;
                    last_c = line;
                    if (line.charAt(i) == '1') bitcount_c++;
                    else bitcount_c--;
                }
            }
            if (matches_o == 1 && last_o.charAt(i) != (bitcount_o >= 0 ? '1' : '0')) {
                throw new RuntimeException("WTF just happened here?");
            }

            if (matches_c == 1) {
                System.out.println("only one match at " + c_prefix);
                bitcount_c = last_c.charAt(i) == '1' ? -1 : 1;
            }

            if (matches_c < 1 || matches_o < 1) {
                throw new RuntimeException("No more matches...");
            }

            o = (o << 1) + (bitcount_o >= 0 ? 1 : 0);
            o_prefix = o_prefix + (bitcount_o >= 0 ? "1" : "0");
            c = (c << 1) + (bitcount_c < 0 ? 1 : 0);
            c_prefix = c_prefix + (bitcount_c < 0 ? "1" : "0");

        }
        System.out.println("o: " + o + " c: " + c);
        return o * c;
    }
}
