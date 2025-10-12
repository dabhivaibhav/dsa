package string.medium_problems;

/*
Leetcode: 1614. Maximum Nesting Depth of the Parentheses

Given a valid parentheses string s, return the nesting depth of s. The nesting depth is the maximum number of nested parentheses.

Example 1:
Input: s = "(1+(2*3)+((8)/4))+1"
Output: 3
Explanation: Digit 8 is inside of 3 nested parentheses in the string.

Example 2:
Input: s = "(1)+((2))+(((3)))"
Output: 3
Explanation: Digit 3 is inside of 3 nested parentheses in the string.

Example 3:
Input: s = "()(())((()()))"
Output: 3

Constraints:
            1 <= s.length <= 100
            s consists of digits 0-9 and characters '+', '-', '*', '/', '(', and ')'.
            It is guaranteed that parentheses expression s is a VPS.
 */
public class MaximumNestingDepth {

    public static void main(String[] args) {
        String s = "(1+(2*3)+((8)/4))+1";
        String s1 = "(1)+((2))+(((3)))";
        String s2 = "()(())((()()))";

        System.out.println(maxDepth(s));  // Output: 3
        System.out.println(maxDepth(s1)); // Output: 3
        System.out.println(maxDepth(s2)); // Output: 3
    }


    /**
     * What it does:
     * Determines the **maximum nesting depth** of valid parentheses in a given string `s`.
     * A nesting depth represents how deeply parentheses are nested — for example,
     * `"((()))"` has a maximum depth of 3.
     *
     * <p>
     * Why it works:
     * - Every `'('` increases the current nesting level, and every `')'` decreases it.
     * - The maximum value of the nesting counter at any point is the **maximum depth**.
     * - If `depth` ever becomes negative, it means there’s an extra closing parenthesis → invalid input.
     * - After traversing, if `depth != 0`, there are unmatched `'('` → invalid input as well.
     *
     * <p>
     * How it works:
     * 1. Initialize two counters:
     *    - `depth` = current open parentheses count.
     *    - `max` = maximum depth observed so far.
     * 2. Traverse the string one character at a time:
     *    - If `'('`, increment `depth` and update `max = Math.max(max, depth)`.
     *    - If `')'`, decrement `depth`.
     *    - If `depth < 0`, return `0` immediately (too many closing brackets).
     * 3. After the loop, check if `depth == 0`:
     *    - If true → all parentheses are balanced; return `max`.
     *    - If false → some opening brackets are unmatched; return `0`.
     *
     * <p>
     * Example:
     * Input: `"((1+(2*3))+((8)/4))+1"`
     * - Depth changes: 1 → 2 → 1 → 2 → 3 → 2 → 1 → 0
     * - Maximum depth = 3
     * Output: `3`
     *
     * <p>
     * Edge Cases:
     * - Input = `""` → returns 0
     * - Input = `"((("` → returns 0 (unbalanced)
     * - Input = `"())"` → returns 0 (extra closing bracket)
     * - Input = `"(()(()))"` → returns 3
     *
     * <p>
     * Time Complexity:
     * - O(n): Each character is processed once.
     *
     * Space Complexity:
     * - O(1): Uses only two integer variables.
     *
     * <p>
     * Output:
     * - Returns the maximum valid nesting depth of parentheses, or `0` if invalid.
     */

    private static int maxDepth(String s) {
        int depth = 0, max = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                depth++;
                if (depth > max) max = depth;
            } else if (c == ')') {
                depth--;
                if (depth < 0) return 0; // extra closing
            }
        }
        return (depth == 0) ? max : 0; // extra opening → invalid
    }
}
