package binarysearch.BSOnAnswers;

/*
Leetcode: 410. Split Array Largest Sum

Given an integer array nums and an integer k, split nums into k non-empty subarrays such that the largest sum of any
subarray is minimized. Return the minimized largest sum of the split. A subarray is a contiguous part of the array.

Example 1:
Input: nums = [7,2,5,10,8], k = 2
Output: 18
Explanation: There are four ways to split nums into two subarrays. The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.

Example 2:
Input: nums = [1,2,3,4,5], k = 2
Output: 9
Explanation: There are four ways to split nums into two subarrays.
The best way is to split it into [1,2,3] and [4,5], where the largest sum among the two subarrays is only 9.

Constraints:
            1 <= nums.length <= 1000
            0 <= nums[i] <= 10^6
            1 <= k <= min(50, nums.length)
 */
public class SplitArrayLargestSum {

    public static void main(String[] args) {
        int[] nums = {7, 2, 5, 10, 8};
        int k = 2;
        int[] nums1 = {1, 2, 3, 4, 5};
        int k1 = 2;

        System.out.println(splitArrayBruteForce(nums, k));
        System.out.println(splitArrayBruteForce(nums1, k1));
        System.out.println(splitArrayOptimal(nums, k));
        System.out.println(splitArrayOptimal(nums1, k1));

    }


    /**
     * What it does:
     * Finds the minimum possible maximum subarray sum when splitting an array of integers
     * into exactly k contiguous subarrays, using a brute force approach.
     * <p>
     * Intuition:
     * - We want to minimize the "heaviest" subarray load across k partitions.
     * - The smallest possible maximum load is at least the largest element (no subarray can be smaller than that).
     * - The largest possible maximum load is the sum of the entire array (one student/subarray carries everything).
     * - The true answer must lie between these two extremes.
     * <p>
     * Brute force approach:
     * - Iterate through every candidate value i from low to high:
     * - low = max(nums): the biggest single element.
     * - high = sum(nums): sum of all elements.
     * - For each candidate i, call isPossible to check if the array can be split
     * into ≤ k subarrays without exceeding i in any subarray.
     * - The first i that passes feasibility is the minimum maximum subarray sum.
     * <p>
     * Why it works:
     * - By checking sequentially from low upwards, the first feasible i is guaranteed
     * to be the minimal feasible maximum.
     * - isPossible is greedy and always finds the minimum number of subarrays needed
     * for a given max load, so the feasibility check is correct.
     * <p>
     * Example:
     * nums = [7, 2, 5, 10, 8], k = 2
     * - low = 10, high = 32
     * - Try i = 10 → not feasible.
     * - Try i = 11 → not feasible.
     * - …
     * - Try i = 18 → feasible (split into [7,2,5] and [10,8]).
     * - Answer = 18.
     * <p>
     * Time Complexity:
     * - Outer loop: (high - low + 1) iterations ≈ sum(nums) - max(nums).
     * - Each feasibility check: O(n).
     * - Total: O(n * (sum(nums) - max(nums))) → exponential in input size, impractical for large arrays.
     * <p>
     * Space Complexity:
     * - O(1) extra space.
     * <p>
     * Notes:
     * - This brute force is conceptually simple but highly inefficient compared to the binary search approach.
     * - It’s mainly useful for understanding the problem mechanics or for very small arrays.
     */
    private static int splitArrayBruteForce(int[] nums, int k) {
        int low = Integer.MIN_VALUE;
        int high = 0;
        int ans = -1;
        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }

        for (int i = low; i <= high; i++) {
            if (isPossible(nums, i, k)) {
                ans = i;
                break;
            }
        }
        return ans;
    }

    /**
     * What it does:
     * Splits an array of positive integers into k contiguous subarrays such that
     * the largest subarray sum is minimized. Returns that minimized maximum sum.
     * <p>
     * Problem intuition:
     * - We want to balance loads: if one subarray sum is very large, that determines the answer.
     * - The task is essentially: minimize the "heaviest" subarray sum across exactly k partitions.
     * <p>
     * Why binary search works:
     * - Let’s define a candidate maximum allowed subarray sum = mid.
     * - If we can split the array into ≤ k subarrays with each sum ≤ mid, then mid is feasible.
     * - If mid is feasible, any larger value is also feasible; if mid is not feasible, any smaller
     * value is impossible. This monotone property allows binary search.
     * <p>
     * Choosing search bounds (low and high):
     * - low = max(nums): each subarray must contain at least one element, so no subarray sum
     * can be less than the largest single element.
     * - high = sum(nums): the trivial case where the entire array is one subarray.
     * - The answer lies in [low, high].
     * <p>
     * How the loop works:
     * - mid = (low + high) / 2 → try this as the maximum subarray sum.
     * - If isPossible(mid) == true → we can split within k subarrays, so shrink the upper bound
     * (high = mid - 1) to see if we can do better (smaller maximum).
     * - If isPossible(mid) == false → we need more than k subarrays, so increase lower bound
     * (low = mid + 1).
     * - When the loop ends, low is the smallest feasible maximum subarray sum.
     * <p>
     * Example walkthrough:
     * nums = [7,2,5,10,8], k = 2
     * - low = max(nums) = 10, high = sum(nums) = 32
     * - mid = 21: split → [7,2,5,10], [8], 2 subarrays → feasible → shrink high
     * - mid = 15: split → [7,2,5], [10], [8], 3 subarrays → too many → raise low
     * - Eventually converges to low = 18 → answer = 18
     * (partition [7,2,5], [10,8]).
     * <p>
     * Time complexity:
     * - Each feasibility check is O(n).
     * - Binary search runs O(log(sum(nums))).
     * - Overall: O(n log(sum(nums))).
     * <p>
     * Space complexity:
     * - O(1) extra space.
     */
    private static int splitArrayOptimal(int[] nums, int k) {
        int low = Integer.MIN_VALUE;
        int high = 0;
        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (isPossible(nums, mid, k)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * What it does:
     * Checks whether nums can be split into at most k contiguous subarrays such that
     * no subarray sum exceeds mid.
     * <p>
     * Greedy logic:
     * - Start with the first subarray (count = 1).
     * - Accumulate elements until adding the next would exceed mid.
     * - When sum > mid, start a new subarray with the current element.
     * - If at any point subarray count > k, return false.
     * - If we finish with ≤ k subarrays, return true.
     * <p>
     * Why greedy works:
     * - Packing left to right and cutting only when forced creates the fewest subarrays possible
     * under a given mid. If even this needs > k subarrays, mid is infeasible.
     * <p>
     * Example:
     * nums = [7,2,5,10,8], mid = 15, k = 2
     * - Subarray 1: 7+2+5=14 (next 10 would exceed 15) → cut
     * - Subarray 2: 10 (next 8 would exceed 15) → cut → Subarray 3: 8
     * - Total subarrays = 3 > k=2 → not feasible.
     * <p>
     * Time complexity:
     * - O(n): single pass over nums.
     * <p>
     * Space complexity:
     * - O(1).
     */
    private static boolean isPossible(int[] nums, int mid, int k) {
        int sum = 0;
        int count = 1; // at least one subarray is needed
        for (int num : nums) {
            sum += num;
            if (sum > mid) {
                count++;
                sum = num; // start new subarray with current number
                if (count > k) return false; // more than k subarrays needed
            }
        }
        return true; // can be done with k or fewer subarrays
    }
}
