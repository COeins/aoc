package de.coeins.aoc21;

import java.util.Arrays;
import java.util.Collections;

public class Day9 implements Day {
    public long task1(String input) {

        Integer[][] inputmap = convertToArray(input);

        int sum = 0;
        int count = 0;

        for (int i = 1; i < inputmap.length - 1; i++) {
            for (int j = 1; j < inputmap[i].length - 1; j++) {
                if (
                        inputmap[i][j] < inputmap[i - 1][j]
                                && inputmap[i][j] < inputmap[i + 1][j]
                                && inputmap[i][j] < inputmap[i][j - 1]
                                && inputmap[i][j] < inputmap[i][j + 1]
                ) {
                    sum += inputmap[i][j];
                    count++;
                    //System.out.println("Found " + inputmap[i][j] + " at " + i + ", " + j);
                }
            }
        }
        return sum + count;
    }

    public long task2(String input) {
        Integer[][] inputmap = convertToArray(input);
        Integer[][] basins = new Integer[inputmap.length][inputmap[0].length];

        Integer[] sizes = new Integer[1024];
        int nextbase = 1;

        for (int i = 0; i < inputmap.length; i++) {
            for (int j = 0; j < inputmap[i].length; j++) {

                if (inputmap[i][j] == 9) {
                    basins[i][j] = 0;
                } else {
                    int copybase = 0;
                    if (basins[i][j - 1] == 0 && basins[i - 1][j] == 0) // new basin
                    {
                        basins[i][j] = nextbase++;
                        sizes[basins[i][j]] = 0;
                    } else if (basins[i][j - 1] != 0 && basins[i - 1][j] == 0) // left basin
                    {
                        basins[i][j] = basins[i][j - 1];
                    } else if (basins[i][j - 1] == 0 && basins[i - 1][j] != 0) // upper basin
                    {
                        basins[i][j] = basins[i - 1][j];
                    } else if (basins[i][j - 1] != 0 && basins[i - 1][j] != 0
                            && basins[i][j - 1].equals(basins[i - 1][j])) // joint basin
                    {
                        basins[i][j] = basins[i][j - 1];
                    } else if (basins[i][j - 1] != 0 && basins[i - 1][j] != 0
                            && !basins[i][j - 1].equals(basins[i - 1][j])) // disjoint basin
                    {
                        basins[i][j] = basins[i][j - 1];
                        copybase = basins[i - 1][j];

                        for (int k = 0; k <= i; k++) {
                            for (int l = 0; l < inputmap[k].length; l++) {
                                if (basins[k][l] != null && basins[k][l].equals(copybase))
                                    basins[k][l] = basins[i][j];
                            }
                        }
                    }
                    sizes[basins[i][j]]++;
                    if (copybase > 0) {
                        sizes[basins[i][j]] += sizes[copybase];
                        sizes[copybase] = 0;
                    }
                }
            }
        }

        //convertToString(basins);

        Integer[] newsizes = new Integer[nextbase - 1];
        newsizes[0] = 0;
        for (int i = 1; i < nextbase; i++) {
            //System.out.println("base " + i + " has size " + sizes[i]);
            newsizes[i - 1] = sizes[i];
        }
        Arrays.sort(newsizes, Collections.reverseOrder());
        return newsizes[0] * newsizes[1] * newsizes[2];
    }

    private static Integer[][] convertToArray(String input) {

        String[] inputlines = input.split("\n");
        Integer[][] lines = new Integer[inputlines.length + 2][inputlines[0].length() + 2];

        for (int j = 0; j <= inputlines[0].length() + 1; j++) {
            lines[0][j] = 9;
            lines[inputlines.length + 1][j] = 9;
        }

        for (int i = 1; i <= inputlines.length; i++) {
            lines[i][0] = 9;
            lines[i][inputlines[0].length() + 1] = 9;
            for (int j = 1; j <= inputlines[0].length(); j++) {
                lines[i][j] = inputlines[i - 1].charAt(j - 1) - 48;
            }
        }

        return lines;
    }

    private static void convertToString(Integer[][] input) {

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(String.format("%03d ", input[i][j]));
            }
            System.out.println();
        }
    }
}
