package binary_tree.hard_problems;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Morris Preorder Traversal

Given root of binary tree, return the preorder traversal of the binary tree.

Morris preorder Traversal is a tree traversal algorithm aiming to achieve a
space complexity of O(1) without recursion or an external data structure.


Example 1
Input : root = [1, 4, null, 4 2]
Output : [1, 4, 4, 2]

Example 2
Input : root = [1]
Output : [1]

Explanation : Only root node is present.

Constraints:
            1 <= Number of Nodes <= 100
            -100 Node.val <= 100
 */
public class MorrisPreorderTraversal {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        System.out.println(morrisPreOrderTraversal(root));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Returns the PREORDER traversal (root, left, right) of a binary tree using MORRIS
     * TRAVERSAL, achieving O(1) auxiliary space: no recursion, no stack, no queue. Like
     * Morris inorder, it temporarily threads each node's null right-pointer back to an
     * ancestor to create a return path, then removes the thread to restore the tree. The
     * ONLY difference from inorder is WHERE the value is recorded.
     *
     * ---
     *
     * THE "THREADED TREE, RECORD-ON-ARRIVAL" PATTERN (MORRIS PREORDER, O(1) SPACE)
     *
     * Your Thought Process & Intuition:
     * 1. SAME ENGINE AS MORRIS INORDER: the threading mechanism is identical. Find the
     *    predecessor (rightmost node of the left subtree), thread its right pointer back
     *    to current so you can return, descend left, and tear the thread down on the way
     *    back. All of that is unchanged.
     *
     * 2. THE ONE DIFFERENCE (record on FIRST arrival): preorder is root-FIRST, so a node
     *    is recorded the first time you reach it, BEFORE descending into its left subtree.
     *    Inorder recorded a node AFTER its left subtree finished (in case A and case B2).
     *    Preorder records it on arrival (in case A and case B1).
     *
     * 3. THE THREE CASES AT ANY NODE (current):
     *    A) current has NO left child  -> first arrival, and no left subtree to go into,
     *       so RECORD current and move right.  (Same as inorder.)
     *    B) current HAS a left child   -> find the predecessor, then:
     *       B1) predecessor.right is null  -> FIRST time arriving here (left not yet
     *           processed). For PREORDER, RECORD current NOW, then thread
     *           predecessor.right = current and go LEFT.
     *       B2) predecessor.right is current -> the thread exists, left subtree is DONE
     *           and we came back via the thread. Just REMOVE the thread
     *           (predecessor.right = null) and move right. RECORD NOTHING here.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Recording lives in case A and case B1 (NOT B2):
     *    - Why? Both are "first arrival" moments. A no-left node (A) is recorded where you
     *      stand. A has-left node (B1) is recorded just before you descend, which is
     *      root-before-children order. B2 is a RETURN visit (you have already recorded and
     *      traversed this node's left), so it must not record again, or you would emit the
     *      node twice.
     *
     * 2. The predecessor-finding inner loop still has TWO stop conditions:
     *    while (pred.right != null && pred.right != current) pred = pred.right;
     *    - `!= null`    -> keep walking right to the rightmost node.
     *    - `!= current` -> stop at the node already threaded back to current, or you loop
     *      forever along your own thread. Same make-or-break line as inorder.
     *
     * 3. Threads are still removed (in B2) to restore the original tree structure.
     *
     * ---
     *
     * MISTAKES / TRAPS ON THIS PROBLEM (reread before writing Morris preorder):
     * - THE TWO-ADDS TRAP (the main one): there are TWO record points, and converting
     *   from inorder, only ONE moves. Case A's record STAYS. The record that was in B2
     *   (inorder) MOVES to B1 (preorder). If you delete case A's record thinking "preorder
     *   records only in B1," every leaf and every no-left node is silently dropped.
     * - RECORDING IN B2 BY MISTAKE -> each node with a left child gets emitted twice (once
     *   on the way down, once on the return). B2 must record nothing.
     * - FORGETTING `!= current` in the inner loop -> infinite loop along your own thread.
     * - NOT REMOVING THE THREAD in B2 -> tree left mutated/corrupted after traversal. The
     *   `!= current` guard stops the loop; thread REMOVAL is a separate job: it restores
     *   the tree. (Two different safeguards, do not conflate them.)
     *
     * - THE META-NOTE: inorder vs preorder differ ONLY in record placement, not in the
     *   threading logic. Do not re-derive the whole algorithm; identify the single moving
     *   piece (where the value is emitted) and move just that.
     *
     * ---
     *
     * ALGORITHM STEPS (preorder):
     * Step 1: current = root.
     * Step 2: While current != null:
     *   - If current.left == null: RECORD current.val; current = current.right.
     *   - Else: find predecessor = rightmost of current.left (stop if its right is null
     *     OR equals current):
     *       - If predecessor.right == null: RECORD current.val; set predecessor.right =
     *         current; current = current.left.     (record first, thread, go left)
     *       - Else: set predecessor.right = null; current = current.right.
     *                                              (return visit: untie thread, go right,
     *                                               record nothing)
     * Step 3: Return the recorded list.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - Record sites are case A and case B1 (both first-arrival). B2 is a return visit and
     *   records nothing.
     * - In B1 the record happens BEFORE threading/descending, giving root-before-children.
     * - pred.right toggles null -> current -> null; that state IS how the code knows first
     *   visit (B1) versus return visit (B2).
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N). Nodes with a left child are visited about twice (thread
     *    create, then thread remove), but each edge is traversed a constant number of
     *    times, so total work is linear.
     * -> Space Complexity: O(1) AUXILIARY. No recursion stack, no explicit stack, no queue;
     *    just a couple of pointers. The output list is not counted as auxiliary space. This
     *    O(1) is the whole reason to use Morris.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Morris preorder = root/left/right in O(N) time and O(1) auxiliary space via the
     *   same threading as inorder; only the record position changes.
     * - Record on FIRST arrival: case A (no left) and case B1 (before descending left).
     *   Never in B2, which is the return visit.
     * - The single conceptual difference between Morris inorder and preorder is WHERE you
     *   emit the value; the pointer-threading is identical.
     * - The two-condition inner loop (right != null && right != current) is still the
     *   critical line, and the tree is restored to its original shape by the end.
     */
    private static List<Integer> morrisPreOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        while (current != null) {
            if (current.left == null) {
                result.add(current.val);
                current = current.right;
            } else {
                TreeNode temp = current.left;
                while (temp.right != null && temp.right != current) {
                    temp = temp.right;
                }
                if (temp.right == null) {
                    result.add(current.val);
                    temp.right = current;
                    current = current.left;
                } else {
                    temp.right = null;
                    current = current.right;
                }
            }
        }
        return result;
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
