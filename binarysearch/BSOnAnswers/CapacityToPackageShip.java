package binarysearch.BSOnAnswers;

/*
Leetcode: 1011. Capacity To Ship Packages Within D Days

A conveyor belt has packages that must be shipped from one port to another within "days" days.
The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages on the conveyor
belt (in the order given by weights). We may not load more weight than the maximum weight capacity of the ship.
Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within days days.

Example 1:
Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
Output: 15
Explanation: A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
1st day: 1, 2, 3, 4, 5
2nd day: 6, 7
3rd day: 8
4th day: 9
5th day: 10

Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and splitting the packages into
parts like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed.

Example 2:
Input: weights = [3,2,2,4,1,4], days = 3
Output: 6
Explanation: A ship capacity of 6 is the minimum to ship all the packages in 3 days like this:
1st day: 3, 2
2nd day: 2, 4
3rd day: 1, 4
Example 3:

Input: weights = [1,2,3,1,1], days = 4
Output: 3
Explanation:
1st day: 1
2nd day: 2
3rd day: 3
4th day: 1, 1

Constraints:
            1 <= days <= weights.length <= 5 * 10^4
            1 <= weights[i] <= 500
 */

public class CapacityToPackageShip {

    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int days = 5;
        int[] weights1 = {3, 2, 2, 4, 1, 4};
        int days1 = 3;
        int[] weights2 = {1, 2, 3, 1, 1};
        int days2 = 4;

        findMaxCapacityBruteForce(weights, days);
        findMaxCapacityBruteForce(weights1, days1);
        findMaxCapacityBruteForce(weights2, days2);
        findMaxCapacityOptimized(weights, days);
        findMaxCapacityOptimized(weights1, days1);
        findMaxCapacityOptimized(weights2, days2);
    }

    /**
     * What it does:
     * Finds the smallest ship capacity required to deliver all packages within the given number of days,
     * using a brute-force approach.
     * <p>
     * How it works:
     * - Computes two boundary values:
     * - `max` = the heaviest single package → this is the minimum possible capacity
     * - `maxCapacity` = the sum of all packages → this is the maximum possible capacity (ship everything in 1 day)
     * - Iterates through all possible capacities `i` from `max` up to `maxCapacity`:
     * - For each capacity, calls `canShip()` to check if all packages can be shipped in ≤ `days`.
     * - As soon as a capacity `i` works, it prints and breaks the loop (since it’s the smallest feasible one).
     * <p>
     * Time Complexity:
     * - O(n * (sum(weights) - max(weights))):
     * - For each candidate capacity, canShip() does O(n) work.
     * - This is very slow if the sum is large, so it’s mainly for learning.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few variables.
     * <p>
     * Output:
     * Prints the smallest required capacity using brute force.
     * <p>
     * Example:
     * weights = [1,2,3,4,5,6,7,8,9,10], days = 5
     * → prints Max Capacity: 15
     */

    private static void findMaxCapacityBruteForce(int[] weights, int days) {
        int max = findMax(weights);
        int maxCapacity = 0;
        for (int weight : weights) {
            maxCapacity += weight;
        }
        for (int i = max; i <= maxCapacity; i++) {
            if (canShip(weights, days, i)) {
                System.out.println("Max Capacity: " + i);
                break;
            }
        }
    }


    /**What it does:
     * Finds the smallest ship capacity required to deliver all packages within the given number of days,
     * using binary search (optimal approach).
     * <p>
     * Why it works:
     * - The number of days needed is a **monotonic function** of capacity:
     * - If capacity increases, it never takes more days.
     * - If capacity decreases, it takes the same or more days.
     * - This makes it possible to binary search the answer.
     * <p>
     * How it works:
     * - Computes two boundary values:
     * - `low` = the heaviest single package → smallest possible capacity
     * - `high` = the sum of all packages → largest possible capacity
     * - Binary search between `low` and `high`:
     * - mid = (low + high) / 2
     * - If canShip(mid) is true (can ship in ≤ days),
     * → shrink right side (high = mid - 1) to find smaller capacity
     * - Else (mid is too small),
     * → move left side up (low = mid + 1)
     * - Loop stops when low crosses high, and `low` will be the smallest feasible capacity.
     * <p>
     * Time Complexity:
     * - O(n * log(sum(weights))):
     * - Each canShip() check is O(n),
     * - Binary search does O(log(sum(weights))) iterations.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few variables.
     * <p>
     * Output:
     * Prints the smallest required capacity using the optimized approach.
     * <p>
     * Example:
     * weights = [1,2,3,4,5,6,7,8,9,10], days = 5
     * → prints Optimized approach - Max Capacity: 15
     */

    private static void findMaxCapacityOptimized(int[] weights, int days) {
        int low = findMax(weights);
        int high = 0;

        for (int weight : weights) {
            high += weight;
        }
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canShip(weights, days, mid)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println("Optimized approach - Max Capacity: " + low);
    }

    /**
     *  What it does:
     * Checks whether all packages can be shipped within the given number of days using the given ship capacity.
     * <p>
     * How it works:
     * - Simulates loading packages in order, day by day:
     * - Start with day 1 and load = 0
     * - For each package `w`:
     * - If w > maxCapacity → impossible, return false immediately.
     * - If adding `w` to the current load ≤ maxCapacity → add it to the current day.
     * - Else → start a new day with this package, increment daysUsed.
     * - If daysUsed ever exceeds `days`, return false early.
     * - If the loop finishes and daysUsed ≤ days, return true.
     * <p>
     * Time Complexity:
     * - O(n): Visits each package once.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few counters.
     * <p>
     * Output:
     * Returns true if all packages can be shipped within `days` using this capacity.
     * <p>
     * Example:
     * weights = [3,2,2,4,1,4], days = 3, maxCapacity = 6
     * → returns true
     */

    private static boolean canShip(int[] weights, int days, int maxCapacity) {
        int daysUsed = 1;           // we start on day 1
        int load = 0;

        for (int w : weights) {
            if (w > maxCapacity) return false;     // infeasible: single item too big
            if (load + w <= maxCapacity) {
                load += w;                          // keep loading today
            } else {
                daysUsed++;                         // start a new day
                load = w;                           // put current item on the new day
                if (daysUsed > days) return false;  // early exit if we already exceed days
            }
        }
        return daysUsed <= days;
    }

    private static int findMax(int[] weights) {
        int max = weights[0];
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] > max) {
                max = weights[i];
            }
        }
        return max;
    }


}
