package heap;

/*
Problem: K-th Largest element in an array

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
        int[] nums = {1, 2, 3, 4, 5};
        int k = 2;
        System.out.println(findKthLargestElement(nums, k));
    }

    private static int findKthLargestElement(int[] nums, int k) {
        MinHeap heap = new MinHeap(); // Your class!

        for (int num : nums) {
            heap.insert(num);

            // If the heap gets too big, kick out the smallest person
            if (heap.getSize() > k) {
                heap.delete();
            }
        }

        // The root of the Min-Heap is the K-th largest
        return heap.peek();
    }
}
