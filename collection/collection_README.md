
# Java Collection Overview

This document provides a comprehensive overview of **Java Arrays** and the **Collection Framework**. It's designed to give a solid understanding of core concepts, interfaces, and differences.

---

## Arrays in Java

- Arrays are indexed collections of a **fixed number of homogeneous elements**.
- **Fixed in size** — size must be declared at creation.
- Store **only homogeneous** data types (primitives or objects).
- **No underlying data structure**.
- **No built-in utility methods** (e.g., for searching or sorting).

### ➕ Pros:
- High performance.
- Can store **primitives and objects**.

### ➖ Cons:
- Not dynamic (size must be known in advance).
- Limited functionality — no ready-made methods.

### 🔤 Example

```java
int[] numbers = new int[5];
numbers[0] = 10;
System.out.println(numbers[0]);
```

---

## 📦 Collection in Java

### What is a Collection?
- A **group of individual objects** treated as a single entity.

### What is the Collection Framework?
- A **collection.set of classes and interfaces** for storing and manipulating groups of data.
- Part of `java.util` package.

### Key Characteristics:
- **Growable in nature** (dynamic size).
- Supports both **homogeneous and heterogeneous** elements.
- Built on **standard data structures** (e.g., linked lists, trees).
- Rich in **predefined methods** (e.g., `add()`, `remove()`, `contains()`).

---

# Java Collections: `Serializable` and `Cloneable` Support with Reasons

Many commonly used Collection implementations support transferring objects by implementing Serializable and sometimes Cloneable, but not all do.

This table summarizes whether common Java collection classes implement `Serializable` and `Cloneable`, along with the reasons why some do not.

## ✅ Summary Table

| Collection Class              | Serializable | Cloneable | Reason for Not Implementing |
|------------------------------|--------------|-----------|------------------------------|
| `ArrayList`                  | ✅ Yes       | ✅ Yes    | —                            |
| `LinkedList`                 | ✅ Yes       | ✅ Yes    | —                            |
| `HashSet`                    | ✅ Yes       | ✅ Yes    | —                            |
| `TreeSet`                    | ✅ Yes       | ✅ Yes    | Only if elements (and comparator) are serializable. It supports serialization, but element compatibility matters. |
| `CopyOnWriteArrayList`       | ✅ Yes       | ❌ No     | Clone would be confusing/inefficient in concurrent use. |
| `CopyOnWriteArraySet`        | ✅ Yes       | ❌ No     | Same as above — cloning a concurrent structure is unsafe. |
| `PriorityQueue`              | ✅ Yes       | ❌ No     | Not Cloneable by default — priority logic not easily copyable. |
| `ArrayDeque`                 | ✅ Yes       | ❌ No     | Internal buffer state makes cloning error-prone. |
| `ConcurrentLinkedQueue`      | ✅ Yes       | ❌ No     | Cloning not safe or meaningful for concurrent queues. |
| `LinkedBlockingQueue`        | ✅ Yes       | ❌ No     | Cloning would violate thread-safety or queue semantics. |
| `HashMap`                    | ✅ Yes       | ✅ Yes    | —                            |
| `TreeMap`                    | ✅ Yes       | ✅ Yes    |  only when all of its keys, values, and custom comparator (if any) are also serializable. |
| `ConcurrentHashMap`          | ✅ Yes       | ❌ No     | Cloning a concurrent map is unsafe and complex. |

---

## 🧠 Why Not All Collections Implement These Interfaces

### 🔸 `Serializable`
- Not all collections are meant to be persisted.
- Many concurrent collections use internal state (locks, threads) that **cannot be serialized meaningfully**.

### 🔸 `Cloneable`
- Java's `Cloneable` is known to be **flawed** and performs a **shallow copy**, which is often insufficient.
- Concurrent collections may expose **inconsistent or invalid states** if cloned improperly.
- Java encourages using **copy constructors** instead:
  
  ```java
  List<String> copy = new ArrayList<>(originalList);
  
---

## 🔄 Arrays vs Collection: A Comparison

| Feature                          | Arrays                             | Collection                            |
|----------------------------------|------------------------------------|----------------------------------------|
| Size                             | Fixed                              | Dynamic (growable)                     |
| Type                             | Homogeneous only                   | Homogeneous & Heterogeneous            |
| Performance                      | High                               | Slightly lower due to abstraction      |
| Utility Methods                  | Not available                      | Rich collection.set of utility methods            |
| Underlying Structure             | None                               | Based on data structures               |
| Stores                           | Primitives & Objects               | Objects only                           |

---

## 📚 Collection vs Collections

| Term           | Description |
|----------------|-------------|
| `Collection`   | **Interface**: Root of the collection hierarchy. Represents a group of objects. |
| `Collections`  | **Utility class** in `java.util`: Contains static methods (e.g., `Collections.sort()`, `Collections.reverse()`). |

---

## 🔑 Core Interfaces of Collection Framework

### 1. `Collection` (Interface)

- Root interface of the collection framework.
- Note: There is no concrete class which implements collection interface directly.
- Common methods: `add()`, `remove()`, `isEmpty()`, etc.

---

### 2. `List` (Interface)

- Ordered collection — **insertion order is preserved**.
- Allows **duplicates**.

**Implementations**:
- `ArrayList`
- `LinkedList`
- `Vector`
- `Stack`

**Example**:

```java
List<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");
System.out.println(names);
```

---

### 3. `Set` (Interface)

- **No duplicate elements**.
- **Insertion order is not preserved**.
- Child of `Collection (I)`.

**Implementations**:
- `HashSet`
- `LinkedHashSet`

**Example**:

```java
Set<Integer> collection.set = new HashSet<>();
collection.set.add(10);
collection.set.add(20);
collection.set.add(10); // Duplicate won't be added
System.out.println(collection.set);
```

---

### 4. `SortedSet` (Interface)

- Child of `Set`.
- Maintains **sorted order** and **duplicates** are not allowed.

---

### 5. `NavigableSet` (Interface)

- It is child interface of `SortedSet (I)`.
- Provides navigation methods like `lower()`, `higher()`.

**Implementation**:
- `TreeSet`

**Example**:

```java
NavigableSet<Integer> tree = new TreeSet<>();
tree.add(100);
tree.add(200);
tree.add(150);
System.out.println(tree); // Sorted output
```

---

### 6. `Queue` (Interface)

- Child of `Collection (I)`.
- If we want to represent a group of individual objects prior to processing then we should go for Queue.
- FIFO (First-In-First-Out) structure.
- Example: sending an email to a thousand email address seperated by comma but before sending an email all mail id's
  need to be store somewhere and in which order we saved in the same order mail should be delivered Which is First in
  First Out for this requirement Queue concept is the best choice.

**Implementations**:
- `PriorityQueue`
- `BlockingQueue`

**Example**:

```java
Queue<String> queue = new LinkedList<>();
queue.add("Email1");
queue.add("Email2");
System.out.println(queue.poll());
```

---

## 🗺️ Map Interface

- Stores data as **key-value pairs**.
- **Keys must be unique**, values can be duplicate.
- `Map` and `Collection` Interface are different. `Map` is not child interface of `Collection`.

**Implementations**:
- `HashMap`
- `LinkedHashMap`
- `WeakHashMap`
- `IdentityHashMap`
- `Hashtable`
- `Properties`

**Example**:

```java
Map<Integer, String> map = new HashMap<>();
map.put(1, "Alice");
map.put(2, "Bob");
System.out.println(map.get(1));
```

---

### `SortedMap` and `NavigableMap`

- SortedMap: Sorts by keys.
- NavigableMap: Adds methods for navigation.

**Example**:

```java
NavigableMap<Integer, String> treeMap = new TreeMap<>();
treeMap.put(3, "Three");
treeMap.put(1, "One");
treeMap.put(2, "Two");
System.out.println(treeMap); // Sorted by keys
```

---

## 🔃 Sorting in Collection

### 1. `Comparable`

- Natural ordering.

```java
class Student implements Comparable<Student> {
    int roll;
    public int compareTo(Student s) {
        return this.roll - s.roll;
    }
}
```

### 2. `Comparator`

- Custom ordering.

```java
Comparator<Student> byRoll = (s1, s2) -> s1.roll - s2.roll;
```

---

## 🎯 Cursors in Collection Framework

| Cursor          | Direction | Modify Allowed | Applicable |
|-----------------|-----------|----------------|------------|
| `Enumeration`   | Forward   | No             | Legacy classes |
| `Iterator`      | Forward   | Yes (`remove()`) | All collections |
| `ListIterator`  | Bi-directional | Yes | List only |

---

## 🛠️ Utility Classes

| Class        | Description |
|--------------|-------------|
| `Collections` | Static methods for collection manipulation. |
| `Arrays`      | Static methods for array operations. |

---

