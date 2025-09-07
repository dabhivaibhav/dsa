package array.hard_problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 229. Majority Element II
 * <p>
 * Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: [3]
 * <p>
 * Example 2:
 * Input: nums = [1]
 * Output: [1]
 * <p>
 * Example 3:
 * Input: nums = [1,2]
 * Output: [1,2]
 * <p>
 * Constraints:
 * 1 <= nums.length <= 5 * 104
 * -109 <= nums[i] <= 109
 */
public class MajorityElement2 {

    public static void main(String[] args) {

        int[] arr = {3, 2, 3};
        int[] arr1 = {1};
        int[] arr2 = {1, 2};
        int targetFrequency = arr.length / 3;
        int targetFrequency1 = arr1.length / 3;
        int targetFrequency2 = arr2.length / 3;
        majorityElementBruteForceApproach(arr, targetFrequency);
        majorityElementBruteForceApproach(arr1, targetFrequency1);
        majorityElementBruteForceApproach(arr2, targetFrequency2);
        majorityElementOptimizedApproach(arr, targetFrequency);
        majorityElementOptimizedApproach(arr1, targetFrequency1);
        majorityElementOptimizedApproach(arr2, targetFrequency2);
    }

    /**
     * Finds majority elements using HashMap to count frequencies.
     * Algorithm:
     * 1. Create HashMap to store element frequencies
     * 2. Single pass through array updating frequencies and checking threshold
     * 3. Add elements to result list if frequency exceeds n/3
     * Time Complexity: O(n) - single pass through array
     * Space Complexity: O(n) - HashMap stores at most n elements
     *
     * @param arr             array to find majority elements in
     * @param targetFrequency threshold frequency (n/3)
     */
    private static void majorityElementBruteForceApproach(int[] arr, int targetFrequency) {

        List<Integer> majorityElementList = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            if (map.get(arr[i]) > targetFrequency) {
                majorityElementList.add(arr[i]);
            }
        }
        System.out.println("Majority element list: " + majorityElementList);
    }


    /**
     * Implements modified Boyer-Moore Voting Algorithm to find majority elements.
     * Algorithm:
     * First Pass:
     * 1. Maintain two candidates and their counts
     * 2. If count is 0, assign new candidate if different from other candidate
     * 3. If current element matches either candidate, increment respective count
     * 4. Otherwise, decrement both counts
     * <p>
     * Second Pass:
     * 1. Count actual frequencies of both candidates
     * 2. Add to result if frequency exceeds n/3
     * Time Complexity: O(n) - two passes through array
     * Space Complexity: O(1) - uses constant extra space
     *
     * @param nums            array to find majority elements in
     * @param targetFrequency threshold frequency (n/3)
     */
    private static void majorityElementOptimizedApproach(int[] nums, int targetFrequency) {
        int count1 = 0;
        int count2 = 0;
        int candidate1 = Integer.MIN_VALUE;
        int candidate2 = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {

            /*
            Two conditions are needed because:
            1. count1 == 0: Ensures we only assign new candidate1 when its count is reset
            2. nums[i] != candidate2: Prevents same number being assigned to both candidates
            Example: For array [2,2,1,3], without second condition both candidates could
            become 2, missing other potential majority elements like 1 or 3
             */

            if (count1 == 0 && nums[i] != candidate2) {
                count1 = 1;
                candidate1 = nums[i];
            } else if (count2 == 0 && nums[i] != candidate1) {
                count2 = 1;
                candidate2 = nums[i];
            } else if (nums[i] == candidate1) {
                count1++;
            } else if (nums[i] == candidate2) {
                count2++;
            } else {
                count1--;
                count2--;
            }
        }

        ArrayList<Integer> majorityElementList = new ArrayList<>();
        count1 = 0;
        count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == candidate1) {
                count1++;
            } else if (nums[i] == candidate2) {
                count2++;
            }
        }
        if (count1 > targetFrequency) {
            majorityElementList.add(candidate1);
        }
        if (count2 > targetFrequency) {
            majorityElementList.add(candidate2);
        }
        System.out.println("Majority element list: " + majorityElementList);


    }
}
