package array.medium_problems;


import java.util.ArrayList;

/*
Print the matrix in spiral manner

Given an M * N matrix, print the elements in a clockwise spiral manner. Return an array with the elements in the order
of their appearance when printed in a spiral manner.


Examples:
Input: matrix = [[1, 2, 3], [4 ,5 ,6], [7, 8, 9]]
Output: [1, 2, 3, 6, 9, 8, 7, 4, 5]
Explanation: The elements in the spiral order are 1, 2, 3 -> 6, 9 -> 8, 7 -> 4, 5

Input: matrix = [[1, 2, 3, 4], [5, 6, 7, 8]]
Output: [1, 2, 3, 4, 8, 7, 6, 5]
Explanation: The elements in the spiral order are 1, 2, 3, 4 -> 8, 7, 6, 5

Constraints:
            m == matrix.length
            n == matrix[i].length
            1 <= m, n <= 100
            -100 <= matrix[i][j] <= 100
 */

/**
 * This class implements a solution to print a matrix in spiral order.
 * The approach uses four pointers (top, bottom, left, right) to track boundaries
 * and prints elements in clockwise direction:
 * 1. Left to right (top row)
 * 2. Top to bottom (rightmost column)
 * 3. Right to left (bottom row)
 * 4. Bottom to top (leftmost column)
 * <p>
 * Time Complexity: O(m*n) where m and n are dimensions of matrix
 * Space Complexity: O(1) excluding the space needed for output
 */
public class PrintMatrixSpiral {

    public static void main(String[] args) {

        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}};
        int[][] matrix1 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};


        printMatrixSpiral(matrix);
    }


    /**
     * Prints the elements of a matrix in spiral order.
     * Uses four pointers to track boundaries and prints elements layer by layer
     * moving inward until all elements are processed.
     *
     * @param matrix input 2D array to be printed in spiral order
     */
    private static void printMatrixSpiral(int[][] matrix) {

// Initialize matrix dimensions
        int n = matrix.length;        // number of rows in the matrix
        int m = matrix[0].length;     // number of columns in the matrix (assumes at least 1 row)

// Initialize boundary pointers
        int top = 0;                  // the index of the current topmost row that hasn't been traversed
        int bottom = n - 1;           // the index of the current bottommost row that hasn't been traversed
        int left = 0;                 // the index of the current leftmost column that hasn't been traversed
        int right = m - 1;            // the index of the current rightmost column that hasn't been traversed

// Prepare the output container
// Pre-sizing to n*m avoids internal ArrayList resizes as we add elements
        ArrayList<Integer> result = new ArrayList<>(n * m);

// Keep spiraling while there is still a valid rectangular layer remaining
        while (top <= bottom && left <= right) {

            // 1) Traverse the TOP row from LEFT → RIGHT (fixed row = top, varying column = left..right)
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);   // collect elements across the current top boundary
            }
            top++; // we just consumed the 'top' row, so move the top boundary down by one

            // 2) Traverse the RIGHT column from TOP → BOTTOM (fixed col = right, varying row = top..bottom)
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]); // collect elements down the current right boundary
            }
            right--; // we just consumed the 'right' column, so move the right boundary left by one

            // 3) Traverse the BOTTOM row from RIGHT → LEFT (fixed row = bottom, varying column = right..left)
            // Only if there is still an untraversed row after moving 'top' (prevents double-counting in single-row leftovers)
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]); // collect elements across the current bottom boundary (right to left)
                }
                bottom--; // we consumed the 'bottom' row, so move the bottom boundary up by one
            }

            // 4) Traverse the LEFT column from BOTTOM → TOP (fixed col = left, varying row = bottom..top)
            // Only if there is still an untraversed column after moving 'right' (prevents double-counting in single-column leftovers)
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]); // collect elements up the current left boundary
                }
                left++; // we consumed the 'left' column, so move the left boundary right by one
            }
        }
        // 'result' now contains the matrix elements in spiral order
        System.out.println(result);
    }
}
