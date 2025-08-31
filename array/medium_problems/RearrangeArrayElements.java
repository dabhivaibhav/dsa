package array.medium_problems;

import java.util.ArrayList;
import java.util.Arrays;

/*
Given an integer array nums of even length consisting of an equal number of positive and negative integers.Return the
answer array in such a way that the given conditions are met:
Every consecutive pair of integers have opposite signs.
For all integers with the same sign, the order in which they were present in nums is preserved.
The rearranged array begins with a positive integer.

Examples:
Input : nums = [2, 4, 5, -1, -3, -4]
Output : [2, -1, 4, -3, 5, -4]
Explanation: The positive number 2, 4, 5 maintain their relative positions and -1, -3, -4 maintain their relative positions

Input : nums = [1, -1, -3, -4, 2, 3]
Output : [1, -1, 2, -3, 3, -4]
Explanation: The positive number 1, 2, 3 maintain their relative positions and -1, -3, -4 maintain their relative positions

Constraints:
            2 <= nums.length <= 105
            1 <= | nums[i] | <= 104
            nums.length is an even number.
            Number of positive and negative numbers are equal.
 */
public class RearrangeArrayElements {

    public static void main(String[] args) {

        int[] nums = {2, 4, 5, -1, -3, -4};
        int[] nums1 = {1, -1, -3, -4, 2, 3};

        rearrangeArrayBruteForceApproach(nums);
        rearrangeArrayOptimalApproach(nums1);

    }

    private static void rearrangeArrayBruteForceApproach(int[] nums) {
        int n = nums.length;

        ArrayList<Integer> positiveList = new ArrayList<>();
        ArrayList<Integer> negativeList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (nums[i] >= 0) {
                positiveList.add(nums[i]);
            } else {
                negativeList.add(nums[i]);
            }
        }

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                nums[i] = positiveList.get(i / 2);
            } else {
                nums[i] = negativeList.get(i / 2);
            }
        }

        System.out.println(Arrays.toString(nums));
    }


    private static void rearrangeArrayOptimalApproach(int[] nums) {
        int n = nums.length;
        int positiveindex = 0;
        int negativeindex = 1;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            if (nums[i] < 0) {
                result[negativeindex] = nums[i];
                negativeindex += 2;
            } else {
                result[positiveindex] = nums[i];
                positiveindex += 2;
            }
        }
        System.out.println(Arrays.toString(result));
    }
}
