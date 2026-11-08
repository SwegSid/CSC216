import java.util.ArrayList;
import java.util.List;

public class AVLTree {

    private static final String NIL = "#";

    private static final class Node {
        int value;
        int height = 1; // a leaf has height 1, an empty subtree has height 0
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;
    private int size;



    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int height() {
        return height(root);
    }

    public boolean contains(int value) {
        Node n = root;
        while (n != null) {
            if (value < n.value) {
                n = n.left;
            } else if (value > n.value) {
                n = n.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /** Values in sorted order. */
    public List<Integer> inOrder() {
        List<Integer> out = new ArrayList<>(size);
        inOrder(root, out);
        return out;
    }

    private void inOrder(Node n, List<Integer> out) {
        if (n == null) {
            return;
        }
        inOrder(n.left, out);
        out.add(n.value);
        inOrder(n.right, out);
    }



    /** Inserts the value and rebalances. Does nothing if it is already present. */
    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node n, int value) {
        if (n == null) {
            size++;
            return new Node(value);
        }
        if (value < n.value) {
            n.left = insert(n.left, value);
        } else if (value > n.value) {
            n.right = insert(n.right, value);
        } else {
            return n; // duplicate
        }
        return rebalance(n);
    }



    /** Deletes the value and rebalances. Does nothing if it is absent. */
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node n, int value) {
        if (n == null) {
            return null;
        }
        if (value < n.value) {
            n.left = delete(n.left, value);
        } else if (value > n.value) {
            n.right = delete(n.right, value);
        } else {
            if (n.left == null) {
                size--;
                return n.right;
            }
            if (n.right == null) {
                size--;
                return n.left;
            }
            // two children: copy up the in-order successor, then delete it from the right
            Node successor = n.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            n.value = successor.value;
            n.right = delete(n.right, successor.value);
        }
        return rebalance(n);
    }



    private static int height(Node n) {
        return n == null ? 0 : n.height;
    }

    private static int balanceFactor(Node n) {
        return height(n.left) - height(n.right);
    }

    private static void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private static Node rebalance(Node n) {
        updateHeight(n);
        int bf = balanceFactor(n);
        if (bf > 1) {                        // left heavy
            if (balanceFactor(n.left) < 0) { // left-right case
                n.left = rotateLeft(n.left);
            }
            return rotateRight(n);
        }
        if (bf < -1) {                        // right heavy
            if (balanceFactor(n.right) > 0) { // right-left case
                n.right = rotateRight(n.right);
            }
            return rotateLeft(n);
        }
        return n;
    }

    private static Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private static Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }



    /** Preorder, comma-separated, {@code #} for empty children. An empty tree is "#". */
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    private void serialize(Node n, StringBuilder sb) {
        if (sb.length() > 0) {
            sb.append(',');
        }
        if (n == null) {
            sb.append(NIL);
            return;
        }
        sb.append(n.value);
        serialize(n.left, sb);
        serialize(n.right, sb);
    }


    public static AVLTree deserialize(String str) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("empty input");
        }
        String[] tokens = str.split(",", -1);
        AVLTree tree = new AVLTree();
        int[] pos = {0};
        tree.root = tree.build(tokens, pos);
        if (pos[0] != tokens.length) {
            throw new IllegalArgumentException("extra tokens after the tree");
        }
        if (!tree.isValid()) {
            throw new IllegalArgumentException("not a valid AVL tree");
        }
        return tree;
    }

    private Node build(String[] tokens, int[] pos) {
        if (pos[0] >= tokens.length) {
            throw new IllegalArgumentException("input ended early");
        }
        String token = tokens[pos[0]++].trim();
        if (token.equals(NIL)) {
            return null;
        }
        Node n;
        try {
            n = new Node(Integer.parseInt(token));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("bad token: " + token, e);
        }
        size++;
        n.left = build(tokens, pos);
        n.right = build(tokens, pos);
        updateHeight(n);
        return n;
    }


    /** True if the tree is ordered, its stored heights are right, and every node is balanced. */
    public boolean isValid() {
        return check(root, Long.MIN_VALUE, Long.MAX_VALUE) >= 0;
    }

    /** Returns the subtree height, or -1 if any invariant is broken. */
    private int check(Node n, long lo, long hi) {
        if (n == null) {
            return 0;
        }
        if (n.value <= lo || n.value >= hi) {
            return -1;
        }
        int l = check(n.left, lo, n.value);
        int r = check(n.right, n.value, hi);
        if (l < 0 || r < 0 || Math.abs(l - r) > 1 || n.height != 1 + Math.max(l, r)) {
            return -1;
        }
        return n.height;
    }
}
