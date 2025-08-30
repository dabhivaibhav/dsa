package two_pointer;

import java.util.Arrays;

/*
Given an array nums consisting of only 0, 1, or 2. Sort the array in non-decreasing order. The sorting must be done in-
place, without making a copy of the original array.

Examples:
Input: nums = [1, 0, 2, 1, 0]
Output: [0, 0, 1, 1, 2]

Explanation: The nums array in sorted order has 2 zeroes, 2 ones and 1 two
Input: nums = [0, 0, 1, 1, 1]
Output: [0, 0, 1, 1, 1]

Explanation: The nums array in sorted order has 2 zeroes, 3 ones and zero twos

Constraints:
            1 <= nums.length <= 105
            nums consists of 0, 1 and 2 only.
 */
public class SortArrayOf012 {

    public static void main(String[] args) {

        int[] nums = {1, 0, 2, 1, 0};
        int[] nums1 = {0, 0, 1, 1, 1};
        int[] nums2 = {2, 0, 1};
        int[] nums3 = { 0,1,0,0,0,0,1,2,0,2,1,1,0,0,1,2,2,2};

        sortArray(nums);
        sortArray(nums1);
        sortArray(nums2);
        sortArray(nums3);

        System.out.println(Arrays.toString(nums3));
    }


    /**
     * Sorts an array containing only 0s, 1s, and 2s using the Dutch National Flag algorithm.
     * This algorithm uses three pointers (low, mid, high) to partition the array into three sections:
     * - Elements before low are 0s
     * - Elements between low and mid are 1s
     * - Elements after high are 2s
     * Time Complexity: O(n) where n is the length of array
     * Space Complexity: O(1) as it sorts in-place
     *
     * @param nums array containing only 0s, 1s, and 2s to be sorted
     */
    private static void sortArray(int[] nums) {
        int low = 0;          // pointer for 0s section
        int mid = 0;          // pointer for 1s section
        int high = nums.length - 1;  // pointer for 2s section

        while (mid <= high) {
            if (nums[mid] == 0) {
                // If we find 0, swap with low pointer and increment both low and mid
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                // If we find 1, just increment mid as it's in correct position
                mid++;
            } else{
                // If we find 2, swap with high pointer and decrement high
                int temp = nums[high];
                nums[high] = nums[mid];
                nums[mid] = temp;
                high--;
            }
        }
    }
}

