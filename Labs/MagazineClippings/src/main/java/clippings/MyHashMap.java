package clippings;

import java.util.Objects;
import java.util.function.BiConsumer;


public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private static final class Node<K, V> {
        final K key;
        final int hash;
        V value;
        Node<K, V> next;

        Node(K key, int hash, V value, Node<K, V> next) {
            this.key = key;
            this.hash = hash;
            this.value = value;
            this.next = next;
        }
    }

    private Node<K, V>[] table;
    private int size;

    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashMap(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        table = newTable(tableSizeFor(initialCapacity));
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Node<K, V>[] newTable(int capacity) {
        return (Node<K, V>[]) new Node[capacity];
    }

    /** Smallest power of two >= n. */
    private static int tableSizeFor(int n) {
        int cap = 1;
        while (cap < n) {
            cap <<= 1;
        }
        return cap;
    }

    /** Mix the high bits into the low bits, since only the low bits pick the bucket. */
    private static int hash(Object key) {
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    /** Returns the value for the key, or null if absent. */
    public V get(K key) {
        Node<K, V> node = findNode(key);
        return node == null ? null : node.value;
    }

    public V getOrDefault(K key, V defaultValue) {
        Node<K, V> node = findNode(key);
        return node == null ? defaultValue : node.value;
    }

    /** Associates value with key; returns the previous value or null. */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "null keys are not supported");
        int h = hash(key);
        int idx = indexFor(h, table.length);
        for (Node<K, V> n = table[idx]; n != null; n = n.next) {
            if (n.hash == h && n.key.equals(key)) {
                V old = n.value;
                n.value = value;
                return old;
            }
        }
        table[idx] = new Node<>(key, h, value, table[idx]);
        if (++size > table.length * LOAD_FACTOR) {
            resize();
        }
        return null;
    }

    /** Removes the key; returns its value or null if it was absent. */
    public V remove(K key) {
        Objects.requireNonNull(key, "null keys are not supported");
        int h = hash(key);
        int idx = indexFor(h, table.length);
        Node<K, V> prev = null;
        for (Node<K, V> n = table[idx]; n != null; prev = n, n = n.next) {
            if (n.hash == h && n.key.equals(key)) {
                if (prev == null) {
                    table[idx] = n.next;
                } else {
                    prev.next = n.next;
                }
                size--;
                return n.value;
            }
        }
        return null;
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        for (Node<K, V> head : table) {
            for (Node<K, V> n = head; n != null; n = n.next) {
                action.accept(n.key, n.value);
            }
        }
    }

    private Node<K, V> findNode(K key) {
        Objects.requireNonNull(key, "null keys are not supported");
        int h = hash(key);
        for (Node<K, V> n = table[indexFor(h, table.length)]; n != null; n = n.next) {
            if (n.hash == h && n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }

    private void resize() {
        Node<K, V>[] old = table;
        Node<K, V>[] bigger = newTable(old.length << 1);
        for (Node<K, V> head : old) {
            Node<K, V> n = head;
            while (n != null) {
                Node<K, V> next = n.next;
                int idx = indexFor(n.hash, bigger.length);
                n.next = bigger[idx];
                bigger[idx] = n;
                n = next;
            }
        }
        table = bigger;
    }
}
