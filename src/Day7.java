import java.util.ArrayList;
import java.util.List;

public class Day7 {
    record Equation(long goal, long[] operands) {
    }

    List<Equation> equations = new ArrayList<>();

    public void solve(String filePath) {
        List<String> lines = Utils.getStrings(filePath);
        parse(lines);
        System.out.println("Day 7");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        for (String line : lines) {
            String[] parts = line.split(": ");
            long goal = Long.parseLong(parts[0]);
            String[] operandsStr = parts[1].split(" ");
            long[] operandsArr = new long[operandsStr.length];
            for (int i = 0; i < operandsStr.length; i++) {
                operandsArr[i] = Long.parseLong(operandsStr[i]);
            }

            equations.add(new Equation(goal, operandsArr));
        }
    }

    private boolean canBeSolvedByAddingOperators(
            long[] operands,
            Long goal,
            int index,
            long sum,
            boolean isPart2) {
        if (index == operands.length) {
            return sum == goal;
        }

        long withAdd = sum + operands[index];
        long withMul = sum * operands[index];

        boolean b1 = canBeSolvedByAddingOperators(operands, goal, index + 1, withAdd, isPart2);
        boolean b2 = canBeSolvedByAddingOperators(operands, goal, index + 1, withMul, isPart2);
        boolean b3 = false;

        if (isPart2) {
            long withExtraOperator = Long.parseLong(sum + "" + operands[index]);
            b3 = canBeSolvedByAddingOperators(operands, goal, index + 1, withExtraOperator, true);
        }

        return b1 || b2 || b3;
    }

    private long part1() {
        long sum = 0L;

        for (Equation equation : equations) {
            if (canBeSolvedByAddingOperators(equation.operands, equation.goal, 0, 0L, false)) {
                sum += equation.goal;
            }
        }

        return sum;
    }

    private long part2() {
        long sum = 0L;

        for (Equation equation : equations) {
            if (canBeSolvedByAddingOperators(equation.operands, equation.goal, 0, 0L, true)) {
                sum += equation.goal;
            }
        }

        return sum;
    }
}
