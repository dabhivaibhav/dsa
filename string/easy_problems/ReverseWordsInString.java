package string.easy_problems;

/*
Leetcode: 151. Reverse Words in a String

Given an input string s, reverse the order of the words.
A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.
Return a string of the words in reverse order concatenated by a single space.
Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string should only
have a single space separating the words. Do not include any extra spaces.

Example 1:
Input: s = "the sky is blue"
Output: "blue is sky the"

Example 2:
Input: s = "  hello world  "
Output: "world hello"
Explanation: Your reversed string should not contain leading or trailing spaces.

Example 3:
Input: s = "a good   example"
Output: "example good a"
Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.

Constraints:
            1 <= s.length <= 10^4
            s contains English letters (upper-case and lower-case), digits, and spaces ' '.
            There is at least one word in s.

Follow-up: If the string data type is mutable in your language, can you solve it in-place with O(1) extra space?
 */
public class ReverseWordsInString {

    public static void main(String[] args) {
        String string = "the sky is blue";
        String string1 = "  hello world  ";
        String string2 = "a good   example";
        System.out.println(reverseWordsStringBruteForce(string));
        System.out.println(reverseWordsStringBruteForce(string1));
        System.out.println(reverseWordsStringBruteForce(string2));

    }

    /**
     * What it does:
     * Reverses the order of words in a given string, ensuring that multiple spaces
     * are collapsed into a single space and that no leading or trailing spaces remain.
     *
     * <p>
     * Why it works:
     * - The task is to reverse only the words, not the characters within words.
     * - By trimming the input string, we remove unnecessary leading and trailing spaces.
     * - Splitting on `" "` produces tokens, where actual words remain intact and
     * sequences of multiple spaces become empty strings.
     * - Iterating from the last token to the first and skipping empty tokens ensures
     * that words are appended in reverse order with correct spacing.
     *
     * <p>
     * How it works:
     * - If the input is null or empty, return an empty string.
     * - Apply `trim()` to remove leading and trailing spaces.
     * - Split the string by `" "` into an array of tokens (words and possibly empty strings).
     * - Iterate over the tokens from the end to the beginning:
     * - Skip empty tokens (caused by multiple spaces).
     * - Append non-empty tokens to a `StringBuilder`.
     * - Add a space after each word except the last one appended.
     * - Return the final string from the `StringBuilder`.
     *
     * <p>
     * Example:
     * Input: "  hello   world  "
     * - After trim → "hello   world"
     * - After split → ["hello", "", "", "world"]
     * - Iterate backwards: append "world", then "hello"
     * - Output → "world hello"
     * <p>
     * Input: "a  b   c"
     * - Split → ["a", "", "b", "", "", "c"]
     * - Reverse and skip empty → "c b a"
     * <p>
     * Input: "   "
     * - After trim → ""
     * - Split → [""]
     * - All tokens skipped → ""
     *
     * <p>
     * Time Complexity:
     * - O(n): where n is the length of the string, due to splitting and rebuilding.
     * <p>
     * Space Complexity:
     * - O(n): to store the split tokens and the StringBuilder result.
     *
     * <p>
     * Output:
     * Returns a new string with words in reversed order,
     * separated by a single space, with no leading or trailing spaces.
     */
    private static String reverseWordsStringBruteForce(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }

        String[] words = string.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            if (words[i].isEmpty()) {
                continue;
            }
            sb.append(words[i]);
            if (i != 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}

