package array.medium_problems;


import java.util.Arrays;
import java.util.List;

import static array.medium_problems.GenerateAllPermutation.permutation;

/*
A permutation of an array of integers is an arrangement of its members into a sequence or linear order.

For example, for arr = [1,2,3], the following are all the permutations of arr:
[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1].

The next permutation of an array of integers is the next lexicographically greater permutation of its integers.
More formally, if all the permutations of the array are sorted in lexicographical order, then the next permutation of
that array is the permutation that follows it in the sorted order.

If such arrangement is not possible (i.e., the array is the last permutation), then rearrange it to the lowest possible
order (i.e., sorted in ascending order).
You must rearrange the numbers in-place and use only constant extra memory.

Examples:
Input: nums = [1,2,3]
Output: [1,3,2]
Explanation: The next permutation of [1,2,3] is [1,3,2].

Input: nums = [3,2,1]
Output: [1,2,3]
Explanation: [3,2,1] is the last permutation. So we return the first: [1,2,3].
 */

public class NextPermutation {

    public static void main(String[] args) {
        int[] nums = {3, 2, 1};
        permutationBruteForceApproach(nums);
    }

    /**
     * The goal is to find the next lexicographically greater permutation of given numbers.
     * Time Complexity: O(n! * n) where n is the length of input array
     * O(n!) for generating all permutations
     * O(n) for finding current permutation and copying next permutation
     * Space Complexity: O(n!) to store all permutations
     * Finds the next permutation using brute force approach:
     * 1. Generate all possible permutations using helper method
     * 2. Find the index of current permutation in the list
     * 3. Return the next permutation (or first if current is last)
     *
     * @param nums Input array to find next permutation for
     */
    private static void permutationBruteForceApproach(int[] nums) {
        // Generate all possible permutations of the input array
        List<List<Integer>> arr = permutation(nums);

        // Find the index of current permutation in the generated list
        int currentIndex = -1;
        for (int i = 0; i < arr.size(); i++) {
            boolean match = true;
            for (int j = 0; j < nums.length; j++) {
                if (arr.get(i).get(j) != nums[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                currentIndex = i;
                break;
            }
        }

        // Get next permutation, wrapping around to first if current is last
        List<Integer> nextPerm = arr.get((currentIndex + 1) % arr.size());

        // Copy next permutation back to input array
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nextPerm.get(i);
        }

        System.out.println(Arrays.toString(nums));
    }


}
