package bitmanipulation;

/*
Leetcode 693: Binary Number with Alternating Bits

Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits will always have different values.

Example 1:
Input: n = 5
Output: true
Explanation: The binary representation of 5 is: 101

Example 2:
Input: n = 7
Output: false
Explanation: The binary representation of 7 is: 111.

Example 3:
Input: n = 11
Output: false
Explanation: The binary representation of 11 is: 1011.

Constraints:
            1 <= n <= 2^31 - 1
 */
public class BinaryNumberWithAlternatingBits {

    public static void main(String[] args) {

        int n = 5;
        System.out.println("Number " + n + " has " + hasAlternatingBitsBruteForce(n));
        n = 7;
        System.out.println("Number " + n + " has " + hasAlternatingBitsBruteForce(n));
        n = 11;
        System.out.println("Number " + n + " has " + hasAlternatingBitsBruteForce(n));
        n = 10;
        System.out.println("Number " + n + " has " + hasAlternatingBitsBruteForce(n));
    }

    /**
     * Method: hasAlternatingBitsBruteForce(int n)
     * <p>
     * What this method does:
     * Checks whether the binary representation of a number contains
     * alternating bits where every adjacent bit is different.
     * <p>
     * Core Idea:
     * Extract bits one by one from right to left and compare
     * each bit with the previous bit.
     * <p>
     * Thought Process:
     * If a number has alternating bits, then every adjacent pair
     * of bits must be different.
     * <p>
     * Example:
     * 5  → binary 101 → alternating → true
     * 7  → binary 111 → not alternating → false
     * <p>
     * So the simple approach is:
     * 1. Take the last bit.
     * 2. Compare it with the next bit.
     * 3. If both are equal, return false.
     * 4. Continue until the number becomes 0.
     * <p>
     * Line-by-Line Explanation:
     * <p>
     * int lastBit = n % 2;
     * Extract the rightmost bit.
     * <p>
     * n = n / 2;
     * Remove the last bit.
     * <p>
     * while (n > 0)
     * Continue checking remaining bits.
     * <p>
     * int currentBit = n % 2;
     * Extract next bit.
     * <p>
     * if (currentBit == lastBit)
     * If two adjacent bits are the same, return false.
     * <p>
     * lastBit = currentBit;
     * Update for next comparison.
     * <p>
     * n = n / 2;
     * Move to next bit.
     * <p>
     * If loop completes, return true.
     * <p>
     * Complexity:
     * Time: O(log n)
     * Space: O(1)
     * <p>
     * Interview Takeaway:
     * This is the intuitive bit-by-bit solution.
     * Always explain this first before introducing the optimized trick.
     */
    private static boolean hasAlternatingBitsBruteForce(int n) {
        // Take very first bit to start our comparison
        int lastBit = n % 2;
        n = n / 2;

        while (n > 0) {
            int currentBit = n % 2;

            // If the current bit is the same as the last one, it's not alternating
            if (currentBit == lastBit) {
                return false;
            }

            // Update lastBit and move to the next
            lastBit = currentBit;
            n = n / 2;
        }

        return true;
    }

    /**
     * Method: hasAlternatingBitsOptimized(int n)
     * <p>
     * What this method does:
     * Uses a bit manipulation trick to determine whether
     * the number has alternating bits.
     * <p>
     * Core Insight:
     * If a number has alternating bits,
     * then n XOR (n >> 1) produces a number consisting entirely of 1s.
     * <p>
     * Why This Works:
     * <p>
     * Example:
     * n = 5 → binary 101
     * <p>
     * n >> 1 = 010
     * <p>
     * XOR:
     * 101
     * 010
     * ---
     * 111
     * <p>
     * If bits are alternating,
     * the XOR result becomes a continuous block of 1s.
     * <p>
     * Now we check if a number is a block of 1s.
     * <p>
     * Property:
     * If x = 11111,
     * then x + 1 = 100000.
     * <p>
     * And:
     * 11111 & 100000 = 0
     * <p>
     * So if (x & (x + 1)) == 0,
     * then x is a block of consecutive 1s.
     * <p>
     * Line-by-Line Explanation:
     * <p>
     * int x = n ^ (n >> 1);
     * XOR with right-shifted version.
     * <p>
     * return (x & (x + 1)) == 0;
     * Checks whether x is a continuous sequence of 1s.
     * <p>
     * Complexity:
     * Time: O(1)
     * Space: O(1)
     * <p>
     * Interview Takeaway:
     * Alternating bits → XOR with shifted version → block of 1s.
     * Then use (x & (x + 1)) trick to verify.
     * Compact and very impressive in interviews.
     */
    private static boolean hasAlternatingBitsOptimized(int n) {
        /* If n is 10101, then (n >> 1) is 01010.
         * XORing them: 10101 ^ 01010 = 11111.
         * If the bits were alternating, x will now be a solid block of 1s.
         */
        int x = n ^ (n >> 1);

        /* If x is a solid block of 1s (like 11111),
         * then (x + 1) will be 100000.
         * A solid block of 1s ANDed with the next power of 2
         * MUST be zero: (11111 & 100000) == 0.
         */
        return (x & (x + 1)) == 0;
    }
}