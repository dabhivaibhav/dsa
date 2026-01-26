package recursion;

import java.util.ArrayList;
import java.util.List;

/*
Given an integer M and an undirected graph with N vertices (zero indexed) and E edges. The goal is to determine whether
the graph can be coloured with a maximum of M colors so that no two of its adjacent vertices have the same colour applied to them.
In this context, colouring a graph refers to giving each vertex a different colour. If the colouring of vertices is
possible then return true, otherwise return false.

Example 1
Input : N = 4 , M = 3 , E = 5 , Edges = [ (0, 1) , (1, 2) , (2, 3) , (3, 0) , (0, 2) ]
Output : true
Explanation : Consider the three colors to be red, green, blue.
We can color the vertex 0 with red, vertex 1 with blue, vertex 2 with green, vertex 3 with blue.
In this way we can color graph using 3 colors at most.

Example 2
Input : N = 3 , M = 2 , E = 3 , Edges = [ (0, 1) , (1, 2) , (0, 2) ]
Output : false
Explanation : Consider the two colors to be red, green.
We can color the vertex 0 with red, vertex 1 with green.
As the vertex 2 is adjacent to both vertex 1 and 0 , so we cannot color with red and green.
Hence as we could not color all vertex of graph we return false.

Constraints:
            1 <= N <= 20
            1 <= E <= (N*(N-1)/2)
            1 <= M <= N
 */
public class MColorProblem {

    public static void main(String[] args) {
        int n = 4, m = 3, e = 5;
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        adj[0].add(1);
        adj[1].add(2);
        adj[2].add(3);
        adj[3].add(0);
        adj[0].add(2);
        int[] color = new int[n];
        System.out.println(solve(0, n, m, color, adj));


    }

    /**
     * solve (M-Coloring Problem using Backtracking)
     * <p>
     * What it does:
     * Determines if a graph can be colored with at most M colors such that no two
     * adjacent vertices (nodes connected by an edge) share the same color.
     * This is a classic constraint satisfaction problem.
     * <p>
     * Core Intuition:
     * We try to color each node one by one, starting from node 0.
     * For each node, we pick a color from the available palette (1 to M).
     * Before finalizing a color, we check our neighbors: "Is anyone I'm connected
     * to already using this color?"
     * If the move is valid, we move to the next node. If we get stuck, we go
     * back and change the color of the previous node.
     * <p>
     * Why this is a backtracking problem:
     * We make a choice (assign a color to a node).
     * We explore if this choice leads to a complete solution for all nodes.
     * If we reach a state where no color (1 to M) can be safely assigned to a node,
     * we backtrack to the previous node and try its next available color option.
     * <p>
     * Step-by-step explanation:
     * node: The current vertex index we are trying to color.
     * n: Total number of nodes in the graph.
     * m: The maximum number of colors allowed.
     * color[]: An array where index 'i' stores the color assigned to node 'i' (0 means uncolored).
     * adj: The adjacency list where adj[i] contains all neighbors of node 'i'.
     * <p>
     * Base Case:
     * If node == n, we have successfully assigned a valid color to every single
     * vertex. We return true to stop the search and signal success.
     * <p>
     * Recursive Case: Loop through every color i from 1 to m.
     * Check isSafe: Can this node be painted with color i?
     * <p>
     * If Safe:
     * Assign: color[node] = i.
     * Recurse: Try to color the next node (node + 1).
     * Success Check: If the recursive call returns true, keep the color and
     * pass true up the chain.
     * Backtrack: If it returns false, reset color[node] = 0 and try color i + 1.
     * <p>
     * How isSafe works:
     * It looks at the adjacency list for the current node.
     * It iterates through every neighbor.
     * If any neighbor already has the same color we are trying to use, it returns
     * false (unsafe).
     * If no neighbors conflict, it returns true.
     * <p>
     * Example:
     * Imagine a Triangle graph (Nodes 0, 1, 2) and M = 3.
     * Color Node 0 -> Red.
     * Color Node 1 -> Blue (Safe, 0 is Red).
     * Color Node 2 -> Green (Safe, 0 is Red, 1 is Blue).
     * All nodes colored! Return true.
     * <p>
     * Complexity:
     * Time: O(M^N) - In the worst case, we try M colors for N nodes.
     * Space: O(N) - For the color array and the recursion stack depth.
     */
    private static boolean solve(int node, int n, int m, int[] color, List<Integer>[] adj) {
        // Base Case All nodes colored
        if (node == n) return true;

        // Try every color (1 to m)
        for (int i = 1; i <= m; i++) {
            if (isSafe(node, i, color, adj)) {
                color[node] = i; // "Choose"

                if (solve(node + 1, n, m, color, adj)) return true; // Explore

                color[node] = 0; // Backtrack
            }
        }
        return false;
    }

    private static boolean isSafe(int node, int color, int[] colorArr, List<Integer>[] adj) {
        for (int neighbour : adj[node]) {
            if (colorArr[neighbour] == color) return false;
        }
        return true;
    }
}
