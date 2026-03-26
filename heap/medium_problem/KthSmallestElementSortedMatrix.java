package heap.medium_problem;

/*
Leetcode 378: Kth Smallest Element In Sorted Matrix

Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.
Note that it is the kth smallest element in the sorted order, not the kth distinct element.
You must find a solution with a memory complexity better than O(n2).

Example 1:
Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
Output: 13
Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13

Example 2:
Input: matrix = [[-5]], k = 1
Output: -5

Constraints:
            n == matrix.length == matrix[i].length
            1 <= n <= 300
            -10^9 <= matrix[i][j] <= 10^9
            All the rows and columns of matrix are guaranteed to be sorted in non-decreasing order.
            1 <= k <= n^2

Follow up:
Could you solve the problem with a constant memory (i.e., O(1) memory complexity)?
Could you solve the problem in O(n) time complexity? The solution may be too advanced for an interview but you may find reading this paper fun.
 */

import java.util.Arrays;
import java.util.PriorityQueue;

public class KthSmallestElementSortedMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
        int k = 8;
        System.out.println(kthSmallestBruteForce(matrix, k));
        System.out.println(kthSmallestBetter(matrix, k));
    }

    /**
     * kthSmallestBruteForce(int[][] matrix, int k)
     * <p>
     * What this method does:
     * <p>
     * Returns the k-th smallest element
     * in a sorted matrix by flattening
     * and sorting all elements.
     * <p>
     * Core Idea:
     * <p>
     * Ignore the sorted structure
     * of the matrix.
     * <p>
     * Treat the matrix as a simple
     * collection of numbers:
     * <p>
     * 1. Flatten it into a 1D array
     * 2. Sort the array
     * 3. Return the k-th element
     * <p>
     * Thought Process:
     * <p>
     * Since all elements are needed
     * anyway, we collect everything
     * into one array.
     * <p>
     * Once sorted, the k-th smallest
     * element is simply at index (k - 1).
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Create an array of size n * n
     * to store all elements.
     * <p>
     * Step 2:
     * <p>
     * Traverse the matrix
     * and copy all values
     * into the array.
     * <p>
     * Step 3:
     * <p>
     * Sort the array using
     * Arrays.sort().
     * <p>
     * Step 4:
     * <p>
     * Return the element at index (k - 1).
     * <p>
     * Example:
     * <p>
     * matrix = [[1,5,9],[10,11,13],[12,13,15]]
     * <p>
     * Flatten:
     * <p>
     * [1,5,9,10,11,13,12,13,15]
     * <p>
     * Sort:
     * <p>
     * [1,5,9,10,11,12,13,13,15]
     * <p>
     * k = 8 → element at index 7 → 13
     * <p>
     * Complexity:
     * <p>
     * Time Complexity:
     * <p>
     * O(n² log n²) ≈ O(n² log n)
     * <p>
     * Space Complexity:
     * <p>
     * O(n²)
     * <p>
     * Interview Takeaway:
     * <p>
     * This approach is simple
     * but ignores the sorted property
     * of rows and columns,
     * leading to unnecessary work.
     */
    private static int kthSmallestBruteForce(int[][] matrix, int k) {

        int n = matrix.length;
        int[] masterMatrix = new int[n * n];
        int count = 0;
        for (int[] ints : matrix) {
            for (int j = 0; j < n; j++) {
                masterMatrix[count] = ints[j];
                count++;
            }
        }
        Arrays.sort(masterMatrix);

        return masterMatrix[k - 1];
    }

    /**
     * kthSmallestBetter(int[][] matrix, int k)
     * <p>
     * What this method does:
     * <p>
     * Returns the k-th smallest element
     * in the matrix using a Max Heap
     * of size k.
     * <p>
     * Core Idea:
     * <p>
     * Instead of storing all elements,
     * we maintain only the k smallest
     * elements seen so far.
     * <p>
     * A Max Heap helps us track
     * the largest among these k elements.
     * <p>
     * If a new element is smaller,
     * it replaces the current largest.
     * <p>
     * Thought Process:
     * <p>
     * Traverse all elements
     * in the matrix.
     * <p>
     * For each element:
     * <p>
     * - Add it to the heap
     * - If heap size exceeds k,
     * remove the largest element
     * <p>
     * This ensures the heap always
     * contains the k smallest elements.
     * <p>
     * The root of the heap
     * will be the k-th smallest.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Create a Max Heap using:
     * <p>
     * (a, b) -> b - a
     * <p>
     * Step 2:
     * <p>
     * Traverse each element
     * in the matrix.
     * <p>
     * Step 3:
     * <p>
     * Add each element
     * to the heap.
     * <p>
     * Step 4:
     * <p>
     * If heap size becomes > k,
     * remove the largest element.
     * <p>
     * Step 5:
     * <p>
     * After traversal,
     * the root of the heap
     * is the k-th smallest element.
     * <p>
     * Example:
     * <p>
     * matrix = [[1,5,9],[10,11,13],[12,13,15]]
     * <p>
     * k = 8
     * <p>
     * Heap keeps only 8 smallest elements:
     * <p>
     * [1,5,9,10,11,12,13,13]
     * <p>
     * Root (largest among them) → 13
     * <p>
     * Complexity:
     * <p>
     * Let n = matrix dimension.
     * <p>
     * Time Complexity:
     * <p>
     * O(n² log k)
     * <p>
     * - Traverse all elements → O(n²)
     * - Heap operations → O(log k)
     * <p>
     * Space Complexity:
     * <p>
     * O(k)
     * <p>
     * Interview Takeaway:
     * <p>
     * This approach improves space usage
     * compared to brute force.
     * <p>
     * When asked for k-th smallest
     * or largest elements,
     * using a heap of size k
     * is a common and efficient pattern.
     */
    private static int kthSmallestBetter(int[][] matrix, int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int col = matrix[0].length;
        for (int[] ints : matrix) {
            for (int j = 0; j < col; j++) {
                pq.add(ints[j]);
                if (pq.size() > k) pq.poll();
            }
        }
        return pq.isEmpty() ? 0 : pq.peek();
    }
}
