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


}
