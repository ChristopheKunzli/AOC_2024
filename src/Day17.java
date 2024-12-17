import java.util.ArrayList;
import java.util.List;

public class Day17 {
    int pc = 0;//program counter
    long a = 0, b = 0, c = 0;//registers
    List<Integer> program = new ArrayList<>();

    public void solve(String filePath) {
        parse(Utils.getStrings(filePath));
        System.out.println("Day 17");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        a = Long.parseLong(lines.get(0).replace("Register A: ", ""));
        b = Long.parseLong(lines.get(1).replace("Register B: ", ""));
        c = Long.parseLong(lines.get(2).replace("Register C: ", ""));

        for (String num : lines.get(4).replace("Program: ", "").split(",")) {
            program.add(Integer.parseInt(num));
        }
    }

    private String part1() {
        List<Integer> output = new ArrayList<>();
        while (pc < program.size()) {
            int opcode = program.get(pc);
            int operand = program.get(pc + 1);
            switch (opcode) {
                case 0:
                    adv(operand);
                    break;
                case 1:
                    bxl(operand);
                    break;
                case 2:
                    bst(operand);
                    break;
                case 3:
                    jnz(operand);
                    break;
                case 4:
                    bxc();
                    break;
                case 5:
                    output.add(out(operand));
                    break;
                case 6:
                    bdv(operand);
                    break;
                case 7:
                    cdv(operand);
                    break;
                default:
                    System.out.println("Invalid opcode: " + opcode);
                    return "";
            }
            pc += 2;
        }
        return String.join(",", output.stream().map(String::valueOf).toList());
    }

    private long getComboOperand(int operand) {
        return switch (operand) {
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            case 7 -> throw new IllegalStateException("Reserved combo operand: " + operand);
            default -> operand;
        };
    }

    private void adv(int operand) {
        long pow = getComboOperand(operand);
        long denominator = (long) Math.pow(2, pow);
        a = Math.floorDiv(a, denominator);
    }

    private void bxl(int operand) {
        b = b ^ operand;
    }

    private void bst(int operand) {
        b = getComboOperand(operand) % 8;
    }

    private void jnz(int operand) {
        if (a != 0) {
            pc = operand - 2;
        }
    }

    private void bxc() {
        b = b ^ c;
    }

    private int out(int operand) {
        return (int) getComboOperand(operand) % 8;
    }

    private void bdv(int operand) {
        long pow = getComboOperand(operand);
        long denominator = (long) Math.pow(2, pow);
        b = Math.floorDiv(a, denominator);
    }

    private void cdv(int operand) {
        long pow = getComboOperand(operand);
        long denominator = (long) Math.pow(2, pow);
        c = Math.floorDiv(a, denominator);
    }

    private long part2() {
        return 0;
    }
}
