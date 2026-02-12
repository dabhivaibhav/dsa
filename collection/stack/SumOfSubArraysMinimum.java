package collection.stack;

import java.util.Stack;

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
public class SumOfSubArraysMinimum {

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

    /**
     * sumSubarrayMins
     * <p>
     * What it does:
     * Calculates the sum of the minimum element of every possible contiguous subarray
     * using a monotonic stack based contribution technique.
     * The result is returned modulo 10^9 + 7.
     * <p>
     * Core idea:
     * Instead of generating all subarrays, we flip the perspective.
     * We ask:
     * For each element arr[i], in how many subarrays does it act as the minimum?
     * Once we know that count, we multiply it by arr[i] and add it to the total sum.
     * <p>
     * Thought process from brute force to optimized:
     * In the brute force solution, we explicitly generate all subarrays
     * and compute their minimums, which takes O(n^2).
     * While doing that, an important observation appears:
     * the same element contributes as the minimum to many subarrays.
     * <p>
     * So instead of iterating over subarrays,
     * we iterate over elements and count how many subarrays
     * consider that element as the minimum.
     * <p>
     * Key observation:
     * An element arr[i] is the minimum for all subarrays where:
     * - It is the smallest element in that subarray
     * - The subarray boundaries extend until a smaller element is found
     * <p>
     * This naturally leads to two boundaries:
     * - Previous Smaller or Equal Element on the left
     * - Next Smaller Element on the right
     * <p>
     * Explanation of the approach step by step:
     * <p>
     * 1. Precompute Next Smaller Element indices (nse):
     * For each index i, nse[i] gives the first index to the right
     * where the element is strictly smaller than arr[i].
     * If no such element exists, nse[i] is set to n.
     * <p>
     * 2. Precompute Previous Smaller or Equal Element indices (psee):
     * For each index i, psee[i] gives the closest index to the left
     * where the element is smaller or equal to arr[i].
     * If no such element exists, psee[i] is set to -1.
     * <p>
     * 3. Contribution calculation:
     * For each index i:
     * - left = i - psee[i]
     * Number of choices to extend the subarray to the left
     * - right = nse[i] - i
     * Number of choices to extend the subarray to the right
     * <p>
     * Total subarrays where arr[i] is the minimum:
     * left * right
     * <p>
     * Contribution of arr[i]:
     * arr[i] * (left * right)
     * <p>
     * 4. Add each contribution to the total sum modulo 10^9 + 7.
     * <p>
     * Example walkthrough:
     * arr = [3, 1, 2, 4]
     * <p>
     * For index 1 (value = 1):
     * - psee = -1
     * - nse = 4
     * - left = 1 - (-1) = 2
     * - right = 4 - 1 = 3
     * - frequency = 2 * 3 = 6
     * - contribution = 1 * 6 = 6
     * <p>
     * This means the value 1 is the minimum in 6 subarrays.
     * <p>
     * Important details:
     * - We use indices instead of values to handle duplicates correctly.
     * - Strictly smaller is used on one side and smaller or equal on the other
     * to avoid double counting.
     * - Multiplication is done using long to prevent overflow.
     * <p>
     * Complexity:
     * Time: O(n)
     * Each element is pushed and popped at most once from stacks.
     * <p>
     * Space: O(n)
     * Extra space is used for stacks and index arrays.
     * <p>
     * Interview takeaway:
     * This problem is a classic example of contribution technique
     * combined with index-based monotonic stacks.
     * Once mastered, the same pattern applies to:
     * sum of subarray maximums, histogram problems, and range contribution questions.
     */


    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;

        // Get NSE and PSEE indices
        int[] nse = findNSE(arr);
        int[] psee = findPSEE(arr);

        // Modulo for large results
        int mod = (int) 1e9 + 7;
        int sum = 0;

        // Traverse each element to compute its contribution
        for (int i = 0; i < n; i++) {
            // Count of elements to the left including current
            int left = i - psee[i];

            // Count of elements to the right including current
            int right = nse[i] - i;

            long freq = (long) left * right;

            int val = (int) ((freq * arr[i]) % mod);

            // Add contribution to sum
            sum = (sum + val) % mod;
        }

        // Return the final sum
        return sum;
    }

    /**
     * findNSE
     * <p>
     * What it does:
     * Finds the index of the Next Smaller Element to the right
     * for every element in the array.
     * <p>
     * Core idea:
     * Use a monotonic increasing stack of indices.
     * The stack ensures that elements waiting for a smaller value
     * stay unresolved until such a value appears.
     * <p>
     * Explanation:
     * - Traverse the array from right to left.
     * - While the stack top refers to an element greater than or equal
     * to the current element, pop it.
     * - The remaining top (if any) is the next smaller element.
     * - If the stack is empty, there is no smaller element to the right.
     * <p>
     * Why indices are used:
     * Using indices allows us to compute distances later
     * and correctly handle duplicate values.
     * <p>
     * Complexity:
     * Time: O(n)
     * Space: O(n)
     */

    // Function to find indices of Next Smaller Element (NSE)
    private int[] findNSE(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && arr[st.peek()] >= arr[i]) {
                st.pop();
            }
            ans[i] = !st.isEmpty() ? st.peek() : n;
            st.push(i);
        }
        return ans;
    }

    /**
     * findPSEE
     * <p>
     * What it does:
     * Finds the index of the Previous Smaller or Equal Element to the left
     * for every element in the array.
     * <p>
     * Core idea:
     * Use a monotonic increasing stack of indices while scanning left to right.
     * <p>
     * Explanation:
     * - Traverse the array from left to right.
     * - While the stack top refers to an element strictly greater
     * than the current element, pop it.
     * - The remaining top (if any) is the previous smaller or equal element.
     * - If the stack is empty, no such element exists on the left.
     * <p>
     * Why smaller or equal is used:
     * This tie-breaking rule ensures each subarray
     * assigns its minimum to exactly one index.
     * Without this, duplicate values would cause overcounting.
     * <p>
     * Complexity:
     * Time: O(n)
     * Space: O(n)
     */
    // Function to find indices of Previous Smaller or Equal Element (PSEE)
    private int[] findPSEE(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && arr[st.peek()] > arr[i]) {
                st.pop();
            }
            ans[i] = !st.isEmpty() ? st.peek() : -1;
            st.push(i);
        }
        return ans;
    }

}
