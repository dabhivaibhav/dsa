package heap.medium_problem;

/*
Leetcode 621. Task Scheduler

You are given an array of CPU tasks, each labeled with a letter from A to Z, and a number n.
Each CPU interval can be idle or allow the completion of one task. Tasks can be completed in any order,
but there's a constraint: there has to be a gap of at least n intervals between two tasks with the same label.
Return the minimum number of CPU intervals required to complete all tasks.

Example 1:
Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: A possible sequence is: A -> B -> idle -> A -> B -> idle -> A -> B.
After completing task A, you must wait two intervals before doing A again. The same applies to task B.
In the 3rd interval, neither A nor B can be done, so you idle. By the 4th interval, you can do A again as 2 intervals have passed.

Example 2:
Input: tasks = ["A","C","A","B","D","B"], n = 1
Output: 6
Explanation: A possible sequence is: A -> B -> C -> D -> A -> B.
With a cooling interval of 1, you can repeat a task after just one other task.

Example 3:
Input: tasks = ["A","A","A", "B","B","B"], n = 3
Output: 10
Explanation: A possible sequence is: A -> B -> idle -> idle -> A -> B -> idle -> idle -> A -> B.
There are only two types of tasks, A and B, which need to be separated by 3 intervals.
This leads to idling twice between repetitions of these tasks.

Constraints:
            1 <= tasks.length <= 10^4
            tasks[i] is an uppercase English letter.
            0 <= n <= 100
 */
public class TaskScheduler {

    public static void main(String[] args) {
        char[] ch = {'A', 'A', 'A', 'B', 'B', 'B'};
        System.out.println(leastIntervalBruteForce(ch, 1));
    }

    /**
     * leastIntervalBruteForce(char[] tasks, int n)
     * <p>
     * What this method does:
     * <p>
     * Simulates CPU execution step-by-step
     * to find the minimum time required
     * to complete all tasks with a cooldown.
     * <p>
     * <p>
     * Core Idea:
     * <p>
     * At every unit of time,
     * we try to pick the BEST possible task
     * that is:
     * <p>
     * 1. Still remaining
     * 2. Not in cooldown
     * <p>
     * If no such task exists,
     * we stay IDLE.
     * <p>
     * <p>
     * Mental Model:
     * <p>
     * Think of a CPU ticking like a clock:
     * <p>
     * Time → 1 → 2 → 3 → ...
     * <p>
     * At each time:
     * <p>
     * → Choose a valid task
     * → Or stay idle
     * <p>
     * <p>
     * Data Structures Used:
     * <p>
     * 1. frequency[26]
     * → Stores how many times each task appears
     * <p>
     * 2. nextAvailableTime[26]
     * → Stores when a task can be executed again
     * <p>
     * <p>
     * Thought Process:
     * <p>
     * We simulate each second.
     * <p>
     * At each time:
     * <p>
     * → Scan all 26 tasks (A–Z)
     * → Pick the one with:
     * - highest frequency
     * - and not in cooldown
     * <p>
     * <p>
     * This is a "Greedy-like brute force"
     * because we try to always execute
     * the most frequent available task.
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Count frequencies of all tasks.
     * <p>
     * <p>
     * Step 2:
     * <p>
     * Initialize:
     * <p>
     * currentTime = 0
     * totalTasks = tasks.length
     * <p>
     * <p>
     * Step 3:
     * <p>
     * While tasks are remaining:
     * <p>
     * → Increment time (clock tick)
     * <p>
     * <p>
     * Step 4:
     * <p>
     * Find the best task:
     * <p>
     * Loop from A → Z:
     * <p>
     * If:
     * - frequency[i] > 0
     * - currentTime ≥ nextAvailableTime[i]
     * <p>
     * → It is eligible
     * <p>
     * Choose the one with
     * highest frequency.
     * <p>
     * <p>
     * Step 5:
     * <p>
     * If a valid task is found:
     * <p>
     * → Execute it:
     * <p>
     * frequency--
     * <p>
     * → Set cooldown:
     * <p>
     * nextAvailableTime[i] = currentTime + n + 1
     * <p>
     * → Decrease totalTasks
     * <p>
     * <p>
     * Step 6:
     * <p>
     * If no task is available:
     * <p>
     * → CPU stays IDLE
     * <p>
     * <p>
     * Step 7:
     * <p>
     * Repeat until all tasks are done.
     * <p>
     * <p>
     * Example:
     * <p>
     * tasks = [A, A, A, B, B, B], n = 2
     * <p>
     * Timeline:
     * <p>
     * Time:  1   2   3   4   5   6   7   8
     * Task:  A   B  idle A   B  idle A   B
     * <p>
     * Output = 8
     * <p>
     * <p>
     * Key Insight:
     * <p>
     * The cooldown is enforced by:
     * <p>
     * nextAvailableTime[i] = currentTime + n + 1
     * <p>
     * Why +1?
     * <p>
     * Because current time slot is used,
     * and we need n FULL gaps after it.
     * <p>
     * <p>
     * Complexity:
     * <p>
     * Time Complexity:
     * <p>
     * O(T * 26) ≈ O(T)
     * <p>
     * Where T = total time intervals
     * (including idle time)
     * <p>
     * <p>
     * Space Complexity:
     * <p>
     * O(1)
     * <p>
     * Fixed arrays of size 26
     * <p>
     * <p>
     * Limitations:
     * <p>
     * - We simulate every time unit
     * - May include many idle steps
     * - Not optimal for large inputs
     * <p>
     * <p>
     * Interview Takeaway:
     * <p>
     * This builds strong intuition:
     * <p>
     * → How scheduling works
     * → How cooldown is enforced
     * → Why greedy alone is tricky
     * <p>
     * <p>
     * Better Approach Hint:
     * <p>
     * Instead of simulation,
     * we can use:
     * <p>
     * → Math / Greedy formula
     * <p>
     * Based on:
     * <p>
     * max frequency task
     * and number of such tasks
     * <p>
     * <p>
     * Golden Rule:
     * <p>
     * Always pick the most frequent
     * AVAILABLE task at current time.
     */
    public static int leastIntervalBruteForce(char[] tasks, int n) {
        int[] frequency = new int[26];
        int[] nextAvailableTime = new int[26];
        int totalTasks = tasks.length;

        for (char ch : tasks) {
            frequency[ch - 'A']++;

        }

        int currentTime = 0;
        while (totalTasks > 0) {
            currentTime++; // The clock ticks

            int bestTask = -1;
            int maxCount = -1;

            // 3. Scan all choices (A-Z)
            for (int i = 0; i < 26; i++) {
                if (frequency[i] > 0 && currentTime >= nextAvailableTime[i]) {
                    // In brute force, we just pick the first available or
                    // the one with the highest count (Greedy-ish Brute Force)
                    if (frequency[i] > maxCount) {
                        maxCount = frequency[i];
                        bestTask = i;
                    }
                }
            }

            // 4. Execute the choice
            if (bestTask != -1) {
                frequency[bestTask]--;
                nextAvailableTime[bestTask] = currentTime + n + 1; // Set cooldown
                totalTasks--;
            }
            // Else: if bestTask is -1, we automatically "Idle" for this currentTime
        }
        return currentTime;
    }
}
