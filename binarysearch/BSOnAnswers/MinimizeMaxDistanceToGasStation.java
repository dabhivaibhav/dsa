package binarysearch.BSOnAnswers;

import java.util.PriorityQueue;

/*
Leetcode: Minimize Max Distance to Gas Station

Given a sorted array arr of size n, containing integer positions of n gas stations on the X-axis, and an integer k, place
k new gas stations on the X-axis.
The new gas stations can be placed anywhere on the non-negative side of the X-axis, including non-integer positions.
Let dist be the maximum distance between adjacent gas stations after adding the k new gas stations.
Find the minimum value of dist.
Your answer will be accepted if it is within 1e-6 of the true value.

Examples:
Input: n = 10, arr = [1, 2, 3, 4, 5, 6 ,7, 8, 9, 10], k = 9
Output: 0.50000
Explanation:
One of the possible ways to place 10 gas stations is [1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10].
Thus the maximum difference between adjacent gas stations is 0.5.
Hence, the value of dist is 0.5. It can be shown that there is no possible way to add 10 gas stations in such a way that
the value of dist is lower than this.

Input : n = 10, arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 1
Output: 1.00000
Explanation:
One of the possible ways to place 1 gas station is [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11].
New Gas Station is at 11.
Thus the maximum difference between adjacent gas stations is still 1.
Hence, the value of dist is 1.
It can be shown that there is no possible way to add 1 gas station in such a way that the value of dist is lower than this.

Constraints:
            10 <= n <= 5000
            0 <= arr[i] <= 10^9
            arr is sorted in a strictly increasing order
            0 <= k <= 10^5
 */
public class MinimizeMaxDistanceToGasStation {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 9;
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k1 = 1;

        System.out.println(minimizedMaxDistanceGasStationBruteForce(arr, k));
        System.out.println(minimizedMaxDistanceGasStationBetter(arr, k));
        System.out.println(minimizedMaxDistanceGasStationOptimal(arr, k));
        System.out.println(minimizedMaxDistanceGasStationOptimal2(arr, k));

    }

    /**
     * minimizedMaxDistanceGasStationBruteForce
     * <p>
     * What it does:
     * Places k new gas stations by repeatedly splitting the current largest gap between
     * consecutive existing stations. After k placements, returns the resulting maximum
     * gap length (the minimized maximum distance).
     * <p>
     * Intuition (greedy-by-splitting-the-largest-gap):
     * - Let arr be sorted station positions. Each adjacent pair (arr[i], arr[i+1]) forms a gap of length diff = arr[i+1]-arr[i].
     * - If we insert x new stations into that gap, it is split into (x+1) equal sub-gaps,
     * each with length diff / (x+1). The “bottleneck” gap after all insertions is the
     * maximum of these per-gap lengths across all gaps.
     * - A natural greedy heuristic is: each time, add a station to the gap that currently
     * has the largest sub-gap (i.e., the worst bottleneck), because reducing the worst gap
     * gives the biggest improvement to the objective (“minimize the maximum”).
     * <p>
     * How it works:
     * - howMany[i] tracks how many new stations we have inserted into gap i so far.
     * - Repeat k times:
     * • For each gap i, compute current sub-gap length = (arr[i+1]-arr[i]) / (howMany[i]+1).
     * • Pick the gap with the largest sub-gap length and increment howMany there.
     * - After k insertions, recompute each gap’s final sub-gap length and return the maximum.
     * <p>
     * Example (arr = [1,2,3,4,5,6,7,8,9,10], k = 9):
     * - Initially, all gaps are length 1. The largest sub-gap is 1 everywhere.
     * - First insertion into any gap splits that gap into two sub-gaps of 0.5.
     * - Repeating this k=9 times evenly across gaps yields every gap having 1 insert each,
     * so the maximum sub-gap becomes 0.5 — the optimal answer.
     * <p>
     * Time complexity:
     * - O(k * (n-1)) to scan all gaps each time and pick the max (no heap).
     * - Final pass O(n) to compute the answer.
     * - This is acceptable for small k but slow for large k.
     * <p>
     * Space complexity: O(n) for howMany.
     */
    private static double minimizedMaxDistanceGasStationBruteForce(int[] arr, int k) {
        int n = arr.length; //size of array.
        int[] howMany = new int[n - 1];

        //Pick and place k gas stations:
        for (int gasStations = 1; gasStations <= k; gasStations++) {
            //Find the maximum section
            //and insert the gas station:
            double maxSection = -1;
            int maxInd = -1;
            for (int i = 0; i < n - 1; i++) {
                double diff = arr[i + 1] - arr[i];
                double sectionLength =
                        diff / (double) (howMany[i] + 1);
                if (sectionLength > maxSection) {
                    maxSection = sectionLength;
                    maxInd = i;
                }
            }
            //insert the current gas station:
            howMany[maxInd]++;
        }

        //Find the maximum distance i.e. the answer:
        double maxAns = -1;
        for (int i = 0; i < n - 1; i++) {
            double diff = arr[i + 1] - arr[i];
            double sectionLength =
                    diff / (double) (howMany[i] + 1);
            maxAns = Math.max(maxAns, sectionLength);
        }
        return maxAns;
    }

    /**
     * minimizedMaxDistanceGasStationOptimal
     * <p>
     * What it does:
     * Computes the minimal possible maximum distance (dist) between adjacent stations
     * after adding k new stations, using binary search on a real number answer.
     * <p>
     * Core feasibility idea:
     * - For a candidate dist = d, each original gap G = arr[i+1]-arr[i] must be cut
     * into segments of length ≤ d. The minimum number of segments is ceil(G/d),
     * so the number of new stations needed in that gap is ceil(G/d) - 1.
     * - Summing that over all gaps gives needed(d). If needed(d) ≤ k, then d is feasible.
     * <p>
     * How it works:
     * - Search range: low = 0, high = maxGap (largest original gap).
     * - While (high - low > 1e-6):
     * • mid = (low + high)/2.
     * • cnt = numberOfGasStationsRequired(mid, arr) — counts total insertions needed so that
     * every sub-gap ≤ mid.
     * • If cnt > k → mid too small (not feasible) → low = mid.
     * else        → mid feasible → high = mid.
     * - Return high (or (low+high)/2) after the loop; accuracy is within 1e-6.
     * <p>
     * Example (arr = [1..10], k = 9):
     * - maxGap = 1 → search [0, 1].
     * - mid ~ 0.5 is feasible with exactly 9 stations (ceil(1/0.5)-1 = 1 per gap, 9 gaps).
     * - Any smaller d < 0.5 requires more than 9 stations, so the answer converges to 0.5.
     * <p>
     * Time complexity:
     * - Each feasibility check is O(n). We run a fixed ~60–70 iterations to reach 1e-6 precision.
     * - Overall O(n * log(precision)) with a tiny constant; effectively linear in n.
     * <p>
     * Space complexity: O(1).
     */
    public static double minimizedMaxDistanceGasStationOptimal(int[] arr, int k) {
        int n = arr.length; // size of the array
        double low = 0;
        double high = 0;

        //Find the maximum distance:
        for (int i = 0; i < n - 1; i++) {
            high = Math.max(high, (double) (arr[i + 1] - arr[i]));
        }

        //Apply Binary search:
        double diff = 1e-6;
        while (high - low > diff) {
            double mid = (low + high) / (2.0);
            int cnt = numberOfGasStationsRequired(mid, arr);
            if (cnt > k) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return high;
    }

    /**
     * minimizedMaxDistanceGasStationOptimal2
     * <p>
     * What it does:
     * Same binary-search-on-ans approach as minimizedMaxDistanceGasStationOptimal, but the feasibility
     * test is written as canAchieve(arr, k, d), which directly returns boolean (“≤ k stations or not”).
     * Functionally equivalent to computing numberOfGasStationsRequired(d, arr) and comparing to k.
     * <p>
     * Intuition recap:
     * - needed(d) = Σ_i ( ceil( gap_i / d ) - 1 ), where gap_i = arr[i+1] - arr[i].
     * - Monotonicity: as d grows, needed(d) never increases → binary search d in [0, maxGap].
     * <p>
     * Differences from the first optimal function:
     * - Uses a fixed iteration count (~70) to shrink [low, high] until they are within 1e-6.
     * - canAchieve early-exits when the running needed exceeds k (slightly faster).
     * <p>
     * Time/space complexity:
     * - Same as the first optimal method: O(n * log(precision)) time, O(1) space.
     */
    public static double minimizedMaxDistanceGasStationOptimal2(int[] arr, int k) {
        // array is given sorted per problem; sort defensively if unsure
        // Arrays.sort(arr);

        // 1) Search range: [0, maxGap]
        double low = 0.0;
        double high = 0.0;
        for (int i = 0; i + 1 < arr.length; i++) {
            high = Math.max(high, arr[i + 1] - arr[i]);
        }

        // 2) Binary search on real numbers until precision reaches 1e-6
        // Invariant: answer ∈ [low, high]
        for (int iter = 0; iter < 70; iter++) { // ~70 iterations comfortably beats 1e-6 precision
            double mid = (low + high) / 2.0;
            if (canAchieve(arr, k, mid)) {
                // mid is feasible → shrink right
                high = mid;
            } else {
                // mid too small → need to allow larger gaps
                low = mid;
            }
        }
        return high; // or (low + high)/2; both are within 1e-6 now
    }

    /**
     * canAchieve
     * <p>
     * What it does:
     * Feasibility predicate for a given maximum allowed gap d. Returns true if we can ensure
     * every adjacent distance ≤ d by inserting at most k new stations.
     * <p>
     * Logic:
     * - For each original gap G, stations needed = ceil(G/d) - 1 (0 if G ≤ d).
     * - Accumulate over all gaps; if the total needed ≤ k, return true; else false.
     * - Early exit if needed already exceeds k.
     * <p>
     * Numerical notes:
     * - Using doubles is fine. ceiling(gap/d) handles non-integer positions naturally.
     * <p>
     * Example (arr=[1..10], d=0.5):
     * - Each gap G=1 → ceil(1/0.5)-1 = 1 → total needed = 9.
     * - If k ≥ 9, return true; otherwise false.
     * <p>
     * Time complexity: O(n).
     * Space complexity: O(1).
     */
    // Feasibility check: with maximum allowed gap = d, do we need ≤ k new stations?
    private static boolean canAchieve(int[] arr, int k, double d) {
        int needed = 0;
        for (int i = 0; i + 1 < arr.length; i++) {
            double gap = arr[i + 1] - arr[i];
            // stations needed in this gap to make all subgaps ≤ d:
            // ceil(gap / d) - 1. Using doubles is fine here.
            needed += Math.max(0, (int) Math.ceil(gap / d) - 1);
            if (needed > k) return false; // early exit
        }
        return needed <= k;
    }

    /**
     * numberOfGasStationsRequired
     * <p>
     * What it does:
     * Computes the total number of new stations needed so that every adjacent distance ≤ dist.
     * This returns the same quantity that canAchieve compares against k.
     * <p>
     * Formula per gap:
     * - G = arr[i] - arr[i-1].
     * - stations needed in this gap = ceil(G / dist) - 1.
     * <p>
     * Implementation detail in this version:
     * - It computes numberInBetween = floor(G / dist).
     * - If G is exactly a multiple of dist, we don’t need an extra station at the end,
     * so it decrements by 1 in that exact case. This effectively realizes ceil(G/dist)-1.
     * <p>
     * Example (G = 1, dist = 0.5):
     * - G/dist = 2.0 ⇒ exact multiple: numberInBetween = 2, then decrement → 1.
     * - Over 9 such gaps, total needed = 9.
     * <p>
     * Time complexity: O(n).
     * Space complexity: O(1).
     */
    public static int numberOfGasStationsRequired(double dist, int[] arr) {
        int n = arr.length; // size of the array
        int cnt = 0;
        for (int i = 1; i < n; i++) {
            int numberInBetween = (int) ((arr[i] - arr[i - 1]) / dist);
            if ((arr[i] - arr[i - 1]) == (dist * numberInBetween)) {
                numberInBetween--;
            }
            cnt += numberInBetween;
        }
        return cnt;
    }

    /**
     * minimizedMaxDistanceGasStationBetter
     * <p>
     * What it does:
     * “Better” greedy using a max-heap (priority queue). Instead of scanning every gap each time (O(k*(n-1))),
     * this keeps a priority queue keyed by the current largest sub-gap length of each original gap.
     * Each iteration inserts one station into the gap producing the largest sub-gap, updates that gap’s
     * new sub-gap length, and pushes it back.
     * <p>
     * Intuition (same as brute force, but efficient selection of the worst gap):
     * - For each original gap i, if howMany[i] stations have already been placed inside it,
     * its current max sub-gap is (arr[i+1]-arr[i]) / (howMany[i] + 1).
     * - The gap with the largest sub-gap is the current bottleneck; splitting it gives the best improvement.
     * - Repeat k times.
     * <p>
     * How it works:
     * - Initialize howMany[i] = 0 and push Pair(length, i) for each gap into a max-heap,
     * where length = (arr[i+1]-arr[i])/(howMany[i]+1) initially equals arr[i+1]-arr[i].
     * - Repeat k times:
     * • Pop the gap with the largest current sub-gap (tp).
     * • Increment howMany for that gap.
     * • Recompute the new sub-gap length: (originalGapLength)/(howMany+1).
     * • Push updated Pair back into the heap.
     * - The heap top after k insertions is the final maximum sub-gap length (the minimized maximum distance).
     * <p>
     * Example (arr=[1..10], k=9):
     * - Start with 9 gaps, each length 1, all in the heap.
     * - Each iteration pops one of them (length 1), inserts a station, pushes back with new length 0.5.
     * - After 9 iterations, all gaps have one station, so the heap max is 0.5 (the answer).
     * <p>
     * Time complexity:
     * - Building heap: O(n).
     * - Each of k iterations does one poll and one add: O(log n) each → O(k log n) total.
     * - Much faster than the O(k * n) brute-force scan for large k.
     * <p>
     * Space complexity: O(n) for the heap and howMany.
     */
    public static double minimizedMaxDistanceGasStationBetter(int[] arr, int k) {
        int n = arr.length; // size of array.
        int[] howMany = new int[n - 1];
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(b.first, a.first));

        // insert the first n-1 elements into pq
        // with respective distance values:
        for (int i = 0; i < n - 1; i++) {
            pq.add(new Pair(arr[i + 1] - arr[i], i));
        }

        // Pick and place k gas stations:
        for (int gasStations = 1; gasStations <= k; gasStations++) {
            // Find the maximum section
            // and insert the gas station:
            Pair tp = pq.poll();
            int secInd = tp.second;

            // insert the current gas station:
            howMany[secInd]++;

            double inidiff = arr[secInd + 1] - arr[secInd];
            double newSecLen = inidiff / (double) (howMany[secInd] + 1);
            pq.add(new Pair(newSecLen, secInd));
        }

        return pq.peek().first;
    }

    /**
     * Pair
     *
     * What it does:
     * Simple (length, index) pair for the priority queue.
     * - first: the current sub-gap length used as the priority key.
     * - second: the gap index in [0, n-2] so we can update howMany for that exact original gap.
     */
    public static class Pair {
        double first;
        int second;

        Pair(double first, int second) {
            this.first = first;
            this.second = second;
        }
    }
}
