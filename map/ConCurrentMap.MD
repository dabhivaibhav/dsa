
# 🔒 Java Concurrent Maps

## 🔹 Why and When to Use Concurrent Maps?

In multi-threaded applications, regular `HashMap` is **not thread-safe**, leading to **data inconsistency** if accessed simultaneously by multiple threads. To solve this, Java provides **thread-safe map implementations** under the `java.util.concurrent` package:
- `ConcurrentMap`
- `ConcurrentHashMap`
- `ConcurrentSkipListMap`

These maps allow **safe concurrent access** without needing to wrap maps in `Collections.synchronizedMap()`.

---

## 🧮 Hashtable

### 🔹 When and Why to Use?
- It is a **legacy synchronized Map implementation**.
- Was used before `ConcurrentHashMap` existed.
- All methods are **synchronized**, meaning only one thread can access the map at a time.
- **Not used anymore** in modern code due to inefficiency.

### 🔑 Key Characteristics:
- ❌ **Does not allow null keys or null values**
- ✅ **Thread-safe**
- 🚫 **Inefficient** under high concurrency due to single lock on the entire map
- 🔄 Uses **linked list** to resolve collisions
- 🐢 Slower than `HashMap` because:
    - Every operation (`put`, `get`, `remove`) acquires a **global lock**
    - **Lock contention** leads to performance bottlenecks

---

## ⚙️ ConcurrentHashMap

### 🔹 When and Why to Use?
- Introduced to **solve inefficiencies of Hashtable**.
- Implements `ConcurrentMap`, which extends `Map`.
- Offers **fine-grained locking and high concurrency**.

---

### 🧪 Java 7: Segment-Based Locking

- Map is internally divided into **segments** (default 16).
- Each segment acts like a **mini HashMap** with its own lock.
- Multiple threads can access different segments **simultaneously**.
- **Read operations** do not require locks unless a **write occurs** in that segment.

---

### 🚀 Java 8: CAS and Node-Level Synchronization

- **Segments removed** for better scalability.
- Introduced **CAS (Compare-And-Swap)** algorithm and **synchronized blocks** only when necessary.

#### 🔍 CAS Explained:

- Say variable `X = 42`.
- Thread A wants to change `X` to `50`. It:
    1. Reads `X` as `42`.
    2. Checks if `X` is still `42` before updating.
    3. If yes, it **updates to 50**.
    4. If `X` changed meanwhile, it **fails and retries**.

🔁 This retry loop continues until it succeeds.

##### ✅ Why CAS Is Efficient:
- **No locking** → less blocking.
- Used only during **update operations**.
- When heavy contention occurs, the map falls back to **synchronized blocks**.

#### 🔧 Example:

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("apple", 1);
map.putIfAbsent("banana", 2); // CAS used internally
map.computeIfAbsent("cherry", k -> 3);
```

---

## 🌐 ConcurrentSkipListMap

### 🔹 When and Why to Use?

- Used when you need a **thread-safe, sorted map**.
- Alternative to `TreeMap` in concurrent environments.

### ⚙️ Key Characteristics:
- Implements `ConcurrentNavigableMap` and extends `AbstractMap`.
- Underlying data structure: **SkipList**
- **Sorted keys** and thread-safe
- Allows **logarithmic time complexity** for `put`, `get`, and `remove`

---

### 🧱 What is a SkipList?

A **SkipList** is a multi-level linked list with "express lanes" to improve search performance.

#### 🔍 Example Layers:

```
Layer 3: 1 ---- 5 ---- 9
Layer 2: 1 -- 3 -- 5 -- 7 -- 9
Layer 1: 1-2-3-4-5-6-7-8-9
```

- Searching for `2` starts at Layer 3 → drops to Layer 2 → finally finds it in Layer 1.
- This results in **faster traversal** than regular linked list.

---

### 🔧 Example:

```java
ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
map.put(3, "three");
map.put(1, "one");
map.put(2, "two");

System.out.println(map); // Output: {1=one, 2=two, 3=three}
```

---
## 🕒 Time Complexity Table

### 🔹 ConcurrentHashMap (Java 8+)

| **Operation**         | **Average Time** | **Worst Time** | **Description**                                     |
|------------------------|------------------|----------------|-----------------------------------------------------|
| `get(key)`             | O(1)             | O(log n)       | Fast read; no locking required.                    |
| `put(key, value)`      | O(1)             | O(log n)       | Lock on bucket; tree-based after threshold.        |
| `remove(key)`          | O(1)             | O(log n)       | Efficient deletion with fine-grained locking.      |
| `containsKey(key)`     | O(1)             | O(log n)       | Similar to `get`.                                  |
| `size()`               | O(n)             | O(n)           | Traverses all entries; not constant time.          |
| `keySet()/entrySet()`  | O(n)             | O(n)           | Snapshot views; not updated in real time.          |

### 🔹 ConcurrentSkipListMap

| **Operation**           | **Time Complexity** | **Description**                                 |
|--------------------------|---------------------|-------------------------------------------------|
| `get(key)`               | O(log n)            | Multi-level search via skip list layers.        |
| `put(key, value)`        | O(log n)            | Maintains sorted order; probabilistic balancing.|
| `remove(key)`            | O(log n)            | Efficient deletion while preserving structure.  |
| `containsKey(key)`       | O(log n)            | Same as `get()`.                                |
| `firstKey()/lastKey()`   | O(log n)            | Skip directly to ends.                          |
| `subMap()/headMap()/tailMap()` | O(log n + k) | k = number of entries in the subview.           |

---

## ✅ Summary

| Feature                    | Hashtable         | ConcurrentHashMap     | ConcurrentSkipListMap       |
|----------------------------|-------------------|------------------------|------------------------------|
| Thread-Safe                | ✅ Yes             | ✅ Yes                 | ✅ Yes                       |
| Null Keys/Values           | ❌ Not Allowed     | ❌ (null key), ✅ values | ❌ (null key), ✅ values      |
| Sorted Order               | ❌ No              | ❌ No                  | ✅ Yes (natural order)       |
| Locking Mechanism          | Global Lock        | Segment (Java 7), CAS (Java 8) | Fine-grained with SkipList |
| Legacy or Modern           | ❌ Legacy          | ✅ Modern              | ✅ Modern                    |
