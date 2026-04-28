package binary_tree.easy_problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
Leetcode 145. Binary Tree Postorder Traversal

Given the root of a binary tree, return the postorder traversal of its nodes' values.


Example 1:
Input: root = [1,null,2,3]
Output: [3,2,1]

Example 2:
Input: root = [1,2,3,4,5,null,8,null,null,6,7,9]
Output: [4,6,7,5,2,9,8,3,1]

Example 3:
Input: root = []
Output: []

Example 4:
Input: root = [1]
Output: [1]


Constraints:
            The number of the nodes in the tree is in the range [0, 100].
            -100 <= Node.val <= 100

Follow up: Recursive solution is trivial, could you do it iteratively?
 */
public class PostOrderTraversal {

    public static void main(String[] args) {


    }


    /*
    BINARY TREE POSTORDER TRAVERSAL BREAKDOWN

    WHAT THIS METHOD DOES:
    This method visits every node in a Binary Tree in the sequence:
    Left Subtree -> Right Subtree -> Root. It ensures that no parent node
    is processed until both of its children have been fully visited.

    MENTAL MODEL: THE "BOTTOM-UP" CLEANUP
    Imagine you are cleaning a house with multiple floors and rooms. Your
    rule is: "I cannot finish cleaning a hallway (the parent) until I have
    cleaned every single room attached to it (the children)." You start
    from the deepest, leftmost corner, finish the rooms there, then move
    to the right rooms, and only finally declare that section of the
    hallway clean.

    CORE IDEA: THE "LRN" PATTERN (Left, Right, Node)
    Postorder is the opposite of Preorder in many ways. While Preorder is
    "Top-Down," Postorder is "Bottom-Up." This makes it perfect for
    operations like deleting a tree (you must delete children before
    deleting the parent).

    1. Postorder Traversal via Two Stacks (Iterative)
    WHAT THIS METHOD DOES:
    Since Postorder (LRN) is tricky to do with one stack, this approach
    uses a "Reverse Engineering" trick. We actually perform a
    "Modified Preorder" (Root -> Right -> Left) and then reverse the
    entire result to get Left -> Right -> Root.

    MENTAL MODEL: THE REVERSE MIRROR
    Think of it like a reflection. We record nodes in the order: Top,
    then Right, then Left. If you put all those recordings into a
    container and then dump them out from last-to-first, they
    magically appear in the correct Postorder (Left, Right, Top).

    STEP-BY-STEP LOGIC:
    Initialize two stacks: st1 (the workhorse) and st2 (the storage).
    Push the root onto st1.
    WHILE st1 is not empty:
    Pop the top node from st1.
    Push that node immediately onto st2.
    Push its LEFT child onto st1 (if it exists).
    Push its RIGHT child onto st1 (if it exists).

    Note: By pushing Left then Right onto st1, the Right child will
    be processed first by the next loop, which is exactly how we get
    the "Root-Right-Left" sequence in st2.

    Once st1 is empty, st2 contains the entire tree in reversed
    postorder.

    Pop everything from st2 and add the values to your result list.

    WHY IT WORKS:
    Standard Preorder is Root -> Left -> Right.
    If we swap the order of children, we get Root -> Right -> Left.
    If we reverse that entire sequence, we get Left -> Right -> Root (Postorder!).
    st2 acts as our reversal mechanism because it is LIFO (Last-In, First-Out).

    COMPLEXITY ANALYSIS:
    Time Complexity: O(n). We need to consider all the possible TC and SC.
    We touch every node twice (once per stack).
    Space Complexity: O(n). We need to consider all the possible TC and SC.
    In the worst case (a skewed tree), both stacks could hold all nodes.
    Usually, it is O(n) regardless of tree height because st2 eventually
    stores every node.
    */
    private static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        // If the tree is empty, return an empty traversal
        if (root == null) {
            return result;
        }

        Stack<TreeNode> st1 = new java.util.Stack<>();  // First stack for iterative traversal
        Stack<TreeNode> st2 = new java.util.Stack<>();  // Second stack to store the nodes in postorder

        // Push the root node onto the first stack
        st1.push(root);

        // Iterative traversal to populate st2 with nodes in postorder
        while (!st1.isEmpty()) {
            root = st1.pop();  // Get the top node from st1
            st2.push(root);  // Push the node onto st2

            // Push left child onto st1 if exists
            if (root.left != null) {
                st1.push(root.left);
            }

            // Push right child onto st1 if exists
            if (root.right != null) {
                st1.push(root.right);
            }
        }
        // Populate the postorder traversal list by popping st2
        while (!st2.isEmpty()) {
            result.add(st2.pop().val);  // Add the node's value to the postorder result
        }

        // Return the postorder traversal result
        return result;
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
