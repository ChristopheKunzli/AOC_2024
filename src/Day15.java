import java.util.ArrayList;
import java.util.List;

public class Day15 {
    private char[][] grid;
    private List<Character> moves = new ArrayList<>();
    int robotX, robotY;

    public void solve(String filePath) {
        parse(Utils.getStrings(filePath));
        System.out.println("Day 15");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
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
    }

    private void printGrid() {
        for (var line : grid) {
            for (var c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private void move(char c) {
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

        // do nothing if there is a wall on the way
        if (grid[robotY + dy][robotX + dx] == '#') return;

        //move one step if there is an empty space
        if (grid[robotY + dy][robotX + dx] == '.') {
            grid[robotY + dy][robotX + dx] = grid[robotY][robotX];
            grid[robotY][robotX] = '.';
            robotX += dx;
            robotY += dy;
            return;
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

    private long computeGPS() {
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

    private long part1() {
        for (char c : moves) {
            move(c);
            //System.out.println(c);
            //printGrid();
            //System.out.println();
        }
        //printGrid();
        return computeGPS();
    }

    private long part2() {
        return 0;
    }
}
