package bitmanipulation;

/*
Check if the i-th bit is Set or Not
Given two integers n and i, return true if the ith bit in the binary representation of n (counting from the least
significant bit, 0-indexed) is set (i.e., equal to 1). Otherwise, return false.

Example 1
Input: n = 5, i = 0
Output: true
Explanation: Binary representation of 5 is 101. The 0-th bit from LSB is set (1).

Example 2
Input: n = 10, i = 1
Output: true
Explanation: Binary representation of 10 is 1010. The 1-st bit from LSB is set (1).

Constraints:
            1 <= n <= 10^9
            0 <= i <= 31
 */
public class CheckIthBitSet {
    public static void main(String[] args) {

        int n = 5, i = 0;
        int n1 = 1;
        int n2 = 13;
        System.out.println("Brute force: ");
        System.out.println(checkIthBitSetBruteFroce(n, i));
        System.out.println(checkIthBitSetBruteFroce(n1, i));
        System.out.println(checkIthBitSetBruteFroce(n2, 1));
        System.out.println("\nBitwise: ");
        System.out.println(checkIthBitSetBitwise(n, i));
        System.out.println(checkIthBitSetBitwise(n1, i));
        System.out.println(checkIthBitSetBitwise(n2, 1));

    }

    /**
     * checkIthBitSetBruteFroce:
     * <p>
     * What it does:
     * Checks whether the i-th bit of a number is set (1) or not (0)
     * by manually converting the number into its binary representation.
     * <p>
     * Why it works:
     * A number’s binary representation explicitly shows the value of each bit.
     * By converting the number to binary and indexing from the right,
     * we can directly inspect the i-th bit.
     * <p>
     * Important details:
     * - The number is repeatedly divided by 2 to extract binary digits.
     * - Binary digits are collected in reverse order, then reversed.
     * - Bit positions are counted from the right (0-based indexing).
     * - Assumes i is a valid index within the binary length.
     * - This approach is conceptually clear but inefficient.
     * <p>
     * Example:
     * n = 13, i = 2
     * <p>
     * Binary conversion:
     * 13 → 1101
     * <p>
     * Indexing (from right, 0-based):
     * index:  3 2 1 0
     * bits :  1 1 0 1
     * <p>
     * i = 2 → bit = 1 → true
     * <p>
     * Complexity:
     * <p>
     * Time:  O(log n)   (binary conversion requires dividing by 2)
     * <p>
     * Space: O(log n)   (stores binary representation)
     */
    private static boolean checkIthBitSetBruteFroce(int n, int i) {
        StringBuilder binary = new StringBuilder();
        while (n > 0) {
            if (n % 2 == 1) binary.append(1);
            else binary.append(0);
            n = n / 2;
        }

        binary.reverse();
        return binary.charAt(binary.length() - i - 1) == '1';
    }

    /**
     * checkIthBitSetBitwise:
     * <p>
     * What it does:
     * Checks whether the i-th bit of a number is set (1) using a bitwise AND operation.
     * <p>
     * Why it works:
     * The expression (1 << i) creates a mask where only the i-th bit is set.
     * Performing AND (&) with the number keeps only that bit:
     * - If the result is non-zero → the i-th bit was set
     * - If the result is zero     → the i-th bit was not set
     * <p>
     * This avoids binary conversion and works directly at the bit level.
     * <p>
     * Important details:
     * - Bit positions are 0-based from the right.
     * - (1 << i) shifts the bit '1' left by i positions.
     * - AND (&) isolates the target bit.
     * - Works for both positive and negative integers.
     * - Preferred approach in DSA and interviews.
     * <p>
     * Example:
     * n = 13, i = 2
     * <p>
     * Binary of n:
     * 13 = 00001101
     * <p>
     * Mask creation:
     * 1 << 2 = 00000100
     * <p>
     * AND operation:
     * 00001101
     * &00000100
     * ----------
     * 00000100  → non-zero → bit is set → true
     * <p>
     * Complexity:
     * <p>
     * Time:  O(1)   (single bitwise operation)
     * <p>
     * Space: O(1)   (no extra memory used)
     */
    private static boolean checkIthBitSetBitwise(int n, int i) {
        return (n & (1 << i)) != 0;
    }
}
