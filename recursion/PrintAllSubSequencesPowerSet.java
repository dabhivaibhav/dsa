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
        System.out.println();
        System.out.println(powerSet(new int[]{1, 2, 3}).toString());
        System.out.println(powerSet(new int[]{1, 2, 2}).toString());
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
     * including the empty set and the full set.
     * - For every element in the array, each existing subset can either:
     * - exclude the element, or
     * - include the element.
     * - This naturally doubles the number of subsets at each step.
     * <p>
     * How the algorithm works (step-by-step):
     * - Start with one subset: the empty list `[]`.
     * - For each number `n` in the input array:
     * 1. Capture the current size of the result list.
     * 2. For each existing subset:
     * - Create a copy of that subset.
     * - Add the current element `n` to the copy.
     * - Add the new subset back into the result list.
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
     * - There are 2ⁿ total subsets.
     * - Copying each subset takes up to O(n) time.
     * <p>
     * Space Complexity:
     * - O(2ⁿ * n)
     * - Stores all subsets.
     * - Each subset can have up to n elements.
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
     * for clarity and stack safety.
     * - Demonstrates understanding of:
     * - Power set logic
     * - Incremental subset construction
     * - Avoiding mutation pitfalls in list-based algorithms
     */
    private static void printSubsequencesBruteForce(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int n : nums) {
            int resultSize = result.size();
            for (int i = 0; i < resultSize; i++) { // do not use size here directly because size can change
                // in each operation so loop will never end
                List<Integer> list = new ArrayList<>(result.get(i));
                list.add(n);
                result.add(list);
            }
        }

        for (List<Integer> list : result) {
            System.out.print(list + " ");
        }
    }

    /**
     * powerSet
     * <p>
     * What it does:
     * Generates all possible subsets (power set) of the given integer array
     * using a **recursive backtracking approach**.
     * <p>
     * Each subset represents a subsequence where elements can be either
     * included or excluded while preserving relative order.
     * <p>
     * Core Idea:
     * - At every index, you have two choices:
     * 1. Include the current element
     * 2. Exclude the current element
     * - Recursion explores all such combinations systematically.
     * <p>
     * How it works:
     * - Starts with an empty subset.
     * - Recursively builds subsets by adding elements one by one.
     * - Each recursive call decides the next element to consider.
     * - Backtracking ensures all combinations are explored without duplication.
     * <p>
     * Time Complexity:
     * - O(2ⁿ * n)
     * - There are 2ⁿ subsets.
     * - Each subset copy takes up to O(n).
     * <p>
     * Space Complexity:
     * - O(n) recursion stack depth.
     * - O(2ⁿ * n) for storing all subsets.
     */

    private static List<List<Integer>> powerSet(int[] nums) {
        int size = nums.length;
        List<List<Integer>> result = new ArrayList<>();

        printSubsequencesRecursive(new ArrayList<>(), result, 0, nums);
        return result;
    }

    /**
     * printSubsequencesRecursive
     * <p>
     * What it does:
     * Recursively generates all subsets of the array starting from a given index
     * and adds them to the result list.
     * <p>
     * This method uses **backtracking** to explore all valid inclusion paths
     * while maintaining a temporary list (`current`) that represents the
     * subset being built.
     * <p>
     * Parameters:
     * - `current`: The subset currently being constructed.
     * - `result`: Stores all generated subsets.
     * - `start`: The index from which new elements can be chosen.
     * - `nums`: Input array of integers.
     * <p>
     * Step-by-step execution:
     * - Add a copy of `current` to `result` (every state is a valid subset).
     * - Iterate from `start` to the end of the array:
     * - Add the current element to `current`.
     * - Recurse to explore further subsets including this element.
     * - Remove the element (backtrack) before moving to the next choice.
     * <p>
     * Why copying `current` is necessary:
     * - `current` is modified during recursion.
     * - Storing a copy ensures previously generated subsets remain unchanged.
     * <p>
     * Duplicate handling logic:
     * - `if (i > start && nums[i] == nums[i - 1]) continue;`
     * - Prevents generating duplicate subsets when input contains duplicates.
     * - Ensures only the first occurrence at each recursion level is considered.
     * <p>
     * Why `start` is important:
     * - Prevents reusing earlier elements.
     * - Maintains increasing index order to avoid permutations.
     * <p>
     * Example Walkthrough:
     * Input: [1, 2, 3]
     * Generated subsets:
     * [], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]
     * <p>
     * Time Complexity:
     * - O(2ⁿ * n)
     * <p>
     * Space Complexity:
     * - O(n) recursion depth.
     * - O(2ⁿ * n) result storage.
     * <p>
     * Interview Insight:
     * - This is the **standard recursive power set template**.
     * - The same pattern applies to:
     * - Subsets
     * - Combination Sum
     * - K-length combinations
     * - Backtracking problems in general
     */
    private static void printSubsequencesRecursive(List<Integer> current, List<List<Integer>> result, int start, int[] nums) {

        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            current.add(nums[i]);
            printSubsequencesRecursive(current, result, i + 1, nums);
            current.removeLast();
        }
    }
}
