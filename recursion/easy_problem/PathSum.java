package recursion.easy_problem;

/*
Leetcode 112. Path Sum

Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such that
adding up all the values along the path equals targetSum.

A leaf is a node with no children.

Example 1:
Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
Output: true
Explanation: The root-to-leaf path with the target sum is shown.

Example 2:
Input: root = [1,2,3], targetSum = 5
Output: false
Explanation: There are two root-to-leaf paths in the tree:
(1 --> 2): The sum is 3.
(1 --> 3): The sum is 4.
There is no root-to-leaf path with sum = 5.

Example 3:
Input: root = [], targetSum = 0
Output: false
Explanation: Since the tree is empty, there are no root-to-leaf paths.

Constraints:
            The number of nodes in the tree is in the range [0, 5000].
            -1000 <= Node.val <= 1000
            -1000 <= targetSum <= 1000
 */
public class PathSum {

    public static void main(String[] args) {
        PathSum ps = new PathSum();

        TreeNode root =
                ps.new TreeNode(5,
                        ps.new TreeNode(4,
                                ps.new TreeNode(11,
                                        ps.new TreeNode(7),
                                        ps.new TreeNode(2)),
                                null),
                        ps.new TreeNode(8,
                                ps.new TreeNode(13),
                                ps.new TreeNode(4,
                                        null,
                                        ps.new TreeNode(1)))
                );

        int targetSum = 22;

        boolean result = pathSumRecursive(root, targetSum);

        System.out.println("Path exists: " + result);
    }

    /*
    THE "RECURSIVE BUDGET" PATTERN

    THE CONCEPT:
    Trees are recursive. To solve a big tree,
    solve the root and then let the same
    logic solve the sub-trees.

    The '||' (OR) is powerful here. It's like
    sending out two scouts. If Scout A finds
    the gold, the mission is a success. We
    don't care if Scout B fails.

    TIME COMPLEXITY:
    O(N) - We might have to visit every node once.
    SPACE COMPLEXITY:
    O(H) - Where H is the height of the tree (for the stack).
    */

    /**
     * pathSumRecursive(TreeNode root, int targetSum)
     * <p>
     * What this method does:
     * <p>
     * Determines whether there exists a root-to-leaf path
     * in the binary tree such that the sum of node values
     * along the path equals targetSum.
     * <p>
     * A root-to-leaf path means:
     * <p>
     * Start at the root and end at a leaf node
     * (a node with no left and no right child).
     * <p>
     * Core Idea:
     * <p>
     * Use recursion to simulate walking down the tree
     * while subtracting node values from the target sum.
     * <p>
     * Think of targetSum as a "budget".
     * <p>
     * Every node we visit spends part of that budget.
     * <p>
     * If we reach a leaf and the remaining budget
     * exactly equals the leaf value,
     * we found a valid path.
     * <p>
     * Thought Process:
     * <p>
     * At each node we ask:
     * <p>
     * "If I spend this node's value,
     * can one of my children finish the path?"
     * <p>
     * That is why recursion works perfectly here.
     * <p>
     * The remaining work is delegated
     * to the left and right subtrees.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * If the node is null,
     * no path exists → return false.
     * <p>
     * Step 2:
     * If the node is a leaf node
     * (no left child and no right child):
     * <p>
     * check whether:
     * <p>
     * targetSum == node.val
     * <p>
     * If true → path found.
     * <p>
     * Step 3:
     * Otherwise subtract current node value
     * from the target:
     * <p>
     * remainingBudget = targetSum - root.val
     * <p>
     * Step 4:
     * Recursively search both children.
     * <p>
     * pathSumRecursive(root.left, remainingBudget)
     * OR
     * pathSumRecursive(root.right, remainingBudget)
     * <p>
     * Why the OR (||) matters:
     * <p>
     * It acts like sending two explorers
     * down two different paths.
     * <p>
     * If either explorer finds the correct path,
     * the answer is true.
     * <p>
     * Example:
     * <p>
     * Tree:
     * <p>
     * 5
     * / \
     * 4   8
     * /   / \
     * 11  13  4
     * /  \       \
     * 7    2       1
     * <p>
     * targetSum = 22
     * <p>
     * One valid path:
     * <p>
     * 5 → 4 → 11 → 2
     * <p>
     * Sum:
     * <p>
     * 5 + 4 + 11 + 2 = 22
     * <p>
     * Therefore return true.
     * <p>
     * Complexity:
     * <p>
     * Let N = number of nodes.
     * <p>
     * Time Complexity:
     * <p>
     * O(N)
     * <p>
     * In the worst case we must visit
     * every node once.
     * <p>
     * Space Complexity:
     * <p>
     * O(H)
     * <p>
     * where H is the height of the tree.
     * <p>
     * This space is used by the
     * recursion call stack.
     * <p>
     * Interview Takeaway:
     * <p>
     * This is a classic recursive pattern:
     * <p>
     * "Pass the remaining requirement downward."
     * <p>
     * Instead of tracking the entire path sum,
     * we reduce the problem at every step.
     * <p>
     * The tree gradually consumes the target
     * until a leaf confirms the exact match.
     */
    private static boolean pathSumRecursive(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        int remainingBudget = targetSum - root.val;

        return pathSumRecursive(root.left, remainingBudget) ||
                pathSumRecursive(root.right, remainingBudget);
    }

    public class TreeNode {
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
