package graph.medium_problems;

import java.util.LinkedList;
import java.util.Queue;

/*
Leetcode 994. Rotting Oranges

You are given an m x n grid where each cell can have one of three values:
0 representing an empty cell,
1 representing a fresh orange, or
2 representing a rotten orange.
Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.

Example 1:
Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4

Example 2:
Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.

Example 3:
Input: grid = [[0,2]]
Output: 0
Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.


Constraints:
            m == grid.length
            n == grid[i].length
            1 <= m, n <= 10
            grid[i][j] is 0, 1, or 2.
 */
public class RottingOranges {

    public static void main(String[] args) {
        int[][] oranges = {{2, 1, 1}, {0, 1, 1}, {0, 1, 1}};
        System.out.println(orangesRotting(oranges));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Finds the minimum number of minutes until all fresh oranges rot, or returns -1 if
     * impossible. Uses MULTI-SOURCE BFS: all initially-rotten oranges start in the queue
     * together, and the BFS expands level by level where each level = one minute. The grid
     * itself serves as both the graph and the visited array.
     *
     * THE SENTENCE: multi-source BFS on a grid, level by level, where each level is one
     * minute and setting a cell to 2 is both the infection and the visited mark.
     *
     * ---
     *
     * THE "MULTI-SOURCE LEVEL BFS ON A GRID" PATTERN (ROTTING ORANGES)
     *
     * Your Thought Process & Intuition:
     * 1. WHAT IS NEW HERE vs previous BFS problems (three things, all in one problem):
     *    a) MULTI-SOURCE: previous BFS started from ONE node. Here ALL rotten oranges
     *       start spreading simultaneously, so ALL of them go into the queue BEFORE the
     *       loop. They form "minute 0's wave." This is not multiple separate BFS runs;
     *       it is one BFS with multiple starting points. The wavefront expands outward
     *       from all sources at once, the way a real infection would.
     *    b) GRID as graph: no adjacency list or matrix of connections. The grid IS the
     *       graph. Each cell's neighbors are the 4 adjacent cells (up, down, left, right),
     *       found by adding direction offsets to the current position, with bounds checks.
     *    c) LEVEL COUNTING: the answer is not "can I reach everything" (that is connected
     *       components), it is "how many levels until everything is reached." The size
     *       snapshot separates levels, and each level = one minute.
     *
     * 2. THE CLASSROOM ANALOGY (how I understood the level separation):
     *    Sick kids in a classroom. Each minute, every kid next to a sick kid catches the
     *    cold. I write the currently-sick kids on a list. I process ONLY that list. Anyone
     *    who catches it goes on a NEW list. When the old list is done, one minute has
     *    passed. The new list becomes the current list. Repeat until nobody new gets sick.
     *    The "list" is the queue. The "old list boundary" is the size snapshot.
     *
     * 3. THE SIZE SNAPSHOT IS THE LEVEL SEPARATOR:
     *    At the start of each while-iteration, the queue holds exactly the oranges that
     *    turned rotten LAST minute. size = queue.size() captures how many. The for-loop
     *    processes exactly that many. Anything enqueued DURING the for-loop enters the
     *    queue AFTER the snapshot boundary, so it waits for the next minute. Without the
     *    snapshot, all oranges get processed in one batch with no way to count minutes.
     *
     * 4. NO SEPARATE VISITED ARRAY:
     *    Setting grid[r][c] = 2 does two jobs: it marks the orange as rotten (the actual
     *    answer), and it prevents re-enqueueing (the == 1 check fails on a 2). The grid
     *    is both the state and the visited marker. One assignment, two purposes.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Seed ALL rotten oranges before the loop:
     *    - Why? They all start spreading at minute 0 simultaneously. If I seeded them
     *      one at a time with separate BFS runs, the infection from the second source
     *      would start late and the minute count would be wrong.
     *
     * 2. Track freshCount:
     *    - Why? An empty queue means BFS explored everything REACHABLE. It does NOT mean
     *      every fresh orange was reached. A fresh orange walled off by empty cells is
     *      unreachable. freshCount > 0 after BFS means some oranges could not be infected:
     *      return -1. This is the check I initially got wrong ("empty queue means all
     *      infected") because I confused "BFS finished" with "everything reached."
     *
     * 3. Return 0 when freshCount starts at 0:
     *    - Why? No fresh oranges means nothing needs infecting. That is 0 minutes, not -1.
     *      -1 means impossible; 0 means already done. I had these backwards initially.
     *
     * 4. minutes - 1 at the return:
     *    - Why? The last wave processes the final rotten oranges. They check their
     *      neighbors, find nobody new, enqueue nothing. But the while-loop still ran
     *      that iteration and minutes still incremented. That final empty-result wave
     *      is not a real minute of spreading. Subtract 1 to correct.
     *
     * 5. Direction array int[][] dirs:
     *    - Why? Encodes the 4-neighbor pattern (right, left, down, up) so the neighbor
     *      loop is a clean for-each instead of four copy-pasted if-blocks. Same directions
     *      for every grid problem; this array is reusable.
     *
     * ---
     *
     * THE THREE GRID-GRAPH PATTERNS (pin these, they recur):
     *   Finding neighbors on a grid: direction offsets + bounds check.
     *   Visited on a grid: either a separate boolean[][], or mutate the grid itself.
     *   Starting a grid BFS: enqueue coordinates as int[] pairs, not node numbers.
     *
     * ---
     *
     * MISTAKES I MADE / TRAPS:
     * - THOUGHT I NEEDED AN ADJACENCY LIST: grid problems do not need one. The grid IS
     *   the graph. Neighbors are the 4 adjacent cells found by coordinate arithmetic.
     *   This is the third input format from my graph notes (section 10): edge list needs
     *   adj list, adj matrix needs row scan, grid needs direction offsets.
     * - "EMPTY QUEUE MEANS ALL INFECTED": wrong. Empty queue means BFS finished exploring
     *   everything REACHABLE. Unreachable fresh oranges are still fresh. freshCount is
     *   the real check, not the queue state.
     * - CONFUSED 0 AND -1: no fresh oranges = 0 minutes (already done), not -1. Fresh
     *   oranges that cannot be reached = -1 (impossible), not 0.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Scan the grid. Enqueue every rotten orange (2). Count every fresh one (1).
     * Step 2: If freshCount is 0, return 0 (nothing to infect).
     * Step 3: BFS level by level:
     *         - size = queue.size() (this minute's wave).
     *         - For each in the wave: poll, check 4 neighbors, infect fresh ones
     *           (set to 2, enqueue, freshCount--).
     *         - After the wave: minutes++.
     * Step 4: Return freshCount == 0 ? minutes - 1 : -1.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(R x C) where R is rows and C is columns. The initial scan is R x C. Each
     *    cell is enqueued at most once and polled once, and each poll checks 4 neighbors:
     *    at most 4 x R x C neighbor checks total. Everything is proportional to the grid
     *    size.
     * -> Space: O(R x C) worst case for the queue (if every cell is a rotten orange at
     *    once). No separate visited array (the grid itself marks visited).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - This is the MULTI-SOURCE BFS problem. Say "multi-source" in your first sentence
     *   and the interviewer knows you recognize the pattern.
     * - The size snapshot is what separates levels / minutes / steps. It is the same
     *   technique as level-order tree traversal, now earning its existence on a problem
     *   where levels matter for the answer.
     * - Grid = graph where neighbors are adjacent cells. No adjacency list needed. The
     *   direction array + bounds check is the universal neighbor-finding pattern for grids.
     * - Setting the cell to 2 is both the mutation and the visited mark: one assignment,
     *   two purposes. Know why no separate visited array is needed.
     * - freshCount is the correctness check. An empty queue is NOT proof of full coverage;
     *   unreachable cells stay fresh silently.
     */
    private static int orangesRotting(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;

        // I need to find all the rotten oranges first because they are my starting
        // points. Unlike previous BFS problems where I started from one node, here
        // EVERY rotten orange spreads at the same time, so I put them ALL in the
        // queue before the loop starts. I also count fresh oranges so I can check
        // at the end whether all of them got infected or not.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    queue.add(new int[]{r, c});
                } else if (grid[r][c] == 1) {
                    freshCount++;
                }
            }
        }

        // If there are no fresh oranges at all, nothing needs infecting.
        // The answer is 0 minutes, not -1. I got this backwards on my first try:
        // -1 means impossible, 0 means already done.
        if (freshCount == 0) return 0;

        // These are the 4 directions I can move from any cell: right, left, down, up.
        // This is how I find neighbors on a grid. No adjacency list needed because
        // the grid IS the graph. Adding each direction to my current position gives
        // me a candidate neighbor.
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int minutes = 0;

        // This is the BFS loop. Each full pass through the while-loop represents
        // one minute of time passing.
        while (!queue.isEmpty()) {

            // I take a snapshot of the queue size RIGHT NOW. This tells me how many
            // oranges turned rotten in the PREVIOUS minute. I only process those.
            // Anything I enqueue during this for-loop belongs to the NEXT minute
            // and sits in the queue AFTER this snapshot boundary.
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                // I pull out one rotten orange from this minute's wave.
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];

                // I check all 4 neighbors of this rotten orange.
                for (int[] d : dirs) {
                    int newRow = row + d[0];
                    int newCol = col + d[1];

                    // First I check: is this neighbor even inside the grid?
                    // Without this check I would get ArrayIndexOutOfBoundsException.
                    // Second I check: is it a fresh orange? Only fresh ones (value 1)
                    // can get infected. Rotten ones are already done, empty cells
                    // have nothing to infect.
                    if (newRow >= 0 && newRow < rows
                            && newCol >= 0 && newCol < cols
                            && grid[newRow][newCol] == 1) {

                        // I set it to 2 which does TWO things at once:
                        // 1. It marks this orange as rotten (the actual infection).
                        // 2. It acts as my "visited" mark so I never enqueue it again.
                        //    Next time someone checks this cell, it is 2 not 1, so the
                        //    == 1 check fails. No separate visited array needed.
                        grid[newRow][newCol] = 2;
                        queue.add(new int[]{newRow, newCol});
                        freshCount--;
                    }
                }
            }
            // One complete wave is done. Every orange that was rotten at the start
            // of this minute has now infected its neighbors. Those newly-infected
            // ones are sitting in the queue for the next minute.
            minutes++;
        }

        // Why minutes - 1: the LAST wave of rotten oranges gets processed, they
        // check their neighbors, find nobody new, enqueue nothing. But the while
        // loop still ran that iteration and minutes still got incremented. That
        // final empty wave was not a real minute of spreading, so I subtract 1.
        //
        // If freshCount is still > 0, some fresh orange was unreachable (walled
        // off by empty cells or completely isolated). I return -1 because it is
        // impossible to rot them all. If freshCount is 0, everything got infected
        // and I return the elapsed time.
        return freshCount == 0 ? minutes - 1 : -1;

    }
}
