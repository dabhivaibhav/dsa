package binarysearch.BSOn2DArrays;


/*
Leetcode: 74. Search a 2D Matrix

You are given an m x n integer matrix matrix with the following two properties:

Each row is sorted in non-decreasing order.
The first integer of each row is greater than the last integer of the previous row.
Given an integer target, return true if target is in matrix or false otherwise.

You must write a solution in O(log(m * n)) time complexity.

Example 1:
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
Output: true

Example 2:
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
Output: false

Constraints:
            m == matrix.length
            n == matrix[i].length
            1 <= m, n <= 100
            -10^4 <= matrix[i][j], target <= 10^4
 */
public class Search2DMatrix {

    public static void main(String[] args) {

        int[][] matrix = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}
        };
        int target = 3;
        int target1 = 13;

        System.out.println(search2DMatrixBruteForce(matrix, target));
        System.out.println(search2DMatrixBruteForce(matrix, target1));
        System.out.println(search2DMatrixBetter(matrix, target));
        System.out.println(search2DMatrixBetter(matrix, target1));
        System.out.println(search2DMatrixOptimal(matrix, target));
        System.out.println(search2DMatrixOptimal(matrix, target1));
    }

    /**
     * What it does:
     * Searches for a target value in a 2D matrix by scanning every element
     * row by row until the target is found or the matrix ends.
     *
     * <p>
     * Why it works:
     * - The brute-force approach makes no use of the matrix’s sorted property.
     * - Instead, it checks each cell directly against the target.
     * - If any cell matches, the method returns true; otherwise, false.
     *
     * <p>
     * How it works:
     * - Use two nested loops:
     * - Outer loop iterates over rows.
     * - Inner loop iterates over all columns in the current row.
     * - Compare each element with target.
     * - If a match is found, return true immediately.
     * - If the loops complete with no match, return false.
     *
     * <p>
     * Example:
     * matrix = [
     * [1, 3, 5, 7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 60]
     * ], target = 16
     * - Iterates row 0 → no match.
     * - Iterates row 1 → finds 16 → return true.
     * <p>
     * matrix = [
     * [1, 3, 5, 7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 60]
     * ], target = 13
     * - Scans all elements → no match → return false.
     *
     * <p>
     * Time Complexity:
     * - O(m · n), where m = number of rows and n = number of columns.
     * (Every element may be checked in the worst case.)
     * <p>
     * Space Complexity:
     * - O(1): Uses no extra space except loop variables.
     *
     * <p>
     * Output:
     * Returns true if target exists in the matrix, otherwise false.
     */
    private static boolean search2DMatrixBruteForce(int[][] matrix, int target) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * What it does:
     * Searches for a target value in a 2D matrix by pruning rows that cannot
     * contain the target and then applying binary search on the only plausible row(s).
     *
     * <p>
     * Why it works:
     * - The problem guarantees:
     * 1. Each row is sorted in ascending order.
     * 2. The first element of each row is greater than the last element of the previous row.
     * - This allows us to quickly eliminate rows:
     * - If target < first element of a row, no later rows can contain target → return false.
     * - If target > last element of a row, skip this row and continue.
     * - Only when target is within a row’s range [row[0], row[end]] do we need
     * to run binary search in that row.
     *
     * <p>
     * How it works:
     * - For each row:
     * 1. If target < row[0], return false immediately (matrix is sorted row-to-row).
     * 2. If target > row[last], skip this row.
     * 3. Otherwise, run a binary search (`numberFound`) on that row.
     * - If binary search finds the target → return true.
     * - If no row contains the target → return false.
     *
     * <p>
     * Example:
     * matrix = [
     * [1, 3, 5, 7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 60]
     * ], target = 16
     * - Row 0: last element 7 < target → skip.
     * - Row 1: 10 <= target <= 20 → run binary search, finds 16 → return true.
     * <p>
     * matrix = [
     * [1, 3, 5, 7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 60]
     * ], target = 13
     * - Row 0: skip.
     * - Row 1: 10 <= 13 <= 20 → binary search fails.
     * - Row 2: target < row[0] (23) → return false.
     *
     * <p>
     * Time Complexity:
     * - Worst case O(m · log n), where m = number of rows, n = number of columns.
     * (Binary search per row, but early pruning often reduces checks.)
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few variables.
     *
     * <p>
     * Output:
     * Returns true if target exists in the matrix, otherwise false.
     */
    private static boolean search2DMatrixBetter(int[][] matrix, int target) {

        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return false;

        for (int[] row : matrix) {
            if (target < row[0]) return false;          // rows after are even larger
            if (target > row[row.length - 1]) continue;          // try next row
            if (numberFound(row, target)) return true;  // search only plausible row
        }
        return false;
    }

    /**
     * What it does:
     * Searches for a target value in a 2D matrix in O(log(m*n)) time by treating
     * the matrix as a single flattened sorted 1D array and performing binary search.
     *
     * <p>
     * Why it works:
     * - The problem guarantees:
     * 1. Each row is sorted in ascending order.
     * 2. The first element of each row is greater than the last element of the previous row.
     * - Together, these conditions mean that if the matrix is read row by row (row-major order),
     * the elements form one globally sorted sequence.
     * - This allows us to apply binary search directly across m*n elements
     * by mapping 1D indices back into 2D (row, column) coordinates.
     *
     * <p>
     * How it works:
     * - Guard against invalid input (null or empty matrix).
     * - Let m = number of rows, n = number of columns.
     * - Set binary search range as low = 0, high = m*n - 1.
     * - Early prune: if target < matrix[0][0] or target > matrix[m-1][n-1], return false.
     * - While low <= high:
     * 1. mid = (low + high) / 2
     * 2. Map mid into 2D:
     * - row = mid / n
     * - col = mid % n
     * - midVal = matrix[row][col]
     * 3. Compare midVal with target:
     * - If equal → return true.
     * - If midVal < target → search right half (low = mid + 1).
     * - Else → search left half (high = mid - 1).
     * - If loop ends, target not found → return false.
     *
     * <p>
     * Example 1:
     * matrix = [
     * [1, 3, 5, 7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 60]
     * ], target = 3
     * - Flattened array = [1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
     * - Binary search finds 3 → return true.
     * <p>
     * Example 2:
     * matrix = [
     * [1, 3, 5, 7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 60]
     * ], target = 13
     * - Flattened array as above.
     * - Binary search fails → return false.
     *
     * <p>
     * Time Complexity:
     * - O(log(m*n)) → single binary search across all elements.
     * <p>
     * Space Complexity:
     * - O(1): Only a few variables are used.
     *
     * <p>
     * Output:
     * Returns true if the target exists in the matrix, otherwise false.
     */

    private static boolean search2DMatrixOptimal(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return false;
        int m = matrix.length, n = matrix[0].length;
        int low = 0;
        int high = m * n - 1;
        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) return false;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midVal = matrix[mid / n][mid % n];
            if (midVal == target) {
                return true;
            } else if (midVal < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    /**
     * Helper: numberFound
     * - Performs a binary search on a single sorted row (1D array).
     * - Returns true if target is present, else false.
     *
     * <p>
     * Time Complexity: O(log n), where n = row length.
     * Space Complexity: O(1).
     */
    private static boolean numberFound(int[] arr, int target) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
}
