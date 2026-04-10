package greedy.medium_problems;

/*
Leetcode 45. Jump Game II

You are given a 0-indexed array of integers nums of length n. You are initially indexed at index 0.

Each element nums[i] represents the maximum length of a forward jump from index i. In other words, if you are at index i,
you can jump to any index (i + j) where:
0 <= j <= nums[i] and
i + j < n
Return the minimum number of jumps to reach index n - 1. The test cases are generated such that you can reach index n - 1.

Example 1:
Input: nums = [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to
the last index.

Example 2:
Input: nums = [2,3,0,1,4]
Output: 2


Constraints:
            1 <= nums.length <= 10^4
            0 <= nums[i] <= 1000
            It's guaranteed that you can reach nums[n - 1].
 */
public class JumpGameII {

    public static void main(String args[]) {
        int[] arr = {2, 3, 1, 1, 4};
        System.out.println(canJumpBruteForce(arr));
        System.out.println(canJumpOptimal(arr));
        int[] arr1 = {2, 3, 1, 0, 4};
        System.out.println(canJumpBruteForce(arr1));
        System.out.println(canJumpOptimal(arr1));
    }


    private static int canJumpBruteForce(int[] nums) {
        return canJumpRecursion(0, nums);
    }


    /**
     * canJumpRecursion(int index, int[] nums)
     *
     * WHAT THIS METHOD DOES:
     * This is the "Brute Force" approach. It explores every possible jump combination
     * to find the absolute minimum number of steps to reach the end. It’s like
     * checking every single path in a maze to find the shortest one.
     *
     * MENTAL MODEL:
     * **"The Multiverse Explorer."** Imagine you are at a crossroad. You split yourself
     * into clones. Each clone takes a different number of steps (1 step, 2 steps, etc.).
     * Each clone then splits again. Eventually, the clone that reaches the finish line
     * in the fewest steps reports back.
     *
     * CORE IDEA:
     * At any index, you don't know which jump is best. So, you try all of them (from
     * 1 up to the value at nums[index]), calculate the cost for each path, and pick
     * the minimum.
     *
     * THOUGHT PROCESS:
     * 1. "I am at index i."
     * 2. "I can jump anywhere between 1 and nums[i]."
     * 3. "For every spot I land on, I'll ask: 'What’s the shortest way from here to the end?'"
     * 4. "I'll add 1 (the jump I just took) to those results and keep the smallest one."
     *
     * INTUITION (VERY IMPORTANT):
     * This works because it is exhaustive. By calculating every possibility, we
     * guarantee we find the minimum. However, it is inefficient because different
     * "clones" often end up at the same index and re-calculate the same path over
     * and over. This is known as "Overlapping Subproblems."
     *
     * EDGE CASES & GUARDRAILS:
     * - Success: If index >= last index, we reached the goal (return 0).
     * - Dead End: If nums[index] == 0, we are stuck (return Integer.MAX_VALUE).
     * - Overflow: We check for MAX_VALUE before adding 1 to avoid rolling over to a negative number.
     *
     * HOW THE CODE WORKS:
     * -> Step 1: Check if we are at the end. If so, return 0.
     * -> Step 2: Check if we are on a 0. If so, return "Infinity" (MAX_VALUE).
     * -> Step 3: Create a loop to try every jump size from 1 to nums[index].
     * -> Step 4: Recursively call the function for the new index.
     * -> Step 5: Update minStep if (1 + subResult) is smaller than current min.
     *
     * EXAMPLE DRY RUN: nums = [2, 1, 1]
     * | Current Index | Jumps Possible | Recursive Calls | Result         |
     * |---------------|----------------|-----------------|----------------|
     * | 0             | 1, 2           | jump(1), jump(2)| min(2, 1) = 1  |
     * | 1             | 1              | jump(2)         | 1              |
     * | 2             | Goal!          | -               | 0              |
     *
     * COMPLEXITY:
     * -> Time Complexity: $O(M^N)$ where N is length and M is max jump value.
     * -> Space Complexity: $O(N)$ for the recursion stack.
     *
     * COMMON PITFALLS:
     * - Adding 1 to Integer.MAX_VALUE without checking (causes negative numbers).
     * - Not stopping the loop at the array boundary.
     *
     * INTERVIEW TAKEAWAY:
     * This is the **Top-Down Recursion** pattern. In an interview, start here to
     * show you understand the logic, then explain how you would use "Memoization"
     * or a "Greedy" approach to optimize it.
     */
    private static int canJumpRecursion(int index, int[] nums) {
        // If current index is at or beyond the last index
        if (index >= nums.length - 1) return 0;

        // If stuck at a 0
        if (nums[index] == 0) return Integer.MAX_VALUE;

        int minStep = Integer.MAX_VALUE;

        // Try every jump from 1 to nums[index]
        for (int jump = 1; jump <= nums[index]; jump++) {
            int subResult = canJumpRecursion(index + jump, nums);
            if (subResult != Integer.MAX_VALUE) {
                minStep = Math.min(minStep, 1 + subResult);
            }
        }
        return minStep;
    }


    /**
     * canJumpOptimal(int[] nums)
     *
     * WHAT THIS METHOD DOES:
     * This method finds the minimum jumps using a "Greedy" strategy. It calculates
     * the minimum jumps by tracking the farthest possible "horizon" we can reach
     * with each jump.
     *
     * MENTAL MODEL:
     * **"The Relay Race."** Imagine you are carrying a baton. Your "current jump"
     * defines a zone. As you run through this zone, you look at every teammate
     * standing there and see who can run the farthest forward. You don't pass the
     * baton (increment jump) until you reach the very end of your current zone.
     * When you do, you pass it to the teammate who had the best "reach."
     *
     * CORE IDEA:
     * We move through the array and keep track of the maximum reach we can achieve.
     * We only "commit" to a jump when we have exhausted our current reach.
     *
     * THOUGHT PROCESS:
     * 1. "I have a current range I can reach (currentEnd)."
     * 2. "As I walk through this range, I'll look for the best next jump (farthest)."
     * 3. "Once I hit the end of my current range, I MUST jump. I'll take the best
     * jump I found during my walk."
     *
     * INTUITION (VERY IMPORTANT):
     *
     * Why does this work? Because it uses **BFS logic in 1D**. Each jump represents
     * a "layer." All indices reachable in 1 jump are Layer 1; all indices reachable
     * from Layer 1 are Layer 2. By jumping only when we reach the end of a "layer,"
     * we naturally find the minimum number of jumps (layers) needed.
     *
     * EDGE CASES & GUARDRAILS:
     * - Loop Limit: Stop at `nums.length - 2`. If you are at the last index, you are
     * already home—no need to jump again.
     * - Single Element: If the array length is 1, the loop never runs, returning 0.
     *
     * HOW THE CODE WORKS:
     * -> Step 1: Initialize `jumps` (count), `currentEnd` (boundary of current jump),
     * and `farthest` (max potential reach seen so far).
     * -> Step 2: Iterate through the array up to the second-to-last element.
     * -> Step 3: At every step, update `farthest = Math.max(farthest, i + nums[i])`.
     * -> Step 4: If current index `i` reaches `currentEnd`, increment `jumps`.
     * -> Step 5: Update `currentEnd` to the `farthest` point discovered.
     *
     * EXAMPLE DRY RUN: nums = [2, 3, 1, 1, 4]
     * | i | nums[i] | farthest | currentEnd | jumps | Action                       |
     * |---|---------|----------|------------|-------|------------------------------|
     * | 0 | 2       | 2        | 0          | 1     | i == currentEnd: Jump! End=2 |
     * | 1 | 3       | 4        | 2          | 1     | Update farthest to 4         |
     * | 2 | 1       | 4        | 2          | 2     | i == currentEnd: Jump! End=4 |
     * | 3 | 1       | 4        | 4          | 2     | Update farthest (no change)  |
     *
     * COMPLEXITY:
     * -> Time Complexity: $O(n)$ - One single pass through the array.
     * -> Space Complexity: $O(1)$ - Only three integer variables used.
     *
     * COMMON PITFALLS:
     * - Looping all the way to `nums.length - 1`. If the current index hits the
     * last element, the `i == currentEnd` check might trigger an unnecessary
     * extra jump.
     *
     * INTERVIEW TAKEAWAY:
     * The **Greedy Reach** pattern. When a problem asks for "minimum steps" in a
     * range-based movement, track the "farthest reachable" boundary and only
     * increment your counter when that boundary is hit.
     */
    public static int canJumpOptimal(int[] nums) {
        // Initialize jumps and range trackers
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        // Loop through array up to second last index
        for (int i = 0; i < nums.length - 1; i++) {
            // Update the farthest index we can reach
            farthest = Math.max(farthest, i + nums[i]);

            // If current index reaches the end of current range
            if (i == currentEnd) {
                // Increment jump count
                jumps++;

                // Update range to the farthest index
                currentEnd = farthest;
            }
        }

        // Return the total number of jumps
        return jumps;
    }
}
