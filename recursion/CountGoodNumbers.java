package recursion;

/*
Leetcode: 1922. Count Good Numbers

A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are prime (2, 3, 5, or 7).
For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2) at odd positions are prime.
However, "3245" is not good because 3 is at an even index but is not even. Given an integer n, return the total number of good digit
strings of length n. Since the answer may be large, return it modulo 109 + 7. A digit string is a string consisting of digits
0 through 9 that may contain leading zeros.

Example 1:
Input: n = 1
Output: 5
Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".

Example 2:
Input: n = 4
Output: 400

Example 3:
Input: n = 50
Output: 564908303

Constraints:
            1 <= n <= 10^15
 */
public class CountGoodNumbers {


    public static void main(String[] args) {
        System.out.println(countGoodNumbers(1));
        System.out.println(countGoodNumbers(2));
        System.out.println(countGoodNumbers(3));
        System.out.println(countGoodNumbers(4));
        System.out.println(countGoodNumbers(5));

        System.out.println(countGoodNumbersRecursive(1));
        System.out.println(countGoodNumbersRecursive(2));
        System.out.println(countGoodNumbersRecursive(3));
        System.out.println(countGoodNumbersRecursive(4));
        System.out.println(countGoodNumbersRecursive(5));
        System.out.println(countGoodNumbersRecursive(50));
        System.out.println(countGoodNumbersOptimal(806166225460393L));

    }

    /**
     * countGoodNumbers
     * <p>
     * What it does:
     * Counts all "good" digit strings of length n by checking every possible number from 0 up to (10^n - 1).
     * For each number, converts it to a string of length n (with leading zeros as needed), and verifies if it is "good":
     * Digits at even indices must be even numbers (0, 2, 4, 6, 8), and digits at odd indices must be prime (2, 3, 5, 7).
     * If every digit of the number matches these rules, it increments the valid string count.
     * <p>
     * Intuition (Step-by-step explanation):
     * - The brute-force idea is: If we want to count how many n-length strings are "good", try all possible strings and check each one.
     * - For every integer from 0 to (10^n - 1), convert it to a string with exactly n digits (add leading zeros).
     * - Look at each digit position:
     * - If the index (position) is even (like 0, 2, 4, ...), the digit must be even (0, 2, 4, 6, 8).
     * - If the index is odd (1, 3, 5, ...), the digit must be one of the primes (2, 3, 5, 7).
     * - If a number meets all the rules, add 1 to the count.
     * <p>
     * Why each line matters:
     * - for (int i = 0; i < Math.pow(10, n) - 1; i++): Tries every possible n-digit number.
     * - Creates the n-length string with leading zeros for each number.
     * - Checks constraints one digit at a time for the good-number property.
     * - If any digit fails the rule, that number is skipped (not counted).
     * - The final count is the number of good digit strings of length n.
     * <p>
     * Edge Cases Handled:
     * - n = 1: Handles leading zeros, correctly counts single-digit good numbers.
     * - All numbers, including those with leading zeros (such as "0023"), are considered valid.
     * <p>
     * Example:
     * For n = 2:
     * Possible: "02", "20", "24", ..., "86" etc. Each must have even at 0, prime at 1.
     * <p>
     * Time Complexity:
     * - O(10^n * n): For each of 10^n numbers, examines n digits.
     * - Not practical for n > 6 or so.
     * <p>
     * Space Complexity:
     * - O(n): For string manipulations per number.
     * <p>
     * Limitations:
     * - Will not run in reasonable time for n > 6 due to exponential growth.
     */

    private static int countGoodNumbers(int n) {
        int count = 0;
        String s = "";
        for (int i = 0; i < Math.pow(10, n) - 1; i++) {
            String temp = "";
            s = String.valueOf(i);
            for (int j = 0; j < n - s.length(); j++) {
                temp += "0";
            }
            s = temp + s;
            int tempCount = 0;

            for (int j = 0; j < s.length(); j++) {
                int currentNum = Integer.parseInt(String.valueOf(s.charAt(j)));
                if (((j % 2 == 0) && !(currentNum % 2 == 0)) || ((j % 2 != 0) && (currentNum != 2 && currentNum != 3 && currentNum != 5 && currentNum != 7))) {
                    break;
                } else {
                    tempCount++;
                }
                if (tempCount == s.length()) {
                    count++;
                }
            }
//            System.out.println("String: " + s + " Count: " + count);
        }
        return count;
    }

    /**
     * countGoodNumbersRecursive
     * <p>
     * What it does:
     * Efficiently computes the number of "good" digit strings of length n, using recursion and the fact that the number of valid configurations can be broken down by position.
     * At each recursive step:
     * - If n == 1, there are exactly 5 ways (all even digits).
     * - For even n: Each odd index (prime-constrained) multiplies the count by 4.
     * - For odd n: Each even index (even-constrained) multiplies the count by 5.
     * - Multiplies results as it returns up the recursive stack.
     * - Always returns the result modulo 1,000,000,007.
     * <p>
     * Intuition (Step-by-step explanation):
     * - At even indices (0, 2, 4, ...), there are 5 choices (even digits). At odd indices, there are 4 choices (primes).
     * - For length n, the total number of good digit strings is 5^{number of even positions} * 4^{number of odd positions}.
     * - This can be computed recursively: each additional digit at a new position multiplies the total by 5 or by 4, depending on the position.
     * - The base case is n==1: Only need to pick one even digit, so 5 possible choices.
     * - By multiplying step-by-step and taking the result modulo 1e9+7, we avoid large numbers and work for huge n (but see limitations!).
     * <p>
     * Why each line matters:
     * - Base case (n == 1): Returns 5 (all even digits for single length).
     * - if (n % 2 == 0): When n is even, last digit must be at odd index (thus prime, so 4 choices).
     * - else: n is odd, last digit must be at even index (thus even, so 5 choices).
     * - The recursion multiplies by 5 or 4 appropriately as it unwinds.
     * <p>
     * Edge Cases Handled:
     * - n == 1: Only 5 strings possible.
     * - Very large n: Returns answer mod 1e9+7, so handles big outputs.
     * <p>
     * Example:
     * For n = 4:
     * Even indices: 0, 2 → each can be 0,2,4,6,8 (5 choices each)
     * Odd indices:  1, 3 → each can be 2,3,5,7   (4 choices each)
     * Total = 5*4*5*4 = 400
     * <p>
     * Time Complexity:
     * - O(n): Deepest recursion is n steps. (For huge n, like n=10^15, stack overflow or slowness may occur!)
     * <p>
     * Space Complexity:
     * - O(n): Recursion call stack.
     * <p>
     * Limitation:
     * - Will fail for extremely large n due to deep recursion stack or time; this is why fast exponentiation (binary power) is used for problems with n up to 10^15 in practice.
     */
    private static long countGoodNumbersRecursive(long n) {
        if (n == 1) {
            return 5;
        }
        if (n % 2 == 0) {
            return ((countGoodNumbersRecursive(n - 1) * 4L) % 1000000007L);
        } else {
            return ((countGoodNumbersRecursive(n - 1) * 5L) % 1000000007L);
        }
    }

    /**
     * countGoodNumbersOptimal
     * <p>
     * What it does:
     * Efficiently computes the number of "good" digit strings of length n using mathematical properties and modular exponentiation.
     * Directly calculates the answer as 5^even * 4^odd (modulo 1,000,000,007), where:
     *   - 'even' is the number of digits at even indices (positions 0, 2, ...), each of which can be any even digit (0,2,4,6,8).
     *   - 'odd' is the number of digits at odd indices (positions 1, 3, ...), each of which can be any prime digit (2,3,5,7).
     * Uses fast modular exponentiation to efficiently handle very large n (up to 10^15 and beyond) without integer overflow.
     *
     * Intuition (step-by-step, beginner-friendly):
     * - At each index:
     *     - Even indices have 5 choices (even digits).
     *     - Odd indices have 4 choices (prime digits).
     * - The total number of valid strings is the product of:
     *     - 5 choices for every even-indexed position, and
     *     - 4 choices for every odd-indexed position.
     * - So the answer is: 5^(ceil(n/2)) * 4^(floor(n/2)).
     * - Since n can be huge (up to 10^15), direct multiplication will not work.
     * - Instead, exponentiate using a process called "modular exponentiation" to get the answer efficiently and avoid overflow: (a*b) % mod = [(a%mod)*(b%mod)]%mod.
     * - The answer is always returned modulo 1_000_000_007 (a common large prime for programming problems) to prevent overflow and because the output may be huge.
     *
     * Why each line matters:
     * - long even = (n+1)/2; // Calculates how many even indices (including position 0)
     * - long odd = n/2;      // Calculates how many odd indices
     * - modPow(5L, even, 1_000_000_007L): Efficiently computes 5^even % MOD
     * - modPow(4L, odd, 1_000_000_007L): Efficiently computes 4^odd % MOD
     * - Multiplies and mods the two results to get the final answer.
     *
     * Edge Cases Handled:
     * - n = 1: Only considers one even position, so returns 5.
     * - Very large n (up to 10^15): Still efficient.
     * - Handles modulo at every step to prevent overflow.
     *
     * Example:
     * For n = 4:
     *   even = 2, odd = 2 → answer = 5^2 * 4^2 % 1_000_000_007 = 25 * 16 = 400
     *
     * Time Complexity:
     * - O(log n): Due to modular exponentiation.
     *
     * Space Complexity:
     * - O(1): Only a constant number of variables are used.
     */

    private static int countGoodNumbersOptimal(long n) {
        long even = (n + 1) / 2;
        long odd = n / 2;
        long a = modPow(5L, even, 1_000_000_007L);
        long b = modPow(4L, odd, 1_000_000_007L);
        return (int) ((a * b) % 1_000_000_007L);
    }

    /**
     * modPow
     * <p>
     * What it does:
     * Implements fast modular exponentiation to compute (base^exp) % mod.
     * Efficiently raises 'base' to the 'exp' power while taking the result modulo 'mod' at every step.
     * Allows for the computation of large exponents without overflow, even for very big numbers.
     *
     * Intuition (step-by-step, beginner-friendly):
     * - Directly multiplying base^exp is not practical for large exp (like exp=10^15), as it would take too long and cause overflow.
     * - Instead, use the fact that:
     *     - (a * b) % mod = [(a % mod) * (b % mod)] % mod
     *     - (base^exp) can be rewritten as (base^(exp/2))^2 if exp is even,
     *       or as base * (base^(exp-1)) if exp is odd.
     * - This algorithm works by squaring the base and halving the exponent at every step (binary exponentiation).
     * - Whenever the exponent is odd, multiply the running result by the base.
     * - Keep taking % mod at each multiplication to prevent overflow.
     *
     * Why each line matters:
     * - long result = 1L;  // Initializes the result.
     * - base %= mod;       // Reduces initial base to [0, mod)
     * - while (exp > 0):   // Processes each bit of the exponent.
     *      - If exp is odd, multiply result by base modulo mod.
     *      - Square the base (mod mod). Shift exponent right by 1 (divide by 2).
     * - The result is base^exp % mod, computed efficiently.
     *
     * Example:
     * modPow(5, 3, 100) → (5^3)%100 = 125%100 = 25
     *   Steps: 1 * 5 = 5, 5*5=25, 25*5=125, 125%100=25
     *
     * Time Complexity:
     * - O(log exp): Very fast, only log2(exp) multiplications.
     *
     * Space Complexity:
     * - O(1): Constant space.
     */
    private static long modPow(long base, long exp, long mod) {
        long result = 1L;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1L) == 1L) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }
}
