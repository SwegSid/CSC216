package invertedindex;

import java.io.IOException;
import java.io.Writer;
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

/** Times index construction (and construction + sorted output) vs. document size in words. */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class InvertedIndexBenchmark {

    private static final String[] COMMON = {"the", "and", "to", "a", "of", "in", "they", "you"};
    private static final int VOCABULARY = 20_000;

    @Param({"1000", "10000", "100000", "1000000", "5000000"})
    public int words;

    private String text;

    @Setup
    public void setUp() {
        Random rng = new Random(42);

        String[] vocab = new String[VOCABULARY];
        for (int i = 0; i < VOCABULARY; i++) {
            int len = 3 + rng.nextInt(8);
            StringBuilder sb = new StringBuilder(len);
            for (int j = 0; j < len; j++) {
                sb.append((char) ('a' + rng.nextInt(26)));
            }
            vocab[i] = sb.toString();
        }

        StringBuilder sb = new StringBuilder(words * 8);
        for (int i = 0; i < words; i++) {
            if (rng.nextInt(4) == 0) {
                sb.append(COMMON[rng.nextInt(COMMON.length)]);
            } else {
                // skewed toward low indices, roughly like natural text
                int idx = (int) (VOCABULARY * Math.pow(rng.nextDouble(), 3));
                sb.append(vocab[idx]);
            }
            sb.append(rng.nextInt(12) == 0 ? ". " : " ");
        }
        text = sb.toString();
    }

    @Benchmark
    public InvertedIndex build() {
        return InvertedIndex.build(text);
    }

    @Benchmark
    public InvertedIndex buildAndWrite() throws IOException {
        InvertedIndex index = InvertedIndex.build(text);
        try (Writer w = Writer.nullWriter()) {
            index.write(w);
        }
        return index;
    }
}
