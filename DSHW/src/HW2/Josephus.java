package HW2;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 17683 Data Structures for Application Programmers.
 * <p>
 * Andrew ID: wenxuanx
 *
 * @author Wenxuan Xu
 */
public class Josephus {
    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {
        Queue<Integer> arrayDeque = new ArrayDeque<>();

        // Call helper function to finish the remaining task using ArrayDeque
        return helper(arrayDeque, size, rotation);
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     *
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        Queue<Integer> linkedList = new LinkedList<>();

        // Call helper function to finish the remaining task using LinkedList
        return helper(linkedList, size, rotation);
    }

    /**
     * Uses LinkedList class to find the survivor's position.
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     *
     * Time complexity: O(n)
     */
    public int playWithLLAt(int size, int rotation) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            linkedList.add(i + 1);
        }

        // pointer are 1-based
        int pointer = rotation;
        while (linkedList.size() != 1) {
            if (pointer > linkedList.size()) {
                pointer = pointer % linkedList.size();
                // Preventing IndexOutOfBoundsException
                if (pointer == 0) {
                    pointer = linkedList.size();
                }
            }
            linkedList.remove(pointer - 1);
            pointer = pointer + rotation - 1;
        }
        return linkedList.element();
    }


    /**
     * @param list     Different data structure (ArrayDeque & LinkedList as Queue/Deque) for comparison
     * @param size     Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     *
     * n represents the 'size', and m represents 'rotation'
     * Time complexity: O(n * m)
     */
    private int helper(Queue<Integer> list, int size, int rotation) {
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        for (int i = 0; i < size; i++) {
            list.offer(i + 1);
        }

        while (list.size() != 1) {
            for (int j = 1; j < rotation; j++) {
                // Place the survivor of each round to the tail
                Integer temp = list.poll();
                list.offer(temp);
            }
            list.poll();
        }
        return list.element();
    }
}
