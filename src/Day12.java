import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 {
    static class Region {
        char type;
        int area;
        int perimeter;
        Set<String> coordinates;

        int gridHeight;
        int gridWidth;

        public Region(char type, int height, int width) {
            this.area = 0;
            this.perimeter = 0;
            this.type = type;
            this.gridHeight = height;
            this.gridWidth = width;
        }

        int countSides() {
            int sides = 0;

            for (String cell : coordinates) {
                String[] parts = cell.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);

                boolean isTopLeftCorner = !coordinates.contains((x - 1) + "," + y) && !coordinates.contains(x + "," + (y - 1));
                boolean isTopRightCorner = !coordinates.contains((x + 1) + "," + y) && !coordinates.contains(x + "," + (y - 1));
                boolean isBottomLeftCorner = !coordinates.contains((x - 1) + "," + y) && !coordinates.contains(x + "," + (y + 1));
                boolean isBottomRightCorner = !coordinates.contains((x + 1) + "," + y) && !coordinates.contains(x + "," + (y + 1));

                boolean isInnerTopLeftCorner = (!coordinates.contains((x - 1) + "," + (y - 1)) && coordinates.contains((x - 1) + "," + y) && coordinates.contains(x + "," + (y - 1)));
                boolean isInnerTopRightCorner = (!coordinates.contains((x + 1) + "," + (y - 1)) && coordinates.contains((x + 1) + "," + y) && coordinates.contains(x + "," + (y - 1)));
                boolean isInnerBottomLeftCorner = (!coordinates.contains((x - 1) + "," + (y + 1)) && coordinates.contains((x - 1) + "," + y) && coordinates.contains(x + "," + (y + 1)));
                boolean isInnerBottomRightCorner = (!coordinates.contains((x + 1) + "," + (y + 1)) && coordinates.contains((x + 1) + "," + y) && coordinates.contains(x + "," + (y + 1)));

                if (isTopLeftCorner) ++sides;
                if (isTopRightCorner) ++sides;
                if (isBottomLeftCorner) ++sides;
                if (isBottomRightCorner) ++sides;

                if (isInnerTopLeftCorner) ++sides;
                if (isInnerTopRightCorner) ++sides;
                if (isInnerBottomLeftCorner) ++sides;
                if (isInnerBottomRightCorner) ++sides;
            }

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
        System.out.println("Part 2: " + part2());
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
                    Region region = new Region(grid[i][j], grid.length, grid[i].length);
                    Set<String> visited = new HashSet<>();
                    exploreRegion(i, j, grid, region, visited);
                    region.coordinates = visited;
                    regions.add(region);
                    price += region.area * region.perimeter;
                }
            }
        }
        return price;
    }

    private int part2() {
        int price = 0;

        for (var region : regions) {
            price += region.area * region.countSides();
        }

        return price;
    }
}
