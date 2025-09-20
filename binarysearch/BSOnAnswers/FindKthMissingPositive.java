package binarysearch.BSOnAnswers;

/*
1539. Kth Missing Positive Number

Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
Return the kth positive integer that is missing from this array.

Example 1:
Input: arr = [2,3,4,7,11], k = 5
Output: 9
Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.

Example 2:
Input: arr = [1,2,3,4], k = 2
Output: 6
Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.

Constraints:
            1 <= arr.length <= 1000
            1 <= arr[i] <= 1000
            1 <= k <= 1000
            arr[i] < arr[j] for 1 <= i < j <= arr.length
 */
public class FindKthMissingPositive {

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 7, 11};
        int k = 5;
        int[] arr1 = {1, 2, 3, 4};
        int k1 = 2;

        System.out.println(findKthMissingBruteForce(arr, k));
        System.out.println(findKthMissingBruteForce(arr1, k1));
        System.out.println(findKthMissingOptimized(arr, k));
        System.out.println(findKthMissingOptimized(arr1, k1));
    }

    /**
     * What it does:
     * Returns the k-th missing positive integer given a strictly increasing, positive array {@code arr}.
     * <p>
     * How it works (linear counting):
     * - Walk upward from 1, keeping an index {@code idx} into {@code arr}.
     * - If {@code i} equals {@code arr[idx]}, it’s present → advance {@code idx}.
     * - Otherwise {@code i} is missing → increment a {@code missing} counter and remember {@code i} as the latest answer.
     * - Stop as soon as we’ve counted {@code k} missings; the last missing seen is the answer.
     * - Special case: if {@code arr} is empty, the first {@code k} positives are missing, so the answer is {@code k}.
     * <p>
     * Why it’s correct:
     * The scan from 1 upward classifies each integer as “present” (matches next {@code arr} value) or “missing”. The
     * k-th time we classify “missing”, we’ve reached exactly the k-th missing positive by definition.
     * <p>
     * Time Complexity:
     * - O(n + k) in the worst case (we may pass through all elements and count past the last one until the k-th gap).
     * <p>
     * Space Complexity:
     * - O(1): Constant extra variables.
     * <p>
     * Output:
     * - Returns the k-th missing positive integer.
     * <p>
     * Example:
     * - arr = [2,3,4,7,11], k = 5
     * Missing sequence = [1,5,6,8,9,10,...] → 5th missing is 9.
     */
    private static int findKthMissingBruteForce(int[] arr, int k) {
        if (arr == null || arr.length == 0) return k; // all positives missing start at 1

        int idx = 0;       // index in arr
        int missing = 0;   // how many missings counted so far
        int ans = -1;

        // march i upward until we've counted k missings
        for (int i = 1; missing < k; i++) {
            if (idx < arr.length && arr[idx] == i) {
                idx++;                 // i is present, move to next arr element
            } else {
                ans = i;               // i is missing
                missing++;
            }
        }
        return ans;
    }

    /**
     * findKthMissingOptimized
     * <p>
     * What it does:
     * Returns the k-th missing positive integer in O(log n) time using binary search on indices.
     * <p>
     * Key idea (missing-count prefix):
     * - For any index {@code mid}, the number of positives missing up to {@code arr[mid]} is:
     * missingCount(mid) = arr[mid] - (mid + 1)
     * Explanation: In a perfect array with no gaps, the value at index {@code mid} would be {@code mid+1}.
     * If the actual value is larger ({@code arr[mid]}), the difference is exactly how many numbers got skipped
     * before reaching that value.
     * <p>
     * Binary search invariant:
     * - We want the smallest index {@code low} such that {@code missingCount(low) >= k}.
     * - While searching:
     * * If {@code missingCount(mid) < k}, there aren’t enough gaps yet → move right: {@code low = mid + 1}.
     * * Otherwise we have at least k gaps by {@code mid} → move left: {@code high = mid - 1}.
     * - When the loop ends, {@code low} is the first index where the cumulative missing count reaches/exceeds {@code k}.
     * <p>
     * Why the return is {@code low + k}:
     * - After the loop:
     * * All indices {@code < low} have {@code missingCount < k}  (not enough gaps yet).
     * * Index {@code low} (if in range) is the first place where {@code missingCount >= k}.
     * - There are exactly {@code low} array elements ≤ the answer (the answer sits among the numbers
     * that are not in {@code arr} up to that point). The answer is therefore the k-th missing plus those {@code low}
     * present numbers:
     * answer = k + (count of present numbers before it) = k + low.
     * <p>
     * Equivalent form {@code high + k + 1}:
     * - During the same process, {@code high} finishes as the last index with {@code missingCount(high) < k}
     * (i.e., the last “not enough gaps yet”). Because {@code low = high + 1} at termination, we get:
     * answer = k + low = k + (high + 1) = high + k + 1.
     * - Both formulas are identical; pick the one that matches your code style.
     * <p>
     * Concrete example:
     * - arr = [2,3,4,7,11], k = 5
     * Index:      0  1  2  3   4
     * Value:      2  3  4  7  11
     * missingCount:
     * mid=0 → 2 - (0+1) = 1
     * mid=1 → 3 - (1+1) = 1
     * mid=2 → 4 - (2+1) = 1
     * mid=3 → 7 - (3+1) = 3
     * mid=4 → 11 - (4+1) = 6
     * We seek first index with missingCount ≥ 5 → that’s index 4.
     * After the loop: low = 4, high = 3.
     * Return:
     * low + k     = 4 + 5 = 9
     * high + k + 1= 3 + 5 + 1 = 9  (same result)
     * <p>
     * Edge cases:
     * - If all k missing numbers lie beyond the last element (e.g., arr = [1,2,3,4], k = 2):
     * the search ends with low = arr.length. Then:
     * answer = low + k = n + k  (here 4 + 2 = 6), which is correct.
     * - If k-th missing is before arr[0] (e.g., arr = [2], k = 1):
     * the search ends with low = 0, high = -1. Then:
     * answer = low + k = 0 + 1 = 1 (also equals high + k + 1 = -1 + 1 + 1 = 1).
     * <p>
     * Time Complexity:
     * - O(log n): Binary search over indices with O(1) work per step.
     * <p>
     * Space Complexity:
     * - O(1): Constant extra space.
     * <p>
     * Output:
     * - Returns the k-th missing positive integer.
     */
    private static int findKthMissingOptimized(int[] arr, int k) {
        if (arr == null || arr.length == 0) return k;
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int missingCount = arr[mid] - (mid + 1); // Calculate how many numbers are missing until arr[mid]

            if (missingCount < k) {
                low = mid + 1; // Move to the right half
            } else {
                high = mid - 1; // Move to the left half
            }
        }

        return low + k; // The kth missing number is low + k
    }
}
