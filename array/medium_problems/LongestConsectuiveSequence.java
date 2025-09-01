package array.medium_problems;

import java.util.Arrays;
import java.util.HashSet;

/*
Given an array nums of n integers. Return the length of the longest sequence of consecutive integers. The integers in
this sequence can appear in any order.

Examples:
Input: nums = [100, 4, 200, 1, 3, 2]
Output: 4
Explanation:
The longest sequence of consecutive elements in the array is [1, 2, 3, 4], which has a length of 4. This sequence can be
formed regardless of the initial order of the elements in the array.

Input: nums = [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]
Output: 9
Explanation:
The longest sequence of consecutive elements in the array is [0, 1, 2, 3, 4, 5, 6, 7, 8], which has a length of 9.

Constraints:
             1 <= nums.length <= 10^5
             -10^9 <= nums[i] <= 10^9
 */


public class LongestConsectuiveSequence {

    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 1, 3, 2};
        int[] nums1 = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};

        longestConsecutiveSequenceBruteForceApproach(nums);
        longestConsecutiveSequenceBruteForceApproach(nums1);
        longestConsecutiveSequenceOptimizedApproach(nums);
    }

    /**
     * A brute force approach to find the longest consecutive sequence in an array.
     * This method first sorts the array and then looks for consecutive elements.
     * <p>
     * Algorithm:
     * 1. Handle edge cases (null or empty array)
     * 2. Sort the array to make consecutive numbers adjacent
     * 3. Traverse array from right to left, counting consecutive numbers
     * 4. Keep track of maximum sequence length found
     * <p>
     * Time Complexity: O(n log n) due to sorting
     * Space Complexity: O(1) as we only use constant extra space
     *
     * @param nums Input array of integers
     */

    private static void longestConsecutiveSequenceBruteForceApproach(int[] nums) {
        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform operation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element, operation has no effect.");
        }
        Arrays.sort(nums);
        int count = 0;
        int currentMax = 0;
        int i = nums.length - 1;
        while (i >= 0) {
            if (i > 0 && nums[i] == nums[i - 1] + 1) {
                currentMax++;
            } else if (i > 0 && nums[i] != nums[i - 1]) {
                currentMax = 0;
            }
            count = Math.max(count, currentMax);
            i--;
        }
        System.out.println("Longest consecutive sequence: " + (count + 1));
    }


    /**
     * An optimized approach to find the longest consecutive sequence in an array using HashSet.
     * This method uses a HashSet to achieve O(n) time complexity by avoiding sorting.
     * Algorithm:
     * 1. Add all numbers to HashSet for O(1) lookups
     * 2. For each number, check if it's the start of a sequence (no num-1 exists). For example, if the hashset has
     * following elements like 102,101,100 and if you are starting the loop from 102 and you found 101 exist that means
     * 102 is not the starting point so move on to the next elements until you found the starting point
     * 3. If it is, count consecutive numbers following it
     * 4. Keep track of the longest sequence found
     * Time Complexity: O(n) as we traverse the array only twice
     * Space Complexity: O(n) for storing numbers in HashSet
     *
     * @param nums Input array of integers
     */
    private static void longestConsecutiveSequenceOptimizedApproach(int[] nums) {

        HashSet<Integer> set = new HashSet<>();
        int longest = 0;
        for (int num : nums) {
            set.add(num);
        }

        for (int num : set) {
            if (!set.contains(num - 1)) {
                int counter = 1;
                int currentNum = num;
                while (set.contains(currentNum + 1)) {
                    currentNum += 1;
                    counter += 1;
                }
                longest = Math.max(longest, counter);

            }
        }
        System.out.println("Longest consecutive sequence: " + (longest));
    }
}
