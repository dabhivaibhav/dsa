package HackerRankProblems;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/*
There is a large pile of socks that must be paired by color. Given an array of integers representing the color of each
sock, determine how many pairs of socks with matching colors there are.

Example
n = 7
ar = [1,2,1,2,1,3,2]

There is one pair of color and one of color. There are three odd socks left, one of each color. The number of pairs is.

->  Function Description
    Complete the sockMerchant function in the editor below.
    sockMerchant has the following parameter(s):
                int n: the number of socks in the pile
                int ar[n]: the colors of each sock
Returns = int: the number of pairs

Input Format:
The first line contains an integer , the number of socks represented in .
The second line contains  space-separated integers, , the colors of the socks in the pile.

Constraints
1 <= n <= 100
1 <= ar[i] <= 100 where 0 <= i < n

Example:
n = 6
ar = [1,2,1,2,1,3]
output: 2

n = 8
ar = [10,20,20,10,10,30,50,10,20]
output: 3
 */
class Result {

    /**
     * sockMerchantBruteForce
     * <p>
     * What it does:
     * Counts the total number of matching sock pairs using a frequency map.
     * Iterates through the list of sock colors and records how many times each color has been seen.
     * Every time the count for a color becomes even, that signals one completed pair and the pair counter is incremented.
     * <p>
     * Intuition:
     * Keep a running tally of occurrences per color. You don't need to store every sock — just the counts for each color.
     * When a color's count flips from odd → even, a pair has been completed. Using a HashMap makes this simple and general for any integer color domain.
     * <p>
     * Why each line matters:
     * - `HashMap<Integer, Integer> map = new HashMap<>();`
     * → Creates a dynamic dictionary to map color → frequency. Works for any integer color values.
     * - `int count = 0;`
     * → Holds the total number of pairs found so far.
     * - `for (int i = 0; i < n; i++) {`
     * → Single pass over the input, ensuring O(n) time.
     * - `if (map.containsKey(ar.get(i))) {`
     * → Check if we've seen this color before so we can increment its frequency.
     * - `map.put(ar.get(i), map.get(ar.get(i)) + 1);`
     * → Update the frequency for this color.
     * - `if (map.get(ar.get(i)) % 2 == 0) { count++; }`
     * → When frequency becomes even, we have just completed a pair — increment pair counter.
     * - `} else { map.put(ar.get(i), 1); }`
     * → First time seeing this color: initialize its count to 1.
     * - `return count;`
     * → Return the total number of matching pairs.
     * <p>
     * Edge Cases Handled:
     * - n = 1 (returns 0 pairs).
     * - All socks same color (counts multiple pairs correctly).
     * - All socks distinct (returns 0).
     * - Colors outside small fixed ranges (works for arbitrary integer color ids).
     * <p>
     * Example:
     * Input: n = 9, ar = [10, 20, 20, 10, 10, 30, 50, 10, 20]
     * Process:
     * - color 10 counts → 1, 2 (pair++), 3, 4 (pair++)
     * - color 20 counts → 1, 2 (pair++), 3
     * - color 30 → 1
     * - color 50 → 1
     * Output: 3
     * <p>
     * Time Complexity: O(n) — one pass over the list; HashMap operations are amortized O(1).
     * Space Complexity: O(k) where k = number of distinct colors (worst-case O(n)).
     * Note: given the problem constraints (1 ≤ ar[i] ≤ 100), k ≤ 100 and the space becomes O(1) (a small constant).
     */
    public static int sockMerchantBruteForce(int n, List<Integer> ar) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(ar.get(i))) {
                map.put(ar.get(i), map.get(ar.get(i)) + 1);
                if (map.get(ar.get(i)) % 2 == 0) {
                    count++;
                }
            } else {
                map.put(ar.get(i), 1);
            }
        }
        return count;

    }

    /**
     * sockMerchantOptimal
     * <p>
     * What it does:
     * Efficiently counts the total number of matching sock pairs given a list of sock colors.
     * Each color is represented by an integer between 1 and 100, and a pair is formed when two socks share the same color.
     * This method leverages a fixed-size frequency array (size 101) to count occurrences and identify pairs as they occur.
     * <p>
     * Intuition:
     * Since the color range is limited to 1–100, a HashMap is unnecessary.
     * A fixed integer array can track how many times each color appears.
     * Every time a color’s frequency becomes even, that indicates a completed pair.
     * <p>
     * Why each line matters:
     * - `int[] freq = new int[101];` → Creates a constant-sized array to count socks by color.
     * - `int pairs = 0;` → Keeps track of total pairs found so far.
     * - `for (int color : ar)` → Iterates once through the sock list (O(n)).
     * - `freq[color]++;` → Increments the count for that color.
     * - `if (freq[color] % 2 == 0)` → Detects when a color’s count becomes even, signifying a completed pair.
     * - `pairs++;` → Increments the total pair count when a new pair is found.
     * - `return pairs;` → Returns the total number of matching pairs.
     * <p>
     * Edge Cases Handled:
     * - Handles smallest input where n = 1 (no pairs).
     * - Handles all socks being of the same color (multiple pairs).
     * - Handles all distinct colors (no pairs).
     * - Works correctly even if the list is unordered.
     * <p>
     * Example:
     * Input: n = 7, ar = [1, 2, 1, 2, 1, 3, 2]
     * Process:
     * - color 1 count → 1, 2 (pair formed), 3
     * - color 2 count → 1, 2 (pair formed), 3
     * - color 3 count → 1
     * Output: 2 (pairs of color 1 and 2)
     * <p>
     * Time Complexity: O(n)
     * Space Complexity: O(1) (constant-size array of 101 elements)
     */
    public static int sockMerchantOptimal(int n, List<Integer> ar) {
        int[] freq = new int[101];
        int pairs = 0;

        for (int color : ar) {
            freq[color]++;
            if (freq[color] % 2 == 0) {
                pairs++;
            }
        }
        return pairs;
    }

}

public class CountColorSocksPair {
    public static void main(String[] args) throws IOException {
        List<Integer> ar = Stream.of(1, 2, 1, 2, 1, 3, 2).toList();
        List<Integer> ar1 = Stream.of(10, 20, 20, 10, 10, 30, 50, 10, 20).toList();
        System.out.println("Total Pairs: " + Result.sockMerchantOptimal(6, ar));
        System.out.println("Total Pairs: " + Result.sockMerchantOptimal(8, ar1));
    }
}

