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
        long x, y;

        public Equation(long x, long y) {
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
        long prizeX, prizeY;

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

    private long compute(Machine machine) {
        long b_term1 = machine.prizeY * machine.equation1.x - machine.prizeX * machine.equation1.y;
        long b_term2 = machine.equation2.y * machine.equation1.x - machine.equation2.x * machine.equation1.y;

        long a_term1 = machine.prizeX - machine.equation2.x * (b_term1 / b_term2);
        long a_term2 = machine.equation1.x;

        if (b_term1 % b_term2 != 0 || a_term1 % a_term2 != 0) {
            return 0L;
        }

        long b = b_term1 / b_term2;
        long a = a_term1 / a_term2;

        return a * 3L + b;
    }

    private long part1() {
        long sum = 0;
        for (Machine machine : machines) {
            sum += compute(machine);
        }
        return sum;
    }

    private long part2() {
        long sum = 0;
        for (Machine machine : machines) {
            machine.prizeX += 10000000000000L;
            machine.prizeY += 10000000000000L;
            sum += compute(machine);
        }
        return sum;
    }
}