package hofstadter;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class HofstadterNaiveBenchmark {

    @Param({"20", "50", "80", "110", "140", "160", "180", "195", "205", "215"})
    public long n;

    @Benchmark
    public long benchmarkNaiveG(Blackhole blackhole) {
        long result = GHofstadterCalc.naiveG(n);
        blackhole.consume(result);
        return result;
    }
}
