import java.util.ArrayList;
import java.util.List;

public class Day5 {
    private List<Pair> pairs = new ArrayList<>();
    private List<List<Integer>> numbers = new ArrayList<>();

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
        for (int i = 0; i < text.size(); ++i) {
            if (text.get(i).isEmpty()) {
                flag = false;
                continue;
            }

            if (flag) {
                String[] parts = text.get(i).split("\\|");

                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);

                pairs.add(new Pair(a, b));
            } else {
                List<Integer> list = new ArrayList<>();
                for (String s : text.get(i).split(",")) {
                    list.add(Integer.parseInt(s));
                }
                numbers.add(list);
            }
        }
    }

    private int part1() {
        return 0;
    }

    private int part2() {
        return 0;
    }
}
