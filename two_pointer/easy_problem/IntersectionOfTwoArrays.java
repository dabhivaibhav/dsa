package two_pointer.easy_problem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
Leetcode 349. Intersection of Two Arrays

Given two integer arrays nums1 and nums2, return an array of their intersection.
Each element in the result must be unique and you may return the result in any order.

Example 1:
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2]

Example 2:
Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [9,4]
Explanation: [4,9] is also accepted.

Constraints:
            1 <= nums1.length, nums2.length <= 1000
            0 <= nums1[i], nums2[i] <= 1000
 */
public class IntersectionOfTwoArrays {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};
        System.out.println(Arrays.toString(intersectionBruteForce(nums1, nums2)));
        System.out.println(Arrays.toString(intersectionOptimized(nums1, nums2)));
        int[] nums11 = {4, 9, 5};
        int[] nums22 = {9, 4, 9, 8, 4};
        System.out.println(Arrays.toString(intersectionBruteForce(nums11, nums22)));
        System.out.println(Arrays.toString(intersectionOptimized(nums11, nums22)));
    }

    /**
     * intersectionBruteForce(int[] nums1, int[] nums2)
     * <p>
     * What this method does:
     * <p>
     * Finds the intersection of two arrays using brute force.
     * <p>
     * The intersection contains only unique elements
     * that appear in both arrays.
     * <p>
     * Core Idea:
     * <p>
     * Compare every element of nums1 with every element of nums2.
     * <p>
     * If a match is found, add it to the result.
     * <p>
     * Use a HashSet to ensure uniqueness.]
     * <p>
     * Thought Process:
     * <p>
     * An element belongs to the intersection if:
     * <p>
     * element exists in nums1
     * AND
     * element exists in nums2
     * <p>
     * Brute force approach:
     * <p>
     * 1. Take each element from nums1.
     * 2. Compare it with every element in nums2.
     * 3. If equal → add to result.
     * 4. Use a Set to avoid duplicates.]
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Create a HashSet
     * <p>
     * Set<Integer> result = new HashSet<>();
     * <p>
     * This ensures only unique elements are stored.]
     * <p>
     * Step 2: Outer Loop (nums1)
     * <p>
     * for each element j in nums1:
     * <p>
     * This selects one element at a time from nums1.]
     * <p>
     * Step 3: Inner Loop (nums2)
     * <p>
     * for each element k in nums2:
     * <p>
     * Compare j and k.
     * <p>
     * If j == k:
     * <p>
     * add j to result set
     * <p>
     * The Set automatically prevents duplicates.]
     * <p>
     * Step 4: Convert Set to Array
     * <p>
     * result.stream().mapToInt(i -> i).toArray()
     * <p>
     * Converts the unique elements into int[] format.]
     * <p>
     * Example:
     * <p>
     * nums1 = [1,2,2,1]
     * nums2 = [2,2]
     * <p>
     * Comparisons:
     * <p>
     * 1 vs 2 → no match
     * 2 vs 2 → match → add 2
     * 2 vs 2 → match → already exists in Set
     * 1 vs 2 → no match
     * <p>
     * result = [2]]
     * <p>
     * Complexity:
     * <p>
     * Outer loop runs n times
     * Inner loop runs m times
     * <p>
     * Time Complexity: O(n × m)
     * <p>
     * Space Complexity: O(k)
     * <p>
     * where k = size of intersection
     * <p>
     * Interview Takeaway:
     * <p>
     * This solution is simple and intuitive.
     * <p>
     * But inefficient for large arrays.
     * <p>
     * We repeatedly compare the same values.
     * <p>
     * This can be optimized using sorting
     * and the Two Pointer technique.
     *
     */
    private static int[] intersectionBruteForce(int[] nums1, int[] nums2) {
        Set<Integer> result = new HashSet<>();
        for (int j : nums1) {
            for (int k : nums2) {
                if (j == k) {
                    result.add(j);
                }
            }
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    /*
    THE "PARALLEL WALK" PATTERN

    THE PROBLEM:
    Find common elements in two sorted arrays.

    THE LOGIC:
    1. Pointer i at nums1[0], Pointer j at nums2[0].
    2. If values match: Found an intersection! Move both.
    3. If nums1[i] < nums2[j]: i is lagging behind. Increment i to catch up.
    4. If nums1[i] > nums2[j]: j is lagging behind. Increment j to catch up.

    AHA! MOMENT:
    Because the arrays are sorted, if nums1[i] is smaller than nums2[j],
    there is NO POINT checking nums1[i] against anything else in nums2.
    It will always be smaller. We can safely skip it.

    COMPLEXITY:
    Time: O(N log N + M log M) due to sorting. The actual walk is only O(N + M).
    Space: O(1) if we don't count the result array (or O(min(N,M)) for the set).
    */

    /**
     * intersectionOptimized(int[] nums1, int[] nums2)
     * <p>
     * What this method does:
     * <p>
     * Finds the intersection of two arrays using
     * sorting and the Two Pointer technique.
     * <p>
     * Ensures only unique elements are returned.
     * <p>
     * Core Idea:
     * <p>
     * If both arrays are sorted,
     * equal elements will appear aligned in order.
     * <p>
     * We can use two pointers to scan both arrays efficiently.
     * <p>
     * Thought Process:
     * <p>
     * After sorting:
     * <p>
     * Smaller values appear first.
     * <p>
     * Compare elements at both pointers:
     * <p>
     * If equal → add to result
     * <p>
     * If nums1[i] < nums2[j]
     * move pointer i forward
     * <p>
     * If nums2[j] < nums1[i]
     * move pointer j forward
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Sort both arrays
     * <p>
     * Arrays.sort(nums1)
     * Arrays.sort(nums2)
     * <p>
     * Example:
     * <p>
     * nums1 = [4,9,5] → [4,5,9]
     * nums2 = [9,4,9,8,4] → [4,4,8,9,9]
     * <p>
     * Step 2: Initialize pointers
     * <p>
     * i = 0 → pointer for nums1
     * j = 0 → pointer for nums2
     * <p>
     * Step 3: Traverse both arrays
     * <p>
     * while (i < nums1.length AND j < nums2.length)
     * <p>
     * Case 1: Equal elements
     * <p>
     * nums1[i] == nums2[j]
     * <p>
     * Add element to result set
     * <p>
     * Move both pointers forward
     * <p>
     * Case 2: nums1 element smaller
     * <p>
     * nums1[i] < nums2[j]
     * <p>
     * Move pointer i forward
     * <p>
     * Case 3: nums2 element smaller
     * <p>
     * nums2[j] < nums1[i]
     * <p>
     * Move pointer j forward
     * <p>
     * Step 4: Convert Set to array
     * <p>
     * Copy elements from Set into int[]
     * <p>
     * Example Walkthrough:
     * <p>
     * nums1 = [4,5,9]
     * nums2 = [4,4,8,9,9]
     * <p>
     * Compare 4 and 4 → match → add 4
     * <p>
     * Compare 5 and 4 → move j
     * <p>
     * Compare 5 and 8 → move i
     * <p>
     * Compare 9 and 8 → move j
     * <p>
     * Compare 9 and 9 → match → add 9
     * <p>
     * Result = [4,9]
     * <p>
     * Complexity:
     * <p>
     * Sorting takes O(n log n + m log m)
     * <p>
     * Traversal takes O(n + m)
     * <p>
     * Time Complexity: O(n log n + m log m)
     * <p>
     * Space Complexity: O(k)
     * <p>
     * where k = intersection size
     * <p>
     * Interview Takeaway:
     * <p>
     * Sorting enables efficient linear comparison.
     * <p>
     * Two pointers eliminate unnecessary comparisons.
     * <p>
     * This approach is much faster than brute force.
     * <p>
     * Two Pointer technique is a powerful pattern
     * when working with sorted arrays.
     *
     */
    private static int[] intersectionOptimized(int[] nums1, int[] nums2) {
        // Sort both arrays first (Requirement for Two Pointers)
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int i = 0; // Pointer for nums1
        int j = 0; // Pointer for nums2
        Set<Integer> resultSet = new HashSet<>(); // To handle uniqueness

        // Walk through both arrays
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                resultSet.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++; // nums1 is too small, move it up
            } else {
                j++; // nums2 is too small, move it up
            }
        }

        // Convert Set to int[]
        int[] result = new int[resultSet.size()];
        int k = 0;
        for (int num : resultSet) {
            result[k++] = num;
        }
        return result;
    }
}
