package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class TaskList<T, R> {
	private final List<T> tasks;
	private final Map<T, R> results;
	private final Processor<T, R> proc;

	private int deph = 0;
	private boolean recurse = true;
	private int maxDepth = Integer.MAX_VALUE;

	TaskList(Processor<T, R> proc) {
		this.tasks = new ArrayList<>();
		this.results = new HashMap<>();
		this.proc = proc;
	}

	public R run(T start) {
		if (results.containsKey(start))
			return results.get(start);

		if (!tasks.contains(start))
			tasks.add(start);

		int startPos = tasks.indexOf(start);

		do {
			for (int i = startPos; i < tasks.size(); i++) {
				deph = 0;
				recurse = true;
				T t = tasks.get(i);
				if (!results.containsKey(t)) {
					runTask(t);
				}
			}
			tasks.removeAll(results.keySet());
			startPos = 0;
		} while (!results.containsKey(start));

		return results.get(start);
	}

	public Optional<R> recurse(T next) {
		if (results.containsKey(next))
			return Optional.of(results.get(next));

		if (!tasks.contains(next)) {
			if (recurse && deph < maxDepth - 100)
				try {
					deph++;
					Optional<R> r = runTask(next);
					deph--;
					return r;
				} catch (StackOverflowError e) {
					recurse = false;
					maxDepth = deph < maxDepth ? deph : maxDepth;
				}
			tasks.add(next);

		}
		// task being queued, we'll have to wait;
		return Optional.empty();
	}

	private Optional<R> runTask(T task) {
		Optional<R> res = proc.run(this, task);
		if (res.isPresent()) {
			results.put(task, res.get());
		}
		return res;
	}

	public TaskList<T, R> setMaxDepth(int deph) {
		maxDepth = deph;
		return this;
	}

	public interface Processor<T, R> {
		Optional<R> run(TaskList<T, R> tasklist, T task);
	}
}
