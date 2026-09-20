package shuffle;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ShuffleBenchmark {

    @Param({"100", "500", "2000", "5000"})
    public int size;

    private int[] array;

    @Setup(Level.Trial)
    public void setup() {
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
    }

    @Benchmark
    public int[] benchmarkNaiveRetryShuffle(Blackhole blackhole) {
        int[] result = ShuffleAlgorithm.naiveRetryShuffle(array);
        blackhole.consume(result);
        return result;
    }

    @Benchmark
    public int[] benchmarkShuffleWithRemoval(Blackhole blackhole) {
        int[] result = ShuffleAlgorithm.shuffleWithRemoval(array);
        blackhole.consume(result);
        return result;
    }

    @Benchmark
    public int[] benchmarkFisherYatesShuffle(Blackhole blackhole) {
        int[] result = ShuffleAlgorithm.fisherYatesShuffle(array);
        blackhole.consume(result);
        return result;
    }
}
