package graph.easy_problems;

import java.util.LinkedList;
import java.util.Queue;

/*
Leetcode 733. Flood Fill

You are given an image represented by an m x n grid of integers image, where image[i][j]
represents the pixel value of the image. You are also given three integers sr, sc, and color.
Your task is to perform a flood fill on the image starting from the pixel image[sr][sc].

To perform a flood fill:

Begin with the starting pixel and change its color to color.
Perform the same process for each pixel that is directly adjacent (pixels that share a side
with the original pixel, either horizontally or vertically) and shares the same color as the
starting pixel. Keep repeating this process by checking neighboring pixels of the updated
pixels and modifying their color if it matches the original color of the starting pixel.
The process stops when there are no more adjacent pixels of the original color to update.
Return the modified image after performing the flood fill.

Example 1:
Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]
Explanation:
From the center of the image with position (sr, sc) = (1, 1) (i.e., the red pixel), all pixels
connected by a path of the same color as the starting pixel (i.e., the blue pixels) are colored
with the new color.

Note the bottom corner is not colored 2, because it is not horizontally or vertically connected
to the starting pixel.

Example 2:
Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0
Output: [[0,0,0],[0,0,0]]
Explanation:
The starting pixel is already colored with 0, which is the same as the target color. Therefore, no
changes are made to the image.

Constraints:
            m == image.length
            n == image[i].length
            1 <= m, n <= 50
            0 <= image[i][j], color < 216
            0 <= sr < m
            0 <= sc < n
 */
public class FloodFill {

    /*
     * WHAT THIS METHOD DOES:
     * Paints all cells connected to (sr, sc) that share the starting cell's original color
     * with a new color. Single-source BFS on a grid: start from one cell, spread to all
     * 4-directional neighbors that match the original color, paint each one. The grid itself
     * serves as the visited marker. O(R x C) time, O(R x C) space.
     *
     * THE SENTENCE: single-source BFS on a grid, painting connected same-colored cells,
     * where changing the color IS the visited mark.
     *
     * ---
     *
     * THE "PAINT THE CONNECTED REGION" PATTERN (FLOOD FILL)
     *
     * Your Thought Process & Intuition:
     * 1. HOW THIS RELATES TO MY OTHER GRAPH PROBLEMS:
     *    Connected components: outer loop + BFS, count the starts.
     *    Provinces: same algorithm, adjacency matrix instead of edge list.
     *    Rotting oranges: multi-source BFS, level counting for minutes.
     *    Flood fill: single-source BFS, no level counting, just paint everything reachable.
     *    Same skeleton (queue, poll, check neighbors, enqueue), different knobs turned.
     *
     * 2. WHAT TRANSFERS FROM ROTTING ORANGES AND WHAT DOES NOT:
     *    TRANSFERS: grid as graph, direction offsets for 4 neighbors, bounds checking,
     *    grid mutation as visited mark (no separate boolean array).
     *    DOES NOT TRANSFER: multi-source seeding (this has ONE start, not many), level
     *    counting with size snapshot (no minutes to track here), freshCount (no "did I
     *    reach everything" check needed), the specific values 0/1/2 (those were rotting
     *    oranges' constants, not universal).
     *
     * 3. THE PROBLEM IS "PAINT A CONNECTED COMPONENT OF ONE COLOR":
     *    Starting from (sr, sc), every cell reachable through same-colored neighbors is
     *    one connected component. Paint it. That is all. No counting, no optimization,
     *    no minimum, just reachability.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Capture originalColor BEFORE painting the starting cell:
     *    - Why? The starting cell must be painted (it is part of the region). But once
     *      painted, its color changes. If I capture originalColor after painting, I
     *      capture the NEW color, and the neighbor check (== originalColor) matches
     *      nothing. Capture first, paint second.
     *
     * 2. Paint the starting cell BEFORE enqueueing it:
     *    - Why? The BFS neighbor loop only paints NEIGHBORS. Nobody is the starting
     *      cell's "parent" to paint it. If I skip painting it, the source is the one
     *      cell that keeps its old color while everything around it changes. This was
     *      my actual bug: 222/278 tests passed, and the failing case was a single cell
     *      surrounded by different colors, where the starting cell was the ONLY cell
     *      that needed painting, and nobody painted it.
     *
     * 3. The same-color guard (originalColor == color -> return immediately):
     *    - Why? If the new color equals the original color, painting changes nothing.
     *      But the neighbor check (== originalColor) still passes on every painted cell,
     *      because painting didn't actually change it. So nothing ever fails the check,
     *      nothing is ever "visited," and the BFS enqueues the same cells forever.
     *      Infinite loop. The guard prevents it.
     *    - This trap does not exist in rotting oranges because 1 (fresh) and 2 (rotten)
     *      are always different. Here the colors are arbitrary parameters, so equality
     *      is possible.
     *
     * 4. No separate visited array:
     *    - Why? Painting a cell from originalColor to color makes it fail the
     *      == originalColor check on future neighbor scans. The color change IS the
     *      visited mark. Same principle as rotting oranges setting 1 to 2.
     *    - When this trick BREAKS: when the mutation does not affect the neighbor check,
     *      which is exactly the same-color case the guard handles.
     *
     * 5. No level counting (no size snapshot, no minutes counter):
     *    - Why? The problem asks "paint everything reachable," not "how many steps."
     *      Level separation is only needed when the answer depends on WHICH level a
     *      node was reached at. Here it does not.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - HARDCODED ROTTING-ORANGES VALUES: used == 1, == 0, and = 2 instead of
     *   == originalColor and = color. Those values were specific to rotting oranges
     *   (0 = empty, 1 = fresh, 2 = rotten). Here the values are arbitrary colors
     *   defined by the input parameters. The BFS STRUCTURE transfers between grid
     *   problems; the SPECIFIC CONSTANTS do not.
     *   THE GENERAL RULE: when adapting a template, identify which values were
     *   problem-specific and replace them with the new problem's parameters. The
     *   skeleton copies; the constants inside it need conscious accounting.
     *
     * - FORGOT TO PAINT THE STARTING CELL: enqueued (sr, sc) without changing its
     *   color. The BFS explored outward and painted everything around it, but the
     *   source itself stayed its old color. On the failing test case, (sr, sc) was
     *   the ONLY cell in the region (all neighbors were different colors), so the
     *   output was identical to the input. Fix: image[sr][sc] = color right before
     *   queue.add.
     *   THE GENERAL RULE: the starting node in a BFS must be marked (visited / painted
     *   / processed) BEFORE enqueueing, because no other node will process it. Every
     *   BFS I have written marks the source before the loop. I followed that rule for
     *   visited but not for painting, because I did not recognize they were the same
     *   operation on this problem.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Capture originalColor = image[sr][sc].
     * Step 2: If originalColor == color, return image (same-color guard).
     * Step 3: Paint image[sr][sc] = color. Enqueue (sr, sc).
     * Step 4: BFS: poll a cell, check 4 neighbors. For each in-bounds neighbor where
     *         image[r][c] == originalColor: paint it color, enqueue it.
     * Step 5: Queue empty -> return image.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(R x C). Each cell is enqueued at most once and polled once. Each poll
     *    checks 4 neighbors. Total: at most 4 x R x C neighbor checks. Proportional to
     *    the grid size.
     * -> Space: O(R x C) worst case for the queue (if every cell is the same color, the
     *    entire grid enters the queue). No separate visited array.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - This is "paint a connected component on a grid." Say that first.
     * - The same-color guard is the trap: without it, painting changes nothing, visited
     *   marking fails, BFS loops forever.
     * - Paint the source before enqueueing. It is the one node nobody else processes.
     * - No level counting needed (contrast with rotting oranges). Know when the size
     *   snapshot earns its existence and when it does not.
     * - When adapting a template: structure transfers, constants don't. Replace every
     *   problem-specific value with the new problem's parameters.
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int originalColor = image[sr][sc];          // capture BEFORE painting

        if (originalColor == color) return image;    // same-color guard

        int rows = image.length;
        int cols = image[0].length;

        Queue<int[]> queue = new LinkedList<>();
        image[sr][sc] = color;                       // paint the starting cell
        queue.add(new int[]{sr, sc});

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            for (int[] d : dirs) {
                int newRow = row + d[0];
                int newCol = col + d[1];
                if (newRow >= 0 && newRow < rows
                        && newCol >= 0 && newCol < cols
                        && image[newRow][newCol] == originalColor) {  // same color as start
                    image[newRow][newCol] = color;                    // paint with NEW color
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }
        return image;
    }
}
