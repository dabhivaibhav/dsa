package bitmanipulation;

/*
Swap Two Numbers
Given two integers a and b, swap them in-place using only 2 variables (without using a temporary variable).
Can you solve it using:
Arithmetic operations?
Bitwise XOR?

Example 1
Input: a = 5, b = 10
Output: a = 10, b = 5

Example 2
Input: a = -100, b = -200
Output: a = -200, b = -100

Constraints:
            10^9 <= a, b <= 10^9
 */
public class SwapTwoNumbers {
    public static void main(String[] args) {

        int a = 5, b = 10;
        System.out.println("Brute force");
        swapBruteForce(a, b);
        System.out.println("Bitwise");
        swapBitwise(a, b);
    }

    /**
     * swapBruteForce:
     * <p>
     * What it does:
     * Swaps the values of two integers using a temporary variable.
     * This is the most straightforward and commonly used swapping technique.
     * <p>
     * Why it works:
     * The temporary variable stores one value so it is not lost
     * when the other variable is overwritten.
     * Once both assignments are complete, the values are exchanged.
     * <p>
     * Important details:
     * - Uses an extra variable for safety and clarity.
     * - Works for all valid integer values (positive, negative, zero).
     * - This is the preferred approach in real-world production code
     * due to readability and lack of edge cases.
     * <p>
     * Example:
     * a = 5, b = 10
     * <p>
     * temp = 5
     * a = 10
     * b = 5
     * <p>
     * Result:
     * a = 10, b = 5
     * <p>
     * Complexity:
     * <p>
     * Time:  O(1)   (constant number of operations)
     * Space: O(1)   (only one extra variable)
     */
    public static void swapBruteForce(int a, int b) {
        System.out.println("Original: a: " + a + " b: " + b);
        int temp = a;
        a = b;
        b = temp;
        System.out.println("Swapped: a: " + a + " b: " + b);
    }

    /**
     * What it does:
     * Swaps the values of two integers using bitwise XOR operations,
     * without using any temporary variable.
     * <p>
     * Why it works:
     * XOR has unique properties:
     * - x ^ x = 0
     * - x ^ 0 = x
     * - XOR is reversible
     * <p>
     * By applying XOR operations in a specific sequence,
     * the original values of both variables are reconstructed
     * in swapped positions.
     * <p>
     * Important details:
     * - Uses only bitwise operations.
     * - Does not require extra memory.
     * - Works correctly for both positive and negative integers.
     * - Should not be used when a and b refer to the same memory location
     * (e.g., swapping the same variable), as it would zero out the value.
     * - Mostly used in interviews and low-level discussions,
     * not preferred in production code due to readability concerns.
     * <p>
     * Example:
     * a = 5, b = 10
     * <p>
     * Step 1: a = a ^ b → a = 15
     * Step 2: b = a ^ b → b = 5
     * Step 3: a = a ^ b → a = 10
     * <p>
     * Result:
     * a = 10, b = 5
     * <p>
     * Complexity:
     * Time:  O(1)   (constant number of operations)
     * Space: O(1)   (no extra memory used)
     */
    public static void swapBitwise(int a, int b) {
        System.out.println("Original: a: " + a + " b: " + b);
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("Swapped: a: " + a + " b: " + b);
    }


}
