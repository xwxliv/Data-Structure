package HW4;

/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 4
 * HashTable Implementation with linear probing
 *
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class MyHashTable implements MyHTInterface {

    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;

    /**
     * number of items.
     */
    private int nItems = 0;

    /**
     * number of collisions.
     */
    private int nCollisions = 0;

    /**
     * constant for default capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * constant for default load factor.
     */
    private static final double LOAD_FACTOR = 0.5;


    /**
     * constant for deleted item.
     */
    private static final DataItem DELETED = new DataItem("#DEL#");

    /**
     * default constructor.
     */
    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * parameterized constructor with initial capacity.
     * @param initialCapacity assigned capacity
     */
    public MyHashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new RuntimeException();
        }
        hashArray = new DataItem[initialCapacity];
    }

    /**
     * Inserts a new String value (word).
     * Frequency of each word to be stored too.
     * @param value String value to add
     */
    public void insert(String value) {
        if (value == null || value.trim().length() == 0 || !value.matches("^[a-z]+$")) {
            return;
        }

        value = value.trim();

        int hashVal = hashFunc(value);
        // edge case
        if (hashVal < 0) {
            return;
        }

        // helper variables for the purpose of determining whether the value is repetitive or collision
        int bucket = hashVal;
        int index = hashVal;
        boolean flag = false;
        int len = 0;

        // put KV pair into the array iff the position is available for insertion
        if (hashArray[hashVal] == null || hashArray[hashVal] == DELETED) {
            hashArray[hashVal] = new DataItem(value, 1);
            nItems++;
            return;
        }

        while (hashArray[index] != null) {
            // check if it's a repetitive key
            if (hashArray[index] != null && hashArray[index].value.equals(value)) {
                hashArray[index].frequency++;
                return;
            }
            index++;
            index %= hashArray.length;
            if (index == bucket) {
                break;
            }
        }

        while (hashArray[hashVal] != null && hashArray[hashVal] != DELETED && len < hashArray.length) {
            // collision counts only once
            if (!flag && hashFunc(hashArray[hashVal].value) == bucket) {
                nCollisions++;
                flag = true;
            }
            hashVal++;
            len++;
            hashVal %= hashArray.length;
        }

        hashArray[hashVal] = new DataItem(value, 1);
        nItems++;
        // check whether exceed the efficient load level. if yes, double the capacity
        rehash();
    }

    /**
     * Returns the size, number of items, of the table.
     * @return the number of items in the table
     */
    public int size() {
        return nItems;
    }

    /**
     * Displays the values of the table.
     * If an index is empty, it shows **
     * If previously existed data item got deleted, then it should show #DEL#
     */
    public void display() {
        // concat the result as desired output
        StringBuilder sb = new StringBuilder();
        for (DataItem item: hashArray) {
            if (item == null) {
                sb.append("**");
            } else {
                sb.append(item.toString());
            }
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }

    /**
     * Returns true if value is contained in the table.
     * @param key String key value to search
     * @return true if found, false if not found.
     */
    public boolean contains(String key) {
        // call helper function 'search'
        return search(key) != -1;
    }

    /**
     * Returns the number of collisions in relation to insert and rehash.
     * When rehashing process happens, the number of collisions should be properly updated.
     *
     * The definition of collision is "two different keys map to the same hash value."
     * Be careful with the situation where you could overcount.
     * Try to think as if you are using separate chaining.
     * "How would you count the number of collisions?" when using separate chaining.
     * @return number of collisions
     */
    public int numOfCollisions() {
        return nCollisions;
    }

    /**
     * Returns the hash value of a String.
     * Assume that String value is going to be a word with all lowercase letters.
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    public int hashValue(String value) {
        // edge case
        if (value == null || value.length() == 0 || !value.matches("^[a-z]+$")) {
            return 0;
        }
        // call helper function 'search'
        return hashFunc(value);
    }

    /**
     * Returns the frequency of a key String.
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    public int showFrequency(String key) {
        // call helper function 'search'
        int index = search(key);
        if (index != -1) {
            return hashArray[index].frequency;
        }
        return 0;
    }

    /**
     * Removes and returns removed value.
     * @param key String to remove
     * @return value that is removed. If not found, return null
     */
    public String remove(String key) {
        // call helper method
        int index = search(key);
        // handle removal and decrement number of size in the array
        if (index != -1) {
            hashArray[index] = DELETED;
            --nItems;
            return key;
        }
        return null;
    }

    /**
     * helper function: find out the index of the key. -1 if not found.
     * @param key target key to search
     * @return the bucket position of the key.
     */
    private int search(String key) {
        // edge case
        if (key == null || key.length() == 0 || !key.matches("^[a-z]+$")) {
            return -1;
        }

        // locate the position or range of the key
        int hash = hashFunc(key);
        int bucket = hash;

        // return the actual bucket index
        while (hashArray[hash] != null) {
            if (hashArray[hash].value.equals(key)) {
                return hash;
            }
            hash++;
            hash %= hashArray.length;
            if (hash == bucket) {
                return -1;
            }
        }
        return -1;
    }
    /**
     * Instead of using String's hashCode, you are to implement your own here.
     * You need to take the table length into your account in this method.
     *
     * In other words, you are to combine the following two steps into one step.
     * 1. converting Object into integer value
     * 2. compress into the table using modular hashing (division method)
     *
     * Helper method to hash a string for English lowercase alphabet and blank,
     * we have 27 total. But, you can assume that blank will not be added into
     * your table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's method should be applied as follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time, I would encourage you to try with other
     * constant values than 27 and compare the results but it is not required.
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        // edge cases
        if (input == null || input.length() == 0) {
            return -1;
        }
        if (!input.matches("^[a-z]+$")) {
            return -1;
        }

        // prevents overflow
        long result = 0;
        // Horner's method and folding
        for (char c: input.toCharArray()) {
            result = (result * 27 + (c - 'a' + 1)) % hashArray.length;
        }
        result %= hashArray.length;
        return (int) result;
    }

    /**
     * doubles array length and rehash items whenever the load factor is reached.
     * Note: do not include the number of deleted spaces to check the load factor.
     * Remember that deleted spaces are available for insertion.
     */
    private void rehash() {
        // check the fullness of the array.
        if ((1.0 * nItems / hashArray.length) <= LOAD_FACTOR) {
            return;
        }

        DataItem[] tmp = hashArray;
        int newCapacity = findPrime(hashArray.length);
        hashArray = new DataItem[newCapacity];
        nCollisions = 0;

        // traverse each KV pair in the original array
        for (DataItem item: tmp) {
            if (item != null && item != DELETED) {
                int hash = hashFunc(item.value);
                int bucket = hash;
                boolean flag = false;
                int len = 0;


                // copy it to the expanded array
                while (hashArray[hash] != null && hashArray[hash] != DELETED && len < hashArray.length) {
                    // count collision only once
                    if (!flag && hashFunc(hashArray[hash].value) == bucket) {
                        nCollisions++;
                        flag = true;
                    }
                    hash++;
                    hash %= hashArray.length;
                    len++;
                }
                hashArray[hash] = item;
            }
        }
        System.out.println("Rehashing " + nItems + " items, new length is " + newCapacity);
    }

    /**
     * Check if the capacity is a prime number or not.
     * @param newCapacity to check
     * @return check result
     *
     * Time complexity: O(log n)
     */
    private boolean isPrime(int newCapacity) {
        // edge cases
        if (newCapacity <= 1) {
            return false;
        }
        if (newCapacity == 2) {
            return true;
        }
        // for efficiency
        if ((newCapacity % 2) == 0) {
            return false;
        }

        // starts from 3, checking if is a valid prime number
        for (int i = 3; i * i <= newCapacity; i++) {
            if (newCapacity % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find next available prime number.
     * @param capacity capacity that is not a prime number
     * @return prime capacity number
     */
    private int findPrime(int capacity) {
        capacity = capacity * 2 + 1;
        // avoid such cases where 14, 15, and 16 are consecutively non-prime number
        while (!isPrime(capacity)) {
            ++capacity;
        }
        return capacity;
    }
    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        /**
         * Parameterized constructor with value for DELETED var.
         * @param val inserted value
         */
        DataItem(String val) {
            this.value = val;
        }

        /**
         * Parameterized constructor with value and its frequency.
         * @param val inserted value
         * @param freq occurrence of the value
         */
        DataItem(String val, int freq) {
            this.value = val;
            this.frequency = freq;
        }

        /**
         * override the toString method for proper output.
         * @return formatted output.
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (value.equals("#DEL#")) {
                return value;
            }
            sb.append("[");
            sb.append(value);
            sb.append(", ");
            sb.append(frequency);
            sb.append("]");
            return sb.toString();
        }
    }
}
