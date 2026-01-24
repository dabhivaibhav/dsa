package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Leetcode: 51. N Queens

The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a
queen and an empty space, respectively.

Example 1:
Input: n = 4
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above

Example 2:
Input: n = 1
Output: [["Q"]]


Constraints:
            1 <= n <= 9
 */
public class NQueen {

    public static void main(String[] args) {

        int n = 4;
        List<List<String>> result = new ArrayList<>();
        String[][] board = new String[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], ".");
        }
        nQueenBruteForce(0, n, board, result);
        System.out.println(result);
    }


    /**
     * nQueenBruteForce (N-Queens Backtracking)
     * <p>
     * What it does:
     * Solves the classic N-Queens puzzle, where the goal is to place N queens on an NxN
     * chessboard such that no two queens threaten each other. This means no two queens
     * can share the same row, column, or diagonal.
     * <p>
     * Core Intuition:
     * - Since each queen must be in a unique row, we process the board row by row.
     * - For the current row, we try placing a queen in every column (0 to N-1).
     * - We only place a queen if that square is "safe" (not under attack from above).
     * - If we successfully place a queen, we move to the next row and repeat.
     * <p>
     * Why this is a backtracking problem:
     * - We make a choice (place a queen in a specific column).
     * - We explore all possibilities following that choice.
     * - If we reach a state where no column in a row is safe, we "backtrack" to the
     * previous row and move that queen to a different column to try again.
     * <p>
     * Step-by-step explanation:
     * - row: The current row index we are trying to place a queen in.
     * - board: A 2D array representing the chessboard ("Q" for Queen, "." for empty).
     * - result: A list to store all valid board configurations found.
     * <p>
     * Base Case:
     * - If row == n, it means we have reached the end of the board and successfully
     * placed all N queens. We save the current board state to our result list.
     * <p>
     * Recursive Case:
     * - Iterate from col = 0 to n-1.
     * - Call isSafe to check if board[row][col] is a valid move.
     * - If Safe:
     * 1. Place Queen: Set board[row][col] to "Q".
     * 2. Recursion: Call nQueenBruteForce for row + 1.
     * 3. Backtrack: Reset board[row][col] to "." so other paths can be explored.
     * <p>
     * How isSafe works:
     * Since we fill the board from top to bottom, we only check for threats from above:
     * 1. Vertical: Checks the same column in all rows above the current one.
     * 2. Upper-Left Diagonal: Checks cells diagonally up and to the left.
     * 3. Upper-Right Diagonal: Checks cells diagonally up and to the right.
     * <p>
     * Complexity:
     * - Time: O(N!) - The number of possibilities drops as you place more queens.
     * - Space: O(N^2) - For the 2D board and the recursion stack depth.
     * Interview Insight:
     * - This is the "gold standard" problem for learning backtracking.
     * - Optimization: You can use Bitmasking or boolean arrays to track attacked columns
     * and diagonals to make `isSafe` run in O(1) time instead of O(N).
     */
    private static void nQueenBruteForce(int row, int n, String[][] board, List<List<String>> result) {
        // Base Case: All queens are placed successfully
        if (row == n) {
            List<String> temp = new ArrayList<>();
            for (String[] row1 : board) {
                temp.add(String.join("", row1));
            }
            result.add(temp);
            return;
        }

        // Try placing a queen in each column of the current row
        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col, n)) {
                board[row][col] = "Q";          // Place Queen
                nQueenBruteForce(row + 1, n, board, result); // Move to next row
                board[row][col] = ".";          // Backtrack (Undo)
            }
        }
    }

    private static boolean isSafe(String[][] board, int row, int col, int n) {
        // Check column upwards
        for (int i = 0; i < row; i++) {
            if (board[i][col].equals("Q")) return false;
        }

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j].equals("Q")) return false;
        }

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j].equals("Q")) return false;
        }

        return true;
    }

}
