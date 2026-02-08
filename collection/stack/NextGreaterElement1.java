package collection.stack;

import java.util.Arrays;

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
        System.out.println(Arrays.toString(nextGreaterElement(nums1, nums2)));
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
    private static int[] nextGreaterElement(int[] nums1, int[] nums2) {
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
}
