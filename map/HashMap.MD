# Java Collections: HashMap

## When and Why Should You Use HashMap?
`HashMap` is the most widely used implementation of the `Map` interface in Java. It provides **constant-time performance** for basic operations like `get()` and `put()` assuming a good hash function. Use it when:
- You need fast access to data via keys
- Insertion order is not important
- You need to store unique keys with associated values

---

## Characteristics of HashMap
- Stores data as **key-value pairs**
- Does **not allow duplicate keys**
- Allows **one null key** and **multiple null values**
- Backed by a **hash table**
- Provides **O(1)** time complexity on average for operations like `put()`, `get()`, and `remove()`
- Not synchronized (not thread-safe)
- Iteration order is **not guaranteed**
- Implements `Map`, `Cloneable`, and `Serializable`

---

## 🧠 Internal Working of HashMap

### 🔹 Key Components of HashMap
1. **Key** – Identifier used to retrieve the value.
2. **Value** – The actual data associated with a key.
3. **Bucket** – Logical unit (index) where key-value pairs are stored; part of the internal array.
4. **Hash Function** – Converts a key into an index (bucket location).

---

### 🔹 How HashMap Stores Data

#### Step 1: Hashing the Key
The key is passed through a **hash function**, which returns a hash code (an integer). This hash helps determine where to store the key-value pair.

> A good hash function:
> - Produces the same output for the same input.
> - Has consistent fixed size output (e.g., 32-bit).
> - Should be fast and efficient.

#### Step 2: Calculating Index
The hash code is used to calculate the index of the internal array:
```java
int index = hashCode % arraySize;
```
For example, with array size 16 and hashCode = 19, the bucket index would be `3`.

#### Step 3: Storing in Buckets
The key-value pair is stored at the computed bucket index. Buckets are organized as a **linked list**, and if multiple entries hash to the same index, they are chained together.

---

### 🔹 Collision Handling in HashMap

When multiple keys hash to the same bucket (same index), this is called a **collision**. HashMap handles collisions using **Linked Lists** initially. If the number of nodes in a bucket exceeds the **treeify threshold (8)**, it switches to a **Red-Black Tree** for faster access.

```java
class Node<K, V> {
  final int hash;
  final K key;
  V value;
  Node<K,V> next;
}
```

---

### 🔹 Retrieving Data from HashMap

When calling `get(key)`:
1. The key is hashed using the same hash function.
2. The hash code is used to find the bucket index.
3. HashMap searches the bucket:
    - If it's a linked list, it scans nodes linearly.
    - If it's a Red-Black Tree, it performs tree search (O(log n)).

---

### 🔹 Resizing and Rehashing

HashMap starts with a default capacity of **16** and a **load factor of 0.75**. When the number of entries exceeds `capacity * loadFactor`, the map resizes (usually doubles in size).

#### During Rehashing:
- A new array is created.
- All entries are rehashed and redistributed across new buckets.

This keeps HashMap performant even with growth.

---

### 🔹 Time Complexity Summary

- **Average Case**:
    - `put()`, `get()`, `remove()` → **O(1)**
- **Worst Case**:
    - With poor hash function (all keys into same bucket): **O(n)**
    - With Red-Black Tree: **O(log n)**

> Java 8+ uses treeification to mitigate the risk of worst-case `O(n)` by using balanced trees.

---

## HashMap Constructors
| Constructor                             | Description |
|----------------------------------------|-------------|
| `HashMap()`                            | Creates a map with default capacity 16 and load factor 0.75 |
| `HashMap(int initialCapacity)`         | Creates a map with given capacity |
| `HashMap(int initialCapacity, float loadFactor)` | Full control over size and load behavior |
| `HashMap(Map<? extends K, ? extends V> m)` | Copies all mappings from another map |

---

## Time Complexity of HashMap Methods
| Method              | Average Time | Worst Time | Description |
|---------------------|--------------|------------|-------------|
| `put(K key, V val)` | O(1)         | O(log n)   | Adds/updates key-value pair; Tree structure helps under collision |
| `get(Object key)`   | O(1)         | O(log n)   | Retrieves value by key |
| `remove(Object key)`| O(1)         | O(log n)   | Deletes key-value pair |
| `containsKey()`     | O(1)         | O(log n)   | Checks key presence |
| `containsValue()`   | O(n)         | O(n)       | Checks value presence (no indexing on value) |
| `keySet()`          | O(n)         | O(n)       | Returns keys as a Set |
| `values()`          | O(n)         | O(n)       | Returns Collection of values |
| `entrySet()`        | O(n)         | O(n)       | Returns Set of entries |

> 📝 Thanks to Java 8's Red-Black Tree upgrade, many **worst-case operations are now O(log n)** instead of O(n), depending on collision resolution strategy.

---

## Summary
- `HashMap` is the go-to data structure for **fast access to mappings**
- Ideal for use cases like frequency counting, caching, memoization, and lookup tables
- Not thread-safe — use `ConcurrentHashMap` or synchronize externally for concurrent use