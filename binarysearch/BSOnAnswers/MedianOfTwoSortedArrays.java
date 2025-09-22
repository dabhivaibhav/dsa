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

        System.out.println(medianBinarySearch(nums1, nums2));
        System.out.println(medianBinarySearch(arr1, arr2));
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

    /**
     * What it does:
     * Computes the median of two sorted arrays in O(log(min(n1, n2))) time
     * using binary search on the shorter array to find a valid "partition"
     * that splits the combined arrays into balanced left and right halves.
     *
     * <p>
     * Why it works:
     * - The median is the value(s) in the middle of the combined sorted sequence.
     * - Instead of merging the arrays fully (O(n1 + n2)), we realize that the median
     * depends only on the elements around the cut between left and right halves.
     * - By partitioning both arrays at certain indices, we can ensure that:
     * - Every element in the left partition ≤ every element in the right partition.
     * - If we can find such a partition, then the median can be derived directly
     * from the border values.
     * - The key monotonic property: if the cut in array `a` is too far right,
     * move it left; if it is too far left, move it right. This makes binary search possible.
     *
     * <p>
     * How it works:
     * 1. Always apply binary search on the shorter array (to minimize the search space).
     * 2. Let `left = (n1 + n2 + 1) / 2` → the number of elements that must go into
     * the left partition.
     * 3. During binary search:
     * - `mid1` = how many elements we take from `a` into the left.
     * - `mid2` = `left - mid1` = how many elements we take from `b` into the left.
     * 4. Define four boundary values:
     * - `l1` = last element of `a`'s left partition (or -∞ if none).
     * - `l2` = last element of `b`'s left partition (or -∞ if none).
     * - `r1` = first element of `a`'s right partition (or +∞ if none).
     * - `r2` = first element of `b`'s right partition (or +∞ if none).
     * 5. Check if this is a valid partition:
     * - If `l1 <= r2` AND `l2 <= r1`, the partitions are correct.
     * - Median depends on total length:
     * - Odd → median = max(l1, l2).
     * - Even → median = (max(l1, l2) + min(r1, r2)) / 2.0.
     * 6. If not valid:
     * - If `l1 > r2` → took too many from `a`, move left (`high = mid1 - 1`).
     * - Else → took too few from `a`, move right (`low = mid1 + 1`).
     * 7. Continue until the correct partition is found.
     *
     * <p>
     * Examples:
     * Example 1:
     * a = [1, 3], b = [2]
     * - n1 = 2, n2 = 1, total = 3, left = 2.
     * - mid1 = 1 → mid2 = 1 → l1 = 1, r1 = 3, l2 = 2, r2 = ∞.
     * - Check: l1 <= r2 (1 ≤ ∞) ✓ and l2 <= r1 (2 ≤ 3) ✓ → valid cut.
     * - Odd total → median = max(l1, l2) = max(1, 2) = 2.
     * <p>
     * Example 2:
     * a = [1, 2], b = [3, 4]
     * - n1 = 2, n2 = 2, total = 4, left = 2.
     * - mid1 = 1 → mid2 = 1 → l1 = 1, r1 = 2, l2 = 3, r2 = 4.
     * - Check: l1 <= r2 (1 ≤ 4) ✓ but l2 <= r1 (3 ≤ 2) ✗ → invalid (move right).
     * - Next mid1 = 2 → mid2 = 0 → l1 = 2, r1 = ∞, l2 = -∞, r2 = 3.
     * - Check: l1 <= r2 (2 ≤ 3) ✓ and l2 <= r1 (-∞ ≤ ∞) ✓ → valid cut.
     * - Even total → median = (max(l1, l2) + min(r1, r2)) / 2 = (2 + 3) / 2 = 2.5.
     * <p>
     * Example 3:
     * a = [], b = [1]
     * - n1 = 0, n2 = 1, total = 1, left = 1.
     * - mid1 = 0 → mid2 = 1 → l1 = -∞, r1 = ∞, l2 = 1, r2 = ∞.
     * - Valid cut → odd total → median = max(l1, l2) = max(-∞, 1) = 1.
     *
     * <p>
     * Time Complexity:
     * - O(log(min(n1, n2))) → binary search on the shorter array.
     * <p>
     * Space Complexity:
     * - O(1) → only a few variables for boundaries and indices.
     *
     * <p>
     * Output:
     * Returns the median as a double.
     */

    private static double medianBinarySearch(int[] a, int[] b) {
        int n1 = a.length, n2 = b.length;
        //if n1 is bigger swap the arrays:
        if (n1 > n2) return medianBinarySearch(b, a);

        int n = n1 + n2; //total length
        int left = (n1 + n2 + 1) / 2; //length of left half
        //apply binary search:
        int low = 0, high = n1;
        while (low <= high) {
            int mid1 = (low + high) / 2;
            int mid2 = left - mid1;
            //calculate l1, l2, r1 and r2;
            int l1 = (mid1 > 0) ? a[mid1 - 1] : Integer.MIN_VALUE;
            int l2 = (mid2 > 0) ? b[mid2 - 1] : Integer.MIN_VALUE;
            int r1 = (mid1 < n1) ? a[mid1] : Integer.MAX_VALUE;
            int r2 = (mid2 < n2) ? b[mid2] : Integer.MAX_VALUE;

            if (l1 <= r2 && l2 <= r1) {
                if (n % 2 == 1) return Math.max(l1, l2);
                else return ((double) (Math.max(l1, l2) + Math.min(r1, r2))) / 2.0;
            } else if (l1 > r2) high = mid1 - 1;
            else low = mid1 + 1;
        }
        return 0; //dummy statement
    }
}
