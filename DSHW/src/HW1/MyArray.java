package HW1;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework 1 HW1.MyArray
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class MyArray {
    /** Objects of words. */
    private String[] array = null;

    /** Always points at the index ready for insertion. */
    private int addIndex = 0;

    /** Default constructor. */
    public MyArray() {
        this.array = new String[5];
    }

    /**
     * @param initialCapacity creates a specific size of String array.
     *
     * Parameterized constructor.
     */
    public MyArray(int initialCapacity) {
        this.array = new String[initialCapacity];
    }

    /**
     * @param text a single word from file.
     *
     * Insertion operation.
     * O(n).
     */
    public void add(String text) {
        if (text == null || text.length() == 0) {
            return;
        }

        if (!text.matches("^[a-zA-Z]+")) {
            return;
        }

        if (array.length == 0) {
            array = new String[1];
        }

        if (addIndex == array.length) {
            String[] temp = new String[array.length * 2];
            System.arraycopy(array, 0, temp, 0, array.length);
            array = temp;
        }
        array[addIndex++] = text;
    }

    /**
     * @param key target word.
     * @return boolean.
     *
     * O(n).
     */
    public boolean search(String key) {
        // if key is null, simply return false
        if (key == null) {
            return false;
        }

        for (int i = 0; i < addIndex; i++) {
            if (array[i].equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return int the word count of the array.
     *
     * O(1).
     */
    public int size() {
        return addIndex;
    }

    /**
     * @return int the capacity of the array.
     *
     * O(1).
     */
    public int getCapacity() {
        return array.length;
    }

    /**
     * Visit from index 0 to n, where index n is the first position holding a null for insertion.
     *
     * O(n).
     */
    public void display() {
        StringBuilder displayString = new StringBuilder();
        for (int i = 0; i < addIndex; i++) {
            displayString.append(array[i]);
            displayString.append(" ");
        }
        System.out.println(displayString.toString());
    }

    /**
     * Double pointer.
     * Left pointer points at one word, the right pointers will traverse through the rest array and shift
     * down a position if a duplicate of that particular word is found.
     *
     * O(n^2).
     */
    public void removeDups() {
        for (int left = 0; left < addIndex - 1; left++) {
            for (int right = left + 1; right < addIndex; right++) {
                if (array[left].equals(array[right])) {
                    System.arraycopy(array, right + 1, array, right,  addIndex - 1 - right);
                    right--;
                    array[addIndex - 1] = null;
                    addIndex--;
                }
            }
        }
    }
}
