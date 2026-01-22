package recursion;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 131: Palindrome Partitioning
Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.
Example 1:
Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]

Example 2:
Input: s = "a"
Output: [["a"]]

Constraints:
            1 <= s.length <= 16
            s contains only lowercase English letters.
 */
public class PalindromePartitioning {
    public static void main(String[] args) {

        String s = "aab";
        List<List<String>> result = new ArrayList<>();
        palindromePartitioningRecursion(0, s, new ArrayList<>(), result);
        System.out.println(result);
    }

    /**
     * palindromePartitioningRecursion
     * <p>
     * What it does:
     * Generates **all possible palindrome partitions** of a given string.
     * Each partition is a list of substrings such that **every substring is a palindrome**.
     * The method explores all valid ways to cut the string while preserving palindrome properties.
     * <p>
     * Core Intuition:
     * - At every index, we try to split the string at all possible positions.
     * - We only proceed with a split if the chosen substring is a palindrome.
     * - Once a palindrome substring is chosen, we recursively solve the remaining string.
     * - When the entire string is consumed, the current partition is a valid solution.
     * <p>
     * Why this is a backtracking problem:
     * - Each cut is a decision.
     * - Some decisions lead to valid partitions, others don’t.
     * - Backtracking allows us to explore all valid paths and undo choices when needed.
     * <p>
     * Step-by-step explanation:
     * - `start` represents the index from which we are trying to partition the string.
     * - `tempList` stores the current partition being built.
     * - `result` collects all valid palindrome partitions.
     * <p>
     * Base Case:
     * - If `start == s.length()`, the entire string has been partitioned successfully.
     * - Add a copy of `tempList` to `result`.
     * <p>
     * Recursive Case:
     * - Iterate from `start` to the end of the string.
     * - For each index `i`:
     * - Check if `s[start..i]` is a palindrome.
     * - If yes:
     * - Add the substring to `tempList`.
     * - Recursively partition the remaining string starting from `i + 1`.
     * - Remove the last substring (backtracking step).
     * <p>
     * Why `isPalindrome` is checked before recursion:
     * - Prevents unnecessary recursive calls.
     * - Prunes invalid branches early, improving performance.
     * <p>
     * Why backtracking is required:
     * - A substring that works in one partition may not work in another.
     * - Removing the last choice ensures we try alternative partitions correctly.
     * <p>
     * Example:
     * Input: "aab"
     * Possible partitions:
     * - ["a","a","b"]
     * - ["aa","b"]
     * <p>
     * Time Complexity:
     * - O(2^N * N)
     * - There are O(2^N) possible partitions.
     * - Each palindrome check takes O(N) in the worst case.
     * <p>
     * Space Complexity:
     * - O(N) recursion depth.
     * - O(2^N * N) for storing all partitions in the result.
     * <p>
     * Edge Cases Handled:
     * - Single character string.
     * - Entire string being a palindrome.
     * - No multi-character palindrome possible.
     * <p>
     * Interview Insight:
     * - This is a **classic backtracking + string problem**.
     * - Frequently asked to test recursion, pruning, and partition logic.
     * - Can be optimized using DP to cache palindrome checks.
     */
    private static void palindromePartitioningRecursion(int start, String s, List<String> tempList, List<List<String>> result) {

        if (start == s.length()) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = start; i < s.length(); i++) {
            if (isPalindrome(s, start, i)) {
                tempList.add(s.substring(start, i + 1));
                palindromePartitioningRecursion(i + 1, s, tempList, result);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    private static boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
}
