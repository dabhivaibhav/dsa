package graph.medium_problems;

import java.util.LinkedList;
import java.util.Queue;

/*
Leetcode 547. Number of Provinces

There are n cities. Some of them are connected, while some are not.
If city a is connected directly with city b, and city b is connected
directly with city c, then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and
no other cities outside of the group. You are given an n x n matrix
isConnected where isConnected[i][j] = 1 if the ith city and the jth city
are directly connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.

Example 1:
Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2

Example 2:
Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3

Constraints:
            1 <= n <= 200
            n == isConnected.length
            n == isConnected[i].length
            isConnected[i][j] is 1 or 0.
            isConnected[i][i] == 1
            isConnected[i][j] == isConnected[j][i]
 */
public class NumberOfProvinces {

    public static void main(String[] args) {
        int[][] grid = new int[3][3];
        grid[0] = new int[]{1,1,0};
        grid[1] = new int[]{1,1,0};
        grid[2] = new int[]{0,0,1};
        System.out.println(findCircleNum(grid));
    }
    /*
     * WHAT THIS METHOD DOES:
     * Counts the number of provinces (connected components) in an undirected graph given
     * as an N x N adjacency MATRIX. Same outer-loop + BFS pattern as connected components,
     * with the only difference being how neighbors are found: scan a matrix row instead of
     * reading an adjacency list. O(N^2) time, O(N) space.
     *
     * THE SENTENCE: this IS connected components, wearing a "provinces" costume, with an
     * adjacency matrix input instead of an edge list.
     *
     * ---
     *
     * THE "SAME ALGORITHM, DIFFERENT INPUT FORMAT" PATTERN (NUMBER OF PROVINCES)
     *
     * Your Thought Process & Intuition:
     * 1. PROBLEM RECOGNITION: "group of connected cities = one province" is the definition
     *    of a connected component. The answer is the number of components. Same algorithm,
     *    same outer loop, same visited array, same count-the-BFS-starts logic.
     *
     * 2. THE ONLY NEW THING IS THE INPUT FORMAT: last problem gave an edge list, so phase 1
     *    was "build an adjacency list." This problem gives an adjacency MATRIX directly, so
     *    phase 1 disappears entirely. The matrix IS the representation. No conversion needed.
     *
     * 3. HOW NEIGHBORS CHANGE BETWEEN REPRESENTATIONS:
     *    Adjacency LIST:   for (int neighbor : adj.get(node))
     *      -> neighbors are pre-collected, iterate the list.
     *    Adjacency MATRIX:  for (int j = 0; j < n; j++) if (matrix[node][j] == 1)
     *      -> scan the row, each 1 is a neighbor.
     *    Same meaning (find all neighbors), different access pattern. Everything AROUND the
     *    neighbor loop (the outer loop, visited, the queue, the count) is unchanged.
     *
     * 4. THE DECISION RULE FOR "DO I NEED TO BUILD AN ADJACENCY LIST":
     *    Input is an edge list    -> yes, build it. That is phase 1.
     *    Input is an adj matrix   -> no. The matrix IS the structure. Scan rows.
     *    Input is a grid          -> no. The grid IS the structure. Neighbors are adjacent cells.
     *    This problem is case 2. Skip phase 1, scan rows in BFS.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     * 1. 0-INDEXED EVERYTHING, zero +1 or -1 conversions:
     *    - Why? The matrix is a Java 2D array: valid indices are 0 to N-1. The problem
     *      description labels cities 1, 2, 3 for humans; the code uses 0, 1, 2 because
     *      that is what the array gives you. Every +1 and -1 is a place a bug can hide.
     *      Zero conversions means zero places for that bug.
     *    - THE RULE: always match the array's native indexing. Never convert unless the
     *      DATA (not the description) uses a different system.
     *
     * 2. No adjacency list built:
     *    - Why? Building one from the matrix would cost O(N^2) time and O(N + E) space,
     *      and then traversal would ALSO cost O(N + E). Total: O(N^2 + N + E). Scanning
     *      rows directly costs O(N^2) total with no extra storage. Same or better, and
     *      simpler. Don't convert a representation unless the new one is cheaper to use.
     *
     * 3. Self-loops on the diagonal (isConnected[i][i] = 1):
     *    - Why not special-case them? When BFS starts from node i, visited[i] is already
     *      true. The neighbor loop hits isConnected[i][i] = 1, checks visited[i], finds
     *      true, skips. The visited array handles self-loops for free, same way it handles
     *      back-edges. Zero special-case code needed.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - 1-BASED INDEXING ON A 0-BASED ARRAY: started the loop at i=1 and used i+1 and
     *   j+1 throughout. Caused ArrayIndexOutOfBoundsException when j reached N (valid
     *   indices stop at N-1), and skipped node 0 entirely (never visited).
     *   This is common mistake #6 from my graph .md notes, and it happened because BST
     *   problems used 1-based nodes and the reflex carried over.
     *
     * - MIXED 0-BASED AND 1-BASED IN THE SAME QUEUE: started BFS with start = i+1
     *   (1-based) but enqueued j (0-based) for neighbors. The queue held two different
     *   numbering systems. When polled, node-1 computed the wrong row: a 0-based j polled
     *   as if it were 1-based produced isConnected[j-1], reading the wrong city's row.
     *   PASSED BY ACCIDENT because the test matrices were symmetric, so the wrong row
     *   had the same 1s. On an asymmetric case it would silently miscount.
     *
     * - THOUGHT j+1 IN THE VISITED CHECK WAS "CHECKING THE NEXT NODE": it was actually
     *   compensating for the 1-based visited array while j was 0-based. The confusion
     *   was a SYMPTOM of mixed indexing, not a logic question. Once everything became
     *   0-based, the check became visited[j] and the confusion dissolved.
     *
     * - THE GENERAL RULE EXTRACTED: the moment you write node-1 or j+1 to convert between
     *   systems, stop and ask why you are not using the native system everywhere. The
     *   conversion is where every off-by-one in this problem hid.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: visited = boolean[N], count = 0. No adjacency list to build.
     * Step 2: For each node i from 0 to N-1:
     *         - If !visited[i]: call bfs(i), then count++.
     * Step 3: Return count.
     *
     * BFS(start):
     * Step 1: Queue start, mark visited[start] = true.
     * Step 2: While queue not empty: poll node, scan row isConnected[node][0..N-1].
     *         For each j where isConnected[node][j] == 1 and !visited[j]: mark and enqueue.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N^2). The outer loop runs N times. Each BFS scans a full row of length N
     *    for each node it processes. Across all BFS calls, each node is processed once, so
     *    N rows are scanned, each of length N: N x N = N^2. (Compare to the edge-list
     *    version which was O(V + E): scanning rows costs O(N) per node instead of O(degree),
     *    which is the known tradeoff of adjacency matrix vs adjacency list.)
     * -> Space: O(N). Visited array is N. Queue holds at most N nodes. No adjacency list
     *    built. The input matrix is given, not counted as extra space.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Recognize the costume: "provinces" = connected components. Say it first.
     * - The input format decides phase 1: edge list -> build adj list; matrix -> scan rows;
     *   grid -> use cell coordinates. The traversal logic doesn't change.
     * - 0-based everything unless the DATA says otherwise. Never introduce +1/-1 conversions
     *   to match the problem DESCRIPTION's labeling.
     * - Self-loops are handled by visited for free. Don't special-case the diagonal.
     * - Time is O(N^2) with a matrix, O(N + E) with an adj list. The matrix representation
     *   forces the scan-full-row cost even when edges are sparse. Know this tradeoff.
     */
    private static int findCircleNum(int[][] isConnected) {
        boolean[] visited = new boolean[isConnected.length];
        int count = 0;

        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                bfs(i, isConnected, visited);
                count++;
            }
        }
        return count;
    }

    private static void bfs(int start, int[][] isConnected, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int j = 0; j < isConnected.length; j++) {
                if (isConnected[node][j] == 1 && !visited[j]) {
                    visited[j] = true;
                    queue.add(j);
                }
            }
        }
    }
}
