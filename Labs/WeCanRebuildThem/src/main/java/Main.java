public class Main {

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        for (int v : new int[] {10, 20, 30, 40, 50, 25}) {
            tree.insert(v);
        }
        System.out.println("after inserts:  " + tree.serialize());

        tree.delete(40);
        System.out.println("after delete 40: " + tree.serialize());

        String saved = tree.serialize();
        AVLTree copy = AVLTree.deserialize(saved);
        System.out.println("deserialized:    " + copy.serialize());
        System.out.println("same shape: " + saved.equals(copy.serialize())
                + ", size " + copy.size() + ", height " + copy.height());
    }
}
