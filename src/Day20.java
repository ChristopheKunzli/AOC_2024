import java.util.*;

public class Day20 {

    int startX, startY;
    int endX, endY;

    public void solve(String filePath) {
        System.out.println("Day 19");
        System.out.println("Part 1: " + part1(parse(Utils.getStrings(filePath))));
        System.out.println("Part 2: " + part2());
    }

    private int[][] parse(List<String> lines) {
        int[][] grid = new int[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); ++i) {
            char[] line = lines.get(i).toCharArray();
            for (int j = 0; j < line.length; ++j) {
                if (line[j] == '#') {
                    grid[i][j] = -1;
                } else if (line[j] == 'S') {
                    startX = j;
                    startY = i;
                } else if (line[j] == 'E') {
                    endX = j;
                    endY = i;
                }
            }
        }
        return grid;
    }

    private boolean isInBounds(int[][] grid, int x, int y) {
        return x >= 0 && y >= 0 && x < grid[0].length && y < grid.length;
    }

    private boolean hasNeighbour(int[][] grid, int x, int y, int dx, int dy) {
        if (!isInBounds(grid, x + dx, y + dy)) return false;
        return grid[y + dy][x + dx] != -1;
    }


    private void getDistances(int[][] grid, int x, int y, int currentDistance, Set<String> visited) {
        if (!isInBounds(grid, x, y) || grid[y][x] == -1 || visited.contains(x + ";" + y)) return;

        grid[y][x] = currentDistance;
        visited.add(x + ";" + y);

        getDistances(grid, x + 1, y, grid[y][x] + 1, visited);
        getDistances(grid, x - 1, y, grid[y][x] + 1, visited);
        getDistances(grid, x, y + 1, grid[y][x] + 1, visited);
        getDistances(grid, x, y - 1, grid[y][x] + 1, visited);

        for (int[] dir : new int[][]{{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}}) {
            if (hasNeighbour(grid, x, y, dir[0], dir[1])) {
                grid[y][x] = Math.min(grid[y + dir[1]][x + dir[0]] + 1, grid[y][x]);
            }
        }
    }

    private void displayGrid(int[][] grid) {
        for (var l : grid) {
            for (int e : l) {
                if (e == -1) {
                    System.out.print("  #");
                } else {
                    System.out.printf("%3s", ("" + e));
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private int part1(int[][] grid) {
        Set<String> visited = new HashSet<>();
        getDistances(grid, startX, startY, 0, visited);
        displayGrid(grid);
        Map<Integer, Integer> count = new HashMap<>();

        return 0;
    }

    private int part2() {
        return 0;
    }
}
