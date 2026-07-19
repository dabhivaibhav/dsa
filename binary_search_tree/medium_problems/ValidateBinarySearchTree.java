package binary_search_tree.medium_problems;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 98: Validate Binary Search Tree

Given the root of a binary tree, determine if it is a valid binary search tree (BST).

A valid BST is defined as follows:
The left subtree of a node contains only nodes with keys strictly less than the node's key.
The right subtree of a node contains only nodes with keys strictly greater than the node's key.
Both the left and right subtrees must also be binary search trees.


Example 1:
Input: root = [2,1,3]
Output: true

Example 2:
Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.

Constraints:
            The number of nodes in the tree is in the range [1, 104].
            -2^31 <= Node.val <= 2^31 - 1
 */
public class ValidateBinarySearchTree {

    static Integer prev;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println(isValidBSTBruteForce(root));
        System.out.println(isValidBSTBetter(root));

        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(4);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(6);
        System.out.println(isValidBSTBruteForce(root1));
        System.out.println(isValidBSTBetter(root1));

    }

    /*
     * WHAT THIS METHOD DOES:
     * OPTIMAL BST validation: one inorder traversal carrying a single `prev` value instead
     * of a list. At each visit, the current value must be STRICTLY greater than prev, or
     * the whole tree is invalid and false drains up through the return chain immediately.
     * O(N) time worst case, early exit on the first violation, O(H) space.
     *
     * THE SENTENCE: valid BST iff inorder is strictly increasing, so carry only the
     * previous value and fail the moment the climb breaks.
     *
     * ---
     *
     * THE "PREV-ONLY INORDER CHECK" PATTERN (VALIDATE BST, OPTIMAL)
     *
     * Your Thought Process & Intuition:
     * 1. SAME UPGRADE AS 230: list -> single variable. The scan only ever compared
     *    neighbors, so the whole list was scaffolding; keep just the last-certified value.
     *    The check sits exactly where list.add sat: BETWEEN the recursive calls.
     *
     * 2. DIRECTION DISAPPEARS: my first plan had two conditions ("coming from the left,
     *    check prev greater; from the right, check prev lower"). Wrong frame. Inorder
     *    flattens the tree into ONE sequence; there is no "which side am I coming from,"
     *    only "does the sequence still climb." One check, identical at every visit. The
     *    moment I want to know my direction, I have slid into the min/max-bounds solution
     *    (a real alternative: pass allowed (low, high) down the recursion) and am mixing
     *    frames.
     *
     * 3. HOW THE RIGHT SUBTREE GETS POLICED (my own question, answered by trace): prev is
     *    one continuous stream across the WHOLE tree. When the traversal visits a node, it
     *    sets prev = node.val, and the right subtree's smallest element (its leftmost,
     *    visited first) is immediately compared against that. In [5,1,4,null,null,3,6],
     *    node 3 fails against prev = 5, an ancestor two levels up, with no explicit
     *    ancestor check anywhere. The sequence closes the subtree-vs-child trap by itself.
     *
     * 4. THE VERDICT TRAVELS BY RETURN, prev BY FIELD, and the split is principled:
     *    validity is something every level must react to (false must stop ancestors from
     *    working), so it rides the returns; prev is cross-call state no level reacts to,
     *    so it lives in a field. Contrast 230: void helper, answer in a field, because a
     *    self-sealing one-time capture needs no reaction. When levels must combine and act
     *    on a result, return it; when it is a one-way capture, a field suffices.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. `Integer prev`, starting null (NOT an int sentinel):
     *    - Why? Node values span the ENTIRE int range, so every int is some tree's legal
     *      first value; there is no safe int sentinel. prev = 0 fails on a single node
     *      holding -5; prev = Integer.MIN_VALUE fails on a node holding MIN_VALUE itself.
     *      null means "nothing seen yet", a state no value can collide with. The guard
     *      `prev != null &&` IS the first-visit skip, folded into the comparison.
     *      (Alternative escape: widen the type, long prev = Long.MIN_VALUE.)
     *
     * 2. Reset prev in the public method:
     *    - Third appearance of the same debt (sb, count/answer): static state survives
     *      between runs; run two must not inherit run one's prev.
     *
     * 3. `if (!inOrder(root.left)) return false;` as the first line:
     *    - The drain. A deep false returns; each ancestor's first line sees it and returns
     *      false without visiting or going right. O(1) per ancestor, stack empties fast.
     *
     * 4. Strict check (`root.val <= prev` fails):
     *    - "Strictly" in the problem statement is load-bearing; duplicates are invalid.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - PROPOSED prev = 0: killed by a one-node tree holding -5 (0 >= -5 -> false on a
     *   valid tree). The general lesson: when values span the whole type, NO in-band
     *   sentinel exists; escape via null (out-of-band state) or a wider type.
     * - PROPOSED A BOOLEAN PARAMETER to force early exits: Java passes parameters BY
     *   VALUE; a deep call flipping its copy moves nobody else's. Information cannot
     *   travel up or sideways through parameters, only through RETURNS (the countNodes
     *   lesson, mirror image). The drain I wanted falls out of short-circuiting returns
     *   for free.
     * - `Prev` vs `prev`: declared the field capitalized, used it lowercase. Java is
     *   case-sensitive; two unrelated names; the entire "why is it not working" was a
     *   compile error. Convention: variables start lowercase.
     * - DUPLICATE inOrder(root.left) LEFT BEHIND after editing: traversed the left subtree
     *   twice with the second verdict ignored. ACCIDENTALLY HARMLESS here (the second
     *   pass's first visit fails against the subtree's max already in prev, and the false
     *   is swallowed at the ignored call site, so prev survives uncorrupted), which is
     *   WORSE than obviously broken: it passes tests today and detonates on the next edit.
     *   After editing, re-read the whole method, not just the changed line.
     * - THE TWO-CONDITIONS PLAN (see intuition 2): direction-aware checks are the wrong
     *   frame for a sequence-based solution.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: prev = null. Call the helper; its verdict is the answer.
     * Step 2 (helper): null node -> true.
     * Step 3: If the left subtree is invalid, return false (drain).
     * Step 4: VISIT: if prev != null and node.val <= prev, return false. Else prev = node.val.
     * Step 5: Return the right subtree's verdict.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - The null guard is the sentinel escape AND the first-visit skip in one expression.
     * - prev crosses subtree boundaries; that is how deep violators get caught with no
     *   ancestor bookkeeping.
     * - False travels the return chain; nothing else is needed for the early exit.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N) worst case (a valid tree must be fully verified, every node
     *    visited once). Invalid trees exit early: nothing after the first violation is
     *    visited, and the drain costs O(1) per ancestor.
     * -> Space Complexity: O(H) recursion stack (O(log N) balanced, O(N) skewed) plus O(1)
     *    for the field. No list this time, so O(H) is the honest total.
     * -> THE LADDER: list version O(N)/O(N); this version O(N)/O(H) with early exit;
     *    Morris inorder with the same prev check gives O(N)/O(1), no early exit (threads
     *    must unwind), same trade as 230's Morris rung.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Open with the sentence; the code is transcription.
     * - THE probe on this problem is the sentinel: "what do you initialize prev to?" Know
     *   why int sentinels are impossible here and name both escapes (null / wider type).
     * - Know the alternative solution: min/max bounds passed down the recursion, same
     *   complexity; be able to say both exist and that mixing the frames causes the
     *   "which side am I coming from" confusion.
     * - Verdicts combine by return; cross-call context rides a field. Say why each piece
     *   went where it did.
     */
    private static boolean isValidBSTBetter(TreeNode root) {
        prev = null;
        return inOrder(root);

    }

    private static boolean inOrder(TreeNode root) {
        if (root == null) return true;

        if (!inOrder(root.left)) return false;
        if (prev != null && root.val <= prev) return false;
        prev = root.val;
        return inOrder(root.right);
    }


    /*
     * WHAT THIS METHOD DOES:
     * BRUTE-FORCE BST validation: inorder-traverse the tree into a list, then scan the
     * list once checking that every element is STRICTLY greater than the one before it.
     * Sorted list means valid BST; any tie or drop means invalid.
     *
     * THE SENTENCE: a tree is a BST if and only if its inorder traversal is strictly
     * increasing.
     *
     * ---
     *
     * THE "INORDER MUST BE SORTED" PATTERN (VALIDATE BST, BRUTE FORCE)
     *
     * Your Thought Process & Intuition:
     * 1. THE RITUAL RAN FIRST THIS TIME, all three questions, before any code:
     *    Q1 (structure's guarantee): BST -> inorder is sorted. So validation flips the
     *    fact around: if the inorder ISN'T sorted, it wasn't a BST.
     *    Q2 (nearest neighbor): LC 230, same scaffolding exactly, shared-list inorder
     *    helper, only the post-processing loop changed (pick k-1 there, verify order here).
     *    Q3 (dumbest thing that works): store everything, scan once.
     *    Contrast with 230 itself, where skipping the ritual cost a queue-and-nested-loops
     *    detour. Same problem family, minutes instead of hours.
     *
     * 2. WHY THIS CATCHES THE SUBTREE-vs-CHILD TRAP AUTOMATICALLY: the classic wrong
     *    validator checks each parent against its immediate children and accepts trees
     *    like 5/(3,8) with 1 hiding under 8. Inorder doesn't care about local pairs; it
     *    linearizes the WHOLE tree, so a deep violator lands out of order in the sequence
     *    no matter how far from its ancestor it sits. The rule binds subtrees, and the
     *    inorder sequence IS the subtree rule made visible.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. STRICT comparison (fail on >=, not just >):
     *    - Why? The problem says strictly less / strictly greater. Duplicates are NOT a
     *      valid BST. The scan must reject equal neighbors: list.get(i-1) >= list.get(i)
     *      -> false.
     *
     * 2. Shared-list void helper (the sb pattern, third use):
     *    - Why? Same reasons as 230 and serialize: one collector created at the top,
     *      every call appends to it, no per-call lists dropped on the floor.
     *
     * 3. ArrayList, not LinkedList:
     *    - Why? The scan calls get(i) in a loop; LinkedList would make that O(N^2).
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - PROSE SAID >=, CODE SAID >: my own explanation stated "higher OR EQUAL means not
     *   valid," and the code I typed checked only >. Head correct, transcription dropped
     *   half the rule, the REVERSE of floor/ceil (where code filled a gap the head hadn't
     *   closed). Breaking case: root 2 with left child 2 -> inorder [2,2] -> 2 > 2 is
     *   false -> wrongly returns true. One character fix: >=.
     *   HOW TO CATCH: after writing, read the condition OUT LOUD against the problem's
     *   own wording. "Strictly" in the statement means equality must fail somewhere in
     *   my code; find the line that fails it, or the rule isn't implemented.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Inorder traverse into one shared ArrayList (left, add, right).
     * Step 2: Scan from index 1: if list[i-1] >= list[i], return false.
     * Step 3: Survived the scan -> return true.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N). One full traversal (O(N)) plus one scan (O(N)).
     *    No early exit: the whole tree is traversed even if the first two values
     *    already break order. That is the brute force's real waste.
     * -> Space Complexity: O(N) for the list, plus O(H) recursion stack. The list
     *    dominates: O(N). (State every allocation, then take the biggest.)
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Open with the sentence: valid BST iff inorder is strictly increasing.
     * - "Strictly" is load-bearing: duplicates invalid, so the check is >=, not >.
     * - The upgrade is the same list-to-single-variable move as 230: keep only prev,
     *   compare at the visit point, fail fast. O(H) space, early exit. Morris for O(1).
     * - The initialization of prev is the famous trap: values reach Integer.MIN_VALUE
     *   itself, so "start prev at MIN_VALUE" has a hole. (Solved in the optimal version.)
     */
    private static boolean isValidBSTBruteForce(TreeNode root) {
        if (root == null) return true;

        List<Integer> list = new ArrayList<>();

        inOrderHelper(root, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) >= list.get(i)) {
                return false;
            }
        }
        return true;
    }

    private static void inOrderHelper(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        inOrderHelper(root.left, list);
        list.add(root.val);
        inOrderHelper(root.right, list);
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
