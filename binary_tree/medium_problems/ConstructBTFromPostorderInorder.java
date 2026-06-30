package binary_tree.medium_problems;

import java.util.HashMap;

/*
Leetcode 106: Construct Binary Tree from Inorder and Postorder Traversal

Given two integer arrays inorder and postorder where inorder is the inorder
traversal of a binary tree and postorder is the postorder traversal of the
same tree, construct and return the binary tree.

Example 1:
Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
Output: [3,9,20,null,null,15,7]

Example 2:
Input: inorder = [-1], postorder = [-1]
Output: [-1]


Constraints:
            1 <= inorder.length <= 3000
            postorder.length == inorder.length
            -3000 <= inorder[i], postorder[i] <= 3000
            inorder and postorder consist of unique values.
            Each value of postorder also appears in inorder.
            inorder is guaranteed to be the inorder traversal of the tree.
            postorder is guaranteed to be the postorder traversal of the tree.
 */
public class ConstructBTFromPostorderInorder {

    private static HashMap<Integer, Integer> map = new HashMap<>();

    private static int postIndex;

    /*
     * WHAT THIS METHOD DOES:
     * Reconstructs the unique binary tree from its inorder and postorder traversals.
     * Postorder's LAST element is the root, so this consumes the postorder array from
     * the BACK using a single shared pointer (postIndex) that walks left one step per
     * node created. Inorder is used only to split each subtree into left and right.
     * No postorder boundary arithmetic at all. Assumes node values are unique.
     *
     * ---
     *
     * THE "CONSUME REVERSED POSTORDER WITH A POINTER" PATTERN (BUILD TREE, INORDER+POSTORDER)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE OBJECTIVE: Same engine as preorder+inorder (problem 105), but the
     *    root now comes from the END of postorder, not the front of preorder. Find the
     *    root, locate it in the inorder to split left vs right, recurse. The inorder
     *    handling is IDENTICAL to 105; only the root-source array changes.
     *
     * 2. THE KEY INSIGHT (READ POSTORDER BACKWARD):
     *    -> Postorder is [left, right, root]. Read BACKWARD it becomes [root, right,
     *       left]: root first, then the whole right subtree, then the whole left subtree.
     *    -> So if you consume postorder from the back, one element at a time, the order
     *       you meet nodes is exactly root -> (all of right subtree) -> (all of left
     *       subtree). A single shared pointer walking backward hands out nodes in that
     *       precise order. This eliminates ALL postorder index arithmetic: no postStart
     *       parameter, no leftSize math, no boundary computation. The pointer just
     *       decrements once per node.
     *
     * 3. WHY RIGHT IS BUILT BEFORE LEFT (the load-bearing decision):
     *    -> Because reversed postorder is root, then RIGHT, then left. After consuming
     *       the root, the next nodes the pointer will hand out belong to the RIGHT
     *       subtree. So the right subtree MUST be built first to receive them. Building
     *       left first would feed right-subtree nodes into the left subtree and produce
     *       a completely wrong tree. (In 105, preorder reads forward as root-LEFT-right,
     *       so it was the opposite order: left before right. This is the mirror of that.)
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Shared backward pointer postIndex (starts at postorder.length - 1):
     *    - Why? Consuming postorder in reversed order means each node is just "the next
     *      one back." A single decrementing index expresses that with zero arithmetic,
     *      far cleaner than passing and computing postorder boundaries.
     *    - Reset at the top of buildTree so a fresh call starts from the true last index.
     *
     * 2. HashMap value -> inorder index:
     *    - Why? Locating the root inside the inorder is needed at every node. The map
     *      makes it O(1) instead of an O(n) scan, keeping the whole solve O(n).
     *    - Relies on UNIQUE values (a value maps to one index). Duplicates would break it.
     *
     * 3. Base case inStart > inEnd (empty inorder range) -> return null:
     *    - Why? Same as 105: an empty range means no node here, and that null becomes a
     *      leaf's missing child. Only the inorder range needs checking; it fully encodes
     *      each subtree's size.
     *
     * ---
     *
     * MISTAKES / TRAPS ON THIS PROBLEM (reread before the next tree-build):
     * - STALE 105 VARIABLE NAMES copied over: used rootIdx (declared rootIndex) and
     *   preStart (the parameter was postStart). Pure copy-paste leftovers that wouldn't
     *   even compile. Lesson: when adapting a previous solution, rename deliberately.
     * - THE postStart CONTRADICTION (the real bug): first attempt initialized the
     *   postorder index to length-1 (the ROOT's position, the BACK) but then wrote
     *   formulas treating postStart as the FRONT where the left subtree begins. Those
     *   two conventions clash. The right-child start came out as postStart+leftSize+1
     *   = 5 on a length-5 array -> ArrayIndexOutOfBounds. A pointer can't mean both
     *   "root at the back" and "front of the slice" at once.
     * - THE FIX WAS A BETTER APPROACH, not a patched formula: instead of juggling a
     *   postorder boundary at all, switched to the single backward-walking pointer.
     *   That sidesteps the whole front-vs-back confusion, because the pointer just
     *   yields nodes in reversed-postorder order and the inorder bounds do the splitting.
     * - RIGHT-BEFORE-LEFT IS MANDATORY, not style (see intuition 3). Swapping the two
     *   recursive calls silently builds the wrong tree.
     *
     * - THE META-MISTAKE (carry this across all the tree problems): reflexively reusing
     *   105's "+1 for the root at the front" logic when the root here is at the BACK.
     *   The off-by-one lives in a DIFFERENT place per traversal. Don't pattern-match the
     *   formula; re-derive it from where the root actually sits in THIS array's layout.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Build map: value -> its index in the inorder array.
     * Step 2: Set postIndex = postorder.length - 1 (the root of the whole tree).
     * Step 3: Call build with inStart=0, inEnd=inorder.length-1.
     * Step 4: If inStart > inEnd, slice empty -> return null.
     * Step 5: root = postorder[postIndex]; create the node; then postIndex-- (consume it).
     * Step 6: rootIndex = map.get(rootVal).
     * Step 7: Build the RIGHT child first: build(rootIndex + 1, inEnd).
     *         Then the LEFT child: build(inStart, rootIndex - 1).
     * Step 8: return root.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - postIndex-- happens right AFTER reading the root, so the next call sees the next
     *   node back. The decrement order plus right-before-left recursion is the whole trick.
     * - root.right is built BEFORE root.left. Mandatory. Reversed postorder = root,right,left.
     * - inStart > inEnd is the empty-slice signal; produces null children for leaves.
     * - The map is the only reason finding the root is O(1) and the solve is O(n).
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N). Each node is created exactly once; per-node work is one
     *    O(1) map lookup plus constant pointer/arithmetic. A linear scan to find the
     *    root instead would make it O(N^2).
     * -> Space Complexity: O(N). The map holds N entries. The recursion stack adds O(H)
     *    where H is the tree height (O(log N) balanced, O(N) skewed), dominated by the
     *    O(N) map. (Note: postIndex/map are shared static state, fine for a single
     *    call but not reentrant; an instance field reset per call would be cleaner.)
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Postorder's last element is the root; consume postorder from the back.
     * - Reversed postorder is root -> right -> left, which is WHY you recurse right
     *   before left. State this clearly; it is the one thing an interviewer will probe.
     * - The inorder split is identical to 105; only the root source and recursion order
     *   change. The "+1 for the root" of 105 does NOT apply, because the root is at the
     *   back here, not the front.
     * - A value->index map keeps it O(n); works only because values are unique.
     * - Two mental models now exist (105 vs 106): pass explicit ranges with arithmetic,
     *   OR consume one array with a shared pointer in node-order. Pick per problem.
     */
    private static TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int index = 0; index < inorder.length; index++) {
            map.put(inorder[index], index);
        }
        postIndex = postorder.length - 1;
        return build(postorder, 0, inorder.length - 1);
    }

    private static TreeNode build(int[] postorder, int inStart, int inEnd) {
        if (inStart > inEnd) return null;
        int rootVal = postorder[postIndex];
        TreeNode root = new TreeNode(rootVal);
        postIndex--;
        int rootIndex = map.get(rootVal);
        root.right = build(postorder, rootIndex + 1, inEnd);
        root.left = build(postorder, inStart, rootIndex - 1);

        return root;
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
