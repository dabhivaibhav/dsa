package two_pointer.medium_problem;

/*
Leetcode 1004. Max Consecutive Ones III

Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.

Example 1:
Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
Output: 6
Explanation: [1,1,1,0,0,1,1,1,1,1,1]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.

Example 2:
Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
Output: 10
Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.

Constraints:
            1 <= nums.length <= 10^5
            nums[i] is either 0 or 1.
            0 <= k <= nums.length
 */
public class MaxConsecutiveOnesIII {

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k = 2;
        System.out.println("Brute force approach: " + longestOnesBruteForce(nums, k));
        System.out.println("Sliding window approach: " + longestOnesSlidingWindow(nums, k));
    }

    /**
     * longestOnesBruteForce(int[] nums, int k)
     * <p>
     * What this method does:
     * Finds the maximum length of a contiguous subarray containing only 1s,
     * where we are allowed to flip at most k zeros into ones.
     * <p>
     * Core Idea:
     * Generate every possible subarray.
     * For each subarray, count how many zeros it contains.
     * If the zero count exceeds k, stop expanding that subarray.
     * Track the maximum valid length found.
     * <p>
     * Thought Process:
     * Since the problem asks for the longest consecutive 1s after flipping
     * at most k zeros, the most straightforward idea is:
     * <p>
     * 1. Try every possible starting index.
     * 2. Expand the subarray to the right.
     * 3. Keep counting zeros.
     * 4. If zeros exceed k, break.
     * 5. Update the maximum length whenever valid.
     * <p>
     * This guarantees correctness because every possible subarray is examined.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int length = 0;
     * This variable stores the maximum valid subarray length.
     * <p>
     * Outer Loop:
     * for (int i = 0; i < nums.length; i++)
     * <p>
     * Choose every possible starting index of the subarray.
     * <p>
     * Inner Loop:
     * for (int j = i; j < nums.length; j++)
     * <p>
     * Expand the subarray from index i to j.
     * <p>
     * int zeroCount = 0;
     * Tracks how many zeros are in the current subarray.
     * <p>
     * If nums[j] == 0:
     * Increment zeroCount.
     * <p>
     * If zeroCount > k:
     * We cannot flip more than k zeros.
     * Break the inner loop.
     * <p>
     * Otherwise:
     * Update maximum length using:
     * length = Math.max(length, j - i + 1);
     * <p>
     * Example:
     * nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
     * <p>
     * Starting at index 0:
     * We can flip at most 2 zeros.
     * The longest valid stretch becomes length 6.
     * <p>
     * Complexity:
     * <p>
     * Outer loop runs n times.
     * Inner loop runs up to n times.
     * <p>
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     * <p>
     * Interview Takeaway:
     * <p>
     * This is the naive brute force approach.
     * It is simple and easy to explain.
     * <p>
     * However, it is inefficient for large inputs.
     * <p>
     * The key optimization idea:
     * Instead of restarting for every index,
     * use a sliding window that dynamically adjusts
     * when zero count exceeds k.
     * <p>
     * That reduces time complexity to O(n).
     */

    private static int longestOnesBruteForce(int[] nums, int k) {
        int length = 0;
        for (int i = 0; i < nums.length; i++) {
            int zeroCount = 0;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] == 0) zeroCount++;
                if (zeroCount > k) break;
                length = Math.max(length, j - i + 1);
            }
        }
        return length;
    }

    /**
     * Method: longestOnesSlidingWindow(int[] nums, int k)
     * <p>
     * What this method does:
     * Finds the maximum number of consecutive 1s in a binary array
     * if we are allowed to flip at most k zeros.
     * <p>
     * Core Idea:
     * Use the Sliding Window (Two Pointer) technique.
     * <p>
     * Instead of checking every subarray, we maintain a dynamic window
     * that always satisfies the condition:
     * "At most k zeros inside the window".
     * <p>
     * We expand the window from the right.
     * If zero count exceeds k, we shrink from the left
     * until the window becomes valid again.
     * <p>
     * Thought Process:
     * <p>
     * In the brute force approach, we restarted counting
     * for every index, causing O(n^2) time.
     * <p>
     * The key observation is:
     * If a window already satisfies zeroCount <= k,
     * we should try expanding it.
     * <p>
     * If zeroCount > k,
     * we only need to shrink from the left
     * until it becomes valid again.
     * <p>
     * This avoids rechecking the same elements repeatedly.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int length = 0;
     * Stores maximum valid window length.
     * <p>
     * int left = 0;
     * Left boundary of the window.
     * <p>
     * int zeroCount = 0;
     * Tracks number of zeros inside current window.
     * <p>
     * Main Loop:
     * for (right = 0; right < nums.length; right++)
     * <p>
     * Expand the window by moving right forward.
     * <p>
     * If nums[right] == 0:
     * Increment zeroCount.
     * <p>
     * If zeroCount > k:
     * The window is invalid.
     * <p>
     * While zeroCount > k:
     * - If nums[left] is zero, reduce zeroCount.
     * - Move left pointer forward.
     * <p>
     * This shrinks the window until it becomes valid again.
     * <p>
     * Once window is valid:
     * Update maximum length using:
     * length = Math.max(length, right - left + 1);
     * <p>
     * Example:
     * <p>
     * nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
     * <p>
     * Window expands.
     * When more than 2 zeros appear,
     * left pointer moves forward
     * until zeroCount becomes <= k.
     * <p>
     * Longest valid window becomes length 6.
     * <p>
     * Why This Works:
     * <p>
     * Each element:
     * - Is visited once by right pointer.
     * - Is removed once by left pointer.
     * <p>
     * No repeated work.
     * <p>
     * Complexity:
     * <p>
     * Time Complexity: O(n)
     * Each element enters and leaves window at most once.
     * <p>
     * Space Complexity: O(1)
     * Only counters and pointers used.
     * <p>
     * Interview Takeaway:
     * <p>
     * This is a classic variable-size sliding window problem.
     * <p>
     * Pattern to remember:
     * <p>
     * 1. Expand right pointer.
     * 2. Track condition (here zeroCount).
     * 3. Shrink left pointer if condition violated.
     * 4. Update answer.
     * <p>
     * Whenever problem says:
     * "Longest subarray with at most k something"
     * Think Sliding Window immediately.
     */
    private static int longestOnesSlidingWindow(int[] nums, int k) {
        int length = 0;
        int left = 0;
        int right = 0;
        int zeroCount = 0;
        for (right = 0; right < nums.length; right++) {

            if (nums[right] == 0) {
                zeroCount++;
            }
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            length = Math.max(length, right - left + 1);

        }
        return length;
    }
}
