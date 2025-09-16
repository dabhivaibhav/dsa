package binarysearch.arrays_1D;

/*
Leetcode 153. Find Minimum in Rotated Sorted Array
Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
For example, the array nums = [0,1,2,4,5,6,7] might become: [4,5,6,7,0,1,2] if it was rotated 4 times. [0,1,2,4,5,6,7] if it was rotated 7 times.
Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
Given the sorted rotated array nums of unique elements, return the minimum element of this array.
You must write an algorithm that runs in O(log n) time.

Example 1:
Input: nums = [3,4,5,1,2]
Output: 1
Explanation: The original array was [1,2,3,4,5] rotated 3 times.

Example 2:
Input: nums = [4,5,6,7,0,1,2]
Output: 0
Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.

Example 3:
Input: nums = [11,13,15,17]
Output: 11
Explanation: The original array was [11,13,15,17] and it was rotated 4 times.

Constraints:
            n == nums.length
            1 <= n <= 5000
            -5000 <= nums[i] <= 5000
            All the integers of nums are unique.
            nums is sorted and rotated between 1 and n times.
 */
public class FindMinInSortedArr {

    public static void main(String[] args) {

        int[] nums = {3, 4, 5, 1, 2};
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int[] nums2 = {11, 13, 15, 17};
        findMin(nums);
        findMin(nums1);
        findMin(nums2);
    }

    /**
     * findMin
     * <p>
     * What it does:
     * This method finds the **minimum element** in a rotated sorted array of unique integers
     * using **binary search**. The array was originally sorted in ascending order but rotated
     * between 1 and n times.
     * <p>
     * Why it works:
     * - In a rotated sorted array, one half (left or right) is always sorted.
     * - If the left half `[low..mid]` is sorted (`nums[low] <= nums[mid]`), then the minimum
     * cannot be in this half except possibly at `nums[low]`. We update the current answer
     * with `nums[low]` and move to the right half (`low = mid + 1`).
     * - Otherwise, the rotation point (and thus the minimum) lies in the left half.
     * We update the current answer with `nums[mid]` and move to the left half (`high = mid - 1`).
     * - This keeps narrowing the search space toward the rotation point, where the smallest
     * element lives.
     * <p>
     * Example:
     * nums = [4,5,6,7,0,1,2]
     * - mid=3 → nums[0]=4 <= nums[3]=7 → ans=4, move right (low=4)
     * - mid=5 → nums[4]=0 <= nums[5]=1 → ans=0, move right (low=6)
     * - mid=6 → nums[6]=2 >= nums[6]=2 → ans=0, move right (low=7 → loop ends)
     * → answer = 0 (minimum element)
     * <p>
     * Edge cases:
     * - If the array is already fully sorted (not rotated), the first element is the minimum.
     * - If the array has only one element, return that element.
     * <p>
     * Complexity:
     * Time:  O(log n) — halves the search space every iteration
     * Space: O(1) — uses only a few extra variables
     */
    private static void findMin(int[] nums) {

        if (nums == null || nums.length == 0) {
            return;
        }
        if (nums.length == 1) {
            System.out.println(nums[0]);
            return;
        }
        int low = 0;
        int high = nums.length - 1;
        int ans = Integer.MAX_VALUE;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[low] <= nums[mid]) {
                ans = Math.min(ans, nums[low]);
                low = mid + 1;
            } else {
                ans = Math.min(ans, nums[mid]);
                high = mid - 1;
            }
        }

        System.out.println("Minimum element is " + ans);
    }
}
