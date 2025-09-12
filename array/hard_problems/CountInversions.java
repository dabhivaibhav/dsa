package array.hard_problems;

import java.util.ArrayList;

/*
Count Inversions
Given an integer array nums. Return the number of inversions in the array.
Two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j.
It indicates how close an array is to being sorted.
A sorted array has an inversion count of 0.
An array sorted in descending order has maximum inversion.

Examples:
Input: nums = [2, 3, 7, 1, 3, 5]
Output: 5
Explanation:
The responsible indexes are:
nums[0], nums[3], values: 2 > 1 & indexes: 0 < 3
nums[1], nums[3], values: 3 > 1 & indexes: 1 < 3
nums[2], nums[3], values: 7 > 1 & indexes: 2 < 3
nums[2], nums[4], values: 7 > 3 & indexes: 2 < 4
nums[2], nums[5], values: 7 > 5 & indexes: 2 < 5

Input: nums = [-10, -5, 6, 11, 15, 17]
Output: 0
Explanation:
nums is sorted, hence no inversions present.
 */
public class CountInversions {


    public static void main(String[] args) {
        int[] nums = {2, 3, 7, 1, 3, 5};
        countingInversionBruteforceApproach(nums);
        System.out.println("Optimal Approach: " + countingInversionOptimalApproach(nums, nums.length));
    }

    /**
     * Counts inversions by checking every pair (brute force).
     *
     * Idea:
     * - An inversion is a pair of indexes (i, j) with i < j and nums[i] > nums[j].
     * - Check all pairs using two loops. If a left element is bigger than a right element,
     *   we found one inversion and we increment the counter.
     *
     * Algorithm steps:
     * 1) Set count = 0.
     * 2) For each i from 0 to n-1:
     *      For each j from i+1 to n-1:
     *         If nums[i] > nums[j], increase count.
     * 3) Print (or return) the total count.
     *
     * Example (nums = [2, 3, 7, 1, 3, 5]):
     * - Pairs that count: (2,1), (3,1), (7,1), (7,3), (7,5) → total = 5.
     *
     * Time Complexity: O(n^2)
     * - Two nested loops over the array.
     *
     * Space Complexity: O(1)
     * - Only a few variables.
     *
     * Note:
     * - For big arrays the inversion count can be as large as n*(n-1)/2.
     *   Consider using a long counter to avoid overflow when n is large.
     */
    private static void countingInversionBruteforceApproach(int[] nums) {

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    count++;
                }
            }
        }
        System.out.println("Brute force approach: " + count);
    }

    /**
     * Counts inversions using a modified Merge Sort (optimal O(n log n)).
     *
     * Intuition:
     * - When merging two sorted halves (Left and Right), if Right[j] is smaller than Left[i],
     *   then Right[j] forms an inversion with every remaining element in Left starting at i
     *   (because Left is sorted, so Left[i], Left[i+1], ... Left[mid] are all >= Left[i] > Right[j]).
     * - We can add all those inversions in one step: (mid - i + 1).
     *
     * What the code does:
     * - countingInversionOptimalApproach(a, n): kicks off the process and returns the total count.
     * - mergeSort(arr, low, high): recursively sorts arr[low..high] and returns
     *   the number of inversions inside this range.
     * - mergeArr(arr, low, mid, high): merges two sorted halves [low..mid] and [mid+1..high],
     *   and counts the “cross” inversions (left half vs right half).
     *
     * Algorithm steps:
     * 1) Split: Recursively sort left half and count inversions there.
     * 2) Split: Recursively sort right half and count inversions there.
     * 3) Merge: While merging the two sorted halves:
     *      - If left[i] <= right[j], place left[i] into the merged list (no new inversions).
     *      - Else (left[i] > right[j]), place right[j] and add (mid - i + 1) to the count,
     *        because right[j] is smaller than all remaining elements in the left half.
     * 4) Total inversions = leftCount + rightCount + crossCount.
     *
     * Why (mid - i + 1)?
     * - i currently points to the first left element that is greater than right[j].
     *   Since the left half is sorted, *all* elements from i to mid are ≥ left[i] > right[j].
     *   Each of those pairs is an inversion, so we add the number of remaining left elements.
     *
     * Tiny walkthrough (nums = [2, 3, 7, 1, 3, 5]):
     * - During merges, the only time we add to the count is when a right element
     *   is placed before a left element. Summing these adds gives 5.
     *
     * Time Complexity: O(n log n)
     * - Same recurrence as merge sort: T(n) = 2T(n/2) + O(n).
     *
     * Space Complexity: O(n)
     * - Temporary list/array used during merging, plus O(log n) recursion stack.
     *
     * Implementation notes:
     * - The inversion count can exceed Integer.MAX_VALUE for large n (max ≈ n*(n-1)/2).
     *   Prefer returning a long and using long for the internal counter if n can be large.
     * - This algorithm sorts the array as a side effect. If you must keep the original order,
     *   operate on a cloned copy.
     *
     * @param a the input array
     * @param n the length of the array
     * @return the number of inversions in the array
     */
    public static int countingInversionOptimalApproach(int[] a, int n) {
        // Count the number of pairs:
        return mergeSort(a, 0, n - 1);
    }

    public static int mergeSort(int[] arr, int low, int high) {
        int count = 0;
        if (low < high) {

            //Find the mid point
            // This is the point where the array will be divided into two halves
            // low is the starting index, high is the ending index
            // mid is the middle index
            int mid = (low + high) / 2;

            // Sort the first half
            count += mergeSort(arr, low, mid);
            // Sort the second half
            count += mergeSort(arr, mid + 1, high);
            // Merge the sorted halves
            count += mergeArr(arr, low, mid, high);
        }
        return count;
    }

    private static int mergeArr(int[] arr, int low, int mid, int high) {

        //tempArray to store the merger array
        ArrayList<Integer> list = new ArrayList<>();
        int left = low; // Starting index of the left subarray
        int right = mid + 1; // Starting index of the right subarray
        int count = 0;
        //here it will compare the left and right subarray and add the smaller element to the list
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                list.add(arr[left]);
                left++;
            } else {
                list.add(arr[right]);
                count += (mid - left + 1);
                right++;
            }
        }

        //here it will add the remaining elements of the left subarray to the list
        while (left <= mid) {
            list.add(arr[left]);
            left++;
        }

        //here it will add the remaining elements of the right subarray to the list
        while (right <= high) {
            list.add(arr[right]);
            right++;
        }

        //here it will copy the elements of the list to the original array
        for (int i = low; i <= high; i++) {
            arr[i] = list.get(i - low);
        }
        return count;
    }
}
