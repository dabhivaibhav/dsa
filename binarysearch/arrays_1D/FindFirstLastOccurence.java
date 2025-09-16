package binarysearch.arrays_1D;


import java.util.Arrays;

/*
Leetcode 34
Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
If target is not found in the array, return [-1, -1].
You must write an algorithm with O(log n) runtime complexity.

Example 1:
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
Example 3:

Input: nums = [], target = 0
Output: [-1,-1]


Constraints:
            0 <= nums.length <= 10^5
            -10^9 <= nums[i] <= 10^9
            nums is a non-decreasing array.
            -10^9 <= target <= 10^9
 */
public class FindFirstLastOccurence {

    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 10};
        int target = 8;
        int[] nums1 = {5, 7, 7, 8, 8, 10};
        int target1 = 6;
        int[] nums2 = {};
        int target2 = 0;

        System.out.println("Brute Force:");
        System.out.println(Arrays.toString(findFirstLastOccurenceBruteForce(nums, target)));
        System.out.println(Arrays.toString(findFirstLastOccurenceBruteForce(nums1, target1)));
        System.out.println(Arrays.toString(findFirstLastOccurenceBruteForce(nums2, target2)));
        System.out.println("Optimal using upper and lower bound:");
        System.out.println(Arrays.toString(findFirstLastOccurenceOptimal(nums, target)));
        System.out.println(Arrays.toString(findFirstLastOccurenceOptimal(nums1, target1)));
        System.out.println(Arrays.toString(findFirstLastOccurenceOptimal(nums2, target2)));
        System.out.println("Optimal by implementing binary search:");
        System.out.println(Arrays.toString(findFirstLastOccurenceOptimal1(nums, target)));
        System.out.println(Arrays.toString(findFirstLastOccurenceOptimal1(nums1, target1)));
        System.out.println(Arrays.toString(findFirstLastOccurenceOptimal1(nums2, target2)));


    }

    /**
     * findFirstLastOccurenceBruteForce
     * <p>
     * What it does:
     * This method finds the first and last occurrence of a target value in a sorted array
     * using a linear search approach. It returns an array of two integers representing
     * the start and end positions of the target value.
     * <p>
     * Why it works:
     * - Iterates through the array once, keeping track of first and last occurrences
     * - Uses a boolean flag to mark when first occurrence is found
     * - Updates last occurrence position whenever target is found
     * - Returns [-1, -1] if target is not found
     * <p>
     * Example:
     * nums = [5,7,7,8,8,10], target = 8
     * - First 8 found at index 3 → ans[0] = 3
     * - Second 8 found at index 4 → ans[1] = 4
     * - Returns [3,4]
     * <p>
     * Edge cases:
     * - Empty array returns [-1,-1]
     * - Target not found returns [-1,-1]
     * - Single occurrence sets both indices to same value
     * <p>
     * Complexity:
     * Time:  O(n) — needs to scan entire array
     * Space: O(1) — only uses fixed extra space
     */
    private static int[] findFirstLastOccurenceBruteForce(int[] nums, int target) {

        int[] ans = {-1, -1};
        boolean isFound = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target && !isFound) {
                ans[0] = i;
                ans[1] = i;
                isFound = true;
            } else if (nums[i] == target) {
                ans[1] = i;
            }

        }
        return ans;
    }


    /**
     * findFirstLastOccurenceOptimal
     * <p>
     * What it does:
     * Finds the first and last occurrence of target value using binary search approach
     * by utilizing separate methods for finding first and last occurrences.
     * <p>
     * Why it works:
     * - Uses binary search to find lower and upper bounds of target
     * - Lower bound gives first occurrence
     * - Upper bound - 1 gives last occurrence
     * - Returns [-1, -1] if target not found
     * <p>
     * Example:
     * nums = [5,7,7,8,8,10], target = 8
     * - First occurrence at index 3
     * - Last occurrence at index 4
     * - Returns [3,4]
     * <p>
     * Edge cases:
     * - Empty array returns [-1,-1]
     * - Target not found returns [-1,-1]
     * - Single occurrence returns same index twice
     * <p>
     * Complexity:
     * Time:  O(log n) — uses binary search
     * Space: O(1) — constant extra space
     */
    private static int[] findFirstLastOccurenceOptimal(int[] nums, int target) {
        int lb = findFirstOccurence(nums, target);
        int ub = findLastOccurence(nums, target);
        if (lb == nums.length || nums[lb] != target) {
            return new int[]{-1, -1};
        }
        return new int[]{lb, ub - 1};

    }

    /**
     * findFirstOccurence
     * <p>
     * What it does:
     * Finds the index of first occurrence of target value in sorted array
     * using binary search approach (lower bound).
     * <p>
     * Why it works:
     * - Uses binary search to find first element >= target
     * - When found, continues searching left half for earlier occurrence
     * - Returns array length if target not found
     * <p>
     * Example:
     * nums = [5,7,7,8,8,10], target = 8
     * Returns 3 (first occurrence of 8)
     * <p>
     * Edge cases:
     * - Empty array returns array length
     * - Target not found returns array length
     * <p>
     * Complexity:
     * Time:  O(log n) — binary search
     * Space: O(1) — constant space
     */
    private static int findFirstOccurence(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int ans = nums.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] >= target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    /**
     * findLastOccurence
     * <p>
     * What it does:
     * Finds the index after the last occurrence of target value in sorted array
     * using binary search approach (upper bound).
     * <p>
     * Why it works:
     * - Uses binary search to find first element > target
     * - When found, continues searching left half for earlier occurrence
     * - Returns array length if all elements <= target
     * <p>
     * Example:
     * nums = [5,7,7,8,8,10], target = 8
     * Returns 5 (index after last occurrence of 8)
     * <p>
     * Edge cases:
     * - Empty array returns array length
     * - All elements <= target returns array length
     * <p>
     * Complexity:
     * Time:  O(log n) — binary search
     * Space: O(1) — constant space
     */
    private static int findLastOccurence(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int ans = nums.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > target) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }


    /**
     * findFirstLastOccurenceOptimal1
     * <p>
     * What it does:
     * Alternative implementation to find first and last occurrence of target
     * using two separate binary searches in a single method.
     * <p>
     * Why it works:
     * - First binary search finds leftmost occurrence
     * - Second binary search finds rightmost occurrence
     * - Returns [-1,-1] if target not found
     * <p>
     * Example:
     * nums = [5,7,7,8,8,10], target = 8
     * - First binary search finds index 3
     * - Second binary search finds index 4
     * - Returns [3,4]
     * <p>
     * Edge cases:
     * - Empty array returns [-1,-1]
     * - Target not found returns [-1,-1]
     * - Single occurrence returns same index twice
     * <p>
     * Complexity:
     * Time:  O(log n) — two binary searches
     * Space: O(1) — constant extra space
     */
    private static int[] findFirstLastOccurenceOptimal1(int[] nums, int target) {
        int n = nums.length;
        int first = -1;
        //find first occurence
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] >= target) {
                if (nums[mid] == target) first = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        //find last occurence
        low = 0;
        high = n - 1;
        int last = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] <= target) {
                if (nums[mid] == target) last = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return new int[]{first, last};
    }
}

