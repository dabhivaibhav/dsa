package binary_tree.hard_problems;

/*
Problem: Requirements needed to construct a unique BT

Given a pair of tree traversal, return true if a unique binary
tree can be constructed otherwise false. Each traversal is
represented with integer: 1 -> Preorder , 2 -> Inorder , 3 -> Postorder.

Example 1
Input : 1 2
Output : true
Explanation : Answer is True.
It is possible to construct a unique binary tree. This is because the
preorder traversal provides the root of the tree, and the inorder
traversal helps determine the left and right subtrees.

Example 2
Input : 2 2
Output : false
Explanation : Two inorder traversals are insufficient to uniquely determine a binary tree.

Constraints:
            1 <= a, b <= 3
 */
public class ConstructUniqueBinaryTree {

    public static void main(String[] args) {

        int a = 1;
        int b = 2;
        System.out.println(uniqueBinaryTree(a, b));
        a = 3;
        b = 2;
        System.out.println(uniqueBinaryTree(a, b));
        a = 2;
        b = 2;
        System.out.println(uniqueBinaryTree(a, b));
        a = 1;
        b = 3;
        System.out.println(uniqueBinaryTree(a, b));
    }

    /*
     * WHAT THIS METHOD DOES:
     * Given which TWO traversals you have been handed (1=preorder, 2=inorder,
     * 3=postorder), returns true if those two are enough to reconstruct EXACTLY ONE
     * binary tree, and false if more than one different tree could have produced them.
     * The whole problem is a single boolean check on two integers. There is no tree
     * built, no traversal run, no data structure. The difficulty is entirely in
     * understanding the QUESTION, not in the code.
     *
     * ---
     *
     * THE "WHICH TRAVERSALS PIN DOWN A TREE" PATTERN (UNIQUE TREE CONSTRUCTION)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE OBJECTIVE: "Can a unique binary tree be constructed" does NOT mean
     *    "can I build some different-looking tree from these numbers." It means: given
     *    these traversal sequences, is there EXACTLY ONE tree that produces them, or
     *    could many different trees produce the same output? Unique = only one tree
     *    fits. Not unique = the information is ambiguous, several trees fit, so you
     *    cannot tell which was the original.
     *
     * 2. THE INFORMATION EACH TRAVERSAL CARRIES (the engine of the whole problem):
     *    -> PREORDER visits root FIRST, so its first element is always the root.
     *       Its power: it finds the root.
     *    -> POSTORDER visits root LAST, so its last element is always the root.
     *       Its power: it also finds the root.
     *    -> INORDER visits left, root, right. Once you KNOW the root, everything left
     *       of it is the left subtree and everything right of it is the right subtree.
     *       Its power: it SPLITS left from right. But only if you already know the root.
     *    -> KEY: inorder is the ONLY traversal where left-stuff and right-stuff land on
     *       physically different sides of the root. Preorder and postorder both bury
     *       the root at an end, where it cannot act as a divider. So inorder is the
     *       ONLY traversal that can separate left subtree from right subtree.
     *
     * 3. THE RULE: To rebuild a tree with no ambiguity you need BOTH abilities at once:
     *    (a) find the root  -> need at least one of {preorder, postorder}
     *    (b) split L from R -> need inorder (it is the only splitter)
     *    Given you only have two slots, "has a root-finder AND has inorder" compresses
     *    to: EXACTLY ONE of the two is inorder. One inorder to split, one non-inorder
     *    (which must then be preorder or postorder) to find the root. Not zero, not two.
     *
     * ---
     *
     * WHY THERE IS NO DATA STRUCTURE HERE:
     * This problem looks like a tree problem but never touches a tree. It is a pure
     * logic check on what INFORMATION two traversals provide. The trap is doing real
     * reconstruction work; the insight is that you only need to ask "is the right
     * information present," which is a constant-time question.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE (the real lesson of this problem, reread these):
     * - MISREAD "UNIQUE": first thought it meant building a different-looking tree from
     *   the same numbers. It actually means "is exactly one tree consistent with this
     *   traversal output." Got the definition backwards before getting it right.
     * - THOUGHT PREORDER + POSTORDER WAS TRUE: reasoned "preorder finds the root, so we
     *   can build it." WRONG. Finding the root is NOT the same as being able to split
     *   left from right. Two root-finders and no splitter cannot tell a left-only child
     *   from a right-only child. Counterexample: tree (1 with 2 on left) and tree
     *   (1 with 2 on right) have the SAME preorder [1,2] and SAME postorder [2,1].
     *   Identical output, different trees -> ambiguous -> FALSE.
     * - SAID "AT LEAST ONE 2" INSTEAD OF "EXACTLY ONE 2": "at least one" wrongly passes
     *   the 2,2 case (two inorders), which the problem says is false. The fix is the
     *   word "exactly": one is 2 AND the other is not 2.
     * - WORRIED ORDER MATTERED (1 2 vs 2 1): it does NOT. The two integers are not a
     *   sequence of steps ("do preorder, then inorder"). They are an unordered SET of
     *   which traversals you possess. Reconstruction consults them in whatever order it
     *   needs, so the root-finder is always available before you split. The condition
     *   is symmetric in a and b, so listing order is irrelevant.
     *
     * - THE META-MISTAKE (the pattern across all of these): I kept stating the FIRST
     *   necessary condition and stopping, when a SECOND was clamped to it. "Find the
     *   root" (forgot: also split). "At least one 2" (forgot: and not a second 2). The
     *   fix is a habit, not knowledge: once you reach a rule, immediately try to BREAK
     *   it on the smallest example before trusting it. Every one of these holes showed
     *   itself the instant I tested against a concrete case.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Check whether exactly one of the two values equals 2 (inorder).
     * Step 2: That is: (a is 2 and b is not 2) OR (b is 2 and a is not 2).
     * Step 3: Return that boolean. Done.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - return (a == 2 && b != 2) || (b == 2 && a != 2);
     *     "Exactly one is inorder." First clause catches a being the lone 2, second
     *     catches b being the lone 2. Both fail when there are zero 2s (no root-split
     *     possible the right way) and when there are two 2s (no root-finder present).
     * - The expression is SYMMETRIC: swapping a and b changes nothing, which is exactly
     *   why input order does not matter.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(1). It is a fixed boolean expression on two integers. No
     *    loop, no recursion, no traversal. The work does not grow with anything.
     * -> Space Complexity: O(1). No data structures, no call stack growth. A couple of
     *    comparisons and nothing stored.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * The code is trivial; the entire test is whether you understand what the question
     * is asking. The reusable facts:
     * - Preorder's first element and postorder's last element are the root.
     * - Inorder is the ONLY traversal that splits left subtree from right subtree.
     * - A pair reconstructs a unique tree IFF exactly one of them is inorder.
     * - Two root-finders (preorder + postorder) cannot disambiguate a single child's
     *   side, so that pair is NOT unique.
     * - "Which traversals do I have" is a SET question; listing order is irrelevant.
     * And the personal note: the failure here was reading comprehension, not algorithm
     * skill. Misunderstanding the question is its own interview failure mode. Slow down
     * and pin the definition before touching code.
     */
    private static boolean uniqueBinaryTree(int a, int b) {
        if ((a == 2 && b != 2) || (b == 2 && a != 2))
            return true;
        else return false;
    }
}
