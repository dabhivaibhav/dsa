# BFS & DFS - Skeleton, Reasoning, and Problem-Solving Approach

Everything you need to revise before solving any graph problem. Not just the code, but WHY each line exists and WHEN to use which tool.

---

## 1. WHY BFS Works (The Reasoning, Not Just The Rule)

### The Queue Is The Whole Secret

BFS uses a queue (FIFO: first in, first out). That single choice produces the level-by-level behavior and the shortest-path guarantee. Here is why:

You are at node 0. It has neighbors 1, 2, 3. You add them to the queue. The queue now holds [1, 2, 3]. You process 1 (front of the line). Node 1 has neighbors 4 and 5. You add them to the BACK of the queue: [2, 3, 4, 5]. You process 2 next (front), not 4.

Nodes discovered EARLIER are processed BEFORE nodes discovered LATER. That means ALL of layer 1 (1, 2, 3) is processed before ANY of layer 2 (4, 5, ...). Layer 2 before layer 3. And so on. That is level-by-level traversal, and it comes entirely from the FIFO property.

### Why BFS Guarantees Shortest Path (Unweighted)

Layer 0 = distance 0 (the start). Layer 1 = distance 1. Layer 2 = distance 2. A node enters the queue at the FIRST layer where it is discovered, and the visited mark prevents it from entering at any later layer.

So when BFS reaches a node for the first time, could a shorter path exist that BFS hasn't found yet? No. BFS has already processed ALL closer layers completely. If a shorter path existed, the node would have been discovered in an earlier layer. The visited mark would already be set. The fact that it is being discovered NOW means this IS the shortest path.

This guarantee ONLY holds for unweighted graphs (every edge costs the same). If edges have different weights, a 2-edge path could be shorter than a 1-edge path (if the 1-edge path has a heavy weight). That is where Dijkstra takes over.

### Why A Stack (DFS) Does NOT Guarantee Shortest Path

Same example. Node 0 has neighbors 1, 2, 3. Push them on a stack. Pop 3 (LIFO: last in, first out). Process 3, discover its neighbors 6, 7. Push them. Pop 7. Process 7, discover... you are going DEEP into 3's branch before you ever touch 1 or 2.

Suppose 7 connects to node 1. DFS reaches node 1 through the path 0 -> 3 -> 7 -> 1 (3 edges), when the direct path 0 -> 1 (1 edge) existed but 1 is still sitting at the bottom of the stack. DFS found A path, not THE shortest path.

**The one-line distinction:**
- Queue (FIFO) = BFS = closest first = shortest path guaranteed.
- Stack (LIFO) = DFS = deepest first = no shortest path guarantee.

---

## 2. WHY DFS Works

### Recursion IS A Stack

When you call `dfs(0)`, Java creates a stack frame: "I am running dfs, node = 0." Then dfs(0) calls dfs(1). Java pushes another frame on top: "node = 1." Then dfs(1) calls dfs(4). Another frame: "node = 4."

```
TOP:    dfs(4)    <- currently running
        dfs(1)    <- paused, waiting for dfs(4) to finish
BOTTOM: dfs(0)    <- paused, waiting for dfs(1) to finish
```

When dfs(4) finishes, its frame is POPPED. dfs(1) resumes where it left off (inside the for-loop, moving to the next neighbor). When dfs(1) finishes, its frame is popped. dfs(0) resumes.

Every recursive call is a PUSH. Every return is a POP. The for-loop's current position (which neighbor am I on?) is saved inside each frame. That is why you never create a Stack object in recursive DFS: Java's call stack IS the stack.

### Why DFS Goes Deep

Because recursion immediately dives into the first neighbor before trying the second. When dfs(0) finds neighbors 1, 2, 3, it calls dfs(1) immediately. dfs(1) calls dfs(4) immediately. dfs(4) calls dfs(7) immediately. Only when a dead end is reached (no unvisited neighbors) does the recursion unwind and try the next neighbor. That is depth-first.

---

## 3. The Visited Array (Why It Exists)

### The Problem It Solves

Graphs can have cycles. Without visited, BFS/DFS loops forever:

```
0 -- 1
BFS: enqueue 0. Poll 0, enqueue neighbor 1. Poll 1, enqueue neighbor 0.
Poll 0, enqueue 1. Poll 1, enqueue 0... INFINITE LOOP.
```

The visited array closes the door behind you. Once node 0 is marked, nobody can enqueue it again. The `if (!visited[neighbor])` check blocks it.

### Trees Did Not Need It

In LeetCode-style tree problems, TreeNode has left/right pointers that only go DOWNWARD. No back-edges exist. You can never revisit a parent because no pointer leads back to it. That is why your tree BFS/DFS never had a visited array.

Graphs stored as adjacency lists have edges going BOTH directions. Edge (0,1) means adj[0] has 1 AND adj[1] has 0. Every edge is a two-way door. The visited array is what prevents walking back through the door you just came from.

### When To Mark

**BFS: mark BEFORE enqueuing.** The mark is a RESERVATION, not a receipt. You are saying "I have CLAIMED this node, nobody else touch it." If you mark on poll instead, the same node can enter the queue multiple times (pushed by different neighbors before being polled).

**Recursive DFS: mark at the TOP of the function.** The first line of the function marks the node. Same idea: claim it the moment you start working on it.

**Iterative DFS: mark on POP with a continue guard.** A node might be pushed multiple times (by different neighbors). Only the first pop processes it. Later pops hit `if (visited[node]) continue;` and skip. This preserves correct DFS traversal order.

---

## 4. BFS Skeleton (9 Lines, Built From 6 Questions)

### The 6 Questions That Produce The Code

1. What do I do to the start node before the loop? → **Claim it and queue it.**
2. When does the loop stop? → **When there is nothing left to process (queue empty).**
3. First thing inside the loop? → **Take one out so I know who to work with.**
4. What do I do with that node? → **Look at all its neighbors.**
5. For each neighbor, what do I check? → **Has anyone claimed it already?**
6. If unclaimed, what two things, in what order? → **Claim it, then queue it.**

### The Code

```java
private void bfs(int start, List<List<Integer>> adj, boolean[] visited) {
    Queue<Integer> queue = new LinkedList<>();
    visited[start] = true;                          // Q1: claim the start
    queue.add(start);                               // Q1: queue it

    while (!queue.isEmpty()) {                      // Q2: while there is work
        int node = queue.poll();                    // Q3: take one out

        for (int neighbor : adj.get(node)) {        // Q4: look at all neighbors
            if (!visited[neighbor]) {               // Q5: only if unclaimed
                visited[neighbor] = true;           // Q6: claim it
                queue.add(neighbor);                // Q6: queue it
            }
        }
    }
}
```

### Why Each Line Exists

- `Queue<Integer> queue`: FIFO structure. This choice alone produces level-by-level order. Replace with a stack and you get DFS.
- `visited[start] = true` BEFORE `queue.add(start)`: reservation before entering the shared space. Prevents double-claiming.
- `while (!queue.isEmpty())`: "keep going while there are nodes on my to-do list." When the to-do list empties, everything REACHABLE has been processed. Not everything in the graph, just everything reachable from the start.
- `queue.poll()`: take from the FRONT. This is what makes it FIFO. The oldest discovered node is processed first. That is the entire BFS property.
- `for (int neighbor : adj.get(node))`: find all neighbors. The adjacency list has them pre-collected.
- `if (!visited[neighbor])`: prevents infinite loops on cycles. Prevents duplicate processing. Prevents wrong answers.
- `visited[neighbor] = true; queue.add(neighbor);`: same two-line pair as the start node. Every node gets the same treatment: claim, then queue.

---

## 5. Recursive DFS Skeleton (5 Lines, Built From 4 Questions)

### The 4 Questions

1. First thing when I arrive at a node? → **Mark it (claim my territory).**
2. Then what? → **Look at all neighbors.**
3. For each neighbor, what do I check? → **Visited or not?**
4. If not visited, what do I do? → **Call dfs on it (go deeper immediately).**

### The Code

```java
private void dfs(int node, List<List<Integer>> adj, boolean[] visited) {
    visited[node] = true;                           // Q1: mark it

    for (int neighbor : adj.get(node)) {            // Q2: look at all neighbors
        if (!visited[neighbor]) {                   // Q3: only if unvisited
            dfs(neighbor, adj, visited);             // Q4: go deeper immediately
        }
    }
}
```

### Why Each Line Exists

- `visited[node] = true` at the TOP: this node is mine. No other call can claim it. In recursive DFS, the function takes full responsibility for its own node. The caller does not mark it, unlike BFS where the discoverer marks the neighbor.
- `for (int neighbor : adj.get(node))`: same neighbor scan as BFS. Identical line.
- `if (!visited[neighbor])`: same guard as BFS. Identical line.
- `dfs(neighbor, adj, visited)`: this is where BFS and DFS diverge. BFS would say `queue.add(neighbor)` (process later, after finishing the current level). DFS says `dfs(neighbor)` (process NOW, go deeper immediately, come back to the next neighbor later). That one difference, "later" vs "now", is the entire behavioral difference.

---

## 6. Iterative DFS Skeleton

### When To Use This Instead Of Recursive

Recursive DFS can crash with StackOverflowError on deep graphs (long chains of 10,000+ nodes). Iterative DFS puts the stack on the heap where memory is much larger. Same traversal, no crash risk.

### The Code

```java
private void dfsIterative(int start, List<List<Integer>> adj, boolean[] visited) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited[node]) continue;       // may have been pushed multiple times
        visited[node] = true;

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                stack.push(neighbor);
            }
        }
    }
}
```

### Why Mark-On-Pop, Not Mark-Before-Push

In BFS, marking before enqueue works because the queue processes nodes in discovery order: the first enqueue IS the shortest path, so blocking later enqueues is correct.

In iterative DFS, the stack reverses push order. Marking before push can change which branch gets explored first, producing a different visit order than recursive DFS. Mark-on-pop with a continue guard allows duplicates in the stack but processes each node only once, preserving correct DFS order.

The cost: duplicate entries in the stack (same node pushed by multiple neighbors). The gain: correct traversal order on order-sensitive problems (topological sort, etc.).

---

## 7. Finding Neighbors On A Grid (The Universal 6 Lines)

### Why This Exists

Many graph problems give you a 2D grid instead of an adjacency list. The grid IS the graph. Each cell connects to its 4 adjacent cells (up, down, left, right). No adjacency list needed.

### The Physical Intuition

You are at cell (1, 1). Where can you go?

```
Up:    (0, 1)     row - 1, col same
Down:  (2, 1)     row + 1, col same
Left:  (1, 0)     row same, col - 1
Right: (1, 2)     row same, col + 1
```

Each move is just arithmetic: add a direction to your current position.

### The Code

```java
int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};   // up, down, left, right

for (int[] d : dirs) {
    int newRow = row + d[0];
    int newCol = col + d[1];

    if (newRow >= 0 && newRow < rows          // not above the grid
            && newCol >= 0 && newCol < cols) { // not off the sides
        // (newRow, newCol) is a valid neighbor
        // add your problem-specific check here
    }
}
```

### The Bounds Check Explained

```
newRow >= 0        prevents falling off the TOP
newRow < rows      prevents falling off the BOTTOM
newCol >= 0        prevents falling off the LEFT
newCol < cols      prevents falling off the RIGHT
```

Without it: ArrayIndexOutOfBoundsException when a cell at the edge tries to check a neighbor that doesn't exist.

### What Changes Per Problem (The Plug-In)

These 6 lines are IDENTICAL on every grid problem. The only thing that changes is the condition INSIDE the if-block:

```
Flood fill:       && grid[newRow][newCol] == originalColor   -> paint it
Rotting oranges:  && grid[newRow][newCol] == 1               -> rot it
01 Matrix:        && result[newRow][newCol] == -1            -> set distance
Number of islands: && grid[newRow][newCol] == '1'            -> mark visited
Cycle detection:  && !visited[newRow][newCol]                -> explore it
```

Same skeleton, different plug-in. Learn the skeleton once, apply it forever.

---

## 8. The Three Input Formats (How To Find Neighbors)

Every graph problem gives you the graph in one of three formats. The format decides how you find neighbors:

### Format 1: Edge List

You are given V (nodes) and a list of edges like [[0,1], [1,2], [2,3]].

**What to do:** build an adjacency list first (Phase 1), then use `adj.get(node)` to find neighbors.

```java
List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

for (int[] edge : edges) {
    adj.get(edge[0]).add(edge[1]);
    adj.get(edge[1]).add(edge[0]);     // ONLY for undirected
}
```

### Format 2: Adjacency Matrix

You are given an N x N matrix where `matrix[i][j] = 1` means node i connects to node j.

**What to do:** no building step. Scan the row to find neighbors.

```java
for (int j = 0; j < n; j++) {
    if (matrix[node][j] == 1 && !visited[j]) {
        // j is a neighbor
    }
}
```

### Format 3: Grid

You are given a 2D grid where cells have values (0, 1, 2, colors, etc.).

**What to do:** no building step. Use direction offsets (section 7 above).

### Quick Decision

```
Edge list       -> build adjacency list, then adj.get(node)
Adjacency matrix -> scan row matrix[node][0..N-1]
Grid            -> direction offsets + bounds check
```

---

## 9. The Problem-Solving Approach (The Gear Check)

Run these four questions IN ORDER before writing any code. Every graph problem. No exceptions.

### Gear 1: What Is The Graph Made Of?

Read the problem for five facts:
1. **Directed or undirected?** Decides whether edges go one way or both.
2. **Weighted or unweighted?** Decides BFS vs Dijkstra for shortest paths.
3. **0-indexed or 1-indexed?** Decides array sizes.
4. **Can it be disconnected?** Decides whether you need the outer loop.
5. **What is the input format?** Edge list, adjacency matrix, or grid?

### Gear 2: What Shape Is The Question?

Not "what algorithm," but what TYPE of answer does the problem want:

```
"Does X exist?"                    -> yes/no (existence)
"How many X?"                      -> counting
"What is the shortest/minimum?"    -> optimization
"In what order?"                   -> ordering
"Is there a cycle?"                -> existence (specific)
```

### Gear 3: What Tool Answers That Shape?

```
Shortest path (unweighted)         -> BFS (queue guarantees closest first)
Shortest path (weighted)           -> Dijkstra (priority queue)
Just reachability                  -> BFS or DFS, either works
Cycle detection (undirected)       -> BFS or DFS with parent tracking
Cycle detection (directed)         -> DFS with three-color (white/gray/black)
Topological sort                   -> DFS + stack, or BFS + in-degree (Kahn's)
Connected components               -> BFS or DFS with outer loop
Level-by-level processing          -> BFS with size snapshot
"Minimum steps/moves"              -> BFS (each level = one step)
Backtracking / exhaustive search   -> DFS
```

### Gear 4: What Is Special About THIS Problem?

What does this problem add on top of basic BFS/DFS?

```
Multiple starting points           -> multi-source BFS (seed all sources before the loop)
Need to count levels/steps          -> size snapshot inside BFS loop
Need to detect cycles (undirected)  -> parent tracking ({node, parent} in queue)
Need to detect cycles (directed)    -> three-color DFS (white/gray/black)
Need shortest distance per cell     -> distance = parent + 1 in result matrix
Grid instead of graph               -> direction offsets for neighbor-finding
Disconnected graph                  -> outer loop over all nodes
```

---

## 10. BFS Variations (What Gets Added On Top Of The Skeleton)

### Variation 1: Multi-Source BFS

**When:** multiple starting points spread simultaneously (rotting oranges, 01 matrix).

**What changes:** instead of enqueueing ONE start node, enqueue ALL sources before the loop.

```java
// Instead of:
visited[start] = true;
queue.add(start);

// Do:
for (each source in the grid/graph) {
    visited[source] = true;
    queue.add(source);
}
```

**Why it works:** all sources enter at "layer 0." BFS expands from all of them at once. The first wave to reach any target IS the nearest source, because BFS processes closer things first.

**Derivation (how to arrive at this idea from scratch):**
1. Brute force: run BFS from each target, find nearest source. O((R*C)^2).
2. Flip: run BFS from each source instead. Same distances (undirected). Still slow if done one at a time.
3. Merge: put ALL sources in one queue. One BFS pass. O(R*C).

### Variation 2: Level-By-Level BFS (Size Snapshot)

**When:** the answer depends on counting WHOLE levels (minutes, moves, depth).

**What changes:** wrap the poll-and-process in a for-loop over the current queue size.

```java
while (!queue.isEmpty()) {
    int size = queue.size();             // snapshot: how many in THIS level
    for (int i = 0; i < size; i++) {
        int node = queue.poll();
        // process node, enqueue neighbors
    }
    // one full level done
    levelCount++;
}
```

**Why the snapshot works:** at the start of each while-iteration, the queue holds EXACTLY the nodes from the current level. The snapshot captures that count. Nodes enqueued DURING the for-loop belong to the NEXT level and sit in the queue AFTER the snapshot boundary.

**When NOT needed:** when each cell records its OWN distance (parent + 1). The level boundary only matters when the answer is a GLOBAL counter (minutes, total steps).

### Variation 3: BFS With Parent Tracking (Undirected Cycle Detection)

**When:** detecting cycles in undirected graphs.

**What changes:** queue holds {node, parent} instead of just node.

```java
queue.add(new int[]{start, -1});              // -1 = no parent

// inside the loop:
int node = pair[0];
int parent = pair[1];

for (int neighbor : adj.get(node)) {
    if (!visited[neighbor]) {
        visited[neighbor] = true;
        queue.add(new int[]{neighbor, node});  // I am its parent
    } else if (neighbor != parent) {
        return true;                           // visited, not my parent = CYCLE
    }
}
```

**Why parent tracking is needed:** in undirected graphs, every neighbor list includes the node you came from (edge goes both ways). Without the parent check, EVERY edge looks like a cycle. The parent check distinguishes "I see my own door" (not a cycle) from "I see someone else's door" (a cycle).

---

## 11. DFS Variations

### Variation 1: Three-Color DFS (Directed Cycle Detection)

**When:** detecting cycles in directed graphs.

**Why two colors aren't enough:** in directed graphs, two paths reaching the same node is NORMAL (A->B->C and A->C). A boolean visited array cannot distinguish "on my current path" (a real cycle) from "finished by an earlier path" (safe convergence).

**The three colors:**
```
WHITE (0): unvisited.                         -> Java int[] defaults to 0, free.
GRAY  (1): currently being explored.          -> set at TOP of function.
BLACK (2): completely finished.               -> set at BOTTOM of function.
```

**The cycle rule:** reaching a GRAY neighbor = following directed edges back to a node still being explored = a directed cycle.

```java
private boolean dfs(int node, List<List<Integer>> adj, int[] color) {
    color[node] = 1;                                    // gray: exploring

    for (int neighbor : adj.get(node)) {
        if (color[neighbor] == 2) continue;             // black: done, skip
        if (color[neighbor] == 1) return true;          // gray: CYCLE
        if (dfs(neighbor, adj, color)) return true;     // white: explore deeper
    }

    color[node] = 2;                                    // black: done
    return false;
}
```

### Variation 2: DFS/BFS With Outer Loop (Disconnected Graphs)

**When:** the graph might have multiple components.

**What changes:** loop over ALL nodes, start a fresh traversal from each unvisited one.

```java
for (int i = 0; i < V; i++) {
    if (!visited[i]) {
        bfs(i, adj, visited);     // or dfs
        // do something: count++, collect component, etc.
    }
}
```

**Why:** one BFS/DFS only reaches one component. The outer loop bridges components. The visited array is the communication channel: the traversal writes marks, the outer loop reads them.

---

## 12. The Visited Trick On Grids (No Separate Array Needed)

On grids, you can often MUTATE the grid itself to serve as the visited marker, avoiding a separate boolean[][]:

```
Flood fill:      paint cell from originalColor to newColor.
                 The == originalColor check fails on painted cells. Visited.

Rotting oranges: set cell from 1 to 2.
                 The == 1 check fails on rotted cells. Visited.

01 Matrix:       result matrix starts at -1 for unreached cells.
                 Write the distance. The == -1 check fails. Visited.
```

**The rule:** if the operation you perform on the cell already makes it fail the neighbor-check, no separate visited array is needed. The mutation IS the visited mark.

**When it breaks:** when the original value equals the new value (flood fill with same color). The mutation changes nothing, the check still passes, BFS loops forever. Guard against this case upfront.

---

## 13. Three-Phase Structure Of Every Graph Solution

Every graph problem follows this skeleton:

```
PHASE 1 - BUILD:    convert input to a usable graph representation.
                    Edge list -> adjacency list.
                    Adjacency matrix -> scan rows directly.
                    Grid -> use direction offsets directly.

PHASE 2 - TRAVERSE: BFS or DFS with visited.
                    Same skeleton every time.
                    The tool (BFS/DFS) is chosen by the question shape.

PHASE 3 - ANSWER:   use the traversal to compute what the problem asks.
                    Count components, find shortest distance, detect a cycle,
                    record distances, collect an ordering, etc.
```

The traversal is the ENGINE. The answer logic WRAPS AROUND it. Different problems have different Phase 3 logic on top of the same Phase 2 skeleton.

---

## 14. One-Glance Summary

```
BFS = queue.    Mark before enqueue.    Closest first.    Shortest path guaranteed.
DFS = stack.    Mark at function top.   Deepest first.    No shortest path guarantee.

BFS for: shortest path, minimum steps, level-by-level, multi-source spreading.
DFS for: cycle detection, topological sort, backtracking, exhaustive search.
Either for: reachability, connected components, flood fill.

QUEUE makes BFS work.  FIFO = process oldest first = layer by layer = closest first.
STACK makes DFS work.  LIFO = process newest first = go deep = explore one branch fully.

VISITED prevents infinite loops.  Trees don't need it (one-way pointers).
Graphs need it (two-way edges).   Mark early (before enqueue / at function top).

GRID neighbors: direction array + bounds check.  Same 6 lines every problem.
The plug-in (what you check inside) is the only thing that changes.

GEAR CHECK before every problem:
  1. What is the graph? (directed/undirected, weighted, indexing, format)
  2. What shape is the question? (existence, counting, shortest, ordering)
  3. What tool? (BFS for shortest, DFS for cycles/topo, either for reachability)
  4. What is special? (multi-source, level counting, parent tracking, 3-color)
```