package recursion;

/*
Leetcode: 50. Pow(x, n)

Implement pow(x, n), which calculates x raised to the power n (i.e., xn).

Example 1:
Input: x = 2.00000, n = 10
Output: 1024.00000

Example 2:
Input: x = 2.10000, n = 3
Output: 9.26100

Example 3:
Input: x = 2.00000, n = -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25

Constraints:
            -100.0 < x < 100.0
            -2^31 <= n <= 2^31-1
            n is an integer.
            Either x is not zero or n > 0.
            -10^4 <= xn <= 10^4
 */
public class MathPow {

    public static void main(String[] args) {
        System.out.println(findPowBruteForce(2.00000, 10));
        System.out.println(findPowBruteForce(2.10000, 3));
        System.out.println(findPowBruteForce(2.00000, -2));
        System.out.println(findPowBruteForce(2.00000, 0));
        System.out.println(findPowBruteForce(1, 2147483647));

    }


    /**
     * findPowBruteForce
     * <p>
     * What it does:
     * Computes x raised to the power n (x^n) using brute-force repeated multiplication.
     * Handles positive and negative exponents, and special cases for x = 1 and x = -1.
     * <p>
     * Intuition:
     * - When n == 0, any number to the zero power is 1.
     * - When x == 1 or x == -1, the output alternates or is constant.
     * - For positive n, multiply x by itself n times.
     * - For negative n, compute as 1/(x^|n|).
     * - Special case for Integer.MIN_VALUE due to overflow.
     * <p>
     * Why each line matters:
     * - if (n == 0 || x == 1): Edge/special case for identities.
     * - if (x == -1): Alternates output based on even/odd n.
     * - if (n == Integer.MIN_VALUE): Returns 0.0 to prevent overflow, but not mathematically accurate.
     * - Negative n: Loop multiplies -n times and takes reciprocal.
     * - Positive n: Simple multiplication loop.
     * <p>
     * Edge Cases Handled:
     * - n == 0: Any x^0 == 1.
     * - x == 1: Always 1.
     * - x == -1: Alternates between 1 and -1 depending on n's parity.
     * - n < 0: Handles via reciprocal, except for Integer.MIN_VALUE.
     * - Handles (attempts to) n == Integer.MIN_VALUE to avoid overflow.
     * <p>
     * Example:
     * findPowBruteForce(2.0, 3) → 8.0
     * findPowBruteForce(2.0, -2) → 0.25
     * findPowBruteForce(1, 2^31-1) → 1.0
     * <p>
     * Time Complexity:
     * - O(|n|): Requires up to |n| multiplications.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a constant amount of extra space.
     * <p>
     * Limitations:
     * - For large n or n == Integer.MIN_VALUE, the brute-force method is slow or fails due to overflow.
     * - Not suitable for competitive coding or real-world usage if n is large.
     */
    private static double findPowBruteForce(double x, int n) {

        if (n == 0 || x == 1) {
            return 1.0;
        }
        if (x == -1) {
            double remainder = (double) n % 10;
            return remainder % 2 == 0 ? 1 : -1;
        }

        if (n == Integer.MIN_VALUE) return 0.0;

        if (n < 0) {
            double product = 1;
            for (int i = 0; i < (n * -1); i++) {
                product *= x;
            }
            System.out.println(product);
            return 1.0 / product;
        } else {
            double product = 1;
            for (int i = 0; i < n; i++) {
                product *= x;
            }
            return product;
        }
    }
}
