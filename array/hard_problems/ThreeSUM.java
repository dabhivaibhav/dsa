package array.hard_problems;

import java.util.*;

/**
 * 15. 3Sum
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k,
 * and nums[i] + nums[j] + nums[k] == 0.
 * Notice that the solution set must not contain duplicate triplets.
 * <p>
 * Example 1:
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation:
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not matter.
 * <p>
 * Example 2:
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 * <p>
 * Example 3:
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 * <p>
 * <p>
 * Constraints:
 * 3 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 */
public class ThreeSUM {

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        int[] nums1 = {-1, 0, 1};

        System.out.println("Brute force approach: " + threeSumBruteForceAppraoch(nums1));
        System.out.println("Better Approach: " + threeSumBetterApproach(nums1));
        System.out.println("Optimal Approach:" + threeSumOptimalApproach(nums1));
        System.out.println("Optimal Approach: " + threeSumOptimalApproach(nums));
    }

    /**
     * Brute Force Approach for finding triplets that sum to zero.
     * <p>
     * Algorithm:
     * 1. Use three nested loops to try every possible combination of three numbers
     * 2. Check if their sum equals zero
     * 3. Add unique triplets to result list after sorting to avoid duplicates
     * <p>
     * Example:
     * Input: [-1,0,1,2,-1,-4]
     * One valid triplet found: [-1,-1,2] when i=0, j=4, k=3
     * <p>
     * Time Complexity: O(n³) due to three nested loops
     * Space Complexity: O(1) excluding the space needed for output
     *
     * @param nums input array of integers
     * @return List of triplets that sum to zero
     */
    private static List<List<Integer>> threeSumBruteForceAppraoch(int[] nums) {

        List<List<Integer>> triplets = new ArrayList<>();

        if (nums == null || nums.length < 3) {
            return null;
        }

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[k]);
                        Collections.sort(list);
                        if (!triplets.contains(list)) {
                            triplets.add(list);
                        }


                    }
                }
            }
        }
        return triplets;
    }

    /**
     * Better Approach using HashSet for finding triplets that sum to zero.
     * <p>
     * Algorithm:
     * 1. Fix first element with outer loop
     * 2. Use HashSet to store elements as we traverse for second element
     * 3. For each pair of elements, check if their complement exists in HashSet
     * <p>
     * Example:
     * Input: [-1,0,1]
     * When i=0 (-1), j=1 (0)
     * We look for k=1 in the HashSet
     * <p>
     * Time Complexity: O(n²) as we have one outer loop and HashSet operations
     * Space Complexity: O(n) for storing elements in HashSet
     *
     * @param nums input array of integers
     * @return List of triplets that sum to zero
     */
    private static List<List<Integer>> threeSumBetterApproach(int[] nums) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            HashSet<Integer> set = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                int k = -nums[i] - nums[j];
                if (set.contains(k)) {
                    List<Integer> list = Arrays.asList(nums[i], nums[j], k);
                    Collections.sort(list);
                    if (!triplets.contains(list)) {
                        triplets.add(list);
                    }
                }
                set.add(nums[j]);
            }
        }
        return triplets;
    }

    /**
     * Optimal Two-Pointer Approach for finding triplets that sum to zero.
     * <p>
     * Algorithm:
     * 1. Sort the array first
     * 2. Fix first element and use two pointers (left and right) for remaining array
     * 3. Skip duplicates to avoid duplicate triplets
     * 4. Move pointers based on sum comparison with zero
     * <p>
     * Example:
     * Input (sorted): [-4,-1,-1,0,1,2]
     * Fix -4, use pointers on rest of array
     * When sum < 0, increment left pointer
     * When sum > 0, decrement right pointer
     * <p>
     * Time Complexity: O(n²) - one loop for first element, two pointers for rest
     * Space Complexity: O(1) excluding the space needed for output
     *
     * @param nums input array of integers
     * @return List of triplets that sum to zero
     */
    private static List<List<Integer>> threeSumOptimalApproach(int[] nums) {
        List<List<Integer>> triplets = new ArrayList<>();
        Arrays.sort(nums);

        int n = nums.length;
        for (int i = 0; i < n; i++) {

            //remove duplicates:
            if (i != 0 && nums[i] == nums[i - 1]) continue;

            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum < 0) {
                    j++;
                } else if (sum > 0) {
                    k--;
                } else {
                    List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k]);
                    triplets.add(temp);
                    j++;
                    k--;
                    //skip the duplicates:
                    while (j < k && nums[j] == nums[j - 1]) j++;
                    while (j < k && nums[k] == nums[k + 1]) k--;
                }
            }

        }
        return triplets;
    }
}
