import java.util.ArrayList;
import java.util.List;

public class Day9 {
    List<Character> list;

    public void solve(String filePath) {
        list = parse(Utils.getStrings(filePath).getFirst());
        System.out.println("Day 9");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private List<Character> parse(String line) {
        boolean skip = false;
        int index = 0;
        List<Character> list = new ArrayList<>();
        for (char c : line.toCharArray()) {
            int digit = c - '0';
            if (!skip) {
                for(int i = 0; i < digit; ++i) {
                    list.add((char) (index + '0'));
                }
                ++index;
            } else {

                for(int i = 0; i < digit; ++i) {
                    list.add(' ');
                }
            }
            skip = !skip;
        }
        return list;
    }

    private long part1() {
        Character[] arr = list.toArray(new Character[0]);
        int left = 0, right = arr.length - 1;
        while (true) {
            while (left < arr.length && arr[left] != ' ') ++left;
            while (right >= 0 && arr[right] == ' ') --right;
            if (left >= right) {
                break;
            }

            arr[left] = arr[right];
            arr[right] = ' ';
            ++left;
            --right;
        }

        long sum = 0;
        for (int i = 0; i < arr.length && arr[i] != ' '; ++i) {
            sum += (long) i * (arr[i] - '0');
        }

        boolean foundSpace = false;
        boolean flag = false;
        for (char c : arr) {
            if (c == ' ') {
                foundSpace = true;
            } else if (foundSpace) {
                flag = true;
            }
        }
        if (flag) {
            System.out.println("Found space before last digit");
        }
        return sum;
    }

    private int part2() {
        return 0;
    }
}
