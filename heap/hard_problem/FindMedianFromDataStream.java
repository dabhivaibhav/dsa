package heap.hard_problem;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/*
Leetcode 295. Find Median from Data Stream
The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value,
and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.

Implement the MedianFinder class:
MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.

Example 1:
Input;
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output:
[null, null, null, 1.5, null, 2.0]

Explanation:
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0


Constraints:
            -10^5 <= num <= 10^5
            There will be at least one element in the data structure before calling findMedian.
            At most 5 * 10^4 calls will be made to addNum and findMedian.

Follow up:
If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
 */
public class FindMedianFromDataStream {

    public static void main(String[] args) {
        int[] arr = new int[100000];
        int position = 0;
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(6);
        medianFinder.addNum(10);
        medianFinder.addNum(2);

        System.out.println(medianFinder.findMedian());


    }

    /**
     * Calculates the median of all elements added so far using an "Extract-and-Restore" strategy.
     * <p>
     * Since a PriorityQueue does not allow index-based access to the middle element,
     * this method temporarily empties the heap to determine the median.
     * </p>
     * * <b>Algorithm Steps:</b>
     * <ol>
     * <li><b>Validation:</b> Check if the heap is empty to avoid division by zero or errors.</li>
     * <li><b>Extraction:</b> Uses a while(!maxHeap.isEmpty()) loop to poll every element.
     * Because this is a Max-Heap, elements are stored in the List in
     * descending order (largest to smallest).</li>
     * <li><b>Restoration:</b> Immediately iterates through the temporary list to offer()
     * all elements back into the maxHeap. This ensures the object state is
     * preserved for future calls.</li>
     * <li><b>Median Logic:</b>
     * <ul>
     * <li>If the total count is odd: Returns the element at the middle index.</li>
     * <li>If the total count is even: Calculates the average of the two middle elements.</li>
     * </ul>
     * </li>
     * </ol>
     * * <b>Complexity:</b>
     * <ul>
     * <li>Time: O(N log N) due to polling and re-offering N elements.</li>
     * <li>Space: O(N) to store elements in the temporary List.</li>
     * </ul>
     * * @return The median as a double.
     */

    static class MedianFinder {
        PriorityQueue<Integer> maxHeap;

        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a, b) -> b - a);
        }

        public void addNum(int num) {
            maxHeap.offer(num);
        }

        public double findMedian() {
            int length = maxHeap.size();
            if (length == 0) return 0.0;

            List<Integer> temp = new ArrayList<>();

            while (!maxHeap.isEmpty()) {
                temp.add(maxHeap.poll());
            }

            for (int val : temp) {
                maxHeap.offer(val);
            }

            if (length % 2 == 0) {
                return (temp.get(length / 2) + temp.get(length / 2 - 1)) / 2.0;
            } else {
                return temp.get(length / 2);
            }
        }
    }
}
