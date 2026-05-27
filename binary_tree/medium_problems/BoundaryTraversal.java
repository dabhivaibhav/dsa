package binary_tree.medium_problems;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Boundary Traversal

Given a root of Binary Tree, perform the boundary traversal of the tree.
The boundary traversal is the process of visiting the boundary nodes of
the binary tree in the anticlockwise direction, starting from the root.

The boundary of a binary tree is the concatenation of the root, the left
boundary, the leaves ordered from left-to-right, and the reverse order of the right boundary.

The left boundary is the set of nodes defined by the following:
The root TreeNode's left child is in the left boundary. If the root does not have a left child, then the left boundary is empty.
If a TreeNode in the left boundary and has a left child, then the left child is in the left boundary.
If a TreeNode is in the left boundary, has no left child, but has a right child, then the right child is in the left boundary.
The leftmost leaf is not in the left boundary.
The right boundary is similar to the left boundary, except it is the right side of the root's right subtree.
Again, the leaf is not part of the right boundary, and the right boundary is empty if the root does not have a right child.

Example 1
Input : root = [1, 2, 3, 4, 5, 6, 7, null, null, 8, 9]
Output : [1, 2, 4, 8, 9, 6, 7, 3]

Example 2
Input : root = [1, 2, null, 4, 9, 6, 5, 3, null, null, null, null, null, 7, 8]
Output : [1, 2, 4, 6, 5, 7, 8]

Constraints:
            0 <= Number of Nodes <= 10^4
            -10^3 <= TreeNode.val <= 10^3
 */
public class BoundaryTraversal {


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
        root.right.left.left = new TreeNode(12);
        List<Integer> result = printBoundary(root);
        printResult(result);


    }

    /*
     * WHAT THIS METHOD DOES:
     * This method traces the complete "outer perimeter" or "crust" of a Binary Tree
     * in a strict anti-clockwise direction. The traversal is uniquely broken down
     * into a sequence of four clean, orderly phases:
     * 1. The Root Node (if it isn't an isolated leaf).
     * 2. The Left Boundary (excluding the bottom-most leaf node).
     * 3. All Leaf Nodes (the entire bottom floor, visited left-to-right).
     * 4. The Right Boundary (excluding the top root and bottom-most leaf node,
     * tracked top-down but added in reverse order).
     *
     * MENTAL MODEL: THE CRUST-ONLY PIZZA EATER
     * Imagine you are eating the crust around a triangular slice of pizza, starting
     * from the top peak (the Root).
     * * To make a complete anti-clockwise loop without double-counting your bites:
     * 1. You start at the very top peak.
     * 2. You chew your way down the left-hand edge of the slice (`addLeftBoundary`).
     * You stop just before reaching the bottom corner so you don't ruin the
     * bottom crust processing.
     * 3. You switch strategies and walk entirely along the bottom edge, picking up
     * every single crumb resting on the floor (`addLeaves`).
     * 4. You walk back up the right-hand edge (`addRightBoundary`). However, since
     * your physical path moves top-to-bottom, you write down these values into a
     * temporary diary (`temp`) and then read your diary backward (`reverse`) so
     * that the final path seamlessly flows upwards back to the peak.
     *
     * CORE IDEA: DIVIDE AND CONQUER (CONSTRAINED SPECIFIC TRAVERSALS)
     * Instead of trying to write a single complex recursive loop that somehow knows
     * if a node sits on the edge or not, this algorithm handles the perimeter by breaking
     * the problem into isolated, modular responsibilities.
     *
     * The biggest trap in this problem is avoiding "Double Counting" at the overlap points—namely,
     * where the left/right boundaries meet the bottom leaves. We cleanly solve this via a
     * single rule: "Boundary functions are exclusively banned from recording leaf nodes."
     *
     * PHASE-BY-PHASE LOGIC:
     *
     * 1. THE ROOT INITIATION (`printBoundary`):
     * - Guardrail: If the tree is empty, return an empty list.
     * - If the root is a normal parent, add its value first. If it's a lone node
     * with no children, let the leaf collector handle it to avoid double-entry.
     *
     * 2. THE LEFT DESCENDER (`addLeftBoundary`):
     * - Start a pointer at `root.left`.
     * - Loop while the pointer is valid. If the node is NOT a leaf, add it to our list.
     * - Navigation Rule: Try to stay left as aggressively as possible (`curr = curr.left`).
     * If a node doesn't have a left child but still has a right child, that right child
     * is structurally part of the outer left boundary, so we step right (`curr = curr.right`).
     *
     * 3. THE CRUMB COLLECTOR (`addLeaves`):
     * - This uses standard Depth-First Search (DFS) recursion to scan the tree.
     * - Base Case: If a node has no left or right child, it's a leaf! Record it and stop.
     * - Otherwise, recursively call down the left subtree, then the right subtree. This
     * naturally uncovers all leaves in a uniform left-to-right order.
     *
     * 4. THE RIGHT ASCENDER (`addRightBoundary`):
     * - Start a pointer at `root.right`.
     * - Loop downwards, matching the same logic as the left side: add non-leaves, and
     * preferentially move right (`curr = curr.right`), falling back to left if blocked.
     * - The Reverse Hook: Because this loop runs top-to-bottom, but anti-clockwise
     * requires bottom-to-top order, we push values into a temporary list and read
     * them off backwards into our final collection.
     *
     * COMPLEXITY ANALYSIS:
     * Time Complexity: O(n)
     * Finding the left and right boundaries takes time proportional to the height of the
     * tree, which is O(h). Finding all the leaves requires a full traversal of the tree,
     * which takes O(n) time. Combined, the total time spent scales linearly with the number
     * of nodes.
     *
     * Space Complexity: O(h)
     * The memory used is bounded by the height of the tree. The leaf collection uses
     * implicit recursion stack memory up to O(h). The right boundary temporary list stores
     * at most h elements. In a balanced tree, this runs efficiently at O(log n); in a
     * worst-case skewed tree, it extends to O(n).
     *
     * INTERVIEW TAKEAWAY:
     * Complex layout problems are best solved by separating concerns. Trying to calculate
     * boundaries and leaves inside a single traversal function results in confusing conditional
     * checks that are difficult to debug. Splitting the problem into distinct boundary-tracking
     * loops and a leaf-collecting recursive call keeps the code robust, scannable, and maintainable.
     */

    // Function to check
    // if a TreeNode is a leaf
    private static boolean isLeaf(TreeNode root) {
        return root.left == null && root.right == null;
    }

    // Function to add the
    // left boundary of the tree
    private static void addLeftBoundary(TreeNode root, List<Integer> res) {
        TreeNode curr = root.left;
        while (curr != null) {
            // If the current TreeNode is not a leaf,
            // add its value to the result
            if (!isLeaf(curr)) {
                res.add(curr.val);
            }
            // Move to the left child if it exists,
            // otherwise move to the right child
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
    }

    // Function to add the
    // right boundary of the tree
    private static void addRightBoundary(TreeNode root, List<Integer> res) {
        TreeNode curr = root.right;
        List<Integer> temp = new ArrayList<>();
        while (curr != null) {
            // If the current TreeNode is not a leaf,
            // add its value to a temporary list
            if (!isLeaf(curr)) {
                temp.add(curr.val);
            }
            // Move to the right child if it exists,
            // otherwise move to the left child
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        // Reverse and add the values from
        // the temporary list to the result
        for (int i = temp.size() - 1; i >= 0; --i) {
            res.add(temp.get(i));
        }
    }

    // Function to add the
    // leaves of the tree
    private static void addLeaves(TreeNode root, List<Integer> res) {
        // If the current TreeNode is a
        // leaf, add its value to the result
        if (isLeaf(root)) {
            res.add(root.val);
            return;
        }
        // Recursively add leaves of
        // the left and right subtrees
        if (root.left != null) {
            addLeaves(root.left, res);
        }
        if (root.right != null) {
            addLeaves(root.right, res);
        }
    }

    // Main function to perform the
    // boundary traversal of the binary tree
    static List<Integer> printBoundary(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        // If the root is not a leaf,
        // add its value to the result
        if (!isLeaf(root)) {
            res.add(root.val);
        }

        // Add the left boundary, leaves,
        // and right boundary in order
        addLeftBoundary(root, res);
        addLeaves(root, res);
        addRightBoundary(root, res);

        return res;
    }

    // Helper function to
    // print the result
    private static void printResult(List<Integer> result) {
        for (int val : result) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(){
        }

        TreeNode(int val){
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
