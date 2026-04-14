package greedy.medium_problems;

import java.util.Arrays;

/*
Problem: Shortest Job First

A software engineer is tasked with using the shortest job first (SJF) policy to calculate the average waiting time for
each process. The shortest job first also known as shortest job next (SJN) scheduling policy selects the waiting process
with the least execution time to run next. You are given an array of integers bt of size n representing the burst times
(execution times) of n processes.

Your task is to calculate the average waiting time for all processes when scheduled using the SJF policy. The waiting
time of a process is the total time a process has to wait before its execution starts, which is the sum of burst times
of all previously executed processes.

Return the floor of the average waiting time, i.e., the largest whole number less than or equal to the actual average.

Example 1
Input : bt = [4, 1, 3, 7, 2]
Output : 4
Explanation : The total waiting time is 20.
So the average waiting time will be 20/5 => 4.

Example 2
Input : bt = [1, 2, 3, 4]
Output : 2
Explanation : The total waiting time is 10.
So the average waiting time will be 10/4 => 2.

Constraints:
            1 <= n <= 10^5
            1 <= bt[i] <= 10^   5
 */
public class ShortestJobFirst {

    public static void main(String[] args) {
        int[] bt = {4, 1, 3, 7, 2};
        System.out.println(solve(bt));
    }

    /*
     * solve(int[] bt)
     *
     * WHAT THIS METHOD DOES:
     * This method calculates the average waiting time for a set of tasks using the
     * Shortest Job First (SJF) policy. It ensures that tasks that finish quickly
     * are handled first to minimize the time everyone else spends standing in line.
     * Its goal is to calculate the minimum average waiting time for a set of
     * processes given their burst times (execution times).
     *
     * SJF POLICY EXPLAINED:
     * -> WHAT: A scheduling policy that selects the process with the smallest 
     * execution time to run next.
     * -> WHY: It is mathematically proven to provide the absolute minimum average 
     * waiting time for a fixed set of processes.
     * -> WHEN: Used in environments where the duration of tasks is known beforehand 
     * (like batch processing or specific background computer tasks).
     *  
     * MENTAL MODEL:
     * "The Fast-Pass Lane." Imagine a line at a theme park. If we let the people
     * with the shortest rides go first, the total number of people standing in line
     * at any given moment drops faster. We are optimizing for "Collective Happiness"
     * by clearing the quick tasks out of the way immediately.
     *
     * CORE IDEA:
     * To get the lowest average wait time, we MUST sort the tasks from shortest
     * to longest. The waiting time for any specific task is simply the sum of the
     * burst times of every task that came before it.
     *
     * THOUGHT PROCESS:
     * 1. "If I want to minimize waiting, I should never let a long task block a
     * short one." -> Action: Sort the array.
     * 2. "The first person never waits (Wait = 0)."
     * 3. "The second person waits for the first person."
     * 4. "The third person waits for the first AND the second person."
     * 5. "I need to keep a running total of the time passed and add that 'passed
     * time' to my 'total waiting' bucket for every new person."
     *
     * INTUITION (VERY IMPORTANT):
     * SJF works because of the way waiting time accumulates. If a job with time T
     * is placed early in the schedule, it adds T to the waiting time of every
     * single job that follows it. By making T as small as possible for the
     * early slots, we add the smallest possible "penalty" to the remaining tasks.
     *
     * EDGE CASES & GUARDRAILS:
     * - Empty or Single Task: If there is only one task, the waiting time is 0.
     * - Large Burst Times: Since burst times can be up to 10^5 and there are
     * 10^5 processes, the total waiting time can exceed the limits of a
     * standard 32-bit `int`. Using `long` for calculations is safer (though the
     * return type here is `int` per instructions).
     * - Precision: We use `(double)` during division before applying `Math.floor`
     * to ensure we don't lose decimal info prematurely.
     *
     * HOW THE CODE WORKS:
     * -> Step 1: Sort the array. This is the "Greedy" step that makes SJF possible.
     * -> Step 2: Initialize `totalWaitingTime` (the time the *current* process had to wait).
     * -> Step 3: Initialize `totalAvgWaiting` (the sum of *all* processes' wait times).
     * -> Step 4: Loop through the sorted jobs. For each job, the time it waits is
     * the sum of all previous jobs' burst times.
     * -> Step 5: Add that specific wait time to our global accumulator.
     * -> Step 6: Divide the global sum by the number of processes and return the floor.
     *
     * EXAMPLE DRY RUN: bt = [4, 1, 3]
     * | Step | Sorted Job (BT) | Time Passed (Current Wait) | Total Accumulated Wait |
     * |------|-----------------|----------------------------|------------------------|
     * | Init | [1, 3, 4]       | 0                          | 0                      |
     * | 1    | 1               | 0                          | 0                      |
     * | 2    | 3               | 0 + 1 = 1                  | 0 + 1 = 1              |
     * | 3    | 4               | 1 + 3 = 4                  | 1 + 4 = 5              |
     * | Final| Average         | 5 / 3                      | Result: 1              |
     *
     * COMPLEXITY:
     * -> Time Complexity: O(n \log n). We need to consider all the possible TC and SC.
     * (The sorting step takes O(n \log n), while the subsequent loop takes O(n)).
     * -> Space Complexity: O(1) or O(n). We need to consider all the possible TC and SC.
     * (In Java, `Arrays.sort()` for primitives uses Dual-Pivot Quicksort, which has O(\log n)
     * stack space, but we aren't using additional data structures proportional to n).
     *
     * COMMON PITFALLS:
     * - Wait Time vs. Turnaround Time: Don't confuse "Waiting Time" (time spent
     * *before* starting) with "Turnaround Time" (total time from arrival to 100% completion).
     * - Accumulation Error: Ensuring you don't add the current job's burst time
     * to its own wait time. A job only waits for those *ahead* of it.
     *
     * INTERVIEW TAKEAWAY:
     * The SJF is a classic Greedy Scheduling problem. The key insight is that
     * sorting is the optimal strategy for minimizing wait-based costs. If an
     * interview question asks to "minimize average delay/wait," your first
     * instinct should be to sort the inputs.
     */
    private static int solve(int[] bt) {
        Arrays.sort(bt);
        int totalAvgWaiting = bt[0];
        int totalWaitingTime = bt[0];
        for (int i = 1; i < bt.length - 1; i++) {
            totalWaitingTime += bt[i];
            totalAvgWaiting += totalWaitingTime;
        }
        return (int) Math.floor((double) totalAvgWaiting / bt.length);
    }
}
