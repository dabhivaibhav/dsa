package bitmanipulation;

/*
Problem: XOR of numbers in a given range

Given two integers L and R. Find the XOR of the elements in the range [L , R].

Example 1:
Input : L = 3 , R = 5
Output : 2
Explanation : answer = (3 ^ 4 ^ 5) = 2.

Example 2:
Input : L = 1, R = 3
Output : 0
Explanation : answer = (1 ^ 2 ^ 3) = 0.

Constraints:
            1 <= L <= R <= 10^9
 */
public class FindXOROfNumbersInGivenRange {

    public static void main(String[] args) {
        System.out.println(findXORBruteForce(3, 5));
        System.out.println(findXORBruteForce(1, 3));
        System.out.println(findXOROptimized(3, 5));
        System.out.println(findXOROptimized(1, 3));
    }

    /**
     * What it does:
     * Computes the XOR of all integers in the range [L, R] by iterating
     * through every number and XORing them one by one.
     * <p>
     * Why it works:
     * XOR is associative and commutative, so the order of XOR operations
     * does not matter. By XORing all values from L to R sequentially,
     * we obtain the correct result.
     * <p>
     * Important details:
     * - Initializes the result with 0 because x ^ 0 = x.
     * - Iterates through the entire range from L to R.
     * - Simple and intuitive, but inefficient for large ranges.
     * <p>
     * Example:
     * L = 3, R = 5
     * <p>
     * XOR process:
     * 0 ^ 3 = 3
     * 3 ^ 4 = 7
     * 7 ^ 5 = 2
     * <p>
     * Final result = 2
     * <p>
     * Complexity:
     * Time:  O(R - L + 1)   (iterates through the entire range)
     * Space: O(1)           (constant extra space)
     */

    private static int findXORBruteForce(int L, int R) {
        int number = 0;
        for (int i = L; i <= R; i++) {
            number ^= i;
        }
        return number;
    }


    /**
     * What it does:
     * Computes the XOR of all integers in the range [L, R] using a mathematical
     * pattern instead of iterating through each number.
     * <p>
     * Why it works:
     * XOR of numbers from 0 to n follows a repeating pattern based on n % 4:
     * <p>
     * n % 4 == 0 → XOR(0…n) = n
     * n % 4 == 1 → XOR(0…n) = 1
     * n % 4 == 2 → XOR(0…n) = n + 1
     * n % 4 == 3 → XOR(0…n) = 0
     * <p>
     * Using this property:
     * XOR(L…R) = XOR(0…R) ^ XOR(0…L−1)
     * <p>
     * This works because numbers before L cancel out due to XOR properties.
     * <p>
     * Important details:
     * - Avoids looping over large ranges.
     * - Uses a helper function to compute XOR from 0 to n in O(1).
     * - Works efficiently even when L and R are as large as 10^9.
     * <p>
     * Example:
     * L = 3, R = 5
     * <p>
     * XOR(0…5) = 1
     * XOR(0…2) = 3
     * <p>
     * XOR(3…5) = 1 ^ 3 = 2
     * <p>
     * Complexity:
     * Time:  O(1)
     * Space: O(1)
     */
    private static int findXOROptimized(int L, int R) {
        return findXORHelper(L - 1) ^ findXORHelper(R);

    }

    /**
     * What it does:
     * Computes the XOR of all integers from 0 to n using a fixed pattern.
     * <p>
     * Why it works:
     * XOR results repeat every 4 numbers due to binary cancellation:
     * <p>
     * Sequence of XOR(0…n):
     * n = 0 → 0
     * n = 1 → 1
     * n = 2 → 3
     * n = 3 → 0
     * n = 4 → 4
     * n = 5 → 1
     * n = 6 → 7
     * n = 7 → 0
     * <p>
     * This pattern depends only on n % 4, allowing constant-time computation.
     * <p>
     * Important details:
     * - Uses modulo 4 to identify the correct XOR result.
     * - Eliminates the need for loops or recursion.
     * - Core building block for optimized range XOR problems.
     * <p>
     * Complexity:
     * Time:  O(1)
     * Space: O(1)
     */
    private static int findXORHelper(int n) {
        if (n % 4 == 1) return 1;
        else if (n % 4 == 2) return n + 1;
        else if (n % 4 == 3) return 0;
        else return n;

    }
}
