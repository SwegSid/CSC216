package monopoly;

public class CircularLinkedList<T> {

    private static class Node<T> {
        private final T data;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;
    private int size;

    public void append(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            // First node ever added: it points to itself.
            head = newNode;
            tail = newNode;
            newNode.next = newNode;
            current = newNode;
        } else {
            newNode.next = head;   // new node wraps back to the head
            tail.next = newNode;   // old tail now points to the new node
            tail = newNode;        // new node is now the tail
        }

        size++;
    }

    public T getCurrentData() {
        requireNonEmpty();
        return current.data;
    }

    public void stepForward() {
        requireNonEmpty();
        current = current.next;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void requireNonEmpty() {
        if (current == null) {
            throw new IllegalStateException("Cannot operate on an empty circular linked list");
        }
    }
}
