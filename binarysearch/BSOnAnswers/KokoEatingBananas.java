package binarysearch.BSOnAnswers;

/*
LeetCode: 875. Koko Eating Bananas

Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas.
The guards have gone and will come back in h hours. Koko can decide her bananas-per-hour eating speed of k. Each hour,
she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of
them instead and will not eat any more bananas during this hour. Koko likes to eat slowly but still wants to finish
eating all the bananas before the guards return. Return the minimum integer k such that she can eat all the bananas
within h hours.

Example 1:
Input: piles = [3,6,7,11], h = 8
Output: 4

Example 2:
Input: piles = [30,11,23,4,20], h = 5
Output: 30

Example 3:
Input: piles = [30,11,23,4,20], h = 6
Output: 23

Constraints:
            1 <= piles.length <= 10^4
            piles.length <= h <= 10^9
            1 <= piles[i] <= 10^9
 */
public class KokoEatingBananas {

    public static void main(String[] args) {
        int[] piles = {3, 6, 7, 11};
        int h = 8;
        int[] piles1 = {30, 11, 23, 4, 20};
        int h1 = 5;

        System.out.println(findKBruteforce(piles, h));
        System.out.println(findKBruteforce(piles1, h1));
        System.out.println(findKOptimal(piles, h));
        System.out.println(findKOptimal(piles1, h1));
    }


    /**
     * What it does:
     * Finds the minimum eating speed `k` (bananas per hour) at which Koko can finish all the bananas
     * within `h` hours using a brute-force approach.
     * <p>
     * Why it works:
     * - Koko must eat all piles within `h` hours.
     * - Eating faster means fewer hours needed, and eating slower means more hours.
     * - The possible speeds `k` range from:
     * - Minimum: 1 banana/hour (the slowest possible speed Koko can choose)
     * - Maximum: max(piles) bananas/hour (the largest pile size)
     * - Why max(piles) is the upper bound:
     * - If she eats at a speed equal to the largest pile size, she can finish that pile in 1 hour,
     * and every other pile will take ≤ 1 hour at that same speed.
     * - Any speed faster than the largest pile is unnecessary because she will still spend exactly 1 hour
     * on that largest pile (ceil(max/max) = 1). So this is the logical ceiling of our search space.
     * - The algorithm linearly tests every speed `i` from 1 up to max(piles):
     * - For each `i`, it calls calculateTotalHours() to find how many hours it would take to eat all piles at speed `i`.
     * - As soon as the required hours are ≤ `h`, it returns `i` as the answer (the slowest valid speed).
     * <p>
     * Time Complexity:
     * - O(maxPile * n):
     * - Outer loop tries all speeds from 1 to max(piles)
     * - Inner calculation is O(n) per speed.
     * - In the worst case, max(piles) could be up to 10^9, which makes this approach impractical for large inputs.
     * <p>
     * Space Complexity:
     * - O(1): Uses only constant extra variables.
     * <p>
     * Output:
     * Returns the smallest integer speed `k` at which Koko can finish all bananas within `h` hours.
     * <p>
     * Example:
     * piles = [3, 6, 7, 11], h = 8
     * Speeds tested: 1..11
     * When i = 4 → total hours = 8 → returns 4
     */
    private static int findKBruteforce(int[] piles, int h) {
        int requiredTime = 0;
        int maxBananas = findMaxBanana(piles);
        for (int i = 1; i <= maxBananas; i++) {
            requiredTime = calculateTotalHours(piles, i);
            if (requiredTime <= h) {
                return i;
            }
        }

        return maxBananas;
    }

    /**
     * findKOptimal
     * <p>
     * What it does:
     * Finds the minimum eating speed `k` (bananas per hour) at which Koko can finish
     * all the bananas within `h` hours using binary search.
     * <p>
     * Why it works:
     * - The total hours needed to eat all piles at a given speed `k` decreases as `k` increases.
     * - This monotonic property makes it ideal for binary search:
     * - If `k` is too slow → hours > h → we must go faster (move right).
     * - If `k` is fast enough → hours <= h → try slower (move left).
     * - The search range is [1, max(piles)]:
     * - Lower bound = 1 (slowest possible speed).
     * - Upper bound = max(piles) (if she eats as fast as the biggest pile, she finishes it in 1 hour
     * and all other piles in ≤ 1 hour; any faster is unnecessary).
     * - Each binary search step checks the total hours at `mid` speed using calculateTotalHours().
     * <p>
     * Important Implementation Details:
     * - Uses `long` for total hours to avoid integer overflow when piles are very large.
     * - Returns `low` at the end because it represents the smallest speed that still works.
     * <p>
     * Time Complexity:
     * - O(n * log(maxPile)):
     * - Binary search does O(log(maxPile)) steps.
     * - Each step computes total hours in O(n).
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few variables.
     * <p>
     * Output:
     * Returns the smallest integer speed `k` that allows finishing all piles within `h` hours.
     * <p>
     * Example:
     * piles = [3, 6, 7, 11], h = 8
     * → returns 4
     */

    private static int findKOptimal(int[] piles, int h) {
        int low = 1;
        int high = findMaxBanana(piles);

        while (low <= high) {
            int mid = low + (high - low) / 2;
            long hours = calculateTotalHoursWithEarlyExit(piles, mid, h);

            if (hours <= (long) h) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low; // smallest k that works
    }

    /**
     * Why it works:
     * - If a pile has `p` bananas and she eats at `hourly` bananas/hour,
     * she needs ceil(p / hourly) hours for that pile.
     * - Since she can only eat from one pile per hour, the ceiling is important:
     * even if the last batch has fewer than `hourly` bananas, it still consumes a full hour.
     * - The method sums up ceil(piles[i] / hourly) for all piles[i].
     * - This gives the total hours Koko would spend at that speed.
     * <p>
     * Time Complexity:
     * - O(n): It goes through all piles once and does a constant-time division and ceiling each time.
     * <p>
     * Space Complexity:
     * - O(1): Only uses a few local variables.
     * <p>
     * Output:
     * Returns the total number of hours needed to eat all piles at the given speed.
     * <p>
     * Example:
     * piles = [3, 6, 7, 11], hourly = 4
     * hours = ceil(3/4)+ceil(6/4)+ceil(7/4)+ceil(11/4)
     * = 1 + 2 + 2 + 3 = 8 hours
     */
    public static int calculateTotalHours(int[] piles, int hourly) {
        int totalH = 0;
        int n = piles.length;
        //find total hours:
        for (int i = 0; i < n; i++) {
            totalH += (int) Math.ceil((double) (piles[i]) / (double) (hourly));
        }
        return totalH;
    }


    /**
     * What it does:
     * Calculates how many hours Koko would need to finish all piles at a given eating speed `hourly`.
     * <p>
     * Why it works:
     * - For each pile `p`, the hours required are ceil(p / hourly),
     * because she spends full hours and can’t split time between piles.
     * - This uses integer ceiling arithmetic `(p + hourly - 1) / hourly` instead of floating point,
     * which is faster and avoids precision issues.
     * - Accumulates the total hours across all piles and stops early if the total already exceeds `hLimit`
     * (since any value beyond `hLimit` is enough to reject this speed during binary search).
     * <p>
     * Important Implementation Details:
     * - Uses `long` for the running total to avoid overflow when piles have very large values.
     * - Includes early exit optimization to stop summing once hours exceed the allowed `hLimit`.
     * <p>
     * Time Complexity:
     * - O(n): Visits each pile once.
     * <p>
     * Space Complexity:
     * - O(1): Uses only constant extra space.
     * <p>
     * Output:
     * Returns the total number of hours needed to eat all piles at the given speed.
     * <p>
     * Example:
     * piles = [3, 6, 7, 11], hourly = 4
     * → hours = 1 + 2 + 2 + 3 = 8
     */

    private static long calculateTotalHoursWithEarlyExit(int[] piles, int hourly, int hLimit) {
        long totalH = 0L;
        for (int p : piles) {
            // integer ceiling: ceil(p / hourly) without doubles, done in 64-bit
            totalH += (p + (long) hourly - 1L) / (long) hourly;

            // early exit: once we exceed h, no need to keep summing
            if (totalH > (long) hLimit) return totalH;
        }
        return totalH;
    }

    private static int findMaxBanana(int[] piles) {
        int max = 0;
        for (int i = 0; i < piles.length; i++) {
            if (piles[i] > max) {
                max = piles[i];
            }
        }
        return max;
    }
}

