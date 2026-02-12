package collection.stack;

/*
Leetcode 2104. Sum of Subarray Ranges

You are given an integer array nums. The range of a subarray of nums is the difference between the largest and
smallest element in the subarray. Return the sum of all subarray ranges of nums. A subarray is a contiguous non-empty
sequence of elements within an array.

Example 1:
Input: nums = [1,2,3]
Output: 4
Explanation: The 6 subarrays of nums are the following:
[1], range = largest - smallest = 1 - 1 = 0
[2], range = 2 - 2 = 0
[3], range = 3 - 3 = 0
[1,2], range = 2 - 1 = 1
[2,3], range = 3 - 2 = 1
[1,2,3], range = 3 - 1 = 2
So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.

Example 2:
Input: nums = [1,3,3]
Output: 4
Explanation: The 6 subarrays of nums are the following:
[1], range = largest - smallest = 1 - 1 = 0
[3], range = 3 - 3 = 0
[3], range = 3 - 3 = 0
[1,3], range = 3 - 1 = 2
[3,3], range = 3 - 3 = 0
[1,3,3], range = 3 - 1 = 2
So the sum of all ranges is 0 + 0 + 0 + 2 + 0 + 2 = 4.

Example 3:
Input: nums = [4,-2,-3,4,1]
Output: 59
Explanation: The sum of all subarray ranges of nums is 59.


Constraints:
            1 <= nums.length <= 1000
            -10^9 <= nums[i] <= 10^9

Follow-up: Could you find a solution with O(n) time complexity?
 */
public class SumOfSubArrayRanges {
    public static void main(String[] args) {
        int[] nums = {4, -2, -3, 4, 1};
        System.out.println(sumOfSubArrayRangesBruteForce(nums));

        nums = new int[]{1, 2, 3};
        System.out.println(sumOfSubArrayRangesBruteForce(nums));

        nums = new int[]{1, 3, 3};
        System.out.println(sumOfSubArrayRangesBruteForce(nums));
    }

    /*
    Thought Process:

    The most brute force solution I could think of was to generate all possible subarrays
    and calculate their ranges directly.

    To do this, I decided to use two nested loops.
    The outer loop fixes the starting index of the subarray.
    The inner loop extends the subarray to the right, one element at a time.

    While extending the subarray, I keep track of two variables:
    - min: the minimum element seen so far in the current subarray
    - max: the maximum element seen so far in the current subarray

    At each step of the inner loop, I update min and max using the current element,
    then compute the range of the current subarray as (max - min),
    and add this value to a running sum.

    After all possible subarrays are processed, the final sum is returned.

    This approach is simple, intuitive, and directly matches the problem statement,
    but it has a quadratic time complexity because it explicitly checks every subarray.
    */

    /**
     * sumOfSubArrayRangesBruteForce
     * <p>
     * What it does:
     * Calculates the sum of ranges of all possible contiguous subarrays.
     * The range of a subarray is defined as the difference between
     * its maximum and minimum elements.
     * <p>
     * Core idea:
     * Every contiguous subarray has exactly one minimum and one maximum.
     * If we generate all subarrays and track these two values,
     * we can compute each subarray’s range and accumulate the total sum.
     * <p>
     * Why this approach works:
     * - A subarray is uniquely defined by its start index i and end index j
     * - By fixing the start index and extending the end index,
     * all possible subarrays are covered
     * - The minimum and maximum can be updated incrementally
     * instead of being recomputed from scratch
     * <p>
     * Explanation of the approach step by step:
     * - A variable sum is used to store the final result.
     * - The outer loop fixes the starting index i of the subarray.
     * - For each i:
     * - min and max are initialized to nums[i]
     * - The inner loop extends the subarray from index i to j.
     * - At each step:
     * - min is updated using Math.min(min, nums[j])
     * - max is updated using Math.max(max, nums[j])
     * - The range (max - min) is added to sum
     * <p>
     * Example walkthrough:
     * nums = [1, 2, 3]
     * <p>
     * Subarrays and ranges:
     * [1]       -> range = 0
     * [1,2]     -> range = 1
     * [1,2,3]   -> range = 2
     * [2]       -> range = 0
     * [2,3]     -> range = 1
     * [3]       -> range = 0
     * <p>
     * Total sum = 0 + 1 + 2 + 0 + 1 + 0 = 4
     * <p>
     * Important details:
     * - Only contiguous subarrays are considered
     * - min and max are updated dynamically for efficiency
     * - long is used for sum to avoid overflow during accumulation
     * <p>
     * Complexity:
     * Time: O(n^2)
     * Two nested loops generate all possible subarrays.
     * <p>
     * Space: O(1)
     * Only constant extra variables are used.
     * <p>
     * Interview takeaway:
     * This brute force solution is the correct starting point in interviews.
     * It clearly demonstrates understanding of subarrays and range computation.
     * From here, the natural optimization is to use monotonic stacks
     * and contribution technique to achieve O(n) time complexity.
     */
    private static int sumOfSubArrayRangesBruteForce(int[] nums) {

        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            long min = nums[i];
            long max = nums[i];
            for (int j = i; j < nums.length; j++) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                sum += max - min;
            }
        }
        return (int) sum;
    }
}
