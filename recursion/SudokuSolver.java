package recursion;

import java.util.Arrays;

/*
Leetcode 37. Sudoku Solver

Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
The '.' character indicates empty cells.



Example 1:
Input: board = [["5","3",".",".","7",".",".",".","."],
                ["6",".",".","1","9","5",".",".","."],
                [".","9","8",".",".",".",".","6","."],
                ["8",".",".",".","6",".",".",".","3"],
                ["4",".",".","8",".","3",".",".","1"],
                ["7",".",".",".","2",".",".",".","6"],
                [".","6",".",".",".",".","2","8","."],
                [".",".",".","4","1","9",".",".","5"],
                [".",".",".",".","8",".",".","7","9"]]

Output: [["5","3","4","6","7","8","9","1","2"],
         ["6","7","2","1","9","5","3","4","8"],
         ["1","9","8","3","4","2","5","6","7"],
         ["8","5","9","7","6","1","4","2","3"],
         ["4","2","6","8","5","3","7","9","1"],
         ["7","1","3","9","2","4","8","5","6"],
         ["9","6","1","5","3","7","2","8","4"],
         ["2","8","7","4","1","9","6","3","5"],
         ["3","4","5","2","8","6","1","7","9"]]
 */
public class SudokuSolver {

    public static void main(String[] args) {

        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

        sudokuSolver(board);
    }

    /**
     * sudokuSolver (Recursive Backtracking)
     * <p>
     * What it does:
     * Solves a standard 9x9 Sudoku puzzle. It fills empty cells (marked with '.')
     * with digits from '1' to '9' such that each digit appears exactly once in
     * each row, each column, and each of the nine 3x3 subgrids.
     * <p>
     * Core Intuition:
     * The algorithm searches for an empty cell.
     * Once found, it tries placing digits '1' through '9' one by one.
     * If a placement is valid, it recursively tries to solve the rest of the board.
     * If the board becomes impossible to solve with that digit, it "backtracks"
     * by resetting the cell and trying the next digit.
     * <p>
     * Why this is a backtracking problem:
     * Sudoku is a "Constraint Satisfaction Problem."
     * You make a choice, and if that choice leads to a violation of rules later
     * on, you must undo that choice and try another.
     * <p>
     * Step-by-step explanation:
     * Nested Loops: We scan the board row by row (i) and column by column (j).
     * Empty Cell Check: If board[i][j] == '.', we have found a "hole" to fill.
     * Trial Loop: We loop through characters '1' to '9'.
     * Validation: We call isValidPlacement to see if the move follows Sudoku rules.
     * Recursion: If valid, we move to the next empty cell.
     * Backtrack: If the recursion returns false, we reset the cell to '.' and
     * continue the loop to try the next number.
     * <p>
     * Base Case:
     * If the nested loops finish without finding any '.', it means the board is
     * completely full and valid. We return true.
     * <p>
     * Logic for the Helper Method (isValidPlacement):
     * The helper method checks three rules simultaneously in a single loop from 0 to 8 (k):
     * <p>
     * Row Check: board[row][k] == c checks if the digit exists anywhere in the current row.
     * <p>
     * Column Check: board[k][col] == c checks if the digit exists anywhere in the current column.
     * <p>
     * Subgrid Check: This is the most complex part.
     * <p>
     * Deep Dive: How the Subgrid Logic Works
     * To check a 3x3 subgrid, we first need to find the "Top-Left" corner of that subgrid.
     * Formula for start row: 3 * (row / 3)
     * Formula for start col: 3 * (col / 3)
     * (Example: If you are at row 4, 4/3 is 1. 1 * 3 = 3. The subgrid starts at row 3).
     * <p>
     * To iterate through all 9 cells of that subgrid using a single loop variable 'k':
     * <p>
     * Row offset: k / 3 (As k goes 0-8, this results in 0,0,0, 1,1,1, 2,2,2)
     * <p>
     * Col offset: k % 3 (As k goes 0-8, this results in 0,1,2, 0,1,2, 0,1,2)
     * <p>
     * Combining these:
     * board[3 * (row / 3) + k / 3][3 * (col / 3) + k % 3]
     * This single line allows us to check all 9 cells of the specific 3x3 square
     * belonging to the current row and column.
     * <p>
     * Complexity:
     * Time: O(9^(N^2)) - Where N is the number of empty cells. In practice,
     * pruning (isValidPlacement) makes it much faster.
     * Space: O(N^2) - For the recursion stack.
     */
    private static boolean sudokuSolver(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValidPlacement(board, i, j, c)) {

                            board[i][j] = c;
                            if (sudokuSolver(board)) return true;
                            else board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        for (char[] row : board) {
            System.out.println(Arrays.toString(row));
        }
        return true;
    }

    private static boolean isValidPlacement(char[][] board, int row, int col, char c) {

        for (int k = 0; k < 9; k++) {
            if (board[row][k] == c || board[k][col] == c) return false;
            // Rejects placement if value exists in subgrid
            if (board[3 * (row / 3) + k / 3][3 * (col / 3) + k % 3] == c) return false;
        }
        return true;
    }
}

//        static {
//            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//                try (java.io.FileWriter fw = new java.io.FileWriter("display_runtime.txt")) {
//                    fw.write("0");
//                } catch (Exception e) {
//                }
//            }));
//        }
