package binary_tree.easy_problems;

import com.sun.source.tree.BinaryTree;

/*
Leetcode 543. Diameter of Binary Tree

Given the root of a binary tree, return the length of the diameter of the tree.
The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
This path may or may not pass through the root.
The length of a path between two nodes is represented by the number of edges between them.

Example 1:
Input: root = [1,2,3,4,5]
Output: 3
Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].

Example 2:
Input: root = [1,2]
Output: 1

Constraints:
            The number of nodes in the tree is in the range [1, 10^4].
            -100 <= Node.val <= 100
*/
public class DiameterOfABinaryTree {

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        node1.left = new TreeNode(2);
        node1.right = new TreeNode(3);
        node1.left.left = new TreeNode(4);
        node1.left.right = new TreeNode(5);
        node1.left.right.right = new TreeNode(6);
        node1.left.right.right.right = new TreeNode(7);
        node1.left.right.right.right.right = new TreeNode(8);
        node1.left.right.right.right.right.right = new TreeNode(9);
        System.out.println(diameterOfBinaryTree(node1));


    }
    // This variable acts as our global scoreboard to track the largest diameter found
    private static int maxDiameter = 0;
    
    /*
     * WHAT THIS METHOD DOES:
     * This method finds the length of the longest path between any two nodes in a
     * binary tree. This longest path is called the "diameter." Crucially, this
     * path does NOT have to pass through the main root of the entire tree—it
     * can exist entirely within a deep subtree.
     *
     * MENTAL MODEL: THE LONG-DISTANCE PHONE CALL
     * Imagine every node in the tree is a small town router. A long-distance phone
     * call line has to stretch from a deep leaf on the left side of a town, go up
     * through that town's router, and go down to a deep leaf on its right side.
     * The length of the wire used is measured by the number of connections (edges).
     *
     * To find the longest wire in the whole country, we check every single town
     * router. We ask: "If a call passes through you as the highest turning point,
     * how much wire do we need?" That would simply be (Left Dept. Height + Right Dept. Height).
     * We keep a master scoreboard (maxDiameter) on the wall. Every time a town
     * calculates its local wire length, we compare it to our scoreboard and record
     * the highest number seen.
     *
     * CORE IDEA: POSTORDER HEIGHT COMPUTATION WITH A GLOBAL SIDE-EFFECT
     * Just like in "Balanced Binary Tree," we don't want to calculate heights
     * repeatedly from scratch (which causes a sluggish O(n^2) time).
     *
     * Instead, we use a single bottom-up traversal. The primary return value of
     * our recursive function is the standard **Height** of the subtree. However,
     * as a continuous "side-effect," each node secretly computes its local
     * diameter and updates a global maximum tracker (maxDiameter).
     *
     * STEP-BY-STEP LOGIC:
     *
     * 1. THE WRAPPER (diameterOfBinaryTree):
     *    - Resets the global maxDiameter scoreboard to 0 (important for consecutive tests).
     *    - Kicks off the recursive engine calculateHeight(root).
     *    - Returns the final top score stored in maxDiameter.
     *
     * 2. THE WORKER ENGINE (calculateHeight):
     *    - Base Case: If the current node is null, it's empty space. Its height is 0.
     *    - Left Height: Recursively find the true max height of the left branch.
     *    - Right Height: Recursively find the true max height of the right branch.
     *    - The Diameter Snapshot: Calculate the path length running through the current
     *      node from its deepest left leaf to its deepest right leaf:
     *      currentDiameter = leftHeight + rightHeight
     *    - Scoreboard Update: If currentDiameter is larger than anything we've seen
     *      before, update maxDiameter.
     *    - The Height Return: Pass the actual height of this current node back up to
     *      the parent: Math.max(leftHeight, rightHeight) + 1.
     *
     * CONNECTION TO WHAT YOU KNOW:
     * This is the exact same skeletal code as **Maximum Depth of a Binary Tree**!
     * Look at the core engine lines:
     *    int leftHeight = calculateHeight(node.left);
     *    int rightHeight = calculateHeight(node.right);
     *    return Math.max(leftHeight, rightHeight) + 1;
     *
     * The genius of this algorithm is that we intercept the execution right before the
     * return statement to extract a bonus piece of information: leftHeight + rightHeight.
     * You are solving two distinct structural questions simultaneously for the price of one!
     *
     * COMPLEXITY ANALYSIS:
     * Time Complexity: O(n)
     * Every node is visited exactly once. We calculate heights bottom-up, completing the
     * diameter checks across the entire tree in a single pass.
     *
     * Space Complexity: O(h), where h is the height of the tree
     * The system call stack relies on memory proportional to the tree height. In the worst
     * case (a completely straight line/skewed tree), it will require O(n) memory space.
     * For a perfectly balanced tree, it balances out to O(log n).
     *
     * INTERVIEW TAKEAWAY:
     * Do not assume the longest path always runs through the absolute top root. A tree
     * could have a massive, dense cluster of deep levels buried far down on one side.
     * By tracking global maximums via an instance/class tracker variable while executing
     * standard traversals, you can globally record deep local structural traits without
     * disrupting your clean bottom-up flow.]
     */
    private static int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0; // Reset for each test case
        calculateHeight(root);
        return maxDiameter;
    }

    private static int calculateHeight(TreeNode node) {
        // An empty node has a height of 0
        if (node == null) {
            return 0;
        }

        // Get the true height of both subtrees
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);

        // Calculate the diameter at this specific node
        // The path length passing through this node is left height + right height
        int currentDiameter = leftHeight + rightHeight;

        // Update our global maximum if this node's path is the longest so far
        maxDiameter = Math.max(maxDiameter, currentDiameter);

        // Return the true height of this node back up to its parent
        return Math.max(leftHeight, rightHeight) + 1;
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
