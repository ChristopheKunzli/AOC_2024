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

    private void getDistances(int[][] grid, int startX, int startY, Set<String> visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 0});
        visited.add(startX + ";" + startY);

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1];
            int distance = current[2];

            grid[y][x] = distance;

            for (int[] dir : directions) {
                int newX = x + dir[0], newY = y + dir[1];
                if (isInBounds(grid, newX, newY) && grid[newY][newX] != -1 && !visited.contains(newX + ";" + newY)) {
                    queue.add(new int[]{newX, newY, distance + 1});
                    visited.add(newX + ";" + newY);
                }
            }
        }
    }

    private int secondsSaved(int[][] grid, int x, int y, int dx, int dy) {
        if (!isInBounds(grid, x + dx, y + dy) || grid[y + dy][x + dx] == -1) return 0;
        return grid[y][x] - grid[y + dy][x + dx];
    }

    private int part1(int[][] grid) {
        Set<String> visited = new HashSet<>();
        getDistances(grid, endX, endY, visited);
        Map<Integer, Integer> count = new HashMap<>();

        int[][] cheatPoints = new int[][]{{0, 2}, {0, -2}, {2, 0}, {-2, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, 1}};

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] > 0) {
                    for (int[] cheat : cheatPoints) {
                        int timeSaved = secondsSaved(grid, j, i, cheat[0], cheat[1]) - 2;
                        if (timeSaved > 0) {
                            count.put(timeSaved, count.getOrDefault(timeSaved, 0) + 1);
                        }
                    }
                }
            }
        }

        int sum = 0;

        for (var e : count.entrySet()) {
            if (e.getKey() >= 100) {
                sum += e.getValue();
            }
        }

        return sum;
    }

    private int part2() {
        return 0;
    }
}
