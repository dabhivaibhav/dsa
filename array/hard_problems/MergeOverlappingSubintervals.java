package array.hard_problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals and return an array
 * of the non-overlapping intervals that cover all the intervals in the input. You can return the intervals in any order.
 * <p>
 * Examples:
 * Input: intervals = [[1,5],[3,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Intervals [1,5] and [3,6] overlap, so they are merged into [1,6].
 * <p>
 * Input: intervals = [[5,7],[1,3],[4,6],[8,10]]
 * Output: [[1,3],[4,7],[8,10]]
 * Explanation: Intervals [4,6] and [5,7] overlap and are merged into [4,7].
 * <p>
 * Constraints:
 * 1 <= intervals.length <= 10^⁵
 * 0 <= starti <= endi <= 10^⁵
 */
public class MergeOverlappingSubintervals {

    public static void main(String[] args) {

        int[][] intervals = {{1, 5}, {3, 6}, {8, 10}, {15, 18}};
        int[][] intervals1 = {{5, 7}, {1, 3}, {4, 6}, {8, 10}};

        System.out.println(Arrays.deepToString(mergerOverLappingBruteForceApproach(intervals)));
        System.out.println(Arrays.deepToString(mergerOverLappingBruteForceApproach(intervals1)));
    }


    /**
     * Merges overlapping intervals in the given array using a brute force approach.
     * <p>
     * Algorithm steps:
     * 1. Sort intervals based on start time to ensure proper ordering
     * 2. Iterate through each interval and for each:
     * - Track current interval's start and end times
     * - Skip if current interval is already included in previous merged interval
     * - Check subsequent intervals for overlap and extend end time if needed
     * - Add the merged interval to result list
     * 3. Convert result list to array and return
     * <p>
     * Think of intervals like time slots. If you have meetings from 1-3 PM and 2-4 PM,
     * they overlap and can be merged into one slot of 1-4 PM. We first sort all slots
     * by start time, then combine any that overlap.
     * <p>
     * Time Complexity: O(n * log n) + O(n^2)
     * - O(n * log n) for sorting n intervals
     * - O(n^2) for checking each interval against others
     * <p>
     * Space Complexity: O(n)
     * - Space needed to store the merged intervals in the result list
     *
     * @param arr Array of intervals where each interval is [start, end]
     * @return Array of merged non-overlapping intervals
     */
    private static int[][] mergerOverLappingBruteForceApproach(int[][] arr) {
        int n = arr.length; // size of the array
        // Sort the given intervals:
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();

        for (int i = 0; i < n; i++) { // select an interval:
            int start = arr[i][0];
            int end = arr[i][1];

            // Skip all the merged intervals:
            if (!merged.isEmpty() && end <= merged.get(merged.size() - 1)[1]) {
                continue;
            }

            // Check the rest of the intervals:
            for (int j = i + 1; j < n; j++) {
                if (arr[j][0] <= end) {
                    end = Math.max(end, arr[j][1]);
                } else {
                    break;
                }
            }
            merged.add(new int[]{start, end});
        }

        // Convert List<int[]> to int[][]
        int[][] ans = new int[merged.size()][2];
        for (int i = 0; i < merged.size(); i++) {
            ans[i] = merged.get(i);
        }

        return ans;

    }


    /**
     * Merges overlapping intervals in the given array using an optimal approach.
     * <p>
     * Algorithm steps:
     * 1. Sort intervals based on start time for proper ordering
     * 2. Iterate through each interval once and:
     * - If result list is empty or current interval doesn't overlap with last merged interval,
     * add current interval to result
     * - If overlap exists, extend the end time of last merged interval if needed
     * 3. Convert result list to array and return
     * <p>
     * Think of intervals like time slots. If you have meetings from 1-3 PM and 2-4 PM,
     * they overlap and can be merged into one slot of 1-4 PM. This approach is more efficient
     * as it requires only a single pass through the sorted intervals.
     * <p>
     * Time Complexity: O(n * log n)
     * - O(n * log n) for sorting n intervals
     * - O(n) for single pass through intervals
     * <p>
     * Space Complexity: O(n)
     * - Space needed to store the merged intervals in the result list
     *
     * @param arr Array of intervals where each interval is [start, end]
     * @return Array of merged non-overlapping intervals
     */
    private static int[][] mergerOverLappingOptimalApproach(int[][] arr) {
        int n = arr.length;
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));
        List<int[]> merged = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < arr[i][0]) {
                merged.add(arr[i]);
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], arr[i][1]);
            }
        }

        int[][] ans = new int[merged.size()][2];
        for (int i = 0; i < merged.size(); i++) {
            ans[i] = merged.get(i);
        }

        return ans;
    }
}
