package string.medium_problems;

import java.util.HashMap;

/*
Leetcode: 13. Roman to Integer

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, 2 is written as II in Roman numeral, just two ones added together. 12 is written as XII,
which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII.
Instead, the number four is written as IV. Because the one is before the five we subtract it making four.
The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9.
X can be placed before L (50) and C (100) to make 40 and 90.
C can be placed before D (500) and M (1000) to make 400 and 900.
Given a roman numeral, convert it to an integer.

Example 1:
Input: s = "III"
Output: 3
Explanation: III = 3.

Example 2:
Input: s = "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.

Example 3:
Input: s = "MCMXCIV"
Output: 1994
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.

Constraints:
            1 <= s.length <= 15
            s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
            It is guaranteed that s is a valid roman numeral in the range [1, 3999].
 */
public class RomanToInteger {

    public static void main(String[] args) {
        String s = "III";
        String s1 = "LVIII";
        String s2 = "MCMXCIV";

        System.out.println(romanToInteger(s));
        System.out.println(romanToInteger(s1));
        System.out.println(romanToInteger(s2));
    }

    /**
     * What it does:
     * Converts a valid **Roman numeral** string into its corresponding **integer** value.
     * Handles both additive and subtractive Roman numeral rules efficiently in a single pass.
     *
     * <p>
     * Why it works:
     * - Roman numerals use both **additive** and **subtractive** notation.
     * - Normally, values are added left to right (e.g., "VIII" = 5 + 3 = 8).
     * - But when a smaller numeral appears before a larger one, it’s **subtracted**
     *   (e.g., "IV" = 5 - 1 = 4).
     * - This algorithm leverages that rule by scanning each character and deciding
     *   whether to **add** or **subtract** it based on the value of the next symbol.
     *
     * <p>
     * How it works:
     * 1. Create a `HashMap<Character, Integer>` mapping each Roman symbol to its numeric value:
     *    I=1, V=5, X=10, L=50, C=100, D=500, M=1000.
     * 2. Initialize `sum = 0`.
     * 3. Loop through the string from index `0` to `length - 2`:
     *    - If the **current symbol’s value < next symbol’s value**, subtract it from `sum`.
     *      (e.g., `I` before `V` → subtract 1).
     *    - Otherwise, add it to `sum`.
     * 4. After the loop, add the value of the **last character**, since it has no next neighbor to compare.
     * 5. Return the computed `sum`.
     *
     * <p>
     * Example Walkthrough:
     * Input: `"MCMXCIV"`
     * Steps:
     * - M (1000) < C (100)? No → add 1000 → sum = 1000
     * - C (100) < M (1000)? Yes → subtract 100 → sum = 900
     * - M (1000) < X (10)? No → add 1000 → sum = 1900
     * - X (10) < C (100)? Yes → subtract 10 → sum = 1890
     * - C (100) < I (1)? No → add 100 → sum = 1990
     * - I (1) < V (5)? Yes → subtract 1 → sum = 1989
     * Add last V (5) → sum = 1994
     * Output: **1994**
     *
     * <p>
     * Edge Cases:
     * - Input: `"III"` → output: 3 (all additive).
     * - Input: `"IX"` → output: 9 (subtractive case).
     * - Input: `"LVIII"` → output: 58 (mixed sequence).
     * - Input: `"MCMXCIV"` → output: 1994 (contains multiple subtractive pairs).
     *
     * <p>
     * Time Complexity:
     * - O(n): Single traversal of the string.
     *
     * Space Complexity:
     * - O(1): Fixed-size map with only 7 Roman symbols.
     *
     * <p>
     * Output:
     * Returns the integer value equivalent of the Roman numeral.
     */

    private static int romanToInteger(String s) {
        int sum = 0;

        HashMap<Character, Integer> roman = new HashMap<>();
        roman.put('I', 1);
        roman.put('V', 5);
        roman.put('X', 10);
        roman.put('L', 50);
        roman.put('C', 100);
        roman.put('D', 500);
        roman.put('M', 1000);

        // Loop through the string, except the last character
        for (int i = 0; i < s.length() - 1; i++) {
            // Subtract if current value is less than next value
            if (roman.get(s.charAt(i)) < roman.get(s.charAt(i + 1))) {
                sum -= roman.get(s.charAt(i));
            } else {
                // Otherwise, add the value
                sum += roman.get(s.charAt(i));
            }
        }

        // Add the value of the last character
        return sum + roman.get(s.charAt(s.length() - 1));
    }
}
