package array.medium_problems;


/*

Longest subarray with sum K

Given an array nums of size n and an integer k, find the length of the longest sub-array that sums to k. If no such sub-array exists, return 0.
Examples:
Input: nums = [10, 5, 2, 7, 1, 9],  k=15
Output: 4
Explanation:The longest sub-array with a sum equal to 15 is [5, 2, 7, 1], which has a length of 4. This sub-array starts
at index 1 and ends at index 4, and the sum of its elements (5 + 2 + 7 + 1) equals 15. Therefore, the length of this sub-array is 4.

Input: nums = [-3, 2, 1], k=6
Output: 0
Explanation: There is no sub-array in the array that sums to 6. Therefore, the output is 0.

Constraints:
             1<=n<=10^5
             -10^5<=nums[i]<=10^5
             -10^9<= k<=10^9
 */
public class LongestSubArraySum {

    public static void main(String[] args) {
        int[] nums = {10, 5, 2, 7, 1, 9};
        int k = 15;
        int[] nums1 = {-3, 2, 1};
        int k1 = 6;

        longestSubArraySumBruteforce(nums, k);
        longestSubArraySumBruteforce(nums1, k1);
    }


    /**
     * Finds the length of longest subarray with sum equal to target value using brute force approach.
     * Algorithm:
     * 1. Consider each element as starting point using outer loop
     * 2. For each start point, calculate sum of subarrays using inner loop
     * 3. Keep track of maximum length subarray that sums to target
     * <p>
     * Time Complexity: O(n²) where n is the length of input array
     * Space Complexity: O(1) as we only use constant extra space
     *
     * @param nums Input array of integers
     * @param k    Target sum to find in subarrays
     */
    private static void longestSubArraySumBruteforce(int[] nums, int k) {
        // Track length of longest valid subarray found
        int longestSubArray = 0;

        // Outer loop - consider each element as starting point
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            currentSum += nums[i];
            // Inner loop - expand subarray by adding elements
            for (int j = i + 1; j < nums.length; j++) {
                currentSum += nums[j];
                // Reset sum if it exceeds target
                if (currentSum > k) {
                    currentSum = 0;
                }
                // If sum equals target, update longest length if current is longer
                if (currentSum == k) {
                    longestSubArray = Math.max(longestSubArray, j - i + 1);
                }
            }
        }
        System.out.println("Longest subarray sum: " + longestSubArray);
    }
}
