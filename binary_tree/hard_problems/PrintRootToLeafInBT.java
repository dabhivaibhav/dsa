package binary_tree.hard_problems;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Print root to leaf path in BT Alternative problem present in leetcode and the problem number is 257.

Given the root of a binary tree. Return all the root-to-leaf paths in the binary tree.
A leaf node of a binary tree is the node which does not have a left and right child.

Example 1
Input : root = [1, 2, 3, null, 5, null, 4]
Output : [ [1, 2, 5] , [1, 3, 4] ]
Explanation : There are only two paths from root to leaf.
              From root 1 to 5 , 1 -> 2 -> 5.
              From root 1 to 4 , 1 -> 3 -> 4.

Example 2
Input : root = [1, 2, 3, 4, 5]
Output : [ [1, 2, 4] , [1, 2, 5] , [1, 3] ]

Constraints:
            1 <= Number of Nodes <= 3*10^3
            -10^3 <= Node.val <= 10^3
 */
public class PrintRootToLeafInBT {

    public static void main(String args[]) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println(allRootToLeaf(root));

    }

    /*
     * ============================================================================
     * LEETCODE 257: BINARY TREE PATHS (ALL ROOT-TO-LEAF PATHS)
     * ============================================================================
     *
     * WHAT THIS METHOD DOES:
     * This method finds and isolates every single complete straight-line path from
     * the absolute top root node down to every bottom leaf node in a binary tree.
     * The output is returned as a list of lists containing the integer sequence of
     * each individual branch route.
     *
     * ---
     *
     * THE "REUSABLE BACKPACK" PATTERN (ALL ROOT-TO-LEAF PATHS)
     *
     * Your Thought Process & Intuition:
     * 1. THE PERSPECTIVE: To find all possible paths from the root to every single leaf node,
     * we must completely explore every branch of the tree. Since we need to dive deep down
     * individual paths before coming back up, recursion (DFS) is the perfect engine.
     * We do not assume the left and right subtrees have the same depth or values; recursion
     * naturally maps out asymmetric shapes independently.
     *
     * 2. THE "EXPLORER'S BACKPACK" (THE LIST): As we travel down a path, we carry a live container
     * (currentPath) to track the exact breadcrumbs of how we got here. Every time we step onto
     * a node, we drop its value into our backpack (path.add(node.data)).
     *
     *
     *
     * 3. THE LEAF DESTINATION (BASE CASE): A leaf node is defined by having no children
     * (node.left == null && node.right == null). When we land on a leaf, our current path is
     * officially complete.
     * -> CRITICAL JAVA DETAIL: We must make a permanent snapshot copy of our backpack
     * (new ArrayList<>(path)) when adding it to our final results. If we don't,
     * future backtracking cleanups will accidentally modify our saved answers!
     *
     * 4. THE BACKTRACK (THE CLEANUP): When we finish exploring a node's left and right branches
     * and are about to return back up to its parent, we MUST take our breadcrumb back out of the
     * backpack (path.remove(path.size() - 1)). This ensures that the history of a left branch
     * never leaks into or corrupts the history of a right branch.
     *
     * ---
     *
     * THE DEEP TRAP: WHY WE MUST CREATE A COPY OF THE PATH
     * * In Java, objects like an ArrayList live in a shared memory space called the Heap. When you pass
     * path down through your recursion, you are passing a single reference (a memory pointer).
     * If you do not create a deep copy when adding to your results, you suffer from the "Shared
     * Blueprint" trap:
     *
     * If you simply write result.add(path);:
     * - You are adding a pointer to the live backpack into your results.
     * - As your recursion steps keep modifying the path (adding some numbers and losing some numbers via
     * backtracking), the values inside your previously saved results will constantly change in real-time!
     * - When the recursion fully winds down and cleans up after itself, your path list will be completely
     * emptied out ([]).
     * - Consequently, your final result list will look like a collection of empty boxes: [[], [], []].
     *
     * Executing new ArrayList<>(path) creates an isolated snapshot clone of that specific successful
     * trail in memory, shielding your saved answers from future backtracking adjustments.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. ArrayList<Integer> (The Shared Backpack Container):
     * - Why? Instead of creating a brand-new path list allocation at every single node, we
     * pass a single reference down through the recursive calls.
     * - Reason: Passing one shared list keeps memory consumption extremely low. Constant-time
     * appends (add()) and tail removals (removeLast()) mean we don't waste time copying
     * arrays mid-flight.
     *
     * 2. Copy Constructor new ArrayList<>(path) (The Snapshot Mechanism):
     * - Why? Java passes objects by reference value. We use this constructor to instantiate a deep
     * shallow-copy, locking down the contents of that specific successful path route permanently.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Guard against empty conditions. If root == null, instantly return the empty list.
     * Step 2: Initialize our master result list and a single dynamic tracking list (currentPath).
     * Step 3: Call our recursive explorer engine: explorePaths(root, currentPath, result).
     * Step 4: Inside the recursive worker loop, enforce the primary execution sequence:
     * - Safety First: If the pointer lands on a null spot, instantly return up.
     * - Add Node: Drop the current node's value into our shared path tracker.
     * - Check Leaf: Evaluate if node.left == null && node.right == null. If yes, clone the list
     * using new ArrayList<>(path) and save it inside result.
     * - Continue Exploration: If children exist, recursively branch down into the left child,
     * followed by the right child.
     * - The Backtrack: Right before exiting the functional frame, remove the final element from
     * the tail using path.removeLast().
     * Step 5: Once recursion unwinds completely, return the populated master collection.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * -> Time Complexity: O(N) — Every node is visited exactly once.
     * The traversal algorithm visits all N nodes precisely once. At each individual leaf node,
     * we perform a deep copy operation that copies the elements of the path. The length of a path
     * is bounded by the maximum depth H of the tree. If there are L leaf nodes, the copying phase
     * takes O(L * H) time. Because L * H <= N in all standard variations, the overall time
     * complexity scales linearly as O(N).
     *
     * -> Space Complexity: O(H) — The live path list only ever grows as large as the maximum height
     * of the tree before backtracking cleans it back up, making it extremely memory efficient.
     * The maximum number of stack frames stored together on the runtime call stack equals the height
     * H of the tree. Our shared currentPath backpack holds at most H values concurrently.
     * Thus, the auxiliary memory footprint remains highly optimized at O(H) (which balances to
     * O(log N) in a uniform tree and scales to O(N) in a fully skewed stick tree).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * The "Reusable Backpack" pattern is the golden standard template for backtracking and path-finding
     * challenges across trees, graphs, and combinatorics.
     * - Always pass a single reference to a collection down through your recursive calls instead of
     * instantiating massive collections at each level.
     * - Remember to create a snapshot clone (new ArrayList<>(path)) the moment your criteria hits a
     * terminal success point.
     * - Always mirror your actions: if you push a state onto your tracker when entering a choice, you
     * must pop that state back out right before exiting the function frame.
     */
    private static List<List<Integer>> allRootToLeaf(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        // Our path tracker list (the backpack)
        List<Integer> currentPath = new ArrayList<>();

        explorePaths(root, currentPath, result);
        return result;
    }

    private static void explorePaths(TreeNode node, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;

        // Drop the current node's value into our backpack
        path.add(node.val);

        // BASE CASE: If we hit a leaf node, snapshot our progress
        if (node.left == null && node.right == null) {
            // CRITICAL: We must make a NEW deep copy of the path list.
            // If we just add 'path', future backtracking steps will modify our results!
            result.add(new ArrayList<>(path));
        } else {
            // Otherwise, continue exploring deeper down both paths
            explorePaths(node.left, path, result);
            explorePaths(node.right, path, result);
        }

        // THE BACKTRACK: Pop the last element out on our way back up to the parent
        // This keeps our path history completely clean for the next branch!
        path.removeLast();
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
