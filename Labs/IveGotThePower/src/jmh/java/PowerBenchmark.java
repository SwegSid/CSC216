
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class PowerBenchmark {

    // Ten values for x, as recommended by the lab.
    @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    public int x;

    // Ten values for n, as recommended by the lab.
    @Param({"1", "2", "4", "8", "16", "32", "64", "128", "256", "512"})
    public int n;

    @Benchmark
    public long benchmarkNaivePower(Blackhole blackhole) {
        long result = PowerCalculator.naivePower(x, n);
        blackhole.consume(result);
        return result;
    }

    @Benchmark
    public long benchmarkUnoptimizedDCPower(Blackhole blackhole) {
        long result = PowerCalculator.unoptimizedDCPower(x, n);
        blackhole.consume(result);
        return result;
    }

    @Benchmark
    public long benchmarkOptimizedDCPower(Blackhole blackhole) {
        long result = PowerCalculator.optimizedDCPower(x, n);
        blackhole.consume(result);
        return result;
    }
}
