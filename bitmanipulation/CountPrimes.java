package bitmanipulation;

/*
Leetcode: 204. Count Primes

Given an integer n, return the number of prime numbers that are strictly less than n.

Example 1:
Input: n = 10
Output: 4
Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.

Example 2:
Input: n = 0
Output: 0

Example 3:
Input: n = 1
Output: 0

Constraints:
            0 <= n <= 5 * 106
 */
public class CountPrimes {

    public static void main(String[] args) {

        int n = 10;
        System.out.println(countPrimeBruteForce(n));
    }

    /**
     * What it does:
     * Counts the number of prime numbers that are strictly less than a given integer n
     * using a brute force primality check for each number.
     * <p>
     * Why it works:
     * A prime number is defined as a number greater than 1 that has no divisors
     * other than 1 and itself.
     * By checking every number from 2 up to n - 1 and verifying whether it has
     * any divisor, we can correctly identify all prime numbers.
     * <p>
     * Explanation of the approach:
     * - A counter variable is initialized to keep track of how many prime numbers are found.
     * - The outer loop iterates through every number starting from 2 up to n - 1.
     * Each of these numbers is treated as a candidate prime.
     * - For each candidate number, a boolean flag is set to true assuming the number is prime.
     * - The inner loop checks divisibility of the candidate number by all integers
     * starting from 2 up to the candidate number - 1.
     * - If the candidate number is divisible by any of these values, it is not prime,
     * the flag is set to false, and the inner loop terminates early.
     * - After the inner loop completes, if the flag is still true,
     * the candidate number is confirmed as prime and the counter is incremented.
     * - Once all numbers less than n are checked, the counter is returned.
     * <p>
     * Example:
     * n = 10
     * Numbers checked: 2, 3, 4, 5, 6, 7, 8, 9
     * Prime numbers identified: 2, 3, 5, 7
     * Final count returned: 4
     * <p>
     * Important details:
     * - Numbers 0 and 1 are not considered prime and are skipped implicitly.
     * - The inner loop stops early as soon as a divisor is found.
     * - This approach prioritizes clarity over performance.
     * <p>
     * Complexity:
     * Time: O(n^2) (nested loops checking divisibility)
     * Space: O(1) (constant extra space)
     * <p>
     * Interview takeaway:
     * This is the simplest and most intuitive way to count primes.
     * In interviews, it should be presented first,
     * followed by mentioning optimizations like checking up to sqrt(i)
     * or using the Sieve of Eratosthenes for large n.
     */
    private static int countPrimeBruteForce(int n) {
        int count = 0;

        for (int i = 2; i < n; i++) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) count++;
        }
        return count;
    }
}
