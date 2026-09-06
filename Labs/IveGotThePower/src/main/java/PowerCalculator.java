

public final class PowerCalculator {

    private PowerCalculator() {
        // utility class; no instances
    }

    /**
     * Naive recursive approach: x^n = x * x^(n-1).
     */
    public static long naivePower(long x, int n) {
        if (x == 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        return x * naivePower(x, n - 1);
    }

    /**
     * Divide-and-conquer approach that does NOT cache the recursive
     * result, so it ends up recomputing the same sub-problem twice
     * per level of recursion.
     */
    public static long unoptimizedDCPower(long x, int n) {
        if (x == 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            return unoptimizedDCPower(x, n / 2) * unoptimizedDCPower(x, n / 2);
        } else {
            return x * unoptimizedDCPower(x, n / 2) * unoptimizedDCPower(x, n / 2);
        }
    }

    /**
     * Divide-and-conquer approach that caches the recursive result
     * in a temporary variable, avoiding the duplicate computation
     * present in {@link #unoptimizedDCPower(long, int)}.
     */
    public static long optimizedDCPower(long x, int n) {
        if (x == 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }

        long temp = optimizedDCPower(x, n / 2);
        if (n % 2 == 0) {
            return temp * temp;
        } else {
            return x * temp * temp;
        }
    }
}
