package two_pointer.hard_problem;

/*
Leetcode 76. Minimum Window Substring

Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every
character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".
The testcases will be generated such that the answer is unique.

Example 1:
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.

Example 2:
Input: s = "a", t = "a"
Output: "a"
Explanation: The entire string s is the minimum window.

Example 3:
Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.

Constraints:
            m == s.length
            n == t.length
            1 <= m, n <= 10^5
            s and t consist of uppercase and lowercase English letters.

Follow up: Could you find an algorithm that runs in O(m + n) time?
 */
public class MinimumWindowSubString {

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(minWindowBruteForce(s, t));
        s = "a";
        t = "a";
        System.out.println(minWindowBruteForce(s, t));
        s = "a";
        t = "aa";
        System.out.println(minWindowBruteForce(s, t));
    }

    /**
     * minWindowBruteForce(String s, String t)
     * <p>
     * Goal:
     * <p>
     * Find the smallest substring of 's'
     * that contains ALL characters of 't'
     * including duplicates.
     * <p>
     * Core Idea (Brute Force):
     * <p>
     * Generate every possible substring of 's'
     * and check whether it contains all characters of 't'.
     * <p>
     * If yes, compare its length with the current minimum
     * and store the smaller one.
     * <p>
     * How It Works Step-by-Step:
     * <p>
     * Step 1: Pick every possible starting index 'i'
     * <p>
     * Step 2: For each 'i', pick every possible ending index 'j'
     * <p>
     * Step 3: Extract substring:
     * <p>
     * sub = s.substring(i, j + 1)
     * <p>
     * Step 4: Check if this substring contains all characters of 't'
     * using frequency counting.
     * <p>
     * Step 5: If valid and smaller than current minimum,
     * update the answer.
     * <p>
     * How isValid() Works:
     * <p>
     * 1. Create frequency array for substring.
     * <p>
     * 2. Decrease counts using characters from t.
     * <p>
     * 3. If any count becomes negative,
     * substring is missing a required character.
     * <p>
     * 4. Otherwise, substring is valid.
     * <p>
     * Example:
     * <p>
     * s = "ADOBECODEBANC"
     * t = "ABC"
     * <p>
     * Possible valid substrings include:
     * <p>
     * "ADOBEC"
     * "DOBECODEBA"
     * "BANC"   ← smallest valid substring
     * <p>
     * Output:
     * "BANC"
     * <p>
     * Time Complexity:
     * <p>
     * Generating substrings: O(n²)
     * <p>
     * Validating each substring: O(n)
     * <p>
     * Total: O(n³)
     * <p>
     * Space Complexity: O(1)
     * <p>
     * Frequency array size is constant (128 ASCII characters)
     * <p>
     * Why This Is Inefficient:
     * <p>
     * It checks many unnecessary substrings repeatedly.
     * <p>
     * Example:
     * If string length = 100000
     * <p>
     * Total substrings ≈ 5,000,000,000
     * <p>
     * This is extremely slow.
     * <p>
     * What Needs Improvement:
     * <p>
     * Instead of checking every substring,
     * we should use Sliding Window to:
     * <p>
     * • Expand when invalid
     * • Shrink when valid
     * • Track minimum efficiently
     * <p>
     * This reduces complexity to: O(m + n)
     * which is optimal.
     */
    private static String minWindowBruteForce(String s, String t) {
        String minWindow = "";
        int minLen = Integer.MAX_VALUE;

        // Generate every possible starting point
        for (int i = 0; i < s.length(); i++) {
            // Generate every possible ending point
            for (int j = i; j < s.length(); j++) {
                String sub = s.substring(i, j + 1);

                // Check if this substring 'sub' contains all of 't'
                if (isValid(sub, t)) {
                    // If it's the smallest we've seen, remember it
                    if (sub.length() < minLen) {
                        minLen = sub.length();
                        minWindow = sub;
                    }
                }
            }
        }
        return minWindow;
    }

    private static boolean isValid(String sub, String t) {
        int[] count = new int[128];
        // Map out the substring
        for (char c : sub.toCharArray()) count[c]++;

        // Check if we have enough of each character required by t
        for (char c : t.toCharArray()) {
            count[c]--;
            if (count[c] < 0) return false; // We are missing a character
        }
        return true;
    }
}
