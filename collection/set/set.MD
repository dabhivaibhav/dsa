
# 📚 Java Collections: Set

## 🔹 When and Why Should You Use a Set?

The **`Set`** collection in Java represents a group of **unique elements**—duplicates are strictly not allowed. Use a `Set` when you need to ensure **element uniqueness**, such as storing user IDs, tags, or keys where repetition would break logic or waste memory. It offers efficient lookup and insertion operations and is ideal when you don’t require indexed access or duplicate values.

---

## 🔸 Characteristics of Set

- ✅ **Underlying Data Structure**: Typically a **Hash Table** (`HashSet`) or **LinkedHashMap** (`LinkedHashSet`).
- ❌ **No Insertion Order Preserved**: Only `LinkedHashSet` preserves insertion order; others do not.
- 🚫 **No Duplicates Allowed**: Every element in a Set must be **unique**.
- 🧠 **Stored Using Hashing**: Relies on `hashCode()` and `equals()` for storage and uniqueness checks.
- ⚡ **Optimized for Lookup**: Provides **fast lookup performance** (O(1) average-case in `HashSet`).
- ♻️ **Implements Interfaces**: `Set` extends `Collection` and implements `Cloneable` and `Serializable`.
- 🚫 **Not Random-Access**: Unlike `List`, you can't access elements by index.
- 🔄 **Supports Heterogeneous Elements**: Yes — since `Set` uses `equals()` and `hashCode()`, it doesn't require elements to be comparable or sortable.
- 🧺 **No New Methods in Set Interface**: The `Set` interface does not introduce new methods; it inherits from `Collection`.

---

## 🔸 HashSet Constructors

### 1. `HashSet<E> collection.set = new HashSet<>();`
- **Description**: Creates an empty `HashSet` with:
    - **Default initial capacity** = 16
    - **Default load factor** = 0.75
- Automatically resizes when the number of elements exceeds `capacity * loadFactor`.

```java
Set<String> collection.set = new HashSet<>();
```

---

### 2. `HashSet<E> collection.set = new HashSet<>(Collection<? extends E> c);`
- **Description**: Creates a `HashSet` containing all elements from another collection.
- Automatically removes duplicates.

```java
List<String> list = List.of("apple", "banana", "apple");
Set<String> collection.set = new HashSet<>(list); // collection.set: [apple, banana]
```

---

### 3. `HashSet<E> collection.set = new HashSet<>(int initialCapacity);`
- **Description**: Creates a `HashSet` with a specified initial capacity.
- Use when you know the expected number of elements to minimize resizing.

```java
Set<Integer> collection.set = new HashSet<>(50); // Ideal for storing up to 50 elements
```

---

### 4. `HashSet<E> collection.set = new HashSet<>(int initialCapacity, float loadFactor);`
- **Description**: Full control over internal capacity and load threshold.
    - **initialCapacity**: Initial number of buckets.
    - **loadFactor**: Determines when to rehash (`capacity * loadFactor`).

```java
Set<String> collection.set = new HashSet<>(32, 0.5f); // Resize when 16 elements are reached
```
---

## 🔸 Internal Working of HashSet

The `HashSet` class in Java is backed by a **`HashMap`**. Internally, when you add an element to a `HashSet`, it actually stores the element as a **key** in a `HashMap`, with a constant dummy object as the value (usually `PRESENT`).

```java
private transient HashMap<E, Object> map;
private static final Object PRESENT = new Object();
```

So when you do:

```java
collection.set.add("apple");
```

It internally does:

```java
map.put("apple", PRESENT);
```

### 🧠 Key Characteristics:
- Uses **hashing** to store elements in **buckets**.
- Uses **`hashCode()`** to determine the bucket index.
- Uses **`equals()`** to ensure no duplicates exist in the same bucket.
- No shifting of elements occurs on deletion, unlike in `ArrayList`.
- Allows **one null element** since `HashMap` allows one null key.
- Since **Java 8**, long collision chains can be **treeified** (red–black tree), making per-bucket operations **O(log m)** instead of linear.

---

## 🔸 Time Complexity of HashSet Methods

| **Method**             | **Average Time** | **Worst Time** | **Description** |
|------------------------|------------------|----------------|-----------------|
| `add(E e)`             | O(1)             | O(log n)*; O(n) on resize           | Adds element if not already present. |
| `remove(Object o)`     | O(1)             | O(log n)*           | Removes specified element if it exists. |
| `contains(Object o)`   | O(1)             | O(log n)*           | Checks if an element exists in the collection.set. |
| `size()`               | O(1)             | O(1)           | Returns the number of elements. |
| `isEmpty()`            | O(1)             | O(1)           | Checks if the collection.set is empty. |
| `clear()`              | O(n)             | O(n)           | Removes all elements from the collection.set. |
| `iterator()`           | O(n)             | O(n)           | Returns an iterator over the collection.set. |
| `clone()`              | O(n)             | O(n)           | Returns a shallow copy of the collection.set. |
| `toArray()`            | O(n)             | O(n)           | Converts collection.set elements into an array. |

**Notes:** `*` Treeified bucket worst-case (Java 8+). With many inserts colliding into a single bucket, per-op becomes **O(log n)**; across **n** such inserts the total can be **O(n log n)** (∑ log k). A single `add` that triggers a **resize/rehash** costs **O(n)** for that call.

---
## 🔸 What is a Hash Bucket and Same Hash Bucket?

In `HashSet`, elements are stored in buckets determined by their `hashCode()`. Internally, `HashSet` is backed by a `HashMap`, which uses an array (`table[]`) where each index is called a **bucket**.

### 🔹 How Hash Buckets Work:

When you call `add()` on a `HashSet`:
1. Java computes the element’s `hashCode()`.
2. It maps that hash to a bucket using `(hash % array.length)` (simplified).
3. The element is added to that bucket (as a node in a linked list or tree).

If multiple elements hash to the **same bucket index**, they are added in a chain (linked list or red-black tree). This is known as a **hash collision**, and the bucket is shared — called a **same hash bucket** scenario.

---

### 🔹 Example: Two Objects in the Same Hash Bucket

```java
class Item {
    String name;

    Item(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 42; // Force same bucket!
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Item)) return false;
        return name.equals(((Item) obj).name);
    }

    @Override
    public String toString() {
        return name;
    }
}
```

```java
Set<Item> collection.set = new HashSet<>();
collection.set.add(new Item("Apple"));
collection.set.add(new Item("Banana"));
collection.set.add(new Item("Cherry"));
```

Even though these objects have different values, they all land in the **same bucket**, because their `hashCode()` is the same. They are then stored in a linked list within that bucket.

---

### 🔹 Internal Bucket Visualization

```text
Bucket[5] → "Apple" → "Banana" → "Cherry"
```

Each element is stored in a chain (called a collision chain). If you remove one, it's simply "unlinked" from the chain — references are adjusted to skip over it.

---

### ✅ Summary

- **Same Hash Bucket** means multiple elements land in the same slot of the internal array due to matching or colliding hash values.
- Java resolves this with **chaining** (linked list or tree).
- Hash collisions are normal but should be minimized with good `hashCode()` implementations to maintain performance.

---
## 🔸 LinkedHashSet

### 🔹 What is `LinkedHashSet`?
`LinkedHashSet` is a hybrid collection that combines the **hashing mechanism** of `HashSet` with a **doubly-linked list** to preserve **insertion order**.

### 🔹 Key Characteristics:
- ✅ Preserves **insertion order**
- ❌ Still does **not allow duplicates**
- ⚙️ Internally backed by a **LinkedHashMap**
- 🕒 Slightly slower than `HashSet`, but preserves order during iteration

### 🔹 Example:
```java
Set<String> linkedSet = new LinkedHashSet<>();
linkedSet.add("one");
linkedSet.add("two");
linkedSet.add("three");
System.out.println(linkedSet); // Output: [one, two, three]
```

### 🔹 Use Cases:
- When you want a `Set` that guarantees **both uniqueness and insertion order**.
- Examples:
    - Caching recent search queries
    - Maintaining the order of selected UI elements

---
## 🔸 Internal Working of LinkedHashSet

The `LinkedHashSet` in Java is a subclass of `HashSet` that **maintains the insertion order** of elements. It achieves this by using a combination of:

- A **Hash Table** for fast element lookup (like `HashSet`).
- A **Doubly Linked List** to track the order in which elements were inserted.

Internally, `LinkedHashSet` is backed by a **`LinkedHashMap`**. Each element you add to a `LinkedHashSet` is stored as a key in the `LinkedHashMap`, and a constant dummy object (usually `PRESENT`) is used as the value.

```java
LinkedHashMap<E, Object> map;
private static final Object PRESENT = new Object();
```

---

### 🔹 How `LinkedHashSet` Maintains Insertion Order

When you add elements:

```java
Set<String> collection.set = new LinkedHashSet<>();
collection.set.add("apple");
collection.set.add("banana");
collection.set.add("cherry");
```

Internally:

1. Each element is stored in a hash bucket based on its `hashCode()`.
2. Additionally, a **doubly linked list** is maintained across all entries in the order they were added.
3. The list is updated to preserve the order for iteration.

So the internal state looks like this:

```text
HashTable:
Bucket[i] → "apple"
Bucket[j] → "banana"
Bucket[k] → "cherry"

Linked List:
"apple" <-> "banana" <-> "cherry"
```

---

### 🔹 Removal in LinkedHashSet

When you remove an element:
- It is removed from both the **hash table** and the **linked list**.
- No shifting is needed; the linked list pointers are updated (just like in a doubly linked list).

---

### 🔹 Iteration Order

The **iterator** of a `LinkedHashSet` returns elements in the **exact order** they were inserted, unlike `HashSet` which may appear random.

```java
for (String s : collection.set) {
    System.out.println(s); // apple, banana, cherry
}
```

---
## 🔸 Time Complexity of LinkedHashSet Methods

| **Method**             | **Average Time** | **Worst Time** | **Description** |
|------------------------|------------------|----------------|-----------------|
| `add(E e)`             | O(1)             | O(log n)*; O(n) on resize           | Adds element if not already present; maintains insertion order. |
| `remove(Object o)`     | O(1)             | O(log n)*           | Removes specified element if it exists. |
| `contains(Object o)`   | O(1)             | O(log n)*           | Checks if an element exists in the collection.set. |
| `size()`               | O(1)             | O(1)           | Returns the number of elements. |
| `isEmpty()`            | O(1)             | O(1)           | Checks if the collection.set is empty. |
| `clear()`              | O(n)             | O(n)           | Removes all elements from the collection.set. |
| `iterator()`           | O(n)             | O(n)           | Returns an iterator in insertion order. |
| `toArray()`            | O(n)             | O(n)           | Converts collection.set elements into an array. |

> Note: Like `HashSet`, the performance of `LinkedHashSet` depends on the quality of the hash function and the distribution of elements. It adds a slight overhead to maintain the linked list for ordering.

---


| Feature               | Behavior |
|------------------------|----------|
| Backing structure      | Hash Table + Doubly Linked List |
| Preserves order?       | ✅ Yes — insertion order |
| Allows duplicates?     | ❌ No |
| Allows one `null`?     | ✅ Yes |
| Lookup performance     | ⚡ Fast — O(1) average |
| Iteration order        | ✅ Insertion order preserved |
| Memory overhead        | Slightly more than `HashSet` due to links |

---

## 📊 Comparison: HashSet vs LinkedHashSet vs TreeSet

| Feature                     | HashSet              | LinkedHashSet              | TreeSet                      |
|----------------------------|----------------------|----------------------------|------------------------------|
| **Underlying Data Structure** | HashTable             | HashTable + LinkedList      | Balanced Tree (Red-Black Tree) |
| **Insertion Order**         | Not Preserved         | Preserved                   | Not Preserved (Sorted Order) |
| **Sorting Order**           | Not Applicable        | Not Applicable              | Applicable (Natural or Custom) |
| **Heterogeneous Objects**   | Allowed               | Allowed                     | ❌ Not Allowed (unless using Comparator) |
| **Duplicate Objects**       | Not Allowed           | Not Allowed                 | Not Allowed                   |
| **Null Acceptance**         | Allowed only once     | Allowed only once           | Allowed only as first element if TreeSet is empty, otherwise ❌ `NullPointerException` |

---

## 🔚 Summary

The `Set` interface is a foundational part of the Java Collections Framework. It ensures **uniqueness**, supports **efficient element retrieval**, and offers multiple implementations for different needs:
- Use `HashSet` for **fast, unordered, unique storage**.
- Use `LinkedHashSet` for **order-preserving** unique storage.
- Use `TreeSet` (not covered here) for **sorted** sets with **navigational operations**.

Knowing when and how to use `Set` types gives you robust control over data integrity and performance in Java applications.

---
