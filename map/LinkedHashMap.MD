# 🔗 Java Collections: LinkedHashMap

## 🔹 When and Why Should You Use LinkedHashMap?

`LinkedHashMap` is a subclass of `HashMap`. Unlike `HashMap`, it maintains a **doubly linked list** internally which preserves the **insertion order** (or **access order**, if configured) of keys. This makes it slightly **slower than `HashMap`** and also consumes **more memory**, but it provides **predictable iteration order**.
Use it when:
- You need fast lookup + **preserved order**
- You want to implement **LRU cache logic** using access order.
- Order matters in iteration
- You are working with APIs or data where **the order of keys matters**.
- It is **not thread-safe**, so for multi-threaded environments, consider using `Collections.synchronizedMap()` or `ConcurrentHashMap`.

---

## 🔸 Characteristics of LinkedHashMap

- 🧠 Extends `HashMap` and maintains a **linked list** of entries
- ✅ Preserves **insertion order** by default
- 🔁 Can maintain **access order** using constructor config
- ✅ Allows one **null key**, multiple **null values**
- ❌ Not thread-safe
- ⏱️ Slightly slower than `HashMap` due to extra linked list overhead

---

## 🔸 LinkedHashMap Constructors

| Constructor                                               | Description |
|------------------------------------------------------------|-------------|
| `LinkedHashMap()`                                         | Default insertion-order map |
| `LinkedHashMap(int initialCapacity)`                      | With capacity |
| `LinkedHashMap(int initialCapacity, float loadFactor)`    | With capacity + load factor |
| `LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)` | `accessOrder=true` enables LRU ordering |

---

### 🔁 `accessOrder` in LinkedHashMap

`LinkedHashMap` has a special constructor that takes a boolean flag `accessOrder`. This flag determines the iteration order:

- If `accessOrder = false` (default): maintains **insertion order**
- If `accessOrder = true`: maintains **access order** — the order in which entries were last accessed (read or updated)

This is particularly useful for **implementing LRU (Least Recently Used) cache**.

### 🧪 Example of Access Order in LinkedHashMap

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class AccessOrderExample {
    public static void main(String[] args) {
        Map<Integer, String> lruMap = new LinkedHashMap<>(5, 0.75f, true); // accessOrder = true

        lruMap.put(1, "One");
        lruMap.put(2, "Two");
        lruMap.put(3, "Three");
        lruMap.put(4, "Four");

        // Access key 2 and 1
        lruMap.get(2);
        lruMap.get(1);

        System.out.println("Access Order:");
        for (Map.Entry<Integer, String> entry : lruMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
```

### 🔍 Output:
```
3 => Three
4 => Four
2 => Two
1 => One
```

### 🧠 Explanation:
- Keys 2 and 1 were accessed last, so they appear at the end.
- This behavior mimics **recent usage tracking**, making it perfect for LRU cache designs.
---

## 🔸 Time Complexity of LinkedHashMap Methods

| Method              | Average Time | Worst Time | Description |
|---------------------|--------------|------------|-------------|
| `put(K key, V val)` | O(1)         | O(log n)   | Adds entry maintaining order |
| `get(Object key)`   | O(1)         | O(log n)   | Returns value by key |
| `remove(Object key)`| O(1)         | O(log n)   | Removes entry |
| `containsKey()`     | O(1)         | O(log n)   | Check if key exists |
| `containsValue()`   | O(n)         | O(n)       | Check if value exists |
| `keySet()`          | O(n)         | O(n)       | Ordered key set |
| `values()`          | O(n)         | O(n)       | Ordered values |
| `entrySet()`        | O(n)         | O(n)       | Ordered entries |

---
## 🧩 Additional Map Implementations

### 🧠 WeakHashMap

`WeakHashMap` is a `Map` implementation where **keys are stored with weak references**. This means if a key is **no longer referenced elsewhere**, it becomes eligible for **garbage collection**, and its entry will automatically be removed from the map.

#### 🔹 When to Use:
- When you want to **allow automatic cleanup** of entries whose keys are no longer in use.
- Useful for **caching**, where you don't want cached keys to prevent memory from being reclaimed.

#### 🔍 Example Use Case:
```java
Map<Object, String> map = new WeakHashMap<>();
Object key = new Object();
map.put(key, "value");

key = null; // Now eligible for GC
System.gc(); // Entry may be removed automatically
```

---

### 🧠 IdentityHashMap

`IdentityHashMap` uses **reference equality** (`==`) instead of `.equals()` for comparing keys. This means two keys are considered the same only if they are the **exact same object in memory**.

#### 🔹 When to Use:
- When **object identity** (not logical equality) matters.
- Useful in **parsing, compilers, or interning scenarios**, where key distinction is based on memory address.

#### 🔍 Example Use Case:
```java
Map<String, String> map = new IdentityHashMap<>();
map.put(new String("a"), "value1");
map.put(new String("a"), "value2"); // Both entries remain

System.out.println(map.size()); // Output: 2
```
- In a regular `HashMap`, size would be 1 because the `.equals()` method would treat the keys as equal.
---

## 🔚 Summary

- ✅ Preserves **insertion or access order**
- ✅ Ideal for **ordered lookup** and **cache design**
- ⚠️ Slightly more memory than `HashMap`
- Allows null key and null values

---
