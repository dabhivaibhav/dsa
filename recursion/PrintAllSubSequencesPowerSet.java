package recursion;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 78: Print All Subsequences of a Powerset
Given an integer array nums of unique elements, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.

Example 1:
Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]

Example 2:
Input: nums = [0]
Output: [[],[0]]

Constraints:
            1 <= nums.length <= 10
            -10 <= nums[i] <= 10
            All the numbers of nums are unique.
 */
public class PrintAllSubSequencesPowerSet {

public static void main(String[] args) {
        printSubsequencesBruteForce(new int[]{1, 2, 3});
    }


    /**
     * printSubsequencesBruteForce
     * <p>
     * What it does:
     * Generates and prints **all possible subsequences (power set)** of a given
     * integer array containing unique elements, using an **iterative brute-force approach**.
     * <p>
     * The method builds the power set incrementally by expanding previously
     * generated subsets when a new element is encountered.
     * <p>
     * Core Idea:
     * - A power set contains all possible combinations of elements,
     *   including the empty set and the full set.
     * - For every element in the array, each existing subset can either:
     *   - exclude the element, or
     *   - include the element.
     * - This naturally doubles the number of subsets at each step.
     * <p>
     * How the algorithm works (step-by-step):
     * - Start with one subset: the empty list `[]`.
     * - For each number `n` in the input array:
     *   1. Capture the current size of the result list.
     *   2. For each existing subset:
     *      - Create a copy of that subset.
     *      - Add the current element `n` to the copy.
     *      - Add the new subset back into the result list.
     * - Repeat until all elements are processed.
     * <p>
     * Why storing `resultSize` is important:
     * - `result.size()` changes as new subsets are added.
     * - Iterating directly on `result.size()` would cause an infinite loop.
     * - Fixing the size ensures only previously existing subsets are expanded.
     * <p>
     * Why copying the list is required:
     * - `new ArrayList<>(result.get(i))` creates an independent subset.
     * - Without copying, modifications would affect previously stored subsets.
     * <p>
     * Example Walkthrough:
     * Input: [1, 2, 3]
     * - Start: [[]]
     * - After processing 1: [[], [1]]
     * - After processing 2: [[], [1], [2], [1,2]]
     * - After processing 3: [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]
     * <p>
     * Time Complexity:
     * - O(2ⁿ * n)
     *   - There are 2ⁿ total subsets.
     *   - Copying each subset takes up to O(n) time.
     * <p>
     * Space Complexity:
     * - O(2ⁿ * n)
     *   - Stores all subsets.
     *   - Each subset can have up to n elements.
     * <p>
     * Edge Cases Handled:
     * - Empty array → prints `[[]]`
     * - Single element → prints `[[], [x]]`
     * <p>
     * Limitations:
     * - Not suitable for large input sizes due to exponential growth.
     * - Uses extra memory to store all subsets before printing.
     * <p>
     * Interview Insight:
     * - This iterative solution is often preferred over recursion
     *   for clarity and stack safety.
     * - Demonstrates understanding of:
     *   - Power set logic
     *   - Incremental subset construction
     *   - Avoiding mutation pitfalls in list-based algorithms
     */
    private static void printSubsequencesBruteForce(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for(int n : nums) {
            int resultSize = result.size();
            for(int i = 0; i < resultSize  ; i++) { // do not use size here directly because size can change
                                                    // in each operation so loop will never end
                List<Integer> list = new ArrayList<>(result.get(i));
                list.add(n);
                result.add(list);
            }
        }

        for(List<Integer> list : result) {
            System.out.println(list);
        }
    }
}
