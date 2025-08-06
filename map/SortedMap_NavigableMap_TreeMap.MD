# 📘 Java Collections: SortedMap, NavigableMap & TreeMap

---

## 🔹 When and Why Should You Use SortedMap?

A `SortedMap` is a specialized `Map` that maintains the **keys in sorted order**. It is useful when:
- You need **natural ordering** or **custom sorting** of keys.
- You require **range-based operations** like fetching entries before or after a key.
- Efficient **searching**, **range queries**, and **ordered traversal** is needed.

---

## 🔸 Key Characteristics of SortedMap

- 📊 Keys are stored in **ascending sorted order** (natural order or custom `Comparator`).
- 🚫 **No duplicate keys** allowed.
- ❌ **Null keys are not allowed** in most implementations like `TreeMap`.
- ✅ **Multiple null values** are allowed.
- 🔐 It is an interface, not a concrete class.
- 💡 Common implementation: `TreeMap`.

---

## 🔸 SortedMap Methods

| Method | Description |
|--------|-------------|
| `comparator()` | Returns the comparator used for sorting or `null` if natural ordering is used. |
| `firstKey()` | Returns the first (lowest) key. |
| `lastKey()` | Returns the last (highest) key. |
| `headMap(K toKey)` | Returns a view of the map whose keys are strictly less than `toKey`. |
| `tailMap(K fromKey)` | Returns a view of the map with keys greater than or equal to `fromKey`. |
| `subMap(K fromKey, K toKey)` | Returns a view of the map with keys ranging from `fromKey` to `toKey`. |

---

## 🔹 NavigableMap

`NavigableMap` is a **child interface of SortedMap** that adds powerful **navigation capabilities**.

### 🔸 Key Characteristics

- 🧭 Allows **ceiling, floor, higher, lower** operations.
- 🔄 Supports **reversed views** using `descendingMap()`.
- 🔍 Ideal for **nearest match lookups** and **range-based operations**.

### 🔸 NavigableMap Methods

| Method | Description |
|--------|-------------|
| `ceilingKey(K key)` | Smallest key ≥ given key |
| `floorKey(K key)` | Greatest key ≤ given key |
| `higherKey(K key)` | Smallest key > given key |
| `lowerKey(K key)` | Greatest key < given key |
| `pollFirstEntry()` | Removes and returns first entry |
| `pollLastEntry()` | Removes and returns last entry |
| `descendingMap()` | Returns a reverse order view |

---

## 🌳 TreeMap (Implementation of SortedMap and NavigableMap)

### 🔹 When and Why to Use TreeMap?

`TreeMap` is a **Red-Black Tree based implementation** of `SortedMap` and `NavigableMap`. It automatically **sorts keys**, and provides **logarithmic time complexity** for basic operations.

#### ✅ Use TreeMap When:
- You want keys in **sorted order**
- Need **range or ceiling/floor queries**
- Require **predictable iteration order**

---

## 🔸 Key Characteristics of TreeMap

- 🌲 Uses **Red-Black Tree** for internal structure.
- ✅ Keys are **sorted naturally** or using custom `Comparator`.
- 🚫 **No null keys** allowed (throws `NullPointerException`).
- ✅ **Allows null values**
- 📘 Implements both **`SortedMap`** and **`NavigableMap`**

---

## 🔹 TreeMap Constructors

| Constructor | Description |
|-------------|-------------|
| `TreeMap()` | Creates an empty TreeMap with natural key ordering |
| `TreeMap(Comparator c)` | Uses a custom comparator |
| `TreeMap(Map m)` | Initializes with the mappings of another map |
| `TreeMap(SortedMap m)` | Initializes with the mappings and ordering of another SortedMap |

---

## 🔹 TreeMap Example

```java
SortedMap<Integer, String> map = new TreeMap<>();
map.put(2, "B");
map.put(1, "A");
map.put(3, "C");

System.out.println(map); // {1=A, 2=B, 3=C}
System.out.println(map.firstKey()); // 1
System.out.println(map.lastKey());  // 3
```

---

## 🔹 Time Complexity of TreeMap Methods

| Method | Time Complexity | Description |
|--------|------------------|-------------|
| `put()` | O(log n) | Inserts key-value pair |
| `get()` | O(log n) | Retrieves value for key |
| `remove()` | O(log n) | Deletes mapping for a key |
| `firstKey()`, `lastKey()` | O(log n) | Gets boundary keys |
| `subMap()`, `headMap()`, `tailMap()` | O(log n) | Returns sub views |
| `ceilingKey()`, `floorKey()` | O(log n) | Navigational queries |

> TreeMap uses Red-Black Tree which ensures that **no path from root to leaf is more than twice as long as any other**, maintaining O(log n) balance.

---

## 🧠 Summary

- Use **SortedMap** for maintaining sorted keys.
- Use **NavigableMap** for nearest match, reverse views, and flexible key-based navigation.
- Use **TreeMap** when you need a **sorted map** with reliable log-time performance.