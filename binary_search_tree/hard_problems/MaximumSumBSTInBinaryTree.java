package binary_search_tree.hard_problems;

/*
Leetcode 1373. Maximum Sum BST in Binary Tree

Given a binary tree root, return the maximum sum of all keys of any sub-tree which is also a Binary Search Tree (BST).

Assume a BST is defined as follows:
The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.

Example 1:
Input: root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
Output: 20
Explanation: Maximum sum in a valid Binary search tree is obtained in root node with key equal to 3.

Example 2:
Input: root = [4,3,null,1,2]
Output: 2
Explanation: Maximum sum in a valid Binary search tree is obtained in a single root node with key equal to 2.

Example 3:
Input: root = [-4,-2,-5]
Output: 0
Explanation: All values are negatives. Return an empty BST.

Constraints:
            The number of nodes in the tree is in the range [1, 4 * 10^4].
            -4 * 10^4 <= Node.val <= 4 * 10^4
 */
public class MaximumSumBSTInBinaryTree {

    private static int answer = 0;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.left.left = new TreeNode(8);
        System.out.println(maxSumBST(root));
    }

    private static int maxSumBST(TreeNode root) {
        answer = 0;
        postOrder(root);
        return answer;

    }

    /*
     * WHAT THIS METHOD DOES:
     * Finds the maximum sum among all valid BST subtrees inside a binary tree. Each node
     * collects a four-field bundle (valid, sum, min, max) from both children, checks BST
     * validity locally, computes its subtree sum if valid, and updates a global max.
     * Post-order, one pass: O(N) time, O(H) space.
     *
     * THE SENTENCE: post-order traversal where each node asks its children "are you a BST,
     * what's your sum, and what are your extremes?" and decides its own status from the
     * answers.
     *
     * ---
     *
     * THE "BUNDLE-UP POST-ORDER" PATTERN (MAXIMUM SUM BST IN BINARY TREE)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE QUESTIONS I IDENTIFIED: can a binary tree contain more than one BST? Yes.
     *    How do I identify them? Validate each subtree. How do I find the max sum? Track a
     *    global best across all valid subtrees. These three questions, asked upfront, shaped
     *    the whole approach.
     *
     * 2. WHY POST-ORDER (the "information flows up" insight): a node cannot know whether
     *    its subtree is a valid BST until BOTH children have reported in. It needs to know:
     *    are my children valid? What are the extreme values below me? What's the sum below
     *    me? All of that lives BELOW the node, so the children must go first: left, right,
     *    THEN me. That is post-order, and this is the general rule: any tree problem where
     *    the parent's answer depends on its children's answers is post-order.
     *
     * 3. THE BUNDLE (what each call hands to its parent): four fields packed into an int[4]:
     *    [0] valid: 1 if this subtree is a BST, 0 if not.
     *    [1] sum:   total of all node values in this subtree (meaningful only when valid).
     *    [2] min:   smallest value in this subtree.
     *    [3] max:   largest value in this subtree.
     *    The parent reads these four numbers and does O(1) work to produce its own bundle.
     *    No re-traversal, no re-validation, one pass total.
     *
     * 4. THE VALIDITY CHECK AT EACH NODE (four conditions ANDed):
     *    - left is valid
     *    - right is valid
     *    - my value > left's MAX (everything on my left is smaller than me)
     *    - my value < right's MIN (everything on my right is larger than me)
     *    Note: comparing against the child's EXTREMES, not the child's value. A child's
     *    value might be fine while a deep descendant violates the constraint. The extremes
     *    carry the whole-subtree guarantee upward in O(1). This is the same subtree-not-child
     *    lesson from my searchBST and validateBST comment blocks, encoded as data.
     *
     * 5. THE WORKED EXAMPLE THAT LOCKED IT IN: node 10, left bundle (valid, sum=6, min=1,
     *    max=8), right bundle (valid, sum=35, min=20, max=25). Check: 10 > 8 yes, 10 < 20
     *    yes, both valid. Sum = 6 + 10 + 35 = 51. Min = min(1, 10) = 1. Max = max(25, 10)
     *    = 25. Bundle up: (valid, 51, 1, 25). Pure arithmetic, no tree-staring.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. The null bundle: {1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE}:
     *    - Why? A null child must not disqualify its parent (valid=1), must not add to the
     *      sum (sum=0), and must not fail ANY comparison. For the parent's "my val > left
     *      max" check to pass vacuously, left max must be as small as possible: MIN_VALUE.
     *      For "my val < right min," right min must be as large as possible: MAX_VALUE.
     *      The min and max look BACKWARDS and that is exactly right: "nothing" has an
     *      impossibly high floor and an impossibly low ceiling, so any real value passes.
     *    - Why this matters: a leaf calls postOrder on two nulls and gets two of these.
     *      The leaf's checks become node.val > MIN_VALUE (always true) and node.val
     *      MAX_VALUE (always true). The leaf's bundle becomes {1, node.val, node.val,
     *      node.val}, which is the base case I identified: the node itself is the BST and
     *      min and max are its own value. No special leaf-case code needed.
     *
     * 2. The invalid bundle: {0, 0, 0, 0}:
     *    - Why? Once a subtree is invalid, EVERY ancestor must also be invalid, because a
     *      BST cannot contain a non-BST subtree. The 0 in position [0] fails the left[0]==1
     *      or right[0]==1 check at every level above. The infection travels upward
     *      automatically, no flag, no field, just the bundle.
     *
     * 3. Global answer field, initialized to 0:
     *    - Why 0? The problem says return 0 if no valid BST subtree has a positive sum. A
     *      single node with a negative value IS a valid BST, but its sum is negative, and
     *      the problem wants 0 in that case. So 0 is the floor, not Integer.MIN_VALUE.
     *    - Updated inside the valid branch only: answer = Math.max(answer, sum). Invalid
     *      subtrees never touch it.
     *
     * 4. int[4] as a poor person's tuple:
     *    - Why? Java lacks tuples. Alternatives: a small class with named fields (verbose
     *      but readable), or four separate return values via four traversals (wasteful).
     *      The array is interview-pragmatic. Commenting the indices prevents confusion
     *      between [2] (min) and [3] (max).
     *
     * 5. Min uses Math.min(leftBundle.min, node.val), not just leftBundle.min:
     *    - Why? If the node has no left child, leftBundle.min is MAX_VALUE, so Math.min
     *      picks node.val, the node IS its own subtree's minimum. With a left child, the
     *      left's min is always smaller (it's a valid BST and node.val > left's max >=
     *      left's min). Same logic mirrored for max. The Math.min/Math.max makes the leaf
     *      case and the internal-node case collapse into one expression.
     *
     * ---
     *
     * MISTAKES / TRAPS:
     * - TRIED TO RE-DERIVE THE BUNDLES INSTEAD OF READING THEM: when given explicit numbers
     *   (sum=6, min=1, max=8), rebuilt a tree from memory and produced different numbers
     *   (sum=14, min=15). The whole point of the bundle is that the parent treats it as a
     *   BLACK BOX, four numbers, no tree reconstruction. Same as the mock interview: under
     *   pressure, went abstract instead of reading the concrete values in front of me.
     * - "ADD THE MIN AND MAX TO THE SUM": confused the sum computation with the validity
     *   check. Min and max are for CHECKING ("is my value in range?"). Sum is for COUNTING
     *   ("what's the total?"). They serve different questions and never mix.
     * - THE GENERAL PATTERN WORTH NAMING: any problem where a parent's answer depends on
     *   aggregated information from its children is a "bundle-up post-order." The bundle
     *   fields are determined by the question: here it was validity + sum + extremes. For
     *   other problems it might be height + balance-flag (balanced BT), or diameter + height.
     *   The shape is always: children report up, parent combines, one pass.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: answer = 0. Call postOrder(root).
     * Step 2 (postOrder): if null, return {1, 0, MAX_VALUE, MIN_VALUE}.
     * Step 3: Recurse left, recurse right (post-order: children first).
     * Step 4: If both valid AND node.val > left max AND node.val < right min:
     *         sum = left sum + node.val + right sum.
     *         min = Math.min(left min, node.val).
     *         max = Math.max(right max, node.val).
     *         Update answer if sum > answer.
     *         Return {1, sum, min, max}.
     * Step 5: Else return {0, 0, 0, 0}. (Invalid, infects all ancestors.)
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N). Each node visited exactly once, O(1) work per node (four comparisons,
     *    three additions, two Math calls). One pass, no re-traversal.
     * -> Space: O(H) recursion stack. The int[4] arrays are O(1) each, created per call
     *    and discarded after the parent reads them. No global collection.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Frame it as "post-order with a bundle." Name the four fields and the null bundle
     *   before writing code. The design IS the fields; the code is transcription.
     * - The null bundle's backwards min/max (MAX_VALUE for min, MIN_VALUE for max) is the
     *   elegant part: it makes leaves, one-child nodes, and internal nodes all run the same
     *   code with zero special cases. Say WHY it works, not just what it is.
     * - The invalid bundle's 0-in-valid-slot automatically infects every ancestor. No flag,
     *   no field, the data structure carries the signal.
     * - Comparing against the child's EXTREMES (not the child's value) is the subtree-not-
     *   child guarantee encoded as data. Same lesson as searchBST and validateBST, now
     *   carried upward in a bundle instead of checked by traversal.
     * - The general "bundle-up post-order" shape recurs in balanced-tree checks, diameter,
     *   and any problem where a parent needs aggregated child information. Know the shape
     *   and you recognize the family.
     */
    private static int[] postOrder(TreeNode root) {
        if (root == null) {
            return new int[]{1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        }

        int[] left = postOrder(root.left);
        int[] right = postOrder(root.right);

        if (left[0] == 1 && right[0] == 1
                && root.val > left[3]
                && root.val < right[2]) {

            int sum = left[1] + root.val + right[1];
            int min = Math.min(left[2], root.val);
            int max = Math.max(right[3], root.val);

            answer = Math.max(answer, sum);

            return new int[]{1, sum, min, max};
        }

        return new int[]{0, 0, 0, 0};
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

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
