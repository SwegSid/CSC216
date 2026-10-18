package queue;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;


public class MaxHeap<T extends Comparable<T>> {

    private static final int DEFAULT_CAPACITY = 16;

    private T[] items;
    private int size;

    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MaxHeap(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        // T erases to Comparable, so an array of Comparable is safe here.
        items = (T[]) new Comparable[initialCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Adds an item to the heap. */
    public void insert(T item) {
        Objects.requireNonNull(item, "Cannot insert null into the heap");

        if (size == items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }

        items[size] = item;
        siftUp(size);
        size++;
    }

    /** Returns the largest item without removing it. */
    public T peekMax() {
        requireNonEmpty();
        return items[0];
    }

    /** Removes and returns the largest item. */
    public T extractMax() {
        requireNonEmpty();

        T max = items[0];
        size--;
        items[0] = items[size]; // move the last item to the root
        items[size] = null;     // don't keep a stale reference
        if (size > 0) {
            siftDown(0);
        }
        return max;
    }



    /** Moves the item at index i up until its parent is >= it. */
    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (items[i].compareTo(items[parent]) <= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    /** Moves the item at index i down until both children are <= it. */
    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            if (left >= size) {
                break; // no children
            }

            int right = left + 1;
            int larger = (right < size && items[right].compareTo(items[left]) > 0) ? right : left;

            if (items[larger].compareTo(items[i]) <= 0) {
                break;
            }
            swap(i, larger);
            i = larger;
        }
    }

    private void swap(int a, int b) {
        T temp = items[a];
        items[a] = items[b];
        items[b] = temp;
    }

    private void requireNonEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        }
    }
}
