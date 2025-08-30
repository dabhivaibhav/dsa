package hashing;

/*
Given an array of integers nums and an integer target. Return the indices(0 - indexed) of two elements in nums such that they add up to target.
Each input will have exactly one solution, and the same element cannot be used twice. Return the answer in increasing order.
Constraints:
            2 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
            -10^5 <= target <= 10^5
            Only one valid answer exists.
 */

import java.util.HashMap;

public class TwoSumProblem {

    public static void main(String[] args) {
        int[] num1 = {2, 6, 5, 8, 11, 15};
        int target1 = 14;

        int[] num2 = {1, 2, 3, 4, 5, 6};
        int target2 = 12;

        System.out.println("Two Sum Problem: ");
        twoSum(num1, target1);
        twoSum(num2, target2);
    }

    private static void twoSum(int[] num, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();
        boolean found = false;
        for (int i = 0; i < num.length; i++) {
            if (map.containsKey(target - num[i])) {
                System.out.println("Index of " + target + " - " + num[i] + " is " + map.get(target - num[i]) + " and " + i);
                found = true;
                break;
            }

            map.put(num[i], i);
        }

        if (found == false)
            System.out.println("No two elements sum up to the target.");
    }
}
