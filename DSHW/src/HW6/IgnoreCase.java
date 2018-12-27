import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6: IgnoreCase class
 *
 * Sorts words by case insensitive alphabetical order.
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class IgnoreCase implements Comparator<Word> {
    @Override
    public int compare(Word word1, Word word2) {
        return word1.getWord().compareToIgnoreCase(word2.getWord());
    }
}
