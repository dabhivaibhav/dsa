package recursion;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 22: Generate Parentheses
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

Example 1:
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]

Example 2:
Input: n = 1
Output: ["()"]

Constraints:
            1 <= n <= 8
 */
public class GenerateParentheses {

    public static void main(String[] args) {
        System.out.println(generateParentheses(3));


    }

    public static List<String> generateParentheses(int n) {
        List<String> result = new ArrayList<>();
        int len = 2 * n;
        StringBuilder sb = new StringBuilder();
        generateParenthesesBruteForce(len, 0, sb, result, n);
        return result;
    }

    /**
     * generateParenthesesBruteForce
     * <p>
     * What it does:
     * Recursively generates *all possible strings* of parentheses of length `len`
     * by placing either '(' or ')' at each position, and collects only those
     * strings that form valid parentheses expressions.
     * <p>
     * This method does NOT try to build only valid parentheses.
     * Instead, it:
     * - Generates every possible combination (brute force)
     * - Validates each complete string using `isValidParentheses`
     * <p>
     * Core idea:
     * - For every position in the string, there are two choices: '(' or ')'
     * - This creates a full binary recursion tree of depth `len`
     * - Once a string reaches length `len`, it is checked for validity
     * <p>
     * How recursion works (step-by-step):
     * - `pos` represents the current position being filled.
     * - At each recursive call:
     *   1. Append '(' and recurse to the next position.
     *   2. Backtrack (remove last character).
     *   3. Append ')' and recurse to the next position.
     *   4. Backtrack again.
     * - This ensures *all* combinations are explored.
     * <p>
     * Why backtracking is required:
     * - `StringBuilder` is reused to avoid creating new string objects.
     * - `deleteCharAt` restores the previous state before trying the next choice.
     * - Without backtracking, strings would accumulate incorrect characters.
     * <p>
     * Why each line matters:
     * - `if (pos == len)`: Base case — a full candidate string is formed.
     * - `sb.toString()`: Converts the built sequence into an immutable string.
     * - `isValidParentheses(...)`: Filters invalid sequences.
     * - `sb.append(...)`: Chooses a character for the current position.
     * - `sb.deleteCharAt(...)`: Undoes the choice (backtracking).
     * <p>
     * Example Walkthrough (n = 2, len = 4):
     * - Generated strings:
     *   "((((", "((()", "()()", "(())", "())(", "))()", "))))", ...
     * - After validation:
     *   "(())", "()()" are kept.
     * <p>
     * Time Complexity:
     * - O(2^len * len)
     *   - `2^len` total strings generated
     *   - Each validation takes O(len)
     * <p>
     * Space Complexity:
     * - O(len) recursion stack depth
     * - O(len) StringBuilder size
     * - Output space depends on number of valid combinations
     * <p>
     * Limitations:
     * - Extremely inefficient for larger `n`
     * - Generates many invalid strings unnecessarily
     * - Used mainly for learning and comparison with optimized backtracking
     */
    private static void generateParenthesesBruteForce(int len, int pos, StringBuilder sb, List<String> result, int n) {
        if (pos == len) {
            String candidate = sb.toString();
            if (isValidParentheses(candidate, n)) {
                result.add(candidate);
            }
            return;
        }

        sb.append('(');
        generateParenthesesBruteForce(len, pos + 1, sb, result, n);
        sb.deleteCharAt(sb.length() - 1);

        sb.append(')');
        generateParenthesesBruteForce(len, pos + 1, sb, result, n);
        sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * isValidParentheses
     * <p>
     * What it does:
     * Checks whether a given parentheses string is *balanced* and *well-formed*.
     * <p>
     * Validation Rules:
     * - Every '(' must have a matching ')'
     * - At no point should ')' exceed '(' while scanning left to right
     * - Total number of '(' must equal total number of ')'
     * <p>
     * Algorithm Explanation:
     * - Use a `balance` counter:
     *   - Increment for '('
     *   - Decrement for ')'
     * - If balance becomes negative at any point:
     *   - There are more closing brackets than opening ones → invalid
     * - After processing all characters:
     *   - Balance must be exactly zero
     * <p>
     * Why balance < 0 check is important:
     * - It catches cases like ")(" or "())(" early
     * - Ensures parentheses close in the correct order
     * <p>
     * Example:
     * - "()()" → valid (balance never negative, ends at 0)
     * - "(())" → valid
     * - "())(" → invalid (balance becomes negative)
     * - "((()" → invalid (balance ≠ 0 at end)
     * <p>
     * Time Complexity:
     * - O(len), where len is the length of the string
     * <p>
     * Space Complexity:
     * - O(1), uses only a counter
     */

    private static boolean isValidParentheses(String s, int n) {
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') balance++;
            else balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }




}


