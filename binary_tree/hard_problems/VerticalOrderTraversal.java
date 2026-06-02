package binary_tree.hard_problems;

import java.util.*;

/*
Leetcode 987: Vertical Order Traversal of a Binary Tree

Given the root of binary tree, calculate the vertical order traversal of the binary tree.

For each node at position (row, col), its left and right children will be at positions
(row + 1, col - 1) and (row + 1, col + 1) respectively. The root of the tree is at (0,0).

The vertical order traversal of a binary tree is a list of top to bottom orderings for each
column index starting from the leftmost column and ending on the rightmost column. There may
be multiple nodes in the same row and same column. In such a case, sort these nodes by their values.

Return the vertical order traversal of the binary tree.

Example 1:


Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
Explanation:
Column -1: Only node 9 is in this column.
Column 0: Nodes 3 and 15 are in this column in that order from top to bottom.
Column 1: Only node 20 is in this column.
Column 2: Only node 7 is in this column.
Example 2:


Input: root = [1,2,3,4,5,6,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
Column -2: Only node 4 is in this column.
Column -1: Only node 2 is in this column.
Column 0: Nodes 1, 5, and 6 are in this column.
          1 is at the top, so it comes first.
          5 and 6 are at the same position (2, 0), so we order them by their value, 5 before 6.
Column 1: Only node 3 is in this column.
Column 2: Only node 7 is in this column.
Example 3:


Input: root = [1,2,3,4,6,5,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
This case is the exact same as example 2, but with nodes 5 and 6 swapped.
Note that the solution remains the same since 5 and 6 are in the same location and
should be ordered by their values.


Constraints:

The number of nodes in the tree is in the range [1, 1000].
0 <= Node.val <= 1000

 */
public class VerticalOrderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        root.left.left.right = new TreeNode(9);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(11);
        System.out.println(verticalTraversal(root));
    }


    /*
     * WHAT THIS METHOD DOES:
     * This method groups a binary tree's nodes into distinct vertical columns,
     * scanning from the absolute leftmost column to the absolute rightmost column.
     * Within each individual column, nodes must be sorted from top to bottom (by row).
     * If two or more nodes share the exact same column AND the exact same row,
     * they must be sorted numerically by their actual values.
     *
     * ---
     *
     * MENTAL MODEL & REAL-LIFE ANALOGY: THE CITY APARTMENT COMPLEX BUILDER
     * Imagine a massive, multi-level apartment complex built on an grid system.
     * The main office lobby (the Root) is positioned precisely at grid coordinate (0, 0).
     *
     * 1. Building Guidelines:
     * - Moving down a floor increases the row index (row + 1).
     * - Building a unit out to the left shifts the column left (col - 1).
     * - Building a unit out to the right shifts the column right (col + 1).
     *
     * 2. The Mail Delivery Challenge:
     * - The city postal service wants to drop off mail column-by-column, starting
     * from the westernmost block (lowest negative column) and ending at the
     * easternmost block (highest positive column).
     * - On any single block, the carrier delivers mail floor-by-floor (top-to-bottom).
     * - If multiple tenants live in the exact same apartment unit (same floor, same block),
     * the mail carrier strictly sorts their packages alphabetically by name (numerical values)
     * to avoid arguments.
     *
     * To organize this logistics nightmare, the delivery manager uses a nested sorting bin
     * system (TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>>) to automatically
     * keep every package perfectly segmented and pre-sorted as soon as it arrives.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     * This hard problem is entirely a challenge of data organization. Let's look at why
     * this specific nested structure is mandatory:
     *
     * 1. TreeMap<Integer, ...> (The Outer Map - Columns / X-Axis):
     * - Why? We need to print columns from leftmost to rightmost (e.g., Column -2, then -1, then 0, then 1).
     * - Reason: A regular HashMap does not preserve ordering. A TreeMap automatically
     * sorts its keys in ascending order. This guarantees we read out columns left-to-right.
     *
     * 2. TreeMap<Integer, ...> (The Inner Map - Rows / Y-Axis):
     * - Why? Inside a single column, we must sort nodes from top to bottom (Row 0, then 1, then 2).
     * - Reason: Just like columns, we need rows sorted automatically. Another TreeMap ensures
     * higher levels are naturally processed before lower levels.
     *
     * 3. PriorityQueue<Integer> (The Deepest Value Container - Collisions):
     * - Why? If two nodes land on the exact same (X, Y) coordinate, the problem demands
     * we sort them by value.
     * - Reason: A PriorityQueue (Min-Heap) automatically sorts numbers in ascending
     * order. As elements are pulled out, the smallest number naturally emerges first.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Initialize the master 3D nested sorting bin map (nodes).
     * Step 2: Initialize a tracking Queue (todo) to execute a Breadth-First Search (BFS).
     * We package each node with its coordinates into a custom wrapper object (Pair).
     * Step 3: Run the BFS engine. For every node popped:
     * - Use .putIfAbsent() to instantly manufacture maps/queues if visiting a coordinate for the first time.
     * - Push the node's value into the coordinate's specific PriorityQueue.
     * - Append the left child with updated coordinates (col - 1, row + 1).
     * - Append the right child with updated coordinates (col + 1, row + 1).
     * Step 4: Flatten the storage bins into the final list. Because the maps are TreeMaps,
     * looping through them naturally extracts columns left-to-right, and rows top-to-bottom.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - Queue<Pair> todo = new LinkedList<>();
     * Sets up our classic BFS level-order conveyor belt, tracking the spatial position of each node.
     * * - nodes.putIfAbsent(x, new TreeMap<>());
     * Checks if our master layout has encountered this column (x) yet. If not, it reserves a
     * new chronological map for its floors.
     * * - nodes.get(x).putIfAbsent(y, new PriorityQueue<>());
     * Checks if this specific floor (y) inside column x exists. If not, initializes a Min-Heap.
     * * - nodes.get(x).get(y).offer(temp.val);
     * Drops the value into the Min-Heap. If another node lands here later, the Min-Heap will
     * house them together and auto-sort them.
     * * - for (TreeMap<Integer, PriorityQueue<Integer>> ys : nodes.values()) { ... }
     * The flattening cycle. We iterate through the values of the outer map. Because it's sorted
     * by keys, we visit the column lists in perfect sequential order from left to right.
     * * - while (!pq.isEmpty()) { col.add(pq.poll()); }
     * Drains the Min-Heap for that specific cell. This safely transfers the values into the
     * final column tracking list in sorted numeric order.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * * Time Complexity: O(n log n)
     * Let's dissect the time spent during the two main phases of execution:
     * * 1. The BFS Phase: We visit all n nodes exactly once. For each node, we insert its value
     * into a nested structure of TreeMaps and a PriorityQueue.
     * - Finding or inserting keys in a TreeMap of size k takes O(\log k) time.
     * - Inserting into a PriorityQueue takes logarithmic time relative to the number of elements in it.
     * In the worst case (a highly asymmetric or fully overlapping layout), the maximum width/depth
     * scales with n, making each insertion take up to O(\log n) time. Doing this for n nodes
     * takes O(n \log n) time.
     * * 2. The Flattening Phase: We iterate through every node value across all columns and rows.
     * Popping elements out of a PriorityQueue takes O(\log k) time per element. Processing all
     * n nodes out of their respective sorting heaps takes a collective O(n \log n) time.
     * * Combining both stages, the overall runtime scales strictly as O(n log n).
     *
     * * Space Complexity: O(n)
     * - The nested nodes structure ultimately retains information for every single node in the
     * tree, consuming O(n) space.
     * - The BFS traversal queue (todo) holds at most the maximum width of a single level, which
     * is bounded by O(n) in a complete binary tree.
     * - Therefore, the absolute total memory footprint scales linearly as O(n).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * 1. The Multi-Dimensional Sorting Trick:
     * When a tree problem demands that you sort nodes across multiple independent
     * constraints (X-coordinates, Y-coordinates, and Node values), do not try to
     * sort the tree directly. Instead, project the tree into a custom-tailored data
     * structure architecture that enforces those sorting constraints automatically.
     *
     * 2. Recognizing Coordinates in Trees:
     * Whenever an interview question mentions terms like "Vertical," "Columns,"
     * "Top View," or "Bottom View," you should immediately think of a coordinate
     * indexing system. Establish a virtual (X, Y) grid layout by mapping:
     * - Left child: (x - 1, y + 1)
     * - Right child: (x + 1, y + 1)
     *
     * 3. HashMap vs. TreeMap in Interviews:
     * If an interviewer asks you how to optimize this, you can mention that using a
     * standard HashMap instead of a TreeMap drops the insertion time down from logarithmic
     * to constant time. However, you would then have to track the minimum and maximum
     * column indices manually and sort them at the very end. The TreeMap approach trades
     */
    public static List<List<Integer>> verticalTraversal(TreeNode root) {
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> nodes = new TreeMap<>();

        Queue<Pair> todo = new LinkedList<>();

        // Push root node with coordinates (0,0)
        todo.offer(new Pair(root, 0, 0));

        // Perform BFS
        while (!todo.isEmpty()) {
            Pair p = todo.poll();
            TreeNode temp = p.node;
            int x = p.vertical;
            int y = p.level;

            // Add node value to map
            nodes.putIfAbsent(x, new TreeMap<>());
            nodes.get(x).putIfAbsent(y, new PriorityQueue<>());
            nodes.get(x).get(y).offer(temp.val);

            // If left child exists, push to queue
            if (temp.left != null) {
                todo.offer(new Pair(temp.left, x - 1, y + 1));
            }

            // If right child exists, push to queue
            if (temp.right != null) {
                todo.offer(new Pair(temp.right, x + 1, y + 1));
            }
        }

        // Final answer
        List<List<Integer>> ans = new ArrayList<>();

        // Iterate through map to build result
        for (TreeMap<Integer, PriorityQueue<Integer>> ys : nodes.values()) {
            List<Integer> col = new ArrayList<>();
            for (PriorityQueue<Integer> pq : ys.values()) {
                while (!pq.isEmpty()) {
                    col.add(pq.poll());
                }
            }
            ans.add(col);
        }

        return ans;
    }

    static class Pair {
        TreeNode node;
        int vertical;
        int level;

        Pair(TreeNode n, int v, int l) {
            node = n;
            vertical = v;
            level = l;
        }
    }


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int x) {
            val = x;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
