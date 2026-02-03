package bitmanipulation;

/*
Leetcode 50: Implement pow(x, n), which calculates x raised to the power n (i.e., xn).

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
            -10^4 <= x^n <= 10^4
 */
public class Pow_Leetcode50 {

    public static void main(String[] args) {
        double x = 2.00000;
        int n = 10;
        System.out.println("x: " + x + ", n: " + n);
        System.out.println(computePowerBruteForce(x, n));
        System.out.println(computePowerOptimized(x, n));
        x = 2.10000;
        n = 3;
        System.out.println();
        System.out.println("x: " + x + ", n: " + n);
        System.out.println(computePowerBruteForce(x, n));
        System.out.println(computePowerOptimized(x, n));
        x = 2.00000;
        n = -2;
        System.out.println();
        System.out.println("x: " + x + ", n: " + n);
        System.out.println(computePowerBruteForce(x, n));
        System.out.println(computePowerOptimized(x, n));
    }

    /**
     * What it does:
     * Computes x raised to the power n by multiplying x repeatedly n times.
     * This is the most straightforward and intuitive implementation of power.
     * <p>
     * Why it works:
     * By definition, x raised to the power n means multiplying x by itself n times.
     * Starting with an initial value of 1 and repeatedly multiplying by x
     * produces the correct result.
     * <p>
     * Explanation of the approach:
     * - A variable answer is initialized to 1 because multiplying by 1 does not change the value.
     * - A loop runs exactly n times.
     * - In each iteration, answer is multiplied by x.
     * - After the loop finishes, answer contains x raised to the power n.
     * <p>
     * Example:
     * x = 2, n = 4
     * Iterations:
     * answer = 1
     * answer = 1 * 2 = 2
     * answer = 2 * 2 = 4
     * answer = 4 * 2 = 8
     * answer = 8 * 2 = 16
     * Final result is 16.
     * <p>
     * Important details:
     * - This approach assumes n is non-negative.
     * - It performs redundant multiplications for large n.
     * - It is easy to understand but inefficient.
     * <p>
     * Complexity:
     * Time:  O(n)
     * Space: O(1)
     * <p>
     * Interview takeaway:
     * This solution is useful for explaining the basic idea,
     * but it is not suitable when n is large.
     * In interviews, it should be followed by an optimized approach.
     */
    private static double computePowerBruteForce(double x, int n) {
        double answer = 1;
        for (int i = 0; i < n; i++) {
            answer *= x;
        }
        return answer;
    }

    /**
     * What it does:
     * Computes x raised to the power n using binary exponentiation,
     * also known as exponentiation by squaring.
     * This method efficiently handles large values of n,
     * including negative exponents.
     * <p>
     * How I think of this approach starting from brute force:
     * In the brute force solution, I multiply x exactly n times.
     * This repeats a lot of work because the same intermediate powers
     * are recomputed again and again.
     * <p>
     * The key observation is that:
     * x raised to the power n can be broken into smaller powers.
     * If n is even, x^n equals (x * x)^(n / 2).
     * If n is odd, x^n equals x * x^(n - 1).
     * <p>
     * This means I can reduce the exponent by half at each step
     * instead of reducing it by one.
     * That immediately suggests a logarithmic approach.
     * <p>
     * Why bit manipulation is used:
     * The binary representation of n tells me whether n is odd or even.
     * Checking the last bit using (n & 1) lets me decide
     * whether to multiply the current answer by x.
     * Right shifting n removes the last bit and effectively divides n by 2.
     * <p>
     * Explanation of the code line by line:
     * - The exponent n is stored in a long variable m.
     * This avoids overflow when n equals Integer.MIN_VALUE.
     * <p>
     * - If m is negative:
     * The problem x^(-n) is converted into (1/x)^n.
     * The exponent is made positive so the same loop logic can be reused.
     * <p>
     * - answer is initialized to 1.
     * This will accumulate the final result.
     * <p>
     * - The loop runs while m is greater than 0.
     * Each iteration processes one bit of the exponent.
     * <p>
     * - If the last bit of m is 1:
     * This means the current power of x contributes to the result,
     * so answer is multiplied by x.
     * <p>
     * - x is squared.
     * This represents moving to the next power of two.
     * For example, x becomes x squared, then x to the fourth, then x to the eighth.
     * <p>
     * - m is right shifted by one.
     * This discards the processed bit and moves to the next bit of the exponent.
     * <p>
     * - The loop continues until all bits of the exponent are processed.
     * <p>
     * Example:
     * x = 2, n = 10
     * Binary of 10 is 1010.
     * Step by step:
     * m = 10, even, answer unchanged, x becomes 4
     * m = 5, odd, answer = answer * 4, x becomes 16
     * m = 2, even, answer unchanged, x becomes 256
     * m = 1, odd, answer = answer * 256
     * Final answer is 1024.
     * <p>
     * Handling negative exponent example:
     * x = 2, n = -2
     * Convert to x = 1/2 and n = 2
     * Result becomes 0.25.
     * <p>
     * Important details:
     * - Works for both positive and negative exponents.
     * - Avoids overflow by using long for the exponent.
     * - Uses bitwise operations for efficiency.
     * <p>
     * Complexity:
     * Time:  O(log n)
     * Space: O(1)
     * <p>
     * Interview takeaway:
     * This algorithm reduces repeated work by squaring the base
     * and processing the exponent bit by bit.
     * It is the standard optimal solution for power computation
     * and demonstrates strong understanding of both math and bit manipulation.
     */
    private static double computePowerOptimized(double x, int n) {
        long m = n;

        if (m < 0) {
            x = 1 / x;
            m = -m;
        }
        double answer = 1.0;
        while (m > 0) {
            if ((m & 1) == 1) {
                answer *= x;
            }
            x *= x;
            m >>= 1;
        }
        return answer;
    }
}
