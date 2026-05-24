package binary_tree.medium_problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Leetcode 103. Binary Tree Zigzag Level Order Traversal

Given the root of a binary tree, return the zigzag level order traversal of its nodes' values.
(i.e., from left to right, then right to left for the next level and alternate between).

Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[20,9],[15,7]]

Example 2:
Input: root = [1]
Output: [[1]]

Example 3:
Input: root = []
Output: []

Constraints:
            The number of nodes in the tree is in the range [0, 2000].
            -100 <= Node.val <= 100
 */
public class ZigZagLevelOrderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        root.left.left.right = new TreeNode(9);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(11);
        root.right.left.left = new TreeNode(12);
        root.right.left.right = new TreeNode(13);
        root.right.right.left = new TreeNode(14);
        System.out.println(zigzagLevelOrder(root));
    }

    /*
     * WHAT THIS METHOD DOES:
     * This method visits every node in a Binary Tree level by level (Breadth-First Search),
     * but with a dramatic architectural twist: the horizontal ordering alternates on
     * every single floor. The first level is read left-to-right, the second level is
     * read right-to-left, the third level left-to-right, and so on, creating a
     * "serpentine" or "zigzag" sequence of inner lists.
     *
     * MENTAL MODEL: THE COURIER ON A ZIGZAG STAIRCASE
     * Imagine you are a delivery courier dropping off packages floor-by-floor in a
     * highly unusual apartment complex.
     * * 1. On Floor 0 (the top), you walk from Left to Right down the hallway, dropping
     * off packages.
     * 2. You take the stairs down to Floor 1. Instead of starting at the left side
     * again, you turn around and walk completely backward from Right to Left.
     * 3. On Floor 2, you switch your direction back to a standard Left-to-Right walk.
     * * To keep your logistics clean, you maintain a single warehouse conveyor belt (the Queue)
     * where tenants' children are always lined up uniformly from left to right. However,
     * when you load the apartment data into your delivery logbook for each individual floor,
     * you use a toggle switch (leftToRight). If the switch is off, instead of writing lines
     * at the bottom of your log notebook page, you aggressively stack new records at the
     * top of the page (addFirst), forcing the written sequence to read completely backward!
     *
     * CORE IDEA: BFS WITH CONDITIONAL INSERTION EFFICIENCY
     * This problem is a direct descendant of standard Level Order Traversal. We
     * keep the identical engine structural pattern: a standard FIFO Queue where children
     * are consistently pushed left-to-right.
     *
     *
     *
     * Rather than messing with the underlying Queue insertion mechanics (which can easily
     * introduce structural bugs), we isolate the zigzag effect exclusively to *how we insert
     * elements into the current level's collection*.
     * By using a LinkedList as our temporary row container, we can utilize addFirst() to
     * inject values at index 0 in O(1) constant time, effortlessly flipping the row sequence on
     * alternate levels.
     *
     * STEP-BY-STEP LOGIC:
     *
     * 1. THE WRAPPER & GUARDRAIL:
     * - Initialize our result list. If the root is null, immediately exit to
     * prevent empty tree exceptions.
     *
     * 2. THE CONVEYOR BELT SETUP:
     * - Initialize a standard FIFO Queue and load the root node onto it.
     *
     * 3. THE TOGGLE SWITCH (leftToRight):
     * - Create a boolean flag initialized to true. This flag acts as a direction indicator
     * that flips every time we cross a floor boundary.
     *
     * 4. THE LEVEL SNAPSHOT (levelSize = queue.size()):
     * - At the start of the level loop, we capture a static snapshot of how many nodes
     * are currently sitting in the queue. This count completely locks the boundaries of the
     * current floor, ignoring any new children being appended to the back of the queue.
     *
     * 5. THE CONDITION INJECTION (The Zigzag Twist):
     * - We pop a node out of the front of the queue using poll().
     * - If leftToRight == true: Append the value to the normal end of the line (add).
     * - If leftToRight == false: Inject the value at the absolute front of the line (addFirst).
     * This creates a mirror-reversal stacking effect.
     *
     * 6. UNIFORM EXPANSION:
     * - Regardless of our reading direction, we *always* add the left child first, then the
     * right child into the queue. Keeping the queue's internal structure consistent avoids
     * index confusion.
     *
     * 7. THE TOGGLE FLIP (leftToRight = !leftToRight):
     * - Once the inner loop processes exactly levelSize elements, the row container is added
     * to the master list, and the boolean flag is inverted to swap directions for the next level.
     *
     * CONNECTION TO WHAT YOU KNOW:
     * This algorithm shares the exact blueprint of Level Order Traversal. Look at the skeleton:
     * while(!queue.isEmpty()) {
     * int size = queue.size();
     * for(int i = 0; i < size; i++) {
     * TreeNode node = queue.poll();
     * ...
     * if(node.left != null) queue.add(node.left);
     * }
     * }
     *
     * You are not learning a new tree traversal framework. You took the exact BFS structure you
     * already mastered and simply applied an alternate data insertion rule inside the loop body,
     * using a boolean toggle switch to select between normal insertion and front insertion.
     *
     * COMPLEXITY ANALYSIS:
     * Time Complexity: O(n)
     * Every node in the binary tree is added to and removed from the queue exactly once.
     * Because we use a LinkedList for our temporary row storage, both add() (append)
     * and addFirst() (prepend) run in true O(1) constant time, ensuring the overall
     * time complexity remains strictly linear.
     *
     * Space Complexity: O(w), where w is the maximum width of the tree
     * The maximum memory allocation corresponds to the largest single level in the tree structure.
     * In a fully populated, complete binary tree, the lowest leaf level contains roughly n/2 nodes,
     * which scales directly to O(n) space in the worst case.
     *
     * INTERVIEW TAKEAWAY:
     * Avoid over-complicating tree problems by changing how you traverse data when you can simply
     * change how you *store* the data. Manipulating the queue's insertion order based on row depth
     * often leads to messy, bug-prone edge cases. By keeping a stable Left-to-Right BFS engine and
     * delegating the reversing mechanism to a highly efficient LinkedList.addFirst() call, you keep
     * your logic simple and performant.
     */
    private static List<List<Integer>> zigzagLevelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>();

        // Edge Case Safety Net: Always handle an empty tree first
        if (root == null) {
            return result;
        }

        // Setting up our FIFO conveyor belt (Queue)
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root); // Load the first node onto the belt

        // THE TWIST VARIABLE (Specific to Zigzag)
        // This acts as a toggle switch.
        // True = Pack items left-to-right. False = Pack items right-to-left.
        boolean leftToRight = true;

        // THE ENGINE ROOM (The Nested Loops)
        while (!queue.isEmpty()) {

            // THE SNAPSHOT: Count exactly how many nodes are on the belt RIGHT NOW.
            // This represents the total count of the current level.
            int levelSize = queue.size();

            // THE CONTAINER: We use a LinkedList because it allows us to do
            // addFirst() instantly (O(1) time) without shifting elements in memory.
            LinkedList<Integer> currentLevelList = new LinkedList<>();

            // THE ISOLATOR: This loop runs EXACTLY 'levelSize' times.
            // It completely ignores any new children being added to the back of the queue.
            for (int i = 0; i < levelSize; i++) {

                // Pull the oldest node off the front of the queue
                TreeNode currentNode = queue.poll();

                // THE TWIST LOGIC: Deciding how to record the data
                if (leftToRight) {
                    // Normal behavior: Append to the back of the list
                    currentLevelList.add(currentNode.val);
                } else {
                    // Zigzag behavior: Inject at the very front of the list
                    // This creates a stacking effect that reverses the row order
                    currentLevelList.addFirst(currentNode.val);
                }

                // THE SKELETON EXPANSION: Loading the next generation
                // Always add children left-to-right so the queue order stays clean.
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }

            // Add the packed row to our final master list
            result.add(currentLevelList);

            // THE TWIST FLIP: Prepare for the next level
            // If it was true, it becomes false. If it was false, it becomes true.
            leftToRight = !leftToRight;
        }

        return result;
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
