package binarysearch.BSOn2DArrays;

/*
Leetcode 240. Search a 2D Matrix II
Write an efficient algorithm that searches for a value target in an m x n integer matrix. This matrix has the following properties:
Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.

Example 1:
Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
Output: true

Example 2:
Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
Output: false

Constraints:
            m == matrix.length
            n == matrix[i].length
            1 <= n, m <= 300
            -10^9 <= matrix[i][j] <= 10^9
            All the integers in each row are sorted in ascending order.
            All the integers in each column are sorted in ascending order.
            -10^9 <= target <= 10^9
 */

public class Search2DMatrix2 {

    public static void main(String args[]) {
        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        int target = 5;
        int[][] matrix1 = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        int target1 = 20;

        System.out.println(searchTargetBruteForce(matrix, target));
        System.out.println(searchTargetBruteForce(matrix1, target1));
        System.out.println(searchTargetBetter(matrix, target));
        System.out.println(searchTargetBetter(matrix1, target1));
        System.out.println(searchTargetOptimal(matrix, target));
        System.out.println(searchTargetOptimal(matrix1, target1));
    }

    /**
     * What it does:
     * Searches for a target value in a 2D matrix by scanning every element
     * row by row until the target is found or the matrix ends.
     *
     * <p>
     * Why it works:
     * - The brute-force approach ignores the matrix's sorted properties.
     * - Instead, it checks every cell directly against the target.
     * - If a match is found, return true; otherwise, return false.
     *
     * <p>
     * How it works:
     * - Use two nested loops:
     * - Outer loop iterates through rows.
     * - Inner loop iterates through all columns in each row.
     * - Compare each value with target.
     * - If match is found, return true immediately.
     * - If the loops finish, return false.
     *
     * <p>
     * Example:
     * matrix = [
     * [1, 4, 7, 11],
     * [2, 5, 8, 12],
     * [3, 6, 9, 16]
     * ], target = 8
     * - Scans all elements in order until reaching 8 → return true.
     *
     * <p>
     * Time Complexity:
     * - O(m · n), where m = rows, n = columns.
     * <p>
     * Space Complexity:
     * - O(1).
     *
     * <p>
     * Output:
     * Returns true if target is found, otherwise false.
     */
    private static boolean searchTargetBruteForce(int[][] matrix, int target) {
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
     * Searches for a target value in a 2D matrix by running a binary search
     * on each row independently.
     *
     * <p>
     * Why it works:
     * - Each row in the matrix is sorted in ascending order.
     * - Therefore, binary search can be applied row by row.
     * - If target is present in any row, the binary search will find it.
     *
     * <p>
     * How it works:
     * - Iterate over all rows.
     * - For each row:
     * - Call `targetFound` (binary search).
     * - If it returns true, return true immediately.
     * - If no row contains the target, return false.
     *
     * <p>
     * Example:
     * matrix = [
     * [1, 4, 7, 11],
     * [2, 5, 8, 12],
     * [3, 6, 9, 16]
     * ], target = 5
     * - Row 0 binary search → not found.
     * - Row 1 binary search → finds 5 → return true.
     *
     * <p>
     * Time Complexity:
     * - O(m log n), where m = rows, n = columns.
     * (Each row is searched with binary search.)
     * <p>
     * Space Complexity:
     * - O(1).
     *
     * <p>
     * Output:
     * Returns true if target is found, otherwise false.
     */
    private static boolean searchTargetBetter(int[][] matrix, int target) {

        for (int[] ints : matrix) {

            if (targetFound(ints, target)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Helper: targetFound
     * - Standard binary search in a 1D sorted array.
     * - Returns true if target is found, else false.
     * - Time Complexity: O(log n).
     * - Space Complexity: O(1).
     */
    private static boolean targetFound(int[] matrix, int target) {
        int low = 0;
        int high = matrix.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (matrix[mid] == target) {
                return true;
            } else if (matrix[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }


    /**
     * What it does:
     * Searches for a target value in a 2D matrix in O(m + n) time
     * using the "staircase search" approach, starting from the top-right corner.
     *
     * <p>
     * Why it works:
     * - The matrix is sorted both row-wise and column-wise:
     * - Values increase left to right within rows.
     * - Values increase top to bottom within columns.
     * - By starting at the top-right element:
     * - If the current element > target, move left (all elements below are larger).
     * - If the current element < target, move down (all elements left are smaller).
     * - Each step eliminates one row or one column, ensuring progress.
     *
     * <p>
     * How it works:
     * - Start at row = 0, col = n-1 (top-right corner).
     * - While within bounds:
     * - If matrix[row][col] == target → return true.
     * - If matrix[row][col] > target → move left (col--).
     * - If matrix[row][col] < target → move down (row++).
     * - If search space exhausted, return false.
     *
     * <p>
     * Example:
     * matrix = [
     * [1, 4, 7, 11],
     * [2, 5, 8, 12],
     * [3, 6, 9, 16]
     * ], target = 9
     * - Start at (0,3)=11 → 11 > 9 → move left.
     * - At (0,2)=7 → 7 < 9 → move down.
     * - At (1,2)=8 → 8 < 9 → move down.
     * - At (2,2)=9 → found → return true.
     *
     * <p>
     * Time Complexity:
     * - O(m + n): At most m + n moves (each step reduces row or column index).
     * <p>
     * Space Complexity:
     * - O(1).
     *
     * <p>
     * Output:
     * Returns true if target is found, otherwise false.
     */
    private static boolean searchTargetOptimal(int[][] matrix, int target) {

        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0;
        int col = n - 1;

        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;
            } else {
                row++;
            }
        }

        return false;
    }
}
