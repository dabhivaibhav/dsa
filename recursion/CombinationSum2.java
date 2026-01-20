package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
40. Combination Sum II
Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in
candidates where the candidate numbers sum to target.
Each number in candidates may only be used once in the combination.
Note: The solution set must not contain duplicate combinations.

Example 1:
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output:
[[1,1,6],[1,2,5],[1,7],[2,6]]

Example 2:
Input: candidates = [2,5,2,1,2], target = 5
Output:
[[1,2,2],[5]]

Constraints:
            1 <= candidates.length <= 100
            1 <= candidates[i] <= 50
            1 <= target <= 30
 */
public class CombinationSum2 {

    public static void main(String[] args) {

        int[] arr = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        combinationSum2Recursive(0, target, arr, result, new ArrayList<>());
        System.out.println(result);
        result.removeAll(result);
        System.out.println("Second Approach:");
        combinationSum2Recursive(0, target, arr, result, new ArrayList<>());
        System.out.println(result);

        arr = new int[]{2, 5, 2, 1, 2};
        target = 5;
        Arrays.sort(arr);
        result = new ArrayList<>();
        combinationSum2Recursive(0, target, arr, result, new ArrayList<>());
        System.out.println(result);
        result.removeAll(result);
        System.out.println("Second Approach:");
        combinationSum2Recursive(0, target, arr, result, new ArrayList<>());
        System.out.println(result);

    }

    private static void combinationSum2Recursive(int index, int target, int[] arr, List<List<Integer>> result, List<Integer> tempList) {


        if (target == 0) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        if (index == arr.length || target < 0) {
            return;
        }

        tempList.add(arr[index]);
        combinationSum2Recursive(index + 1, target - arr[index], arr, result, tempList);
        tempList.removeLast();
        while (index + 1 < arr.length && arr[index] == arr[index + 1]) {
            index++;
        }
        combinationSum2Recursive(index + 1, target, arr, result, tempList);
    }


    private static void combinationSum2RecursiveSecondApproach(int index, int target, int[] candidates, ArrayList<Integer> tempList, ArrayList<ArrayList<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(tempList));
            return;
        }
        if (index >= candidates.length || target < 0) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }
            tempList.add(candidates[index]);
            combinationSum2RecursiveSecondApproach(index, target - candidates[index], candidates, tempList, result);
            tempList.remove(tempList.size() - 1);
        }

    }
}
