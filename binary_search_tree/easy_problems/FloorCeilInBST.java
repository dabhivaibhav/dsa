package binary_search_tree.easy_problems;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Problem: Floor and Ceil in a BST

Given a root of binary search tree and a key(node) value, find the floor and ceil value for that particular key value.
Floor Value Node: Node with the greatest data lesser than or equal to the key value.
Ceil Value Node: Node with the smallest data larger than or equal to the key value.
If a particular floor or ceil value is not present then output -1.

Example 1
Input : root = [8, 4, 12, 2, 6, 10, 14] , key = 11
Output : [10, 12]

Example 2
Input : root = [8, 4, 12, 2, 6, 10, 14] , key = 15
Output : [14, -1]

Constraints:
            1 <= Number of Nodes <= 5000
            1 <= Node.val <= 10^7
            1 <= key <= 10^7
 */
public class FloorCeilInBST {

    public static void main(String[] args) {

        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(4);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(6);
        root.right = new TreeNode(12);
        root.right.left = new TreeNode(10);
        root.right.right = new TreeNode(14);
        System.out.println(floorCeilOfBST(root, 11));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Finds the FLOOR (greatest value <= key) and CEIL (smallest value >= key) in a BST,
     * returning -1 for either if it does not exist. Walks a SINGLE root-to-leaf path,
     * recording candidates as it descends. Iterative, so O(H) time and O(1) space.
     *
     * ---
     *
     * THE "RECORD CANDIDATE, THEN NARROW" PATTERN (FLOOR AND CEIL IN A BST)
     *
     * Your Thought Process & Intuition:
     * 1. GET THE DEFINITIONS EXACT FIRST: floor is the greatest value <= THE KEY. Ceil is
     *    the smallest value >= THE KEY. Both are defined by their relationship to the KEY,
     *    not to the current node. (Getting this fuzzy is what caused the worst bug below.)
     *
     * 2. THE CORE MOVE AT ANY NODE:
     *    - cur.val < val  -> cur qualifies as a FLOOR candidate (it is below the key).
     *      Record it, then go RIGHT hoping to find something larger that is still <= key.
     *    - cur.val > val  -> cur qualifies as a CEIL candidate (it is above the key).
     *      Record it, then go LEFT hoping to find something smaller that is still >= key.
     *    - cur.val == val -> the key itself is both floor and ceil. Return [val, val].
     *
     * 3. WHY NO Math.max / Math.min IS NEEDED (the nicest part of this problem):
     *    Walking DOWN a BST, every new floor candidate you meet is further RIGHT than the
     *    last one, therefore LARGER, therefore automatically better. Same going left for
     *    ceil: each new candidate is smaller, therefore better. The BST structure guarantees
     *    monotonic improvement, so plain assignment (floor = cur.val) is correct. There is
     *    nothing to compare.
     *
     * 4. RECORD BEFORE MOVING: the node you are STANDING ON is the candidate. Move first
     *    and you have thrown away the node you needed to record.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Plain assignment, not Math.max/Math.min:
     *    - Why? See intuition 3. Each successive candidate is strictly better by
     *      construction. A max/min here is not just unnecessary, it signals the rule was
     *      never pinned down (see the diagnosis section).
     *
     * 2. Initialize floor = -1 and ceil = -1:
     *    - Why? That is the problem's "does not exist" output. If no candidate is ever
     *      recorded, -1 survives and is already the right answer. No special-casing needed.
     *
     * 3. Iterative while loop:
     *    - Why? Only one subtree is ever explored per step, so there is nothing to backtrack
     *      to. Recursion would cost O(H) stack for no benefit.
     *
     * 4. An explicit equality case is mandatory:
     *    - Why? With only `if (<)` and `else if (>)`, an exact match matches NEITHER branch,
     *      cur never advances, and the loop spins forever.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - MOVED BEFORE RECORDING: did `cur = cur.right` and then inspected cur. The candidate
     *   was the node just left behind. Recording must happen at the node being compared.
     *
     * - Math.max/Math.min INVERTED AND UNTIED TO THE COMPARISON: used max for ceil (ceil is
     *   the SMALLEST qualifying value, so max is backwards) and the updates were not
     *   connected to the < / > branch at all, so they fired regardless of what the
     *   comparison had just proven.
     *
     * - USED cur.left.val AS A FLOOR CANDIDATE: reasoned "the left child is always less than
     *   its parent, so it is a floor candidate." The FACT is true; it just answers a
     *   different question. Floor is about the relationship to the KEY, not to the current
     *   node. This was a true fact substituted for the needed one, because the needed rule
     *   was not yet pinned down.
     *
     * - NO EQUALITY CASE -> infinite loop on an exact match.
     *
     * - TRACED ONLY EXAMPLE 1: it passed BY ACCIDENT (returned [10,12] via wrong logic).
     *   Example 2 (key=15 -> expected [14,-1]) was printed in the problem statement and my
     *   code returned [10,12] on it. Thirty seconds of tracing a FREE example would have
     *   exposed everything.
     *
     * ---
     *
     * THE DIAGNOSIS: WHY I WROTE IT THAT WAY (the real lesson of this problem)
     * The intuition was NOT the failure. Going in, I already knew: candidates appear at the
     * comparison, I doubted whether the FIRST hit is the best, I knew equality needed
     * handling, and I had noticed my traversal was checking before moving. Four correct
     * observations.
     *
     * What happened: AN UNRESOLVED QUESTION AND A PIECE OF CODE OCCUPIED THE SAME SPOT, AND
     * THE CODE FILLED THE GAP INSTEAD OF THE QUESTION GETTING ANSWERED.
     *   - The real question was "is the first candidate I hit the best one?" That is the
     *     CRUX of the problem and it has a clean answer (keep looking; each later candidate
     *     is automatically better).
     *   - Instead of answering it, I reached for Math.max/Math.min. Those functions ARE a
     *     hedge: they are what you write when you do not know which candidate wins, so you
     *     let a function decide.
     *   - Same with cur.left.val: no clear rule for what qualifies, so I grabbed a
     *     plausible-looking fact and moved on.
     *
     * WHY THIS IS SO HARD TO CATCH: code that hedges LOOKS like working code. Math.max(...)
     * compiles, looks purposeful, has the SHAPE of an answer. The moment it is typed, the
     * hole becomes invisible, because now something is there.
     *
     * This is a DIFFERENT failure from my usual meta-mistake. The old one was "state the
     * first true condition and stop before the second." This one is sneakier: I never
     * consciously concluded anything at all. The code concluded for me.
     *
     * ---
     *
     * HOW TO CATCH IT NEXT TIME (procedures, not intuition):
     * 1. TRACE EVERY EXAMPLE THE PROBLEM GIVES, NOT JUST THE FIRST. The author prints the
     *    tricky one on purpose; Example 2 exists precisely because it breaks naive
     *    solutions. Running only Example 1 is leaving the answer key face-up and unread.
     *
     * 2. WHEN A GENUINE QUESTION ARISES MID-WRITING, STOP WRITING. Not "note it and
     *    continue." Resolve it on paper first. Once "does the first hit win?" is closed,
     *    `floor = cur.val` is OBVIOUSLY right and Math.max is OBVIOUSLY unnecessary. The
     *    code writes itself after the question closes, never before.
     *
     * 3. BREAK YOUR OWN CLAIM WITH A CONCRETE CASE. State it ("the first hit wins"), then
     *    try to build an input where it fails. Key=11: first candidate is 8; is there
     *    anything bigger still <= 11? Yes, 10. Claim dead in ten seconds. No insight
     *    required, just an instance.
     *
     * 4. TREAT max/min (AND SIMILAR HEDGES) AS A SMELL. Ask: do I genuinely not know which
     *    wins, or am I avoiding figuring it out? Sometimes max is right. Often it is a
     *    hedge for an unresolved rule.
     *
     * 5. BRANCH COVERAGE: for EACH branch, what input forces it to run? No input hits the
     *    equality case -> the missing branch (and the infinite loop) surfaces before running
     *    anything. Any branch with no possible input is dead code or a misunderstanding.
     *
     * 6. "I NOTICED X WAS OFF AND WROTE IT ANYWAY" IS A HARD STOP. Noticing and continuing
     *    is how a KNOWN problem becomes an UNKNOWN one; five minutes later it is just code
     *    on the screen and the noticing is gone.
     *
     * DISTINCTION WORTH KEEPING: "does the first hit win?" is NOT an edge case. It is the
     * CENTRAL MECHANIC. Edge cases are the corners (empty tree, single node, key below
     * everything, key above everything, key equal to a node, key absent). Two different
     * failure types, two different remedies. Merging them makes both look like one vague
     * "I cannot see things" problem, which they are not: both are procedural.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: floor = -1, ceil = -1, cur = root.
     * Step 2: While cur != null:
     *   - If cur.val == val: return [val, val]      (key present; it is both floor and ceil)
     *   - If cur.val < val:  floor = cur.val; cur = cur.right   (record, then look for bigger)
     *   - Else:              ceil  = cur.val; cur = cur.left    (record, then look for smaller)
     * Step 3: Return [floor, ceil]. Anything never recorded stays -1.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - Record happens BEFORE the move, on the node being compared.
     * - Plain assignment, never max/min: the BST guarantees each new candidate is better.
     * - -1 initialization doubles as the "not found" answer; no post-check needed.
     * - The equality branch is what prevents the infinite loop.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(H), where H is the height. Each iteration descends exactly one
     *    level and discards one whole subtree, so the work is bounded by one root-to-leaf
     *    path. O(log N) if balanced, O(N) if skewed. (Never state a flat "O(log N)".)
     * -> Space Complexity: O(1). Two ints and one pointer. No recursion, no stack, no list.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Floor/ceil = descend once, recording the best candidate seen on each side; the BST
     *   ordering makes every later candidate strictly better, so no max/min is needed.
     * - Floor and ceil are defined against the KEY, not against the current node.
     * - The equality case is mandatory (both the answer AND the loop-termination guard).
     * - -1 initialization makes "does not exist" fall out for free.
     * - Same O(H) shape as BST search, insert, and delete: compare, discard one side, descend.
     */
    private static List<Integer> floorCeilOfBST(TreeNode root, int val) {
        TreeNode cur = root;
        int currentCeil = -1;
        int currentFloor = -1;
        List<Integer> result = new ArrayList<>();
        while (cur != null) {
            if (cur.val == val) {
                return Arrays.asList(val, val);
            }
            if (cur.val < val) {
                currentFloor = cur.val;
                cur = cur.right;
            } else {
                currentCeil = cur.val;
                cur = cur.left;
            }
        }

        return Arrays.asList(currentFloor, currentCeil);
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
