package binary_tree.medium_problems;

import java.util.*;

/*
LeetCode: 863. All Nodes Distance K in Binary Tree

Given the root of a binary tree, the value of a target node target, and an integer k,
return an array of the values of all nodes that have a distance k from the target node.
You can return the answer in any order.

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
Output: [7,4,1]
Explanation: The nodes that are a distance 2 from the target node (with value 5) have values 7, 4, and 1.

Example 2:
Input: root = [1], target = 1, k = 3
Output: []

Constraints:
            The number of nodes in the tree is in the range [1, 500].
            0 <= Node.val <= 500
            All the values Node.val are unique.
            target is the value of one of the nodes in the tree.
            0 <= k <= 1000
 */
public class AllNodeDistanceK {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.left.left = new TreeNode(8);

        int k = 2;
        TreeNode target = root.left.right;

        System.out.println(distanceK(root, target, k));
    }

    /*
     * WHAT THIS METHOD DOES:
     * This method finds and returns the values of all nodes that are positioned exactly
     * K edges away from a specific target node in a binary tree. The distance is
     * measured across any valid structural boundary, meaning it can track downwards into
     * children subtrees, sideways into sibling branches, or upwards through parent nodes.
     *
     * ---
     *
     * THE "REARVIEW MIRROR + RIPPLE EFFECT" PATTERN (ALL NODES DISTANCE K)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE OBJECTIVE: We need to locate the target node first, and then find all
     * node values that sit exactly at distance 'K' away from it. Since the final output
     * can be returned in any order, the specific traversal output layout doesn't matter.
     *
     *
     *
     * 2. THE INITIAL ATTACK & THE ROADBLOCK:
     * → Your first instinct was to use a tracking variable (currentDistance) to count
     * downward from the target.
     * → WHY IT FAILS: A standard binary tree is a top-down, one-way street. If the target
     * lives in the right subtree, your code cannot walk "backward" up the tree to explore
     * the left subtree. The distance tracker stays stuck at 0 for those upper branches
     * because there are no pointers pointing up to parent nodes.
     *
     * 3. THE INDEXING INSTINCT & THE TRAP:
     * → Your next brilliant instinct was to try a mathematical approach, using the heap-indexing
     * formula (subtracting/dividing from 2*i) to calculate the parent's position.
     * → WHY IT FAILS:
     * 1. Overflow: On highly skewed, deep trees, doubling indices at every level will
     * quickly exceed Integer.MAX_VALUE and overflow into negative numbers.
     * 2. Memory Reference: Even if you calculate the parent's math index, a standard
     * TreeNode object doesn't have an address book. Knowing a node's "seat number"
     * doesn't give your code a physical memory reference to jump up to that parent object.
     *
     * 4. THE BREAKTHROUGH (TREE TO GRAPH): If a data structure doesn't give you the pointers
     * you need, you build them yourself!
     * → STAGE 1 (DFS): We run a quick pre-processing pass to populate a HashMap<TreeNode, TreeNode>.
     * This map acts as a "rearview mirror," allowing any node to look up its parent instantly.
     * → This representation shift completely transforms the strict one-way Binary Tree into a
     * flexible, two-way Undirected Graph.
     *
     * 5. THE RADIAL RIPPLE (THE BFS ENGINE): Once every node has three potential travel directions
     * (Left, Right, and Up), we treat the target like a spark lighting a fire. The fire spreads out
     * radially, exactly 1 unit of distance per step, in all 3 directions uniformly.
     * → Because we are expanding outward layer-by-layer from a central source, BFS with a Queue
     * is the structurally perfect engine.
     * → We use a HashSet<TreeNode> to track "visited" nodes so the fire never steps backward
     * into an infinite loop.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. HashMap<TreeNode, TreeNode> (The Rearview Mirror):
     * - Why? Trees are inherently directional; pointers only lead down. We construct this map
     * to temporarily act as synthetic "parent pointers."
     * - Reason: A lookup in a HashMap runs in constant O(1) time. This gives every node
     * immediate access to its spatial parent, enabling upward structural travel.
     *
     * 2. Queue<TreeNode> (The Layered Radial BFS Engine):
     * - Why? We must find elements located at a uniform radius (K) from a customized origin point.
     * - Reason: A First-In, First-Out (Queue) processes nodes layer-by-layer. This expansion
     * guarantees that our currentDistance counter tracks our physical distance from the source.
     *
     * 3. HashSet<TreeNode> (The Cycle Guardrail):
     * - Why? By adding upward parent links, we convert our acyclic tree into a bidirectional graph.
     * Graphs are vulnerable to infinite cycles (e.g., Parent -> Child -> Parent -> Child).
     * - Reason: A HashSet filters out previously explored memory reference values in O(1) time,
     * keeping our path execution expanding strictly outward.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Pre-process the tree topography. Run a recursive Depth-First Search (populateParents)
     * to map every node reference to its parent node placeholder in parentMap.
     * Step 2: Establish the search origin. Initialize a BFS Queue and a HashSet to manage
     * visited nodes, loading the target node as the starting entry point.
     * Step 3: Launch the radial iteration loop. While the queue contains entries:
     * - Distance Check: If currentDistance == k, we have arrived at our perimeter ring.
     * Drain the active queue directly into the result collector list and return.
     * Step 4: Isolate the current horizontal layer. Capture int size = queue.size() and loop through
     * those elements exclusively:
     * - Pop a node and test its three cardinal expansion paths: Left child, Right child, and Parent node.
     * - For each direction: If the node is not null AND has not been checked off in the visited set,
     * log it into visited and push it onto the back of the queue.
     * Step 5: After exhausting an entire distance layer loop, increment currentDistance++ by 1.
     * Step 6: Return the final answers collection if the search naturally drains out.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * * - populateParents(root, null, parentMap);
     * Executes our mapping strategy, recording the parent pointers for every child node in the tree.
     * * - if (currentDistance == k) { ... }
     * The termination point. Because BFS advances uniformly, the moment our distance counter hits
     * K, the queue holds exactly the nodes making up that perimeter ring.
     * * - TreeNode parent = parentMap.get(current);
     * Activates our graph abstraction layer. It converts the standard tree lookup into a multi-directional
     * step, unlocking the upward exploration track.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * → Time Complexity: O(N) — One pass to map parents, one pass for the BFS ripple.
     * Building the map using DFS checks all N elements exactly once, costing O(N) time.
     * The BFS phase checks each node at most once via its directional checks, with each insertion and
     * lookup in our HashMap and HashSet running in constant O(1) time. Combined, the total
     * time scales linearly as O(N).
     *
     * → Space Complexity: O(N) — To store the parent mappings and visited states.
     * The parentMap holds references for N-1 node associations, consuming O(N) memory space.
     * The visited tracker handles up to N entries in the absolute widest exploration sweeps.
     * The implicit DFS stack frames scale with tree depth O(H), which is dominated by the overall
     * allocation layout of O(N).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * "All Nodes Distance K" is an elegant example of Representation Shift.
     * - When a problem's directional requirements mismatch your data structure's built-in constraints,
     * don't be afraid to add a pre-processing step to convert it into something that works.
     * - By mapping parent nodes with a hash table, you can transform a highly constrained hierarchical
     * tree into an undirected graph, allowing you to use standard graph tools like Breadth-First Search
     * to solve complex proximity problems.
     */
    private static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        // Create an empty list where we will store our final answers
        List<Integer> result = new ArrayList<>();

        // Safety check: If the tree doesn't even exist, return our empty list immediately
        if (root == null) return result;

        // Create an empty storage book (Map) to remember who the parents are
        // Key = Child Node, Value = Parent Node
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();

        // Run our helper function below to fill up the parent map completely
        populateParents(root, null, parentMap);

        // Create our BFS queue line to hold nodes we want to visit next
        Queue<TreeNode> queue = new LinkedList<>();

        // Create a safety checklist (Set) to mark nodes we have already visited
        Set<TreeNode> visited = new HashSet<>();

        // Start the fire! Put our starting target node into the queue line
        queue.offer(target);

        // Immediately check off the target node so we don't visit it again
        visited.add(target);

        // Start our timer tracker at 0 units of distance
        int currentDistance = 0;

        // Keep running as long as there are still nodes waiting in our queue line
        while (!queue.isEmpty()) {

            // Check: If our timer has perfectly reached K, we stop moving outward!
            if (currentDistance == k) {
                // Take every single node that is currently sitting in the queue...
                while (!queue.isEmpty()) {
                    // ...remove it from the line and pull its number value...
                    result.add(queue.poll().val); // ...and add that number to our answers list
                }
                return result; // Hand over the final answer list and finish!
            }

            // Look at how many nodes are waiting in line for this exact distance layer
            int size = queue.size();

            // Process ONLY the nodes belonging to this specific layer
            for (int i = 0; i < size; i++) {
                // Pull out the very first node waiting in the queue line
                TreeNode current = queue.poll();

                // DIRECTION 1: Try to look DOWN at the Left Child
                // If it exists AND we haven't visited it yet:
                if (current.left != null && !visited.contains(current.left)) {
                    visited.add(current.left); // Mark it as visited so we don't loop back
                    queue.offer(current.left); // Add it to the queue line to process next
                }

                // DIRECTION 2: Try to look DOWN at the Right Child
                // If it exists AND we haven't visited it yet:
                if (current.right != null && !visited.contains(current.right)) {
                    visited.add(current.right); // Mark it as visited
                    queue.offer(current.right); // Add it to the queue line
                }

                // DIRECTION 3: Try to look UP at the Parent Node using our map
                TreeNode parent = parentMap.get(current);
                // If a parent exists in our map AND we haven't visited it yet:
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent); // Mark the parent as visited
                    queue.offer(parent); // Add the parent to the queue line
                }
            }

            // After dealing with all nodes at the current distance, increment our timer by 1
            currentDistance++;
        }

        // If the loop finishes and we never hit distance K, just return the empty list
        return result;
    }

    // Helper Function: Recursively walks the tree to build the parent map links
    private static void populateParents(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
        // Base case: If we hit a dead end, stop and go back up
        if (node == null) return;

        // If the current node actually has a parent, save that connection in our map
        if (parent != null) {
            parentMap.put(node, parent);
        }

        // Tell the left child that the current node is its parent
        populateParents(node.left, node, parentMap);

        // Tell the right child that the current node is its parent
        populateParents(node.right, node, parentMap);
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
