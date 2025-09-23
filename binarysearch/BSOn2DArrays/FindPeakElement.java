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


}
