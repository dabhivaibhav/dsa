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


