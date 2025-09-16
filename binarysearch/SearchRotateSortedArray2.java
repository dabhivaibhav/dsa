package binarysearch;

/*
Leetcode 81. Search in Rotated Sorted Array II
There is an integer array nums sorted in non-decreasing order (not necessarily with distinct values).
Before being passed to your function, nums is rotated at an unknown pivot index k (0 <= k < nums.length) such that the
resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
For example, [0,1,2,4,4,4,5,6,6,7] might be rotated at pivot index 5 and become [4,5,6,6,7,0,1,2,4,4].
Given the array nums after the rotation and an integer target, return true if target is in nums, or false if it is not in nums.
You must decrease the overall operation steps as much as possible.

Example 1:
Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true

Example 2:
Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false

Constraints:
            1 <= nums.length <= 5000
            -10^4 <= nums[i] <= 10^4
            nums is guaranteed to be rotated at some pivot.
            -10^4 <= target <= 10^4
 */
public class SearchRotateSortedArray2 {

    public static void main(String[] args) {

        int[] nums = {2,5,6,0,0,1,2};
        int target = 0;
        int[] nums1 = {2,5,6,0,0,1,2};
        int target1 = 3;

        System.out.println("Search in Rotated Sorted Array II: " + searchRotatedSortedArray(nums, target));
        System.out.println("Search in Rotated Sorted Array II: " + searchRotatedSortedArray(nums1, target1));
    }

    /**
     * searchRotatedSortedArray
     * <p>
     * What it does:
     * This method searches for a target inside a rotated sorted array
     * that may contain duplicates. It returns true if the target exists and false otherwise.
     * <p>
     * Why it works:
     * In a rotated sorted array:
     *  - One of the two halves ([low..mid] or [mid..high]) is always sorted.
     *  - But duplicates can hide which half is sorted (e.g. [2,2,2,3,2]).
     * The algorithm:
     *  1. Compares mid with target: if equal → return true.
     *  2. If low == mid == high (ambiguous due to duplicates), shrink the window from both ends.
     *  3. Otherwise, detect which half is sorted and check if target lies in it.
     *  4. Narrow the search to the half that may contain the target.
     * This keeps the binary search logic valid even when duplicates are present.
     * <p>
     * How it works step by step:
     * - Calculate mid = low + (high-low)/2.
     * - If nums[mid] == target → return true.
     * - If nums[low] == nums[mid] == nums[high] → can't tell which side is sorted,
     *   so increment low and decrement high to shrink the range.
     * - Else if left half [low..mid] is sorted (nums[low] <= nums[mid]):
     *     - if target is inside [nums[low], nums[mid]] → go left (high = mid-1)
     *     - else → go right (low = mid+1)
     * - Else right half [mid..high] is sorted:
     *     - if target is inside [nums[mid], nums[high]] → go right (low = mid+1)
     *     - else → go left (high = mid-1)
     * - Continue until low > high → return false.
     * <p>
     * Edge cases handled:
     * - Rotation at any position
     * - Duplicates that break sorted-half detection
     * - Single element arrays
     * - Target smaller than all or larger than all elements
     * <p>
     * Example:
     * nums = [2,5,6,0,0,1,2], target = 0
     * mid=3 → nums[mid]=0 → return true
     * <p>
     * Complexity:
     * Time:  O(log n) on average, but can degrade to O(n) in worst case due to duplicates
     * Space: O(1) — only a few pointers used
     */
    private static boolean searchRotatedSortedArray(int[] nums, int target) {
        int n = nums.length;
        int low = 0;
        int high = n-1;
        while( low <= high){


            int mid = low + (high-low)/2;

            if(nums[mid] == target) return true;
            if(nums[low] == nums[mid] && nums[mid] == nums[high]){
                low = low + 1;
                high = high - 1;
                continue;
            }

            if(nums[low] <= nums[mid]){
                if(nums[low] <= target && target <= nums[mid]){
                    high = mid - 1;
                }else{
                    low = mid + 1;
                }
            }else{
                if(nums[mid] <= target && target <= nums[high]){
                    low = mid + 1;
                }else{
                    high = mid - 1;
                }
            }
        }
        return false;
    }
}
