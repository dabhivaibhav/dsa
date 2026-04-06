package greedy.medium_problems;

/*
Leetcode 678. Valid parenthesis Checker

Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
The following rules define a valid string:
Any left parenthesis '(' must have a corresponding right parenthesis ')'.
Any right parenthesis ')' must have a corresponding left parenthesis '('.
Left parenthesis '(' must go before the corresponding right parenthesis ')'.
'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".

Example 1:
Input: s = "()"
Output: true

Example 2:
Input: s = "(*)"
Output: true

Example 3:
Input: s = "(*))"
Output: true

Constraints:
            1 <= s.length <= 100
            s[i] is '(', ')' or '*'.

 */
public class ValidParenthesisChecker {
    public static void main(String[] args) {

        String str = "()";
        System.out.println(checkValidString(str));

    }

    /**
     * THE "FLEXIBLE RANGE" PATTERN:
     *
     * What this method does:
     *
     * Validates a parentheses string containing '(', ')' and '*'.
     * The asterisk '*' is a "Wildcard" that can represent an open bracket,
     * a close bracket, or an empty string.
     *
     * REAL-LIFE ANALOGY: The "Budget Estimate"
     * Imagine you are planning a trip. You have some fixed costs (brackets) and
     * some uncertain costs (asterisks). Instead of guessing exactly how much you
     * will spend, you track a Minimum Budget (assuming everything is cheap)
     * and a Maximum Budget (assuming everything is expensive).
     * As long as your "Minimum Budget" can hit exactly zero at the end, and your
     * "Maximum Budget" never goes into debt (negative), the trip is possible.
     *
     * ALGORITHM EXPLAINED: Range Tracking
     * <Instead of a single Stack, we maintain a Range of possible open brackets:
     *
     * minOpen: The fewest '(' we could have (treating '*' as ')').
     * maxOpen: The most '(' we could have (treating '*' as '(').
     *
     * STEP-BY-STEP LOGIC:
     *
     * '(':Both minOpen and maxOpen increase.
     * ')': Both minOpen and maxOpen decrease.
     * '*': minOpen decreases (acts as ')') and maxOpen increases (acts as '(').
     *
     *
     * CRITICAL GUARDS:
     * If maxOpen < 0: Even in the best-case scenario, we have too many
     * closing brackets. Return false.
     * If minOpen < 0}: We reset it to 0. We can't have "negative"
     * open brackets; it just means we chose to treat some '*' as empty strings instead of ')'.
     *
     * COMPLEXITY:
     * Time: O(n) - Single pass through the string.
     * Space: O(1) - Only two integer variables used.
     */
    private static boolean checkValidString(String s) {
        // Variable to track minimum possible open brackets at current index
        int minOpen = 0;

        // Variable to track maximum possible open brackets at current index
        int maxOpen = 0;

        // Traverse through each character in the string
        for (int i = 0; i < s.length(); i++) {

            // Get current character
            char c = s.charAt(i);

            // If character is '(', it increases both minOpen and maxOpen
            if (c == '(') {
                minOpen++;
                maxOpen++;
            }

            // If character is ')', it decreases both minOpen and maxOpen
            else if (c == ')') {
                minOpen--;
                maxOpen--;
            }

            // If character is '*', it can be '(', ')' or ''
            else {
                // if '*' is ')'
                minOpen--;
                // if '*' is '('
                maxOpen++;
            }

            // If maxOpen becomes negative, too many closing brackets : invalid string
            if (maxOpen < 0) return false;

            // minOpen can't go below 0, as we can't have negative unmatched '('
            if (minOpen < 0) minOpen = 0;
        }

        // If minOpen is 0 at the end, it's a valid configuration
        return minOpen == 0;
    }

}
