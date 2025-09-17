package binarysearch.BSOnAnswers;

/*
Find square root of a number

Given a positive integer n. Find and return its square root.
If n is not a perfect square, then return the floor value of sqrt(n).

Examples:
Input: n = 36
Output: 6
Explanation: 6 is the square root of 36.

Input: n = 28
Output: 5
Explanation: The square root of 28 is approximately 5.292. So, the floor value will be 5.

Constraints:
             0 <= n <= 2^31 - 1
 */
public class FindSqrt {

    public static void main(String[] args) {
        int num1 = 28;
        int num2 = 36;
        findSqrtBruteForce(num1);
        findSqrtOptimalApproach(num2);
    }

    /**
     * findSqrtBruteForce
     * <p>
     * What it does:
     * Finds the floor of the square root of a given positive integer n using a simple linear search.
     * <p>
     * Why it works:
     * - Start from 1 and keep checking i*i <= n.
     * - When i*i becomes greater than n, stop — the previous i is the floor of sqrt(n).
     * <p>
     * Time Complexity:
     * - O(n): Checks all numbers from 1 to n in the worst case.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a constant amount of extra space.
     * <p>
     * Output:
     * Prints the floor value of the square root of n.
     * <p>
     * Example:
     * Input: 28
     * Output: 5  (since sqrt(28) ≈ 5.29 and floor is 5)
     */

    private static void findSqrtBruteForce(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (i * i <= n) {
                ans = i;
            } else {
                break;
            }
        }
        System.out.println("Brute force approach: Square root of " + n + " is: " + ans);
    }

    /**
     * findSqrtOptimalApproach
     * <p>
     * What it does:
     * Finds the floor of the square root of a given positive integer n using binary search.
     * <p>
     * Why it works:
     * - The square function is monotonically increasing: if x < y then x² < y².
     * - Use binary search between 1 and n:
     * - If mid*mid <= n, store mid as a potential answer and move right (low = mid + 1)
     * to see if there’s a larger valid number.
     * - If mid*mid > n, move left (high = mid - 1) to find a smaller number.
     * - This keeps shrinking the search space until low > high.
     * - By the time the loop ends, `high` points to the greatest number whose square is <= n,
     * which is exactly the floor of sqrt(n). So `high` could directly be used as the answer.
     * <p>
     * Time Complexity:
     * - O(log n): Binary search cuts the range in half each iteration.
     * <p>
     * Space Complexity:
     * - O(1): Uses only a constant amount of extra space.
     * <p>
     * Output:
     * Prints the floor value of the square root of n.
     * <p>
     * Example:
     * Input: 36
     * Output: 6  (since sqrt(36) is exactly 6)
     */
    private static void findSqrtOptimalApproach(int n) {
        int ans = 0;

        int low = 1;
        int high = n;


        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (mid * mid <= n) {
                ans = mid;
                low = mid + 1;
            } else if (mid * mid > n) {
                high = mid - 1;
            }
        }
        System.out.println("Optimal approach: Square root of " + n + " is: " + ans);
    }
}


