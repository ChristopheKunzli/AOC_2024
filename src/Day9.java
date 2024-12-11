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
                for (int i = 0; i < digit; ++i) {
                    list.add((char) (index + '0'));
                }
                ++index;
            } else {
                for (int i = 0; i < digit; ++i) {
                    list.add(' ');
                }
            }
            skip = !skip;
        }
        return list;
    }

    private long computeCheckSum(Character[] arr) {
        long sum = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == ' ') continue;
            sum += (long) i * (arr[i] - '0');
        }
        return sum;
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
        return computeCheckSum(arr);
    }

    private int findNextBlockStart(Character[] characters, int rightLimit) {
        length = 0;
        int i = rightLimit;
        while (i >= 0 && characters[i] == ' ') --i;
        char current = characters[i];
        while (i >= 0 && characters[i] == current) {
            --i;
            ++length;
        }
        return i + 1;
    }

    int length;

    private int findSuitableEmptyBlock(Character[] characters, int len, int limit) {
        int i = 0;
        while (i < limit - len) {
            while (i < limit - len && characters[i] != ' ') ++i;
            int count = 0, start = i;
            while (i < limit - len && characters[i] == ' ') {
                ++i;
                ++count;
                if (count >= len) return start;
            }
        }
        return -1;
    }

    private boolean move(Character[] characters, int start, int len, int right) {
        int emptyStart = findSuitableEmptyBlock(characters, len, right);
        if (emptyStart == -1) return false;
        for (int i = 0; i < len; ++i) {
            characters[emptyStart + i] = characters[start + i];
            characters[start + i] = ' ';
        }
        return true;
    }

    private long part2() {
        Character[] characters = list.toArray(new Character[0]);
        int right = characters.length - 1;

        while (right >= 0) {
            int nextBlockStart = findNextBlockStart(characters, right);
            right = nextBlockStart - 1;
            move(characters, nextBlockStart, length, right);
        }

        return computeCheckSum(characters);
    }
    //6431472344710
    //6431472688582
}
