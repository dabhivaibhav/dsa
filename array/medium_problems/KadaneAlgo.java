package array.medium_problems;

/*
Given an integer array nums, find the subarray with the largest sum and return the sum of the elements present in that subarray.
A subarray is a contiguous non-empty sequence of elements within an array.
Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4

Examples:
        Input: nums = [2, 3, 5, -2, 7, -4]
        Output: 15
        Explanation: The subarray from index 0 to index 4 has the largest sum = 15

        Input: nums = [-2, -3, -7, -2, -10, -4]
        Output: -2
        Explanation: The element on index 0 or index 3 make up the largest sum when taken as a subarray
 */
public class KadaneAlgo {
    public static void main(String[] args) {

        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] nums1 = {2, 3, 5, -2, 7, -4};
        int[] num2  = {-2, -3, -7, -2, -10, -4};

        System.out.println(kadaneBruteForceApproach(nums));
        System.out.println(kadaneOptimalApproach(nums1));
        System.out.println(kadaneOptimalApproach(nums));
        System.out.println(kadaneOptimalApproach(num2));
    }

    /**
     * Brute Force Approach to find maximum subarray sum:
     * This method checks all possible subarrays by using two nested loops.
     * For each starting position i, it calculates sum of all subarrays starting at i.
     * Time Complexity: O(n²) where n is the length of input array
     * Space Complexity: O(1) as it uses only constant extra space
     * @param nums input array of integers
     * @return maximum sum found in any subarray
     */
    private static int kadaneBruteForceApproach(int[] nums) {

        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;

        if (nums.length == 0 || nums == null) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        for (int i = 0; i < nums.length; i++) {

            currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                if (currentSum > maxSum) {
                    maxSum = currentSum;
                }
            }
        }
        return maxSum;
    }


    /**
     * Kadane's Algorithm - Optimal approach to find maximum subarray sum:
     * This algorithm maintains two variables:
     * - maxSum: tracks the maximum sum found so far
     * - currentSum: tracks the current running sum
     * Key points of the algorithm:
     * 1. If currentSum becomes negative, reset it to 0 (start fresh from next element)
     * 2. For each element, add it to currentSum and update maxSum if needed
     * 3. This effectively handles both positive and negative numbers
     * Time Complexity: O(n) where n is the length of input array
     * Space Complexity: O(1) as it uses only constant extra space
     * @param nums input array of integers
     * @return maximum sum found in any subarray
     */
    private static int kadaneOptimalApproach(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;
        if (nums == null || nums.length == 0) {
            return -1;
        }

        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
            if (currentSum < 0) {
                currentSum = 0;
            }
        }
        return maxSum;
    }
}
