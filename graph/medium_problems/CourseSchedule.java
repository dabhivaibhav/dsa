package graph.medium_problems;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 207. Course Schedule

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1.
You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you
must take course bi first if you want to take course ai.

For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
Return true if you can finish all courses. Otherwise, return false.

Example 1:
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0. So it is possible.

Example 2:
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take.
To take course 1 you should have finished course 0, and to take course 0 you should also have
finished course 1. So it is impossible.

Constraints:
            1 <= numCourses <= 2000
            0 <= prerequisites.length <= 5000
            prerequisites[i].length == 2
            0 <= ai, bi < numCourses
            All the pairs prerequisites[i] are unique.
 */
public class CourseSchedule {


    public static void main(String[] args) {
        int[][] matrix = {{1, 0}, {0, 1}};
        System.out.println(canFinish(2, matrix));
        int[][] matrix2 = {{1, 0}};
        System.out.println(canFinish(2, matrix2));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Determines whether all courses can be finished given their prerequisites. Models
     * courses as nodes and prerequisites as directed edges, then checks for a cycle using
     * three-color DFS. A cycle means a deadlock (A requires B requires A), making it
     * impossible to finish. No cycle means all courses can be completed. O(V + E) time,
     * O(V + E) space.
     *
     * THE SENTENCE: course schedule is directed cycle detection. Build the directed graph
     * from prerequisites, three-color DFS to find cycles, no cycle means can finish.
     *
     * ---
     *
     * THE "THREE-COLOR DFS" PATTERN (DIRECTED CYCLE DETECTION)
     *
     * THE GEAR CHECK:
     * Gear 1 (what is the graph): DIRECTED. [a, b] means "a requires b," so the edge
     *   points from a to b (or b to a depending on convention, what matters is that
     *   requiring is one-way: a needs b does NOT mean b needs a). Adjacency list built
     *   from the prerequisites array. 0-indexed.
     * Gear 2 (what shape is the question): "can you finish all courses?" = "is there
     *   no deadlock?" = "is there no directed cycle?" Yes/no existence question.
     * Gear 3 (what tool): DFS with coloring. NOT the parent-tracking BFS from undirected
     *   cycle detection, that approach fails on directed graphs (explained below).
     * Gear 4 (what is special): THREE states per node instead of two. Boolean visited
     *   is not enough for directed graphs.
     *
     * ---
     *
     * WHY UNDIRECTED CYCLE DETECTION (PARENT TRACKING) FAILS ON DIRECTED GRAPHS:
     *
     * In an undirected graph, "visited neighbor that isn't my parent" = cycle. In a
     * directed graph, two paths reaching the same node is NORMAL and NOT a cycle:
     *     A -> B -> C
     *     A -> C
     * C is reachable from A two ways. No cycle. But a boolean visited array marks C
     * as visited on the first path (A->B->C), and when the second path (A->C) reaches
     * C and finds it visited, it wrongly reports a cycle. The parent check cannot fix
     * this because the concept of "parent" is ambiguous in directed graphs (C has two
     * incoming edges, who is its parent?).
     *
     * THE FIX: distinguish "this node is still being explored (on my current path)"
     * from "this node is completely finished (explored by an earlier path and proven
     * safe)." That requires THREE states, not two.
     *
     * ---
     *
     * THE THREE COLORS (the core idea):
     *
     * WHITE (0): unvisited. DFS has never touched this node.
     *   -> Java initializes int arrays to 0 automatically, so all nodes start white
     *      for free.
     *
     * GRAY (1): currently being explored. DFS is INSIDE this node's call right now.
     *   This node is ON the current call stack. Its descendants are still being explored.
     *   -> A node turns gray at the TOP of the DFS function, the moment its exploration
     *      BEGINS. It stays gray for the entire duration of its call.
     *
     * BLACK (2): completely finished. DFS entered this node, explored ALL its descendants,
     *   and returned. We know everything reachable from this node, and there is no cycle
     *   through it.
     *   -> A node turns black at the BOTTOM of the DFS function, right before it returns,
     *      the moment its exploration ENDS.
     *
     * THE CYCLE RULE: if you reach a GRAY neighbor, you followed directed edges and
     * arrived back at a node whose exploration is still in progress. You are inside its
     * call, which called something, which called something, which reached it again.
     * That is a directed cycle.
     *
     * A BLACK neighbor is safe: it was fully explored by an earlier path and proven
     * cycle-free. The A->B->C, A->C case: C turns black after A->B->C finishes it,
     * so when A->C reaches C and sees black, it correctly says "done, not a cycle."
     *
     * ---
     *
     * THE THREE CASES IN THE FOR-LOOP (same structure as undirected, different checks):
     *
     * Case A: neighbor is WHITE (0).
     *   -> Unexplored. Recurse into it. If the recursive call returns true (cycle found
     *      deeper), pass true up immediately. If false, continue to next neighbor.
     *   This is the same short-circuit return pattern from Two Sum BST and validate BST:
     *   a deep true rides up immediately, a false lets the loop continue.
     *
     * Case B: neighbor is GRAY (1).
     *   -> Currently on the path. Cycle found. Return true immediately. Do NOT recurse
     *      into it (it is already being explored, recursing would corrupt its color).
     *
     * Case C: neighbor is BLACK (2).
     *   -> Done. Skip. No code needed. A finished node is proven safe.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. int[] color instead of boolean[] visited:
     *    - Why? Two states (visited/not) cannot distinguish "on the current path" from
     *      "finished by an earlier path." Three states can. That distinction IS the
     *      algorithm. Using 0/1/2 in an int[] gets the 0-initialization free from Java.
     *
     * 2. Gray at the TOP, black at the BOTTOM:
     *    - Why? A node is gray for the entire duration of its exploration. Gray means "I
     *      am currently inside this node's DFS call, exploring its descendants." The top
     *      of the function is where exploration begins. The bottom, after the for-loop, is
     *      where it ends. Gray at top = "I just started." Black at bottom = "I just
     *      finished everything below me."
     *
     * 3. Only call DFS on WHITE nodes in the outer loop:
     *    - Why? A black node is already proven safe. A gray node should never appear at
     *      the outer-loop level (it would mean DFS is still inside that node's call, which
     *      contradicts being at the outer loop). Calling DFS on a non-white node would set
     *      it back to gray, corrupting the coloring.
     *
     * 4. canFinish returns the OPPOSITE of dfsIsCycle:
     *    - Why? dfsIsCycle returns true when a cycle IS found (bad). canFinish returns
     *      true when courses CAN be finished (good, no cycle). They are inverses. If
     *      dfsIsCycle ever returns true, canFinish returns false.
     *
     * 5. The outer loop for disconnected graphs:
     *    - Why? Same reason as every graph problem: one DFS only reaches one component.
     *      A cycle could hide in any component. Loop over all white nodes.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     *
     * - MERGED THE THREE CASES INTO ONE BROKEN CONDITION:
     *   Wrote `if (color[neighbor] == 1 && dfsIsCycle(neighbor, adj, color))`. This says
     *   "if gray AND recurse." Two bugs in one line: (a) if gray, it IS a cycle, no need
     *   to recurse, recursing into a gray node corrupts its color; (b) the condition
     *   requires gray to recurse, but WHITE nodes need recursing, so white neighbors were
     *   never explored. DFS never went deeper than one level.
     *   FIX: separate the cases. Gray = return true immediately. White = recurse. Black =
     *   skip. Three distinct checks, never merged.
     *
     * - OUTER LOOP OVERWROTE THE ANSWER:
     *   Wrote `answer = dfsIsCycle(i, ...)` in a loop, so each iteration overwrote the
     *   previous result. If node 0 found a cycle (true) but node 1 didn't (false), the
     *   cycle was lost. FIX: return true the MOMENT any call finds a cycle. Don't store
     *   and overwrite, short-circuit.
     *
     * - CALLED DFS ON ALREADY-BLACK NODES:
     *   The outer loop called dfs on every node, including finished ones. Calling dfs
     *   on a black node sets it back to gray (first line of dfs), corrupting the entire
     *   coloring. FIX: only call dfs on white (color == 0) nodes.
     *
     * - INITIAL APPROACH WAS BOOLEAN VISITED (the undirected method):
     *   "If I reach a node that is already visited, it is a cycle." Tested it mentally
     *   on A->B->C, A->C: C gets visited on path A->B->C, then path A->C finds C
     *   visited and wrongly screams cycle. Caught this BEFORE coding by testing the
     *   approach on a counterexample. That is the ritual working: break your own rule
     *   on a concrete case before committing to code.
     *
     * - RETURNED false FOR GRAY (should be true, cycle found) AND true FOR NO CYCLE
     *   (should be false): confused which boolean means what. The naming rule: name the
     *   method for what true means. dfsIsCycle: true = yes there is a cycle. canFinish:
     *   true = yes you can finish. They are opposites.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Build adjacency list from prerequisites. Directed: add one direction only.
     * Step 2: int[] color = new int[numCourses]. All start at 0 (white) automatically.
     * Step 3: For each node i: if color[i] == 0 (white), call dfsIsCycle(i).
     *         If any call returns true, return false (cannot finish).
     * Step 4: All components checked, no cycle -> return true (can finish).
     *
     * dfsIsCycle(node):
     * Step 1: color[node] = 1 (gray). Exploration begins.
     * Step 2: For each neighbor:
     *         - BLACK (2): skip.
     *         - GRAY (1): return true. Cycle.
     *         - WHITE (0): recurse. If true, return true.
     * Step 3: color[node] = 2 (black). Exploration complete.
     * Step 4: return false. No cycle through me.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(V + E). Each node is colored white -> gray -> black exactly once (never
     *    revisited after black). Each edge is examined once during its source node's DFS
     *    call. Total: V color changes + E edge checks = O(V + E).
     * -> Space: O(V + E). Adjacency list holds V lists with E total entries. Color array
     *    is O(V). Recursion stack is O(V) worst case (a straight chain). Total O(V + E).
     *
     * ---
     *
     * UNDIRECTED vs DIRECTED CYCLE DETECTION (the comparison to carry):
     *
     * UNDIRECTED: parent tracking. Two states (visited/not). "Visited neighbor that isn't
     *   my parent" = cycle. Works because in undirected graphs, the only legitimate reason
     *   to see a visited neighbor is the parent edge.
     *
     * DIRECTED: three-color DFS. Three states (white/gray/black). "Gray neighbor" = cycle.
     *   Needed because in directed graphs, multiple paths to the same node are normal and
     *   NOT cycles. Only a path back to a node STILL BEING EXPLORED (gray) is a cycle.
     *
     * The extra color is the price of directed edges: they create legitimate convergence
     * that undirected edges don't, and distinguishing convergence from cycles requires one
     * more state.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "three-color DFS for directed cycle detection" in the first sentence.
     * - Know WHY boolean visited fails on directed graphs (the A->B->C, A->C case).
     *   This is the most common interview probe on this problem.
     * - Gray at the top, black at the bottom. A node is gray for the entire duration
     *   of its call. Say that; it shows you understand what the colors MEAN, not just
     *   their values.
     * - The three cases are separate checks, never merged. Gray = immediate true. White =
     *   recurse and propagate. Black = skip. Merging them was my actual bug.
     * - canFinish and dfsIsCycle are boolean inverses. Name methods for what true means.
     * - Alternative: BFS with in-degree tracking (Kahn's algorithm for topological sort).
     *   If you can process all nodes by repeatedly removing zero-in-degree nodes, no cycle
     *   exists. Worth mentioning as the BFS approach to the same problem.
     */
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            adj.get(prerequisite[0]).add(prerequisite[1]);
        }

        int[] color = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            if (color[i] == 0) {
                if (dfsIsCycle(i, adj, color)) {
                    return false;        // cycle found, CANNOT finish
                }
            }
        }
        return true;

    }

    private static boolean dfsIsCycle(int node, List<List<Integer>> adj, int[] color) {

        color[node] = 1;

        for (int neighbor : adj.get(node)) {
            if (color[neighbor] == 2) continue;                              // BLACK: skip
            if (color[neighbor] == 1) return true;                           // GRAY: cycle
            if (dfsIsCycle(neighbor, adj, color)) return true;               // WHITE: explore
        }

        color[node] = 2;
        return false;
    }
}
