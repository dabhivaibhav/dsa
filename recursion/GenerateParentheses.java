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
        List<String> result = new ArrayList<>();
        generateParenthesesRecursion("", 0, 0, 3, result);
        System.out.println(result);

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


    /**
     * generateParenthesesRecursion
     * <p>
     * What it does:
     * Generates all valid (well-formed) parentheses combinations of `n` pairs
     * using an optimized backtracking approach that *never generates invalid strings*.
     * <p>
     * Unlike the brute-force method, this approach applies constraints while
     * constructing the string, ensuring that only valid paths are explored.
     * <p>
     * Core Idea (Key Insight):
     * - A valid parentheses string must satisfy:
     *   1. Number of '(' ≤ n
     *   2. Number of ')' ≤ number of '(' at any time
     * - By enforcing these rules during recursion, we avoid unnecessary work.
     * <p>
     * Parameters Explanation:
     * - `curr`: Current partially constructed parentheses string.
     * - `open`: Number of '(' used so far.
     * - `close`: Number of ')' used so far.
     * - `n`: Total number of parentheses pairs required.
     * - `res`: List that stores all valid parentheses combinations.
     * <p>
     * How recursion works (step-by-step):
     * - At each step, we decide whether we can safely add '(' or ')'.
     * - '(' can be added if we have not used all `n` opening brackets.
     * - ')' can be added only if there are more unmatched '(' (i.e., `close < open`).
     * - The recursion continues until the string length becomes `2 * n`.
     * <p>
     * Why each condition matters:
     * - `curr.length() == 2 * n`:
     *   - Base case: a complete valid string is formed.
     * - `if (open < n)`:
     *   - Ensures we do not exceed the allowed number of opening brackets.
     * - `if (close < open)`:
     *   - Guarantees that we never close more brackets than we open,
     *     preserving validity at every step.
     * <p>
     * Example Walkthrough (n = 3):
     * - Start: curr="", open=0, close=0
     * - Add '(' → curr="(", open=1
     * - Add '(' → curr="((", open=2
     * - Add ')' → curr="(()", close=1
     * - Continue recursively until valid strings like:
     *   "((()))", "(()())", "(())()", "()(())", "()()()"
     * <p>
     * Why this approach is optimal:
     * - Invalid strings like ")(" or "())(" are never generated.
     * - The recursion tree is pruned early using constraints.
     * - This drastically reduces the number of recursive calls.
     * <p>
     * Time Complexity:
     * - O(Cₙ), where Cₙ is the nth Catalan number.
     * - Approximately O(4ⁿ / √n) in asymptotic form.
     * <p>
     * Space Complexity:
     * - O(n) recursion depth.
     * - O(Cₙ * n) for storing the output strings.
     * <p>
     * Edge Cases Handled:
     * - n = 0 → returns a list with an empty string.
     * - n = 1 → returns ["()"].
     * <p>
     * Interview Insight:
     * - This is the **expected optimal solution**.
     * - Shows understanding of:
     *   - Backtracking
     *   - Constraint-based recursion
     *   - Pruning invalid paths early
     * - Always preferred over brute-force in coding interviews.
     */
    private static void generateParenthesesRecursion(String curr, int open, int close, int n, List<String> res) {
        if (curr.length() == 2 * n) {
            res.add(curr);
            return;
        }
        if (open < n) generateParenthesesRecursion(curr + "(", open + 1, close, n, res);
        if (close < open) generateParenthesesRecursion(curr + ")", open, close + 1, n, res);
    }



}


