import java.util.ArrayList;
import java.util.List;

public class Day17 {
    long baseA = 0;
    List<Integer> program = new ArrayList<>();

    public void solve(String filePath) {
        parse(Utils.getStrings(filePath));
        System.out.println("Day 17");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        baseA = Long.parseLong(lines.get(0).replace("Register A: ", ""));
        //b = Long.parseLong(lines.get(1).replace("Register B: ", ""));
        //c = Long.parseLong(lines.get(2).replace("Register C: ", ""));

        for (String num : lines.get(4).replace("Program: ", "").split(",")) {
            program.add(Integer.parseInt(num));
        }
    }

    private long getComboOperand(int operand, long a, long b, long c) {
        return switch (operand) {
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            case 7 -> throw new IllegalStateException("Reserved combo operand: " + operand);
            default -> operand;
        };
    }

    private List<Integer> runProgram(long a) {
        List<Integer> output = new ArrayList<>();
        int pc = 0;
        long b = 0L, c = 0L;

        while (pc < program.size()) {
            int opcode = program.get(pc);
            int operand = program.get(pc + 1);

            long pow = getComboOperand(operand, a, b, c);
            long denominator = (long) Math.pow(2, pow);
            switch (opcode) {
                case 0:
                    a = Math.floorDiv(a, denominator);
                    break;
                case 1:
                    b = b ^ operand;
                    break;
                case 2:
                    b = getComboOperand(operand, a, b, c) % 8;
                    break;
                case 3:
                    if (a != 0) pc = operand - 2;
                    break;
                case 4:
                    b = b ^ c;
                    break;
                case 5:
                    output.add((int) getComboOperand(operand, a, b, c) % 8);
                    break;
                case 6:
                    b = Math.floorDiv(a, denominator);
                    break;
                case 7:
                    c = Math.floorDiv(a, denominator);
                    break;
                default:
                    System.out.println("Invalid opcode: " + opcode);
                    return output;
            }
            pc += 2;
        }
        return output;
    }

    private String part1() {
        List<Integer> output = runProgram(baseA);
        return String.join(",", output.stream().map(String::valueOf).toList());
    }

    private long part2() {
        return -1;
    }
}
