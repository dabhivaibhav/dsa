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
     * What it does:
     * Finds the minimum eating speed `k` (bananas per hour) at which Koko can finish
     * all the bananas within `h` hours using an optimal binary search approach.
     * <p>
     * Why it works:
     * - The number of hours needed to finish all piles at speed `k` is a monotonic function:
     * - If `k` increases → total hours decrease.
     * - If `k` decreases → total hours increase.
     * - This monotonic behavior makes it ideal for binary search.
     * <p>
     * Choosing the search range:
     * - Lower bound (`low = 1`): the slowest speed Koko could possibly eat.
     * - Upper bound (`high = max(piles)`): the largest pile size.
     * - If she eats at a speed equal to the largest pile, she finishes that pile in 1 hour
     * and all other piles will take ≤ 1 hour each.
     * - Any speed greater than the largest pile is unnecessary, because it won’t reduce hours
     * below 1 for that largest pile. So `max(piles)` is the logical ceiling of the search space.
     * <p>
     * How it works:
     * - Repeatedly choose `mid = (low + high)/2` as a candidate eating speed.
     * - Calculate how many hours it would take at this speed using calculateTotalHours().
     * - If hours ≤ h:
     * - This speed is fast enough — but maybe we can go slower.
     * - Move left to search smaller speeds: `high = mid - 1`.
     * - If hours > h:
     * - This speed is too slow — we must go faster.
     * - Move right: `low = mid + 1`.
     * - When the loop ends, `low` will be the smallest speed that still works.
     * <p>
     * Time Complexity:
     * - O(n * log(maxPile)):
     * - Each binary search step does O(n) work (calculating total hours),
     * - and there are O(log(maxPile)) steps.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few variables; no extra data structures.
     * <p>
     * Output:
     * Returns the smallest eating speed `k` at which Koko can eat all piles within `h` hours.
     * <p>
     * Example:
     * piles = [3, 6, 7, 11], h = 8
     * Binary search range = [1, 11]
     * → answer = 4
     */
    private static int findKOptimal(int[] piles, int h) {
        int maxBananas = findMaxBanana(piles);
        int low = 1;
        int high = maxBananas;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int hours = calculateTotalHours(piles, mid);
            if (hours <= h) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    /**
     * What it does:
     * Calculates how many total hours it would take for Koko to eat all piles at a given fixed speed `hourly`.
     * <p>
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

