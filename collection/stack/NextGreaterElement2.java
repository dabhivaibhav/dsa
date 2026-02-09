package collection.stack;

import java.util.Arrays;

/*
Leetcode 503. Next Greater Element II

Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] is nums[0]), return the next greater
number for every element in nums. The next greater number of a number x is the first greater number to its traversing-order
next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, return -1 for this number.

Example 1:
Input: nums = [1,2,1]
Output: [2,-1,2]
Explanation: The first 1's next greater number is 2;
The number 2 can't find next greater number.
The second 1's next greater number needs to search circularly, which is also 2.

Example 2:
Input: nums = [1,2,3,4,3]
Output: [2,3,4,-1,4]

Constraints:
            1 <= nums.length <= 10^4
            -10^9 <= nums[i] <= 10^9
 */
public class NextGreaterElement2 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 1};
        System.out.println(Arrays.toString(nextGreaterElementBruteForce(nums)));
        nums = new int[]{1, 2, 3, 4, 3};
        System.out.println(Arrays.toString(nextGreaterElementBruteForce(nums)));
        nums = new int[]{1, 3, 2, 4};
        System.out.println(Arrays.toString(nextGreaterElementBruteForce(nums)));
        nums = new int[]{4, 1, 2};
        System.out.println(Arrays.toString(nextGreaterElementBruteForce(nums)));


    }

    /**
     * Method name:
     * nextGreaterElementBruteForce
     * <p>
     * What it does:
     * Finds the next greater element for every element in a circular array.
     * For each index i, it searches for the first element greater than nums[i]
     * while moving to the right in circular order.
     * If no such element exists, the result for that index is -1.
     * <p>
     * What makes this problem different:
     * Unlike the normal next greater element problem,
     * the array is circular.
     * This means that after reaching the last index,
     * the search continues from the beginning of the array.
     * <p>
     * How circular searching is handled:
     * Circular behavior is simulated using the modulo operator.
     * The expression (i + j) % n allows the index to wrap around
     * to the start of the array when it exceeds the last index.
     * <p>
     * Explanation of variables:
     * - n stores the length of the array.
     * - result array stores the next greater element for each index.
     * - i represents the current index whose next greater element is being found.
     * - j represents how far we move ahead from index i during the search.
     * - nextIndex represents the actual index in circular traversal.
     * <p>
     * Step by step explanation of the logic:
     * <p>
     * 1) Iterate through each index i of the array:
     * Each element is treated independently.
     * <p>
     * 2) Initialize result[i] to -1:
     * This is the default answer in case no greater element is found.
     * <p>
     * 3) Search the next n-1 elements circularly:
     * - Start from the next position after i.
     * - Use (i + j) % n to move forward and wrap around if needed.
     * - Compare nums[nextIndex] with nums[i].
     * <p>
     * 4) First greater element wins:
     * - As soon as an element greater than nums[i] is found,
     * store it in result[i] and break the loop.
     * - This ensures we find the nearest greater element in traversal order.
     * <p>
     * Example walkthrough:
     * nums = [1, 2, 1]
     * <p>
     * For index 0 (value 1):
     * - Check index 1 → value 2 (greater)
     * - result[0] = 2
     * <p>
     * For index 1 (value 2):
     * - Check index 2 → 1
     * - Check index 0 → 1
     * - No greater element found
     * - result[1] = -1
     * <p>
     * For index 2 (value 1):
     * - Check index 0 → 1
     * - Check index 1 → 2 (greater)
     * - result[2] = 2
     * <p>
     * Time complexity:
     * O(n^2), where n is the length of the array.
     * For each element, up to n - 1 elements may be checked.
     * <p>
     * Space complexity:
     * O(n) for the result array.
     * <p>
     * Interview takeaway:
     * This brute force solution directly follows the problem statement
     * and is easy to reason about.
     * In interviews, it is a good baseline approach.
     * After explaining this, you should mention that the time complexity
     * can be optimized to O(n) using a monotonic stack.
     */
    private static int[] nextGreaterElementBruteForce(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = -1;
            // Search the next n-1 elements circularly
            for (int j = 1; j < n; j++) {
                int nextIndex = (i + j) % n; // The "Rotation" trick
                if (nums[nextIndex] > nums[i]) {
                    result[i] = nums[nextIndex];
                    break;
                }
            }
        }
        return result;
    }
}
