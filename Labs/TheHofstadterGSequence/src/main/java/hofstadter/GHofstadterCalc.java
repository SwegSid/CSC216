package hofstadter;

import java.util.HashMap;
import java.util.Map;


public class GHofstadterCalc {

    /**
     * Naive recursive implementation with no memoization.
     */
    public static long naiveG(long n) {
        if (n == 0) {
            return 0;
        }
        return n - naiveG(naiveG(n - 1));
    }

    /**
     * Memoized recursive implementation.
     */
    public static long memoizedG(long n, Map<Long, Long> memo) {
        if (n == 0) {
            return 0;
        }

        Long cached = memo.get(n);
        if (cached != null) {
            return cached;
        }

        long result = n - memoizedG(memoizedG(n - 1, memo), memo);
        memo.put(n, result);
        return result;
    }

    /**
     * Convenience overload that starts with a fresh, empty cache.
     */
    public static long memoizedG(long n) {
        return memoizedG(n, new HashMap<>());
    }
}
