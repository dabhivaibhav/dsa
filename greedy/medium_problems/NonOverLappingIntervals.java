package greedy.medium_problems;

import java.util.Arrays;

/*
Leetcode 435. Non-overlapping Intervals

Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals you
need to remove to make the rest of the intervals non-overlapping.
Note that intervals which only touch at a point are non-overlapping. For example, [1, 2] and [2, 3] are non-overlapping.

Example 1:
Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.

Example 2:
Input: intervals = [[1,2],[1,2],[1,2]]
Output: 2
Explanation: You need to remove two [1,2] to make the rest of the intervals non-overlapping.

Example 3:
Input: intervals = [[1,2],[2,3]]
Output: 0
Explanation: You don't need to remove any of the intervals since they're already non-overlapping.

Constraints:
            1 <= intervals.length <= 10^5
            intervals[i].length == 2
            -5 * 10^4 <= starti < endi <= 5 * 10^4
 */
public class NonOverLappingIntervals {

    public static void main(String args[]) {
        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        System.out.println(eraseOverlapIntervals(intervals));

    }

    /*
     * ERASE OVERLAP INTERVALS BREAKDOWN
     *
     * WHAT THIS METHOD DOES:
     * This method calculates the minimum number of intervals that must be removed
     * from a list so that no two remaining intervals overlap. Instead of trying
     * to find every possible combination of removals, it uses a greedy strategy
     * to keep the maximum number of intervals possible and subtracts that from
     * the total.
     *
     * MENTAL MODEL: THE MOVIE MARATHONER
     * Imagine you are at a film festival. You want to see as many movies as
     * possible in one day. If you pick a movie that starts at 10:00 AM but lasts
     * for 8 hours, you might miss 4 or 5 shorter movies that happen during that
     * time. To see the maximum number of films, your strategy should be to always
     * pick the movie that finishes earliest. This leaves you with the most time
     * remaining in the day to fit in more movies. Every movie that starts before
     * your current movie finishes is one you simply cannot see (it must be removed).
     *
     * CORE IDEA: THE GREEDY CHOICE
     * The key to this problem is sorting by the end time. We want to be "greedy"
     * for the earliest possible finish time. By picking the interval that ends
     * first, we leave the maximum possible room for all the other intervals that
     * come after it.
     *
     * WHY IT WORKS:
     * If you were to sort by start time, you could accidentally pick a very long
     * interval that starts early but blocks many other potential tasks. Sorting
     * by end time is the only greedy strategy that guarantees you are making the
     * best choice at every step to keep your timeline as open as possible for
     * future intervals.
     *
     * STEP-BY-STEP LOGIC:
     * Step 1: Sort all intervals based on their finish times (the second number
     * in each pair). In Java, this is done using Arrays.sort with a custom comparison.
     *
     * Step 2: Initialize a count variable to 0. This will track how many intervals
     * we need to remove.
     *
     * Step 3: Identify the finish time of the very first interval (the one that
     * ends earliest) and store it in a variable like prevEnd.
     *
     * Step 4: Iterate through the rest of the intervals one by one. For each
     * interval, check if its start time is less than prevEnd.
     *
     * Step 5: If the start is less than prevEnd, they overlap. Since we already
     * chose the interval that ends earliest, we remove this new one and
     * increment our count.
     *
     * Step 6: If the start is not less than prevEnd, there is no overlap. We
     * keep this interval and update prevEnd to be this new interval's finish time.
     *
     * EXAMPLE DRY RUN:
     * Input: [[1,2], [2,3], [3,4], [1,3]]
     * Sorted by end time: [[1,2], [2,3], [1,3], [3,4]]
     *
     * 1. We keep [1,2]. Finish time (prevEnd) = 2.
     * 2. Next is [2,3]. Start time (2) is not less than prevEnd (2). We keep it.
     * New finish time (prevEnd) = 3.
     * 3. Next is [1,3]. Start time (1) is less than prevEnd (3). This is a
     * conflict. We remove it. count = 1.
     * 4. Next is [3,4]. Start time (3) is not less than prevEnd (3). We keep it.
     * New finish time (prevEnd) = 4.
     *
     * Final result: 1.
     *
     * COMPLEXITY ANALYSIS:
     * Time Complexity: O(n log n). We need to consider all the possible TC and SC.
     * Sorting the intervals is the most expensive part of this algorithm, taking
     * O(n log n) time. The subsequent loop that walks through the intervals
     * only takes O(n) time.
     *
     * Space Complexity: O(1) or O(log n). We need to consider all the possible
     * TC and SC. The algorithm itself only uses a few integer variables (count
     * and prevEnd), which is O(1). However, the sorting function usually
     * requires O(log n) extra space for the internal recursion stack.
     *
     * HOW TO REMEMBER:
     * To keep the most, finish the soonest. Always look at the finish line to
     * make your next choice.
     */
    private static int eraseOverlapIntervals(int[][] intervals) {
        // Sort intervals by their end time
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

        // Counter for how many intervals we need to remove
        int count = 0;

        // Track end time of last non-overlapping interval
        int prevEnd = intervals[0][1];

        // Loop through remaining intervals
        for (int i = 1; i < intervals.length; i++) {

            // If current interval overlaps with previous
            if (intervals[i][0] < prevEnd) {
                // Increment count to remove this interval
                count++;
            } else {
                // Update previous end time
                prevEnd = intervals[i][1];
            }
        }

        // Return total number of intervals removed
        return count;
    }
}
