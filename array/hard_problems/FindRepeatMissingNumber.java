package array.hard_problems;

import java.util.Arrays;

/*
Given an integer array nums of size n containing values from [1, n] and each value appears exactly once in the array,
except for A, which appears twice and B which is missing. Return the values A and B, as an array of size 2, where A
appears in the 0-th index and B in the 1st index.

Note: You are not allowed to modify the original array.
Examples:
Input: nums = [3, 5, 4, 1, 1]
Output: [1, 2]
Explanation: 1 appears two times in the array and 2 is missing from nums

Input: nums = [1, 2, 3, 6, 7, 5, 7]
Output: [7, 4]
Explanation: 7 appears two times in the array and 4 is missing from nums.

Constraints:
        n == nums.length
        1 <= n <= 10^5
        n - 2 elements in nums appear exactly once and are valued between [1, n].
        1 element in nums appears twice, and is valued between [1, n].
 */
public class FindRepeatMissingNumber {

    public static void main(String[] args) {
        int[] nums = {3, 5, 4, 1, 1};
        int[] nums1 = {1, 2, 3, 6, 7, 5, 7};
        System.out.println("Brute force approach: " + Arrays.toString(findRepeatingMissingNumberBruteForceApproach(nums)));
        System.out.println("Brute force approach: " + Arrays.toString(findRepeatingMissingNumberBruteForceApproach(nums1)));
        System.out.println("Hashing approach: " + Arrays.toString(findRepeatingMissingNumberHashingApproach(nums)));
        System.out.println("Hashing approach: " + Arrays.toString(findRepeatingMissingNumberHashingApproach(nums1)));
        System.out.println("Optimal approach using Math: " + Arrays.toString(findRepeatingMissingNumberOptimalApproach(nums)));
        System.out.println("Optimal approach using Math: " + Arrays.toString(findRepeatingMissingNumberOptimalApproach(nums1)));
        System.out.println("Optimal approach using XOR operation: " + Arrays.toString(findRepeatingMissingNumberOptimalApproach2(nums)));
        System.out.println("Optimal approach using XOR operation: " + Arrays.toString(findRepeatingMissingNumberOptimalApproach2(nums1)));
    }

    /**
     * Brute force (counting by scanning for each value 1..n).
     * <p>
     * Idea:
     * We know every number from 1 to n should appear exactly once, except one number A appears twice
     * and one number B is missing. For each possible value x from 1..n, scan the array and count how
     * many times x appears. If it appears 2 times, x is the repeating number (A). If it appears 0 times,
     * x is the missing number (B). Stop once both are found.
     * <p>
     * Algorithm steps:
     * 1) For x = 1 to n:
     * a) Set count = 0.
     * b) Scan the whole array and increment count whenever nums[j] == x.
     * c) If count == 2, set repeat = x.
     * If count == 0, set missing = x.
     * d) If both repeat and missing are known, stop early.
     * <p>
     * Example (nums = [3,5,4,1,1], n=5):
     * - x=1 → appears 2 times → repeat=1
     * - x=2 → appears 0 times → missing=2 → answer [1,2]
     * <p>
     * Time Complexity: O(n^2)
     * - For each x in 1..n (n times), we scan the array (up to n).
     * <p>
     * Space Complexity: O(1)
     * - Uses a few variables, no extra arrays or collections.
     * <p>
     * Does not modify the input array.
     *
     * @param nums Array of length n with values in [1..n], exactly one repeat and one missing.
     * @return int[]{A, B} where A is the repeating number and B is the missing number.
     */
    private static int[] findRepeatingMissingNumberBruteForceApproach(int[] nums) {

        int repeat = -1;
        int missing = -1;
        for (int i = 1; i <= nums.length; i++) {
            int count = 0;

            for (int j = 0; j < nums.length; j++) {
                if (nums[j] == i) {
                    count++;
                }
            }
            if (count == 2) {
                repeat = i;
            } else if (count == 0) {
                missing = i;
            }
            if (repeat != -1 && missing != -1) {
                break;
            }
        }

        int[] ans = {repeat, missing};
        return ans;
    }

    /**
     * Hashing / frequency array (O(n) time, O(n) extra space).
     * <p>
     * Idea:
     * Count how many times each value 1..n appears by using a small helper array "hash"
     * of size n+1. The index i of this helper array stores the frequency of value i.
     * Then scan 1..n to find which value has frequency 2 (repeat) and which has 0 (missing).
     * <p>
     * Algorithm steps:
     * 1) Create an int[] hash of size n+1, initially all zeros.
     * 2) For each v in nums: hash[v]++.
     * 3) For i from 1 to n:
     * - If hash[i] == 2 → repeat = i
     * - If hash[i] == 0 → missing = i
     * 4) Return [repeat, missing].
     * <p>
     * Example (nums = [3,5,4,1,1], n=5):
     * - After counting: hash[1]=2, hash[2]=0, others=1 → repeat=1, missing=2.
     * <p>
     * Time Complexity: O(n)
     * - One pass to fill hash, one pass to read it.
     * <p>
     * Space Complexity: O(n)
     * - The helper array hash of size n+1.
     * <p>
     * Does not modify the input array.
     *
     * @param nums Array of length n with values in [1..n], exactly one repeat and one missing.
     * @return int[]{A, B} where A is the repeating number and B is the missing number.
     */
    private static int[] findRepeatingMissingNumberHashingApproach(int[] nums) {
        int n = nums.length;
        int[] hash = new int[n + 1];
        for (int i = 0; i < n; i++) {
            hash[nums[i]]++;
        }
        int repeat = -1;
        int missing = -1;
        for (int i = 1; i <= n; i++) {
            if (hash[i] == 2) {
                repeat = i;
            }
            if (hash[i] == 0) {
                missing = i;
            }


        }

        int[] ans = {repeat, missing};
        return ans;
    }

    /**
     * Optimal using math (sum and sum of squares) — O(n) time, O(1) extra space.
     * <p>
     * Core identities:
     * Let A = repeating number, B = missing number.
     * Let S  = sum(nums),        Sn  = 1 + 2 + ... + n = n(n+1)/2
     * Let SS = sum(nums^2),      SSn = 1^2 + ... + n^2 = n(n+1)(2n+1)/6
     * <p>
     * Then:
     * diff   = S  - Sn  = A - B
     * sqDiff = SS - SSn = A^2 - B^2 = (A - B)(A + B) = diff * (A + B)
     * → A + B  = sqDiff / diff
     * → A      = (diff + (A + B)) / 2
     * → B      = A - diff
     * <p>
     * Algorithm steps:
     * 1) Compute S  = sum of all elements in nums.
     * 2) Compute SS = sum of squares of all elements in nums.
     * 3) Compute Sn  = n(n+1)/2 and SSn = n(n+1)(2n+1)/6.
     * 4) diff   = S - Sn         // equals A - B
     * 5) sqDiff = SS - SSn       // equals (A - B)(A + B)
     * 6) sumAB  = sqDiff / diff  // equals A + B
     * 7) A = (diff + sumAB)/2; B = A - diff.
     * 8) Return [A, B].
     * <p>
     * Mini example (nums = [3,5,4,1,1], n=5):
     * S=14,  Sn=15 → diff=-1 (=A-B)
     * SS=52, SSn=55 → sqDiff=-3 (= (A-B)(A+B))
     * sumAB = (-3)/(-1) = 3 (=A+B)
     * A = ( -1 + 3 ) / 2 = 1;  B = 1 - (-1) = 2 → answer [1,2]
     * <p>
     * Time Complexity: O(n)
     * - One pass to compute sums; the rest is O(1) arithmetic.
     * <p>
     * Space Complexity: O(1)
     * - Only a few variables; no extra arrays.
     * <p>
     * Implementation note:
     * - Use long for sums to avoid overflow on large n (you already did).
     * - diff cannot be zero because A != B.
     * <p>
     * Does not modify the input array.
     *
     * @param nums Array of length n with values in [1..n], exactly one repeat and one missing.
     * @return int[]{A, B} where A is the repeating number and B is the missing number.
     */
    private static int[] findRepeatingMissingNumberOptimalApproach(int[] nums) {
        long n = nums.length;
        int repeat = 0;
        int missing = 0;

        // S - sn
        long sn = (n * (n + 1)) / 2;
        long s2n = (n * (n + 1) * (2 * n + 1)) / 6;
        long s = 0;
        long s2 = 0;
        for (int i = 0; i < n; i++) {
            s += nums[i];
            s2 += (long) nums[i] * (long) nums[i];
        }
        //S2 - S2n
        long val1 = s - sn;
        long val2 = s2 - s2n;
        val2 = val2 / val1;
        long x = (val1 + val2) / 2;
        long y = x - val1;
        missing = (int) y;
        repeat = (int) x;

        return new int[]{repeat, missing};

    }

    /**
     * Optimal using XOR partitioning — O(n) time, O(1) extra space, no overflow risk.
     * <p>
     * Key XOR facts:
     * - x ^ x = 0 and x ^ 0 = x  (same numbers cancel out).
     * - If we XOR all numbers from 1..n with all elements of nums, every value that appears
     * exactly once cancels, leaving A ^ B (repeat XOR missing).
     * <p>
     * Algorithm steps:
     * 1) Compute xr by XORing every element in nums and every number in 1..n.
     * After this step: xr = A ^ B.
     * 2) Extract a set bit where A and B differ. Use the rightmost set bit:
     * mask = xr & ~(xr - 1)   // equivalent to xr & -xr
     * 3) Partition both the array and 1..n into two buckets using that bit:
     * - Bucket "one":   numbers with (value & mask) != 0
     * - Bucket "zero":  numbers with (value & mask) == 0
     * XOR all values inside each bucket separately (array + 1..n).
     * After cancellation, you get two candidates: cand1 and cand2 = {A, B} in some order.
     * 4) Disambiguate which is the duplicate:
     * Count how many times cand1 appears in nums.
     * - If it appears twice, cand1 is A (repeating) and cand2 is B (missing).
     * - Otherwise cand2 is A and cand1 is B.
     * <p>
     * Tiny walkthrough (nums = [3,5,4,1,1], n=5):
     * - xr = (1^2^3^4^5) ^ (3^5^4^1^1) = 3 (0b011) = A^B
     * - mask = xr & -xr = 1 (rightmost set bit)
     * - Partition & XOR:
     * Bucket one (bit 0 set): {1,3,5} and {3,5,1,1} → XOR = 1
     * Bucket zero (bit 0 not set): {2,4} and {4}    → XOR = 2
     * - Candidates are {1,2}. Count in nums: 1 appears twice → A=1, B=2.
     * <p>
     * Why this works:
     * - A and B differ at the chosen bit, so they fall into different buckets and do not cancel each other.
     * - All matching numbers from array and 1..n land in the same bucket and cancel out.
     * <p>
     * Time Complexity: O(n)
     * - One pass to compute xr; one pass to partition & XOR; one small pass to count a candidate.
     * <p>
     * Space Complexity: O(1)
     * - Only a few integer variables.
     * <p>
     * Bonus:
     * - No risk of integer overflow (XOR does not grow with n).
     * - Does not modify the input array.
     *
     * @param nums Array of length n with values in [1..n], exactly one repeat and one missing.
     * @return int[]{A, B} where A is the repeating number and B is the missing number.
     */
    private static int[] findRepeatingMissingNumberOptimalApproach2(int[] nums) {
        int n = nums.length; // size of the array
        int xr = 0;

        //Step 1: Find XOR of all elements:
        for (int i = 0; i < n; i++) {
            xr = xr ^ nums[i];
            xr = xr ^ (i + 1);
        }

        //Step 2: Find the differentiating bit number:
        int number = (xr & ~(xr - 1));

        //Step 3: Group the numbers:
        int zero = 0;
        int one = 0;
        for (int i = 0; i < n; i++) {
            //part of 1 group:
            if ((nums[i] & number) != 0) {
                one = one ^ nums[i];
            }
            //part of 0 group:
            else {
                zero = zero ^ nums[i];
            }
        }

        for (int i = 1; i <= n; i++) {
            //part of 1 group:
            if ((i & number) != 0) {
                one = one ^ i;
            }
            //part of 0 group:
            else {
                zero = zero ^ i;
            }
        }

        // Last step: Identify the numbers:
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == zero) cnt++;
        }

        if (cnt == 2) return new int[]{zero, one};
        return new int[]{one, zero};
    }
}
