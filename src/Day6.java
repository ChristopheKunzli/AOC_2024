import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private char getNextDirection(Point p) {
        return switch (p.direction) {
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
                currentPos.direction = getNextDirection(currentPos);
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

    class Job implements Runnable {
        int x, y;
        private char[][] jobGrid;
        private final Point jobCurrentPos;
        boolean hasLoop = false;

        public Job(int x, int y, Point initialPos) {
            this.x = x;
            this.y = y;
            jobCurrentPos = initialPos;
        }

        private boolean isInBounds(int x, int y) {
            return x >= 0 && y >= 0 && x < jobGrid.length && y < jobGrid[x].length;
        }

        private boolean hasLoop() {
            Set<String> visited = new HashSet<>();

            while (true) {
                if (visited.contains(jobCurrentPos.toString())) {
                    return true;
                }

                visited.add(jobCurrentPos.toString());
                int nextX = jobCurrentPos.x, nextY = jobCurrentPos.y;
                switch (jobCurrentPos.direction) {
                    case '^' -> --nextX;
                    case 'v' -> ++nextX;
                    case '<' -> --nextY;
                    case '>' -> ++nextY;
                }

                if (!isInBounds(nextX, nextY)) {
                    return false;
                }

                char next = jobGrid[nextX][nextY];
                if (next == '#') {
                    jobCurrentPos.direction = getNextDirection(jobCurrentPos);
                } else {
                    jobCurrentPos.x = nextX;
                    jobCurrentPos.y = nextY;
                    jobGrid[jobCurrentPos.x][jobCurrentPos.y] = 'X';
                }

            }
        }

        @Override
        public void run() {
            jobGrid = new char[initialGrid.length][];
            for (int i = 0; i < initialGrid.length; ++i) {
                jobGrid[i] = initialGrid[i].clone();
            }
            jobGrid[x][y] = '#';
            jobGrid[initialPos.x][initialPos.y] = 'X';
            hasLoop = hasLoop();
        }
    }

    public int part2() {
        List<Job> jobs = new ArrayList<>();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == 'X') {
                    jobs.add(new Job(i, j, new Point(initialPos.x, initialPos.y, initialPos.direction)));
                    executor.execute(jobs.getLast());
                }
            }
        }
        executor.close();
        int sum = 0;
        for (Job job : jobs) {
            if (job.hasLoop) ++sum;
        }
        return sum;
    }
}
