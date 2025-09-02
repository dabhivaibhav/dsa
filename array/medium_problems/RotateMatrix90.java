package array.medium_problems;


import java.util.Arrays;

/*
Rotate matrix by 90 degrees

Given an N * N 2D integer matrix, rotate the matrix by 90 degrees clockwise.



The rotation must be done in place, meaning the input 2D matrix must be modified directly.


Examples:
Input: matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
Output: matrix = [[7, 4, 1], [8, 5, 2], [9, 6, 3]]

Input: matrix = [[0, 1, 1, 2], [2, 0, 3, 1], [4, 5, 0, 5], [5, 6, 7, 0]]
Output: matrix = [[5, 4, 2, 0], [6, 5, 0, 1], [7, 0, 3, 1], [0, 5, 1, 2]]

Constraints:
            n == matrix.length.
            n == matrix[i].length.
            1 <= n <= 100.
            -104 <= matrix[i][j] <= 104
 */
public class RotateMatrix90 {

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix1 = {{0, 1, 1, 2}, {2, 0, 3, 1}, {4, 5, 0, 5}, {5, 6, 7, 0}};

        rotateMatrixBruteforceApproach(matrix);
        rotateMatrixOptimalApproach(matrix);
        rotateMatrixBruteforceApproach(matrix1);

    }

    /*
    Brute Force Approach:
    1. Create a new matrix of same size as input matrix
    2. Copy elements to new matrix in rotated position using formula:
       result[j][n-i-1] = matrix[i][j]
    3. Print the rotated matrix

    Time Complexity: O(N^2) - where N is the size of matrix
    Space Complexity: O(N^2) - using additional matrix for rotation
    */
    private static void rotateMatrixBruteforceApproach(int[][] matrix) {
        // Example: For matrix [[1,2,3],[4,5,6],[7,8,9]]
        int n = matrix.length;
        // Create new matrix to store rotated values
        int[][] result = new int[n][n];

        // Step 1: Copy elements to new matrix in rotated position
        // Using formula: result[j][n-i-1] = matrix[i][j]
        // This maps: 1->3, 2->6, 3->9, 4->2, 5->5, 6->8, 7->1, 8->4, 9->7
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[j][n - i - 1] = matrix[i][j];
            }
        }

        // Step 2: Print the rotated matrix
        // Final result: [[7,4,1],[8,5,2],[9,6,3]]
        System.out.println("Using Bruteforce approach 90 degrees rotation of the matrix is:");
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }

    /*
    Optimal Approach:
    1. First transpose the matrix (swap elements across diagonal)
    2. Then reverse each row of the matrix
    3. This gives us 90 degree clockwise rotation without using extra space

    Time Complexity: O(N^2) - where N is the size of matrix
    Space Complexity: O(1) - in-place rotation without extra space
    */
    private static void rotateMatrixOptimalApproach(int[][] matrix) {
        // Example: For matrix [[1,2,3],[4,5,6],[7,8,9]]
        int n = matrix.length;
        // Step 1: Transpose matrix - convert rows to columns
        // After this step: [[1,4,7],[2,5,8],[3,6,9]]
        for (int i = 0; i < n - 1; i++) {
            // Start from i+1 to avoid swapping elements twice
            for (int j = i + 1; j < n; j++) {
                // Swap matrix[i][j] with matrix[j][i]
                // Example: Swap 2 with 4, then 3 with 7, then 6 with 8
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        System.out.println("Using optimal approach 90 degrees rotation of the matrix is:");
        // Step 2: Reverse each row
        // Final result: [[7,4,1],[8,5,2],[9,6,3]]
        for (int[] row : matrix) {
            // Only need to iterate to middle as we're swapping pairs
            for (int i = 0; i < row.length / 2; i++) {
                // Swap elements from start and end of row
                // Example: 1 with 7, 2 with 8, 3 with 9
                int temp = row[i];
                row[i] = row[n - 1 - i];
                row[n - 1 - i] = temp;
            }
            System.out.println(Arrays.toString(row));
        }
    }
}
