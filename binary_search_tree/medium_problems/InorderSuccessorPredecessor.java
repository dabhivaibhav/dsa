package binary_search_tree.medium_problems;

import java.util.Arrays;
import java.util.List;

/*
Problem: Inorder successor and predecessor in BST

Given the root node of a binary search tree (BST) and an integer key.
Return the Inorder predecessor and successor of the given key from the provided BST.
Note: key will always present in given BST.
If predecessor or successor is missing then return -1.

Example 1
Input : root = [5, 2, 10, 1, 4, 7, 12] , key = 10
Output : [7, 12]

Example 2
Input : root = [5, 2, 10, 1, 4, 7, 12] , key = 12
Output : [10, -1]

Constraints:
            1 <= Number of Nodes <= 10^4
            1 <= Node.val <= 10^5
            All the values Node.val are unique.
 */
public class InorderSuccessorPredecessor {

    /*
     * WHAT THIS METHOD DOES:
     * Finds the inorder PREDECESSOR and SUCCESSOR of a key in a BST using two mirrored
     * candidate walks, one for each answer. Each walk is a single root-to-null descent:
     * O(H) time, O(1) space, run twice. Returns [predecessor, successor], with -1 for a
     * missing one.
     *
     * ---
     *
     * THE DEFINITIONS (plain words, worth memorizing exactly):
     * - PREDECESSOR of key = the LARGEST value in the tree that is STRICTLY LESS than key.
     *   The value that would come just BEFORE the key in sorted order.
     * - SUCCESSOR of key   = the SMALLEST value in the tree that is STRICTLY GREATER than
     *   key. The value that would come just AFTER the key in sorted order.
     * - "Inorder" in the name is just this: inorder of a BST is sorted order, so
     *   predecessor/successor means the previous/next element of that sorted sequence.
     * - Connection to my library: predecessor = STRICT floor, successor = STRICT ceil.
     *   The only delta from my floor/ceil problem is the strictness, because the key is
     *   guaranteed present here, non-strict floor/ceil would both just return the key.
     *
     * THE SENTENCE: two mirrored floor/ceil walks with strict comparisons; ask, record,
     * then step.
     *
     * ---
     *
     * THE "TWO MIRRORED CANDIDATE WALKS" PATTERN
     *
     * Your Thought Process & Intuition:
     * 1. THE ONE RULE PER WALK (two lines each, nothing else exists):
     *    SUCCESSOR walk:   is this node GREATER than key? Yes -> record it as best-so-far,
     *                      step LEFT (something smaller but still greater may exist there).
     *                      No -> record nothing, step RIGHT.
     *    PREDECESSOR walk: is this node LESS than key? Yes -> record, step RIGHT.
     *                      No -> record nothing, step LEFT.
     *    Recording is safe because the candidate is already written down before stepping;
     *    if the deeper side has nothing better, the last recorded value stands.
     *
     * 2. ASK -> RECORD -> STEP, in that order. The node I am STANDING ON is the candidate.
     *    Reversing to step-then-ask forces inner re-checks and peeks at children, and none
     *    of it can work, because the node that passed the test is the one just abandoned.
     *
     * 3. EQUALITY NEEDS NO BRANCH (the elegance, same family as LCA): when cur.val == key,
     *    both strict checks fail, so each walk falls into its else, and each else steps
     *    exactly the right way: successor walk goes RIGHT (a present key's successor lives
     *    in its right subtree, the leftmost node there, my delete successor), predecessor
     *    walk goes LEFT. The strictness does the routing for free.
     *
     * 4. WHY TWO WALKS, NOT ONE: at the key node, the predecessor's refinement lives in
     *    the LEFT subtree and the successor's in the RIGHT. One pointer cannot go both
     *    ways. Two three-line loops beat one clever loop with harvesting.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     * 1. Record BEFORE stepping: kills the inner ifs, the child-peek, and every possible
     *    NPE at once. Nothing ever reads a value after moving.
     * 2. No equality branch: see intuition 3. Adding one is not just unnecessary, its
     *    absence is the correctness argument.
     * 3. -1 initialization doubles as "does not exist", the floor/ceil move.
     * 4. Return order [predecessor, successor]: matches the printed examples (key 10 ->
     *    [7, 12]; 7 is the predecessor). Easy to flip by accident.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE (this problem earned a long list):
     * - INVENTED POSITION RULES INSTEAD OF A CANDIDATE WALK: "the node I meet going right
     *   is the successor, its left child is the predecessor." Killed by one edit: put 67
     *   where 60 was, and the true successor 67 hides LEFT of 70, where the rule never
     *   looks. A candidate is best-so-far, not the answer, my own floor/ceil lesson.
     * - "RIGHTMOST OF THE LEFT SUBTREE" FROM THE ROOT: that rule belongs at the KEY's
     *   node (delete's mirror move), not at the root. From the root it names the wrong
     *   node entirely; my correct answer had come from the drawing, not from my rule.
     * - STEP-THEN-ASK, TWICE: moved the pointer, then inspected the NEW node, then peeked
     *   at ITS child for the predecessor. Floor/ceil bug one (moved before recording)
     *   rebuilt at full scale. Symptom set: walks away from the key, NPE on a null step,
     *   right answers recorded by coincidence then crash before returning.
     * - THE GUARANTEED INFINITE LOOP: with only < and > branches and no routing through
     *   an else, reaching the key node froze the pointer, and since the problem GUARANTEES
     *   the key is present, every valid input hit it. A missing case whose trigger is
     *   promised by the constraints is not an edge case, it is the main road.
     * - ASKED "IS THIS CORRECT?" THREE TIMES INSTEAD OF RUNNING A FOUR-LINE TRACE: the
     *   verification skim. The problem's own two examples break every one of those
     *   versions in under a minute. Trace the printed examples FIRST, they are the answer
     *   key lying face up.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: successor walk from root: cur.val > key -> successor = cur.val, go left;
     *         else go right. Until null.
     * Step 2: predecessor walk from root: cur.val < key -> predecessor = cur.val, go
     *         right; else go left. Until null.
     * Step 3: return [predecessor, successor], -1 where never recorded.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(H) per walk, two walks, still O(H). Each step discards a whole subtree,
     *    one root-to-null path each. (O(log N) balanced, O(N) skewed, never a flat
     *    "O(log N)".)
     * -> Space: O(1). One pointer and two ints. No recursion, no stack, no list.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say the definitions first, then the mapping: predecessor/successor = strict
     *   floor/ceil, neighbors in the BST's sorted (inorder) sequence.
     * - The rule is ask-record-step; the equality case is handled by having no equality
     *   code, and being able to say WHY is the differentiator.
     * - Eleventh appearance of the one BST move: compare, discard a side, descend, here
     *   run twice with mirrored rules.
     * - Alternative worth naming: at the key node, predecessor = rightmost of left
     *   subtree, successor = leftmost of right subtree (my delete machinery), used by the
     *   one-pass harvest variant.
     */
    private static List<Integer> succPredBST(TreeNode root, int key) {
        int predecessor = -1;
        int successor = -1;

        // SUCCESSOR walk: greater -> record, step left. Otherwise step right.
        TreeNode cur = root;
        while (cur != null) {
            if (cur.val > key) {
                successor = cur.val;   // record BEFORE stepping — the node I'm ON is the candidate
                cur = cur.left;
            } else {
                cur = cur.right;       // less than OR EQUAL: equality falls here and routes right,
            }                          // which is exactly where a present key's successor lives
        }

        // PREDECESSOR walk: less -> record, step right. Otherwise step left.
        cur = root;
        while (cur != null) {
            if (cur.val < key) {
                predecessor = cur.val;
                cur = cur.right;
            } else {
                cur = cur.left;        // equality routes left, toward the predecessor
            }
        }

        return Arrays.asList(predecessor, successor);
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
