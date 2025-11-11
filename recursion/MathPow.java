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

        System.out.println(findPowOptimal(2.00000, 10));
        System.out.println(findPowOptimal(2.10000, 3));
        System.out.println(findPowOptimal(2.00000, -2));
        System.out.println(findPowOptimal(2.00000, 0));
        System.out.println(findPowOptimal(1, 2147483647));

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
            return 1.0 / product;
        } else {
            double product = 1;
            for (int i = 0; i < n; i++) {
                product *= x;
            }
            return product;
        }
    }


    /**
     * findPowOptimal
     * <p>
     * What it does:
     * Efficiently computes x raised to the power n (x^n), for any integer n, using exponentiation by squaring.
     * Handles both positive and negative exponents as well as all common edge cases.
     * <p>
     * Intuition (Beginner-Friendly and Step-by-Step):
     * - The straightforward (brute-force) way to find x^n is to multiply x by itself n times.
     * For large n, this is very slow and can require billions of multiplications.
     * - A much faster method leverages patterns in how exponents work. When raising x to a power:
     * - If the exponent n is even, you can split the work in half:
     * For example, x^8 = (x^4)^2, only needing a result for x^4, then squaring it.
     * - If n is odd, you make it even by taking out one x: x^9 = x * (x^4)^2.
     * - Each time you apply these rules, the size of the problem is cut in half.
     * Instead of doing n multiplications, you only need about log2(n). For n = 1,000,000, that's about 20 steps.
     * - If n is negative, math tells us that x^-n = 1 / x^n, so you can flip x to 1/x, make n positive, and use the same pattern.
     * - This method, called "exponentiation by squaring," is both efficient and easy to implement recursively or iteratively.
     * <p>
     * Why each line matters:
     * - Convert exponent n to a long value if negative, to handle edge cases like Integer.MIN_VALUE, which can't be negated safely as an int.
     * - If n < 0, compute the reciprocal (turn x into 1/x) and use positive exponent.
     * - The recursive function fastPow:
     * - If the current exponent is 0, returns 1 (any number to the 0 power is 1).
     * - Each step splits the problem in half: calculate x^{n/2}, then combine results:
     * - If n is even, multiply the half result by itself.
     * - If n is odd, multiply the half result by itself and multiply by x one more time.
     * - Continues until all multiplications are done.
     * <p>
     * Edge Cases Handled:
     * - n == 0: Returns 1 for any x.
     * - x == 1 or x == -1: Returns correct alternation for all n.
     * - n == Integer.MIN_VALUE: Avoids overflow by using long.
     * - Negative exponents: Computes reciprocal.
     * - Extremely large n: Remains efficient.
     * <p>
     * Example:
     * myPow(2.0, 10) → 1024.0     (multiply 2 by itself 10 times, but optimized)
     * myPow(2.0, -2) → 0.25       (take reciprocal: 1/(2^2))
     * myPow(2, Integer.MIN_VALUE) → 0.0 (works without overflow)
     * <p>
     * Time Complexity:
     * - O(log |n|): Only about log2(n) operations, which is very fast.
     * <p>
     * Space Complexity:
     * - O(log |n|): Due to the recursion stack. Can be made O(1) if rewritten iteratively.
     */
    private static double findPowOptimal(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1.0 / x;
            N = -N;
        }
        return fastPow(x, N);
    }

    private static double fastPow(double x, long n) {
        if (n == 0) return 1.0;
        double half = fastPow(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }
}
