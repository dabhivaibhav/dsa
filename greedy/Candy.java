package greedy;

import java.util.Arrays;

/*
Leetcode 135. Candy

There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
You are giving candies to these children subjected to the following requirements:
Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
Return the minimum number of candies you need to have to distribute the candies to the children.

Example 1:
Input: ratings = [1,0,2]
Output: 5
Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.

Example 2:
Input: ratings = [1,2,2]
Output: 4
Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
The third child gets 1 candy because it satisfies the above two conditions.

Constraints:
            n == ratings.length
            1 <= n <= 2 * 10^4
            0 <= ratings[i] <= 2 * 10^4
 */
public class Candy {

    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};
        System.out.println(candyBruteForce(ratings));
        int ratings2[] = {1, 2, 3, 4};
        System.out.println(candyBruteForce(ratings2));
    }

    /*
     * candyBruteForce(int[] ratings)
     *
     * WHAT THIS METHOD DOES:
     * This method calculates the minimum number of candies required to satisfy two rules:
     * 1. Every child gets at least 1 candy.
     * 2. Children with higher ratings than their immediate neighbors must get more candies.
     *
     * MENTAL MODEL:
     * "The Symmetrical Scout." Imagine you are a teacher handing out rewards. If you
     * only look at the child to the left, you might be unfair to the child on the right.
     * To be perfectly fair (and use the minimum amount), you walk down the line once
     * from left-to-right to fix "left-side" unfairness, then walk back from
     * right-to-left to fix "right-side" unfairness.
     *
     * CORE IDEA:
     * We use two "Greedy" passes. The first pass ensures every child is happier than
     * a lower-rated neighbor on their left. The second pass ensures they are also
     * happier than a lower-rated neighbor on their right, without breaking the
     * first rule.
     *
     * THOUGHT PROCESS:
     * 1. "Initially, let's give everyone 1 candy to meet the basic requirement."
     * 2. "Now, let's look forward: If a kid is smarter than the kid before them,
     * they deserve one more candy than that kid."
     * 3. "Now, let's look backward: If a kid is smarter than the kid after them,
     * they might need more candies. I'll check if they already have enough
     * from the first pass; if not, I'll give them one more than their right neighbor."
     *
     * INTUITION (VERY IMPORTANT):
     * Why do we need TWO passes? Because a single pass only has "local" information
     * from one direction. If ratings are [1, 2, 3, 2, 1], the child at the peak (3)
     * needs to be higher than BOTH the '2' on the left and the '2' on the right.
     * By taking the Math.max() during the second pass, we satisfy both
     * neighbors simultaneously using the smallest possible value.
     *
     * EDGE CASES & GUARDRAILS:
     * - Equal Ratings: If two neighbors have the same rating (e.g., [1, 2, 2]),
     * the second '2' does NOT need more candies than the first '2'. They can
     * both have different amounts as long as the "higher rating" rule isn't violated.
     * - Single Child: If `n=1`, the loops won't trigger, and they correctly get 1 candy.
     *
     * HOW THE CODE WORKS:
     * -> Step 1: Initialize a `candies` array and fill it with 1s.
     * -> Step 2: Left-to-Right Pass: Loop from index 1 to n-1. If `ratings[i] > ratings[i-1]`,
     * set `candies[i] = candies[i-1] + 1`.
     * -> Step 3: Right-to-Left Pass: Loop from index n-2 down to 0. If `ratings[i] > ratings[i+1]`,
     * update `candies[i]` to be the maximum of its current value or `candies[i+1] + 1`.
     * -> Step 4: Sum all values in the `candies` array for the final answer.
     *
     * EXAMPLE DRY RUN: ratings = [1, 0, 2]
     * | Pass | Child 0 (Rat:1) | Child 1 (Rat:0) | Child 2 (Rat:2) | Action / Note |
     * |------|-----------------|-----------------|-----------------|---------------|
     * | Init | 1               | 1               | 1               | Everyone starts with 1 |
     * | L->R | 1               | 1               | 2               | Index 2 > Index 1, so 1+1=2 |
     * | R->L | 2               | 1               | 2               | Index 0 > Index 1, so max(1, 1+1)=2 |
     * | Sum  | Total: 5        |                 |                 | 2 + 1 + 2 = 5 |
     *
     * COMPLEXITY:
     * -> Time Complexity: O(3n) - We traverse the array three times (Forward, Backward, and Sum).
     * -> Space Complexity: O(n) - We store an extra array of size n to keep track of candies.
     *
     * COMMON PITFALLS:
     * - Forgetting the `Math.max()` in the second pass. If you just set `candies[i] = candies[i+1] + 1`,
     * you might accidentally decrease the candy count and break the rule established
     * in the first pass!
     * - Using `ratings[i] >= ratings[i-1]`. The rule says "higher," not "higher or equal."
     *
     * INTERVIEW TAKEAWAY:
     * This is the Two-Pass Greedy pattern. It is common in problems where an
     * element's value depends on both its left and right neighbors. Instead of
     * complex nested loops, split the dependencies into two linear directions.
     */
    private static int candyBruteForce(int[] ratings) {

        int n = ratings.length;
        int[] candies = new int[n];

        Arrays.fill(candies, 1);

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                // We take the MAX to ensure we don't break the work
                // we did in Pass 1!
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        // Sum up the total
        int totalCandies = 0;
        for (int c : candies) totalCandies += c;

        return totalCandies;
    }

    /*
     * candyBetter(int[] ratings)
     *
     * WHAT THIS METHOD DOES:
     * This method is an optimized version of the Two-Pass Greedy approach. It calculates
     * the minimum candies required to satisfy neighbor rules while combining the
     * summation step into the second pass to reduce the total number of iterations.
     *
     * MENTAL MODEL:
     * "The Efficiency Expert." Imagine you are a teacher walking down a line of
     * children twice. On the way out (Left-to-Right), you fix one side of the problem.
     * On the way back (Right-to-Left), you fix the other side and simultaneously
     * drop the candies into a "Total" bucket. You finish the job as soon as you get
     * back to the start.
     *
     * CORE IDEA:
     * We resolve the "higher than left neighbor" rule first. Then, during the
     * "higher than right neighbor" pass, we calculate the final candy count for
     * each child on the fly and add it to our running total.
     *
     * THOUGHT PROCESS:
     * 1. "Let's give everyone 1 candy as a baseline."
     * 2. "Walk forward: If a child's rating is higher than the one before, give them
     * more than that child."
     * 3. "Walk backward: If a child's rating is higher than the one after, check if
     * they need an increase. While I'm deciding their final number, add it to the
     * total sum immediately."
     *
     * INTUITION (VERY IMPORTANT):
     *
     * The logic remains the same: a child's final candy count is the maximum * of what the left-neighbor requires and what the right-neighbor requires.
     * By waiting until the second pass to sum the values, we avoid a third loop,
     * making the code cleaner and slightly faster in practice.
     *
     * EDGE CASES & GUARDRAILS:
     * - Pre-summing the last element: Since the second loop starts at `n-2`,
     * we must initialize `totalCandies` with the value of the very last child
     * (`candies[n-1]`) so they aren't left out of the total.
     * - Array of Size 1: If the array has only one element, the loops don't run
     * and we correctly return the 1 candy initialized at the start.
     *
     * HOW THE CODE WORKS:
     * -> Step 1: Initialize the `candies` array and fill with 1s.
     * -> Step 2: Forward Pass: If `ratings[i] > ratings[i-1]`, update `candies[i]`.
     * -> Step 3: Backward Pass & Sum: Initialize `totalCandies` with the last element's value.
     * -> Step 4: Loop from `n-2` down to `0`. Update `candies[i]` using `Math.max()`
     * to satisfy the right-side neighbor.
     * -> Step 5: Immediately add the (now finalized) `candies[i]` to `totalCandies`.
     *
     * EXAMPLE DRY RUN: ratings = [1, 2, 2]
     * | Index | Ratings | Forward Pass | Backward Pass | totalCandies | Action |
     * |-------|---------|--------------|---------------|--------------|--------|
     * | -     | [1,2,2] | [1, 2, 1]    | -             | -            | L->R done |
     * | 2     | 2       | 1            | 1             | 1            | Init total with last element |
     * | 1     | 2       | 2            | max(2, 1+1)=2 | 1+2 = 3      | Right neighbor is not lower |
     * | 0     | 1       | 1            | max(1, 2+0)=1 | 3+1 = 4      | Right neighbor is not lower |
     *
     * COMPLEXITY:
     * -> Time Complexity: O(2n).
     * (One pass for Forward, and one pass that combines Backward adjustment and Summation).
     * -> Space Complexity: O(n).
     * (An auxiliary array is used to store the candy counts).
     *
     * COMMON PITFALLS:
     * - Initialization Error: Forgetting to add `candies[n-1]` to the total before
     * starting the backward loop.
     * - Over-optimization: Trying to do everything in one pass. It is impossible
     * to know a child's right-neighbor status while only walking forward!
     *
     * INTERVIEW TAKEAWAY:
     * This is an Optimized Two-Pass pattern. When you realize you are
     * iterating over the same data twice (once to calculate and once to sum),
     * look for ways to "piggyback" the sum onto the final calculation pass.
     */
    private static int candyBetter(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];

        Arrays.fill(candies, 1);

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        int totalCandies = candies[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
            totalCandies += candies[i];
        }
        return totalCandies;
    }

    /*
     * candyOptimal(int[] ratings)
     *
     * WHAT THIS METHOD DOES:
     * This is the "Space-Optimized" approach to the Candy problem. It calculates 
     * the total number of candies in a single pass without using an extra array. 
     * It treats the ratings like a landscape of mountains (ups) and valleys (downs).
     *
     * MENTAL MODEL:
     * "The Mountain Climber." Imagine you are walking across a mountain range. 
     * Every time you go uphill, you need more energy (candies). Every time you go 
     * downhill, the children also need specific amounts. The only tricky part is the 
     * "Peak" of the mountain—it must be high enough to satisfy both the people 
     * climbing up and the people sliding down.
     *
     * CORE IDEA:
     * Instead of storing candy counts for every child, we track the lengths of 
     * increasing slopes (up) and decreasing slopes (down). We use the property 
     * of "Arithmetic Progressions" (1+2+3...) to calculate the total candies 
     * needed for those slopes on the fly.
     *
     * THOUGHT PROCESS:
     * 1. "Everyone gets at least 1 candy, so let's start my total at N."
     * 2. "As I walk, am I going uphill? If yes, keep track of how high I go."
     * 3. "Am I going downhill? If yes, keep track of how deep the valley is."
     * 4. "The child at the very top (the Peak) needs to be higher than both 
     * sides. I'll compare the 'Up' height and the 'Down' height and make sure 
     * the peak matches the taller one."
     *
     * INTUITION (VERY IMPORTANT):
     * When you have a slope of length k (e.g., 3 people in a row with increasing 
     * ratings), the extra candies needed are 1 + 2 + ... + k. 
     *
     * If you have a mountain with an "uphill" of 3 people and a "downhill" of 5 
     * people, the person at the peak must satisfy both. The "Peak" naturally 
     * belongs to the longer slope. The `Math.min(peak, valley)` adjustment 
     * ensures we don't double-count the peak incorrectly or make it too short.
     *
     * EDGE CASES & GUARDRAILS:
     * - Equal Ratings: If `ratings[i] == ratings[i-1]`, the slope "resets." 
     * The neighbor rule only applies to "higher" ratings, so equal ratings 
     * allow us to start fresh with 1 candy.
     * - Peak/Valley Overlap: The code handles the peak by adding it to both 
     * counts and then subtracting the smaller overlap at the end.
     *
     * HOW THE CODE WORKS:
     * -> Step 1: Initialize `candies = n` (the baseline of 1 per child).
     * -> Step 2: Use a `while` loop to traverse the array from index 1.
     * -> Step 3: Skip Equals: If ratings are the same, just move to the next child.
     * -> Step 4: Uphill Pass: While ratings are increasing, increment `peak` 
     * and add that count to `candies`.
     * -> Step 5: Downhill Pass: While ratings are decreasing, increment `valley` 
     * and add that count to `candies`.
     * -> Step 6: The Adjustment: Subtract `Math.min(peak, valley)` from 
     * the total to fix the peak height overlap.
     *
     * EXAMPLE DRY RUN: ratings = [1, 3, 5, 2, 1]
     * | State | i | Rating | peak | valley | candies | Note |
     * |-------|---|--------|------|--------|---------|------|
     * | Start | 1 | 3      | 0    | 0      | 5       | Init candies = n |
     * | Up    | 2 | 5      | 2    | 0      | 5+1+2=8 | 2 steps up |
     * | Down  | 4 | 1      | 2    | 2      | 8+1+2=11| 2 steps down |
     * | Adjust| - | -      | 2    | 2      | 11-2=9  | min(2,2)=2. Final: 9 |
     *
     * COMPLEXITY:
     * -> Time Complexity: O(n). We need to consider all the possible TC and SC.
     * (Even with nested while loops, the index `i` only moves forward from 0 to N).
     * -> Space Complexity: O(1). We need to consider all the possible TC and SC.
     * (No extra arrays; we only use a few integer variables).
     *
     * COMMON PITFALLS:
     * - Arithmetic Logic: Forgetting that the "base" candy is already 
     * accounted for in `candies = n`.
     * - Peak Heights: If the downhill slope is longer than the uphill slope, 
     * the peak must be "pulled up" to match the downhill length.
     *
     * INTERVIEW TAKEAWAY:
     * This is a Slope/Pattern Recognition pattern. It's the "Gold Standard" 
     * for the Candy problem. Whenever you see a problem involving "Neighbors" 
     * and "Relative heights," ask yourself if you can calculate the result 
     * based on the *shape* of the data rather than storing the state of 
     * every individual element.
     */
    private static int candyOptimal(int[] ratings) {
        // Get number of children
        int n = ratings.length;

        // Initially give 1 candy to each child
        int candies = n;

        // Start from second child
        int i = 1;

        while (i < n) {

            // Skip equal ratings
            if (ratings[i] == ratings[i - 1]) {
                i++;
                continue;
            }

            // Initialize increasing slope counter
            int peak = 0;

            // Traverse strictly increasing ratings
            while (i < n && ratings[i] > ratings[i - 1]) {
                peak++;
                candies += peak;
                i++;
            }

            // Initialize decreasing slope counter
            int valley = 0;

            // Traverse strictly decreasing ratings
            while (i < n && ratings[i] < ratings[i - 1]) {
                valley++;
                candies += valley;
                i++;
            }

            // Remove overlapping candy at the peak
            candies -= Math.min(peak, valley);
        }

        // Return total candies required
        return candies;
    }


}
