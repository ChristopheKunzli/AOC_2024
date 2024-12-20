import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main <filePath>");
            return;
        }
        List<String> filesPaths = Utils.getStrings(args[0]);

        int i = 0;
        new Day1().solve(filesPaths.get(i++));
        new Day2().solve(filesPaths.get(i++));
        new Day3().solve(filesPaths.get(i++));
        new Day4().solve(filesPaths.get(i++));
        new Day5().solve(filesPaths.get(i++));
        new Day6().solve(filesPaths.get(i++));
        new Day7().solve(filesPaths.get(i++));
        new Day8().solve(filesPaths.get(i++));
        new Day9().solve(filesPaths.get(i++));
        new Day10().solve(filesPaths.get(i++));
        new Day11().solve(filesPaths.get(i++));
        new Day12().solve(filesPaths.get(i++));
        new Day13().solve(filesPaths.get(i++));
        new Day14().solve(filesPaths.get(i++));
        new Day15().solve(filesPaths.get(i++));
        new Day16().solve(filesPaths.get(i++));
        new Day17().solve(filesPaths.get(i++));
        new Day18().solve(filesPaths.get(i++));
        new Day19().solve(filesPaths.get(i++));
        new Day20().solve(filesPaths.get(i++));
    }
}