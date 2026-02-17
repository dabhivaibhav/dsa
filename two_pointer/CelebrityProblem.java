package two_pointer;

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
        System.out.println(findCelebrityOptimized(arr));
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

    /**
     * findCelebrityOptimized(int[][] matrix)
     * <p>
     * What this method does:
     * This method finds the celebrity in O(2N) time using a two-pointer
     * elimination approach instead of checking all pairs like the brute force.
     * <p>
     * Core Idea:
     * Instead of verifying every person with everyone else,
     * we eliminate non-celebrities step by step until only one
     * potential candidate remains. Then we verify that candidate.
     * <p>
     * Thought Process
     * <p>
     * A celebrity must satisfy two strict conditions:
     * 1. Celebrity knows nobody.
     * 2. Everyone else knows the celebrity.
     * <p>
     * Important Observation:
     * If person A knows person B,
     * then A cannot be a celebrity.
     * <p>
     * If person A does NOT know person B,
     * then B cannot be a celebrity.
     * <p>
     * This observation allows us to eliminate one person
     * in every comparison.
     * <p>
     * <p>
     * Step 1: Elimination Phase (Two Pointer Approach)
     * <p>
     * We start with:
     * top = 0
     * down = n - 1
     * <p>
     * While top < down:
     * <p>
     * Case 1:
     * If matrix[top][down] == 1
     * → top knows down
     * → top cannot be celebrity
     * → move top forward
     * <p>
     * Case 2:
     * Else if matrix[down][top] == 1
     * → down knows top
     * → down cannot be celebrity
     * → move down backward
     * <p>
     * Case 3:
     * If neither knows the other
     * → both cannot be celebrity
     * → eliminate both sides
     * <p>
     * Each step eliminates at least one person.
     * <p>
     * After the loop ends:
     * Only one candidate may remain.
     * <p>
     * <p>
     * Step 2: Verification Phase
     * <p>
     * Just because someone survived elimination
     * does NOT guarantee they are a celebrity.
     * <p>
     * We must verify:
     * <p>
     * For every i ≠ candidate:
     * <p>
     * - candidate must NOT know i
     * - i must know candidate
     * <p>
     * If any condition fails → return -1.
     * <p>
     * If all conditions pass → return candidate.
     * <p>
     * <p>
     * Why This Works
     * <p>
     * The elimination phase guarantees that
     * if a celebrity exists,
     * it must be the final remaining candidate.
     * <p>
     * The verification phase ensures correctness.
     * <p>
     * <p>
     * Complexity Analysis
     * <p>
     * Elimination Phase:
     * Runs at most N comparisons.
     * <p>
     * Verification Phase:
     * Runs N checks.
     * <p>
     * Total Time Complexity:
     * O(2N)
     * <p>
     * Space Complexity:
     * O(1)
     * <p>
     * <p>
     * Interview Takeaway
     * <p>
     * This is the optimal solution.
     * <p>
     * Key idea to remember:
     * “If A knows B, eliminate A.”
     * <p>
     * Always separate:
     * 1. Candidate elimination
     * 2. Final verification
     * <p>
     * This pattern appears in many elimination-based problems.
     */
    private static int findCelebrityOptimized(int[][] matrix) {

        // Size of the given matrix
        int n = matrix.length;

        // Top and Down pointers for narrowing the possible celebrity
        int top = 0, down = n - 1;

        // Traverse for all the people to find potential celebrity
        while (top < down) {
            // If top knows down, top cannot be a celebrity
            if (matrix[top][down] == 1) {
                top = top + 1;
            }
            // If down knows top, down cannot be a celebrity
            else if (matrix[down][top] == 1) {
                down = down - 1;
            }
            // If neither knows each other, both are not the celebrity
            else {
                top++;
                down--;
            }
        }

        // If top exceeds down, no celebrity is found
        if (top > down) return -1;

        // Check if the person pointed by top is a celebrity
        for (int i = 0; i < n; i++) {
            if (i == top) continue; // Skip checking the person itself

            // If top knows someone or someone doesn't know top, it's not a celebrity
            if (matrix[top][i] == 1 || matrix[i][top] == 0) {
                return -1;
            }
        }
        // Return the index of the celebrity
        return top;
    }
}
