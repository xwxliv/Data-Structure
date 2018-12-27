package HW3;
/**
 * 17683 Data Structures for Applications Programmers.
 * <p>
 * Homework 3 SortedLinkedList Implementation with Recursion
 * <p>
 * Andrew ID: wenxuanx
 *
 * @author Wenxuan Xu
 */
public class SortedLinkedList implements MyListInterface {
    /**
     * Global var: singly linked list.
     */
    private Node head;

    /**
     * Node (static nested class).
     */
    private static class Node {
        /**
         * data type of node.
         */
        private String data;

        /**
         * reference to next node.
         */
        private Node next;

        /**
         * constructor a new node with data and next node reference.
         *
         * @param newData data element of the node
         * @param newNode next node reference
         */
        Node(String newData, Node newNode) {
            data = newData;
            next = newNode;
        }
    }

    /**
     * Constructor with no parameters.
     */
    public SortedLinkedList() {
        head = null;
    }

    /**
     * Parameterized constructor with unsorted array.
     *
     * @param unsorted string array
     */
    public SortedLinkedList(String[] unsorted) {
        // do nothing if malformed array passed
        if (unsorted == null || unsorted.length == 0) {
            return;
        }

        // keeps track of the travered index of the String array.
        int index = 0;
        // call the helper function to recursively traverse the array until all elements have been visited.
        recursionTraverse(unsorted, index);
    }

    /**
     * Helper function of parameterized constructor
     * @param unsorted object to traverse
     * @param index keep track of elements in 'unsorted'
     */
    private void recursionTraverse(String[] unsorted, int index) {
        // Base case
        if (index == unsorted.length) {
            return;
        }
        // Recursion case
        add(unsorted[index]);
        recursionTraverse(unsorted, ++index);
    }

    /**
     * Inserts a new String into Node.
     * Do not throw exceptions if invalid word is added (Gently ignore it).
     * No duplicates allowed and maintain the order in ascending order.
     *
     * @param value String to be added.
     */
    public void add(String value) {
        // discard null and empty value.
        if (value == null || value.length() == 0) {
            return;
        }
        // discard illege word that is not strictly consisting of only letters.
        if (!value.matches("^[a-zA-Z]+$")) {
            return;
        }

        // discard duplicates by calling a helper function using recursion
        if (contains(value)) {
            return;
        }

        // insert the value at the begining if Node is null or the value of
        // first reference is larger than current value.
        if (head == null || head.data.compareTo(value) > 0) {
            head = new Node(value, head);
            return;
        }

        /*
         * Separates conditions such:
         * 1. inserting to the end of Node; 2. inserting in the middle
         * into a helper function using recursion
         */
        Node temp = head;
        recursionSortAndInsert(temp, value);
    }

    private void recursionSortAndInsert(Node node, String value) {
        // Base case 1: insert at the tail
        if (node.next == null) {
            node.next = new Node(value, null);
            return;
        }
        // Base case 2: insert after
        if (node.next.data.compareTo(value) > 0) {
            node.next = new Node(value, node.next);
            return;
        }

        // Recursion case
        recursionSortAndInsert(node.next, value);
    }

    /**
     * Checks the size (number of data items) of the list.
     */
    public int size() {
        // calls a helper function to calculate the size of array.
        return recursionSize(head);
    }

    /**
     * Find out the size of the linked list.
     */
    private int recursionSize(Node node) {
        // Base case
        if (node == null) {
            return 0;
        }

        // Recursion case
        return recursionSize(node.next) + 1;
    }

    /**
     * Displays the values of the list.
     */
    public void display() {
        StringBuilder sb = new StringBuilder();
        // calls helper function to output proper result using recursion
        StringBuilder display = recursionDisplay(head, sb);

        display.insert(0, "[");
        // returned displaying string will have extra ", " at the end
        display.replace(display.lastIndexOf(","), display.length() - 1, "]");
        System.out.println(display.toString());
    }

    /**
     * Helper function of display(): output the proper result.
     * Base case: when reaching to the tail of the list, concatenation is finished
     *      so that simply quit the recursion
     * Recursion case: append the value of current node and explore the next,
     *      process repeats until base case is valid
     *
     * @param node object to traverse
     * @param sb carries the most recent string
     * @return formatted StringBuilder object
     */
    private StringBuilder recursionDisplay(Node node, StringBuilder sb) {
        // Base case
        if (node == null) {
            return sb;
        }

        // Recursion case
        sb.append(node.data);
        sb.append(", ");
        return recursionDisplay(node.next, sb);
    }

    /**
     * Returns true if the key value is in the list.
     *
     * @param key String key to search
     * @return true if found, false if not found
     */
    public boolean contains(String key) {
        Node temp = head;
        // calls the helper function
        return recursionFind(temp, key);
    }

    /**
     * Helper function of contains(): find out whether or not target object exists in linked list.
     * Base case 1: return result and quit if no matching element existed
     * Base case 2: return result and quit if found matching element
     * Recursion case: traversing every node util one of base cases is satisfied.
     *
     * @param node object to traverse
     * @param key target object
     * @return boolean whether the target object has been found
     */
    private boolean recursionFind(Node node, String key) {
        // Base case 1
        if (node == null) {
            return false;
        }
        // Base case 2
        if (node.data.equals(key)) {
            return true;
        }

        // Recursion case
        return recursionFind(node.next, key);
    }

    /**
     * Returns true is the list is empty.
     *
     * @return true if it is empty, false if it is not empty
     */
    public boolean isEmpty() {
        // call method size() to see if the number is nodes is greate than 0 or not.
        if (size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * Removes and returns the first String object of the list.
     *
     * @return String object that is removed. If the list is empty, returns null
     */
    public String removeFirst() {
        // corner case prevents exception which the linked list is initally null
        if (head == null) {
            return null;
        }

        // unlinks the node and return the value of it
        String removed = head.data;
        head = head.next;
        return removed;
    }

    /**
     * Removes and returns String object at the specified index.
     *
     * @param index index to remove String object
     * @return String object that is removed
     * @throws RuntimeException for invalid index value (index < 0 || index >= size())
     */
    public String removeAt(int index) {
        // edge case 1: throws exception if index is out of bound
        if (index < 0 || index >= size()) {
            throw new RuntimeException();
        }
        // edge case 2: terminate following execution if the linked list is null
        if (head == null) {
            return null;
        }
        // edge case 3: index == 0 means unlinking the first node
        if (index == 0) {
            return removeFirst();
        }

        // calls helper function using recursion
        Node prev = null;
        Node cur = head;
        int count = 0;
        return recursionRemoveAt(prev, cur, index, count);
    }

    /**
     * Helper function of removeAt(): removes and returns String object at the specified index.
     * Base case: retrieves the value of target node for returning and unlink the node.
     * Recursion case: 'pre' is always one node behind 'cur', and recursively, both 'pre' and 'cur' points at the next
     *      node until base case is reached.
     *
     * @param pre previous node
     * @param cur object to traverse
     * @param targetIndex target removal node
     * @param count keeps track of 'targetIndex'
     * @return String object that is removed
     */
    private String recursionRemoveAt(Node pre, Node cur, int targetIndex, int count) {
        // Base case
        if (count == targetIndex) {
            String removed = cur.data;
            pre.next = cur.next;
            return removed;
        }

        // Recursion case
        return recursionRemoveAt(cur, cur.next, targetIndex, ++count);
    }
}
