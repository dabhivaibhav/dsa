
# ⚡ Quick Sort — Complete Guide (with Java Hoare-partition code walk-through)

## 🔹 When & Why to Use Quick Sort
Quick Sort is a **divide-and-conquer**, **in‑place** comparison sort. It’s often the fastest general-purpose sort in practice due to great cache locality and low constant factors. Unlike Merge Sort, it **does not need an auxiliary array**; it partitions the array around a **pivot** and recursively sorts the subarrays.

**Use Quick Sort when you want:**
- Very fast average performance on large, random or “typical” data.
- In‑place sorting with **O(log n)** extra space (recursion stack) on average.
- Flexibility to tune pivot strategy or partition scheme for your data.

**Avoid or tune carefully when:**
- The input is **already sorted / adversarial** and you pick a bad pivot (risk **O(n²)**).
- You need **stability** (relative order of equal elements preserved) — classic Quick Sort is **not stable**.
- Worst‑case recursion depth is a concern (consider randomization, median‑of‑three, or 3‑way partitioning).

---

## 🔸 Core Idea (at a glance)
1. **Pick a pivot** element.
2. **Partition** the array so that elements **≤ pivot** are on the left and **> pivot** on the right (or vice‑versa).
3. **Recurse** on the two partitions.

The secret sauce is the **partition** procedure. Common schemes are **Lomuto** and **Hoare**; your code (below) implements a **Hoare-style** partition with the pivot at the **start**. Hoare typically does **fewer swaps** than Lomuto and performs better when there are many duplicates.

---

## 🔹 The Provided Java Implementation (Hoare-style partition)

```java
public class QuickSortAlgo {

    public static void main(String[] args) {
        int[] arr = {10, 7, 8, 9, 1, 5};
        int n = arr.length;
        quickSort(arr, 0, n - 1);
        System.out.println("Sorted array using Quick Sort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // pi is partitioning index, arr[pi] is now at right place
            int pi = partition(arr, low, high);

            // Recursively sort elements before partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];
        int i = low;
        int j = high;
        while (i < j) {

            while (arr[i] <= pivot && i <= high - 1) {
                i++;
            }

            while (arr[j] > pivot && j >= low + 1) {
                j--;
            }

            if (i < j) {
                // Swap arr[i] and arr[j]
                int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            }
        }
        int temp = arr[low]; arr[low] = arr[j]; arr[j] = temp;
        return j;
    }
}
```

### 🧠 How this partition works (invariants & reasoning)
We choose the **first element** as `pivot = arr[low]`. We scan from both ends:
- `i` moves **right** while `arr[i] <= pivot` (stops at the first element `> pivot` or at `high`).
- `j` moves **left** while `arr[j] > pivot` (stops at the first element `<= pivot` or at `low`).

**Loop invariant throughout `while (i < j)`**:
- All elements in `arr[low+1 .. i-1]` are `<= pivot`.
- All elements in `arr[j+1 .. high]` are `> pivot`.  
  When `i < j` and both pointers have stopped, we have a pair of **misplaced** elements — `arr[i] > pivot` on the left and `arr[j] <= pivot` on the right — so we **swap** them. This progressively repairs the two partitions.

When pointers **cross** (`i >= j`), every element in `arr[low+1 .. j]` is `<= pivot`, and every element in `arr[j+1 .. high]` is `> pivot`. Finally we **swap the pivot** (`arr[low]`) with `arr[j]`. Now the pivot is in its **final sorted position** at index `j`. Recurse on the subarrays `[low .. j-1]` and `[j+1 .. high]`.

> This is a standard Hoare-style partition variant; Hoare’s scheme generally uses fewer swaps than Lomuto and can partition equal values more efficiently.

### 🔍 Why the boundary tests look like that?
- `while (arr[i] <= pivot && i <= high - 1) i++;`
    - We allow `<=` so duplicates of the pivot drift to the **left** side; the first value that **breaks** the left condition is strictly `> pivot`.
    - The guard `i <= high - 1` prevents `i` from marching beyond the subarray end while comparing `arr[i]`.
- `while (arr[j] > pivot && j >= low + 1) j--;`
    - We use `>` so `j` stops at the first `<= pivot`, which belongs on the left; then a swap with `arr[i]` fixes both sides.
    - The guard `j >= low + 1` prevents `j` from **crossing the pivot** at `low` before the loop ends.
- The final swap `swap(arr[low], arr[j])` places the pivot at the **split point** `j`. After this, the pivot is **fixed**, so the recursive calls **exclude** it: `[low .. j-1]` and `[j+1 .. high]`.

> In some Hoare implementations, the recursive ranges are `[low .. p]` and `[p+1 .. high]`; with this exact variant (pivot swapped to `j`), using `[low .. j-1]` / `[j+1 .. high]` is correct.

### 🧪 Mini dry‑run (first two partitions) on `{10, 7, 8, 9, 1, 5}`
- **1st partition** (`low=0, high=5`, `pivot=10`):  
  `i` walks to `high`, `j` stays at `5` → swap pivot with `arr[5]` → array becomes `{5, 7, 8, 9, 1, 10}`, return `j=5`.  
  Recurse on `[0..4]`.
- **2nd partition** on `[0..4]` (`pivot=5`):  
  After pointer moves and one swap, final swap puts pivot at index `1`. Array becomes `{1, 5, 8, 9, 7, 10}`. Recurse on `[0..0]` and `[2..4]`.  
  (Continuing yields the fully sorted array.)

---

## 🔧 Pivot Selection — strategies & impact
- **First/last element**: simple but risky on sorted/reversed inputs (can trigger **O(n²)**).
- **Random pivot**: pick a random index; with high probability avoids adversarial inputs and yields **O(n log n)** expected time.
- **Median‑of‑three**: median of `(first, middle, last)`; stabilizes performance on partially sorted inputs.
- **Dual‑pivot / multi‑pivot**: partition into 3+ regions to reduce work and improve cache behavior; Java uses **dual‑pivot Quick Sort** for primitive arrays.

---

## 🧩 Partition Schemes (quick overview)

| Scheme   | Condition to advance left pointer | Condition to advance right pointer | Split indices to recurse | Traits |
|---|---|---|---|---|
| **Lomuto** | `a[i] <= pivot` with single pass and a storeIndex | N/A (single forward scan) | `[low..p-1]` & `[p+1..high]` | Very simple, more swaps, sensitive to duplicates. |
| **Hoare**  | move `i` right while `a[i] <(=) pivot` | move `j` left while `a[j] >(=) pivot` | often `[low..p]` & `[p+1..high]` (variant‑dependent) | Fewer swaps, efficient with duplicates, watch ranges.|

> For many duplicates, consider **3‑way partitioning** (elements `< pivot`, `== pivot`, `> pivot`) to reduce recursion depth and comparisons.

---

## ⏱️ Time & Space Complexity

| Case | Time | Why |
|---|---|---|
| **Best** | `O(n log n)` | Pivot splits roughly in half each time. |
| **Average** | `O(n log n)` | Random inputs / randomized pivot lead to balanced partitions in expectation. |
| **Worst** | `O(n²)` | Highly unbalanced splits (e.g., sorted input with first/last pivot). |

**Space (auxiliary):** `O(log n)` on average for recursion stack; `O(n)` in the worst case with highly skewed partitions. **In‑place** apart from the stack. 

**Stability:** Not stable (equal keys can reorder).

---

## 🛠️ Engineering Tips
- **Randomize** the pivot or use **median‑of‑three** to avoid pathological cases.
- When subarray length is small (e.g., ≤ 16–32), switch to **Insertion Sort** to reduce overhead — common in production sorts.
- Tail‑call elimination is **not guaranteed** in Java; you can **convert** one recursion into a loop (“tail‑recursion elimination”) to limit stack depth.
- For primitive arrays in Java, `Arrays.sort(...)` uses **Dual‑Pivot Quick Sort**; for object arrays, Java uses **TimSort** (a stable, adaptive mergesort variant).

---

## ✅ Why this algorithm is fast in practice
- **Cache locality:** partitioning scans the array sequentially.
- **In‑place:** minimal memory traffic compared to mergesort’s aux array.
- **Few comparisons/moves:** Hoare partition does **fewer swaps** on average than Lomuto. 

---

## 📎 Common Pitfalls (and fixes)
- **Infinite loops** in Hoare partition: ensure both inner loops **move at least once** and use correct boundary guards (`i <= high-1`, `j >= low+1`).
- **Off‑by‑one** in recursive ranges: for this variant, recurse on `[low..j-1]` and `[j+1..high]` after swapping pivot to `j`.
- **Duplicates**: consider 3‑way partitioning or use `<=`/`>=` consistently so progress is guaranteed. 

---

## 🧾 References / Further Reading
- Take U Forward — *Quick Sort Algorithm* (in‑place, no extra array). 
- Princeton (Algorithms, Sedgewick) — partitioning invariants and quicksort tutorial.
- Wikipedia — Hoare vs Lomuto partition, efficiency, and pseudocode. 
- *Why is Dual‑Pivot Quicksort fast?* — analysis of Java’s choice for primitives. 
- Wikipedia — Timsort (used for Java object arrays). 

---

---

## 🔀 Quick Sort Variants Beyond Hoare

While your main implementation uses **Hoare partition**, two other important variants are widely taught and useful: **Lomuto partition** and **3-Way partitioning**. Below are their Java implementations, detailed walkthroughs, and when to use them.

---

# 1️⃣ Quick Sort with Lomuto Partition

### Code (pivot = last element)
```java
public class QuickSortLomuto {

    public static void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int p = partition(a, low, high);   // pivot placed at index p
            quickSort(a, low, p - 1);          // left of pivot
            quickSort(a, p + 1, high);         // right of pivot
        }
    }

    private static int partition(int[] a, int low, int high) {
        int pivot = a[high];              // choose last element as pivot
        int i = low;                      // boundary for <= pivot region
        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, high);                 // put pivot between <= and > regions
        return i;                         // pivot’s final index
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
```

### How it Works
- **Invariants while scanning**:
    - `a[low .. i-1] ≤ pivot`
    - `a[i .. j-1] > pivot`
    - `a[j .. high-1]` unknown
- For each `j`, if `a[j] ≤ pivot`, we expand the `≤` block by swapping `a[j]` into it.
- After loop, swap pivot into position `i`. Now:
    - Left block `≤ pivot`, index `i = pivot`, right block `> pivot`.
- Recursive calls exclude the pivot.

### Why `<=` not `<`?
Ensures duplicates go left and that `i` always advances, preventing infinite loops.

### Pros & Cons
- ✅ Simple to implement.
- ❌ More swaps than Hoare, sensitive to duplicates.

---

# 2️⃣ Quick Sort with 3-Way Partitioning (Dutch National Flag)

Ideal for arrays with **many duplicate elements**.

### Code
```java
public class QuickSortThreeWay {

    public static void quickSort(int[] a, int low, int high) {
        if (low >= high) return;

        int pivot = a[low];  // pivot at start (can randomize)

        int lt = low;       // boundary for < pivot
        int i  = low + 1;   // current pointer
        int gt = high;      // boundary for > pivot

        while (i <= gt) {
            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++; i++;
            } else if (a[i] > pivot) {
                swap(a, i, gt);
                gt--;           // don’t increment i, re-check swapped element
            } else {
                i++;
            }
        }

        // Now: a[low..lt-1] < pivot, a[lt..gt] == pivot, a[gt+1..high] > pivot
        quickSort(a, low, lt - 1);
        quickSort(a, gt + 1, high);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
```

### How it Works
- **Regions maintained during scan**:
    - `< pivot`: `a[low .. lt-1]`
    - `= pivot`: `a[lt .. i-1]`
    - `unknown`: `a[i .. gt]`
    - `> pivot`: `a[gt+1 .. high]`
- **Actions**:
    - `< pivot`: swap into `<` region (`lt++`, `i++`)
    - `> pivot`: swap into `>` region (`gt--`, `i` stays)
    - `= pivot`: just `i++`
- Recursively sort only `<` and `>` regions.

### Advantages
- Very efficient when many elements equal pivot.
- Reduces recursion depth and comparisons.

---

## 🔎 Choosing a Variant

| Variant         | Use When… |
|-----------------|------------|
| **Lomuto**      | Teaching, simple code, small helper sort |
| **Hoare**       | Fewer swaps, good general performance |
| **3-Way**       | Many duplicates in the array |
| **Randomized**  | Need to defend against adversarial inputs |

All have the same **O(n log n)** average and **O(n²)** worst-case complexities; space is `O(log n)` for recursion.
