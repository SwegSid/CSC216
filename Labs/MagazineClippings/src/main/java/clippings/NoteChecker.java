package clippings;

import java.util.Objects;

public final class NoteChecker {

    private NoteChecker() {}


    public static boolean canCreateNote(String note, String[] articles) {
        Objects.requireNonNull(note);
        Objects.requireNonNull(articles);

        MyHashMap<Character, Integer> available = countCharacters(articles);

        for (int i = 0; i < note.length(); i++) {
            char c = note.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            Character key = Character.toLowerCase(c);
            int remaining = available.getOrDefault(key, 0);
            if (remaining == 0) {
                return false;
            }
            available.put(key, remaining - 1);
        }
        return true;
    }

    /**
     * For diagnostics: how many of each character the note is short by.
     * An empty map means the note can be made.
     */
    public static MyHashMap<Character, Integer> shortfall(String note, String[] articles) {
        MyHashMap<Character, Integer> available = countCharacters(articles);
        MyHashMap<Character, Integer> needed = countCharacters(new String[] {note});
        MyHashMap<Character, Integer> missing = new MyHashMap<>();
        needed.forEach((ch, need) -> {
            int have = available.getOrDefault(ch, 0);
            if (need > have) {
                missing.put(ch, need - have);
            }
        });
        return missing;
    }

    private static MyHashMap<Character, Integer> countCharacters(String[] texts) {
        MyHashMap<Character, Integer> counts = new MyHashMap<>(128);
        for (String text : texts) {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isWhitespace(c)) {
                    continue;
                }
                Character key = Character.toLowerCase(c);
                counts.put(key, counts.getOrDefault(key, 0) + 1);
            }
        }
        return counts;
    }
}
