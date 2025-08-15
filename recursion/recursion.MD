
# 📘 Recursion in Java

## 🔹 When and Why to Use Recursion

Recursion is a programming technique where a function **calls itself** directly or indirectly to solve a problem.  
It is especially useful when:
- A problem **naturally breaks into smaller subproblems**.
- The solution to the problem can be expressed in terms of **solutions to smaller versions** of the same problem.
- Problems follow the **divide-and-conquer** paradigm.
- Data structures like **trees, graphs**, or **linked lists** need traversal.

Common examples:
- Tree traversals (preorder, inorder, postorder)
- DFS in graphs
- Divide-and-conquer algorithms (merge sort, quick sort, binary search)
- Mathematical problems (factorial, Fibonacci)

---

## 🔸 Key Characteristics of Recursion

- A recursive function must have:
    1. **Base Case** → condition where the recursion stops.
    2. **Recursive Case** → function calls itself with a smaller input.
- Uses the **call stack** for execution.
- Can lead to **stack overflow** if base case is missing or incorrect.
- Time complexity can grow quickly if overlapping subproblems are not handled (can be optimized using memoization or dynamic programming).

---

## 🔹 Divide and Conquer in Recursion

**Divide and Conquer** is a problem-solving strategy that:
1. **Divides** the problem into smaller subproblems.
2. **Conquers** each subproblem recursively.
3. **Combines** the results to get the final answer.

### ✅ How to identify a divide-and-conquer problem
- The problem can be split into **smaller, independent subproblems**.
- The subproblems are **similar in nature** to the original problem.
- The results from the subproblems can be **combined** to form the final solution.

### 📊 Examples

| Problem               | Divide                  | Conquer                | Combine                |
|-----------------------|------------------------|------------------------|------------------------|
| Merge Sort            | Split array into halves | Sort each half         | Merge sorted halves    |
| Quick Sort            | Partition array by pivot| Sort partitions        | Combine partitions     |
| Binary Search         | Choose midpoint         | Search in one half     | Return found index     |
| Closest Pair of Points| Split points in halves  | Find closest in halves | Check across boundary  |

---

## 🔸 Example: Print Name N Times (from TakeUForward)

```java
public class RecursionExample {

    // Recursive method to print name N times
    static void printName(int i, int n) {
        if (i > n) return; // Base case
        System.out.println("Vaibhav");
        printName(i + 1, n); // Recursive case
    }

    public static void main(String[] args) {
        int N = 5;
        printName(1, N);
    }
}
```

**Explanation**:
- **Base Case**: Stop when `i > n`.
- **Recursive Case**: Call the function again with `i + 1`.
- **Execution Flow**:
    - `printName(1, 5)` → prints name → calls `printName(2, 5)`
    - This continues until `i = 6`, where the base case stops recursion.

---

## 🔹 Call Stack Visualization

```
printName(1, 5)
    → printName(2, 5)
        → printName(3, 5)
            → printName(4, 5)
                → printName(5, 5)
                    → printName(6, 5) [Base case: stop]
```

At each call, the current state is pushed onto the call stack.  
Once the base case is reached, the stack starts **unwinding**.

---

## 🔸 Time & Space Complexity

| Case              | Complexity | Explanation |
|-------------------|------------|-------------|
| **Time**          | O(N)       | Each recursive call executes once, and there are `N` calls. |
| **Space**         | O(N)       | Due to call stack usage — one stack frame per function call until base case is hit. |

### Why Recursion Complexity is O(N) Here?
- Each call performs O(1) work (print statement + increment).
- There are `N` calls until the base case.
- No overlapping subproblems, so no extra optimization needed.

---

## 🔹 Time Complexity of Recursion — General Guide

The time complexity of a recursive algorithm depends on:
1. **Number of recursive calls** made in total.
2. **Work done in each call** outside the recursive calls.
3. The **recurrence relation** formed by the problem.

### 1) Direct Recursion (One Call per Execution)
- Example: Printing numbers from `n` to `1`  
  Recurrence: `T(n) = T(n-1) + O(1)` → **O(n)**

### 2) Multiple Recursive Calls
- Example: Fibonacci without memoization  
  `T(n) = T(n-1) + T(n-2) + O(1)` → **O(2^n)**

### 3) Divide and Conquer (Master Theorem)
```
T(n) = aT(n/b) + O(f(n))
```
- **a** = Number of subproblems
- **n/b** = Size of each subproblem
- **O(f(n))** = Time to divide/combine

**Master Theorem Cases**
- If `f(n) = O(n^(log_b a - ε))` → **T(n) = O(n^(log_b a))**
- If `f(n) = Θ(n^(log_b a))` → **T(n) = O(n^(log_b a) * log n)**
- If `f(n) = Ω(n^(log_b a + ε))` and regularity holds → **T(n) = O(f(n))**

## Common D&C Complexities
| Algorithm      | Recurrence                       | Complexity |
|----------------|-----------------------------------|------------|
| Merge Sort     | T(n) = 2T(n/2) + O(n)             | O(n log n) |
| Quick Sort*    | T(n) = T(k) + T(n-k-1) + O(n)     | Avg: O(n log n), Worst: O(n²) |
| Binary Search  | T(n) = T(n/2) + O(1)              | O(log n)   |

\* Worst case if pivot selection is poor.

---

## 📊 Recursion vs Iteration

| Feature           | Recursion                           | Iteration                         |
|-------------------|-------------------------------------|------------------------------------|
| **Definition**    | Function calls itself               | Loop repeats block of code        |
| **Base Condition**| Required to stop                    | Loop termination condition        |
| **Memory**        | Uses call stack (O(N) space)        | Constant memory (O(1) space)      |
| **Performance**   | Slower (call overhead)              | Faster (no call overhead)         |
| **Readability**   | Clear for trees/backtracking        | Clear for linear repetition       |
| **Risk**          | Stack overflow if depth is large    | Few such risks                    |

---

## ✅ Advantages of Recursion
- Cleaner, more intuitive for recursive structures (trees/graphs).
- Reduces code size for repetitive patterns.
- Aligns with mathematical definitions (e.g., factorial, Fibonacci).

## ❌ Disadvantages of Recursion
- Higher memory usage (stack frames).
- Risk of **StackOverflowError**.
- May be slower due to call overhead.

---

# Multiple Recursion

## Why do we need multiple recursion calls?
Multiple recursion is needed when a single function call must branch into more than one recursive call in order to solve a problem. This happens when the problem’s structure naturally requires exploring multiple independent subproblems at each step, rather than just one.

In short, you need multiple recursion when the solution to the current state is the *combined result* of multiple smaller states that must each be explored. If you only have one smaller state to handle at each step, single recursion is enough.

---

## How to calculate time complexity for multiple recursion
To calculate the time complexity for a multiple recursion algorithm, follow these steps:

1. **Identify the recurrence relation**
    - Determine how many recursive calls are made per function call.
    - Example: Fibonacci without memoization makes two calls: `T(n) = T(n-1) + T(n-2) + O(1)`.

2. **Express it as a recurrence**
    - If each call spawns `k` calls on smaller inputs of size `n - m`, you can write:
      ```
      T(n) = k * T(n - m) + O(1)
      ```

3. **Solve the recurrence**
    - Use the **recursion tree method** or **Master Theorem** (if applicable).
    - Example for Fibonacci: `T(n) ≈ 2^n` because the recursion tree has exponential growth.

4. **Consider overlapping subproblems**
    - If the recursion recomputes the same results many times, the complexity can blow up exponentially.
    - With memoization or dynamic programming, the same multiple recursion can be reduced to polynomial time.

**Example Table:**

| Problem                  | Recursive Calls per Step | Recurrence Relation                  | Time Complexity |
|--------------------------|--------------------------|---------------------------------------|-----------------|
| Fibonacci (no memo)      | 2                        | `T(n) = T(n-1) + T(n-2) + O(1)`       | O(2^n)          |
| Binary Tree Traversal    | 2                        | `T(n) = 2T(n/2) + O(1)`               | O(n)            |
| Merge Sort               | 2                        | `T(n) = 2T(n/2) + O(n)`               | O(n log n)      |

---

## 🧠 DSA Tips
- Prefer recursion for **tree/graph traversal**, **divide & conquer**, and **backtracking**.
- Ensure your **base case** is correct and reachable.
- For **overlapping subproblems**, switch to **memoization** or **bottom-up DP**.
- For very deep recursion, consider iterative solutions to avoid stack limits.
