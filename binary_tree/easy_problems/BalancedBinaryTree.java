package binary_tree.easy_problems;

/*
Leetcode 110: Balanced Binary Trees

Given a binary tree, determine if it is height-balanced.
A height-balanced binary tree is a binary tree in which
the depth of the two subtrees of every node never differs
by more than one.

Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: true

Example 2:
Input: root = [1,2,2,3,3,null,null,4,4]
Output: false

Example 3:
Input: root = []
Output: true


Constraints:
            The number of nodes in the tree is in the range [0, 5000].
            -10^4 <= Node.val <= 10^4
 */
public class BalancedBinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.left.left = new TreeNode(4);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        root.left.left.right.right = new TreeNode(7);
        root.left.left.right.right.right = new TreeNode(8);
        System.out.println(isBalanced(root));
    }

    /*
     * WHAT THIS METHOD DOES:
     * This method determines if a Binary Tree is "height-balanced." A binary tree
     * is height-balanced if, for EVERY single node in the tree, the absolute
     * difference between the height of its left subtree and its right subtree
     * is at most 1.
     *
     * MENTAL MODEL: THE SEESAW CONTEST
     * Imagine every node in your tree is a pivot for a seesaw, holding up two
     * kids (its left and right subtrees). If one side gets more than 1 level
     * heavier (deeper) than the other, that specific seesaw tilts too much and
     * breaks. For the entire tree to be balanced, *every single seesaw* in the
     * tree must stay within the safe range (height difference <= 1). If even
     * one seesaw breaks, the whole playground is declared unsafe (-1).
     *
     * CORE IDEA: THE "FLAG ON FAILURE" RECURSION
     * A naive approach would calculate the max depth of the left and right sides
     * for every single node repeatedly, leading to a slow, redundant O(n^2) runtime.
     *
     * Instead, this optimized solution uses a brilliant trick: it calculates the
     * height from the bottom up. If it ever detects an imbalance anywhere down
     * below, it immediately "flags" it by returning -1. That -1 acts like an
     * emergency broadcast signal, short-circuiting all the way back up to the root.
     *
     * STEP-BY-STEP LOGIC:
     *
     * 1. THE WRAPPER (isBalanced):
     *    - Calls our worker engine heightOfTree(root).
     *    - If the engine returns -1, it means an imbalance was found somewhere;
     *      return false.
     *    - Otherwise, return true.
     *
     * 2. THE WORKER ENGINE (heightOfTree):
     *    - Base Case: If the current node is null, it's empty space. Empty space
     *      has a height of 0.
     *    - Left Check: Ask the left child for its height. If it returns -1,
     *      pass the -1 straight up!
     *    - Right Check: Ask the right child for its height. If it returns -1,
     *      pass the -1 straight up!
     *    - The Balance Test: Now that we have valid heights for both sides, look
     *      at them. Is Math.abs(left - right) > 1? If yes, this node just broke
     *      the seesaw rule! Return -1 immediately.
     *    - The Healthy Return: If everything is perfectly balanced here, return
     *      the actual height of this subtree: Math.max(left, right) + 1.
     *
     * CONNECTION TO WHAT YOU KNOW:
     * Look closely at the last line: return Math.max(left, right) + 1;. This is
     * exactly the same code you used for Maximum Depth of a Binary Tree!
     *
     * The only thing we added here is a tiny interceptor step before it: checking
     * if the left side and right side are getting along. You didn't learn a brand-new
     * algorithm; you just added an "early-alarm" system to your existing Max Depth
     * algorithm.
     *
     * COMPLEXITY ANALYSIS:
     * Time Complexity: O(n)
     * Every single node in the tree is visited exactly once. Thanks to the bottom-up
     * approach, we compute the heights in a single pass instead of recalculating
     * them from scratch at every level.
     *
     * Space Complexity: O(h), where h is the height of the tree
     * This space is used by the system's implicit recursion call stack. In the
     * worst-case scenario (a completely skewed tree resembling a linked list),
     * it will be O(n). In a perfectly balanced tree, it minimizes to O(log n).
     *
     * INTERVIEW TAKEAWAY:
     * When an interviewer asks you to validate a structural property of a tree
     * that depends on height, always aim for a bottom-up recursion pattern.
     *
     * Using a special sentinel value (like -1) to mean "invalid state" allows you
     * to combine two tasks into one elegant function execution: computing a value
     * while validating a constraint simultaneously.
     */
    public static boolean isBalanced(TreeNode root) {
        int height = heightOfTree(root);
        return height != -1;
    }

    public static int heightOfTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = heightOfTree(root.left);
        int right = heightOfTree(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }
        return Math.max(left, right) + 1;
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
