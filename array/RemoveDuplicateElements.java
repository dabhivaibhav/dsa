package array;

import java.util.HashSet;

/*
Given an integer array nums sorted in non-decreasing order, remove all duplicates in-place so that each unique element appears only once.
Return the number of unique elements in the array. If the number of unique elements be k, then,
Change the array nums such that the first k elements of nums contain the unique values in the order that they were present originally.
The remaining elements, as well as the size of the array does not matter in terms of correctness.
An array sorted in non-decreasing order is an array where every element to the right of an element is either equal to or greater in value than that element.

For example, [1,2,2,3,4] is a non-decreasing array while [1,3,2] is not.

Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
            nums is sorted in non-decreasing order.
 */
public class RemoveDuplicateElements {

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int[] nums1 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println("Brute Force Approach:");
        bruteForceApproach(nums);
        System.out.println("\nOptimal Approach:");
        optimalApproach(nums1);
    }

    /*
     Brute Force (using a HashSet)
         Idea in plain words:
         Walk through the array from left to right and keep a set of values we’ve already seen.
         If the current number isn’t in the set, we add it to the set and also write it into
         the next open slot at the front of the array. Because we scan left→right, the kept
         values stay in their original order. At the end, the first `index` positions hold the
         unique values, and `index` is the count of uniques.

         Why it works:
         A set ignores duplicates. Since the input is already sorted, all duplicates come in
         clumps, so each new value appears once and then repeats—our set filters those repeats.

         Complexity:
         Average time O(n) because each HashSet lookup/insert is O(1) on average; space O(n) for the set.
         (Under heavy hash collisions in Java 8+, a bucket becomes a tree, making per-op O(log n),
         so worst-case across n inserts is O(n log n).)
     */

    private static void bruteForceApproach(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        int index = 0;
        for (int num : nums) {
            if (!set.contains(num)) {
                set.add(num);
                nums[index++] = num;
            }
        }
        System.out.println("Number of unique elements: " + index);
        System.out.print("Array after removing duplicates: ");
        for (int i = 0; i < index; i++) {
            System.out.print(nums[i] + " ");
        }
    }

    /*
    Optimal (two-pointer on sorted array)
     Idea in plain words:
     Keep a pointer `index` to the last unique element we’ve placed at the front.
     Scan the array from i = 1 to end. Whenever nums[i] is different from nums[index],
     we’ve found a new unique value—move `index` forward by one and copy nums[i] there.
     When the loop finishes, the first (index + 1) slots hold all unique values in order.

     Why it works:
     The array is sorted, so all duplicates of a value sit next to each other. Comparing
     only with the last unique is enough to detect “a new value”. We rewrite in-place,
     so we use O(1) extra space.

     Complexity:
     Time O(n), Space O(1).
     */


    private static void optimalApproach(int[] nums) {
        if (nums.length == 0) {
            System.out.println("Array is empty");
            return;
        }
        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[index]) {
                nums[index + 1] = nums[i];
                index++;
            }
        }
        System.out.println("Number of unique elements: " + (index + 1));
        System.out.print("Array after removing duplicates: ");
        for (int i = 0; i <= index; i++) {
            System.out.print(nums[i] + " ");

        }
    }
}

