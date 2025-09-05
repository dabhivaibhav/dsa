package two_pointer;

/*
Longest subarray with sum K

This class implements a solution to find the longest subarray with a given sum K using the two-pointer sliding window technique.
The sliding window approach maintains two pointers (start and end) that define the current window being considered.
The window expands by moving the end pointer forward and contracts by moving the start pointer when needed.

Key aspects of the solution:
1. Use two pointers (i and j) to define the window
2. Maintain a running sum of elements in the window
3. Expand a window by adding elements until a sum exceeds K
4. Contract window starts when sum > K
5. Update max length when a sum equals K

Given an array nums of size n and an integer k, find the length of the longest subarray that sums to k. 
If no such subarray exists, return 0.

Examples:
Input: nums = [10, 5, 2, 7, 1, 9],  k=15
Output: 4
Explanation:The longest subarray with a sum equal to 15 is [5, 2, 7, 1], which has a length of 4. This subarray starts
at index 1 and ends at index 4, and the sum of its elements (5 + 2 + 7 + 1) equals 15. Therefore, the length of this sub-array is 4.

Input: nums = [-3, 2, 1], k=6
Output: 0
Explanation: There is no subarray in the array that sums to 6. Therefore, the output is 0.

Constraints:
             1<=n<=10^5
             -10^5<=nums[i]<=10^5
             -10^9<= k<=10^9
 */
public class LongestSubArray {

    public static void main(String[] args) {
        int[] nums = {10, 5, 2, 7, 1, 9};
        int k = 15;
        int[] nums1 = {-3, 2, 1};
        int k1 = 6;
        int[] nums2 = {2, 0, 0, 3};
        int k2 = 3;

        longestSubArraySumOptimalApproach(nums, k);
        longestSubArraySumOptimalApproach(nums1, k1);
        longestSubArraySumOptimalApproach(nums2, k2);
    }


    /**
     * Finds the length of the longest subarray with sum equal to k using two-pointer sliding window approach.
     * Algorithm:
     * 1. Initialize two pointers i (start) and j (end) at the beginning
     * 2. Expand window by moving j and adding elements to sum
     * 3. If sum exceeds k, contract window from start by moving i
     * 4. When sum equals k, update maximum length if current window is longer
     * Time Complexity: O(n) - each element is processed at most twice
     * Space Complexity: O(1) - uses constant extra space
     *
     * @param nums Input array of integers
     * @param k    Target sum to find
     */
    private static void longestSubArraySumOptimalApproach(int[] nums, int k) {
        int len = 0;  // Stores the length of longest valid subarray
        int sum = 0;  // Maintains running sum of current window
        for (int i = 0, j = 0; j < nums.length; j++) {
            // Expand window by including element at j
            sum += nums[j];

            // Contract window from start if sum exceeds k
            while (i <= j && sum > k) {
                sum -= nums[i];
                i++;
            }
            // Update maximum length if current window sum equals k
            if (sum == k) {
                len = Math.max(len, j - i + 1);
            }
        }
        System.out.println("The length of the longest subarray with sum K is: " + len);
    }
}
