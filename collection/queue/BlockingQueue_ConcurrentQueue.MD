
# 🧵 Java Concurrency Handbook: Blocking & Concurrent Queues

> **Use these in multi-threaded producer–consumer pipelines.** They provide blocking semantics, back-pressure, or lock-free performance. Not typical for competitive DSA, but crucial for real systems.

---

## 1) BlockingQueue — When & Why
- **Blocks** producers on **full**, consumers on **empty**.
- Great for **producer–consumer**, **pipelines**, **throttling**, and **back-pressure**.
- Key blocking ops: `put(e)`, `take()`. Timed: `offer(e, timeout, unit)`, `poll(timeout, unit)`.

### Interface Characteristics
- In `java.util.concurrent`
- Prohibits `null` elements
- Thread-safe; memory visibility handled
- Varied capacity & ordering semantics by implementation

---

## 2) ArrayBlockingQueue
**Bounded**, array-backed FIFO with a **single lock** but separate conditions for put/take.

### Constructors
```java
ArrayBlockingQueue<Integer> q1 = new ArrayBlockingQueue<>(capacity);
ArrayBlockingQueue<Integer> q2 = new ArrayBlockingQueue<>(capacity, fair); // fairness flag
ArrayBlockingQueue<Integer> q3 = new ArrayBlockingQueue<>(capacity, fair, new ArrayList<>()); // initial contents
```

### When & Why
- Fixed-size buffers; predictable memory
- Optional **fairness** prevents starvation (slower under high contention)

### High-Level Complexity / Behavior
- `put`/`take`: O(1) average; can block
- Single lock → potential contention vs. linked variant

---

## 3) LinkedBlockingQueue
Linked nodes; **optionally bounded** (default unbounded). Two-lock design (putLock/takeLock).

### Constructors
```java
LinkedBlockingQueue<Integer> q1 = new LinkedBlockingQueue<>();          // unbounded
LinkedBlockingQueue<Integer> q2 = new LinkedBlockingQueue<>(capacity);  // bounded
LinkedBlockingQueue<Integer> q3 = new LinkedBlockingQueue<>(collection);
```

### When & Why
- General-purpose PC queues; smoother throughput than single-lock
- Beware unbounded default → risk of OOM if producers outrun consumers

### Behavior
- `put`/`take`: O(1) average; blocking semantics dominate

---

## 4) PriorityBlockingQueue
**Unbounded**, heap-based priority ordering (natural or comparator). No fairness guarantees.

### Constructors
```java
PriorityBlockingQueue<Integer> pq1 = new PriorityBlockingQueue<>();
PriorityBlockingQueue<Integer> pq2 = new PriorityBlockingQueue<>(initialCapacity);
PriorityBlockingQueue<Task>   pq3 = new PriorityBlockingQueue<>(11, Comparator.comparingInt(Task::priority));
```

### When & Why
- Priority task schedulers/dispatchers

### Behavior
- `put`/`take`: ~O(log n) due to heap
- `remove(x)` / `contains`: O(n)

---

## 5) SynchronousQueue
**Zero-capacity** handoff; every `put` must rendezvous with a `take`.

### Constructors
```java
SynchronousQueue<Integer> s1 = new SynchronousQueue<>();     // non-fair
SynchronousQueue<Integer> s2 = new SynchronousQueue<>(true); // fair
```

### When & Why
- Direct handoff; threads exchange without buffering
- Used under the hood by `Executors.newCachedThreadPool()`

### Behavior
- Ops are O(1) but **block until paired**

---

## 6) DelayQueue
Holds elements implementing `Delayed`; available only after delay expires. Internally a time-ordered priority queue.

### Constructors
```java
DelayQueue<DelayedTask> dq = new DelayQueue<>();
```

### When & Why
- Retries, scheduled work, time-based caches

### Behavior
- `put` ~O(log n); `take` blocks until earliest due is ready

---

## 7) ConcurrentLinkedQueue (Non-blocking)
Lock-free, CAS-based **unbounded** queue; no blocking methods.

### Constructors
```java
ConcurrentLinkedQueue<Integer> clq1 = new ConcurrentLinkedQueue<>();
ConcurrentLinkedQueue<Integer> clq2 = new ConcurrentLinkedQueue<>(collection);
```

### When & Why
- High-throughput, many-producer/consumer scenarios where **throughput > strict back-pressure**

### Behavior
- `offer`/`poll`: amortized O(1); may spin under contention
- No blocking; consumers must poll

---
## ⏱️ Time Complexity Tables

> Notes:
> - “Blocks” means the call may wait until it can proceed.
> - For concurrent queues, `size()` and iterators are typically **weakly consistent** and may be O(n).

### ArrayBlockingQueue

| Operation                          | Average     | Worst       | Notes                                     |
|------------------------------------|-------------|-------------|-------------------------------------------|
| `put(e)` (blocking)                | O(1)        | O(1)        | Blocks if full                            |
| `offer(e)`                         | O(1)        | O(1)        | Non-blocking; fails if full               |
| `offer(e, t, unit)`                | O(1)        | O(1)        | Waits up to timeout if full               |
| `take()` (blocking)                | O(1)        | O(1)        | Blocks if empty                           |
| `poll()`                           | O(1)        | O(1)        | Non-blocking; returns null if empty       |
| `poll(t, unit)`                    | O(1)        | O(1)        | Waits up to timeout if empty              |
| `peek()`                           | O(1)        | O(1)        |                                           |
| `remove(x)` / `contains(x)`        | O(n)        | O(n)        | Linear scan                               |
| `size()`                           | O(1)        | O(1)        |                                           |
| `iterator()`                       | O(n)        | O(n)        | Weakly consistent                         |

### LinkedBlockingQueue

| Operation                          | Average     | Worst       | Notes                                     |
|------------------------------------|-------------|-------------|-------------------------------------------|
| `put(e)` (blocking)                | O(1)        | O(1)        | Blocks if bounded & full                  |
| `offer(e)`                         | O(1)        | O(1)        | For unbounded, typically succeeds         |
| `offer(e, t, unit)`                | O(1)        | O(1)        | Waits up to timeout if bounded & full     |
| `take()` (blocking)                | O(1)        | O(1)        | Blocks if empty                           |
| `poll()`                           | O(1)        | O(1)        | Non-blocking                              |
| `poll(t, unit)`                    | O(1)        | O(1)        | Waits up to timeout if empty              |
| `peek()`                           | O(1)        | O(1)        |                                           |
| `remove(x)` / `contains(x)`        | O(n)        | O(n)        | Linear scan                               |
| `size()`                           | O(1)\*      | O(n)\*      | May traverse; impl-dependent              |
| `iterator()`                       | O(n)        | O(n)        | Weakly consistent                         |

\* Some JDKs compute size with a counter (O(1)), others may traverse (O(n)) under contention.

### PriorityBlockingQueue

| Operation                          | Average     | Worst       | Notes                                     |
|------------------------------------|-------------|-------------|-------------------------------------------|
| `put(e)` / `offer(e)`              | O(log n)    | O(log n)    | Heap insert                               |
| `take()` / `poll()`                | O(log n)    | O(log n)    | Remove root                               |
| `peek()`                           | O(1)        | O(1)        |                                           |
| `offer(e, t, unit)` / `poll(t,unit)`| O(log n)   | O(log n)    | With timeout                              |
| `remove(x)` / `contains(x)`        | O(n)        | O(n)        | Linear scan                               |
| `size()`                           | O(1)        | O(1)        |                                           |
| `iterator()`                       | O(n)        | O(n)        | Not sorted; weakly consistent             |

### SynchronousQueue

| Operation                          | Average     | Worst       | Notes                                              |
|------------------------------------|-------------|-------------|----------------------------------------------------|
| `put(e)`                           | O(1)        | O(1)        | **Zero capacity**; blocks until a consumer matches |
| `offer(e)`                         | O(1)        | O(1)        | Succeeds only if a consumer is waiting             |
| `take()`                           | O(1)        | O(1)        | Blocks until a producer matches                    |
| `poll()`                           | O(1)        | O(1)        | Succeeds only if a producer is waiting             |
| Timed variants                     | O(1)        | O(1)        | Wait up to timeout                                 |
| `size()`, `iterator()`             | O(1)        | O(1)        | Always ~0; handoff only                            |

### DelayQueue

| Operation                          | Average     | Worst       | Notes                                     |
|------------------------------------|-------------|-------------|-------------------------------------------|
| `put(e)` / `offer(e)`              | O(log n)    | O(log n)    | Ordered by delay (min-heap)               |
| `take()` / `poll()`                | O(log n)    | O(log n)    | Blocks until head delay expires           |
| `peek()`                           | O(1)        | O(1)        |                                           |
| `remove(x)` / `contains(x)`        | O(n)        | O(n)        | Linear scan                               |
| `size()`                           | O(1)        | O(1)        |                                           |
| `iterator()`                       | O(n)        | O(n)        | Weakly consistent                         |

### ConcurrentLinkedQueue (non-blocking)

| Operation                          | Average          | Worst       | Notes                                     |
|------------------------------------|------------------|-------------|-------------------------------------------|
| `offer(e)`                         | O(1) amortized   | O(1)        | Lock-free CAS                             |
| `poll()`                           | O(1) amortized   | O(1)        | Lock-free CAS                             |
| `peek()`                           | O(1)             | O(1)        |                                           |
| `remove(x)` / `contains(x)`        | O(n)             | O(n)        | Linear scan                               |
| `size()`                           | O(n)             | O(n)        | May traverse due to lock-free nature      |
| `iterator()`                       | O(n)             | O(n)        | Weakly consistent                         |

---

## Choosing Quickly
- **Fixed, bounded buffer + fairness** → `ArrayBlockingQueue`
- **General PC queue (bounded/unbounded)** → `LinkedBlockingQueue`
- **Prioritized work** → `PriorityBlockingQueue`
- **Direct handoff / rendezvous** → `SynchronousQueue`
- **Time-delayed work** → `DelayQueue`
- **Lock-free high-throughput** → `ConcurrentLinkedQueue`