package binarysearch.arrays_1D;

/*
Leetcode 540: Single element in sorted array

Given an array nums sorted in non-decreasing order. Every number in the array except one appears twice.
Find the single number in the array.

Examples:
Input :nums = [1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6]
Output:4
Explanation: Only the number 4 appears once in the array.

Input : nums = [1, 1, 3, 5, 5]
Output:3
Explanation: Only the number 3 appears once in the array.

Constraints:
          n == nums.length
          1 <= n <= 10^4
          -10^4 <= nums[i] <= 10^4
 */
public class SingleElementInSortedArray {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6};
        int[] nums1 = {1, 1, 3, 5, 5};
        int[] nums2 = {1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6, 7};
        System.out.println(findSingleElement(nums));
        System.out.println(findSingleElement(nums1));
        System.out.println(findSingleElement(nums2));
    }

    /**
     * findSingleElement
     * <p>
     * What it does:
     * This method finds the single element in a sorted array where every other element appears exactly twice.
     * It uses binary search to locate the unique element efficiently.
     * <p>
     * Why it works:
     * - In a sorted array of pairs, all elements before the single element follow a pattern:
     * the first occurrence of each pair is at an even index, and the second is at an odd index.
     * - After the single element, this pairing pattern shifts:
     * the first occurrence of each pair appears at an odd index, and the second at an even index.
     * - Using this property, we:
     * - Check the middle element.
     * - If it's different from both its neighbors, it must be the single element.
     * - Otherwise, use the index parity and neighbor comparison to decide which half to search:
     * - If `mid` is even and `nums[mid] == nums[mid + 1]`, the single is to the right.
     * - If `mid` is odd and `nums[mid] == nums[mid - 1]`, the single is to the right.
     * - Otherwise, the single is to the left.
     * - This halves the search space each step until only the single element remains.
     * <p>
     * Time Complexity:
     * - O(log n): Each step discards half the array using binary search.
     * <p>
     * Space Complexity:
     * - O(1): Only a few variables are used, and no extra data structures are created.
     * <p>
     * Output:
     * Returns the single element present in the array.
     * <p>
     * Example:
     * Input:  [1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6]
     * Output: 4  (since 4 is the only element that does not have a pair)
     */
    private static int findSingleElement(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        if (nums[0] != nums[1]) return nums[0];
        if (nums[n - 1] != nums[n - 2]) return nums[n - 1];
        int low = 1;
        int high = n - 2;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) {
                return nums[mid];
            }

            if (((mid % 2 == 1) && (nums[mid] == nums[mid - 1])) ||
                    ((mid % 2 == 0) && (nums[mid] == nums[mid + 1]))) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
