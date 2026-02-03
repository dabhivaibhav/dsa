package bitmanipulation;

import java.util.Arrays;

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
        System.out.println(countPrimeOptimized(n));
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

    /**
     * What it does:
     * Counts how many prime numbers are strictly less than n using the Sieve of Eratosthenes.
     * Instead of checking each number one by one for primality, it marks the numbers that are
     * definitely not prime and then counts what remains.
     * <p>
     * How I arrive at this approach starting from the brute force idea:
     * In the brute force solution, for every i I try to find a divisor j.
     * That means I repeatedly do the same work again and again.
     * For example, I check whether 2 divides many different numbers, then I check whether 3 divides many different numbers,
     * and so on. This repeated divisor checking is wasteful.
     * <p>
     * The key realization is this:
     * If a number i is prime, then all multiples of i are not prime.
     * So instead of discovering non primes separately while checking each i, I can proactively mark all multiples as non prime.
     * This converts the problem from repeated checking into systematic marking.
     * That is the idea of the sieve of erastosthenes.
     * <p>
     * Why an array is used:
     * I need to remember for every number from 0 to n - 1 whether it is prime or not.
     * A boolean array is the simplest and fastest structure for this.
     * Index represents the number and the value represents whether it is currently considered prime.
     * This allows constant time marking and constant time checking.
     * <p>
     * Explanation of the code line by line:
     * - If n is less than or equal to 2, return 0.
     * This is because primes start from 2 and the question asks for primes strictly less than n.
     * If n is 0, 1, or 2, there are no primes less than n.
     * <p>
     * - Create a boolean array of size n.
     * This array stores the current prime status of each number from 0 to n - 1.
     * isPrime[x] being true means x is still considered prime.
     * isPrime[x] being false means x is known to be not prime.
     * <p>
     * - Fill the array with true.
     * This is an initial assumption that every number is prime.
     * The sieve works by starting optimistic and then marking only the numbers that are definitely not prime.
     * <p>
     * - Set isPrime[0] and isPrime[1] to false.
     * 0 and 1 are not prime by definition, so they must be excluded explicitly.
     * <p>
     * Explanation of the outer loop:
     * - The loop runs i from 2 while i times i is less than n.
     * I only need to sieve using i values up to sqrt(n - 1).
     * The reason is that if a number x is composite ( a number which is divisible other than 1 and themselves),
     * it has at least one factor less than or equal to sqrt(x).
     * So every composite number less than n will be marked by some i at most sqrt(n - 1).
     * <p>
     * - Inside the loop, I check if isPrime[i] is true.
     * This means i is still considered prime, so it is worth using i to mark its multiples.
     * If isPrime[i] is false, i is already known to be composite, and its multiples would have already been marked
     * by smaller primes, so I skip it.
     * <p>
     * Explanation of the inner loop and why it starts from i times i:
     * - The inner loop marks multiples of i as not prime.
     * It starts from i times i and increases by i each time.
     * <p>
     * Why starting from i times i is correct:
     * All multiples smaller than i times i have already been handled by smaller factors.
     * For example, if i is 5, then 10, 15, and 20 were already marked when i was 2 or 3 or 4.
     * Starting from i times i avoids repeated work and keeps the sieve efficient.
     * <p>
     * Why j increases by i:
     * j represents multiples of i.
     * j equals i times i, then i times (i + 1), then i times (i + 2), and so on.
     * Adding i each time moves to the next multiple of i.
     * <p>
     * - Each time in the inner loop, I set isPrime[j] to false.
     * This marks j as composite because it has i as a divisor.
     * <p>
     * Counting phase:
     * - After all marking is done, I count how many indices from 2 to n - 1 are still true.
     * Those indices are primes because they were never marked as composite.
     * <p>
     * Example:
     * n = 10
     * Initially, assume all are prime except 0 and 1.
     * i = 2 is prime, mark 4, 6, 8 as not prime.
     * i = 3 is prime, mark 9 as not prime.
     * i = 4 is not prime, skip.
     * Remaining true indices are 2, 3, 5, 7.
     * Count is 4.
     * <p>
     * Complexity:
     * Time:O(n) + O(n log log n) + O(n)
     * so the first is to fill the array with default value,
     * second is to set the non prime numbers to false,
     * and third is for coutnign total of prime number
     * Space: O(n) - Using extra space to store the prime numbers
     * <p>
     * Interview takeaway:
     * The brute force solution checks primality repeatedly and wastes work.
     * The sieve flips the thinking: instead of checking each number for divisors,
     * mark all multiples of primes as composite once, then count what remains.
     * This is the standard optimal approach for counting primes under large constraints.
     */
    private static int countPrimeOptimized(int n) {
        if (n <= 2) return 0;

        //Taking the array to precompute about how many prime numbers in the array
        boolean[] isPrime = new boolean[n];
        // Fill the array - assume everything is prime initially
        Arrays.fill(isPrime, true);

        // 0 and 1 are not prime so setting them to false
        isPrime[0] = false;
        isPrime[1] = false;

        //I am executing the for loop upto i*i because i don't have to go upto N
        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                // Optimization: Start marking from i*i and also increasing the j by i so if i is 5 so it will start
                // from 25 directly and will makr everything to false like 30,35,40.
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        // Count the remaining 'true' values
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) count++;
        }

        return count;
    }
}
