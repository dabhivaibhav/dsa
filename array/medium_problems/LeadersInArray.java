package array.medium_problems;


import java.util.ArrayList;
import java.util.Collections;

/*
Given an integer array nums, return a list of all the leaders in the array.
A leader in an array is an element whose value is strictly greater than all elements to its right in the given array.
The rightmost element is always a leader. The elements in the leader array must appear in the order they appear in the nums array.

Examples:
Input: nums = [1, 2, 5, 3, 1, 2]
Output: [5, 3, 2]
Explanation: 2 is the rightmost element, 3 is the largest element in the index range [3, 5], 5 is the largest element in
the index range [2, 5]

Input: nums = [-3, 4, 5, 1, -4, -5]
Output: [5, 1, -4, -5]
Explanation: -5 is the rightmost element, -4 is the largest element in the index range [4, 5], 1 is the largest element
in the index range [3, 5] and 5 is the largest element in the range [2, 5]

Constraints:
            1 <= nums.length <= 10^5
            -10^4 <= nums[i] <= 10^4
 */
public class LeadersInArray {

    public static void main(String[] args) {

        int[] nums = {10, 22, 12, 6, 0, 3};
        leadersInArrayBruteForceApproach(nums);
        leadersInArrayOptimizedApproach(nums);

    }


    private static void leadersInArrayBruteForceApproach(int[] nums) {

        ArrayList<Integer> leadersArray = new ArrayList<>();
        int index = 0;
        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform operation.");
            return;
        }

        if (nums.length == 1) {
            System.out.println("Array has only one element, operation has no effect.");
        }

        for (int i = 0; i < nums.length; i++) {
            boolean isLeader = true;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    isLeader = false;
                    break;
                }

            }
            if (isLeader) {
                leadersArray.add(nums[i]);
            }
        }
        System.out.println("Brute-force approach: " + leadersArray);
    }

    public static void leadersInArrayOptimizedApproach(int[] nums) {

        ArrayList<Integer> leadersArray = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            System.out.println("Array is empty or null, cannot perform operation.");
            return;
        }
        if (nums.length == 1) {
            System.out.println("Array has only one element, operation has no effect.");
            return;
        }

        int n = nums.length - 1;
        int max = Integer.MIN_VALUE;

        for (int i = n; i >= 0; i--) {

            if (nums[i] > max) {
                max = nums[i];
                leadersArray.add(max);
            }
        }

        Collections.reverse(leadersArray);
        System.out.println("Optimized approach: " + leadersArray);
    }
}
