package two_pointer.medium_problem;

import java.util.HashSet;

/*
Leetcode 3. Longest Substring Without Repeating Characters

Given a string s, find the length of the longest substring without duplicate characters.

Example 1:
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.

Example 2:
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.

Example 3:
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

Constraints:
            0 <= s.length <= 5 * 10^4
            s consists of English letters, digits, symbols and spaces.
 */
public class LongestSubStringWORepeat {

    public static void main(String[] args) {

        String s = "abcabcbb";
        System.out.println(lengthOfLongestSubstringBruteForce(s));
    }

    /**
     * lengthOfLongestSubstringBruteForce(String s)
     * <p>
     * What this method does:
     * Finds the length of the longest substring that contains
     * no repeating characters using a brute force approach.
     * <p>
     * Core Idea:
     * Generate every possible substring and check whether
     * it contains only unique characters.
     * <p>
     * Thought Process:
     * Since the problem asks for the longest substring,
     * the most direct idea is:
     * <p>
     * 1. Try every possible starting index.
     * 2. For each starting index, try every possible ending index.
     * 3. Check whether the substring between them has duplicates.
     * 4. Track the maximum length found.
     * <p>
     * This guarantees correctness because we examine
     * every valid substring.
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * int n = s.length();
     * Store the length of the string.
     * <p>
     * int maxLength = 0;
     * This will store the maximum valid substring length found so far.
     * <p>
     * Outer loop (i from 0 to n-1):
     * Chooses every possible starting point of a substring.
     * <p>
     * Inner loop (j from i to n-1):
     * Chooses every possible ending point of that substring.
     * <p>
     * For every pair (i, j):
     * We check whether substring s[i...j] has all unique characters
     * by calling allUnique(s, i, j).
     * <p>
     * If the substring is unique:
     * Update maxLength using:
     * maxLength = Math.max(maxLength, j - i + 1);
     * <p>
     * <p>
     * Helper Method: allUnique(String s, int start, int end)
     * <p>
     * What it does:
     * Checks whether the substring between start and end
     * contains duplicate characters.
     * <p>
     * How it works:
     * - A HashSet is used to store characters.
     * - Iterate from start to end.
     * - If a character is already in the set → duplicate found → return false.
     * - Otherwise, add it to the set.
     * - If loop completes → all characters are unique → return true.
     * <p>
     * <p>
     * Example:
     * Input: "abcabcbb"
     * <p>
     * Substrings checked:
     * "a", "ab", "abc" → valid
     * "abca" → invalid (duplicate 'a')
     * <p>
     * Longest valid substring found = "abc"
     * Length = 3
     * <p>
     * <p>
     * Complexity:
     * Outer loop runs n times.
     * Inner loop runs up to n times.
     * allUnique may scan up to n characters.
     * <p>
     * Time Complexity: O(n^3)
     * Space Complexity: O(n) for the HashSet in worst case.
     * <p>
     * <p>
     * Interview Takeaway:
     * This is the most straightforward and correct approach.
     * However, it is inefficient.
     * <p>
     * The key observation for optimization:
     * We are recomputing duplicate checks again and again.
     * Instead, we should maintain a sliding window
     * and adjust it dynamically when duplicates appear.
     * <p>
     * That leads to the optimized O(n) two-pointer solution.
     */

    private static int lengthOfLongestSubstringBruteForce(String s) {
        int n = s.length();
        int maxLength = 0;

        // Pick every possible starting point
        for (int i = 0; i < n; i++) {
            // Pick every possible ending point
            for (int j = i; j < n; j++) {
                // Check if the substring s[i...j] has unique characters
                if (allUnique(s, i, j)) {
                    // If unique, update the record
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    // Helper method to check for duplicates
    private static boolean allUnique(String s, int start, int end) {
        HashSet<Character> set = new HashSet<>();
        for (int k = start; k <= end; k++) {
            char ch = s.charAt(k);
            if (set.contains(ch)) return false; // Found a duplicate!
            set.add(ch);
        }
        return true;
    }
}
