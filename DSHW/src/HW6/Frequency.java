import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6: Frequency class
 *
 * Sorts words according to their frequencies (a word with highest frequency comes first).
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class Frequency implements Comparator<Word> {
    /**
     * overrides default method.
     * @param word1 first word
     * @param word2 second word
     * @return difference between words
     */
    @Override
    public int compare(Word word1, Word word2) {
        return word2.getFrequency() - word1.getFrequency();
    }
}
