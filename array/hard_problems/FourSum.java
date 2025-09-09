package array.hard_problems;

import java.util.*;

/**
 * 18. 4Sum
 * <p>
 * Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
 * 0 <= a, b, c, d < n
 * a, b, c, and d are distinct.
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * You may return the answer in any order.
 * <p>
 * Example 1:
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * <p>
 * Example 2:
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 * <p>
 * Constraints:
 * 1 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 */
public class FourSum {

    public static void main(String[] args) {

        int[] nums = {-1, 0, 1, 2, -1, -4};
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target = 0;
        int[] nums2 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target2 = -294967296;
//        System.out.println(fourSumBruteForce(nums, target));
//        System.out.println(fourSumHashing(nums, target));
//        System.out.println(fourSumHashing(nums1, target));
//        System.out.println(foursumTwoPointers(nums1, target));

        System.out.println(foursumTwoPointers(nums2, target2));
    }


    /**
     * Brute Force approach to find all unique quadruplets that sum to target.
     * Algorithm:
     * 1. Use four nested loops to try every possible combination of four numbers
     * 2. Check if their sum equals target
     * 3. Sort each valid quadruplet to ensure uniqueness before adding to results
     * <p>
     * Example:
     * nums = [1,0,-1,0,-2,2], target = 0
     * One iteration finds: nums[5](2) + nums[2](-1) + nums[1](0) + nums[3](0) = 1
     * After sorting: [-1,0,0,2]
     * <p>
     * Time Complexity: O(n^4) - four nested loops
     * Space Complexity: O(1) - excluding the space needed for output
     *
     * @param nums   array of integers
     * @param target desired sum
     * @return List of unique quadruplets that sum to target
     */
    private static List<List<Integer>> fourSumBruteForce(int[] nums, int target) {
        List<List<Integer>> results = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (nums[i] + nums[j] + nums[k] + nums[l] == target) {
                            List<Integer> quad = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            Collections.sort(quad);
                            if (!results.contains(quad)) {
                                results.add(quad);
                            }
                        }
                    }
                }
            }
        }
        return results;
    }

    /**
     * Hash-based approach to find all unique quadruplets that sum to target.
     * Algorithm:
     * 1. Use two outer loops to fix first two numbers
     * 2. Use HashSet to store the third number
     * 3. Check if (target - sum of three numbers) exists in HashSet
     * <p>
     * Example:
     * nums = [1,0,-1,0,-2,2], target = 0
     * When i=0,j=1: nums[0](1) + nums[1](0) = 1
     * For k=2: 1 + nums[2](-1) = 0
     * Looking for 0 (target - 0) in HashSet
     * <p>
     * Time Complexity: O(n^3) - two outer loops and one pass through remaining elements
     * Space Complexity: O(n) - for HashSet storage
     *
     * @param nums   array of integers
     * @param target desired sum
     * @return List of unique quadruplets that sum to target
     */
    public static List<List<Integer>> fourSumHashing(int[] nums, int target) {
        int n = nums.length; // size of the array
        Set<List<Integer>> st = new HashSet<>();

        // checking all possible quadruplets:
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Set<Long> hashset = new HashSet<>();
                for (int k = j + 1; k < n; k++) {
                    // taking bigger data type
                    // to avoid integer overflow:
                    long sum = nums[i] + nums[j];
                    sum += nums[k];
                    long fourth = target - sum;
                    if (hashset.contains(fourth)) {
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[k]);
                        temp.add((int) fourth);
                        temp.sort(Integer::compareTo);
                        st.add(temp);
                    }
                    // put the kth element into the hashset:
                    hashset.add((long) nums[k]);
                }
            }
        }
        List<List<Integer>> ans = new ArrayList<>(st);
        return ans;
    }

    /**
     * Two-pointer approach to find all unique quadruplets that sum to target.
     * Algorithm:
     * 1. Sort the array
     * 2. Fix first two numbers using two loops
     * 3. Use two pointers (low and high) for remaining range
     * 4. Skip duplicates to avoid repeated quadruplets
     * <p>
     * Example:
     * nums = [1,0,-1,0,-2,2], target = 0
     * After sorting: [-2,-1,0,0,1,2]
     * When i=0,j=1: nums[0](-2) + nums[1](-1) = -3
     * Use low=2,high=5 to find two numbers summing to 3
     * <p>
     * Time Complexity: O(n^3) - two loops and linear two-pointer operation
     * Space Complexity: O(1) - excluding the space needed for output
     *
     * @param nums   array of integers
     * @param target desired sum
     * @return List of unique quadruplets that sum to target
     */
    private static List<List<Integer>> foursumTwoPointers(int[] nums, int target) {
        List<List<Integer>> triplets = new ArrayList<>();
        Arrays.sort(nums);

        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < n; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int low = j + 1;
                int high = n - 1;
                while (low < high) {
                    long sum = nums[i];
                    sum += nums[j];
                    sum += nums[low];
                    sum += nums[high];
                    if (sum == target) {
                        triplets.add(Arrays.asList(nums[i], nums[j], nums[low], nums[high]));
                        low++;
                        high--;
                        while (low < high && nums[low] == nums[low - 1]) low++;
                        while (low < high && nums[high] == nums[high + 1]) high--;
                    } else if (sum < target) {
                        low++;
                    } else {
                        high--;
                    }
                }
            }
        }
        return triplets;
    }
}
