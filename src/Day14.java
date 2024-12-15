import java.util.ArrayList;
import java.util.List;

public class Day14 {
    static class Robot implements Cloneable {
        int x, y;
        int vx, vy;

        Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        @Override
        public Robot clone() {
            try {
                Robot clone = (Robot) super.clone();
                clone.x = this.x;
                clone.y = this.y;
                clone.vx = this.vx;
                clone.vy = this.vy;
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    public void solve(String filePath) {
        List<Robot> robots = parse(Utils.getStrings(filePath));
        List<Robot> robots2 = parse(Utils.getStrings(filePath));

        System.out.println("Day 14");
        System.out.println("Part 1: " + part1(robots));
        System.out.println("Part 2: " + part2(robots2) + " (Check results folder)");
    }

    private List<Robot> parse(List<String> lines) {
        List<Robot> robots = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            String[] position = parts[0].replace("p=", "").split(",");
            String[] velocity = parts[1].replace("v=", "").split(",");

            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            int vx = Integer.parseInt(velocity[0]);
            int vy = Integer.parseInt(velocity[1]);
            robots.add(new Robot(x, y, vx, vy));
        }
        return robots;
    }

    private final static int HEIGHT = 103;
    private final static int WIDTH = 101;

    private void move(List<Robot> robots) {
        for (Robot robot : robots) {
            robot.x = (robot.x + robot.vx + WIDTH) % WIDTH;
            robot.y = (robot.y + robot.vy + HEIGHT) % HEIGHT;
        }
    }

    private long part1(List<Robot> robots) {
        for (int i = 0; i < 100; ++i) {
            move(robots);
        }

        int[][] grid = new int[HEIGHT][WIDTH];
        for (Robot robot : robots) {
            grid[robot.y][robot.x] += 1;
        }

        return computeSecurityScore(grid);
    }

    private long computeSecurityScore(int[][] grid) {
        long topLeft = 0, topRight = 0, bottomLeft = 0, bottomRight = 0;

        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                boolean isTop = i < HEIGHT / 2;
                boolean isLeft = j < WIDTH / 2;
                if (i == HEIGHT / 2 || j == WIDTH / 2) {
                } else if (grid[i][j] != 0) {
                    if(isTop && isLeft) {
                        topLeft += grid[i][j];
                    } else if(isTop && !isLeft) {
                        topRight += grid[i][j];
                    } else if(!isTop && isLeft) {
                        bottomLeft += grid[i][j];
                    } else {
                        bottomRight += grid[i][j];
                    }
                }
            }
        }

        return topLeft * topRight * bottomLeft * bottomRight;
    }

    private int part2(List<Robot> robots) {
        for (int i = 1; i <= 10000; ++i) {
            move(robots);
            int[][] grid = new int[HEIGHT][WIDTH];
            for (Robot robot : robots) {
                ++grid[robot.y][robot.x];
            }
            Utils.generateImage(grid, i, "results/");
        }
        return 0;
    }
}
