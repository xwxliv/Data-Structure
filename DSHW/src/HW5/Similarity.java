package HW5;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 5
 * Deliverable Question 1: What and why use the data structure I choose in this HW5?
 * Use HashMap from the Java Collection Framework (java.util.?).
 * Purpose: efficiently meet the main requirement:
 *          1. frequency of the word is used for all calculation and it's non-unique;
 *          2. occurrence (can be repeated) of each distinct word in value.
 *
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class Similarity {
    /**
     * HashMap object.
     */
    private Map<String, BigInteger> hashmap = new HashMap<>();
    /**
     * keep track of the number of lines in a single file.
     */
    private int numOfLines = 0;
    /**
     * keep track of the number of words.
     */
    private BigInteger numOfWords = BigInteger.ZERO;

    /**
     * Parameterized constructor.
     * @param string object to manipulate
     */
    public Similarity(String string) {
        if (string == null || string.length() == 0) {
            return;
        }
        numOfLines = string.split("\n").length;
        String[] strArray = string.toLowerCase().split("\\W");
        insert(strArray);
    }

    /**
     * Parameterized constructor.
     * @param file input file
     */
    public Similarity(File file) {
        // edge case check if the file is empty
        if (file == null || !file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file, "latin1")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] strArray = line.toLowerCase().split("\\W");
                insert(strArray);
                ++numOfLines;
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error occurred!");
        }
    }

    /**
     * helper function of two constructors.
     * @param strArray a full line in text or file
     */
    private void insert(String[] strArray) {
        //edge case
        if (strArray == null || strArray.length == 0) {
            return;
        }

        for (String word: strArray) {
            // edge cases
            if (word == null || word.length() == 0 || !word.matches("^[a-z]+$")) {
                continue;
            }

            // calculate frequency
            if (!hashmap.containsKey(word)) {
                hashmap.put(word, BigInteger.ONE);
            } else {
                hashmap.put(word, hashmap.get(word).add(BigInteger.ONE));
            }
            numOfWords = numOfWords.add(BigInteger.ONE);
        }
    }

    /**
     * find out the number of lines.
     * @return nums of lines.
     */
    public int numOfLines() {
        return numOfLines;
    }

    /**
     * find out the number of words.
     * @return the number of words
     */
    public BigInteger numOfWords() {
        return numOfWords;
    }

    /**
     * find out the number of non duplicated words.
     * @return number of non duplicated words.
     */
    public int numOfWordsNoDups() {
        return hashmap.size();
    }

    /**
     * determines the euclideanNorm of assigned HashMap object.
     * @return result of the HashMap
     */
    public double euclideanNorm() {
        return calEuclideanNorm(this.hashmap);
    }

    /**
     * Helper function of euclideanNorm().
     * @param map assigned HashMap
     * @return euclideanNorm result of the assigned HashMap object
     */
    private double calEuclideanNorm(Map<String, BigInteger> map) {
        // edge case
        if (map == null || map.isEmpty()) {
            return 0d;
        }
        BigInteger sum = BigInteger.ZERO;

        for (BigInteger frequency: map.values()) {
            // add up each frequency to the power of 2
            sum = sum.add(frequency.pow(2));
        }
        return Math.sqrt(sum.doubleValue());
    }

    /**
     * Find out the dot-product of two frequency vectors X and Y.
     *
     * Deliverable question 2: Why your implementation doesn't fall into O(n^4) on average?
     * Only one for-loop is used traversing the map in O(n). Process the calculation iff there is a common key existed
     * in both map objects in a constant time. This avoids quadratic running time that implicates using two for-loops.
     *
     * @param map the other map object used for calculation
     * @return sum of two frequency vectors X and Y
     */
    public double dotProduct(Map<String, BigInteger> map) {
        // edge cases
        if (hashmap == null || hashmap.isEmpty() || map == null || map.isEmpty()) {
            return 0d;
        }

        // stores the sum of dot product of X and Y
        BigInteger result = BigInteger.ZERO;
        for (Map.Entry<String, BigInteger> entry: hashmap.entrySet()) {
            String key = entry.getKey();
            if (map.containsKey(key)) {
                result = result.add(entry.getValue().multiply(map.get(key)));
            }
        }
        return result.doubleValue();
    }

    /**
     * Find out the cosine similarity of two texts or files.
     * @param map the other map object used for calculation
     * @return distance between Map1 and Map2
     */
    public double distance(Map<String, BigInteger> map) {
        // edge case
        if (map == null || map.isEmpty() || hashmap == null || hashmap.isEmpty()) {
            return (Math.PI / 2);
        }

        // if two map objects are the same (reference), return 0 as no distance
        if (hashmap.equals(map)) {
            return 0d;
        }

        return Math.acos(dotProduct(map) / (euclideanNorm() * calEuclideanNorm(map)));
    }

    /**
     * the other HashMap object for later calculation (dotProduct() or distance()).
     * @return HashMap object
     */
    public Map<String, BigInteger> getMap() {
        return new HashMap<>(hashmap);
    }
}
