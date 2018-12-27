import java.util.HashSet;
import java.util.Set;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6: Word class
 *
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class Word implements Comparable<Word> {
    /**
     * word value.
     */
    private String word;

    /**
     * HashSet stores the occurrence (line number) of a word.
     */
    private Set<Integer> index;

    /**
     * frequency as its occurrence.
     */
    private int frequency;

    /**
     * parameterized constructor with String.
     * @param vocab new word instance
     */
    public Word(String vocab) {
        if (vocab == null || vocab.length() == 0 || !vocab.matches("^[a-zA-z]+$")) {
            return;
        }
        this.word = vocab;
        this.index = new HashSet<>();
        this.frequency = 1;
    }

    /**
     * getter method for variable 'word'.
     * @return word value
     */
    public String getWord() {
        return this.word;
    }

    /**
     * getter method for 'frequency'.
     * @return frequency of the word
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * getter method for 'index'.
     * @return index object
     */
    public Set<Integer> getIndex() {
        return this.index;
    }

    /**
     * setter for variable 'word'.
     * @param vocab a word
     */
    public void setWord(String vocab) {
        if (vocab == null || vocab.length() == 0 || !vocab.matches("^[a-zA-z]+$")) {
            return;
        }
        this.word = vocab;
    }

    /**
     * setter method for variable 'freq'.
     * @param freq incremental freq for later use
     */
    public void setFrequency(int freq) {
        this.frequency = freq;
    }

    /**
     * add the occurrence position to 'index'.
     * @param lineNum occurrence line number
     */
    public void addToIndex(Integer lineNum) {
        this.index.add(lineNum);
    }

    /**
     * override toString method to format the output.
     * @return 'index' object in string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word).append(" ").append(getFrequency()).append(" ").append("[");
        for (Integer in: index) {
            stringBuilder.append(in).append(", ");
        }

        String result = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
        return result + "]";
    }

    /**
     * override compareTo method to implement customize Comparable interface.
     * @param o another Word object to be compared
     * @return difference between two word values
     */
    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.getWord());
    }
}
