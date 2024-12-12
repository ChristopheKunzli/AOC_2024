import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 {
    static class Region {
        char type;
        int area;
        int perimeter;
        Set<String> visited;

        public Region(char type) {
            this.area = 0;
            this.perimeter = 0;
            this.type = type;
        }

        int countSides() {
            int sides = 0;
            return sides;
        }


        public String toString() {
            return type + " Area: " + area + ", Perimeter: " + perimeter;
        }
    }

    List<Region> regions = new ArrayList<>();

    public void solve(String filePath) {
        List<String> lines = Utils.getStrings(filePath);
        System.out.println("Day 12:");
        System.out.println("Part 1: " + part1(parse(lines)));
        System.out.println("Part 2: " + part2(parse(lines)));
    }

    private char[][] parse(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); ++i) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    private void exploreRegion(int x, int y, char[][] grid, Region region, Set<String> visited) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || grid[x][y] != region.type) {
            if (visited.contains(x + "," + y)) {
                return;
            }
            ++region.perimeter;
            return;
        }

        ++region.area;
        grid[x][y] = '#';
        visited.add(x + "," + y);

        exploreRegion(x + 1, y, grid, region, visited);
        exploreRegion(x - 1, y, grid, region, visited);
        exploreRegion(x, y + 1, grid, region, visited);
        exploreRegion(x, y - 1, grid, region, visited);
    }

    private int part1(char[][] grid) {
        int price = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] != '#') {
                    Region region = new Region(grid[i][j]);
                    Set<String> visited = new HashSet<>();
                    exploreRegion(i, j, grid, region, visited);
                    region.visited = visited;
                    regions.add(region);
                    price += region.area * region.perimeter;
                }
            }
        }
        return price;
    }

    private int part2(char[][] grid) {
        int price = 0;

        for (var region : regions) {
            int sides = region.countSides();
            price += region.area * sides;
        }

        return price;
    }
}
