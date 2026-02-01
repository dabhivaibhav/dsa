package bitmanipulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Problem: Divisors of a Number
You are given an integer n. You need to find all the divisors of n. Return all the divisors of n as an array or list in a sorted order.
A number which completely divides another number is called it's divisor.

Example 1
Input: n = 6
Output = [1, 2, 3, 6]
Explanation: The divisors of 6 are 1, 2, 3, 6.

Example 2
Input: n = 8
Output: [1, 2, 4, 8]
Explanation: The divisors of 8 are 1, 2, 4, 8.

Constraints:
            1 <= n <= 1000
 */
public class FindAllDivisor {

    public static void main(String[] args) {

        int n = 6;
        System.out.println(findAllDivisorBruteForce(n));
        n = 8;
        System.out.println(findAllDivisorBruteForce(n));
        System.out.println(findAllDivisorBitwise(n));
    }


    /**
     * What it does:
     * Finds all divisors of a given integer by checking every number from 1 to n.
     * <p>
     * Why it works:
     * A divisor is any number that divides n completely without leaving a remainder.
     * By checking every number in the range, all valid divisors are guaranteed to be found.
     * <p>
     * Explanation of the approach:
     * The method iterates from 1 up to n.
     * For each value, it checks whether n is divisible by the current number.
     * If it is divisible, the number is added to the list of divisors.
     * <p>
     * Example:
     * n equals 6
     * The divisors found are 1, 2, 3, and 6.
     * <p>
     * Time complexity:
     * O(n)
     * <p>
     * Space complexity:
     * O(d)
     * Where d is the number of divisors stored in the result list.
     */

    private static List<Integer> findAllDivisorBruteForce(int n) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
            }
        }
        return divisors;
    }

    /**
     * What it does:
     * Finds all divisors of a given integer efficiently using the square root optimization.
     * <p>
     * Why it works:
     * Divisors always come in pairs.
     * If i is a divisor of n, then n divided by i is also a divisor.
     * One of these values must be less than or equal to the square root of n.
     * <p>
     * Explanation of the approach:
     * The loop runs from 1 up to the square root of n.
     * When a divisor is found, both members of the divisor pair are added.
     * If both values are the same, the divisor is added only once.
     * The final list is sorted to return the divisors in ascending order.
     * <p>
     * Example:
     * n equals 8
     * The divisor pairs are 1 and 8, 2 and 4.
     * The final sorted result is 1, 2, 4, and 8.
     * <p>
     * Time complexity:
     * O(sqrt(n))
     * <p>
     * Space complexity:
     * O(d)
     * Where d is the number of divisors stored in the result list.
     */
    private static List<Integer> findAllDivisorBitwise(int n) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i != n / i) {
                    divisors.add(n / i);
                }
            }
        }
        Collections.sort(divisors);
        return divisors;
    }
}
