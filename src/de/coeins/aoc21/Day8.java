package de.coeins.aoc21;

public class Day8 implements Day {

    // abcdefg
    private static int N0 = 0x77; // 1110111
    private static int N1 = 0x12; // 0010010
    private static int N2 = 0x5d; // 1011101
    private static int N3 = 0x5b; // 1011011
    private static int N4 = 0x3a; // 0111010
    private static int N5 = 0x6b; // 1101011
    private static int N6 = 0x6f; // 1101111
    private static int N7 = 0x52; // 1010010
    private static int N8 = 0x7f; // 1111111
    private static int N9 = 0x7b; // 1111011

    private static int[] code = new int[]{N0, N1, N2, N3, N4, N5, N6, N7, N8, N9};

    private static int SA = 0x40;
    private static int SB = 0x20;
    private static int SC = 0x10;
    private static int SD = 0x08;
    private static int SE = 0x04;
    private static int SF = 0x02;
    private static int SG = 0x01;

    private static int FIST_CHAR = 97;

    public long task1(String input) {
        String[] inputlines = input.split("\n");
        int counter = 0;
        for (String line : inputlines) {
            String[] halves = line.split(" \\| ");
            String[] digits = halves[1].split(" ");
            for (String digit : digits) {
                if (digit.length() == 2
                        || digit.length() == 3
                        || digit.length() == 4
                        || digit.length() == 7)
                    counter++;
            }
        }
        return counter;
    }

    public long task2(String input) {
        String[] inputlines = input.split("\n");
        int sum = 0;
        for (String line : inputlines) {
            String[] halves = line.split(" \\| ");
            String[] hints = halves[0].split(" ");
            String[] digits = halves[1].split(" ");

            int[] count = new int[7];
            int[] mapping = new int[7];
            String one = "";
            String seven = "";
            String four = "";

            for (String hint : hints) {
                for (int i = 0; i < hint.length(); i++) {
                    count[hint.charAt(i) - FIST_CHAR]++;
                }
                if (hint.length() == 2) one = hint;
                if (hint.length() == 3) seven = hint;
                if (hint.length() == 4) four = hint;
            }

            // first segments = unique numbers
            mapping[arrayPos(count, 6)] = SB;
            mapping[arrayPos(count, 4)] = SE;
            mapping[arrayPos(count, 9)] = SF;

            // c = 1 - f
            mapping[subtract(one, new int[]{arrayPos(mapping, SF) + FIST_CHAR}).charAt(0) - FIST_CHAR] = SC;

            // a = 7 - cf
            mapping[subtract(seven, new int[]{arrayPos(mapping, SC) + FIST_CHAR,
                    arrayPos(mapping, SF) + FIST_CHAR}).charAt(0) - FIST_CHAR] = SA;

            // d = 4 - bcf
            mapping[subtract(four, new int[]{arrayPos(mapping, SB) + FIST_CHAR,
                    arrayPos(mapping, SC) + FIST_CHAR,
                    arrayPos(mapping, SF) + FIST_CHAR}).charAt(0) - FIST_CHAR] = SD;

            // g = last one standing
            mapping[arrayPos(mapping, 0)]= SG;

            int result = 0;
            for (int i = 0; i < digits.length; i++) {
                int dts = 0;
                for (int j = 0; j < digits[i].length(); j++) {
                    dts += mapping[digits[i].charAt(j)-FIST_CHAR];
                }
                int decoded = arrayPos(code, dts);
                result = result * 10 + decoded;
            }
            //System.out.println(result+", "+halves[1]);
            sum += result;
        }
        return sum;
    }

    private static int arrayPos(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }
        throw new RuntimeException(value + " not found in array");
    }

    private static String subtract(String in, int[] removeChars) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < in.length(); i++) {
            int c = in.charAt(i);
            try {
                arrayPos(removeChars, c);
            } catch (RuntimeException e) {
                out.append((char) c);
            }
        }
        return out.toString();
    }


}
