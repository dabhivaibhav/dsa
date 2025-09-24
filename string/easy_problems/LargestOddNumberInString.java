package string.easy_problems;

/*
1903. Largest Odd Number in String
You are given a string num, representing a large integer. Return the largest-valued odd integer (as a string) that is a
non-empty substring of num, or an empty string "" if no odd integer exists. A substring is a contiguous sequence of
characters within a string.

Example 1:
Input: num = "52"
Output: "5"
Explanation: The only non-empty substrings are "5", "2", and "52". "5" is the only odd number.

Example 2:
Input: num = "4206"
Output: ""
Explanation: There are no odd numbers in "4206".

Example 3:
Input: num = "35427"
Output: "35427"
Explanation: "35427" is already an odd number.

Constraints:
            1 <= num.length <= 10^5
            num only consists of digits and does not contain any leading zeros.
 */
public class LargestOddNumberInString {

    public static void main(String[] args) {
        String num = "52";
        String num1 = "4206";
        String num2 = "35427";
        String num3 = "7542351161";
        String num4 = "239537672423884969653287101";
        String num5 = "3691669784801845146";
        String num6 = "32782489638346578713315098393010310518347382";
        System.out.println(largestOddNumberStringBruteforce(num));
        System.out.println(largestOddNumberStringBruteforce(num1));
        System.out.println(largestOddNumberStringBruteforce(num2));
        System.out.println(largestOddNumberStringBruteforce(num3));
        System.out.println(largestOddNumberStringBruteforce(num4));
        System.out.println(largestOddNumberStringBruteforce(num5));
        System.out.println(largestOddNumberStringBruteforce(num6));
        System.out.println(largestOddNumberStringOptimal(num));
        System.out.println(largestOddNumberStringOptimal(num1));
        System.out.println(largestOddNumberStringOptimal(num2));
        System.out.println(largestOddNumberStringOptimal(num3));
        System.out.println(largestOddNumberStringOptimal(num4));
        System.out.println(largestOddNumberStringOptimal(num5));
        System.out.println(largestOddNumberStringOptimal(num6));
    }

    /**
     * What it does:
     * Finds the largest odd number that can be formed by taking a prefix of the given numeric string
     * using a brute-force approach. A number is considered odd if its last digit is odd (1, 3, 5, 7, or 9).
     *
     * <p>
     * Why it works:
     * - The problem asks for the largest odd number that is a prefix of the input.
     * - Every time we find a prefix ending at an odd digit, that prefix is a valid odd number.
     * - Among all such prefixes, the one ending at the **rightmost odd digit** will be the largest.
     * - This method iterates over every character from left to right, updating the answer each time an odd digit is found.
     * - By the end of the loop, `answer` holds the prefix that ends at the last odd digit seen.
     *
     * <p>
     * How it works:
     * - Initialize an empty string `answer`.
     * - Traverse the input string from left to right:
     * - Convert the current character to an integer and check if it’s odd.
     * - If it is, update `answer` with the prefix `num.substring(0, i + 1)`.
     * - After the loop, return the final `answer`. If no odd digit is found, `answer` remains empty.
     *
     * <p>
     * Example:
     * Input: "35428"
     * - '3' → odd → answer = "3"
     * - '5' → odd → answer = "35"
     * - '4' → even → skip
     * - '2' → even → skip
     * - '8' → even → skip
     * Output: "35"
     * <p>
     * Input: "4206" → no odd digit → answer = ""
     *
     * <p>
     * Time Complexity:
     * - O(n²): Each time an odd digit is found, `substring()` creates a new string of length O(i),
     * leading to potentially quadratic work if there are many odd digits.
     * <p>
     * Space Complexity:
     * - O(n): Multiple substrings may be created during execution.
     *
     * <p>
     * Output:
     * Returns the largest odd-number prefix by examining every possible prefix ending at an odd digit.
     */
    private static String largestOddNumberStringBruteforce(String num) {
        String answer = "";
        for (int i = 0; i < num.length(); i++) {
            if (Integer.parseInt(String.valueOf(num.charAt(i))) % 2 != 0) {
                answer = num.substring(0, i + 1);
            }
        }
        return answer;
    }

    /**
     * What it does:
     * Finds the largest odd number that can be formed by taking a prefix of the given numeric string
     * using an optimized single-pass approach. If no odd digit exists, returns an empty string.
     *
     * <p>
     * Why it works:
     * - The largest odd prefix must end at the **rightmost odd digit** in the string.
     * - There’s no need to check every prefix individually — finding the last odd digit index is enough.
     * - Once found, returning the substring from the start to that index (inclusive) yields the largest odd prefix.
     *
     * <p>
     * How it works:
     * - Handle edge cases: if the input is null or empty, return "".
     * - Iterate from right to left:
     * - Convert the current character to a digit and check if it’s odd.
     * - As soon as an odd digit is found, return the substring from index `0` to `i + 1`.
     * - If no odd digit is found after scanning the entire string, return an empty string.
     *
     * <p>
     * Example:
     * Input: "35428"
     * - Start from end:
     * - '8' → even
     * - '2' → even
     * - '4' → even
     * - '5' → odd → return "35"
     * <p>
     * Input: "12345" → last digit is odd → return "12345"
     * Input: "4206"  → no odd digit → return ""
     *
     * <p>
     * Time Complexity:
     * - O(n): A single scan from right to left visits each character once.
     * <p>
     * Space Complexity:
     * - O(1): Only a few variables are used. The returned substring reuses the original string’s storage.
     *
     * <p>
     * Output:
     * Returns the largest odd-number prefix by directly finding the rightmost odd digit
     * and slicing the string once, making this the most efficient solution.
     */
    private static String largestOddNumberStringOptimal(String num) {
        if (num == null || num.isEmpty()) return "";
        for (int i = num.length() - 1; i >= 0; i--) {
            if (((num.charAt(i) - '0') & 1) == 1) {
                return num.substring(0, i + 1);
            }
        }
        return "";
    }

}

