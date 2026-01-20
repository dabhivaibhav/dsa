package recursion;

import java.util.ArrayList;

/*
Leetcode 39: Combination Sum
Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of
candidates where the chosen numbers sum to target. You may return the combinations in any order. The same number may be
chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the
chosen numbers is different. The test cases are generated such that the number of unique combinations that sum up to
target is less than 150 combinations for the given input.

Example 1:
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation: 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times. 7 is a candidate,
and 7 = 7. These are the only two combinations.

Example 2:
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]

Example 3:
Input: candidates = [2], target = 1
Output: []

Constraints:
            1 <= candidates.length <= 30
            2 <= candidates[i] <= 40
            All elements of candidates are distinct.
            1 <= target <= 40
 */
public class CombinationSum {

    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        int remaining = 0;
        for (int i : candidates) {
            remaining += i;
        }
        System.out.println("First: [2, 3, 6, 7]");
        combinationSum(candidates, target, 0, new ArrayList<Integer>());

        candidates = new int[]{2, 3, 5};
        target = 8;
        for (int i : candidates) {
            remaining += i;
        }
        System.out.println("\nSecond: [2, 3, 5]");
        combinationSum(candidates, target, 0, new ArrayList<>());

        candidates = new int[]{2};
        target = 1;
        for (int i : candidates) {
            remaining += i;
        }
        System.out.println("\nThird: [2]");
        combinationSum(candidates, target, 0, new ArrayList<>());
    }

    /**
     * combinationSum
     * <p>
     * What it does:
     * Finds and prints all unique combinations of numbers from the `candidates`
     * array that sum exactly to the given `target`.
     * <p>
     * Each number in `candidates` may be used unlimited times, and the order
     * of elements inside a combination does not matter.
     * <p>
     * This method uses backtracking with recursion, exploring all valid
     * include/exclude paths while maintaining a current combination list.
     * <p>
     * Core Idea:
     * - At each index, you have two choices:
     * 1. Pick the current element (stay at the same index because reuse is allowed)
     * 2. Do not pick the current element (move to the next index)
     * <p>
     * Parameters:
     * - `candidates`: Array of distinct positive integers.
     * - `target`: Remaining sum needed to reach the desired total.
     * - `index`: Current position in the candidates array.
     * - `combination`: Temporary list storing the current combination being built.
     * <p>
     * Base Conditions:
     * - `target == 0`:
     * - A valid combination is found.
     * - Print a copy of `combination` and return.
     * - `target < 0` or `index == candidates.length`:
     * - Invalid path or no more elements to choose.
     * - Stop recursion.
     * <p>
     * Recursive Logic:
     * - Pick the current element:
     * - Add `candidates[index]` to `combination`.
     * - Reduce `target` by the picked value.
     * - Recurse with the same index (element can be reused).
     * - Backtrack:
     * - Remove the last added element to restore state.
     * - Not pick the current element:
     * - Move to the next index (`index + 1`) without changing the target.
     * <p>
     * Why backtracking is required:
     * - `combination` is a shared mutable list.
     * - Removing the last element ensures sibling recursion paths are not polluted.
     * <p>
     * Example Walkthrough:
     * candidates = [2, 3, 6, 7], target = 7
     * Valid combinations printed:
     * - [2, 2, 3]
     * - [7]
     * <p>
     * Time Complexity:
     * - Exponential in nature.
     * - Roughly O(2^target) in the worst case due to unlimited reuse.
     * - Actual complexity depends on pruning and candidate values.
     * <p>
     * Space Complexity:
     * - O(target) recursion depth (worst case when repeatedly picking smallest number).
     * - Additional space for storing combinations during recursion.
     * <p>
     * Edge Cases Handled:
     * - No valid combination → prints nothing.
     * - Single-element candidates.
     * - Target smaller than smallest candidate.
     * <p>
     * Interview Insight:
     * - This is the classic backtracking pattern for:
     * - Combination Sum
     * - Unbounded Knapsack
     * - Key observation:
     * - Staying at the same index allows unlimited reuse.
     * - Moving to `index + 1` prevents duplicate combinations.
     */
    private static void combinationSum(int[] candidates, int target, int index, ArrayList<Integer> combination) {
        if (target == 0) {
            System.out.println(new ArrayList<>(combination)); // Print a copy
            return;
        }
        if (target < 0 || index == candidates.length) {
            return;
        }

        combination.add(candidates[index]);
        combinationSum(candidates, target - candidates[index], index, combination);

        combination.removeLast();
        combinationSum(candidates, target, index + 1, combination);
    }
}
