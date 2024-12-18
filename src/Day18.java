import java.util.*;

public class Day18 {
    private final static int DIMENSION = 71;
    private final static int BYTES = 1024;

    record Coord(int x, int y) {
        public String toString() {
            return x + "," + y;
        }
    }

    private final List<Coord> coords = new ArrayList<>();

    public void solve(String filePath) {
        parse(Utils.getStrings(filePath));
        System.out.println("Day 18");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        for (String line : lines) {
            String[] parts = line.split(",");
            coords.add(new Coord(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
    }

    private boolean isInBounds(Coord c) {
        return c.x >= 0 && c.y >= 0 && c.x < DIMENSION && c.y < DIMENSION;
    }

    private int bfs(char[][] grid) {
        Queue<Coord> queue = new LinkedList<>();
        queue.add(new Coord(0, 0));
        Set<String> seen = new HashSet<>();
        seen.add(queue.peek().toString());

        int pathLength = 0;
        while (!queue.isEmpty()) {
            Queue<Coord> nextStep = new LinkedList<>();
            while (!queue.isEmpty()) {
                Coord current = queue.poll();
                if (current.x == DIMENSION - 1 && current.y == DIMENSION - 1) {
                    return pathLength;
                }

                Coord left = new Coord(current.x - 1, current.y);
                Coord right = new Coord(current.x + 1, current.y);
                Coord up = new Coord(current.x, current.y - 1);
                Coord down = new Coord(current.x, current.y + 1);

                if (isInBounds(left) && !seen.contains(left.toString()) && grid[left.y][left.x] == '.')
                    nextStep.add(left);
                if (isInBounds(right) && !seen.contains(right.toString()) && grid[right.y][right.x] == '.')
                    nextStep.add(right);
                if (isInBounds(up) && !seen.contains(up.toString()) && grid[up.y][up.x] == '.') nextStep.add(up);
                if (isInBounds(down) && !seen.contains(down.toString()) && grid[down.y][down.x] == '.')
                    nextStep.add(down);

                seen.add(left.toString());
                seen.add(right.toString());
                seen.add(up.toString());
                seen.add(down.toString());
            }
            queue = nextStep;
            ++pathLength;
        }

        return -1;//if no path found
    }

    private char[][] initGrid(int height, int width, int steps) {
        char[][] grid = new char[height][width];
        for (int i = 0; i < steps; ++i) {
            grid[coords.get(i).y][coords.get(i).x] = '#';
        }
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] != '#') grid[i][j] = '.';
            }
        }
        return grid;
    }

    private int part1() {
        return bfs(initGrid(DIMENSION, DIMENSION, BYTES));
    }

    private String part2() {
        char[][] grid = initGrid(DIMENSION, DIMENSION, BYTES);
        int count = BYTES;
        while (bfs(grid) != -1) {
            grid[coords.get(count).y][coords.get(count).x] = '#';
            ++count;
        }
        return coords.get(count - 1).toString();
    }
}
