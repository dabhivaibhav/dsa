package two_pointer.hard_problem;

/*
Longest Substring With At Most K Distinct Characters

Given a string s and an integer k.Find the length of the longest substring with at most k distinct characters.

Example 1
Input : s = "aababbcaacc" , k = 2
Output : 6
Explanation : The longest substring with at most two distinct characters is "aababb".
The length of the string 6.

Example 2
Input : s = "abcddefg" , k = 3
Output : 4
Explanation : The longest substring with at most three distinct characters is "bcdd".
The length of the string 4.

Constraints:
            1 <= s.length <= 10^5
            1 <= k <= 26
*/
public class LongestSubstringWithKDistinctCharacters {

    public static void main(String[] args) {
        String s = "aababbcaacc";
        int k = 2;
        System.out.println(findLengthBruteFroce(s, k));

        s = "abcddefg";
        k = 3;
        System.out.println(findLengthBruteFroce(s, k));

    }

    /**
     * findLengthBruteFroce(String s, int k)
     * <p>
     * What this method does:
     * Finds the length of the longest substring
     * that contains at most k distinct characters.
     * <p>
     * Core Idea:
     * <p>
     * Generate every possible substring
     * and track how many distinct characters it contains.
     * <p>
     * If the substring has at most k distinct characters,
     * update the maximum length.
     * <p>
     * Thought Process:
     * <p>
     * A substring is valid if:
     * number of distinct characters ≤ k
     * <p>
     * Brute force approach:
     * <p>
     * 1. Fix a starting index i.
     * 2. Expand the substring to the right using j.
     * 3. Track frequency of characters inside the window.
     * 4. Maintain countDistinct.
     * 5. If distinct characters exceed k → stop expanding.
     * <p>
     * How the Code Works:
     * <p>
     * Outer Loop (i):
     * Picks the starting index of the substring.
     * <p>
     * For each starting index:
     * - Create a fresh frequency array of size 26
     * (since input contains lowercase letters).
     * - Reset distinct character count.
     * <p>
     * Inner Loop (j):
     * Expands substring from index i to j.
     * <p>
     * If charCount[currentChar] == 0:
     * It means we are seeing this character
     * for the first time in this window.
     * Increase countDistinct.
     * <p>
     * Increment the frequency of the character.
     * <p>
     * If countDistinct ≤ k:
     * Update maxLength = max(maxLength, window size)
     * <p>
     * If countDistinct > k:
     * Break.
     * No need to continue expanding from this i,
     * because adding more characters
     * will only increase distinct count further.
     * <p>
     * Example:
     * <p>
     * s = "aababbcaacc", k = 2
     * <p>
     * Valid longest substring:
     * "aababb"
     * Length = 6
     * <p>
     * Complexity:
     * <p>
     * Outer loop runs n times.
     * Inner loop runs up to n times.
     * <p>
     * Time Complexity: O(n^2)
     * <p>
     * Space Complexity: O(1)
     * Frequency array size is fixed (26).
     * <p>
     * Interview Takeaway:
     * <p>
     * This solution is correct but inefficient.
     * <p>
     * The optimization insight:
     * Instead of restarting from every index,
     * we can use a Sliding Window approach
     * and adjust the left pointer dynamically
     * when distinct characters exceed k.
     * <p>
     * That reduces the time complexity to O(n).
     */
    private static int findLengthBruteFroce(String s, int k) {
        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            int countDistinct = 0;
            int[] charCount = new int[26];

            for (int j = i; j < s.length(); j++) {
                // Check if this is a NEW character for this specific window
                if (charCount[s.charAt(j) - 'a'] == 0) {
                    countDistinct++;
                }
                charCount[s.charAt(j) - 'a']++;

                if (countDistinct <= k) {
                    // Update maxLength if the current window [i...j] is valid
                    maxLength = Math.max(maxLength, j - i + 1);
                } else {
                    // Too many characters, stop looking from this start point 'i'
                    break;
                }
            }
        }
        return maxLength;
    }
}
