package graph.easy_problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Problem: Connected Components

Given a undirected Graph consisting of V vertices numbered from 0 to V-1 and E edges.
The ith edge is represented by [ai,bi], denoting a edge between vertex ai and bi.
We say two vertices u and v belong to a same component if there is a path from u to v or v to u.
Find the number of connected components in the graph.

A connected component is a subgraph of a graph in which there exists a path between
any two vertices, and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.

Example 1:
Input: V=4, edges=[[0,1],[1,2]]
Output: 2
Explanation: Vertices {0,1,2} forms the first component and vertex 3 forms the second component.

Example 2:
Input: V = 7, edges = [[0, 1], [1, 2], [2, 3], [4, 5]]
Output: 3
Explanation: The edges [0, 1], [1, 2], [2, 3] form a connected component with vertices {0, 1, 2, 3}.
The edge [4, 5] forms another connected component with vertices {4, 5}.
Therefore, the graph has 3 connected components: {0, 1, 2, 3}, {4, 5}, and the isolated vertices {6}
(vertices 6 and any other unconnected vertices).

Constraints:
            1 ≤ V, edges.length ≤ 10^4
            0 <= edges[i][0], edges[i][1] <= V-1
            All edges are unique
 */
public class ConnectedComponents {

    /*
     * WHAT THIS METHOD DOES:
     * Counts the number of connected components in an undirected graph. Builds an adjacency
     * list from the edge list, then loops over all nodes: each unvisited node starts a BFS
     * that marks everything reachable, and each BFS start is one component. O(V + E) time,
     * O(V + E) space.
     *
     * THE SENTENCE: loop over all nodes; each unvisited one starts a BFS that explores one
     * whole component. Count the starts.
     *
     * ---
     *
     * THE GRAPH PROBLEM BLUEPRINT (use this on EVERY graph problem, not just this one):
     *
     * STEP 0 - READ THE PROBLEM FOR FIVE FACTS (before thinking about algorithms):
     *   1. Directed or undirected? -> decides whether edges go one way or both.
     *   2. Weighted or unweighted? -> decides BFS vs Dijkstra for shortest paths.
     *   3. 0-indexed or 1-indexed? -> decides array sizes (V vs V+1).
     *   4. Can the graph be disconnected? -> decides whether you need the outer loop.
     *   5. What is the input format? -> edges list, adjacency matrix, or grid?
     *   For THIS problem: undirected, unweighted, 0-indexed, CAN be disconnected (that
     *   is the whole point), edges given as a list of pairs.
     *
     * STEP 1 - BUILD THE ADJACENCY LIST (almost always the first code you write):
     *   Convert whatever input format you're given into adj[i] = list of i's neighbors.
     *   This is the same block on every problem; only three things change:
     *     - 0-indexed (size V) vs 1-indexed (size V+1)
     *     - undirected (add both directions) vs directed (add one direction)
     *     - unweighted (store just the neighbor) vs weighted (store {neighbor, weight})
     *   For grid problems, skip this step entirely: the grid IS the structure, and
     *   neighbors are the 4 (or 8) adjacent cells.
     *
     * STEP 2 - CHOOSE YOUR TRAVERSAL:
     *   BFS or DFS? Decision table from my graph notes:
     *     - need shortest path (unweighted) -> BFS
     *     - need level-by-level processing -> BFS
     *     - need "minimum steps" -> BFS
     *     - need cycle detection -> DFS (especially directed, with coloring)
     *     - need topological sort -> DFS or BFS (Kahn's)
     *     - need just reachability / "visit everything" -> either works, pick whichever
     *       I'm more comfortable with
     *   For THIS problem: just reachability (is node X in the same component as node Y?).
     *   Either works. I used BFS because the queue pattern is fresh from tree work.
     *
     * STEP 3 - HANDLE DISCONNECTED GRAPHS (the outer loop):
     *   A single BFS/DFS from one node only reaches nodes in THAT node's component. If
     *   the graph can be disconnected, you MUST loop over all nodes and start a fresh
     *   traversal from each unvisited one. This outer loop is:
     *     for (int i = 0; i < V; i++) {
     *         if (!visited[i]) {
     *             bfs(i, adj, visited);   // or dfs
     *             // do something: count++, or collect the component, etc.
     *         }
     *     }
     *   A tree is always connected, so tree problems never needed this. Graphs can be
     *   disconnected, so THIS LOOP IS THE FIRST NEW PATTERN from trees. (The visited
     *   array is the second.)
     *
     * STEP 4 - THE TRAVERSAL ITSELF (BFS or DFS, same as trees plus visited):
     *   The code is your tree BFS/DFS with two changes:
     *     - "children" -> "neighbors from adj.get(node)"
     *     - "null check" -> "visited check"
     *   That is the ENTIRE tree-to-graph upgrade. No new mechanics.
     *
     * ---
     *
     * THE "OUTER LOOP + INNER BFS" PATTERN (CONNECTED COMPONENTS)
     *
     * Your Thought Process & Intuition:
     * 1. WHAT IS A COMPONENT: a group of nodes where every node can reach every other
     *    through some path, and no node in the group connects to a node outside it. In
     *    code terms: everything one BFS can reach from one starting point.
     *
     * 2. WHY THE OUTER LOOP: one BFS explores one component. To find ALL components, you
     *    must try starting from every node. Nodes already visited by a prior BFS are in a
     *    component already counted, so the visited check skips them. Each fresh BFS start
     *    = one new component found.
     *
     * 3. WHY ISOLATED NODES ARE COMPONENTS: a node with no edges (empty neighbor list)
     *    still gets its own BFS start (it's unvisited). The BFS enqueues it, polls it,
     *    finds no neighbors, and finishes. One start, one component. The empty list is not
     *    "nothing"; it is a component of size 1.
     *
     * 4. THE CONNECTION TO TREES: this BFS is my count-nodes BFS with visited replacing
     *    the null check. Queue mechanics are identical: add, poll, enqueue neighbors. The
     *    only genuinely new code compared to all my tree work is the outer for-loop that
     *    restarts BFS on each unvisited node.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Adjacency list, not matrix:
     *    - Why? V can be up to 10^4. A matrix would be 10^4 x 10^4 = 10^8 cells, likely
     *      TLE or MLE. The adjacency list uses O(V + 2E) for undirected, proportional to
     *      the actual edges. Interview default is always adjacency list unless the problem
     *      specifically needs O(1) edge-existence checks on a small dense graph.
     *
     * 2. Mark visited BEFORE enqueuing, not on poll:
     *    - Why? If you mark on poll, the same node can enter the queue multiple times
     *      (pushed by different neighbors before being polled). Wastes time, can cause
     *      wrong answers in other problems, and the .md notes flag it as common mistake #2.
     *
     * 3. BFS is void, shared visited array passed in:
     *    - Why? The same visited array spans ALL components. BFS marks the nodes it visits,
     *      and the outer loop reads those marks to decide where to start next. If each BFS
     *      had its own visited, the outer loop could not see what was already explored.
     *      Same shared-state pattern as prev/count fields in BST problems, but here it is
     *      a parameter rather than a field because the method has a natural place to pass
     *      it (the call site in the outer loop).
     *
     * 4. Both directions added for undirected:
     *    - Why? Edge (0,1) means 0 can reach 1 AND 1 can reach 0. Adding only one
     *      direction would make BFS from 1 unable to find 0, splitting a real component
     *      into fake pieces. Common mistake #5 in the .md notes.
     *
     * ---
     *
     * THREE PHASES OF EVERY GRAPH SOLUTION (pin this to the wall):
     *
     *   PHASE 1 - BUILD: convert input to adjacency list. Same code every time, three
     *             knobs (indexing, direction, weight).
     *   PHASE 2 - TRAVERSE: BFS or DFS with visited. Same as tree traversal + visited.
     *   PHASE 3 - ANSWER: use the traversal to compute what the problem asks. Here it is
     *             "count the BFS starts." Other problems: "find shortest distance," "detect
     *             a cycle," "collect all nodes in each component," etc. The traversal is
     *             the engine; the answer logic wraps around it.
     *
     *   This three-phase split is not a coincidence; it is the graph-problem equivalent of
     *   the BST "compare, discard a side, descend" move. Every graph solution is build +
     *   traverse + answer, the same way every BST solution was compare + discard + descend.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Build adjacency list from edge list. V slots (0-indexed), both directions.
     * Step 2: Create visited[V], all false. Set count = 0.
     * Step 3: For each node i from 0 to V-1:
     *         - If !visited[i]: call bfs(i), then count++.
     * Step 4: Return count.
     *
     * BFS(start):
     * Step 1: Queue start, mark visited.
     * Step 2: While queue not empty: poll a node, for each unvisited neighbor mark and enqueue.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - The outer loop is what handles disconnected graphs. Without it, you only count one
     *   component. This is the #1 new pattern from trees.
     * - visited is shared across all BFS calls, not per-call. That sharing is how the outer
     *   loop knows what has already been explored.
     * - An isolated node (no edges) is still a component. Its BFS starts, finds nothing,
     *   finishes. count++ fires. Correct.
     * - Mark before enqueue, not on poll. Same node entering the queue twice wastes work
     *   and can cause bugs in problems where you track distances or parents.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(V + E). The outer loop runs V times (once per node). Across ALL BFS calls
     *    combined, each node is polled exactly once (V pops total) and each edge is checked
     *    exactly twice (once from each endpoint in an undirected graph, 2E neighbor checks
     *    total). So: V + 2E = O(V + E). NOT V x E; the BFS calls do not restart from
     *    scratch, they share the visited array and each node/edge is processed once globally.
     * -> Space: O(V + E). The adjacency list holds V lists with 2E total entries. The
     *    visited array is O(V). The BFS queue holds at most O(V) nodes. Total: O(V + E).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - The outer loop is the answer to "what if the graph is disconnected?" Know it cold;
     *   it appears on nearly every graph problem.
     * - The three-phase structure (build, traverse, answer) is the graph-problem skeleton.
     *   Name it before coding; it shows you have a framework, not just a solution.
     * - BFS here could be DFS with zero change to the answer. Say "either works because
     *   this only needs reachability, not shortest paths or level order." That one sentence
     *   shows you know WHEN each traversal matters.
     * - The complexity is O(V + E), not O(V x E). Each node and edge is processed once
     *   GLOBALLY across all BFS calls. Explaining why the outer loop does not multiply the
     *   cost is a common probe.
     * - Alternative: Union-Find (DSU) solves this in O(V + E x alpha(V)), nearly O(V + E),
     *   without explicit traversal. Worth mentioning as the second approach; you will build
     *   it soon in the graph playlist.
     */
    private static int findNumberOfComponent(int V, List<List<Integer>> edges) {

        // PHASE 1: Building adjacency list from the edge list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        for (List<Integer> edge : edges) {

            int u = edge.get(0);
            int v = edge.get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);       // undirected: both directions
        }

        // PHASE 3: Looping over all nodes, BFS from each unvisited one
        boolean[] visited = new boolean[V];
        int count = 0;

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                bfs(i, adj, visited);  // explore the entire component
                count++;               // one more component found
            }
        }
        return count;
    }

    private static void bfs(int start, List<List<Integer>> adj, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;         // mark BEFORE enqueuing

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

}
