package collection.stack;

import java.util.Arrays;
import java.util.Stack;

/*
Leetcode 739. Daily Temperatures

Given an array of integers temperatures represents the daily temperatures,
return an array answer such that answer[i] is the number of days you have
to wait after the ith day to get a warmer temperature. If there is no future
day for which this is possible, keep answer[i] == 0 instead.

Example 1:
Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]

Example 2:
Input: temperatures = [30,40,50,60]
Output: [1,1,1,0]

Example 3:
Input: temperatures = [30,60,90]
Output: [1,1,0]


Constraints:
            1 <= temperatures.length <= 10^5
            30 <= temperatures[i] <= 100
 */
public class DailyTemperature {

    public static void main(String[] args) {
        int[] temp = {73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println(Arrays.toString(dailyTemperatures(temp)));
    }

    /*
     * Daily Temperatures (LeetCode 739)
     *
     * Intuition:
     *   Brute force scans forward from each day to find the next warmer day,
     *   redoing work across iterations. Instead, keep a "waiting list" of
     *   day indices that haven't found a warmer day yet. Each new day is
     *   checked against that list: any waiting day colder than today gets
     *   resolved right now.
     *
     * How I identified the pattern:
     *   Noticed the brute force inner loop re-scans temperatures a previous
     *   iteration already looked at. That's the signal to ask: "can I
     *   remember unresolved candidates instead of re-scanning?"
     *   Verified resolution order matters by tracing an example
     *   (74, 73, 75) and confirmed the most-recently-added unresolved index
     *   must resolve first. Most-recent-first = stack.
     *
     * Design choices:
     *   - Stack holds indices, not temperatures. The temperature is always
     *     recoverable via temperatures[index], so no need for a Pair or
     *     two parallel stacks.
     *   - Inner while loop keeps resolving as long as the top of the stack
     *     is colder than the current day, not just once. A single warm day
     *     can resolve multiple waiting days at once.
     *
     * Mistakes made:
     *   - Initially compared daysIndex.peek() (an index) directly against
     *     temperatures[i] (a temperature), instead of comparing
     *     temperatures[daysIndex.peek()] against temperatures[i].
     *   - Had a redundant outer isEmpty() check wrapping the while loop,
     *     which already checks isEmpty() itself.
     *
     * Complexity:
     *   Time:  O(n) - each index is pushed once and popped at most once
     *   Space: O(n) - stack holds at most n indices in the worst case
     *   (strictly decreasing temperatures)
     */
    private static int[] dailyTemperatures(int[] temperatures) {
        int[] awaitedDays = new int[temperatures.length];
        Stack<Integer> daysIndex = new Stack<>();

        for (int i = 0; i < temperatures.length; i++) {
            while (!daysIndex.isEmpty() && temperatures[daysIndex.peek()] < temperatures[i]) {
                int waitingIndex = daysIndex.pop();
                awaitedDays[waitingIndex] = i - waitingIndex;
            }
            daysIndex.push(i);
        }

        return awaitedDays;
    }
}
