package bitmanipulation;

/*
Check if a Number is Power of 2 or Not

Given an integer n, return true if it is a power of two. Otherwise, return false.
An integer n is a power of two if there exists an integer x such that n == 2x.
You must solve it without using loops or recursion.


Example 1:
Input: n = 1
Output: true
Explanation: 20 = 1

Example 2:
Input: n = 16
Output: true
Explanation: 24 = 16

constraints:
            1 <= n <= 2^31 - 1
 */
public class CheckNumberIsPowerOfTwo {
    public static void main(String[] args) {

    }

    /**
     * isPowerOfTwoBruteForce() method:
     * <p>
     * What it does:
     * Checks whether a number is a power of 2 by repeatedly dividing it by 2.
     * <p>
     * Why it works:
     * A power of 2 can be reduced to 1 by dividing by 2 without remainder.
     * Any non-power of 2 will eventually produce an odd number (>1).
     * <p>
     * Important details:
     * - n must be positive.
     * - Stops early if remainder appears.
     * <p>
     * Example:
     * n = 16
     * 16 → 8 → 4 → 2 → 1 → true
     * <p>
     * n = 12
     * 12 → 6 → 3 → stop → false
     * <p>
     * Complexity:
     * Time:  O(log n)
     * Space: O(1)
     */
    private static boolean isPowerOfTwoBruteForce(int n) {
        if (n <= 0) return false;

        while (n > 1) {
            if (n % 2 != 0) return false;
            n = n / 2;
        }
        return true;
    }

    /**
     * isPowerOfTwoBitwise:
     * <p>
     * What it does:
     * Determines whether a given integer is a power of 2 using a bitwise operation.
     * <p>
     * Why it works:
     * A power of 2 has exactly one set bit (1) in its binary representation.
     * Subtracting 1 from such a number flips that set bit to 0 and turns all lower bits to 1.
     * Performing bitwise AND (&) between n and (n - 1) clears the only set bit.
     * <p>
     * If the result is 0, the number had exactly one set bit → power of 2.
     * <p>
     * Important details:
     * - n must be greater than 0 (0 and negative numbers are not powers of 2).
     * - Uses bitwise operations instead of loops or division.
     * - This is the most efficient and commonly expected solution in DSA interviews.
     * - Relies on the binary structure of numbers, not arithmetic properties.
     * <p>
     * Example:
     * n = 8
     * <p>
     * Binary of n:
     * 8  = 00001000
     * 7  = 00000111
     * <p>
     * AND operation:
     * 00001000
     * &00000111
     * ----------
     * 00000000  → result is 0 → power of 2
     * <p>
     * n = 10
     * <p>
     * Binary:
     * 10 = 00001010
     * 9  = 00001001
     * <p>
     * 00001010
     * &00001001
     * ----------
     * 00001000  → non-zero → not a power of 2
     * <p>
     * Complexity:
     * Time:  O(1)   (single bitwise operation)
     * Space: O(1)   (no extra memory used)
     */
    private static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
