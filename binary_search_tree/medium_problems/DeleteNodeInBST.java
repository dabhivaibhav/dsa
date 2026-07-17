package binary_search_tree.medium_problems;

/*
Leetcode 450. Delete Node in a BST

Given a root node reference of a BST and a key, delete the node with
the given key in the BST. Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:
Search for a node to remove.
If the node is found, delete the node.


Example 1:
Input: root = [5,3,6,2,4,null,7], key = 3
Output: [5,4,6,2,null,null,7]
Explanation: Given key to delete is 3. So we find the node with value 3 and delete it.
One valid answer is [5,4,6,2,null,null,7], shown in the above BST.
Please notice that another valid answer is [5,2,6,null,4,null,7] and it's also accepted.

Example 2:
Input: root = [5,3,6,2,4,null,7], key = 0
Output: [5,3,6,2,4,null,7]
Explanation: The tree does not contain a node with value = 0.

Example 3:
Input: root = [], key = 0
Output: []

Constraints:
            The number of nodes in the tree is in the range [0, 10^4].
            -10^5 <= Node.val <= 10^5
            Each node has a unique value.
            root is a valid binary search tree.
            -10^5 <= key <= 10^5

Follow up: Could you solve it with time complexity O(height of tree)?
 */
public class DeleteNodeInBST {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.left = new TreeNode(8);
        root.right.left.left = new TreeNode(9);
        System.out.println(deleteNode(root, 5));
    }


    /*
     * WHAT THIS METHOD DOES:
     * Deletes a node from a Binary Search Tree and returns the root. Works in THREE
     * SEPARATE PHASES: find the node, reduce the hard case to the easy case, then unlink.
     * Iterative: O(H) time, O(1) space.
     *
     * ---
     *
     * THE "REDUCE TO THE EASY CASE" PATTERN (DELETE FROM A BST)
     *
     * Your Thought Process & Intuition:
     *
     * 1. THE THREE CASES, SORTED BY CHILD COUNT (not by "has a subtree"):
     *      0 children -> replace the node with null
     *      1 child    -> replace the node with that child
     *      2 children -> the hard case, and the whole point of this problem
     *
     * 2. THE REFRAME THAT UNLOCKS THE HARD CASE:
     *    Do NOT ask "how do I remove this node?" That question has no clean answer. If you
     *    pull the node out you are left holding two orphaned subtrees and one empty slot.
     *    Ask instead: "WHAT VALUE IS ALLOWED TO SIT IN THIS SLOT?"
     *    If some value can legally live there, you never remove the node at all. You
     *    overwrite its value, and now you have a DIFFERENT, EASIER node to delete.
     *
     * 3. WHAT THE SLOT REQUIRES:
     *    Whatever sits there must be
     *      - greater than everything in the left subtree, AND
     *      - less than everything in the right subtree.
     *    Only TWO nodes in the whole tree can satisfy both:
     *      - PREDECESSOR = the largest value in the left subtree  = its RIGHTMOST node
     *      - SUCCESSOR   = the smallest value in the right subtree = its LEFTMOST node
     *    Either one works. This code uses the successor (the usual convention).
     *
     * 4. THE ANCESTOR CONSTRAINTS ARE FREE:
     *    You might worry the replacement also has to satisfy the ancestors (be less than
     *    the grandparent, and so on). It does, but you never check it. Anything pulled from
     *    inside this node's OWN subtrees was already living under those ancestors, so it
     *    already satisfies them by construction. Only the two LOCAL requirements matter.
     *
     * 5. THE PROOF THAT MAKES THIS WORK (I derived this one myself):
     *    QUESTION: after copying the successor's value up, I still have to delete the
     *    original successor node. What stops THAT from being another two-child problem,
     *    and another, forever?
     *    PROOF: the successor is the LEFTMOST node of the right subtree. Suppose it had a
     *    left child. That child would be further left than the successor. So the child
     *    would be the leftmost, not the successor. Contradiction. Therefore:
     *      THE LEFTMOST NODE OF ANY SUBTREE CANNOT HAVE A LEFT CHILD.
     *    So the successor has AT MOST ONE CHILD (a right child only), which means deleting
     *    it is the easy 0-or-1-child case. THE HARD CASE REDUCES TO THE EASY CASE. No new
     *    machinery, no infinite regress.
     *    This is not a fact about any particular tree. It is forced by what "leftmost"
     *    means. It is true of every tree, always.
     *
     * 6. PHASE 2 DELETES NOTHING. IT MOVES THE TARGET.
     *    This is the step that feels too easy to trust, which is exactly why my first
     *    attempt reached for hand-written pointer surgery instead. Three lines:
     *      copy the successor's value into the current node
     *      point `current` at the successor
     *      point `prev` at the successor's parent
     *    Phase 3 then runs unchanged. It does not know or care that phase 2 happened. It
     *    just deletes whatever `current` points at now, which is guaranteed easy.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. NO `left` BOOLEAN FLAG (unlike INSERT, which used one):
     *    - Why? Phase 2 can REASSIGN `prev`. A flag set back in phase 1 would then be stale
     *      and point at the wrong slot. So compare references instead:
     *          if (prev.left == current) prev.left = child; else prev.right = child;
     *      A reference check is always correct no matter which phase set `prev`.
     *    - This is the difference from insert: insert has only one phase, so a flag is safe
     *      there. Delete has two places that set `prev`, so it is not.
     *
     * 2. succParent starts at `current`, NOT at `current.right`:
     *    - Why? If `current.right` has no left child, the walk loop never runs, so `succ`
     *      stays as `current.right`, and its parent genuinely IS `current`.
     *    - This is the case in my own test tree (delete 8, where 9 is the immediate right
     *      child). Initializing to current.right gets this wrong.
     *
     * 3. child = (current.left != null) ? current.left : current.right:
     *    - Why? One line covers BOTH the 0-child and 1-child cases. Zero children means
     *      both are null, so child is null and the slot gets nulled out. One child means you
     *      get it, whichever side it is on. No separate leaf branch needed.
     *
     * 4. `if (prev == null) return child;`
     *    - Why? This is the DELETING-THE-ROOT case. The root has no parent, so there is
     *      nothing to attach to. The child becomes the new root. Without this line, the next
     *      statement dereferences a null `prev` and throws.
     *
     * 5. The FIND loop condition is `current != null && current.val != key`:
     *    - Why? Unlike insert (where the goal was to fall OFF the tree into a null slot),
     *      here the goal is to STOP ON the node. So there are two ways out, and they mean
     *      different things:
     *        current == null      -> key is not in the tree -> return root unchanged
     *        current.val == key   -> found it, and `prev` is its parent
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     *
     * - INFINITE LOOP (first attempt): the branch that FOUND the key set some flags but
     *   never advanced `current` and never broke. The loop spun forever.
     *   HOW TO CATCH: my own written rule, from the flatten problem: "on every branch, does
     *   the loop variable make progress?" And from floor/ceil: "for each branch, what input
     *   forces it to run?" The input that forces this branch is the MAIN case (key is in the
     *   tree). Both rules were written down. Neither was run.
     *
     * - SET `prev = current` IN THE FOUND BRANCH: that makes prev equal the node itself, not
     *   its PARENT. The parent assignment belongs in the WALKING branches, before the move.
     *   I had this correct in insert and dropped it here.
     *
     * - THE SUCCESSOR WALK WENT THE WRONG DIRECTION: started the walk at `current` instead
     *   of `current.right`, so it descended the LEFT subtree and found the smallest value
     *   there. Also assigned successorParent once and never updated it inside the loop, so
     *   it could not track.
     *
     * - PHASE 2 AS POINTER SURGERY: re-parented nodes by hand (successorParent.left = null;
     *   successor.right = ...; prev.right = successor). The entire reason the successor trick
     *   works is that you NEVER re-parent anything. Copy a value, retarget two pointers, done.
     *
     * - MERGED TWO INDEPENDENT QUESTIONS IN THE UNLINK: tangled "which slot of prev do I
     *   write?" (answered by the direction) with "which child of current do I promote?"
     *   (answered by which one exists). Splitting them collapses four branches into two lines.
     *
     * - MISSED THE LEAF CASE ENTIRELY: with both children null, none of my three branches
     *   matched, so a leaf would just stay in the tree.
     *
     * - "SUCCESSOR **AND** PREDECESSOR": thought I needed to find both. You need ONE value
     *   for ONE slot. Finding both leaves you holding two values and one hole.
     *
     * - "REPLACE IT WITH 9 **ONLY**": found one legal candidate and stopped. There are two
     *   (7 the predecessor, 9 the successor). Both produce a valid BST.
     *
     * - "IT MUST BE LESS THAN 10": true, but that is the LOOSE constraint, not the BINDING
     *   one. The binding one is > 7 and < 9. The 10 does no work in the proof at all.
     *
     * - CONFUSED ORDERING WITH BALANCE (three separate times): said "I never have to worry
     *   about balancing because the BST already provides that." The BST property provides
     *   ORDERING. It provides NOTHING about balance. Insert 1,2,3,4,5 in order and you get a
     *   valid BST that is a straight chain of height 5. Delete can make a tree MORE lopsided
     *   and nothing here fixes it. The true statement is: I do not have to rebalance because
     *   THE PROBLEM DOES NOT ASK ME TO. Keeping height at O(log N) requires rotations, which
     *   is what AVL and red-black trees do. That is a different, harder problem.
     *      ORDERING = correctness.  BALANCE = speed.  Never mix them.
     *
     * - THE META-PATTERN, AGAIN: almost every error above is the same shape. Name the first
     *   true thing and stop before the second one clamped to it. "Less than 10" (but not the
     *   tighter bound). "Replace with 9" (but there are two candidates). "It has a right
     *   child so promote it" (true fact, wrong question). "The BST provides structure" (true,
     *   but ordering, not balance).
     *
     * ---
     *
     * ALGORITHM STEPS:
     *
     * PHASE 1 - FIND:
     *   current = root, prev = null.
     *   While current is not null AND current.val != key:
     *     prev = current
     *     go left if key < current.val, else go right
     *   If current is null, the key is not in the tree: return root unchanged.
     *
     * PHASE 2 - REDUCE (only if current has TWO children):
     *   succParent = current, succ = current.right
     *   While succ.left is not null: succParent = succ; succ = succ.left
     *   current.val = succ.val      (copy the value up)
     *   current = succ              (retarget: now we delete the successor instead)
     *   prev = succParent
     *
     * PHASE 3 - UNLINK (current now has at most one child):
     *   child = current.left if it exists, else current.right
     *   If prev is null: return child          (we deleted the root)
     *   If prev.left == current: prev.left = child
     *   Else: prev.right = child
     *   Return root.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - The three phases are SEPARATE. Trying to do them in one pass is what produced six
     *   tangled variables in my first attempt. Each phase has one job.
     * - Phase 2 moves the target; it never deletes or re-parents.
     * - Reference comparison (prev.left == current), never a stale boolean flag.
     * - succParent starts at `current` so the immediate-right-child case works.
     * - prev == null is the delete-the-root case and must be checked before dereferencing.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(H), where H is the height. Phase 1 walks one root-to-leaf path.
     *    Phase 2 walks a second downward path (down the left spine of the right subtree).
     *    Two downward walks is still O(H), not more. Phase 3 is O(1).
     *    O(log N) if balanced, O(N) if skewed. (Never state a flat "O(log N)".)
     * -> Space Complexity: O(1). A handful of pointers. No recursion, no stack.
     *
     * THE ALTERNATIVE (worth knowing, and worth saying in an interview):
     *    The RECURSIVE version is far shorter, because `root.left = deleteNode(root.left,
     *    key)` makes the recursion carry the reattachment. No prev, no direction check, no
     *    root special case. It costs O(H) stack instead of O(1).
     *    That is a real tradeoff, not an obvious win either way: this iterative version buys
     *    O(1) space at the cost of significantly more state to keep straight. Knowing WHEN
     *    NOT to optimize is judgment, and saying that out loud lands better than either
     *    version alone.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Sort the cases by CHILD COUNT: 0, 1, or 2. Only the 2-child case is interesting.
     * - The 2-child case is solved by REDUCING it, not by attacking it. Copy the successor's
     *   value up, then delete the successor instead.
     * - SAY THE PROOF OUT LOUD, it is the strongest thing you know here: "the successor is
     *   the leftmost node of the right subtree, so it cannot have a left child, because that
     *   child would be further left and would be the leftmost instead. So it has at most one
     *   child, and deleting it is the easy case."
     * - Either the predecessor or the successor works. Say that; it shows you understand WHY
     *   rather than which one you memorized.
     * - Ordering is correctness, balance is speed. Delete preserves ordering and does not
     *   rebalance, because the problem does not ask for it.
     * - O(H) time, O(1) space iteratively; the recursive version is shorter but O(H) space.
     */
    private static TreeNode deleteNode(TreeNode root, int key) {
        // PHASE 1 — FIND
        TreeNode current = root;
        TreeNode prev = null;
        while (current != null && current.val != key) {
            prev = current;
            if (key < current.val) current = current.left;
            else current = current.right;
        }
        if (current == null) return root;          // key not in tree

        // PHASE 2 — move the target, delete nothing
        if (current.left != null && current.right != null) {
            TreeNode succParent = current;         // not current.right — see below
            TreeNode succ = current.right;
            while (succ.left != null) {
                succParent = succ;
                succ = succ.left;
            }
            current.val = succ.val;                // copy value up
            current = succ;                        // retarget
            prev = succParent;
        }

        // PHASE 3 — UNLINK (current now has at most one child)
        TreeNode child = (current.left != null) ? current.left : current.right;
        if (prev == null) return child;            // deleting the root
        if (prev.left == current) prev.left = child;
        else prev.right = child;
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
