import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main <filePath>");
            return;
        }
        List<String> filesPaths = Utils.getStrings(args[0]);

        int i = 0;
        new Day2().solve(filesPaths.get(i++));
        new Day3().solve(filesPaths.get(i++));
    }
}