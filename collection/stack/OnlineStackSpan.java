package collection.stack;

import java.util.Stack;

/*
Leetcode 901. Online Stock Span

Design an algorithm that collects daily price quotes for some stock and returns the span of that stock's price for the current day.
The span of the stock's price in one day is the maximum number of consecutive days (starting from that day and going backward)
for which the stock price was less than or equal to the price of that day.

For example, if the prices of the stock in the last four days is [7,2,1,2] and the price of the stock today is 2,
then the span of today is 4 because starting from today, the price of the stock was less than or equal 2 for 4 consecutive days.
Also, if the prices of the stock in the last four days is [7,34,1,2] and the price of the stock today is 8,
then the span of today is 3 because starting from today, the price of the stock was less than or equal 8 for 3 consecutive days.

Implement the StockSpanner class:
StockSpanner() Initializes the object of the class.
int next(int price) Returns the span of the stock's price given that today's price is price.

Example 1:
Input
["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
[[], [100], [80], [60], [70], [60], [75], [85]]
Output: [null, 1, 1, 1, 2, 1, 4, 6]
Explanation
StockSpanner stockSpanner = new StockSpanner();
stockSpanner.next(100); // return 1
stockSpanner.next(80);  // return 1
stockSpanner.next(60);  // return 1
stockSpanner.next(70);  // return 2
stockSpanner.next(60);  // return 1
stockSpanner.next(75);  // return 4, because the last 4 prices (including today's price of 75) were less than or equal to today's price.
stockSpanner.next(85);  // return 6

Constraints:
            1 <= price <= 10^5
            At most 10^4 calls will be made to next.
 */
public class OnlineStackSpan {

    public static void main(String[] args) {
        StockSpanner stockSpanner = new StockSpanner();
        System.out.println(stockSpanner.next(100));
        System.out.println(stockSpanner.next(80));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(70));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(75));
        System.out.println(stockSpanner.next(85));

    }

    private static class StockSpanner {
        private final Stack<Integer> stack;
        private final Stack<Integer> spanStack;

        public StockSpanner() {
            stack = new Stack<>();
            spanStack = new Stack<>();
        }

        /**
         * Method: next(int price)
         * <p>
         * What it does:
         * This method returns the stock span for today's price.
         * The span is defined as the number of consecutive days
         * (including today) for which the stock price was less than
         * or equal to today's price.
         * <p>
         * Core Idea:
         * We use a Monotonic Decreasing Stack to efficiently track
         * previous prices that are greater than the current price.
         * <p>
         * Instead of checking every previous day one by one,
         * we compress spans using an additional stack that stores
         * the span associated with each price.
         * <p>
         * Why two stacks:
         * - stack → stores stock prices.
         * - spanStack → stores the span of each price.
         * <p>
         * The trick:
         * When today's price is greater than or equal to
         * previous prices, those previous prices can never
         * affect future span calculations.
         * So we pop them and accumulate their spans.
         * <p>
         * Step-by-step logic:
         * <p>
         * 1. Initialize currentSpan as 1.
         * Every price has at least a span of 1 (itself).
         * <p>
         * 2. While:
         * stack is not empty AND
         * current price >= top of price stack
         * <p>
         * We pop:
         * - the previous price
         * - its stored span
         * <p>
         * And we add that span to currentSpan.
         * <p>
         * This is the key optimization.
         * Instead of counting day by day,
         * we jump directly by adding precomputed spans.
         * <p>
         * 3. Push today's price into stack.
         * <p>
         * 4. Push currentSpan into spanStack.
         * <p>
         * 5. Return currentSpan.
         * <p>
         * Example:
         * Prices: 100, 80, 60, 70, 60, 75, 85
         * <p>
         * When 75 comes:
         * - It removes 60 (span 1)
         * - It removes 70 (span 2)
         * Total span becomes 1 + 1 + 2 = 4
         * <p>
         * When 85 comes:
         * - It removes 75 (span 4)
         * - It removes 80 (span 1)
         * Total span becomes 1 + 4 + 1 = 6
         * <p>
         * Why this works:
         * Each price is pushed once.
         * Each price is popped at most once.
         * <p>
         * That guarantees amortized O(1) time per call.
         * <p>
         * Time Complexity:
         * Amortized O(1) per next() call
         * O(n) total for n calls
         * <p>
         * Space Complexity:
         * O(n) for storing prices and spans in worst case
         * <p>
         * Interview Insight:
         * This is a classic "Monotonic Stack with Span Compression" pattern.
         * Whenever you see:
         * - Consecutive previous elements
         * - Greater than or equal conditions
         * - Span or distance accumulation
         * <p>
         * Think: Monotonic Stack + Store aggregated information.
         */
        public int next(int price) {
            int currentSpan = 1; // Start with 1 (today's day)

            // While today's price is >= the top of our prices stack
            while (!stack.isEmpty() && price >= stack.peek()) {
                stack.pop();
                currentSpan += spanStack.pop();
            }

            // Push the new price and its total accumulated span
            stack.push(price);
            spanStack.push(currentSpan);

            return currentSpan;
        }
    }
}
