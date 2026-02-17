package collection.stack;

/*
Celebrity Problem

A celebrity is a person who is known by everyone else at the party but does not know anyone in return.
Given a square matrix M of size N x N where M[i][j] is 1 if person i knows person j, and 0 otherwise,
determine if there is a celebrity at the party. Return the index of the celebrity or -1 if no such person exists.
Note that M[i][i] is always 0.

Example 1
Input: M = [ [0, 1, 1, 0], [0, 0, 0, 0], [1, 1, 0, 0], [0, 1, 1, 0] ]
Output: 1
Explanation: Person 1 does not know anyone and is known by persons 0, 2, and 3. Therefore, person 1 is the celebrity.

Example 2
Input: M = [ [0, 1], [1, 0] ]
Output: -1
Explanation: Both persons know each other, so there is no celebrity.

Constraints:
           1 <= N <= 3000
           0 <= M[][] <= 1
 */
public class CelebrityProblem {

    public static void main(String[] args) {
        int[][] arr = {{0, 1, 1, 0}, {0, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 1, 0}};
        System.out.println(findCelebrityBruteForce(arr));
    }


    /**
     * findCelebrityBruteForce(int[][] matrix)
     * <p>
     * What this method does:
     * This method determines whether a celebrity exists in a party using
     * a brute-force counting approach. If a celebrity exists, it returns
     * the index of that person. Otherwise, it returns -1.
     * <p>
     * Definition of Celebrity:
     * 1. A celebrity knows nobody.
     * 2. Everyone else knows the celebrity.
     * <p>
     * In matrix terms:
     * - Row i must contain all 0s (person i knows nobody).
     * - Column i must contain N - 1 ones (everyone except themselves knows them).
     * <p>
     * Step-by-step Explanation
     * <p>
     * Step 1: Create two helper arrays
     * <p>
     * int[] knownBy
     * This counts how many people know person i.
     * <p>
     * int[] knows
     * This counts how many people person i knows.
     * <p>
     * Why we need both:
     * - knownBy helps check condition "everyone knows them".
     * - knows helps check condition "they know nobody".
     * <p>
     * <p>
     * Step 2: Traverse the entire matrix
     * <p>
     * for each i and j:
     * if matrix[i][j] == 1:
     * knownBy[j]++
     * knows[i]++
     * <p>
     * Meaning:
     * If person i knows person j:
     * - Increase j's knownBy count.
     * - Increase i's knows count.
     * <p>
     * After this double loop:
     * - knows[i] tells how many people i knows.
     * - knownBy[i] tells how many people know i.
     * <p>
     * <p>
     * Step 3: Identify the celebrity candidate
     * <p>
     * For each person i:
     * <p>
     * If:
     * knows[i] == 0
     * AND
     * knownBy[i] == N - 1
     * <p>
     * Then:
     * return i
     * <p>
     * Why:
     * - knows[i] == 0 ensures they know nobody.
     * - knownBy[i] == N - 1 ensures everyone else knows them.
     * <p>
     * <p>
     * Step 4: If no such person exists
     * Return -1.
     * <p>
     * <p>
     * Example Walkthrough
     * <p>
     * Matrix:
     * [
     * [0, 1, 1, 0],
     * [0, 0, 0, 0],
     * [1, 1, 0, 0],
     * [0, 1, 1, 0]
     * ]
     * <p>
     * Person 1:
     * - Row 1 → all zeros → knows nobody.
     * - Column 1 → known by 0, 2, 3 → total 3 = N - 1.
     * <p>
     * Therefore, person 1 is the celebrity.
     * <p>
     * <p>
     * Complexity Analysis
     * <p>
     * Time Complexity:
     * O(N^2)
     * Because we scan the entire N x N matrix.
     * <p>
     * Space Complexity:
     * O(N)
     * For the two helper arrays.
     * <p>
     * Interview Insight
     * <p>
     * This is the straightforward counting solution.
     * It clearly verifies both celebrity conditions.
     * <p>
     * However, this is not optimal.
     * <p>
     * The optimized solution reduces time to O(N)
     * using a stack or two-pointer elimination technique.
     * <p>
     * Rule of Thumb:
     * When the matrix size is large,
     * think about eliminating candidates instead of counting everything.
     */

    private static int findCelebrityBruteForce(int[][] matrix) {

        int[] knownBy = new int[matrix.length];
        int[] knows = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 1) {
                    knownBy[j]++;
                    knows[i]++;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (knows[i] == 0 && knownBy[i] == matrix.length - 1) {
                return i;
            }
        }
        return -1;
    }
}
