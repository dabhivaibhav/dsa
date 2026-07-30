package binary_search_tree.easy_problems;

import java.util.HashSet;
import java.util.Set;

/*
Leetcode 653. Two Sum IV - Input is a BST

Given the root of a binary search tree and an integer k,
return true if there exist two elements in the BST such
that their sum is equal to k, or false otherwise.

Example 1:
Input: root = [5,3,6,2,4,null,7], k = 9
Output: true

Example 2:
Input: root = [5,3,6,2,4,null,7], k = 28
Output: false

Constraints:
            The number of nodes in the tree is in the range [1, 104].
            -10^4 <= Node.val <= 10^4
            root is guaranteed to be a valid binary search tree.
            -10^5 <= k <= 10^5
 */
public class TwoSumIVBST {

    private static Set<Integer> set = new HashSet<>();

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);

        System.out.println(findTargetBruteForce(root, 9));
        System.out.println(findTargetOptimal(root, 9));


    }

    /*
     * WHAT THIS METHOD DOES:
     * Checks whether any two nodes in a BST sum to k, using a HashSet to store visited
     * values and the complement check (k - current) at each node. One traversal, one
     * lookup per node: O(N) time, O(N) space.
     *
     * THE SENTENCE: this is Two Sum on an array, wearing a tree costume. The HashSet +
     * complement engine is identical; only the "iteration" changed from a for-loop to
     * a tree traversal.
     *
     * ---
     *
     * THE "TWO SUM WITH A TREE WALK" PATTERN
     *
     * Your Thought Process & Intuition:
     * 1. RITUAL Q2 (nearest neighbor): Two Sum. The classic version uses a HashSet: for
     *    each value v, check if the set contains k-v; if yes, pair found; if no, add v
     *    and continue. Here the values live in tree nodes instead of array slots. The
     *    walk replaces the loop, everything else is identical.
     *
     * 2. THE COMPLEMENT CHECK IS O(1), NOT A SET ITERATION: you want a specific number,
     *    k - current.val, and HashSet.contains() answers that in O(1). "Iterate over the
     *    set checking each" was my first plan and would have been O(N^2). The whole point
     *    of choosing a HashSet over a list is that you never scan it.
     *
     * 3. THE TRAVERSAL ORDER DOES NOT MATTER: preorder, inorder, postorder, any of them
     *    visit every node once, and the complement check is order-independent. Preorder
     *    is the simplest to write (check, add, recurse left, recurse right), so use it.
     *
     * 4. THE BST PROPERTY IS NOT USED IN THIS VERSION: this solution works on ANY binary
     *    tree. The "Input is a BST" in the title is a hint toward the O(H)-space follow-up
     *    (two BSTIterators doing a two-pointer walk on the sorted inorder sequence), which
     *    DOES use the ordering. The HashSet version ignores it, and that is fine for a
     *    first pass.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     * 1. HashSet<Integer>, not a list or array:
     *    - Why? contains() is O(1). A list's contains() is O(N), making the whole solution
     *      O(N^2). The data structure choice IS the complexity difference.
     *
     * 2. Check BEFORE adding:
     *    - Why? If k = 2 * current.val and you add first, the set already contains
     *      current.val, and contains(k - current.val) finds the node paired with itself.
     *      Check first, add after, and a node can only pair with a PREVIOUSLY visited one.
     *      (The problem says "two different nodes," so self-pairing is invalid.)
     *
     * 3. The || combiner on the recursive calls:
     *    - Why? See the mistakes section below. A deep call may find the pair; the || is
     *      what carries that true up through every ancestor. Without it, the answer dies
     *      at the call site.
     *
     * 4. Set as a field, not a local:
     *    - Why? A local resets in every call (the countNodes lesson). The set must
     *      accumulate across the entire traversal. Same cross-call-state principle as
     *      prev, count, postIndex, and the BSTIterator's stack.
     *
     * ---
     *
     * THE RECURSION-RETURN RULE (four appearances, now a permanent checklist item):
     *
     * THE BUG: calling a recursive method as a bare statement and writing `return false`
     * at the bottom:
     *     findTarget(root.left, k);    // true might come back, nobody looks
     *     findTarget(root.right, k);   // same, dropped
     *     return false;                // always runs, always wrong when the pair was below
     *
     * THE FIX:
     *     return findTarget(root.left, k) || findTarget(root.right, k);
     *
     * WHY IT FAILS: recursion combines through RETURN VALUES. A bare statement on a
     * non-void method is a call whose answer dies at the semicolon. The method computed
     * something; nothing received it. It is the serialize bug (ignored the returned
     * string), the inorder-into-list bug (ignored the returned list), the validate-BST
     * bug (needed short-circuiting returns to drain the stack on a violation), and now
     * this, fourth time.
     *
     * THE PERMANENT RULE: every time you write a recursive call as a bare statement
     * (no assignment, no return, no if-guard around it), stop and ask: does this method
     * return something? If yes, the call MUST use the return value. A bare call on a
     * non-void method is almost always a bug. The only exception is when you genuinely
     * want the SIDE EFFECT only (like a void helper that appends to a shared collector),
     * and even then the method should BE void, not a type whose value you are silently
     * dropping.
     *
     * HOW THE || WORKS AS A COMBINER (same mechanics as validate BST's short-circuit):
     * - Left call returns true -> || short-circuits, returns true immediately, right call
     *   never runs. The "found" verdict climbs the stack at full speed.
     * - Left returns false -> right call runs. If right returns true, || yields true.
     * - Both false -> || yields false, this level returns false, and the parent's || keeps
     *   trying.
     * - One true ANYWHERE below, no matter how deep, rides the || chain all the way to
     *   the original caller. That is the combiner doing its job.
     *
     * WHEN TO USE WHICH COMBINER:
     * - || (any-true-wins): "does X exist anywhere in the tree?" Two Sum, path-sum, search.
     * - && (all-true-required): "is every subtree valid?" Validate BST, symmetric tree.
     * - + (sum the pieces): "count everything below me." Count nodes, kth smallest brute
     *   force, tree height.
     * The combiner matches the QUESTION: existence = ||, universal = &&, aggregation = +.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - PLANNED TO ITERATE OVER THE SET: "for each node, iterate the hashset checking if
     *   any value plus current equals k." That is O(N) per node = O(N^2) total. The
     *   HashSet answers "do you contain this one value?" in O(1); that is WHY it is a
     *   HashSet. Iterating it erases its advantage.
     * - DROPPED THE RECURSIVE RETURN VALUES: see the full rule above. Fourth appearance
     *   of the same bug. Called the method, ignored what it said, returned false.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: If node is null, return false (base case, nothing here).
     * Step 2: If set contains (k - node.val), return true (pair found).
     * Step 3: Add node.val to the set.
     * Step 4: Return findTarget(left) || findTarget(right).
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N). Each node visited once, O(1) work per node (one contains, one add,
     *    both O(1) amortized on a HashSet).
     * -> Space: O(N) for the set (up to N values stored) plus O(H) recursion stack.
     *    O(N) dominates.
     *
     * THE FOLLOW-UP (O(H) space, uses the BST property):
     * Two BSTIterators, one walking forward (smallest to largest) and one walking backward
     * (largest to smallest), give a two-pointer walk on the sorted sequence. Left pointer
     * starts at the smallest, right at the largest. Sum too small -> advance left. Sum too
     * large -> advance right. Sum equals k -> found. Pointers meet -> not found. Each
     * iterator uses O(H) space, total O(H). This is the sorted-array two-pointer technique,
     * run on the BST's inorder sequence without materializing it, using the iterator I
     * just built (LC 173). Every piece is already in my library.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "this is Two Sum on a tree" first. It frames the whole solution.
     * - The complement check is one O(1) lookup, never a set scan.
     * - Check before add, so a node cannot pair with itself.
     * - The || combiner carries a deep true up through the ancestor chain. Know why a
     *   bare recursive call drops the answer, and know which combiner (||, &&, +) matches
     *   which question shape.
     * - This version ignores the BST property; the O(H)-space follow-up uses it via two
     *   BSTIterators doing a two-pointer walk. Offer both rungs unprompted.
     */

    private static boolean findTargetBruteForce(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return findTargetBruteForce(root.left, k) || findTargetBruteForce(root.right, k);

    }

    /*
     * WHAT THIS METHOD DOES:
     * Finds whether two nodes in a BST sum to k using the SORTED-ARRAY TWO-POINTER
     * technique, run on two lazy BST iterators instead of an actual array. A forward
     * iterator walks smallest-to-largest, a backward one walks largest-to-smallest, and
     * the main loop advances whichever side needs adjusting. O(N) time, O(H) space.
     *
     * THE SENTENCE: Two Sum on a sorted array, where the "array" is the BST's inorder
     * sequence and the "indices" are two BSTIterators walking from opposite ends.
     *
     * ---
     *
     * THE "TWO ITERATORS AS TWO POINTERS" PATTERN
     *
     * Your Thought Process & Intuition:
     * 1. THE REFRAME THAT UNLOCKS IT: stop looking at the TREE. Look at the SORTED
     *    SEQUENCE the tree represents. Inorder of a BST is sorted. Two Sum on a sorted
     *    array is a solved problem: left pointer at smallest, right at largest, adjust
     *    whichever side makes the sum wrong. The only question is how to walk the sorted
     *    sequence without building the array, and that is what the iterators are for.
     *
     * 2. THE MAIN LOOP NEVER TOUCHES THE TREE: it sees two ints, left and right, and
     *    calls nextSmallest() or nextLargest() to advance one. All tree navigation is
     *    hidden inside the iterators. Tree complexity inside, clean two-pointer logic
     *    outside. That separation is the whole design.
     *
     * 3. THE FORWARD ITERATOR IS MY LC 173, UNCHANGED: pushAllLeft in the constructor
     *    to seed the smallest, pop-and-push-right's-left-spine in next(). The stack holds
     *    the paused state of a forward inorder walk.
     *
     * 4. THE BACKWARD ITERATOR IS THE MIRROR, THREE WORDS CHANGED:
     *    pushAllLeft  -> pushAllRight  (walk right spine to reach largest)
     *    curr.right   -> curr.left     (after popping, load the LEFT child's right spine)
     *    pushAllLeft  -> pushAllRight  (same helper, mirrored)
     *    Same skeleton, mirrored axis. Reverse inorder is right, root, left, so after
     *    visiting a node, the next-largest lives down its left child's right spine.
     *
     * 5. WHY left < right, NOT left <= right: on a BST with unique values (guaranteed),
     *    equal values means the same node. The problem requires two DIFFERENT nodes, so
     *    stop before they meet. If the problem allowed the same node twice, use <=.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     * 1. Two separate stacks, not one:
     *    - Why? The forward and backward walks are independent traversals with independent
     *      paused states. Sharing a stack would tangle them.
     *
     * 2. Instance fields, not static:
     *    - Why? The reentrancy debt, fourteenth appearance: static state shared across
     *      calls corrupts a second run.
     *
     * 3. The iterators are LAZY:
     *    - Why? They produce values one at a time, on demand, holding only O(H) stack
     *      entries at any moment. A list-based approach would eagerly store all N values
     *      upfront, which is the O(N) space the brute force pays. Laziness is the entire
     *      space win.
     *
     * ---
     *
     * HOW THIS CONNECTS TO MY LIBRARY:
     * - The forward iterator IS my LC 173 BSTIterator, the "paused iterative inorder"
     *   pattern, stack as a field, pushAllLeft as the shared helper.
     * - The backward iterator is its mirror, same relationship as Morris inorder to Morris
     *   preorder: same skeleton, one axis flipped.
     * - The two-pointer loop is the sorted-array Two Sum, which I identified as the
     *   neighbor in ritual Q2 for the HashSet version. Same algorithm, different access
     *   pattern (lazy iterators vs array indices).
     * - The "successor = leftmost of right subtree" from delete appears inside
     *   nextSmallest, and "predecessor = rightmost of left subtree" appears inside
     *   nextLargest. Both lazily, one step at a time.
     *
     * ---
     *
     * TRAPS TO KNOW:
     * - TRYING TO DO TWO-POINTER ON THE TREE SHAPE (my first instinct): "both pointers
     *   on the root, move them down children." A tree is not a line; "move right pointer
     *   to the next node" has no meaning without specifying "next in WHAT order," and
     *   following children leads to the same "how do I go back" confusion as bstFromPreorder.
     *   The fix: stop navigating the tree, navigate the SEQUENCE, and let the iterators
     *   handle the tree behind the scenes.
     * - FORGETTING THE MIRROR IS A MIRROR: the backward iterator must push RIGHT in the
     *   constructor (not left), and process LEFT children after popping (not right). Every
     *   left/right swap matters; getting one wrong silently produces forward-order from
     *   both ends, and the loop terminates immediately.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Seed the forward stack with pushAllLeft(root).
     * Step 2: Seed the backward stack with pushAllRight(root).
     * Step 3: left = nextSmallest(), right = nextLargest().
     * Step 4: While left < right:
     *         sum = left + right.
     *         sum == k -> return true.
     *         sum < k  -> left = nextSmallest()   (need bigger on the left end).
     *         sum > k  -> right = nextLargest()   (need smaller on the right end).
     * Step 5: Exhausted without a match -> return false.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N). Each node is pushed once and popped once across both iterators
     *    combined. The two-pointer loop drives at most N advances total.
     * -> Space: O(H). Each stack holds at most one root-to-leaf path. Two stacks:
     *    O(H) + O(H) = O(H). No HashSet, no list. This is the upgrade from the brute
     *    force's O(N).
     *
     * THE LADDER (say unprompted):
     *    1. HashSet + any traversal:      O(N) time, O(N) space. Works on any binary tree.
     *    2. Two iterators + two-pointer:  O(N) time, O(H) space. Uses BST ordering.
     *    3. Morris-based iterators:       O(N) time, O(1) space. Threading replaces stacks.
     *    Each rung trades implementation complexity for space.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Frame it as Two Sum on a sorted sequence accessed lazily. That one sentence shows
     *   you see through the tree costume to the array problem underneath.
     * - The main loop is tree-agnostic; the iterators encapsulate all tree navigation.
     *   That separation is the design, and naming it shows architectural thinking.
     * - The backward iterator is the forward one with three words flipped. Say that, and
     *   the interviewer knows you derived it rather than memorized a second class.
     * - left < right (not <=) because equal values = same node on a unique-value BST.
     * - Offer all three rungs and the tradeoff between them.
     */
    private static boolean findTargetOptimal(TreeNode root, int k) {
        // If root is null, tree is empty, return false
        if (root == null) return false;

        // Create two iterators: one from smallest, one from largest
        BSTIterator l = new BSTIterator(root, false);
        BSTIterator r = new BSTIterator(root, true);

        // Get the first values
        int i = l.next();
        int j = r.next();

        // Loop until two values meet
        while (i < j) {
            // If sum is exactly k, return true
            if (i + j == k) return true;
                // If sum is smaller, move left iterator forward
            else if (i + j < k) i = l.next();
                // If sum is bigger, move right iterator backward
            else j = r.next();
        }

        // If no such pair found, return false
        return false;
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

    // This class is an iterator that allows us to traverse the BST
    static class BSTIterator {
        // Stack is used to keep track of nodes while traversing
        private java.util.Stack<TreeNode> stack;
        // This flag tells us whether we move forward (inorder) or backward (reverse inorder)
        private boolean reverse;

        // Constructor initializes the iterator with root and traversal mode
        BSTIterator(TreeNode root, boolean isReverse) {
            stack = new java.util.Stack<>();
            reverse = isReverse;
            // Push nodes from one side into the stack
            pushAll(root);
        }

        // This function checks if there are still nodes to visit
        boolean hasNext() {
            // If stack is not empty, then we still have nodes left
            return !stack.isEmpty();
        }

        // This function returns the next node’s value in the chosen order
        int next() {
            // Get the node on top of the stack
            TreeNode tmpNode = stack.pop();

            // If we are not in reverse mode, move to the right child
            if (!reverse) {
                pushAll(tmpNode.right);
            }
            // If we are in reverse mode, move to the left child
            else {
                pushAll(tmpNode.left);
            }

            // Return the value of the node we just processed
            return tmpNode.val;
        }

        // Helper function pushes nodes from current down to edge (left or right)
        private void pushAll(TreeNode node) {
            // Keep going until node becomes null
            while (node != null) {
                // Push this node into stack
                stack.push(node);
                // If reverse is true, move to right child
                if (reverse) {
                    node = node.right;
                }
                // Otherwise, move to left child
                else {
                    node = node.left;
                }
            }
        }
    }
}
