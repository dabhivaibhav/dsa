package array.medium_problems;

import java.util.Arrays;

/*
Given an integer array nums, find the subarray with the largest sum and return the sum of the elements present in that subarray.
A subarray is a contiguous non-empty sequence of elements within an array. Here also return the subarray of largest sum.
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
public class KadaneAlgoSubarraySumWithArray {

    public static void main(String[] args) {

        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        kadaneAlgo(nums);
    }

    private static void kadaneAlgo(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;
        if (nums == null || nums.length == 0) {
            return;
        }
        int subarrrayStart = 0;
        int subarrayEnd = 0;
        int start = 0;
        for (int i = 0; i < nums.length; i++) {

            if (currentSum == 0) {
                start = i;
            }
            currentSum += nums[i];

            if (currentSum > maxSum) {
                maxSum = currentSum;
                subarrrayStart = start;
                subarrayEnd = i;
            }
            if (currentSum < 0) {
                currentSum = 0;
            }
        }
        System.out.println("Maximum subarray sum: " + maxSum);
        System.out.println("Subarray: " + Arrays.toString(Arrays.copyOfRange(nums, subarrrayStart, subarrayEnd)));
    }
}
