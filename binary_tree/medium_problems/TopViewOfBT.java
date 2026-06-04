package binary_tree.medium_problems;

import java.util.*;

/*
Problem: Top View of BT

Given the root of a binary tree, return the top view of the binary tree.
The top view of a binary tree consists of the set of nodes visible when the tree is observed from above.
Return the values of these nodes ordered from the leftmost to the rightmost position.
If multiple nodes share the same horizontal distance from the root, only the node that appears first when
traversing from left to right (i.e., the leftmost node) should be included in the result.


Example 1
Input : root = [1, 2, 3, 4, 5, 6, 7]
Output : [4, 2, 1, 3, 7]

Example 2
Input : root = [10, 20, 30, 40, 60, 90, 100]
Output : [40, 20, 10, 30, 100]

Constraints:
            1 <= Number of Nodes <= 10^4
            -10^3 <= Node.val <= 10^3
 */
public class TopViewOfBT {

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
        System.out.println(topView(root));
    }


    /*
     * WHAT THIS METHOD DOES:
     * This method finds the nodes that are visible when looking at a Binary Tree
     * straight down from the absolute top. It returns these values in a sequence
     * flowing from the leftmost vertical column to the rightmost vertical column.
     *
     * ---
     *
     * MENTAL MODEL & REAL-LIFE ANALOGY: THE SKYLINE DRONE CAM
     * Imagine a long street where houses (nodes) are built next to and behind each
     * other. The main intersection (the Root) is marked as Column 0.
     * * 1. Building Coordinates:
     * - If you build a room down and out to the left, it shifts a column left (col - 1).
     * - If you build a room down and out to the right, it shifts a column right (col + 1).
     *
     * 2. The Blocking Effect:
     * - A drone is flying high up in the sky, looking straight down onto the street.
     * - On any single vertical block (column), the drone can ONLY see the very roof of
     * the building that was built first (the highest one up).
     * - Any other nodes built lower down on that exact same column are completely
     * hidden underneath the shade of that first node.
     *
     * To record this view, we use a Breadth-First Search (BFS) conveyor belt to move
     * top-to-bottom. The moment a column is claimed by a node, it's permanently locked.
     * Lower nodes on that column are ignored.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. TreeMap<Integer, Integer> (The Top View Map):
     * - Why Key? The keys are the vertical column numbers (e.g., -2, -1, 0, 1, 2).
     * - Why Value? The value is the integer ID of the first node to claim that column.
     * - Reason: A TreeMap keeps its keys automatically sorted in ascending order.
     * When we extract the values at the end, they will perfectly align from left to right.
     *
     * 2. Queue<Pair> (The Level-Order Conveyor Belt):
     * - Why? We *must* use a Breadth-First Search (BFS) pattern here.
     * - Reason: BFS explores the tree level-by-level (top-to-bottom). This guarantees
     * that the higher-up nodes on any column hit our loop *before* any deeper nodes.
     * If we used Depth-First Search (DFS), we might hit a deep leaf on a column before
     * discovering its higher parent, which complicates the logic.
     *
     * 3. Pair Class (The Shipping Label):
     * - Why? A standard Queue can only hold one type of object.
     * - Reason: We need to bind each TreeNode to its specific vertical column integer
     * so it knows exactly where it sits on the horizon as it passes through the loop.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Guard against edge cases. If the tree is empty, return an empty list.
     * Step 2: Set up a TreeMap to track who owns each column, and a Queue packed
     * with the root node tagged at column 0.
     * Step 3: Run the level-order BFS loop. For each element popped:
     * - Check if the current column already exists in the TreeMap.
     * - If it is missing (!containsKey), this node has successfully claimed the column! Put it in.
     * - If it already exists, do nothing. It is blocked by a higher node.
     * - Push the left child into the queue with a column index of col - 1.
     * - Push the right child into the queue with a column index of col + 1.
     * Step 4: After exploring the entire tree, loop through the TreeMap.values().
     * Because the keys are auto-sorted, your results are naturally packed left-to-right.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - if (!topViewMap.containsKey(col)) { topViewMap.put(col, node.val); }
     * This is the ultimate filter step. Since we travel top-down, the first node to land on
     * a column coordinate has the absolute highest altitude. Once it fills that slot, no other
     * node can overwrite it.
     * * - queue.add(new Pair(node.left, col - 1));
     * Moves our focus to the left child, shifting its horizontal coordinate grid marker one step west.
     * * - for (int value : topViewMap.values()) { result.add(value); }
     * Iterates cleanly through our captured skyline elements. The underlying TreeMap structural ordering
     * ensures we read them out in a flawless geographic sweep.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * * Time Complexity: O(n log n)
     * - We visit all $n$ nodes exactly once via our BFS traversal loop.
     * - Inside the loop, for each node, we check and potentially insert data into a TreeMap.
     * TreeMap lookups and insertions run in logarithmic time relative to the number of columns
     * stored, which takes $O(\log w)$ where $w$ is the maximum width of the tree.
     * - Since $w \le n$, the cost per node scales up to $O(\log n)$.
     * - Processing all $n$ nodes results in a tight overall execution runtime of O(n log n).
     *
     * * Space Complexity: O(n)
     * - The topViewMap stores at most $w$ elements (one per unique column), which scales to $O(n)$ in a skewed tree.
     * - The BFS queue holds at most the maximum width of a single level, which is bounded by $O(n)$
     * on the largest floor of a balanced binary tree.
     * - Thus, the absolute memory footprint scales linearly as O(n).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * Top View is a classic test of your ability to manage tree coordinates.
     * The winning combination is BFS + TreeMap.
     * - BFS ensures you process elements from top-to-bottom so the highest nodes hit the map first.
     * - TreeMap ensures your columns are effortlessly sorted from left-to-right when building the output.
     * If an interviewer asks you to do the "Bottom View" instead, you use the exact same framework,
     * but you remove the !containsKey guard rule, allowing deeper nodes to constantly overwrite
     * the higher ones until only the lowest row remains visible!
     */
    private static List<Integer> topView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        // TreeMap automatically sorts column positions from left to right
        TreeMap<Integer, Integer> topViewMap = new TreeMap<>();

        // Beautifully typed queue using your Pair class! No Objects needed.
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                // Pull out the pair container directly
                Pair current = queue.poll();

                // Read properties directly without any type casting fumbles
                TreeNode node = current.node;
                int col = current.col;

                // First node to claim this column coordinate locks it in from above
                if (!topViewMap.containsKey(col)) {
                    topViewMap.put(col, node.val);
                }

                // Push children onto the conveyor belt with updated column positions
                if (node.left != null) {
                    queue.add(new Pair(node.left, col - 1));
                }
                if (node.right != null) {
                    queue.add(new Pair(node.right, col + 1));
                }
            }
        }

        // Collect final values in perfectly sorted left-to-right alignment
        result.addAll(topViewMap.values());

        return result;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int x) {
            val = x;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Pair {
        TreeNode node;
        int col;

        Pair(TreeNode node, int col) {
            this.node = node;
            this.col = col;
        }
    }

}


