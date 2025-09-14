package binarysearch;

/*
LEETCODE 35
Search Insert Position
Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the
index where it would be if it were inserted in order. You must write an algorithm with O(log n) runtime complexity.

Example 1:
Input: nums = [1,3,5,6], target = 5
Output: 2

Example 2:
Input: nums = [1,3,5,6], target = 2
Output: 1

Example 3:
Input: nums = [1,3,5,6], target = 7
Output: 4


Constraints:
        1 <= nums.length <= 10^4
        -10^4 <= nums[i] <= 10^4
        nums contains distinct values sorted in ascending order.
        -10^4 <= target <= 10^4
 */

public class SearchInsertPosition {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int target = 0;
        int[] nums1 = {1, 3, 5, 6};
        int target1 = 2;
        int[] nums2 = {1, 3, 5, 6};
        int target2 = 7;

        searchInsertPositionBruteForce(nums, target);
        searchInsertPositionBruteForce(nums1, target1);
        searchInsertPositionBruteForce(nums2, target2);
        searchInsertPositionOptimized(nums, target);
        searchInsertPositionOptimized(nums1, target1);
        searchInsertPositionOptimized(nums2, target2);


    }


    /**
     * searchInsertPositionBruteForce
     * <p>
     * What it does:
     * This method finds the position where a target value should be inserted in a sorted array
     * using a linear search approach. If the target is found, returns its index. Otherwise,
     * returns the index where it should be inserted to maintain the sorted order.
     * <p>
     * Why it works:
     * - Iterates through the array linearly
     * - When it finds the first element >= target, that's the insertion position
     * - If no such element exists, insert at the end (array length)
     * <p>
     * Example:
     * nums = [1,3,5,6], target = 2
     * - Checks 1: 1 < 2, continue
     * - Checks 3: 3 > 2, return index 1
     * <p>
     * Edge cases:
     * - If target is smaller than all elements, returns 0
     * - If target is larger than all elements, returns array length
     * <p>
     * Complexity:
     * Time:  O(n) — linear search through array
     * Space: O(1) — only uses a few variables
     */
    private static void searchInsertPositionBruteForce(int[] nums, int target) {
        int ans = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                System.out.println("Insert position for " + target + " found at index: " + i);
                return;
            }
        }
        System.out.println("Insert position for " + target + " found at index: " + ans);
    }

    /**
     * searchInsertPositionOptimized
     * <p>
     * What it does:
     * This method finds the position where a target value should be inserted in a sorted array
     * using binary search. If the target is found, returns its index. Otherwise,
     * returns the index where it should be inserted to maintain the sorted order.
     * <p>
     * Why it works:
     * - Uses binary search to efficiently find the insertion position
     * - When middle element >= target, that's a possible insertion position
     * - Continues searching right half if middle element < target
     * <p>
     * Example:
     * nums = [1,3,5,6], target = 2
     * - mid=1: nums[1]=3 >= 2, possible answer
     * - search complete, return 1
     * <p>
     * Edge cases:
     * - If target is smaller than all elements, returns 0
     * - If target is larger than all elements, returns array length
     * <p>
     * Complexity:
     * Time:  O(log n) — binary search
     * Space: O(1) — only uses a few variables
     */
    private static void searchInsertPositionOptimized(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int ans = nums.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] >= target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println("Insert position for " + target + " found at index: " + ans);
    }
}
