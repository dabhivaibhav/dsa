package binarysearch.BSOnAnswers;

/*
Leetcode: 1482. Minimum Number of Days to Make m Bouquets

You are given an integer array bloomDay, an integer m and an integer k.
You want to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.
The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet.
Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible to
make m bouquets return -1.

Example 1:
Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
Output: 3
Explanation: Let us see what happened in the first three days. x means flower bloomed and _ means flower did not bloom
in the garden.
We need 3 bouquets each should contain 1 flower.
After day 1: [x, _, _, _, _]   // we can only make one bouquet.
After day 2: [x, _, _, _, x]   // we can only make two bouquets.
After day 3: [x, _, x, _, x]   // we can make 3 bouquets. The answer is 3.

Example 2:
Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
Output: -1
Explanation: We need 3 bouquets each has 2 flowers, that means we need 6 flowers. We only have 5 flowers so it is impossible
to get the needed bouquets and we return -1.

Example 3:
Input: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
Output: 12
Explanation: We need 2 bouquets each should have 3 flowers.

Here is the garden after the 7 and 12 days:
After day 7: [x, x, x, x, _, x, x]
We can make one bouquet of the first three flowers that bloomed. We cannot make another bouquet from the last three
flowers that bloomed because they are not adjacent.
After day 12: [x, x, x, x, x, x, x]
It is obvious that we can make two bouquets in different ways.

Constraints:
            bloomDay.length == n
            1 <= n <= 10^5
            1 <= bloomDay[i] <= 10^9
            1 <= m <= 10^6
            1 <= k <= n
 */
public class MinimumDaysMakeBouquets {
    public static void main(String[] args) {

        int[] bloomDays = {1, 10, 3, 10, 2};
        int m = 3;
        int k = 1;
        int[] bloomDays1 = {1, 10, 3, 10, 2};
        int m1 = 3;
        int k1 = 2;
        int[] bloomDays2 = {7, 7, 7, 7, 12, 7, 7};
        int m2 = 2;
        int k2 = 3;
        System.out.println("Optimal Approach: Minimum days to make " + m + " bouquets is: " + findMinimumDay(bloomDays, m, k));
        System.out.println("Better Approach: Minimum days to make " + m1 + " bouquets is: " + findMinimumDay(bloomDays1, m1, k1));
        System.out.println("Better Approach: Minimum days to make " + m2 + " bouquets is: " + findMinimumDay(bloomDays2, m2, k2));
    }

    /**
     * What it does:
     * Finds the minimum number of days needed for enough flowers to bloom so that Koko can make `m` bouquets,
     * where each bouquet requires `k` adjacent bloomed flowers.
     * <p>
     * Why it works:
     * - Each flower blooms on a specific day given by bloomDay[i].
     * - After a flower blooms, it can be used exactly once in a bouquet.
     * - We want the smallest day `D` such that at least `m` bouquets of size `k` can be made from
     * the flowers that have bloomDay ≤ D.
     * <p>
     * Observations:
     * - If a certain day `D` is enough to make `m` bouquets, then any day > D will also be enough.
     * - If a day `D` is not enough, then any day < D will also not be enough.
     * - This is a **monotonic property**, which makes binary search the perfect approach.
     * <p>
     * How it works:
     * - First, check if it's even possible: if `m*k > bloomDay.length`, return -1 immediately
     * (not enough flowers overall).
     * - Determine the search range for days:
     * - low = minimum bloom day among all flowers (earliest any flower blooms)
     * - high = maximum bloom day among all flowers (latest any flower blooms)
     * - Perform binary search on this day range:
     * - For mid = (low + high) / 2, check if it's possible to make `m` bouquets by day mid using canMake().
     * - If yes, try earlier days (high = mid).
     * - If no, try later days (low = mid + 1).
     * - When the loop finishes, low will be the smallest day that works.
     * <p>
     * Time Complexity:
     * - O(n * log(maxDay - minDay)):
     * - Binary search does O(log(range of days)) steps.
     * - Each step checks all n flowers in O(n) via canMake().
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few counters and pointers.
     * <p>
     * Output:
     * Returns the smallest number of days needed to make `m` bouquets of `k` adjacent flowers.
     * <p>
     * Example:
     * bloomDay = [1,10,3,10,2], m = 3, k = 1
     * → returns 3
     */

    private static int findMinimumDay(int[] bloomDay, int m, int k) {
        long need = (long) m * k;
        if (bloomDay.length < need) return -1;

        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
        for (int d : bloomDay) {
            if (d < low) low = d;
            if (d > high) high = d;
        }

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (canMake(bloomDay, m, k, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * What it does:
     * Checks whether it's possible to make at least `m` bouquets of size `k` from the flowers
     * that have bloomed on or before a given day `D`.
     * <p>
     * Why it works:
     * - We scan the bloomDay array from left to right.
     * - If bloomDay[i] ≤ D, it means the flower has bloomed and is usable.
     * - We count how many **consecutive bloomed flowers** we see:
     * - Every time the count reaches `k`, it forms one bouquet (increase `made` by 1, reset count to 0).
     * - If we ever reach `m` bouquets, return true early.
     * - If we hit a flower that hasn't bloomed yet (bloomDay[i] > D), we reset the consecutive counter.
     * - At the end, return true if `made >= m`, otherwise false.
     * <p>
     * Time Complexity:
     * - O(n): Goes through all flowers once.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few counters.
     * <p>
     * Output:
     * Returns true if it's possible to make `m` bouquets of `k` adjacent flowers by day `D`; otherwise false.
     * <p>
     * Example:
     * days = [1,10,3,10,2], m = 3, k = 1, D = 3
     * → returns true
     */
    private static boolean canMake(int[] days, int m, int k, int D) {
        int consec = 0;
        int made = 0;
        for (int d : days) {
            if (d <= D) {
                consec++;
                if (consec == k) {
                    made++;
                    if (made >= m) return true;
                    consec = 0;
                }
            } else {
                consec = 0;
            }
        }
        return false;
    }

}
