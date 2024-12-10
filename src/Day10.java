import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {
    int[][] grid;

    public void solve(String filePath) {
        grid = parse(Utils.getStrings(filePath));
        System.out.println("Day 10");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private int[][] parse(List<String> lines) {
        int[][] grid = new int[lines.size()][lines.getFirst().length()];

        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); ++j) {
                grid[i][j] = line.charAt(j) - '0';
            }
        }

        return grid;
    }

    private int explore(int[][] grid, int i, int j, int current, Set<String> set) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return 0;
        }
        if (grid[i][j] != current) return 0;
        if (grid[i][j] == 9) {
            set.add(i + ";" + j);
            return 1;
        }

        int left = explore(grid, i, j - 1, current + 1, set);
        int right = explore(grid, i, j + 1, current + 1, set);
        int up = explore(grid, i - 1, j, current + 1, set);
        int down = explore(grid, i + 1, j, current + 1, set);

        return left + right + up + down;
    }

    int part2Sum = 0;

    private int part1() {
        int sum = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == 0) {
                    Set<String> set = new HashSet<>();
                    part2Sum += explore(grid, i, j, 0, set);
                    sum += set.size();
                }
            }
        }
        return sum;
    }

    private int part2() {
        return part2Sum;
    }
}
