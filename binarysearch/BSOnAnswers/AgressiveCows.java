package binarysearch.BSOnAnswers;

/*
Aggressive cows
Given an array nums of size n, which denotes the positions of stalls, and an integer k, which denotes the number of
aggressive cows, assign stalls to k cows such that the minimum distance between any two cows is the maximum possible.
Find the maximum possible minimum distance.

Examples:
Input: n = 6, k = 4, nums = [0, 3, 4, 7, 10, 9]
Output: 3
Explanation: The maximum possible minimum distance between any two cows will be 3 when 4 cows are placed at positions
[0, 3, 7, 10]. Here the distances between cows are 3, 4, and 3 respectively. We cannot make the minimum distance greater
than 3 in any ways.

Input : n = 5, k = 2, nums = [4, 2, 1, 3, 6]
Output: 5
Explanation: The maximum possible minimum distance between any two cows will be 5 when 2 cows are placed at positions [1, 6].

Constraints:
              2 <= n <= 10^5
              2 <= k <= n
              0 <= nums[i] <= 10^9
 */

import java.util.Arrays;

public class AgressiveCows {

    public static void main(String[] args) {
        int[] nums = {0, 3, 4, 7, 10, 9};
        int k = 4;
        int[] nums1 = {4, 2, 1, 3, 6};
        int k1 = 2;
        System.out.println(aggressiveCowsBruteForce(nums1, k1));
        System.out.println(aggressiveCowsBruteForce(nums, k));
        System.out.println(aggressiveCowsOptimal(nums1, k1));
        System.out.println(aggressiveCowsOptimal(nums, k));
    }

    /**
     * aggressiveCowsBruteForce
     * <p>
     * What it does:
     * Finds the maximum possible minimum distance between any two of the k cows by
     * linearly testing every candidate distance from 1 up to (max - min) after sorting the stalls.
     * <p>
     * Intuition:
     * - After sorting stall positions, the feasible minimum distance d* has a monotone property:
     * if a distance d is feasible (we can place all k cows with gaps ≥ d), then any distance ≤ d is also feasible.
     * - This brute-force version simply walks d = 1, 2, 3, ... upward and remembers the last feasible d.
     * <p>
     * Why each line matters:
     * - int[] arr = nums.clone(); Arrays.sort(arr);
     * We must reason about gaps on a line; sorting gives increasing stall positions.
     * <p>
     * - int min = arr[0]; int max = arr[arr.length - 1];
     * The smallest and largest stall establish the maximum possible span; the largest
     * minimum distance cannot exceed (max - min).
     * <p>
     * - for (int i = 1; i <= max - min; i++) { ... }
     * Try each candidate minimum distance i. We include the upper bound (max - min)
     * because for k = 2, placing cows at the ends is often optimal.
     * <p>
     * - if (canPlaceCows(arr, k, i)) best = i; else break;
     * Greedily test feasibility for this i. As soon as a distance fails, larger distances will also fail
     * (monotonicity), so we can break. The last feasible i stored in best is the answer.
     * <p>
     * Example walk-through (nums = [10,1,2,7,5], k = 3):
     * - Sorted: [1,2,5,7,10], span = 9
     * - Try i = 1 → feasible; i = 2 → feasible; i = 3 → feasible; i = 4 → feasible; i = 5 → not feasible → break.
     * - best = 4
     * <p>
     * Time Complexity:
     * - Sorting: O(n log n)
     * - Testing distances: in worst case O((max - min) * n)
     * - Total: O(n log n + n * (max - min)) → too slow if (max - min) is large, but simple and correct.
     * <p>
     * Space Complexity:
     * - O(n) for the cloned array; O(1) extra beyond that.
     */
    private static int aggressiveCowsBruteForce(int[] nums, int k) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int min = arr[0];
        int max = arr[arr.length - 1];
        int best = 0;
        for (int i = 1; i <= max - min; i++) {
            if (canPlaceCows(arr, k, i)) {
                best = i;
            } else {
                break;
            }
        }
        return best;
    }

    /**
     * aggressiveCowsOptimal
     * <p>
     * What it does:
     * Computes the maximum possible minimum distance between any two of the k cows using
     * binary search on the answer (the distance), which is optimal.
     * <p>
     * Intuition:
     * - Define predicate P(d): “We can place k cows so that adjacent cows are at least d apart.”
     * - P(d) is monotone: if P(d) is true, then P(d') is true for all d' ≤ d; if P(d) is false, then P(d') is false for all d' ≥ d.
     * - Therefore, the set of feasible distances looks like [1 .. d*] for some d*. Binary search can find d* efficiently.
     * <p>
     * Why each line matters:
     * - int[] arr = nums.clone(); Arrays.sort(arr);
     * Sorting is essential; greedy feasibility relies on increasing stall positions.
     * <p>
     * - int min = arr[0]; int max = arr[arr.length - 1];
     * The distance d cannot exceed (max - min).
     * <p>
     * - int low = 1, high = max - min;
     * Search space for the answer: smallest non-zero gap to the largest possible gap.
     * <p>
     * - while (low <= high) { int mid = low + (high - low) / 2; ... }
     * Standard binary search on the candidate distance mid (avoid overflow in mid computation).
     * <p>
     * - if (canPlaceCows(arr, k, mid)) { low = mid + 1; } else { high = mid - 1; }
     * If mid is feasible, push right to try a larger minimum distance; otherwise shrink left.
     * <p>
     * - return high;
     * When the loop ends, high is the last feasible distance (the maximum d with P(d) true),
     * because the final move that breaks the loop sets low = high + 1.
     * <p>
     * Example (nums = [10,1,2,7,5], k = 3):
     * - Sorted: [1,2,5,7,10], low = 1, high = 9
     * mid=5 → canPlace? false → high=4
     * mid=2 → canPlace? true  → low=3
     * mid=3 → canPlace? true  → low=4
     * mid=4 → canPlace? true  → low=5
     * loop ends (low=5, high=4) → answer=high=4
     * <p>
     * Time Complexity:
     * - Sorting: O(n log n)
     * - Binary search over distances: O(log(max - min))
     * - Each feasibility check: O(n)
     * - Total: O(n log n + n log(max - min)) → typically reported as O(n log n).
     * <p>
     * Space Complexity:
     * - O(n) for the cloned array; O(1) extra beyond that.
     */
    private static int aggressiveCowsOptimal(int[] nums, int k) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        int min = arr[0];
        int max = arr[arr.length - 1];
        int low = 1;
        int high = max - min;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canPlaceCows(arr, k, mid)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return high;
    }

    /**
     * canPlaceCows
     * <p>
     * What it does:
     * Greedy feasibility check: given sorted stalls, can we place 'cows' cows such that
     * each consecutive placement is at least 'distance' apart?
     * <p>
     * Intuition:
     * - Greedy works because to maximize minimum separation, you always place the next cow
     * as early as allowed (the leftmost stall that is ≥ 'distance' away from the last placement).
     * - This choice never hurts future placements and quickly reveals whether 'distance' is feasible.
     * <p>
     * Why each line matters:
     * - int count = 1; int lastPos = arr[0];
     * Place the first cow at the first stall; we’ve placed one cow so far.
     * <p>
     * - for (int j = 1; j < arr.length; j++) { ... }
     * Scan left to right, looking for the earliest stall far enough from lastPos.
     * <p>
     * - if (arr[j] - lastPos >= distance) { count++; lastPos = arr[j]; }
     * Found a stall at least 'distance' away; place a cow and update the last position.
     * <p>
     * - if (count >= cows) return true;
     * Early success: as soon as we’ve placed all cows, the candidate distance is feasible.
     * <p>
     * - return false;
     * End of array without placing all cows → distance too large (not feasible).
     * <p>
     * Example with distance = 4 on [1,2,5,7,10], cows=3:
     * - Place at 1 (count=1, lastPos=1)
     * - j=1: 2-1=1 < 4 → skip
     * - j=2: 5-1=4 ≥ 4 → place at 5 (count=2, lastPos=5)
     * - j=3: 7-5=2 < 4 → skip
     * - j=4: 10-5=5 ≥ 4 → place at 10 (count=3) → success
     * <p>
     * Time Complexity:
     * - O(n): Single pass through stalls.
     * <p>
     * Space Complexity:
     * - O(1).
     */
    private static boolean canPlaceCows(int[] arr, int cows, int distance) {
        int count = 1;
        int lastPos = arr[0];
        for (int j = 1; j < arr.length; j++) {
            if (arr[j] - lastPos >= distance) {
                count++;
                lastPos = arr[j];
            }
            if (count >= cows) {
                return true;
            }
        }
        return false;
    }
}
