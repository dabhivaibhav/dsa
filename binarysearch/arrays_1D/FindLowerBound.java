package binarysearch.arrays_1D;

/*
Find lower bound

Given a sorted array of nums and an integer x, write a program to find the lower bound of x.
The lower bound algorithm finds the first and smallest index in a sorted array where the value at that index is greater
than or equal to a given key i.e. x. If no such index is found, return the size of the array.

Examples:
Input : nums= [1,2,2,3], x = 2
Output:1
Explanation: Index 1 is the smallest index such that arr[1] >= x.

Input : nums= [3,5,8,15,19], x = 9
Output: 3
Explanation: Index 3 is the smallest index such that arr[3] >= x.

Constraints:
          1 <= nums.length <= 10^5
          -10^5 < nums[i], x < 10^5
          nums is sorted in ascending order.
 */
public class FindLowerBound {

    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 4;
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 2;
        binarySearchLowerBoundBruteForce(nums, target);
        binarysearchLowerOptimal(nums, 0, nums.length - 1, target);
    }


    private static void binarySearchLowerBoundBruteForce(int[] nums, int target){
        for(int i=0; i<nums.length; i++){
            if(nums[i]>=target){
                System.out.println("Lower bound of " + target + " is " + i);
                return;
            }
        }
    }

    /**
     * binarysearchLowerBoundBruteforce
     * <p>
     * What it does:
     * This method finds the **lower bound** of a given target in a sorted array using
     * :contentReference[oaicite:0]{index=0}.
     * The **lower bound** is defined as the **first index `i`** where `arr[i] >= target`.
     * If all elements are smaller than the target, it returns `arr.length`.
     * <p>
     * Why it works:
     * - Because the array is sorted, all elements before the correct position are < target
     *   and all elements from the correct position onward are >= target.
     * - By checking the mid element and adjusting the search boundaries (`low` and `high`),
     *   we eliminate half the search space at each step.
     * - When `arr[mid] >= target`, we store `mid` as a possible answer and continue
     *   searching left (`high = mid - 1`) to find if there's an even smaller index
     *   satisfying the condition.
     * - When `arr[mid] < target`, we move right (`low = mid + 1`).
     * <p>
     * Example:
     * arr = [-1, 0, 3, 5, 9, 12], target = 4
     * - mid=2 → arr[2]=3 < 4 → move right (low=3)
     * - mid=3 → arr[3]=5 >= 4 → ans=3, move left (high=2)
     * loop ends → answer = 3 (index of first element >= 4)
     * <p>
     * Edge cases:
     * - If `target` is smaller than all elements, answer = 0.
     * - If `target` is larger than all elements, answer = arr.length.
     * <p>
     * Complexity:
     * Time:  O(log n) — halves the search space every step
     * Space: O(1) — only a few variables used
     */
    private static void binarysearchLowerOptimal(int[] arr, int low, int high, int target) {
        int ans = arr.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] >= target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println("Lower bound of " + target + " is " + ans);
    }
}
