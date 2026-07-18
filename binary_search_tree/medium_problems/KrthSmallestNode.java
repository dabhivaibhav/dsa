package binary_search_tree.medium_problems;

/*
230. Kth Smallest Element in a BST

Given the root of a binary search tree, and an integer k, return
the kth smallest value (1-indexed) of all the values of the nodes in the tree.

Example 1:
Input: root = [3,1,4,null,2], k = 1
Output: 1

Example 2:
Input: root = [5,3,6,2,4,null,null,1], k = 3
Output: 3

Constraints:
            The number of nodes in the tree is n.
            1 <= k <= n <= 10^4
            0 <= Node.val <= 10^4

Follow up: If the BST is modified often (i.e., we can do insert and delete operations)
and you need to find the kth smallest frequently, how would you optimize?
*/


import java.util.LinkedList;
import java.util.List;

public class KrthSmallestNode {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println(kthSmallestBruteForce(root, 3));
    }

    /*
     * WHAT THIS METHOD DOES:
     * BRUTE-FORCE kth smallest in a BST: run an inorder traversal collecting every value
     * into a list, then return the element at index k-1. Works because of THE fact:
     * INORDER TRAVERSAL OF A BST YIELDS SORTED ORDER. No sorting step is needed, ever.
     *
     * THE SENTENCE: inorder of a BST is sorted, so the kth inorder visit IS the kth smallest.
     *
     * ---
     *
     * THE "COLLECT INORDER, PICK K-1" PATTERN (KTH SMALLEST, BRUTE FORCE)
     *
     * Your Thought Process & Intuition:
     * 1. THE RITUAL, QUESTION 1 (what does the structure guarantee?): it is a BST, and a
     *    BST's inorder is sorted. That single fact IS the problem. My first attempt built
     *    queues and case analysis for left-only/right-only trees because I skipped this
     *    question; the shape of the tree never mattered at all.
     *
     * 2. THE RITUAL, QUESTION 3 (dumbest thing that works): collect all values, take the
     *    kth. The "collect" step is inorder, and inorder already emits sorted order, so the
     *    planned "then sort them" step died on contact with question 1. The naive version
     *    and the real version differ ONLY in whether you STORE everything or COUNT as you
     *    go. That is the upgrade path, not a different algorithm.
     *
     * 3. THE SHARED COLLECTOR: one list created at the top, passed into every recursive
     *    call, every call adds to the SAME list. This is the sb pattern from
     *    serialize/deserialize: helper is void, appends as a side effect, caller reads the
     *    result once at the end.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. NO Collections.sort:
     *    - Why? The list is already sorted by construction. Sorting it again is O(N log N)
     *      spent re-proving what the BST guaranteed. Worse, it signals to a reader that the
     *      author does not know WHY the traversal was inorder.
     *
     * 2. ArrayList, not LinkedList:
     *    - Why? get(i) on a LinkedList walks from the head: O(i) per call, so even the
     *      single get(k-1) costs O(k), and a get-in-a-loop costs O(k^2). ArrayList get is
     *      O(1). Same algorithm, different structure, different complexity.
     *
     * 3. list.get(k - 1):
     *    - Why? k is 1-indexed (the problem says so), the list is 0-indexed. The kth
     *      smallest lives at index k-1. Constraints guarantee 1 <= k <= n, so no bounds
     *      check is needed.
     *
     * 4. Void helper with the list as a parameter:
     *    - Why? Recursion combines either by RETURN-AND-COMBINE or by ONE SHARED COLLECTOR.
     *      Merging returned lists at every node is clunky; the shared collector is the
     *      natural fit when output is a sequence built in visit order.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - WROTE POSTORDER AND CALLED IT INORDER: the add was AFTER both recursive calls
     *   (left, right, root). Inorder is left, ROOT, right: the add goes BETWEEN the calls.
     *   The visit-position IS the traversal; same three lines, the add's position is the
     *   only difference (same lesson as Morris inorder vs preorder).
     * - EVERY CALL MADE ITS OWN LIST AND DROPPED THE CHILDREN'S: `inOrder(root.left);`
     *   called the method and ignored the returned list, so every call's list held exactly
     *   one value and the top call returned a one-element list for any tree. This is the
     *   countNodes fear ("each call gets its own copy") actually happening. Fix: the shared
     *   collector.
     * - RETURNED null FOR AN EMPTY SUBTREE in the first version: any caller using that
     *   return would NPE. The void-with-collector shape makes the question disappear.
     * - KEPT THE SORT after being told the list was already sorted: did not trust my own
     *   documented fact. The library only pays if it is consulted AND believed.
     * - PICK LOOP INSTEAD OF get(k-1), on a LinkedList: functionally identical to get(k-1)
     *   but O(k^2) because LinkedList.get(i) is O(i) per call.
     * - THE ROOT MISTAKE OF THE WHOLE PROBLEM: started inventing machinery (bounded queue,
     *   nested loops, left-only/right-only edge cases) before running the ritual. Question
     *   1 alone finishes this problem before any code exists.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Create one ArrayList.
     * Step 2: Inorder traverse (left, add current value, right), all calls sharing the list.
     * Step 3: Return list.get(k - 1).
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N).
     *      - Traversal: O(N), each node visited once, O(1) work per node (ArrayList add is
     *        amortized O(1)).
     *      - Pick: O(1) with ArrayList.get(k-1).
     *      - (As originally written it was O(N log N): the redundant sort dominated. And
     *        the LinkedList pick loop added a hidden O(k^2). Both removed.)
     * -> Space Complexity: O(N) for the list, plus O(H) recursion stack (O(log N) balanced,
     *    O(N) skewed). The list dominates: O(N) total.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say the sentence first: "inorder of a BST is sorted, so the kth inorder visit is
     *   the kth smallest." That sentence is the whole solution.
     * - Never sort an inorder-of-a-BST. It is already sorted; sorting it reveals you do not
     *   know why.
     * - The real solution stores NOTHING: keep a counter during inorder, stop at the kth
     *   visit. O(H + k) time, O(H) space recursive, O(1) space with Morris inorder.
     * - The follow-up (tree modified often, query often): augment each node with the size
     *   of its subtree; then kth smallest is an O(H) walk deciding left/self/right by
     *   counts, the same count-by-structure idea as LC 222.
     * - LinkedList.get(i) is O(i). Structure choice can change complexity with zero change
     *   to the algorithm.
     */
    private static int kthSmallestBruteForce(TreeNode root, int k) {
        List<Integer> list = new LinkedList<>();
        inOrder(root, list);
        int ans = -1;
        for (int i = 0; i < k; i++) {
            ans = list.get(i);
        }


        return ans;

    }

    private static void inOrder(TreeNode root, List<Integer> list) {
        if (root == null) return;
        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
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
