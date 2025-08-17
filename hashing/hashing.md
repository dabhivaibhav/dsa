# Hashing Technique

## Introduction
Hashing is a technique used to optimize searching and frequency-related problems by using two main steps: **pre-storing** and **fetching**. It significantly reduces the time complexity of queries from `O(Q*N)` in brute force to nearly `O(1)` for each lookup.

Let’s first try to understand the importance of hashing using an example:

Given an array of integers: `[1, 2, 1, 3, 2]` and we are given some queries: `[1, 3, 4, 2, 10]`. For each query, we need to find out how many times the number appears in the array. For example, if the query is `1` our answer would be 2, and if the query is `4` the answer will be 0.

Similarly, the following will be the answers to the given queries.

---

## Brute Force Approach
As we have learned the `for loop`, the first approach that should come to our mind is to use it to solve this problem. For each query, we will iterate over the array using a loop. We will count the number of times the query number appears in that array i.e. increment the counter variable if the array element at that index equals the query number.

### Time Complexity Analysis
- A single query requires `O(N)`.
- For `Q` queries, total complexity = `O(Q*N)`.

If the length of the query becomes large like `10^5` and the array size also becomes large like `10^5`, the time complexity will be `O(10^10)`.

We know from our previous knowledge that `10^8` operations take ~1 second to execute. So, `10^10` operations will take around 100 seconds. Such a solution is not efficient.

---

## Optimized Approach using Hashing
### Definition
In order to optimize this approach, we need to use hashing. If we say the definition of hashing in a simple way, it is nothing but the combination of two steps: **pre-storing** and **fetching**.

### Step 1 - Pre-storing
- Create a hash array of size `max_element+1`, initialized to 0.
- For each element `arr[i]`, do `hash[arr[i]]++`.

This step is named pre-storing as we are pre-calculating the information about the elements of the array before answering the queries.

### Step 2 - Fetching
- For a query element `x`, simply return `hash[x]`.

### Example
Input:
```
5
1 3 2 1 3
5
1 4 2 3 12
```
Output:
```
2
0
1
2
0
```

### Point to Remember
If the maximum element is very large (e.g., `10^9`), we cannot create such a huge array. In such cases, we use **HashMap in Java** instead.

---

## Character Hashing
Given the string: `"abcdabefc"` we are given some queries: `[a, c, z]`. For each query, we need to find out how many times the character appears in the string.

### Brute Force
Iterate over the string for each query and count occurrences. Complexity = `O(Q*N)`.

### Optimized Hashing
We need to map the characters of the string to integers:
- If only lowercase: `'a' → 0, 'b' → 1, … 'z' → 25`. Formula: `char - 'a'.`
- If only uppercase: `'A' → 0, 'B' → 1, … 'Z' → 25`. Formula: `char - 'A'.`
- If both uppercase and lowercase: Use ASCII values (array size = 256).

### Example
Input:
```
abcdabehf
5
a g h b c
```
Output:
```
2
0
1
2
1
```

---

## Hashing Large Numbers (10^9 or higher)
Using array hashing is not feasible. We use **HashMap in Java**.

### Key Concepts
- **Key** → element.
- **Value** → frequency.
- Example: Array `[1, 2, 3, 1, 3, 2, 12]` becomes:
  ```
  1 → 2
  2 → 2
  3 → 2
  12 → 1
  ```

---

## HashMap in Java
- `HashMap` uses hashing and provides **O(1)** average time for insertion and retrieval.
- In the **worst case**, due to collision, operations may degrade to `O(N)`.

### Example Code
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Precompute frequencies
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }

        int q = sc.nextInt();
        while (q-- > 0) {
            int number = sc.nextInt();
            System.out.println(map.getOrDefault(number, 0));
        }
    }
}
```

---

## Collision in Hashing
A collision occurs when two or more keys hash to the same index.

### Division Method
We calculate `key % size` to determine the index. If multiple values map to the same index, collisions occur.

### Example
Array: `[2, 5, 16, 28, 139]` with modulo 10.
- Pre-storing: `hash[arr[i] % 10]++`

### Linear Chaining
When collisions occur, we use **Linked List chaining** at that index to store multiple elements.

Example: `[28, 38, 48, 18] % 10` → same remainder → all stored in one chain.

### Worst Case Collision
Array: `[8, 18, 28, 38, … 1008]` with `% 10`.
- All elements go to index 8.
- Lookup degrades to `O(N)`.

---

## Time Complexity Summary
| Operation | Array Hashing | HashMap (Java) |
|-----------|---------------|----------------|
| Insert    | O(1)          | O(1) average, O(N) worst |
| Search    | O(1)          | O(1) average, O(N) worst |

---

## Notes
- Hashing is highly efficient for searching and frequency-related problems.
- Array hashing is limited by maximum element size.
- HashMap handles large values without needing huge arrays.
- Collisions are rare but can degrade performance to `O(N)`.
- In Java: Always prefer **HashMap**.
