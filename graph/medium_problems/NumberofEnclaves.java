package graph.medium_problems;

import java.util.LinkedList;
import java.util.Queue;

/*
Leetcode 1020. Number of Enclaves

You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.

A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking
off the boundary of the grid. Return the number of land cells in grid for which we cannot walk off the
boundary of the grid in any number of moves.

Example 1:
Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
Output: 3
Explanation: There are three 1s that are enclosed by 0s, and one 1 that is not enclosed because its on the boundary.

Example 2:
Input: grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
Output: 0
Explanation: All 1s are either on the boundary or can reach the boundary.

Constraints:
            m == grid.length
            n == grid[i].length
            1 <= m, n <= 500
            grid[i][j] is either 0 or 1.
 */
public class NumberofEnclaves {

    public static void main(String[] args) {
        int[][] matrix = {{0, 0, 0, 0}, {1, 0, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        System.out.println(findNumEnclaves(matrix));

        int[][] matrix1 = {{0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}};
        System.out.println(findNumEnclaves(matrix1));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Counts the number of land cells (1's) from which you CANNOT walk off the grid's
     * boundary. Uses the inversion approach: instead of checking each 1-cell for an escape
     * route (hard), find all 1-cells that CAN escape (easy, BFS from border 1's) and count
     * whatever is left. O(R x C) time, O(R x C) space. This IS the optimal solution.
     *
     * THE SENTENCE: same inversion as Surrounded Regions. BFS from border 1's marks every
     * escapable land cell. Count the remaining unmarked 1's.
     *
     * ---
     *
     * THE "INVERT AND COUNT" PATTERN (NUMBER OF ENCLAVES)
     *
     * HOW I RECOGNIZED THE PATTERN:
     * "Same face as Surrounded Regions." Both problems ask about cells NOT connected to the
     * border. Surrounded Regions flips those cells. This one counts them. The traversal is
     * identical: multi-source BFS from border cells of interest, spreading inward, marking
     * everything reachable. Only Phase 3 (the answer logic) differs:
     *   Surrounded Regions: S -> O (restore), O -> X (capture).
     *   Number of Enclaves: count remaining 1's. No restoration needed.
     * The engine (BFS) is the same. The answer wrapping changed. That is the Phase 2 vs
     * Phase 3 distinction from my graph skeleton: the traversal is the engine, the answer
     * logic wraps around it.
     *
     * ---
     *
     * THE THREE STEPS:
     *
     * STEP 1 - BORDER SCAN: scan the entire grid. For each cell on the border (i == 0,
     *   i == rows-1, j == 0, j == cols-1), if it is 1, mark it 2 and enqueue it. The mark
     *   2 means "this land cell can reach the border, it is escapable." 2 serves as the
     *   visited marker because the == 1 check fails on 2.
     *
     * STEP 2 - MULTI-SOURCE BFS: process the queue. For each polled cell, check 4
     *   neighbors. If a neighbor is in bounds and is 1, mark it 2 and enqueue it. BFS
     *   spreads inward from the border, marking every land cell reachable from any border
     *   land cell. When the queue empties, every escapable cell is marked 2.
     *
     * STEP 3 - COUNT: scan the entire grid. Count every cell that is still 1. These are
     *   the land cells that BFS never reached, meaning they have no path to any border.
     *   They are enclaved. Return the count.
     *
     * ---
     *
     * THE GEAR CHECK:
     * Gear 1: grid, undirected, unweighted. 4-directional neighbors.
     * Gear 2: counting. "How many land cells cannot walk off the boundary?"
     * Gear 3: BFS or DFS, either works. Reachability only, no shortest path needed.
     * Gear 4: multi-source BFS from border 1's. The inversion: mark the escapable cells,
     *   count the rest.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Mark with 2, not a separate visited array:
     *    - Why? The grid is int[][]. 1 means land, 0 means water. Setting reached land
     *      to 2 makes it fail the == 1 check (visited), keeps it distinguishable from 0
     *      (water), and requires no extra memory. Same mutation-as-visited trick as every
     *      grid BFS in my library.
     *
     * 2. No restoration step (contrast with Surrounded Regions):
     *    - Why? The problem asks for a COUNT, not a modified grid. The grid's final state
     *      does not matter. In Surrounded Regions the output WAS the modified grid, so
     *      S -> O restoration was mandatory. Here the output is an integer, so the 2's
     *      can stay. One fewer pass over the grid.
     *
     * 3. No size snapshot:
     *    - Why? Reachability only. No level counting, no distance, no minutes. Same
     *      reasoning as Surrounded Regions.
     *
     * 4. This IS the optimal solution:
     *    - Why? The input is R x C cells. Any correct solution must read every cell at
     *      least once (a single unseen 1 changes the answer). So O(R x C) is the floor.
     *      This solution does three O(R x C) passes: border scan, BFS, count. It hits the
     *      floor. You cannot do better than reading your own input.
     *
     * ---
     *
     * WHAT TRANSFERRED FROM SURROUNDED REGIONS AND WHAT CHANGED:
     *
     * TRANSFERRED (identical):
     *   - The inversion insight: find the safe cells, answer about the rest.
     *   - Border scan with the i==0 || i==rows-1 || j==0 || j==cols-1 check.
     *   - Multi-source BFS from border cells, spreading inward.
     *   - Mutation as visited (char 'S' there, int 2 here, same purpose).
     *   - No size snapshot (reachability, not level counting).
     *
     * CHANGED:
     *   - Grid type: char[][] there, int[][] here. Marker is 2 instead of 'S'.
     *   - Phase 3: flip-and-restore there, count-remaining here.
     *   - No restoration pass needed (output is int, not modified grid).
     *
     * THE META-LESSON: when two problems have the "same face," the traversal engine (Phase
     * 2) transfers unchanged. Only Phase 1 (input specifics) and Phase 3 (answer logic)
     * adapt. Recognizing the shared engine is what makes the second problem take minutes
     * instead of hours.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Scan grid. Each border cell with value 1: mark 2, enqueue.
     * Step 2: BFS. Poll cell, check 4 neighbors. If in bounds and == 1: mark 2, enqueue.
     * Step 3: Scan grid. Count cells still == 1. Return count.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * TIME:
     *   Step 1 (border scan):    O(R x C). Full grid scan, O(1) per cell.
     *   Step 2 (BFS):            O(R x C). Each cell enqueued at most once, polled once,
     *                            4 neighbor checks per poll.
     *   Step 3 (count scan):     O(R x C). Full grid scan, O(1) per cell.
     *   TOTAL: O(R x C). Optimal, cannot do better than reading the input.
     *
     * SPACE:
     *   Queue: O(R x C) worst case (entire border is 1's, or entire grid is 1's connected
     *          to the border).
     *   Direction array: O(1).
     *   No separate visited array.
     *   TOTAL: O(R x C).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "same inversion as Surrounded Regions" if you've just solved that one. It
     *   shows pattern recognition and saves explanation time.
     * - The only difference is Phase 3: count instead of flip. Name it.
     * - This is already optimal. If asked "can you do better?", the answer is no, and the
     *   reason is the input-size floor: you must read every cell at least once.
     * - This was the first graph problem I solved without any guidance, no bugs, pattern
     *   recognized from the previous problem. That is the library paying dividends.
     */
    private static int findNumEnclaves(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        //Make the queue to store the 1's which are on the edge of the grid
        Queue<int[]> queue = new LinkedList<>();

        //Finding all the 1 on the edge of the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1) {
                    if (matrix[i][j] == 1) {
                        matrix[i][j] = 2;
                        queue.offer(new int[]{i, j});
                    }
                }
            }
        }

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();

            for (int[] dir : dirs) {
                int newRow = cell[0] + dir[0];
                int newCol = cell[1] + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && matrix[newRow][newCol] == 1) {
                    matrix[newRow][newCol] = 2;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }

        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1)
                    count++;
            }
        }

        return count;
    }
}
