package binarysearch.arrays_1D;

/*
Find out how many times the array is rotated

Given an integer array nums of size n, sorted in ascending order with distinct values. The array has been right rotated
an unknown number of times, between 0 and n-1 (including). Determine the number of rotations performed on the array.

Examples:
Input : nums = [4, 5, 6, 7, 0, 1, 2, 3]
Output: 4
Explanation: The original array should be [0, 1, 2, 3, 4, 5, 6, 7]. So, we can notice that the array has been rotated 4 times.

Input: nums = [3, 4, 5, 1, 2]
Output: 3
Explanation: The original array should be [1, 2, 3, 4, 5]. So, we can notice that the array has been rotated 3 times.

Constraints:
             n == nums.length
             1 <= n <= 10^4
             -10^4 <= nums[i] <= 10^4
             All the integers of nums are unique.
 */
public class FindSortedArrayRotationCount {

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2, 3};
        int[] nums1 = {3, 4, 5, 1, 2};
        int[] nums2 = {1, 2, 3, 4, 5};
        int[] nums3 = {1, 2, 3, 4, 5, 6};

        findRotationCount(nums);
        findRotationCount(nums1);
        findRotationCount(nums2);
        findRotationCount(nums3);

    }


    /**
     * findRotationCount
     * <p>
     * What it does:
     * This method determines how many times a sorted array has been right-rotated.
     * The number of rotations is equal to the index of the smallest element (pivot)
     * in the rotated sorted array.
     * <p>
     * Why it works:
     * - In a sorted array rotated 'k' times, the smallest element will be at index 'k'.
     * - The array is split into two sorted halves at the pivot (smallest element).
     * - Using binary search:
     * - If the left half [low..mid] is sorted, the smallest element must be in the right half,
     * so we move 'low' to 'mid + 1'.
     * - If the right half [mid..high] is sorted, the smallest element must be in the left half,
     * so we move 'high' to 'mid - 1'.
     * - While doing this, we track the smallest element's index (minIndex).
     * - This approach narrows down to the pivot in O(log n) time.
     * <p>
     * Output:
     * Prints the number of rotations performed on the array.
     * <p>
     * Example:
     * Input:  [4, 5, 6, 7, 0, 1, 2, 3]
     * Output: 4  (since smallest element 0 is at index 4)
     */
    public static void findRotationCount(int[] nums) {
        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform operation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Rotation count: 0");
            return;
        }
        int low = 0;
        int high = nums.length - 1;
        int minIndex = 0;
        while (low <= high) {


            int mid = low + (high - low) / 2;

            if (nums[low] <= nums[high]) {
                if (nums[low] < nums[minIndex]) {
                    minIndex = low;
                }
                break;
            }

            if (nums[low] <= nums[mid]) {
                if (nums[low] < nums[minIndex]) minIndex = low;
                low = mid + 1;
            } else {
                if (nums[mid] < nums[minIndex]) minIndex = mid;
                high = mid - 1;
            }
        }
        System.out.println("Rotation count: " + (minIndex));
    }
}

