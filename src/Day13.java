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

    record ButtonMovement(long x, long y) { }

    static class Machine {
        ButtonMovement buttonMovement1;
        ButtonMovement buttonMovement2;
        long prizeX, prizeY;

        public Machine(ButtonMovement buttonMovement1, ButtonMovement buttonMovement2, int prizeX, int prizeY) {
            this.buttonMovement1 = buttonMovement1;
            this.buttonMovement2 = buttonMovement2;
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
            ButtonMovement buttonMovement1 = new ButtonMovement(Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]));
            ButtonMovement buttonMovement2 = new ButtonMovement(Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]));
            machines.add(new Machine(buttonMovement1, buttonMovement2, Integer.parseInt(parts3[0]), Integer.parseInt(parts3[1])));
            i += 4;
        }
        return machines;
    }

    private long compute(Machine machine) {
        long b_term1 = machine.prizeY * machine.buttonMovement1.x - machine.prizeX * machine.buttonMovement1.y;
        long b_term2 = machine.buttonMovement2.y * machine.buttonMovement1.x - machine.buttonMovement2.x * machine.buttonMovement1.y;

        long a_term1 = machine.prizeX - (machine.buttonMovement2.x * (b_term1 / b_term2));
        long a_term2 = machine.buttonMovement1.x;

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
