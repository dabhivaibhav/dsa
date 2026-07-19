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

public class KthSmallestNode {

    private static int count = 0;
    private static int answer = -1;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println(kthSmallestBruteForce(root, 3));
        System.out.println(kthSmallestBetter(root, 3));
        System.out.println(kthSmallestMorris(root, 3));
    }


    /*
     * WHAT THIS METHOD DOES:
     * Finds the kth smallest value in a BST in O(1) AUXILIARY SPACE using Morris inorder
     * traversal with a counter. Same threading machinery as my Morris inorder; the only
     * change is what happens at the two record sites: instead of adding to a list, count
     * the visit and capture the value on the kth one. The loop always runs to completion,
     * no break, so every planted thread is removed and the tree is restored.
     *
     * THE SENTENCE: Morris inorder with the visit pair (count++, capture at k) at both
     * record sites, run to completion so the threads come down.
     *
     * ---
     *
     * THE "MORRIS WITH A COUNTER" PATTERN (KTH SMALLEST, O(1) SPACE)
     *
     * Your Thought Process & Intuition:
     * 1. THE TRANSFORMATION IS THE SAME ONE AS THE RECURSIVE UPGRADE: list -> counter.
     *    The record sites are fixed by the traversal (my own Morris preorder note: WHERE
     *    you record is the traversal, WHAT you do there is the problem). Morris inorder
     *    has exactly two record sites: case A (no left child) and case B2 (thread exists,
     *    left subtree done). The visit pair goes into BOTH, identically. B1 records
     *    nothing; it only plants the thread.
     *
     * 2. THE VISIT PAIR: count++; if (count == k) answer = current.val;
     *    No return, no break. The == k check is SELF-SEALING (once count passes k it can
     *    never equal k again), so the later visits during the rest of the traversal
     *    cannot overwrite the answer.
     *
     * 3. WHY THERE IS NO EARLY EXIT (the whole interview content of this problem):
     *    At the capture moment, the tree is CORRUPTED. The completed subtrees are
     *    restored, their threads came down at their own B2 moments, but every OPEN
     *    ANCESTOR (each node whose left subtree we are currently inside) still has a
     *    planted thread waiting: up to O(H) of them. Smallest counterexample: tree
     *    2 with left child 1, k=1. At capture (count=1 at node 1), 1.right is a thread
     *    pointing at 2. Break here and you hand back a corrupted tree.
     *    THE CONTINUATION IS THE CLEANUP: each open ancestor's B2 fires as the traversal
     *    rides the threads back up, removing them one by one. The rest of the loop after
     *    the capture is not waste; it is the restoration mechanism.
     *
     * 4. LOCAL VARIABLES, NO FIELDS: one loop, no recursion, so nothing must survive
     *    across calls. No reset dance, no reentrancy debt (unlike the recursive version's
     *    shared count/answer fields). The one way Morris is CLEANER than recursion here.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Run to completion, no break:
     *    - Why? Restoring the tree. Morris only BORROWS the null right-pointers; B2 puts
     *      every one back, but only if every B2 is reached. Breaking early skips the
     *      pending B2s and leaves threads planted. (My own Morris traps section: not
     *      removing the thread leaves the tree mutated/corrupted.)
     *
     * 2. The capture is a bare assignment, not a return:
     *    - Why? Nothing needs to escape. The answer sits in a local; the loop's natural
     *      end is the exit. The self-sealing check does the protecting.
     *
     * 3. Identical visit pair at both sites:
     *    - Why? Both sites ARE the inorder visit; a node is emitted at exactly one of
     *      them depending on whether it had a left subtree. Different actions at the two
     *      sites would mean two different traversals.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - CLAIMED THE TRAVERSED PART WAS ALREADY CLEAN: argued "the value is in the left
     *   part, I haven't touched the right subtree, so no pointers are set there." Right
     *   DECISION (no break), wrong REASON. The threads live exactly IN the traversed-
     *   so-far region, on the open-ancestor spine, the opposite of my claim. The 2/1
     *   tree kills it: at capture, node 1 (the left part) is the node carrying the
     *   thread. In an interview the follow-up attacks the reason, not the decision.
     * - DODGED THE BREAK QUESTION THREE MESSAGES RUNNING while asking for confirmation
     *   of the easy half (list -> counter). The transcription was never the problem;
     *   the exit decision was, and it was the part I kept routing around. The skim
     *   wears a polite mask: "just confirm this bit" while the hard blank stays blank.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: count = 0, answer = -1, current = root.
     * Step 2: While current != null:
     *   - If current.left == null: VISIT (count++, capture if count==k); go right.
     *   - Else: find predecessor (walk right from current.left, stopping at null or at
     *     current):
     *       - predecessor.right == null: plant thread (= current); go left. No visit.
     *       - predecessor.right == current: remove thread (= null); VISIT; go right.
     * Step 3: Loop ends naturally; return answer. Tree is fully restored.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - The visit pair appears TWICE, sites A and B2, never B1.
     * - No break anywhere: the post-capture iterations are the thread-removal pass.
     * - count == k is self-sealing; the drain's visits cannot touch the answer.
     * - The inner loop keeps both stop conditions (!= null && != current) exactly as in
     *   plain Morris; nothing about the counter changes the threading.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(N), ALWAYS. No early exit exists in this design: even if k=1,
     *    the loop must finish to unwind the threads. Each edge is traversed a constant
     *    number of times, so linear total.
     * -> Space Complexity: O(1) auxiliary. Two ints and two pointers. No stack, no list.
     *
     * THE LADDER (say it unprompted):
     *    1. List + get(k-1):        O(N) time,   O(N) space
     *    2. Recursive counter:      O(H+k) time, O(H) space  (early exit)
     *    3. Morris counter:         O(N) time,   O(1) space  (no early exit)
     *    Morris is NOT a strict win: it TRADES the early exit for the space. Naming that
     *    trade out loud is what shows you understood Morris rather than memorized it.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - The record sites are fixed by the traversal; the problem only changes what runs
     *   at them. List, counter, whatever, same two sites.
     * - Breaking out of Morris mid-run corrupts the tree: open ancestors hold planted
     *   threads at exactly the moment of capture. Run to completion; the continuation IS
     *   the cleanup. If you ever DO break early, you must say so and either unwind the
     *   threads manually or explicitly accept a mutated tree.
     * - Offer the three-rung ladder with the trade named. O(1) space costs the early exit.
     */
    private static int kthSmallestMorris(TreeNode root, int k) {
        int count = 0;
        int answer = -1;
        TreeNode current = root;

        while (current != null) {
            if (current.left == null) {
                count++;
                if (count == k) answer = current.val;
                current = current.right;
            } else {
                TreeNode temp = current.left;
                while (temp.right != null && temp.right != current) {
                    temp = temp.right;
                }
                if (temp.right == null) {
                    temp.right = current;
                    current = current.left;
                } else {
                    temp.right = null;
                    count++;
                    if (count == k) answer = current.val;
                    current = current.right;
                }
            }
        }
        return answer;
    }

    /*
     * WHAT THIS METHOD DOES:
     * Finds the kth smallest value in a BST by running an inorder traversal with a COUNTER
     * instead of a list. The kth node visited in inorder order IS the kth smallest, so we
     * count visits and capture the value on the kth one, then let the recursion drain
     * early. O(H + k) time, O(H) space, no list, no sorting.
     *
     * THE SENTENCE: inorder of a BST is sorted, so the kth inorder visit IS the kth smallest.
     *
     * ---
     *
     * THE "COUNT THE INORDER VISITS" PATTERN (KTH SMALLEST, OPTIMAL)
     *
     * Your Thought Process & Intuition:
     * 1. THE UPGRADE FROM BRUTE FORCE IS STORAGE, NOT ALGORITHM: the brute force stored
     *    every value and picked index k-1. This version stores NOTHING: same traversal,
     *    same visit order, but a counter replaces the list. The traversal was always the
     *    solution; the list was just scaffolding.
     *
     * 2. WHERE THE COUNT HAPPENS (the bug I wrote three times): the visit is BETWEEN the
     *    two recursive calls. Left subtree completely, THEN me, THEN right subtree
     *    completely, at every node. NOT "go to the bottom and count on the way back up",
     *    that model counts in the wrong order and only agrees with inorder for k=1 (the
     *    leftmost node happens to be both "the bottom" and the first visit). k=2 exposes
     *    it: after counting a node, the code walks into that node's RIGHT subtree before
     *    returning to the parent. A node's right child can be the very next count; the
     *    parent is not.
     *
     * 3. HOW A RECURSION "STOPS HALFWAY": it does not. There is no stop button. When the
     *    kth visit fires, a stack of unfinished ancestor calls still exists, each intending
     *    its own visit and right-subtree walk. The answer survives because of two things:
     *    - the ANSWER lives in a shared field, so it cannot be lost when calls unwind
     *    - the capture condition is count == k EXACTLY, which is SELF-SEALING: once count
     *      passes k it can never equal k again, so later visits cannot overwrite the answer
     *    The guard then makes the drain fast: every FRESH call bails on its first line.
     *
     * ---
     *
     * CORE DESIGN CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. Shared fields count and answer, reset in the public method:
     *    - Why shared? A local resets in every call (the countNodes lesson). Same family as
     *      postIndex in LC 106 and sb in serialize: state that must survive across calls
     *      lives in a field, and the public method resets it so a second run does not
     *      inherit the first run's values (sb.setLength(0), postIndex = length-1, same move,
     *      third appearance).
     *
     * 2. The helper is void:
     *    - Why? The answer travels through the FIELD, not through return values. The bail
     *      is a bare `return;`, not `return null` (that reflex belongs to tree-BUILDING
     *      helpers, where the return carries a subtree; here it carries nothing).
     *
     * 3. ONE guard (count >= k at the top), not two:
     *    - The pseudo-code had a second guard before the visit, to stop RESUMING parents
     *      from incrementing past k. I dropped it, and the drop is safe: correctness is
     *      protected by the self-sealing == k check, and speed is unchanged, each resuming
     *      ancestor does one increment, one failed comparison, and one child call that dies
     *      at guard 1. O(1) per ancestor either way. What guard 2 buys is only that count
     *      ends the run at exactly k instead of overshooting, which matters only if later
     *      code reads count. Nothing here does. One load-bearing guard beats two.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     * - FIRST ATTEMPT WAS A BOUNDED QUEUE WITH NESTED LOOPS AND SHAPE CASE-ANALYSIS:
     *   invented machinery because I skipped the ritual. Question 1 (what does the
     *   structure guarantee?) finishes this problem before code exists, and the fact was
     *   already written in my own LC 700 notes with this problem's number next to it. The
     *   library only pays if consulting it is STEP ONE.
     * - THE BOTTOM-THEN-BACK-UP MODEL, three times: postorder-instead-of-inorder in the
     *   brute force, "count on the way back" in the first counter plan, then predicting
     *   k=2 returns 3 when it returns 2. Same wrong model, three costumes. The k=2 hand
     *   trace is what killed it: node 2 (right child of node 1) is counted BEFORE the
     *   parent 3, because inorder enters the right subtree after each visit.
     * - CLAIMED O(H) SPACE WHILE THE CODE HELD AN O(N) LIST: stated the complexity of the
     *   version I had not written yet. Rule: to state space, list EVERY allocation
     *   (collections, stack, maps), then take the biggest. Never quote the stack alone
     *   while a data structure sits in the code.
     * - "RETURN NULL ON EACH CALL" for the bail-out: type confusion, the helper is void.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Reset count = 0, answer = -1. Call the helper. Return answer.
     * Step 2 (helper): if node is null, return. If count >= k, return (drain guard).
     * Step 3: Recurse left.
     * Step 4: THE VISIT: count++. If count == k, answer = node.val, return.
     * Step 5: Recurse right.
     *
     * ---
     *
     * STEP-BY-STEP "GOTCHA" EXPLANATION:
     * - The visit sits BETWEEN the recursive calls. Moving it is changing the traversal.
     * - count == k is self-sealing; the answer can be set exactly once.
     * - The guard makes fresh calls die instantly after the find; resuming ancestors cost
     *   O(1) each regardless.
     * - Fields need the reset in the public method or run two inherits run one's count.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time Complexity: O(H + k). Walking down to the smallest element costs O(H); then
     *    at most k visits happen before the answer is set; then the drain costs O(1) per
     *    ancestor on the stack, at most O(H). Worst case (k = n) this is O(N), same as any
     *    full inorder, but for small k it stops early.
     * -> Space Complexity: O(H), the recursion stack only. No list this time, so O(H) is
     *    finally the honest answer. (O(log N) balanced, O(N) skewed.)
     * -> THE O(1)-SPACE FLOURISH: swap this counter into Morris inorder (increment at both
     *    record sites, stop at k). Same time, O(1) auxiliary space. Worth one sentence in
     *    an interview; every piece of it is already in my Morris notes.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Open with the sentence: "inorder of a BST is sorted, so the kth inorder visit is
     *   the kth smallest." The sentence is the solution; the code is transcription.
     * - Know why the early exit is safe without a stop button: shared answer field plus a
     *   self-sealing == k check; the guard is a speed optimization, not correctness.
     * - Offer the ladder unprompted: O(N)-space list version, O(H)-space counter version,
     *   O(1)-space Morris version.
     * - The follow-up (frequent inserts/deletes, frequent kth queries) is answered by
     *   AUGMENTING the tree, storing something extra per node, so the query becomes an
     *   O(H) descent with no counting. Same count-by-structure idea as LC 222.
     */
    private static int kthSmallestBetter(TreeNode root, int k) {
        count = 0;
        answer = -1;
        inOrderHelper(root, k);
        return answer;
    }

    private static void inOrderHelper(TreeNode root, int k) {
        if (root == null) return;

        if (count >= k) return;

        inOrderHelper(root.left, k);

        count++;
        if (count == k) {
            answer = root.val;
            return;
        }
        inOrderHelper(root.right, k);
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
