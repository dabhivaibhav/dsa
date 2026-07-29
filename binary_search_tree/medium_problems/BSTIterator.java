package binary_search_tree.medium_problems;

import java.util.ArrayDeque;
import java.util.Deque;

/*
Leetcode 173. Binary Search Tree Iterator

Implement the BSTIterator class that represents an iterator over the
in-order traversal of a binary search tree (BST):

BSTIterator(TreeNode root) Initializes an object of the BSTIterator class.
The root of the BST is given as part of the constructor. The pointer should
be initialized to a non-existent number smaller than any element in the BST.
boolean hasNext() Returns true if there exists a number in the traversal to
the right of the pointer, otherwise returns false. int next() Moves the pointer
to the right, then returns the number at the pointer.
Notice that by initializing the pointer to a non-existent smallest number,
the first call to next() will return the smallest element in the BST.

You may assume that next() calls will always be valid. That is, there will be
at least a next number in the in-order traversal when next() is called.

Example 1:
Input
["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
[[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
Output
[null, 3, 7, true, 9, true, 15, true, 20, false]

Explanation
BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
bSTIterator.next();    // return 3
bSTIterator.next();    // return 7
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 9
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 15
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 20
bSTIterator.hasNext(); // return False


Constraints:
            The number of nodes in the tree is in the range [1, 10^5].
            0 <= Node.val <= 10^6
            At most 105 calls will be made to hasNext, and next.

Follow up:
Could you implement next() and hasNext() to run in average O(1) time
and use O(h) memory, where h is the height of the tree?
 */
public class BSTIterator {



    /*
     * WHAT THIS CLASS DOES:
     * An iterator that yields BST values in inorder (sorted) order, one at a time, on
     * demand. Instead of doing the full traversal upfront and storing all N values, it
     * "pauses" the inorder walk between calls using an explicit stack. Each next() resumes
     * the walk, emits one value, and re-pauses. O(H) memory at any moment, O(1) AMORTIZED
     * time per next(), O(1) worst-case for hasNext().
     *
     * THE SENTENCE: it is an iterative inorder traversal with the stack held open between
     * calls, so the caller controls when the next step happens.
     *
     * ---
     *
     * THE "PAUSED ITERATIVE INORDER" PATTERN (BST ITERATOR)
     *
     * Your Thought Process & Intuition:
     * 1. THE CONNECTION TO WHAT I ALREADY OWN: I have written iterative inorder with a
     *    stack: push all lefts, pop, visit, push-all-lefts of the right child, repeat. That
     *    whole thing runs inside a single while loop. This iterator is that SAME loop, cut
     *    open: instead of looping, each next() call does ONE iteration of that loop and then
     *    stops. The stack persists between calls because it is a FIELD, not a local. That
     *    persistence is the entire trick: the stack IS the paused state of the traversal.
     *
     * 2. WHY pushAllLeft EXISTS (the helper that makes both the constructor and next() clean):
     *    In inorder, before you can visit any node, you must process its entire left spine
     *    first. "Process the left spine" = push the node and every left descendant onto the
     *    stack. This happens in TWO places: at startup (push from root all the way down-left
     *    to seed the first next()), and inside next() after popping (if the popped node has
     *    a right child, that child's left spine must be loaded before the NEXT next() call).
     *    Same operation, two call sites, hence the helper.
     *
     * 3. THE STACK'S INVARIANT (say this in an interview): at any moment, the stack holds
     *    exactly the ANCESTORS whose left subtrees are fully consumed but who themselves
     *    have NOT yet been visited. The top of the stack is always the next node to emit.
     *    That is why hasNext() is just "is the stack empty": if ancestors remain, values
     *    remain.
     *
     * 4. HOW next() WORKS, ONE SENTENCE: pop the top (that is the next inorder value),
     *    then if it has a right child, push that child's entire left spine (loading up the
     *    next segment of the traversal), and return the popped value.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Stack as a field, not a local:
     *    - Why? The traversal's state must survive between next() calls. A local stack dies
     *      at the end of each call; a field keeps the walk paused between calls. Same
     *      principle as postIndex/prev/count in my other solutions: cross-call state lives
     *      in a field. The difference is that here the field is a COLLECTION (the stack),
     *      not a single int, because the paused state of a tree walk is a path, not a number.
     *
     * 2. pushAllLeft in the constructor:
     *    - Why? The first next() must return the SMALLEST value (leftmost node). To get
     *      there, the entire left spine from the root must already be on the stack before
     *      next() is ever called. The constructor does this setup.
     *
     * 3. pushAllLeft(curr.right) inside next():
     *    - Why? After visiting a node, its inorder successor is either (a) the leftmost node
     *      of its right subtree, or (b) an ancestor already on the stack. Case (a) requires
     *      loading that right subtree's left spine. Case (b) requires nothing, that ancestor
     *      is already sitting on the stack from an earlier pushAllLeft. So the if-guard
     *      (curr.right != null) handles (a), and the absence of any action handles (b).
     *      This is the same "successor = leftmost of right subtree" from my delete problem,
     *      just applied incrementally.
     *
     * 4. Deque<TreeNode> not Stack<TreeNode>:
     *    - Why? Java's Stack class extends Vector, which is synchronized (thread-safe locks
     *      on every operation) and therefore slower than needed for single-threaded code.
     *      ArrayDeque is the modern replacement: same push/pop semantics, no synchronization
     *      overhead. Worth one sentence in an interview if asked about the choice.
     *
     * ---
     *
     * HOW THIS CONNECTS TO MY OTHER SOLUTIONS:
     * - The pushAllLeft loop IS the "go all the way left" spine walk from Morris, from
     *   floor/ceil, from BST search, the same descend-left-as-far-as-possible move, here
     *   done with a stack recording the path instead of a single pointer forgetting it.
     * - The successor logic (after popping a node, process its right child's left spine)
     *   is my delete problem's "successor = leftmost of right subtree," run lazily.
     * - The stack holding ancestors-not-yet-visited is what Morris ELIMINATES by threading:
     *   Morris stores the same "where to go back" information in the tree's own null
     *   pointers instead of an external stack. An interviewer who asks "can you do this in
     *   O(1) space?" is asking for a Morris-based iterator. Worth knowing that connection.
     *
     * ---
     *
     * TRAPS TO KNOW (this solution was learned from a video, not built from bugs):
     * - STATIC FIELDS ON LEETCODE: the stack and methods are static here but LeetCode
     *   instantiates multiple BSTIterator objects in one run. Static state is shared across
     *   instances, so a second iterator would corrupt the first. Remove the statics: make
     *   stack an instance field, methods non-static. (Same reentrancy debt as postIndex and
     *   prev, at object scope instead of method scope.)
     * - NULL CHECK BEFORE pushAllLeft: not needed inside pushAllLeft itself (the while
     *   condition handles null), but the curr.right != null guard in next() is an
     *   optimization, not a correctness need: pushAllLeft(null) would simply do nothing.
     *   Keeping it is fine; knowing it is redundant shows understanding.
     * - THE "O(1) AMORTIZED" CLAIM NEEDS JUSTIFICATION: a single next() call can push up
     *   to O(H) nodes (when it hits a right child with a deep left spine). That looks like
     *   O(H) per call, not O(1). The amortized argument: every node is pushed EXACTLY ONCE
     *   and popped EXACTLY ONCE across the entire lifetime of the iterator. N pushes and N
     *   pops over N next() calls = 2N total operations / N calls = O(1) amortized per call.
     *   An interviewer will probe this; memorizing "O(1) amortized" without the push/pop
     *   counting argument is the video-learning trap.
     * - THE BRUTE-FORCE ALTERNATIVE: flatten the entire tree into a list in the constructor
     *   (full inorder traversal), keep an index, next() returns list[index++], hasNext()
     *   checks index < size. O(N) space, O(1) worst-case per call, O(N) constructor. Simpler,
     *   but fails the follow-up's O(H) memory requirement. Knowing both and stating the
     *   tradeoff is stronger than knowing only the stack version.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * CONSTRUCTOR:
     *   Step 1: Create an empty stack.
     *   Step 2: pushAllLeft(root): push root and every left descendant onto the stack.
     *           The stack now holds the path from root to the leftmost (smallest) node.
     *
     * next():
     *   Step 1: Pop the top node (this is the next inorder value).
     *   Step 2: If it has a right child, pushAllLeft(right child): load the next segment.
     *   Step 3: Return the popped node's value.
     *
     * hasNext():
     *   Step 1: Return !stack.isEmpty(). Ancestors remain = values remain.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> next() Time: O(1) AMORTIZED. A single call may push up to O(H) nodes, but across
     *    all N calls, each node is pushed once and popped once: 2N total stack operations
     *    over N calls. Worst-case per call is O(H), but amortized is O(1).
     * -> hasNext() Time: O(1) worst-case. One stack-empty check.
     * -> Space: O(H) at any moment. The stack holds at most one root-to-leaf path (the left
     *    spine of whatever subtree is currently being explored). This is the follow-up's
     *    requirement: O(H) memory, not O(N). (O(log N) balanced, O(N) skewed.)
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - It is an iterative inorder traversal with the stack held open between calls. That
     *   one sentence is the whole design, and saying it first shows you understand the
     *   WHY, not just the code.
     * - The stack's invariant: ancestors whose left subtrees are consumed but who are not
     *   yet visited. Top = next value. Empty = done.
     * - Know the amortized argument: N pushes + N pops over N calls = O(1) per call.
     *   The interviewer WILL ask "but pushAllLeft is O(H), how is next() O(1)?"
     * - The brute-force alternative (flatten to list) is O(N) space, O(1) worst-case per
     *   call. State the tradeoff: stack version trades worst-case O(H) per call for O(H)
     *   memory instead of O(N).
     * - The O(1)-space version is a Morris-based iterator: threading replaces the stack.
     *   Worth mentioning as the third rung of the ladder.
     * - Remove the statics before submitting on LeetCode.
     */
    private static Deque<TreeNode> stack;

    public BSTIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        pushAllLeft(root);
    }

    public static int next() {
        TreeNode curr = stack.pop();
        if (curr.right != null) {
            pushAllLeft(curr.right);
        }
        return curr.val;
    }

    public static boolean hasNext() {
        return !stack.isEmpty();
    }

    private static void pushAllLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
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
