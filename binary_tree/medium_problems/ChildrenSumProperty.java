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

    /*
     * WHAT THIS METHOD DOES:
     * This method checks whether a given Binary Tree strictly adheres to the
     * "Children Sum Property." This property dictates that for every single internal
     * (non-leaf) node in the tree, its local data value must be exactly equal to the
     * sum of the values of its immediate left and right children.
     *
     * ---
     *
     * THE "BOTTOM-UP AUDIT" PATTERN (CHILDREN SUM PROPERTY)
     *
     * Your Thought Process & Intuition:
     * 1. THE PERSPECTIVE: Checking this property is like running a financial audit
     * on an organization. You cannot verify if a regional director's budget allocation
     * matches their departments until you first verify that all lower-level branch
     * accounts beneath them balance out perfectly.
     *
     *
     *
     * 2. THE BASE CASE EXEMPTIONS:
     * → Empty trees (`root == null`) have no accounts to balance, so they are cleanly
     * marked valid (`true`).
     * → Leaf nodes (`left == null && right == null`) are the lowest-level field workers.
     * They don't have any children underneath them to account for, so they automatically
     * pass the safety check (`true`).
     *
     * 3. THE SAFE DELEGATION (NULL DEFENSE): When aggregating values from a parent's
     * perspective, a child node might not exist. We defensively intercept these null
     * lanes using inline conditional switches, treating a missing child node's financial
     * balance contribution as exactly `0`.
     *
     * ---
     *
     * WHY WE MUST USE POSTORDER TRAVERSAL:
     * Just like finding the Lowest Common Ancestor, verifying the Children Sum Property
     * is a bottom-up validation problem.
     *
     * If you tried to validate this top-down (Preorder), a node might look perfectly
     * valid relative to its immediate children, while those children themselves are completely
     * out of balance with *their* sub-branches below.
     *
     * Postorder Traversal explicitly dictates the execution order:
     * 1. Audit the Left Subtree completely.
     * 2. Audit the Right Subtree completely.
     * 3. Evaluate the Current Node (Verify if the local parent value equals the sum of children values).
     *
     * By bubbling the validation flags (`true`/`false`) up from the ground floor leaves, we
     * ensure that the entire foundation matches before passing a success signal to the top.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. The Call Stack Framework (Implicit Processing DFS Stack):
     * - Why? This problem does not allocate auxiliary memory maps or lists. It relies
     * entirely on the native program call stack frame hierarchy.
     * - Reason: By keeping stack frames synchronized to the depth of our exploration paths,
     * the compiler automatically remembers our tracking coordinates, allowing individual sub-problems
     * to remain cleanly isolated.
     *
     * 2. Primitive Boolean Short-Circuiting:
     * - Why? The execution concludes with a combined logical evaluation statement:
     * `return isLeftValid && isRightValid && isCurrentValid;`
     * - Reason: By evaluating boolean flags directly, we cleanly confirm that the condition
     * is met uniformly across every single layer of the tree topography.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Base Case 1. If the current node is `null`, return `true`.
     * Step 2: Base Case 2. If the current node is a leaf (`left == null && right == null`), return `true`.
     * Step 3: Run the Postorder exploration steps. Recursively dive all the way down to collect
     * validations from both wings:
     * - `isLeftValid = checkChildrenSum(root.left)`
     * - `isRightValid = checkChildrenSum(root.right)`
     * Step 4: Process the current node's validation check:
     * - Extract the left value (use `root.left.val` if present, else default to `0`).
     * - Extract the right value (use `root.right.val` if present, else default to `0`).
     * - Calculate the local condition: `isCurrentValid = (root.val == leftValue + rightValue)`.
     * Step 5: Merge all three audit checkpoints with a logical `&&`. The tree is valid if and only if
     * the left wing is valid, the right wing is valid, AND the current node matches.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - `if (root.left == null && root.right == null) return true;`
     * Your second base case checkpoint. Since leaves have no descendants to sum up, they serve as the
     * valid base stepping stones for our bottom-up calculations.
     * * - `int leftValue = (root.left != null) ? root.left.val : 0;`
     * Defensive value extraction. If a child branch is missing, it is evaluated as 0 so it doesn't
     * throw a NullPointerException when building the local comparison value.
     * * - `return isLeftValid && isRightValid && isCurrentValid;`
     * The ultimate combining rule. If even a single lower branch deep in the tree fails its local balance
     * check, that `false` flag cascades all the way back up to the top root.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * → Time Complexity: O(N) — Every node is visited exactly once.
     * The recursive postorder walker processes all N nodes in the tree structure exactly once.
     * At each node, the work is strictly bounded to constant time O(1) operations (null checks,
     * reference comparisons, and simple additions). Thus, the runtime scales completely linearly with
     * the size of the tree.
     *
     * → Space Complexity: O(H) — The recursive call stack goes as deep as the height of the tree.
     * No auxiliary data collections are instantiated. The memory footprint relies entirely on stack frames
     * generated during deep tree navigation. In a completely balanced binary tree structure, this
     * requires O(log N) space; if the tree collapses into a fully skewed line configuration,
     * stack memory expands to O(N).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * The Children Sum Property problem reinforces the core rule of tree validation: Trust your
     * subproblems.
     * - Don't look too deep from the top. Let your recursive calls do the heavy lifting of auditing
     * the lower subtrees.
     * - Once you receive the results from your left and right branches, your only responsibility at the
     * current node is to process the local values sitting immediately in front of you.
     * This keeps your code incredibly clean and makes it impossible for nested bugs to hide in your logic.
     */
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
