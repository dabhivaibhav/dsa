package binary_tree.medium_problems;

import java.util.*;

/*
Problem: Bottom view of BT

Given root of binary tree, return the bottom view of the binary tree.
The bottom view of a binary tree is the set of nodes visible when the
tree is viewed from the bottom. Return nodes from the leftmost node to
the rightmost node. Also if 2 nodes are outside the shadow of the tree
and are at the same position then consider the node that appears later
in level traversal.

Example 1
Input : root = [20, 8, 22, 5, 3, null, 25, null, null, 10 ,14]
Output : [5, 10, 3, 14, 25]
Explanation : From left to right the path is as follows :
First we encounter node with value 5.
Then we have nodes 8 , 10 but from bottom only 10 will be visible.
Next we have 20 , 3 but from bottom only 3 will be visible.
Next we have 14 , 22 but from bottom only 14 will be visible.
Then we encounter node with value 25.

Example 2
Input : root = [20, 8, 22, 5, 3, 4, 25, null, null, 10 ,14]
Output : [5, 10, 4, 14, 25]
Explanation : From left to right the path is as follows :
First we encounter node with value 5.
Then we have nodes 8 , 10 but from bottom only 10 will be visible.
Next we have 20 , 3 and 4. The 3 and 4 will be nodes visible from
bottom but as the node 4 appears later from left to right , so only
node 4 will be considered visible. Next we have 14 , 22 but from
bottom only 14 will be visible. Then we encounter node with value 25.

Constraints:
            1 <= Number of Nodes <= 10^4
            -10^3 <= Node.val <= 10^3

 */
public class BottomViewOfBT {


    /*
     * WHAT THIS METHOD DOES:
     * This method finds the nodes that are visible when looking at a Binary Tree
     * straight up from the absolute bottom. It returns these values in a sequence
     * flowing from the leftmost vertical column to the rightmost vertical column.
     *
     * ---
     *
     * MENTAL MODEL & REAL-LIFE ANALOGY: THE SKYLINE STREET LIGHTS
     * Imagine a long street where buildings (nodes) are constructed on a strict grid.
     * The main landmark is marked as Column 0.
     * * 1. Building Coordinates:
     * - Branching out to the left shifts our column left (col - 1).
     * - Branching out to the right shifts our column right (col + 1).
     *
     * 2. The Overwriting Effect:
     * - Imagine you are standing underground, looking straight up at the street grid.
     * - For any single vertical block (column), a higher-up building (closer to the root)
     * gets completely blocked from your sight if a lower building is built directly
     * underneath it on a deeper floor.
     * - Therefore, you can ONLY see the lowest, deepest node that occupies that
     * specific column coordinate.
     *
     * To capture this view, we run a Breadth-First Search (BFS) conveyor belt moving
     * top-to-bottom. Every time we encounter a node at a column, it aggressively
     * overwrites whatever node was previously sitting there. By the time the conveyor
     * belt stops, only the lowest remaining nodes on each column survive.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. TreeMap<Integer, Integer> (The Bottom View Map):
     * - Why Key? The keys represent our horizontal column markers (e.g., -2, -1, 0, 1, 2).
     * - Why Value? The value stores the integer data of the node currently claiming that column.
     * - Reason: A TreeMap guarantees that its keys stay sorted in ascending order.
     * When we extract the values at the end, they naturally line up from left to right.
     *
     * 2. Queue<Pair> (The Level-Order Conveyor Belt):
     * - Why? We use a Breadth-First Search (BFS) framework to process nodes floor-by-floor.
     * - Reason: Traveling top-down guarantees that the shallowest nodes hit the map
     * first, and the deepest nodes hit it last, effortlessly overwriting the old entries.
     *
     * 3. Pair Class (The Spatial Package Container):
     * - Why? A standard Java Queue holds exactly one generic item.
     * - Reason: We must bundle each TreeNode object with its calculated horizontal
     * column index so it retains its placement information throughout the iteration.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Handle the empty tree edge case. If root is null, immediately return the empty list.
     * Step 2: Initialize our sorting TreeMap and our BFS Queue, inserting the root node at column 0.
     * Step 3: Run the level-order BFS traversal loop. For each element popped:
     * - Use .put(col, node.val) to map the column to the node's value. We do NOT check
     * if it exists; we intentionally let deep nodes overwrite higher ones.
     * - Push the left child into the queue with a column coordinate of col - 1.
     * - Push the right child into the queue with a column coordinate of col + 1.
     * Step 4: Once the tree is fully traversed, cycle through bottomViewMap.values().
     * Because the keys are auto-sorted, the results are collected in perfect left-to-right order.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - bottomViewMap.put(col, node.val);
     * This is the crucial twist line. In "Top View," we guarded this with an if (!contains)
     * statement. In Bottom View, we drop the guard entirely. Every new node on this column
     * kicks the old one out of the map. The last node processed wins!
     * * - queue.add(new Pair(node.left, col - 1));
     * Loads the left child onto the tracking belt while shifting its spatial location coordinate
     * one step west on the grid map.
     * * - for (int value : bottomViewMap.values()) { result.add(value); }
     * Pulls the surviving values out of our storage map. The self-sorting layout of the
     * TreeMap automatically strings them together in a clean left-to-right sweep.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * * Time Complexity: O(n log n)
     * - We process all n nodes in the tree exactly once through our BFS loop.
     * - Inside the loop, every single node performs a .put() operation into our TreeMap.
     * TreeMap lookups and modifications cost logarithmic time relative to the number of columns
     * tracked, which is O(log w), where w is the maximum width of the tree.
     * - Since the width w can scale up to n in highly unbalanced structures, each insertion
     * costs up to O(log n).
     * - Doing this across all n nodes yields a strict overall runtime of **O(n log n)**.
     *
     * * Space Complexity: O(n)
     * - The bottomViewMap retains exactly one entry for each unique vertical column, which
     * consumes at most O(w) space, scaling up to O(n) in a skewed line tree.
     * - The BFS execution queue holds at most the maximum width of a single tree level,
     * which requires O(n) space on a fully populated bottom floor.
     * - Therefore, the total auxiliary memory consumption scales linearly as **O(n)**.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * Top View and Bottom View are two sides of the exact same coin.
     * Both problems rely on a stable **BFS + Coordinate System** foundation.
     * - If you want the TOP view: Use a guard statement (if (!containsKey)) to protect
     * the very first node that lands on that column coordinate.
     * - If you want the BOTTOM view: Remove the guard completely. Let the .put() operation
     * continually overwrite older values.
     * Understanding this relationship allows you to solve two classic interview problems with
     * a single unified mental framework.
     */
    public List<Integer> bottomView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        // Automatically keeps the columns ordered cleanly from left to right (-2, -1, 0, 1...)
        TreeMap<Integer, Integer> bottomViewMap = new TreeMap<>();

        // BFS Level-Order queue using type-safe Pair container
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                Pair current = queue.poll();
                TreeNode node = current.node;
                int col = current.col;

                // YOUR INTUITION IN ACTION: Always update the column!
                // The last node processed in this column will naturally be the bottom-most one.
                bottomViewMap.put(col, node.val);

                // Add left and right children to continue tracking coordinates down the rows
                if (node.left != null) {
                    queue.add(new Pair(node.left, col - 1));
                }
                if (node.right != null) {
                    queue.add(new Pair(node.right, col + 1));
                }
            }
        }

        // Extract the values from our map in sequential left-to-right order
        result.addAll(bottomViewMap.values());

        return result;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Pair {
        TreeNode node;
        int col;

        Pair(TreeNode node, int col) {
            this.node = node;
            this.col = col;
        }
    }
}
