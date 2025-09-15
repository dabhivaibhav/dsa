package binarysearch;


/*
Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
If target is not found in the array, return [-1, -1].
You must write an algorithm with O(log n) runtime complexity.

Example 1:
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
Example 3:

Input: nums = [], target = 0
Output: [-1,-1]


Constraints:
            0 <= nums.length <= 10^5
            -10^9 <= nums[i] <= 10^9
            nums is a non-decreasing array.
            -10^9 <= target <= 10^9
 */
public class FindFirstLastOccurence {

    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 8;
        int[] nums1 = {5, 7, 7, 8, 8, 10};
        int target1 = 6;
        int[] nums2 = {};
        int target2 = 0;

    }

    private static int[] findFirstLastOccurenceBruteForce(int[] nums, int target) {

        int[] ans = {-1, -1};
        return null;
    }
}
