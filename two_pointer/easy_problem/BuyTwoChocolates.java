package two_pointer.easy_problem;

import java.util.Arrays;

/*
Leetcode 2706: Buy Two Chocolates

You are given an integer array prices representing the prices of various chocolates in a store.
You are also given a single integer money, which represents your initial amount of money.
You must buy exactly two chocolates in such a way that you still have some non-negative leftover money.
You would like to minimize the sum of the prices of the two chocolates you buy.
Return the amount of money you will have leftover after buying the two chocolates.
If there is no way for you to buy two chocolates without ending up in debt, return money.
Note that the leftover must be non-negative.

Example 1:
Input: prices = [1,2,2], money = 3
Output: 0
Explanation: Purchase the chocolates priced at 1 and 2 units respectively.
You will have 3 - 3 = 0 units of money afterwards. Thus, we return 0.

Example 2:
Input: prices = [3,2,3], money = 3
Output: 3
Explanation: You cannot buy 2 chocolates without going in debt, so we return 3.

Constraints:
            2 <= prices.length <= 50
            1 <= prices[i] <= 100
            1 <= money <= 100
 */
public class BuyTwoChocolates {

    public static void main(String[] args) {
        int[] prices = {1, 2, 2};
        int money = 3;
        System.out.println(buyChocolateBruteForce(prices, money));
        int[] prices1 = {3, 2, 3};
        int money1 = 3;
        System.out.println(buyChocolateBruteForce(prices1, money1));
    }


    /**
     * buyChocolateBruteForce(int[] prices, int money)
     * <p>
     * What this method does:
     * <p>
     * Finds the amount of money left after buying exactly two chocolates
     * with the minimum possible total cost, while ensuring
     * the leftover money is non-negative.
     * <p>
     * If buying two chocolates would result in debt,
     * the original money is returned.
     * <p>
     * Core Idea:
     * <p>
     * The cheapest way to buy two chocolates is to pick
     * the two smallest prices in the array.
     * <p>
     * Sorting the array ensures the smallest elements
     * appear at the beginning.
     * <p>
     * Thought Process:
     * <p>
     * We must buy exactly two chocolates.
     * <p>
     * To minimize total cost:
     * select the two smallest prices.
     * <p>
     * Sorting makes it easy to access them directly.
     * <p>
     * After finding the cost:
     * <p>
     * If cost ≤ money:
     * return remaining money.
     * <p>
     * If cost > money:
     * return original money.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Sort the prices array
     * <p>
     * Arrays.sort(prices)
     * <p>
     * This rearranges prices in ascending order.
     * <p>
     * Example:
     * prices = [3, 1, 2]
     * becomes
     * prices = [1, 2, 3]
     * <p>
     * Step 2: Select the two cheapest chocolates
     * <p>
     * prices[0] → cheapest
     * prices[1] → second cheapest
     * <p>
     * cost = prices[0] + prices[1]
     * <p>
     * Step 3: Check if purchase is possible
     * <p>
     * If cost ≤ money:
     * <p>
     * leftover = money − cost
     * <p>
     * return leftover
     * <p>
     * Step 4: Handle debt condition
     * <p>
     * If cost > money:
     * <p>
     * Buying would result in negative money.
     * <p>
     * Return original money.
     * <p>
     * Example 1:
     * <p>
     * prices = [1,2,2], money = 3
     * <p>
     * After sorting:
     * [1,2,2]
     * <p>
     * cost = 1 + 2 = 3
     * <p>
     * leftover = 3 − 3 = 0
     * <p>
     * Return 0
     * <p>
     * Example 2:
     * <p>
     * prices = [3,2,3], money = 3
     * <p>
     * After sorting:
     * [2,3,3]
     * <p>
     * cost = 2 + 3 = 5
     * <p>
     * cost > money
     * <p>
     * Return 3
     * <p>
     * Complexity:
     * <p>
     * Sorting takes O(n log n)
     * <p>
     * Access takes O(1)
     * <p>
     * Time Complexity: O(n log n)
     * <p>
     * Space Complexity: O(1)
     * <p>
     * Interview Takeaway:
     * <p>
     * Sorting guarantees the two smallest values,
     * but sorting the entire array is more work than needed.
     * <p>
     * We can find the two smallest values in one pass
     * using a linear scan.
     *
     */
    private static int buyChocolateBruteForce(int[] prices, int money) {

        // Sort the prices
        Arrays.sort(prices);

        // The two cheapest are now at the start
        int cost = prices[0] + prices[1];

        // Check the debt rule
        if (cost <= money) {
            return money - cost;
        }
        return money; // Return original money if we'd go into debt
    }

    /**
     * buyChocolateOptimized(int[] prices, int money)
     * <p>
     * What this method does:
     * <p>
     * Finds the amount of money left after buying exactly two chocolates
     * with minimum cost using a single pass,
     * without sorting the array.
     * <p>
     * Returns the leftover money if purchase is possible,
     * otherwise returns original money.
     * <p>
     * Core Idea:
     * <p>
     * Instead of sorting the entire array,
     * track the two smallest values while scanning once.
     * <p>
     * Maintain:
     * <p>
     * min1 → smallest price found so far
     * min2 → second smallest price found so far
     * <p>
     * Thought Process:
     * <p>
     * We only need the two smallest elements,
     * not the entire sorted order.
     * <p>
     * While scanning the array:
     * <p>
     * If current price < min1:
     * shift min1 to min2
     * update min1
     * <p>
     * Else if current price < min2:
     * update min2
     * <p>
     * At the end:
     * <p>
     * min1 and min2 are the two cheapest chocolates.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Initialize variables
     * <p>
     * min1 = Integer.MAX_VALUE
     * min2 = Integer.MAX_VALUE
     * <p>
     * These represent the smallest and second smallest prices.
     * <p>
     * Step 2: Traverse the array
     * <p>
     * For each price p:
     * <p>
     * Case 1:
     * If p < min1
     * <p>
     * Then:
     * min2 = min1
     * min1 = p
     * <p>
     * <p>
     * Case 2:
     * Else if p < min2
     * <p>
     * Then:
     * min2 = p
     * <p>
     * This ensures min1 and min2 always store
     * the two smallest values seen so far.
     * <p>
     * Step 3: Calculate total cost
     * <p>
     * cost = min1 + min2
     * <p>
     * Step 4: Check purchase condition
     * <p>
     * If cost ≤ money:
     * return money − cost
     * <p>
     * Else:
     * return money
     * <p>
     * Example:
     * <p>
     * prices = [1,2,2], money = 3
     * <p>
     * Iteration:
     * <p>
     * p=1 → min1=1, min2=∞
     * p=2 → min1=1, min2=2
     * p=2 → no change
     * <p>
     * cost = 3
     * <p>
     * leftover = 0
     * <p>
     * Complexity:
     * <p>
     * Single scan of array.
     * <p>
     * Time Complexity: O(n)
     * <p>
     * Space Complexity: O(1)
     * <p>
     * Interview Takeaway:
     * <p>
     * This is the optimal solution.
     * <p>
     * Sorting is unnecessary when only two minimum values are required.
     * <p>
     * Tracking minimum values in one pass reduces
     * time complexity from O(n log n) to O(n).
     * <p>
     * This pattern is very common in interviews
     * when finding smallest, largest, or top-k elements.
     *
     */
    /*
    THE "BUDGET SHOPPER" STRATEGY

    THE BRUTE/SORT APPROACH:
    Sort the array (Arrays.sort). The two cheapest are at index 0 and 1.
    - Time: O(N log N)
    - Good for: Quick coding, very readable.

    THE OPTIMAL APPROACH:
    Scan the array once (O(N)). Keep track of 'min1' and 'min2'.
    - Like holding two small items in your hands and swapping if you find
      something smaller on the shelf.
    - Time: O(N)
    - Space: O(1)

    Click!! MOMENT:
    Sorting is often "overkill" if you only need the Top 2 or Bottom 2
    elements. A simple scan (or a Heap!) is usually better.
    */
    private static int buyChocolateOptimized(int[] prices, int money) {
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int p : prices) {
            if (p < min1) {
                min2 = min1; // Old #1 becomes #2
                min1 = p;    // New #1
            } else if (p < min2) {
                min2 = p;    // New #2
            }
        }
        int cost = min1 + min2;
        return (cost <= money) ? (money - cost) : money;
    }
}
