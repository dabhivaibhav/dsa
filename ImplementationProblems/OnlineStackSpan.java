package ImplementationProblems;

import java.util.Arrays;
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

        StockSpanner2 stockSpanner2 = new StockSpanner2();
        int[] arr = {100, 80, 60, 70, 60, 75, 85};
        System.out.println(Arrays.toString(stockSpanner2.stockSpan(arr, arr.length)));

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


    /**
     * Class: StockSpanner2
     * <p>
     * What this implementation does:
     * This version solves the Stock Span problem in an offline manner.
     * Instead of processing one price at a time like the online version,
     * it receives the entire price array and computes the span for all days.
     * <p>
     * Core Idea:
     * The span of a stock price at index i is:
     * number of consecutive previous days (including today)
     * where price <= current price.
     * <p>
     * Instead of checking backward linearly for each day,
     * we use the concept of Previous Greater Element (PGE).
     * <p>
     * Why Previous Greater Element:
     * If we know the nearest index to the left that has a strictly
     * greater price than arr[i], then:
     * <p>
     * span = i - previousGreaterIndex
     * <p>
     * If no such element exists:
     * span = i - (-1) = i + 1
     * <p>
     * So the entire problem reduces to:
     * "Find the Previous Greater Element index for every position."
     * <p>
     * --------------------------------------------
     * Method: findPGE(int[] arr)
     * --------------------------------------------
     * <p>
     * What it does:
     * Finds the index of the Previous Greater Element for every element.
     * <p>
     * Stack stores:
     * Indices of elements in decreasing order of values.
     * <p>
     * Step-by-step logic:
     * <p>
     * 1. Traverse array from left to right.
     * <p>
     * 2. For each element:
     * While stack is not empty AND
     * arr[stack.peek()] <= current element:
     * pop the stack.
     * <p>
     * Why:
     * Because any smaller or equal element can never
     * be a Previous Greater for future elements.
     * <p>
     * 3. After popping:
     * - If stack is empty → no previous greater → store -1.
     * - Else → top of stack is previous greater index.
     * <p>
     * 4. Push current index into stack.
     * <p>
     * Time Complexity:
     * O(n)
     * Each element is pushed once and popped once.
     * <p>
     * Space Complexity:
     * O(n)
     * <p>
     * --------------------------------------------
     * Method: stockSpan(int[] arr, int n)
     * --------------------------------------------
     * <p>
     * What it does:
     * Computes the stock span for every day.
     * <p>
     * Steps:
     * 1. Call findPGE(arr) to get previous greater indices.
     * 2. For each index i:
     * <p>
     * span[i] = i - PGE[i]
     * <p>
     * Why this works:
     * <p>
     * If previous greater exists at index j:
     * span = i - j
     * <p>
     * If previous greater does not exist:
     * PGE[i] = -1
     * span = i - (-1) = i + 1
     * <p>
     * That means the price was never blocked by a greater value,
     * so span stretches all the way to the beginning.
     * <p>
     * Example:
     * Prices: [100, 80, 60, 70, 60, 75, 85]
     * <p>
     * PGE indices: [-1, 0, 1, 1, 3, 1, 0]
     * <p>
     * Span:
     * index 0 → 0 - (-1) = 1
     * index 1 → 1 - 0 = 1
     * index 2 → 2 - 1 = 1
     * index 3 → 3 - 1 = 2
     * index 4 → 4 - 3 = 1
     * index 5 → 5 - 1 = 4
     * index 6 → 6 - 0 = 6
     * <p>
     * Final Span:
     * [1, 1, 1, 2, 1, 4, 6]
     * <p>
     * Interview Insight:
     * <p>
     * This solution transforms the span problem into
     * a Previous Greater Element problem.
     * <p>
     * Pattern Recognition:
     * Whenever you see:
     * - "Consecutive previous elements"
     * - "Until a greater element appears"
     * - "Distance to blocking element"
     * <p>
     * Think:
     * Monotonic Stack + Index Difference.
     * <p>
     * Comparison with Online Version:
     * - This is batch processing.
     * - Online version compresses spans dynamically.
     * - Both achieve O(n) time.
     */

    private static class StockSpanner2 {
        Stack<int[]> stack = new Stack<>();
        int index = -1;

        public StockSpanner2() {
            index = -1;
            stack.clear();
        }

        private int[] findPGE(int[] arr) {
            int n = arr.length; // Size of the array

            // To store the previous greater elements
            int[] ans = new int[n];

            // Stack to get elements in LIFO fashion
            Stack<Integer> st = new Stack<>();

            // Start traversing from the front
            for (int i = 0; i < n; i++) {
                // Get the current element
                int currEle = arr[i];

                // Pop elements from the stack until we find a greater element
                while (!st.isEmpty() && arr[st.peek()] <= currEle) {
                    st.pop();
                }

                // If the stack is empty, it means there's no greater element, so assign -1
                if (st.isEmpty())
                    ans[i] = -1;
                else
                    ans[i] = st.peek(); // Otherwise, the top element is the previous greater

                // Push the current index in the stack
                st.push(i);
            }

            // Return the result (indices of previous greater elements)
            return ans;
        }

        // Function to find the span of stock prices for each day
        public int[] stockSpan(int[] arr, int n) {
            // Get the indices of previous greater elements
            int[] PGE = findPGE(arr);

            // To store the final span results
            int[] ans = new int[n];

            // Compute the span for each element using the previous greater element indices
            for (int i = 0; i < n; i++) {
                ans[i] = i - PGE[i]; // Calculate span based on previous greater element
            }

            // Return the result (stock span for each day)
            return ans;
        }
    }
}
