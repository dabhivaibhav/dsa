package two_pointer.medium_problem;

/*
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
        int[] nums1 = {0, 0, 0, 0, 0};
        int goal1 = 0;
        System.out.println("Brute force approach: " + subarraysWithSumBruteForce(nums1, goal1));
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
}
