package bitmanipulation;

/*
Leetcode 136: Single Number

Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
You must implement a solution with a linear runtime complexity and use only constant extra space.

Example 1:
Input: nums = [2,2,1]
Output: 1

Example 2:
Input: nums = [4,1,2,1,2]
Output: 4

Example 3:
Input: nums = [1]
Output: 1

Constraints:
            1 <= nums.length <= 3 * 10^4
            -3 * 10^4 <= nums[i] <= 3 * 10^4
            Each element in the array appears twice except for one element which appears only once.
 */
public class SingleNumber {

    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{2, 2, 1}));
    }

    /**
     * What it does:
     * Finds the element that appears exactly once in an array where
     * every other element appears exactly twice.
     * <p>
     * Why it works:
     * The solution relies on the properties of the bitwise XOR (^) operator:
     * - x ^ x = 0 (a number XORed with itself cancels out)
     * - x ^ 0 = x (XOR with zero leaves the number unchanged)
     * - XOR is associative and commutative
     * <p>
     * When all elements are XORed together:
     * - Each pair of identical numbers cancels itself out
     * - The remaining value is the number that appears only once
     * <p>
     * This allows the algorithm to identify the unique element
     * without using extra memory.
     * <p>
     * Important details:
     * - Uses only constant extra space.
     * - Runs in linear time by scanning the array once.
     * - Order of elements does not matter due to XOR properties.
     * - Works correctly for positive, negative, and zero values.
     * - Assumes exactly one element appears once, as per problem constraints.
     * <p>
     * Example:
     * nums = [4, 1, 2, 1, 2]
     * <p>
     * XOR process:
     * 0 ^ 4 = 4
     * 4 ^ 1 = 5
     * 5 ^ 2 = 7
     * 7 ^ 1 = 6
     * 6 ^ 2 = 4
     * <p>
     * Final result = 4
     * <p>
     * Complexity:
     * Time:  O(n)   (single pass through the array)
     * Space: O(1)   (constant extra space)
     */
    private static int singleNumber(int[] nums) {
        // This check is not needed here because as per the givne constraint there would be always one element present in the array.
        // But in interview I should ask what if the array is empty.
        // if (nums == null || nums.length == 0) {
        //  return 0;
        //  }
        if (nums.length == 1) return nums[0];
        int answer = 0;
        for (int num : nums) {
            answer ^= num;
        }
        return answer;
    }
}
