package array.easy_problems;

/*
Leetcode 121. Best Time to Buy and Sell Stock

You are given an array prices where prices[i] is the price of a given stock on the ith day.

You want to maximize your profit by choosing a single day to buy one stock and choosing a
different day in the future to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

Example 1:
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.

Example 2:
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.


Constraints:
            1 <= prices.length <= 10^5
            0 <= prices[i] <= 10^4
 */
public class BestTimeBuySellStock {

    public static void main(String[] args) {
        int[] prices = {2, 4, 1};
        System.out.println(maxProfitBruteForce(prices));
        System.out.println(maxProfitOptimal(prices));
    }

    /*
     * WHAT THIS METHOD DOES (BRUTE FORCE):
     * Checks every possible (buy, sell) pair where sell comes after buy, computes the
     * profit for each pair, and returns the maximum. O(N^2) time, O(1) space.
     *
     * THE SENTENCE: try every pair, track the best profit.
     *
     * ---
     *
     * Your Thought Process & Intuition:
     * 1. THE DUMBEST THING THAT WORKS: for each day i, check every future day j and
     *    compute prices[j] - prices[i]. Track the maximum across all pairs. That is
     *    every possible transaction, exhaustively checked.
     *
     * 2. WHY THIS WORKS: brute force never misses a case. Every valid pair is checked.
     *    The maximum across all of them is guaranteed correct.
     *
     * 3. WHY THIS IS SLOW: two nested loops. Outer loop runs N times, inner loop runs
     *    up to N times per outer iteration. Total: O(N^2). For N = 100,000, that is
     *    10 billion operations. Too slow.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. Inner loop starts at j = i + 1:
     *    - Why? You cannot sell before you buy. j must come after i. Starting at i + 1
     *      enforces this constraint.
     *
     * 2. Math.max to track profit:
     *    - Why? Multiple pairs might be profitable. You want the best one across all
     *      pairs, not just the first one found.
     *
     * 3. The if (prices[i] > prices[j]) continue is unnecessary:
     *    - Why? When the selling price is lower than the buying price, the profit is
     *      negative. Math.max(profit, negative) keeps the old profit. The check is
     *      redundant, the math handles it.
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: profit = 0.
     * Step 2: For each day i (the buy day):
     *           For each day j > i (the sell day):
     *             profit = max(profit, prices[j] - prices[i]).
     * Step 3: Return profit.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N^2). Two nested loops. Outer: N iterations. Inner: up to N-1 per
     *    outer. Total comparisons: N*(N-1)/2.
     * -> Space: O(1). Two variables (buyingPrice, profit). No extra data structures.
     *
     * ---
     *
     * WHAT THE BRUTE FORCE REVEALS ABOUT THE OPTIMIZATION:
     * The inner loop does one thing: find the best selling price AFTER day i. But you
     * don't need the best selling price for EACH buying day separately. You need the
     * overall best pair. Flipping the perspective, fixing the sell day and asking "what
     * was the cheapest buy before today?", replaces the inner loop with one variable:
     * the minimum price seen so far. That is the optimal solution.
     */
    private static int maxProfitBruteForce(int[] prices) {
        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[j] > prices[i]) {
                    profit = Math.max(profit, prices[j] - prices[i]);
                }
            }
        }
        return profit;
    }

    /*
     * WHAT THIS METHOD DOES (OPTIMAL):
     * Scans the prices array left to right in one pass, tracking the cheapest buying
     * price seen so far and the best profit achievable at each point. At each day, asks
     * two questions: "is today a better buy?" and "if I sell today, is that a better
     * profit?" O(N) time, O(1) space.
     *
     * THE SENTENCE: track the minimum price so far, compute profit at every step, keep
     * the maximum. One loop, two variables.
     *
     * ---
     *
     * THE "RUNNING STATE" PATTERN (BEST TIME TO BUY AND SELL STOCK)
     *
     * HOW I ARRIVED AT THIS:
     * 1. THE BRUTE FORCE SHOWED THE WASTE: the inner loop scans everything after day i
     *    looking for the best sell. But when I move to day i+1, I scan almost the same
     *    range again. The information from the previous scan is thrown away.
     *
     * 2. THE FLIP: instead of fixing the buy and scanning for the best sell, fix the
     *    sell day (today) and ask "what was the cheapest buy before today?" That cheapest
     *    buy is just the minimum price seen so far, one variable, updated as I go.
     *
     * 3. THE SIMULTANEOUS CHECK: at every day, two things happen:
     *    - Is today's price lower than my best buy so far? If yes, update.
     *    - If I sold today (today's price minus best buy), is that better than my best
     *      profit? If yes, update.
     *    Both questions, every day, one loop. Buy and sell decisions run simultaneously.
     *
     * 4. WHY THIS IS NOT A NAMED PATTERN: not two pointers (both pointers move toward
     *    each other; here there is only one scan direction). Not sliding window (no
     *    window expands or contracts). Not binary search (no sorted structure). It is
     *    simply tracking running state as you scan, one of the most basic array
     *    techniques, below the level of named patterns.
     *
     * ---
     *
     * CORE DESIGN CHOICES:
     *
     * 1. buyingPrice = prices[0], loop starts at i = 1:
     *    - Why? Day 0 is the first possible buy. No selling can happen on day 0 (nothing
     *      before it). So initialize buyingPrice to day 0 and start checking from day 1.
     *
     * 2. if/else instead of two separate ifs:
     *    - Why? If today's price is lower than buyingPrice, it CANNOT also be higher.
     *      The two conditions are mutually exclusive. The else makes that explicit.
     *    - The else also covers the equal case: prices[i] == buyingPrice gives profit 0,
     *      which loses to Math.max against any existing profit. No separate branch needed.
     *
     * 3. Math.max for profit, not a simple assignment:
     *    - Why? Profit can go up and down as buyingPrice updates. A later buy might be
     *      cheaper but the remaining sell days might have lower prices. The maximum
     *      protects the best profit found at any point, even if later profits are worse.
     *
     * 4. No buyDay index tracked:
     *    - Why? The problem asks for the profit amount, not which day to buy. Tracking
     *      the index would be correct but unnecessary. Only track what the problem asks.
     *
     * ---
     *
     * EDGE CASES (the five-point checklist):
     *
     * 1. MINIMUM INPUT: prices = [5]. One element. Loop starts at i=1, 1 < 1 is false,
     *    loop never runs. Returns 0. Correct: can't sell with only one day.
     *
     * 2. ALL SAME: prices = [3,3,3,3]. Price never less than buyingPrice, else fires
     *    every time with profit 3-3=0. Returns 0. Correct: no profit possible.
     *
     * 3. EXTREMES: strictly decreasing [5,4,3,2,1]. buyingPrice keeps updating downward,
     *    the else never fires (each new price becomes the new buyingPrice). Returns 0.
     *    Strictly increasing [1,2,3,4,5]. buyingPrice stays 1, profit updates to 1, 2,
     *    3, 4. Returns 4. Best buy at start, best sell at end. Both work.
     *
     * 4. BOUNDARY VALUES: prices[i] can be 0. prices = [0,1]. Profit = 1. No overflow
     *    risk: max price 10^4, max difference 10^4, fits in int.
     *
     * 5. NO VALID ANSWER: strictly decreasing, profit stays 0. Problem says return 0
     *    when no transaction is profitable. Matches.
     *
     * ---
     *
     * MISTAKES I ACTUALLY MADE:
     *
     * - FIRST ATTEMPT USED TWO PASSES: first loop found the cheapest buy day, second
     *   loop scanned from that day onward for the best sell. Failed on [2,4,1]: cheapest
     *   is 1 (last day), no days left to sell. The two-pass approach assumes the best
     *   buy day is globally cheapest, which is wrong when the cheapest day is too late.
     *
     * - TRIED TO MATCH A NAMED PATTERN: considered two pointers, sliding window, binary
     *   search, recursion. None fit. The solution is simpler than any named pattern: one
     *   loop, two running variables. Not every problem needs a framework. Some just need
     *   "walk through and remember what matters."
     *
     * - DID NOT STATE EDGE CASES PROACTIVELY: wrote the code and waited for the
     *   interviewer to ask. The habit is: after writing the solution, before anyone asks,
     *   walk through the five-point edge case checklist yourself. 
     *
     * ---
     *
     * ALGORITHM STEPS:
     * Step 1: buyingPrice = prices[0]. profit = 0.
     * Step 2: For each day i from 1 to end:
     *         - If prices[i] < buyingPrice: update buyingPrice (better buy found).
     *         - Else: profit = max(profit, prices[i] - buyingPrice) (check sell profit).
     * Step 3: Return profit.
     *
     * ---
     *
     * DETAILED COMPLEXITY ANALYSIS:
     * -> Time: O(N). One pass through the array. O(1) work per element (one comparison,
     *    one possible Math.max). Total: N iterations.
     * -> Space: O(1). Two variables: buyingPrice and profit. No extra data structures.
     *
     * ---
     *
     * INTERVIEW TAKEAWAY:
     * - Say the brute force first (O(N^2), every pair) and then explain the optimization:
     *   "instead of scanning forward from each buy, I track the minimum buy so far and
     *   check profit at every step."
     * - This is not two pointers, not sliding window, not any named pattern. It is a
     *   running-state scan. Saying "I considered those patterns but none fit, the solution
     *   is simpler" shows pattern literacy AND the judgment to not force a framework.
     * - State edge cases proactively after writing the code: single element, all same,
     *   decreasing, increasing, answer at boundaries. 15 seconds, five cases.
     * - The if/else is cleaner than two separate ifs. The two conditions are mutually
     *   exclusive, and the else makes that relationship visible.
     */
    private static int maxProfitOptimal(int[] prices) {
        int buyingPrice = prices[0];
        int profit = 0;
        for (int day = 1; day < prices.length; day++) {
            if (prices[day] > buyingPrice) {
                profit = Math.max(profit, prices[day] - buyingPrice);
            } else {
                buyingPrice = prices[day];
            }
        }
        return profit;
    }
}
