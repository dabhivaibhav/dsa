package collection.stack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
Leetcode 496. Next Greater Element I

The next greater element of some element x in an array is the first greater element that is to the right of x in the same array.
You are given two distinct 0-indexed integer arrays nums1 and nums2, where nums1 is a subset of nums2.
For each 0 <= i < nums1.length, find the index j such that nums1[i] == nums2[j] and determine the next greater element of
nums2[j] in nums2. If there is no next greater element, then the answer for this query is -1.
Return an array ans of length nums1.length such that ans[i] is the next greater element as described above.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
Output: [-1,3,-1]
Explanation: The next greater element for each value of nums1 is as follows:
- 4 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
- 1 is underlined in nums2 = [1,3,4,2]. The next greater element is 3.
- 2 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.

Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4]
Output: [3,-1]
Explanation: The next greater element for each value of nums1 is as follows:
- 2 is underlined in nums2 = [1,2,3,4]. The next greater element is 3.
- 4 is underlined in nums2 = [1,2,3,4]. There is no next greater element, so the answer is -1.

Constraints:
            1 <= nums1.length <= nums2.length <= 1000
            0 <= nums1[i], nums2[i] <= 10^4
            All integers in nums1 and nums2 are unique.
            All the integers of nums1 also appear in nums2.

Follow up: Could you find an O(nums1.length + nums2.length) solution?
 */
public class NextGreaterElement1 {

    public static void main(String[] args) {
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};
        System.out.println(Arrays.toString(findNextGreaterElementBruteForce(nums1, nums2)));
        System.out.println(Arrays.toString(findNextGreaterElementOptimized(nums1, nums2)));
    }


    /**
     * Method name:
     * nextGreaterElement
     * <p>
     * What it does:
     * For each element in nums1, this method finds the next greater element
     * to its right in nums2.
     * If no such element exists, it assigns -1 as the result for that element.
     * <p>
     * How the problem is interpreted:
     * - nums1 is a subset of nums2.
     * - Every value in nums1 appears exactly once in nums2.
     * - For a given value x in nums1, we first locate x in nums2.
     * - Then we look to the right of that position in nums2
     * and find the first element that is strictly greater than x.
     * <p>
     * Explanation of the approach:
     * This is a brute force solution that uses nested loops.
     * The method solves the problem directly by simulating
     * what the problem statement asks, step by step.
     * <p>
     * Explanation of variables:
     * - result array stores the final answers for each element in nums1.
     * - currentVal represents the current element from nums1
     * for which we are finding the next greater element.
     * - foundIndex stores the index where currentVal appears in nums2.
     * - nextGreater stores the next greater element found to the right,
     * or remains -1 if none exists.
     * <p>
     * Step by step explanation of the logic:
     * <p>
     * 1) Iterate through each element of nums1:
     * For every element in nums1, we independently find its answer.
     * <p>
     * 2) Find the index of nums1[i] in nums2:
     * - Loop through nums2 until the matching value is found.
     * - Store the index where the match occurs.
     * - This tells us where to start searching for the next greater element.
     * <p>
     * 3) Search to the right of the found index:
     * - Start scanning nums2 from foundIndex + 1.
     * - Compare each value with currentVal.
     * - The first value that is greater than currentVal
     * is taken as the next greater element.
     * - If no such value is found, nextGreater remains -1.
     * <p>
     * 4) Store the result:
     * - Assign the value of nextGreater to the result array
     * at the corresponding index.
     * <p>
     * Example walkthrough:
     * nums1 = [4, 1, 2]
     * nums2 = [1, 3, 4, 2]
     * <p>
     * For 4:
     * - Found at index 2 in nums2
     * - No element greater than 4 to the right
     * - Result = -1
     * <p>
     * For 1:
     * - Found at index 0 in nums2
     * - Next greater element is 3
     * <p>
     * For 2:
     * - Found at index 3 in nums2
     * - No elements to the right
     * - Result = -1
     * <p>
     * Time complexity:
     * O(n1 * n2), where n1 is nums1.length and n2 is nums2.length.
     * For each element in nums1, we may scan nums2 twice.
     * <p>
     * Space complexity:
     * O(n1) for the result array.
     * <p>
     * Interview takeaway:
     * This solution is easy to understand and implement.
     * In interviews, it is a good starting point.
     * After presenting this, you should mention that the time complexity
     * can be optimized to O(n1 + n2) using a stack and a hashmap.
     */
    private static int[] findNextGreaterElementBruteForce(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            int currentVal = nums1[i];
            int foundIndex = -1;

            // Finding the index of nums1[i] in nums2
            for (int j = 0; j < nums2.length; j++) {
                if (nums2[j] == currentVal) {
                    foundIndex = j;
                    break;
                }
            }

            // Search to the right of foundIndex for something bigger
            int nextGreater = -1; // Default if not found
            for (int k = foundIndex + 1; k < nums2.length; k++) {
                if (nums2[k] > currentVal) {
                    nextGreater = nums2[k];
                    break;
                }
            }

            result[i] = nextGreater;
        }
        return result;
    }

    /**
     * findNextGreaterElementOptimized
     * <p>
     * What it does:
     * Finds the next greater element for each value in nums1 by preprocessing nums2.
     * For every element x in nums1, the method returns the first element greater than x
     * that appears to the right of x in nums2.
     * If no such element exists, the result is -1.
     * <p>
     * Key idea behind the optimization:
     * Instead of searching to the right for every element in nums1,
     * this method computes the next greater element for all elements in nums2 in one pass.
     * The results are stored in a map for constant time lookup later.
     * <p>
     * Why a stack is used:
     * A monotonic decreasing stack is used to track elements from nums2
     * whose next greater element has not yet been found.
     * The stack ensures that elements are processed in an efficient order.
     * <p>
     * Explanation of data structures:
     * - map stores the next greater element for each number in nums2.
     * Key is the number, value is its next greater element.
     * - stack stores numbers in decreasing order.
     * Elements wait in the stack until a greater number appears.
     * <p>
     * Step by step explanation of processing nums2:
     * - Traverse nums2 from left to right.
     * - For each number:
     * - While the stack is not empty and the current number is greater
     * than the element on top of the stack:
     * - Pop the stack element.
     * - Record that the current number is the next greater element
     * for the popped value.
     * - Push the current number onto the stack.
     * <p>
     * Why this works:
     * The first number that pops an element from the stack
     * is the first greater number to its right.
     * Since each element is pushed and popped at most once,
     * the total processing time is linear.
     * <p>
     * Building the result for nums1:
     * - Iterate through nums1.
     * - For each value, fetch its next greater element from the map.
     * - If the value is not present in the map, return -1.
     * <p>
     * Example:
     * nums1 = [4, 1, 2]
     * nums2 = [1, 3, 4, 2]
     * <p>
     * Processing nums2:
     * - 1 waits in stack
     * - 3 is greater than 1, so map[1] = 3
     * - 4 is greater than 3, so map[3] = 4
     * - 2 waits but has no greater element
     * <p>
     * Final map:
     * 1 -> 3
     * 3 -> 4
     * <p>
     * Result for nums1:
     * 4 -> -1
     * 1 -> 3
     * 2 -> -1
     * <p>
     * Time complexity:
     * O(nums1.length + nums2.length).
     * Each element of nums2 is pushed and popped once,
     * and each lookup for nums1 is constant time.
     * <p>
     * Space complexity:
     * O(nums2.length) for the stack and map.
     * <p>
     * Interview takeaway:
     * This optimized solution uses a monotonic stack to reduce the time complexity
     * from quadratic to linear by preprocessing the next greater elements in nums2.
     * The map allows quick lookup for nums1 elements.
     */

    private static int[] findNextGreaterElementOptimized(int[] nums1, int[] nums2) {
        // Map to store the 'Next Greater' relationship for every number in nums2
        Map<Integer, Integer> map = new HashMap<>();
        // Monotonic stack (will stay in descending order)
        Stack<Integer> stack = new Stack<>();

        // Process nums2 to find NGE for all elements
        for (int num : nums2) {
            // While the current number is bigger than the top of the stack,
            // we've found the "Next Greater" for the number on the stack.
            while (!stack.isEmpty() && num > stack.peek()) {
                map.put(stack.pop(), num);
            }
            // Add current number to stack to wait for its own next greater
            stack.push(num);
        }

        // Build the result for nums1
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            // If the map has a value, use it; otherwise, use -1
            result[i] = map.getOrDefault(nums1[i], -1);
        }
        return result;
    }
}
