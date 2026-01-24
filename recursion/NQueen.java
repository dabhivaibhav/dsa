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

        result.clear();
        board = new String[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], ".");
        }
        int[] leftRow = new int[n];
        int[] lowerDiagonal = new int[2 * n - 1];
        int[] upperDiagonal = new int[2 * n - 1];
        nQueenRecursionOptimized(0, board, n, leftRow, upperDiagonal, lowerDiagonal, result);
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


    /**
     * nQueenRecursionOptimized (N-Queens with Hashing)
     * <p>
     * What it does:
     * This is an optimized version of the N-Queens solver. Instead of scanning the
     * board manually to check if a square is safe, it uses three lookup arrays
     * (hashing) to check for queen attacks in constant time O(1).
     * Core Intuition:
     * We process the board column by column (from left to right).
     * A queen at (row, col) attacks:
     * Its horizontal row.
     * Its lower diagonal (bottom-left to top-right).
     * Its upper diagonal (top-left to bottom-right).
     * We use three boolean-like arrays (0 for empty, 1 for occupied) to keep
     * track of these "attack zones" instantly.
     * <p>
     * Why this is better:
     * In the brute force version, the 'isSafe' function takes O(N) time to scan.
     * Here, we check three array indices, which takes O(1) time. This significantly
     * speeds up the search.
     * <p>
     * The Hashing Logic:
     * leftRow: Size N. Tracks which rows already have a queen.
     * lowerDiagonal: Size 2N-1. The formula (row + col) uniquely identifies
     * every diagonal that slopes from bottom-left to top-right.
     * upperDiagonal: Size 2N-1. The formula (n - 1 + col - row) uniquely
     * identifies every diagonal that slopes from top-left to bottom-right.
     * <p>
     * Step-by-step explanation:
     * col: The current column index we are trying to fill.
     * board: The 2D grid where we place "Q" or ".".
     * leftRow / upperDiagonal / lowerDiagonal: The lookup arrays used for safety checks.
     * <p>
     * Base Case:
     * If col == n, we have successfully placed a queen in every column.
     * The current board is a valid solution. We copy it and add it to the result list.
     * <p>
     * Recursive Case:
     * For the current column, try every row from 0 to n-1.
     * Check if the row and both diagonals are free (all must be 0).
     * <p>
     * If Safe:
     * Mark: Place "Q" and set the corresponding indices in the three arrays to 1.
     * Recursion: Call nQueenRecursionOptimized for col + 1.
     * Backtrack: Reset "Q" to "." and set the array indices back to 0.
     * Example (N=4):
     * To check if (row 2, col 1) is safe:
     * Check leftRow[2]
     * Check lowerDiagonal[2 + 1] = lowerDiagonal[3]
     * Check upperDiagonal[4 - 1 + 1 - 2] = upperDiagonal[2]
     * If all are 0, we can place a queen!
     * <p>
     * Complexity:
     * Time: O(N!) - Still the same number of states to explore, but each state is processed faster.
     * Space: O(N) - For the lookup arrays and the recursion stack. (Excluding the board itself).
     */
    private static void nQueenRecursionOptimized(int col, String[][] board, int n,
                                                 int[] leftRow, int[] upperDiagonal, int[] lowerDiagonal,
                                                 List<List<String>> res) {
        if (col == n) {
            List<String> temp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                temp.add(String.join("", board[i]));
            }
            res.add(temp);
            return;
        }

        for (int row = 0; row < n; row++) {
            if (leftRow[row] == 0 && lowerDiagonal[row + col] == 0 &&
                    upperDiagonal[n - 1 + col - row] == 0) {

                board[row][col] = "Q";
                leftRow[row] = 1;
                lowerDiagonal[row + col] = 1;
                upperDiagonal[n - 1 + col - row] = 1;
                nQueenRecursionOptimized(col + 1, board, n, leftRow, upperDiagonal, lowerDiagonal, res);

                board[row][col] = ".";
                leftRow[row] = 0;
                lowerDiagonal[row + col] = 0;
                upperDiagonal[n - 1 + col - row] = 0;
            }
        }
    }
}
