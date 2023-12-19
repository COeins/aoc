package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Day19 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		Map<String, Workflow> workflows = parseWorkflows(in);
		List<Map<Category, Integer>> parts = parseParts(in, workflows.size() + 1);

		long sum = 0;
		for (Map<Category, Integer> p : parts) {
			if (evaluatePart(workflows, p))
				for (Integer propVal : p.values())
					sum += propVal;
		}
		return sum;
	}

	@Override
	public Long task2(String[] in) {
		int min = 1;
		int max = 4000;

		Map<String, Workflow> workflows = parseWorkflows(in);

		Map<Category, Set<Integer>> bordersCollect = Map.of(
				Category.x, new HashSet<>(),
				Category.m, new HashSet<>(),
				Category.a, new HashSet<>(),
				Category.s, new HashSet<>()
		);

		for (Set<Integer> b : bordersCollect.values())
			b.add(min);

		for (Workflow w : workflows.values())
			for (Rule r : w.rules)
				if (r.compare != null)
					bordersCollect.get(r.property).add(r.compare > 0 ? r.compare + 1 : -r.compare);

		List<Integer> bordersX = bordersCollect.get(Category.x).stream().sorted().toList();
		List<Integer> bordersM = bordersCollect.get(Category.m).stream().sorted().toList();
		List<Integer> bordersA = bordersCollect.get(Category.a).stream().sorted().toList();
		List<Integer> bordersS = bordersCollect.get(Category.s).stream().sorted().toList();

		long sum = 0;
		long areas = 0;
		long matching = 0;
		long steps = (long) bordersX.size() * bordersM.size() * bordersA.size() * bordersS.size() / 100;
		for (int ix = 0; ix < bordersX.size(); ix++)
			for (int im = 0; im < bordersM.size(); im++)
				for (int ia = 0; ia < bordersA.size(); ia++)
					for (int is = 0; is < bordersS.size(); is++) {
						areas++;
						Map<Category, Integer> p = Map.of(
								Category.x, bordersX.get(ix),
								Category.m, bordersM.get(im),
								Category.a, bordersA.get(ia),
								Category.s, bordersS.get(is));
						if (evaluatePart(workflows, p)) {
							matching++;
							long dx = getOrDefault(bordersX, ix + 1, max + 1) - bordersX.get(ix);
							long dm = getOrDefault(bordersM, im + 1, max + 1) - bordersM.get(im);
							long da = getOrDefault(bordersA, ia + 1, max + 1) - bordersA.get(ia);
							long ds = getOrDefault(bordersS, is + 1, max + 1) - bordersS.get(is);
							sum += dx * dm * da * ds;
						}
						if (areas % steps == 0)
							log(areas / steps, "% completed");
					}

		log(areas, "areas checked, with", matching, "matching");
		return sum;
	}

	private boolean evaluatePart(Map<String, Workflow> workflows, Map<Category, Integer> part) {
		Workflow wf = workflows.get("in");
		nextWf:
		while (true) {
			for (Rule r : wf.rules) {
				if (r.property == null
						|| r.compare > 0 && part.get(r.property) > r.compare
						|| r.compare < 0 && part.get(r.property) < (-r.compare)) {
					if (r.accept)
						return true;
					if (r.reject)
						return false;
					wf = r.nextWf;
					continue nextWf;
				}
			}
			throw new RuntimeException("No rule matched");
		}
	}

	private Map<String, Workflow> parseWorkflows(String[] in) {
		Map<String, Workflow> workflows = new HashMap<>();
		for (int i = 0; in[i].length() > 0; i++) {
			String[] split = in[i].split("[}{]");
			List<Rule> rules = new ArrayList<>();
			for (String r : split[1].split(",")) {
				String[] r1 = r.split("[<>:]");
				Category property = null;
				Integer value = null;
				String decision = null;
				if (r1.length == 1) {
					decision = r1[0];
				} else if (r1.length == 3) {
					property = Category.valueOf(r1[0]);
					value = Integer.parseInt(r1[1]);
					if (r.charAt(1) == '<')
						value *= -1;
					else if (r.charAt(1) != '>')
						throw new RuntimeException("Operator not <>");
					decision = r1[2];
				} else {
					throw new RuntimeException("Parse Error");
				}
				if (decision.equals("A"))
					rules.add(new Rule(property, value, null, null, true, false));
				else if (decision.equals("R"))
					rules.add(new Rule(property, value, null, null, false, true));
				else
					rules.add(new Rule(property, value, decision, null, false, false));
			}
			workflows.put(split[0], new Workflow(split[0], rules));
		}

		for (Workflow w : workflows.values()) {
			List<Rule> newRules = new ArrayList<>();
			for (Rule r : w.rules)
				newRules.add(new Rule(r.property, r.compare, r.next, workflows.get(r.next), r.accept, r.reject));
			w.rules.removeAll(w.rules);
			w.rules.addAll(newRules);
		}
		return workflows;
	}

	private List<Map<Category, Integer>> parseParts(String[] in, int start) {
		List<Map<Category, Integer>> parts = new ArrayList<>();
		for (int i = start; i < in.length; i++) {
			String[] p = in[i].split("[=,}]");
			parts.add(Map.of(Category.x, Integer.parseInt(p[1]),
					Category.m, Integer.parseInt(p[3]),
					Category.a, Integer.parseInt(p[5]),
					Category.s, Integer.parseInt(p[7])));
		}
		return parts;
	}

	private int getOrDefault(List<Integer> list, int i, int defaultVal) {
		if (i >= list.size())
			return defaultVal;
		else
			return list.get(i);
	}

	record Workflow(String name, List<Rule> rules) {
	}

	record Rule(Category property, Integer compare, String next, Workflow nextWf, boolean accept, boolean reject) {
	}

	enum Category {x, m, a, s}

}
