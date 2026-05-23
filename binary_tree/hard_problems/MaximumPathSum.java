package binary_tree.hard_problems;

/*
Leetcode 124. Binary Tree Maximum Path Sum

A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence
has an edge connecting them. A node can only appear in the sequence at most once. Note that the
path does not need to pass through the root.
The path sum of a path is the sum of the node's values in the path.
Given the root of a binary tree, return the maximum path sum of any non-empty path.

Example 1:
Input: root = [1,2,3]
Output: 6
Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.

Example 2:
Input: root = [-10,9,20,null,null,15,7]
Output: 42
Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.

Constraints:
            The number of nodes in the tree is in the range [1, 3 * 104].
            -1000 <= Node.val <= 1000
 */
public class MaximumPathSum {


    private static int maxSum = Integer.MIN_VALUE;  // Global variable to store max path sum


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);

        System.out.println("Maximum Path: " + maxPathSum(root));
    }

    /*
     * WHAT THIS METHOD DOES:
     * This method finds the highest possible sum you can get by adding up the values
     * of nodes along any continuous path in a binary tree. The path must contain
     * at least one node, does not have to pass through the absolute top root,
     * and cannot branch or split—it must be a single, continuous line.
     *
     * MENTAL MODEL: THE GOLD MINING EXPEDITION
     * Imagine you are exploring a vertical network of mine shafts (nodes). Each
     * shaft contains either a pile of pure gold (positive values) or a pile of toxic
     * waste that costs money to clean up (negative values).
     *
     * You want to establish a single continuous tunnel that uncovers the absolute
     * maximum amount of wealth.
     * At any given shaft (node), you look down at your left tunnel and right tunnel:
     * 1. If a branch returns a negative net worth, you choose to completely block
     *    it off (Math.max(0, branch))—you are better off not extending your tunnel there!
     * 2. You calculate the total wealth of a "complete horseshoe arch" passing
     *    through you: (Left Gain + Right Gain + Your Gold). You compare this to
     *    the global record on your scoreboard (maxSum).
     * 3. However, when reporting back up to your supervisor shaft (the parent node),
     *    you can only offer ONE single continuous path. You can't split left and
     *    right for them, so you send up your best single branch plus your own gold.
     *
     * CORE IDEA: POSTORDER MAXIMUM EXTRACTION WITH NEGATIVE FILTERING
     * This builds directly upon the "Diameter of a Binary Tree" pattern. Instead of
     * counting edges (heights), we accumulate node values.
     *
     * The trickiest part is handling negative numbers. If a subtree's total contribution
     * is negative, a greedy choice dictates we drop it entirely by treating its value
     * as 0. Our recursive function returns the maximum single-branch path sum extending
     * from that node downward, while updating a global tracker (`maxSum`) to record any
     * complete arch paths.
     *
     * STEP-BY-STEP LOGIC:
     *
     * 1. THE WRAPPER (maxPathSum):
     *    - Sets the global `maxSum` scoreboard to Integer.MIN_VALUE (crucial because
     *      the entire tree could consist of negative numbers, so 0 is not a safe default).
     *    - Starts the depth-first search engine `dfs(root)`.
     *    - Returns the final record-breaking sum found.
     *
     * 2. THE WORKER ENGINE (dfs):
     *    - Base Case: If the current node is null, empty space contributes a sum of 0.
     *    - Left Gain: Find the maximum path sum from the left child. If it's negative,
     *      chop it off and clip it to 0 using `Math.max(0, dfs(node.left))`.
     *    - Right Gain: Find the maximum path sum from the right child. If it's negative,
     *      clip it to 0 using `Math.max(0, dfs(node.right))`.
     *    - The Local Umbrella Sum: Calculate what happens if we create a full path
     *      combining both sides through the current node: left + right + node.val.
     *    - Scoreboard Update: Check if this local sum beats the global `maxSum`.
     *    - The Single-Branch Return: Pass only the *best single path* up to the parent
     *      node: `Math.max(left, right) + node.val`.
     *
     * CONNECTION TO WHAT YOU KNOW:
     * This code matches the exact structure of Diameter of a Binary Tree!
     * Let's align them side-by-side mentally:
     *    - Diameter: local Diameter = leftHeight + rightHeight
     *    - Max Path Sum: local Sum = leftGain + rightGain + node.val
     *
     * In Diameter, a node returns `Math.max(left, right) + 1` to add itself as 1 edge.
     * In Max Path Sum, a node returns `Math.max(left, right) + node.val` to add its
     * actual numeric value. The only structural addition is the safety clamp
     * `Math.max(0, ...)` to defend against negative values.
     *
     * COMPLEXITY ANALYSIS:
     * Time Complexity: O(n)
     * Every node in the binary tree is processed exactly once during the bottom-up
     * traversal, ensuring linear time complexity.
     *
     * Space Complexity: O(h), where h is the height of the tree
     * Memory footprint relies directly on the execution stack frames. In the worst
     * case (a completely skewed tree), it reaches O(n). In a well-balanced tree,
     * it drops down to an efficient O(log n).
     *
     * INTERVIEW TAKEAWAY:
     * The key distinction in this problem is understanding the difference between what
     * a node calculates locally for the global scoreboard versus what it can return
     * to its parent. A path cannot branch; therefore, a node can include both children
     * when checking a final answer locally, but it can only pass its single best branch
     * up the chain.
     */
    public static int maxPathSum(TreeNode root) {
        dfs(root);
        return maxSum;
    }

    // DFS recursive function
    private static int dfs(TreeNode node) {
        if (node == null) return 0;

        // Calculate left and right subtree max path
        int left = Math.max(0, dfs(node.left));
        int right = Math.max(0, dfs(node.right));

        // Update max sum considering current node
        maxSum = Math.max(
                maxSum,
                left + right + node.val
        );

        // Return one-sided path
        return Math.max(left, right) + node.val;
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
