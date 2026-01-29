/*
Leetcode: 29. Divide Two Integers

Given two integers dividend and divisor, divide two integers without using multiplication, division, and mod operator.
The integer division should truncate toward zero, which means losing its fractional part. For example, 8.345 would be
truncated to 8, and -2.7335 would be truncated to -2. Return the quotient after dividing dividend by divisor.

Note: Assume we are dealing with an environment that could only store integers within the 32-bit signed integer
range: [−231, 231 − 1]. For this problem, if the quotient is strictly greater than 231 - 1, then return 231 - 1, and
if the quotient is strictly less than -231, then return -231.

Example 1:
Input: dividend = 10, divisor = 3
Output: 3
Explanation: 10/3 = 3.33333.. which is truncated to 3.

Example 2:
Input: dividend = 7, divisor = -3
Output: -2
Explanation: 7/-3 = -2.33333.. which is truncated to -2.

Constraints:
            -2^31 <= dividend, divisor <= 2^31 - 1
            divisor != 0
 */
public class DivisionByBitWiseOperation {

    public static void main(String[] args) {
        int dividend = 22, divisor = 3;
        System.out.println(divide(dividend, divisor));
        int dividend1 = -(int) Math.pow(2, 31);
        System.out.println(divide(dividend1, 1));
        System.out.println(divide(-2147483648, 2));
    }

    /**
     * What it does:
     * Divides two integers without using multiplication, division, or modulo operators,
     * and returns the quotient truncated toward zero.
     * <p>
     * Why it works:
     * Division can be simulated using repeated subtraction combined with bit shifting.
     * Instead of subtracting the divisor one unit at a time (which would be slow),
     * the algorithm exponentially increases the divisor using left shifts to subtract
     * the largest possible multiple at each step.
     * <p>
     * This mimics long division in binary:
     * - Left shifting the divisor by k positions multiplies it by 2^k
     * - Subtracting the largest shifted value reduces the dividend efficiently
     * <p>
     * Important details:
     * - Handles overflow explicitly when dividend = Integer.MIN_VALUE and divisor = -1
     * (since the result exceeds the 32-bit signed integer range).
     * - Converts operands to long before taking absolute values to avoid overflow.
     * - Determines the sign of the result using the signs of dividend and divisor.
     * - Truncates toward zero, matching integer division behavior.
     * - Uses bitwise left shifts to achieve logarithmic performance.
     * <p>
     * Example:
     * dividend = 22, divisor = 3
     * <p>
     * Binary-style division:
     * - 3 << 2 = 12   → subtract → remaining = 10, quotient += 4
     * - 3 << 1 = 6    → subtract → remaining = 4,  quotient += 2
     * - 3 << 0 = 3    → subtract → remaining = 1,  quotient += 1
     * <p>
     * Final quotient = 7
     * <p>
     * Example with sign:
     * dividend = 7, divisor = -3
     * Absolute division gives 2
     * Sign adjustment → -2
     * <p>
     * Complexity:
     * Time:  O(log n)^2   (each subtraction removes a power-of-two multiple)
     * Space: O(1)       (constant extra space)
     */
    private static int divide(int dividend, int divisor) {
        if (dividend == divisor) return 1;
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        if (divisor == 1) return dividend;

        boolean sign = true;
        if (dividend < 0 && divisor >= 0) sign = false;
        else if (dividend >= 0 && divisor < 0) sign = false;

        long n = Math.abs((long) dividend);
        long d = Math.abs((long) divisor);

        int answer = 0;

        while (n >= d) {
            int count = 0;
            while (n >= (d << (count + 1))) {
                count++;
            }
            answer += 1 << count;
            n = n - (d * (1L << count));
        }

        return sign ? answer : -answer;
    }
}
