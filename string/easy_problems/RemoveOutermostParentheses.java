package string.easy_problems;

/*
Leetcode: 1021. Remove Outermost Parentheses

A valid parentheses string is either empty "", "(" + A + ")", or A + B, where A and B are valid parentheses strings, and
+ represents string concatenation. For example, "", "()", "(())()", and "(()(()))" are all valid parentheses strings.
A valid parentheses string s is primitive if it is nonempty, and there does not exist a way to split it into s = A + B,
with A and B nonempty valid parentheses strings. Given a valid parentheses string s,
consider its primitive decomposition: s = P1 + P2 + ... + Pk, where Pi are primitive valid parentheses strings.
Return s after removing the outermost parentheses of every primitive string in the primitive decomposition of s.

Example 1:
Input: s = "(()())(())"
Output: "()()()"
Explanation:
The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
After removing outer parentheses of each part, this is "()()" + "()" = "()()()".

Example 2:
Input: s = "(()())(())(()(()))"
Output: "()()()()(())"
Explanation:
The input string is "(()())(())(()(()))", with primitive decomposition "(()())" + "(())" + "(()(()))".
After removing outer parentheses of each part, this is "()()" + "()" + "()(())" = "()()()()(())".

Example 3:
Input: s = "()()"
Output: ""
Explanation:
The input string is "()()", with primitive decomposition "()" + "()".
After removing outer parentheses of each part, this is "" + "" = "".

Constraints:
            1 <= s.length <= 10^5
            s[i] is either '(' or ')'.
            s is a valid parentheses string.
 */
public class RemoveOutermostParentheses {

    public static void main(String[] args) {
        String s = "(()())(())";
        String s1 = "(()())(())(()(()))";
        String s2 = "()()";

        System.out.println(removeOuterParentheses(s));
        System.out.println(removeOuterParentheses(s1));
        System.out.println(removeOuterParentheses(s2));
    }

    /**
     * What it does:
     * Removes the outermost parentheses from every primitive substring in a valid
     * parentheses string by streaming through the string character by character
     * and selectively appending only non-outer parentheses.
     *
     * <p>
     * Why it works:
     * - A valid parentheses string can be decomposed into one or more "primitive"
     * parts, where each primitive is a smallest valid substring that cannot be
     * split further into valid parts.
     * - The outermost parentheses of a primitive are precisely the ones that
     * cause the depth to go from 0 → 1 (for '(') or from 1 → 0 (for ')').
     * - By keeping track of the current nesting depth:
     * - For an opening '(':
     * - If depth > 0 before adding it, it is not outermost → append.
     * - Increase depth.
     * - For a closing ')':
     * - Decrease depth.
     * - If depth > 0 after decrement, it is not outermost → append.
     * - This ensures that only the outer layer of each primitive is skipped.
     *
     * <p>
     * How it works:
     * - Initialize:
     * - A StringBuilder `sb` with initial capacity equal to input length.
     * - An integer `depth` = 0.
     * - Loop over each character `c` in the input string:
     * 1. If `c` is '(':
     * - If depth > 0, append it.
     * - Increment depth.
     * 2. If `c` is ')':
     * - Decrement depth.
     * - If depth > 0, append it.
     * - Return the contents of `sb` as the final result.
     *
     * <p>
     * Example:
     * Input: "(()())(())"
     * - Read '(' depth=0 → increment to 1, skip (outer).
     * - Read '(' depth=1 → append, depth=2.
     * - Read ')' depth=2 → decrement to 1, append.
     * - Read ')' depth=1 → decrement to 0, skip (outer).
     * → First primitive reduced to "()()".
     * - Next primitive "(())" similarly reduces to "()".
     * - Result = "()()()".
     * <p>
     * Input: "()()"
     * - First "()": both '(' at depth=0 and ')' at depth=1 → skipped.
     * - Second "()": same logic → skipped.
     * - Result = "" (empty string).
     *
     * <p>
     * Time Complexity:
     * - O(n): Single pass over the string, each character processed in O(1).
     * <p>
     * Space Complexity:
     * - O(n): StringBuilder stores the reduced string.
     *
     * <p>
     * Output:
     * Returns a string with all outermost parentheses removed from each
     * primitive substring, preserving the concatenation of their inner contents.
     */
    private static String removeOuterParentheses(String s) {
        if (s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder(s.length());
        int depth = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                if (depth > 0) sb.append('('); // not an outer '('
                depth++;
            } else {
                depth--;
                if (depth > 0) sb.append(')'); // not an outer ')'
            }
        }
        return sb.toString();
    }

}
