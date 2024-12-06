import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    private List<String> text;
    //Pattern xmasPattern = Pattern.compile("XMAS|SAMX");

    public void solve(String fileName) {
        text = Utils.getStrings(fileName);
        System.out.println("Day 4");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private boolean searchTopLeft(int i, int j) {
        if (i < 3 || j < 3) {
            return false;
        }

        return text.get(i - 1).charAt(j - 1) == 'M' &&
                text.get(i - 2).charAt(j - 2) == 'A' &&
                text.get(i - 3).charAt(j - 3) == 'S';
    }

    private boolean searchTopRight(int i, int j) {
        if (i < 3 || j >= text.get(i).length() - 3) {
            return false;
        }

        return text.get(i - 1).charAt(j + 1) == 'M' &&
                text.get(i - 2).charAt(j + 2) == 'A' &&
                text.get(i - 3).charAt(j + 3) == 'S';
    }

    private boolean searchBottomLeft(int i, int j) {
        if (i >= text.size() - 3 || j < 3) {
            return false;
        }

        return text.get(i + 1).charAt(j - 1) == 'M' &&
                text.get(i + 2).charAt(j - 2) == 'A' &&
                text.get(i + 3).charAt(j - 3) == 'S';
    }

    private boolean searchBottomRight(int i, int j) {
        if (i >= text.size() - 3 || j >= text.get(i).length() - 3) {
            return false;
        }

        return text.get(i + 1).charAt(j + 1) == 'M' &&
                text.get(i + 2).charAt(j + 2) == 'A' &&
                text.get(i + 3).charAt(j + 3) == 'S';
    }

    private boolean searchTop(int i, int j) {
        if (i < 3) {
            return false;
        }

        return text.get(i - 1).charAt(j) == 'M' &&
                text.get(i - 2).charAt(j) == 'A' &&
                text.get(i - 3).charAt(j) == 'S';
    }

    private boolean searchBottom(int i, int j) {
        if (i >= text.size() - 3) {
            return false;
        }

        return text.get(i + 1).charAt(j) == 'M' &&
                text.get(i + 2).charAt(j) == 'A' &&
                text.get(i + 3).charAt(j) == 'S';
    }

    private boolean searchLeft(int i, int j) {
        if (j < 3) {
            return false;
        }

        return text.get(i).charAt(j - 1) == 'M' &&
                text.get(i).charAt(j - 2) == 'A' &&
                text.get(i).charAt(j - 3) == 'S';
    }

    private boolean searchRight(int i, int j) {
        if (j >= text.get(i).length() - 3) {
            return false;
        }

        return text.get(i).charAt(j + 1) == 'M' &&
                text.get(i).charAt(j + 2) == 'A' &&
                text.get(i).charAt(j + 3) == 'S';
    }

    private int part1() {
        int count = 0;

        for (int i = 0; i < text.size(); ++i) {
            for (int j = 0; j < text.get(i).length(); ++j) {
                if (text.get(i).charAt(j) == 'X') {
                    if (searchLeft(i, j)) ++count;
                    if (searchRight(i, j)) ++count;
                    if (searchTop(i, j)) ++count;
                    if (searchBottom(i, j)) ++count;
                    if (searchTopLeft(i, j)) ++count;
                    if (searchTopRight(i, j)) ++count;
                    if (searchBottomLeft(i, j)) ++count;
                    if (searchBottomRight(i, j)) ++count;
                }
            }
        }

        return count;
    }

    private int part2() {
        int count = 0;
        for (int i = 1; i < text.size() - 1; ++i) {
            for (int j = 1; j < text.get(i).length() - 1; ++j) {
                if(text.get(i).charAt(j) == 'A') {
                    char topLeft = text.get(i - 1).charAt(j - 1);
                    char topRight = text.get(i - 1).charAt(j + 1);
                    char bottomLeft = text.get(i + 1).charAt(j - 1);
                    char bottomRight = text.get(i + 1).charAt(j + 1);

                    String d1 = "" + topLeft + bottomRight;
                    String d2 = "" + topRight + bottomLeft;

                    Pattern p = Pattern.compile("MS|SM");
                    Matcher m1 = p.matcher(d1);
                    Matcher m2 = p.matcher(d2);
                    if(m1.matches() && m2.matches()) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }
}
