# 📘 Java Collections: Map Interface

## 🔹 When and Why Should You Use a Map?

A `Map` is the perfect choice when you want to store **key-value pairs** and quickly retrieve values based on their keys. Unlike lists or sets, a map does not store individual elements—it stores mappings. This is crucial when:
- You need **constant-time lookup** of values using keys (like dictionaries)
- You want to **associate metadata** with objects (e.g., userID → UserData)
- Duplicates are not allowed in keys but can exist in values

Use Map when **key-based access** is required over index-based access.

---

## 🔸 Characteristics of Map

- 🔑 A `Map` is **not a child of the Collection interface**.
- 📘 It is an **independent module** in the Java Collections Framework.
- 🔐 Stores **key-value pairs**.
- 🚫 **No duplicate keys** allowed.
- ✅ Allows **one null key** and **multiple null values** (in most implementations like HashMap).
- 🧠 Keys are stored using **hashing** (in `HashMap`) or sorted order (in `TreeMap`).
- ♻️ Implements `Cloneable` and `Serializable` interfaces.
- 🔄 Does **not support random access** by index.
- 🔍 **Supports heterogeneous keys and values**, but both must be **consistent in equality and hashing logic**.

---

## 🔸 Common Map Implementations

| Implementation     | Ordering             | Sorting          | Null Keys/Values         | Thread-Safe |
|--------------------|----------------------|------------------|--------------------------|-------------|
| `HashMap`          | ❌ No insertion order| ❌ No             | ✅ One null key, multiple null values | ❌ No |
| `LinkedHashMap`    | ✅ Insertion order   | ❌ No             | ✅ One null key, multiple null values | ❌ No |
| `TreeMap`          | ❌ Sorted by key     | ✅ Yes (Natural or Comparator) | ❌ No null key, but null values allowed | ❌ No |
| `Hashtable`        | ❌ No ordering       | ❌ No             | ❌ No null key or value  | ✅ Yes |

---

## 🔸 Key Concepts for DSA Usage

- Use `HashMap` when you need **constant-time lookup**, insertion, and deletion.
- Use `LinkedHashMap` when **insertion order** matters (e.g., cache implementations).
- Use `TreeMap` when you need to **maintain sorted keys** or do **range-based queries**.
- Maps are frequently used in DSA for:
    - Counting frequency of elements
    - Grouping data (e.g., anagrams, graph edges)
    - Caching (memoization in DP)
    - Associative lookups and reverse mappings

---

## 🔸 Internal Working (HashMap Example)

- Uses a **Hash Table**.
- Each key is hashed to find a **bucket index**.
- Collisions are handled via **Linked List** or **Red-Black Tree** (in Java 8+ if many collisions occur).
- When adding an entry:
  ```java
  map.put("apple", 10);
  // internally: hash("apple") → bucket index → add Entry<"apple", 10>
  ```
- If the key exists, its value is updated.

---

## 🔸 Time Complexity of Common Map Methods

| Method              | HashMap Avg | HashMap Worst | LinkedHashMap Avg | TreeMap Avg/Worst | Description                              |
|---------------------|-------------|----------------|-------------------|------------------|------------------------------------------|
| `put(K key, V val)` | O(1)        | O(log n)       | O(1)              | O(log n)         | Inserts key-value pair; replaces if key exists |
| `get(Object key)`   | O(1)        | O(log n)       | O(1)              | O(log n)         | Retrieves value associated with the key  |
| `remove(Object key)`| O(1)        | O(log n)       | O(1)              | O(log n)         | Removes entry for the specified key      |
| `containsKey()`     | O(1)        | O(log n)       | O(1)              | O(log n)         | Checks if key exists                     |
| `containsValue()`   | O(n)        | O(n)           | O(n)              | O(n)             | Checks if value exists                   |
| `keySet()`          | O(n)        | O(n)           | O(n)              | O(n)             | Returns a Set of all keys                |
| `values()`          | O(n)        | O(n)           | O(n)              | O(n)             | Returns a Collection of all values       |
| `entrySet()`        | O(n)        | O(n)           | O(n)              | O(n)             | Returns a Set of all key-value pairs     |

> 📌 Notes:
> - `HashMap` and `LinkedHashMap` use hashing with Red-Black tree fallback (Java 8+), hence **worst-case `O(log n)`** instead of `O(n)`.
> - `TreeMap` uses a Red-Black Tree structure and guarantees **logarithmic time** for all operations.
> - `containsValue()` requires full traversal, making it always **O(n)** across implementations.

| Method               | Average Time | Worst Time | Description |
|----------------------|--------------|------------|-------------|
| `put(K key, V val)`  | O(1)         | O(n)       | Inserts key-value pair; replaces if key exists |
| `get(Object key)`    | O(1)         | O(n)       | Retrieves value associated with the key |
| `remove(Object key)` | O(1)         | O(n)       | Removes entry for the specified key |
| `containsKey()`      | O(1)         | O(n)       | Checks if key exists |
| `containsValue()`    | O(n)         | O(n)       | Checks if value exists |
| `keySet()`           | O(n)         | O(n)       | Returns a Set of all keys |
| `values()`           | O(n)         | O(n)       | Returns a Collection of all values |
| `entrySet()`         | O(n)         | O(n)       | Returns a Set of all key-value pairs |

> **Note:** Time complexity depends on the quality of `hashCode()` and distribution of elements.


---

## 🔚 Summary

- ✅ Map is not a child of Collection but a top-level interface in the Java Collections Framework.
- 🔑 Best choice when **fast key-based retrieval** is required.
- 🧠 Crucial in DSA for frequency maps, hashing solutions, and lookup tables.
- 🧰 Choose the right implementation (`HashMap`, `LinkedHashMap`, or `TreeMap`) based on needs like order preservation or sorting.