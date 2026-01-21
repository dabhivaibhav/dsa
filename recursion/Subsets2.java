package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
90. Subsets II

Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
The solution set must not contain duplicate subsets. Return the solution in any order.

Example 1:
Input: nums = [1,2,2]
Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]

Example 2:
Input: nums = [0]
Output: [[],[0]]

Constraints:
            1 <= nums.length <= 10
            -10 <= nums[i] <= 10
 */
public class Subsets2 {
    public static void main(String[] args) {

        int[] nums = {1, 2, 2};
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        System.out.println("Nums:\n" + "First Approach:");
        subSetsRecursiveApproach1(0, nums, new ArrayList<Integer>(), result);
        System.out.println(result);
        result.clear();
        System.out.println("Second Approach:");
        subSetsRecursiveApproach2(0, nums, new ArrayList<Integer>(), result);
        System.out.println(result);

        int[] nums1 = {0};
        Arrays.sort(nums1);
        List<List<Integer>> result1 = new ArrayList<>();
        System.out.println("Nums1:\n" + "First Approach:");
        subSetsRecursiveApproach1(0, nums1, new ArrayList<Integer>(), result1);
        System.out.println(result1);
        result1.clear();
        System.out.println("Second Approach:");
        subSetsRecursiveApproach2(0, nums1, new ArrayList<Integer>(), result1);
        System.out.println(result1);


    }

    /**
     * subSetsRecursiveApproach1
     * <p>
     * What it does:
     * Generates all unique subsets (power set) of an integer array that may contain
     * duplicate elements, using a recursive include / exclude approach with
     * explicit duplicate skipping.
     * <p>
     * This method ensures that duplicate subsets are not added to the result
     * by skipping consecutive duplicate elements during the "exclude" decision.
     * <p>
     * Core Idea:
     * - At each index, we make two decisions:
     * 1. Include the current element.
     * 2. Exclude the current element.
     * - When excluding, we skip all consecutive duplicate values to prevent
     * generating identical subsets.
     * <p>
     * Preconditions:
     * - The input array must be sorted before calling this method.
     * - Sorting groups duplicates together, making them easy to skip.
     * <p>
     * Parameters:
     * - `index`: Current position in the candidate array.
     * - `candidate`: Sorted input array (may contain duplicates).
     * - `tempList`: Temporary list representing the current subset being built.
     * - `result`: List that stores all unique subsets.
     * <p>
     * Base Condition:
     * - When `index == candidate.length`:
     * - A complete subset is formed.
     * - Add a copy of `tempList` to `result`.
     * <p>
     * Recursive Logic:
     * - Include the current element:
     * - Add `candidate[index]` to `tempList`.
     * - Recurse for `index + 1`.
     * - Backtrack:
     * - Remove the last added element.
     * - Skip duplicates:
     * - While the next element is the same as the current one,
     * increment `index` to skip duplicates.
     * - Exclude branch:
     * - Recurse with the next distinct element.
     * <p>
     * Why the `while` loop is important:
     * - Prevents generating duplicate subsets like [1,2] multiple times
     * when duplicate elements exist.
     * <p>
     * Example:
     * Input: [1,2,2]
     * Output:
     * [], [1], [1,2], [1,2,2], [2], [2,2]
     * <p>
     * Time Complexity:
     * - O(2ⁿ) in the worst case.
     * <p>
     * Space Complexity:
     * - O(n) recursion stack depth.
     * - O(2ⁿ * n) for storing all subsets.
     * <p>
     * Interview Insight:
     * - This approach clearly demonstrates the include / exclude decision tree.
     * - Useful for understanding how duplicates are eliminated explicitly.
     * - Slightly harder to reason about compared to the loop-based approach.
     */
    public static void subSetsRecursiveApproach1(int index, int[] candidate, ArrayList<Integer> tempList, List<List<Integer>> result) {

        if (index == candidate.length) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        tempList.add(candidate[index]);
        subSetsRecursiveApproach1(index + 1, candidate, tempList, result);
        tempList.remove(tempList.size() - 1);
        while (index + 1 < candidate.length && candidate[index] == candidate[index + 1]) {
            index++;
        }
        subSetsRecursiveApproach1(index + 1, candidate, tempList, result);
    }

    /**
     * subSetsRecursiveApproach2
     * <p>
     * What it does:
     * Generates all unique subsets (power set) of an integer array that may contain
     * duplicate elements using a loop-based backtracking approach.
     * <p>
     * This is the most commonly used and preferred approach for the
     * "Subsets II" problem in interviews.
     * <p>
     * Core Idea:
     * - Every recursive call represents a valid subset.
     * - From a given index, iterate through all remaining elements and
     *   decide whether to include each one.
     * - Skip duplicate elements at the same recursion depth to avoid
     *   duplicate subsets.
     * <p>
     * Preconditions:
     * - The input array must be sorted before calling this method.
     * <p>
     * Parameters:
     * - `index`: Starting index for the current recursion level.
     * - `candidate`: Sorted input array (may contain duplicates).
     * - `tempList`: Current subset being constructed.
     * - `result`: Stores all unique subsets.
     * <p>
     * Recursive Logic:
     * - Add the current subset (`tempList`) to the result.
     * - Iterate from `index` to the end of the array:
     *   - If the current element is the same as the previous element at the same
     *     recursion level, skip it.
     *   - Add the element to `tempList`.
     *   - Recurse for the next index (`i + 1`).
     *   - Backtrack by removing the last added element.
     * <p>
     * Why `if (i > index && candidate[i] == candidate[i - 1]) continue;` works:
     * - Ensures that duplicates are skipped only at the same recursion level.
     * - Allows valid subsets like [2,2] while preventing duplicate subsets.
     * <p>
     * Example:
     * Input: [1,2,2]
     * Output:
     * [], [1], [1,2], [1,2,2], [2], [2,2]
     * <p>
     * Time Complexity:
     * - O(2ⁿ) in the worst case.
     * <p>
     * Space Complexity:
     * - O(n) recursion stack depth.
     * - O(2ⁿ * n) for result storage.
     * <p>
     * Interview Insight:
     * - This is the standard template for Subsets II.
     * - Easier to understand, cleaner, and less error-prone.
     * - Strongly preferred in coding interviews.
     */

    public static void subSetsRecursiveApproach2(int index, int[] candidate, ArrayList<Integer> tempList, List<List<Integer>> result) {
        result.add(new ArrayList<>(tempList));
        for (int i = index; i < candidate.length; i++) {
            if (i > index && candidate[i] == candidate[i - 1]) continue;
            tempList.add(candidate[i]);
            subSetsRecursiveApproach2(i + 1, candidate, tempList, result);
            tempList.remove(tempList.size() - 1);
        }
    }
}
