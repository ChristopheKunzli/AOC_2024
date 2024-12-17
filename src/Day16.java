import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Day16 {
    private Coord start;
    private Coord end;

    public void solve(String filePath) {
        List<String> lines = Utils.getStrings(filePath);
        System.out.println("Day 16");
        System.out.println("Part 1: " + part1(parse(lines)));
        System.out.println("Part 2: " + part2());
    }

    private char[][] parse(List<String> lines) {
        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); ++i) {
            String l = lines.get(i);
            grid[i] = l.toCharArray();
            if (l.contains("S")) start = new Coord(i, l.indexOf('S'));
            if (l.contains("E")) end = new Coord(i, l.indexOf('E'));
        }
        return grid;
    }

    record Coord(int y, int x) {
        Coord getNext(char direction) {
            return switch (direction) {
                case '^' -> new Coord(y - 1, x);
                case 'v' -> new Coord(y + 1, x);
                case '<' -> new Coord(y, x - 1);
                case '>' -> new Coord(y, x + 1);
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            };
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) return true;
            if (!(other instanceof Coord(int y1, int x1))) return false;
            return x1 == x && y1 == y;
        }

        @Override
        public int hashCode() {
            return x * 31 + y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }
    }

    static class Path {
        Coord current;
        char direction;

        Set<Coord> path;

        public Path(Coord c, char d) {
            current = c;
            direction = d;
            path = new HashSet<>();
        }

        public Path(Coord c, char d, Set<Coord> soFar) {
            this(c, d);
            path.addAll(soFar);
        }
    }

    private char getLeft(char d) {
        return switch (d) {
            case '^' -> '<';
            case '<' -> 'v';
            case 'v' -> '>';
            case '>' -> '^';
            default -> throw new IllegalStateException("Unexpected value: " + d);
        };
    }

    private char getRight(char d) {
        return switch (d) {
            case '^' -> '>';
            case '>' -> 'v';
            case 'v' -> '<';
            case '<' -> '^';
            default -> throw new IllegalStateException("Unexpected value: " + d);
        };
    }

    private long explore(char[][] grid, long score, Path p) {
        if (p.current.y < 0
                || p.current.x < 0
                || p.current.y >= grid.length
                || p.current.x >= grid[0].length
                || grid[p.current.y][p.current.x] == '#') {
            return Long.MAX_VALUE;
        }

        if (p.current.equals(end)) {
            return score;
        }

        Coord nextLeft = p.current.getNext(getLeft(p.direction));
        boolean canGoLeft = grid[nextLeft.y][nextLeft.x] != '#';

        Coord nextRight = p.current.getNext(getRight(p.direction));
        boolean canGoRight = grid[nextRight.y][nextRight.x] != '#';

        Coord next = p.current.getNext(p.direction);
        boolean canGoForward = grid[next.y][next.x] != '#';

        long left = Long.MAX_VALUE;
        long right = Long.MAX_VALUE;
        long forward = Long.MAX_VALUE;

        if (canGoLeft && !p.path.contains(nextLeft)) {
            Path leftPath = new Path(nextLeft, getLeft(p.direction), p.path);
            leftPath.path.add(nextLeft);
            left = explore(grid, score + 1001, leftPath);
        }

        if (canGoRight && !p.path.contains(nextRight)) {
            Path rightPath = new Path(nextRight, getRight(p.direction), p.path);
            rightPath.path.add(nextRight);
            right = explore(grid, score + 1001, rightPath);
        }

        if (canGoForward && !p.path.contains(next)) {
            Path forwardPath = new Path(next, p.direction, p.path);
            forwardPath.path.add(next);
            forward = explore(grid, score + 1, forwardPath);
        }

        return Math.min(left, Math.min(right, forward));
    }

    private long part1(char[][] labyrinth) {
        for(var li : labyrinth){
            //System.out.println(li);
        }
        return -1;//explore(labyrinth, 0, new Path(start, '>'));
    }

    private int part2() {
        return 0;
    }
}
