package bitmanipulation;

/*
Problem Statement: Given a positive integer n, set the rightmost unset (0) bit of its binary representation to 1 and
return the resulting integer. If all bits are already set, return the number as it is.
 */
public class SetUnsetRightmostBit {


    public static void main(String[] args) {

        int n = 10;
        System.out.println(setRightmostBit(n));
        n = 11;
        System.out.println(setRightmostBit(n));
        n = 13;
        System.out.println(setRightmostBit(n));
    }

    /**
     * setRightmostBit:
     * <p>
     * What it does:
     * Sets the rightmost unset (0) bit in the binary representation of a given
     * positive integer and returns the resulting number.
     * If all bits are already set, the number remains unchanged.
     * <p>
     * Why it works:
     * Adding 1 to a number flips all trailing 1s to 0 until it encounters
     * the first 0 bit, which becomes 1.
     * <p>
     * Performing a bitwise OR (|) between the original number and (n + 1)
     * guarantees that this rightmost unset bit is set to 1,
     * while all higher bits remain unchanged.
     * <p>
     * Important details:
     * - Works for positive integers.
     * - Uses a constant-time bitwise trick with no loops.
     * - If the number already has all bits set (e.g., 1111),
     * then n + 1 introduces a higher bit, and OR keeps the original value.
     * - Commonly used in bit manipulation problems involving masks or toggling.
     * <p>
     * Example:
     * n = 10
     * Binary of n     = 1010
     * Binary of n + 1 = 1011
     * <p>
     * OR operation:
     * 1010
     * |1011
     * ----
     * 1011  → result = 11
     * <p>
     * n = 11
     * Binary of n     = 1011
     * Binary of n + 1 = 1100
     * <p>
     * 1011
     * |1100
     * ----
     * 1111  → result = 15
     * <p>
     * n = 13
     * Binary of n     = 1101
     * Binary of n + 1 = 1110
     * <p>
     * 1101
     * |1110
     * ----
     * 1111  → result = 15
     * <p>
     * Complexity:
     * Time:  O(1)   (single bitwise operation)
     * Space: O(1)   (no extra memory used)
     */
    private static int setRightmostBit(int n) {
        return n | (n + 1);
    }
}
