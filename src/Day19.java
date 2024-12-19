import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day19 {
    private List<String> goal = new ArrayList<>();
    private Set<String> towels = new HashSet<>();

    public void solve(String filePath) {
        parse(Utils.getStrings(filePath));
        System.out.println("Day 19");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines){

    }

    private int part1() {
        return 0;
    }

    private int part2() {
        return 0;
    }
}
