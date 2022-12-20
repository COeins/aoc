package de.coeins.aoc22;

import java.util.Set;

public class Main {

	public static void main(String[] args) {
		// run both tasks with all inputs
		int tasks = 1 | 2;
		int inputs = -1;

		// run only selected tasks/inputs
		// tasks = 2;
		// inputs = 0;

		// all days
		// Set<Integer> days = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19);

		// only a single day
		Set<Integer> days = Set.of(20);

		int s = 0;
		int e = 0;
		for (int d : days) {
			Day day = Inputs.days.get(d);
			String[] taskInputs = Inputs.tasks.get(d);
			int imin, imax;
			if (inputs < 0) {
				imin = 0;
				imax = taskInputs.length;
			} else {
				imin = inputs;
				imax = inputs + 1;
			}

			if ((tasks & 1) > 0) {
				Object[] solutions1 = Inputs.solutionsTask1.get(d);

				for (int i = imin; i < imax; i++) {
					log("### Running day " + d + " task 1 input " + i);
					long start = System.currentTimeMillis();
					try {
						Object result = day.task1(taskInputs[i].split("\n"));
						log("### Finished run with " + (result.equals(solutions1[i]) ? "correct" : "incorrect") + " result of " + result
								+ " after " + (((double) (System.currentTimeMillis() - start)) / 1000) + " " + "seconds\n");
						if (result.equals(solutions1[i]))
							s++;
						else
							e++;
					} catch (Exception ex) {
						ex.printStackTrace();
						log("### Finished run with " + ex.getClass().getSimpleName() + ": " + ex.getMessage()
								+ " after " + (((double) (System.currentTimeMillis() - start)) / 1000) + " seconds\n");
						e++;
					}
				}
			}

			log("");
			if ((tasks & 2) > 0) {
				Object[] solutions2 = Inputs.solutionsTask2.get(d);
				for (int i = imin; i < imax; i++) {
					log("### Running day " + d + " task 2 input " + i);
					long start = System.currentTimeMillis();
					try {
						Object result = day.task2(taskInputs[i].split("\n"));
						log("### Finished run with " + (result.equals(solutions2[i]) ? "correct" : "incorrect") + " result of " + result
								+ " after " + (((double) (System.currentTimeMillis() - start)) / 1000) + " seconds\n");
						if (result.equals(solutions2[i]))
							s++;
						else
							e++;
					} catch (Exception ex) {
						ex.printStackTrace();
						log("### Finished run with " + ex.getClass().getSimpleName() + ": " + ex.getMessage()
								+ " after " + (((double) (System.currentTimeMillis() - start)) / 1000) + " seconds\n");
						e++;
					}
				}
			}
		}

		log("### Run complete. Successful: " + s + " Errors: " + e);
	}

	private static void log(String text) {
		System.out.println(text);
	}
}
