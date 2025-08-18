

# Selection Sort

## How it Works
- Split the array into two parts: "sorted" at the front and "unsorted" after it.
- In each pass, find the minimum element from the unsorted part and place it at the beginning of that part.
- After each pass, the sorted part grows by one element, while the unsorted part shrinks.
- Keep repeating until the entire array is sorted.

---

## Plain English Notes for Future Reference

### Goal
Keep splitting the array into sorted and unsorted sections.  
Each pass finds the minimum element in the unsorted section and swaps it to the front.

### Brute-force version
- For every position i (from 0 to n-2):
    - Assume arr[i] is the minimum.
    - Scan the rest of the unsorted part (i+1 to n-1) to find the true minimum.
    - Swap the found minimum with arr[i].
- After all passes, the array is sorted.

### Why the two for-loops exist
- **Outer loop (i):** Decides which index we are fixing in this pass.
- **Inner loop (j):** Scans the rest of the array to find the minimum.
- At the end of each outer loop, the element at index i is in its final sorted place.

### Key points (compared to Bubble Sort)
- No early-exit optimization: even if already sorted, Selection Sort keeps scanning.
- Always O(n²) time complexity in best, worst, and average cases.

### Mental picture
- Pass 1: find the smallest element in the array, put it at index 0.
- Pass 2: find the smallest element from the rest, put it at index 1.
- Pass 3: find the smallest from the remaining, put it at index 2.
- Continue until the array is sorted.

---

## Complexity Analysis

- **Worst-case Time Complexity:** O(n²)  
  (Always scans the unsorted part in each pass)

- **Average-case Time Complexity:** O(n²)  
  (Still needs to scan each unsorted element in every pass)

- **Best-case Time Complexity:** O(n²)  
  (No optimization, even for sorted arrays)

- **Space Complexity:** O(1)  
  (Sorting done in place with constant memory)

- **Stability:** Not stable  
  (Equal elements may change relative order during swaps)

---

### Important point to remember
*"At each step, grab the smallest from the unsorted part and stick it at the front."*

---
# Bubble Sort

## How it Works
- Imagine you keep walking through the list, comparing two neighbors at a time.
- If the left one is bigger than the right one, swap them.
- After one full walk, the biggest number has "bubbled up" to the end.
- Then you do the same walk again, but now you don’t need to look at the last part,  
  because it’s already sorted.
- Keep repeating until no swaps are needed (which means the list is sorted).

---

## Implementation

### Goal
Walk through the list, compare neighbors, and swap when out of order.  
Each full pass pushes the current largest item to the end, like a bubble rising.

### Brute-force version (simple but wasteful)
- Do (n-1) full passes no matter what.
- In each pass, compare every neighbor pair from index 0 to n-2.
- Swap if arr[j] > arr[j+1].
- This sorts the array, but it keeps re-checking parts that are already sorted.

### Why the two for-loops exist
- **Outer loop** = "how many passes" we make over the array.  
  In the worst case we need (n-1) passes.
- **Inner loop** = "the actual comparisons/swaps" in a single pass.  
  After i passes, the last i elements are already in their final spots,  
  so we only need to compare up to index (n - i - 2). Hence: j < n - i - 1.

### Key optimization #1 (shrink the inner loop)
- After each pass, the biggest remaining element is at the end.
- So the region we need to check gets smaller each time: j < n - i - 1.

### Key optimization #2 (boolean 'swapped' for early exit)
- If we go through a whole pass and never swap, the array was already sorted.
- Use a boolean 'swapped' that starts false for each pass; set it true if we swap.
- If 'swapped' stays false after a pass, break early. No more work needed.
- This turns best-case time from **O(n^2)** to **O(n)** (already sorted input).

### Mental picture
- Pass 1: largest item moves to index n-1.
- Pass 2: next-largest moves to index n-2.
- Keep going until either we’ve done n-1 passes or we detect no swaps.

---

## Complexity Analysis

- **Worst-case Time Complexity:** O(n²)  
  (Array in reverse order; every element needs to be swapped in each pass)

- **Average-case Time Complexity:** O(n²)  
  (Random order; on average about half of possible swaps occur)

- **Best-case Time Complexity:** O(n)  
  (Array already sorted; boolean 'swapped' avoids extra passes)

- **Space Complexity:** O(1)  
  (Sorting is done in place with constant extra memory)

- **Stability:** Yes  
  (Equal elements maintain their original order)

---

### Key point to remember
*"Do passes that swap out-of-order neighbors, shrink the range each pass, and stop early if no swaps."*
---

# INSERTION SORT

### Goal:
Build the sorted array one element at a time, by taking the current element
and placing it in its correct position among the elements before it.

### Brute-force idea:
- Start with the second element (index 1).
- Compare it with the element(s) before it and keep swapping backwards
  until it’s in the right place.
- Then move to the next element and repeat the process.
- By the end, all elements before the current index are sorted.

### Why the for-loop and while-loop exist:
- Outer for-loop (i): picks the "current element" to insert into the sorted part.
- Inner while-loop (j): moves the current element backwards, swapping until it
  reaches its correct spot.

### Mental picture:
- Pass 1: put element at index 1 into correct place relative to index 0.
- Pass 2: put element at index 2 into correct place among first two elements.
- Pass 3: put element at index 3 into correct place among first three, and so on.
- Each pass grows the "sorted prefix" by one.

### Key observation:
- The left part of the array (0...i-1) is always sorted.
- The element at arr[i] is "inserted" into this sorted part at its correct position.

### Complexities:
- Worst-case Time: O(n²)  (array in descending order → lots of shifts/swaps)
- Average-case Time: O(n²)
- Best-case Time: O(n)    (array already sorted → just one comparison per element)
- Space: O(1)             (sorting in place)
- Stability: Yes          (equal elements keep original order)

### Important point to remember:
"Take each new element and walk it backwards into its correct place in the sorted prefix."
*/
---
# Comparison of Bubble Sort, Selection Sort, and Insertion Sort

## 1. Bubble Sort
- **Idea**: Repeatedly compare adjacent elements and swap if out of order. Largest elements "bubble" to the end.
- **Best Use Cases**:
    - Teaching algorithm basics (easy to visualize and understand).
    - Checking if an array is already sorted (with the swapped-flag optimization).
- **Pros**:
    - Very simple to implement.
    - Detects sorted input quickly (O(n) best case).
- **Cons**:
    - Very slow on large arrays (O(n²) worst/average).
    - Too many swaps compared to other algorithms.

---

## 2. Selection Sort
- **Idea**: Repeatedly select the minimum element from the unsorted part and put it in its correct place at the front.
- **Best Use Cases**:
    - When memory writes are expensive but comparisons are cheap  
      (because Selection Sort makes exactly *n–1 swaps*).
    - Small arrays where stability is not important.
    - Teaching min-finding and in-place sorting.
- **Pros**:
    - Simple to implement.
    - Minimum number of swaps (good for memory-constrained systems).
- **Cons**:
    - Comparisons are still O(n²).
    - Not adaptive (no early stop if already sorted).
    - Not stable by default.


---

## 3. Insertion Sort
- **Idea**: Build a sorted part one element at a time by inserting each new element into its correct position.
- **Best Use Cases**:
    - Small arrays (fast for n < 30).
    - Nearly sorted data (adaptive — runs in O(n) when input is already sorted).
    - Used as a building block in hybrid algorithms (e.g., Python’s Timsort, C++’s Introsort).
    - Sorting linked lists (shifting is easier than swapping).
- **Pros**:
    - Simple and intuitive (like sorting playing cards).
    - Adaptive: very efficient for nearly-sorted input.
    - Stable sort.
- **Cons**:
    - Still O(n²) for random or reverse-sorted arrays.
    - Many shifts/swaps in worst case.
