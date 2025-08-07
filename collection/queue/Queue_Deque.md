
# 📚 Java DSA Handbook: Queue, PriorityQueue, Deque (ArrayDeque & LinkedList)

> **Hierarchy:** `Iterable → Collection → Queue → Deque` (Deque extends Queue)  
> **DSA theme:** Use FIFO for traversal/scheduling; use Deque for two-ended tricks (sliding window, monotonic queues); use PriorityQueue for greedy/min-max extraction.

---

## 1) Queue — When & Why (DSA Lens)
Use a **Queue (FIFO)** when you must process in arrival order:
- **BFS / level-order traversal** on graphs/trees
- **Topological sort (Kahn’s algorithm)**
- **Multi-source BFS** (flood fill, shortest steps in unweighted grid)
- **Task scheduling / buffer pipelines**

### Key Characteristics
- **FIFO**: enqueue at tail, dequeue at head
- **No random access** (no indexing)
- Backed by **array (circular buffer)** or **linked nodes**
- Queue interface core ops: `offer`, `poll`, `peek` (`add`, `remove`, `element` throw on failure)

### Constructors / Initialization
- **Array-backed (recommended):**
  ```java
  Queue<Integer> q1 = new ArrayDeque<>();
  Queue<Integer> q2 = new ArrayDeque<>(64); // initial capacity
  ```
- **Linked-list–backed:**
  ```java
  Queue<Integer> q3 = new LinkedList<>();
  ```

### Internal Working (Quick Intuition)

**ArrayDeque (circular array):**
```
 indices:  0   1   2   3   4   5   6
 buffer:  [_,  A,  B,  C,  _,  _,  _]
 head ->  1
 tail ->              4   (next insert at 4)
 wrap via (index + 1) % capacity
```
- Enqueue moves `tail`; Dequeue moves `head`; **no shifting**.

**LinkedList:**
```
 head -> [A] <-> [B] <-> [C] <- tail
```
- Nodes for each element; O(1) at ends; more memory per element.

### Methods & Time Complexity (Queue)
| Method                       | ArrayDeque | LinkedList | Description                                   |
|-----------------------------|------------|------------|-----------------------------------------------|
| `offer(e)` / `add(e)`       | O(1)*      | O(1)       | Enqueue at tail (*amortized for ArrayDeque)   |
| `poll()` / `remove()`       | O(1)       | O(1)       | Dequeue at head                               |
| `peek()` / `element()`      | O(1)       | O(1)       | Read head without removal                     |
| `contains(x)`               | O(n)       | O(n)       | Linear scan                                   |
| `size()`, `isEmpty()`       | O(1)       | O(1)       |                                               |
| `clear()`                   | O(n)       | O(n)       | Null out array / unlink nodes                 |

> *ArrayDeque amortizes to O(1) due to occasional resizes.

### Advantages & Limitations
- **ArrayDeque:** ✅ fast, cache-friendly, no node overhead; ❌ no `null`, resizes (amortized).
- **LinkedList:** ✅ steady O(1) ends, no resize; ❌ node overhead, poorer locality.

---

## 2) PriorityQueue — When & Why
Use a **PriorityQueue** when you need quick access to the **smallest / largest** item (min-heap by default):
- **Dijkstra**, **Prim**, **A*** (with custom comparator)
- **Top-K** (k largest/smallest), **merge k sorted lists**
- **Scheduling** by priority

### Key Characteristics
- Backed by **binary heap** (array)
- Default **min-heap**; use comparator for max-heap or custom order
- Not sorted overall; only the root obeys global min/max

### Constructors
```java
PriorityQueue<Integer> pq1 = new PriorityQueue<>(); // min-heap
PriorityQueue<Integer> pq2 = new PriorityQueue<>(Comparator.reverseOrder()); // max-heap
PriorityQueue<Node> pq3 = new PriorityQueue<>(Comparator.comparingInt(n -> n.dist));
PriorityQueue<Integer> pq4 = new PriorityQueue<>(initialCapacity);
PriorityQueue<Integer> pq5 = new PriorityQueue<>(initialCapacity, comparator);
```

### Internal Working (Heap Picture)
```
array: [ _, 1, 4, 7, 9, 6, 8 ]
             1
           /   \
          4     7
         / \   /
        9  6  8
```
- `offer`: append then **sift-up**
- `poll`: swap root with last, shrink, **sift-down**

### Methods & Time Complexity (PriorityQueue)
| Method            | Time      | Description                                     |
|------------------|-----------|-------------------------------------------------|
| `offer(e)`       | O(log n)  | Insert + sift-up                                |
| `poll()`         | O(log n)  | Remove root + sift-down                         |
| `peek()`         | O(1)      | Read root                                       |
| `remove(x)`      | O(n)      | Linear search + fix heap                        |
| `contains(x)`    | O(n)      | Linear scan                                     |
| `size()`, `isEmpty()` | O(1) |                                                 |

**DSA Notes:** For k-way merge, keep heap size ≤ k; for Top-K, often store k elements (max-heap/min-heap depending on problem).

---

## 3) Deque — When & Why
Use a **Deque** when you need both ends:
- **Sliding window min/max** (monotonic deque)
- **Two-pointer / palindrome** checks with ends
- **Implement stack + queue** interchangeably

### Key Characteristics
- Supports **front/back** insertions/removals in O(1)**amortized**
- Implemented by **ArrayDeque (recommended)** or **LinkedList**

### Constructors
```java
Deque<Integer> d1 = new ArrayDeque<>();
Deque<Integer> d2 = new ArrayDeque<>(64);
Deque<Integer> d3 = new LinkedList<>();
```

### Methods & Time Complexity (Deque)
| Method                         | ArrayDeque | LinkedList | Description                          |
|--------------------------------|------------|------------|--------------------------------------|
| `offerFirst/Last(e)`           | O(1)*      | O(1)       | Insert at front/back                 |
| `pollFirst/Last()`             | O(1)       | O(1)       | Remove at front/back                 |
| `peekFirst/Last()`             | O(1)       | O(1)       | Read ends                            |
| `push(e)` / `pop()` (stack)    | O(1)*      | O(1)       | Use Deque as stack                   |
| `iterator()` (front→back)      | O(n)       | O(n)       |                                      |
| `descendingIterator()`         | O(n)       | O(n)       |                                      |
| `contains(x)`                  | O(n)       | O(n)       |                                      |

> ArrayDeque forbids `null`; LinkedList allows it.

### Pros / Cons
- **ArrayDeque:** ✅ fastest in practice, stack+queue in one; ❌ no `null`, resizing.
- **LinkedList:** ✅ simple O(1) ends; ❌ node/G.C. overhead, slower in hotspots.

---

## DSA Playbook: Quick Picks
- **BFS / Level order** → `ArrayDeque` (or `LinkedList` if you must)
- **Sliding Window Max/Min** → `ArrayDeque` with monotonic policy
- **Greedy / Top-K / Shortest paths** → `PriorityQueue`
- **Deque-as-Stack** → `ArrayDeque` (faster than `Stack`)

