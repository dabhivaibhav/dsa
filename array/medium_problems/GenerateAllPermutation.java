package array.medium_problems;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
 */
public class GenerateAllPermutation {
    public static void main(String[] args) {

        int[] nums = {3, 1, 2};
        System.out.println(permutation(nums));
    }


    public static List<List<Integer>> permutation(int[] nums) {
        int[] a = nums.clone();     // do NOT mutate caller’s `nums`
        Arrays.sort(a);             // safe to sort the clone
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        boolean[] used = new boolean[a.length];
        nextPermutation(a, ds, ans, used);
        return ans;


    }

    public static void nextPermutation(int[] nums, List<Integer> ds, List<List<Integer>> ans, boolean[] frequency) {
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
