import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6: AlphaFreq class
 *
 * Sorts words according to alphabets first and if there is a tie,
 * then words are sorted by their frequencies in ascending order
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class AlphaFreq implements Comparator<Word> {
    /**
     * overrides default method.
     * @param word1 first word
     * @param word2 second word
     * @return difference between words.
     */
    @Override
    public int compare(Word word1, Word word2) {
        int result = word1.getWord().compareTo(word2.getWord());
        if (result != 0) {
            return result;
        }
        return word1.getFrequency() - word2.getFrequency();
    }
}
