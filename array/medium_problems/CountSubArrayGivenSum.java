package array.medium_problems;

/*
Count subarrays with given sum

Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.

Examples:
Input: nums = [1, 1, 1], k = 2
Output: 2
Explanation: In the given array [1, 1, 1], there are two subarrays that sum up to 2: [1, 1] and [1, 1]. Hence, the output is 2.

Input: nums = [1, 2, 3], k = 3
Output: 2
Explanation: In the given array [1, 2, 3], there are two subarrays that sum up to 3: [1, 2] and [3]. Hence, the output is 2.

Constraints:
           1 <= nums.length <= 10^5
           -1000 <= nums[i] <= 1000
           -10^7 <= k <= 10^7
 */
public class CountSubArrayGivenSum {

    public static void main(String[] args) {
        int[] nums = {1, 1, 1};
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {1, 2, -3, 1, 2, -3};
        int k = 2;
        int k1 = 3;
        int k2 = 0;

        countSubArrayBruteForce(nums, k);
        countSubArrayBruteForce(nums1, k1);
        countSubArrayBruteForce(nums2, k2);

    }


    /**
     * Counts the number of subarrays in the given array that sum up to the target sum.
     * Uses a nested loop approach to consider all possible subarrays:
     * - Outer loop selects the starting point
     * - Inner loop expands the subarray by adding elements
     * - Maintains running sum and checks against target
     * Time Complexity: O(n²) where n is the length of input array
     * Space Complexity: O(1) as we only use constant extra space
     *
     * @param nums      Input array of integers
     * @param targetSum Target sum to find in subarrays
     */
    private static void countSubArrayBruteForce(int[] nums, int targetSum) {
        // Counter to keep track of valid subarrays found
        int count = 0;

        // Outer loop - consider each element as starting point
        for (int i = 0; i < nums.length; i++) {
            // Check if single element equals target sum
            if (nums[i] == targetSum) {
                count++;
            }

            // Initialize sum for current subarray starting at index i
            int sum = nums[i];

            // Inner loop - expand subarray by adding elements
            for (int j = i + 1; j < nums.length; j++) {
                // Add current element to running sum
                sum += nums[j];

                // If running sum equals target, we found a valid subarray
                if (sum == targetSum) {
                    count++;
                }
            }
        }
        System.out.println(" Using Bruteforce approach: Number of subarrays with given sum: " + count);
    }

}


