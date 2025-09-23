package binarysearch.BSOn2DArrays;

/*
Matrix Median

Given a 2D array matrix that is row-wise sorted. The task is to find the median of the given matrix.

Examples:
Input: matrix=[ [1, 4, 9], [2, 5, 6], [3, 7, 8] ]
Output: 5
Explanation: If we find the linear sorted array, the array becomes 1 2 3 4 5 6 7 8 9. So, median = 5

Input: matrix=[ [1, 3, 8], [2, 3, 4], [1, 2, 5] ]
Output: 3
Explanation: If we find the linear sorted array, the array becomes 1 1 2 2 3 3 4 5 8. So, median = 3

Constraints:
          N==matrix.size
          M==matrix[0].size
          1 <= N, M <= 10^5
          1 <= N*M <= 10^6
          1 <= matrix[i] <= 10^9
          N*M is odd
 */
public class MatrixMedian {

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 3, 8},
                {2, 3, 4},
                {1, 2, 5}
        };
        int[][] matrix1 = {
                {1, 4, 9},
                {2, 5, 6},
                {3, 7, 8}
        };
        int[][] matrix2 = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14, 15, 16, 17, 18},
                {20, 21, 22, 23, 24, 25, 26, 27, 28},
                {30, 31, 32, 33, 34, 35, 36, 37, 38},
                {40, 41, 42, 43, 44, 45, 46, 47, 48},
                {50, 51, 52, 53, 54, 55, 56, 57, 58},
        };

        System.out.println(findMatrixMedian(matrix));
        System.out.println(findMatrixMedian(matrix1));
        System.out.println(findMatrixMedian(matrix2));
    }

    /**
     * What it does:
     * Finds the median of a row-wise sorted matrix without flattening it into a
     * 1D array, using binary search on the value range.
     *
     * <p>
     * Why it works:
     * - Brute force would collect all N*M elements, sort them, and return the
     * middle → O(N*M log(N*M)).
     * - Since each row is already sorted, we can exploit this by:
     * 1. Setting a search range between the global minimum (first element in some row)
     * and the global maximum (last element in some row).
     * 2. Performing binary search on this value range:
     * - For a candidate value mid, count how many elements in the matrix
     * are ≤ mid.
     * - If this count is ≤ (N*M)/2, move right (low = mid+1).
     * - Otherwise, move left (high = mid-1).
     * - When binary search completes, low points to the median value.
     *
     * <p>
     * How it works:
     * - Determine initial value bounds:
     * - low = min of first element in each row
     * - high = max of last element in each row
     * - While low <= high:
     * - mid = (low + high) / 2
     * - smallEqual = count of elements ≤ mid (via helper countSmallEqual).
     * - If smallEqual <= required count → search right half
     * - Else search left half
     * - Return low as the median.
     *
     * <p>
     * Example:
     * matrix = [
     * [1, 4, 9],
     * [2, 5, 6],
     * [3, 7, 8]
     * ]
     * - Flattened = [1,2,3,4,5,6,7,8,9], median = 5
     * - Algorithm:
     * - low=1, high=9
     * - mid=5 → countSmallEqual=5 (half) → continue
     * - low eventually converges to 5 → answer.
     *
     * <p>
     * Time Complexity:
     * - O(log(max-min) * N * log M):
     * - Outer binary search runs over the value range (log(max-min)).
     * - For each mid, we scan all N rows.
     * - Each row check uses binary search (upperBound) in O(log M).
     * <p>
     * Space Complexity:
     * - O(1): Only variables used, no extra data structures.
     *
     * <p>
     * Output:
     * Returns the median element of the matrix as an integer.
     */
    private static int findMatrixMedian(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;

        // point low and high to right elements
        for (int i = 0; i < m; i++) {
            low = Math.min(low, matrix[i][0]);
            high = Math.max(high, matrix[i][n - 1]);
        }

        int req = (n * m) / 2;
        while (low <= high) {
            int mid = (low + high) / 2;
            int smallEqual = countSmallEqual(matrix, m, n, mid);
            if (smallEqual <= req) low = mid + 1;
            else high = mid - 1;
        }
        return low;
    }

    /**
     * Helper: countSmallEqual
     * - Counts how many elements in the matrix are ≤ x.
     * - For each row, uses binary search (upperBound) to count elements ≤ x.
     * <p>
     * Time Complexity: O(N log M).
     * Space Complexity: O(1).
     */
    private static int countSmallEqual(int[][] matrix, int m, int n, int x) {
        int cnt = 0;
        for (int i = 0; i < m; i++) {
            cnt += upperBound(matrix[i], x, n);
        }
        return cnt;
    }

    /**
     * Helper: upperBound
     * - Standard upper bound implementation for a sorted 1D array.
     * - Returns the index of the first element greater than x,
     * which is also the count of elements ≤ x.
     * <p>
     * Example:
     * arr = [1,2,3,5,7], x=3 → returns 3 (elements 1,2,3 are ≤ 3).
     * <p>
     * Time Complexity: O(log M).
     * Space Complexity: O(1).
     */
    private static int upperBound(int[] arr, int x, int n) {
        int low = 0, high = n - 1;
        int ans = n;

        while (low <= high) {
            int mid = (low + high) / 2;
            // maybe an answer
            if (arr[mid] > x) {
                ans = mid;
                // look for a smaller index on the left
                high = mid - 1;
            } else {
                low = mid + 1; // look on the right
            }
        }
        return ans;
    }

}
