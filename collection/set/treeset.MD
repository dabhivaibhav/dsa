
# 🌲 Java Collections: TreeSet

## 🔹 What is TreeSet?

`TreeSet` is a collection in Java that stores elements in a **sorted and unique** manner. It is backed by a **Red-Black Tree**, a self-balancing binary search tree, which ensures that operations like insertion, deletion, and lookup are always efficient.

---

## 🔸 Key Characteristics

- ✅ **Underlying Data Structure**: Red-Black Tree (a type of self-balancing BST)
- ❌ **Insertion Order Not Preserved**
- ❌ **No Duplicate Elements Allowed**
- ❌ **Heterogeneous Elements Not Allowed** (unless a custom `Comparator` is provided)
- ⚠️ **Null Insertion**:
    - Allowed only **once**, and **only** if the collection.set is initially empty.
    - Any further insertion or comparison with `null` will cause a `NullPointerException`.

---

## 🔸 TreeSet Constructors

| Constructor | Description |
|-------------|-------------|
| `TreeSet()` | Creates an empty collection.set sorted in **natural order**. |
| `TreeSet(Comparator c)` | Creates a collection.set with **custom sorting order** using a `Comparator` object. |
| `TreeSet(Collection c)` | Creates a collection.set containing elements of the collection in **sorted order**. |
| `TreeSet(SortedSet s)` | Creates a collection.set with the same sorting as the provided `SortedSet`. |

---

## 🔸 Rules for Null Insertion

- **Empty TreeSet**: `null` can be added as the first element.
- **Non-Empty TreeSet**: Adding `null` results in **`NullPointerException`** because it must be compared with existing elements.
- Comparison like `null.compareTo("A")` throws `NullPointerException`.

---

## 🔸 Rules for Element Insertion in TreeSet

- If using **default natural order**, elements must be:
    - ✅ **Homogeneous**
    - ✅ **Comparable**
    - ❌ Otherwise, you'll get a `ClassCastException`
- If using a **custom comparator**, implement the `Comparator` interface and pass it to the constructor.

---

## 🔸 Comparable Interface

- Present in `java.lang`
- Declares the method:
```java
public int compareTo(Object obj)
```

### Meaning of return values:
- `obj1.compareTo(obj2)` → `-ve`: `obj1` comes **before**
- `obj1.compareTo(obj2)` → `+ve`: `obj1` comes **after**
- `obj1.compareTo(obj2)` → `0`: **equal**
- `obj1.compareTo(null)` → ❌ `NullPointerException`

---

## 🔸 Comparator Interface

- Present in `java.util`
- Used for **custom sorting** if default isn't sufficient

### Methods:
```java
public int compare(Object obj1, Object obj2)
```

- `compare(obj1, obj2)` → `-ve`: `obj1` before `obj2`
- `compare(obj1, obj2)` → `+ve`: `obj1` after `obj2`
- `compare(obj1, obj2)` → `0`: equal

> Note: Though `Comparator` has both `compare()` and `equals()`, you only need to implement `compare()` since `equals()` is already defined in `Object`.

---

## 🧑‍🏫 Comparable vs Comparator Use Case

- A class **designer** should implement `Comparable` to define **natural sorting**.
- A class **user** can implement `Comparator` to define **custom sorting**.

### Example:
If you define an `Employee` class, the class itself can be `Comparable<Employee>` to sort by ID, and someone else using it can write a `Comparator<Employee>` to sort by name or salary.

---

## ⚠️ Special Case: StringBuffer

`StringBuffer` does **not implement** `Comparable`. So:

- ✅ Homogeneous ✔️
- ❌ Not Comparable ❌
  → **Fails with TreeSet using natural order**.

If you want to store `StringBuffer` in a TreeSet, you **must provide a custom `Comparator`**.

---

## ⏱ Time Complexity Table for TreeSet

| Method                      | Time Complexity | Description |
|-----------------------------|------------------|-------------|
| `add(E e)`                  | O(log n)         | Inserts element in sorted position |
| `remove(Object o)`          | O(log n)         | Removes specified element |
| `contains(Object o)`        | O(log n)         | Checks for existence |
| `first()` / `last()`        | O(log n)         | Retrieves smallest or largest |
| `headSet(E toElement)`      | O(log n + k)     | Returns all elements less than `toElement` |
| `tailSet(E fromElement)`    | O(log n + k)     | Returns all elements ≥ `fromElement` |
| `subSet(E from, E to)`      | O(log n + k)     | Elements between `from` (inclusive) and `to` (exclusive) |
| `iterator()`                | O(n)             | Returns iterator in sorted order |

---

## 🧠 Why TreeSet Time Complexity is O(log n)

The **TreeSet** is backed by a **Red-Black Tree**, a type of self-balancing Binary Search Tree.

### What it guarantees:

- Tree height is **log₂(n)** at most
- Every operation like `add`, `remove`, `contains` traverses from **root to leaf**, which is O(log n)
- Self-balancing ensures no path is more than twice as long as any other → avoids skewed structure

This balance keeps all operations efficient, even in the worst case.

---

## 📊 Difference Between Comparable and Comparator

| Feature | Comparable | Comparator |
|--------|------------|------------|
| Usage | Used for **default natural sorting order** | Used for **customized sorting order** |
| Package | `java.lang` | `java.util` |
| Methods | `compareTo(Object obj)` | `compare(Object obj1, Object obj2)`, `equals(Object obj)` *(optional)* |
| Implementation | Implemented by all wrapper classes and `String` | Implemented by `Collator`, `RuleBasedCollator` and others |
| Purpose | Used by class designers to define natural order | Used by class users to override or provide new order dynamically |
| Sorting Logic | Defined inside the actual class | Defined outside the class (external sorting logic) |

---