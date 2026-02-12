package collection.stack;

import java.util.Stack;

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
    private static long sumOfSubArrayRangesBruteForce(int[] nums) {

        if (nums.length == 0) return 0;
        if (nums.length == 1) return 0;
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
        return sum;
    }

    /**
     * SumOfSubArrayRangesOptimized:
     * <p>
     * Instead of computing the range for every subarray directly,
     * this solution breaks the problem into two independent parts:
     * - Sum of subarray maximums
     * - Sum of subarray minimums
     * <p>
     * The final answer is:
     * sum of subarray maximums minus sum of subarray minimums
     * <p>
     * Core insight:
     * Every element contributes to many subarrays as:
     * - the minimum value
     * - the maximum value
     * <p>
     * If we can count how many subarrays treat nums[i] as the minimum
     * and how many treat nums[i] as the maximum,
     * we can compute its total contribution without enumerating subarrays.
     * <p>
     * High-level approach:
     * 1. Compute sum of subarray minimums using monotonic increasing stacks.
     * 2. Compute sum of subarray maximums using monotonic decreasing stacks.
     * 3. Subtract the two results to get the sum of subarray ranges.
     * <p>
     * Why this works:
     * For any element nums[i]:
     * - It contributes as a minimum in all subarrays where it is the smallest value.
     * - It contributes as a maximum in all subarrays where it is the largest value.
     * <p>
     * The number of such subarrays is determined by how far the element
     * can expand to the left and right before a smaller or larger element blocks it.
     * <p>
     * Key technique used:
     * Index-based monotonic stacks.
     * <p>
     * For each element, we compute:
     * - Distance to previous smaller or larger element
     * - Distance to next smaller or larger element
     * <p>
     * These distances tell us how many subarrays include nums[i]
     * as the minimum or maximum.
     * <p>
     * Method overview:
     * <p>
     * sumOfSubArrayRangesOptimized:
     * - Orchestrates the solution.
     * - Returns sumSubarrayMaxs(nums) - sumSubarrayMins(nums).
     * <p>
     * sumSubarrayMins:
     * - Uses a monotonic increasing stack.
     * - Finds how many subarrays treat each element as the minimum.
     * - Uses two arrays:
     * left[i]  = distance to previous smaller or equal element
     * right[i] = distance to next smaller element
     * <p>
     * sumSubarrayMaxs:
     * - Uses a monotonic decreasing stack.
     * - Finds how many subarrays treat each element as the maximum.
     * - Uses two arrays:
     * left[i]  = distance to previous larger or equal element
     * right[i] = distance to next larger element
     * <p>
     * Tie-breaking rules:
     * - For minimums:
     * one side uses strictly smaller, the other uses smaller or equal
     * - For maximums:
     * one side uses strictly larger, the other uses larger or equal
     * <p>
     * These rules prevent double counting when duplicate values exist.
     * <p>
     * Example intuition:
     * nums = [1, 2, 3]
     * <p>
     * Subarray minimum contributions:
     * 1 contributes to 3 subarrays
     * 2 contributes to 2 subarrays
     * 3 contributes to 1 subarray
     * <p>
     * Subarray maximum contributions:
     * 1 contributes to 1 subarray
     * 2 contributes to 2 subarrays
     * 3 contributes to 3 subarrays
     * <p>
     * Range sum = max contributions minus min contributions
     * <p>
     * Complexity:
     * Time: O(n)
     * Each element is pushed and popped at most once per stack.
     * <p>
     * Space: O(n)
     * Extra arrays and stacks are used for distance calculations.
     * <p>
     * Interview takeaway:
     * This solution demonstrates advanced problem-solving skills:
     * - Contribution technique
     * - Monotonic stack mastery
     * - Careful handling of duplicates
     * <p>
     * Once this pattern is understood, it applies to:
     * - Sum of subarray minimums
     * - Sum of subarray maximums
     * - Histogram problems
     * - Range and contribution-based problems
     * <p>
     * This is a high-signal solution often expected in senior-level interviews.
     */

    private static long sumOfSubArrayRangesOptimized(int[] nums) {
        return sumSubarrayMaxs(nums) - sumSubarrayMins(nums);
    }

    private static long sumSubarrayMins(int[] nums) {
        int n = nums.length;
        long sum = 0;
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();

        // Finding Distance to Previous Smaller (or equal)
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }

        stack.clear();

        // Finding Distance to Next Smaller
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
            stack.push(i);
        }

        for (int i = 0; i < n; i++) {
            sum += (long) nums[i] * left[i] * right[i];
        }
        return sum;
    }

    private static long sumSubarrayMaxs(int[] nums) {
        int n = nums.length;
        long sum = 0;
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();

        // Finding Distance to Previous Larger (or equal)
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }

        stack.clear();

        // Finding Distance to Next Larger
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
            stack.push(i);
        }

        for (int i = 0; i < n; i++) {
            sum += (long) nums[i] * left[i] * right[i];
        }
        return sum;
    }

}
