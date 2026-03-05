package heap;

import java.util.Arrays;

/*
Problem: K Smallest element in an array

Given an array nums, return the kth largest element in the array.

Example 1
Input: nums = [1, 2, 3, 4, 5], k = 2
Output: 2

Example 2
Input: nums = [-5, 4, 1, 2, -3], k = 5
Output: -5

Constraints:
            1 <= nums.length <= 10^5
            -1000 <= nums[i] <= 1000
            1 <= k <= nums.length

 */
public class FindKSmallestElement {

    public static void main(String[] args) {
        int[] nums = {3, 10, 1, 2, 5};
        int k = 3;
        System.out.println(Arrays.toString(findKSmallestElement(nums, k)));
    }


    public static int[] findKSmallestElement(int[] nums, int k) {
        MaxHeap heap = new MaxHeap();

        for (int num : nums) {
            heap.insert(num);

            // If the heap gets too big, kick out the smallest person
            if (heap.getSize() > k) {
                heap.pop();
            }
        }

        // The root of the Max-Heap is the K largest
        int[] heapArray = new int[k];
        for (int i = 0; i < k; i++) {
            heapArray[i] = heap.pop();
        }

        return heapArray;
    }
}
