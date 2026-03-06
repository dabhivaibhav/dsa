package heap.easy_problem;

import heap.MinHeap;

/*
Problem: Find K-th Largest element in an array

Given an array nums, return the kth largest element in the array.

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
public class FindKthLargestElement {
    public static void main(String[] args) {

        int[] nums = {3, 2, 3, 1, 5};
        int k = 2;
        System.out.println(findKthLargestElement(nums, k));
    }

    /**
     * findKthLargestElement(int[] nums, int k)
     * <p>
     * What this method does:
     * <p>
     * Returns the k-th largest element in the array.
     * <p>
     * Instead of sorting the entire array,
     * it uses a Min Heap of size k
     * <p>
     * Core Idea:
     * <p>
     * Maintain a Min Heap that always stores
     * the k largest elements seen so far.
     * <p>
     * Why Min Heap?
     * <p>
     * Because the smallest element in the heap
     * (the root)
     * will represent the k-th largest overall.
     * <p>
     * Thought Process:
     * <p>
     * If we keep only k elements in a Min Heap:
     * <p>
     * - The heap root is the smallest among them.
     * - That smallest element is exactly
     * the k-th largest in the entire array.
     * <p>
     * Any element smaller than the heap root
     * is irrelevant and discarded.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Create an empty Min Heap.
     * <p>
     * Step 2: Iterate through each number in nums.
     * <p>
     * For each number:
     * <p>
     * - Insert it into the heap.
     * <p>
     * - If heap size becomes greater than k:
     * remove (delete) the smallest element.
     * <p>
     * This ensures:
     * The heap never stores more than k elements.
     * <p>
     * Step 3: After processing all elements,
     * the heap contains the k largest elements.
     * <p>
     * The root of the Min Heap
     * is the k-th largest element.
     * <p>
     * Return heap.peek().
     * <p>
     * Example 1:
     * <p>
     * nums = [1,2,3,4,5], k = 2
     * <p>
     * Heap evolution:
     * <p>
     * Insert 1 → [1]
     * Insert 2 → [1,2]
     * Insert 3 → remove 1 → [2,3]
     * Insert 4 → remove 2 → [3,4]
     * Insert 5 → remove 3 → [4,5]
     * <p>
     * Root = 4
     * <p>
     * 4 is the 2nd largest.
     * <p>
     * Example 2:
     * <p>
     * nums = [-5,4,1,2,-3], k = 5
     * <p>
     * Since k == n,
     * heap will contain all elements.
     * <p>
     * Root = -5
     * <p>
     * -5 is the 5th largest.
     * <p>
     * Complexity:
     * <p>
     * Let n = nums.length
     * <p>
     * Each insertion takes O(log k)
     * Each deletion takes O(log k)
     * <p>
     * Total Time Complexity: O(n log k)
     * <p>
     * Space Complexity: O(k)
     * <p>
     * Interview Takeaway:
     * <p>
     * Sorting would take O(n log n).
     * <p>
     * Using a Min Heap of size k
     * reduces it to O(n log k),
     * which is much faster when k << n.
     * <p>
     * This is a classic pattern:
     * <p>
     * "Use a Min Heap to track Top K elements."
     * <p>
     * The heap stores only what matters.
     *
     */
    public static int findKthLargestElement(int[] nums, int k) {
        MinHeap heap = new MinHeap();

        for (int num : nums) {
            heap.insert(num);

            if (heap.getSize() > k) {
                heap.delete();
            }
        }

        return heap.peek();
    }
}
