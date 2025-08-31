package array.medium_problems;


import java.util.ArrayList;
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
        int[] nums = {2, 1, 5, 4, 3, 0};
        int[] nums1 = {2, 1, 5, 4, 3, 0, 0};
        int[] nums2 = {2, 1, 5, 4, 3, 0, 0};
        System.out.print("Brute Force Approach with unique elements:");
        permutationBruteForceApproachForUnique(nums);
        System.out.print("\nOptimal Approach:");
        nextPermutationOptimalApproach(nums1);
        System.out.print("\nBrute Force Approach with duplicates:");
        permutationBruteForceApproach(nums2);
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
     * <p>
     * NOTE: THIS METHOD DOES NOT WORK FOR ARRAYS WITH DUPLICATES
     *
     * @param nums Input array to find next permutation for
     */
    private static void permutationBruteForceApproachForUnique(int[] nums) {
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


    /**
     * Finds the next lexicographically greater permutation in-place using optimal approach.
     * Time Complexity: O(n) where n is length of array, Actual time complexity is O(3n) as we are using 3 for loops
     * which almost runs upto the length of array.
     * Space Complexity: O(1) as we only use constant extra space
     * Algorithm steps:
     * 1. Find the first pair from right where nums[i] < nums[i+1]. Mark this index.
     * Index is set to -1 if no such pair exists (array is in descending order).
     * 2. If index is -1, array is last permutation, so reverse entire array.
     * 3. Otherwise, find smallest number greater than nums[index] on its right.
     * 4. Swap these numbers.
     * 5. Reverse the subarray after index to get smallest arrangement.
     *
     * @param nums Input array to find next permutation for
     */
    private static void nextPermutationOptimalApproach(int[] nums) {
        int index = -1;
        int n = nums.length;

        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            reverse(0, n - 1, nums);
            return;
        }

        for (int i = n - 1; i > index; i--) {
            if (nums[index] < nums[i]) {
                int temp = nums[index];
                nums[index] = nums[i];
                nums[i] = temp;
                break;
            }
        }
        int begin = index + 1;
        int end = n - 1;
        reverse(begin, end, nums);
        System.out.println(Arrays.toString(nums));
    }

    private static void reverse(int begin, int end, int[] nums) {
        for (int i = begin, j = end; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    /**
     * Finds the next lexicographically greater permutation using brute force approach.
     * This method handles arrays with duplicate elements correctly.
     * Time Complexity: O(n! * n) where n is length of array
     * Space Complexity: O(n!) to store all permutations
     * Algorithm steps:
     * 1. Generate all unique permutations in lexicographic order
     * 2. Find index of current permutation in generated list
     * 3. Return next permutation (or first if current is last)
     * 4. Copy next permutation back to input array
     *
     * @param nums Input array to find next permutation for
     */
    private static void permutationBruteForceApproach(int[] nums) {
        // Generate all possible permutations of the input array (unique + lexicographic)
        List<List<Integer>> arr = permutationsUniqueLex(nums.clone());

        // Find the index of current permutation in the generated list
        int currentIndex = indexOfPermutation(arr, nums);

        // Get next permutation, wrapping around to first if current is last
        List<Integer> nextPerm = arr.get((currentIndex + 1) % arr.size());

        // Copy next permutation back to input array
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nextPerm.get(i);
        }

        System.out.println(Arrays.toString(nums));
    }


    // Generate unique permutations in lexicographic order (handles duplicates)
    private static List<List<Integer>> permutationsUniqueLex(int[] a) {
        Arrays.sort(a); // crucial for lexicographic order + duplicate skipping
        List<List<Integer>> ans = new ArrayList<>();
        boolean[] frequency = new boolean[a.length]; // keep your naming style
        List<Integer> ds = new ArrayList<>();
        nextPermutationLex(a, ds, ans, frequency);
        return ans;
    }

    // backtracking that skips duplicates and emits in lexicographic order
    private static void nextPermutationLex(int[] nums, List<Integer> ds, List<List<Integer>> ans, boolean[] frequency) {
        if (ds.size() == nums.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (frequency[i]) continue;
            // skip equal neighbors when the previous identical hasn't been used
            if (i > 0 && nums[i] == nums[i - 1] && !frequency[i - 1]) continue;

            frequency[i] = true;
            ds.add(nums[i]);
            nextPermutationLex(nums, ds, ans, frequency);
            frequency[i] = false;
            ds.remove(ds.size() - 1);
        }
    }

    // find index of current permutation in the list
    private static int indexOfPermutation(List<List<Integer>> arr, int[] nums) {
        for (int i = 0; i < arr.size(); i++) {
            boolean match = true;
            for (int j = 0; j < nums.length; j++) {
                if (!arr.get(i).get(j).equals(nums[j])) {
                    match = false;
                    break;
                }
            }
            if (match) return i;
        }
        throw new IllegalStateException("Current permutation not found");
    }
}
