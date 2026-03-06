package heap.easy_problem;

import heap.MaxHeap;

/*
Problem: Find K-th Smallest element in an array

Given an array nums, return the kth Smallest element in the array.

Example 1
Input: nums = [1, 2, 3, 4, 5], k = 2
Output: 4

Example 2
Input: nums = [-5, 4, 1, 2, -3], k = 5
Output: -5

Constraints:
            1 <= nums.length <= 10^5
            -1000 <= nums[i] <= 1000
            1 <= k <= nums.length
 */
public class FindKthSmallestElement {
    public static void main(String[] args) {

        int[] nums = {3, 10, 1, 2, 5};
        int k = 2;
        System.out.println(findKthSmallestElement(nums, k));

    }

    /**
     * findKthSmallestElement(int[] nums, int k)
     * <p>
     * What this method does:
     * <p>
     * Returns the k-th smallest element in the array.
     * <p>
     * It uses a Max Heap of size k
     * to efficiently track the k smallest elements.
     * <p>
     * Core Idea:
     * <p>
     * Maintain a Max Heap that stores
     * the k smallest elements seen so far.
     * <p>
     * Why Max Heap?
     * <p>
     * Because the largest element in this heap
     * represents the current k-th smallest.
     * <p>
     * If a new element is smaller than the largest
     * inside the heap,
     * it deserves to stay.
     * <p>
     * Otherwise, it gets discarded.
     * <p>
     * <p>
     * Thought Process:
     * <p>
     * If we keep only k elements in a Max Heap:
     * <p>
     * - The root (largest in heap)
     * is the k-th smallest overall.
     * <p>
     * - Any number larger than that root
     * is irrelevant and removed.
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Create an empty Max Heap.
     * <p>
     * Step 2: Iterate through nums.
     * <p>
     * For each number:
     * <p>
     * - Insert it into the heap.
     * <p>
     * - If heap size exceeds k:
     * remove the maximum element (heap.pop()).
     * <p>
     * This ensures:
     * The heap always contains exactly
     * the k smallest elements seen so far.
     * <p>
     * Step 3: After processing all elements,
     * the heap root holds the k-th smallest element.
     * <p>
     * Return heap.peek().
     * <p>
     * Example:
     * <p>
     * nums = [3, 10, 1, 2, 5]
     * k = 1
     * <p>
     * Insert 3 → [3]
     * Insert 10 → size > 1 → remove 10 → [3]
     * Insert 1 → size > 1 → remove 3 → [1]
     * Insert 2 → size > 1 → remove 2 → [1]
     * Insert 5 → size > 1 → remove 5 → [1]
     * <p>
     * Root = 1
     * <p>
     * 1 is the 1st smallest element.
     * <p>
     * Complexity:
     * <p>
     * Let n = nums.length
     * <p>
     * Each insertion takes O(log k)
     * Each removal takes O(log k)
     * <p>
     * Total Time Complexity: O(n log k)
     * <p>
     * Space Complexity: O(k)
     * <p>
     * Interview Takeaway:
     * <p>
     * Sorting would take O(n log n).
     * <p>
     * Using a Max Heap of size k
     * reduces the complexity to O(n log k),
     * which is efficient when k is small.
     * <p>
     * Pattern Summary:
     * <p>
     * - To find k largest → use Min Heap of size k.
     * - To find k smallest → use Max Heap of size k.
     * <p>
     * Always store only what matters.
     *
     */
    public static int findKthSmallestElement(int[] nums, int k) {
        MaxHeap heap = new MaxHeap();

        for (int num : nums) {
            heap.insert(num);

            if (heap.getSize() > k) {
                heap.pop();
            }
        }
        return heap.peek();
    }
}
