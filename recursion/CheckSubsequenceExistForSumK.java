package recursion;

/*
Check if there exists a subsequence with sum K

Given an array nums and an integer k. Return true if there exist subsequences such that the sum of all elements in
subsequences is equal to k else false.

Example 1
Input: nums = [1, 2, 3, 4, 5], k = 8
Output: Yes
Explanation: The subsequences like [1, 2, 5] , [1, 3, 4] , [3, 5] sum up to 8.

Example 2
Input: nums = [4, 3, 9, 2] , k = 10
Output: No
Explanation: No subsequence can sum up to 10.

Constraints:
            1 <= nums.length <= 20
            1 <= nums[i] <= 100
            1 <= k <= 2000
 */
public class CheckSubsequenceExistForSumK {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int targetSum = 8;
        System.out.println(checkSubSequenceForGivenSumK(0, 0, arr, targetSum));

        arr = new int[]{4, 3, 9, 2};
        targetSum = 10;
        System.out.println(checkSubSequenceForGivenSumK(0, 0, arr, targetSum));
    }

    /**
     * checkSubSequenceForGivenSumK
     * <p>
     * What it does:
     * Determines whether there exists **at least one subsequence** of the given
     * array whose elements sum exactly to `targetSum`.
     * <p>
     * This method uses recursion with the classic **pick / not pick** strategy
     * and stops early as soon as a valid subsequence is found.
     * <p>
     * Core Idea:
     * - At each index, we have two choices:
     * 1. Pick the current element and add it to the running sum.
     * 2. Do not pick the current element and move ahead.
     * - If any path results in `sum == targetSum`, we return true immediately.
     * <p>
     * Why early pruning works:
     * - All array elements are positive (`nums[i] >= 1`).
     * - If `sum > targetSum`, adding more elements will only increase the sum.
     * - So the branch can be terminated early.
     * <p>
     * Parameters:
     * - `index`: Current position in the array being processed.
     * - `sum`: Running sum of the chosen subsequence so far.
     * - `arr`: Input array of positive integers.
     * - `targetSum`: Desired sum K.
     * <p>
     * Base Conditions:
     * - If `sum > targetSum`, return false (invalid path).
     * - If `index == arr.length`:
     * - Return true if `sum == targetSum`
     * - Otherwise return false
     * <p>
     * Recursive Logic:
     * - Try including the current element:
     * `check(index + 1, sum + arr[index])`
     * - Try excluding the current element:
     * `check(index + 1, sum)`
     * - Use logical OR (`||`) so recursion stops as soon as one valid subsequence
     * is found.
     * <p>
     * Example Walkthrough:
     * Input: arr = [1, 2, 3, 4, 5], targetSum = 8
     * Possible valid subsequences:
     * - [1, 2, 5]
     * - [1, 3, 4]
     * - [3, 5]
     * Output: true
     * <p>
     * Time Complexity:
     * - O(2ⁿ) in the worst case, where n is the length of the array.
     * - Early pruning can significantly reduce the actual number of calls.
     * <p>
     * Space Complexity:
     * - O(n) recursion stack depth.
     * <p>
     * Edge Cases Handled:
     * - No valid subsequence → returns false.
     * - Single element equal to targetSum → returns true.
     * - Empty subsequence is naturally excluded for `targetSum >= 1`.
     * <p>
     * Interview Insight:
     * - This is the **boolean variant** of the "count subsequences with sum K" problem.
     * - Replacing `||` with `+` converts this logic into a counting solution.
     * - This pattern appears frequently in:
     * - Subset Sum
     * - Knapsack problems
     * - Backtracking decision trees
     */
    private static boolean checkSubSequenceForGivenSumK(int index, int sum, int[] arr, int targetSum) {
        if (sum > targetSum) {
            return false;
        }
        if (index == arr.length) {
            return sum == targetSum;
        }
        return checkSubSequenceForGivenSumK(index + 1, sum + arr[index], arr, targetSum) || checkSubSequenceForGivenSumK(index + 1, sum, arr, targetSum);
    }
}
