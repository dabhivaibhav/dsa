package binary_tree.medium_problems;

/*
Leetcode 199: Binary Tree Right Side View

Given the root of a binary tree, imagine yourself standing on the right side of it,
return the values of the nodes you can see ordered from top to bottom.

Example 1:
Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]

Example 2:
Input: root = [1,2,3,4,null,null,null,5]
Output: [1,3,4,5]

Example 3:
Input: root = [1,null,3]
Output: [1,3]

Example 4:
Input: root = []
Output: []

Constraints:
            The number of nodes in the tree is in the range [0, 100].
            -100 <= Node.val <= 100
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeRightSide {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.left.left = new TreeNode(8);
        root.right.left.right = new TreeNode(9);
        root.right.right.right = new TreeNode(10);

    }

    /*
     * WHAT THIS METHOD DOES:
     * This method finds the values of the nodes that are visible when looking at a
     * Binary Tree straight from the right side. The final output list captures these
     * visible nodes in sequence from the top level (the root) down to the deepest floor.
     *
     * ---
     *
     * THE "SILHOUETTE SCAN" PATTERN (BINARY TREE RIGHT SIDE VIEW)
     *
     * Your Thought Process & Intuition:
     * 1. THE PERSPECTIVE: Imagine standing on the far right side of the tree, looking at
     * its side profile. Your eyes scan horizontally, level-by-level (row-by-row),
     * from top to bottom.
     *
     * 2. THE OVERLAP RESOLUTION (ROW OVER COLUMNS): Unlike Top/Bottom views which care
     * about columns, the Right Side View is strictly a ROW problem. On any given
     * horizontal layer, multiple nodes can crowd the same row. However, the node
     * furthest to the right physically blocks your line of sight to all other nodes
     * sitting to its left. Therefore, every single row contributes exactly ONE visible node.
     *
     *
     *
     * 3. THE CORE MECHANISM: A standard Level-Order Traversal (BFS) processes nodes across
     * a level from Left to Right. Because a Queue is First-In, First-Out (FIFO), the
     * leftmost node comes out first, and the rightmost node comes out last. By using
     * a loop that freezes the size of the current row (levelSize), the very last
     * element polled in that loop (i == levelSize - 1) is guaranteed to be the
     * rightmost survivor of that row.
     *
     * 4. THE EDGE CASES (SKEWED TREES): This logic natively handles a left-skewed tree.
     * If a row only contains a single left-child node, levelSize becomes 1. That node
     * is simultaneously the first and the last element of its row, so it correctly
     * registers as visible since there are no right-side branches blocking it from above.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Queue<TreeNode> (The Level-by-Level Isolator):
     * - Why? We use a standard First-In, First-Out (LinkedList acting as a Queue)
     * to guarantee that nodes are explored uniformly from left to right within each layer.
     * - Reason: By combining FIFO order with the levelSize loop snapshot, we can easily
     * identify when we have reached the end of a floor without needing to store extra
     * coordinate metrics.
     *
     * 2. List<Integer> (The Result Collection):
     * - Why? A standard dynamic array (ArrayList) is used to sequentially collect the
     * rightmost value discovered at the end of each processed floor.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Handle the empty tree guardrail. If root == null, return the empty list.
     * Step 2: Initialize a FIFO Queue and load the root node onto it to kickstart the system.
     * Step 3: Run the outer BFS loop while the queue contains nodes.
     * - Capture a snapshot of the current horizontal floor size: int levelSize = queue.size().
     * Step 4: Run an inner loop exactly levelSize times to empty the current level:
     * - Poll the next node off the front of the queue.
     * - Check if this is the final index of the current row: if (i == levelSize - 1).
     * If true, add its value to our result list.
     * - Push any existing left and right children to the back of the queue for the next generation.
     * Step 5: Return the populated list once all levels are completely drained.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - int levelSize = queue.size();
     * Captures the exact horizontal item boundary for the current level. Any children added
     * during this round will sit safely behind this boundary line.
     * * - if (i == levelSize - 1) { result.add(current.val); }
     * Your core filtering logic. Because nodes are unpacked left-to-right, the index reaching
     * the final element of the floor size snapshot flags the single node exposed to the right-hand view.
     * * - if (current.left != null) queue.add(current.left);
     * Enqueues children left-first, maintaining the strict left-to-right reading pattern across the tree rows.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * → Time Complexity: O(N) — Every node is visited and queued exactly once.
     * Each node undergoes constant time O(1) operations (one push, one pop, and an
     * index validation check). Thus, the runtime scales completely linearly with the
     * total number of nodes N in the tree.
     *
     * → Space Complexity: O(D) — Where D is the maximum diameter of the tree (the size of
     * the bottom row). In the worst case (a perfect binary tree), the queue holds up to
     * N/2 nodes at the lowest level, which scales bound-wise to O(N). For highly skewed,
     * stick-like trees, the queue footprint scales down to an optimal O(1) size.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * The Right Side View is a beautiful demonstration of how a standard Level-Order Traversal
     * can be modified using index filtering.
     * - For a Right Side View: Look for the end of the layer loop (i == levelSize - 1).
     * - For a Left Side View: Look for the beginning of the layer loop (i == 0).
     *
     * If an interviewer asks you to optimize the space complexity from O(N) down to O(H)
     * (where H is the tree height), you can transition this logic into a Depth-First Search (DFS).
     * By prioritizing right-side branches first during the recursion (Root -> Right -> Left) and
     * tracking your current depth layer, you can record a node value the very first time you
     * step onto a new depth level.
     */
    private static List<Integer> rightSide(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (i == levelSize - 1) {
                    result.add(current.val);
                }

                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            }
        }
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

}
