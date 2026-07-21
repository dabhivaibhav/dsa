package binary_search_tree.medium_problems;

/*
Leetcode 235. Lowest Common Ancestor of a Binary Search Tree

Given a binary search tree (BST), find the lowest common ancestor (LCA)
node of two given nodes in the BST.
According to the definition of LCA on Wikipedia: “The lowest common
ancestor is defined between two nodes p and q as the lowest node in
T that has both p and q as descendants (where we allow a node to be
a descendant of itself).”

Example 1:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.

Example 2:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [2,1], p = 2, q = 1
Output: 2

Constraints:
            The number of nodes in the tree is in the range [2, 10^5].
            -10^9 <= Node.val <= 10^9
            All Node.val are unique.
            p != q
            p and q will exist in the BST.
 */
public class LCAOfBST {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
    }


    /*
     * WHAT THIS METHOD DOES (lowestCommonAncestorOptimal):
     * Finds the lowest common ancestor of p and q in a BST with a single descending walk.
     * At each node: both values smaller -> go left; both larger -> go right; otherwise this
     * node is the split point and IS the LCA. O(H) time, O(1) space, no recursion.
     *
     * THE SENTENCE: the LCA is the first node where p and q stop being on the same side.
     *
     * ---
     *
     * THE "FIND THE SPLIT NODE" PATTERN (LCA IN A BST)
     *
     * Your Thought Process & Intuition:
     * 1. CASH THE GUARANTEE (ritual Q1, answered fully this time): comparing p and q
     *    against the current node tells you which SIDE each lives on without looking at
     *    the tree's shape. Same side -> the LCA is deeper on that side, descend. Opposite
     *    sides -> STOP: step left and you lose the right one, step right and you lose the
     *    left one, so the current node is the last node from which both are reachable.
     *    "They have been separated from this node" = this node is the answer.
     *
     * 2. THE ANCESTOR CASE COSTS NOTHING (the elegance to say out loud): a node counts as
     *    its own ancestor (p=2, q=4 -> answer 2). No special case needed: at node 2,
     *    "both greater" fails because 2 > 2 is FALSE under the STRICT comparison, so the
     *    walk falls into the else and returns node 2, which is p itself. The strict < and >
     *    swallow equality for free. Soften either comparison to <= or >= and exactly this
     *    case breaks.
     *
     * 3. LOOP, NOT RECURSION: one descending path, no backtracking, nothing to combine on
     *    the way up, the same argument as search and floor/ceil. Ninth appearance of the
     *    one BST move: compare at a node, discard a whole side, descend.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     * 1. Strict comparisons: load-bearing (see intuition 2). Do not "fix" them.
     * 2. p.val / q.val, not p / q: the parameters are TreeNodes; comparisons are on values.
     * 3. The return after the loop is UNREACHABLE in practice: the problem guarantees both
     *    nodes exist, so the split always fires before cur falls off the tree. Java just
     *    cannot prove that, so it demands a statement. (Returning root there is slightly
     *    misleading; null with a comment saying "unreachable: p and q guaranteed present"
     *    reads better in review.)
     *
     * TRAPS AVOIDED / NOTES:
     * - RELIES ON THE INPUT BEING A REAL BST: feed it a plain binary tree and it walks the
     *   wrong way silently. (My main() currently builds a NON-BST test tree; the brute
     *   force tolerates it, this method does not.)
     * - If p or q were NOT guaranteed present, this walk could return a wrong node
     *   confidently; the guarantee is what licenses never verifying they exist.
     *
     * ALGORITHM STEPS:
     * Step 1: cur = root.
     * Step 2: While cur != null: both < cur -> left; both > cur -> right; else return cur.
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(H), one root-to-split path, one comparison pair per level.
     *    (O(log N) balanced, O(N) skewed; never a flat "O(log N)".)
     * -> Space: O(1). One pointer.
     *
     * INTERVIEW TAKEAWAY:
     * - Open with the sentence; the three-way split IS the algorithm.
     * - Say why equality needs no special case (strict comparisons + the else).
     * - Contrast with the binary-tree version below: ordering collapses O(N) signal
     *   recursion into an O(H) walk. That contrast is the answer to "why is BST easier."
     */
    private static TreeNode lowestCommonAncestorOptimal(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode ans = root;

        while (ans != null) {
            if (p.val < ans.val && q.val < ans.val) {
                ans = ans.left;
            } else if (p.val > ans.val && q.val > ans.val) {
                ans = ans.right;
            } else {
                return ans;
            }
        }
        return root;
    }

    /*
     * WHAT THIS METHOD DOES (lowestCommonAncestorBruteForce):
     * LCA for ANY binary tree (this is the standard LC 236 solution): recurse both
     * subtrees, treat each returned node as a SIGNAL of "I found p or q (or their LCA)
     * down here," and combine the two signals at each node. Works without any ordering
     * property. O(N) time, O(H) stack.
     *
     * THE SENTENCE: recurse both sides; the first node where both sides report a find is
     * the LCA.
     *
     * ---
     *
     * THE "TWO SIGNALS MEET" PATTERN (LCA IN A PLAIN BINARY TREE)
     *
     * Your Thought Process & Intuition:
     * 1. WHAT A CALL RETURNS (the whole trick): from any subtree, the call returns
     *    - null: neither p nor q is down here
     *    - p or q: exactly one of them is down here (returned the moment it is seen)
     *    - some other node: BOTH are down here, and that node is already their LCA
     *
     * 2. THE COMBINE AT EACH NODE:
     *    - I AM p or q -> return myself immediately. No need to look deeper: if the other
     *      one is below me, I am its ancestor, hence the LCA; if it is elsewhere, some
     *      ancestor above me will see two signals. Either way returning me is correct.
     *    - left and right BOTH non-null -> p and q live on opposite sides of me: I am the
     *      meeting point, return me. (The binary-tree version of "the split node.")
     *    - one side non-null -> pass that signal up unchanged.
     *    - both null -> nothing here, return null.
     *
     * 3. REFERENCE EQUALITY (root == p) IS CORRECT HERE: LeetCode hands the actual node
     *    objects, and values might repeat in a general binary tree, so compare identities,
     *    not vals. (Contrast the BST version, which compares vals and leans on uniqueness.)
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     * 1. Return-and-combine, not fields: the verdict must be REACTED TO at every level
     *    (two signals -> stop and return self), so it travels by returns, the same
     *    principle as validate-BST's boolean chain.
     * 2. Both children are always recursed: no ordering exists to discard a side. That is
     *    exactly the O(N) the BST version's ordering eliminates.
     * 3. Also leans on "both nodes exist": if q were absent, finding p alone still bubbles
     *    p to the top, a wrong confident answer. The 236 follow-up variant (nodes may be
     *    absent) requires counting finds before trusting the result.
     *
     * ALGORITHM STEPS:
     * Step 1: null -> null. Root is p or q -> root.
     * Step 2: left = recurse(left), right = recurse(right).
     * Step 3: both non-null -> root. One non-null -> that one. Else null.
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N), every node visited in the worst case; nothing prunes the search.
     * -> Space: O(H) recursion stack (O(log N) balanced, O(N) skewed).
     *
     * INTERVIEW TAKEAWAY:
     * - This IS the LC 236 answer; owning both versions means owning the contrast:
     *   no ordering -> search both sides, O(N); ordering -> discard a side per step, O(H).
     * - Know what each return value MEANS (null / one of them / their LCA); the meanings
     *   are the algorithm.
     * - Identity comparison here, value comparison in the BST version, and why.
     */
    private static TreeNode lowestCommonAncestorBruteForce(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root == p || root == q) {
            return root;
        }

        TreeNode leftSignal = lowestCommonAncestorBruteForce(root.left, p, q);
        TreeNode rightSignal = lowestCommonAncestorBruteForce(root.right, p, q);

        if (leftSignal != null && rightSignal != null) {
            return root;
        }

        if (leftSignal != null) {
            return leftSignal;
        }

        return rightSignal;
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
