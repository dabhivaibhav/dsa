package heap.hard_problem;

import java.util.ArrayList;
import java.util.Collections;
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
        medianFinder.addNumBetter(6);
        medianFinder.addNumBetter(10);
        medianFinder.addNumBetter(2);

        System.out.println(medianFinder.findMedianBruteForce());
        System.out.println(medianFinder.findMedianBetter());

        MedianFinderOptimal medianFinderOptimal = new MedianFinderOptimal();
        medianFinderOptimal.addNum(6);
        medianFinderOptimal.addNum(10);
        medianFinderOptimal.addNum(2);
        System.out.println(medianFinderOptimal.findMedian());


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
        List<Integer> arr;

        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a, b) -> b - a);
            arr = new ArrayList<>();
        }

        public void addNum(int num) {
            maxHeap.offer(num);
        }

        public void addNumBetter(int num) {
            arr.add(num);
        }


        public double findMedianBruteForce() {
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

        public double findMedianBetter() {

            Collections.sort(arr);
            int length = arr.size();

            if (length % 2 == 0) {
                return (arr.get(length / 2) + arr.get(length / 2 - 1)) / 2.0;
            }
            return arr.get(length / 2);
        }
    }

    /**
     * MedianFinderOptimal
     * <p>
     * What this class does:
     * <p>
     * Maintains a data stream of numbers
     * and efficiently returns the median
     * at any point in time.
     * <p>
     * <p>
     * Core Idea:
     * <p>
     * Use TWO HEAPS:
     * <p>
     * 1. Max Heap (maxHeap)
     * → Stores the smaller half of numbers
     * → Largest of small numbers at the top
     * <p>
     * 2. Min Heap (minHeap)
     * → Stores the larger half of numbers
     * → Smallest of large numbers at the top
     * <p>
     * <p>
     * Mental Model:
     * <p>
     * Think of splitting numbers into two halves:
     * <p>
     * [ smaller half ] | [ larger half ]
     * <p>
     * maxHeap        minHeap
     * <p>
     * We maintain:
     * <p>
     * - All elements in maxHeap ≤ elements in minHeap
     * - Size difference ≤ 1
     * <p>
     * <p>
     * Why this works:
     * <p>
     * Median always lies:
     * <p>
     * - At the top of one heap (odd count)
     * - Or between tops of both heaps (even count)
     * <p>
     * <p>
     * METHOD: addNum(int num)
     * <p>
     * What this method does:
     * <p>
     * Inserts a number into the data structure
     * while maintaining heap properties.
     * <p>
     * <p>
     * Step-by-Step Logic:
     * <p>
     * Step 1: Decide correct heap
     * <p>
     * If maxHeap is empty OR num ≤ maxHeap.peek():
     * <p>
     * → num belongs to smaller half
     * → add to maxHeap
     * <p>
     * Else:
     * <p>
     * → num belongs to larger half
     * → add to minHeap
     * <p>
     * <p>
     * Step 2: Balance the heaps
     * <p>
     * We must ensure:
     * <p>
     * |size(maxHeap) - size(minHeap)| ≤ 1
     * <p>
     * <p>
     * Case 1:
     * <p>
     * maxHeap has too many elements:
     * <p>
     * → Move top element to minHeap
     * <p>
     * minHeap.add(maxHeap.poll())
     * <p>
     * <p>
     * Case 2:
     * <p>
     * minHeap has too many elements:
     * <p>
     * → Move top element to maxHeap
     * <p>
     * maxHeap.add(minHeap.poll())
     * <p>
     * <p>
     * Why balancing is important:
     * <p>
     * Ensures median can always be
     * directly derived from heap tops.
     * <p>
     * <p>
     * ---------------------------------------------------
     * METHOD: findMedian()
     * ---------------------------------------------------
     * <p>
     * What this method does:
     * <p>
     * Returns the median of all inserted numbers.
     * <p>
     * <p>
     * Cases:
     * <p>
     * Case 1: maxHeap has more elements
     * <p>
     * → Median = maxHeap.peek()
     * <p>
     * <p>
     * Case 2: minHeap has more elements
     * <p>
     * → Median = minHeap.peek()
     * <p>
     * <p>
     * Case 3: Both heaps have equal size
     * <p>
     * → Median = average of both tops
     * <p>
     * (maxHeap.peek() + minHeap.peek()) / 2.0
     * <p>
     * <p>
     * ---------------------------------------------------
     * Example Walkthrough:
     * ---------------------------------------------------
     * <p>
     * Stream: [5, 3, 8, 2]
     * <p>
     * Insert 5:
     * <p>
     * maxHeap = [5]
     * minHeap = []
     * <p>
     * Median = 5
     * <p>
     * <p>
     * Insert 3:
     * <p>
     * maxHeap = [5, 3]
     * → balance → move 5 to minHeap
     * <p>
     * maxHeap = [3]
     * minHeap = [5]
     * <p>
     * Median = (3 + 5) / 2 = 4
     * <p>
     * <p>
     * Insert 8:
     * <p>
     * minHeap = [5, 8]
     * <p>
     * Median = 5
     * <p>
     * <p>
     * Insert 2:
     * <p>
     * maxHeap = [3, 2]
     * <p>
     * Median = (3 + 5) / 2 = 4
     * <p>
     * <p>
     * ---------------------------------------------------
     * Complexity:
     * ---------------------------------------------------
     * <p>
     * Time Complexity:
     * <p>
     * addNum():
     * O(log n)
     * → Heap insertion/removal
     * <p>
     * findMedian():
     * O(1)
     * → Direct access to heap tops
     * <p>
     * <p>
     * Space Complexity:
     * <p>
     * O(n)
     * <p>
     * → Stores all elements in heaps
     * <p>
     * <p>
     * ---------------------------------------------------
     * Interview Takeaway:
     * ---------------------------------------------------
     * <p>
     * This is a CLASSIC problem
     * demonstrating:
     * <p>
     * → Two Heap Technique
     * → Streaming data processing
     * <p>
     * <p>
     * Key Patterns:
     * <p>
     * 1. Split data into two halves
     * 2. Maintain order using heaps
     * 3. Keep heaps balanced
     * <p>
     * <p>
     * When to use:
     * <p>
     * - Running median problems
     * - Real-time analytics
     * - Continuous data streams
     * <p>
     * <p>
     * Golden Rule:
     * <p>
     * maxHeap.peek() ≤ minHeap.peek()
     * <p>
     * AND
     * <p>
     * Size difference ≤ 1
     * <p>
     * That guarantees correctness.
     */
    static class MedianFinderOptimal {
        PriorityQueue<Integer> maxHeap;
        PriorityQueue<Integer> minHeap;

        public MedianFinderOptimal() {
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {

            // Step 1: Add number to correct heap
            if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
                maxHeap.add(num);
            } else {
                minHeap.add(num);
            }

            // Step 2: Balance the heaps
            if (maxHeap.size() > minHeap.size() + 1) {
                minHeap.add(maxHeap.poll());
            } else if (minHeap.size() > maxHeap.size() + 1) {
                maxHeap.add(minHeap.poll());
            }
        }

        public double findMedian() {

            // If odd count → heap with more elements
            if (maxHeap.size() > minHeap.size()) {
                return maxHeap.peek();
            }
            if (minHeap.size() > maxHeap.size()) {
                return minHeap.peek();
            }

            // If even count → average of both heap tops
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }

    }

}
