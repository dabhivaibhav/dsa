# BFS Solution Blueprint

A five-stage framework for building any BFS solution from scratch. Apply these stages in order, every time, before writing a single line of code. The stages are the thinking. The code is the transcription.

---

## How To Use This Blueprint

Open this file before every new BFS problem. Walk through each stage and write your answers on paper or in a comment block BEFORE coding. If you can answer every stage, the code assembles itself. If you get stuck on a stage, that's where the problem's difficulty actually lives, and now you know exactly what to figure out.

---

## Stage 1: What Do I Need? (The Ingredients)

Before writing code, list four things. Each one is a design decision.

### 1a. What goes in the queue?

The queue holds whatever information you need when you poll an entry. Ask: "when I pull something out of the queue, what do I need to know to process it?"

| Problem Type | Queue Holds | Why |
|---|---|---|
| Simple graph traversal | Just a node (int) | Only need to know which node to process |
| Grid traversal | A coordinate pair (int[]{row, col}) | Need both coordinates to check neighbors |
| Cycle detection (undirected) | Node + parent (int[]{node, parent}) | Need to know who sent me here |
| Shortest path with distance | Node + distance, OR just node with level counting | Need to track how far I've traveled |
| Path reconstruction | A full path (List<String>) | Need the entire history, not just current position |

**The decision rule:** start with the simplest option (just a node). Only add more information if the problem REQUIRES it. Don't carry data you won't use.

### 1b. What is the visited mechanism?

Something must prevent a node from being enqueued twice. Ask: "what stops infinite loops?"

| Approach | When To Use |
|---|---|
| boolean[] visited | Standard graphs with numbered nodes |
| HashSet with remove | Word problems, state-space problems |
| Grid mutation (change cell value) | Grid problems where you can modify the input |
| Result matrix check (result[r][c] == -1) | Grid problems where output doubles as visited |
| Delayed removal (remove after level) | When multiple paths at the same level share nodes |

**The decision rule:** pick the lightest one that works. If the grid is already being modified, don't add a separate boolean[][]. If a set already exists for lookups, use remove() as visited.

### 1c. What is the lookup structure?

You need to check whether a candidate neighbor is valid. Ask: "how do I verify that a potential next state actually exists?"

| Input Format | Lookup Structure |
|---|---|
| Adjacency list given | adj.get(node), neighbors are pre-collected |
| Adjacency matrix given | Scan row matrix[node][0..N-1] for 1s |
| Grid given | Direction array + bounds check |
| Word list given | HashSet of valid words, set.contains() in O(1) |
| No explicit structure | Define the rule: what makes two states neighbors? |

### 1d. What is the result?

What does the problem want back? Ask: "what am I collecting during or after the BFS?"

| Result Type | Examples |
|---|---|
| A count (int) | Number of components, shortest distance, minimum steps |
| A boolean | Does a path exist? Is there a cycle? |
| A modified grid | Flood fill, surrounded regions |
| A matrix of distances | 01 Matrix |
| A list of paths | Word Ladder II |

---

## Stage 2: How Do I Find Neighbors? (The Part That Changes)

This is the ONLY part that differs between problems. Everything else is the same BFS skeleton. Ask: "from my current state, what are ALL possible next states?"

### Neighbor-finding patterns:

**Grid (4-directional):**
```java
int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
for (int[] d : dirs) {
    int newRow = row + d[0];
    int newCol = col + d[1];
    if (inBounds && validCell) { ... }
}
```

**Adjacency list:**
```java
for (int neighbor : adj.get(node)) {
    if (!visited[neighbor]) { ... }
}
```

**Adjacency matrix:**
```java
for (int j = 0; j < n; j++) {
    if (matrix[node][j] == 1 && !visited[j]) { ... }
}
```

**Implicit graph (words - character replacement):**
```java
char[] chars = word.toCharArray();
for (int j = 0; j < chars.length; j++) {
    char original = chars[j];
    for (char c = 'a'; c <= 'z'; c++) {
        if (c == original) continue;
        chars[j] = c;
        String newWord = new String(chars);
        if (set.contains(newWord)) { ... }
    }
    chars[j] = original;    // restore
}
```

**Implicit graph (numbers - digit manipulation):**
```java
// e.g., Open the Lock: turn each digit up or down
for (int i = 0; i < 4; i++) {
    char[] next = current.toCharArray();
    next[i] = (next[i] == '9') ? '0' : (char)(next[i] + 1);  // turn up
    // check if new String(next) is valid
    next[i] = (original == '0') ? '9' : (char)(original - 1);  // turn down
    // check again
}
```

**The decision rule:** the problem's structure tells you which pattern. Grid = directions. Explicit graph = adjacency access. Word/string/state problem = generate candidates and check validity.

---

## Stage 3: Do I Need Level Counting? (The Size Snapshot Decision)

Ask: "does the ANSWER depend on which level a node was found at?"

### YES, use the size snapshot when:
- "How many steps/minutes/moves?" (rotting oranges, word ladder)
- "What is the shortest distance?" (01 matrix with global counting)
- "Process all nodes at depth exactly K"
- "Stop after the first level where X is found" (word ladder II)
- "Delayed removal: remove visited words after each level" (word ladder II)

### NO, skip the snapshot when:
- "Does a path exist?" (just reachability)
- "How many components?" (counting starts, not levels)
- "Fill/paint all reachable cells" (flood fill)
- Per-node distance with parent + 1 formula (01 matrix alternative)

### When YES, the skeleton adds this:
```java
while (!queue.isEmpty()) {
    int size = queue.size();        // snapshot: this level's batch
    for (int i = 0; i < size; i++) {
        // process one entry from THIS level
        // anything enqueued goes to the NEXT level
    }
    // one full level done
    counter++;
}
```

### When NO, the skeleton is simpler:
```java
while (!queue.isEmpty()) {
    // poll and process one entry
    // no batching needed
}
```

---

## Stage 4: Assemble (Plug Everything In)

Now you have all the answers. Assemble them into the skeleton:

```
SETUP:
  Create the queue (type decided in stage 1a).
  Create the visited mechanism (type decided in stage 1b).
  Create the lookup structure (type decided in stage 1c).
  Initialize the result (type decided in stage 1d).

SEED:
  Put the starting point(s) in the queue.
  Mark the starting point(s) as visited.
  (Multi-source? Seed ALL sources before the loop.)

LOOP:
  while queue is not empty [and any extra stop condition]:
    [if level counting: size = queue.size()]
    [if level counting: for i from 0 to size-1:]
      Poll one entry.
      Check: is this the answer? If yes, handle it.
      Find neighbors (stage 2 method).
      For each valid, unvisited neighbor:
        Mark visited (or collect for delayed removal).
        Enqueue.
    [if level counting: counter++ after the for-loop]
    [if delayed removal: remove collected words after the for-loop]

RETURN:
  Return the result.
```

### The assembly checklist (verify before running):
- [ ] Starting point is marked visited BEFORE enqueueing
- [ ] Neighbor check includes bounds/validity AND visited check
- [ ] Each neighbor is marked visited BEFORE (or during) enqueueing
- [ ] If using character replacement: original character is RESTORED after inner loop
- [ ] If using level counting: counter starts at the right value (0 or 1, read the problem)
- [ ] If collecting paths: each neighbor gets a NEW COPY of the path, not the same reference
- [ ] If delayed removal: words collected during level, removed AFTER level

---

## Stage 5: Edge Cases (Check Before Submitting)

Run through these before hitting submit:

- [ ] **Start equals end:** does the problem allow this? What should you return?
- [ ] **End not reachable:** return 0, -1, false, or empty list?
- [ ] **End not in valid set:** return immediately or let BFS discover it?
- [ ] **Empty input:** empty graph, empty word list, single-node graph?
- [ ] **Single element:** one cell grid, one word in list?
- [ ] **Already visited start:** did you remove/mark the start so it's not revisited?
- [ ] **Disconnected graph:** does one BFS cover everything, or do you need the outer loop?

---

## Quick Reference: The Three Recognition Signals

Before even opening this blueprint, identify whether BFS is the right tool:

```
Signal 1: Does the problem ask for shortest / minimum / fewest?
  YES -> BFS candidate.
  NO  -> might be DFS, might be either. Check other signals.

Signal 2: Does each step cost the same (unweighted)?
  YES -> BFS confirmed (not Dijkstra).
  NO  -> weighted graph. Use Dijkstra or Bellman-Ford.

Signal 3: Can I define "next states" from any current state?
  YES -> I have a graph (possibly implicit). BFS applies.
  NO  -> this might not be a graph problem at all.

All three YES = BFS on a graph. Apply this blueprint.
```

---

## Quick Reference: Common BFS Variations

| Variation | What Changes | Example Problem |
|---|---|---|
| **Basic BFS** | Nothing, plain skeleton | Connected components |
| **Multi-source** | Seed ALL sources before loop | Rotting oranges, 01 matrix |
| **Level counting** | Size snapshot + counter | Rotting oranges, word ladder |
| **Parent tracking** | Queue holds {node, parent} | Undirected cycle detection |
| **Path collection** | Queue holds List, copy on branch | Word ladder II |
| **Delayed removal** | Remove visited after level, not during | Word ladder II |
| **Grid BFS** | Direction array for neighbors | Flood fill, surrounded regions |
| **Implicit graph** | Generate neighbors by rule | Word ladder, open the lock |

The skeleton is always the same. The variation is which knobs are turned on.

---

## The One-Sentence Summary

**BFS is a queue-based traversal that processes the closest nodes first. The queue (FIFO) is what makes it level-by-level. The visited mechanism is what makes it terminate. The neighbor-finding is what makes it specific to the problem. Everything else is the same nine-line skeleton you can type with your eyes closed.**

---

*Apply the five stages in order. Write your answers before coding. The blueprint is the thinking. The code is the transcription.*