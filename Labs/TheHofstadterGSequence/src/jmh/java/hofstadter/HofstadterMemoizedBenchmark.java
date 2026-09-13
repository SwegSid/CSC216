package hofstadter;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class HofstadterMemoizedBenchmark {

    @Param({"10", "100", "500", "1000", "2000", "5000", "8000", "10000", "20000", "50000"})
    public long n;

    private Map<Long, Long> memo;

    @Setup(Level.Invocation)
    public void resetCache() {
        memo = new HashMap<>();
    }

    @Benchmark
    public long benchmarkMemoizedG(Blackhole blackhole) {
        long result = GHofstadterCalc.memoizedG(n, memo);
        blackhole.consume(result);
        return result;
    }
}
