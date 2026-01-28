package bitmanipulation;

/*
Given a non-negative integer n, determine whether it is odd.
Return true if the number is odd, otherwise return false.
A number is odd if it is not divisible by 2 (i.e., n % 2 != 0).

Example 1
Input: n = 7
Output: true
Explanation: 7 is not divisible by 2. Hence, it is odd.

Example 2
Input: n = 0
Output: false
Explanation: 0 is divisible by 2. Hence, it is not odd.

Constraints:
            0 <= n <= 10^4
 */
public class CheckNumberOddEven {

    public static void main(String[] args) {

        int n = 7;
        System.out.println(checkNumberIsOddOrEvenBruteForce(n));
        System.out.println(checkNumberIsOddOrEvenBitwise(n));

        int n1 = 8;
        System.out.println(checkNumberIsOddOrEvenBruteForce(n1));
        System.out.println(checkNumberIsOddOrEvenBitwise(n1));

    }


    private static String checkNumberIsOddOrEvenBruteForce(int n) {
        return n % 2 != 0 ? "Odd" : "Even";
    }

    /**
     * What it does:
     * Determines whether a given integer is Odd or Even using a bitwise operation.
     * It inspects the least significant bit (LSB) of the number using the AND (&) operator.
     * <p>
     * Why it works:
     * In binary representation:
     * - Even numbers always end with 0
     * - Odd numbers always end with 1
     * <p>
     * The expression (n & 1) isolates the last bit:
     * - If the last bit is 1 → number is Odd
     * - If the last bit is 0 → number is Even
     * <p>
     * This works because the bitwise AND compares bits position by position,
     * and 1 & 1 = 1 while 0 & 1 = 0.
     * <p>
     * Important details:
     * - Uses bitwise AND instead of modulo (%), which is faster at the CPU level.
     * - Works for both positive and negative integers.
     * - Only checks the least significant bit; no division or remainder needed.
     * - Commonly used in performance-sensitive code and DSA interviews.
     * <p>
     * Example:
     * n = 7
     * Binary of 7  = 00000111
     * Binary of 1  = 00000001
     * <p>
     * 00000111
     * &00000001
     * ----------
     * 00000001  → result is 1 → Odd
     * <p>
     * n = 8
     * Binary of 8  = 00001000
     * <p>
     * 00001000
     * &00000001
     * ----------
     * 00000000  → result is 0 → Even
     * <p>
     * Complexity:
     * Time:  O(1)   (single bitwise operation)
     * Space: O(1)   (no extra memory used)
     */
    private static String checkNumberIsOddOrEvenBitwise(int n) {
        return (n & 1) == 1 ? "Odd" : "Even";
    }
}
