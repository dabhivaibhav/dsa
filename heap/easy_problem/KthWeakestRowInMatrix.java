package heap.easy_problem;

import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;

/*
Leetcode 1337: The K Weakest Rows in a Matrix

You are given an m x n binary matrix mat of 1's (representing soldiers) and 0's (representing civilians).
The soldiers are positioned in front of the civilians. That is, all the 1's will appear to the left of all the 0's in each row.
A row i is weaker than a row j if one of the following is true:
The number of soldiers in row i is less than the number of soldiers in row j.
Both rows have the same number of soldiers and i < j.
Return the indices of the k weakest rows in the matrix ordered from weakest to strongest.

Example 1:
Input: mat =
[[1,1,0,0,0],
 [1,1,1,1,0],
 [1,0,0,0,0],
 [1,1,0,0,0],
 [1,1,1,1,1]],
k = 3
Output: [2,0,3]
Explanation:
The number of soldiers in each row is:
- Row 0: 2
- Row 1: 4
- Row 2: 1
- Row 3: 2
- Row 4: 5
The rows ordered from weakest to strongest are [2,0,3,1,4].

Example 2:
Input: mat =
[[1,0,0,0],
 [1,1,1,1],
 [1,0,0,0],
 [1,0,0,0]],
k = 2
Output: [0,2]
Explanation:
The number of soldiers in each row is:
- Row 0: 1
- Row 1: 4
- Row 2: 1
- Row 3: 1
The rows ordered from weakest to strongest are [0,2,3,1].

Constraints:
            m == mat.length
            n == mat[i].length
            2 <= n, m <= 100
            1 <= k <= m
            matrix[i][j] is either 0 or 1.
 */
public class KthWeakestRowInMatrix {

    public static void main(String[] args) {
        int[][] mat = {{1, 1, 0, 0, 0}, {1, 1, 1, 1, 0}, {1, 0, 0, 0, 0}, {1, 1, 0, 0, 0}, {1, 1, 1, 1, 1}};
        int k = 3;
        System.out.println(Arrays.toString(kWeakestRows(mat, k)));

    }


    /**
     * kWeakestRows(int[][] mat, int k)
     * <p>
     * What this method does:
     * <p>
     * Returns the indices of the k weakest rows
     * in the matrix ordered from weakest
     * to strongest.
     * <p>
     * A row is considered weaker if:
     * <p>
     * 1. It has fewer soldiers (1s).
     * 2. If two rows have the same number of soldiers,
     * the row with the smaller index is weaker.
     * <p>
     * Core Idea:
     * <p>
     * Instead of sorting all rows by strength,
     * we maintain a Max-Heap of size k
     * that stores the current k weakest rows.
     * <p>
     * Each heap entry stores:
     * <p>
     * [soldierCount, rowIndex]
     * <p>
     * The heap keeps the strongest row among
     * the current k weakest rows at the top.
     * <p>
     * If a new row is weaker than the strongest
     * row in the heap, it replaces it.
     * <p>
     * Thought Process:
     * <p>
     * Step 1:
     * <p>
     * For every row in the matrix,
     * count how many soldiers (1s)
     * appear in that row.
     * <p>
     * Because the row is sorted
     * (all 1s come before 0s),
     * we can use Binary Search
     * to find the first 0.
     * <p>
     * That position equals
     * the number of soldiers.
     * <p>
     * Step 2:
     * <p>
     * Insert the row into a Max Heap
     * containing:
     * <p>
     * [soldierCount, rowIndex]
     * <p>
     * Step 3:
     * <p>
     * If the heap size becomes
     * larger than k,
     * remove the strongest row.
     * <p>
     * This guarantees the heap
     * always keeps only
     * the k weakest rows.
     * <p>
     * Step 4:
     * <p>
     * After processing all rows,
     * extract elements from the heap
     * to build the result array.
     * <p>
     * Since it is a Max Heap,
     * the strongest of the survivors
     * comes out first,
     * so we fill the result array
     * from right to left.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Create a Max Heap
     * that compares rows by:
     * <p>
     * 1. Soldier count
     * 2. Row index (tie breaker)
     * <p>
     * Rows with more soldiers
     * are considered stronger.
     * <p>
     * Step 2:
     * <p>
     * Iterate through every row
     * in the matrix.
     * <p>
     * Count the soldiers
     * using the countSoldiers() method.
     * <p>
     * Step 3:
     * <p>
     * Insert the pair:
     * <p>
     * [soldierCount, rowIndex]
     * <p>
     * into the heap.
     * <p>
     * Step 4:
     * <p>
     * If heap size exceeds k,
     * remove the strongest row.
     * <p>
     * Step 5:
     * <p>
     * Extract elements from the heap
     * to build the final result.
     * <p>
     * Example 1:
     * <p>
     * Matrix:
     * <p>
     * [1,1,0,0,0] → 2 soldiers
     * [1,1,1,1,0] → 4 soldiers
     * [1,0,0,0,0] → 1 soldier
     * [1,1,0,0,0] → 2 soldiers
     * [1,1,1,1,1] → 5 soldiers
     * <p>
     * k = 3
     * <p>
     * Weakest rows in order:
     * <p>
     * Row 2 → 1 soldier
     * Row 0 → 2 soldiers
     * Row 3 → 2 soldiers
     * <p>
     * Output → [2,0,3]
     * <p>
     * Example 2:
     * <p>
     * Matrix:
     * <p>
     * [1,0,0,0] → 1 soldier
     * [1,1,1,1] → 4 soldiers
     * [1,0,0,0] → 1 soldier
     * [1,0,0,0] → 1 soldier
     * <p>
     * k = 2
     * <p>
     * Weakest rows:
     * <p>
     * Row 0
     * Row 2
     * <p>
     * Output → [0,2]
     * <p>
     * Complexity:
     * <p>
     * Let:
     * <p>
     * m = number of rows
     * n = number of columns
     * <p>
     * Time Complexity:
     * <p>
     * O(m log n + m log k)
     * <p>
     * - O(log n) to count soldiers using binary search
     * - O(log k) for heap operations
     * <p>
     * Space Complexity:
     * <p>
     * O(k)
     * <p>
     * The heap stores at most k rows.
     * <p>
     * Interview Takeaway:
     * <p>
     * When solving "Top K" problems,
     * a common technique is to maintain
     * a heap limited to size k.
     * <p>
     * This avoids sorting all elements
     * and reduces complexity from
     * O(n log n) to O(n log k).
     * <p>
     * Combined with binary search
     * on each row, this solution
     * becomes both efficient
     * and scalable.
     */
    public static int[] kWeakestRows(int[][] mat, int k) {
        // PriorityQueue stores [soldierCount, rowIndex]
        // Max-Heap: Compare soldierCount first, then rowIndex as tie-breaker
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return b[0] - a[0]; // Higher count at top
            return b[1] - a[1]; // Higher index at top
        });

        for (int i = 0; i < mat.length; i++) {
            int soldiers = countSoldiers(mat[i]);
            maxHeap.offer(new int[]{soldiers, i});

            // If heap exceeds K, remove the 'strongest' of the weak
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        int[] result = new int[k];
        // Since it's a Max-Heap, the strongest of the K survivors comes out first
        for (int i = k - 1; i >= 0; i--) {
            result[i] = Objects.requireNonNull(maxHeap.poll())[1];
        }
        return result;
    }

    // Optimization: Use Binary Search because the row is sorted (1s then 0s)
    private static int countSoldiers(int[] row) {
        int low = 0, high = row.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (row[mid] == 1) low = mid + 1;
            else high = mid;
        }
        return low;
    }


}
