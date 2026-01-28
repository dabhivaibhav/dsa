package bitmanipulation;

/*
Count the Number of Set Bits:
Given an integer n, return the number of set bits (1s) in its binary representation.
Can you solve it in O(log n) time complexity?

Example 1
Input: n = 5
Output: 2
Explanation: The binary representation of 5 is 101, which has 2 set bits.

Example 2
Input: n = 15
Output: 4
Explanation: The binary representation of 15 is 1111, which has 4 set bits.

Constraints:
            1 ≤ n ≤ 10⁹
 */
public class CountSetBits {

    public static void main(String[] args) {

        int n = 5;
        int n1 = 13;
        System.out.println("Brute force Solution: ");
        System.out.println("The count of set bit for " + n + " : " + countSetBitsBruteForce(n));
        System.out.println("The count of set bit for " + n1 + " : " + countSetBitsBruteForce(n1));
        System.out.println("Bitwise Solution: ");
        System.out.println("The count of set bit for " + n + " : " + countSetBitsOptimal(n));
        System.out.println("The count of set bit for " + n1 + " : " + countSetBitsOptimal(n1));
    }

    /**
     * countSetBitsBruteForce:
     * <p>
     * What it does:
     * Counts the number of set bits (1s) in the binary representation of a number
     * by explicitly converting the number into binary form and then scanning it.
     * <p>
     * Why it works:
     * Every integer has a unique binary representation.
     * By converting the number into binary and counting how many times '1' appears,
     * we directly determine the number of set bits.
     * <p>
     * Important details:
     * - The number is repeatedly divided by 2 to extract binary digits.
     * - Binary digits are initially generated in reverse order and then reversed.
     * - Each character of the binary string is inspected to count '1's.
     * - This approach is intuitive but not optimal for performance.
     * <p>
     * Example:
     * n = 13
     * Binary representation = 1101
     * Number of set bits = 3
     * <p>
     * Complexity:
     * Time:  O(log n)   (binary conversion + traversal)
     * Space: O(log n)   (stores binary representation)
     */
    private static int countSetBitsBruteForce(int n) {
        StringBuilder binaryString = new StringBuilder();
        while (n > 0) {
            binaryString.append(n % 2);
            n /= 2;
        }
        binaryString.reverse();
        // System.out.println(binaryString);
        int count = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '1') count++;
        }
        return count;
    }

    /**
     * countSetBitsOptimal:
     * <p>
     * What it does:
     * Counts the number of set bits (1s) in the binary representation of a number
     * using bitwise operations without converting the number to binary.
     * <p>
     * Why it works:
     * The expression (n & 1) isolates the least significant bit of the number.
     * By repeatedly checking this bit and shifting the number to the right,
     * every bit is examined exactly once.
     * <p>
     * Important details:
     * - Bitwise AND (&) is used to check whether the current bit is set.
     * - Right shift (>>) moves through the number bit by bit.
     * - Avoids string conversion and extra memory usage.
     * - This method is faster and preferred in DSA and interviews.
     * <p>
     * Example:
     * n = 13
     * <p>
     * Step-by-step:
     * 13 → 1101 → count += 1
     * 6  → 0110 → count += 0
     * 3  → 0011 → count += 1
     * 1  → 0001 → count += 1
     * <p>
     * Total set bits = 3
     * <p>
     * Complexity:
     * Time:  O(log n)   (one operation per bit)
     * Space: O(1)       (constant extra space)
     */
    private static int countSetBitsOptimal(int n) {
        int count = 0;
        while (n > 0) {
            count += n & 1;
            n >>= 1;
        }
        return count;
    }

    /**
     * countSetBitsKernighan:
     * <p>
     * Counts set bits using Brian Kernighan’s Algorithm.
     * <p>
     * Idea:
     * Each operation n & (n - 1) removes the lowest set bit.
     * The loop runs exactly as many times as there are 1s.
     * <p>
     * Example:
     * n = 13 (1101)
     * 1101 & 1100 → 1100
     * 1100 & 1011 → 1000
     * 1000 & 0111 → 0000
     * Count = 3
     * <p>
     * Complexity:
     * Time:  O(k) where k = number of set bits
     * Space: O(1)
     */
    private static int countSetBitsKernighan(int n) {
        int count = 0;
        while (n > 0) {
            n = n & (n - 1); // removes lowest set bit
            count++;
        }
        return count;
    }

    /**
     * countSetBitsBuiltIn:
     * <p>
     * Counts set bits using Java’s built-in method.
     * <p>
     * Why it works:
     * The JVM uses highly optimized bit-count logic,
     * often mapped to a CPU POPCOUNT instruction.
     * <p>
     * Complexity:
     * Time:  O(1) in practice
     * Space: O(1)
     */
    private static int countSetBitsBuiltIn(int n) {
        return Integer.bitCount(n);
    }
}
