package monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class MonopolyDriver {

    public static void main(String[] args) throws IOException {
        CircularLinkedList<String> board = loadBoard();

        System.out.println("Loaded " + board.size() + " spaces onto the board.\n");

        System.out.println("Stepping through the board once, in order:");
        int totalSpaces = board.size();
        for (int i = 1; i <= totalSpaces; i++) {
            System.out.println(i + ". " + board.getCurrentData());
            board.stepForward();
        }

        System.out.println("\nStepping 5 more times to confirm the list wraps back around to Go:");
        for (int i = 0; i < 5; i++) {
            System.out.println(board.getCurrentData());
            board.stepForward();
        }
    }

    private static CircularLinkedList<String> loadBoard() throws IOException {
        CircularLinkedList<String> board = new CircularLinkedList<>();

        try (InputStream in = MonopolyDriver.class.getClassLoader().getResourceAsStream("monopoly_spaces.txt")) {
            if (in == null) {
                throw new IOException("Could not find monopoly_spaces.txt on the classpath");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        board.append(line);
                    }
                }
            }
        }

        return board;
    }
}
