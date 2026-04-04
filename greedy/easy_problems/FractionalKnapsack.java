package greedy.easy_problems;

import java.text.DecimalFormat;
import java.util.Arrays;

/*
Problem: Fractional Knapsack

You have n items; the i-th item has value val[i] and weight wt[i].
A knapsack can carry at most capacity units of weight.
You may take any fraction of an item (i.e. split items).
Return the maximum total value that can be placed in the knapsack, rounded to exactly 6 decimal places.


Example 1
Input: val = [60,100,120], wt = [10,20,30], capacity = 50
Output: 240.000000
Explanation:
 • Take item 0 (w=10, v=60)
 • Take item 1 (w=20, v=100)
 • Take 2⁄3 of item 2 (w=20, v=80)
Total value = 60 + 100 + 80 = 240

Example 2
Input: val = [60,100], wt = [10,20], capacity = 50
Output: 160.000000
Explanation: Both items fit entirely (total weight 30 ≤ 50).

Constraints:
            1 ≤ n = val.length = wt.length ≤ 10^5
            1 ≤ capacity ≤ 10^9
            1 ≤ val[i], wt[i] ≤ 10 000
 */
public class FractionalKnapsack {

    public static void main(String args[]) {
        DecimalFormat df = new DecimalFormat("###,000.000000");
        int[] val = {61, 118, 1113};
        int[] wt = {10, 20, 30};
        long capacity = 45;

        double result = fractionalKnapsack(val, wt, capacity);
        System.out.printf(df.format(result)); // Output: 240.000000
    }


    /**
     * fractionalKnapsack(int[] val, int[] wt, long cap)
     * <p>
     * What this method does:
     * <p>
     * Calculates the maximum value
     * that can be obtained in a knapsack
     * when FRACTIONS of items are allowed.
     * <p>
     * <p>
     * Core Idea:
     * <p>
     * Unlike 0/1 Knapsack,
     * we can take PART of an item.
     * <p>
     * So the best strategy is:
     * <p>
     * → Pick items with highest
     * value per unit weight first.
     * <p>
     * <p>
     * Key Concept:
     * <p>
     * value/weight ratio
     * <p>
     * This tells us:
     * <p>
     * → "Profit per unit weight"
     * <p>
     * <p>
     * Greedy Strategy:
     * <p>
     * 1. Calculate ratio for each item
     * 2. Sort items in descending order of ratio
     * 3. Pick items greedily:
     * - Take full item if possible
     * - Otherwise take fraction
     * <p>
     * <p>
     * Why Greedy Works Here:
     * <p>
     * Because we are allowed
     * to take fractions,
     * choosing locally optimal (best ratio)
     * always leads to global optimum.
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Create Item objects:
     * <p>
     * Each item stores:
     * - value
     * - weight
     * - ratio = value / weight
     * <p>
     * <p>
     * Step 2:
     * <p>
     * Sort items by ratio (descending):
     * <p>
     * Highest ratio → highest priority
     * <p>
     * <p>
     * Step 3:
     * <p>
     * Initialize:
     * <p>
     * totalValue = 0
     * remainingCap = cap
     * <p>
     * <p>
     * Step 4:
     * <p>
     * Traverse sorted items:
     * <p>
     * Case 1:
     * <p>
     * If item weight ≤ remaining capacity:
     * <p>
     * → Take whole item
     * <p>
     * totalValue += value
     * remainingCap -= weight
     * <p>
     * <p>
     * Case 2:
     * <p>
     * If item weight > remaining capacity:
     * <p>
     * → Take fraction
     * <p>
     * totalValue += ratio * remainingCap
     * <p>
     * → Knapsack becomes full
     * → Break loop
     * <p>
     * <p>
     * Step 5:
     * <p>
     * Return totalValue
     * <p>
     * <p>
     * Example:
     * <p>
     * val = [60, 100, 120]
     * wt  = [10, 20, 30]
     * cap = 50
     * <p>
     * Ratios:
     * <p>
     * [6.0, 5.0, 4.0]
     * <p>
     * Sorted:
     * <p>
     * (60,10), (100,20), (120,30)
     * <p>
     * <p>
     * Process:
     * <p>
     * Take 60 → remaining = 40
     * Take 100 → remaining = 20
     * Take 20/30 of 120 → value = 80
     * <p>
     * Total = 240
     * <p>
     * <p>
     * Complexity:
     * <p>
     * Time Complexity:
     * <p>
     * O(n log n)
     * <p>
     * → Sorting items
     * <p>
     * <p>
     * Space Complexity:
     * <p>
     * O(n)
     * <p>
     * → Storing Item objects
     * <p>
     * <p>
     * Interview Takeaway:
     * <p>
     * This is a CLASSIC GREEDY problem.
     * <p>
     * <p>
     * Key Difference:
     * <p>
     * Fractional Knapsack → Greedy works
     * 0/1 Knapsack → Requires DP
     * <p>
     * <p>
     * Golden Rule:
     * <p>
     * Always pick the item with
     * highest value/weight ratio first.
     * <p>
     * <p>
     * Pattern:
     * <p>
     * → Sort by ratio
     * → Greedy selection
     */
    public static double fractionalKnapsack(int[] val, int[] wt, long cap) {
        int n = val.length;
        Item[] items = new Item[n];

        // 1. Calculate ratios and store as Item objects
        for (int i = 0; i < n; i++) {
            items[i] = new Item(val[i], wt[i]);
        }

        // 2. Sort items by ratio in descending order
        Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));

        double totalValue = 0.0;
        long remainingCap = cap;

        for (int i = 0; i < n; i++) {
            if (remainingCap == 0) break;

            if (items[i].weight <= remainingCap) {
                // Take the whole item
                totalValue += items[i].value;
                remainingCap -= items[i].weight;
            } else {
                // Take a fraction of the item to fill the remaining capacity
                totalValue += items[i].ratio * remainingCap;
                remainingCap = 0; // Bag is full
            }
        }

        return totalValue;
    }


    static class Item {
        int value, weight;
        double ratio;

        Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
            this.ratio = (double) value / (double) weight;
        }
    }
}
