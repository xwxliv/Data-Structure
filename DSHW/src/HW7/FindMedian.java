
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 17683 Data Structures for Application Programmers.
 * <p>
 * Lab 7 Heaps and Java PriorityQueue class
 * Find the median of integers using both maxHeap and minHeap
 * <p>
 * Andrew ID: wenxuanx
 *
 * @author Wenxuan Xu
 */
public class FindMedian {
    /**
     * max heap data structure using PQ.
     */
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(20, Collections.reverseOrder());
    /**
     * min heap data structure using PQ.
     */
    private PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(20);

    /**
     * Adds int values into two heaps.
     * It should maintain a condition that maxHeap.size() >= minHeap.size().
     *
     * @param value int value to add
     */
    public void addNumber(int value) {
        if (maxHeap.size() == minHeap.size()) {
            maxHeap.offer(value);
        } else {
            minHeap.offer(value);
        }

        if (!minHeap.isEmpty()) {
            if (minHeap.peek() < maxHeap.peek()) {
                Integer min = minHeap.poll();
                minHeap.offer(maxHeap.poll());
                maxHeap.offer(min);
            }
        }
    }

    /**
     * Returns the median value of the added values.
     * If maxHeap and minHeap are of different sizes, then maxHeap must have one extra element.
     *
     * @return median value
     */
    public double getMedian() {
        if ((maxHeap.size() + minHeap.size()) % 2 == 0) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
        return (double) maxHeap.peek();
    }

    /**
     * Test program to add int values and find median of those.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        FindMedian tester = new FindMedian();
        tester.addNumber(6);
        tester.addNumber(4);
        tester.addNumber(3);
        tester.addNumber(10);
        tester.addNumber(12);

        // should print 6.0
        System.out.println(tester.getMedian());

        tester.addNumber(5);

        // should print 5.5
        System.out.println(tester.getMedian());

        tester.addNumber(7);
        tester.addNumber(8);

        // should print 6.5
        System.out.println(tester.getMedian());
    }

}
