package binary_tree.medium_problems;

/*
Problem: Children Sum Property in Binary Tree

Given the root of a binary tree, return true if and only if every node’s value
is equal to the sum of the values stored in its left and right children.
For any missing ( null ) child, its value is treated as 0.
A leaf node automatically satisfies the rule because both children are null.

Example 1
Input: root = [1,4,3,5]
Output: false
Explanation:The root is 1, but its children sum to 4 + 3 = 7. Since 1 ≠ 7, the tree violates the property.

Example 2
Input: root = [10,4,6,1,3,2,4]
Output: true
Explanation:
            4 = 1 + 3
            6 = 2 + 4
            10 = 4 + 6
All internal nodes satisfy the condition.

Constraints:
            1 ≤ n ≤ 10^4 (n = number of nodes `)
            -10^5 ≤ Node.val ≤ 10^5
 */
public class ChildrenSumProperty {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println(checkChildrenSum(root));
    }


    private static boolean checkChildrenSum(TreeNode root) {
        // Base Case 1: An empty tree automatically satisfies the rule
        if (root == null) return true;

        // Base Case 2: A leaf node automatically satisfies the rule
        if (root.left == null && root.right == null) return true;

        // POSTORDER STEP 1 & 2: Go all the way down and validate the subtrees first
        boolean isLeftValid = checkChildrenSum(root.left);
        boolean isRightValid = checkChildrenSum(root.right);

        // POSTORDER STEP 3: Process the current parent node using children data
        int leftValue = (root.left != null) ? root.left.val : 0;
        int rightValue = (root.right != null) ? root.right.val : 0;

        boolean isCurrentValid = (root.val == leftValue + rightValue);

        // The entire tree is valid only if left subtree is valid,
        // right subtree is valid, AND the current node matches the sum.
        return isLeftValid && isRightValid && isCurrentValid;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
