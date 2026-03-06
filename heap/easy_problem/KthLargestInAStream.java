package heap.easy_problem;

import java.util.PriorityQueue;

/*
Leetcode 703: Kth Largest Element in a Stream

You are part of a university admissions office and need to keep track of the kth highest test score from applicants in real-time.
This helps to determine cut-off marks for interviews and admissions dynamically as new applicants submit their scores.

You are tasked to implement a class which, for a given integer k, maintains a stream of test scores and continuously
returns the kth highest test score after a new score has been submitted. More specifically, we are looking for the kth
highest score in the sorted list of all scores.

Implement the KthLargest class:
KthLargest(int k, int[] nums) Initializes the object with the integer k and the stream of test scores nums.
int add(int val) Adds a new test score val to the stream and returns the element representing the kth largest element in
the pool of test scores so far.

Example 1:
Input:
["KthLargest", "add", "add", "add", "add", "add"]
[[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
Output: [null, 4, 5, 5, 8, 8]
Explanation:
KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);
kthLargest.add(3); // return 4
kthLargest.add(5); // return 5
kthLargest.add(10); // return 5
kthLargest.add(9); // return 8
kthLargest.add(4); // return 8

Example 2:
Input:
["KthLargest", "add", "add", "add", "add"]
[[4, [7, 7, 7, 7, 8, 3]], [2], [10], [9], [9]]
Output: [null, 7, 7, 7, 8]
Explanation:
KthLargest kthLargest = new KthLargest(4, [7, 7, 7, 7, 8, 3]);
kthLargest.add(2); // return 7
kthLargest.add(10); // return 7
kthLargest.add(9); // return 7
kthLargest.add(9); // return 8


Constraints:
            0 <= nums.length <= 10^4
            1 <= k <= nums.length + 1
            -10^4 <= nums[i] <= 10^4
            -10^4 <= val <= 10^4
            At most 10^4 calls will be made to add.
 */
public class KthLargestInAStream {

    public static void main(String[] args) {
        int[] nums = {4, 5, 8, 2};
        int k = 3;
        KthLargest kthClass = new KthLargest(k, nums);
        System.out.println(kthClass.add(3));
        System.out.println(kthClass.add(5));
        System.out.println(kthClass.add(10));
        System.out.println(kthClass.add(9));
        System.out.println(kthClass.add(4));


    }

    /*
    THE "K-SURVIVORS" STREAM PATTERN

    THE PROBLEM:
    Continuous data where we only care about the top tier.

    THE SOLUTION:
    A Min-Heap that 'caps' its size at K.

    Even though we want the LARGEST elements, we use a
    MIN-heap because the smallest of the 'winners' is
    the one most likely to be replaced.
    The Root is our 'Current Threshold.'

    PERFORMANCE:
    Adding 10,000 scores takes O(N log K).
    If K is 100, log K is ~7.
    70,000 operations is nothing for a computer!
    */

    /**
     * Implementation of a Kth Largest Element tracker for a data stream.
     * <p>
     * <b>Mental Model: The "VIP Room" Strategy</b><br>
     * Imagine a VIP room that only has capacity for 'k' people. To find the kth largest
     * score at any time, we only keep the 'k' highest scores in this room. The
     * "poorest" person in this elite VIP room is technically the kth wealthiest person overall.
     * </p>
     * * <b>Logic:</b>
     * <ul>
     * <li>We use a <b>Min-Heap</b> to store the k largest elements.</li>
     * <li>The "Min" property ensures the smallest of the top-K (the kth largest)
     * is always at the root (peek).</li>
     * <li>When a new score arrives:
     * <ol>
     * <li>If the room isn't full, let them in.</li>
     * <li>If the room is full and the new score is better than the "poorest" (root),
     * evict the root and add the newcomer.</li>
     * </ol>
     * </li>
     * </ul>
     * *
     * * <b>Complexity Analysis:</b>
     * <ul>
     * <li><b>Time:</b> O(log k) per addition. Adding to a heap of size k takes logarithmic time.</li>
     * <li><b>Space:</b> O(k). We only store the k most relevant elements, regardless of stream size.</li>
     * </ul>
     */
    static class KthLargest {

        private final int k;
        private final PriorityQueue<Integer> pq;

        // Initializes the tracker with a target rank k and an initial set of scores.
        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.pq = new PriorityQueue<>(k);
            for (int num : nums) {
                add(num);
            }
        }

        // Adds a new score to the stream and returns the current kth largest element.

        public int add(int val) {
            // Optimization: Only add if we have space or if val is better than the current kth
            if (pq.size() < k) {
                pq.add(val);
            } else if (val > pq.peek()) {
                pq.poll();
                pq.add(val);
            }

            // The root of our Min-Heap is always the "smallest of the best,"
            // which is the kth largest overall.
            return pq.peek();
        }
    }
}
