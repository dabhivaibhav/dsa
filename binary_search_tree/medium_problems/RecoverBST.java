package binary_search_tree.medium_problems;

/*
Leetcode 99. Recover Binary Search Tree

You are given the root of a binary search tree (BST), where the values of
exactly two nodes of the tree were swapped by mistake. Recover the tree
without changing its structure.

Example 1:
Input: root = [1,3,null,null,2]
Output: [3,1,null,null,2]
Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.

Example 2:
Input: root = [3,1,4,null,null,2]
Output: [2,1,4,null,null,3]
Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.


Constraints:
            The number of nodes in the tree is in the range [2, 1000].
            -2^31 <= Node.val <= 2^31 - 1

Follow up: A solution using O(n) space is pretty straight-forward. Could you devise a constant O(1) space solution?
 */
public class RecoverBST {

    static TreeNode prev = null;
    static TreeNode second = null;
    static TreeNode first = null;


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        recoverTree(root);
    }

    /*
     * WHAT THIS METHOD DOES:
     * Recovers a BST where exactly two nodes have been swapped by mistake. Finds the two
     * violating nodes using an inorder walk with a prev pointer (the validate-BST skeleton),
     * then swaps their VALUES to restore the tree. O(N) time, O(H) stack space.
     *
     * THE SENTENCE: inorder of a correct BST is sorted; two swapped nodes create one or two
     * "drops" in that sorted sequence. Find the drops, swap the values back.
     *
     * ---
     *
     * THE "FIND THE DROPS IN THE SORTED SEQUENCE" PATTERN (RECOVER BST)
     *
     * Your Thought Process & Intuition:
     * 1. RITUAL Q1 (structure's guarantee): inorder of a BST is sorted. Two swapped nodes
     *    means exactly two values are out of place in that sequence. The problem lives in
     *    the SEQUENCE, not in the tree's shape.
     *
     * 2. RITUAL Q2 (nearest neighbor): validate BST. Same prev-based inorder walk, same
     *    "prev > current means something broke" detection. Validate returns true/false;
     *    this one RECORDS which nodes broke the order, then fixes them.
     *
     * 3. THE TWO CASES (the one new idea this problem adds):
     *    ADJACENT SWAP: sorted [1,2,3,4,5], swap 3 and 4 -> [1,2,4,3,5]. ONE drop (4>3).
     *      The two bad nodes are prev and current at that single drop.
     *    DISTANT SWAP:  sorted [1,2,3,4,5], swap 2 and 5 -> [1,5,3,4,2]. TWO drops (5>3
     *      and 4>2). The two bad nodes are PREV at the FIRST drop and CURRENT at the
     *      SECOND drop. Not both prevs, not both currents. The too-large value (5) shows
     *      up too early and gets caught as a prev; the too-small value (2) shows up too
     *      late and gets caught as a current.
     *
     * 4. WHY `second = root` RUNS ON EVERY DROP (the elegant part): on the first drop it
     *    sets second to current (correct for the adjacent case, where there IS only one
     *    drop). On the second drop it OVERWRITES second with the new current (correct for
     *    the distant case). One unconditional assignment handles both cases with no if/else
     *    between them. `first` uses an if-guard so it only records once; `second` records
     *    every time so the last drop wins.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     * 1. Three shared fields: prev, first, second, all reset in the public method:
     *    - Why fields? Cross-call state, same debt as count/answer/prev in every prior
     *      tree solution. Reset prevents run-two inheriting run-one's nodes.
     *    - prev tracks the last-visited node (the validate-BST pointer).
     *    - first captures prev at the FIRST drop (the too-large node).
     *    - second captures current at EVERY drop (the too-small node, last drop wins).
     *
     * 2. prev is a TreeNode, not an Integer:
     *    - Why? In validate BST prev held an Integer because only comparison was needed.
     *      Here prev must be ASSIGNED to first, which is a TreeNode. Types must match.
     *
     * 3. Swap VALUES (int temp), not node references:
     *    - Why? Swapping values is O(1) and requires no re-parenting. Moving nodes would
     *      require knowing their parents, re-wiring children, handling the root case,
     *      all the delete-level complexity, for zero benefit.
     *
     * 4. The visit sits between the recursive calls:
     *    - Why? Inorder. The position IS the traversal. Same as validate, kth smallest,
     *      and every other inorder-based BST solution in my library.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - FORGOT `prev = root` AFTER THE IF BLOCK: the exact line that makes prev track the
     *   last-visited node. Without it, prev stays null forever, the null-guard never passes,
     *   first and second are never set, and the swap throws NullPointerException. This line
     *   was present in my validate-BST code and fell out during the copy. The visit point
     *   does TWO jobs: check against prev, then BECOME prev. Dropping the second half guts
     *   the walk silently.
     *   HOW TO CATCH: after copying a pattern, diff the visit blocks side by side. Every
     *   line in the original must be accounted for in the copy: present, or deliberately
     *   removed with a reason. "It fell out" is the signal that no accounting happened.
     *
     * - SWAPPED REFERENCES INSTEAD OF VALUES: wrote `TreeNode temp = second;` then
     *   `second.val = first.val; first.val = temp.val;`. temp is a REFERENCE, not a copy:
     *   it points at the same object as second, so overwriting second.val also overwrites
     *   temp.val. Both nodes end up holding first's original value. Fix: `int temp =
     *   first.val;` copies the NUMBER, which is immune to later node mutations. This is
     *   the delete lesson, same wall: variables POINT, nodes HOLD, and copying a pointer
     *   does not copy the data inside.
     *
     * - SECOND PARAGRAPH OF MY FIRST APPROACH WAS THE WRONG FRAME: "save prev when going
     *   left, keep root when going right, decide per subtree." That is the min/max bounds
     *   frame from validate BST, which detects violations but cannot identify WHICH TWO
     *   NODES to swap. The prev-based walk is the right frame because it finds the exact
     *   pair through the drop signature. Ritual Q2 would have handed it immediately:
     *   "nearest neighbor is validate BST, same prev walk."
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Reset prev, first, second to null.
     * Step 2: Inorder traversal. At each visit (between the recursive calls):
     *         - If prev != null AND prev.val >= root.val (a drop):
     *             - If first == null: first = prev.     (first drop, record the too-large)
     *             - second = root.                      (every drop, record the too-small)
     *         - prev = root.                            (always, become the new prev)
     * Step 3: Swap first.val and second.val using an int temp.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - `prev = root` runs UNCONDITIONALLY after the if block, at every visit. Without it,
     *   the detection is dead.
     * - `first` is guarded (set once); `second` is unconditional (last drop wins). That
     *   asymmetry handles both adjacent and distant swaps with no case-split.
     * - The swap is on VALUES (int), never references (TreeNode). Reference swap copies
     *   the pointer, not the data.
     * - >= not just >: equal values still violate strict BST ordering.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N). One full inorder traversal, O(1) work per node (one comparison, a few
     *    conditional assignments). The swap at the end is O(1).
     * -> Space: O(H) recursion stack. Three extra pointers (prev, first, second) are O(1).
     *    Total O(H). (O(log N) balanced, O(N) skewed.)
     *
     * THE LADDER (say unprompted):
     *    1. Inorder into a list, sort, compare to find the two mismatches: O(N) time,
     *       O(N) space. The dumbest correct version.
     *    2. This version, prev-based inorder walk: O(N) time, O(H) space. No list.
     *    3. Morris inorder with the same three pointers: O(N) time, O(1) space. Threading
     *       replaces the stack. Same upgrade as every other Morris rung in my library.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Open with the sentence: swapped nodes create drops in the sorted inorder sequence.
     * - Know the two-case signature: one drop = adjacent swap (first=prev, second=current),
     *   two drops = distant swap (first=prev-at-first-drop, second=current-at-second-drop).
     *   Say WHY the asymmetry: the too-large value is caught as a prev, the too-small as a
     *   current.
     * - The unconditional `second = root` handling both cases in one line is the thing that
     *   makes an interviewer nod. Say it.
     * - Swap values, not nodes. Say why: no re-parenting needed.
     * - This is validate BST + recording + a value swap. Three problems from the library
     *   (validate, the prev pattern, the value-vs-reference lesson) converge here.
     */
    public static void recoverTree(TreeNode root) {
        prev = null;
        second = null;
        first = null;
        inOrder(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    public static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        inOrder(root.left);
        if (prev != null && prev.val >= root.val) {
            if (first == null) {
                first = prev;
            }
            second = root;
        }
        prev = root;
        inOrder(root.right);
    }

    private static class TreeNode {
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
