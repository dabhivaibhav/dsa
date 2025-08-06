
# 📚 Java Collections: SortedSet

## 🔹 When and Why Should You Use a SortedSet?

Use a **`SortedSet`** when you need to maintain a **collection of unique elements in a sorted order**. It is ideal for situations where the **order of elements matters** and you frequently need access to the **smallest or largest elements**, or perform **range-based queries** (e.g., all elements less than or greater than a certain value).

### 🧠 Common Use Cases:
- Maintaining a sorted list of user names or IDs.
- Fetching the highest or lowest score, price, or timestamp.
- Creating autocomplete systems or suggestion engines.

---

## 🔸 Key Characteristics of SortedSet

- ✅ **Child Interface of `Set`**: Inherits all behaviors of `Set`, plus adds sorting.
- ❌ **Insertion Order Not Preserved**: Elements are reordered based on sorting rules.
- ✅ **Stores Elements in Sorted Order**: Natural ordering (ascending) unless a custom `Comparator` is provided.
- ❌ **No Duplicates Allowed**: Like all sets, uniqueness is enforced using `compareTo()` or `compare()`.
- ✅ **Supports `first()` and `last()` Terminology**: Along with `NavigableSet` and `TreeSet`.
- ⚠️ **`LinkedHashSet` Doesn’t Directly Support `first()`/`last()`**, but they can be obtained via iteration.
- ❌ **No Heterogeneous Elements**: Only **homogeneous**, mutually comparable elements are allowed.
- ✅ **Natural Sorting Order**: Ascending by default unless overridden with a custom `Comparator`.

---

## 🔸 Important Methods in SortedSet

| Method | Description |
|--------|-------------|
| `first()` | Returns the **smallest element** in the collection.set. |
| `last()` | Returns the **largest element** in the collection.set. |
| `headSet(E toElement)` | Returns a view of elements **strictly less than** `toElement`. |
| `tailSet(E fromElement)` | Returns a view of elements **greater than or equal to** `fromElement`. |
| `subSet(E fromElement, E toElement)` | Returns elements **between `fromElement` (inclusive)** and **`toElement` (exclusive)**. |
| `comparator()` | Returns the comparator used for sorting, or `null` if natural ordering is used. |

> These methods are defined in `SortedSet` and are available in **`TreeSet`** which implements `SortedSet`.

---

## 🔸 Constructors and Implementations

Although `SortedSet` is an interface and doesn’t have constructors itself, its main implementation `TreeSet` provides all these operations.

### Commonly Supported Methods in Implementing Classes:

| **Method Signature**                          | **Description**                                                              | **Supported In** |
|-----------------------------------------------|------------------------------------------------------------------------------|------------------|
| `Object first()`                               | Returns the lowest element in the collection.set                                        | `SortedSet`, `TreeSet`, `NavigableSet` |
| `Object last()`                                | Returns the highest element in the collection.set                                       | `SortedSet`, `TreeSet`, `NavigableSet` |
| `SortedSet headSet(Object toElement)`          | Returns a view of elements less than `toElement`                             | `SortedSet`, `TreeSet` |
| `SortedSet tailSet(Object fromElement)`        | Returns a view of elements from `fromElement` (inclusive) to end             | `SortedSet`, `TreeSet` |
| `SortedSet subSet(Object from, Object to)`     | Returns a view from `from` (inclusive) to `to` (exclusive)                   | `SortedSet`, `TreeSet` |
| `Comparator comparator()`                      | Returns the comparator used, or null if natural ordering                     | `SortedSet`, `TreeSet`, `ConcurrentSkipListSet` |

---


---

## 🔸 Time Complexity of SortedSet Methods

| **Method**                   | **Average Time** | **Worst Time** | **Description** |
|------------------------------|------------------|----------------|-----------------|
| `add(E e)`                   | O(log n)         | O(log n)       | Inserts the element in sorted position. |
| `remove(Object o)`           | O(log n)         | O(log n)       | Removes the specified element. |
| `contains(Object o)`         | O(log n)         | O(log n)       | Checks if the element exists. |
| `first()` / `last()`         | O(log n)         | O(log n)       | Retrieves smallest/largest element. |
| `headSet(E toElement)`       | O(log n + k)     | O(log n + k)   | Returns a view of elements less than given value. |
| `tailSet(E fromElement)`     | O(log n + k)     | O(log n + k)   | Returns a view of elements greater than or equal to given value. |
| `subSet(E from, E to)`       | O(log n + k)     | O(log n + k)   | Returns view between two given values. |
| `comparator()`               | O(1)             | O(1)           | Returns the comparator used or null. |

> `n` is the number of elements in the collection.set, and `k` is the number of elements returned in the view for range operations.

---

---

## 🔸 Constructors and Implementations

Although `SortedSet` is an interface and doesn’t have constructors itself, its primary implementation `TreeSet` provides full support for sorted collection.set operations.



---
## 🔚 Summary

| Feature | SortedSet |
|--------|------------|
| Maintains insertion order? | ❌ No |
| Maintains sorted order? | ✅ Yes |
| Allows duplicates? | ❌ No |
| Allows heterogeneous types? | ❌ No |
| Default sorting order | ✅ Ascending |
| Custom sorting possible? | ✅ Yes, with `Comparator` |
| Provides `first()` and `last()`? | ✅ Yes |
| Implementation class | `TreeSet`, `ConcurrentSkipListSet` |

---

> The `SortedSet` interface is essential when your application needs an automatically sorted, duplicate-free collection with navigational capabilities. Its implementation via `TreeSet` unlocks powerful range and boundary operations with high performance.

---
