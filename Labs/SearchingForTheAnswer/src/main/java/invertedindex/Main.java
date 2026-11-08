package invertedindex;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: ./gradlew run --args=\"<input.txt> [output.json]\"");
            return;
        }
        Path in = Path.of(args[0]);
        Path out = Path.of(args.length > 1 ? args[1] : "inverted-index.json");

        String text = new String(Files.readAllBytes(in), StandardCharsets.UTF_8);

        long start = System.nanoTime();
        InvertedIndex index = InvertedIndex.build(text);
        try (BufferedWriter writer = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            index.write(writer);
        }
        long ms = (System.nanoTime() - start) / 1_000_000;

        System.out.printf("%,d words read, %,d unique indexed terms, wrote %s (%d ms)%n",
                index.totalWords(), index.uniqueWords(), out, ms);
    }
}
