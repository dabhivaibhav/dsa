package greedy.medium_problems;

import java.util.Arrays;

/*
Problem: N meetings in one room

Given one meeting room and N meetings represented by two arrays, start and end, where start[i] represents the start time
of the ith meeting and end[i] represents the end time of the ith meeting, determine the maximum number of meetings that
can be accommodated in the meeting room if only one meeting can be held at a time.

Example 1
Input : Start = [1, 3, 0, 5, 8, 5] , End = [2, 4, 6, 7, 9, 9]
Output : 4
Explanation : The meetings that can be accommodated in meeting room are (1,2) , (3,4) , (5,7) , (8,9).

Example 2
Input : Start = [10, 12, 20] , End = [20, 25, 30]
Output : 1
Explanation : Given the start and end time, only one meeting can be held in meeting room.

Constraints:
            1 <= N <= 10^5
            0 <= start[i] < end[i] <= 10^5
 */


/*
INTERVIEW QUESTIONS TO CONSIDER:

Why does sorting by start time fail?
Answer: A meeting could start at 1 AM and end at 11 PM, blocking 10 smaller meetings that start at 2 AM.

What if the constraint says start[i] <= end[i]?
Answer: Usually, meetings must strictly start AFTER the previous one ends (start > lastEnd). If they can touch,
        change the condition to >=.

Can we solve this without the Meeting class?
Answer: Yes, using a 2D array [N][2] where [i][0] is start and [i][1] is end, then sorting that 2D array.

THE PATTERN TAKEAWAY:
This is the "Interval Scheduling" pattern. Whenever you need to fit the maximum number of non-overlapping intervals into
a single resource, ALWAYS sort by the END TIME.
 */

public class NMeetingsInOneRoom {

    public static void main(String[] args) {
        int[] start = {1, 3, 0, 5, 8, 5};
        int[] end = {2, 4, 6, 7, 9, 9};
        System.out.println(maxMeetingsBruteForce(start, end));
        System.out.println(maxMeetingOptimal(start, end));
    }

    /*
    BRUTE FORCE METHOD: RECURSIVE BACKTRACKING

    THE INTUITION:
    The brute force approach is based on the idea of "Exhaustive Search."
    It asks the question: "What if I try every possible valid combination of meetings?"
    It doesn't assume any specific order.
    It treats the problem like a Decision Tree where at every step, you can pick any meeting that hasn't been used yet
    and doesn't overlap with the last one you picked.

    HOW THE ALGORITHM WORKS (STEP-BY-STEP):

    Start at time -1 (meaning the room is empty).

    Look at all N meetings.

    If a meeting 'i' has not been used AND its start time is after the current lastEndTime, you have a choice:
    Branch A: Pick this meeting. Now, the room is occupied until this meeting's end time.
    Branch B: Recursively call the function to find out how many MORE meetings you can fit after this one.

    After exploring everything following Meeting 'i', "un-pick" it (Backtrack) so you can try a different meeting in that
    same slot for the next branch of the tree.

    Keep track of the maximum meetings found across all possible branches.

    CODE EXPLANATION:

    solve() method: This is our recursive engine.

    used[] array: This keeps track of which meetings are already "in our bag" for the current branch so we don't pick
                  the same meeting twice.

    lastEndTime: This acts as the "barrier." Any new meeting must start after this time.

    1 + solve(): The '1' represents the current meeting we just picked. We add it to the result of the sub-problem.

    used[i] = false: This is the backtracking step. It resets the state so the loop can try the next meeting 'i+1' as a
                     fresh starting point.

    COMPLEXITY:
        Time: O(N!) - Because we are essentially checking permutations. For N=100,000, this will crash.
        Space: O(N) - Due to the recursion stack depth.
    */
    private static int maxMeetingsBruteForce(int[] start, int[] end) {

        return solve(start, end, -1, new boolean[start.length]);
    }

    private static int solve(int[] start, int[] end, int lastEndTime, boolean[] used) {
        int maxCount = 0;

        for (int i = 0; i < start.length; i++) {
            // Rule: Meeting must not be used AND must start after previous end
            if (!used[i] && start[i] > lastEndTime) {
                used[i] = true; // Make a choice

                // Explore this branch: 1 (current) + whatever else fits
                int count = 1 + solve(start, end, end[i], used);
                maxCount = Math.max(maxCount, count);

                used[i] = false; // Backtrack (undo choice for next branch)
            }
        }
        return maxCount;
    }


    /*
    OPTIMAL METHOD: GREEDY APPROACH

    THE INTUITION:
    To be optimal, we need a "Decision Rule" that allows us to pick a meeting without ever looking back.
    If we pick the earliest starting meeting, it might last all day (Bad).
    If we pick the shortest meeting, it might start in the middle of two others (Bad).
    BUT, if we pick the meeting that ENDS EARLIEST, we leave the maximum possible time remaining for the rest of the day.
    The "Villain" of this problem is the End Time. The sooner the room is free, the more meetings we can squeeze in.

    HOW THE ALGORITHM WORKS (STEP-BY-STEP):

    BUNDLE: First, we must keep start and end times together. If we sort them separately, we lose the meeting identity.
            We create a 'Meeting' object for this.

    SORT: Sort all meeting objects based on their END TIME in ascending order. If two end at the same time, their
          relative order doesn't strictly matter for the count.

    THE FIRST CHOICE: Always take the first meeting in the sorted list. It finishes the earliest of all possible choices.

    THE LINEAR WALK: Iterate through the rest of the meetings. For each meeting, check: "Does its start time come after
                     the end time of the last meeting I actually scheduled?"

    If YES: Schedule it. Update your 'lastEndTime' to this meeting's end time.

    If NO: Skip it. It starts before the room is free.

    CODE EXPLANATION:
    Meeting class: A simple data structure to hold the pair.
    Arrays.sort(): Uses a comparator to order meetings by end time. This is the most expensive part of the code.
    lastEndTime: We only update this when we successfully "take" a meeting.
    The Loop: We start from index 1 because index 0 (the earliest finisher) is always included in the optimal set.

    COMPLEXITY:
    Time: O(N log N) - Dominating factor is the sorting. The walk through the array is only O(N).
    Space: O(N) - We create an array of N Meeting objects.
     */
    private static int maxMeetingOptimal(int[] start, int[] end) {
        int n = start.length;
        // Bundle the data of start and end time array into one
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i]);
        }

        // Sort by END time (The Greedy Rule)
        Arrays.sort(meetings, (a, b) -> Integer.compare(a.end, b.end));

        // The Linear Walk
        int count = 1; // We always take the first meeting
        int lastEndTime = meetings[0].end;

        for (int i = 1; i < n; i++) {
            if (meetings[i].start > lastEndTime) {
                count++;
                lastEndTime = meetings[i].end;
            }
        }
        return count;
    }

    static class Meeting {
        int start, end;

        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
