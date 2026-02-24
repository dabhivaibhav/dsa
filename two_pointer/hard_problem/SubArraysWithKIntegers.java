package two_pointer.hard_problem;

import java.util.HashMap;

/*
Leetcode 992: Subarrays with K Different Integers

Given an integer array nums and an integer k, return the number of good subarrays of nums.
A good array is an array where the number of different integers in that array is exactly k.

For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
A subarray is a contiguous part of an array.

Example 1:
Input: nums = [1,2,1,2,3], k = 2
Output: 7
Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]

Example 2:
Input: nums = [1,2,1,3,4], k = 3
Output: 3
Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].

Constraints:
            1 <= nums.length <= 2 * 10^4
            1 <= nums[i], k <= nums.length
 */
public class SubArraysWithKIntegers {
    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 2, 3};
        int k = 2;
        System.out.println(subarraysWithKDistinctBruteForce(nums, k));
    }

    /**
     * Method: subarraysWithKDistinctBruteForce(int[] nums, int k)
     * <p>
     * What this method does:
     * Counts the number of subarrays
     * that contain exactly k distinct integers.
     * <p>
     * Core Idea:
     * <p>
     * Generate every possible subarray.
     * Track how many distinct integers it contains.
     * If distinct count equals k → increment answer.
     * <p>
     * How It Works:
     * <p>
     * Outer Loop (i):
     * Fix the starting index of the subarray.
     * <p>
     * For each starting index:
     * - Create a fresh HashMap to store
     * frequency of elements in the current window.
     * <p>
     * Inner Loop (j):
     * Expand the subarray from index i to j.
     * <p>
     * If nums[j] is not in the map:
     * Add it with frequency 1.
     * Else:
     * Increment its frequency.
     * <p>
     * If map.size() > k:
     * Break.
     * Because adding more elements
     * will only increase distinct count further.
     * <p>
     * <p>
     * If map.size() == k:
     * Increment totalCount.
     * <p>
     * Example:
     * <p>
     * nums = [1,2,1,2,3], k = 2
     * <p>
     * Valid subarrays:
     * [1,2], [2,1], [1,2], [2,3],
     * [1,2,1], [2,1,2], [1,2,1,2]
     * <p>
     * Total = 7
     * <p>
     * Why Break When > k?
     * <p>
     * Because once distinct elements exceed k,
     * any further expansion will only increase or maintain
     * distinct count — it can never decrease.
     * <p>
     * So there is no reason to continue from this start index.
     * <p>
     * Time Complexity:
     * <p>
     * Outer loop → O(n)
     * Inner loop → O(n) in worst case
     * <p>
     * Total:
     * O(n) * O(n)
     * →   O(n²)
     * <p>
     * Space Complexity:
     * O(k)
     * At most k distinct integers
     * are stored in the map at any time.
     * <p>
     * Interview Insight:
     * <p>
     * This solution is correct but not optimal.
     * <p>
     * The optimized approach uses
     * the mathematical trick:
     * <p>
     * Exactly(k) = AtMost(k) − AtMost(k − 1)
     * <p>
     * That reduces time complexity to O(n).
     * <p>
     * The leap from brute-force to optimal
     * is recognizing that counting
     * "exactly k" directly is messy,
     * but counting "at most k"
     * is much cleaner with sliding window.
     */
    private static int subarraysWithKDistinctBruteForce(int[] nums, int k) {
        int totalCount = 0;
        for (int i = 0; i < nums.length; i++) {
            // Storing the frequency of each element in the window
            HashMap<Integer, Integer> map = new HashMap<>();
            // Expanding the window from index i
            for (int j = i; j < nums.length; j++) {
                // Adding nums[j] to the window
                if (!map.containsKey(nums[j]))
                    map.put(nums[j], 1);
                    // Incrementing frequency of nums[j]
                else map.put(nums[j], map.get(nums[j]) + 1);
                // If the map contains more than k distinct elements, break
                if (map.size() > k) break;
//                for (Map.Entry<Integer, Integer> entry : map.entrySet())
//                    System.out.println(entry.getKey() + " " + entry.getValue());
                // If the map contains exactly k distinct elements, increment totalCount
                if (map.size() == k) totalCount++;
            }
        }
        return totalCount;
    }
}
