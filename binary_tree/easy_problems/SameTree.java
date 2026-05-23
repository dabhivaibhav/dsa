package binary_tree.easy_problems;

/*
Leetcode 100. Same Tree

Given the roots of two binary trees p and q, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

Example 1:
Input: p = [1,2,3], q = [1,2,3]
Output: true

Example 2:
Input: p = [1,2], q = [1,null,2]
Output: false

Example 3:
Input: p = [1,2,1], q = [1,1,2]
Output: false

Constraints:
            The number of nodes in both trees is in the range [0, 100].
            -10^4 <= Node.val <= 10^4
 */
public class SameTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);


        System.out.println(isSameTree(root, root1));
    }

    /*
     * WHAT THIS METHOD DOES:
     * This method compares two completely separate binary trees, starting from their
     * roots (p and q), and determines if they are an exact mirror copy of each
     * other. To be considered the "same," the two trees must possess the exact same
     * geometric shape (structure) and contain the exact same data values at every
     * corresponding position.
     *
     * MENTAL MODEL: THE LOCKSTEP TOURING COMPANIONS
     * Imagine you and a friend are tasked with inspecting two corporate office buildings
     * (p and q) to ensure they were built using identical architectural blueprints.
     * You stand in building p, and your friend stands in building q.
     *
     * You coordinate your inspection strictly over a walkie-talkie, moving room-by-room
     * in absolute lockstep:
     * 1. Base Case (Both Empty): You both walk into a hallway and realize there are no
     *    more rooms in this direction. You say, "I hit a blank wall." Your friend says,
     *    "Me too." Perfect! That section matches (return true).
     * 2. Structure Mismatch: You walk into a left office room, but your friend's walkie-talkie
     *    says, "Wait, there's no door here, it's just a solid wall!" The structures don't
     *    match. Instantly, the blueprints are a failure (return false).
     * 3. Value Mismatch: You both successfully walk into a corresponding room. You check the
     *    serial number on the server box and say, "Mine says 5." Your friend says, "Mine
     *    says 9." The data values conflict. The inspection fails immediately (return false).
     * 4. Branching Out: If your current rooms match perfectly, you don't stop there. You both
     *    agree to simultaneously split up and inspect the left wings, and then check the right wings.
     *    The buildings are only declared identical if BOTH wings pass the inspection.
     *
     * CORE IDEA: PREORDER SIMULTANEOUS TRAVERSAL (LOCKSTEP EVALUATION)
     * Unlike Diameter or Maximum Path Sum which process data from the bottom up (Postorder),
     * Same Tree uses a Preorder philosophy. We evaluate the current nodes first before we
     * ever waste energy checking their children. If the current roots fail to match, we can
     * short-circuit early and skip evaluating the rest of the massive subtrees below.
     *
     * STEP-BY-STEP LOGIC:
     *
     * 1. BASE CASE 1: if (p == null && q == null) return true;
     *    - Both nodes are null. Two empty trees are identical. This is our safe harbor
     *      that returns true and stops the recursion from drifting into space.
     *
     * 2. BASE CASE 2: if (p == null || q == null || p.val != q.val) return false;
     *    - This line uses conditional short-circuiting to check three structural traps:
     *      - p == null || q == null: One tree has a node here, but the other tree is
     *        empty. Structural asymmetry!
     *      - p.val != q.val: Both nodes exist, but their structural contents hold
     *        differing values.
     *    - If any of these conditions trigger, the trees are not identical.
     *
     * 3. THE LOCKSTEP RECURSION:
     *    - isSameTree(p.left, q.left): We send our verification engines down to the
     *      left children simultaneously.
     *    - isSameTree(p.right, q.right): We send our verification engines down to the
     *      right children simultaneously.
     *    - return leftMatches && rightMatches;: We apply a strict logical AND. A
     *      single structural breakdown anywhere down the line ruins the entire match.
     *
     * CONNECTION TO WHAT YOU KNOW:
     * Think of this as the multi-tree extension of simple recursive properties! When you
     * write maxDepth(root), you pass down a single pointer (root.left). Here, you are
     * simply maintaining a dual-pointer engine (p, q), synchronizing their steps down
     * to their respective branches: (p.left, q.left) and (p.right, q.right).
     *
     * COMPLEXITY ANALYSIS:
     * Time Complexity: O(min(n, m))
     * Where n and m represent the total number of nodes in tree p and tree q. The
     * algorithm will visit every node up until it hits the first point of structural difference.
     * In the worst-case scenario where the trees match completely, it visits every single node,
     * resulting in linear time proportional to the size of the smaller tree.
     *
     * Space Complexity: O(min(h1, h2))
     * Where h1 and h2 are the heights of the respective trees. The execution call stack
     * consumes space directly proportional to the deep paths of the trees. For balanced trees,
     * this maps efficiently to O(log n); for highly skewed line trees, it stretches to O(n).
     *
     * INTERVIEW TAKEAWAY:
     * When analyzing multiple recursive tree structures simultaneously, structure your
     * base conditions to isolate the "identity" states first. By verifying null states and
     * structural presence before comparing values, you protect your execution from throwing
     * dangerous NullPointerException errors while optimizing performance via early-exit evaluation.
     */
    private static boolean isSameTree(TreeNode p, TreeNode q) {
        // BASE CASES (The Room Checks)
        // If both rooms are empty, they match!
        if (p == null && q == null) return true;

        // If one is empty but the other isn't, or their values don't match -> Failure!
        if (p == null || q == null || p.val != q.val) return false;

        // THE LOCKSTEP RECURSION
        // Check if the left subtrees match AND the right subtrees match
        boolean leftMatches = isSameTree(p.left, q.left);
        boolean rightMatches = isSameTree(p.right, q.right);

        return leftMatches && rightMatches;
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
