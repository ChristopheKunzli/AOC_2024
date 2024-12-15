import java.util.ArrayList;
import java.util.List;

public class Day15 {
    private final List<Character> moves = new ArrayList<>();
    int robotX, robotY;

    public void solve(String filePath) {
        List<String> lines = Utils.getStrings(filePath);
        System.out.println("Day 15");
        System.out.println("Part 1: " + part1(parse(lines)));
        System.out.println("Part 2: " + part2());
    }

    private char[][] parse(List<String> lines) {
        char[][] grid = null;
        List<char[]> rows = new ArrayList<>();
        boolean flag = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                flag = true;
                grid = new char[rows.size()][rows.getFirst().length];
                for (int i = 0; i < rows.size(); i++) {
                    grid[i] = rows.get(i);
                }
                continue;
            }

            if (flag) {
                for (char c : line.toCharArray()) {
                    moves.add(c);
                }
            } else {
                rows.add(line.toCharArray());
                if (line.contains("@")) {
                    robotX = line.indexOf("@");
                    robotY = rows.size() - 1;
                }
            }
        }
        return grid;
    }

    private void move(char c, char[][] grid) {
        int dx = 0, dy = 0;
        switch (c) {
            case '^':
                dy = -1;
                break;
            case 'v':
                dy = 1;
                break;
            case '<':
                dx = -1;
                break;
            case '>':
                dx = 1;
                break;
        }

        int x = robotX + dx, y = robotY + dy;
        while (grid[y][x] != '.') {
            if (grid[y][x] == '#') return;
            x += dx;
            y += dy;
        }

        while (robotX != x || robotY != y) {
            grid[y][x] = grid[y - dy][x - dx];
            x -= dx;
            y -= dy;
        }
        grid[robotY][robotX] = '.';
        robotX += dx;
        robotY += dy;
    }

    private long computeGPS(char[][] grid) {
        long sum = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == 'O') {
                    sum += (i * 100L) + j;
                }
            }
        }
        return sum;
    }

    private long part1(char[][] grid) {
        for (char c : moves) {
            move(c, grid);
        }
        return computeGPS(grid);
    }

    private long part2() {
        return 0;
    }
}
