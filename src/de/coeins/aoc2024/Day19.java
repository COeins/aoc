package de.coeins.aoc2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class Day19 implements de.coeins.aoc2023.Day<Long> {
	@Override
	public Long task1(String[] in) {
		Pattern pattern = Pattern.compile("^(" + join("|", Arrays.stream(in[0].split(",")).map(String::trim).toList()) + ")+$");
		int count = 0;
		for (int i = 2; i < in.length; i++) {
			if (pattern.matcher(in[i]).find()) {
				log(in[i], "looks good");
				count++;
			} else
				log(in[i], "seems impossible");
		}
		return (long) count;
	}

	@Override
	public Long task2(String[] in) {
		List<String> patterns = Arrays.stream(in[0].split(",")).map(String::trim).toList();
		long count = 0;
		for (int i = 2; i < in.length; i++) {
			Map<Integer, Long> lookup = new HashMap<>();
			long c = countVariants(in[i], patterns, 0, lookup);
			log(in[i], "has", c, "variations");
			count += c;
		}
		return count;
	}

	private long countVariants(String search, List<String> patterns, int startPos, Map<Integer, Long> lookup) {
		if (startPos == search.length())
			return 1;
		if (lookup.containsKey(startPos))
			return lookup.get(startPos);
		long variants = 0;
		nextPat:
		for (String p : patterns) {
			int l = p.length();
			if (l > search.length() - startPos)
				continue;

			for (int j = 0; j < l; j++)
				if (search.charAt(startPos + j) != p.charAt(j))
					continue nextPat;
			variants += countVariants(search, patterns, startPos + l, lookup);
		}
		lookup.put(startPos, variants);
		return variants;
	}

	private String join(String delim, List<?> list) {
		StringBuilder out = new StringBuilder();
		for (Object o : list) {
			out.append(o.toString()).append(delim);
		}
		return out.substring(0, out.length() - delim.length());
	}
}
