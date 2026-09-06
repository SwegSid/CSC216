
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerCalculatorTest {

    @Test
    void testKnownValues() {
        assertEquals(8, PowerCalculator.naivePower(2, 3));
        assertEquals(8, PowerCalculator.unoptimizedDCPower(2, 3));
        assertEquals(8, PowerCalculator.optimizedDCPower(2, 3));
    }

    @Test
    void testPowerOfZeroIsOne() {
        assertEquals(1, PowerCalculator.naivePower(5, 0));
        assertEquals(1, PowerCalculator.unoptimizedDCPower(5, 0));
        assertEquals(1, PowerCalculator.optimizedDCPower(5, 0));
    }

    @Test
    void testZeroBaseIsZero() {
        assertEquals(0, PowerCalculator.naivePower(0, 5));
        assertEquals(0, PowerCalculator.unoptimizedDCPower(0, 5));
        assertEquals(0, PowerCalculator.optimizedDCPower(0, 5));
    }

    @Test
    void testAllThreeAlgorithmsAgree() {
        int[] xValues = {1, 2, 3, 4, 5, 7, 10};
        int[] nValues = {0, 1, 2, 3, 4, 5, 8, 10, 15, 20};

        for (int x : xValues) {
            for (int n : nValues) {
                long naive = PowerCalculator.naivePower(x, n);
                long unopt = PowerCalculator.unoptimizedDCPower(x, n);
                long opt = PowerCalculator.optimizedDCPower(x, n);

                assertEquals(naive, unopt,
                        () -> "Mismatch (naive vs unoptimized) at x=" + x + ", n=" + n);
                assertEquals(unopt, opt,
                        () -> "Mismatch (unoptimized vs optimized) at x=" + x + ", n=" + n);
            }
        }
    }
}
