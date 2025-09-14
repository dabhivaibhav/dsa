package binarysearch;

/*
Floor and Ceil in Sorted Array
Given a sorted array nums and an integer x. Find the floor and ceil of x in nums. The floor of x is the largest element
in the array which is smaller than or equal to x. The ceiling of x is the smallest element in the array greater than or
equal to x. If no floor or ceil exists, output -1.

Examples:
Input : nums =[3, 4, 4, 7, 8, 10], x= 5
Output: 4 7
Explanation: The floor of 5 in the array is 4, and the ceiling of 5 in the array is 7.

Input : nums =[3, 4, 4, 7, 8, 10], x= 8
Output: 8 8
Explanation: The floor of 8 in the array is 8, and the ceiling of 8 in the array is also 8.

Constraints:
          1 <= nums.length <= 10^5
          0 < nums[i], x < 10^5
          nums is sorted in ascending order.
 */
public class FindFloorCeil {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        int[] nums1 = {3, 4, 4, 7, 8, 10};
        int target1 = 5;

        findFloorCeilBruteForce(nums, target);
        findFloorCeilBruteForce(nums1, target1);

    }


    /**
     * Finds the floor and ceiling values of a target number in a sorted array using brute force approach.
     * <p>
     * What it does:
     * This method finds both the **floor** (largest element <= target) and **ceiling** (smallest element >= target)
     * of a given target value in a sorted array by iterating through the array once.
     * <p>
     * Why it works:
     * - For floor: Keeps track of the largest element <= target while iterating
     * - For ceil: Takes the first element > target encountered
     * - If target is found exactly, both floor and ceil are set to that value
     * <p>
     * Example:
     * nums = [3,4,4,7,8,10], target = 5
     * - Floor = 4 (largest element <= 5)
     * - Ceil = 7 (smallest element >= 5)
     * <p>
     * Edge cases:
     * - If no floor exists (target < all elements), floor = -1
     * - If no ceil exists (target > all elements), ceil = -1
     * <p>
     * Complexity:
     * Time:  O(n) — single pass through the array
     * Space: O(1) — only uses two variables
     *
     * @param nums   sorted array to search in
     * @param target value to find floor and ceiling for
     */
    private static void findFloorCeilBruteForce(int[] nums, int target) {
        int floor = -1, ceil = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                floor = nums[i];
                ceil = nums[i];
                break;
            } else if (nums[i] > target) {
                ceil = nums[i];
                if (i > 0) floor = nums[i - 1];
                break;
            } else {
                floor = nums[i];  // keep updating until we pass target
            }
        }
        System.out.println("Floor of " + target + " is " + floor);
        System.out.println("Ceiling of " + target + " is " + ceil);
    }
}
