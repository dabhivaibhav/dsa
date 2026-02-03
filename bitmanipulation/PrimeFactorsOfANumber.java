package bitmanipulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Problem: Prime factorisation of a Number

You are given an integer array queries of length n.
Return the prime factorization of each number in array queries in sorted order.

Example 1
Input: queries = [2, 3, 4, 5, 6]
Output: [ [2], [3], [2, 2], [5], [2, 3] ]
Explanation: The values 2, 3, 5 are itself prime numbers.
The prime factorization of 4 will be --> 2 * 2.
The prime factorization of 6 will be --> 2 * 3.

Example 2
Input: queries = [7, 12, 18]
Output: [ [7], [2, 2, 3], [2, 3, 3] ]
Explanation: The value 7 itself is a prime number.
The prime factorization of 12 will be --> 2 * 2 * 3.
The prime factorization of 18 will be --> 2 * 3 * 3.

Constraints:
            1 <= n <= 10^5
            2 <= queries[i] <= 2*10^5
 */
public class PrimeFactorsOfANumber {
    public static void main(String[] args) {

        int[] queries = {2, 3, 4, 5, 6};
        List<List<Integer>> result = findPrimeFactors(queries);
        System.out.println(result);
    }

    /**
     * What it does:
     * For each number in the input array queries, computes its prime factorization and returns the factors
     * in sorted order for every number.
     * <p>
     * How I think of this approach from the naive idea:
     * The simplest way to factor a number is to try dividing it by 2, then 3, then 4, and so on,
     * and whenever division works, record the factor and continue.
     * That works for one number, but it becomes too slow when there are many queries.
     * <p>
     * The key improvement is to avoid repeating the same division checks for every query.
     * Since all query values are limited by a maximum value, I can precompute useful information once
     * and reuse it for every query.
     * <p>
     * The information I precompute is the smallest prime factor for every number up to MAX_NUM.
     * Once I know the smallest prime factor of a number, I can factorize it quickly by repeatedly dividing
     * by that smallest prime factor until the number becomes 1.
     * <p>
     * Why the smallest prime factor array is needed:
     * I store an array spf where spf[x] represents the smallest prime factor of x.
     * If x is prime, then spf[x] equals x.
     * If x is composite, spf[x] is the smallest prime number that divides x.
     * This allows factorization of any number in almost constant work per factor.
     * <p>
     * Explanation of the code in primeFactors method line by line:
     * - MAX_NUM is chosen as the maximum value up to which we want precomputation.
     * Every query number must be less than or equal to this maximum for correctness.
     * <p>
     * - result is created but not used in the final return. The method builds ans and returns ans.
     * This means result can be removed without changing the outcome.
     * <p>
     * - spf is created with size MAX_NUM + 1 so that indexing can be done directly by number.
     * Index 0 and 1 are included to make indexing simple even though factorization starts from 2.
     * <p>
     * - Arrays.fill(spf, 1) sets every entry to 1.
     * In this implementation, the value 1 is used as a marker meaning the smallest prime factor
     * has not been assigned yet.
     * <p>
     * - sieve(spf, MAX_NUM) is called to fill the spf array.
     * After this call:
     * spf[x] contains the smallest prime factor of x for all x from 2 to MAX_NUM.
     * <p>
     * - ans is created as the final list of answers.
     * ans will store one list of prime factors for each query in the same order as queries.
     * <p>
     * - For each query number, primeFact(query, spf, MAX_NUM) is called.
     * This generates the prime factorization using the precomputed spf array.
     * The result list is added into ans.
     * <p>
     * - Finally ans is returned.
     * <p>
     * Helper method primeFact explanation line by line:
     * - A new list ans is created to store prime factors for the current number n.
     * <p>
     * - The loop runs while n is not equal to 1.
     * This loop reduces n step by step by dividing out prime factors.
     * <p>
     * - ans.add(SPF[n]) adds the smallest prime factor of the current n.
     * This is guaranteed to be a prime number.
     * <p>
     * - n = n / SPF[n] removes one occurrence of that prime factor from n.
     * If the prime factor appears multiple times, it will be added multiple times
     * because the loop continues until n becomes 1.
     * <p>
     * Why the factor list is already sorted:
     * Every step removes the smallest prime factor of the current number.
     * That means factors are produced in non decreasing order naturally.
     * So no extra sorting is required in primeFact.
     * <p>
     * Example for primeFact:
     * n = 12
     * smallest prime factor is 2, add 2, n becomes 6
     * smallest prime factor is 2, add 2, n becomes 3
     * smallest prime factor is 3, add 3, n becomes 1
     * output becomes [2, 2, 3]
     * <p>
     * Helper method sieve explanation line by line:
     * The goal of sieve is to fill SPF so that SPF[x] stores the smallest prime factor for x.
     * <p>
     * - The outer loop runs i from 2 to MAX_N.
     * i represents the current candidate prime.
     * <p>
     * - If SPF[i] equals 1, it means i has not been marked by any smaller prime,
     * which implies i is prime.
     * <p>
     * - The inner loop runs through multiples of i, starting from i itself and increasing by i.
     * For each multiple j:
     * If SPF[j] is still 1, it means no smaller prime has assigned a factor to j yet.
     * Therefore i must be the smallest prime factor of j, so SPF[j] is set to i.
     * <p>
     * Why we check SPF[j] equals 1 before setting:
     * This ensures we only assign the smallest prime factor once.
     * A number may be a multiple of many primes, but the first prime that reaches it in the sieve
     * is the smallest prime factor. Once assigned, it should not be overwritten.
     * <p>
     * Example intuition for sieve:
     * When i equals 2, we set SPF of every even number to 2 if it is not already set.
     * When i equals 3, we set SPF of multiples of 3 that have not already been assigned.
     * Multiples like 6 already have SPF 2 from the earlier step, so they are not overwritten.
     * <p>
     * Complexity:
     * Precomputation sieve time: O(MAX_N log log MAX_N)
     * Each query factorization time: O(log q) in the sense that each division reduces the number,
     * and the number of prime factors is at most proportional to log q
     * Total time: O(MAX_N log log MAX_N + n log MAX_N)
     * Space: O(MAX_N) for the spf array plus output storage
     * <p>
     * Interview takeaway:
     * When there are many factorization queries and the values are bounded,
     * precompute the smallest prime factor array once using a sieve.
     * Then factorize each number by repeatedly dividing by its smallest prime factor.
     * This avoids repeated divisor searching and scales well for large number of queries.
     * <p>
     * Important note for correctness:
     * MAX_NUM must be at least the maximum value present in queries.
     * If queries can contain values up to 200000, then MAX_NUM should be set accordingly.
     */
    private static List<List<Integer>> findPrimeFactors(int[] queries) {

        final int MAX_NUM = 100000;
        List<List<Integer>> result = new ArrayList<>();

        int[] spf = new int[MAX_NUM + 1];
        Arrays.fill(spf, 1);


        /* Precompute the smallest
        possible factor for all numbers */
        sieve(spf, MAX_NUM);

        // To store the answer
        List<List<Integer>> ans = new ArrayList<>();

        // Iterate on each number in query
        for (int query : queries) {
            /* Function call to get the prime
            factorization of current number */
            ans.add(primeFact(query, spf, MAX_NUM));
        }

        // Return the answer
        return ans;
    }


    /* Helper function to find the prime
    factorization of a number */
    private static List<Integer> primeFact(int n, int[] SPF, int MAX_N) {
        // To store the result
        List<Integer> ans = new ArrayList<>();

        // Until the number is not reduced to 1
        while (n != 1) {
            // Add the smallest prime factor to the list
            ans.add(SPF[n]);
            // Update the number
            n = n / SPF[n];
        }

        // Return the result
        return ans;
    }

    private static void sieve(int[] SPF, int MAX_N) {

        // Iterate on all the number
        for (int i = 2; i <= MAX_N; i++) {
            // If the number is a prime number
            if (SPF[i] == 1) {
                /* Mark all its multiples who have not
                received their smallest prime factor yet*/
                for (int j = i; j <= MAX_N; j += i) {
                    // If smallest prime factor not received yet
                    if (SPF[j] == 1) {
                        /* Store i as the smallest
                        prime factor for its multiple */
                        SPF[j] = i;
                    }
                }
            }
        }
    }
}
