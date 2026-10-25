package clippings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NoteCheckerBenchmark {

    private static final String ALPHABET =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .,;:!?'-\n";
    private static final int ARTICLE_LENGTH = 2_000;

    @Param({"1000", "10000", "100000", "1000000"})
    public int articleChars;

    @Param({"100", "1000", "10000", "100000"})
    public int noteChars;

    private String note;
    private String[] articles;

    @Setup
    public void setUp() {
        Random rng = new Random(42);

        int count = Math.max(1, articleChars / ARTICLE_LENGTH);
        int length = articleChars / count;
        articles = new String[count];
        StringBuilder all = new StringBuilder(articleChars);
        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                sb.append(ALPHABET.charAt(rng.nextInt(ALPHABET.length())));
            }
            articles[i] = sb.toString();
            all.append(articles[i]);
        }

        // Shuffle the article characters and take as many as the note needs,
        // capped at what the articles can supply so the answer stays "true".
        List<Character> pool = new ArrayList<>(all.length());
        for (int i = 0; i < all.length(); i++) {
            pool.add(all.charAt(i));
        }
        Collections.shuffle(pool, rng);
        int take = Math.min(noteChars, pool.size());
        StringBuilder sb = new StringBuilder(take);
        for (int i = 0; i < take; i++) {
            sb.append(pool.get(i));
        }
        note = sb.toString();
    }

    @Benchmark
    public boolean canCreateNote() {
        return NoteChecker.canCreateNote(note, articles);
    }
}
