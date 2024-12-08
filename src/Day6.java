import java.util.*;

public class Day6 {
    static class Point {
        int x, y;
        char direction;

        Point(int x, int y, char direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return x + "," + y + direction;
        }
    }

    private char[][] grid;
    private char[][] initialGrid;
    private Point currentPos;
    private Point initialPos;

    public void solve(String fileName) {
        parse(Utils.getStrings(fileName));
        System.out.println("Day 6");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        initialGrid = new char[lines.size()][lines.getFirst().length()];
        grid = new char[lines.size()][lines.getFirst().length()];

        for (int i = 0; i < lines.size(); ++i) {
            grid[i] = lines.get(i).toCharArray();
            initialGrid[i] = lines.get(i).toCharArray();
            int index = lines.get(i).indexOf('^');
            if (index != -1) {
                initialPos = new Point(i, index, '^');
                currentPos = new Point(i, index, '^');
                grid[i][index] = 'X';
            }
        }
    }

    private char getNextDirection() {
        return switch (currentPos.direction) {
            case '^' -> '>';
            case '>' -> 'v';
            case 'v' -> '<';
            case '<' -> '^';
            default -> 0;
        };
    }

    private int part1() {
        while (true) {
            int nextX = currentPos.x, nextY = currentPos.y;
            switch (currentPos.direction) {
                case '^' -> --nextX;
                case 'v' -> ++nextX;
                case '<' -> --nextY;
                case '>' -> ++nextY;
            }

            if (nextX < 0 || nextY < 0 || nextX >= grid.length || nextY >= grid[nextX].length) {
                break;
            }

            char next = grid[nextX][nextY];
            if (next == '#') {
                currentPos.direction = getNextDirection();
            } else {
                currentPos.x = nextX;
                currentPos.y = nextY;
                grid[currentPos.x][currentPos.y] = 'X';
            }
        }

        int count = 0;
        for (var l : grid) {
            for (char v : l) {
                if (v == 'X') {
                    ++count;
                }
            }
        }
        return count;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[x].length;
    }

    private boolean hasLoop(char[][] gridCopy) {
        Set<String> visited = new HashSet<>();

        while (true) {
            if (visited.contains(currentPos.toString())) {
                return true;
            }

            visited.add(currentPos.toString());
            int nextX = currentPos.x, nextY = currentPos.y;
            switch (currentPos.direction) {
                case '^' -> --nextX;
                case 'v' -> ++nextX;
                case '<' -> --nextY;
                case '>' -> ++nextY;
            }

            if (!isInBounds(nextX, nextY)) {
                return false;
            }

            char next = gridCopy[nextX][nextY];
            if (next == '#') {
                currentPos.direction = getNextDirection();
            } else {
                currentPos.x = nextX;
                currentPos.y = nextY;
                gridCopy[currentPos.x][currentPos.y] = 'X';
            }

        }
    }

    public int part2() {
        int count = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == 'X') {
                    currentPos.x = initialPos.x;
                    currentPos.y = initialPos.y;
                    currentPos.direction = initialPos.direction;
                    char[][] gridCopy = new char[grid.length][grid[i].length];
                    for (int k = 0; k < gridCopy.length; ++k) {
                        gridCopy[k] = initialGrid[k].clone();
                    }
                    gridCopy[i][j] = '#';
                    gridCopy[initialPos.x][initialPos.y] = 'X';
                    if (hasLoop(gridCopy)) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }
}
