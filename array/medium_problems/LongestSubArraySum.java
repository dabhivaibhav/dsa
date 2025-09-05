package array.medium_problems;


import java.util.HashMap;
import java.util.Map;

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
        int[] nums2 = {2, 0, 0, 3};
        int k2 = 3;

        longestSubArraySumBruteforce(nums, k);
        longestSubArraySumBruteforce(nums1, k1);
        longestSubArraySumBetterApproach(nums, k);
        longestSubArraySumBetterApproach(nums1, k1);
        longestSubArraySumBetterApproach(nums2, k2);
    }


    /**
     * Finds the length of the longest subarray with a sum equal to target value using brute force approach.
     * Algorithm:
     * 1. Consider each element as a starting point using the outer loop
     * 2. For each start point, calculate a sum of subarrays using the inner loop
     * 3. Keep track of the maximum length subarray that sums to the target
     * <p>
     * Time Complexity: O(n²) where n is the length of input array
     * Space Complexity: O(1) as we only use constant extra space
     *
     * @param nums Input array of integers
     * @param k    Target sum to find in subarrays
     */
    private static void longestSubArraySumBruteforce(int[] nums, int k) {
        // Track length of the longest valid subarray found
        int longestSubArray = 0;

        // Outer loop - consider each element as a starting point
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
                // If sum equals target, update the longest length if current is longer
                if (currentSum == k) {
                    longestSubArray = Math.max(longestSubArray, j - i + 1);
                }
            }
        }
        System.out.println("Longest subarray sum: " + longestSubArray);
    }


    /**
     * Algorithm:
     * 1. Maintain running sum and store prefix sums in HashMap
     * 2. For each position, check if (sum - k) exists in a map to find a valid subarray
     * 3. Update the longest length when a valid subarray found
     * 4. Store the current prefix sum in a map if not already present
     * Time Complexity: O(n) where n is the length of input array - single pass through array
     * Space Complexity: O(n) to store prefix sums in HashMap
     *
     * @param nums Input array of integers
     * @param k    Target sum to find in subarrays
     */
    public static void longestSubArraySumBetterApproach(int[] nums, int k) {
        int n = nums.length;
        long sum = 0;  // Running sum of elements
        long leng = 0; // Length of the longest valid subarray
        Map<Long, Integer> map = new HashMap<>(); // Stores prefix sums and their indices

        // Process each element to find the longest subarray
        for (int i = 0; i < n; i++) {
            // Update the running sum with the current element
            sum += nums[i];

            // If the current sum equals k, entire subarray from start is valid
            if (sum == k) {
                leng = i + 1;
            }

            // Check if there exists a prefix sum that can be removed to get target sum
            if (map.containsKey(sum - k)) {
                leng = Math.max(leng, i - map.get(sum - k));
            }

            // Store the current prefix sum if not already in map
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        System.out.println("Longest subarray sum: " + leng);
    }
}
