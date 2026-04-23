package binary_tree.easy_problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
Leetcode 144. Binary Tree Preorder Traversal

Given the root of a binary tree, return the preorder traversal of its nodes' values.

Example 1:
Input: root = [1,null,2,3]
Output: [1,2,3]
Explanation:
    1
     \
      2
     /
    3

Example 2:
Input: root = [1,2,3,4,5,null,8,null,null,6,7,9]
Output: [1,2,4,5,6,7,3,8,9]

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
public class BinaryTreePreorderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(8);
        root.right.right.right.right = new TreeNode(9);
        System.out.println(preorderTraversalRecursion(root));
    }

    /*
    MY THOUGHT PROCESS:
    Originally I intended to use indices logic but here we have Node based structure, so we can't use that.

    Arrays vs. NodesYour current thought (2i+1): This is for Heaps or trees  stored in a flat array. You
    have to calculate where the children are.

    We have given a TreeNode object. You don't need math to find the children; they are already connected
    via pointers. You just ask for node.left or node.right.
    ---------------------------------------------
    THE "RECURSIVE BOOKMARK" PATTERN (PREORDER)

    What this method does:
    Visits every node in a binary tree in the specific order: Current Node -> Left Subtree -> Right Subtree.

    Core Idea:
    Using the "System Stack." Every time you call explore(), the computer saves your current spot. It finishes the
    entire left side before the "bookmark" for the right side is ever opened.

    How the Code Works:
    Step 1: Check if the node is null (The "Dead End" check).
    Step 2: Add the current node's value to your list (This is the "Root" step).
    Step 3: Call the function on the left child. This "pauses" the current function and starts a new one for the left.
    Step 4: Once the left side is completely done, the paused function wakes up and calls the right child.

    Complexity:
    → Time Complexity: O(N) — You visit every node exactly once.
    → Space Complexity: O(N) — In the worst case (a tree that is just one long line), the computer has to keep N
                        "bookmarks" (function calls) in its memory stack.

    Interview Takeaway:
    Recursion is the cleanest way to express tree traversals. Always start with this. If the interviewer asks "How can
    you do this without recursion?", they are asking you to manage those "bookmarks" yourself using a manual Stack data structure.
    */
    private static List<Integer> preorderTraversalRecursion(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        explore(root, result);
        return result;
    }

    private static void explore(TreeNode currentNode, List<Integer> result) {
        // BASE CASE: If the room is empty, just go back
        if (currentNode == null) {
            return;
        }

        // ROOT: Record the value of the current node
        result.add(currentNode.val);

        // LEFT: Recursively visit the left child
        explore(currentNode.left, result);

        // RIGHT: Recursively visit the right child
        explore(currentNode.right, result);
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

    /*
    THE "MANUAL STACK" PATTERN (ITERATIVE PREORDER)

    What this method does:
    Simulates recursion using a Stack data structure to traverse the tree in Root-Left-Right order.

    Core Idea:
    Recursion is just a hidden stack. By using our own Stack<TreeNode>, we gain more control and
    avoid crashing on extremely deep trees (where the system stack might run out of memory).

    How the Code Works:
    Step 1: Push the root onto the stack.
    Step 2: While the stack isn't empty, POP the top node.
    Step 3: Record its value (Root).
    Step 4: Push the RIGHT child, then the LEFT child.
    → Why? Because the Stack is LIFO. The last thing pushed (Left) will be the first thing popped in the next loop.

    INTUITION (VERY IMPORTANT):
    Think of the Stack as a "To-Do List." When you visit a node, you cross it off,
    but you realize you now have two new tasks: "Visit Left Subtree" and "Visit Right Subtree."
    You put them on the list, making sure the most urgent one (Left) stays at the top.

    Complexity:
    → Time Complexity: O(N) — Every node is pushed and popped exactly once.
    → Space Complexity: O(H) — The stack stores at most the height of the tree.

    Interview Takeaway:
    Most interviewers consider the Iterative Stack approach the "Optimal" practical solution.
    Morris Traversal is great to mention as a "bonus," but it is often discouraged in production
    because it temporarily modifies the tree structure.
    */
    private static  List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(current.val); // ROOT

            // Push Right first, so Left is processed first!
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }
        return result;
    }
}
