package array.hard_problems;

import java.util.Arrays;

/*
Merge two sorted arrays without extra space
Given two integer arrays nums1 and nums2. Both arrays are sorted in non-decreasing order.
Merge both the arrays into a single array sorted in non-decreasing order.
The final sorted array should be stored inside the array nums1 and it should be done in-place.
nums1 has a length of m + n, where the first m elements denote the elements of nums1 and rest are 0s.
nums2 has a length of n.

Examples:
Input: nums1 = [-5, -2, 4, 5], nums2 = [-3, 1, 8]
Output: [-5, -3, -2, 1, 4, 5, 8]
Explanation:
The merged array is: [-5, -3, -2, 1, 4, 5, 8], where [-5, -2, 4, 5] are from nums1 and [-3, 1, 8] are from nums2

Input: nums1 = [0, 2, 7, 8], nums2 = [-7, -3, -1]
Output: [-7, -3, -1, 0, 2, 7, 8]
Explanation:
The merged array is: [-7, -3, -1, 0, 2, 7, 8], where [0, 2, 7, 8] are from nums1 and [-7, -3, -1] are from nums2

Constraints:
        n == nums2.length.
        m + n == nums1.length.
        0 <= n, m <= 1000
        -10^4 <= nums1[i], nums2[i] <= 10^4
        Both nums1 and nums2 are sorted in non-decreasing order
 */
public class MergeSortedArray {

    public static void main(String[] args) {

        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int n = 3;
        int[] nums2 = {2, 5, 6};

        mergeSortedArray(nums1, m, nums2, n);

    }


    /**
     * Merges two sorted arrays nums1 and nums2 into nums1 in-place.
     * <p>
     * Algorithm steps:
     * 1. Start from the end of both arrays (i points to last element of nums1, j points to last element of nums2)
     * 2. Compare elements from both arrays and place larger element at the end of nums1
     * 3. Move respective pointers backwards after placing each element
     * 4. If elements remain in nums2, copy them to beginning of nums1
     * <p>
     * Example walkthrough:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6], n = 3
     * - Compare 3 and 6: place 6 at the end
     * - Compare 3 and 5: place 5
     * - Compare 3 and 2: place 3
     * - Continue until all elements are placed
     * <p>
     * Time Complexity: O(m + n)
     * - Single pass through both arrays from end to beginning
     * <p>
     * Space Complexity: O(1)
     * - No extra space used, modifications done in-place
     *
     * @param nums1 First sorted array with extra space at end
     * @param m     Number of elements in nums1
     * @param nums2 Second sorted array
     * @param n     Number of elements in nums2
     */
    private static void mergeSortedArray(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        while (i >= 0 && j >= 0) {
            if (nums1[i] >= nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }

        System.out.println("Merged arrray" + Arrays.toString(nums1));
    }
}
