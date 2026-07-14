package binary_tree.hard_problems;

import java.util.ArrayDeque;
import java.util.Queue;

/*
297. Serialize and Deserialize Binary Tree

Serialization is the process of converting a data structure or object into
a sequence of bits so that it can be stored in a file or memory buffer, or
transmitted across a network connection link to be reconstructed later in
the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no
restriction on how your serialization/deserialization algorithm should work.
You just need to ensure that a binary tree can be serialized to a string and
this string can be deserialized to the original tree structure.

Clarification: The input/output format is the same as how LeetCode serializes
a binary tree. You do not necessarily need to follow this format, so please be
creative and come up with different approaches yourself.

Example 1:
Input: root = [1,2,3,null,null,4,5]
Output: [1,2,3,null,null,4,5]

Example 2:
Input: root = []
Output: []


Constraints:
            The number of nodes in the tree is in the range [0, 10^4].
            -1000 <= Node.val <= 1000
 */
public class SerializeDeserializeBT {

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        System.out.println(serialize(root));
        deserialize(serialize(root));

    }

    /*
     * WHAT THIS METHOD DOES:
     * Serializes a binary tree to a string and deserializes it back to the identical
     * tree. Serialize does a PREORDER walk that explicitly writes a marker ("#") for
     * every null child, so the string fully captures the tree's SHAPE, not just its
     * values. Deserialize splits that string into tokens and rebuilds the tree by
     * consuming them front-to-back with a shared queue, recursing left then right.
     *
     * ---
     *
     * THE "PREORDER WITH NULL MARKERS" PATTERN (SERIALIZE / DESERIALIZE A TREE)
     *
     * Your Thought Process & Intuition:
     * 1. THIS IS 105/106 IN DISGUISE: rebuilding a tree from a sequence is exactly
     *    what "construct from traversals" did. Deserialize is that same skill. The new
     *    half is serialize (tree -> sequence), and the freedom to choose the format.
     *
     * 2. WHY WRITE THE NULLS (the whole trick): a PLAIN preorder is ambiguous (105
     *    proved preorder alone can't rebuild a tree) because it drops the null
     *    children, losing where each subtree ends. Fix: DON'T skip nulls, record them
     *    with a marker. Once every null is written, the string is unambiguous: it
     *    literally says "this node's left is #, its right is 5." The markers carry the
     *    structure that a bare traversal throws away.
     *
     * 3. DESERIALIZE IS SIMPLER THAN 105/106, NOT HARDER: those had TWO arrays and had
     *    to cross-reference (search for a root, compute subtree sizes, track start/end
     *    boundaries). Here there is ONE self-describing array. No searching, no size
     *    math, no boundaries. A single forward pointer (a queue) is enough, because the
     *    tokens are in preorder order (root, then whole left subtree, then whole right)
     *    and the "#" markers tell each branch exactly where to stop.
     *
     * 4. WHY RECURSION, NOT A LOOP (the confusion I had to clear): a while-loop over
     *    the queue cannot express "build the ENTIRE left subtree, then come back and
     *    build the right." Only recursion does that hand-off. The queue is the DATA
     *    SOURCE; the recursion is the CONTROL FLOW. Each recursive call polls exactly
     *    the tokens its own subtree needs and returns, leaving the queue's front sitting
     *    precisely at the start of the next subtree. (Same property as 106's pointer.)
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. "#" null marker AND a "," separator (need BOTH):
     *    - Marker: without it, shape is lost and the tree is ambiguous (see intuition 2).
     *    - Separator: without it, multi-digit and negative values collide. "15" would
     *      be unreadable, and "-1000" (allowed by constraints) needs its own token.
     *      So the unit is a TOKEN (a comma-separated piece), never a single character.
     *
     * 2. Queue<String> consumed by poll() as the shared pointer:
     *    - Why? Polling one token per call, shared across all recursive calls, tracks
     *      "where am I" automatically. It is 106's shared index, but the queue's own
     *      state IS the position, so no index variable to manage. Forward this time,
     *      with "#" as the terminator instead of index bounds.
     *
     * 3. Preorder specifically (root first):
     *    - Why? Reading front-to-back, the first token is always the current subtree's
     *      root, which is exactly what the recursion needs to create before recursing
     *      into its children. Root-first order matches build-node-then-children.
     *
     * 4. Shared StringBuilder sb, reset with setLength(0) each serialize:
     *    - Why? All recursive appends land in one buffer (no wasteful string passing/
     *      returning). The reset makes repeated calls safe (no leftover from a prior tree).
     *
     * ---
     *
     * MISTAKES / TRAPS ON THIS PROBLEM (reread before the next serialize):
     * - MISSING return AFTER THE NULL MARKER: first serialize appended "#," then fell
     *   through to root.val on a null root -> NullPointerException. The null case must
     *   append its marker AND return (it's the base case).
     * - GUARDING THE RECURSION with if(child != null): that RE-SKIPS the nulls I was
     *   trying to record. Always recurse; let the base case mark the null. The guards
     *   fought the very purpose of writing markers.
     * - void HELPER vs String SIGNATURE: the recursive helper should be void (just
     *   append); only the PUBLIC method converts once and returns sb.toString(). A
     *   String signature with a bare `return;` inside will not compile.
     * - QUEUING CHARACTERS instead of tokens: char-by-char splits "15" into '1','5'
     *   and mishandles negatives. Split on commas into STRING tokens and parseInt.
     * - TRYING TO USE A WHILE LOOP for deserialize: the load-bearing realization. This
     *   is pure recursion; the loop instinct was the thing blocking me (see intuition 4).
     *
     * - THE META-MISTAKE (carry across all these): assuming a NEW problem needs NEW,
     *   heavier machinery. Deserialize needed LESS than 105/106, not more. First ask
     *   "what have I already solved that this resembles," then reach for the simplest
     *   correct mechanic, not the most elaborate one.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * SERIALIZE:
     *   1. Reset the shared StringBuilder.
     *   2. Preorder walk: if node is null, append "#," and return; else append value +
     *      "," then recurse left, then recurse right.
     *   3. Return the buffer as a string.
     * DESERIALIZE:
     *   1. Split the string on "," into tokens; load them into a queue.
     *   2. Recursive helper: poll one token. If "#", return null. Else make a node from
     *      it, set node.left = helper(queue), node.right = helper(queue), return node.
     *   3. Return the root from the single top-level helper call.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - Every recursive deserialize call polls EXACTLY ONE token at its start; the
     *   two child calls consume their entire subtrees, so the queue front always lands
     *   on the next subtree's root. That auto-alignment is why no index math is needed.
     * - "#" is both the serialize marker for a null child and the deserialize base case.
     * - Tokens, not characters, so multi-digit and negative values survive.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N) for each direction. Serialize visits every node and
     *    every null child once (there are N nodes and N+1 null slots, still O(N)),
     *    appending constant work each. Deserialize polls each token once and does O(1)
     *    work per token (N values + N+1 markers = O(N) tokens).
     * -> Space Complexity: O(N). The serialized string is O(N). Deserialize holds all
     *    O(N) tokens in the queue. Recursion stack adds O(H) height (O(log N) balanced,
     *    O(N) skewed), dominated by the O(N) string/queue.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Serialize = a traversal that WRITES THE NULLS; that marker is what makes the
     *   string unambiguous (a bare traversal loses shape, per 105).
     * - Deserialize = consume the tokens with a single forward pointer (queue) and
     *   recurse; the "#" markers replace all the boundary/size math of 105/106.
     * - This problem is EASIER than construct-from-two-traversals: one self-describing
     *   array beats cross-referencing two.
     * - It is recursion, not iteration: the queue is data, recursion is control.
     * - Use tokens + a separator so multi-digit and negative values are preserved.
     */
    private static void serializeHelper(TreeNode root) {
        if (root == null) {
            sb.append("#,");
            return;
        }
        sb.append(root.val).append(",");
        serializeHelper(root.left);
        serializeHelper(root.right);
    }

    private static String serialize(TreeNode root) {
        sb.setLength(0);
        serializeHelper(root);
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }

    // Decodes your encoded data to tree.
    private static TreeNode deserialize(String data) {
        String[] strs = data.split(",");
        Queue<String> queue = new ArrayDeque<>();
        for (String c : strs) {
            queue.offer(c);
        }
        return deserializeHelper(queue);
    }

    private static TreeNode deserializeHelper(Queue<String> queue) {
        if (queue.isEmpty()) {
            return null;
        }
        String currentVal = queue.poll();
        TreeNode root;
        if ("#".equals(currentVal)) {
            return null;
        } else {
            root = new TreeNode(Integer.parseInt(currentVal));
        }

        root.left = deserializeHelper(queue);
        root.right = deserializeHelper(queue);
        return root;

    }


    static class TreeNode {
        int val;
        TreeNode right;
        TreeNode left;

        public TreeNode(int x) {
            val = x;
        }
    }
}
