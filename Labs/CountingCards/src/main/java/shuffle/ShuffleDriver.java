package shuffle;

import java.util.Arrays;

public class ShuffleDriver {

    public static void main(String[] args) {
        int[] original = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        System.out.println("Original array:        " + Arrays.toString(original));
        System.out.println("naiveRetryShuffle:      " + Arrays.toString(ShuffleAlgorithm.naiveRetryShuffle(original)));
        System.out.println("shuffleWithRemoval:     " + Arrays.toString(ShuffleAlgorithm.shuffleWithRemoval(original)));
        System.out.println("fisherYatesShuffle:     " + Arrays.toString(ShuffleAlgorithm.fisherYatesShuffle(original)));

        // Confirm the original array wasn't mutated by any of them
        System.out.println("\nOriginal array after all shuffles (should be unchanged):");
        System.out.println(Arrays.toString(original));
    }
}
