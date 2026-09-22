package two_pointer.medium_problem;

/*
Leetcode 11. Container With Most Water
You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the
ith line are (i, 0) and (i, height[i]).
Find two lines that together with the x-axis form a container, such that the container contains the most water.
Return the maximum amount of water a container can store.
Notice that you may not slant the container.

Example 1:
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].
In this case, the max area of water (blue section) the container can contain is 49.

Example 2:
Input: height = [1,1]
Output: 1

Constraints:
            n == height.length
            2 <= n <= 105
            0 <= height[i] <= 10^4
 */
public class ContainWithMostWater {

    public static void main(String[] args) {

        int[] heights1 = {1,2,3,4,5};
        int[] heights2 = {2, 1, 2, 1, 1, 1, 1};
        int[] heights3 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("heights1: " + maxCapacityBruteForce(heights1));
        System.out.println("heights2: " + maxCapacityBruteForce(heights2));
        System.out.println("heights3: " + maxCapacityBruteForce(heights3));

        System.out.println("heights1: " + maxCapacityOptimal(heights1));
        System.out.println("heights2: " + maxCapacityOptimal(heights2));
        System.out.println("heights3: " + maxCapacityOptimal(heights3));
    }


    /*
     * WHAT THIS METHOD DOES (BRUTE FORCE):
     * Checks every possible pair of walls and computes the water area between them.
     * Returns the maximum. O(N^2) time, O(1) space.
     *
     * THE SENTENCE: try every pair, area = shorter wall times distance, track the max.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. j starts at i + 1:
     *    - Why? A container needs two DIFFERENT walls. j = i is zero width. j < i is
     *      the same pair reversed. Starting at i + 1 checks each unique pair once.
     *
     * 2. Math.min for the height:
     *    - Why? Water overflows the shorter wall. The taller wall's extra height above
     *      the shorter one holds nothing. The shorter wall IS the water level.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: maxArea = 0.
     * Step 2: For each i, for each j > i:
     *         area = min(heights[i], heights[j]) * (j - i).
     *         maxArea = max(maxArea, area).
     * Step 3: Return maxArea.
     *
     * DETAILED COMPLEXITY:
     * -> Time: O(N^2). N*(N-1)/2 pairs checked.
     * -> Space: O(1). Two variables.
     */
    private static int maxCapacityBruteForce(int[] heights) {
        int maxArea = 0;

        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                int area = Math.min(heights[i], heights[j]) * (j - i);
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    /*
     * WHAT THIS METHOD DOES (OPTIMAL):
     * Uses two pointers starting at opposite ends of the array, converging inward. At
     * each step computes the area, then moves the shorter wall inward (the only move
     * that can improve the result). O(N) time, O(1) space.
     *
     * THE SENTENCE: two pointers from opposite ends, always move the shorter wall,
     * because moving the taller wall can only lose width without gaining height.
     *
     * ---
     *
     * THE "CONVERGING TWO POINTERS" PATTERN (CONTAINER WITH MOST WATER)
     *
     * HOW I IDENTIFIED THE PATTERN:
     * 1. Single array: check.
     * 2. Answer depends on TWO positions: left wall and right wall.
     * 3. Starting wide (maximum distance) and narrowing makes sense because width
     *    only decreases, so you only narrow when height might compensate.
     * 4. Two variables starting at opposite ends, moving toward each other based on
     *    a decision rule: that is converging two pointers.
     *
     * ---
     *
     * WHY MOVING THE SHORTER WALL IS CORRECT (the proof):
     *
     * Standing at left and right. Left wall is shorter (height 3, right is height 7).
     * Water level = min(3, 7) = 3. The left wall is the BOTTLENECK.
     *
     * OPTION A, move the taller wall (right) inward:
     *   Width shrinks by 1. Height is STILL limited by the short wall (3). Even if the
     *   new right wall is 1000, the water level stays 3. Area is guaranteed to decrease
     *   or stay the same. This move can NEVER help.
     *
     * OPTION B, move the shorter wall (left) inward:
     *   Width shrinks by 1. But the new left wall MIGHT be taller (say height 8). Now
     *   the water level becomes min(8, 7) = 7. The height gain (3 to 7) can more than
     *   compensate for the width loss. This move MIGHT help.
     *
     * So: moving the taller wall is provably useless. Moving the shorter wall is the
     * only move with potential. We never need to check the pairs we skip (taller wall
     * moved inward with the same short wall), because they are all provably worse.
     *
     * This is why O(N) is correct and doesn't miss the optimal: every skipped pair is
     * provably non-optimal.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Compute THEN move (not move then compute):
     *    - Why? Computing at the top of the loop means the first iteration checks the
     *      widest container. No special pre-loop computation needed. If you move first,
     *      you miss the widest pair and need to compute it before the loop (duplication).
     *
     * 2. while (left < right), not for loop:
     *    - Why? Two variables move at different times based on a condition. A for loop
     *      implies one variable incrementing uniformly. The while reads more naturally
     *      when the step depends on a decision.
     *
     * 3. Equal heights fall into the else (move right):
     *    - Why is this okay? When heights are equal, both walls equally limit the water.
     *      Moving either one is correct. The optimal pair will still be found because
     *      whichever wall we keep will be checked against every future partner as the
     *      other pointer moves inward. No pair is skipped that could beat the current max.
     *
     * ---
     *
     * EDGE CASES:
     *
     * 1. MINIMUM INPUT: two elements [1, 5]. One iteration: area = 1 * 1 = 1. Pointers
     *    meet. Returns 1. Correct.
     *
     * 2. ALL SAME HEIGHTS: [3, 3, 3, 3]. First area is widest: 3 * 3 = 9. Each step
     *    narrows. First area wins. Correct.
     *
     * 3. ONE SHORT, ONE TALL: [1, 1000000]. area = 1 * 1 = 1. The short wall limits
     *    everything regardless of the tall wall. Correct.
     *
     * 4. STRICTLY INCREASING: [1, 2, 3, 4, 5]. Answer is 6 (indices 1,4 or 2,4).
     *    The widest pair (0,4) gives only 4 because the short wall (height 1) limits
     *    it. The optimal is in the middle where height and width balance. This proves
     *    the algorithm correctly narrows past weak walls to find the true best.
     *
     * 5. OPTIMAL IN THE MIDDLE: [1, 8, 6, 2, 5, 4, 8, 3, 7]. Answer is 49 (indices
     *    1 and 8, heights 8 and 7, width 7). Pointers converge and reach this pair.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     *
     * - INNER LOOP STARTED AT j = 0 INSTEAD OF j = i + 1: checked invalid pairs where
     *   j < i, producing negative widths. Math.max filtered them out (no wrong answer)
     *   but doubled the work and checked each pair twice.
     *
     * - THOUGHT IT WAS SLIDING WINDOW: both use two variables on an array, but the
     *   mechanic is different. Sliding window: both move left to right, window
     *   expands/contracts. Converging two pointers: start at opposite ends, squeeze
     *   inward. The direction of movement is the distinguishing feature.
     *
     * - DUPLICATED AREA COMPUTATION: computed area before the loop and inside it because
     *   the loop moved before computing. Fix: compute first, move second, and the
     *   pre-loop line disappears.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: left = 0, right = length - 1, maxArea = 0.
     * Step 2: While left < right:
     *         - area = min(heights[left], heights[right]) * (right - left).
     *         - maxArea = max(maxArea, area).
     *         - If heights[left] < heights[right]: left++. Else: right--.
     * Step 3: Return maxArea.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N). Each pointer moves at most N-1 times. Each step does O(1) work
     *    (one min, one max, one comparison). Total: at most N-1 iterations.
     * -> Space: O(1). Three variables: left, right, maxArea.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say "converging two pointers" in the first sentence. It frames the approach.
     * - The WHY behind moving the shorter wall is the interview probe. Know the proof:
     *   moving the taller wall is provably useless (height still bottlenecked, width
     *   shrinks). State it before being asked.
     * - Compute then move, not move then compute. Avoids pre-loop duplication.
     * - State edge cases proactively: two elements, all same, one short one tall.
     */
    private static int maxCapacityOptimal(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        int maxArea = 0;

        while (left < right) {
            // area = shorter wall x distance between walls
            int area = Math.min(heights[left], heights[right]) * (right - left);
            maxArea = Math.max(maxArea, area);

            // move the shorter wall inward: the taller wall can never be the bottleneck,
            // so moving it can only lose width without gaining height
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
