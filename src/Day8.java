import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {
    record Coordinate(int x, int y) {
    }

    Map<Character, List<Coordinate>> map = new HashMap<>();

    char[][] grid;
    char[][] grid2;

    public void solve(String filePath) {
        parse(Utils.getStrings(filePath));
        System.out.println("Day 8");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        grid = new char[lines.size()][lines.getFirst().length()];
        grid2 = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            char[] chars = line.toCharArray();
            for (int j = 0; j < chars.length; ++j) {
                char c = chars[j];
                grid[i][j] = c;
                grid2[i][j] = c;
                if (c == '.') {
                    continue;
                }
                if (!map.containsKey(c)) {
                    map.put(c, new ArrayList<>());
                }
                map.get(c).add(new Coordinate(i, j));
            }
        }
    }

    private boolean isInBound(int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    private int countAntinodes(char[][] grid) {
        int count = 0;
        for (char[] chars : grid) {
            for (char c : chars) {
                System.out.print(c);
                if (c == '#') {
                    ++count;
                }
            }
            System.out.println();
        }
        return count;
    }

    private int part1() {
        for (char c : map.keySet()) {
            List<Coordinate> coordinates = map.get(c);
            for (int i = 0; i < coordinates.size(); ++i) {
                for (int j = i + 1; j < coordinates.size(); ++j) {
                    Coordinate c1 = coordinates.get(i);
                    Coordinate c2 = coordinates.get(j);

                    int xDiff = (c1.x - c2.x);
                    int yDiff = (c1.y - c2.y);

                    int x1 = c1.x + xDiff;
                    int y1 = c1.y + yDiff;
                    int x2 = c2.x - xDiff;
                    int y2 = c2.y - yDiff;

                    if (isInBound(x1, y1)) {
                        grid[x1][y1] = '#';
                    }
                    if (isInBound(x2, y2)) {
                        grid[x2][y2] = '#';
                    }
                }
            }
        }

        return countAntinodes(grid);
    }

    private int part2() {
        for (char c : map.keySet()) {
            List<Coordinate> coordinates = map.get(c);
            for (int i = 0; i < coordinates.size(); ++i) {
                for (int j = i + 1; j < coordinates.size(); ++j) {
                    Coordinate c1 = coordinates.get(i);
                    Coordinate c2 = coordinates.get(j);

                    grid2[c1.x][c1.y] = '#';
                    grid2[c2.x][c2.y] = '#';

                    int xDiff = (c1.x - c2.x);
                    int yDiff = (c1.y - c2.y);

                    int x1 = c1.x + xDiff;
                    int y1 = c1.y + yDiff;
                    int x2 = c2.x - xDiff;
                    int y2 = c2.y - yDiff;

                    while (isInBound(x1, y1)) {
                        grid2[x1][y1] = '#';
                        x1 += xDiff;
                        y1 += yDiff;
                    }

                    while (isInBound(x2, y2)) {
                        grid2[x2][y2] = '#';
                        x2 -= xDiff;
                        y2 -= yDiff;
                    }
                }
            }
        }

        return countAntinodes(grid2);
    }
}
