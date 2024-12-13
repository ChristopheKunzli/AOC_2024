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

    record Button(long x, long y) { }

    static class Machine {
        Button a;
        Button b;
        long prizeX, prizeY;

        public Machine(Button a, Button b, int prizeX, int prizeY) {
            this.a = a;
            this.b = b;
            this.prizeX = prizeX;
            this.prizeY = prizeY;
        }
    }

    private List<Machine> parse(List<String> lines) {
        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); ) {
            String[] parts1 = lines.get(i).replace("Button A: X+", "").split(", Y+");
            String[] parts2 = lines.get(i + 1).replace("Button B: X+", "").split(", Y+");
            String[] parts3 = lines.get(i + 2).replace("Prize: X=", "").split(", Y=");
            Button button1 = new Button(Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]));
            Button button2 = new Button(Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            machines.add(new Machine(button1, button2, Integer.parseInt(parts3[0]), Integer.parseInt(parts3[1])));
            i += 4;
        }
        return machines;
    }

    private long compute(Machine machine) {
        long b_term1 = machine.prizeY * machine.a.x - machine.prizeX * machine.a.y;
        long b_term2 = machine.b.y * machine.a.x - machine.b.x * machine.a.y;

        long a_term1 = machine.prizeX - (machine.b.x * (b_term1 / b_term2));
        long a_term2 = machine.a.x;

        if (b_term1 % b_term2 != 0 || a_term1 % a_term2 != 0) {
            return 0L;
        }

        long bPressCount = b_term1 / b_term2;
        long aPressCount = a_term1 / a_term2;

        return aPressCount * 3L + bPressCount;
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
