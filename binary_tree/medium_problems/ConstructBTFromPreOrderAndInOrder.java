package binary_tree.medium_problems;

import java.util.HashMap;
import java.util.Map;

/*
Leetcode 105: Construct Binary Tree from Preorder and Inorder Traversal

Given two integer arrays preorder and inorder where preorder is the
preorder traversal of a binary tree and inorder is the inorder traversal
of the same tree, construct and return the binary tree.

Example 1:
Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]

Example 2:
Input: preorder = [-1], inorder = [-1]
Output: [-1]

Constraints:
            1 <= preorder.length <= 3000
            inorder.length == preorder.length
            -3000 <= preorder[i], inorder[i] <= 3000
            preorder and inorder consist of unique values.
            Each value of inorder also appears in preorder.
            preorder is guaranteed to be the preorder traversal of the tree.
            inorder is guaranteed to be the inorder traversal of the tree.
 */
public class ConstructBTFromPreOrderAndInOrder {

    // Map to store value -> index relations for O(1) lookups of the inorder array
    private static Map<Integer, Integer> inorderIndex = new HashMap<>();

    public static void main(String[] args) {

    }

    /*
     * WHAT THIS METHOD DOES:
     * Reconstructs the unique binary tree from its preorder and inorder traversals.
     * Preorder gives the root of each (sub)tree; inorder splits the remaining nodes
     * into left and right subtrees. The method recurses on index RANGES into both
     * arrays (no array copying), using a value->index map so locating each root in
     * the inorder is O(1). Assumes all node values are unique (guaranteed here).
     *
     * ---
     *
     * THE "ROOT FROM PREORDER, SPLIT FROM INORDER" PATTERN (BUILD TREE FROM TRAVERSALS)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE OBJECTIVE: Rebuild the exact tree. The engine: the FIRST element of
     *    the current preorder slice is the root. Find that root inside the inorder
     *    slice. Everything LEFT of it (in inorder) is the left subtree, everything
     *    RIGHT of it is the right subtree. That one root + one split turns the whole
     *    tree into two smaller versions of the same problem -> recursion.
     *
     * 2. THE KEY REALIZATION (THE INORDER COUNT DRIVES THE PREORDER CUT):
     *    -> Inorder tells you the SIZE of the left subtree (count of elements before
     *       the root). That size is what tells you where to CUT the preorder: after
     *       the root, the next leftSize elements are the left subtree's preorder, and
     *       everything after that is the right subtree's preorder.
     *    -> The two arrays talk to each other through that one number, leftSize. Get
     *       it wrong and the preorder is sliced in the wrong place and the tree mangles.
     *
     * 3. WHY RANGES, NOT COPIES: Instead of slicing new sub-arrays at each call (which
     *    would cost O(n) per call), the recursion passes index boundaries: preStart
     *    for the preorder, and inStart..inEnd for the inorder. Cheaper and cleaner.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. HashMap<Integer,Integer> inorderIndex (value -> index in inorder):
     *    - Why? "Find the root in the inorder" is needed at every node. A linear scan
     *      makes it O(n) per node -> O(n^2) overall. The map makes it O(1) -> O(n) overall.
     *    - Relies on values being UNIQUE: a value maps to exactly one index. With
     *      duplicates this lookup would be ambiguous and the approach would break.
     *
     * 2. leftSize = rootIdx - inStart (NOT just rootIdx):
     *    - Why? The count must be RELATIVE to where the current inorder slice starts,
     *      not absolute from index 0. Subtracting inStart gives the true left-subtree
     *      size for THIS slice.
     *
     * 3. Base case inStart > inEnd (empty range), not "index == 0":
     *    - Why? A node is absent exactly when its slice is EMPTY. When a child's range
     *      has start past end, there are zero nodes there -> return null. That null
     *      becoming a child pointer is literally "this node has no child on that side."
     *
     * ---
     *
     * MISTAKES / TRAPS ON THIS PROBLEM (reread before the next tree-build):
     * - THE RIGHT-CHILD PREORDER START is the killer line: preStart + leftSize + 1.
     *   "+1 for the root, +leftSize for the ENTIRE left subtree." Forgetting the
     *   leftSize term re-processes left-subtree nodes and corrupts the whole tree.
     *   This is THE classic bug in problem 105.
     * - LEFTSIZE MUST BE RELATIVE (rootIdx - inStart). Using rootIdx alone works only
     *   at the very first call (where inStart is 0) and breaks in every deeper call.
     * - BASE CASE keys off an EMPTY RANGE (inStart > inEnd), not "index is 0". (I
     *   first reached for "if index is 0" and had to correct it: it's about the slice
     *   being empty, which is also what correctly produces null children for leaves.)
     * - ONE base-case check is enough: left and right slices have equal size in both
     *   arrays, so an empty inorder slice means an empty preorder slice too. No need
     *   to also test the preorder boundary.
     *
     * - THE META-MISTAKE (carry this one): the pull to jump straight to code / to the
     *   finished comment BEFORE writing and tracing the index logic myself. On this
     *   problem the index arithmetic IS the lesson, so skipping the rep would have
     *   skipped the whole point. The fix that worked all weekend: write my own code,
     *   trace it on a concrete example, fix the bug, THEN record it.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Build inorderIndex mapping each value to its index in the inorder array.
     * Step 2: Call build with preStart=0, inStart=0, inEnd=inorder.length-1.
     * Step 3: If inStart > inEnd, the slice is empty -> return null.
     * Step 4: root = preorder[preStart]; create the node; rootIdx = inorderIndex.get(root).
     * Step 5: leftSize = rootIdx - inStart.
     * Step 6: root.left  = build(preStart + 1,            inStart,     rootIdx - 1).
     *         root.right = build(preStart + leftSize + 1, rootIdx + 1, inEnd).
     * Step 7: return root.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - root.right's preStart = preStart + leftSize + 1  -> skip the root (+1) AND the
     *   whole left subtree (+leftSize). The single most error-prone expression here.
     * - leftSize = rootIdx - inStart  -> relative count, not absolute.
     * - inStart > inEnd  -> the empty-slice signal; returns null, which correctly forms
     *   the missing children of leaves.
     * - The hash map is the only reason this is O(n) and not O(n^2).
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N). Each of the N nodes is created exactly once, and the
     *    per-node work (one map lookup + constant arithmetic) is O(1) thanks to the
     *    inorderIndex map. A linear scan to find the root instead would make it O(N^2).
     * -> Space Complexity: O(N). The map holds N entries. The recursion stack adds
     *    O(H) where H is the tree height (O(log N) balanced, O(N) skewed), dominated
     *    by the O(N) map. Total O(N).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Preorder front = root; inorder position of the root splits left vs right.
     * - The inorder split SIZE (leftSize) is what tells you where to cut the preorder.
     * - Recurse on index ranges, not copied sub-arrays, to stay efficient.
     * - The make-or-break line is the right child's preorder start: preStart+leftSize+1.
     * - A value->index map turns the repeated root-search from O(n) into O(1), making
     *   the whole solution O(n). This works only because values are unique.
     */
    private static TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        return build(preorder, inorder, 0, 0, inorder.length - 1);
    }

    private static TreeNode build(int[] preorder, int[] inorder,
                                  int preStart, int inStart, int inEnd) {

        // BASE CASE: when is this slice empty (no node to build)? Fill the condition.
        if (inStart > inEnd) return null;

        // root is the first element of the current preorder slice
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // where does the root sit in the inorder slice? (O(1) lookup)
        int rootIdx = inorderIndex.get(rootVal);

        // how many nodes are in the LEFT subtree?
        // (count of inorder elements before the root, RELATIVE to this slice's start)
        int leftSize = rootIdx - inStart;

        // Recurse for the left child:
        // - Next preorder root is right after the current one (preStart + 1)
        // - Inorder slice goes from the current inStart up to rootIdx - 1
        root.left = build(preorder, inorder, preStart + 1, inStart, rootIdx - 1);

        // Recurse for the right child:
        // - Next preorder root skips past the current root and the entire left subtree (preStart + leftSize + 1)
        // - Inorder slice goes from just after the root (rootIdx + 1) up to the current inEnd
        root.right = build(preorder, inorder, preStart + leftSize + 1, rootIdx + 1, inEnd);
        return root;
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
