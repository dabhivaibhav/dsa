package bitmanipulation;

/*
Leetcode: 2220. Minimum Bit Flips to Convert Number

A bit flip of a number x is choosing a bit in the binary representation of x and flipping it from either 0 to 1 or 1 to 0.

For example, for x = 7, the binary representation is 111 and we may choose any bit (including any leading zeros not shown)
and flip it. We can flip the first bit from the right to get 110, flip the second bit from the right to get 101, flip the
fifth bit from the right (a leading zero) to get 10111, etc. Given two integers start and goal, return the minimum number
of bit flips to convert start to goal.

Example 1:
Input: start = 10, goal = 7
Output: 3
Explanation: The binary representation of 10 and 7 are 1010 and 0111 respectively. We can convert 10 to 7 in 3 steps:
- Flip the first bit from the right: 1010 -> 1011.
- Flip the third bit from the right: 1011 -> 1111.
- Flip the fourth bit from the right: 1111 -> 0111.
It can be shown we cannot convert 10 to 7 in less than 3 steps. Hence, we return 3.

Example 2:
Input: start = 3, goal = 4
Output: 3
Explanation: The binary representation of 3 and 4 are 011 and 100 respectively. We can convert 3 to 4 in 3 steps:
- Flip the first bit from the right: 011 -> 010.
- Flip the second bit from the right: 010 -> 000.
- Flip the third bit from the right: 000 -> 100.
It can be shown we cannot convert 3 to 4 in less than 3 steps. Hence, we return 3.


Constraints:
            0 <= start, goal <= 10^9
 */
public class MinimumBitFlipsToConvertNumber {

    public static void main(String[] args) {
        System.out.println(minFlips(10, 7));
        System.out.println(minFlips(3, 4));
        System.out.println(minFlips(1, 2));
    }

    /**
     * What it does:
     * Computes the minimum number of bit flips required to convert one integer
     * (start) into another integer (goal).
     * <p>
     * Why it works:
     * A bit flip is required exactly at those bit positions where the binary
     * representations of start and goal differ.
     * <p>
     * Using bitwise XOR (^) highlights these differences:
     * - Same bits   → XOR produces 0 (no flip needed)
     * - Different bits → XOR produces 1 (flip needed)
     * <p>
     * Therefore, start ^ goal produces a number whose set bits (1s)
     * represent exactly the positions where flips are required.
     * Counting the number of set bits in this XOR result gives the
     * minimum number of flips needed.
     * <p>
     * Important details:
     * - XOR is used to compare bits position-by-position.
     * - The algorithm does not simulate flips; it only counts them.
     * - Iterates over all 32 bits of an integer to ensure full coverage.
     * - Works for all non-negative integers within the constraints.
     * <p>
     * Core logic – understanding the for loop (the heart of the solution):
     * The loop iterates through all 32 bit positions of an integer.
     * <p>
     * In each iteration:
     * 1. The expression (ans & 1) checks the least significant bit (LSB):
     * - If the LSB is 1, this means start and goal differed at this bit position,
     * so one flip is required.
     * - If the LSB is 0, no flip is needed at this position.
     * <p>
     * 2. The result of (ans & 1) is added to the count.
     * <p>
     * 3. The value of ans is then right-shifted by one position (ans >> 1),
     * discarding the current LSB and bringing the next bit into position.
     * <p>
     * This process repeats, effectively scanning the XOR result bit by bit
     * from right to left until all 32 bits are examined.
     * <p>
     * Example walkthrough:
     * start = 10, goal = 7
     * <p>
     * Binary:
     * start = 1010
     * goal  = 0111
     * <p>
     * XOR:
     * ans = 1101
     * <p>
     * Iterations:
     * - Iteration 1: ans = 1101 → LSB = 1 → count = 1 → shift → 110
     * - Iteration 2: ans = 110  → LSB = 0 → count = 1 → shift → 11
     * - Iteration 3: ans = 11   → LSB = 1 → count = 2 → shift → 1
     * - Iteration 4: ans = 1    → LSB = 1 → count = 3 → shift → 0
     * <p>
     * Remaining iterations contribute 0 as ans is now 0.
     * <p>
     * Final count = 3 → minimum flips required.
     * <p>
     * Complexity:
     * Time:  O(32) ≈ O(1)   (fixed number of bit checks)
     * Space: O(1)           (constant extra space)
     */

    private static int minFlips(int start, int goal) {
        int ans = start ^ goal;
        int count = 0;
        for (int i = 0; i < 32; i++) {
            count += ans & 1;
            ans >>= 1;
        }
        return count;
    }
}
