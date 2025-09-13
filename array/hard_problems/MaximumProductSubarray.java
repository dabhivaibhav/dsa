package array.hard_problems;

/*
Maximum Product Subarray in an Array
Given an integer array nums. Find the subarray with the largest product, and return the product of the elements present
in that subarray. A subarray is a contiguous non-empty sequence of elements within an array.

Examples:
Input: nums = [4, 5, 3, 7, 1, 2]
Output: 840
Explanation: The largest product is given by the whole array itself

Input: nums = [-5, 0, -2]
Output: 0
Explanation: The largest product is achieved with the following subarrays [0], [-5, 0], [0, -2], [-5, 0, -2].

Constraints:
            1 <= nums.length <= 10^4
            -10 <= nums[i] <= 10
            The product of any subarray of nums is guaranteed to fit in a 32-bit integer.
 */
public class MaximumProductSubarray {

    public static void main(String[] args) {
        int[] nums = {-5, 0, -2};
        int[] nums1 = {4, 5, 3, 7, 1, 2};

        maximumProductSubArrayBruteforce(nums);
        maximumProductSubArrayBruteforce(nums1);
        maximumProductSubArrayOptimizedApproach(nums);
        maximumProductSubArrayOptimizedApproach(nums1);
    }

    /**
     * maximumProductSubArrayBruteforce
     * <p>
     * What it does:
     * I check every possible subarray product and keep the maximum seen.
     * For each start index i, I extend to the right and keep a running product (product *= nums[j]).
     * <p>
     * Why it works:
     * The maximum product subarray must be one of the O(n^2) subarrays.
     * Multiplying forward from each i lets me evaluate all of them without recomputing from scratch.
     * <p>
     * Important details:
     * - I reset product = 1 when I move the start i, so each inner run builds products incrementally.
     * - This naturally handles negatives and zeros because the running product reflects exactly
     * what the current subarray multiplies to.
     * <p>
     * Example (nums = [2, 3, -2, 4]):
     * i=0: product=2 → max=2; then 2*3=6 → max=6; then 6*-2=-12 → max=6; then -12*4=-48 → max=6
     * i=1: product=3 → max=6; then 3*-2=-6 → max=6; then -6*4=-24 → max=6
     * i=2: product=-2 → max=6; then -2*4=-8 → max=6
     * i=3: product=4 → max=6
     * Answer = 6
     * <p>
     * Complexity:
     * Time:  O(n^2)    (two nested loops over i and j)
     * Space: O(1)      (only a few integers)
     */
    private static void maximumProductSubArrayBruteforce(int[] nums) {

        int product = 1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            product = 1;
            for (int j = i; j < nums.length; j++) {
                product = product * nums[j];

                max = Math.max(product, max);
            }
        }
        System.out.println("Maximum product: " + max);
    }


    /**
     * maximumProductSubArrayOptimizedApproach
     * <p>
     * What it does:
     * I scan from both ends at once using two running products: 'prefix' (left→right) and
     * 'suffix' (right→left). At each step, I update the answer with max(prefix, suffix).
     * <p>
     * Why it works (core idea):
     * - ZEROS split the array into independent segments because any product crossing a zero is 0.
     * Within a zero-free segment, the maximum product is either:
     * • the entire segment if it has an even number of negatives, OR
     * • the segment with either the prefix up to the first negative removed,
     * OR the suffix after the last negative removed (odd number of negatives).
     * - A left→right product (prefix) captures the effect of dropping an initial prefix.
     * A right→left product (suffix) captures the effect of dropping a trailing suffix.
     * - Taking max(prefix, suffix) at each position effectively considers both “cut from left”
     * and “cut from right” options across every zero-free segment.
     * <p>
     * Handling zeros:
     * - When a running product becomes 0, I reset it to 1 on the next iteration so the next
     * segment can start fresh. Importantly, I still consider 0 as a candidate because in the
     * iteration where I multiply by 0, max = max(max, 0) is performed.
     * <p>
     * Tricky cases and how this catches them:
     * - [2, 3, -2, 4]:
     * prefix: 2 → 6 → -12 → -48
     * suffix: 4 → -8 → -24  → -48
     * max over the scan is 6 (left-to-right catches the best suffix product).
     * Note: the standard accepted answer for LeetCode 152 is 6 because it asks for the
     * maximum *contiguous subarray product*; your prefix/suffix scan correctly finds the
     * best over the segment perspectives—this approach is widely used because max is updated
     * at every step and zeros split segments, so the running maxima encountered correspond
     * to valid subarrays in those segments.
     * - [-2, 0, -1]: zero is considered; best is 0.
     * - [-2, -3, -4]: even number of negatives → big positive; both prefix/suffix will expose it.
     * <p>
     * Complexity:
     * Time:  O(n)   (single pass; both directions computed in the same loop)
     * Space: O(1)   (a few scalars)
     * <p>
     * Notes:
     * - Initial max = Integer.MIN_VALUE ensures single-element cases are handled (e.g., [-1]).
     * - If you want extra safety in other contexts, you could use long for intermediate products,
     * but LeetCode’s constraints typically keep int products within range.
     */
    private static void maximumProductSubArrayOptimizedApproach(int[] nums) {
        int max = Integer.MIN_VALUE;
        int prefix = 1;
        int suffix = 1;
        for (int i = 0; i < nums.length; i++) {
            if (prefix == 0) {
                prefix = 1;
            }
            if (suffix == 0) {
                suffix = 1;
            }
            prefix *= nums[i];
            suffix *= nums[nums.length - i - 1];
            max = Math.max(Math.max(prefix, suffix), max);

        }
        System.out.println("Optimized approach Maximum product: " + max);
    }
}

