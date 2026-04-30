package binary_tree.medium_problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Leetcode: 102. Binary Tree Level Order Traversal

Given the root of a binary tree, return the level order traversal
of its nodes' values. (i.e., from left to right, level by level).

Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]

Example 2:
Input: root = [1]
Output: [[1]]

Example 3:
Input: root = []
Output: []

Constraints:
            The number of nodes in the tree is in the range [0, 2000].
            -1000 <= Node.val <= 1000.
 */
public class LevelOrderTraversal {

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
        root.right.right.right = new TreeNode(10);
        root.right.right.left = new TreeNode(11);
        root.right.right.right = new TreeNode(12);
        System.out.println(levelOrder(root));
    }


    /*
    BINARY TREE LEVEL ORDER TRAVERSAL BREAKDOWN

    WHAT THIS METHOD DOES:
    This method explores a Binary Tree "breadth-first." Instead of diving deep
    into one branch (DFS), it visits all nodes at the current depth before
    moving down to the next level. The output is a list of lists, where each
    inner list represents a single level of the tree from left to right.

    MENTAL MODEL: THE OFFICE HIERARCHY
    Imagine a corporate office. You want to talk to the employees level by level.
    First, you talk to the CEO (Level 0). Then, you talk to all the VPs
    (Level 1). Only after meeting every VP do you move down to the Managers
    (Level 2). To keep track of who to talk to next, you maintain a
    "Waiting List" (Queue). When you finish talking to someone, you add their
    direct reports to the end of that list.

    CORE IDEA: THE BREADTH-FIRST SEARCH (BFS)
    The engine of this algorithm is a Queue. Because a Queue is First-In,
    First-Out (FIFO), it naturally preserves the horizontal order of the nodes.

    STEP-BY-STEP LOGIC:

        GUARDRAIL: If the root is null, return an empty list.

        INITIALIZE: Create a Queue and add the root to start the process.

        OUTER LOOP (Level by Level): While the queue is not empty:
        Determine the size of the queue. This is the crucial step! It tells
        you exactly how many nodes are on the *current* level.
        Create a temporary list to store values for this level.

        INNER LOOP (Process current level): Loop exactly size times:
        Poll (remove) a node from the queue.
        Add its value to the level list.
        If it has a LEFT child, add it to the queue.
        If it has a RIGHT child, add it to the queue.

        WRAP UP: Once the inner loop finishes, the level is complete. Add the
        level list to the final result.

        WHY THE "SIZE" VARIABLE IS IMPORTANT:
        Without saving the size at the start of the level, the loop wouldn't
        know where one level ends and the next begins, because we are constantly
        adding new children to the same queue. size acts as a snapshot of the
        current floor.

    EXAMPLE DRY RUN:

    Tree: [3, 9, 20, null, null, 15, 7]
    Queue: [3]. Size = 1.
    Process 3. Queue kids: [9, 20]. Level List: [3].
    Queue: [9, 20]. Size = 2.
    Process 9. Queue kids: [].
    Process 20. Queue kids: [15, 7]. Level List: [9, 20].
    Queue: [15, 7]. Size = 2.
    Process 15.
    Process 7. Level List: [15, 7].
    Final Result: [[3], [9, 20], [15, 7]]

    COMPLEXITY ANALYSIS:
        Time Complexity: O(n). We need to consider all the possible TC and SC.
        Every node is added to and removed from the queue exactly once.

        Space Complexity: O(w), where w is the maximum width of the tree.
        We need to consider all the possible TC and SC.

    In a perfect binary tree, the last level contains approximately n/2 nodes,
    so the queue can grow to O(n) in the worst case.

    INTERVIEW TAKEAWAY:
    Whenever you see the words "Level by Level" or "Shortest Path" in a tree
    or graph, your brain should immediately think Queue and BFS.
    The int size = queue.size() snapshot is the signature pattern for
    separating levels in a BFS.
    */
    private static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();

        if (root == null) {
            return result;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(list);
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
