package binary_tree.easy_problems;

/*
Leetcode 104: Maximum Depth of Binary Tree

Given the root of a binary tree, return its maximum depth.
A binary tree's maximum depth is the number of nodes along
the longest path from the root node down to the farthest leaf node.

Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: 3

Example 2:
Input: root = [1,null,2]
Output: 2


Constraints:
            The number of nodes in the tree is in the range [0, 10^4].
            -100 <= Node.val <= 100
 */
public class MaximumDepthOfBinaryTree {

    public static void main(String[] args) {

    }


    /*
    MAXIMUM DEPTH OF BINARY TREE BREAKDOWN

    WHAT THIS METHOD DOES:
    This method calculates the height of a Binary Tree, which is the number of
    nodes along the longest path from the root down to the farthest leaf. It
    effectively measures how "tall" the tree is.

    MENTAL MODEL: THE HEIGHT CONTEST

    Imagine you are a manager (the root). You want to know the maximum height
    of your organizational chart. You ask your two assistants (Left and Right
    children): "How deep is the longest branch in your department?"
    The Left assistant goes off and finds their deepest path.
    The Right assistant does the same for their side.
    Once they report back, you take the winner (the larger number) and add
    1 (representing yourself) to get the final answer.

    CORE IDEA: THE "POSTORDER" RECURSION

    To know the height of a node, you must first know the height of its children.
    This is a naturally recursive problem. We solve it from the bottom up.

    STEP-BY-STEP LOGIC:
    Step 1: BASE CASE: If the current node is null, it means we've reached
    past a leaf. A non-existent node has a depth of 0.
    Step 2: RECURSIVE STEP (LEFT): Call maxDepth on the left child to find
    the height of the left subtree.
    Step 3: RECURSIVE STEP (RIGHT): Call maxDepth on the right child to
    find the height of the right subtree.
    Step 4: COMBINE: Take the maximum of those two heights and add 1 (for
    the current node).

    INTUITION (WHY IT WORKS):
    Think of it like climbing a ladder. Each node is a rung. To find the total
    height, you look at the two ladders attached to your current rung, pick
    the tallest one, and add your current rung to the count.

    EXAMPLE DRY RUN:
    Tree: [3, 9, 20, null, null, 15, 7]
    maxDepth(15) returns 1.
    maxDepth(7) returns 1.
    maxDepth(20) takes max(1, 1) + 1 = 2.
    maxDepth(9) returns 1.
    maxDepth(3) takes max(1, 2) + 1 = 3.
    Final Result: 3.

    COMPLEXITY ANALYSIS:
    Time Complexity: O(n). We need to consider all the possible TC and SC.
    We must visit every single node in the tree exactly once to determine
    the overall maximum depth.

    Space Complexity: O(h), where h is the height of the tree. We need to
    consider all the possible TC and SC.
    This space is used by the recursion stack. In the worst case (a skewed
    tree), it's O(n). In a balanced tree, it's O(log n).

    COMMON PITFALLS:
    Forgetting the "+ 1": If you forget to add 1, every call will
    effectively return 0, and you'll never count the root or the intermediate
    levels.
    Null Check: Always ensure the base case root == null is first to
    prevent NullPointerException.

    INTERVIEW TAKEAWAY:
    This is the quintessential "Recursive Tree" problem. It demonstrates
    the "Divide and Conquer" strategy: break the tree into smaller sub-trees,
    solve for them, and combine the results. Mastery of this logic is
    fundamental for more complex tree problems like "Balanced Binary Tree"
    or "Diameter of a Binary Tree."
    */
    private static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
