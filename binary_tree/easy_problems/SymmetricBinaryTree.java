package binary_tree.easy_problems;

/*
Leetcode 101. Symmetric Tree

Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).

Example 1:

Input: root = [1,2,2,3,4,4,3]
Output: true

Example 2:
Input: root = [1,2,2,null,3,null,3]
Output: false

Constraints:
            The number of nodes in the tree is in the range [1, 1000].
            -100 <= Node.val <= 100

Follow up: Could you solve it both recursively and iteratively?
 */
public class SymmetricBinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        System.out.println(isSymmetric(root));

    }

    /*
     * WHAT THIS METHOD DOES:
     * This method checks if a Binary Tree is a perfect mirror image of itself around
     * its center axis. It ensures that the structural arrangement and the data values
     * on the left half perfectly match the reflection of the right half.
     *
     * ---
     *
     * THE "MIRROR-IMAGE REFLECTION" PATTERN (SYMMETRIC TREE)
     *
     * Your Thought Process & Intuition:
     * 1. THE PERSPECTIVE: A binary tree is symmetric if it can be folded perfectly in half
     * down the middle along the root node. This means the left subtree must be a perfect
     * mirror image of the right subtree.
     *
     *
     *
     * 2. THE CO-TRAVERSAL STRATEGY: To check for a mirror image, you cannot look at the left
     * side and right side independently. You must deploy two structural explorers at the
     * exact same time, moving in perfectly synchronized, opposite directions:
     * → Left Explorer processes: Root → Left → Right (Standard Preorder)
     * → Right Explorer processes: Root → Right → Left (Reverse Preorder)
     *
     * 3. THE STRUCTURAL ALIGNMENT: For the mirror alignment to hold true:
     * → The Left child of the left explorer must match the Right child of the right explorer.
     * → The Right child of the left explorer must match the Left child of the right explorer.
     *
     * 4. THE BASE CASE CHECKPOINTS: At any given step, the two nodes (n1, n2) must pass
     * three strict structural tests:
     * → If both are null: They match perfectly (empty mirrors empty). Return true.
     * → If only one is null: The tree is lopsided/broken. Return false.
     * → If both exist but values mismatch: The reflection is broken. Return false.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. The Call Stack (Implicit System Stack):
     * - Why? This solution leverages the system's recursive call stack to maintain
     * synchronized tracking of our dual exploration pointers (n1 and n2).
     * - Reason: By tracking pairs of recursive calls together on the stack framework,
     * the program maintains spatial synchronization without requiring complex multi-pointer
     * iterative data structures.
     *
     * 2. Boolean Logic Short-Circuiting:
     * - Why? We use the logical && operator to chain our dual recursive sub-problems.
     * - Reason: If the inner or outer branch checks fail, the application instantly stops
     * unnecessary processing of lower levels and forwards false directly back to the top.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Handle the top-level tree boundary. An empty tree (root == null) is
     * technically symmetric; return true.
     * Step 2: Launch the twin explorer engine isMirror by feeding it the left
     * subtree and right subtree as starting locations: isMirror(root.left, root.right).
     * Step 3: Inside the recursive engine, evaluate the three sequential structural checkpoints:
     * - Check 1: If both pointers hit null together, this sub-path is perfectly uniform. Return true.
     * - Check 2: If only one pointer is null, asymmetry is found. Return false.
     * - Check 3: If both exist but n1.val != n2.val, values break the reflection. Return false.
     * Step 4: Perform the synchronized cross-over step:
     * - Cross-Check A: Send explorer 1 left while explorer 2 moves right: isMirror(n1.left, n2.right).
     * - Cross-Check B: Send explorer 1 right while explorer 2 moves left: isMirror(n1.right, n2.left).
     * Step 5: Combine both cross-checks with a strict && operator. Both conditions must hold
     * true for the entire structure to be verified as symmetric.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - if (n1 == null && n2 == null) return true;
     * Your first base case safety net. When both explorer streams drift past the leaves at
     * the exact same time, it validates that this specific branch path matches symmetrically.
     * * - if (n1 == null || n2 == null) return false;
     * The structural shape validation filter. If one tracker has room to breathe but the
     * other hits a dead end, the mirror configuration is invalid.
     * * - return isMirror(n1.left, n2.right) && isMirror(n1.right, n2.left);
     * Your key intuition statement translated directly into code. It pairs the outermost edges
     * together (n1.left and n2.right) and the innermost edges together (n1.right and n2.left).
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * → Time Complexity: O(N) — We visit every node in the tree exactly once as we pair them up.
     * The recursive engine processes a total of N/2 pairs of nodes. For each individual pair,
     * the system executes constant time O(1) equality tests before branching further down.
     * Thus, the total execution time scales linearly with the size of the tree.
     *
     * → Space Complexity: O(H) — Where H is the height of the tree, representing the maximum
     * depth of the recursion stack. In a perfectly balanced binary tree, the call stack frame
     * footprint drops down to an optimal O(\log N). However, in the absolute worst-case scenario
     * of a completely skewed or linear tree structure, the execution stack depth can grow up to **O(N)**.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * Symmetric Tree is a magnificent extension of the **Same Tree** framework.
     * - Same Tree requires a parallel, identical walk: (p.left, q.left) and (p.right, q.right).
     * - Symmetric Tree requires a cross-over, inverted walk: (n1.left, n2.right) and (n1.right, n2.left).
     *
     * If an interviewer challenges you to solve this question iteratively instead of using recursion,
     * do not panick! You can run the exact same cross-matching logic by setting up a single standard
     * Queue or Stack. Simply add nodes into the data structure in pairs: push root.left and
     * root.right to start. Then, inside your processing loop, pull out two items at a time, apply
     * your three base cases, and push their children in cross-over order onto the belt.
     */
    private static boolean isSymmetric(TreeNode root) {
        // An empty tree is technically symmetric
        if (root == null) return true;

        // Kick off our two simultaneous explorers at the first level down
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode n1, TreeNode n2) {
        // BASE CASE 1: Both endpoints reached a dead end together (Symmetric)
        if (n1 == null && n2 == null) return true;

        // BASE CASE 2: One side ended early but the other didn't (Asymmetric)
        if (n1 == null || n2 == null) return false;

        // BASE CASE 3: Both exist, but their values don't match (Mirror broken)
        if (n1.val != n2.val) return false;

        // Left Explorer goes Left while Right Explorer goes Right -> (n1.left, n2.right)
        // Left Explorer goes Right while Right Explorer goes Left -> (n1.right, n2.left)
        return isMirror(n1.left, n2.right) && isMirror(n1.right, n2.left);
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
