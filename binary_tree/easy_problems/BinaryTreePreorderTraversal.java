package binary_tree.easy_problems;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(preorderTraversal(root));
    }

    /*
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
    private static List<Integer> preorderTraversal(TreeNode root) {
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
}
