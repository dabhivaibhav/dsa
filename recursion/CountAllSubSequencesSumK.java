package recursion;

/*
Count all subsequences with sum K
Given an array nums and an integer k.Return the number of non-empty subsequences of nums such that the sum of all elements in the subsequence is equal to k.

Example 1
Input: nums = [4, 9, 2, 5, 1], k = 10
Output: 2
Explanation: The possible subsets with sum k are [9, 1], [4, 5, 1].

Example 2
Input: nums = [4, 2, 10, 5, 1, 3], k = 5
Output : 3
Explanation: The possible subsets with sum k are [4, 1], [2, 3], [5].

Constraints:
            1 <= nums.length <= 20
            1 <= nums[i] <= 100
            1 <= k <= 2000
 */
public class CountAllSubSequencesSumK {


    public static void main(String args[]) {

        int[] arr = {4, 9, 2, 5, 1};
        int targetSum = 10;
        System.out.println(countAllSubSequences(0, arr.length, 0, targetSum, arr));
        System.out.println(countAllSubSequences(0, arr.length, 0, 5, arr));
        System.out.println(countAllSubSequences(0, arr.length, 0, 13, arr));
        System.out.println(countAllSubSequences(0, arr.length, 0, 14, arr));
    }

    /**
     * countAllSubSequences
     * <p>
     * What it does:
     * Counts the number of subsequences (subsets) whose elements sum exactly to `targetSum`
     * using recursion with the classic "pick / not pick" strategy.
     * <p>
     * The function explores every element and decides:
     * - pick the element (add it to the running sum)
     * - OR skip the element (leave sum unchanged)
     * <p>
     * Why pruning works here:
     * - Because all array values are positive (nums[i] >= 1),
     *   once `sum > targetSum`, adding more elements can never bring the sum back down.
     * - So we can safely stop exploring that branch early.
     * <p>
     * Parameters:
     * - `index`: current position in the array being considered.
     * - `n`: length of the array.
     * - `sum`: running sum of the current chosen subsequence.
     * - `targetSum`: the required sum K.
     * - `arr`: input array.
     * <p>
     * Recursion logic:
     * - Base case 1: if `sum > targetSum`, return 0 (invalid branch).
     * - Base case 2: if `index == n`, return 1 if `sum == targetSum`, else 0.
     * - Recursive cases:
     *   - `l` = count including `arr[index]` → `sum + arr[index]`
     *   - `r` = count excluding `arr[index]` → `sum`
     *   - total = `l + r`
     * <p>
     * Edge Cases:
     * - If no subsequence equals `targetSum`, returns 0.
     * - Handles the case where a single element equals `targetSum`.
     * - Empty subsequence is naturally excluded for `targetSum >= 1`.
     * <p>
     * Time Complexity:
     * - O(2^n) in the worst case (each element has two choices).
     * - Pruning may reduce work significantly when sums exceed target early.
     * <p>
     * Space Complexity:
     * - O(n) recursion stack depth.
     */

    private static int countAllSubSequences(int index, int n, int sum, int targetSum, int[] arr) {
        if (sum > targetSum) {
            return 0;
        }
        if (index == n) {
            return sum == targetSum ? 1 : 0;
        }

        int l = countAllSubSequences(index + 1, n, sum + arr[index], targetSum, arr);
        int r = countAllSubSequences(index + 1, n, sum, targetSum, arr);

        return l + r;
    }
}
