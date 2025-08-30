package array.easy_problems;

import java.util.Arrays;

/*
Write a Java program to find the second largest element in an array. If the second-largest element does not exist, return -1.
Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
            nums may contain duplicate elements.

In the optimal approach it is returning the lowest value of integer because the array can contain the negative values also.
So I can't take any other negative value as a default value for secondlargest element in those methods
 */
public class FindSecondLargestElement {

    public static void main(String[] args) {
        int[] nums = {3, 3};
        System.out.println("Optimal approach Second largest element in the array: " + bruteForceApproach(nums));

        System.out.println("Better approach Second largest element in the array: " + betterApproach(nums));

        System.out.println("Optimal approach Second largest element in the array: " + optimalApproach(nums));
    }

    /*
     * What I'm doing here (my “brute force” baseline):
     * I sort the array in-place (O(n log n)), then I scan from the end toward the left
     * and return the first value that is strictly smaller than the largest (the last element).
     * If everything is equal to the max, there is no second-largest, so I return -1.
     *
     * Why I’m okay with it:
     * - It’s simple to reason about and guarantees correctness.
     * - Time: O(n log n) because of sorting; Space: O(1) extra (sorts the same array).
     *
     * Caveat:
     * - This mutates the input array due to Arrays.sort; I’m fine with that here.
     */
    private static int bruteForceApproach(int[] nums) {

        if (nums == null || nums.length == 0) return -1;
        if (nums.length == 1) return -1;
        int n = nums.length;
        Arrays.sort(nums);
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] != nums[n - 1]) {
                return nums[i];
            }
        }
        return -1;
    }


    /*
     * What I’m doing here (two-pass linear scan):
     * Pass 1: find the largest value.
     * Pass 2: walk again and keep the best candidate strictly less than the largest.
     * If I never find a smaller distinct value, I return Integer.MIN_VALUE as my “not found” signal.
     *
     * Why I chose it:
     * - Still very straightforward, but now it’s linear.
     * - Time: O(n + n), Space: O(1).
     *
     * Note to reader (and future me):
     * - This method uses Integer.MIN_VALUE to represent “no second-largest”.
     *   That’s different from the -1 I return in the sorting version.
     *   I’m okay with that here, but we could standardize if needed.
     */
    private static int betterApproach(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        if (nums.length == 1) return -1;
        int n = nums.length;
        int largest = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] > largest) {
                largest = nums[i];
            }
        }
        int secondLargest = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (nums[i] != largest && nums[i] > secondLargest) {
                secondLargest = nums[i];
            }
        }
        return secondLargest;
    }


    /*
     * What I’m doing here (one-pass optimal):
     * I track two variables as I go: `largest` and `secondLargest`.
     * - If I see a value bigger than `largest`, I push `largest` down into `secondLargest`
     *   and update `largest` to the new value.
     * - Otherwise, if it’s strictly less than `largest` but bigger than `secondLargest`,
     *   I update `secondLargest`.
     * If I never see a second distinct value, `secondLargest` stays at Integer.MIN_VALUE.
     *
     * Why I like it:
     * - Single pass, constant space, and no sorting.
     * - Time: O(n), Space: O(1).
     *
     * Behavior note:
     * - Returns Integer.MIN_VALUE when no second-largest exists (e.g., [3,3]).
     *   Same “signal” behavior as the two-pass method above.
     */
    private static int optimalApproach(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        if (nums.length == 1) return -1;
        int n = nums.length;
        int largest = nums[0];
        int secondLargest = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (nums[i] > largest) {
                secondLargest = largest;
                largest = nums[i];
            } else if (nums[i] != largest && nums[i] > secondLargest) {
                secondLargest = nums[i];
            }
        }
        return secondLargest;
    }
}
