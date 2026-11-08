package invertedindex;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Inverted file index: word -> positions (0-based, counting every word including
 * common words) where it appears.
 *
 * <p>Build: one pass over the text, O(n) expected, with a hash map from word to
 * position list. Output: sort the u unique words once, O(u log u). Total
 * O(n + u log u) time and O(n) space.
 */
public final class InvertedIndex {

    /** Common words that are not indexed (but still occupy a position). */
    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "and", "the", "they", "to", "for", "you", "your", "in", "of", "on", "at",
            "is", "are", "was", "were", "be", "been", "it", "its", "he", "she", "we", "i", "them",
            "their", "that", "this", "with", "as", "by", "from", "or", "but", "not", "so", "if");

    private final Map<String, IntList> postings = new HashMap<>();
    private int wordCount;

    private InvertedIndex() {}

    public static InvertedIndex build(CharSequence text) {
        InvertedIndex index = new InvertedIndex();
        index.scan(text);
        return index;
    }

    /**
     * Words are runs of letters/digits, lowercased. A hyphen between two word characters
     * stays in the word ("well-known"); an apostrophe between them is dropped
     * ("don't" -> "dont"); all other punctuation ends a word.
     */
    private void scan(CharSequence text) {
        int n = text.length();
        StringBuilder word = new StringBuilder(32);
        for (int i = 0; i < n; i++) {
            char c = text.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                word.append(Character.toLowerCase(c));
            } else if (word.length() > 0 && isJoiner(c) && i + 1 < n
                    && Character.isLetterOrDigit(text.charAt(i + 1))) {
                if (c == '-') {
                    word.append('-');
                }
            } else if (word.length() > 0) {
                emit(word);
                word.setLength(0);
            }
        }
        if (word.length() > 0) {
            emit(word);
        }
    }

    private static boolean isJoiner(char c) {
        return c == '-' || c == '\'' || c == '\u2019';
    }

    private void emit(StringBuilder sb) {
        int position = wordCount++;
        String word = sb.toString();
        if (STOP_WORDS.contains(word)) {
            return;
        }
        IntList list = postings.get(word);
        if (list == null) {
            list = new IntList();
            postings.put(word, list);
        }
        list.add(position);
    }

    /** Total words seen, including common words. */
    public int totalWords() {
        return wordCount;
    }

    public int uniqueWords() {
        return postings.size();
    }

    /** Positions of the word (already lowercase), or an empty array. */
    public int[] positions(String word) {
        IntList list = postings.get(word);
        return list == null ? new int[0] : list.toArray();
    }

    /** Writes the index as JSON with terms in alphabetical order. */
    public void write(Writer out) throws IOException {
        String[] terms = postings.keySet().toArray(new String[0]);
        Arrays.sort(terms);

        if (terms.length == 0) {
            out.write("{}\n");
            return;
        }
        out.write("{\n");
        for (int t = 0; t < terms.length; t++) {
            IntList list = postings.get(terms[t]);
            out.write("  \"");
            out.write(terms[t]);
            out.write("\": [");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    out.write(", ");
                }
                out.write(Integer.toString(list.get(i)));
            }
            out.write(t + 1 < terms.length ? "],\n" : "]\n");
        }
        out.write("}\n");
    }

    public String toJson() {
        StringWriter sw = new StringWriter();
        try {
            write(sw);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return sw.toString();
    }
}
