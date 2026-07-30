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

        System.out.println(findTarget(root, 9));


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
    private static boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return findTarget(root.left, k) || findTarget(root.right, k);

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
