package tree;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class TreeBenchmark {

    private static final PrintStream SINK =
            new PrintStream(OutputStream.nullOutputStream(), false, StandardCharsets.UTF_8);


    private static void runTree(Path root) {
        PrintStream original = System.out;
        System.setOut(SINK);
        try {
            new Tree().print(root.toString());
        } finally {
            System.setOut(original);
        }
    }

    // ------------------------------------------------------------------
    // Shape 1: one folder with lots of files
    // ------------------------------------------------------------------
    @State(Scope.Benchmark)
    public static class FlatFilesState {
        @Param({"10", "100", "1000", "10000"})
        public int fileCount;

        Path root;

        @Setup(Level.Trial)
        public void setup() throws IOException {
            root = Files.createTempDirectory("tree-flat-files");
            TreeFixtures.createFlatFiles(root, fileCount);
        }

        @TearDown(Level.Trial)
        public void teardown() throws IOException {
            TreeFixtures.deleteRecursively(root);
        }
    }

    @Benchmark
    public void flatFiles(FlatFilesState state) {
        runTree(state.root);
    }

    // ------------------------------------------------------------------
    // Shape 2: one folder with lots of (empty) subfolders
    // ------------------------------------------------------------------
    @State(Scope.Benchmark)
    public static class FlatDirectoriesState {
        @Param({"10", "100", "1000"})
        public int dirCount;

        Path root;

        @Setup(Level.Trial)
        public void setup() throws IOException {
            root = Files.createTempDirectory("tree-flat-dirs");
            TreeFixtures.createFlatDirectories(root, dirCount);
        }

        @TearDown(Level.Trial)
        public void teardown() throws IOException {
            TreeFixtures.deleteRecursively(root);
        }
    }

    @Benchmark
    public void flatDirectories(FlatDirectoriesState state) {
        runTree(state.root);
    }

    // ------------------------------------------------------------------
    // Shape 3: one very deep chain of nested folders
    // ------------------------------------------------------------------
    @State(Scope.Benchmark)
    public static class DeepChainState {
        @Param({"5", "10", "25", "50"})
        public int chainDepth;

        Path root;

        @Setup(Level.Trial)
        public void setup() throws IOException {
            root = Files.createTempDirectory("tree-deep");
            TreeFixtures.createDeepChain(root, chainDepth);
        }

        @TearDown(Level.Trial)
        public void teardown() throws IOException {
            TreeFixtures.deleteRecursively(root);
        }
    }

    @Benchmark
    public void deepChain(DeepChainState state) {
        runTree(state.root);
    }

    // ------------------------------------------------------------------
    // Shape 4: balanced tree (branching 3, 2 files per folder)
    // ------------------------------------------------------------------
    @State(Scope.Benchmark)
    public static class BalancedState {
        @Param({"2", "3", "4", "5"})
        public int treeDepth;

        Path root;

        @Setup(Level.Trial)
        public void setup() throws IOException {
            root = Files.createTempDirectory("tree-balanced");
            TreeFixtures.createBalanced(root, 3, treeDepth, 2);
        }

        @TearDown(Level.Trial)
        public void teardown() throws IOException {
            TreeFixtures.deleteRecursively(root);
        }
    }

    @Benchmark
    public void balanced(BalancedState state) {
        runTree(state.root);
    }

    // ------------------------------------------------------------------
    // Shape 5: mostly hidden files (listed + sorted, but skipped when printing)
    // ------------------------------------------------------------------
    @State(Scope.Benchmark)
    public static class HiddenFilesState {
        @Param({"10", "100", "1000"})
        public int hiddenCount;

        Path root;

        @Setup(Level.Trial)
        public void setup() throws IOException {
            root = Files.createTempDirectory("tree-hidden");
            TreeFixtures.createHiddenFiles(root, hiddenCount);
        }

        @TearDown(Level.Trial)
        public void teardown() throws IOException {
            TreeFixtures.deleteRecursively(root);
        }
    }

    @Benchmark
    public void hiddenFiles(HiddenFilesState state) {
        runTree(state.root);
    }
}
