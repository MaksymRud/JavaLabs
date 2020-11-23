import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("input.txt")));
        LexemReader lexemReader = new LexemReader();
        lexemReader.ReadBuffer(contents);


    }
}
