package string.medium_problems;

/*
Leetcode: 8. String to Integer (atoi)
Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.

The algorithm for myAtoi(string s) is as follows:
Whitespace: Ignore any leading whitespace (" ").
Signedness: Determine the sign by checking if the next character is '-' or '+', assuming positivity if neither present.
Conversion: Read the integer by skipping leading zeros until a non-digit character is encountered or the end of the
string is reached. If no digits were read, then the result is 0.
Rounding: If the integer is out of the 32-bit signed integer range [-2^31, 2^31 - 1], then round the integer to remain in
the range. Specifically, integers less than -2^31 should be rounded to -2^31, and integers greater than 2^31 - 1 should be
rounded to 2^31 - 1.
Return the integer as the final result.

Example 1:
Input: s = "42"
Output: 42
Explanation: The underlined characters are what is read in and the caret is the current reader position.
Step 1: "42" (no characters read because there is no leading whitespace)
         ^
Step 2: "42" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "42" ("42" is read in)
           ^

Example 2:
Input: s = " -042"
Output: -42
Explanation:
Step 1: "   -042" (leading whitespace is read and ignored)
            ^
Step 2: "   -042" ('-' is read, so the result should be negative)
             ^
Step 3: "   -042" ("042" is read in, leading zeros ignored in the result)
               ^

Example 3:
Input: s = "1337c0d3"
Output: 1337
Explanation:
Step 1: "1337c0d3" (no characters read because there is no leading whitespace)
         ^
Step 2: "1337c0d3" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "1337c0d3" ("1337" is read in; reading stops because the next character is a non-digit)
             ^

Example 4:
Input: s = "0-1"
Output: 0
Explanation:
Step 1: "0-1" (no characters read because there is no leading whitespace)
         ^
Step 2: "0-1" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "0-1" ("0" is read in; reading stops because the next character is a non-digit)
          ^

Example 5:
Input: s = "words and 987"
Output: 0
Explanation: Reading stops at the first non-digit character 'w'.

Constraints:
            0 <= s.length <= 200
            s consists of English letters (lower-case and upper-case), digits (0-9), ' ', '+', '-', and '.'.
 */
public class StringtoIntegerATOI {
    public static void main(String[] args) {
        String s = "42";
        String s1 = "   -42";
        String s2 = "4193 with words";
        String s3 = "words and 987";
        String s4 = "-91283472332";

        System.out.println(myAtoi(s));  // Output: 42
        System.out.println(myAtoi(s1)); // Output: -42
        System.out.println(myAtoi(s2)); // Output: 4193
        System.out.println(myAtoi(s3)); // Output: 0
        System.out.println(myAtoi(s4)); // Output: -2147483648 (clamped to INT_MIN)
    }

    /**
     * What it does:
     * Converts a given string into a 32-bit signed integer, following the exact behavior of the C/C++ `atoi()` function.
     * Handles leading spaces, optional '+' or '-' signs, valid digits, and automatically clamps out-of-range values
     * to stay within the 32-bit integer range.
     *
     * <p>
     * Why it works:
     * - The algorithm systematically simulates how integers are parsed character by character.
     * - It first trims leading whitespace, identifies the sign, and then processes only valid digits in sequence.
     * - Once a non-digit character appears, it stops reading further.
     * - During digit accumulation, overflow checks ensure that values exceeding `Integer.MAX_VALUE` (2³¹−1) or below
     *   `Integer.MIN_VALUE` (−2³¹) are safely clamped to those limits.
     *
     * <p>
     * How it works step-by-step:
     * 1. Skip all leading whitespace using a loop.
     * 2. Determine if the next character is '+' or '−', and record the sign accordingly.
     * 3. Initialize an integer accumulator `result = 0`.
     * 4. For each remaining character:
     *    - If it’s not a digit, stop processing.
     *    - Convert the digit character into its numeric value.
     *    - Before multiplying by 10 and adding the new digit, check for overflow or underflow using boundary logic:
     *      `if (result > INT_MAX/10 || (result == INT_MAX/10 && digit > 7))`
     *      If overflow is detected, return the clamped boundary value immediately.
     * 5. Multiply the final `result` by the sign and return it.
     *
     * <p>
     * Example walkthrough:
     * <pre>
     * Input:  "   -042"
     *  → Ignore leading spaces → "-042"
     *  → Sign = -1
     *  → Process digits: "042" → 42
     *  → Apply sign: -42
     *  Output: -42
     *
     * Input: "4193 with words"
     *  → Process digits until non-digit ' ' or 'w'
     *  → Output: 4193
     *
     * Input: "-91283472332"
     *  → Exceeds -2³¹, return clamped value: -2147483648
     * </pre>
     *
     * <p>
     * Edge cases:
     * - Input: `"words and 987"` → returns 0 (no valid digits at start)
     * - Input: `"2147483648"` → returns 2147483647 (clamped to INT_MAX)
     * - Input: `"-2147483649"` → returns -2147483648 (clamped to INT_MIN)
     * - Input: `"   +0 123"` → returns 0 (stops reading after '0')
     *
     * <p>
     * Time Complexity: O(n) — each character is processed at most once.
     * Space Complexity: O(1) — only a few integer variables are used.
     *
     * <p>
     * Output:
     * Returns the integer representation of the input string within the 32-bit range.
     */

    public static int myAtoi(String s) {
        if (s == null || s.isEmpty()) return 0;

        int i = 0, n = s.length();

        // 1) Skip leading spaces
        while (i < n && s.charAt(i) == ' ') i++;

        // If only spaces
        if (i == n) return 0;

        // 2) Optional sign
        int sign = 1;
        char c = s.charAt(i);
        if (c == '+' || c == '-') {
            sign = (c == '-') ? -1 : 1;
            i++;
        }

        // 3) Read digits, stop at first non-digit; clamp on overflow
        int result = 0;
        final int INT_MAX = Integer.MAX_VALUE;
        final int INT_MIN = Integer.MIN_VALUE;

        while (i < n) {
            char ch = s.charAt(i);
            if (ch < '0' || ch > '9') break;
            int digit = ch - '0';

            // Overflow check before multiplying by 10 and adding digit
            if (result > INT_MAX / 10 || (result == INT_MAX / 10 && digit > INT_MAX % 10)) {
                return (sign == 1) ? INT_MAX : INT_MIN;
            }

            result = result * 10 + digit;
            i++;
        }

        // 4) Apply sign
        result *= sign;
        return result;
    }
}
