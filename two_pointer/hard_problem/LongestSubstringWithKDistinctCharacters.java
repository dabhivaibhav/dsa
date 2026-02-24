package two_pointer.hard_problem;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println(findLengthSlidingWindow(s, k));
        System.out.println(findLengthSlidingWindowOptimized(s, k));

        s = "abcddefg";
        k = 3;
        System.out.println(findLengthBruteFroce(s, k));
        System.out.println(findLengthSlidingWindow(s, k));
        System.out.println(findLengthSlidingWindowOptimized(s, k));

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

    /**
     * findLengthSlidingWindow(String s, int k)
     * <p>
     * What this method does:
     * Finds the length of the longest substring
     * containing at most k distinct characters
     * using the Sliding Window technique.
     * <p>
     * Core Idea:
     * <p>
     * Maintain a dynamic window [left...right]
     * and track character frequencies using a HashMap.
     * <p>
     * Expand the window by moving 'right'.
     * Shrink the window when distinct characters exceed k.
     * <p>
     * How It Works:
     * <p>
     * 1. Expand Phase:
     * Add s.charAt(right) into the map.
     * <p>
     * 2. Shrink Phase:
     * While distinct characters > k:
     * - Decrease frequency of s.charAt(left)
     * - Remove it from map if frequency becomes 0
     * - Move left pointer forward
     * <p>
     * 3. Measure:
     * When window is valid (≤ k distinct),
     * update maxLength.
     * <p>
     * Time Complexity:
     * <p>
     * Outer loop (right pointer) runs O(n).
     * Inner while loop (left pointer) also runs O(n) overall.
     * <p>
     * Total Time Complexity:
     * O(n) + O(n)
     * → O(2n)
     * → O(n)
     * <p>
     * Why?
     * Each character is added to the window once (right pointer)
     * and removed from the window once (left pointer).
     * <p>
     * Space Complexity:
     * O(k) → In worst case, map stores at most k characters.
     */
    private static int findLengthSlidingWindow(String s, int k) {
        if (s == null || s.isEmpty() || k == 0) return 0;
        int left = 0, maxLength = 0;
        Map<Character, Integer> charFreqMap = new HashMap<>();
        for (int right = 0; right < s.length(); right++) {
            // Expand: Add the character at the "Head"
            char rightChar = s.charAt(right);
            charFreqMap.put(rightChar, charFreqMap.getOrDefault(rightChar, 0) + 1);
            // Shrink: While we have more than K distinct characters
            while (charFreqMap.size() > k) {
                char leftChar = s.charAt(left);
                charFreqMap.put(leftChar, charFreqMap.get(leftChar) - 1);
                // If frequency hits 0, remove the character entirely
                if (charFreqMap.get(leftChar) == 0) {
                    charFreqMap.remove(leftChar);
                }
                // Move the "Tail" forward
                left++;
            }
            // Measure: Current window [left...right] is now valid
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }


    /**
     * findLengthSlidingWindowOptimized(String s, int k)
     * <p>
     * What was Improved:
     * <p>
     * Instead of using:
     * while (map.size() > k)
     * <p>
     * We use:
     * if (map.size() > k)
     * <p>
     * Why is this valid?
     * <p>
     * Because at every step, we only add ONE character.
     * So the distinct count can increase by at most 1.
     * <p>
     * That means:
     * We only ever exceed k by exactly 1.
     * <p>
     * Therefore,
     * removing one character from the left
     * is sufficient to restore validity.
     * <p>
     * Practical Improvement:
     * <p>
     * - Removes unnecessary repeated checks.
     * - Slightly cleaner logic.
     * - Same asymptotic complexity,
     * but fewer operations in practice.
     * <p>
     * Time Complexity:
     * <p>
     * Right pointer → O(n)
     * Left pointer  → O(n)
     * <p>
     * Total:
     * O(n) + O(n)
     * →O(2n)
     * →O(n)
     * <p>
     * Space Complexity:
     * O(k)
     * <p>
     * Final Insight:
     * <p>
     * The brute-force version was O(n²).
     * Sliding window reduces it to linear time.
     * <p>
     * The true power here is recognizing:
     * We never re-process characters unnecessarily.
     * <p>
     * Each character enters the window once
     * and leaves once.
     * <p>
     * That’s the secret rhythm of all good
     * sliding window problems.
     */
    private static int findLengthSlidingWindowOptimized(String s, int k) {
        if (s == null || s.isEmpty() || k == 0) return 0;
        int left = 0, maxLength = 0;
        Map<Character, Integer> charFreqMap = new HashMap<>();
        for (int right = 0; right < s.length(); right++) {
            // Expand: Add the character at the "Head"
            char rightChar = s.charAt(right);
            charFreqMap.put(rightChar, charFreqMap.getOrDefault(rightChar, 0) + 1);
            // Shrink: While we have more than K distinct characters
            if (charFreqMap.size() > k) {
                char leftChar = s.charAt(left);
                charFreqMap.put(leftChar, charFreqMap.get(leftChar) - 1);
                // If frequency hits 0, remove the character entirely
                if (charFreqMap.get(leftChar) == 0) {
                    charFreqMap.remove(leftChar);
                }
                // Move the "Tail" forward
                left++;
            }
            // Measure: Current window [left...right] is now valid
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

}
