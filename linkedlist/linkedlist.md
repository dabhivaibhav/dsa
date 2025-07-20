
# 📚 LinkedList in Java

## 🔍 What is the Underlying Data Structure?

- The underlying data structure of `LinkedList` is a **Doubly Linked List**.
- Nodes are stored **non-contiguously** in memory.
- Each node contains:
    - Data
    - Reference to the **previous** node
    - Reference to the **next** node

---

## 🚀 When Should You Use a LinkedList?

Use `LinkedList` when:

- You need **frequent insertions or deletions** of elements.
- **Random access** is **not** a priority (poor retrieval performance due to linear traversal).
- Elements need to be **added/removed from the beginning, middle, or end** frequently.

Avoid `LinkedList` if:

- Your main operation is **retrieval by index**.
- You need **cache locality** or **fast access** to elements.

---

## 🌟 Characteristics of LinkedList

| Feature                          | Support        |
|----------------------------------|----------------|
| Duplicates Allowed               | ✅ Yes         |
| Heterogeneous Elements Allowed   | ✅ Yes         |
| Memory Allocation                | ❌ No contiguous allocation |
| Growable                         | ✅ Yes         |
| Insertion Order Preserved        | ✅ Yes         |
| Access by Index                  | ❌ No (Traverses from head) |
| Removal by Index                 | ❌ No direct access (Traversal needed) |
| Capacity Concept                 | ❌ Not applicable (linked structure) |
| Underlying Structure             | 🔁 Doubly Linked List |

---

## 🏗️ Constructors

```java
// Creates an empty LinkedList
LinkedList l1 = new LinkedList();

// Creates a LinkedList initialized with elements from a Collection
LinkedList l2 = new LinkedList(Collection c);
```

- `LinkedList()` — Initializes an empty linked list.
- `LinkedList(Collection c)` — Constructs a linked list containing the elements of the specified collection, in the order they are returned by the collection’s iterator.

---

## 🔄 Use Cases

### ✅ LinkedList can be used to implement:

- **Stack**
    - Use `addFirst()` and `removeFirst()` for push/pop
- **Queue**
    - Use `addLast()` and `removeFirst()` for enqueue/dequeue

> Note: `ArrayDeque` is often preferred for performance over `LinkedList` for Stack/Queue due to better cache performance.

---

## ⏱️ Time Complexity Table

| Operation            | Time Complexity | Explanation                                                                 |
|----------------------|------------------|-----------------------------------------------------------------------------|
| Add at Beginning     | O(1)             | Adjusts head reference                                                      |
| Add at End           | O(1)             | Maintains tail reference for constant time insertion                        |
| Add at Middle        | O(n)             | Traverses to the specific position                                          |
| Remove from Beginning| O(1)             | Adjusts head reference                                                      |
| Remove from End      | O(1)             | Tail reference allows constant time deletion                                |
| Remove from Middle   | O(n)             | Requires traversal to that node                                             |
| Get Element by Index | O(n)             | Traverses from head or tail depending on index position                     |
| Search Element       | O(n)             | Linearly checks each node                                                   |

---

## ⚙️ Additional Notes for DSA:

### 1. **Traversal**:
- LinkedList does not allow direct access to elements via an index. You need to traverse from the head or tail node to access an element, which takes O(n) time.

### 2. **Memory Usage**:
- In comparison to arrays, `LinkedList` uses more memory because of the storage required for the next and previous references (pointers) in each node.

### 3. **Applications**:
- **LRU Cache Implementation**: A LinkedList can be used to implement an efficient Least Recently Used (LRU) cache, where elements are moved to the front or back as they are used.
- **Polynomial Representation**: A linked list can be used to represent a polynomial where each node stores a term of the polynomial.
- **Undo Mechanism in Software**: LinkedList can be utilized to keep track of previous actions for undo functionality.

### 4. **Memory Considerations**:
- If memory efficiency is critical and you don’t need the benefits of dynamic size allocation, using arrays might be a better choice.

### 5. **Doubly Linked List**:
- In a doubly linked list, each node has two references (next and previous), which allows traversal in both directions. This comes at the cost of extra memory overhead compared to singly linked lists.

---

## 🧑‍💻 Example Code for LinkedList Operations:

```java
import java.util.LinkedList;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        // Adding elements
        list.add(10); // Adds at the end
        list.addFirst(5); // Adds at the beginning
        list.addLast(15); // Adds at the end

        // Removing elements
        list.removeFirst(); // Removes the first element
        list.removeLast(); // Removes the last element

        // Accessing elements
        System.out.println("First Element: " + list.getFirst());
        System.out.println("Last Element: " + list.getLast());

        // Traversing the list
        for (Integer num : list) {
            System.out.println(num);
        }
    }
}
```

---

## 📖 Conclusion

LinkedList is a flexible data structure used for cases where frequent insertions and deletions are necessary. While it doesn't support fast random access, its ability to efficiently add and remove nodes makes it a powerful tool for many applications. Understanding the trade-offs in terms of memory usage and traversal time is important when deciding when to use LinkedList versus other data structures like ArrayList.

