package heap.medium_problem;

import java.util.Arrays;
import java.util.HashMap;

/*
Problem: Replace Elements by Their Rank

Given an array of N integers, replace each element by its rank in the array. The rank is defined as the position of the
element in the sorted unique array (1-based).
Smaller numbers have smaller ranks.
Duplicate elements have the same rank.
The output array should contain the ranks corresponding to each element in the original order.

Example 1
Input: arr = [20, 15, 26, 2, 98, 6]
Output: [4, 3, 5, 1, 6, 2]
Explanation:
Sorted unique array is [2, 6, 15, 20, 26, 98]
Ranks: 2 → 1, 6 → 2, 15 → 3, 20 → 4, 26 → 5, 98 → 6

Example 2
Input: arr = [1, 5, 8, 15, 8, 25, 9]
Output: [1, 2, 3, 5, 3, 6, 4]
Explanation:
Sorted unique array: [1, 5, 8, 9, 15, 25]
Ranks: 1 → 1, 5 → 2, 8 → 3, 9 → 4, 15 → 5, 25 → 6

Constraints:
            1 <= N <= 10^5
            -10^9 <= arr[i] <= 10^9
 */
public class ReplaceElementsByTheirRank {

    public static void main(String[] args) {
        int[] arr = {20, 15, 26, 2, 98, 6};
        System.out.println(Arrays.toString(replaceWithRankBruteForce(arr)));

        int[] arr1 = {1, 5, 8, 15, 8, 25, 9};
        System.out.println(Arrays.toString(replaceWithRankBruteForce(arr1)));

    }

    /**
     * <h2>THE "AWARD CEREMONY" PATTERN</h2>
     *
     * <h3>THE PROBLEM:</h3>
     * We need to assign a "rank" (1st place, 2nd place, etc.) to every number in the array.
     * If two numbers are identical, they share the same rank. The final output must
     * preserve the original relative positions of the input.
     *
     * <h3>REAL-LIFE ANALOGY: The "Marathon Results"</h3>
     * Imagine a marathon where 100 people finish at different times.
     * <ol>
     * <li><b>The Sorting:</b> To find out who came in 1st, 2nd, or 3rd, you must first
     * line everyone up by their finish time (Sorting).</li>
     * <li><b>The Mapping:</b> You create a master list. The person with the fastest
     * time gets Rank 1. If two people finish at the exact same second, they both
     * get the same Rank.</li>
     * <li><b>The Distribution:</b> Finally, you go back to the original registration
     * list (the original array) and write down the rank next to each runner's name.</li>
     * </ol>
     *
     *
     *
     * <h3>ALGORITHM EXPLAINED:</h3>
     * <b>Example:</b> arr = [10, 5, 10, 2]
     * <ol>
     * <li><b>Clone & Sort:</b> We create a copy [10, 5, 10, 2] &rarr; [2, 5, 10, 10].</li>
     * <li><b>Rank Mapping:</b> We iterate through the sorted copy.
     * <ul>
     * <li>2 is new &rarr; Map: {2: 1}, Rank: 2</li>
     * <li>5 is new &rarr; Map: {2: 1, 5: 2}, Rank: 3</li>
     * <li>10 is new &rarr; Map: {2: 1, 5: 2, 10: 3}, Rank: 4</li>
     * <li>10 is <b>NOT</b> new &rarr; Skip (it shares Rank 3).</li>
     * </ul>
     * </li>
     * <li><b>Final Replacement:</b> Look up each original element in the Map:
     * [10, 5, 10, 2] &rarr; [3, 2, 3, 1].</li>
     * </ol>
     *
     * <h3>COMPLEXITY:</h3>
     * <ul>
     * <li><b>Time: O(N \log N)</b> - Dominating factor is {@code Arrays.sort()}.
     * The HashMap lookups are O(1) on average.</li>
     * <li><b>Space: O(N)</b> - We store a duplicate array and a HashMap containing
     * up to N entries.</li>
     * </ul>
     *
     * <h3>INTERVIEW TAKEAWAY:</h3>
     * This is a classic <b>"Coordinate Compression"</b> technique. It is frequently
     * used in advanced segment tree or Fenwick tree problems where the <i>actual values</i>
     * of the numbers are too large (e.g., 10^9), but their <i>relative order</i> is
     * all that matters. By "compressing" them into ranks, you simplify the problem space
     * from 10^9 down to N.
     */
    private static int[] replaceWithRankBruteForce(int[] arr) {
        // Creating a sorted version of the unique elements by duplicating the original array
        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        // Mapping each unique number to its rank
        HashMap<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;
        for (int num : sortedArr) {
            //Inserting the elements with their ranks if it don't exist in map
            if (!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }

        // Replace original elements with ranks
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = rankMap.get(arr[i]);
        }
        return result;
    }
}
