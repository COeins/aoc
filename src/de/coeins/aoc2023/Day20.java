package de.coeins.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Day20 implements Day<Long> {
	@Override
	public Long task1(String[] in) {
		Map<String, Module> modules = parse(in);

		int[] counters = new int[3];
		List<Pulse> pulses = new LinkedList<>();
		for (long run = 0; run < 1000; run++) {
			pulses.add(new Pulse(null, "broadcaster", 0));
			counters[0]++;
			while (!pulses.isEmpty()) {
				Pulse p = pulses.get(0);
				pulses.remove(0);
				Module mod = modules.get(p.target);
				if (mod != null) {
					int out = mod.pulse(pulses, p);
					counters[out] += mod.outputNames.size();
				}
			}
		}
		log((Object) counters);
		return (long) counters[0] * counters[1];
	}

	@Override
	public Long task2(String[] in) {
		Map<String, Module> modules = parse(in);

		String output = "rx";
		List<String> trigger;
		do {
			trigger = new ArrayList<>();
			for (String k : modules.keySet()) {
				Module m = modules.get(k);
				if (m.outputNames.contains(output))
				{
					trigger.add(m.inputs.size() < 2 ? m.inputs.keySet().iterator().next() : k);
				}
			}
			if (trigger.isEmpty())
				return -1L;
			output = trigger.get(0);
		} while (trigger.size() < 2);

		log("Trigger modules:", trigger);

		List<Pulse> pulses = new LinkedList<>();
		List<Long> triggerTime = new ArrayList<>(trigger.size());
		for (long run = 1; run < 100000; run++) {
			pulses.add(new Pulse(null, "broadcaster", 0));
			while (!pulses.isEmpty()) {
				Pulse p = pulses.get(0);
				pulses.remove(0);
				Module mod = modules.get(p.target);
				if (mod != null) {
					int out = mod.pulse(pulses, p);
					if (out == 0 && trigger.contains(mod.name)) {
						log("Trigger fired at", run, mod.name, mod.inputs);
						triggerTime.add(run);
						trigger.remove(mod.name);
						if (trigger.isEmpty())
							return MathUtils.lcmAll(triggerTime);
					}
				}
			}
		}
		throw new RuntimeException("Nothing found within 100000 iterations");
	}

	private Map<String, Module> parse(String[] in) {
		Map<String, Module> modules = new HashMap<>();
		for (String l : in) {
			String[] split = l.split("[->]");
			String name = split[0].substring(split[0].charAt(0) == 'b' ? 0 : 1).trim();
			List<String> outputs = Arrays.stream(split[2].split(",")).map(String::trim).toList();
			Type t;
			switch (split[0].charAt(0)) {
				case 'b' -> t = Type.BROAD;
				case '%' -> t = Type.FFLOP;
				case '&' -> t = Type.CONJU;
				default -> throw new RuntimeException("Nö: " + split[0]);
			}
			modules.put(name, new Module(t, name, outputs));
		}
		for (Module m : modules.values())
			m.initialize(modules);
		log(modules.values());
		return modules;
	}

	class Module {
		private final Type type;
		private final String name;
		private final List<String> outputNames;
		private final List<Module> outputs;
		private final Map<String, Integer> inputs;
		private int state = 0;

		private long loop = 0;

		Module(Type type, String name, List<String> outputNames) {
			this.type = type;
			this.name = name;
			this.outputNames = outputNames;
			outputs = new ArrayList<>();
			inputs = new HashMap<>();
			if (type == Type.BROAD)
				loop = 1;
		}

		public void initialize(Map<String, Module> others) {
			for (Module o : others.values()) {
				if (outputNames.contains(o.name))
					outputs.add(o);
				if (o.outputNames.contains(name))
					inputs.put(o.name, 0);
			}
		}

		public int pulse(List<Pulse> pulses, Pulse in) {
			switch (type) {
				case BROAD -> {
					for (String outputName : outputNames)
						pulses.add(new Pulse(this, outputName, in.value));
					return in.value;
				}
				case FFLOP -> {
					if (in.value == 1)
						return 2;
					else
						state = 1 - state;
					for (String outputName : outputNames)
						pulses.add(new Pulse(this, outputName, state));
					return state;
				}
				case CONJU -> {
					inputs.put(in.source.name, in.value);
					state = 1;
					for (int s : inputs.values())
						state *= s;
					state = 1 - state;
					for (String outputName : outputNames)
						pulses.add(new Pulse(this, outputName, state));
					return state;
				}
				default -> throw new RuntimeException("Invalid type");
			}
		}

		@Override
		public String toString() {
			return type.name() + " " + name + " " + inputs.keySet() + " -> " + outputNames.toString();
		}
	}

	enum Type {
		BROAD, FFLOP, CONJU
	}

	record Pulse(Module source, String target, int value) {
	}
}
