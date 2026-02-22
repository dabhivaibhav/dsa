package two_pointer.medium_problem;

/*
Leetcode 1358: Number of Substrings Containing All Three Characters

Given a string s consisting only of characters a, b and c.
Return the number of substrings containing at least one occurrence of all these characters a, b and c.

Example 1:
Input: s = "abcabc"
Output: 10
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab",
"abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).

Example 2:
Input: s = "aaacb"
Output: 3
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".

Example 3:
Input: s = "abc"
Output: 1

Constraints:
            3 <= s.length <= 5 x 10^4
            s only consists of a, b or c characters.
 */
public class SubStringContainingAllThreeChar {

    public static void main(String[] args) {
        String s = "abcabc";
        System.out.println(countSubstringBruteForce(s));
    }

    /**
     * countSubstringBruteForce(String s)
     * <p>
     * What this method does:
     * Counts the number of substrings that contain
     * at least one 'a', one 'b', and one 'c'.
     * <p>
     * <p>
     * Core Idea:
     * <p>
     * Generate every possible substring.
     * For each substring, check whether it contains
     * all three characters: 'a', 'b', and 'c'.
     * <p>
     * If yes → increment the count.
     * <p>
     * <p>
     * Thought Process:
     * <p>
     * A substring is valid if:
     * it includes at least one occurrence
     * of each character a, b, and c.
     * <p>
     * The brute force way:
     * <p>
     * 1. Choose a starting index i.
     * 2. Choose an ending index j (where j ≥ i).
     * 3. Extract substring(i, j).
     * 4. Check whether it contains 'a', 'b', and 'c'.
     * 5. If true → increment answer.
     * <p>
     * <p>
     * How the Code Works:
     * <p>
     * Outer Loop (i):
     * Fixes the starting index of the substring.
     * <p>
     * Inner Loop (j):
     * Expands the substring to the right.
     * <p>
     * For each substring:
     * s.substring(i, j + 1)
     * <p>
     * Call findABC() to check validity.
     * <p>
     * <p>
     * Helper Method: findABC(String s)
     * <p>
     * Uses:
     * s.contains("a")
     * s.contains("b")
     * s.contains("c")
     * <p>
     * If all three conditions are true,
     * return 1 (valid substring),
     * otherwise return 0.
     * <p>
     * <p>
     * Example:
     * <p>
     * s = "abc"
     * <p>
     * Substrings:
     * "a"   → invalid
     * "ab"  → invalid
     * "abc" → valid
     * "b"   → invalid
     * "bc"  → invalid
     * "c"   → invalid
     * <p>
     * Total valid substrings = 1
     * <p>
     * <p>
     * Complexity Analysis:
     * <p>
     * Number of substrings = O(n^2)
     * Each substring creation = O(n)
     * Each contains() check = O(n)
     * <p>
     * Total Time Complexity: O(n^3)
     * <p>
     * Space Complexity:
     * Extra space used for substring creation.
     * <p>
     * <p>
     * Interview Takeaway:
     * <p>
     * This solution is simple but highly inefficient.
     * <p>
     * Since the string only contains 'a', 'b', and 'c',
     * we can use a sliding window approach
     * to solve this in O(n).
     * <p>
     * The optimization idea:
     * Once a window contains all three characters,
     * every larger extension of that window
     * will also remain valid.
     */
    private static int countSubstringBruteForce(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                count += findABC(s.substring(i, j + 1));
            }
        }
        return count;
    }

    private static int findABC(String s) {
        return s.contains("a") && s.contains("b") && s.contains("c") ? 1 : 0;
    }
}
