package array.easy_problems;

import java.util.Arrays;

/*
Given an integer array nums, rotate the array to the left by one. Note: There is no need to return anything, just modify the given array.
Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
 */
public class LeftRotateArrayByOne {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        System.out.println("Array before left rotation by one:");
        System.out.println(Arrays.toString(nums));
        System.out.println("Array after left rotation by one:");
        leftRotateByOne(nums);
    }

    private static void leftRotateByOne(int[] nums) {
        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform rotation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element, rotation has no effect.");
            return;
        }

        int n = nums.length;
        int firstElement = nums[0];

        for (int i = 1; i < n; i++) {
            nums[i - 1] = nums[i];
        }
        nums[n - 1] = firstElement;

        System.out.println(Arrays.toString(nums));
    }
}
