package recursion;

/*
79. Word Search
Given an m x n grid of characters board and a string word, return true if word exists in the grid.
The word can be constructed from letters of sequentially adjacent cells,
where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true

Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false

Constraints:
            m == board.length
            n = board[i].length
            1 <= m, n <= 6
            1 <= word.length <= 15
            board and word consist of only lowercase and uppercase English letters.

Follow up: Could you use search pruning to make your solution faster with a larger board?
*/
public class WordSearch {


    public static void main(String[] args) {

        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";
        String word1 = "ABFCD";
        String word2 = "SEE";
        System.out.println("Found " + word + " : " + exist(board, word));
        System.out.println("Found " + word1 + " : " + exist(board, word1));
        System.out.println("Found " + word2 + " : " + exist(board, word2));
    }

    /**
     * Word Search (Backtracking on 2D Grid)
     * What it does:
     * - Determines if a target word exists within an m x n grid of characters.
     * - The word must be formed by sequentially adjacent cells (Up, Down, Left, or Right).
     * - A single cell (letter) cannot be used more than once in a single word path.
     * Core Intuition:
     * - We treat the grid as a map. Every cell is a potential starting point.
     * - We use Depth First Search (DFS) to dive deep into a path as long as the letters match.
     * - If we hit a dead end (letters don't match or we hit boundaries), we "backtrack" and try a different direction.
     * Why this is a Backtracking problem:
     * - We make a choice (moving to an adjacent cell).
     * - If that choice doesn't lead to the solution, we "undo" our visit by restoring the character.
     * - This allows the same cell to be used by a different starting path later.
     * Step-by-Step Explanation:
     * 1. Initial Search (`exist` method):
     * - We loop through every row `i` and column `j` in the grid.
     * - We only trigger the recursive DFS if the current cell matches the first letter of our target word.
     * * 2. Recursive Exploration (`dfs` method):
     * - Check Boundaries & Match: If we go off the grid or the character at `board[r][c]` doesn't match `word.charAt(index)`, return `false`.
     * - Base Case: If `index` is equal to the last character of the word, we've found the whole word! Return `true`.
     * - Marking as Visited: To avoid using the same cell twice, we store the current character in a `temp` variable and replace the cell with a placeholder (like `#`).
     * - Explore 4 Directions: We recursively call `dfs` for Down, Up, Right, and Left. We use the OR (`||`) operator because we only need one of these paths to be successful.
     * - Backtrack: Once the search in all directions is done, we put the `temp` character back into the cell so other paths can use it.
     * Example:
     * Input: Word = "SEE", Board =
     * [A, B, C]
     * [S, E, D]
     * [A, E, E]
     * 1. Find 'S' at (1,0). Start DFS.
     * 2. Mark (1,0) as '#'. Look for 'E'.
     * 3. Found 'E' at (1,1). Mark as '#'. Look for 'E'.
     * 4. Found 'E' at (2,1). Index matches word length. Success!
     * Time Complexity:
     * - O(N * M * 4^L)
     * - N*M is the number of cells. For each cell, we explore 4 directions up to the length of the word (L).
     * Space Complexity:
     * - O(L)
     * - The maximum depth of the recursion stack is the length of the word.
     * * Interview Insight:
     * - This is a classic "Matrix DFS" problem.
     * - The "In-place" marking (using '#') is a clever way to save space because it avoids using an extra `boolean[][] visited` array.
     */
    private static boolean dfs(char[][] board, int r, int c, int index, String word) {
        // Boundary & Match Check
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != word.charAt(index)) {
            return false;
        }

        // Found the whole word
        if (index == word.length() - 1) return true;

        // Mark the cell
        char temp = board[r][c];
        board[r][c] = '#';

        // Explore all 4 directions
        boolean found = dfs(board, r + 1, c, index + 1, word) || // Down
                dfs(board, r - 1, c, index + 1, word) || // Up
                dfs(board, r, c + 1, index + 1, word) || // Right
                dfs(board, r, c - 1, index + 1, word);   // Left

        // Backtrack (Restore the cell)
        board[r][c] = temp;
        return found;
    }

    private static boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, i, j, 0, word)) return true;
                }
            }
        }
        return false;
    }
}
