
public class PowerDriver {

    public static void main(String[] args) {
        int[] xValues = {2, 3, 5, 10};
        int[] nValues = {0, 1, 3, 8, 16};

        System.out.printf("%-6s %-6s %-15s %-20s %-15s%n",
                "x", "n", "naivePower", "unoptimizedDCPower", "optimizedDCPower");
        System.out.println("---------------------------------------------------------------------");

        for (int x : xValues) {
            for (int n : nValues) {
                long naive = PowerCalculator.naivePower(x, n);
                long unopt = PowerCalculator.unoptimizedDCPower(x, n);
                long opt = PowerCalculator.optimizedDCPower(x, n);

                System.out.printf("%-6d %-6d %-15d %-20d %-15d%n",
                        x, n, naive, unopt, opt);

                if (naive != unopt || unopt != opt) {
                    System.out.println("  ^^ MISMATCH between algorithms for x=" + x + ", n=" + n);
                }
            }
        }
    }
}
