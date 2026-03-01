# Heap Data Structure — Complete Mastery Guide

---

### 1. Big Picture: Why Care About Heaps?

A **heap** is a special tree-based data structure that gives you very fast access to the minimum or maximum element while still allowing efficient insertions and deletions. It is the standard way to implement a **priority queue** and appears frequently in interviews and real-world systems:

- Dijkstra’s shortest path
- Prim’s minimum spanning tree
- Heap sort
- Top‑K problems (Top K largest/smallest)
- Streaming median (two-heaps pattern)
- Task/job scheduling
- Event simulation, load balancing

The goal of this guide is to take you from beginner to mastery: clear intuition, implementation details, complexities, and interview usage, in a clean, logical order.

---

### 2. Formal Definition

A (binary) **heap** is:

> A **complete binary tree** that satisfies the **heap property**.

Two essential parts:

1. **Structure**: complete binary tree
2. **Order**: heap property

If either is missing, it’s not a (binary) heap.

---

### 3. Complete Binary Tree

A binary tree is **complete** if:

- Every level is completely filled
- Except possibly the last level
- The last level is filled **from left to right** (no missing nodes on the left side)

### Examples

**Valid complete binary tree:**

text
10
/
20 30
/
40 50

text

This is complete: levels are filled, and the last level (40, 50) is filled from left to right.

**Invalid complete binary tree:**

text
10
/
20 30

40

text

This is not complete: the left child of 20 is missing but the right child exists, violating the “fill from left to right” rule.

The completeness property is what allows us to store heaps efficiently in arrays.

---

### 4. Heap Property

The **heap property** defines how nodes are ordered relative to their children.

### Min-Heap Property

For a **min-heap**:

- For every node: `node value ≤ value of both children` (if they exist)
- The **minimum** element of the entire tree is at the **root**

Example:

text
10
/
20 30
/
40 50

text

Here, `10 ≤ 20, 30` and `20 ≤ 40, 50`. Every parent is smaller than or equal to its children, so this is a valid min-heap.

### Max-Heap Property

For a **max-heap**:

- For every node: `node value ≥ value of both children` (if they exist)
- The **maximum** element of the entire tree is at the **root**

Example:

text
50
/
30 40
/
10 20

text

Here, `50 ≥ 30, 40` and `30 ≥ 10, 20`. Every parent is greater than or equal to its children, so this is a valid max-heap.

### Local vs Global Ordering

Important:

- Heaps only guarantee **local** ordering (parent vs children)
- They do **not** guarantee the whole tree is sorted
- You can always get the min/max quickly, but you cannot quickly search for an arbitrary value

---

### 5. Types of Heaps

### 5.1 Min-Heap

- Root holds the **smallest** element in the heap
- Useful when you need to repeatedly get/remove the **minimum**

Typical uses:

- Dijkstra’s algorithm (priority = current shortest distance)
- Prim’s algorithm
- Priority queue where “smaller key = higher priority”
- Merge K sorted lists/arrays
- Scheduling by earliest deadline

### 5.2 Max-Heap

- Root holds the **largest** element in the heap
- Useful when you need to repeatedly get/remove the **maximum**

Typical uses:

- Top K largest elements
- Priority queue where “larger key = higher priority”
- Heap sort (build max-heap then repeatedly extract max)
- Real-time leaderboards

---

### 6. Heap vs Binary Tree vs BST

### Binary Tree

- Any tree with at most 2 children per node
- No requirements on shape or order
- Too general to guarantee any performance

### Binary Search Tree (BST)

- Ordering: `left subtree < node < right subtree`
- Efficient **search** (average O(log n) if balanced)
- Shape is not necessarily complete
- Supports ordered traversal (inorder traversal is sorted)

### Heap

- Shape: **must** be a **complete** binary tree
- Order: only **parent-child** ordering (min-heap or max-heap)
- No guarantee that left subtree elements < right subtree elements
- **Search** is O(n) in worst case
- Optimized for:
    - **Fast access to min/max** (O(1) peek)
    - **Fast insert/delete-root** (O(log n))

**Key takeaway:**

- Use **BST** (or balanced trees) for **fast search** and **sorted traversal**
- Use **heap** for **fast min/max** and **priority queue behavior**

---

### 7. Array Representation of a Heap

Most practical heaps are implemented using an **array**, not pointer-based nodes.

### 7.1 Why Use an Array?

Because the tree is **complete**:

- Nodes are packed level by level from left to right
- There are no gaps
- We can map nodes to array indices without storing explicit pointers

This makes implementation:

- Memory-efficient (no pointers)
- Cache-friendly
- Simple index arithmetic

### 7.2 Index Relationships (0-based indexing)

For an array `heap[]` and an index `i`:

- **Parent index**: `parent(i) = (i - 1) / 2` (integer division)
- **Left child index**: `left(i) = 2 * i + 1`
- **Right child index**: `right(i) = 2 * i + 2`

Example:

Array representation:

`[10, 20, 30, 40, 50]`

Indexes:

` 0   1   2   3   4`

- Node at index 0 (10) is root
- Children of index 0: indices 1 and 2 → 20 and 30
- Parent of index 3: `(3 - 1) / 2 = 1` → value 20

We also maintain:

- `size` = current number of elements in the heap

The array may have extra capacity beyond `size`.

---

### 8. Core Operations Overview

Key heap operations:

- **Insert** (push)
- **Extract-Min / Extract-Max** (delete root)
- **Peek** (get min/max without removing)
- **Heapify Up** (bubble-up / sift-up)
- **Heapify Down** (bubble-down / sift-down)
- **Build Heap** from an array
- **Increase/Decrease Key** (update priority)
- **Delete arbitrary element**

We’ll describe them mainly for a **min-heap**; a max-heap is symmetric with reversed comparisons.

---

### 9. Insert Operation (Min-Heap) — Master This

### 9.1 Goal

Insert a new element into the heap while preserving:

- **Complete tree shape**
- **Min-heap property**

### 9.2 High-Level Steps

1. **Place** the new element at the **end** of the array (next free index).
2. **Heapify Up** (bubble-up) from that index:
    - While the new element is smaller than its parent, swap them.

### 9.3 Step-by-Step Example

Current min-heap array:

`[10, 20, 30, 40, 50]`

Tree:

text
10
/
20 30
/
40 50

text

Insert `15`:

1. Append at the end:

   `[10, 20, 30, 40, 50, 15]`

   Tree:

text
10
/  \
20    30
/ \ /
40 50 15

text

2. Heapify Up from index 5 (value 15):

- Parent index = `(5 - 1) / 2 = 2`, value = 30
- Compare: 15 < 30 → violation for min-heap → swap:

  `[10, 20, 15, 40, 50, 30]`

- Now 15 is at index 2, parent index = `(2 - 1) / 2 = 0`, value = 10
- Compare: 15 < 10? No → stop

Final heap:

text
10
/
20 15
/
40 50

30

text

(Exact shape may differ depending on visualization, but the array is heap-valid.)

### 9.4 Heapify Up Logic (Min-Heap)

Pseudocode:

```text
function heapifyUp(i):
    while i > 0:
        p = parent(i)
        if heap[i] < heap[p]:
            swap(heap[i], heap[p])
            i = p
        else:
            break
```                       
For a max-heap, replace heap[i] < heap[p] with heap[i] > heap[p].

### 9.5 Complexity
Height of heap with n elements: O(log n)

New node may bubble from leaf to root

Time: O(log n)

Extra space: O(1) (non-recursive version)

---

# 10. Extract-Min / Extract-Max (Delete Root)
This is the main delete operation.

### 10.1 Goal
Remove the root (min or max) while preserving:

Complete tree shape

Heap property

### 10.2 High-Level Steps (Min-Heap)
Store the root value (to return).

Move the last element in the array to the root position (index 0).

Decrease size by 1.

Heapify Down from the root.

### 10.3 Step-by-Step Example
Min-heap:

[10, 20, 15, 40, 50, 30]

Tree:

```text
    10
   /  \
  20   15
 / \   /
40 50 30

Extract-min:

 1. Save min = 10.

 2. Move last element (30) to root, reduce size:

    [30, 20, 15, 40, 50]

Tree:
    30
   /  \
 20    15
/ \
40 50

3. Heapify Down from index 0 (value 30):

- Children: index 1 (20), index 2 (15)
- Smallest child = 15 at index 2
- Compare 30 > 15 → violation → swap:

  `[15, 20, 30, 40, 50]`

- Now at index 2 (value 30); children indices = 5, 6 (≥ size), so stop

Resulting heap:

`[15, 20, 30, 40, 50]`

`extractMin` returns `10`.
```
### 10.4 Heapify Down Logic (Min-Heap)

Pseudocode:

```text
function heapifyDown(i):
 while true:
     left  = 2 * i + 1
     right = 2 * i + 2
     smallest = i

     if left < size and heap[left] < heap[smallest]:
         smallest = left
     if right < size and heap[right] < heap[smallest]:
         smallest = right

     if smallest != i:
         swap(heap[i], heap[smallest])
         i = smallest
     else:
         break
```         
For a max-heap, choose the largest child (use > instead of <).

### 10.5 Complexity
Heapify down goes from root down to at most last level

Height = O(log n)

Time: O(log n)

Extra space: O(1) (non-recursive version)

---

# 11. Peek Operation
Peek returns the root (min or max) without removing it.

For min-heap: getMin() = heap[0]

For max-heap: getMax() = heap[0]

Complexity:

Time: O(1)

Space: O(1)

---

# 12. Heapify (Local Fixing)
“Heapify” means: given that the heap property may be violated at some index, restore the property for the whole subtree rooted at that index.

Two directions:

Heapify Up (after insert or decrease-key in min-heap)

Heapify Down (after extract-root or increase-key in min-heap)

Both have:

Time: O(log n)

Space: O(1) for iterative implementations

---

# 13. Build Heap From an Array
We often start with an unsorted array and want to transform it into a valid heap.

### 13.1 Naive Method
Start with an empty heap.

Insert elements one by one using the heap insert operation.

Complexity:

Each insert: O(log n) (worst case)

n elements total → O(n log n)

### 13.2 Optimal Bottom-Up Method (Important)
Key idea:

Treat the array as the level-order representation of a complete binary tree.

All leaves (bottom level) are already valid heaps (size 1).

Start from the last non-leaf node and call heapifyDown on each node going backwards to index 0.

For array heap[] of size n:

Last index = n - 1

Last non-leaf index = (n - 2) / 2 (integer division)

Pseudocode:

```text
function buildHeap(heap[], n):
    for i from (n - 2) / 2 down to 0:
        heapifyDown(i)
Result: entire array becomes a valid min-heap (or max-heap, if heapify uses max-heap comparisons).
```
Complexity:

This bottom-up build runs in linear time.

Time: O(n)

Extra space: O(1)

---

# 14. Time and Space Complexity Summary

### 14.1 Operation Complexity Table
| Operation                          | Time Complexity | Space Complexity | Notes                                      |
|-----------------------------------|-----------------|------------------|--------------------------------------------|
| Insert                            | O(log n)       | O(1)             | Heapify up along tree height              |
| Extract Min / Extract Max        | O(log n)       | O(1)             | Heapify down from root                    |
| Peek (getMin/getMax)             | O(1)           | O(1)             | Root is always min/max                    |
| Heapify Up                       | O(log n)       | O(1)             | Worst case up to root                     |
| Heapify Down                     | O(log n)       | O(1)             | Worst case to leaf                        |
| Build Heap (bottom-up)           | O(n)           | O(1) extra       | Start from last non-leaf                  |
| Build Heap (naive inserts)       | O(n log n)     | O(1) extra       | Insert each element                       |
| Search for arbitrary value       | O(n)           | O(1)             | Not ordered for searching                 |
| Delete arbitrary element         | O(log n)       | O(1)             | Swap with last, then heapify up/down     |
| Increase Key (min-heap)          | O(log n)       | O(1)             | Usually heapify down                     |
| Decrease Key (min-heap)          | O(log n)       | O(1)             | Usually heapify up                       |
| Get size                         | O(1)           | O(1)             | Stored in a variable                      |
| Check if empty                   | O(1)           | O(1)             | size == 0                                 |

### 14.2 Overall Space Complexity
Data storage: O(n) for n elements in the array.

Extra fields (size, capacity, etc.): O(1).

Total: O(n).

---

# 15. Java-Style Min-Heap Implementation
Below is a clear, commented implementation of a min-heap using an array.
```
java
class MinHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.size = 0;
    }

    // Index helpers
    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i)   { return 2 * i + 1; }
    private int right(int i)  { return 2 * i + 2; }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap;
    }

    public void insert(int value) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }
        heap[size] = value;       // Place at the end
        heapifyUp(size);          // Fix heap upwards
        size++;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int p = parent(index);
            if (heap[p] > heap[index]) {
                swap(p, index);
                index = p;
            } else {
                break;
            }
        }
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int min = heap;                 // Root element
        heap = heap[size - 1];          // Move last to root
        size--;
        heapifyDown(0);                    // Fix heap downwards
        return min;
    }

    private void heapifyDown(int index) {
        while (true) {
            int l = left(index);
            int r = right(index);
            int smallest = index;

            if (l < size && heap[l] < heap[smallest]) {
                smallest = l;
            }
            if (r < size && heap[r] < heap[smallest]) {
                smallest = r;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    public void decreaseKey(int index, int newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (newValue > heap[index]) {
            throw new IllegalArgumentException("New value is greater than current value");
        }
        heap[index] = newValue;
        heapifyUp(index);
    }

    public void increaseKey(int index, int newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (newValue < heap[index]) {
            throw new IllegalArgumentException("New value is smaller than current value");
        }
        heap[index] = newValue;
        heapifyDown(index);
    }

    public void delete(int index) {
        // One strategy: decrease key to -infinity, then extractMin
        decreaseKey(index, Integer.MIN_VALUE);
        extractMin();
    }

    private void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }
}
```
To make a max-heap, invert the comparison signs (> ↔ <) in heapifyUp, heapifyDown, and adjust decreaseKey / increaseKey logic accordingly.

---

# 16. Priority Queue and Heap
A priority queue is an abstract data type with operations like:

insert(item, priority)

extractHighestPriority()

peekHighestPriority()

It does not specify the underlying implementation.

A heap is a common concrete implementation of a priority queue:

For a min-priority queue, use a min-heap.

For a max-priority queue, use a max-heap.

Operations:

Insert: O(log n)

Extract highest priority (root): O(log n)

Peek highest priority: O(1)

---

# 17. Advanced Heap Variants (Names Only)
Beyond binary heaps, there are more advanced variants (mostly theoretical or library-level):

Binomial heap

Fibonacci heap

Pairing heap

Min-max heap (supports both min and max efficiently)

In most coding interviews, you only need deep understanding of binary heaps. Knowing the names and that some of these improve operations like decrease-key is usually enough.

---

# 18. Heap vs Other Data Structures
### 18.1 Heap vs Balanced BST (e.g., AVL, Red-Black Tree)
Operation	Heap	Balanced BST
Search by key	O(n)	O(log n)
Insert	O(log n)	O(log n)
Delete by key	O(log n)	O(log n)
Find min/max	O(1)	O(log n)
Iterate in order	O(n log n)*	O(n)
*For heap: repeatedly extract min/max gives a sorted sequence but costs O(n log n).

Use heap when you primarily care about min/max.
Use balanced BST when you care about search and sorted traversal.

### 18.2 Heap vs Sorted Array
Operation	Heap	Sorted Array
Find min (front)	O(1)	O(1)
Insert	O(log n)	O(n)
Delete min	O(log n)	O(n)
Random access	O(1)	O(1)
Binary search	O(n)	O(log n)
Sorted arrays are great for search but terrible at updates (insert/delete).
Heaps give a better balance when you need frequent min/max with updates.

---

# 19. Advantages and Disadvantages
### 19.1 Advantages of Heaps
Fast min/max access: O(1) peek

Efficient insertion and root deletion: O(log n)

Space-efficient: array-based, no pointer overhead

Perfect fit for priority queues

Useful in many algorithms: Dijkstra, Prim, heap sort, top‑K, streaming problems

### 19.2 Disadvantages of Heaps
Slow search for arbitrary elements: O(n)

Not suitable when you frequently need:

Arbitrary key lookup

Ordered traversal

Range queries

To get all elements in sorted order, need repeated extraction: O(n log n)

---

# 20. When to Use Heaps
Use a heap when:

You frequently need to get/remove the smallest or largest element.

You need a priority queue structure.

Common problem patterns:

Top K largest/smallest elements

Kth smallest/largest element

Merge K sorted lists/arrays

Streaming median (two heaps)

Event scheduling, job processing by priority

---

# 21. When to Avoid Heaps
Avoid heaps when:

You need fast search by arbitrary key (use hash maps or BSTs).

You need to repeatedly iterate in sorted order (use balanced BST or keep data sorted).

You need to support ordered operations like:

Range queries

Predecessor/successor

In-order traversal

---

# 22. Classic Interview Patterns and Questions

### 22.1 Two-Heap Pattern (Median of Data Stream)
Use a max-heap to store the lower half of numbers.

Use a min-heap to store the upper half of numbers.

Maintain sizes so that:

Either both halves have equal size, or

One heap has one extra element

Median: If sizes equal: average of roots

Otherwise: root of the larger heap

Operations:

Insert: O(log n)

Get median: O(1)

### 22.2 Top-K Elements
Top K largest:

Maintain a min-heap of size K

For each new element:

If heap size < K: insert

Else if element > root: pop root, then insert element

At the end, heap contains K largest elements

Complexity: O(n log K).

Top K smallest:

Do the symmetric version with a max-heap.

### 22.3 Kth Smallest / Largest Element
You can adapt the Top-K approach:

Kth smallest:

Use a max-heap of size K

Maintain K smallest elements seen so far

Root will be the Kth smallest

Kth largest: Use a min-heap of size K

### 22.4 Merge K Sorted Lists
Maintain a min-heap of size K

Each heap entry stores:

The current element value

The list index (and/or node pointer)

Steps:

Initialize heap with first element of each list

Repeatedly extract min, append to result, and push next element from that list

Total complexity: O(N log K) where N is total number of elements

---

# 23. Interview Talking Points (What to Say)
When asked about heaps, key points to mention:

Definition: complete binary tree + heap property (min or max)

Implementation: typically an array; show index formulas for parent/children

Operations:

Insert: O(log n), heapify up

Extract-min / max: O(log n), heapify down

Peek: O(1)

Build heap bottom-up: O(n)

Limitations:

Search: O(n)

No full ordering

Use cases:

Priority queues

Dijkstra, Prim

Top-K, Kth smallest/largest

Median of data stream (two heaps)

Comparison:

vs BST: heap is better for min/max; BST is better for search and ordered traversal

If you can also walk through a small insert and extract-min example verbally, you will look very strong in interviews on this topic.

---

# 24. Quick Summary Cheat Sheet
Heap = complete binary tree + heap property

Implemented efficiently with an array

Index formulas (0-based):

parent(i) = (i - 1) / 2

left(i) = 2 * i + 1

right(i) = 2 * i + 2

Operations (min-heap):

Insert: O(log n)

Extract-min: O(log n)

Peek (min): O(1)

Build heap (bottom-up): O(n)

Use when:

Need frequent min/max

Need a priority queue

Solving top-K / Kth / Dijkstra / streaming median

Avoid when: You need fast search or sorted traversal