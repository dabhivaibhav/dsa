package collection.stack;

/*
Leetcode: 907. Sum of Subarray Minimums

Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous) subarray of arr.
Since the answer may be large, return the answer modulo 109 + 7.

Example 1:
Input: arr = [3,1,2,4]
Output: 17
Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1. Sum is 17.

Example 2:
Input: arr = [11,81,94,43,3]
Output: 444

Constraints:
            1 <= arr.length <= 3 * 10^4
            1 <= arr[i] <= 3 * 10^4
 */
public class SumOfSubArrays {

    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4};
        System.out.println(sumOfSubArraysBruteForce(arr));
    }

    /**
     * Method name:
     * sumOfSubArraysBruteForce
     * <p>
     * What it does:
     * Computes the sum of the minimum element of every possible contiguous subarray
     * using a straightforward brute force approach.
     * The final result is returned modulo 10^9 + 7.
     * <p>
     * Core idea:
     * Every subarray has exactly one minimum element.
     * If we generate all subarrays and keep track of their minimum values,
     * we can sum those minimums to get the answer.
     * <p>
     * Thought process:
     * The most naive solution is to explicitly generate all subarrays.
     * For each starting index, extend the subarray to the right and continuously
     * update the minimum value.
     * This avoids recomputing the minimum from scratch for each subarray
     * and keeps the logic simple and intuitive.
     * <p>
     * Why this approach works:
     * - Every contiguous subarray starts at some index i
     * - For a fixed i, extending the subarray from i to j only adds one new element
     * - The minimum of the subarray [i...j] is either the previous minimum
     * or the newly added element
     * By updating the minimum incrementally, all subarrays are covered correctly.
     * <p>
     * Explanation of the approach step by step:
     * - A variable sum is used to store the final answer.
     * - A modulo value (10^9 + 7) is used to prevent integer overflow.
     * - The outer loop fixes the starting index i of the subarray.
     * - For each i, min is initialized to a very large value.
     * - The inner loop extends the subarray from i to j.
     * - At each step:
     * - min is updated as the minimum of the current min and arr[j]
     * - This min represents the minimum of subarray arr[i...j]
     * - min is added to sum and reduced modulo the given value
     * - This process continues until all subarrays are processed.
     * <p>
     * Example walkthrough:
     * arr = [3, 1, 2, 4]
     * <p>
     * Subarrays starting at index 0:
     * [3]       -> min = 3
     * [3,1]     -> min = 1
     * [3,1,2]   -> min = 1
     * [3,1,2,4] -> min = 1
     * <p>
     * Subarrays starting at index 1:
     * [1]       -> min = 1
     * [1,2]     -> min = 1
     * [1,2,4]   -> min = 1
     * <p>
     * Subarrays starting at index 2:
     * [2]       -> min = 2
     * [2,4]     -> min = 2
     * <p>
     * Subarrays starting at index 3:
     * [4]       -> min = 4
     * <p>
     * Total sum = 17
     * <p>
     * Important details:
     * - Only contiguous subarrays are considered.
     * - The minimum is updated incrementally to avoid unnecessary recalculation.
     * - Modulo operation is applied at each step to avoid overflow.
     * <p>
     * Complexity:
     * Time: O(n^2)
     * Two nested loops generate all subarrays.
     * <p>
     * Space: O(1)
     * Only a few variables are used regardless of input size.
     * <p>
     * Interview takeaway:
     * This brute force approach is the correct starting point in interviews.
     * It clearly demonstrates understanding of subarrays and minimum tracking.
     * From here, the natural optimization path is to use monotonic stacks
     * to reduce time complexity to O(n).
     */
    private static int sumOfSubArraysBruteForce(int[] arr) {
        int sum = 0;
        int mode = (int) Math.pow(10, 9) + 7;
        for (int i = 0; i < arr.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = i; j < arr.length; j++) {
                min = Math.min(min, arr[j]);
                sum = (sum + min) % mode;
            }
        }
        return sum;
    }

}
