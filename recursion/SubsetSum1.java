package recursion;

import java.util.ArrayList;
import java.util.Collections;

/*
Subsets I

Given an array nums of n integers. Return array of sum of all subsets of the array nums.
Output can be returned in any order.

Example 1
Input : nums = [2, 3]
Output : [0, 2, 3, 5]
Explanation :
When no elements is taken then Sum = 0.
When only 2 is taken then Sum = 2.
When only 3 is taken then Sum = 3.
When element 2 and 3 are taken then sum = 2+3 = 5.

Example 2
Input : nums = [5, 2, 1]
Output : [0, 1, 2, 3, 5, 6, 7, 8]
Explanation :
When no elements is taken then Sum = 0.
When only 5 is taken then Sum = 5.
When only 2 is taken then Sum = 2.
When only 1 is taken then Sum = 1.
When element 2 and 1 are taken then sum = 2+1 = 3.

Constraints:
            1 <= n <= 15
            0 <= nums[i] <= 10^4
 */
public class SubsetSum1 {

    public static void main(String[] args) {
        ArrayList<Integer> result = new ArrayList<>();
        int[] nums = {2, 3};
        subsetSum(0, 0, nums, result);
        Collections.sort(result);
        System.out.println("1st Array:");
        System.out.println(result);
        result.clear();
        int[] nums1 = {5, 2, 1};
        subsetSum(0, 0, nums1, result);
        Collections.sort(result);
        System.out.println("2nd Array:");
        System.out.println(result);
    }

    /**
     * subsetSum
     * <p>
     * What it does:
     * Generates the **sum of every possible subset** of the given integer array
     * and stores all subset sums in the provided result list.
     * <p>
     * This method does not generate subsets explicitly; instead, it computes
     * the sum corresponding to each subset formed through recursive decisions.
     * <p>
     * Core Idea:
     * - For every element in the array, there are exactly two choices:
     * 1. Include the element in the current subset (add its value to the sum).
     * 2. Exclude the element from the current subset (sum remains unchanged).
     * - Recursion explores all such choices, effectively generating all subsets.
     * <p>
     * Parameters:
     * - `index`: Current position in the array being processed.
     * - `currentSum`: Sum of elements chosen so far for the current subset.
     * - `nums`: Input array of integers.
     * - `result`: List that collects the sum of each subset.
     * <p>
     * Base Condition:
     * - When `index == nums.length`:
     * - All decisions for the current subset are complete.
     * - Add `currentSum` to the result list and return.
     * <p>
     * Recursive Logic:
     * - Pick the current element:
     * `subsetSum(index + 1, currentSum + nums[index], nums, result)`
     * - Do not pick the current element:
     * `subsetSum(index + 1, currentSum, nums, result)`
     * <p>
     * Why this works:
     * - Each recursive path represents one unique subset.
     * - Passing `currentSum` forward ensures no backtracking is required
     * for sum calculations (primitive value).
     * <p>
     * Example Walkthrough:
     * Input: nums = [2, 3]
     * Subsets and sums:
     * - [] → 0
     * - [2] → 2
     * - [3] → 3
     * - [2, 3] → 5
     * Output after sorting: [0, 2, 3, 5]
     * <p>
     * Time Complexity:
     * - O(2ⁿ), where n is the number of elements.
     * - Every element contributes two choices (include or exclude).
     * <p>
     * Space Complexity:
     * - O(n) recursion stack depth.
     * - O(2ⁿ) for storing all subset sums in the result list.
     * <p>
     * Edge Cases Handled:
     * - Empty array → result contains [0].
     * - Single-element array → result contains [0, element].
     * <p>
     * Interview Insight:
     * - This is the **Subset Sum I** problem commonly asked in interviews.
     * - Forms the foundation for:
     * - Subset Sum II
     * - Partition Equal Subset Sum
     * - Meet-in-the-middle optimizations
     */
    private static void subsetSum(int index, int currentSum, int[] nums, ArrayList<Integer> result) {
        if (index == nums.length) {
            result.add(currentSum);
            return;
        }
        subsetSum(index + 1, currentSum + nums[index], nums, result);
        subsetSum(index + 1, currentSum, nums, result);
    }
}
