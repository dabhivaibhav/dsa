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
}
