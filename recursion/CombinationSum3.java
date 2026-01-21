package recursion;

import java.util.ArrayList;
import java.util.List;

/*
216. Combination Sum III

Find all valid combinations of k numbers that sum up to n such that the following conditions are true:
Only numbers 1 through 9 are used.
Each number is used at most once.
Return a list of all possible valid combinations. The list must not contain the same combination twice,
and the combinations may be returned in any order.

Example 1:
Input: k = 3, n = 7
Output: [[1,2,4]]
Explanation: 1 + 2 + 4 = 7. There are no other valid combinations.

Example 2:
Input: k = 3, n = 9
Output: [[1,2,6],[1,3,5],[2,3,4]]
Explanation:
1 + 2 + 6 = 9
1 + 3 + 5 = 9
2 + 3 + 4 = 9
There are no other valid combinations.

Example 3:
Input: k = 4, n = 1
Output: []
Explanation: There are no valid combinations.
Using 4 different numbers in the range [1,9], the smallest sum we can get is 1+2+3+4 = 10 and since 10 > 1,
there are no valid combination.

Constraints:
            2 <= k <= 9
            1 <= n <= 60
 */
public class CombinationSum3 {

    public static void main(String[] args) {

        List<List<Integer>> result = new ArrayList<>();
        combinationSum3(3, 7, 1, new ArrayList<>(), result, 0);
        System.out.println(result);
        result.clear();
        combinationSum3(4, 1, 1, new ArrayList<>(), result, 0);
        System.out.println(result);
        result.clear();
        combinationSum3(3, 9, 1, new ArrayList<>(), result, 0);
        System.out.println(result);
        result.clear();
        combinationSum3SecondApproach(1, 3, 7, new ArrayList<>(), result);
        System.out.println(result);
    }

    /**
     * combinationSum3
     * <p>
     * What it does:
     * Finds all valid combinations of exactly `k` distinct numbers (chosen from 1 to 9)
     * whose total sum equals `n`.
     * <p>
     * Each number:
     * - Can be used at most once
     * - Must lie in the range [1, 9]
     * - Order of numbers in a combination does not matter
     * <p>
     * This method uses backtracking with recursion, incrementally building
     * combinations while tracking the current sum.
     * <p>
     * Core Idea:
     * - We explore numbers from `index` to 9.
     * - At each step, we decide whether to include a number.
     * - Once a number is used, we move to the next index (`i + 1`)
     * to ensure uniqueness and avoid permutations.
     * <p>
     * Parameters:
     * - `k`: Required number of elements in each combination.
     * - `n`: Target sum.
     * - `index`: Current starting number for selection.
     * - `temp`: Temporary list representing the current combination.
     * - `result`: Stores all valid combinations.
     * - `sum`: Running sum of elements in `temp`.
     * <p>
     * Base Condition:
     * - If `temp.size() == k` and `sum == n`:
     * - A valid combination is formed.
     * - Add a copy of `temp` to `result`.
     * <p>
     * Recursive Logic:
     * - Loop from `index` to 9:
     * - Skip numbers already present in `temp` (ensures uniqueness).
     * - Add the current number to `temp`.
     * - Recurse with updated sum and next index.
     * - Backtrack by removing the last added number.
     * <p>
     * Why `index` is important:
     * - Prevents reuse of the same number.
     * - Avoids generating permutations of the same combination.
     * <p>
     * Limitations of this approach:
     * - `temp.contains(i)` is unnecessary because `index` already guarantees
     * uniqueness.
     * - No early pruning when `sum > n`, which may cause extra recursion.
     * <p>
     * Example:
     * Input: k = 3, n = 7
     * Output: [[1, 2, 4]]
     * <p>
     * Time Complexity:
     * - Exponential in nature.
     * - Upper bound is O(C(9, k)) due to constrained search space.
     * <p>
     * Space Complexity:
     * - O(k) recursion depth.
     * - O(number of valid combinations × k) for result storage.
     * <p>
     * Interview Insight:
     * - Demonstrates correct backtracking logic.
     * - Can be optimized further with pruning and cleaner constraints.
     */
    public static void combinationSum3(int k, int n, int index, List<Integer> temp, List<List<Integer>> result, int sum) {
        if (temp.size() == k && sum == n) {
            result.add(new ArrayList<>(temp));
        }

        for (int i = index; i <= 9; i++) {
            if (temp.contains(i)) continue;
            temp.add(i);
            combinationSum3(k, n, i + 1, temp, result, sum + i);
            temp.remove(temp.size() - 1);
        }
    }


    /**
     * combinationSum3SecondApproach
     * <p>
     * What it does:
     * Generates all valid combinations of `k` distinct numbers (from 1 to 9)
     * whose sum equals `target`, using an optimized backtracking approach.
     * <p>
     * This version is cleaner and more efficient than the first approach
     * and closely matches the expected interview solution.
     * <p>
     * Core Idea:
     * - Build combinations incrementally.
     * - Reduce the remaining target at each step.
     * - Stop recursion early when:
     * - The combination size reaches `k`
     * - The target becomes negative
     * <p>
     * Parameters:
     * - `index`: Starting number to consider (ensures numbers are used once).
     * - `k`: Required number of elements in a combination.
     * - `target`: Remaining sum needed.
     * - `tempList`: Current combination being constructed.
     * - `result`: Stores all valid combinations.
     * <p>
     * Base Condition:
     * - If `tempList.size() == k`:
     * - If `target == 0`, store a copy of `tempList`.
     * - Return immediately (cannot add more elements).
     * <p>
     * Recursive Logic:
     * - Loop from `index` to 9:
     * - Add the current number to `tempList`.
     * - Recurse with:
     * - `i + 1` (next number)
     * - `target - i`
     * - Backtrack by removing the last element.
     * <p>
     * Why this approach is better:
     * - No need to track running sum separately.
     * - No redundant `contains()` checks.
     * - Naturally enforces:
     * - Uniqueness
     * - Fixed combination size
     * - Sum constraint
     * <p>
     * Example:
     * Input: k = 3, n = 9
     * Output:
     * [[1,2,6], [1,3,5], [2,3,4]]
     * <p>
     * Time Complexity:
     * - O(C(9, k)) — very manageable due to small fixed range.
     * <p>
     * Space Complexity:
     * - O(k) recursion depth.
     * - O(result size × k) output storage.
     * <p>
     * Interview Insight:
     * - This is the canonical solution for Combination Sum III.
     * - Demonstrates:
     * - Controlled backtracking
     * - Pruning by constraints
     * - Clean recursion design
     */

    private static void combinationSum3SecondApproach(int index, int k, int target, List<Integer> tempList, List<List<Integer>> result) {
        if (tempList.size() == k) {
            if (target == 0) result.add(new ArrayList<>(tempList));
            return;
        }
        for (int i = index; i <= 9; i++) {
            if (i > target) break;
            tempList.add(i);
            combinationSum3SecondApproach(i + 1, k, target - i, tempList, result);
            tempList.remove(tempList.size() - 1);
        }
    }
}
