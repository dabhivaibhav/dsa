package greedy;

/*
Leetcode 2078: Two Furthest Houses with Different Colors

There are n houses evenly lined up on the street, and each house is beautifully painted.
You are given a 0-indexed integer array colors of length n, where colors[i] represents the color of the ith house.
Return the maximum distance between two houses with different colors.
The distance between the ith and jth houses is abs(i - j), where abs(x) is the absolute value of x.

Example 1:
Input: colors = [1,1,1,6,1,1,1]
Output: 3
Explanation: In the above image, color 1 is blue, and color 6 is red.
The furthest two houses with different colors are house 0 and house 3.
House 0 has color 1, and house 3 has color 6. The distance between them is abs(0 - 3) = 3.
Note that houses 3 and 6 can also produce the optimal answer.

Example 2:
Input: colors = [1,8,3,8,3]
Output: 4
Explanation: In the above image, color 1 is blue, color 8 is yellow, and color 3 is green.
The furthest two houses with different colors are house 0 and house 4.
House 0 has color 1, and house 4 has color 3. The distance between them is abs(0 - 4) = 4.

Example 3:
Input: colors = [0,1]
Output: 1
Explanation: The furthest two houses with different colors are house 0 and house 1.
House 0 has color 0, and house 1 has color 1. The distance between them is abs(0 - 1) = 1.

Constraints:
            n == colors.length
            2 <= n <= 100
            0 <= colors[i] <= 100
            Test data are generated such that at least two houses have different colors.
 */
public class TwoFurthestHouseWithDiffColors {
    public static void main(String[] args) {
        int[] colors = {1, 1, 1, 6, 1, 1, 1};
        System.out.println(maxDistance(colors));
        colors = new int[]{1, 8, 3, 8, 3};
        System.out.println(maxDistance(colors));
        colors = new int[]{0, 1};
        System.out.println(maxDistance(colors));
    }

    /*
    THE "EXTREME EDGES" PATTERN

    THE PROBLEM:
    Find the maximum distance between two distinct items.

    THE NAIVE WAY:
    Nested loops checking every pair. O(N^2) - Too slow for large N.

    THE GREEDY TRICK:
    The maximum distance MUST involve one of the two ends
    of the array. If the ends don't work with each other,
    one end MUST work with something inside.

    You don't need to check middle-to-middle.
    The best answer is always 'pinned' to an edge.

    COMPLEXITY:
    Time: O(N) - We walk the array twice at most.
    Space: O(1) - No extra memory used.
    */

    /**
     * maxDistance(int[] colors)
     * <p>
     * What this method does:
     * <p>
     * Returns the maximum distance between two houses
     * that have different colors.
     * <p>
     * The distance between two houses i and j is:
     * <p>
     * abs(i - j)
     * <p>
     * Core Idea:
     * <p>
     * The furthest possible pair of houses must involve
     * one of the two ends of the array.
     * <p>
     * Either:
     * <p>
     * - the first house paired with some house on the right
     * OR
     * - the last house paired with some house on the left
     * <p>
     * So we only need to check these two possibilities.
     * <p>
     * Thought Process:
     * <p>
     * Let n be the number of houses.
     * <p>
     * If we want the maximum distance:
     * <p>
     * Option 1:
     * Fix the first house (index 0)
     * and search from the end to find the
     * furthest house with a different color.
     * <p>
     * Option 2:
     * Fix the last house (index n-1)
     * and search from the beginning to find
     * the furthest house with a different color.
     * <p>
     * The larger of these two distances
     * is the answer.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Initialize maxDist = 0.
     * <p>
     * Step 2:
     * <p>
     * Compare houses with the first house.
     * <p>
     * Start from the end of the array
     * and move backwards.
     * <p>
     * As soon as we find a house
     * with a different color than colors[0],
     * the distance is simply:
     * <p>
     * i
     * <p>
     * because the first house index is 0.
     * <p>
     * Step 3:
     * <p>
     * Compare houses with the last house.
     * <p>
     * Start from the beginning of the array
     * and move forward.
     * <p>
     * As soon as we find a house
     * with a different color than colors[n-1],
     * the distance becomes:
     * <p>
     * (n - 1) - i
     * <p>
     * Step 4:
     * <p>
     * Take the maximum distance
     * from both scenarios.
     * <p>
     * Example 1:
     * <p>
     * colors = [1,1,1,6,1,1,1]
     * <p>
     * Compare with first house (color 1):
     * <p>
     * The furthest different color is at index 3.
     * <p>
     * Distance = 3
     * <p>
     * Compare with last house (color 1):
     * <p>
     * Again index 3 is different.
     * <p>
     * Distance = 6 - 3 = 3
     * <p>
     * Output → 3
     * <p>
     * Example 2:
     * <p>
     * colors = [1,8,3,8,3]
     * <p>
     * Compare with first house:
     * <p>
     * Last different color is index 4.
     * <p>
     * Distance = 4
     * <p>
     * Output → 4
     * <p>
     * Example 3:
     * <p>
     * colors = [0,1]
     * <p>
     * First and last houses already differ.
     * <p>
     * Distance = 1
     * <p>
     * Output → 1
     * <p>
     * Complexity:
     * <p>
     * Let n = number of houses.
     * <p>
     * Time Complexity:
     * <p>
     * O(n)
     * <p>
     * We scan the array at most twice.
     * <p>
     * Space Complexity:
     * <p>
     * O(1)
     * <p>
     * Only a few variables are used.
     * <p>
     * Interview Takeaway:
     * <p>
     * When searching for maximum distance
     * in a linear structure,
     * always consider the boundaries first.
     * <p>
     * The optimal pair is very often
     * anchored at one of the edges.
     * <p>
     * This insight avoids the brute-force
     * O(n²) comparison of every pair
     * of houses.
     */
    public static int maxDistance(int[] colors) {
        int n = colors.length;
        int maxDist = 0;

        for (int i = n - 1; i >= 0; i--) {
            if (colors[i] != colors[0]) {
                maxDist = Math.max(maxDist, i);
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            if (colors[i] != colors[n - 1]) {
                maxDist = Math.max(maxDist, (n - 1) - i);
                break;
            }
        }
        return maxDist;
    }
}
