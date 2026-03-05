package array.easy_problems;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
Leetcode 2956: Find Common Elements Between Two Arrays

You are given two integer arrays nums1 and nums2 of sizes n and m, respectively. Calculate the following values:
answer1 : the number of indices i such that nums1[i] exists in nums2.
answer2 : the number of indices i such that nums2[i] exists in nums1.
Return [answer1,answer2].

Example 1:
Input: nums1 = [2,3,2], nums2 = [1,2]
Output: [2,1]
Explanation:
The elements at indices at 0 and 2 in nums1 exist in nums2 as well so answer is 2.
The elements at indices  1 in nums2 exist in nums1 as well so answer is 1.

Example 2:
Input: nums1 = [4,3,2,3,1], nums2 = [2,2,5,2,3,6]
Output: [3,4]
Explanation:
The elements at indices 1, 2, and 3 in nums1 exist in nums2 as well. So answer1 is 3.
The elements at indices 0, 1, 3, and 4 in nums2 exist in nums1. So answer2 is 4.

Example 3:
Input: nums1 = [3,4,2,3], nums2 = [1,5]
Output: [0,0]
Explanation: No numbers are common between nums1 and nums2, so answer is [0,0].

Constraints:
            n == nums1.length
            m == nums2.length
            1 <= n, m <= 100
            1 <= nums1[i], nums2[i] <= 100
 */
public class FindCommonElementsInTwoArrays {

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 2};
        int[] nums2 = {1, 2};
        System.out.println(Arrays.toString(findCommonElementsHashing(nums1, nums2)));
        System.out.println(Arrays.toString(findCommonElementsBooleanArray(nums1, nums2)));

        int[] nums3 = {4, 3, 2, 3, 1};
        int[] nums4 = {2, 2, 5, 2, 3, 6};
        System.out.println(Arrays.toString(findCommonElementsHashing(nums3, nums4)));
        System.out.println(Arrays.toString(findCommonElementsBooleanArray(nums3, nums4)));

        int[] nums5 = {3, 4, 2, 3};
        int[] nums6 = {1, 5};
        System.out.println(Arrays.toString(findCommonElementsHashing(nums5, nums6)));
        System.out.println(Arrays.toString(findCommonElementsBooleanArray(nums5, nums6)));
    }


    /**
     * findCommonElementsHashing(int[] nums1, int[] nums2)
     * <p>
     * What this method does:
     * <p>
     * Calculates two values:
     * <p>
     * answer1 → number of indices i such that nums1[i] exists in nums2
     * answer2 → number of indices i such that nums2[i] exists in nums1
     * <p>
     * The result is returned as an array:
     * <p>
     * [answer1, answer2]
     * <p>
     * Core Idea:
     * <p>
     * Use HashSet to quickly check whether
     * a number exists in the other array.
     * <p>
     * HashSet provides O(1) average time
     * for membership checking.
     * <p>
     * Thought Process:
     * <p>
     * Directly checking every element of nums1
     * against every element of nums2 would take:
     * <p>
     * O(n × m)
     * <p>
     * which is inefficient.
     * <p>
     * Instead, we build a "membership lookup"
     * using HashSet.
     * <p>
     * This allows us to verify whether a number
     * exists in the other array instantly.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * Create a HashSet called set1
     * and store all elements from nums1.
     * <p>
     * Step 2:
     * Create another HashSet called set2
     * and store all elements from nums2.
     * <p>
     * Step 3:
     * Traverse nums1.
     * <p>
     * For each element:
     * <p>
     * - If the number exists in set2,
     * increment count1.
     * <p>
     * Step 4:
     * Traverse nums2.
     * <p>
     * For each element:
     * <p>
     * - If the number exists in set1,
     * increment count2.
     * <p>
     * Step 5:
     * Return the result as:
     * <p>
     * [count1, count2]
     * <p>
     * Example:
     * <p>
     * nums1 = [2,3,2]
     * nums2 = [1,2]
     * <p>
     * set1 = {2,3}
     * set2 = {1,2}
     * <p>
     * Checking nums1 against set2:
     * <p>
     * 2 → exists → count1 = 1
     * 3 → not exists
     * 2 → exists → count1 = 2
     * <p>
     * Checking nums2 against set1:
     * <p>
     * 1 → not exists
     * 2 → exists → count2 = 1
     * <p>
     * Result:
     * <p>
     * [2,1]
     * <p>
     * Complexity:
     * <p>
     * Let n = nums1.length
     * Let m = nums2.length
     * <p>
     * Building sets: O(n + m)
     * Checking membership: O(n + m)
     * <p>
     * Total Time Complexity:
     * <p>
     * O(n + m)
     * <p>
     * Space Complexity:
     * <p>
     * O(n + m)
     * <p>
     * because we store both arrays in HashSets.
     */
    private static int[] findCommonElementsHashing(int[] nums1, int[] nums2) {
        // Create our "Membership Verification Systems" (Sets)
        Set<Integer> set1 = new HashSet<>();
        for (int num : nums1) set1.add(num);

        Set<Integer> set2 = new HashSet<>();
        for (int num : nums2) set2.add(num);

        int count1 = 0;
        int count2 = 0;

        // Check members of nums1 against set2
        for (int num : nums1) {
            if (set2.contains(num)) {
                count1++;
            }
        }

        // Check members of nums2 against set1
        for (int num : nums2) {
            if (set1.contains(num)) {
                count2++;
            }
        }

        return new int[]{count1, count2};
    }

    /**
     * findCommonElementsBooleanArray(int[] nums1, int[] nums2)
     * <p>
     * What this method does:
     * <p>
     * Computes the same result as the hashing approach:
     * <p>
     * answer1 → number of indices i such that nums1[i] exists in nums2
     * answer2 → number of indices i such that nums2[i] exists in nums1
     * <p>
     * Returns the result as:
     * <p>
     * [answer1, answer2]
     * <p>
     * Core Idea:
     * <p>
     * Instead of using HashSet,
     * this method uses boolean arrays
     * as a direct lookup table.
     * <p>
     * Why this works:
     * <p>
     * The problem constraint guarantees:
     * <p>
     * 1 ≤ nums[i] ≤ 100
     * <p>
     * This means we can use the value itself
     * as an index.
     * <p>
     * Example:
     * <p>
     * existsIn1[5] = true
     * means number 5 exists in nums1.
     * <p>
     * Thought Process:
     * <p>
     * HashSet works well,
     * but when the value range is small,
     * a boolean lookup table is even faster
     * and uses constant space.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * Create two boolean arrays:
     * <p>
     * existsIn1[101]
     * existsIn2[101]
     * <p>
     * Size 101 ensures index 100 is accessible.
     * <p>
     * Step 2:
     * Traverse nums1 and mark:
     * <p>
     * existsIn1[num] = true
     * <p>
     * Step 3:
     * Traverse nums2 and mark:
     * <p>
     * existsIn2[num] = true
     * <p>
     * Step 4:
     * Traverse nums1 again.
     * <p>
     * For each element:
     * <p>
     * - If existsIn2[num] is true,
     * increment count1.
     * <p>
     * Step 5:
     * Traverse nums2 again.
     * <p>
     * For each element:
     * <p>
     * - If existsIn1[num] is true,
     * increment count2.
     * <p>
     * Step 6:
     * Return:
     * <p>
     * [count1, count2]
     * <p>
     * Example:
     * <p>
     * nums1 = [4,3,2,3,1]
     * nums2 = [2,2,5,2,3,6]
     * <p>
     * existsIn1:
     * 1,2,3,4 → true
     * <p>
     * existsIn2:
     * 2,3,5,6 → true
     * <p>
     * Checking nums1 against nums2:
     * <p>
     * 4 → no
     * 3 → yes
     * 2 → yes
     * 3 → yes
     * 1 → no
     * <p>
     * count1 = 3
     * <p>
     * Checking nums2 against nums1:
     * <p>
     * 2 → yes
     * 2 → yes
     * 5 → no
     * 2 → yes
     * 3 → yes
     * 6 → no
     * <p>
     * count2 = 4
     * <p>
     * Result:
     * <p>
     * [3,4]
     * <p>
     * Complexity:
     * <p>
     * Let n = nums1.length
     * Let m = nums2.length
     * <p>
     * Time Complexity:
     * <p>
     * O(n + m)
     * <p>
     * Space Complexity:
     * <p>
     * O(1)
     * <p>
     * because the boolean arrays have
     * fixed size (101).
     * <p>
     * Interview Takeaway:
     * <p>
     * When the value range is small and fixed,
     * a boolean lookup table can replace hashing
     * and provide a very efficient solution.
     */
    private static int[] findCommonElementsBooleanArray(int[] nums1, int[] nums2) {
        // Since values are 1-100, we need size 101 to include index 100
        boolean[] existsIn1 = new boolean[101];
        boolean[] existsIn2 = new boolean[101];

        // Mark which numbers exist in nums1
        for (int num : nums1) {
            existsIn1[num] = true;
        }

        // Mark which numbers exist in nums2
        for (int num : nums2) {
            existsIn2[num] = true;
        }

        int count1 = 0;
        int count2 = 0;

        // Count how many of nums1 are 'stickered' in list 2
        for (int num : nums1) {
            if (existsIn2[num]) count1++;
        }

        // Count how many of nums2 are 'stickered' in list 1
        for (int num : nums2) {
            if (existsIn1[num]) count2++;
        }

        return new int[]{count1, count2};
    }
}
