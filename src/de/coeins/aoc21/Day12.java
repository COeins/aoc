package de.coeins.aoc21;

import java.util.*;

public class Day12 implements Day {
    public long task1(String input) {
        String[] lines = input.split("\n");
        Map<String, Node> map = new HashMap<>();
        Node start = null;
        for (String line : lines) {
            String[] names = line.split("-");
            if (!map.containsKey(names[0]))
                map.put(names[0], new Node(names[0]));
            if (!map.containsKey(names[1]))
                map.put(names[1], new Node(names[1]));
            map.get(names[0]).addNeighbour(map.get(names[1]), true);
            if (map.get(names[0]).start) start = map.get(names[0]);
            if (map.get(names[1]).start) start = map.get(names[1]);
        }

        if (start == null)
            throw new RuntimeException("No start node");

        List<List<Node>> paths = travel(start, new LinkedList<>(), false);
        for (List<Node> path : paths) {
            for (Node node : path) {
//                System.out.print(node.name+",");
            }
//            System.out.println();
        }

        return paths.size();
    }

    public long task2(String input) {

            String[] lines = input.split("\n");
            Map<String, Node> map = new HashMap<>();
            Node start = null;
            for (String line : lines) {
                String[] names = line.split("-");
                if (!map.containsKey(names[0]))
                    map.put(names[0], new Node(names[0]));
                if (!map.containsKey(names[1]))
                    map.put(names[1], new Node(names[1]));
                map.get(names[0]).addNeighbour(map.get(names[1]), true);
                if (map.get(names[0]).start) start = map.get(names[0]);
                if (map.get(names[1]).start) start = map.get(names[1]);            }

            if (start == null)
                throw new RuntimeException("No start node");

            List<List<Node>> paths = travel(start, new LinkedList<>(), true);
            for (List<Node> path : paths) {
                for (Node node : path) {
//                    System.out.print(node.name+",");
                }
//                System.out.println();
            }

            return paths.size();
    }


    private static List<List<Node>> travel(Node current, List<Node> visited, boolean twice) {
        List<List<Node>> paths = new LinkedList<>();
        visited.add(current);

        if (current.end) {
            paths.add(visited);
            return paths;
        }

        for (Node next : current.neighbours) {
            if (next.large || !visited.contains(next))
                paths.addAll(travel(next, new LinkedList<>(visited), twice));
            else if (twice && !next.start)
                paths.addAll(travel(next, new LinkedList<>(visited), false));
        }

        return paths;
    }

    private static class Node {
        private String name;
        private boolean large;
        private boolean end;
        private boolean start;

        private List<Node> neighbours;

        public Node(String name) {
            this.name = name;
            this.large = (Character.isUpperCase(name.charAt(0)));
            this.start = "start".equals(name);
            this.end = "end".equals(name);
            neighbours = new LinkedList<>();
        }

        public void addNeighbour(Node next, boolean recursive) {
            this.neighbours.add(next);
            if (recursive) next.addNeighbour(this, false);
        }
    }
}
