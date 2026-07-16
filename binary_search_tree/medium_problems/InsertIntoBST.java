package binary_search_tree.medium_problems;


/*
Leetcode 701: Insert Into a Binary Search Tree

You are given the root node of a binary search tree (BST) and a value to insert into the tree.
Return the root node of the BST after the insertion. It is guaranteed that the new value does
not exist in the original BST.

Notice that there may exist multiple valid ways for the insertion, as long as the tree remains
a BST after insertion. You can return any of them.

Example 1:
Input: root = [4,2,7,1,3], val = 5
Output: [4,2,7,1,3,5]

Example 2:
Input: root = [40,20,60,10,30,50,70], val = 25
Output: [40,20,60,10,30,50,70,null,null,25]

Example 3:
Input: root = [4,2,7,1,3,null,null,null,null,null,null], val = 5
Output: [4,2,7,1,3,5]

Constraints:
            The number of nodes in the tree will be in the range [0, 10^4].
            -10^8 <= Node.val <= 10^8
            All the values Node.val are unique.
            -10^8 <= val <= 10^8
            It's guaranteed that val does not exist in the original BST.
 */
public class InsertIntoBST {

    public static void main(String[] args) {

    }

    /*
     * WHAT THIS METHOD DOES:
     * Inserts a value into a Binary Search Tree and returns the root. Descends the single
     * path the BST property dictates, keeping a TRAILING PARENT pointer plus a direction
     * flag, so that when the walk falls off the tree the empty slot is known and the new
     * node is attached there. Iterative: O(H) time, O(1) space.
     *
     * ---
     *
     * MY THOUGHT PROCESS (written BEFORE the code):
     *
     * 1. The new value must be placed so the tree still follows the BST rule.
     *    (The goal is not just "find a spot." The goal is to keep the rule true.)
     *
     * 2. Start at the root. If the value is bigger, go right. If it is smaller, go left.
     *
     * 3. Repeat the same comparison at every node. A value can be smaller than the root
     *    but bigger than the next node down, so each node makes its own decision. The rule
     *    applies at every level, not just at the top.
     *
     * 4. Stop at the point where going further is useless.
     *    (This was the real question. Everything else is easy.)
     *
     * WHAT THIS GOT RIGHT:
     * - Point 1: framed the goal correctly. Keep the BST rule true, do not just find a hole.
     * - Point 2: the comparison rule, correct.
     * - Point 3: this is the important one. The rule repeats at every node. This is what
     *   separates "left child < root" (wrong) from "the whole left subtree < root" (right).
     * - Point 4: this is the crux, and it was named as a QUESTION before any code was
     *   written.
     *
     * WHY WRITING THIS FIRST MATTERED:
     * In the floor/ceil problem, the same kind of question stayed open, and code got written
     * anyway to fill the gap (Math.max, cur.left.val). Those looked like answers but were
     * really guesses. Here the question was written down first, so it had to be answered
     * before the code existed. The code came out correct on the first try.
     *
     * RULE: write the open question down before writing code. Once code is on the screen,
     * the gap becomes invisible.
     *
     * ---
     *
     * THE "TRAILING PARENT" PATTERN (INSERT INTO A BST)
     *
     * Your Thought Process & Intuition:
     * 1. THE OPEN QUESTION I NAMED UP FRONT: "I need to stop at a point where it is of no
     *    use to go further." That IS the crux of this problem. Everything else is mechanics.
     *
     * 2. THE ANSWER: it is of no use to go further exactly when the side the BST property
     *    forces you toward is EMPTY. So descend while there is a node to stand on, and stop
     *    when you fall into null. That null is the hole where val belongs.
     *
     * 3. THE PROBLEM WITH STOPPING THERE: once current is null, you have overshot. You are
     *    standing on nothing and cannot attach anything to it. Hence the TRAILING PARENT:
     *    remember the last real node you were on, plus WHICH slot you fell out of. Those two
     *    facts locate the hole exactly.
     *
     * 4. THE INVARIANT (say this out loud in an interview):
     *    "When the loop exits, current is null, parent is the last real node on the path,
     *    and `left` records which of its child slots I fell out of. So parent has an empty
     *    slot in exactly the direction `left` names, and by the BST property that slot is
     *    where val belongs."
     *    This is the thing that signals structural understanding. Most candidates write the
     *    same code and cannot articulate it.
     *
     * 5. INSERT ALWAYS CREATES A LEAF, NEVER DISPLACES ANYTHING: the loop only stops at an
     *    EMPTY slot, so there is nothing there to push aside. No subtree ever moves, no node
     *    is ever re-parented. Every node passed through is untouched. This is why insert is
     *    EASY. (Contrast: DELETE of a two-child node genuinely must re-parent, replacing the
     *    node with its inorder successor and rewiring subtrees. That is why LC 450 is harder.
     *    The "what if I have to attach in the middle?" worry is real THERE, not here.)
     *
     * 6. THE POSITION IS DETERMINISTIC: there is exactly ONE legal slot for val. At every
     *    node the BST property forces the direction; there is no choice and no ambiguity.
     *    Two people inserting the same value into the same tree land in the same place.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. The `if (root == null)` guard is LOAD-BEARING here (unlike in searchBST and
     *    floor/ceil, where the same-looking line was redundant):
     *    - Why the difference? There, nothing after the loop dereferenced anything, so a
     *      null root just fell through to a correct `return null`. HERE, code AFTER the loop
     *      does `parent.left = ...`. With a null root the loop never runs, parent stays null,
     *      and that line throws an NPE.
     *    - THE RULE: a null guard is redundant only when the loop condition covers it AND
     *      nothing after the loop dereferences. Do not pattern-match "root null guard =
     *      redundant." Check what runs after the loop.
     *
     * 2. `while (current != null)` as the exit condition:
     *    - Why? It states the termination in the header: keep descending while there is a
     *      node to stand on. A reader learns when the loop ends without reading the body.
     *
     * 3. The loop style is NOT an independent knob; it comes with the pattern:
     *    - TRAILING PARENT (this version): overshoot into null, then back up. Has a natural
     *      pre-condition (`current != null`), costs two extra variables (parent, left).
     *    - LOOK-AHEAD (the common alternative): test `if (cur.left == null)` BEFORE
     *      descending, attach and break right there. No parent, no flag, but the exit is
     *      discovered mid-body, so `while (true)` + breaks is INTRINSIC to it.
     *    You cannot have the look-ahead's variable economy AND a self-documenting condition.
     *    They come as a package. Both are O(H)/O(1) and both are standard.
     *
     * 4. Why trailing parent was worth the two variables:
     *    - It GENERALIZES. BST delete requires knowing a node's parent to unlink it, so the
     *      same machinery carries straight into LC 450. Look-ahead does not transfer.
     *
     * 5. Iterative over recursive:
     *    - The recursive version is shorter and reads as the BST property itself
     *      (`root.left = insertIntoBST(root.left, val); return root;`), but costs O(H) stack.
     *      This is O(1). Worth mentioning the recursive one in an interview as the shorter
     *      alternative passed on DELIBERATELY for space.
     *
     * ---
     *
     * TRAPS AVOIDED (no bugs on this one; these are what could have gone wrong):
     * - NPE ON EMPTY TREE: covered by the load-bearing null guard (see design choice 1).
     *   This is the trap, because the same line WAS redundant in the two previous BST
     *   problems, so the reflex to delete it would have crashed here.
     * - PARENT NEVER NULL AT THE ATTACH STEP: guaranteed, because root != null means the
     *   loop body runs at least once and sets parent.
     * - DUPLICATES: the constraints guarantee val is not already in the tree, so equality
     *   never fires. The `else` branch would send a duplicate right if it did. Worth knowing
     *   which guarantee is being leaned on.
     * - FORGETTING TO RETURN root: the method must return the ORIGINAL root, not the new
     *   node. Only the empty-tree case returns something different.
     *
     * - THE META-NOTE (the win on this problem): the open question was named BEFORE writing
     *   ("stop where it is of no use to go further") and then ANSWERED, with the parent +
     *   direction mechanism, rather than filled with plausible-looking code. That is exactly
     *   the floor/ceil failure mode (an unresolved question and a piece of code occupying the
     *   same spot), and it did not happen here. The procedure worked.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: If root is null, the tree is empty: return a new node holding val.
     * Step 2: current = root, parent = null, left = false.
     * Step 3: While current != null:
     *   - If current.val > val: parent = current; current = current.left;  left = true.
     *   - Else:                 parent = current; current = current.right; left = false.
     * Step 4: The walk fell off the tree. If left, attach a new node at parent.left,
     *         otherwise at parent.right.
     * Step 5: Return root (the original root, unchanged).
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - parent and left are set on EVERY iteration, so after the loop they always describe
     *   the last real node and the slot that was empty.
     * - The new node is always a LEAF; nothing is displaced or re-parented.
     * - The null guard is required here specifically because parent is dereferenced after
     *   the loop.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(H), where H is the height. Each iteration descends exactly one
     *    level and discards one whole subtree, so the work is bounded by one root-to-leaf
     *    path. O(log N) if balanced, O(N) if skewed. (Never state a flat "O(log N)".)
     * -> Space Complexity: O(1). One pointer, one parent reference, one boolean. No
     *    recursion, no stack. The recursive version of this same insert would be O(H).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Insert = descend the forced path until the required side is empty, then attach a
     *   LEAF there. It never displaces an existing node; that is what makes it easy.
     * - The trailing-parent invariant is the thing to say out loud: on exit, current is null,
     *   parent is the last real node, and the flag names the empty slot.
     * - The insertion position is unique and deterministic; the BST property does the
     *   deciding.
     * - O(H): O(log N) balanced, O(N) skewed. Same shape as search, floor/ceil, and delete.
     * - Delete is the hard sibling: removing a two-child node DOES require re-parenting via
     *   the inorder successor. The parent pointer learned here carries into that problem.
     */
    private static TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode current = root;
        TreeNode parent = null;
        boolean left = false;
        while (current != null) {
            if (current.val > val) {
                parent = current;
                current = current.left;
                left = true;
            } else {
                parent = current;
                current = current.right;
                left = false;
            }
        }
        if (left) {
            parent.left = new TreeNode(val);
        } else {
            parent.right = new TreeNode(val);
        }
        return root;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
