package binarysearch.arrays_1D;

/*
62. Find Peak Element

A peak element is an element that is strictly greater than its neighbors.
Given a 0-indexed integer array nums, find a peak element, and return its index. If the array contains multiple peaks,
return the index to any of the peaks.
You may imagine that nums[-1] = nums[n] = -∞. In other words, an element is always considered to be strictly greater than
a neighbor that is outside the array.
You must write an algorithm that runs in O(log n) time.

Example 1:
Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.

Example 2:
Input: nums = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.

Constraints:
            1 <= nums.length <= 1000
            -2^31 <= nums[i] <= 2^31 - 1
            nums[i] != nums[i + 1] for all valid i.
 */
public class FindPeakElement {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        int[] nums1 = {1, 2, 1, 3, 5, 6, 4};

        System.out.println(findPeakElement(nums));
        System.out.println(findPeakElement(nums1));

    }

    /**
     * This method finds the index of any peak element in a given array.
     * A peak element is one that is strictly greater than both of its neighbors.
     * If multiple peaks exist, it can return the index of any one of them.
     * It uses binary search to achieve O(log n) time complexity.
     *
     * Why it works:
     * - A peak must exist in any array due to the given constraint: nums[i] != nums[i+1].
     * - We treat elements outside the array (nums[-1] and nums[n]) as negative infinity.
     * - First, handle edge peaks directly:
     *      - If the first element is greater than the second, it's a peak.
     *      - If the last element is greater than the second last, it's a peak.
     * - Otherwise, use binary search on the internal range:
     *      - If nums[mid] is greater than both neighbors, it's a peak — return mid.
     *      - If nums[mid] is greater than its left neighbor, the slope is rising,
     *        so a peak must exist to the right → move low = mid + 1.
     *      - Otherwise, the slope is falling,
     *        so a peak must exist to the left → move high = mid - 1.
     * - This works because at least one peak must lie in the direction of the slope.
     *
     * Time Complexity:
     * - O(log n): Each step discards half of the remaining search space.
     *
     * Space Complexity:
     * - O(1): Uses only constant extra variables without any additional data structures.
     *
     * Output:
     * Returns the index of a peak element in the array.
     *
     * Example:
     * Input:  [1, 2, 3, 1]
     * Output: 2  (since 3 is a peak element at index 2)
     */

    private static int findPeakElement(int[] nums) {

        int n = nums.length;
        if (n == 1) return 0;
        if (nums[0] > nums[1]) return 0;
        if (nums[n - 1] > nums[n - 2]) return n - 1;

        int low = 1;
        int high = n - 2;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (nums[mid] > nums[mid + 1] && nums[mid] > nums[mid - 1]) {
                return mid;
            } else if (nums[mid] > nums[mid - 1]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
