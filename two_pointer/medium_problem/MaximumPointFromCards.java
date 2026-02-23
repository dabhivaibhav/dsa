package two_pointer.medium_problem;

/*
Leetcode 1423. Maximum Points You Can Obtain from Cards

There are several cards arranged in a row, and each card has an associated number of points.
The points are given in the integer array cardPoints.
In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
Your score is the sum of the points of the cards you have taken.
Given the integer array cardPoints and the integer k, return the maximum score you can obtain.

Example 1:
Input: cardPoints = [1,2,3,4,5,6,1], k = 3
Output: 12
Explanation: After the first step, your score will always be 1.
However, choosing the rightmost card first will maximize your total score.
The optimal strategy is to take the three cards on the right, giving a final score of 1 + 6 + 5 = 12.

Example 2:
Input: cardPoints = [2,2,2], k = 2
Output: 4
Explanation: Regardless of which two cards you take, your score will always be 4.

Example 3:
Input: cardPoints = [9,7,7,9,7,7,9], k = 7
Output: 55
Explanation: You have to take all the cards. Your score is the sum of points of all cards.

Constraints:
            1 <= cardPoints.length <= 10^5
            1 <= cardPoints[i] <= 10^4
            1 <= k <= cardPoints.length
 */
public class MaximumPointFromCards {

    public static void main(String[] args) {
        int[] cardPoints = {1, 2, 3, 4, 5, 6, 1};
        int k = 3;
        System.out.println(maxScore(cardPoints, k));
    }

    /*
    THE "DONUT HOLE" MENTAL MODEL (Optimization by Subtraction)

    THE SCENARIO:
    Imagine you have a row of items, and you are only allowed to take pieces from
    the two ends (Left or Right). You want to maximize your "points."

    THE PROBLEM TYPE:
    "Boundary Selection" or "End-Picking" problems.

    THE KEY INSIGHT (The Donut):
    If you take exactly 'k' cards from the edges, the cards you LEAVE BEHIND
    must form one continuous block in the middle of size (N - k).
    Think of the total array as a Donut:

    The Dough (Ends): The cards you take.

    The Hole (Middle): The cards you leave.

    THE LOGIC:
    To get the MOST "Dough," you must find the SMALLEST "Hole."
    Instead of stressing over whether to pick Left or Right, simply find a
    Sliding Window in the middle of size (N - k) that has the MINIMUM sum.

    THE FORMULA:
    Maximum Points = (Total Sum of all cards) - (Minimum Middle Window Sum)

    WHEN TO USE THIS:

    You can only pick from the start or end of an array.

    You must pick exactly 'k' items.

    The items in the middle remain untouched/contiguous.
    */

    /**
     * Method: maxScore(int[] cardPoints, int k)
     * <p>
     * What this method does:
     * Returns the maximum score obtainable
     * by taking exactly k cards
     * from either the beginning or the end.
     * <p>
     * Core Insight:
     * <p>
     * Instead of thinking:
     * "Pick from left or right each time"
     * <p>
     * Think:
     * "We must take exactly k cards total."
     * <p>
     * That means:
     * We are choosing some cards from the left
     * and the remaining from the right.
     * <p>
     * So the real problem becomes:
     * Try every possible split of k cards
     * between left and right.
     * <p>
     * Strategy Used:
     * <p>
     * Step 1:
     * Start by taking all k cards from the left.
     * <p>
     * Step 2:
     * Gradually replace left cards
     * with right cards one by one.
     * <p>
     * Each iteration:
     * - Remove one card from the left portion
     * - Add one card from the right portion
     * - Update maximum sum
     * <p>
     * Why This Works:
     * <p>
     * There are only k + 1 possible combinations:
     * <p>
     * Take:
     * k from left, 0 from right
     * k-1 from left, 1 from right
     * k-2 from left, 2 from right
     * ...
     * 0 from left, k from right
     * <p>
     * Instead of recalculating each sum from scratch,
     * we maintain a running sum
     * and update it in constant time.
     * <p>
     * Example:
     * <p>
     * cardPoints = [1,2,3,4,5,6,1], k = 3
     * <p>
     * Initial:
     * Take first 3 → sum = 1+2+3 = 6
     * <p>
     * Iteration 1:
     * Remove 3, add 1 → sum = 4
     * <p>
     * Iteration 2:
     * Remove 2, add 6 → sum = 8
     * <p>
     * Iteration 3:
     * Remove 1, add 5 → sum = 12
     * <p>
     * Maximum = 12
     * <p>
     * Complexity:
     * <p>
     * Time Complexity: O(k)
     * We iterate at most k times.
     * <p>
     * Space Complexity: O(1)
     * Only variables are used.
     * <p>
     * Interview Takeaway:
     * <p>
     * This is not really a two-pointer problem.
     * It is a fixed-size window trick.
     * <p>
     * The clever mental shift:
     * Instead of choosing k cards directly,
     * think in terms of trying all splits
     * between left and right efficiently.
     * <p>
     * Sometimes optimization is just
     * reframing the problem correctly.
     */
    private static int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int currentSum = 0;

        // Start by taking the first k cards from the LEFT
        for (int i = 0; i < k; i++) {
            currentSum += cardPoints[i];
        }

        int maxSum = currentSum;

        // Now, "swap" the leftmost cards for the rightmost cards one by one
        // We remove the card at (k-1), (k-2)... and add the card at (n-1), (n-2)...
        for (int i = 0; i < k; i++) {
            currentSum -= cardPoints[k - 1 - i]; // Remove from left
            currentSum += cardPoints[n - 1 - i]; // Add from right

            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }
}
