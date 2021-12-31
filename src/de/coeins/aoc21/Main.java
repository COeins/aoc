package de.coeins.aoc21;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<Integer> days = new HashSet<>();
        // run both tasks with all inputs
        int tasks = 1 | 2;
        int inputs = -1;

//        // run only selected tasks/inputs
//        tasks = 2;
//        inputs = 0;

        // all days
//         Collections.addAll(days, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25);

        // only fast days
        Collections.addAll(days, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 24, 25);

        // only slow days
//        Collections.addAll(days, 19, 22, 23);

        // only a single day
//        Collections.addAll(days, 20);

        // TODOs:
        //  - day 20 Task2 example does not match answer given in description

        int s = 0;
        int e = 0;
        for (int d : days) {
            Day day = Inputs.days.get(d);
            String[] taskInputs = Inputs.tasks.get(d);
            int imin, imax;
            if (inputs < 0)
            {
                imin = 0;
                imax= taskInputs.length;
            }
            else
            {
                imin = inputs;
                imax = inputs+1;
            }

            if ((tasks & 1) > 0) {
                Long[] solutions1 = Inputs.solutionsTask1.get(d);

                for (int i = imin; i < imax; i++) {
                    log("### Running day " + d + " task 1 input " + i);
                    long start = System.currentTimeMillis();
                    long result = day.task1(taskInputs[i]);
                    long end = System.currentTimeMillis();
                    log("### Finished run with " + (result == solutions1[i] ? "correct" : "incorrect")
                            + " result of " + result + " after " + (((double) (end - start)) / 1000) + " seconds\n");
                    if (result == solutions1[i]) s++;
                    else e++;
                }
            }

            if ((tasks & 2) > 0) {
                Long[] solutions2 = Inputs.solutionsTask2.get(d);
                for (int i = imin; i < imax; i++) {
                    log("### Running day " + d + " task 2 input " + i);
                    long start = System.currentTimeMillis();
                    long result = day.task2(taskInputs[i]);
                    long end = System.currentTimeMillis();
                    log("### Finished run with " + (result == solutions2[i] ? "correct" : "incorrect")
                            + " result of " + result + " after " + (((double) (end - start)) / 1000) + " seconds\n");
                    if (result == solutions2[i]) s++;
                    else e++;
                }
            }
        }

        log("### Run complete. Successful: " + s + " Errors: " + e);
    }

    private static void log(String text) {
        System.out.println(text);
    }
}
