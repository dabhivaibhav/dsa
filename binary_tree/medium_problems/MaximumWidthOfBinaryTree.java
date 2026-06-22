package binary_tree.medium_problems;

import java.util.LinkedList;
import java.util.Queue;

/*
662. Maximum Width of Binary Tree

Given the root of a binary tree, return the maximum width of the given tree.

The maximum width of a tree is the maximum width among all levels.

The width of one level is defined as the length between the end-nodes
(the leftmost and rightmost non-null nodes), where the null nodes between the
end-nodes that would be present in a complete binary tree extending down to
that level are also counted into the length calculation.

It is guaranteed that the answer will in the range of a 32-bit signed integer.

Example 1:
Input: root = [1,3,2,5,3,null,9]
Output: 4
Explanation: The maximum width exists in the third level with length 4 (5,3,null,9).

Example 2:
Input: root = [1,3,2,5,null,null,9,6,null,7]
Output: 7
Explanation: The maximum width exists in the fourth level with length 7 (6,null,null,null,null,null,7).

Example 3:
Input: root = [1,3,2,5]
Output: 2
Explanation: The maximum width exists in the second level with length 2 (3,2).

Constraints:
            The number of nodes in the tree is in the range [1, 3000].
            -100 <= Node.val <= 100
 */
public class MaximumWidthOfBinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        System.out.println(widthOfBinaryTree(root));
    }

    /*
     *
     * WHAT THIS METHOD DOES:
     * This method calculates the maximum horizontal "width" among all levels of a
     * binary tree. The width of a single level is defined as the number of slots
     * between the leftmost and rightmost non-null nodes in that row, inclusive of
     * any empty space (null nodes) that lie between them.
     *
     * ---
     *
     * THE "HEAP-INDEXED CODES" PATTERN (MAXIMUM WIDTH OF BINARY TREE)
     *
     * Your Thought Process & Intuition:
     * 1. THE PERSPECTIVE: Finding the true width means we cannot simply count the nodes
     * present on a level (like a basic BFS size snapshot), because the problem demands
     * that we include empty null spaces trapped between the edges. To capture this
     * layout, we need a way to track the exact spatial positioning of every node.
     *
     *
     *
     * 2. THE HEAP-INDEXING FORMULA: Imagine flattening out the tree structure into a
     * single consecutive array layout, exactly like a standard binary heap structure.
     * If we assign a virtual "seat number" or "index" to a parent node, we can calculate
     * the exact spatial seats of its children automatically without filling in null nodes:
     * → Left Child Index = 2 * parentIndex
     * → Right Child Index = 2 * parentIndex + 1
     *
     * 3. THE WIDTH CALCULATION: By using a level-by-level Level-Order Traversal (BFS),
     * we can cleanly isolate each floor. For any given level, the first node pulled out
     * tells us the absolute firstIndex (leftmost wall) and the very last node pulled
     * out tells us the lastIndex (rightmost wall). The total space width of that row is
     * simply calculated as: lastIndex - firstIndex + 1.
     *
     * 4. THE OVERFLOW PROTECTION (INDEX NORMALIZATION): In a highly asymmetric, deeply
     * nested skewed tree, the seat numbers grow exponentially (2^h). By level 31, standard
     * 32-bit integers will completely wrap around and overflow. To prevent this, we
     * subtract the very first index of a level (levelMinIndex) from every single node's
     * index on that same floor. This recalculates the floor's indices relative to 0,
     * resetting the numbers while maintaining the exact same distance gap!
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Queue<Pair> (The Spatial Coordinate Tracker):
     * - Why? A standard Queue can only track the tree nodes. We need a way to lock each
     * node to its changing virtual coordinate seat number.
     * - Reason: A custom typed Pair object container binds the TreeNode reference
     * directly to its calculated coordinate integer, tracking its exact geometric position
     * as it flows through the BFS engine.
     *
     * 2. Math.max Operations (The High-Water Mark):
     * - Why? We track a running tracking primitive variable maxWidth.
     * - Reason: As each row finishes its loop, we evaluate its width against our historic
     * maximum, instantly capturing the widest section of the architecture.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Boundary validation check. If root == null, return 0.
     * Step 2: Initialize our level-order processing FIFO Queue and insert the root node
     * paired up with an initial seat location of 0.
     * Step 3: Run the outer BFS traversal loop while nodes exist on the conveyor belt.
     * - Record the current level's snapshot size: int levelSize = queue.size().
     * - Peak at the front item to establish our baseline normalization anchor: int levelMinIndex = queue.peek().index.
     * Step 4: Run the inner collection loop exactly levelSize times to empty the current level:
     * - Poll the next pair container off the line.
     * - Normalize its index tracking value: int normalizedIndex = current.index - levelMinIndex.
     * - If i == 0, save this as our leftmost edge marker (firstIndex).
     * - If i == levelSize - 1, save this as our rightmost edge marker (lastIndex).
     * - Child Expansion: If node.left exists, push it into the queue with a seat label of 2 * normalizedIndex.
     * If node.right exists, push it with a seat label of 2 * normalizedIndex + 1.
     * Step 5: Calculate the row's total horizontal space footprint: lastIndex - firstIndex + 1.
     * Update maxWidth if this floor breaks the record.
     * Step 6: Return the accumulated maximum width value.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - int levelMinIndex = queue.peek().index;
     * Captures the absolute minimum index coordinate representing the leftmost node of the row.
     * This serves as our shifting math baseline anchor to reset our coordinate indices back to 0.
     * * - int normalizedIndex = current.index - levelMinIndex;
     * Your safety mechanism. By subtracting the floor minimum, we shrink massive tracking integers
     * down to tiny, overflow-safe values without altering the geometric distance between the nodes.
     * * - int currentWidth = lastIndex - firstIndex + 1;
     * Applies the index distance formula. Even if middle nodes are missing, the distance between
     * the boundary seat indices perfectly captures the true geometric span of the row.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * → Time Complexity: O(N) — Every node is visited exactly once.
     * The underlying structure is a standard Breadth-First Search traversal loop. Each node is
     * queued and dequeued exactly once, and all inner operations (index recalculations and checks)
     * run in constant O(1) time. Therefore, the total execution time scales linearly with N.
     *
     * → Space Complexity: O(N) — The auxiliary memory footprint is determined by the queue size.
     * In the worst-case scenario (a perfectly balanced complete binary tree), the lowest leaf level
     * houses up to N/2 nodes simultaneously inside our processing queue. This establishes a linear
     * upper bound allocation footprint of O(N).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * Maximum Width is an elegant showcase of mapping alternative mathematical models onto trees.
     * - Do not allocate real memory space for empty null tree nodes just to count them.
     * Instead, assign virtual coordinates using the array-heap indices strategy.
     * - Always keep integer limits in mind. Whenever you are multiplying indices by 2 at every
     * level of a tree depth path (2^h), you must introduce an normalization step at the start
     * of each row loop to prevent disastrous integer overflow bugs.
     */
    public static int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        int maxWidth = 0;
        Queue<Pair> queue = new LinkedList<>();

        // Push the root node into the queue at seat number 0 (or 1)
        queue.offer(new Pair(root, 0));

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // To prevent integer overflow on deeply nested, skewed trees,
            // we can normalize indices at each level by subtracting the first index.
            int levelMinIndex = queue.peek().index;

            int firstIndex = 0;
            int lastIndex = 0;

            // Process all nodes currently present on this specific level
            for (int i = 0; i < levelSize; i++) {
                Pair current = queue.poll();
                TreeNode node = current.node;

                // Normalize the index relative to the start of this row
                int normalizedIndex = current.index - levelMinIndex;

                // If it's the first node of the level loop, it's the leftmost node
                if (i == 0) firstIndex = normalizedIndex;
                // If it's the last node of the level loop, it's the rightmost node
                if (i == levelSize - 1) lastIndex = normalizedIndex;

                // Push left child: index formula is 2 * i
                if (node.left != null) {
                    queue.offer(new Pair(node.left, 2 * normalizedIndex));
                }

                // Push right child: index formula is 2 * i + 1
                if (node.right != null) {
                    queue.offer(new Pair(node.right, 2 * normalizedIndex + 1));
                }
            }

            // Calculate the width of the row we just completed
            int currentWidth = lastIndex - firstIndex + 1;
            maxWidth = Math.max(maxWidth, currentWidth);
        }

        return maxWidth;
    }

    static class Pair {
        TreeNode node;
        int index;

        Pair(TreeNode node, int index) {
            this.node = node;
            this.index = index;
        }
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
