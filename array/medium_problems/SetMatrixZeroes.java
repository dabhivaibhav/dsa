package array.medium_problems;


import java.util.Arrays;

/*
Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0. You must do it in place.

Examples:
Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
Output: [[1,0,1],[0,0,0],[1,0,1]]
Explanation: Element at position (1,1) is 0, so set entire row 1 and column 1 to 0.

Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
Explanation: There are two zeroes: (0,0) and (0,3). Row 0 → all elements become 0. Column 0 and column 3 → all elements become 0

Constraints:
            m == matrix.length
            n == matrix[0].length
            1 <= m, n <= 200
            -2^31 <= matrix[i][j] <= 2^31 - 1
 */
public class SetMatrixZeroes {

    public static void main(String[] args) {

        int[][] matrix = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int[][] matrix1 = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        int[][] matrix2 = {{1, 1, 1, 1}, {1, 0, 1, 1}, {1, 1, 0, 1}, {1, 0, 0, 1}};

        setMatrixZeroes(matrix);
        setMatrixZeroes(matrix1);
        setMatrixZeroes(matrix2);
    }

    /*
    Approach 1: Using Extra Space (setMatrixZeroes method)
    Step-by-step explanation:
        Check for empty matrix:
            If the matrix is empty or has no rows/columns, just return.
        Create helper arrays:
        Make two boolean arrays:
            zeroRow to remember which rows need to be set to zero
            zeroCol to remember which columns need to be set to zero
        Find zeroes:
            Go through every element in the matrix.
            If you find a zero, mark its row and column in zeroRow and zeroCol.
        Set rows and columns to zero:
            Go through the matrix again.
            If the current row or column is marked, set that element to zero.
        Print the result:
            Print each row of the matrix.
        Time Complexity:
            O(m * n) — You go through the matrix twice.
        Space Complexity:
            O(m + n) — You use two extra arrays for rows and columns.
     */
    private static void setMatrixZeroes(int[][] matrix) {

        //Checks if the matrix is empty. If yes, do nothing.
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        //Get the number of rows and columns.
        //Create two arrays to remember which rows and columns need to be zeroed.
        int row = matrix.length;
        int col = matrix[0].length;
        boolean[] zeroRow = new boolean[row];
        boolean[] zeroCol = new boolean[col];

        //Go through every element.
        //If you find a zero, mark its row and column.
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    zeroRow[i] = true;
                    zeroCol[j] = true;
                }
            }
        }

        //Go through every element again.
        //If its row or column is marked, set it to zero.
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (zeroRow[i] || zeroCol[j]) {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int[] num : matrix) {
            System.out.println(Arrays.toString(num));
        }
    }

    /*
    Approach 2: Optimal Space (setMatrixZeroesOptimalApproach method)
    Step-by-step explanation:
        Use first row and column as markers:
        Instead of extra arrays, use the first row and first column of the matrix to remember which rows/columns need to be zeroed.
        Special variable for first column:
        Use col0 to remember if the first column itself needs to be zeroed.
     Mark zeroes:
        Go through the matrix.
        If you find a zero, mark its row by setting the first element of that row to zero.
        Mark its column by setting the first element of that column to zero.
        If the zero is in the first column, set col0 to zero.
     Set zeroes except first row/column:
        Go through the matrix (except first row and column).
        If the marker in the first row or column is zero, set that element to zero.
        Set first row and column if needed:
        If the first row or column was marked, set all their elements to zero.
    Time Complexity:
    O(m * n) — You go through the matrix a few times.
    Space Complexity:
    O(1) — No extra arrays, just a few variables.
         */
    private static void setMatrixZeroesOptimalApproach(int[][] matrix) {
        // int row = matrix[0][..]
        // int col = matrix[..][j]
        // col0 keeps track if the first column should be set to zero at the end
        int col0 = 1;
        // First pass: mark rows and columns that need to be zeroed
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                // If we find a zero, we need to mark its row and column
                if (matrix[i][j] == 0) {
                    // Mark the row by setting its first element to zero
                    matrix[i][0] = 0;
                    if (j != 0) {
                        // Mark the column by setting its first element to zero
                        matrix[0][j] = 0;
                    } else {
                        // If the zero is in the first column, remember to zero the whole first column later
                        col0 = 0;
                    }
                }
            }
        }

        // Second pass: set cells to zero except for the first row and first column
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                // If the row or column was marked, set this cell to zero
                if (matrix[0][j] == 0 || matrix[i][0] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Finally, handle the first row and first column if they need to be zeroed
        // Check if the first cell is zero, which means the first row needs to be zero
        if (matrix[0][0] == 0) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

        // Check if the first cell is zero, which means the first column needs to be zero
        if (col0 == 0) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
    }
}
