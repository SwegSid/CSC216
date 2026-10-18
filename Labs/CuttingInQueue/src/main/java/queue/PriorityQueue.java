package queue;


public class PriorityQueue<T extends Comparable<T>> {

    private final MaxHeap<T> heap = new MaxHeap<>();

    public void insert(T item) {
        heap.insert(item);
    }

    /** Returns the highest-priority item without removing it. */
    public T peekHighest() {
        return heap.peekMax();
    }

    /** Removes and returns the highest-priority item. */
    public T removeHighest() {
        return heap.extractMax();
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
