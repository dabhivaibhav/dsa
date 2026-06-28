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
        System.out.println(countNodesOptimized(root));
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

    /*
     * WHAT THIS METHOD DOES:
     * Counts the total number of nodes in a COMPLETE binary tree in better than
     * linear time. The brute force (BFS or recursive 1 + left + right) visits every
     * node and is O(N), but it throws away the one gift the problem hands you: the
     * tree is complete. This solution exploits that to skip counting most of the tree.
     *
     * ---
     *
     * THE "PERFECT-SUBTREE SHORTCUT" PATTERN (COUNT NODES IN A COMPLETE TREE)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE OBJECTIVE: Count nodes WITHOUT visiting all of them. A complete
     *    tree fills left to right with no gaps, every level above the last is full,
     *    and the last level holds between 1 and 2^h nodes. That regularity is the
     *    thing to exploit. If a solution would work on ANY binary tree, it is ignoring
     *    "complete" and is almost certainly O(N).
     *
     * 2. YOUR FIRST ATTACK & THE ROADBLOCK:
     *    -> First instinct was BFS / recursive full traversal, counting every node.
     *    -> WHY IT FALLS SHORT: It is correct but O(N). It never uses "complete," so
     *       it does the maximum possible work. Correct is not the goal here, FAST is.
     *
     * 3. THE SPLIT INSIGHT (THE SEAM THE WHOLE TRICK RUNS ALONG):
     *    -> Break the count into two parts: the FULL upper block (every level above
     *       the last) plus the PARTIAL last level.
     *    -> The upper block is FREE: a perfect block of L levels has exactly 2^L - 1
     *       nodes, pure arithmetic, zero traversal. So the hard part shrinks to "how
     *       many nodes sit in the bottom row," which you must NOT count one by one or
     *       you are back to O(N).
     *
     * 4. THE RULER ROADBLOCK (A REAL TANGLE, WRITE IT DOWN):
     *    -> Got confused whether "height" means EDGES or LEVELS. They differ by one,
     *       and 2^h - 1 is only correct if h matches the ruler you measured with.
     *    -> RESOLUTION: pick ONE ruler and make the formula agree. The spine helpers
     *       below count NODES on the path (start at 0, +1 per node), so a single node
     *       returns height 1, and 2^1 - 1 = 1. Consistent. If you ever count edges
     *       instead, a single node gives 0 and you would need 2^(h+1) - 1. Derive the
     *       exponent from how YOUR helper counts, never copy it blindly.
     *
     * 5. THE BREAKTHROUGH (DETECT PERFECTNESS WITHOUT LOOKING):
     *    -> You cannot "look" for perfect subtrees, you must TEST for them cheaply.
     *    -> The test: measure the LEFT spine and the RIGHT spine from the current node.
     *       EQUAL  -> the whole subtree is PERFECT -> count it by 2^L - 1, instantly,
     *                 no descending at all.
     *       UNEQUAL-> there is a gap in the bottom row -> this subtree is NOT perfect,
     *                 so count this node and RECURSE: 1 + count(left) + count(right).
     *    -> KEY EFFICIENCY FACT: the recursion only keeps descending along the single
     *       "imperfect" boundary path (root toward the gap). EVERYWHERE off that path
     *       you hit a perfect subtree and stop with formula work. So you go deep on
     *       exactly one root-to-leaf path; everything else terminates fast.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Left spine = the true height:
     *    - Why? A complete tree fills from the left, so the leftmost path NEVER has a
     *      gap and is always the longest possible path. Walking it gives the real height.
     *
     * 2. Right spine = the perfectness test:
     *    - Why? Compared to the left spine, the right spine is either EQUAL (every
     *      bottom slot full -> perfect) or exactly ONE shorter (gap in bottom row ->
     *      not perfect). It can never be longer. That invariant is what makes the
     *      shortcut valid.
     *
     * 3. Bit shift for the power of two (1 << h):
     *    - Why? It computes 2^h as a clean integer. Do NOT use Math.pow (returns a
     *      double, drags in casting and precision bugs).
     *
     * 4. Return-and-combine recursion (1 + left + right):
     *    - Why? The count travels UP through return values. No global counter, no
     *      passed-in accumulator. Each call returns its own subtree's count and the
     *      parent sums the pieces. (Your earlier fear "won't a local counter reset?"
     *      is answered here: recursion COMBINES via returns, it does not SHARE a var.)
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE (REREAD THESE BEFORE ANY TREE PROBLEM):
     * - `2 ^ leftHeight` IS NOT A POWER. In Java `^` is bitwise XOR. `2 ^ 3` is 1,
     *   not 8, so `(2 ^ 3) - 1` returns 0 and SILENTLY gives the wrong count, no crash.
     *   The fix is `(1 << leftHeight) - 1`.
     * - SPINE STARTED ONE NODE TOO LOW. Measuring from root.left / root.right instead
     *   of root drops the root's own level, an off-by-one. A single-node tree then
     *   returned 0 instead of 1. Seed the walk from the NODE ITSELF.
     * - THE EDGE-vs-LEVEL RULER TANGLE (see roadblock 4). Always make the formula
     *   match how the helper counts.
     * - THE META-MISTAKE: skimming toward the solution instead of holding the reason
     *   for each step. Reasons are what let you rebuild it cold. No reason held = can't
     *   reconstruct = "I recognized it" masquerading as "I learned it."
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: If root is null, return 0 (empty tree).
     * Step 2: leftHeight  = walk the LEFT spine from root, counting nodes.
     * Step 3: rightHeight = walk the RIGHT spine from root, counting nodes.
     * Step 4: If leftHeight == rightHeight: subtree is perfect ->
     *         return (1 << leftHeight) - 1. STOP, no traversal.
     * Step 5: Else: return 1 + countNodes(root.left) + countNodes(root.right).
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - (1 << leftHeight) - 1, never (2 ^ leftHeight) - 1.  (XOR trap)
     * - spine helpers seeded at root, never root.left.       (off-by-one trap)
     * - only the LEFT spine is treated as "the height," because in a complete tree the
     *   leftmost path is always the longest and gap-free.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * -> Time Complexity: O(log^2 N) -- this is TWO costs multiplied, not one.
     *    FACTOR 1 (recursion depth): you descend only along the single "imperfect"
     *      boundary path from root toward the gap in the bottom row. That path is the
     *      height of the tree, ~log N levels deep. (This is the part I first saw.)
     *    FACTOR 2 (work per level): at EACH node on that descent you call both spine
     *      helpers, and each spine walks from that node down to a leaf, ~log N steps.
     *      So every node on the path costs O(log N) just for the two measurements.
     *      (This is the factor I first dropped, the mistake to remember: count the
     *      work done AT each level, not only how many levels there are.)
     *    MULTIPLY them: O(log N) levels x O(log N) per level = O(log N * log N) = O(log^2 N).
     *    WHY IT BEATS O(N): for N = 1,000,000 nodes, log N ~ 20, so log^2 N ~ 400.
     *      Four hundred units of work instead of a million. That gap is the entire
     *      reason for splitting the tree and spine-checking instead of visiting nodes.
     *
     * -> Space Complexity: O(H) where H is the height of the tree, which is ~O(log N)
     *    for a complete tree. This is the recursion call stack: the deepest the call
     *    chain ever gets is one root-to-leaf path. No extra data structures, no queue,
     *    no visited set. Just the stack frames along the descent.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * The entire value of this problem is using "complete" to AVOID traversing.
     * - Split into a full upper block (count by 2^L - 1) plus a partial last level.
     * - Detect a perfect subtree by LEFT spine == RIGHT spine, count it by formula,
     *   and recurse only when they differ. You descend just one imperfect path.
     * - Two silent killers live here: `^` is XOR not power (use `1 << h`), and the
     *   spine must start at the node itself (off-by-one).
     * - If a tree solution would work on ANY binary tree, suspect you are ignoring a
     *   structural gift and leaving speed on the table.
     */
    private static int countNodesOptimized(TreeNode root) {
        if (root == null) return 0;

        int leftHeight = countLeftHeight(root);    // was root.left
        int rightHeight = countRightHeight(root);   // was root.right

        if (leftHeight == rightHeight)
            return (1 << leftHeight) - 1;           // was (2 ^ leftHeight) - 1
        else
            return 1 + countNodesOptimized(root.left) + countNodesOptimized(root.right);
    }

    private static int countLeftHeight(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.left;
        }
        return height;
    }

    private static int countRightHeight(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.right;
        }
        return height;
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
