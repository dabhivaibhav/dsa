# 🧠 Binary Search

---

## 📌 What Is Binary Search?

**entity["algorithm","Binary Search",1]** is a **divide and conquer algorithm** used to find an element (or satisfy a condition) in a **sorted or monotonic space** efficiently.

Instead of scanning every element linearly, it:
1. Looks at the **middle element** of the current range.
2. If it equals the target — return its index.
3. If the target is **less than mid**, discard the **right half**.
4. If the target is **greater than mid**, discard the **left half**.
5. Repeat on the remaining half until found or range becomes empty.

It cuts the search space in half each step → `O(log n)` time.

---

## ⏱️ Time and Space Complexity

| Case                 | Complexity         |
|-------------------------|-------------------------|
| **Best Case**             | `O(1)` — target is at the first mid |
| **Worst/Average Case**    | `O(log n)` — space halves each step |
| **Space (Iterative)**     | `O(1)` |
| **Space (Recursive)**     | `O(log n)` (call stack depth) |

---

## ✅ When to Use Binary Search

- The data is **sorted** (ascending/descending) or can be **sorted first**.
- When you need **fast lookups** in large datasets.
- When the property you are testing is **monotonic** (once true, always true).
- When you want to find:
    - First or last occurrence of an element.
    - **Lower bound** (`arr[i] >= target`) or **upper bound** (`arr[i] > target`).
- On **rotated sorted arrays** (with modifications).
- On **2D sorted matrices** (flatten to 1D or use row/column search).
- On **continuous search spaces (floats/doubles)** like square roots or minimizing real values.
- On **answer space** instead of array values (**Binary Search on Answer (BSOA)**):
    - e.g. Minimum days to finish tasks, smallest possible maximum subarray sum.
    - Works if `check(x)` is **monotonic** (false…false → true…true).

---

## 🚫 When NOT to Use Binary Search

- Data is **unsorted** and cannot be sorted (like streaming/order-sensitive data).
- **Frequent insertions/deletions** make it hard to maintain sorted order.
- Dataset is **very small** — linear search is simpler and may be faster.
- You need **all occurrences** and you don’t adapt binary search to first/last occurrence.
- The property is **not monotonic** — values jump up and down.
- **Duplicates** present and not handled — standard binary search returns any one occurrence.

---

## ⚡ Advanced Variants & Techniques

### Lower Bound / Upper Bound
- **Lower Bound**: first index `i` where `arr[i] >= target`.
- **Upper Bound**: first index `i` where `arr[i] > target`.
- Crucial with duplicates and range counting.

---

### First and Last Occurrence
- Modify binary search to keep searching **left** (for first) or **right** (for last) after finding target.

---

### Binary Search on Answer (BSOA)
- Search over a **range of possible answers `[low..high]`**.
- Requires a **monotonic boolean `check(x)`**.
- Goal: find smallest `x` for which `check(x)` is true.
- Examples: minimize maximum subarray sum, minimum days to complete tasks, minimum page capacity.

---

### Binary Search on Real Numbers (Floats)
- Used when the search space is **continuous**.
- Stop when `(high - low) <= epsilon` (like `1e-6`).
- Examples: square root, geometric positions, minimizing real-valued functions.

---

### Search in Rotated Sorted Array
- One half is always sorted.
- Detect which half is sorted, check if target is in it, and adjust `low`/`high`.

---

### Binary Search in 2D Matrix
- If entire matrix is sorted row-wise and column-wise:
    - Flatten index: `mid → row = mid / cols, col = mid % cols`.
    - Or do row-wise + column-wise binary search.

---

### Ternary Search (Related)
- Not binary search, but used for **unimodal functions** (first increasing then decreasing) to find the peak.
- Often confused as "advanced binary search".

---

## ⚠️ Common Pitfalls

- **Overflow on mid calculation**
  ```java
  int mid = low + (high - low) / 2;
  ```
  Instead of `(low + high)/2` (may overflow `int`).

- **Infinite loops**
  Make sure `low` or `high` always change.

- **Off-by-one errors**
    - `while (low <= high)` → standard search
    - `while (low < high)` → lower/upper bound pattern

- **Duplicates**
  Standard binary search returns **any** occurrence, not first or last.

- **Empty arrays**
  Handle `if (arr.length == 0)` before doing `high = arr.length - 1`.

---

## 📐 Standard Iterative Template

```java
int binarySearch(int[] arr, int target) {
    int low = 0, high = arr.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) low = mid + 1;
        else high = mid - 1;
    }
    return -1; // not found
}
```

---

## 🧠 Space Comparison: Iterative vs Recursive

| Approach         | Time       | Space         |
|-------------------|--------------|----------------|
| **Iterative**       | `O(log n)`     | `O(1)`            |
| **Recursive**       | `O(log n)`     | `O(log n)` (stack frames) |

---

## ⚡ Real-World Performance Perspective

- **Logarithmic time** is extremely scalable — doubling input adds just **one iteration**.
- Common in:
    - Competitive programming (fast lookups)
    - Database indexes (entity["data_structure","B-tree"] / entity["data_structure","B+ tree"] use binary search inside nodes)
    - Operating systems (symbol tables, routing tables, kernel jump tables)
    - Compiler symbol lookups
- Often combined with:
    - **Pre-sorting (`O(n log n)`)** once
    - Then doing **many searches (`O(log n)` each)**

---

## 🧠 How to Quickly Decide If Binary Search Fits

Ask yourself:
- Is the array **sorted or can be sorted**?
- Is the property **monotonic** (false…false → true…true)?
- Can I define a **mid** and a **check(mid)** that tells me which half to go?

If **yes to all** → Binary Search is the right choice.

---

## 📌 Key Takeaways for Mastery

- Binary search is not just for arrays — it’s a **way of thinking**:  
  Shrink the search space in half using order/monotonicity.
- Always maintain the **invariant**:  
  After each iteration, the target (if it exists) is still inside `[low..high]`.
- Master these key variations:
    - First/last occurrence
    - Lower/upper bound
    - Binary search on answer
- Be vigilant about:
    - Overflow in `mid`
    - Infinite loops from incorrect updates
    - Off-by-one mistakes in conditions

---

## 📊 Summary Table

| Topic                          | Description                                                   |
|--------------------------------|-------------------------------------------------------------------|
| **Data Requirement**             | Sorted or monotonic                                              |
| **Time Complexity**               | `O(log n)`                                                       |
| **Space Complexity (Iterative)** | `O(1)`                                                            |
| **Space Complexity (Recursive)** | `O(log n)`                                                        |
| **Best Use Cases**                | Search in sorted data, boundary finding, answer space problems   |
| **Avoid When**                     | Data is unsorted, too small, non-monotonic                         |
| **Common Variants**                | Lower bound, upper bound, first/last occurrence, BSOA              |
| **Real-World Usage**                | DB indexes, OS tables, competitive programming, compiler lookups    |

---
