package array.hard_problems;

import java.util.HashMap;

/**
 * Count subarrays with given xor K
 * <p>
 * Given an array of integers nums and an integer k, return the total number of subarrays whose XOR equals to k.
 * <p>
 * Examples:
 * Input : nums = [4, 2, 2, 6, 4], k = 6
 * Output : 4
 * Explanation : The subarrays having XOR of their elements as 6 are [4, 2],  [4, 2, 2, 6, 4], [2, 2, 6], and [6]
 * <p>
 * Input :nums = [5, 6, 7, 8, 9], k = 5
 * Output : 2
 * Explanation : The subarrays having XOR of their elements as 5 are [5] and [5, 6, 7, 8, 9]
 * <p>
 * Constraints:
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 1 <= k <= 10^9
 */
public class SubarrayGivenXOR {

    public static void main(String[] args) {
        int[] nums = {4, 2, 2, 6, 4};
        int k = 6;
        subArrayGivenBruteForceApproach(nums, k);
        subArrayGivenOptimalApproach(nums, k);
    }

    /**
     * Brute Force approach to find count of subarrays with given XOR value k.
     * Algorithm:
     * 1. Use two nested loops to try every possible subarray
     * 2. Calculate XOR of elements in current subarray
     * 3. If XOR equals k, increment counter
     * <p>
     * Example:
     * nums = [4,2,2,6,4], k = 6
     * One iteration finds: nums[0](4) XOR nums[1](2) = 6
     * This subarray [4,2] contributes to count
     * <p>
     * Time Complexity: O(n^2) - two nested loops
     * Space Complexity: O(1) - only using constant extra space
     *
     * @param nums array of integers
     * @param k    target XOR value
     */
    private static void subArrayGivenBruteForceApproach(int[] nums, int k) {

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int xor = 0;

            for (int j = i; j < nums.length; j++) {
                xor ^= nums[j];
                if (xor == k) {
                    count++;
//                    System.out.println("Subarray with XOR of its elements as " + k + " is : " + (j - i + 1));
                }
            }
        }
        System.out.println("Total number of subarrays with XOR of their elements as " + k + " is : " + count);
    }


    /**
     * Optimal approach using HashMap to find count of subarrays with given XOR value k.
     * Algorithm:
     * 1. Use HashMap to store prefix XOR values and their frequencies
     * 2. Calculate running XOR and check if (current XOR ^ k) exists in map
     * 3. Add frequency of complementary XOR to count
     * <p>
     * Example:
     * nums = [4,2,2,6,4], k = 6
     * When xor=4: Check if (4^6=2) exists in map
     * When xor=6: Check if (6^6=0) exists in map
     * <p>
     * Time Complexity: O(n) - single pass through array
     * Space Complexity: O(n) - for HashMap storage
     *
     * @param nums array of integers
     * @param k    target XOR value
     */
    private static void subArrayGivenOptimalApproach(int[] nums, int k) {
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
            if (map.containsKey(xor ^ k)) {
                count += map.get(xor ^ k);
            }
            map.put(xor, map.getOrDefault(xor, 0) + 1);
        }
        System.out.println("Total number of subarrays with XOR of their elements as " + k + " is : " + count);
    }

}
