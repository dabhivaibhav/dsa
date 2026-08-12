package graph.hard_problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
problem:    Detect a cycle in an undirected graph

Given an undirected graph with V vertices labeled from 0 to V-1. The graph
is represented using an adjacency list where adj[i] lists all nodes connected
to node. Determine if the graph contains any cycles.

Note: The graph does not contain any self-edges (edges where a vertex is connected to itself).


Example 1
Input: V = 6, adj= [[1, 3], [0, 2, 4], [1, 5], [0, 4], [1, 3, 5], [2, 4]]
Output: True
Explanation: The graph contains a cycle: 0 ->1 -> 2 -> 5 -> 4 -> 1.

Example 2
Input: V = 4, adj= [[1, 2], [0], [0, 3], [2]]
Output: False
Explanation: The graph does not contain any cycles.

Constraints:
            E=number of edges
            1 ≤ V, E ≤ 10^4
 */
public class DetectCycleUndirectedGraphs {

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(List.of(1, 3)));
        graph.add(new ArrayList<>(List.of(0, 2, 4)));
        graph.add(new ArrayList<>(List.of(1, 5)));
        graph.add(new ArrayList<>(List.of(0, 4)));
        graph.add(new ArrayList<>(List.of(1, 3, 5)));
        graph.add(new ArrayList<>(List.of(2, 4)));
        int V = 6;
        System.out.println(isCycle(V, graph));


    }

    /*
     * WHAT THIS METHOD DOES:
     * Detects whether an undirected graph contains a cycle using BFS with parent tracking.
     * Each queue entry carries both the node and who sent it there (its parent). A visited
     * neighbor that is NOT the parent means a second path exists to that node, which means
     * a cycle. Outer loop handles disconnected graphs. O(V + E) time, O(V) space.
     *
     * THE SENTENCE: BFS where each node remembers who sent it. Visited neighbor that isn't
     * my parent means someone else reached it a different way, two paths, a cycle.
     *
     * ---
     *
     * THE "PARENT-TRACKING BFS" PATTERN (CYCLE DETECTION IN UNDIRECTED GRAPH)
     *
     * THE GEAR CHECK (the four questions I ask before every graph problem):
     * Gear 1 (what is the graph): undirected, unweighted, adjacency list given, 0-indexed.
     * Gear 2 (what shape is the question): "does a cycle exist?" Yes/no. Existence question.
     * Gear 3 (what tool): BFS or DFS, either works for undirected cycle detection.
     * Gear 4 (what is special): the PARENT. Basic BFS just visits everything. This one needs
     *   to distinguish "I see my own parent" from "I see a node reached by a different path."
     *   That distinction IS the cycle detection. Without it, every undirected edge looks like
     *   a cycle because neighbor lists always include the node you came from.
     *
     * ---
     *
     * THE ROOM-AND-JACKET ANALOGY (how I understood the parent check):
     *
     * You and your friend are exploring a building. You start at Room 0. You walk through
     * a door to Room 1. You remember "I came from Room 0."
     *
     * From Room 1 you see a door back to Room 0. You already know about Room 0, it is
     * where you JUST came from. Not surprising. You just turned around and looked backward
     * through the same door. That is Case B: the neighbor is your parent, skip it.
     *
     * Meanwhile your friend started at Room 0 too but took a different hallway: Room 0 ->
     * Room 2 -> Room 3. She reached Room 3 first and left her jacket there.
     *
     * You walk from Room 1 into Room 3. You see your friend's jacket. She has already been
     * here. But she did not come from Room 1, she came from Room 2. YOU came from Room 1.
     * Two different hallways led to the same room. That means there is a loop in the
     * building: Room 1 -> Room 3 -> Room 2 -> Room 0 -> Room 1. That is Case C.
     *
     * My jacket (my parent)       = I am just looking backward. No cycle.
     * Someone else's jacket       = a different path reached this room. Cycle.
     *
     * ---
     *
     * THE THREE CASES AT EACH NEIGHBOR (the core of the algorithm):
     *
     * I pull a node from the queue. I know its parent (who sent it). I scan its neighbors:
     *
     * Case A: neighbor is NOT visited.
     *   -> Mark it visited. Enqueue it with ME as its parent (I am sending it).
     *   This is basic BFS, same as connected components, nothing new.
     *
     * Case B: neighbor IS visited, and it IS my parent.
     *   -> Skip. Do nothing. This is just the edge I arrived through. Every undirected
     *   edge shows up this way: if edge (0,1) exists, adj[0] has 1 and adj[1] has 0.
     *   When I process node 1 (parent 0), I ALWAYS see node 0 in my neighbor list. That
     *   is not a cycle, it is the definition of undirected. Case B has NO CODE AT ALL.
     *   The if-else skips it by not matching either condition.
     *
     * Case C: neighbor IS visited, and it is NOT my parent.
     *   -> CYCLE FOUND. Return true immediately. This neighbor was reached by a different
     *   chain of nodes through a different route. My path and that path both reach the
     *   same node, forming a loop. There is no other explanation for a visited non-parent
     *   neighbor in an undirected graph.
     *
     * ---
     *
     * WHY THE PARENT CHECK IS NECESSARY (not optional, not an optimization):
     * Without it, the first thing that happens on any undirected edge is a false positive.
     * Node 1 sees its parent node 0 as visited and screams "cycle!" on a graph that might
     * be a straight line. The parent check is what makes the algorithm CORRECT, not what
     * makes it faster. Every undirected graph without it returns true, including trees,
     * which by definition have no cycles.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Queue holds int[]{node, parent}, not just node:
     *    - Why? The parent must travel with the node. When I poll a node, I need to know
     *      who sent it so I can compare each visited neighbor against the parent. A separate
     *      parent array would also work (parent[node] = who sent it), but packing it into
     *      the queue entry is simpler and uses no extra array.
     *
     * 2. Starting node gets parent -1:
     *    - Why? Nobody sent the starting node. -1 can never be a real node (nodes are 0 to
     *      V-1), so no neighbor can accidentally equal -1 and be skipped as a parent. -1
     *      means "I have no parent, I started here."
     *
     * 3. The outer loop for disconnected graphs:
     *    - Why? Same reason as connected components: one BFS only reaches one component.
     *      A cycle could hide in any component. So I loop over all nodes and start a fresh
     *      BFS from each unvisited one. If ANY BFS returns true, the graph has a cycle.
     *      If all return false, no cycle anywhere.
     *
     * 4. Case B has no code:
     *    - Why? "Do nothing" is the correct action, and "do nothing" in a loop means the
     *      loop body just moves to the next iteration. No else-if, no continue statement,
     *      nothing. The absence of code IS the implementation. The if handles Case A, the
     *      else-if handles Case C, and Case B falls through both without matching either.
     *
     * 5. Mark visited before enqueueing (same rule as always):
     *    - Why? If I mark on poll instead, the same node could enter the queue multiple
     *      times (pushed by different neighbors before being polled). That wastes time and
     *      could cause false cycle detections because a node appearing twice in the queue
     *      looks like "two paths reached it" when really it was just enqueued twice.
     *
     * ---
     *
     * MY INITIAL WRONG APPROACH AND WHY IT FAILED:
     * - "If a neighbor is already visited, there is a cycle." Tested it on Example 2 (no
     *   cycle): node 1 sees its parent node 0 as visited and reports a false cycle. The
     *   rule flags EVERY undirected edge as a cycle because every neighbor list includes
     *   the parent. The fix is the parent check: visited AND not-my-parent = cycle, visited
     *   AND is-my-parent = just the edge I arrived on.
     * - "If the remaining nodes in the queue also have the same neighbor" was a better
     *   instinct (two paths converging IS the cycle signature), but using queue.contains()
     *   is O(N) per check making the whole thing O(N^2), and it STILL does not distinguish
     *   parent from non-parent. The parent check IS the "reached by someone else" check,
     *   just cheaper and precise.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: visited = boolean[V].
     * Step 2: For each node i from 0 to V-1:
     *         - If !visited[i]: call bfs(i). If it returns true, return true.
     * Step 3: All components checked, none had a cycle. Return false.
     *
     * BFS(start):
     * Step 1: Mark start visited. Enqueue {start, -1}.
     * Step 2: While queue not empty: poll {node, parent}.
     *         For each neighbor of node:
     *           - Not visited: mark, enqueue {neighbor, node}.        (Case A)
     *           - Visited and != parent: return true.                  (Case C)
     *           - Visited and == parent: do nothing.                   (Case B)
     * Step 3: Queue empty, no cycle in this component. Return false.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(V + E). Same as any BFS. Each node is polled once (V pops). Each edge is
     *    examined from both endpoints across all BFS calls (2E neighbor checks). The parent
     *    comparison is O(1) per neighbor. Total: V + 2E = O(V + E).
     * -> Space: O(V). Visited array is V. Queue holds at most V nodes. Each queue entry is
     *    an int[2], constant extra per node. Total O(V).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "parent tracking" in your first sentence. It is the whole algorithm, and naming
     *   it immediately shows you know the undirected-cycle pattern.
     * - The three cases are the thing to say out loud: not-visited (enqueue with parent),
     *   visited-and-parent (skip), visited-and-not-parent (cycle). An interviewer hearing
     *   all three knows you understand WHY the parent matters, not just THAT it matters.
     * - Without the parent check, every undirected edge is a false positive. This is not
     *   an optimization, it is a correctness requirement.
     * - The outer loop handles disconnected graphs. Same pattern as connected components.
     * - Alternative: DFS with parent parameter does the same thing recursively. Same three
     *   cases, same O(V + E), O(V) stack instead of O(V) queue. Worth mentioning both.
     * - Alternative: Union-Find detects cycles when an edge connects two nodes already in
     *   the same set. Worth mentioning as the third approach.
     */
    private static boolean isCycle(int V, List<List<Integer>> adj) {

        // I need the visited array to track which nodes I have already reached.
        // Without it, BFS would loop forever on cycles instead of detecting them.
        boolean[] visited = new boolean[V];

        // The graph might be disconnected. A cycle could be hiding in ANY component.
        // So I loop over all nodes and start a fresh BFS from each unvisited one.
        // Same outer loop I used in connected components and provinces.
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                // If any single BFS finds a cycle, the whole graph has a cycle.
                // I return true immediately, no need to check other components.
                if (bfs(i, adj, visited)) {
                    return true;
                }
            }
        }

        // Every component was checked, none had a cycle.
        return false;
    }

    private static boolean bfs(int start, List<List<Integer>> adj, boolean[] visited) {

        // The queue holds pairs: {node, parent}.
        // Parent means "who sent me here." I need this to tell the difference
        // between "this neighbor is visited because I just came from there" (not
        // a cycle) and "this neighbor is visited because someone ELSE reached it
        // through a different path" (a cycle).
        Queue<int[]> queue = new LinkedList<>();

        // The starting node has no parent. I use -1 because nodes are 0 to V-1,
        // so -1 can never be a real node. -1 means "nobody sent me."
        visited[start] = true;
        queue.add(new int[]{start, -1});

        while (!queue.isEmpty()) {
            int[] pair = queue.poll();
            int node = pair[0];
            int parent = pair[1];

            for (int neighbor : adj.get(node)) {

                if (!visited[neighbor]) {
                    // Case A: neighbor not visited.
                    // Mark it, enqueue it, and I am its parent because I am
                    // the one sending it into the queue.
                    visited[neighbor] = true;
                    queue.add(new int[]{neighbor, node});

                } else if (neighbor != parent) {
                    // Case C: neighbor IS visited, but it is NOT my parent.
                    // Someone else already reached this node through a different
                    // path. Two paths to the same node = a cycle.
                    return true;
                }

                // Case B: neighbor IS visited AND it IS my parent.
                // This is just the door I walked through to get here. Not a cycle.
                // No code needed. The loop moves to the next neighbor automatically.
            }
        }
        return false;
    }
}
