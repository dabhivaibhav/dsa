package bitmanipulation;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 78: Subsets
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
public class SubSet {

    public static void main(String[] args) {

        int[] nums = {1, 2, 3};
        SubSet subSet = new SubSet();
        System.out.println(subSet.findSubSetsBitwise(nums));
    }

    /**
     * What it does:
     * Generates all possible subsets (the power set) of a given array of unique integers
     * using a bitmask (bitwise) approach.
     * <p>
     * Why it works (core idea):
     * For an array of length n, there are exactly 2^n possible subsets.
     * Each subset can be represented by an n-bit binary number (bitmask):
     * <p>
     * - Each bit represents whether an element is included (1) or excluded (0)
     * - Bit position j corresponds to nums[j]
     * <p>
     * By iterating over all numbers from 0 to (2^n - 1),
     * we generate every possible combination of included/excluded elements.
     * <p>
     * This maps directly to how computers represent subsets internally.
     * <p>
     * Step-by-step explanation of the logic:
     * <p>
     * 1) int n = nums.length;
     * - Store the number of elements.
     * - Each element corresponds to one bit position.
     * <p>
     * 2) int totalSubsets = 1 << n;
     * - Left shift 1 by n positions → 2^n.
     * - This gives the total number of subsets.
     * - Example: n = 3 → totalSubsets = 8 (000 to 111).
     * <p>
     * 3) Outer loop: for (int i = 0; i < totalSubsets; i++)
     * - i represents the current bitmask.
     * - Each value of i corresponds to one subset.
     * <p>
     * 4) List<Integer> subset = new ArrayList<>();
     * - Create a new list to store the current subset elements.
     * <p>
     * 5) Inner loop: for (int j = 0; j < n; j++)
     * - Iterate through each bit position (each array element).
     * <p>
     * 6) if ((i & (1 << j)) != 0)
     * - (1 << j) creates a mask with only the j-th bit set.
     * - ANDing it with i checks whether the j-th bit in i is set.
     * - If the result is non-zero, nums[j] is included in the subset.
     * <p>
     * 7) subset.add(nums[j]);
     * - Adds the corresponding element to the current subset.
     * <p>
     * 8) result.add(subset);
     * - After checking all bits, the completed subset is added to the result list.
     * <p>
     * Example walkthrough:
     * nums = [1, 2, 3]
     * <p>
     * Bitmasks from 0 to 7:
     * <p>
     * i = 0 → 000 → []
     * i = 1 → 001 → [1]
     * i = 2 → 010 → [2]
     * i = 3 → 011 → [1, 2]
     * i = 4 → 100 → [3]
     * i = 5 → 101 → [1, 3]
     * i = 6 → 110 → [2, 3]
     * i = 7 → 111 → [1, 2, 3]
     * <p>
     * Important details:
     * - Input elements must be unique to avoid duplicate subsets.
     * - Order of subsets does not matter.
     * - This approach avoids recursion and extra backtracking space.
     * - Bitmasking makes the subset-generation logic explicit and deterministic.
     * <p>
     * Complexity:
     * Time:  O(n * 2^n)
     * - 2^n subsets
     * - n checks per subset
     * <p>
     * Space: it is nearly to this - O(n * 2^n)
     * - required to store all subsets
     * <p>
     * Interview takeaway:
     * When you see "generate all subsets" and n ≤ 10,
     * think: "2^n → bitmask → iterate from 0 to (1 << n) - 1".
     */

    private List<List<Integer>> findSubSetsBitwise(int[] nums) {

        if (nums.length == 1) return List.of(List.of(nums[0]));

        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n;

        for (int i = 0; i < totalSubsets; i++) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            result.add(subset);
        }
        return result;
    }
}
