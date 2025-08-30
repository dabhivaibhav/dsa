package two_pointer;

/*
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.

Example 1:
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

Example 2:
Input: nums = [3,2,4], target = 6
Output: [1,2]

Example 3:
Input: nums = [3,3], target = 6
Output: [0,1]

Constraints:
            2 <= nums.length <= 104
            -109 <= nums[i] <= 109
            -109 <= target <= 109
            Only one valid answer exists.
 */

public class TwoSum {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 17;

        int[] nums1 = {1, 2, 3, 42};
        int target1 = 45;

        twoPointer(nums, target);
        twoPointer(nums1, target1);
    }


    /**
     * The Two Pointer approach is particularly effective for problems involving sorted arrays
     * where we need to find elements that satisfy certain conditions. This method specifically
     * solves the two sum problem using two pointers moving from both ends of the array.
     * When to use Two Pointer approach:
     * 1. When dealing with sorted arrays
     * 2. When searching for pairs with a target sum
     * 3. When need to compare elements from both ends of array
     * 4. When problem involves finding a combination of elements
     * Time Complexity: O(n) - where n is the length of array
     * Space Complexity: O(1) - only using two pointer variables
     *
     * @param nums   sorted array of integers
     * @param target sum to find
     * @return void
     */
    private static void twoPointer(int[] nums, int target) {

        int i = 0;
        int j = nums.length - 1;

        while (i < j) {
            int sum = nums[i] + nums[j];

            if (sum == target) {
                System.out.println("Index of " + target + " is " + i + " and " + j);
                return;
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        if (i == j) {
            System.out.println("No two sum solution");
        }
    }
}
