package binary_tree.hard_problems;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Morris Inorder Traversal

Given root of binary tree, return the Inorder traversal of the binary tree.
Morris Inorder Traversal is a tree traversal algorithm aiming to achieve a
space complexity of O(1) without recursion or an external data structure.

Example 1
Input : root = [1, 4, null, 4, 2]
Output : [4, 4, 2, 1]

Example 2
Input : root = [1, null, 2, 3]
Output : [1, 3, 2]

Constraints:
            1 <= Number of Nodes <= 100
            -100 <= Node.val <= 100
 */
public class MorrisInorderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        System.out.println(morrisInorderTraversal(root));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Returns the inorder traversal (left, root, right) of a binary tree using MORRIS
     * TRAVERSAL, which achieves O(1) auxiliary space: no recursion, no stack, no queue.
     * It works by temporarily rewiring the tree's own null right-pointers into "threads"
     * that point back to ancestors, using the tree as its own breadcrumb trail, then
     * removing each thread so the tree is left exactly as it started.
     *
     * ---
     *
     * THE "THREADED BINARY TREE" PATTERN (MORRIS INORDER, O(1) SPACE)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE PROBLEM MORRIS SOLVES: normal inorder needs a way to get BACK UP after
     *    descending left. Recursion uses the call stack; iterative uses an explicit
     *    stack. Both are O(H) space (O(N) for a skewed tree). Morris removes that
     *    external memory entirely and gets O(1) auxiliary space.
     *
     * 2. THE KEY IDEA (how you go back with no stack): the rightmost node of a node's
     *    LEFT subtree is its inorder PREDECESSOR (the node printed just before it). If
     *    you point that predecessor's (otherwise null) right pointer BACK at the current
     *    node, you build yourself a return path. Descend left; when you later walk right
     *    off the bottom, that thread carries you back up to where you came from. The tree
     *    stores its own "where did I come from" in its unused null pointers.
     *
     * 3. THE THREE CASES AT ANY NODE (current):
     *    A) current has NO left child  -> nothing to do on the left, so RECORD current
     *       and move right. (In inorder, a node with no left subtree is next to print.)
     *    B) current HAS a left child   -> the left subtree must come first. Find the
     *       predecessor (rightmost of the left subtree), then:
     *       B1) predecessor.right is null  -> left subtree NOT processed yet. CREATE the
     *           thread (predecessor.right = current) and go LEFT. Record nothing yet.
     *       B2) predecessor.right is current -> the thread already exists, meaning the
     *           left subtree is DONE and we followed the thread back. REMOVE the thread
     *           (predecessor.right = null, restoring the tree), RECORD current, go right.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. It is a LOOP, not recursion:
     *    - Why? The whole point is O(1) space, so a call stack is off the table. A
     *      single while loop walking `current` down/around the tree replaces it.
     *
     * 2. The predecessor-finding inner loop has TWO stop conditions:
     *    while (pred.right != null && pred.right != current) pred = pred.right;
     *    - `pred.right != null`   -> normal "keep walking right to the rightmost node."
     *    - `pred.right != current`-> STOP if you reach the node that already threads back
     *      to current. Without this you follow your OWN thread in an endless circle.
     *      This is the single trickiest line in Morris.
     *
     * 3. Threads are always removed (in branch B2):
     *    - Why? So the tree is returned to its exact original structure. Morris only
     *      BORROWS the null right-pointers; B2 puts every one of them back.
     *
     * ---
     *
     * MISTAKES / TRAPS ON THIS PROBLEM (reread before writing Morris again):
     * - FORGETTING THE `!= current` CONDITION in the inner loop -> infinite loop, because
     *   you walk right along the very thread you created and never stop. Both conditions
     *   are mandatory.
     * - RECORDING IN THE WRONG BRANCH: for INORDER the value is taken in case A (no left)
     *   and case B2 (left finished), NOT in B1. In B1 you are only setting up the thread
     *   and diving left; recording there would print roots before their left subtrees.
     * - NOT REMOVING THE THREAD in B2 -> the tree stays mutated (corrupted) after the
     *   traversal, and re-running would misbehave. Restoring is part of correctness.
     * - REDUNDANT NULL GUARD: the `if (root == null) return ...` up top is unnecessary,
     *   since `while (current != null)` already handles a null root (loop never runs,
     *   empty list returned). Harmless, just not needed.
     *
     * - THE PREORDER TWEAK (for later): preorder is root-first, so the ONLY change is
     *   WHERE the value is recorded. Record when you FIRST arrive at a node: keep the
     *   record in case A, but in case B move it into B1 (at thread-creation time, before
     *   going left) instead of B2. Same skeleton otherwise.
     *
     * ---
     *
     * ALGORITHM STEPS (inorder):
     * Step 1: current = root.
     * Step 2: While current != null:
     *   - If current.left == null: record current.val; current = current.right.
     *   - Else: find predecessor = rightmost node of current.left (stop if its right is
     *     null OR already equals current):
     *       - If predecessor.right == null: set predecessor.right = current;
     *         current = current.left.               (create thread, go left)
     *       - Else: set predecessor.right = null; record current.val;
     *         current = current.right.              (remove thread, record, go right)
     * Step 3: Return the recorded list.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - pred.right != current is the loop-guard that prevents circling your own thread.
     * - Recording lives in case A and case B2 for inorder (a node is emitted only after
     *   its entire left subtree is done).
     * - predecessor.right toggles: null -> current (thread on) -> null (thread off). The
     *   two states ARE how the algorithm knows whether the left subtree is pending or done.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N). Some nodes are visited up to twice (once to create the
     *    thread, once to remove it), but each EDGE is traversed a constant number of
     *    times, so total work is linear.
     * -> Space Complexity: O(1) AUXILIARY. No recursion stack, no explicit stack, no
     *    queue. Only a couple of pointers (current, predecessor). The output list is not
     *    counted as auxiliary space. This O(1) is the entire reason Morris exists.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Morris = inorder in O(N) time and O(1) auxiliary space by threading the tree's
     *   own null right-pointers to ancestors and undoing them afterward.
     * - The predecessor is the rightmost node of the left subtree; its right pointer being
     *   null vs current tells you whether the left subtree is pending (B1) or done (B2).
     * - The two-condition inner loop (right != null && right != current) is the make-or-
     *   break line; the second condition stops you circling your own thread.
     * - The tree is restored to its original shape by the time traversal ends.
     * - Preorder needs only a shift in WHERE you record the value (into B1); the structure
     *   is identical.
     */
    private static List<Integer> morrisInorderTraversal(TreeNode root) {

        if (root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        TreeNode current = root;
        while (current != null) {
            if (current.left == null) {
                res.add(current.val);
                current = current.right;
            } else {
                TreeNode temp = current.left;
                while (temp.right != null && temp.right != current) {
                    temp = temp.right;
                }
                if (temp.right == null) {
                    temp.right = current;
                    current = current.left;
                } else {
                    temp.right = null;
                    res.add(current.val);
                    current = current.right;
                }
            }
        }
        return res;
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
