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

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println(isValidBSTBruteForce(root));

        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(4);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(6);
        System.out.println(isValidBSTBruteForce(root1));


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
