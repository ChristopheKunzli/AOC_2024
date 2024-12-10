import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 {
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    public void solve(String filename) {
        parse(Utils.getStrings(filename));
        System.out.println("Day 1");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        for (String line : lines) {
            String[] numbers = line.split(" {3}");
            list1.add(Integer.parseInt(numbers[0]));
            list2.add(Integer.parseInt(numbers[1]));
        }
    }

    private int part1() {
        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);
        int total = 0;
        for (int i = 0; i < list1.size(); ++i) {
            total += Math.abs(list1.get(i) - list2.get(i));
        }
        return total;
    }

    private int part2() {
        Map<Integer, Integer> numberCount = new HashMap<>();
        for (int num : list2) {
            numberCount.put(num, numberCount.getOrDefault(num, 0) + 1);
        }

        int sum = 0;
        for (int num : list1) {
            sum += num * numberCount.getOrDefault(num, 0);
        }
        return sum;
    }
}
