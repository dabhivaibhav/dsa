package binary_tree.medium_problems;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 114. Flatten Binary Tree to Linked List

Given the root of a binary tree, flatten the tree into a "linked list":
The "linked list" should use the same TreeNode class where the right child pointer points
to the next node in the list and the left child pointer is always null.
The "linked list" should be in the same order as a pre-order traversal of the binary tree.

Example 1:
Input: root = [1,2,5,3,4,null,6]
Output: [1,null,2,null,3,null,4,null,5,null,6]

Example 2:
Input: root = []
Output: []

Example 3:
Input: root = [0]
Output: [0]


Constraints:
            The number of nodes in the tree is in the range [0, 2000].
            -100 <= Node.val <= 100

Follow up: Can you flatten the tree in-place (with O(1) extra space)?
 */
public class FlattenBinaryTreeToLinkedList {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        flattenOptimal(root);
        while (root.right != null) {
            System.out.print(root.val + " ");
            root = root.right;
        }
    }


    /*
     * WHAT THIS METHOD DOES:
     * Flattens a binary tree into a preorder right-leaning linked list. BRUTE-FORCE
     * version: first collect all nodes in preorder into a list, THEN walk the list and
     * rewire each node (left = null, right = next node). Simple and obviously correct,
     * at the cost of O(N) extra space for the list. Contrast with the O(1) Morris-style
     * splice version.
     *
     * ---
     *
     * THE "COLLECT THEN RELINK" PATTERN (FLATTEN TREE, BRUTE FORCE)
     *
     * Your Thought Process & Intuition:
     * 1. THE OUTPUT IS PREORDER: the flattened chain is just the nodes in preorder order,
     *    linked by right pointers. So the dumbest correct idea is: get the preorder list,
     *    then string the nodes together in that order.
     *
     * 2. TWO STRICT PHASES (this is the key correctness point):
     *    - PHASE 1 (read-only): traverse the WHOLE tree in preorder, appending each node
     *      to a list. Do NOT modify pointers yet.
     *    - PHASE 2 (mutate): walk the list; for each node set left = null and right = the
     *      next node; the last node's right = null.
     *    The phases must not be mixed. Rewiring during traversal would clobber the left/
     *    right pointers you still need to follow, corrupting the rest of the walk.
     *
     * 3. WHY THIS IS "BRUTE FORCE": correct and easy, but spends O(N) space on the list
     *    (plus O(H) recursion stack). The whole point of optimizing this problem is to
     *    remove that extra space, since time is already O(N) and cannot be beaten.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Store NODES in the list, not values:
     *    - Why? You are rewiring the actual tree nodes' pointers, so you need references
     *      to the nodes themselves, not copies of their values.
     *
     * 2. Traverse fully BEFORE rewiring:
     *    - Why? Mutating pointers mid-traversal destroys the structure you are still
     *      reading. Read-all-first, then mutate-all, keeps the two from interfering.
     *
     * ---
     *
     * TRAPS TO AVOID IN THIS APPROACH:
     * - MIXING THE TWO PHASES: rewiring a node's right/left while you are still traversing
     *   loses the branches you have not visited yet. Keep collection and rewiring separate.
     * - FORGETTING THE LAST NODE: the final node in the list must get right = null (and
     *   left = null), or you leave a dangling pointer into old structure.
     * - EMPTY TREE: guard root == null up front so the "last node" indexing does not blow up.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: If root is null, return.
     * Step 2: Preorder-traverse, appending every node to a list (node, then left, then right).
     * Step 3: For i from 0 to size-2: nodes[i].left = null; nodes[i].right = nodes[i+1].
     * Step 4: Set the last node's left = null and right = null.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - The list holds nodes (references), because we rewire their real pointers.
     * - Collection is fully finished before any rewiring, so the traversal is never
     *   corrupted by the mutation.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N). One preorder pass to collect, one pass to relink.
     * -> Space Complexity: O(N). The list holds all N node references, plus O(H) for the
     *    recursion stack. This extra O(N) is exactly what the optimal O(1) version removes.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - This is the "I have a correct solution" answer to state first: collect preorder,
     *   then relink.
     * - Its cost is O(N) SPACE; time is already optimal at O(N). The follow-up "do it
     *   without extra space" leads to the recursive O(H) version, then the O(1) splice.
     * - The one real correctness trap is doing it in two phases so mutation never corrupts
     *   traversal.
     */
    private static void flattenBrutForce(TreeNode root) {
        if (root == null) return;
        List<TreeNode> nodes = new ArrayList<>();
        preorder(root, nodes);              // fill list in preorder
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).left = null;
            nodes.get(i).right = nodes.get(i + 1);
        }
        nodes.getLast().left = null;
        nodes.getLast().right = null;
    }

    private static void preorder(TreeNode node, List<TreeNode> nodes) {
        if (node == null) return;
        nodes.add(node);
        preorder(node.left, nodes);
        preorder(node.right, nodes);
    }

    /*
     * WHAT THIS METHOD DOES:
     * Flattens a binary tree in place into a "linked list" that follows PREORDER order,
     * using right pointers as the "next" link and setting every left pointer to null.
     * Uses a Morris-style O(1) space approach: for each node with a left child, splice
     * the entire left subtree in between the node and its right subtree. No recursion,
     * no stack. Unlike Morris TRAVERSAL, the rewiring here is PERMANENT (it IS the
     * answer), not a temporary thread that gets removed.
     *
     * ---
     *
     * THE "SPLICE THE LEFT SUBTREE IN" PATTERN (FLATTEN TREE TO LINKED LIST, O(1) SPACE)
     *
     * Your Thought Process & Intuition:
     * 1. THE OUTPUT ORDER IS PREORDER: flattening 1 with children 2 (left) and 3 (right)
     *    gives 1 -> 2 -> 3, which is preorder (root, left, right). The whole tree collapses
     *    into a right-leaning chain in preorder order.
     *
     * 2. THE CORE OPERATION (at any node WITH a left child): in the final list, the left
     *    subtree must sit BETWEEN the node and its right subtree. So:
     *    - Find the RIGHTMOST node of the left subtree (walk right from cur.left as far as
     *      possible). This is where the old right subtree must reattach.
     *    - Hang the node's current right subtree off that rightmost node's right.
     *    - Move the left subtree over to be the node's new right.
     *    - Null out the node's left.
     *    Then advance and repeat down the newly-built right chain.
     *
     * 3. HOW THIS RELATES TO MORRIS (and how it DIFFERS): the "find the rightmost node of
     *    the left subtree" step is the SAME predecessor-finding trick as Morris traversal.
     *    BUT here the rewire is permanent (building the real answer), so there is NO thread
     *    removal, NO B1/B2 first-visit-vs-return distinction, and NO collecting values into
     *    a list. It is actually SIMPLER than Morris traversal: one forward pass, rewire,
     *    move on. You never revisit a node.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Inner loop has ONE condition (pred.right != null), not two:
     *    - Why? Morris traversal needed `&& pred.right != current` to avoid circling its
     *      own temporary thread. Here no temporary threads/cycles are ever created (every
     *      rewire moves nodes into a clean forward chain), so a plain "walk right to the
     *      end" is enough.
     *
     * 2. The advance (cur = cur.right) must run EVERY iteration, inside AND outside the
     *    left-child case:
     *    - Why? Nodes with a left child advance after rewiring; nodes WITHOUT a left child
     *      must still move forward. Putting the advance only inside the `if` strands the
     *      pointer on any no-left node forever.
     *
     * 3. Save/order the pointers carefully: reattach the OLD right subtree to the left
     *    subtree's tail BEFORE overwriting cur.right, or the right subtree is lost.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE, AND HOW TO CATCH THEM NEXT TIME:
     * - USED root.left INSTEAD OF cur.left: checked the ORIGINAL root's left child every
     *   iteration instead of the CURRENT node's. `root` never moves, so the condition was
     *   about the wrong node after the first step.
     *   HOW TO CATCH: in any loop that walks a moving pointer (`cur`), every reference
     *   inside must use that moving pointer, not the fixed starting variable. Scan the loop
     *   body for the original name (root) appearing where the moving name (cur) belongs.
     *   This is the same stale-reference slip from earlier problems.
     *
     * - FORGOT cur.left = null: moved the left subtree to the right but never cleared the
     *   left pointer, so the same subtree was reachable via BOTH left and right, violating
     *   the "all left pointers null" requirement.
     *   HOW TO CATCH: re-read the PROBLEM's exact output spec and check each requirement is
     *   produced by a specific line. The spec said "left null AND right = next." I had the
     *   right-rewire line but no left-null line. One requirement, no code -> missing step.
     *
     * - INFINITE LOOP: no `else`/advance for nodes with NO left child. `cur` only moved
     *   inside the `if`, so a leaf or no-left node looped forever on itself.
     *   HOW TO CATCH: trace the loop on the SIMPLEST failing input (a single leaf). If the
     *   pointer does not change on some path through the loop body, that path is an infinite
     *   loop. Always ask: "on every branch, does the loop variable make progress?"
     *
     * - THE META-LESSON (true all weekend): every bug here was a PRECISION slip, not a
     *   conceptual one. The algorithm was right on the first try; a wrong variable, a
     *   missing step, and a control-flow gap were the failures. Fix: after writing, TRACE
     *   on a small concrete tree and verify (a) every problem requirement has a line, and
     *   (b) the loop variable advances on every path.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: cur = root.
     * Step 2: While cur != null:
     *   - If cur.left != null:
     *       - pred = cur.left; walk pred right until pred.right == null (rightmost of left).
     *       - pred.right = cur.right   (attach old right subtree to tail of left subtree)
     *       - cur.right = cur.left     (move left subtree to be the right)
     *       - cur.left  = null         (clear the left pointer)
     *   - cur = cur.right              (ALWAYS advance, both cases)
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - cur.left (not root.left) is the condition: the check must track the moving node.
     * - cur.left = null is mandatory: the spec requires all left pointers null.
     * - cur = cur.right lives OUTSIDE the `if` so every node advances (no infinite loop).
     * - pred walk uses ONE condition; no `!= cur` guard because no cycles are formed here.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N). Each node is visited by `cur` once, and the inner rightmost
     *    walks together traverse each edge a constant number of times, so linear overall.
     * -> Space Complexity: O(1). In-place pointer rewiring only, a couple of references
     *    (cur, pred). No recursion stack, no explicit stack, no output list. This O(1) is
     *    the point of the Morris-style approach over the easy recursive one.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Flattening = splice each node's left subtree between the node and its right subtree;
     *   the reattachment point is the rightmost node of the left subtree (the predecessor).
     * - Reuses Morris's predecessor-finding, but rewires PERMANENTLY: no thread removal, no
     *   first-visit/return branching, no value collection. Simpler than Morris traversal.
     * - Three easy-to-miss lines: use cur (not root), null the left pointer, and advance on
     *   EVERY iteration.
     * - O(N) time, O(1) space, versus the trivial recursive O(H) space version.
     */
    private static void flattenOptimal(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode pred = cur.left;
                while (pred.right != null) {
                    pred = pred.right;
                }
                pred.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int x) {
            val = x;
        }
    }
}
