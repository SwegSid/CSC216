package clippings;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String OTHELLO = "/othello.txt";
    private static final String ROMEO_AND_JULIET = "/romeo_and_juliet.txt";

    public static void main(String[] args) throws IOException {
        System.out.println();
        runTest();
    }

    private static void check(String note, String... articles) {
        System.out.printf("%-20s %-70s -> %b%n",
                note, String.join(" | ", articles), NoteChecker.canCreateNote(note, articles));
    }

    private static void runTest() throws IOException {
        System.out.println("=== Othello -> Romeo and Juliet ===");
        String othello = readResource(OTHELLO);
        String romeo = readResource(ROMEO_AND_JULIET);
        if (othello == null || romeo == null) {
            System.out.println("Missing play text.");
            return;
        }

        String[] articles = {othello};
        System.out.printf("Othello: %,d chars; Romeo and Juliet: %,d chars%n", othello.length(), romeo.length());

        long start = System.nanoTime();
        boolean result = NoteChecker.canCreateNote(romeo, articles);
        long micros = (System.nanoTime() - start) / 1_000;
        System.out.printf("Can Othello's characters make Romeo and Juliet? %b (%,d us, cold single run)%n",
                result, micros);

        if (!result) {
            MyHashMap<Character, Integer> missing = NoteChecker.shortfall(romeo, articles);
            List<String> parts = new ArrayList<>();
            missing.forEach((ch, n) -> parts.add("'" + ch + "' short by " + n));
            parts.sort(null);
            System.out.println("Shortfall (" + missing.size() + " distinct characters):");
            parts.forEach(p -> System.out.println("  " + p));
        }
    }

    private static String readResource(String path) throws IOException {
        try (InputStream in = Main.class.getResourceAsStream(path)) {
            return in == null ? null : new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
