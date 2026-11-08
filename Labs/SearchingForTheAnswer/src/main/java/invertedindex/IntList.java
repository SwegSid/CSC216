package invertedindex;

import java.util.Arrays;

/** Minimal growable int array (avoids boxing every word position into an Integer). */
final class IntList {
    private int[] data = new int[2];
    private int size;

    void add(int value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, size << 1);
        }
        data[size++] = value;
    }

    int size() {
        return size;
    }

    int get(int i) {
        return data[i];
    }

    int[] toArray() {
        return Arrays.copyOf(data, size);
    }
}
