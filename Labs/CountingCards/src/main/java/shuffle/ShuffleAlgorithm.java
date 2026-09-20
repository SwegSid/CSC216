package shuffle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public final class ShuffleAlgorithm {

    private ShuffleAlgorithm() {
        // utility class; no instances
    }

    /**
     * Algorithm 1: naive "pick and retry" shuffle.
     * Does not mutate the input array.
     */
    public static int[] naiveRetryShuffle(int[] array) {
        int n = array.length;
        boolean[] used = new boolean[n];
        int[] result = new int[n];
        Random rand = new Random();

        int filled = 0;
        while (filled < n) {
            int i = rand.nextInt(n);
            if (!used[i]) {
                used[i] = true;
                result[filled] = array[i];
                filled++;
            }
            // else: collision - loop again and re-roll
        }

        return result;
    }

    /**
     * Algorithm 2: shuffle via repeated removal from a shrinking list.
     * Does not mutate the input array.
     */
    public static int[] shuffleWithRemoval(int[] array) {
        List<Integer> remaining = new ArrayList<>(array.length);
        for (int value : array) {
            remaining.add(value);
        }

        int[] result = new int[array.length];
        Random rand = new Random();

        int idx = 0;
        while (!remaining.isEmpty()) {
            int i = rand.nextInt(remaining.size());
            // List.remove(int) shifts every subsequent element down
            // by one - this is the O(m) cost per pick.
            result[idx] = remaining.remove(i);
            idx++;
        }

        return result;
    }

    /**
     * Algorithm 3: the in-place Fisher-Yates shuffle.
     * Does not mutate the input array (operates on a clone).
     */
    public static int[] fisherYatesShuffle(int[] array) {
        int[] copy = array.clone();
        Random rand = new Random();

        for (int m = copy.length; m > 0; m--) {
            int i = rand.nextInt(m);
            int temp = copy[m - 1];
            copy[m - 1] = copy[i];
            copy[i] = temp;
        }

        return copy;
    }
}
