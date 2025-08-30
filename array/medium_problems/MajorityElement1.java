package array.medium_problems;


import java.util.HashMap;

/*
Given an integer array nums of size n, return the majority element of the array.
The majority element of an array is an element that appears more than n/2 times in the array. The array is guaranteed to
have nums majority element.
Constraints:
            n == nums.length.
            1 <= n <= 105
            -104 <= nums[i] <= 104
            One value appears more than n/2 times.
 */
public class MajorityElement1 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 2, 2, 2, 1, 1, 1, 7, 1, 0, 7, 1, 2, 2, 2, 2, 2, 2, 2};
        bruteForceApproach(nums);
        betterApproach(nums);
        optimalApproach(nums);
    }

    /**
     * Brute Force Approach:
     * This method checks each element in the array by comparing it with every other element.
     * For each element, it counts how many times it appears in the array.
     * If any element's count is more than n/2 (where n is array length), it's the majority element.
     * Time Complexity: O(n²) - Uses nested loops to check each element against all others
     * Space Complexity: O(1) - Only uses a single variable for counting
     */
    private static void bruteForceApproach(int[] nums) {


        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = 0; j < nums.length; j++) {

                if (nums[i] == nums[j]) {
                    count++;
                }
            }
            if (count > nums.length / 2) {
                System.out.println("Brute force approach -> Majority element is: " + nums[i]);
                return;
            }

        }
        System.out.println("Brute force approach -> No majority element found!!");

    }


    /**
     * Better Approach using HashMap:
     * This method uses a HashMap to store the frequency count of each element.
     * It iterates through the array once, updating the count for each element in the HashMap.
     * If any element's count exceeds n/2, it's identified as the majority element.
     * Time Complexity: O(n) - Single pass through the array
     * Space Complexity: O(n) - Uses HashMap to store element frequencies
     */
    private static void betterApproach(int[] nums) {
        int count = nums.length / 2;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            int elementExist = map.getOrDefault(num, 0) + 1;
            if (elementExist > count) {
                System.out.println("Better Approach -> Majority element is: " + num);
                return;
            }
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        System.out.println("Better Approach -> No majority element found!!");

    }

    /**
     * Optimal Approach using Boyer-Moore Voting Algorithm:
     * This method implements the Boyer-Moore majority vote algorithm:
     * 1. Initialize a candidate and a count=0
     * 2. For each element:
     * - If count=0, set current element as candidate
     * - If element matches candidate, increment count
     * - If element differs from candidate, decrement count
     * 3. Verify if candidate appears more than n/2 times
     * The algorithm works because majority element (>n/2 occurrences) will always
     * survive the pairing process, as there aren't enough other elements to cancel it out.
     * Time Complexity: O(n) - Two passes through array
     * Space Complexity: O(1) - Uses only two variables
     */
    private static void optimalApproach(int[] nums) {

        int count = 0;
        int candidate = 0;
        for (int x : nums) {
            if (count == 0) candidate = x;
            count += (x == candidate) ? 1 : -1;
        }
        int freq = 0;
        for (int x : nums) if (x == candidate) freq++;
        System.out.println((freq > nums.length / 2) ? "Optimal Approach -> Majority element is: " +
                candidate : "Optimal Approach -> Majority element not exists");
    }

}
