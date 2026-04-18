package greedy.medium_problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Leetcode 56. Merge Intervals

Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of
the non-overlapping intervals that cover all the intervals in the input.

Example 1:
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].

Example 2:
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.

Example 3:
Input: intervals = [[4,7],[1,4]]
Output: [[1,7]]
Explanation: Intervals [1,4] and [4,7] are considered overlapping.

Constraints:
            1 <= intervals.length <= 10^4
            intervals[i].length == 2
            0 <= starti <= endi <= 10^4
 */
public class MergeIntervals {

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println(Arrays.deepToString(merger(intervals)));

        int[][] intervals1 = {{4, 7}, {1, 4}};
        System.out.println(Arrays.deepToString(merger(intervals1)));
    }

    /**
     * merger(int[][] intervals)
     *
     * WHAT THIS METHOD DOES:
     * This method takes a collection of time intervals (which might overlap or be 
     * completely out of order) and consolidates them into a minimal set of 
     * non-overlapping intervals. It ensures that the final timeline is clean 
     * and continuous where possible.
     *
     * MENTAL MODEL:
     * > "The Growing Snowball."
     * > Imagine you are rolling a snowball along a number line. You start at the first interval. 
     * > As you move forward, if the next interval starts while you are still "inside" your 
     * > current snowball (overlap), you absorb it, and your snowball might get bigger. 
     * > If you reach a gap where the next interval is too far away to touch, you leave 
     * > your current snowball behind and start rolling a brand-new one.
     *    
     * CORE IDEA:
     * The secret to this problem is Sorting. By sorting the intervals by their 
     * start times, we transform a chaotic 2D problem into a simple 1D linear scan. 
     * Once sorted, we only ever need to check if the next interval starts before 
     * the current one ends.
     *
     * THOUGHT PROCESS:
     * 1. "If intervals are scattered, I can't easily tell who overlaps with whom." -> Action: Sort by start time.
     * 2. "Now that they are in order, I'll pick the first one as my 'active' block."
     * 3. "I'll look at the next interval: Does it start before my active block ends?"
     * 4. "If YES: They overlap! I'll stretch my active block's end to cover the new one (using Math.max)."
     * 5. "If NO: There is a gap. My current block is finished. I'll save it and make this new interval my new 'active' block."
     *
     * INTUITION (VERY IMPORTANT):
     * Why does sorting by start time work? Because it guarantees that for any 
     * interval i, if it's going to overlap with any previous intervals, it must 
     * overlap with the one immediately before it in our result list. We don't 
     * have to look back at the whole history; we only care about the "current" 
     * merging boundary.
     *
     * EDGE CASES & GUARDRAILS:
     * Single Interval: If intervals.length <= 1, we return it immediately as there is nothing to merge.
     * Touching Intervals: The rule next[0] <= current[1] handles cases like [1,4] and [4,5] correctly—they are considered overlapping because they share the point 4.
     * Contained Intervals: If one interval is entirely inside another (e.g., [1,10] and [2,5]), the Math.max ensures the end stays at 10.
     *
     * HOW THE CODE WORKS:
     * Step 1: Handle the base case (empty or single interval).
     * Step 2: Sort the entire 2D array based on the first element (start time).
     * Step 3: Create a List to hold our merged results.
     * Step 4: Pick the first interval (intervals[0]) as our current working block and add it to the list.
     * Step 5: Loop through all intervals. For each next interval:
     * If next.start <= current.end: Update current.end.
     * Else: The next interval is the start of a new non-overlapping block. Update our current reference to next and add it to the list.
     *
     * EXAMPLE DRY RUN:
     * `intervals = [[1,3], [8,10], [2,6], [15,18]]`
     *
     * | Action | Interval | Sorted List | current | ans List | Note |
     * | :--- | :--- | :--- | :--- | :--- | :--- |
     * | Sort | - | [[1,3],[2,6],[8,10],[15,18]] | - | - | Ordered by start |
     * | Init | [1,3] | - | [1,3] | [[1,3]] | Start first block |
     * | Loop 1 | [2,6] | - | [1,6] | [[1,6]] | 2 <= 3 (T). Merge! |
     * | Loop 2 | [8,10] | - | [8,10] | [[1,6], [8,10]] | 8 <= 6 (F). New block |
     * | Loop 3 | [15,18] | - | [15,18] | [[1,6], [8,10], [15,18]] | 15 <= 10 (F). New block |
     *
     * COMPLEXITY:
     * Time Complexity: O(n log n). We need to consider all the possible TC and SC. (The sorting step takes O(n log n), and the linear scan takes O(n)).
     * Space Complexity: O(n). We need to consider all the possible TC and SC. (We store the merged intervals in a list/array, which in the worst case is the same size as the input).
     *
     * COMMON PITFALLS:
     * Forgetting the Sort: Without sorting, the greedy "check only the neighbor" logic fails completely.
     * Updating the Wrong Reference: Because we add the current object to the list before merging, any changes we make to current[1] automatically update the interval inside the ans list (Pass-by-reference).
     *
     * INTERVIEW TAKEAWAY:
     * This is the Sort-then-Scan pattern. It is the gold standard for 
     * interval problems. If the intervals aren't sorted, your first step is 
     * almost always to sort them. It reduces a complex "compare everyone to 
     * everyone" problem into a simple "compare me to my neighbor" problem.
     */

    private static int[][] merger(int[][] intervals) {
        if (intervals.length <= 1) return intervals;

        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> ans = new ArrayList<>();

        // Start with the first interval as our "working block"
        int[] current = intervals[0];
        ans.add(current);

        for (int[] next : intervals) {
            // If next starts within the current block's range
            if (next[0] <= current[1]) {
                // MERGE: Update the end of the current block
                current[1] = Math.max(current[1], next[1]);
            } else {
                // NEW BLOCK: No overlap, so move onto the next one
                current = next;
                ans.add(current);
            }
        }

        return ans.toArray(new int[ans.size()][]);
    }
}
