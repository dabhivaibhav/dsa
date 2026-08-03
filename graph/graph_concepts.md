# Graph Concepts - Interview Reference

Everything from definitions to storage to traversal pattern.

---

## 1. What Is a Graph?

A graph is a collection of **nodes** (also called **vertices**) connected by **edges**. Unlike a tree, a graph has no root, no parent-child hierarchy, and can have cycles. A tree is actually a special case of a graph: a connected, acyclic, undirected graph.

**Why graphs matter in interviews:** they model relationships. Social networks (people = nodes, friendships = edges), maps (cities = nodes, roads = edges), dependencies (tasks = nodes, "must do X before Y" = directed edge), web pages (pages = nodes, links = directed edges). When a problem says "connected," "reachable," "shortest path," or "dependency," think graph.

---

## 2. Core Terminology

| Term | Meaning | Example |
|------|---------|---------|
| **Node / Vertex** | A point in the graph | A city, a person, a task |
| **Edge** | A connection between two nodes | A road between two cities |
| **Path** | A sequence of nodes where each consecutive pair is connected by an edge | City A -> City B -> City C |
| **Degree** | The number of edges connected to a node | A node connected to 3 others has degree 3 |
| **Adjacent / Neighbor** | Two nodes connected by an edge | If edge (1,2) exists, 1 and 2 are neighbors |
| **Connected** | A path exists between two nodes | Can I get from A to B somehow? |
| **Component** | A group of nodes where every node can reach every other | An "island" of connected nodes |
| **Cycle** | A path of 3 or more distinct nodes that starts and ends at the same node (in undirected); in directed, following edge directions back to the start | A -> B -> C -> A |

---

## 3. Types of Graphs

### 3.1 Undirected Graph
- Edges have **no direction**: if node U connects to V, then V also connects to U.
- Think of it as a **two-way road**: traffic flows both ways.
- Edge (U, V) and edge (V, U) are the **same edge**.
- Example: friendships on Facebook (if you're my friend, I'm yours).

### 3.2 Directed Graph (Digraph)
- Edges **have a direction**: an edge from U to V means U can reach V, but V cannot reach U unless a separate edge V -> U exists.
- Think of it as a **one-way street**.
- Edge (U, V) and edge (V, U) are **different edges**.
- Example: following on Twitter/Instagram (I follow you, you don't necessarily follow me).

### 3.3 Cyclic Graph
- Contains at least **one cycle**: a path that starts and ends at the same node.
- **Undirected cyclic:** any loop in an undirected graph. Three nodes all connected to each other form a cycle.
- **Directed cyclic:** the cycle must follow edge directions. A -> B -> C -> A is a directed cycle.

### 3.4 Acyclic Graph
- Contains **no cycles**.
- **DAG (Directed Acyclic Graph):** a directed graph with no cycles. Extremely important in interviews: task scheduling, build systems, course prerequisites all use DAGs. Topological sort only works on DAGs.
- **Undirected acyclic + connected = a tree.** That's literally the definition.

### 3.5 Weighted Graph
- Each edge carries a **weight** (a number representing cost, distance, time, etc.).
- If the problem gives a graph but does not assign weights to edges, treat each edge as weight **1** (called **unit weight** or **unweighted**). A problem that says "weighted" will always provide the weights.
- Example: cities connected by roads where each road has a distance in kilometers.

### 3.6 Unweighted Graph
- All edges are equal, no cost attached.
- Equivalent to a weighted graph where every weight is 1.
- BFS on an unweighted graph gives shortest paths (fewest edges).

---

## 4. Degree (How Connected a Node Is)

### Undirected Graph
- **Degree of a node** = number of edges touching it.
- **Property:** the sum of all degrees = **2 x number of edges**. Every edge contributes 1 to each of its two endpoints.
- Example: 3 nodes, 3 edges forming a triangle. Each node has degree 2. Sum = 6 = 2 x 3 edges.

### Directed Graph
- **In-degree** = number of edges pointing **INTO** the node (incoming).
- **Out-degree** = number of edges pointing **OUT OF** the node (outgoing).
- **Property:** sum of all in-degrees = sum of all out-degrees = total number of edges. Every edge contributes 1 to one node's out-degree and 1 to another's in-degree.
- **Why this matters:** topological sort starts from nodes with in-degree 0 (no dependencies). BFS-based topological sort (Kahn's algorithm) uses in-degree tracking as its engine.

---

## 5. Reading a Graph Problem (Input Format)

Most problems give you the graph in this format:

```
N = number of nodes
M = number of edges
Then M lines, each containing two (or three) numbers:
  u v       (an edge between u and v, or from u to v)
  u v w     (an edge with weight w)
```

**Key things to check before coding:**
1. **Directed or undirected?** This decides whether edge (u,v) also means (v,u).
2. **0-indexed or 1-indexed?** Nodes starting from 0 or from 1. This changes your array sizes.
3. **Weighted or unweighted?** Decides whether edges carry a third value.
4. **Can there be self-loops?** An edge from a node to itself. Some problems allow it.
5. **Can there be multiple edges between the same pair?** Called "multi-edges." Usually no, but check.
6. **Is the graph connected?** Or could there be isolated nodes / multiple components?

---

## 6. Storing a Graph (The Two Representations)

### 6.1 Adjacency Matrix

A 2D array where `matrix[i][j] = 1` means there is an edge from node i to node j.

```java
// For N nodes (1-indexed, so size N+1)
int[][] matrix = new int[N + 1][N + 1];

// For each edge (u, v):
matrix[u][v] = 1;
matrix[v][u] = 1;   // add this line ONLY for undirected graphs

// For weighted graphs, store the weight instead of 1:
matrix[u][v] = weight;
```

**Check if edge exists:** `matrix[u][v] != 0` -> O(1).
**Find all neighbors of u:** scan the entire row `matrix[u][0..N]` -> O(N).

**When to use:** dense graphs (many edges, close to N^2), or when you need O(1) edge-existence checks.

**When NOT to use:** sparse graphs (few edges compared to N^2), which is most interview problems. A graph with 10^5 nodes would need a 10^5 x 10^5 matrix = 10^10 cells. That's 40 GB of memory. Dead on arrival.

| | Adjacency Matrix |
|---|---|
| Space | O(N^2) |
| Check if edge (u,v) exists | O(1) |
| Find all neighbors of u | O(N) |
| Add an edge | O(1) |

### 6.2 Adjacency List (THE ONE YOU'LL USE 95% OF THE TIME)

An array of lists. `adj[i]` holds the list of all neighbors of node i.

```java
// For N nodes (1-indexed)
List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i <= N; i++) {
    adj.add(new ArrayList<>());
}

// For each edge (u, v) in an UNDIRECTED graph:
adj.get(u).add(v);
adj.get(v).add(u);   // both directions

// For a DIRECTED graph, only one direction:
adj.get(u).add(v);   // edge goes FROM u TO v, only

// For WEIGHTED graphs, store pairs (neighbor, weight):
// Use List<List<int[]>> where each int[] is {neighbor, weight}
List<List<int[]>> adj = new ArrayList<>();
for (int i = 0; i <= N; i++) {
    adj.add(new ArrayList<>());
}
adj.get(u).add(new int[]{v, weight});
adj.get(v).add(new int[]{u, weight});  // undirected
```

**Check if edge (u,v) exists:** scan adj[u] -> O(degree of u).
**Find all neighbors of u:** just read adj[u] -> O(degree of u), which is O(1) to start iterating.

| | Adjacency List |
|---|---|
| Space | O(N + 2M) undirected, O(N + M) directed |
| Check if edge (u,v) exists | O(degree of u) |
| Find all neighbors of u | O(1) to access the list |
| Add an edge | O(1) |

**Why this wins:** space is proportional to the actual number of edges, not N^2. A sparse graph with 10^5 nodes and 10^5 edges uses ~10^5 space, not 10^10.

### Quick Decision
- **Interview default:** adjacency list. Always. Unless the problem specifically needs O(1) edge checks on a small, dense graph.
- **Grid problems** (2D matrix given as input): the grid IS the adjacency matrix. Don't convert it. Neighbors are the 4 (or 8) adjacent cells.

---

## 7. Special Graph Structures Worth Knowing

### Tree
- Connected, undirected, acyclic graph.
- N nodes, exactly N-1 edges.
- Exactly one path between any two nodes.
- You already own this entire topic from BST.

### DAG (Directed Acyclic Graph)
- Directed graph with no cycles.
- Topological ordering exists (and only exists for DAGs).
- Course prerequisites, build dependencies, task scheduling.

### Bipartite Graph
- Nodes can be split into two groups where every edge goes BETWEEN groups, never within.
- Equivalent to: the graph is 2-colorable (you can color nodes with 2 colors so no adjacent nodes share a color).
- Detect with BFS/DFS: try to 2-color it; if you find a conflict, it's not bipartite.

### Connected Components
- In an undirected graph: groups of nodes where every node can reach every other within the group, but no node can reach a node in a different group.
- Count them with BFS/DFS: pick an unvisited node, explore everything reachable, that's one component. Repeat.

---

## 8. Graph Traversals (The Two Workhorses)

### 8.1 BFS (Breadth-First Search)
- Uses a **queue** (FIFO).
- Visits nodes **level by level**: all nodes at distance 1, then distance 2, then distance 3...
- Gives **shortest path in an unweighted graph** (fewest edges).
- Think of it as **ripples in a pond**: the wavefront expands outward uniformly.

```java
// BFS from a source node
Queue<Integer> queue = new LinkedList<>();
boolean[] visited = new boolean[N + 1];

queue.add(source);
visited[source] = true;

while (!queue.isEmpty()) {
    int node = queue.poll();
    // process node

    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            visited[neighbor] = true;  // mark BEFORE enqueuing (prevents duplicates)
            queue.add(neighbor);
        }
    }
}
```

**Time:** O(N + M) -- visit every node, check every edge.
**Space:** O(N) for visited + queue.

**When to use BFS:**
- Shortest path in an unweighted graph
- Level-order anything
- "Minimum number of steps/moves/transformations"
- Multi-source BFS (start from multiple nodes at once, like "rotten oranges")

### 8.2 DFS (Depth-First Search)
- Uses a **stack** (explicit or the recursion call stack).
- Goes as **deep as possible** before backtracking.
- Think of it as **exploring a maze**: go forward until you hit a dead end, then back up and try the next path.

```java
// DFS recursive
boolean[] visited = new boolean[N + 1];

void dfs(int node) {
    visited[node] = true;
    // process node

    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor);
        }
    }
}
```

```java
// DFS iterative (with explicit stack)
// Note: marking happens on POP, not on push. This means the same node can
// sit on the stack more than once (pushed by multiple neighbors before being
// popped). The `if (visited[node]) continue;` guard handles that safely.
// This is the standard iterative DFS; marking on push changes the visit
// order and is NOT equivalent to recursive DFS.
Deque<Integer> stack = new ArrayDeque<>();
boolean[] visited = new boolean[N + 1];

stack.push(source);

while (!stack.isEmpty()) {
    int node = stack.pop();
    if (visited[node]) continue;  // may have been pushed more than once
    visited[node] = true;
    // process node

    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            stack.push(neighbor);
        }
    }
}
```

**Time:** O(N + M) -- same as BFS.
**Space:** O(N) for visited + stack/recursion.

**When to use DFS:**
- Cycle detection
- Connected components
- Topological sort
- Path finding (not shortest, just any path)
- Backtracking problems

### BFS vs DFS - Quick Decision

| Need | Use |
|------|-----|
| Shortest path (unweighted) | BFS |
| Shortest path (weighted, no negatives) | Dijkstra (greedy algorithm with priority queue / min-heap) |
| Shortest path (weighted, with negatives) | Bellman-Ford |
| Cycle detection (undirected) | Either BFS or DFS |
| Cycle detection (directed) | DFS with coloring (white/gray/black) |
| Topological sort | DFS (stack-based) or BFS (Kahn's with in-degree) |
| Connected components | Either |
| Level-order / layer-by-layer | BFS |
| "Minimum steps" | BFS |
| Backtracking / exhaustive search | DFS |

---

## 9. The Visited Array (Why It Exists)

In LeetCode-style tree problems, nodes have left/right pointers that only go downward, so you naturally never revisit a node. But a graph CAN have cycles AND edges pointing back to already-visited nodes, which means without a visited check, your traversal loops forever: A -> B -> C -> A -> B -> C -> ...

**The visited array is what makes a graph traversal terminate.** It is the key difference between "traverse a tree" and "traverse a graph." You already own tree traversals; adding a visited array is what upgrades them to graph traversals.

**Note:** when a tree is stored as an **adjacency list** (which happens in some graph problems where the input happens to be a tree), edges go both ways, so you ALSO need a visited array (or parent tracking) there, even though the structure is a tree. The "no visited needed" shortcut only works with one-directional left/right TreeNode pointers.

**When to mark visited:**
- **BFS:** mark when you ADD to the queue, not when you remove. If you mark on removal, the same node can enter the queue multiple times before being processed, wasting time and potentially causing wrong answers.
- **DFS:** mark when you ENTER the node (first line of the function), before processing children.

---

## 10. Graph on a Grid (The Hidden Graph)

Many problems give you a 2D grid instead of explicit nodes and edges. The grid IS a graph:
- Each cell is a node.
- Each cell's neighbors are the 4 (or 8) adjacent cells.
- No adjacency list needed; the grid coordinates ARE the structure.

```java
// 4-directional neighbors
int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};

for (int[] d : dirs) {
int newRow = row + d[0];
int newCol = col + d[1];
    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
            && !visited[newRow][newCol]) {
        // process neighbor
        }
        }
```

**Common grid-graph problems:** number of islands, rotten oranges, shortest path in a maze, flood fill.

---

## 11. Algorithm Roadmap (What's Coming Next)

This is the order you'll learn them, with one-line descriptions so you know what each one does before you meet it:

| Algorithm | What It Does | Key Structure |
|-----------|-------------|---------------|
| **BFS** | Level-by-level traversal, shortest path in unweighted | Queue |
| **DFS** | Deep-first traversal, cycle detection, components | Stack / recursion |
| **Topological Sort** | Linear ordering of a DAG where every edge U->V has U before V | DFS + stack, or BFS + in-degree |
| **Cycle Detection** | Does the graph contain a cycle? | DFS coloring (directed), union-find or DFS (undirected) |
| **Union-Find (DSU)** | Track connected components, merge groups efficiently | Array with path compression + rank |
| **Dijkstra** | Shortest path from one source, weighted, no negative edges | Priority queue (min-heap) |
| **Bellman-Ford** | Shortest path with negative edges, detects negative cycles | Relax all edges N-1 times |
| **Floyd-Warshall** | Shortest path between ALL pairs | N x N DP table |
| **Prim / Kruskal** | Minimum spanning tree (connect all nodes, minimum total weight) | Priority queue / Union-Find |

---

## 12. The Connection to Trees (What You Already Own)

Your tree knowledge transfers directly:

| Tree Concept | Graph Equivalent |
|-------------|-----------------|
| Recursive DFS (preorder/inorder/postorder) | DFS on a graph (+ visited array) |
| BFS level-order with a queue | BFS on a graph (+ visited array) |
| "Visit every node" | Same, with visited to prevent cycles |
| Tree has no cycles | Graph may have cycles, visited handles it |
| Tree is connected | Graph may have multiple components, loop over all nodes |
| Parent-child relationship | Neighbor relationship (no hierarchy) |

**The single addition:** the visited array. Everything else is what you already do, run on a structure that can point backward.

---

## 13. Common Mistakes in Graph Problems

1. **Forgetting the visited array** -> infinite loop on cyclic graphs.
2. **Marking visited on REMOVAL from queue instead of on ADDITION** -> same node enters the queue multiple times, TLE or wrong answer.
3. **Not handling disconnected components** -> BFS/DFS from one node misses nodes in other components. Solution: loop over all nodes, start a new BFS/DFS from each unvisited one.
4. **Using adjacency matrix for large N** -> memory limit exceeded. Use adjacency list.
5. **Forgetting to add BOTH directions for undirected graphs** -> missing edges, wrong traversal.
6. **Off-by-one on 0-indexed vs 1-indexed** -> missing nodes or array out of bounds.
7. **Grid problems: forgetting boundary checks** -> ArrayIndexOutOfBoundsException.

---

## 14. Template: Reading Input and Building an Adjacency List

```java
// Standard graph input reading
int N = /* number of nodes */;
int M = /* number of edges */;

List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i <= N; i++) {      // N+1 for 1-indexed
        adj.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
int u = edges[i][0];
int v = edges[i][1];
    adj.get(u).add(v);
    adj.get(v).add(u);   // ONLY for undirected; remove for directed
}
```

```java
// Weighted version
List<List<int[]>> adj = new ArrayList<>();
for (int i = 0; i <= N; i++) {
        adj.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
int u = edges[i][0];
int v = edges[i][1];
int w = edges[i][2];   // weight
    adj.get(u).add(new int[]{v, w});
        adj.get(v).add(new int[]{u, w});  // undirected
        }
```

---

## 15. One-Glance Cheat Sheet

```
GRAPH = nodes + edges. Trees are graphs. Graphs can have cycles.

UNDIRECTED: edge (u,v) = edge (v,u). Two-way road.
DIRECTED:   edge (u,v) != edge (v,u). One-way street.
WEIGHTED:   edges carry costs. Unweighted = all costs are 1.
CYCLIC:     a path loops back to its start.
DAG:        directed + no cycles. Topological sort lives here.

STORE IT:   adjacency list, almost always. adj[u] = list of u's neighbors.
TRAVERSE:   BFS (queue, level-by-level, shortest path) or DFS (stack, go deep).
DON'T LOOP: visited array. The one thing trees didn't need and graphs do.

SHORTEST PATH:  unweighted -> BFS.  weighted -> Dijkstra.  negative -> Bellman-Ford.
CYCLE DETECT:   undirected -> DFS/BFS.  directed -> DFS with 3-color.
TOPO SORT:      DFS + stack, or BFS + in-degree (Kahn's). Only on DAGs.
COMPONENTS:     BFS/DFS from each unvisited node. Count the starts.
```
