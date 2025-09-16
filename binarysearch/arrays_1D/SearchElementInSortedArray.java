package binarysearch.arrays_1D;

/*
Search X in sorted array || Leetcode 704 Binary Search

Given a sorted array of integers nums with 0-based indexing, find the index of a specified target integer. If the target
is found in the array, return its index. If the target is not found, return -1.

Examples:
Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: The target integer 9 exists in nums and its index is 4

Input: nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: The target integer 2 does not exist in nums so return -1

Constraints:
          1 <= nums.length <= 10^5
          -10^5 < nums[i], target < 10^5
          nums is sorted in ascending order.
 */
public class SearchElementInSortedArray {
    public static void main(String[] args) {

        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 2;
        System.out.print("Bruteforce: ");
        searchElementInSortedArrayBruteforce(nums, target);
        System.out.print("Bruteforce: ");
        searchElementInSortedArrayBruteforce(nums1, target1);
        System.out.print("Optimized Iterative: ");
        searchElementInSortedArrayOptimized(nums, target);
        System.out.print("Optimized Iterative: ");
        searchElementInSortedArrayOptimized(nums1, target1);
        System.out.print("Optimized Recursion: ");
        searchElementInSortedArrayOptimized1(nums, target);
        System.out.print("Optimized Recursion: ");
        searchElementInSortedArrayOptimized1(nums1, target1);
    }

    /**
     * searchElementInSortedArrayBruteforce
     *  <p>
     * What it does:
     * I check each element of the array one by one from left to right.
     * If any element equals the target, I print its index and stop searching immediately.
     * If I reach the end without finding it, I print "Element not found".
     * <p>
     * Why it works:
     * This approach doesn’t rely on the array being sorted — it simply looks everywhere.
     * Since every index is visited, if the target exists, it will be found.
     * <p>
     * Important details:
     * - Stops as soon as the element is found (early exit saves time in best case).
     * - Works on both sorted and unsorted arrays.
     * - Useful when the array is small or not guaranteed to be sorted.
     *  <p>
     * Example (nums = [4, 2, 7], target = 7):
     * i=0 → 4 != 7
     * i=1 → 2 != 7
     * i=2 → 7 == 7 → print "found at index 2"
     * <p>
     * Complexity:
     * Time:  O(n)   (may scan all elements)
     * Space: O(1)   (just loop variables)
     *
     * @param nums
     * @param target
     */
    public static void searchElementInSortedArrayBruteforce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                System.out.println("Element found at index: " + i);
                return;
            }
        }
        System.out.println("Element not found");

    }

    /**
     * searchElementInSortedArrayOptimized (Iterative Binary Search)
     * <p>
     * What it does:
     * Uses binary search on a sorted array to find the target efficiently.
     * It repeatedly checks the middle element and eliminates half of the search space each time.
     * <p>
     * Why it works:
     * Since the array is sorted, comparing the target with the middle element tells me which half to ignore.
     * Halving the search space each step reduces the number of checks drastically compared to linear search.
     * <p>
     * Important details:
     * - The loop continues as long as low <= high.
     * - If nums[mid] == target, print mid and exit.
     * - If target > nums[mid], search only the right half; else search the left half.
     * - This approach only works when the array is sorted.
     * <p>
     * Example (nums = [1, 3, 5, 7, 9], target = 7):
     * low=0 high=4 mid=2 → nums[2]=5 < 7 → search right half
     * low=3 high=4 mid=3 → nums[3]=7 == 7 → print "found at index 3"
     * <p>
     * Complexity:
     * Time:  O(log n)  (halves the range each step)
     * Space: O(1)      (only a few variables)
     */
    public static void searchElementInSortedArrayOptimized(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                System.out.println("Element found at index: " + mid);
                return;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Element not found");
    }

    /**
     * searchElementInSortedArrayOptimized1 (Recursive Binary Search)
     * <p>
     * What it does:
     * This is the recursive version of binary search. It calls a helper method
     * that checks the middle element and recurses into either the left or right half.
     * <p>
     * Why it works:
     * The same binary search logic applies — sorted order lets me discard half the elements each call.
     * Recursion breaks the problem into smaller halves until the target is found or the range is empty.
     * <p>
     * Important details:
     * - Base condition: if low > high, the element is not present.
     * - Returns -1 if not found, or the index if found.
     * - Prints the result after the recursive call finishes.
     * - Requires the array to be sorted.
     * <p>
     * Example (nums = [2, 4, 6, 8], target = 4):
     * low=0 high=3 mid=1 → nums[1]=4 == 4 → return 1 → print "found at index 1"
     * <p>
     * Complexity:
     * Time:  O(log n)    (halves the range each call)
     * Space: O(log n)    (recursive call stack depth)
     */
    private static void searchElementInSortedArrayOptimized1(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int val = recursionApproach(nums, low, high, target);
        if (val == -1) {
            System.out.println("Element not found");
        } else {
            System.out.println("Element found at index: " + val);
        }
    }

    private static int recursionApproach(int[] nums, int low, int high, int target) {
        if (low > high) return -1;
        int mid = (low + high) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return recursionApproach(nums, mid + 1, high, target);
        }
        return recursionApproach(nums, low, mid - 1, target);
    }
}
