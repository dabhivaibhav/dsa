package array;

/*
Given an array nums of n integers, return true if the array nums is sorted in non-decreasing order or else false.
Constraints:
            1 <= n <= 100
            1 <= nums[i] <= 100
 */
public class CheckArraySorted {

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 4}; // try {3,3,6,1} or { -5 } to see messages vs results

        System.out.println("Is the array sorted? ");
        System.out.println(isArraySorted(nums));
    }

    /*
    I think the optimal approach is to do a single pass through the array, comparing each element to the one before it.
    If I find any element that is less than the one before it, I can immediately return false.
    If I finish the pass without finding any such element, I return true.
    Time complexity: O(n)
    Space complexity: O(1)
     */
    private static boolean isArraySorted(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        if (nums.length == 1) return true;

        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
