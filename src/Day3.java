import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private String input;

    public void solve(String file) {
        List<String> inputs = Utils.getStrings(file);
        StringBuilder sb = new StringBuilder();
        for (String line : inputs) {
            sb.append(line);
        }
        input = sb.toString();
        //input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        //input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

        System.out.println("Day 3");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private int part1() {
        Pattern pattern = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)");
        Matcher matcher = pattern.matcher(input);

        int sum = 0;
        while (matcher.find()) {
            String match = matcher.group();
            String[] numbers = match.replace("mul(", "").replace(")", "").split(",");
            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);
            sum += a * b;
        }

        return sum;
    }

    private int part2() {
        Pattern p = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don't\\(\\)");
        Matcher m = p.matcher(input);
        boolean enabled = true;

        int sum = 0;
        while (m.find()) {
            String match = m.group();
            if (match.equals("do()")) {
                enabled = true;
            } else if (match.equals("don't()")) {
                enabled = false;
            } else if (enabled) {
                String[] numbers = match.replace("mul(", "").replace(")", "").split(",");
                int a = Integer.parseInt(numbers[0]);
                int b = Integer.parseInt(numbers[1]);
                sum += a * b;
            }
        }

        return sum;
    }
}
