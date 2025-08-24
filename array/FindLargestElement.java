package array;

import static SortingAlgorithms.MergeSortAlgo.mergeSort;

/*
Write a Java program to find the largest element in an array.
Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
            nums may contain duplicate elements.

Here I would first think of few questions if the constraints are not given:
1. Are elements integers or floating point numbers?
2. Are elements in the array positive, negative or both?
3. Can the array be empty? then what should I return?
4. Can the array contain duplicate elements?
*/
public class FindLargestElement {
    public static void main(String[] args) {
        int[] nums = {100}; // try {3,3,6,1} or { -5 } to see messages vs results

        printResult("Brute Force", bruteForceApproach(nums));
        printResult("Better",      betterApproach(nums));
        printResult("Optimal",     optimalApproach(nums));
    }

    private static void printResult(String label, Integer val) {
        if (val != null) {
            System.out.println(label + " approach largest element: " + val);
        }
        // If null, that approach already printed why it skipped (empty array).
    }

    // Brute force: O(n^2) time, O(1) space (demonstration only)
    private static Integer bruteForceApproach(int[] nums) {
        Integer bc = baseCaseCheck(nums, "Brute");
        if (bc != Integer.MIN_VALUE) return bc; // empty -> null, single -> value

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            boolean isLargest = true;
            for (int j = 0; j < n; j++) {
                if (nums[i] < nums[j]) { isLargest = false; break; }
            }
            if (isLargest) return nums[i];
        }
        // Should never happen for non-empty arrays
        System.out.println("Brute: unexpected state; no maximum found.");
        return null;
    }

    // Better: sort then take last element. O(n log n) time.
    // NOTE: This mutates nums because we call mergeSort on the same array.
    // If you want to avoid mutation, sort a copy instead.
    private static Integer betterApproach(int[] nums) {
        Integer bc = baseCaseCheck(nums, "Better");
        if (bc != Integer.MIN_VALUE) return bc;

        int n = nums.length;
        mergeSort(nums, 0, n - 1);
        return nums[n - 1];
    }

    // Optimal: single pass. O(n) time, O(1) space.
    private static Integer optimalApproach(int[] nums) {
        Integer bc = baseCaseCheck(nums, "Optimal");
        if (bc != Integer.MIN_VALUE) return bc;

        int largest = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > largest) largest = nums[i];
        }
        return largest;
    }

    /**
     * Base-case handler:
     * - Empty array: print message, return null (caller should return immediately).
     * - Single element: print message with the answer and return that element.
     * - Otherwise: return Integer.MIN_VALUE to signal "continue normal logic".
     *
     * Using Integer.MIN_VALUE as the "continue" signal is safe because nums[i] ∈ [-10^4, 10^4].
     */
    private static Integer baseCaseCheck(int[] nums, String who) {
        if (nums == null || nums.length == 0) {
            System.out.println(who + ": array is empty; skipping this approach.");
            return null;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        return Integer.MIN_VALUE; // signal to continue
    }
}
