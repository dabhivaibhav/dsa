package collection.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


}
