import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2 {
    private String inputPath;

    public void solve(String file) {
        inputPath = file;
        System.out.println("Day 2");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private List<List<Integer>> parseInput(List<String> input) {
        List<List<Integer>> reports = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(" ");
            List<Integer> report = new ArrayList<>();
            for (String part : parts) {
                report.add(Integer.parseInt(part));
            }
            reports.add(report);
        }
        return reports;
    }

    private int part1() {
        List<List<Integer>> reports = parseInput(Utils.getStrings(inputPath));
        int count = 0;

        for (List<Integer> report : reports) {
            if (isSafe(report)) {
                count++;
            }
        }

        return count;
    }

    private int part2() {
        List<List<Integer>> reports = parseInput(Utils.getStrings(inputPath));

        int count = 0;
        for (var report : reports) {
            for (int i = 0; i < report.size(); i++) {
                List<Integer> copy = new ArrayList<>(report);
                copy.remove(i);
                if (isSafe(copy)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private boolean isSafe(List<Integer> report) {
        for (int i = 0; i < report.size() - 1; ++i) {
            int diff = Math.abs((report.get(i) - report.get(i + 1)));

            if (diff > 3 || diff < 1) {
                return false;
            }
        }
        Object[] sorted = report.toArray(), reverseSorted = report.toArray();
        Arrays.sort(sorted);
        Arrays.sort(reverseSorted, Collections.reverseOrder());

        return Arrays.equals(sorted, report.toArray()) || Arrays.equals(reverseSorted, report.toArray());
    }
}
