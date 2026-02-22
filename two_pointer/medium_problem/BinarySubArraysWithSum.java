package two_pointer.medium_problem;

/*
Leetcode 930: Binary Subarrays With Sum
Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
A subarray is a contiguous part of the array.

Example 1:
Input: nums = [1,0,1,0,1], goal = 2
Output: 4
Explanation: The 4 subarrays are bolded and underlined below:
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]

Example 2:
Input: nums = [0,0,0,0,0], goal = 0
Output: 15

Constraints:
            1 <= nums.length <= 3 * 10^4
            nums[i] is either 0 or 1.
            0 <= goal <= nums.length
 */
public class BinarySubArraysWithSum {

    public static void main(String[] args) {

        int[] nums = {1, 0, 1, 0, 1};
        int goal = 2;
        System.out.println("Brute force approach: " + subarraysWithSumBruteForce(nums, goal));
        System.out.println("Sliding window approach: " + (subarraysWithSumSlidingWindow(nums, goal) - subarraysWithSumSlidingWindow(nums, goal - 1)));
        int[] nums1 = {0, 0, 0, 0, 0};
        int goal1 = 0;
        System.out.println("Brute force approach: " + subarraysWithSumBruteForce(nums1, goal1));
        System.out.println("Sliding window approach: " + (subarraysWithSumSlidingWindow(nums1, goal1) - subarraysWithSumSlidingWindow(nums1, goal1 - 1)));
    }

    /**
     * subarraysWithSumBruteForce(int[] nums, int goal)
     * <p>
     * What this method does:
     * Counts the total number of non-empty contiguous subarrays
     * whose sum equals the given goal.
     * <p>
     * Core Idea:
     * <p>
     * Generate every possible subarray.
     * For each starting index, expand to the right
     * while maintaining a running sum.
     * <p>
     * If at any point the running sum equals goal,
     * increment the counter.
     * <p>
     * Thought Process:
     * <p>
     * Since a subarray must be contiguous,
     * the simplest way to solve this is:
     * <p>
     * 1. Pick a starting index i.
     * 2. Keep adding elements from i onward.
     * 3. Check if the cumulative sum equals goal.
     * 4. Count it if it does.
     * <p>
     * Repeat this for every starting index.
     * <p>
     * This guarantees correctness
     * because every possible subarray is examined.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int totalSubArray = 0;
     * Stores the total number of valid subarrays.
     * <p>
     * Outer Loop:
     * for (int i = 0; i < nums.length; i++)
     * <p>
     * Choose every possible starting index.
     * <p>
     * For each starting index:
     * <p>
     * int sum = 0;
     * Maintains running sum from i to j.
     * <p>
     * Inner Loop:
     * for (int j = i; j < nums.length; j++)
     * <p>
     * Expand the subarray to the right.
     * <p>
     * sum += nums[j];
     * <p>
     * If sum == goal:
     * totalSubArray++;
     * <p>
     * Example:
     * <p>
     * nums = [1,0,1,0,1], goal = 2
     * <p>
     * Valid subarrays:
     * [1,0,1]
     * [1,0,1,0]
     * [0,1,0,1]
     * [1,0,1]
     * <p>
     * Total = 4
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
     * This brute force approach is simple and intuitive.
     * However, it becomes inefficient for large arrays.
     * <p>
     * The key insight for optimization:
     * Instead of recalculating sums repeatedly,
     * we can use prefix sums or sliding window techniques
     * (since this is a binary array).
     * <p>
     * Optimized solutions can achieve O(n).
     */
    private static int subarraysWithSumBruteForce(int[] nums, int goal) {
        int totalSubArray = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == goal) {
                    totalSubArray++;
                }
            }
        }
        return totalSubArray;
    }

    /**
     * subarraysWithSumSlidingWindow(int[] nums, int goal)
     * <p>
     * What this method does:
     * Counts the number of subarrays with sum <= goal
     * using the Sliding Window technique.
     * <p>
     * Important:
     * This method does NOT directly return subarrays with sum == goal.
     * It returns the count of subarrays with sum at most goal.
     * <p>
     * To get exactly goal, we use:
     * <p>
     * count(goal) - count(goal - 1)
     * <p>
     * Core Idea:
     * <p>
     * Since this is a binary array (only 0s and 1s),
     * the sum increases monotonically when expanding the window.
     * <p>
     * That allows us to use a variable-size sliding window.
     * <p>
     * How It Works:
     * <p>
     * - Expand the window by moving right forward.
     * - Add nums[right] to the running sum.
     * <p>
     * - If sum exceeds goal:
     * Shrink window from the left
     * until sum <= goal again.
     * <p>
     * - At each position of right:
     * All subarrays ending at right
     * and starting from left to right
     * are valid.
     * <p>
     * Count added = (right - left + 1)
     * <p>
     * Why (right - left + 1)?
     * <p>
     * Because if the window is valid,
     * then every starting index between left and right
     * forms a valid subarray ending at right.
     * <p>
     * Example:
     * <p>
     * nums = [1,0,1,0,1], goal = 2
     * <p>
     * When window is valid,
     * we accumulate counts dynamically.
     * <p>
     * Complexity:
     * <p>
     * Time Complexity: O(n)
     * Each element is added and removed at most once.
     * <p>
     * Space Complexity: O(1)
     * <p>
     * Key Insight:
     * <p>
     * This technique works because:
     * - The array contains only non-negative numbers.
     * - Therefore, once sum exceeds goal,
     * moving left reduces the sum.
     * <p>
     * This is a classic "at most K" sliding window pattern.
     */
    private static int subarraysWithSumSlidingWindow(int[] nums, int goal) {
        if (goal < 0) return 0;
        int totalSubArray = 0;
        int left = 0;
        int right = 0;
        int sum = 0;
        while (right < nums.length) {
            sum += nums[right];
            while (sum > goal) {
                sum -= nums[left];
                left++;
            }
            totalSubArray = totalSubArray + (right - left + 1);
            right++;
        }
        return totalSubArray;
    }
}
