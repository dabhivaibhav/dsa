package binarysearch.BSOn2DArrays;

import java.util.Arrays;

/*
Leetcode: 1901. Find a Peak Element II
A peak element in a 2D grid is an element that is strictly greater than all of its adjacent neighbors to the left, right, top, and bottom.
Given a 0-indexed m x n matrix mat where no two adjacent cells are equal, find any peak element mat[i][j] and return the length 2 array [i,j].
You may assume that the entire matrix is surrounded by an outer perimeter with the value -1 in each cell.
You must write an algorithm that runs in O(m log(n)) or O(n log(m)) time.

Example 1:
Input: mat = [[1,4],[3,2]]
Output: [0,1]
Explanation: Both 3 and 4 are peak elements so [1,0] and [0,1] are both acceptable answers.

Example 2:
Input: mat = [[10,20,15],[21,30,14],[7,16,32]]
Output: [1,1]
Explanation: Both 30 and 32 are peak elements so [1,1] and [2,2] are both acceptable answers.

Constraints:
            m == mat.length
            n == mat[i].length
            1 <= m, n <= 500
            1 <= mat[i][j] <= 10^5
            No two adjacent cells are equal.
 */
public class FindPeakElement {

    public static void main(String[] args) {
        int[][] mat = {
                {1, 4},
                {3, 2}};

        int[][] mat1 = {
                {10, 20, 15},
                {21, 30, 14},
                {7, 16, 32}};

        System.out.println(Arrays.toString(findPeakElementBruteForce(mat)));
        System.out.println(Arrays.toString(findPeakElementBruteForce(mat1)));
        System.out.println(Arrays.toString(findPeakElementOptimal(mat)));
        System.out.println(Arrays.toString(findPeakElementOptimal(mat1)));

    }

    /**
     * What it does:
     * Finds any peak element in a 2D matrix by checking each cell against its
     * four orthogonal neighbors (up, down, left, right). A peak is defined as
     * a cell strictly greater than all valid neighbors. If multiple peaks exist,
     * the first one found is returned.
     *
     * <p>
     * Why it works:
     * - The brute-force approach directly applies the problem's definition of a peak.
     * - Every element is inspected and compared with up to four neighbors.
     * - Border cells (first/last row or column) are handled by treating
     * missing neighbors as -∞, ensuring they can still be peaks.
     * - As soon as a valid peak is found, the method returns its position.
     *
     * <p>
     * How it works:
     * - Initialize an answer array [row, col].
     * - Loop through each row (i) and column (j):
     * 1. Assign neighbor values:
     * - top    = mat[i-1][j] if i > 0, else -∞
     * - bottom = mat[i+1][j] if i < m-1, else -∞
     * - left   = mat[i][j-1] if j > 0, else -∞
     * - right  = mat[i][j+1] if j < n-1, else -∞
     * 2. Check if mat[i][j] > top, bottom, left, and right.
     * 3. If true, return [i, j] as the peak coordinates.
     * - If no peak is found (should not happen under problem constraints),
     * return [-1, -1].
     *
     * <p>
     * Example:
     * mat = [
     * [10, 20, 15],
     * [21, 30, 14],
     * [ 7, 16, 32]
     * ]
     * - At (1,1)=30: top=20, bottom=16, left=21, right=14
     * - 30 > all four neighbors → return [1,1].
     *
     * <p>
     * Time Complexity:
     * - O(m · n): every cell is checked once and compared to up to 4 neighbors.
     * <p>
     * Space Complexity:
     * - O(1): uses only a fixed-size result array.
     *
     * <p>
     * Output:
     * Returns an array of size 2: [rowIndex, colIndex] of a peak element,
     * or [-1, -1] if no peak is found.
     */

    private static int[] findPeakElementBruteForce(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0)
            return new int[]{-1, -1};

        int m = mat.length, n = mat[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = mat[i][j];
                int top = (i == 0) ? Integer.MIN_VALUE : mat[i - 1][j];
                int bottom = (i == m - 1) ? Integer.MIN_VALUE : mat[i + 1][j];
                int left = (j == 0) ? Integer.MIN_VALUE : mat[i][j - 1];
                int right = (j == n - 1) ? Integer.MIN_VALUE : mat[i][j + 1];

                if (val > top && val > bottom && val > left && val > right) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }


    /**
     * What it does:
     * Finds a peak element in a 2D matrix using an optimized binary search
     * strategy on the columns. A peak is defined as an element strictly greater
     * than its valid four neighbors (up, down, left, right). If multiple peaks
     * exist, any one of them can be returned.
     *
     * <p>
     * Why it works:
     * - The brute force O(m·n) approach checks every element against neighbors.
     * - A faster approach is to apply binary search on columns:
     * 1. Pick the middle column.
     * 2. Find the maximum element in that column (guaranteed candidate for peak).
     * 3. Compare it with its immediate left and right neighbors:
     * - If it is greater than both → it's a peak.
     * - If the left neighbor is larger → a peak must exist in the left half.
     * - If the right neighbor is larger → a peak must exist in the right half.
     * - This works because if a neighbor is larger, the "slope" leads to a peak
     * in that direction.
     *
     * <p>
     * How it works:
     * - Guard against null or empty input.
     * - Initialize search range as all columns: low = 0, high = cols - 1.
     * - While low <= high:
     * 1. mid = (low + high) / 2
     * 2. Use `findMaxElement` to find the row index of the maximum element in column mid.
     * 3. Compare this value against its left and right neighbors:
     * - If strictly greater than both, return [row, mid].
     * - If left neighbor is greater, discard right half (high = mid - 1).
     * - Else, discard left half (low = mid + 1).
     * - If no peak is found (should not happen under problem constraints), return [-1, -1].
     *
     * <p>
     * Example:
     * mat = [
     * [10, 20, 15],
     * [21, 30, 14],
     * [ 7, 16, 32]
     * ]
     * - Middle column = 1 → column = [20, 30, 16], max at row 1 (value 30).
     * - Neighbors: left = 21, right = 14.
     * - 30 > 21 and 30 > 14 → return [1, 1].
     *
     * <p>
     * Time Complexity:
     * - O(rows · log cols):
     * - Each binary search step selects one column (log cols steps).
     * - Finding the maximum in a column costs O(rows).
     * <p>
     * Space Complexity:
     * - O(1): constant extra space.
     *
     * <p>
     * Output:
     * Returns an array [rowIndex, colIndex] of a peak element,
     * or [-1, -1] if none is found (though problem guarantees a peak exists).
     */
    private static int[] findPeakElementOptimal(int[][] mat) {

        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) {
            return new int[]{-1, -1};
        }
        int columns = mat[0].length;

        int low = 0;
        int high = columns - 1;

        // Perform binary search on columns
        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Find the index of the row with the maximum element
            // in the middle column
            int row = findMaxElement(mat, mid);
            int val = mat[row][mid];
            // Determine left and right neighbors of middle element
            int left = mid - 1 >= 0 ? mat[row][mid - 1] : Integer.MIN_VALUE;
            int right = mid + 1 < columns ? mat[row][mid + 1] : Integer.MIN_VALUE;

            // Check if the middle element is a peak
            if (val > left && val > right) {
                return new int[]{row, mid};
            } else if (left > val) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        // Return [-1, -1] if no peak element is found
        return new int[]{-1, -1};
    }

    /**
     * Helper: findMaxElement
     * - Finds the row index of the maximum element in the given column.
     *
     * <p>
     * Example:
     * mat = [
     * [10, 20, 15],
     * [21, 30, 14],
     * [ 7, 16, 32]
     * ], mid = 1
     * - Column = [20, 30, 16] → maximum = 30 at row 1 → return 1.
     *
     * <p>
     * Time Complexity: O(rows), since each element in the column is checked once.
     * Space Complexity: O(1).
     */
    private static int findMaxElement(int[][] mat, int mid) {
        int rows = mat.length;
        int maxValue = Integer.MIN_VALUE;
        int index = Integer.MIN_VALUE;
        for (int i = 0; i < rows; i++) {
            if (mat[i][mid] > maxValue) {
                maxValue = mat[i][mid];
                index = i;
            }
        }
        return index;
    }

}
