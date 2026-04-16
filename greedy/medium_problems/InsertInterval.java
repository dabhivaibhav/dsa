package greedy.medium_problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Leetcode 57. Insert Interval

You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] represent the start
and the end of the ith interval and intervals is sorted in ascending order by starti. You are also given an interval
newInterval = [start, end] that represents the start and end of another interval.
Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals still
does not have any overlapping intervals (merge overlapping intervals if necessary).
Return intervals after the insertion.
Note that you don't need to modify intervals in-place. You can make a new array and return it.

Example 1:
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]

Example 2:
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].


Constraints:
            0 <= intervals.length <= 10^4
            intervals[i].length == 2
            0 <= starti <= endi <= 10^5
            intervals is sorted by starti in ascending order.
            newInterval.length == 2
            0 <= start <= end <= 10^5



 */
public class InsertInterval {

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {6, 9}};
        int[] newInterval = {2, 5};
        System.out.println(Arrays.deepToString(insert(intervals, newInterval)));

        int[][] intervals1 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newIntervals1 = {4, 8};
        System.out.println(Arrays.deepToString(insert(intervals1, newIntervals1)));
    }

    /*
    Your intuition was on the right track regarding "merging," but it stumbled on how to define the boundaries of that
    merge and how to transition between states.

    Here is a breakdown of the specific "blind spots" in your initial understanding of the problem:

    1. The "Overlap" Definition
    Your thought: You used intervals[rowExInt][1] > newInterval[0] to start a merge.
    The Reality: This only checks if the existing interval ends after the new one starts. It doesn't check if the new
    one has already ended.

    The Missing Piece: For an overlap to exist, two things must be true simultaneously: The existing interval must start
    before the new one ends, AND it must end after the new one starts. By only checking one side, your code tried to
    merge intervals that were actually far to the right of your new interval.

    2. The "Start Point" Assumption
    Your thought: You assumed the intervalStart would always be intervals[rowExInt][0].
    The Reality: If you insert [2, 5] into [[3, 6]], the new start is actually 2 (from the new interval), not 3.

    The Missing Piece: When merging, the new "blob" takes the absolute minimum of all starts and the absolute maximum of
    all ends involved. You have to compare the newInterval values against the existingInterval values.

    3. The "Jump" Logic (The Inner While Loop)
    Your thought: You tried to find the intervalEnd by jumping to the end of the merge inside the loop.
    The Reality: This is very hard to manage with a single pointer. If the newInterval is huge, you might jump past
    several intervals, or worse, jump off the end of the array.

    The Missing Piece: It is much safer to "absorb" intervals one by one. Instead of looking for the final end
    immediately, you update your newInterval's end bit by bit as you consume each overlapping block.

    How to shift your "DSA Thinking" for this problem
    To master interval problems, you need to move from "Index Thinking" to "Timeline Thinking."

    Index Thinking (What you did): "I am at index i, let me look at index i+1 and see if I should jump there or stop
    here." This is prone to "Off-by-one" errors.

    Timeline Thinking (The Optimal Way): "I am walking along a timeline.

    Am I before the new interval? (Yes? Keep moving).

    Am I touching the new interval? (Yes? Merge and keep moving).

    Am I past the new interval? (Yes? Add the rest)."

    Summary of what you missed:
    Boundary Flexibility: The newInterval itself changes shape as it merges. It isn't a static box; it's a "vacuum" that
    sucks in any interval it touches.

    The "Clean Break": You don't need a boolean flag to know if you added the interval. If you use the Three-Phase
    approach, the "Merge Zone" naturally ends, you add the result, and you move to the "After Zone."
    */
    
    /*
     * insert(int[][] intervals, int[] newInterval)
     *
     * WHAT THIS METHOD DOES:
     * This method inserts a new interval into a list of existing non-overlapping
     * intervals that are already sorted by their start times. If the new interval
     * overlaps with any existing ones, it merges them into a single, larger interval.
     *
     * MENTAL MODEL:
     * "The Bridge Builder." Imagine you have several islands (intervals) already
     * placed in a line. You want to place a new, long bridge (newInterval).
     * 1. Some islands are clearly to the left of your bridge—you leave them alone.
     * 2. Some islands get "swallowed" by your bridge because they overlap—you
     * merge them into one giant landmass.
     * 3. Some islands are clearly to the right—you leave them alone too.
     *
     * CORE IDEA:
     * Since the input is already sorted, we can solve this in a single pass (O(n))
     * by dividing the process into three distinct phases: Left side (no overlap),
     * The Overlap Zone (merging), and Right side (no overlap).
     *
     * THOUGHT PROCESS:
     * 1. "First, I'll walk through the list and grab everything that ends before
     * my new interval even starts."
     * 2. "Then, I'll look at intervals that overlap. I'll 'stretch' my new
     * interval's start to the smallest start I see and its end to the largest
     * end I see."
     * 3. "Once I've merged everything possible, I'll drop that 'Mega-Interval'
     * into my list."
     * 4. "Finally, I'll just add whatever is left over."
     *
     * INTUITION (VERY IMPORTANT):
     *
     * The power of this approach lies in the sorting. Because the islands are
     * in order, we don't have to jump around. Once an interval is "to the right"
     * of our bridge, we know for a fact that every single interval after it
     * will also be to the right. This allows us to process the entire array
     * linearly.
     *
     * EDGE CASES & GUARDRAILS:
     * - Empty Input: If the original list is empty, the first and third loops
     * won't run, and we simply return the new interval.
     * - No Overlap: If the bridge fits perfectly between two islands, Phase 2
     * won't change anything, and it will be inserted as-is.
     * - Complete Overlap: If the new interval covers the entire list, Phase 2
     * will merge everything into one.
     *
     * HOW THE CODE WORKS:
     * -> Step 1: Left Phase: While `intervals[i].end < newInterval.start`,
     * add them to the result.
     * -> Step 2: Merge Phase: While `intervals[i].start <= newInterval.end`,
     * there is an overlap. Update `newInterval`'s start to the `min` and its end
     * to the `max` of the two.
     * -> Step 3: Add the finalized `newInterval` to the result list.
     * -> Step 4: Right Phase: Add all remaining intervals to the result list.
     *
     * EXAMPLE DRY RUN: intervals = [[1,3], [6,9]], newInterval = [2,5]
     * | Phase | i | Interval | Condition | Action | Result List |
     * |-------|---|----------|-----------|--------|-------------|
     * | 1     | 0 | [1,3]    | 3 < 2 (F) | Skip to Phase 2 | [] |
     * | 2     | 0 | [1,3]    | 1 <= 5 (T)| Merge: new=[1,5] | [] |
     * | 2     | 1 | [6,9]    | 6 <= 5 (F)| End Phase 2 | [[1,5]] |
     * | 3     | 1 | [6,9]    | -         | Add [6,9] | [[1,5], [6,9]] |
     *
     * COMPLEXITY:
     * -> Time Complexity: O(n). We need to consider all the possible TC and SC.
     * (We visit each interval exactly once during the three phases).
     * -> Space Complexity: O(n). We need to consider all the possible TC and SC.
     * (We create a new list to store the result, which in the worst case contains
     * all n intervals).
     *
     * COMMON PITFALLS:
     * - Confusing Start/End: Comparing the start of the new interval with the
     * start of the old one in Phase 1 (you must use the end to ensure
     * there's no overlap).
     * - List to Array: Forgetting that `toArray` in Java needs a sized array
     * passed in: `new int[ans.size()][]`.
     *
     * INTERVIEW TAKEAWAY:
     * This is the Three-Phase Linear Scan pattern. When dealing with sorted
     * intervals, don't try to remove or shift elements in place (which is O(n^2)).
     * Instead, build a new list and categorize intervals based on their
     * relationship to the "target" range.
     */
    private static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> ans = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // Add intervals that are strictly to the LEFT of newInterval
        while (i < n && intervals[i][1] < newInterval[0]) {
            ans.add(intervals[i]);
            i++;
        }

        // Merge overlapping intervals into one big newInterval
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        // Now add the merged "mega-interval"
        ans.add(newInterval);

        // Add remaining intervals that are strictly to the RIGHT
        while (i < n) {
            ans.add(intervals[i]);
            i++;
        }

        return ans.toArray(new int[ans.size()][]);
    }
}
