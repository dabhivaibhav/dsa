package string.easy_problems;

/*
Leetcode 1758: Minimum Changes to Make Alternating Binary String

You are given a string s consisting only of the characters '0' and '1'.
In one operation, you can change any '0' to '1' or vice versa.
The string is called alternating if no two adjacent characters are equal.
For example, the string "010" is alternating, while the string "0100" is not.
Return the minimum number of operations needed to make s alternating.

Example 1:
Input: s = "0100"
Output: 1
Explanation: If you change the last character to '1', s will be "0101", which is alternating.

Example 2:
Input: s = "10"
Output: 0
Explanation: s is already alternating.

Example 3:
Input: s = "1111"
Output: 2
Explanation: You need two operations to reach "0101" or "1010".

Constraints:
            1 <= s.length <= 10^4
            s[i] is either '0' or '1'.
 */
public class MinimumChangesToMakeAlternatingBinaryString {
    public static void main(String[] args) {
        String s = "0100";
        System.out.println(minOperations(s));
        String s1 = "10";
        System.out.println(minOperations(s1));
        String s2 = "1111";
        System.out.println(minOperations(s2));
        String s4 = "110010";
        System.out.println(minOperations(s4));

    }

    /*
    THE "TEMPLATE MATCHING" STRATEGY

    THE PROBLEM:
    Don't get bogged down in 'fixing' the string step-by-step.

    THE REVELATION:
    There are only TWO valid answers.
    1. 0101...
    2. 1010...

    If I know how many changes it takes to get to Pattern A,
    I automatically know how many it takes for Pattern B.
    It's just the 'leftover' characters.

    TIME COMPLEXITY: O(N) - One pass.
    SPACE COMPLEXITY: O(1) - No extra arrays or recursion.
    */

    /**
     * minOperations(String s)
     * <p>
     * What this method does:
     * <p>
     * Returns the minimum number of character changes
     * required to convert the binary string into
     * an alternating string.
     * <p>
     * An alternating string means:
     * <p>
     * No two adjacent characters are equal.
     * <p>
     * Valid examples:
     * 0101
     * 1010
     * <p>
     * Core Idea:
     * <p>
     * Any alternating binary string must follow
     * one of only two possible patterns:
     * <p>
     * Pattern A → 010101...
     * Pattern B → 101010...
     * <p>
     * Instead of trying many modifications,
     * we simply check how many positions
     * do NOT match Pattern A.
     * <p>
     * The number of changes required for Pattern B
     * will automatically be the remaining characters.
     * <p>
     * Thought Process:
     * <p>
     * Suppose the string length is n.
     * <p>
     * If "count" positions mismatch Pattern A,
     * then:
     * <p>
     * Pattern A requires → count changes
     * Pattern B requires → n - count changes
     * <p>
     * We return the minimum of the two.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * Initialize a mismatch counter.
     * <p>
     * Step 2:
     * Traverse the string character by character.
     * <p>
     * Step 3:
     * For each index i:
     * <p>
     * Determine the expected character
     * for Pattern A.
     * <p>
     * If i is even → expected = '0'
     * If i is odd  → expected = '1'
     * <p>
     * Step 4:
     * If the current character does not match
     * the expected character,
     * increment the mismatch counter.
     * <p>
     * Step 5:
     * After scanning the string:
     * <p>
     * count → operations needed for Pattern A
     * n-count → operations needed for Pattern B
     * <p>
     * Step 6:
     * Return the minimum of the two values.
     * <p>
     * Example 1:
     * <p>
     * s = "0100"
     * <p>
     * Pattern A → 0101
     * ↑ mismatch
     * <p>
     * Only 1 change required.
     * <p>
     * Output → 1
     * <p>
     * Example 2:
     * <p>
     * s = "10"
     * <p>
     * Already alternating.
     * <p>
     * Output → 0
     * <p>
     * Example 3:
     * <p>
     * s = "1111"
     * <p>
     * Pattern A → 0101
     * mismatches = 2
     * <p>
     * Pattern B → 1010
     * mismatches = 2
     * <p>
     * Minimum operations → 2
     * <p>
     * Complexity:
     * <p>
     * Let n = length of the string.
     * <p>
     * Time Complexity:
     * <p>
     * O(n)
     * <p>
     * We scan the string exactly once.
     * <p>
     * Space Complexity:
     * <p>
     * O(1)
     * <p>
     * Only a few variables are used.
     * <p>
     * Interview Takeaway:
     * <p>
     * When a problem asks for an alternating
     * binary pattern, remember that only
     * TWO valid patterns exist.
     * <p>
     * Instead of constructing the string,
     * compare against the templates
     * and count mismatches.
     * <p>
     * This transforms a seemingly complex
     * transformation problem into
     * a simple counting problem.
     */
    private static int minOperations(String s) {
        int count = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char expected = (i % 2 == 0) ? '0' : '1';

            if (s.charAt(i) != expected) {
                count++;
            }
        }
        return Math.min(count, n - count);
    }
}
