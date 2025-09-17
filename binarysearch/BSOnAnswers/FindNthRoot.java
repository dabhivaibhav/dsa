package binarysearch.BSOnAnswers;

/*
Find Nth root of a number

Given two numbers N and M, find the Nth root of M. The Nth root of a number M is defined as a number X such that when X
is raised to the power of N, it equals M. If the Nth root is not an integer, return -1.

Examples:
Input: N = 3, M = 27
Output: 3
Explanation: The cube root of 27 is equal to 3.

Input: N = 4, M = 69
Output:-1
Explanation: The 4th root of 69 does not exist. So, the answer is -1.

Constraints:
          1 <= N <= 30
          1 <= M <= 10^9
 */
public class FindNthRoot {
    public static void main(String[] args) {

        int n = 3;
        int m = 27;
        int n1 = 4;
        int m1 = 69;
        System.out.println(findNthRoot(n, m));
        System.out.println(findNthRoot(n1, m1));
    }

    /**
     * findNthRoot
     * <p>
     * What it does:
     * Finds the integer Nth root of a given number M.
     * If such an integer does not exist (i.e. M is not a perfect Nth power), it returns -1.
     * <p>
     * Why it works:
     * - The Nth power function is monotonically increasing for positive integers.
     * - So, if we test values x in [1..M], x^n grows as x grows.
     * - We can apply binary search on this range:
     * - Calculate mid^n using the helper method exponentiation().
     * - If mid^n == M, then mid is the Nth root → return mid.
     * - If mid^n > M, search the smaller half → high = mid - 1.
     * - If mid^n < M, search the larger half → low = mid + 1.
     * - This halves the search space every iteration, giving logarithmic time complexity.
     * <p>
     * Time Complexity:
     * - O(log M * N): Each binary search step (log M steps) does an O(N)-time exponentiation.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a few variables; no extra data structures.
     * <p>
     * Output:
     * Returns the integer Nth root of M if it exists, otherwise -1.
     * <p>
     * Example:
     * Input: N = 3, M = 27
     * Output: 3  (since 3^3 = 27)
     */
    private static int findNthRoot(int n, int m) {

        int low = 1;
        int high = m;

        if (n == 1) return m;
        if (m == 1) return 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            int exponent = exponentiation(mid, n, m);
            if (exponent == 1) {
                return mid;
            } else if (exponent == 2) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * exponentiation
     * <p>
     * What it does:
     * Efficiently computes mid^n while stopping early if the result exceeds m.
     * Returns:
     * 1 if mid^n == m,
     * 2 if mid^n > m,
     * 0 if mid^n < m.
     * <p>
     * Why it works:
     * - Multiplies mid by itself n times using a loop.
     * - If the running product exceeds m at any point, it returns 2 early
     * to prevent overflow and save unnecessary work.
     * <p>
     * Time Complexity:
     * - O(N): Multiplies mid exactly n times in the worst case.
     * <p>
     * Space Complexity:
     * - O(1): Uses constant extra space.
     */
    private static int exponentiation(int mid, int n, int m) {
        long ans = 1;
        for (int i = 1; i <= n; i++) {
            ans = ans * mid;
            if (ans > m) {
                return 2;
            }
        }
        if (ans == m) return 1;
        return 0;
    }
}
