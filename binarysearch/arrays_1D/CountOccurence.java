package binarysearch.arrays_1D;

/*
Count Occurrences in a Sorted Array
You are given a sorted array of integers arr and an integer target. Your task is to determine how many times target appears in arr.
Return the count of occurrences of target in the array.

Examples:
Input: arr = [0, 0, 1, 1, 1, 2, 3], target = 1
Output: 3
Explanation: The number 1 appears 3 times in the array.

Input: arr = [5, 5, 5, 5, 5, 5], target = 5
Output: 6
Explanation: All elements in the array are 5, so the target appears 6 times.

Constraints:
            1 <= arr.length <= 10^6
            1 <= arr[i] <= 10^6
            1 <= target <= 10^6
 */
public class CountOccurence {

    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 8;
        int[] nums1 = {5, 7, 7, 8, 8, 10};
        int target1 = 6;

        System.out.println("Brute force approach:");
        countOccurenceBruteForce(nums, target);
        countOccurenceBruteForce(nums1, target1);
        System.out.println("Optimized approach:");
        countOccurenceOptimized(nums, target);
        countOccurenceOptimized(nums1, target1);


    }


    /**
     * countOccurenceBruteForce
     * <p>
     * What it does:
     * This method counts the number of occurrences of a target value in an array using
     * a linear search approach. It iterates through each element and increments a counter
     * when the target is found.
     * <p>
     * Why it works:
     * - Iterates through each element of the array exactly once
     * - Compares each element with the target value
     * - Maintains a count of matches found
     * <p>
     * Example:
     * nums = [5, 7, 7, 8, 8, 10], target = 8
     * - Iterates through array
     * - Finds two matches at indices 3 and 4
     * - Returns count = 2
     * <p>
     * Edge cases:
     * - If target is not found, returns count = 0
     * - Works for arrays of any size
     * <p>
     * Complexity:
     * Time:  O(n) — needs to check every element
     * Space: O(1) — only uses a single counter variable
     */
    private static void countOccurenceBruteForce(int[] nums, int target) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                count++;
            }
        }
        System.out.println("Count of occurence: " + count);
    }

    /**
     * countOccurenceOptimized
     * <p>
     * What it does:
     * This method finds the count of occurrences of a target value in a sorted array using
     * binary search. It finds the first and last occurrence of the target and calculates
     * the total count from these positions.
     * <p>
     * Why it works:
     * - Uses binary search to find first occurrence
     * - Uses binary search to find last occurrence
     * - Count is calculated as (last - first + 1)
     * <p>
     * Example:
     * nums = [5, 7, 7, 8, 8, 10], target = 8
     * - First occurrence at index 3
     * - Last occurrence at index 4
     * - Count = 4 - 3 + 1 = 2
     * <p>
     * Edge cases:
     * - If target not found, returns count = 0
     * - Works for single element arrays
     * <p>
     * Complexity:
     * Time:  O(log n) — uses binary search twice
     * Space: O(1) — only uses a few variables
     */
    private static void countOccurenceOptimized(int[] nums, int target) {

        int first = firstOccurence(nums, target);
        if (first == -1) {
            System.out.println("Count of occurence: 0");
            return;
        }
        int last = lastOccurence(nums, target);
        System.out.println("Count of occurence: " + (last - first + 1));


    }

    /**
     * firstOccurence
     * <p>
     * What it does:
     * This method finds the index of the first occurrence of a target value in a sorted array
     * using binary search. It continues searching in the left half even after finding a match.
     * <p>
     * Why it works:
     * - Uses binary search to narrow down the search space
     * - When target is found, stores the position but continues searching left
     * - Returns the leftmost occurrence of target
     * <p>
     * Example:
     * nums = [5, 7, 7, 8, 8, 10], target = 8
     * - Finds 8 at mid, stores position
     * - Continues searching left half
     * - Returns index 3 (first occurrence)
     * <p>
     * Edge cases:
     * - If target not found, returns -1
     * - Works with duplicate elements
     * <p>
     * Complexity:
     * Time:  O(log n) — binary search
     * Space: O(1) — constant space usage
     */
    private static int firstOccurence(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int first = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                first = mid;
                high = mid - 1;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return first;
    }

    /**
     * lastOccurence
     * <p>
     * What it does:
     * This method finds the index of the last occurrence of a target value in a sorted array
     * using binary search. It continues searching in the right half even after finding a match.
     * <p>
     * Why it works:
     * - Uses binary search to narrow down the search space
     * - When target is found, stores the position but continues searching right
     * - Returns the rightmost occurrence of target
     * <p>
     * Example:
     * nums = [5, 7, 7, 8, 8, 10], target = 8
     * - Finds 8 at mid, stores position
     * - Continues searching right half
     * - Returns index 4 (last occurrence)
     * <p>
     * Edge cases:
     * - If target not found, returns -1
     * - Works with duplicate elements
     * <p>
     * Complexity:
     * Time:  O(log n) — binary search
     * Space: O(1) — constant space usage
     */
    private static int lastOccurence(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int last = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                last = mid;
                low = mid + 1;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return last;
    }
}
