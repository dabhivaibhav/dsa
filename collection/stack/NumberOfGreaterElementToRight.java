package collection.stack;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Number of Greater Elements to the Right

You are given an integer array arr[] of length n and an array of queries indices[] containing positions in arr.
For each query i, determine the number of elements in arr that are strictly greater than arr[indices[i]] and appear to
its right (after position indices[i]). Return an array of answers where the jth value corresponds to the result for indices[j].

Example 1
Input: arr = [3, 4, 2, 7, 5, 8, 10, 6], indices = [0, 5]
Output: [6, 1]
Explanation:
For index 0 → arr[0] = 3, elements greater than 3 to its right are [4, 7, 5, 8, 10, 6] → count = 6.
For index 5 → arr[5] = 8, greater elements to the right are [10] → count = 1.

Example 2
Input: arr = [1, 2, 3, 4, 1], indices = [0, 3]
Output: [3, 0]
Explanation:
For index 0 → arr[0] = 1, greater elements to the right are [2, 3, 4] → count = 3.
For index 3 → arr[3] = 4, no elements greater than 4 exist to the right → count = 0.

Constraints:
            1 ≤ n ≤ 10^4
            1 ≤ arr[i] ≤ 10^5
            1 ≤ queries ≤ 100
            0 ≤ indices[i] ≤ n - 1
 */
public class NumberOfGreaterElementToRight {

    public static void main(String[] args) {
        int[] nums = {3, 4, 2, 7, 5, 8, 10, 6};
        int[] indices = {0, 5};
        System.out.println(countGreaterElementBruteforce(nums, indices));
    }

    /**
     * countGreaterElementBruteforce
     * <p>
     * What it does:
     * For each index given in the indices array, this method counts how many elements
     * in the original array nums are strictly greater than nums[index] and appear
     * to the right of that index.
     * <p>
     * In simple words:
     * - Pick an index from indices
     * - Look at the value stored at that index in nums
     * - Count how many bigger numbers exist after that position
     * <p>
     * Why it works:
     * The problem only asks for elements that appear to the right of a given index.
     * By directly scanning from index + 1 to the end of the array and comparing values,
     * we are guaranteed to find all valid greater elements without missing any.
     * <p>
     * Explanation of the approach step by step:
     * - A result list is created to store answers for each query index.
     * - The outer loop iterates over the indices array.
     * Each element in indices represents a position in nums that we need to query.
     * - For each query index:
     * - targetIndex stores the position in nums.
     * - targetValue stores nums[targetIndex], the value we are comparing against.
     * - A counter is initialized to zero.
     * - The inner loop starts from targetIndex + 1 and goes till the end of nums.
     * This ensures we only look at elements to the right.
     * - If nums[j] is strictly greater than targetValue, the counter is incremented.
     * - After scanning all elements to the right, the counter is added to the result list.
     * - This process repeats for every query index.
     * <p>
     * Example walkthrough:
     * nums = [3, 4, 2, 7, 5, 8, 10, 6]
     * indices = [0, 5]
     * <p>
     * For index 0:
     * - targetValue = 3
     * - Elements to the right: [4, 2, 7, 5, 8, 10, 6]
     * - Greater than 3: [4, 7, 5, 8, 10, 6]
     * - Count = 6
     * <p>
     * For index 5:
     * - targetValue = 8
     * - Elements to the right: [10, 6]
     * - Greater than 8: [10]
     * - Count = 1
     * <p>
     * Final result = [6, 1]
     * <p>
     * Important details:
     * - The comparison is strictly greater, not greater than or equal.
     * - Only elements to the right are considered.
     * - The order of answers matches the order of indices.
     * - This approach is straightforward and easy to reason about.
     * <p>
     * Complexity:
     * Time: O(q * n)
     * where q is the number of query indices and n is the size of nums.
     * In the worst case, for each query we scan the entire remaining array.
     * <p>
     * Space: O(1) auxiliary space
     * excluding the space used for the output list.
     * <p>
     * Interview takeaway:
     * This brute force solution is ideal to explain first in interviews
     * because it clearly matches the problem statement.
     * Once this is established, it naturally leads to discussing optimizations
     * using advanced data structures or preprocessing techniques.
     */
    private static List<Integer> countGreaterElementBruteforce(int[] nums, int[] indices) {

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < indices.length; i++) {
            int count = 0;
            int targetIndex = indices[i];
            int targetValue = nums[targetIndex];
            for (int j = targetIndex + 1; j < nums.length; j++) {
                if (nums[j] > targetValue) {
                    count++;
                }
            }
            result.add(count);
        }
        return result;
    }
}
