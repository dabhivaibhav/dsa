package array.hard_problems;

/*
Reverse Pairs
Leetcode: 493. Reverse Pairs

Given an integer array nums, return the number of reverse pairs in the array.
A reverse pair is a pair (i, j) where:
                                        0 <= i < j < nums.length and
                                        nums[i] > 2 * nums[j].

Example 1:
Input: nums = [1,3,2,3,1]
Output: 2
Explanation: The reverse pairs are:
(1, 4) --> nums[1] = 3, nums[4] = 1, 3 > 2 * 1
(3, 4) --> nums[3] = 3, nums[4] = 1, 3 > 2 * 1

Example 2:
Input: nums = [2,4,3,5,1]
Output: 3
Explanation: The reverse pairs are:
(1, 4) --> nums[1] = 4, nums[4] = 1, 4 > 2 * 1
(2, 4) --> nums[2] = 3, nums[4] = 1, 3 > 2 * 1
(3, 4) --> nums[3] = 5, nums[4] = 1, 5 > 2 * 1

Constraints:
            1 <= nums.length <= 5 * 10^4
            -2^31 <= nums[i] <= 2^31 - 1
 */

import java.util.ArrayList;

public class ReversePairs {

    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 3, 1};
        int[] nums1 = {2, 4, 3, 5, 1};
        int[] nums2 = {-5, -5, -5};
        int[] nums3 = {2147483647, 1073741824, -1};

        reversePairsBruteForceApproach(nums);
        reversePairsBruteForceApproach(nums1);
        reversePairsBruteForceApproach(nums2);
        reversePairsBruteForceApproach(nums3);
        System.out.println(reversePairsOptimalApproach(nums));
        System.out.println(reversePairsOptimalApproach(nums1));
        System.out.println(reversePairsOptimalApproach(nums2));
        System.out.println(reversePairsOptimalApproach(nums3));
    }


    /**
     * reversePairsBruteForceApproach
     * <p>
     * What it does:
     * - Checks every pair (i, j) with i < j and counts it if nums[i] > 2 * nums[j].
     * <p>
     * Why it works:
     * - The definition of a "reverse pair" only depends on the pair (i, j). A double loop
     * that tries all pairs is the most direct and easy-to-trust way to count them.
     * <p>
     * Important detail:
     * - Use 64-bit math on the right side: 2L * nums[j]. If we do 2 * nums[j] in int,
     * it can overflow for values near +/- 2^30 and silently give wrong answers.
     * <p>
     * Complexity:
     * - Time:  O(n^2)  (two nested loops)
     * - Space: O(1)    (no extra structures)
     * <p>
     * When to use:
     * - For understanding and for small inputs. It will time out on the largest constraints.
     */
    private static void reversePairsBruteForceApproach(int[] nums) {

        if (nums == null || nums.length == 0) {
            System.out.println("Arrays is empty");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element");
            return;
        }

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > 2L * nums[j] && i < j) {
                    count++;
                }
            }
        }

        System.out.println("Bruteforce approach using two for loops total number of reverse pairs: " + count);
    }


    /**
     * reversePairsOptimalApproach
     * <p>
     * What it does:
     * - Counts reverse pairs in O(n log n) using a modified merge sort.
     * <p>
     * Why it works (idea overview):
     * - Merge sort naturally splits the array into halves, counts reverse pairs:
     * (1) inside the left half,
     * (2) inside the right half,
     * (3) across the boundary (left element with right element).
     * - After sorting each half, we can count "cross" pairs efficiently with two pointers
     * because both halves are sorted by value. For each left value, we move a right
     * pointer forward until arr[i] <= 2 * arr[right]; everything before that right
     * pointer contributed valid pairs.
     * <p>
     * Complexity:
     * - Time:  O(n log n)
     * - Space: O(n) auxiliary (temporary storage during merging)
     */
    private static int reversePairsOptimalApproach(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    /**
     * merge
     * <p>
     * What it does:
     * - Standard merge step of merge sort: merges two sorted halves [low..mid] and [mid+1..high]
     * back into arr[low..high] in sorted order.
     * <p>
     * Why it exists here:
     * - We count pairs BEFORE we merge (when the halves are sorted). After counting, we must
     * merge so that higher-level calls also see sorted halves. Sorting-by-merge is what
     * enables the two-pointer counting logic in countPairs().
     * <p>
     * Complexity:
     * - Time:  O(high - low + 1)
     * - Space: O(high - low + 1) for the temporary list "temp"
     */
    private static void merge(int[] arr, int low, int mid, int high) {
        ArrayList<Integer> temp = new ArrayList<>(); // temporary array
        int left = low;      // starting index of left half of arr
        int right = mid + 1;   // starting index of right half of arr

        //storing elements in the temporary array in a sorted manner//

        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp.add(arr[left]);
                left++;
            } else {
                temp.add(arr[right]);
                right++;
            }
        }

        // if elements on the left half are still left //

        while (left <= mid) {
            temp.add(arr[left]);
            left++;
        }

        //  if elements on the right half are still left //
        while (right <= high) {
            temp.add(arr[right]);
            right++;
        }

        // transfering all elements from temporary to arr //
        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low);
        }
    }

    /**
     * countPairs
     * <p>
     * What it does:
     * - Counts "cross" reverse pairs where i is in the left half [low..mid] and
     * j is in the right half [mid+1..high], given BOTH halves are already sorted.
     * <p>
     * Core trick (two pointers on sorted halves):
     * - We keep a right pointer that never moves backward.
     * - For each i in the left half:
     * move 'right' forward while arr[i] > 2L * arr[right].
     * Once that loop stops, the number of valid right-side partners for this i
     * is (right - (mid + 1)).
     * - Because both halves are sorted, 'right' only moves forward overall, making
     * the whole counting O(high - low + 1).
     * <p>
     * Why this is correct:
     * - Sorting halves lets us count, for a fixed arr[i], all right-side values that
     * are small enough to satisfy arr[i] > 2 * arr[right] in one sweep.
     * - We sum this over all i in the left half to get all cross pairs.
     * <p>
     * Example (top-level of [2, 4, 3, 5, 1]):
     * - Left half sorted:  [2, 3, 4]
     * - Right half sorted: [1, 5]
     * - i=2:  2 > 2*1 ? (2 > 2) = false -> contributes 0
     * - i=3:  3 > 2*1 ? (3 > 2) = true  -> right moves past '1'
     * now right points to 5; 3 > 10 ? false; contributes (right - (mid+1)) = 1
     * - i=4:  4 > 10 ? false; contributes 1 (the same right window beyond '1')
     * - Cross pairs counted here = 2. The remaining 1 pair is counted in the right-half
     * recursion on [5,1] (since 5 > 2*1).
     * <p>
     * Complexity:
     * - Time:  O(high - low + 1) across all i (because 'right' never moves backward)
     * - Space: O(1) extra (just a few integers)
     */
    public static int countPairs(int[] arr, int low, int mid, int high) {
        int right = mid + 1;
        int cnt = 0;
        for (int i = low; i <= mid; i++) {
            while (right <= high && arr[i] > 2L * arr[right]) right++;
            cnt += (right - (mid + 1));
        }
        return cnt;
    }

    /**
     * mergeSort
     * <p>
     * What it does:
     * - Standard divide-and-conquer:
     * 1) Recursively sort left half and count pairs inside it.
     * 2) Recursively sort right half and count pairs inside it.
     * 3) Count cross pairs between the two sorted halves using countPairs().
     * 4) Merge the two sorted halves with merge().
     * - Returns the total number of reverse pairs in arr[low..high].
     * <p>
     * Why it works:
     * - Every pair belongs either to the left half, the right half, or straddles the boundary.
     * We count in exactly those three buckets without double counting.
     * - Sorting at each level enables the O(n) two-pointer cross-count (countPairs).
     * <p>
     * Complexity:
     * - Time:  O(n log n) overall (merge sort levels * linear work per level)
     * - Space: O(n) auxiliary for merging (the temporary arrays/lists used by merge)
     * <p>
     * Overflow safety:
     * - We always compare with 2L * value to force 64-bit math and avoid int overflow.
     */
    public static int mergeSort(int[] arr, int low, int high) {
        int cnt = 0;
        if (low >= high) return cnt;
        int mid = (low + high) / 2;
        cnt += mergeSort(arr, low, mid);  // left half
        cnt += mergeSort(arr, mid + 1, high); // right half
        cnt += countPairs(arr, low, mid, high); //Modification
        merge(arr, low, mid, high);  // merging sorted halves
        return cnt;
    }


}
