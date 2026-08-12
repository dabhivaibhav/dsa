package graph.medium_problems;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
Leetcode 542: 01 Matrix

Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.
The distance between two cells sharing a common edge is 1.

Example 1:
Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
Output: [[0,0,0],[0,1,0],[0,0,0]]

Example 2:
Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
Output: [[0,0,0],[0,1,0],[1,2,1]]


Constraints:
            m == mat.length
            n == mat[i].length
            1 <= m, n <= 104
            1 <= m * n <= 104
            mat[i][j] is either 0 or 1.
            There is at least one 0 in mat.
 */
public class ZeroOneMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        System.out.println(Arrays.deepToString(updateMatrix(matrix)));
    }

    /*
     * WHAT THIS METHOD DOES:
     * For every cell in a binary matrix, computes the shortest distance to the nearest
     * 0-cell. Uses multi-source BFS: all 0-cells start in the queue together, and the BFS
     * wavefront expands outward, recording each 1-cell's distance the first time it is
     * reached. The result matrix doubles as the visited marker.
     *
     * THE SENTENCE: flip the question from "each 1 searches for a 0" to "all 0s spread
     * outward at once," which is multi-source BFS with distance = parent + 1.
     *
     * ---
     *
     * THE "MULTI-SOURCE BFS FOR NEAREST DISTANCE" PATTERN (01 MATRIX)
     *
     * THE GEAR CHECK:
     * Gear 1 (what is the graph): grid, undirected, unweighted. Each cell connects to its
     *   4 directional neighbors. No adjacency list needed, the grid IS the graph.
     * Gear 2 (what shape is the question): shortest distance. Unweighted shortest path = BFS.
     * Gear 3 (what tool): BFS. Multiple sources (every 0-cell).
     * Gear 4 (what is special): multi-source BFS. Instead of one start, ALL 0-cells start
     *   together. The first wave to reach any 1-cell IS its nearest 0, because BFS visits
     *   closer things first.
     *
     * ---
     *
     * HOW I WOULD DERIVE THIS WITHOUT KNOWING ROTTING ORANGES:
     *
     * Step 1 (brute force): for each 1-cell, run a separate BFS until it hits a 0. Record
     *   the distance. Works but O((R*C)^2) in the worst case: up to R*C separate BFS runs,
     *   each visiting up to R*C cells.
     *
     * Step 2 (flip): instead of "from each 1, find nearest 0," ask "from each 0, how far
     *   to each 1?" Distances are the same (undirected graph). But running BFS from each
     *   0 separately is still slow.
     *
     * Step 3 (merge): put ALL 0-cells into one queue and run ONE BFS. The waves expand
     *   from all 0s simultaneously. The first wave that reaches a 1-cell IS its nearest 0.
     *   No minimum-tracking needed, BFS order guarantees it. One pass, O(R*C).
     *
     * That three-step reasoning (brute force -> flip -> merge) works on ANY "nearest
     * distance from many sources" problem. The specific previous problem doesn't matter.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Result matrix initialized to -1 for 1-cells, 0 for 0-cells:
     *    - Why -1? It means "not yet reached." The check result[r][c] == -1 IS the visited
     *      check. Once a distance is written, it is no longer -1, so the cell can never be
     *      enqueued again. The result matrix doubles as the visited marker, no separate
     *      boolean[][] needed.
     *    - Why 0 for 0-cells? A 0-cell's distance to the nearest 0 is itself: 0. AND the
     *      BFS distance formula (result[parent] + 1) needs the 0-cells to hold 0, otherwise
     *      their neighbors get the wrong distance. If 0-cells stayed -1, the first neighbor
     *      would get -1 + 1 = 0 instead of 1.
     *
     * 2. Arrays.fill must be called PER ROW on a 2D array:
     *    - Why? Java 2D arrays are arrays of arrays. result is int[][], each element is an
     *      int[] (a row). Arrays.fill(result, -1) tries to put an int into an int[] slot:
     *      type mismatch, won't compile. Must loop over rows and fill each one.
     *
     * 3. Distance formula: result[newRow][newCol] = result[row][col] + 1:
     *    - Why not result[newRow][newCol]++? ++ means "add 1 to whatever is there." The
     *      cell starts at -1, so ++ gives 0, which is wrong for distance 1. And if multiple
     *      parents try to increment, it accumulates. Parent + 1 computes the correct
     *      distance from the source, not an increment from zero.
     *    - Why this works: BFS guarantees that when a cell is polled, it already holds its
     *      correct shortest distance. So its neighbor's distance is exactly one more.
     *
     * 4. No size snapshot (no level-by-level processing):
     *    - Why? Each cell records its OWN distance via parent + 1. There is no global
     *      counter like "minutes" that needs to know where one level ends and another
     *      begins. Plain BFS without the snapshot produces correct per-cell distances.
     *      The snapshot is only needed when the ANSWER is a level count (rotting oranges).
     *
     * 5. Multi-source seeding:
     *    - Why? One BFS per 1-cell is O((R*C)^2). Flipping and merging all 0-cells into
     *      one queue makes it one BFS pass: O(R*C). The key insight: in an undirected
     *      graph, distance from A to B equals distance from B to A, so searching from
     *      0-cells toward 1-cells gives the same distances as searching from 1-cells
     *      toward 0-cells.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     *
     * - Arrays.fill ON A 2D ARRAY: wrote Arrays.fill(result, -1) expecting it to fill
     *   every cell. It won't compile: result is int[][], fill expects int[]. Must fill
     *   each row separately with a loop. Java 2D arrays are arrays-of-arrays, not flat
     *   grids, and fill only works on the innermost level.
     *
     * - FORGOT TO SET 0-CELLS TO 0 IN THE RESULT: after filling everything with -1,
     *   0-cells were also -1. The distance formula computed -1 + 1 = 0 for their
     *   neighbors, which is wrong (should be 1). Fix: set result[i][j] = 0 at the same
     *   time as enqueueing the 0-cell.
     *
     * - FIRST ATTEMPT USED result[newRow][newCol]++ INSTEAD OF parent + 1: ++ increments
     *   from the cell's current value. Starting from -1, the first increment gives 0,
     *   not 1. And if multiple parents increment the same cell (no visited check), the
     *   distance accumulates wrongly. Parent + 1 is the correct distance formula.
     *
     * - FIRST ATTEMPT HAD NO VISITED CHECK: checked matrix[r][c] == 1 but never marked
     *   the cell as reached. The original matrix never changes, so the check stays true
     *   forever. Multiple 0-cells all enqueued the same 1-cell neighbor, producing
     *   duplicates in the queue and wrong distances.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Create result matrix. Fill every row with -1.
     * Step 2: Scan the grid. For each 0-cell: set result[i][j] = 0, enqueue (i, j).
     * Step 3: BFS: poll a cell, check 4 neighbors.
     *         If neighbor is in bounds AND result[neighbor] == -1 (unreached):
     *           result[neighbor] = result[current] + 1
     *           enqueue neighbor
     * Step 4: Return result.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS (unsimplified, every operation counted):
     *
     * Let R = number of rows, C = number of columns, so total cells = R * C.
     * In graph terms: V (vertices) = R * C, E (edges) = at most 2 * R * C
     *   (each cell has up to 4 neighbors, but each edge is shared by 2 cells,
     *    so total unique edges is roughly 2*R*C for a grid: R*(C-1) horizontal
     *    edges + (R-1)*C vertical edges).
     *
     * TIME COMPLEXITY (each operation separately):
     *
     *   1. Allocating result matrix:               O(R * C)
     *      Creating an R x C array.
     *
     *   2. Filling result with -1:                  O(R * C)
     *      Loop over R rows, each Arrays.fill is O(C). Total: R * C.
     *
     *   3. Initial scan to find 0-cells:            O(R * C)
     *      Two nested loops visiting every cell once. Each cell: O(1) check
     *      and possibly O(1) enqueue. Total: R * C.
     *
     *   4. BFS traversal:
     *      - Enqueue operations total:              O(R * C)
     *        Each cell is enqueued AT MOST once (the result == -1 check
     *        prevents re-enqueueing). R * C cells, one enqueue each.
     *
     *      - Poll operations total:                 O(R * C)
     *        Each cell is polled AT MOST once (only enqueued once).
     *
     *      - Neighbor checks total:                 O(4 * R * C)
     *        Each polled cell checks 4 directions. R * C polls, 4 checks
     *        each. Some checks hit out-of-bounds (filtered by bounds check)
     *        and some hit already-visited cells (filtered by result == -1).
     *        But the TOTAL number of direction-checks across all polls is
     *        at most 4 * R * C.
     *
     *      - Distance assignments total:            O(R * C)
     *        Each cell gets its distance written AT MOST once.
     *
     *      BFS total: O(R*C) enqueues + O(R*C) polls + O(4*R*C) neighbor
     *      checks + O(R*C) assignments = O(R * C).
     *
     *   TOTAL TIME: O(R*C) + O(R*C) + O(R*C) + O(R*C) = O(R * C).
     *   In graph terms: O(V + E) where V = R*C and E ~ 2*R*C,
     *   so O(R*C + 2*R*C) = O(R * C).
     *
     * SPACE COMPLEXITY (each allocation separately):
     *
     *   1. Result matrix:                           O(R * C)
     *      R * C integers.
     *
     *   2. Queue:                                   O(R * C) worst case
     *      In the worst case every cell is a 0-cell, all enqueued at the start.
     *      Or during BFS, up to R * C cells can be in the queue at once.
     *
     *   3. Direction array:                         O(1)
     *      Fixed 4 x 2 array, constant regardless of input.
     *
     *   4. Local variables (row, col, cell, etc.):  O(1)
     *      Constant number of ints and references.
     *
     *   TOTAL SPACE: O(R*C) + O(R*C) + O(1) + O(1) = O(R * C).
     *   (The result matrix is the OUTPUT, so some analyses don't count it as
     *    "extra" space. If excluded, the extra space is O(R * C) for the queue.)
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "multi-source BFS" in the first sentence. The 0-cells are the sources, the
     *   distance spreads outward from all of them simultaneously.
     * - The derivation path (brute force -> flip -> merge) works on any "nearest distance
     *   from many targets" problem. Knowing the path is more valuable than knowing the
     *   answer, because it applies to problems you haven't seen.
     * - Distance = parent + 1, never ++. This formula works because BFS guarantees the
     *   parent already holds its correct shortest distance when polled.
     * - The result matrix doubles as visited marker (-1 = unreached). No separate boolean
     *   array needed. Same "mutation IS the visited mark" trick as rotting oranges and
     *   flood fill, just with -1/distance instead of 0/1/2.
     * - No size snapshot needed here. Each cell records its own distance via the formula.
     *   The snapshot is only for problems where the ANSWER is a level count (minutes, moves).
     * - Arrays.fill on a 2D array in Java fills the OUTER array (of row-references), not
     *   the cells. Fill each row in a loop.
     */
    private static int[][] updateMatrix(int[][] matrix) {
        //first we need to declare the result matrix with -1;
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int[] row : result) {
            Arrays.fill(row, -1);
        }

        //now we need to implement the queue to store the row and column
        //coordination for each cell. because we have more than one starting point in the graph
        //and we need to process them all in one BFS pass and this technique is called multisource BFS.
        //because if we process BFS for each cell it's just waste of time.
        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    result[i][j] = 0;
                    queue.add(new int[]{i, j});
                }
            }
        }

        // four direction to check for each cell we will poll from the queue.
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();

            for (int[] d : dirs) {
                // Here I am taking the neighbor in all valid four directions.
                int newRow = cell[0] + d[0];
                int newCol = cell[1] + d[1];

                // Need to check that the current neighbor is in the grid limit
                // and also need to check that if for this neighbor the result cell is -1 or not.
                // If it is not -1 then it already has it's nearest value because BFS guarantees
                // shortest distance from node A to node B in unweighted graph. and once we get pass
                // the condition we will update the neighbor node by adding 1 to the result's current cell's
                //value. and adding the new neighbor in the queue.
                if (newRow >= 0 && newRow < matrix.length
                        && newCol >= 0 && newCol < matrix[0].length
                        && result[newRow][newCol] == -1) {
                    result[newRow][newCol] = result[cell[0]][cell[1]] + 1;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }
        return result;
    }
}
