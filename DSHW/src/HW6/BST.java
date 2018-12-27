import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6: BST class
 *
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 * @param <T> any data type
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * BST object.
     */
    private Node<T> root;

    /**
     * for comparison purpose.
     */
    private Comparator<T> comparator;

    /**
     * default constructor.
     */
    public BST() {
        this(null);
    }

    /**
     * parametrized constructor with Comparator object.
     * @param comp object
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }

    /**
     * get the comparator obj.
     * @return comparator of the tree
     */
    public Comparator<T> comparator() {
        return this.comparator;
    }

    /**
     * getter method of the root.
     * @return object data
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }

    /**
     * get height.
     * @return height of the tree
     */
    public int getHeight() {
        // height is 0 if it's an empty tree or only existing one node
        if (root == null || root.left == null && root.right == null) {
            return 0;
        }
        return getHeightRecursive(this.root) - 1;
    }

    /**
     * helper function of getHeight().
     * @param node current tree
     * @return height of the tree
     */
    private int getHeightRecursive(Node<T> node) {
        // Base case
        if (node == null) {
            return 0;
        }
        // Recursive case
        return 1 + Math.max(getHeightRecursive(node.right), getHeightRecursive(node.left));
    }

    /**
     * get number of the nodes.
     * @return total number of nodes in tree
     */
    public int getNumberOfNodes() {
        return getNumberOfNodesRecursive(root);
    }

    /**
     * helper function of getNumberOfNodes().
     * @param node current tree
     * @return number of nodes in the tree
     */
    private int getNumberOfNodesRecursive(Node<T> node) {
        // base case
        if (node == null) {
            return 0;
        }
        // recursion case
        return 1 + getNumberOfNodesRecursive(node.left) + getNumberOfNodesRecursive(node.right);
    }

    /**
     * search the object.
     * @param toSearch Object value to search
     * @return the value of that object if found; o.w. null
     */
    @Override
    public T search(T toSearch) {
        if (root == null || toSearch == null) {
            return null;
        }
        return searchRecursive(root, toSearch);
    }

    /**
     * helpr function of search().
     * @param node current tree
     * @param toSearch for searching
     * @return the value of the toSearch
     */
    private T searchRecursive(Node<T> node, T toSearch) {
        // base case as not found
        if (node == null) {
            return null;
        }
        // compare the difference between two object data
        int isSame = toSearch.compareTo(node.data);

        // base case as found
        if (isSame == 0) {
            return node.data;
        }

        // recursion cases
        if (isSame < 0) {
            // left turn
            return searchRecursive(node.left, toSearch);
        } else {
            // right turn
            return searchRecursive(node.right, toSearch);
        }
    }

    /**
     * insert the node into the tree.
     * @param toInsert a value (object) to insert into the tree.
     */
    @Override
    public void insert(T toInsert) {
        // edge case
        if (toInsert == null) {
            return;
        }

        // empty tree
        if (root == null) {
            root = new Node<>(toInsert);
        }

        // call helper function to recursively find appropriate position and insert
        insertRecursive(root, toInsert);
    }

    /**
     * helper function of insert().
     * @param node current node
     * @param toInsert value to be inserted into the tree
     */
    private void insertRecursive(Node<T> node, T toInsert) {
        // compare & decide which side to go
        if (comparator == null) {
            int compare = toInsert.compareTo(node.data);
            realRecursion(node, toInsert, compare);
        } else {
            int compare = comparator.compare(toInsert, node.data);
            realRecursion(node, toInsert, compare);
        }

    }

    /**
     * recursion of insertion.
     * @param node current node
     * @param toInsert value to be inserted into the tree
     * @param compare difference between words
     */
    private void realRecursion(Node<T> node, T toInsert, int compare) {
        // base case
        if (compare == 0) {
            return;
        }
        // make a left turn when hashCode(toInsert) < hashCode(value of the current node)
        if (compare < 0) {
            if (node.left == null) {
                node.left = new Node<>(toInsert);
            }
            insertRecursive(node.left, toInsert);
        } else {
            // make a right turn
            if (node.right == null) {
                node.right = new Node<>(toInsert);
            }
            insertRecursive(node.right, toInsert);
        }
    }

    /**
     * iterator of the tree in an in-order sequence.
     * @return iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new InOrderTraverse();
    }

    /**
     * Implementation of Iterator.
     */
    private class InOrderTraverse implements Iterator<T> {
        /**
         * LIFO stack.
         */
        private Stack<Node<T>> stack = new Stack<>();

        /**
         * default constructor.
         */
        InOrderTraverse() {
            Node<T> temp = root;
            // all the way to the leftmost node
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
        }

        /**
         * overrides default method.
         * @return boolean result whether exists more value
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * overrides default next() method.
         * @return next object
         */
        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            // the right node is empty ? level down to add all possible left node in to the stack : stay tuned
            Node<T> cur = stack.pop();
            Node<T> tmp = cur.right;
            while (tmp != null) {
                stack.push(tmp);
                tmp = tmp.left;
            }
            return cur.data;
        }
    }

    /**
     * A node in BST.
     * @param <T> any data type
     */
    private static class Node<T> {
        /**
         * value.
         */
        private T data;
        /**
         * left node.
         */
        private Node<T> left;
        /**
         * right node.
         */
        private Node<T> right;

        /**
         * parameterized constructor.
         * @param d any data type object
         */
        Node(T d) {
            this(d, null, null);
        }

        /**
         * parameterized constructor.
         * @param d any data type object
         * @param l left child
         * @param r right child
         */
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }

}
