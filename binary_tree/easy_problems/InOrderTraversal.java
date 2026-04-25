package binary_tree.easy_problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
Leetcode 94. Binary Tree Inorder Traversal

Given the root of a binary tree, return the inorder traversal of its nodes' values.

Example 1:
Input: root = [1,null,2,3]
Output: [1,3,2]

Example 2:
Input: root = [1,2,3,4,5,null,8,null,null,6,7,9]
Output: [4,2,6,5,7,1,3,9,8]

Example 3:
Input: root = []
Output: []

Example 4:
Input: root = [1]
Output: [1]

Constraints:
            The number of nodes in the tree is in the range [0, 100].
            -100 <= Node.val <= 100

Follow up: Recursive solution is trivial, could you do it iteratively?
 */
public class InOrderTraversal {

    public static void main(String[] args) {

    }

    /*
     * inorderTraversalRecursion
     *
     * WHAT THIS METHOD DOES:
     * Uses the system's "Hidden Stack" (the call stack) to explore the tree.
     * It follows a strict LNR (Left-Node-Right) protocol by pausing the
     * current function execution to dive into sub-problems.
     *
     * MENTAL MODEL: THE NESTED DOLL
     * Imagine each node is a Russian nesting doll. To find the "Inorder"
     * sequence, you must open the left doll inside first. Once that's
     * completely finished, you look at the current doll, then move to
     * the right doll.
     *
     * STEP-BY-STEP LOGIC:
     * 1. BASE CASE: If the current node is null, we've hit a "dead end." Return to the previous caller.
     * 2. LEFT: Call the function again on the left child. This repeats until we hit the leftmost leaf.
     * 3. ROOT: Once the left side is finished, "process" the current node (add its value to the list).
     * 4. RIGHT: Finally, call the function on the right child to explore that subtree.
     *
     * COMPLEXITY:
     * - Time: O(n) because we visit every node exactly once.
     * - Space: O(h) where h is the height of the tree (due to the recursion stack).
     */
    private static List<Integer> inorderTraversalRecursion(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        explore(root, result);
        return result;
    }


    private static void explore(TreeNode currentNode, List<Integer> result) {
        // BASE CASE: If the room is empty, just go back
        if (currentNode == null) {
            return;
        }

        // LEFT: Recursively visit the left child
        explore(currentNode.left, result);

        // ROOT: Record the value of the current node
        result.add(currentNode.val);

        // RIGHT: Recursively visit the right child
        explore(currentNode.right, result);
    }

    /*
     * preorderTraversalIterative
     *
     * WHAT THIS METHOD DOES:
     * Manually simulates recursion using a Stack data structure. It mimics
     * the behavior of the "Left-First Explorer" by physically storing
     * nodes we intend to revisit later.
     *
     * MENTAL MODEL: THE BREADCRUMB TRAIL
     * Imagine walking into a dark cave. You always want to go as far left
     * as possible. As you pass a door, you leave a "breadcrumb" (push
     * onto Stack) so you know how to get back. When you can't go left
     * anymore, you follow your breadcrumbs back one step, record what you
     * see, and then try the right-hand door.
     *
     * STEP-BY-STEP LOGIC:
     * 1. Start a pointer `curr` at the root.
     * 2. OUTER LOOP: Keep going as long as `curr` is not null OR the stack has nodes.
     * 3. INNER LOOP: "Go Left." While `curr` is not null, push it onto the
     * stack and move to `curr.left`. This populates the stack with the
     * entire left-side spine of the current subtree.
     * 4. "Process Node." Pop the top node from the stack. This is the
     * node that is "most-left." Add it to your result list.
     * 5. "Go Right." Set `curr = poppedNode.right`. The outer loop starts
     * the process over for this right subtree.
     *
     * WHY IT WORKS:
     * The Stack ensures "Last-In, First-Out" (LIFO) behavior. The last
     * node we pass on the way down is the first one we need to process
     * once we hit the bottom-left.
     *
     * COMPLEXITY:
     * - Time: O(n). Every node is pushed and popped exactly once.
     * - Space: O(h). The stack holds at most the height of the tree.
     */
    private static  List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            // Reach the leftmost node of the current node
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            // Process the node (it's the leftmost available)
            curr = stack.pop();
            result.add(curr.val); // ROOT

            // Now we have visited the node and its left subtree.
            // It's time to visit the right subtree.
            curr = curr.right;
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

