package binarysearch.BSOnAnswers;

/*
Leetcode: 1283. Find the Smallest Divisor Given a Threshold
Given an array of integers nums and an integer threshold, we will choose a positive integer divisor, divide all the
array by it, and sum the division's result. Find the smallest divisor such that the result mentioned above is less than
or equal to threshold. Each result of the division is rounded to the nearest integer greater than or equal to that
element. (For example: 7/3 = 3 and 10/2 = 5). The test cases are generated so that there will be an answer.

Example 1:
Input: nums = [1,2,5,9], threshold = 6
Output: 5
Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1. If the divisor is 4 we can get a sum of 7 (1+1+2+3)
and if the divisor is 5 the sum will be 5 (1+1+1+2).

Example 2:
Input: nums = [44,22,33,11,1], threshold = 5
Output: 44

Constraints:
            1 <= nums.length <= 5 * 10^4
            1 <= nums[i] <= 10^6
            nums.length <= threshold <= 10^6
 */
public class FindSmallestDivisorGivenThreshold {

    public static void main(String[] args) {

        int[] nums = {1, 2, 5, 9};
        int threshold = 6;
        int[] nums1 = {44, 22, 33, 11, 1};
        int threshold1 = 5;

        System.out.println(findSmallestDivisorBruteforce(nums, threshold));
        System.out.println(findSmallestDivisorBruteforce(nums1, threshold1));
        System.out.println(findSmallestDivisionOptimal(nums, threshold));
        System.out.println(findSmallestDivisionOptimal(nums1, threshold1));
    }

    /**
     * What it does:
     * Finds the smallest positive divisor `d` such that the sum of
     * ceil(nums[i] / d) across all elements is less than or equal to `threshold`.
     * This is a brute-force approach.
     * <p>
     * How it works:
     * - First finds the maximum element in the array (using findMax()) to establish the upper bound.
     * - Iterates through all possible divisors `i` from 1 up to max(nums):
     * - For each `i`, it computes the sum:
     * sum = Σ ceil(nums[j] / i) for all elements nums[j]
     * using integer ceiling arithmetic `(num + i - 1) / i` to avoid floating-point.
     * - If at any point `sum` exceeds `threshold`, it breaks early (because this divisor is too small).
     * - If after checking all elements `sum <= threshold`, it immediately returns `i`
     * because this is the smallest working divisor.
     * - If no divisor works (which normally can’t happen given problem constraints), returns -1.
     * <p>
     * Why this range is correct:
     * - The divisor must be at least 1 (slowest) and at most max(nums) (fastest needed).
     * - At divisor = max(nums), each term becomes 1, so total sum = n, which is always ≤ threshold by constraints.
     * <p>
     * Time Complexity:
     * - O(n * max(nums)):
     * - The outer loop runs up to max(nums) times, and each iteration does O(n) work.
     * - This is acceptable only as a brute-force solution for small max(nums).
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few local variables.
     * <p>
     * Output:
     * Returns the smallest divisor `d` such that the total sum is ≤ threshold.
     * <p>
     * Example:
     * nums = [1, 2, 5, 9], threshold = 6
     * → returns 5 (because 1/5+2/5+5/5+9/5 → 1+1+1+2 = 5 ≤ 6)
     */
    private static int findSmallestDivisorBruteforce(int[] nums, int threshold) {
        int max = findMax(nums);
        for (int i = 1; i <= max; i++) {      // smallest → largest
            long sum = 0L;                    // use long to avoid overflow
            for (int num : nums) {
                sum += (num + i - 1) / i;     // integer ceiling
                if (sum > threshold) break;   // early exit: already too big
            }
            if (sum <= threshold) return i;   // first i that works is the answer
        }
        return -1; // if the problem guarantees a solution, this path won't hit
    }


    /**
     * What it does:
     * Finds the smallest positive divisor `d` such that the sum of
     * ceil(nums[i] / d) across all elements is less than or equal to `threshold`.
     * This method uses binary search on the answer for optimal performance.
     * <p>
     * Why it works:
     * - The function f(d) = Σ ceil(nums[i] / d) is **monotonically non-increasing**:
     * - If `d` increases, each term becomes smaller or stays the same.
     * - So as `d` increases, the total sum of divisions goes down.
     * - This monotonic behavior allows binary search over the possible divisor space.
     * <p>
     * How it works:
     * - The possible divisor range is [1, max(nums)]:
     * - At `d = 1`, sum is the largest (sum of all elements).
     * - At `d = max(nums)`, each term becomes 1, so total sum = nums.length (which is ≤ threshold by constraints).
     * - Perform binary search in this range:
     * - Calculate mid = (low + high) / 2.
     * - Compute the sum of ceil(nums[i] / mid) for all elements using
     * integer ceiling math: `(num + mid - 1) / mid`.
     * • This avoids floating-point operations and prevents rounding errors.
     * • `long` is used for the sum to prevent overflow when nums are large.
     * - If sum ≤ threshold:
     * - mid is a valid divisor, but we try to find a smaller one → `high = mid - 1`.
     * - If sum > threshold:
     * - mid is too small, so we need a bigger divisor → `low = mid + 1`.
     * - When the loop ends, `low` will be the smallest divisor that satisfies the threshold.
     * <p>
     * Time Complexity:
     * - O(n * log(max(nums))):
     * - Each binary search step does O(n) work to compute the sum,
     * - and the binary search runs for O(log(max(nums))) steps.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few extra variables.
     * <p>
     * Output:
     * Returns the smallest divisor `d` such that the total sum is ≤ threshold.
     * <p>
     * Example:
     * nums = [1, 2, 5, 9], threshold = 6
     * → returns 5
     * Explanation: ceil(1/5)+ceil(2/5)+ceil(5/5)+ceil(9/5) = 1+1+1+2 = 5 ≤ 6.
     */

    private static int findSmallestDivisionOptimal(int[] nums, int threshold) {
        int low = 1;
        int high = findMax(nums);
        while (low <= high) {
            int mid = low + (high - low) / 2;

            long sum = 0L;
            for (int num : nums) {
                sum += (num + mid - 1) / mid;
                if (sum > threshold) break;
            }
            if (sum <= threshold) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * What it does:
     * Finds and returns the maximum element in the given integer array.
     * <p>
     * How it works:
     * - Iterates through each element in the array.
     * - Tracks the largest element seen so far in a variable `max`.
     * - Returns `max` at the end.
     * <p>
     * Time Complexity:
     * - O(n): Visits each element exactly once.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a single extra variable.
     * <p>
     * Output:
     * Returns the maximum value found in the array.
     * <p>
     * Example:
     * nums = [1, 2, 5, 9]
     * → returns 9
     */
    private static int findMax(int[] nums) {
        int max = nums[0];
        for (int v : nums) if (v > max) max = v;
        return max;
    }

}
