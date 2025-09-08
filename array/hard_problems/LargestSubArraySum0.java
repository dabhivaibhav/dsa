package array.hard_problems;

/**
 * Largest Subarray with Sum 0
 * You are given an integer array arr of size n which contains both positive and negative integers. Your task is to find
 * the length of the longest contiguous subarray with sum equal to 0.
 * Return the length of such a subarray. If no such subarray exists, return 0.
 * <p>
 * Examples:
 * Input: arr = [15, -2, 2, -8, 1, 7, 10, 23]
 * Output: 5
 * Explanation: The subarray [-2, 2, -8, 1, 7] sums up to 0 and has the maximum length among all such subarrays.
 * <p>
 * Input: arr = [2, 10, 4]
 * Output: 0
 * Explanation: There is no subarray whose elements sum to 0.
 * <p>
 * Constraints:
 * 1 <= arr.length <= 10^6
 * -10^3 <= arr[i] <= 10^3 for each valid index i
 */
public class LargestSubArraySum0 {
    public static void main(String[] args) {
        int[] arr = {15, -2, 2, -8, 1, 7, 10, 23};
        int[] arr1 = {2, 10, 4};


        LargestSubArraySumBruteForceApproach(arr);
    }

    private static void LargestSubArraySumBruteForceApproach(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            boolean isSum = false;
            sum += arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                sum += arr[j];
                if (sum > 0) {
                    break;
                }
                if (sum == 0) {
                    count = Math.max(count, j - i + 1);
                }
            }

        }
        System.out.println("Largest subarray for sum 0 is: " + count);
    }


}
