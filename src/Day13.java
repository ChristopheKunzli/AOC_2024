import java.util.ArrayList;
import java.util.List;

public class Day13 {
    List<Machine> machines;

    public void solve(String filePath) {
        machines = parse(Utils.getStrings(filePath));
        System.out.println("Day 13:");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    static class Equation {
        int x, y;

        public Equation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "x=" + x + ", y=" + y;
        }
    }

    static class Machine {
        Equation equation1;
        Equation equation2;
        int prizeX, prizeY;

        public Machine(Equation equation1, Equation equation2, int prizeX, int prizeY) {
            this.equation1 = equation1;
            this.equation2 = equation2;
            this.prizeX = prizeX;
            this.prizeY = prizeY;
        }

        public String toString() {
            return "Equation 1: " + equation1 + ", Equation 2: " + equation2 + ", Prize: x=" + prizeX + ", y=" + prizeY;
        }
    }

    private List<Machine> parse(List<String> lines) {
        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); ) {
            String[] parts1 = lines.get(i).replace("Button A: X+", "").split(", Y+");
            String[] parts2 = lines.get(i + 1).replace("Button B: X+", "").split(", Y+");
            String[] parts3 = lines.get(i + 2).replace("Prize: X=", "").split(", Y=");
            Equation equation1 = new Equation(Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]));
            Equation equation2 = new Equation(Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            machines.add(new Machine(equation1, equation2, Integer.parseInt(parts3[0]), Integer.parseInt(parts3[1])));
            i += 4;
        }
        return machines;
    }

    private int part1() {
        return 0;
    }

    private int part2() {
        return 0;
    }
}
