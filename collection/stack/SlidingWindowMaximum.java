package collection.stack;

import java.util.*;

/*
Leetcode: 239. Sliding Window Maximum

You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the
array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window

Example 1:
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation:
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7

Example 2:
Input: nums = [1], k = 1
Output: [1]

Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
            1 <= k <= nums.length
 */
public class SlidingWindowMaximum {
    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println(Arrays.toString(maxSlidingWindowBruteForce(nums, k)));
        nums = new int[]{1};
        k = 1;
        System.out.println(Arrays.toString(maxSlidingWindowBruteForce(nums, k)));
    }

    /**
     * Method: maxSlidingWindowBruteForce(int[] nums, int k)
     * <p>
     * What it does:
     * This method finds the maximum element in every contiguous
     * subarray (window) of size k as the window slides
     * from left to right across the array.
     * <p>
     * Core Idea:
     * For every possible starting position of the window,
     * we manually scan the next k elements and compute the maximum.
     * <p>
     * How it works step by step:
     * <p>
     * 1. We iterate from index 0 to nums.length - k.
     * Why this range?
     * Because a window of size k must fully fit inside the array.
     * <p>
     * 2. For each starting index i:
     * - We initialize a variable "max" to the smallest possible value.
     * - We then scan from i to i + k - 1.
     * - During this inner loop, we update max using Math.max().
     * <p>
     * 3. After scanning k elements,
     * we store the computed max into the result list.
     * <p>
     * 4. After processing all windows,
     * we convert the List<Integer> into an int[] array
     * and return it.
     * <p>
     * Example:
     * nums = [1,3,-1,-3,5,3,6,7], k = 3
     * <p>
     * Window 1: [1,3,-1] → max = 3
     * Window 2: [3,-1,-3] → max = 3
     * Window 3: [-1,-3,5] → max = 5
     * Window 4: [-3,5,3] → max = 5
     * Window 5: [5,3,6] → max = 6
     * Window 6: [3,6,7] → max = 7
     * <p>
     * Final output: [3,3,5,5,6,7]
     * <p>
     * Why this approach works:
     * It directly follows the problem definition.
     * We explicitly compute the maximum for each window.
     * <p>
     * Time Complexity:
     * O(n * k)
     * Outer loop runs about n times.
     * Inner loop runs k times for each window.
     * <p>
     * Space Complexity:
     * O(n - k + 1)
     * To store the result array.
     * <p>
     * Interview Insight:
     * This is the most straightforward solution.
     * It is easy to understand but inefficient when k is large.
     * For large constraints, we optimize using a Deque
     * with a Monotonic Decreasing structure to achieve O(n).
     */
    private static int[] maxSlidingWindowBruteForce(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length - k + 1; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            result.add(max);
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Method: maxSlidingWindow(int[] nums, int k)
     * <p>
     * What it does:
     * This method finds the maximum element in every sliding window
     * of size k using an optimized O(n) approach.
     * It uses a Deque to maintain a Monotonic Decreasing structure
     * of indices so that the maximum element of the current window
     * is always available at the front.
     * <p>
     * Core Idea:
     * Instead of recalculating the maximum for every window,
     * we maintain a structure that always keeps the potential
     * maximum elements in decreasing order.
     * <p>
     * Why we store indices instead of values:
     * - To check whether an element is outside the current window.
     * - To access its value from nums when needed.
     * - To handle duplicate values correctly.
     * <p>
     * Step-by-step explanation:
     * <p>
     * 1. Initialize:
     * - result array of size n - k + 1.
     * - ri pointer to track result index.
     * - Deque to store indices of elements.
     * <p>
     * 2. Iterate through the array from left to right.
     * <p>
     * 3. Remove out-of-window elements:
     * If the index at the front equals i - k,
     * it means it has moved outside the current window.
     * So we remove it from the front.
     * <p>
     * 4. Maintain decreasing order:
     * While the current value nums[i] is greater than
     * the value at the back of the deque,
     * we remove those smaller indices.
     * <p>
     * Why?
     * Because a smaller element before a larger one
     * can never become the maximum of any future window.
     * <p>
     * 5. Add current index to the back of the deque.
     * <p>
     * 6. Once we have processed at least k elements
     * (i >= k - 1),
     * the front of the deque always stores
     * the index of the maximum element
     * for the current window.
     * <p>
     * 7. Store that value into result.
     * <p>
     * Example:
     * nums = [1,3,-1,-3,5,3,6,7], k = 3
     * <p>
     * Window processing:
     * After processing first 3 elements,
     * deque front gives 3.
     * <p>
     * As window slides,
     * smaller elements are removed,
     * and deque always keeps strong candidates.
     * <p>
     * Final result: [3,3,5,5,6,7]
     * <p>
     * Why this works:
     * Each element is:
     * - Added once
     * - Removed at most once
     * <p>
     * That guarantees linear time.
     * <p>
     * Time Complexity:
     * O(n)
     * Each index is inserted and removed at most once.
     * <p>
     * Space Complexity:
     * O(k)
     * Deque stores at most k indices at any time.
     * <p>
     * Interview Insight:
     * This is a classic Monotonic Deque pattern.
     * Use it when:
     * - You need sliding window maximum or minimum.
     * - You need fast window-based extreme values.
     * - The brute force solution is O(n*k) and too slow.
     * <p>
     * Mental Model:
     * The deque always stores potential "future winners"
     * in decreasing order.
     * The front is always the current window’s champion.
     */

    private static int[] maxSlidingWindowOptimized(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int ri = 0; // result index

        // Store indices in the deque, not the values
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // Remove indices that are out of the window range
            if (!deque.isEmpty() && deque.peekFirst() == i - k) {
                deque.removeFirst();
            }
            // Remove all indices whose values are smaller than current value (The "Younger & Stronger" rule)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.removeLast();
            }
            deque.addLast(i);
            // Once we have reached at least k elements, the front is our max
            if (i >= k - 1) {
                result[ri++] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
