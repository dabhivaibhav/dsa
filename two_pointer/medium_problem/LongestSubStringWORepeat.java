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
        System.out.println("Brute force approach: " + lengthOfLongestSubstringBruteForce(s));
        System.out.println("Optimized approach: " + lengthOfLongestSubstringOptimized(s));

        s = "cabdzabc";
        System.out.println("Brute force approach: " + lengthOfLongestSubstringBruteForce(s));
        System.out.println("Optimized approach: " + lengthOfLongestSubstringOptimized(s));


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


    /*
     * THE "CATERPILLAR" (SLIDING WINDOW) INTUITION
     * * 1. THE SETUP:
     * Imagine a caterpillar moving across the string. The "Head" is our 'right'
     * pointer, and the "Tail" is our 'left' pointer.
     *
     * 2. THE MOVEMENT (GROWING):
     * The Head (right) moves forward one leaf (character) at a time. It "eats"
     * the character and stores it in its stomach (a HashSet).
     *
     * 3. THE CONFLICT (REPEATING CHARACTER):
     * If the Head tries to eat a character that is ALREADY in its stomach:
     * - The caterpillar has a stomach ache! It cannot move the Head further.
     * - To fix this, the Tail (left) must move forward, "digesting" (removing)
     * characters from the stomach one by one.
     * - The Tail keeps moving until the duplicate character is removed.
     *
     * 4. THE RECORD:
     * After every successful move of the Head, we measure the caterpillar's
     * current length: (right - left + 1). We keep track of the biggest size
     * it ever reached.
     *
     * INTERVIEW TIPS & PATTERN RECOGNITION
     *
     * TIP 1: Identify the "Contiguous" Keyword
     * Whenever you see "Longest Substring," "Smallest Subarray," or "Consecutive
     * elements," think SLIDING WINDOW. If the elements must stay in order, the
     * caterpillar approach is almost always the O(N) winner.
     *
     * TIP 2: The "Expand then Contract" Mantra
     * In an interview, explain your logic as: "I will expand my window until it
     * becomes invalid, then I will contract it from the left until it becomes
     * valid again." This shows you understand the two-pointer coordination.
     *
     * TIP 3: Space-Time Tradeoff
     * Using a HashSet (Space) allows us to check for duplicates in O(1) time.
     * This is what brings our total time down from O(N²) (Brute Force) to O(N).
     *
     * TIP 4: The "+1" Rule
     * Always remember: Length = (Right_Index - Left_Index + 1).
     * If you forget the +1, you are calculating the "distance between points"
     * rather than the "number of items."
     *
     * TIP 5: Optimization with HashMap
     * If the interviewer asks for even more speed, tell them: "Instead of a HashSet,
     * I can use a HashMap to store the 'Index' of each character. This allows the
     * Tail (left pointer) to JUMP directly past the duplicate instead of
     * stepping one by one."
     * --------------------------------------------------------------------------------
     */

    /**
     * Method: lengthOfLongestSubstringOptimized(String s)
     * <p>
     * What this method does:
     * Finds the length of the longest substring without repeating characters
     * using the Sliding Window (Two Pointer) technique.
     * <p>
     * Core Idea:
     * Instead of generating all substrings, we maintain a dynamic window
     * that always contains unique characters.
     * <p>
     * We expand the window from the right.
     * If a duplicate appears, we shrink the window from the left
     * until the duplicate is removed.
     * <p>
     * Thought Process:
     * In the brute force approach, we repeatedly rechecked characters.
     * That caused O(n^3) time complexity.
     * <p>
     * The key observation is:
     * If a substring from index left to right is already unique,
     * and we extend right,
     * we only need to fix the window if a duplicate appears.
     * <p>
     * So instead of restarting,
     * we adjust the window intelligently.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int n = s.length();
     * Store the length of the string.
     * <p>
     * int maxLength = 0;
     * Tracks the maximum valid window length found.
     * <p>
     * int left = 0, right = 0;
     * Two pointers define the current window.
     * left  → start of the window
     * right → end of the window
     * <p>
     * HashSet<Character> set:
     * Stores the unique characters currently inside the window.
     * <p>
     * Main Loop:
     * for (right = 0; right < n; right++)
     * <p>
     * We expand the window by moving right forward.
     * <p>
     * Handling Duplicates:
     * <p>
     * while (set.contains(s.charAt(right)))
     * <p>
     * If the character at right already exists in the window:
     * - We remove characters from the left side.
     * - Move left forward.
     * - Keep shrinking until duplicate is removed.
     * <p>
     * This guarantees:
     * The window always contains unique characters.
     * <p>
     * After duplicate removal:
     * Add current character to the set.
     * <p>
     * set.add(s.charAt(right));
     * <p>
     * Update maximum length:
     * <p>
     * Window length = right - left + 1
     * <p>
     * maxLength = Math.max(maxLength, right - left + 1);
     * <p>
     * Example:
     * Input: "abcabcbb"
     * <p>
     * Window expands: "a" → "ab" → "abc"
     * <p>
     * When second 'a' appears:
     * Remove 'a' from left.
     * Window becomes "bca".
     * <p>
     * Continue sliding.
     * <p>
     * Final maxLength = 3
     * <p>
     * Why This Is Efficient:
     * <p>
     * Each character:
     * - Is added to the set once.
     * - Is removed from the set at most once.
     * <p>
     * No character is processed more than twice.
     * <p>
     * Complexity:
     * <p>
     * Time Complexity: O(n)
     * Each character is visited at most twice.
     * <p>
     * Space Complexity: O(n)
     * In worst case, all characters are unique.
     * <p>
     * Interview Takeaway:
     * <p>
     * This is the classical Sliding Window template.
     * <p>
     * Whenever you see:
     * - Longest substring
     * - Without duplicates
     * - Contiguous segment
     * <p>
     * Think:
     * Two pointers + HashSet (or HashMap).
     * <p>
     * Mental Model:
     * Expand right to explore.
     * Shrink left to fix violations.
     * Keep window valid at all times.
     */

    private static int lengthOfLongestSubstringOptimized(String s) {
        int n = s.length();
        int maxLength = 0;
        int left = 0, right = 0;
        // Set to store unique characters in the current window
        HashSet<Character> set = new HashSet<>();

        for (right = 0; right < n; right++) {

            // If we find a duplicate, shrink the window from the left
            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }

            // Add the current character and update max
            set.add(s.charAt(right));

            // Window length is (right - left + 1)
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}
