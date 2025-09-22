package binarysearch.BSOnAnswers;

/*
Kth element of 2 sorted arrays
Given two sorted arrays a and b of size m and n respectively. Find the kth element of the final sorted array.

Examples:
Input: a = [2, 3, 6, 7, 9], b = [1, 4, 8, 10], k = 5
Output: 6
Explanation: The final sorted array would be [1, 2, 3, 4, 6, 7, 8, 9, 10]. The 5th element of this array is 6.

Input: a = [100, 112, 256, 349, 770], b = [72, 86, 113, 119, 265, 445, 892], k = 7
Output: 256
Explanation: Final sorted array is - [72, 86, 100, 112, 113, 119, 256, 265, 349, 445, 770, 892], 7th element of this array is 256.

Constraints:
            1 <= m, n <= 10^4
            0 <= arr1[i[, arr2[i] < 10^9
            1 <= k <= m+n
 */
public class FindKthElementOfTwoSortedArrays {

    public static void main(String[] args) {
        int[] a = {1, 3};
        int[] b = {2};
        int m = 2;
        int n = 1;
        int k = 1;
        System.out.println(kthElement(a, b, m, n, k));
    }

    /**
     * What it does:
     * Finds the k-th smallest element in the union of two sorted arrays without
     * actually merging them, using binary search on the partition point.
     *
     * <p>
     * Why it works:
     * - The k-th element of the merged array must belong to the "left partition"
     * of size k when the two arrays are combined.
     * - By choosing how many elements to take from array a (say mid1), the number
     * taken from array b is fixed as (k - mid1).
     * - If we find a partition where all elements on the left are ≤ all elements
     * on the right, then the largest element on the left side is exactly the k-th element.
     * - The partition property is monotonic: if the cut in a is too far right,
     * move it left; if too far left, move it right. This enables binary search.
     *
     * <p>
     * How it works:
     * 1. Always apply binary search on the smaller array (to minimize search space).
     * 2. The left partition must contain exactly k elements in total.
     * 3. Binary search variable mid1 = elements chosen from array a.
     * - Then mid2 = k - mid1 elements must come from array b.
     * 4. Define boundaries:
     * - l1 = last element in a's left part (or -∞ if none).
     * - l2 = last element in b's left part (or -∞ if none).
     * - r1 = first element in a's right part (or +∞ if none).
     * - r2 = first element in b's right part (or +∞ if none).
     * 5. Valid partition condition:
     * - l1 ≤ r2 AND l2 ≤ r1
     * → Then the k-th element = max(l1, l2).
     * 6. If not valid:
     * - If l1 > r2 → too many from a → move left (high = mid1 - 1).
     * - Else → too few from a → move right (low = mid1 + 1).
     *
     * <p>
     * Examples:
     * Example 1:
     * a = [2, 3, 6, 7, 9], b = [1, 4, 8, 10], k = 5
     * - Combined sorted array = [1, 2, 3, 4, 6, 7, 8, 9, 10]
     * - 5th element = 6.
     * <p>
     * Example 2:
     * a = [100, 112, 256, 349, 770],
     * b = [72, 86, 113, 119, 265, 445, 892], k = 7
     * - Combined sorted = [72, 86, 100, 112, 113, 119, 256, 265, 349, 445, 770, 892]
     * - 7th element = 256.
     * <p>
     * Example 3:
     * a = [1, 3], b = [2], k = 1
     * - Combined sorted = [1, 2, 3]
     * - 1st element = 1.
     *
     * <p>
     * Time Complexity:
     * - O(log(min(m, n))) → binary search on the smaller array.
     * <p>
     * Space Complexity:
     * - O(1) → only a few variables for partition boundaries.
     *
     * <p>
     * Output:
     * Returns the k-th smallest element as an integer.
     */
    public static int kthElement(int[] a, int[] b, int m, int n, int k) {
        if (m > n) return kthElement(b, a, n, m, k);

        int left = k; // length of left half

        // apply binary search:
        int low = Math.max(0, k - n), high = Math.min(k, m);
        while (low <= high) {
            int mid1 = (low + high) >> 1;
            int mid2 = left - mid1;
            // calculate l1, l2, r1, and r2
            int l1 = Integer.MIN_VALUE, l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE, r2 = Integer.MAX_VALUE;
            if (mid1 < m) r1 = a[mid1];
            if (mid2 < n) r2 = b[mid2];
            if (mid1 - 1 >= 0) l1 = a[mid1 - 1];
            if (mid2 - 1 >= 0) l2 = b[mid2 - 1];

            if (l1 <= r2 && l2 <= r1) {
                return Math.max(l1, l2);
            }

            // eliminate the halves:
            else if (l1 > r2) high = mid1 - 1;
            else low = mid1 + 1;
        }
        return 0; // dummy statement
    }

}
