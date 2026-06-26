package binary_tree.easy_problems;

import java.util.LinkedList;
import java.util.Queue;

/*
LeetCode 222: Count Complete Tree Nodes

Given the root of a complete binary tree, return the number of the nodes in the tree.
According to Wikipedia, every level, except possibly the last, is completely filled
in a complete binary tree, and all nodes in the last level are as far left as possible.
It can have between 1 and 2h nodes inclusive at the last level h.

Design an algorithm that runs in less than O(n) time complexity.

Example 1:
Input: root = [1,2,3,4,5,6]
Output: 6

Example 2:
Input: root = []
Output: 0

Example 3:
Input: root = [1]
Output: 1

Constraints:
            The number of nodes in the tree is in the range [0, 5 * 10^4].
            0 <= Node.val <= 5 * 10^4
            The tree is guaranteed to be complete.
 */
public class CountCompleteTreeNodes {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        System.out.println(countNodesBruteForce(root));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Counts the total number of nodes in a binary tree. Two solutions are captured
     * here: an iterative BFS that walks every node with an explicit queue, and a
     * recursive version that sums "1 + left subtree + right subtree" on the way back
     * up. Both visit all N nodes, both are O(N). This file documents the brute-force
     * count; the "complete tree" optimization that beats O(N) is a separate note.
     *
     * ---
     *
     * THE "VISIT EVERY NODE AND TALLY" PATTERN (COUNT NODES IN A TREE)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE OBJECTIVE: Given the root of a binary tree, return how many nodes
     *    it contains. Nothing fancy in the brute force: see every node exactly once,
     *    add one to a counter for each, return the counter.
     *
     * 2. YOUR FIRST ATTACK & THE ROADBLOCK (THE OFF-BY-ONE TRAP):
     *    -> Your first counting rule was "when I process a node, add to the count
     *       based on its CHILDREN" (+2 if it has both, +1 if it has one), then add a
     *       single +1 at the very end to patch the gap.
     *    -> WHY IT FAILS: This counts CHILDREN, not NODES. If every node is counted
     *       by its parent, then (a) the root has no parent, so nobody counts it, and
     *       (b) every leaf is never counted for existing, only for any children it
     *       has, which it doesn't. A single trailing +1 only fixes the root. It can't
     *       fix the missing leaves, because that gap GROWS with the number of leaves,
     *       it is not a constant. A constant patch cannot close a variable-sized hole.
     *    -> HOW YOU CAUGHT IT: You traced it on a 1-node tree and a 3-node tree by
     *       hand. The numbers came out wrong, and that proved the +1 was a symptom,
     *       not a fix.
     *
     * 3. YOUR BREAKTHROUGH INSTINCT (COUNT ON THE POLL, NOT ON THE CHILDREN):
     *    -> You realized the fix: stop counting children, count each node the moment
     *       you REMOVE it from the queue. Every node that enters the queue gets polled
     *       exactly once, so counting on the poll counts every node exactly once.
     *    -> This kills BOTH bugs at once: the root gets counted (it was enqueued at
     *       the start), and every leaf gets counted (its parent enqueued it). The two
     *       bugs were the same bug all along: counting children instead of nodes. And
     *       the +1 disappears, because nothing is being missed to correct for.
     *
     * 4. THE SECOND ROADBLOCK (THE NULL CRASH):
     *    -> Even after moving the count to the poll, the code enqueued node.left and
     *       node.right WITHOUT checking they exist. Leaves have null children, so
     *       nulls entered the queue, then a later poll called .left on a null
     *       reference -> NullPointerException.
     *    -> THE FIX: Guard every enqueue. Only add a child if it is not null. Once
     *       only real nodes ever enter the queue, every poll is a real node, so the
     *       count is clean and the crash is gone.
     *
     * 5. THE RECURSION CONFUSION YOU UNTANGLED (LOCAL vs GLOBAL COUNTER):
     *    -> Your fear: "if I keep the counter inside the method, each recursive call
     *       gets its own fresh copy and resets it, so I lose the count. Do I need a
     *       global variable? Do I need two methods?"
     *    -> THE TRUTH: Your fear was CORRECT, a local variable IS per-call and the
     *       calls do not share it. But the resolution is not a global. In recursion
     *       you do not SHARE a counter, you RETURN a value and let the parent COMBINE.
     *       Each call returns its own subtree's count; the parent adds the pieces:
     *       count(node) = 1 + count(left) + count(right). The count travels UP through
     *       the return values automatically. No global, no passed-in accumulator, no
     *       two methods.
     *
     * ---
     *
     * CORE DATA STRUCTURE / DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Queue<TreeNode> (BFS version, The Wavefront):
     *    - Why? A FIFO queue lets you walk the whole tree breadth-first with one
     *      explicit structure and one shared counter (there is only one call, so no
     *      recursion to fragment the counter).
     *    - Reason: poll a node, count it, enqueue its real children, repeat until
     *      empty. Each node is enqueued once and polled once -> exact count.
     *
     * 2. The null-guard on enqueue (if (node.left != null) ...):
     *    - Why? Tree leaves have null children. Adding nulls to the queue both
     *      corrupts the count and crashes on the next poll.
     *    - Reason: guarding the enqueue is the SINGLE fix for both the wrong-count and
     *      the NullPointerException. They share one root cause.
     *
     * 3. Return-and-combine (recursive version, no counter at all):
     *    - Why? The count lives in the returned numbers bubbling up, not in any
     *      variable you manage. This is the natural shape for "count/sum things in a
     *      tree" and it sidesteps the global-vs-local question entirely.
     *    - Reason: this "return your piece, let the parent sum it" pattern is the
     *      backbone of a huge fraction of tree problems. Worth owning cold.
     *
     * ---
     *
     * ALGORITHM STEPS (BFS version):
     * Step 1: If root is null, return 0 (empty tree has 0 nodes, and this guard stops
     *         you from enqueuing null and crashing on the first poll).
     * Step 2: Initialize total = 0 and a queue seeded with the root.
     * Step 3: While the queue is not empty:
     *         - Poll a node and immediately do total++ (count on the poll).
     *         - If node.left is not null, enqueue it.
     *         - If node.right is not null, enqueue it.
     * Step 4: Return total. No +1 correction anywhere.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - total++ happens on the POLL, not based on children.
     *     This is the line that fixed the off-by-one. Counting children misses the
     *     root and all leaves; counting on the poll counts every node once.
     * - if (node.left != null) queue.add(node.left);
     *     The guard that prevents both the wrong count AND the null crash.
     * - No global counter in the recursive version.
     *     count = 1 + count(left) + count(right). The return values carry the tally
     *     up. A local counter does NOT need to be shared, because nothing writes to a
     *     shared place, each call returns its own number.
     * - The for-loop over queue.size() (level batching) is NOT needed here.
     *     That batching matters only when you care WHICH LEVEL a node is on (e.g.
     *     level-order print). For a plain total, "poll one, process one" is enough.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity (both brute-force versions):
     * -> Time Complexity: O(N) - every node is visited exactly once, whether by the
     *    queue (polled once) or the recursion (one call per node). Constant work per
     *    node. Total scales linearly as O(N).
     *
     * -> Space Complexity: O(N) - BFS queue holds up to a full level, which is ~N/2
     *    nodes in the widest (bottom) level of a balanced tree, so O(N). The recursive
     *    version uses the call stack, O(H) where H is height, which is O(log N) for a
     *    balanced tree but O(N) for a skewed one. Note this trade-off: recursion can
     *    be cheaper on space for balanced trees.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * Counting nodes is "easy" only after you avoid two classic traps. The whole value
     * of this problem is the two mistakes:
     * - THE OFF-BY-ONE: never count a node via its parent/children. Count it when YOU
     *   touch it (on the poll, or as the "1 +" in the recursion). A trailing +1 is a
     *   red flag that you are counting the wrong thing.
     * - THE NULL ENQUEUE: always guard enqueues with a null check. An unguarded
     *   queue.add(node.left) is both a wrong-count bug and a NullPointerException
     *   waiting to fire on the next poll.
     * - THE RECURSION FEAR: "won't my counter reset?" Yes, locals are per-call, and
     *   that is fine, because recursion COMBINES via return values, not a shared
     *   variable. Reach for "1 + left + right", not a global.
     * - AND THE BIG ONE FOR THIS SPECIFIC PROBLEM: both of these solutions IGNORE that
     *   the tree is COMPLETE. They would work on any binary tree. The problem says
     *   "complete" on purpose, which means there is a faster-than-O(N) answer they are
     *   actually fishing for. (See the separate "complete tree" optimization note.)
     */
    private static int countNodesBruteForce(TreeNode root) {
        if (root == null) return 0;
        int total = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            total++;
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        return total;
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
