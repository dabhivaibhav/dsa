package binary_search_tree.medium_problems;

/*
Leetcode 1008: Construct Binary Search Tree from Preorder Traversal

Given an array of integers preorder, which represents the preorder traversal
of a BST (i.e., binary search tree), construct the tree and return its root.

It is guaranteed that there is always possible to find a binary search tree
with the given requirements for the given test cases.

A binary search tree is a binary tree where for every node, any descendant
of Node.left has a value strictly less than Node.val, and any descendant of
Node.right has a value strictly greater than Node.val.

A preorder traversal of a binary tree displays the value of the node first,
then traverses Node.left, then traverses Node.right.

Example 1:
Input: preorder = [8,5,1,7,10,12]
Output: [8,5,10,1,7,null,12]

Example 2:
Input: preorder = [1,3]
Output: [1,null,3]


Constraints:
            1 <= preorder.length <= 100
            1 <= preorder[i] <= 1000
            All the values of preorder are unique
 */
public class ConstructBSTFromPreOrderTraversal {

    public static void main(String[] args) {

        int[] arr = {8,5,1,7,10,12};
        System.out.println(constructBSTFromPreorderBruteforce(arr));

    }

    /*
     * WHAT THIS METHOD DOES:
     * BRUTE-FORCE construction of a BST from its preorder traversal: the first array
     * element becomes the root, and every remaining element is inserted with a standard
     * BST insert, each one walking from the root to its slot. No index tricks, no
     * bounds — just N reuses of code already in my library.
     *
     * THE SENTENCE: first value is the root; every other value finds its own place by
     * walking from the root — the same insert, N times.
     *
     * ---
     *
     * THE "N INSERTS FROM THE ROOT" PATTERN (BST FROM PREORDER, BRUTE FORCE)
     *
     * Your Thought Process & Intuition:
     * 1. WHY PREORDER ALONE IS EVEN ENOUGH (ritual Q2, neighbor one — my own unique-tree
     *    theory problem): preorder alone can NEVER determine a general binary tree — I
     *    proved that with the single-child counterexample. It works HERE only because the
     *    problem says BST: a BST's inorder is its sorted values, so one preorder secretly
     *    carries both traversals. The ordering property substitutes for the inorder split.
     *    If an interviewer asks "why is this well-defined?", that is the answer.
     *
     * 2. "HOW DO I GO BACK TO THE PREVIOUS NODE?" — DISSOLVED, NOT ANSWERED: there is no
     *    back. No state survives from one element to the next. Every value starts a fresh
     *    walk at the root, and the walk computes its position. The "track" I felt I was
     *    missing does not exist in this design; restarting from the root IS the
     *    position-finder.
     *
     * 3. RITUAL Q2, NEIGHBOR TWO — THE WHOLE METHOD IS MY OWN insertIntoBST IN A LOOP:
     *    the walk (compare, descend, fall into a null slot, attach via trailing parent)
     *    is my insert, verbatim, comment block and all. The brute force was never new
     *    code; it was recognizing which library method to loop.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. current = root at the start of EVERY insert:
     *    - Why? Position in a BST is defined by the path from the root, nothing else.
     *      Fresh walk per value = correct slot per value, at the cost of re-walking
     *      shared prefixes (that waste is exactly what the optimal removes).
     *
     * 2. Attach ONLY at a null slot found by walking (trailing parent):
     *    - Why? New nodes land in empty slots. Writing into root.left/root.right
     *      directly makes every new node overwrite the previous one.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - COMPARED AGAINST preorder[i-1]: asked the previous ARRAY element where the new
     *   value goes. Array adjacency and tree adjacency are different relations: 10 comes
     *   right after 7 in the array, and 10's parent is 8, three levels above 7. Only a
     *   walk from the root computes tree position. (Half-redeemed: "smaller than the
     *   previous value means its left child" IS true in BST preorder — the adjacent
     *   comparison belongs to the OPTIMAL solution's intuition; it just cannot decide
     *   the greater case alone, because "greater" means SOME ancestor's right — which one
     *   is the whole question.)
     * - WROTE INTO root.left / root.right DIRECTLY: every new node overwrote the last —
     *   node 5 died the moment node 1 arrived. Only three of six nodes survived.
     * - INVERTED THE BRANCHES in version one (smaller went right).
     * - FIXED ONE BUG OF THREE AND REPOSTED: patched the inverted branches while the
     *   overwrite bug and the preorder[i-1] bug — both named in the same message, with
     *   the full fix as pseudocode one scroll up — sailed past. The reading-skim: first
     *   actionable item registers, rest slides. Explain-to-proceed exists for exactly
     *   this.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: root = new node(preorder[0]).
     * Step 2: For each remaining value, insertIntoBST(root, value) — fresh walk, trailing
     *         parent, attach at the null slot.
     * Step 3: Return root.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * >>> FILL THIS IN YOURSELF — this is gate question one, posed four times now. <
     * >>> Structure of the argument (you supply numbers and the killer input):     <
     * >>>   - N inserts, each costing O(H). What is H in the WORST case?           <
     * >>>   - So worst-case total = N × ____ = O( ______ ).                        <
     * >>>   - THE SPECIFIC INPUT ARRAY that causes it: what does the preorder of a <
     * >>>     completely skewed BST look like written out flat? Give the array.    <
     * >>>   - Best case (balanced): N × O(log N) = O(N log N).                     <
     * >>> Space: O(1) auxiliary per insert; O(N) for the tree itself (the output). <
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Open with WHY it's well-defined: BST ordering substitutes for the missing
     *   inorder (my unique-tree proof says preorder alone never suffices otherwise).
     * - The brute force is library reuse: N × insert. State its worst case unprompted,
     *   WITH the input that causes it.
     * - The upgrade: preorder delivers values in exactly the order the tree wants them,
     *   so a single forward pass with a shared index works — IF each open slot knows
     *   which values it may admit. What a slot inherits from its ancestors is the whole
     *   optimal solution. (Gate question two.)
     */
    private static TreeNode constructBSTFromPreorderBruteforce(int[] preorder) {
        TreeNode root = new TreeNode(preorder[0]);
        for (int i = 1; i < preorder.length; i++) {
            insertIntoBST(root, preorder[i]);
        }
        return root;
    }

    private static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        TreeNode current = root;
        TreeNode parent = null;
        boolean left = false;
        while (current != null) {
            parent = current;
            if (val < current.val) {
                current = current.left;
                left = true;
            } else {
                current = current.right;
                left = false;
            }
        }
        if (left) parent.left = new TreeNode(val);
        else parent.right = new TreeNode(val);
        return root;
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
