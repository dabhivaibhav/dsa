package two_pointer.medium_problem;

/*
Leetcode 1248: Count Number of Nice Subarrays

Given an array of integers nums and an integer k. A continuous subarray is called nice if there are k odd numbers on it.
Return the number of nice sub-arrays.

Example 1:
Input: nums = [1,1,2,1,1], k = 3
Output: 2
Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].

Example 2:
Input: nums = [2,4,6], k = 1
Output: 0
Explanation: There are no odd numbers in the array.

Example 3:
Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
Output: 16


Constraints:
            1 <= nums.length <= 50000
            1 <= nums[i] <= 10^5
            1 <= k <= nums.length
 */
public class CountNumberOfNiceSubarrays {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 1, 1};
        int k = 3;
        System.out.println(numberOfSubarraysBruteForce(nums, k));
    }

    /**
     * numberOfSubarraysBruteForce(int[] nums, int k)
     * <p>
     * What this method does:
     * Counts the number of continuous subarrays
     * that contain exactly k odd numbers.
     * <p>
     * Core Idea:
     * <p>
     * Generate every possible subarray.
     * For each starting index, expand to the right
     * and count how many odd numbers appear.
     * <p>
     * Whenever the count of odd numbers becomes exactly k,
     * increment the answer.
     * <p>
     * Thought Process:
     * <p>
     * A subarray is valid ("nice") if it contains exactly k odd numbers.
     * <p>
     * The brute force way to ensure correctness:
     * <p>
     * 1. Choose a starting index i.
     * 2. Expand the subarray to the right.
     * 3. Maintain a running count of odd numbers.
     * 4. If oddCount == k → increment total count.
     * <p>
     * Repeat this for all starting positions.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int totalOddCount = 0;
     * Stores total number of nice subarrays.
     * <p>
     * Outer Loop:
     * for (int i = 0; i < nums.length; i++)
     * <p>
     * Choose every possible starting index.
     * <p>
     * For each starting index:
     * <p>
     * int oddCount = 0;
     * Tracks number of odd numbers in current subarray.
     * <p>
     * Inner Loop:
     * for (int j = i; j < nums.length; j++)
     * <p>
     * Expand subarray from i to j.
     * <p>
     * Check if nums[j] is odd:
     * <p>
     * (nums[j] & 1) != 0
     * <p>
     * Explanation:
     * - If last bit is 1 → number is odd.
     * - Bitwise check is faster than using % 2.
     * <p>
     * If oddCount == k:
     * Increment totalOddCount.
     * <p>
     * Example:
     * <p>
     * nums = [1,1,2,1,1], k = 3
     * <p>
     * Valid subarrays:
     * [1,1,2,1]
     * [1,2,1,1]
     * <p>
     * Total = 2
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
     * This approach is straightforward and correct,
     * but inefficient for large inputs.
     * <p>
     * The key optimization insight:
     * This problem can be converted into:
     * <p>
     * Count of subarrays with at most k odds
     * minus
     * Count of subarrays with at most (k - 1) odds
     * <p>
     * That allows an O(n) sliding window solution.
     */
    private static int numberOfSubarraysBruteForce(int[] nums, int k) {
        int totalOddCount = 0;

        for (int i = 0; i < nums.length; i++) {
            int oddCount = 0;
            for (int j = i; j < nums.length; j++) {
                if ((nums[j] & 1) != 0) {
                    oddCount++;
                }
                if (oddCount == k) {
                    totalOddCount++;
                }
            }
        }
        return totalOddCount;
    }
}
