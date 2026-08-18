package graph.medium_problems;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
Leetcode 130: Surrounded Regions

You are given an m x n matrix board containing letters 'X' and 'O', capture regions that are surrounded:

Connect: A cell is connected to adjacent cells horizontally or vertically.
Region: To form a region connect every 'O' cell.
Surround: A region is surrounded if none of the 'O' cells in that region are on the edge of the board.
Such regions are completely enclosed by 'X' cells.
To capture a surrounded region, replace all 'O's with 'X's in-place within the original board.
You do not need to return anything.



Example 1:
Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
Explanation: In the above diagram, the bottom region is not captured because it is on the
edge of the board and cannot be surrounded.

Example 2:
Input: board = [["X"]]
Output: [["X"]]

Constraints:
            m == board.length
            n == board[i].length
            1 <= m, n <= 200
            board[i][j] is 'X' or 'O'.
 */
public class SurroundedRegions {
    public static void main(String[] args) {

        char[][] grid = {{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}};
        markRegion(grid);
        System.out.println(Arrays.deepToString(grid));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Captures all regions of 'O' that are completely surrounded by 'X'. A region is
     * surrounded if it has NO path to any border cell. Uses the INVERTED approach: instead
     * of finding surrounded regions (hard to prove), find UNsurrounded regions (easy to
     * find by starting from the border) and capture everything else. Three-step algorithm:
     * mark border-connected O's as safe, BFS inward, final scan to capture and restore.
     * O(R x C) time, O(R x C) space.
     *
     * THE SENTENCE: flip the question. Instead of "which O's are surrounded?" ask "which
     * O's are connected to the border?" BFS from border O's marks the safe ones. Everything
     * else is surrounded.
     *
     * ---
     *
     * THE "INVERT THE QUESTION" PATTERN (SURROUNDED REGIONS)
     *
     * HOW I DERIVED THE APPROACH (the thinking path, not just the answer):
     *
     * Step 1 - UNDERSTAND THE RULE: an O survives if and only if it is on the border OR
     *   connected through a chain of O's to a border O. An O that has no such chain is
     *   completely surrounded by X and gets captured.
     *   KEY INSIGHT: it is not just about the cell's own position. It is about its
     *   CONNECTION to the border. An interior O connected to a border O through other O's
     *   is just as safe as the border O itself.
     *
     * Step 2 - TWO WAYS TO ASK THE QUESTION:
     *   Way A (direct): for every O, check if its connected region touches any border.
     *     Problem: proving "no path to any border exists" requires exploring the entire
     *     region. Multiple regions means multiple traversals. Hard bookkeeping.
     *   Way B (inverted): find all border O's. From each, BFS/DFS inward marking every
     *     connected O as safe. One pass marks all safe O's. Then one scan captures the rest.
     *     Easier because "connected to border" is a REACHABILITY question, and BFS/DFS
     *     answers reachability in one pass.
     *   THE INVERSION: instead of proving negatives (not connected to border), prove
     *   positives (IS connected to border) and capture everything not proven.
     *
     * Step 3 - RECOGNIZE THE PATTERN: multiple starting points (border O's), all spreading
     *   inward simultaneously. That is multi-source BFS, the same pattern as rotting oranges
     *   and 01 matrix. The sources are border O's instead of rotten oranges or 0-cells.
     *
     * ---
     *
     * THE THREE-STEP ALGORITHM:
     *
     * STEP 1 - BORDER SCAN: walk the entire grid. For each cell on the border (first row,
     *   last row, first column, last column), if it is 'O', mark it 'S' (safe) and enqueue
     *   it. 'S' is a temporary marker meaning "this O is connected to the border and must
     *   survive." Why not mark it 'X'? Because then in the final scan we could not tell
     *   safe O's apart from original X's. The temporary third value is what keeps them
     *   distinguishable.
     *
     * STEP 2 - MULTI-SOURCE BFS: process the queue. For each polled cell, check its 4
     *   neighbors. If a neighbor is in bounds and is 'O', mark it 'S' and enqueue it. BFS
     *   spreads inward from the border, marking every O reachable from any border O as safe.
     *   When the queue empties, every border-connected O is 'S'. Every remaining 'O' is
     *   surrounded.
     *
     * STEP 3 - FINAL SCAN: walk the entire grid one more time. Three rules:
     *   'O' -> change to 'X'. This O was never reached from any border. It is surrounded.
     *   'S' -> change back to 'O'. This was a safe O. Restore it.
     *   'X' -> leave as 'X'. It was always X.
     *
     * ---
     *
     * THE GEAR CHECK:
     * Gear 1 (what is the graph): grid, undirected, unweighted. 4-directional neighbors.
     * Gear 2 (what shape is the question): reachability. "Which O's can reach the border?"
     *   Not shortest path, not counting, not cycle detection. Just reachability.
     * Gear 3 (what tool): BFS or DFS, either works. Just reachability, no shortest path
     *   needed, no level counting needed.
     * Gear 4 (what is special): multi-source BFS from border O's only. The inversion:
     *   start from the answer (border = safe) and spread inward, instead of starting from
     *   the question (each interior O, is it surrounded?).
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Temporary marker 'S' instead of a separate visited array:
     *    - Why? The grid itself tracks three states: 'X' (wall), 'O' (unchecked), 'S'
     *      (proven safe). The BFS check `== 'O'` serves as the visited check: an 'S' cell
     *      fails it and cannot be enqueued again. Same mutation-as-visited trick as flood
     *      fill and rotting oranges. No boolean[][] needed.
     *    - Why not mark safe O's as 'X' directly? Then the final scan cannot distinguish
     *      "originally X" from "safe O we already processed." The temporary third value
     *      is what makes the final scan possible.
     *
     * 2. Border scan uses the full double loop with a border check, not four separate loops:
     *    - Why? Simpler to write, impossible to miss corners, easy to read. The condition
     *      i == 0 || i == rows-1 || j == 0 || j == cols-1 says exactly what it means. The
     *      cost of checking interior cells (one boolean comparison, then skip) is negligible.
     *
     * 3. No size snapshot in the BFS:
     *    - Why? This problem needs reachability only, not level counting. No minutes, no
     *      distance, no "how many steps." The size snapshot earns its place only when the
     *      answer depends on counting whole levels. Here it doesn't.
     *
     * 4. The inversion (start from border, not from interior):
     *    - Why? Proving "this O IS connected to the border" is one BFS from the border.
     *      Proving "this O is NOT connected to the border" requires exhaustively searching
     *      the entire region and verifying no cell touches a border. The positive is easier
     *      to prove than the negative. Capture everything not proven safe.
     *
     * ---
     *
     * MISTAKES I WORKED THROUGH:
     *
     * - INITIALLY INVERTED THE RULE: thought "O on the edge gets marked X." The actual
     *   rule is the opposite: edge O's SURVIVE, interior O's not connected to any edge O
     *   get captured. Reading the problem carefully and tracing the example fixed this
     *   before any code was written.
     *
     * - TRIED TO WRITE A CLEVER SINGLE LOOP FOR THE BORDER SCAN: attempted one for-loop
     *   with complex index math to visit only border cells. Hard to write, easy to miss
     *   corners, fragile. The simple double loop with a border-check condition was easier,
     *   correct on the first try, and costs negligible extra work.
     *
     * - DID NOT INITIALLY SEE THAT INTERIOR O's CONNECTED TO BORDER O's ARE ALSO SAFE:
     *   first understanding was "only border O's survive." The question "what if (2,1) is
     *   also O?" revealed the connection rule: safety spreads inward through chains of O's.
     *   That is exactly what BFS does, spread from the border inward along connected O's.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Scan the grid. For each border cell that is 'O': mark 'S', enqueue.
     * Step 2: BFS from all border O's. For each polled cell, check 4 neighbors. If
     *         neighbor is in bounds and 'O': mark 'S', enqueue.
     * Step 3: Final scan. 'O' -> 'X' (captured). 'S' -> 'O' (restored). 'X' -> 'X'.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Let R = rows, C = cols.
     *
     * TIME:
     *   Step 1 (border scan):    O(R x C). Full grid scan, O(1) per cell.
     *   Step 2 (BFS):            O(R x C). Each cell enqueued at most once, polled once,
     *                            4 neighbor checks per poll. Total: 4 x R x C checks.
     *   Step 3 (final scan):     O(R x C). Full grid scan, O(1) per cell.
     *   TOTAL: O(R x C) + O(R x C) + O(R x C) = O(R x C).
     *
     * SPACE:
     *   Queue: O(R x C) worst case (every cell is a border O).
     *   Direction array: O(1).
     *   No separate visited array (grid mutation serves as visited).
     *   TOTAL: O(R x C).
     *
     * ---
     *
     * THE GENERAL LESSON (carry this to future problems):
     *
     * When a problem asks "find everything that does NOT have property X," consider
     * inverting: find everything that DOES have property X, then everything else is your
     * answer. Proving positives (connected to border) is often one BFS. Proving negatives
     * (not connected to border) requires exhaustive verification per region.
     *
     * This inversion pattern appears beyond this problem:
     * - "Find all cells NOT reachable from X" -> BFS from X, capture the unreached.
     * - "Find all nodes NOT in any cycle" -> find all nodes IN cycles, exclude them.
     * - Any time "not X" is harder to check than "X," invert.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "invert the question" in the first sentence. Start from the border, not from
     *   the interior. This shows you see the structural insight, not just the BFS mechanics.
     * - The three-step structure (border scan, BFS inward, final scan) is clean and easy
     *   to explain. Name the three steps before coding.
     * - The temporary marker 'S' avoids a separate visited array AND makes the final scan
     *   possible. Know why a direct mark to 'X' would break the final scan.
     * - This is multi-source BFS, same family as rotting oranges and 01 matrix. The sources
     *   are border O's instead of rotten oranges or 0-cells. Naming the family shows
     *   pattern recognition.
     * - No size snapshot needed (reachability, not level counting). Know when the snapshot
     *   earns its place and when it doesn't.
     */
    private static void markRegion(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    // I am on the border
                    if (board[i][j] == 'O') {
                        board[i][j] = 'S';
                        queue.offer(new int[]{i, j});
                    }
                }
            }
        }

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!queue.isEmpty()) {

            int[] cur = queue.poll();

            for (int[] dir : dirs) {
                int newRow = cur[0] + dir[0];
                int newCol = cur[1] + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && board[newRow][newCol] == 'O') {
                    board[newRow][newCol] = 'S';
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'S') {
                    board[i][j] = 'O';
                }
            }
        }

    }
}
