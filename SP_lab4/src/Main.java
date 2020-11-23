import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("input.txt");
        Scanner scanner = new Scanner(path);
        System.out.println("Reading a text file");
        LexemReader lexemReader = new LexemReader();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            lexemReader.readLexem(line);
            lexemReader.getLexemes();
        }

    }
}
