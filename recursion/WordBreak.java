package recursion;

/*
Leetcode 139: Word Break

Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence
of one or more dictionary words. Note that the same word in the dictionary may be reused multiple times in the segmentation.

Example 1:
Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".

Example 2:
Input: s = "applepenapple", wordDict = ["apple","pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
Note that you are allowed to reuse a dictionary word.

Example 3:
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false

Constraints:
            1 <= s.length <= 300
            1 <= wordDict.length <= 1000
            1 <= wordDict[i].length <= 20
            s and wordDict[i] consist of only lowercase English letters.
            All the strings of wordDict are unique.
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {
    public static void main(String[] args) {
        WordBreak solution = new WordBreak();

        String s1 = "leetcode";
        List<String> dict1 = Arrays.asList("leet", "code");
        System.out.println("Can break 'leetcode'? " + solution.wordBreak(s1, dict1)); // True

        String s2 = "catsandog";
        List<String> dict2 = Arrays.asList("cats", "dog", "sand", "and", "cat");
        System.out.println("Can break 'catsandog'? " + solution.wordBreak(s2, dict2)); // False
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        // Convert list to set for O(1) lookup time
        Set<String> wordSet = new HashSet<>(wordDict);
        // Memoization array: memo[i] stores whether s.substring(i) can be segmented
        Boolean[] memo = new Boolean[s.length()];

        return solve(0, s, wordSet, memo);
    }

    /**
     * solve (Recursive Word Segmentation with Memoization)
     * <p>
     * * What it does:
     * This is the engine of the Word Break solution. It tries to determine if
     * the substring of 's' beginning at 'start' can be broken into valid
     * dictionary words.
     * <p>
     * * Core Intuition:
     * <p>
     * - Think of the string as a sequence of characters where we can place "dividers."
     * <p>
     * - We place a divider at every possible position (end) after the 'start' index.
     * <p>
     * - If the portion to the left of the divider (currentWord) is in the dictionary,
     * we move our 'start' to that divider's position and repeat the process for
     * the rest of the string.
     * <p>
     * Why it uses Memoization:
     * <p>
     * - Without memoization, if the dictionary is ["a", "aa", "aaa"] and the string
     * is "aaaaaaa", the algorithm would evaluate the same suffixes hundreds of times.
     * The `memo` array acts as a "cheat sheet." Once we know that index 'i'
     * cannot lead to a valid segmentation, we save 'false' so we never
     * calculate it again.
     * </p>
     * Step-by-step logic:
     * <p>
     * 1.  Check Memo: If we've already tried to solve the string from this
     * 'start' position, immediately return the saved result (true/false).
     * <p>
     * 2.  Base Case: If 'start' has reached the end of the string, it means
     * all previous segments were valid. We return true.
     * <p>
     * 3.  The "Cut" Loop: We iterate through the string using an 'end' pointer.
     * - We extract a substring: s.substring(start, end).
     * - We check the dictionary (wordSet).
     * <p>
     * 4.  The Chain Reaction: If a valid word is found, we call `solve` for
     * the new suffix starting at 'end'.
     * <p>
     * 5.  State Recording: Before returning, we store the result in `memo[start]`.
     * <p>
     * Example:
     * s = "leetcode", wordDict = ["leet", "code"]
     * - solve(0):
     * <p>
     * - end = 4 -> "leet" is in dict.
     * <p>
     * - Calls solve(4).
     * <p>
     * - solve(4):
     * <p>
     * - end = 8 -> "code" is in dict.
     * <p>
     * - Calls solve(8).
     * <p>
     * - solve(8):
     * <p>
     * - start == s.length. Returns true.
     * - All calls return true back up the stack.
     * <p>
     * * Complexity:
     * - Time: O(N^2) total states/substrings evaluated. Since substring
     * generation is O(N), the total time is O(N^3).
     * - Space: O(N) for the recursion depth and the memoization array.
     */
    private boolean solve(int start, String s, Set<String> wordSet, Boolean[] memo) {
        // BASE CASE: If we reached the end of the string, we successfully segmented it
        if (start == s.length()) {
            return true;
        }

        // MEMOIZATION: If we have already solved this subproblem, return the result
        if (memo[start] != null) {
            return memo[start];
        }

        // Trying every possible end position for a word starting at start
        for (int end = start + 1; end <= s.length(); end++) {
            String currentWord = s.substring(start, end);

            // If the current substring is a valid word, check the rest of the string
            if (wordSet.contains(currentWord)) {
                if (solve(end, s, wordSet, memo)) {
                    return memo[start] = true;
                }
            }
        }

        // If no word starting at start leads to a valid segmentation
        return memo[start] = false;
    }
}