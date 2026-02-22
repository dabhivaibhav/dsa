package two_pointer.medium_problem;

/*
Leetcode 1248: Count Number of Nice Subarrays

Given an array of integers nums and an integer k. A continuous subarray is called nice if there are k odd numbers on it.
Return the number of nice sub-arrays.

Example 1:
Input: nums = [1,1,2,1,1], k = 3
Output: 2
Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].

Example 2:
Input: nums = [2,4,6], k = 1
Output: 0
Explanation: There are no odd numbers in the array.

Example 3:
Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
Output: 16


Constraints:
            1 <= nums.length <= 50000
            1 <= nums[i] <= 10^5
            1 <= k <= nums.length
 */
public class CountNumberOfNiceSubarrays {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 1, 1};
        int k = 3;
        System.out.println(numberOfSubarraysBruteForce(nums, k));
        System.out.println(numberOfSubarraysSlidingWindow(nums, k));
    }

    /**
     * numberOfSubarraysBruteForce(int[] nums, int k)
     * <p>
     * What this method does:
     * Counts the number of continuous subarrays
     * that contain exactly k odd numbers.
     * <p>
     * Core Idea:
     * <p>
     * Generate every possible subarray.
     * For each starting index, expand to the right
     * and count how many odd numbers appear.
     * <p>
     * Whenever the count of odd numbers becomes exactly k,
     * increment the answer.
     * <p>
     * Thought Process:
     * <p>
     * A subarray is valid ("nice") if it contains exactly k odd numbers.
     * <p>
     * The brute force way to ensure correctness:
     * <p>
     * 1. Choose a starting index i.
     * 2. Expand the subarray to the right.
     * 3. Maintain a running count of odd numbers.
     * 4. If oddCount == k → increment total count.
     * <p>
     * Repeat this for all starting positions.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int totalOddCount = 0;
     * Stores total number of nice subarrays.
     * <p>
     * Outer Loop:
     * for (int i = 0; i < nums.length; i++)
     * <p>
     * Choose every possible starting index.
     * <p>
     * For each starting index:
     * <p>
     * int oddCount = 0;
     * Tracks number of odd numbers in current subarray.
     * <p>
     * Inner Loop:
     * for (int j = i; j < nums.length; j++)
     * <p>
     * Expand subarray from i to j.
     * <p>
     * Check if nums[j] is odd:
     * <p>
     * (nums[j] & 1) != 0
     * <p>
     * Explanation:
     * - If last bit is 1 → number is odd.
     * - Bitwise check is faster than using % 2.
     * <p>
     * If oddCount == k:
     * Increment totalOddCount.
     * <p>
     * Example:
     * <p>
     * nums = [1,1,2,1,1], k = 3
     * <p>
     * Valid subarrays:
     * [1,1,2,1]
     * [1,2,1,1]
     * <p>
     * Total = 2
     * <p>
     * Complexity:
     * <p>
     * Outer loop runs n times.
     * Inner loop runs up to n times.
     * <p>
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     * <p>
     * Interview Takeaway:
     * <p>
     * This approach is straightforward and correct,
     * but inefficient for large inputs.
     * <p>
     * The key optimization insight:
     * This problem can be converted into:
     * <p>
     * Count of subarrays with at most k odds
     * minus
     * Count of subarrays with at most (k - 1) odds
     * <p>
     * That allows an O(n) sliding window solution.
     */
    private static int numberOfSubarraysBruteForce(int[] nums, int k) {
        int totalOddCount = 0;

        for (int i = 0; i < nums.length; i++) {
            int oddCount = 0;
            for (int j = i; j < nums.length; j++) {
                if ((nums[j] & 1) != 0) {
                    oddCount++;
                }
                if (oddCount == k) {
                    totalOddCount++;
                }
            }
        }
        return totalOddCount;
    }

    /*
    THE BUDGET MENTAL MODEL
    Imagine you need to find teams with EXACTLY k specialists.

    THE PROBLEM
    Counting Exactly k is hard because adding non-specialists (even numbers) keeps the count the same.
    The window doesn't "break," so it's hard to know when to stop.

    THE SOLUTION
    Instead of counting Exactly k directly, we count At Most k.

    THE BIG CIRCLE (AtMost k)
    This counts every subarray with 0, 1, 2, up to k odd numbers.
    Every time the right pointer moves, the number of NEW subarrays is the window length (right - left + 1).

    THE SMALL CIRCLE (AtMost k-1)
    This counts every subarray with 0, 1, 2, up to k-1 odd numbers.

    THE SUBTRACTION
    Big Circle minus Small Circle leaves only subarrays with EXACTLY k.
    Think of a building: To find only people on the 5th floor, take everyone on floors 1-5 and subtract everyone on floors 1-4.

    WHY IT WORKS
    It turns a hard "Exactly" problem into a simple "At Most" sliding window problem.
    Total = AtMost(k) - AtMost(k-1).
    */

    /**
     * Method: numberOfSubarraysSlidingWindow(int[] nums, int k)
     * <p>
     * What this method does:
     * Counts the number of subarrays containing exactly k odd numbers
     * using an optimized Sliding Window technique.
     * <p>
     * <p>
     * Core Insight:
     * <p>
     * Instead of directly counting subarrays with exactly k odds,
     * we use a powerful identity:
     * <p>
     * exactly(k) = atMost(k) - atMost(k - 1)
     * <p>
     * Why this works:
     * <p>
     * - atMost(k) counts subarrays with 0, 1, 2, ..., k odd numbers.
     * - atMost(k - 1) counts subarrays with 0, 1, 2, ..., k - 1 odd numbers.
     * <p>
     * Subtracting removes all smaller cases,
     * leaving only subarrays with exactly k odds.
     * <p>
     * <p>
     * Method: atMost(int[] nums, int k)
     * <p>
     * What it does:
     * Counts the number of subarrays containing at most k odd numbers.
     * <p>
     * <p>
     * How Sliding Window Works Here:
     * <p>
     * - Expand window using right pointer.
     * - Increase oddCount when encountering an odd number.
     * <p>
     * - If oddCount exceeds k:
     * Shrink window from left
     * until oddCount <= k.
     * <p>
     * The key idea:
     * <p>
     * When the window is valid,
     * the number of valid subarrays ending at index right is:
     * <p>
     * (right - left + 1)
     * <p>
     * Why?
     * <p>
     * Because any starting index between left and right
     * forms a valid subarray ending at right.
     * <p>
     * <p>
     * Example:
     * <p>
     * nums = [1,1,2,1,1], k = 3
     * <p>
     * atMost(3) counts all subarrays with ≤ 3 odds.
     * atMost(2) counts all subarrays with ≤ 2 odds.
     * <p>
     * Subtracting gives subarrays with exactly 3 odds.
     * <p>
     * <p>
     * Complexity:
     * <p>
     * Time Complexity: O(n)
     * Each element is visited at most twice
     * (once by right, once by left).
     * <p>
     * Space Complexity: O(1)
     * <p>
     * <p>
     * Interview Takeaway:
     * <p>
     * Whenever you see:
     * "Exactly K occurrences"
     * <p>
     * Think:
     * atMost(K) - atMost(K - 1)
     * <p>
     * This trick works beautifully with sliding window
     * when elements are non-negative or monotonic.
     */
    private static int numberOfSubarraysSlidingWindow(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private static int atMost(int[] nums, int k) {
        if (k < 0) return 0;
        int left = 0, right = 0, count = 0;
        int oddCount = 0;

        while (right < nums.length) {
            if ((nums[right] & 1) != 0) oddCount++;

            while (oddCount > k) {
                if ((nums[left] & 1) != 0) oddCount--;
                left++;
            }

            // This is the magic: The number of subarrays ending at 'right'
            // with AT MOST k odd numbers is the window length!
            count += (right - left + 1);
            right++;
        }
        return count;
    }

}
