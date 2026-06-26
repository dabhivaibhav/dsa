package binary_tree.medium_problems;

import java.util.*;

/*
Problem: Minimum time taken to burn the BT from a given Node

Given a target node data and a root of binary tree. If the target
is set on fire, determine the shortest amount of time needed to
burn the entire binary tree. It is known that in 1 second all nodes
connected to a given node get burned. That is its left child, right
child, and parent.

Example 1
Input : root = [1, 2, 3, 4, null, 5, 6, null, 7]. target = 1
Output : 3
Explanation : The node with value 1 is set on fire.
In 1st second it burns node 2 and node 3.
In 2nd second it burns nodes 4, 5, 6.
In 3rd second it burns node 7.

Example 2
Input : root = [1, 2, 3, null, 5, null, 4], target = 4
Output : 4
Explanation : The node with value 4 is set on fire.
In 1st second it burns node 3.
In 2nd second it burns node 1.
In 3rd second it burns node 2.
In 4th second it burns node 5.

Constraints:
            1 <= Number of Nodes <= 10^4
            -10^5 <= Node.val <= 10^5
            All Node.val values are unique.
            target will always be present in tree
 */
public class MinimumTimeTakenToBurnTreeFromANode {

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
        System.out.println(timeToBurnTree(root, 3));
    }

    /*
     * WHAT THIS METHOD DOES:
     * This method simulates a fire spreading throughout a binary tree starting from
     * a specific target node value. The fire propagates at a uniform rate of 1 edge per
     * second across any available connection (to left children, right children, or parents).
     * It tracks and returns the exact total time required to burn the entire tree.
     *
     * ---
     *
     * THE "REARVIEW MIRROR + TOTAL BURN TIME" PATTERN (MINIMUM TIME TO BURN TREE)
     *
     * Your Thought Process & Intuition:
     * 1. THE CORE OBJECTIVE: A fire starts at a designated target node and spreads
     * outward to all its adjacent neighbors (left child, right child, and parent)
     * exactly 1 unit of distance per second. We need to measure the total time
     * it takes for the fire to consume the entire binary tree.
     *
     *
     *
     * 2. YOUR FIRST ATTACK & THE ROADBLOCK:
     * → Your first instinct was to use a simple top-down DFS traversal to find the
     * target and count the depth from there.
     * → WHY IT FAILS: A standard binary tree is a one-way, top-down street. If the
     * fire starts deep down in the right subtree, it cannot naturally climb
     * backward up to the root and spill over into the left subtree using standard
     * DFS. A single top-down pass cannot track multi-directional radial travel
     * times without turning the tree into a two-way street first.
     *
     * 3. YOUR BREAKTHROUGH INSTINCT (THE DISTANCE-K CONNECTION):
     * → You accurately caught that this problem shares the exact same spatial structural
     * mechanics as the "All Nodes Distance K" problem!
     * → Instead of stopping at a distance 'K', the goal here is to just let the fire
     * ripple outward until absolutely every node is burned.
     *
     * 4. THE GRAPH-SHIFT SOLUTION ENGINE:
     * → STAGE 1 (DFS Mapping): We do a quick pre-processing pass to find our target
     * node object and build a HashMap<TreeNode, TreeNode>. This gives every node
     * a "rearview mirror" to access its parent, transforming our one-way tree
     * into a two-way Undirected Graph.
     * → STAGE 2 (BFS Fire Simulation): We drop the target into a Queue and mark it
     * as visited in a HashSet. We run a level-by-layer BFS loop. Each full layer
     * processed represents 1 second passing.
     * → TRACKING TIME ACCURATELY: We only increment our time counter if the fire
     * successfully spreads to *new* unvisited neighbors during that second. When the
     * queue runs completely empty, the whole tree is consumed, and we have our answer.
     *
     * ---
     *
     * CORE DATA STRUCTURE CHOICES: THE "WHY" BEHIND THE MACHINERY
     *
     * 1. HashMap<TreeNode, TreeNode> (The Bidirectional Adapter):
     * - Why? Standard tree references only map downward. We establish this map to keep track
     *   of upward structural layout connections.
     * - Reason: Lookups run in constant $O(1)$ time, which allows our simulation engine to
     *   instantly clear paths upward to parents when simulating the flame.
     *
     * 2. Queue<TreeNode> (The Real-Time Chronological Clock):
     * - Why? BFS naturally operates on an explicit distance-by-distance wavefront.
     * - Reason: By processing nodes in level-by-level blocks using a FIFO queue, we align
     *   each iteration cycle with 1 second of actual simulation time passing.
     *
     * 3. Boolean Flag fireSpreadThisSecond (The Ghost Second Filter):
     * - Why? When the final set of leaf nodes are dequeued, they will attempt to expand.
     * However, because all their neighbors are already burned, no new nodes enter the queue.
     * - Reason: If we did not use this flag, the program would add an extra, incorrect
     *   second to the timer right at the end when the queue is about to finish processing.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: Pre-process the tree. Call findTargetAndMapParents to locate the physical targetNode
     * reference and completely populate parentMap via DFS.
     * Step 2: Initialize our fire-tracking BFS Queue and a HashSet to map out our burned zones.
     * Step 3: Insert the targetNode into the queue and mark it as visited to jumpstart the fire.
     * Step 4: Run the outer time-step loop while the queue contains active embers:
     * - Initialize a boolean tracker: boolean fireSpreadThisSecond = false;
     * - Record the current wavefront size snapshot: int size = queue.size();
     * Step 5: Process all elements belonging to this active second's wave:
     * - Pop a node and examine its three directional channels: Left, Right, and Parent.
     * - For each branch: If it exists and hasn't been visited yet, mark it visited, push it
     *   to the queue, and trip our trigger flag: fireSpreadThisSecond = true.
     * Step 6: After processing the layer, check the flag. If fireSpreadThisSecond is true,
     * increment timeTaken++.
     * Step 7: Return the accumulated timeTaken counter once the tree is completely consumed.
     *
     * ---
     *
     * STEP-BY-STEP CODE EXPLANATION:
     * - TreeNode targetNode = findTargetAndMapParents(root, null, targetVal, parentMap);
     *   Combines two expensive tree passes into a single optimized operation. It records the parents
     * while searching for the starting memory reference of our target.
     * - if (fireSpreadThisSecond) { timeTaken++; }
     * 	 Your safety guardrail. It prevents the timer from ticking up if the current wave of nodes
     *   hits a complete structural dead end.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     *
     * Complexity:
     * → Time Complexity: O(N) — One pass to locate the target and map parents, followed
     * by one full radial BFS pass to burn the tree.
     * The pre-processing DFS pass sweeps through all $N$ nodes to construct the map links.
     * The BFS simulation layer touches each node at most once. Hash map and set operations
     * execute in constant $O(1)$ time. Thus, the total time complexity scales completely linearly as O(N).
     *
     * → Space Complexity: O(N) — Required for the parent map, the BFS queue, and the
     * visited tracking set.
     * The parentMap holds $N-1$ associations. The visited set expands to a peak capacity of $N$
     * nodes when the tree is entirely burned out. The BFS queue capacity is bound by the maximum diameter
     * of the network, creating an absolute linear space layout bound of O(N).
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * The "Time to Burn Tree" question demonstrates why mastering the structural mechanics of a past
     * problem (Distance K) unlocks variants effortlessly.
     * - Recognizing that fire propagation, disease spreading, or network packet broadcasting
     *   are all disguised Radial Breadth-First Search problems allows you to use a single tool.
     * - Remember to protect your counters from trailing or phantom increments by using a localized
     *   boolean validation flag (fireSpreadThisSecond) right at the end of your layer loop blocks.
     */
    private static int timeToBurnTree(TreeNode root, int targetVal) {
        // Step 1: Find the target node object and map parents
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        TreeNode targetNode = findTargetAndMapParents(root, null, targetVal, parentMap);

        // Step 2: Set up the BFS fire simulation
        Queue<TreeNode> queue = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();

        queue.offer(targetNode);
        visited.add(targetNode);

        int timeTaken = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean fireSpreadThisSecond = false;

            for (int i = 0; i < size; i++) {
                TreeNode current = queue.poll();

                // Direction 1: Down Left
                if (current.left != null && !visited.contains(current.left)) {
                    visited.add(current.left);
                    queue.offer(current.left);
                    fireSpreadThisSecond = true;
                }
                // Direction 2: Down Right
                if (current.right != null && !visited.contains(current.right)) {
                    visited.add(current.right);
                    queue.offer(current.right);
                    fireSpreadThisSecond = true;
                }
                // Direction 3: Up to Parent
                TreeNode parent = parentMap.get(current);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    queue.offer(parent);
                    fireSpreadThisSecond = true;
                }
            }

            // If the fire successfully reached new nodes, add 1 second to our timer
            if (fireSpreadThisSecond) {
                timeTaken++;
            }
        }

        return timeTaken;
    }

    // Helper to find the target object using its value, while mapping parents at the same time
    private static TreeNode findTargetAndMapParents(TreeNode node, TreeNode parent, int targetVal, Map<TreeNode, TreeNode> parentMap) {
        if (node == null) return null;

        if (parent != null) {
            parentMap.put(node, parent);
        }

        TreeNode target = null;
        if (node.val == targetVal) {
            target = node;
        }

        TreeNode leftTarget = findTargetAndMapParents(node.left, node, targetVal, parentMap);
        TreeNode rightTarget = findTargetAndMapParents(node.right, node, targetVal, parentMap);

        // Return whichever path successfully found the target node object
        return (target != null) ? target : (leftTarget != null ? leftTarget : rightTarget);
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
