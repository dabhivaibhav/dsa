package binarysearch.BSOnAnswers;

/*
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
The overall run time complexity should be O(log (m+n)).

Example 1:
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.

Example 2:
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.

Constraints:
            nums1.length == m
            nums2.length == n
            0 <= m <= 1000
            0 <= n <= 1000
            1 <= m + n <= 2000
            -10^6 <= nums1[i], nums2[i] <= 10^6
 */
public class MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        int[] arr1 = {1, 2};
        int[] arr2 = {3, 4};

        System.out.println(medianBruteForce(nums1, nums2));
        System.out.println(medianBruteForce(arr1, arr2));
    }


    /**
     * What it does:
     * Finds the median of two sorted arrays by fully merging them into a single sorted array
     * and then picking the middle element(s).
     *
     * <p>
     * Why it works:
     * - The median is defined as the middle value(s) in the combined sorted sequence.
     * - By merging the two sorted arrays, we guarantee all elements are in order.
     * - Once merged, the middle element(s) can be accessed directly.
     *
     * <p>
     * How it works:
     * - Create a new array of length nums1.length + nums2.length.
     * - Use two pointers (i, j) to walk through nums1 and nums2:
     * - Always pick the smaller element and place it into the merged array.
     * - Continue until both arrays are exhausted.
     * - After merging:
     * - If the total length is odd → median is the middle element.
     * - If the total length is even → median is the average of the two middle elements.
     *
     * <p>
     * Example:
     * nums1 = [1, 2], nums2 = [3, 4]
     * - Merge → [1, 2, 3, 4]
     * - Length = 4 (even) → median = (2 + 3) / 2 = 2.5
     * <p>
     * nums1 = [1, 3], nums2 = [2]
     * - Merge → [1, 2, 3]
     * - Length = 3 (odd) → median = 2
     *
     * <p>
     * Time Complexity:
     * - O(m + n): Each element from both arrays is visited exactly once.
     * <p>
     * Space Complexity:
     * - O(m + n): Requires an extra array to store all merged elements.
     *
     * <p>
     * Output:
     * Returns the median value as a double.
     */
    private static double medianBruteForce(int[] nums1, int[] nums2) {
        int[] arr = new int[nums1.length + nums2.length];
        int n = arr.length;
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                arr[k++] = nums1[i++];
            } else {
                arr[k++] = nums2[j++];
            }
        }
        while (i < nums1.length) {
            arr[k++] = nums1[i++];
        }
        while (j < nums2.length) {
            arr[k++] = nums2[j++];
        }
        return arr.length % 2 == 0 ? (arr[n / 2] + arr[(n / 2) - 1]) / 2.0 : arr[n / 2];
    }

}
