package frog.test.model;

/**
 * AVL-Tree
 *
 * @author Shinobu
 * @since 2019/5/7
 */
public class Tree<T extends Comparable<T>> {

    private static final int MAX_HEIGHT_DIFFERENCE = 1;

    private Node<T> root;

    class Node<KT> {

        KT key;

        Node<KT> left;

        Node<KT> right;

        int height = 1;

        public Node(KT key, Node<KT> left, Node<KT> right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }

    public Tree(T... keys) {
        if (keys == null || keys.length < 1) {
            throw new NullPointerException();
        }

        root = new Node<>(keys[0], null, null);
        for (int i = 1; i < keys.length && keys[i] != null; i++) {
            root = add(root, keys[i]);
        }
    }

    public T find(T key) {
        if (key == null || root == null) {
            return null;
        }
        return find(root, key, key.compareTo(root.key));
    }

    private T find(Node<T> node, T key, int cmp) {
        if (node == null) {
            return null;
        }

        if (cmp == 0) {
            return node.key;
        }

        return find(
                (node = cmp > 0 ? node.right : node.left),
                key,
                node == null ? 0 : key.compareTo(node.key));
    }

    public void add(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = add(root, key);
    }

    private Node<T> add(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key, null, null);
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            node.left = add(node.left, key);
        } else {
            node.right = add(node.right, key);
        }

        if (Math.abs(height(node.left) - height(node.right)) > MAX_HEIGHT_DIFFERENCE) {
            node = balance(node);
        }
        refreshHeight(node);
        return node;
    }

    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private void refreshHeight(Node<T> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private Node<T> balance(Node<T> node) {
        Node<T> node1, node2;
        // ll & l
        if (height(node.left) > height(node.right) &&
                height(node.left.left) >= height(node.left.right)) {
            node1 = node.left;
            node.left = node1.right;
            node1.right = node;

            refreshHeight(node);
            return node1;
        }
        // lr
        if (height(node.left) > height(node.right) &&
                height(node.left.right) > height(node.left.left)) {
            node1 = node.left;
            node2 = node.left.right;
            node.left = node2.right;
            node1.right = node2.left;
            node2.left = node1;
            node2.right = node;

            refreshHeight(node);
            refreshHeight(node1);
            return node2;
        }
        // rr & r
        if (height(node.right) > height(node.left) &&
                height(node.right.right) >= height(node.right.left)) {
            node1 = node.right;
            node.right = node1.left;
            node1.left = node;

            refreshHeight(node);
            return node1;
        }
        // rl
        if (height(node.right) > height(node.left) &&
                height(node.right.left) > height(node.right.right)) {
            node1 = node.right;
            node2 = node.right.left;
            node.right = node2.left;
            node1.left = node2.right;
            node2.left = node;
            node2.right = node1;

            refreshHeight(node);
            refreshHeight(node1);
            return node2;
        }
        return node;
    }

    public void remove(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = remove(root, key);
    }

    private Node<T> remove(Node<T> node, T key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        }
        if (cmp > 0){
            node.right = remove(node.right, key);
        }
        if (cmp == 0) {
            if (node.left == null || node.right == null) {
                return node.left == null ? node.right : node.left;
            }
            node = remove(node, successorOf(node).key);
            node.key = successorOf(node).key;
        }

        if (Math.abs(height(node.left) - height(node.right)) > MAX_HEIGHT_DIFFERENCE) {
            node = balance(node);
        }
        refreshHeight(node);
        return node;
    }
    
    private Node<T> successorOf(Node<T> node) {
        if (node == null) {
            throw new NullPointerException();
        }
        if (node.left == null || node.right == null) {
            return node.left == null ? node.right : node.left;
        }

        return height(node.left) > height(node.right) ?
                findMax(node.left, node.left.right, node.left.right == null) :
                findMin(node.right, node.right.left, node.right.left == null);
    }

    private Node<T> findMax(Node<T> node, Node<T> right, boolean rightIsNull) {
        if (rightIsNull) {
            return node;
        }
        return findMax((node = right), node.right, node.right == null);
    }

    private Node<T> findMin(Node<T> node, Node<T> left, boolean leftIsNull) {
        if (leftIsNull) {
            return node;
        }
        return findMin((node = left), node.left, node.left == null);
    }

}