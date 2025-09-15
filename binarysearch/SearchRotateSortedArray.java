package binarysearch;

/*
LeetCode : 33. Search in Rotated Sorted Array
There is an integer array nums sorted in ascending order (with distinct values).
Prior to being passed to your function, nums is possibly left rotated at an unknown index k (1 <= k < nums.length) such
that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For
example, [0,1,2,4,5,6,7] might be left rotated by 3 indices and become [4,5,6,7,0,1,2].
Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums,
or -1 if it is not in nums. You must write an algorithm with O(log n) runtime complexity.

Example 1:
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4

Example 2:
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
Example 3:

Input: nums = [1], target = 0
Output: -1

Constraints:
            1 <= nums.length <= 5000
            -10^4 <= nums[i] <= 10^4
            All values of nums are unique.
            nums is an ascending array that is possibly rotated.
            -10^4 <= target <= 10^4
 */
public class SearchRotateSortedArray {

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int target1 = 3;

        System.out.println("Brute force approach:");
        System.out.println("Index of " + target + " is: " + searchSortedArrayBruteForce(nums, target));
        System.out.println("Index of " + target1 + " is: " + searchSortedArrayBruteForce(nums1, target1));
        System.out.println("Optimized approach:");
        System.out.println("Index of " + target + " is: " + searchSortedArrayOptimal(nums, target));
        System.out.println("Index of " + target1 + " is: " + searchSortedArrayOptimal(nums1, target1));

    }

    /**
     * searchSortedArrayBruteForce
     * <p>
     * What it does:
     * This method linearly scans the entire array to find the target element.
     * It compares each element one by one from left to right.
     * If it matches the target, it immediately returns that index.
     * If no match is found after checking all elements, it returns -1.
     * <p>
     * Why it works:
     * Because it checks every element, it guarantees finding the target if it exists.
     * It doesn't depend on the array being sorted or rotated in any way.
     * <p>
     * When to use:
     * - When the array is small.
     * - When clarity is more important than performance.
     * - When the array might not be sorted.
     * <p>
     * Example:
     * nums = [4,5,6,7,0,1,2], target = 0
     * i=0..4 → match at i=4 → return 4
     * <p>
     * Complexity:
     * Time:  O(n) — checks all elements in worst case
     * Space: O(1) — uses only loop variables
     */
    private static int searchSortedArrayBruteForce(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) return i;

        }
        return -1;
    }

    /**
     * searchSortedArrayOptimal
     * <p>
     * What it does:
     * At each step, it finds the middle element and determines which half (left or right)
     * is sorted. If the target lies inside the sorted half, it searches there;
     * otherwise it searches the other half.
     * <p>
     * Why it works:
     * A rotated sorted array is made by cutting a sorted array into two parts
     * and swapping them. This guarantees that:
     * - One of the two halves `[low..mid]` or `[mid..high]` is always sorted.
     * - By checking if the target lies in that sorted half's range,
     * we can safely discard half of the array each iteration.
     * <p>
     * How it works step by step:
     * 1. Compute mid = (low + high)/2
     * 2. If nums[mid] == target → return mid
     * 3. If left half [low..mid] is sorted (nums[low] <= nums[mid]):
     * - if target is within [nums[low], nums[mid]) → search left (high = mid-1)
     * - else → search right (low = mid+1)
     * 4. Else right half [mid..high] is sorted:
     * - if target is within (nums[mid], nums[high]] → search right (low = mid+1)
     * - else → search left (high = mid-1)
     * <p>
     * When to use:
     * - The array is sorted but rotated.
     * - Elements are distinct (this logic assumes no duplicates).
     * - Need O(log n) efficiency.
     * <p>
     * Example:
     * nums = [4,5,6,7,0,1,2], target = 0
     * mid=3 → nums[mid]=7, left sorted [4..7], target not inside → go right
     * mid=5 → nums[mid]=1, right sorted [1..2], target < 1 → go left
     * mid=4 → nums[mid]=0, match → return 4
     * <p>
     * Complexity:
     * Time:  O(log n) — halves search space each iteration
     * Space: O(1) — only a few pointers used
     */
    private static int searchSortedArrayOptimal(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) return mid;

            if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }
}
