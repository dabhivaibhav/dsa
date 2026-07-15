package binary_search_tree.easy_problems;

/*
Leetcode 700. Search in a Binary Search Tree

You are given the root of a binary search tree (BST) and an integer val.

Find the node in the BST that the node's value equals val and return the
subtree rooted with that node. If such a node does not exist, return null.

Example 1:
Input: root = [4,2,7,1,3], val = 2
Output: [2,1,3]

Example 2:
Input: root = [4,2,7,1,3], val = 5
Output: []

Constraints:
            The number of nodes in the tree is in the range [1, 5000].
            1 <= Node.val <= 10^7
            root is a binary search tree.
            1 <= val <= 10^7
 */
public class SearchInBST {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.left.left = new TreeNode(8);
        root.right.left.right = new TreeNode(9);
        System.out.println(searchBST(root, 8));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Finds the node whose value equals val in a Binary Search Tree and returns that
     * node (which, since a TreeNode carries its children, IS the subtree rooted there).
     * Returns null if absent. Uses the BST ordering property to walk a SINGLE root-to-leaf
     * path instead of exploring the whole tree, iteratively, so O(1) auxiliary space.
     *
     * ---
     *
     * THE "BINARY SEARCH ON A TREE" PATTERN (SEARCH IN A BST)
     *
     * Your Thought Process & Intuition:
     * 1. THE BST PROPERTY (state it precisely): for EVERY node, ALL values in its LEFT
     *    SUBTREE are less than it, and ALL values in its RIGHT SUBTREE are greater. Note
     *    SUBTREE, not CHILD. This is the distinction most people get wrong, and it is the
     *    whole core of Validate BST (LC 98).
     *    Counterexample to the "child" misreading:
     *              5
     *             / \
     *            3   8
     *               / \
     *              1   9
     *    Every parent/child pair looks fine locally (3<5, 8>5, 1<8, 9>8), but 1 sits in
     *    5's RIGHT subtree while 1 < 5. NOT a BST. The rule binds whole subtrees.
     *
     * 2. WHY THAT PROPERTY MAKES SEARCH FAST: at each node, comparing val to cur.val tells
     *    you which side the target MUST be on, so you discard the entire other subtree.
     *    That is binary search, just expressed as a tree instead of an array. You walk ONE
     *    root-to-leaf path, never both branches.
     *
     * 3. WHY NO STACK / NO RECURSION IS NEEDED: because you never explore both sides, there
     *    is nothing to backtrack to. A plain binary tree search must try both subtrees (so
     *    it needs a stack or recursion); a BST search never does. Hence a simple while loop
     *    with one moving pointer, O(1) space.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Iterative while loop over recursion:
     *    - Why? The recursive version is equally correct but costs O(H) call-stack space
     *      (O(N) on a skewed BST). The iterative version is O(1). Since no backtracking is
     *      ever required, recursion buys nothing here.
     *
     * 2. Returning the NODE, not a copy or a list:
     *    - Why? A TreeNode already points to its children, so returning the matched node
     *      returns the entire subtree rooted at it, which is exactly what the problem asks.
     *
     * 3. Three-way compare (>, <, else):
     *    - Why? Greater means go left, less means go right, equal means found. The `else`
     *      branch is the success exit; falling out of the loop means the path dead-ended
     *      at null, so the value is absent.
     *
     * ---
     *
     * MISTAKES / TRAPS (mine and the classic ones):
     * - MY CONCEPTUAL SLIP: I said BST search avoids traversing every node, implying it is
     *   always sub-linear. NOT TRUE. Insert 1,2,3,4,5 in order and you get a valid BST that
     *   is a straight right-leaning chain (a linked list). Searching for 5 visits every
     *   node: O(N). The BST property gives O(H), not O(log N).
     *   HOW TO CATCH IT NEXT TIME: whenever I claim a structure is "fast," ask what its
     *   WORST-CASE SHAPE is, then re-check the claim against that shape. Same pattern as my
     *   other misses all weekend: state the first true thing, forget the second condition
     *   clamped to it (here: "fast" requires BALANCE, not just the ordering property).
     *
     * - SAYING "O(log N)" FLAT IN AN INTERVIEW: a common probe. The correct answer is O(H),
     *   which is O(log N) IF BALANCED and O(N) if skewed. Say it that way.
     *
     * - THE SUBTREE-vs-CHILD MISREADING: checking only parent/child pairs locally accepts
     *   non-BSTs (see the counterexample above). Every node constrains its whole subtree.
     *
     * - REDUNDANT NULL GUARD: the `if (root == null) return null;` up top does nothing that
     *   `while (cur != null)` does not already handle (loop never runs, falls through to
     *   `return null`). Harmless, but it is the same reflex I had in Morris inorder. Trust
     *   the loop condition.
     *
     * - PLUMBING: LeetCode's stub is `public TreeNode searchBST(...)`. A private/non-static
     *   method will not match their harness.
     *
     * ---
     *
     * BST CONCEPTS WORTH KNOWING (these feed the next problems):
     * 1. INORDER TRAVERSAL OF A BST YIELDS SORTED ORDER. This is the single most useful BST
     *    fact. Left, root, right = smallest to largest. It is the backbone of:
     *      - Validate BST (98): inorder must be strictly increasing.
     *      - Kth Smallest (230): the kth element of the inorder sequence.
     *      - Recover BST (99): find the two nodes that break the sorted order.
     *    And since Morris inorder gives sorted order in O(1) space, Morris + BST is a strong
     *    combination worth remembering.
     *
     * 2. O(H) IS THE REAL GUARANTEE; BALANCE IS WHAT MAKES H SMALL. The ordering property
     *    alone gives O(H). Self-balancing trees (AVL, red-black) exist precisely to force
     *    H = O(log N) so that O(H) actually means O(log N). Java's TreeMap/TreeSet are
     *    red-black trees for this reason.
     *
     * 3. INSERT/DELETE/SEARCH ARE ALL O(H) and all follow the same "compare and descend one
     *    side" walk. Search is the simplest instance of that shared pattern.
     *
     * 4. A BST IS BINARY SEARCH MADE PERSISTENT. Same halving logic as binary search on a
     *    sorted array, but the structure supports O(H) inserts/deletes, which an array does
     *    not (array insert is O(N) due to shifting).
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: cur = root.
     * Step 2: While cur != null:
     *   - If cur.val > val, the target must be smaller: cur = cur.left.
     *   - Else if cur.val < val, the target must be larger: cur = cur.right.
     *   - Else, cur.val == val: return cur.
     * Step 3: Fell out of the loop (hit null): the value is not in the tree, return null.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - Each comparison discards an ENTIRE subtree; that discard is where the speed comes
     *   from, and it is only valid because the ordering rule binds whole subtrees.
     * - Returning `cur` returns the whole subtree, no extra work needed.
     * - Falling out of the while loop is the "not found" path; no separate check needed.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(H), where H is the height of the tree.
     *    WHY: each iteration moves DOWN exactly one level and discards one whole subtree, so
     *    the number of iterations is bounded by the length of one root-to-leaf path, which
     *    is H. Never O(N) because you never visit both subtrees of any node.
     *    WHAT H ACTUALLY IS:
     *      - Balanced BST: H ~ log N  ->  O(log N). (the case people mean when they say
     *        "BSTs are fast": ~20 steps for a million nodes)
     *      - Skewed BST:   H = N      ->  O(N). (a chain; no better than a linked list)
     *    So the honest answer is O(H): O(log N) if balanced, O(N) worst case.
     * -> Space Complexity: O(1). One pointer, no recursion, no stack. The recursive version
     *    of this same search would be O(H) space for the call stack.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - BST rule binds SUBTREES, not children. Say it that way; it signals you understand
     *   why local checks fail.
     * - Search = discard one subtree per comparison = binary search on a tree = O(H).
     * - Answer complexity as "O(H), which is O(log N) balanced and O(N) skewed." Never a
     *   flat "O(log N)."
     * - Prefer the iterative version: no backtracking is needed, so recursion only costs
     *   O(H) stack for nothing.
     * - Remember the big one: INORDER of a BST is SORTED. Most BST problems reduce to that.
     */
    private static TreeNode searchBST(TreeNode root, int val) {
        TreeNode cur = root;

        while (cur != null) {
            if (cur.val > val) {
                cur = cur.left;
            } else if (cur.val < val) {
                cur = cur.right;
            } else {
                return cur;
            }
        }
        return null;
    }


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {

        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
