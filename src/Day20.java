import java.util.*;

public class Day20 {
    int[][] grid;
    int startX, startY;
    int endX, endY;

    public void solve(String filePath) {
        grid = parse(Utils.getStrings(filePath));
        getDistances(grid, startX, startY, new HashSet<>());
        System.out.println("Day 19");
        System.out.println("Part 1: " + part1());
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

    private int solution (int[][] grid, int size, int minSavedTime){
        Map<Integer, Integer> count = new HashMap<>();

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] > 0) {
                    for (int height = -size; height <= size; ++height) {
                        for (int width = -size; width <= size; ++width) {
                            int distance = Math.abs(height) + Math.abs(width);
                            if (distance <= size) {
                                int timeSaved = secondsSaved(grid, j, i, width, height) - distance;
                                if (timeSaved > 0) {
                                    count.put(timeSaved, count.getOrDefault(timeSaved, 0) + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        int sum = 0;

        for (var e : count.entrySet()) {
            if (e.getKey() >= minSavedTime) {
                sum += e.getValue();
            }
        }

        return sum;
    }

    private int part1() {
        return solution(grid, 2, 100);
    }

    private int part2() {
       return solution(grid, 20, 100);
    }
}
