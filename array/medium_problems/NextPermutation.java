package array.medium_problems;


import java.util.ArrayList;
import java.util.List;

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

        int[] nums = {1, 2, 3};
        System.out.println(permutation(nums));
    }


    public static List<List<Integer>> permutation(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        boolean[] frequency = new boolean[nums.length];
        nextPermutation(nums, ds, ans, frequency);
        return ans;


    }

    private static void nextPermutation(int[] nums, List<Integer> ds, List<List<Integer>> ans, boolean[] frequency) {
        if (ds.size() == nums.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!frequency[i]) {
                frequency[i] = true;
                ds.add(nums[i]);
                nextPermutation(nums, ds, ans, frequency);
                frequency[i] = false;
                ds.remove(ds.size() - 1);
            }

        }
    }

}
