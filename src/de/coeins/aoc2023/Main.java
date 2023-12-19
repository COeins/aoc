package de.coeins.aoc2023;

import java.util.List;

public class Main {

	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36;1m";

	private static boolean logEnabled = true;

	public static void main(String[] args) {
		// run all days
		//List<Integer> days = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);

		// only a single day
		List<Integer> days = List.of(19);

		// run both tasks
		List<Integer> tasks = List.of(1, 2);

		// -1: run all inputs, >=0 run only specified input
		int inputs = -1;

		// 0: no task logging, 1: only log sample tasks; 2: log all tasks
		int verbosity = 2;

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

			for (int task : tasks) {
				Object[] solutions = task == 1 ? Inputs.solutionsTask1.get(d) : Inputs.solutionsTask2.get(d);

				for (int i = imin; i < imax; i++) {
					log(CYAN + "### Running day " + d + " task " + task + " input " + i + RESET);
					long start = System.currentTimeMillis();
					try {
						logEnabled = verbosity > 0 && verbosity > 1 || i < taskInputs.length - 1;
						Object result = task == 1 ? day.task1(taskInputs[i].split("\n")) : day.task2(taskInputs[i].split("\n"));
						logEnabled = true;
						String correct;
						String col;
						if (result.equals(solutions[i])) {
							correct = "correct";
							col = GREEN;
						} else {
							correct = "incorrect";
							col = RED;
						}

						log(col + "### Finished run with " + correct + " result of " + result
								+ " after " + (((double) (System.currentTimeMillis() - start)) / 1000) + " " + "seconds\n" + RESET);
						if (result.equals(solutions[i]))
							s++;
						else
							e++;
					} catch (Exception ex) {
						logEnabled = true;
						ex.printStackTrace(System.out);
						log(PURPLE + "### Finished run with " + ex.getClass().getSimpleName() + ": " + ex.getMessage()
								+ " after " + (((double) (System.currentTimeMillis() - start)) / 1000) + " seconds\n" + RESET);
						e++;
					}
				}

				log("");
			}
		}

		log(CYAN + "### Run complete. Successful: " + s + " Errors: " + e + RESET);
	}

	public static void log(String text) {
		if (logEnabled)
			System.out.println(text);
	}
}
