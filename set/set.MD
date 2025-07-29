
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

### 1. `HashSet<E> set = new HashSet<>();`
- **Description**: Creates an empty `HashSet` with:
    - **Default initial capacity** = 16
    - **Default load factor** = 0.75
- Automatically resizes when the number of elements exceeds `capacity * loadFactor`.

```java
Set<String> set = new HashSet<>();
```

---

### 2. `HashSet<E> set = new HashSet<>(Collection<? extends E> c);`
- **Description**: Creates a `HashSet` containing all elements from another collection.
- Automatically removes duplicates.

```java
List<String> list = List.of("apple", "banana", "apple");
Set<String> set = new HashSet<>(list); // set: [apple, banana]
```

---

### 3. `HashSet<E> set = new HashSet<>(int initialCapacity);`
- **Description**: Creates a `HashSet` with a specified initial capacity.
- Use when you know the expected number of elements to minimize resizing.

```java
Set<Integer> set = new HashSet<>(50); // Ideal for storing up to 50 elements
```

---

### 4. `HashSet<E> set = new HashSet<>(int initialCapacity, float loadFactor);`
- **Description**: Full control over internal capacity and load threshold.
    - **initialCapacity**: Initial number of buckets.
    - **loadFactor**: Determines when to rehash (`capacity * loadFactor`).

```java
Set<String> set = new HashSet<>(32, 0.5f); // Resize when 16 elements are reached
```

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

## 🔚 Summary

The `Set` interface is a foundational part of the Java Collections Framework. It ensures **uniqueness**, supports **efficient element retrieval**, and offers multiple implementations for different needs:
- Use `HashSet` for **fast, unordered, unique storage**.
- Use `LinkedHashSet` for **order-preserving** unique storage.
- Use `TreeSet` (not covered here) for **sorted** sets with **navigational operations**.

Knowing when and how to use `Set` types gives you robust control over data integrity and performance in Java applications.

---
