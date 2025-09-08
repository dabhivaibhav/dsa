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
