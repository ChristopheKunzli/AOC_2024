import java.util.*;

public class Day5 {
    private final List<Pair> pairs = new ArrayList<>();
    private final List<List<Integer>> numbers = new ArrayList<>();
    private final List<List<Integer>> incorrectPrints = new ArrayList<>();

    private record Pair(int left, int right) {
    }

    public void solve(String fileName) {
        parse(Utils.getStrings(fileName));
        System.out.println("Day 5");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> text) {
        boolean flag = true;
        for (String string : text) {
            if (string.isEmpty()) {
                flag = false;
                continue;
            }

            if (flag) {
                String[] parts = string.split("\\|");

                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);

                pairs.add(new Pair(a, b));
            } else {
                List<Integer> list = new ArrayList<>();
                for (String s : string.split(",")) {
                    list.add(Integer.parseInt(s));
                }
                numbers.add(list);
            }
        }
    }

    private boolean isCorrectOrder(List<Integer> pagesPrinting) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < pagesPrinting.size(); ++i) {
            map.put(pagesPrinting.get(i), i);
        }

        for (Pair pair : pairs) {
            if (map.containsKey(pair.left) && map.containsKey(pair.right) && map.get(pair.left) > map.get(pair.right)) {
                return false;
            }
        }

        return true;
    }

    private int part1() {
        int sum = 0;

        for (var pagesPrinting : numbers) {
            if (isCorrectOrder(pagesPrinting)) {
                sum += pagesPrinting.get(pagesPrinting.size() / 2);
            } else {
                incorrectPrints.add(pagesPrinting);
            }
        }

        return sum;
    }

    private Map<Integer, Set<Integer>> getGraph() {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (Pair pair : pairs) {
            graph.putIfAbsent(pair.left, new HashSet<>());
            graph.putIfAbsent(pair.right, new HashSet<>());
            graph.get(pair.left).add(pair.right);
        }
        return graph;
    }

    private int part2() {
        int sum = 0;
        Map<Integer, Set<Integer>> graph = getGraph();

        for (var pagesPrinting : incorrectPrints) {
            //sort pagesPrinting using the graph as a comparator
            pagesPrinting.sort((a, b) -> {
                if (graph.containsKey(a) && graph.get(a).contains(b)) {
                    return -1;
                }
                if (graph.containsKey(b) && graph.get(b).contains(a)) {
                    return 1;
                }
                return 0;
            });

            sum += pagesPrinting.get(pagesPrinting.size() / 2);
        }

        return sum;
    }
}
