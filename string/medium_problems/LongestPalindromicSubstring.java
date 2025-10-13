package string.medium_problems;

/*
Leetcode: 5. Longest Palindromic Substring
Given a string s, return the longest palindromic substring in s.

Example 1:
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.

Example 2:
Input: s = "cbbd"
Output: "bb"

Constraints:
            1 <= s.length <= 1000
            s consist of only digits and English letters.
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        String s = "babad";
        String s1 = "cbbd";
        String s2 = "a";
        String s3 = "bb";
        String s4 = "ccc";
        String s5 = "acd";

        System.out.println(findLongestPalindromicSubStringBruteForce(s));
        System.out.println(findLongestPalindromicSubStringBruteForce(s1));
        System.out.println(findLongestPalindromicSubStringBruteForce(s2));
        System.out.println(findLongestPalindromicSubStringBruteForce(s3));
        System.out.println(findLongestPalindromicSubStringBruteForce(s4));
        System.out.println(findLongestPalindromicSubStringBruteForce(s5));
        System.out.println(findLongestPalindromicSubStringOptimal(s));
        System.out.println(findLongestPalindromicSubStringOptimal(s1));
        System.out.println(findLongestPalindromicSubStringOptimal(s2));
        System.out.println(findLongestPalindromicSubStringOptimal(s3));
        System.out.println(findLongestPalindromicSubStringOptimal(s4));
        System.out.println(findLongestPalindromicSubStringOptimal(s5));

    }

    /**
     * What it does:
     * Implements the brute force approach to find the longest palindromic substring in a given string `s`.
     * It systematically checks every possible substring to determine if it is a palindrome by reversing the substring.
     *
     * <p>
     * Why it works:
     * - Every possible substring is considered.
     * - Each substring's palindrome property is verified by reversing it and comparing.
     * - Tracks the longest palindrome encountered.
     * - Though guaranteed to find the correct answer, it is inefficient for large strings due to its cubic time complexity.
     *
     * <p>
     * How it works:
     * 1. Initialize an empty string `answer` for the longest palindrome found.
     * 2. Use two nested loops to generate every possible substring: from index `i` to `j`.
     * 3. For each substring, reverse it using a StringBuilder and compare to the original substring.
     * 4. If the substring is a palindrome and longer than the previously recorded longest palindrome, update `answer`.
     * 5. Return the longest palindrome stored in `answer`.
     *
     * <p>
     * Edge Cases:
     * - Empty string returns empty string.
     * - Single character strings return the character itself.
     * - Multiple palindromes of same length returns the first encountered palindrome.
     *
     * <p>
     * Time Complexity:
     * - O(n³): O(n²) substrings and O(n) to reverse each substring.
     *
     * Space Complexity:
     * - O(n): Creating reversed substrings using StringBuilder.
     *
     * <p>
     * Output:
     * - Returns the longest palindromic substring found in `s`.
     */
    private static String findLongestPalindromicSubStringBruteForce(String s) {
        String answer = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                StringBuilder temp = new StringBuilder();
                String subStr = s.substring(i, j);
                for (int k = subStr.length() -1 ; k >= 0; k--) {
                    temp.append(subStr.charAt(k));
                }
                if (subStr.contentEquals(temp) && subStr.length() > answer.length()) {
                    answer = subStr;
                }
            }
        }
        return answer;
    }

    /**
     * What it does:
     * Efficiently finds the longest palindromic substring by expanding around each possible center.
     * It handles odd and even length palindromes by considering both single characters and gaps between characters as centers.
     *
     * <p>
     * Why it works:
     * - Every palindrome can be expanded from its center.
     * - By expanding around all possible centers, it checks all palindromes in O(n²) time instead of O(n³).
     * - Keeps track of the longest palindrome found during expansions.
     *
     * <p>
     * How it works:
     * 1. Initialize two pointers, `start` and `end`, to track the longest palindrome substring boundaries.
     * 2. For each index `i` in the string:
     *    - Call `expandAroundCenter` for odd length palindrome (center `i`).
     *    - Call `expandAroundCenter` for even length palindrome (center between `i` and `i+1`).
     * 3. Use the longer palindrome length of the two expansions to update `start` and `end`.
     * 4. Return the substring from `start` to `end`.
     *
     * <p>
     * The helper function `expandAroundCenter`:
     * - Expands from `left` and `right` indices outwards while characters match and boundaries are valid.
     * - Returns the length of the palindrome centered at `left` and `right`.
     *
     * <p>
     * Edge Cases:
     * - Handles empty and single-character strings gracefully.
     * - Finds longest palindrome in strings with repeated characters.
     *
     * <p>
     * Time Complexity:
     * - O(n²): Each center expansion takes O(n), and there are O(n) centers.
     *
     * Space Complexity:
     * - O(1): Uses only a few integer variables for pointers and lengths.
     *
     * <p>
     * Output:
     * - Returns the longest palindromic substring found in `s`.
     */
    public static String findLongestPalindromicSubStringOptimal(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);     // Odd length
            int len2 = expandAroundCenter(s, i, i + 1); // Even length
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1; // Length of palindrome
    }
}

