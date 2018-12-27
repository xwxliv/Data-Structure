import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6: Index class
 *
 * In charge of building an index tree in three different ways using three different methods,
 * Andrew ID: wenxuanx
 * @author Wenxuan Xu
 */
public class Index {
    /**
     * constructor with String.
     * @param fileName dear filename
     * @return a BST
     */
    public BST<Word> buildIndex(String fileName)  {
        return buildIndex(fileName, null);
    }

    /**
     * assigned file and comparator for building a BST.
     * @param fileName dear filename
     * @param comparator but carries a specified compator
     * @return a BST
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        BST<Word> root = new BST<>(comparator);

        // edge case
        if (fileName == null || fileName.length() == 0) {
            return root;
        }

        int lineNum = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName), "latin1");
            while (scanner.hasNextLine()) {
                ++lineNum;
                String line = scanner.nextLine();
                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {
                    if (isValidWord(word)) {
                        // a recipe exclusive to IgnoreCase class: converting word to lower case beforehand
                        if (comparator instanceof IgnoreCase) {
                            word = word.toLowerCase();
                        }
                        // found: addToIndex && insert; not found: addToIndex && update frequency
                        Word node = new Word(word);
                        Word isFound = root.search(node);
                        if (isFound == null) {
                            node.addToIndex(lineNum);
                            root.insert(node);
                        } else {
                            isFound.addToIndex(lineNum);
                            isFound.setFrequency(isFound.getFrequency() + 1);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return root;
    }

    /**
     * assigned list and comparator for building a BST.
     * @param list dear list
     * @param comparator special order for BST's ordering
     * @return  BST
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        // edge case
        if (list == null || list.size() == 0) {
            return null;
        }

        BST<Word> root = new BST<>(comparator);

        // blindly insert
        for (Word node: list) {
            root.insert(node);
        }
        return root;
    }

    /**
     * alphabetically sort from largest to smallest.
     * @param tree dear tree
     * @return a list of nodes info
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        // edge case
        if (tree == null) {
            return null;
        }

        ArrayList<Word> result = new ArrayList<>();
        // blindly add
        for (Word node: tree) {
            result.add(node);
        }
        // sort it based on the spirit of what I wrote
        result.sort(new AlphaFreq());
        return result;
    }

    /**
     * sorting is based on frequency from largest to smallest.
     * @param tree dear tree
     * @return a list of nodes info
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        // edge case
        if (tree == null) {
            return null;
        }

        ArrayList<Word> result = new ArrayList<>();
        // blindly add
        for (Word node: tree) {
            result.add(node);
        }
        // sort it based on the spirit of what I wrote
        result.sort(new Frequency());
        return result;
    }

    /**
     * get highest frequency node(s).
     * @param tree dear BST
     * @return a list of nodes info
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        // base case
        if (tree == null) {
            return null;
        }

        // borrow the result from above method
        ArrayList<Word> arrayList = sortByFrequency(tree);
        int highestFre = arrayList.get(0).getFrequency();

        ArrayList<Word> result = new ArrayList<>();

        // stop right at where node's freq is less than the highest frequency
        for (Word node: arrayList) {
            if (node.getFrequency() == highestFre) {
                result.add(node);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * check if a 'word' is a proper word or not.
     * @param word dear word
     * @return legal word or not
     */
    private boolean isValidWord(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        return word.matches("^[a-zA-Z]+$");
    }
}
