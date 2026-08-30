

import java.io.IOException;

import java.util.Random;

/**
 * Simulates a one-dimensional random walk for a single particle.
 *
 * A particle starts at position 0. On each step, it moves +1 (right)
 * or -1 (left) with 50/50 probability. This is repeated N times, and
 * the final position D is recorded. The process is repeated for M
 * trials at each value of N, and the runtime of each trial is timed.
 */
public class RandomWalk {

    private static final Random RNG = new Random();

    /**
     * Runs a single random walk trial of N steps and returns the
     * particle's final position.
     *
     * @param steps the number of steps (N) the particle takes
     * @return the final position (D) of the particle
     */
    public static int randomWalk(int steps) {
        int currentPosition = 0;

        for (int i = 0; i < steps; i++) {
            // "Flip a coin": true = heads (move right), false = tails (move left)
            boolean heads = RNG.nextBoolean();
            if (heads) {
                currentPosition++;
            } else {
                currentPosition--;
            }
        }

        return currentPosition;
    }

    public static void main(String[] args) throws IOException {
        // Values of N (number of steps) to test.
        int[] stepCounts = {10, 100, 1_000, 10_000, 100_000};

        // Number of trials (M) to run for each value of N.
        int trialsPerStepCount = 10;

        // Print a header for console output.
        System.out.printf("%-12s %-12s %-15s %-20s%n",
                "N (steps)", "Trial", "Final Position", "Time Taken (ms)");
        System.out.println("--------------------------------------------------------------");


            for (int steps : stepCounts) {
                for (int trial = 1; trial <= trialsPerStepCount; trial++) {

                    long startTime = System.nanoTime();
                    int finalPosition = randomWalk(steps);
                    long endTime = System.nanoTime();

                    double elapsedMillis = (endTime - startTime) / 1_000_000.0;

                    System.out.printf("%-12d %-12d %-15d %-20.4f%n",
                            steps, trial, finalPosition, elapsedMillis);

                }
            }
        }
    }

