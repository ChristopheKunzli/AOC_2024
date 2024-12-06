import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

final public class Utils {
    public static ArrayList<String> getStrings(String fileName) {
        ArrayList<String> inputs = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(inputs::add);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return inputs;
    }
}
