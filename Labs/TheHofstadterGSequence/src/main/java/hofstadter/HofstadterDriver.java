package hofstadter;

public class HofstadterDriver {

    public static void main(String[] args) {
        long[] expected = {0, 1, 1, 2, 3, 3, 4, 4, 5, 6, 6, 7};

        System.out.printf("%-4s %-12s %-12s %-10s%n", "n", "naiveG(n)", "memoizedG(n)", "expected");
        System.out.println("----------------------------------------------");

        for (int n = 0; n < expected.length; n++) {
            long naive = GHofstadterCalc.naiveG(n);
            long memoized = GHofstadterCalc.memoizedG(n);

            String flag = (naive != expected[n] || memoized != expected[n]) ? "  <-- MISMATCH" : "";

            System.out.printf("%-4d %-12d %-12d %-10d%s%n",
                    n, naive, memoized, expected[n], flag);
        }
    }
}

