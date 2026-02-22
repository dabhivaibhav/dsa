package two_pointer.medium_problem;

/*
Leetcode 424: Longest Repeating Character Replacement

You are given a string s and an integer k.
You can choose any character of the string and change it to any other uppercase English character.
You can perform this operation at most k times.
Return the length of the longest substring containing the same letter you can get after performing the above operations.

Example 1:
Input: s = "ABAB", k = 2
Output: 4
Explanation: Replace the two 'A's with two 'B's or vice versa.

Example 2:
Input: s = "AABABBA", k = 1
Output: 4
Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.
There may exists other ways to achieve this answer too.

Constraints:
            1 <= s.length <= 10^5
            s consists of only uppercase English letters.
            0 <= k <= s.length
 */
public class LongestRepeatingCharacterPlacement {

    public static void main(String[] args) {
        String s = "ABAB";
        int k = 2;
        System.out.println(findLongestRepeatingCharBruteForce(s, k));
        System.out.println(findLongestRepeatingCharSlidingWindow(s, k));
        System.out.println(findLongestRepeatingCharSlidingOptimal(s, k));
    }

    private static int findLongestRepeatingCharBruteForce(String s, int k) {
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {

            // Frequency array to store counts of each uppercase letter
            int[] freq = new int[26];

            // Variable to track the max frequency character in the current window
            int maxFreq = 0;

            for (int j = i; j < s.length(); j++) {
                freq[s.charAt(j) - 'A']++;

                // Update most frequent character count in window
                maxFreq = Math.max(maxFreq, freq[s.charAt(j) - 'A']);

                // Current window size
                int windowSize = j - i + 1;

                // Calculate replacements needed to make all characters same
                int replacements = windowSize - maxFreq;

                // If replacements are within k, update maxLength
                if (replacements <= k) {
                    maxLength = Math.max(maxLength, windowSize);
                }
            }
        }
        return maxLength;
    }

    /**
     * findLongestRepeatingCharSlidingWindow(String s, int k)
     * <p>
     * What this method does:
     * Finds the length of the longest substring that can be converted
     * into all identical characters by performing at most k replacements.
     * <p>
     * Core Idea:
     * <p>
     * This is a Sliding Window optimization of the brute force approach.
     * <p>
     * Instead of restarting for every index,
     * we maintain a dynamic window that always satisfies:
     * <p>
     * (windowSize - maxFrequency) <= k
     * <p>
     * Where:
     * - windowSize = right - left + 1
     * - maxFrequency = frequency of the most frequent character
     * inside the current window.
     * <p>
     * Why This Formula Works:
     * <p>
     * In any window,
     * the minimum replacements needed to make all characters identical is:
     * <p>
     * replacements = windowSize - maxFrequency
     * <p>
     * Because we convert all other characters
     * into the most frequent one.
     * <p>
     * If replacements <= k,
     * the window is valid.
     * <p>
     * How the Code Works Step by Step:
     * <p>
     * int[] freq = new int[26];
     * Stores frequency of uppercase letters in current window.
     * <p>
     * int maxFrequency = 0;
     * Tracks highest frequency of a single character in window.
     * <p>
     * int left = 0, right = 0;
     * Two pointers defining the sliding window.
     * <p>
     * While right < s.length():
     * <p>
     * 1. Expand window:
     * Increase frequency of s.charAt(right).
     * <p>
     * 2. Update maxFrequency:
     * maxFrequency = Math.max(maxFrequency, current character frequency)
     * <p>
     * 3. If window becomes invalid:
     * <p>
     * while ((windowSize - maxFrequency) > k)
     * <p>
     * That means more than k replacements are needed.
     * So we shrink the window from the left:
     * - Decrease freq of s.charAt(left)
     * - Move left forward
     * <p>
     * 4. After ensuring window is valid:
     * Update maxLength.
     * <p>
     * Important Subtle Insight:
     * <p>
     * maxFrequency is not reduced when shrinking the window.
     * <p>
     * This is intentional.
     * Even if it becomes slightly outdated,
     * it does not break correctness,
     * because it only delays shrinking slightly.
     * <p>
     * The algorithm still guarantees O(n) time.
     * <p>
     * Example:
     * <p>
     * s = "AABABBA", k = 1
     * <p>
     * Window grows.
     * When replacements needed exceed k,
     * left pointer moves forward.
     * <p>
     * Final maximum valid window length = 4.
     * <p>
     * Why This Is Efficient:
     * <p>
     * Each character:
     * - Is added once by right.
     * - Is removed at most once by left.
     * <p>
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * (Frequency array size is fixed at 26.)
     * <p>
     * Interview Takeaway:
     * <p>
     * Pattern:
     * "Longest substring with at most k modifications"
     * <p>
     * Formula to remember:
     * <p>
     * windowSize - maxFrequency <= k
     * <p>
     * This trick appears frequently in string sliding window problems.
     */
    private static int findLongestRepeatingCharSlidingWindow(String s, int k) {
        int maxLength = 0;
        int maxFrequency = 0;
        int left = 0, right = 0;
        int[] freq = new int[26];
        while (right < s.length()) {
            freq[s.charAt(right) - 'A']++;
            maxFrequency = Math.max(maxFrequency, freq[s.charAt(right) - 'A']);
            while ((right - left + 1) - maxFrequency > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }
        return maxLength;
    }

    /**
     * What we improved in this version:
     * <p>
     * In the previous optimal solution, we used a while loop
     * to shrink the window when it became invalid.
     * <p>
     * In this version, we replaced the while loop
     * with a single if condition.
     * <p>
     * Why this works:
     * The window expands only one character at a time (right++).
     * So at most one extra violation can occur in each step.
     * Shrinking the window by one position is enough
     * to gradually restore validity in future iterations.
     * <p>
     * What changed:
     * - Removed the inner while loop.
     * - Reduced unnecessary repeated shrinking.
     * - Made the logic slightly cleaner and more efficient in practice.
     * <p>
     * Time Complexity remains O(n).
     * Space Complexity remains O(1).
     */
    private static int findLongestRepeatingCharSlidingOptimal(String s, int k) {
        int maxLength = 0;
        int maxFrequency = 0;
        int left = 0, right = 0;
        int[] freq = new int[26];
        while (right < s.length()) {
            freq[s.charAt(right) - 'A']++;
            maxFrequency = Math.max(maxFrequency, freq[s.charAt(right) - 'A']);
            if ((right - left + 1) - maxFrequency > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
            right++;
        }
        return maxLength;
    }


}
