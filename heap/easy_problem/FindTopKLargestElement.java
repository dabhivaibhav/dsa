package heap.easy_problem;

import heap.MinHeap;

import java.util.Arrays;

/*
Problem: Top K Largest element in an array

Given an array nums, return the k largest element in the array.

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
public class FindTopKLargestElement {

    public static void main(String[] args) {
        int[] nums = {3, 4, 2, 1, 5};
        int k = 2;
        System.out.println(Arrays.toString(findTopKLargestElement(nums, k)));
    }

    private static int[] findTopKLargestElement(int[] nums, int k) {
        MinHeap heap = new MinHeap(); // Your class!

        for (int num : nums) {
            heap.insert(num);

            // If the heap gets too big, kick out the smallest person
            if (heap.getSize() > k) {
                heap.delete();
            }
        }

        // The root of the Min-Heap is the K largest
        int[] heapArray = new int[k];
        for (int i = 0; i < k; i++) {
            heapArray[i] = heap.delete();
        }
        return heapArray;
    }
}
