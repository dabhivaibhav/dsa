package binary_tree.medium_problems;

/*
Leetcode 236. Lowest Common Ancestor of a Binary Tree

Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined
between two nodes p and q as the lowest node in T that has both p and q as descendants
(where we allow a node to be a descendant of itself).”

 Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [1,2], p = 1, q = 2
Output: 1


Constraints:
            The number of nodes in the tree is in the range [2, 105].
            -10^9 <= Node.val <= 10^9
            All Node.val are unique.
            p != q
            p and q will exist in the tree.
 */
public class LowestCommonAncestorBT {


    public static void main(String[] args) {

    }

    /*
     * WHAT THIS METHOD DOES:
     * This method finds the Lowest Common Ancestor (LCA) of two specific nodes, p
     * and q, inside a binary tree. The LCA is defined as the deepest, lowest-down
     * node in the tree that has both p and q as descendants (where we allow a
     * node to be a descendant of itself).
     *
     * ---
     *
     * THE "BEACON SIGNAL" PATTERN (LOWEST COMMON ANCESTOR)
     *
     * Your Thought Process & Intuition:
     * 1. THE PERSPECTIVE: Finding the Lowest Common Ancestor (LCA) is a "Search and Rescue"
     * mission. Imagine deploying two search drones from the current node—one down the left
     * subtree and one down the right subtree. Their goal is to find targets 'p' or 'q'
     * and send a signal (the node reference) back up to you.
     *
     *
     *
     * 2. THE JUNCTION BOX (THE BREAKTHROUGH):
     * → If a node receives a valid signal from BOTH its left drone and its right drone
     * (left != null && right != null), it means 'p' and 'q' are located in completely
     * different subtrees underneath it. This current node is the exact junction point
     * where they split, making it the LCA!
     * → If only one drone returns a signal, it means both targets reside down that single
     * branch (or the LCA was already found down there), so we pass that signal up.
     *
     * 3. THE OVERRIDE TRIGGER (p IS ANCESTOR OF q): If we hit a node that matches either 'p'
     * or 'q', we immediately stop searching deeper and return that node. If 'p' happens to
     * be the parent of 'q', we will encounter 'p' first, return it immediately, and never
     * even look at its children. 'p' naturally becomes the LCA by floating all the way to the top.
     *
     * ---
     *
     * WHY WE MUST USE POSTORDER TRAVERSAL:
     * This problem is fundamentally a BOTTOM-UP decision-making problem.
     *
     * In a Preorder traversal (Top-Down), a node tries to make a decision before knowing
     * anything about its children. But for LCA, a parent node CANNOT know if it is the
     * common ancestor until it receives the results from its left and right subtrees first.
     *
     * Postorder Traversal explicitly dictates the execution order:
     * 1. Explore Left Subtree completely.
     * 2. Explore Right Subtree completely.
     * 3. Process the Current Node (Make the final decision based on Left and Right results).
     *
     * By using Postorder, we guarantee that the "found it!" signals bubble up from the leaf
     * nodes first, allowing the lowest possible common ancestor to identify itself and
     * return to the top.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. The Call Stack Reference Tracking (No Extra Collections):
     * - Why? Unlike iterative solutions that might keep a map of parent pointers to re-trace
     * paths backwards, this approach stores nothing but simple TreeNode memory address
     * references across the execution call stack frames.
     * - Reason: By letting the recursion framework pass back the actual nodes (p, q, or
     * an LCA junction), the system automatically uses tree topology to identify the answer
     * without wasting memory allocating extra sets or maps.
     *
     * 2. Short-Circuit Base Case Checks:
     * - Why? The check if(root == p || root == q) acts as an early-termination trigger.
     * - Reason: If a node is found, there is no structural reason to run recursive searches
     * on its sub-branches, immediately reducing work for the runtime system.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Base validation. If the current structural pointer hits null, return null.
     * Step 2: Check target proximity. If root == p or root == q, we found a target!
     * Return root back to the parent frame as a tracking beacon.
     * Step 3: Run the Postorder exploration steps. Recursively dispatch drones down both sides:
     * - leftSignal = findLowestCommonAncestor(root.left, p, q)
     * - rightSignal = findLowestCommonAncestor(root.right, p, q)
     * Step 4: Evaluate the returned signals bottom-up at the current junction frame:
     * - Scenario A: If both leftSignal and rightSignal are not null, this node is the
     * converging home base. Return root.
     * - Scenario B: If leftSignal is valid but right is null, forward leftSignal upward.
     * - Scenario C: Otherwise, pass rightSignal up (which covers both the case where right
     * has the values, or both sides returned null).
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - if(root == p || root == q) return root;
     * Your override beacon trigger. When a drone stumbles onto either target, it fires off its signal
     * flare by returning the node pointer, halting any deep scans below it.
     * * - TreeNode leftSignal  = findLowestCommonAncestor(root.left, p, q);
     * Commands the left drone to completely map out and process the left structural wing before
     * the parent takes any action.
     * * - if(leftSignal != null && rightSignal != null) return root;
     * Your key convergence realization. Seeing valid signals flashing simultaneously from both channels
     * confirms that the paths split right here, identifying this node as the absolute LCA.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * → Time Complexity: O(N) — In the worst case, we visit every node in the tree once.
     * The recursive engine performs a postorder walk across the tree. In scenarios where p
     * and q are located at opposite ends of the lower leaves, the program inspects all N nodes.
     * Because the work per node is restricted to basic reference equivalence checks (O(1) operations),
     * the runtime scales completely linearly as **O(N)**.
     *
     * → Space Complexity: O(H) — The recursive call stack goes as deep as the height of the tree.
     * No auxiliary arrays or maps are instantiated. The memory footprint relies entirely on stack frames
     * generated during deep tree navigation. In a completely balanced binary tree structure, this
     * requires O(\log N) space; if the tree collapses into a fully skewed line configuration,
     * stack memory expands to **O(N)**.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * LCA is the classic example of why **Postorder Traversal** is irreplaceable for bottom-up
     * information collection.
     * - If you need to make decisions based on what is happening *below* you, let your recursive
     * calls execute first, capture their returned signals, and write your decision-making
     * logic *after* those calls.
     *
     * If an interviewer changes the problem to a **Binary Search Tree (BST)**, remember that you
     * can throw away this bottom-up approach! Because a BST has sorted properties (left is smaller,
     * right is larger), you can use a clean top-down Preorder approach. If both targets are larger than
     * you, walk right; if both are smaller, walk left. The very first node where the targets split
     * paths is automatically your LCA!
     */
    private static TreeNode findLowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root == p || root == q) {
            return root;
        }

        // STEP DOWN: Send drones down to explore left and right subtrees
        TreeNode leftSignal = findLowestCommonAncestor(root.left, p, q);
        TreeNode rightSignal = findLowestCommonAncestor(root.right, p, q);

        // BOTTOM-UP DECISION MAKING: Analyzing the returned signals

        // Scenario A: Both drones found something!
        // This node is the junction box where p and q split.
        if (leftSignal != null && rightSignal != null) {
            return root;
        }

        // Scenario B: Only one drone found something.
        // It means BOTH p and q live down that single branch, or we found the LCA below us.
        if (leftSignal != null) {
            return leftSignal;
        }

        return rightSignal;
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
