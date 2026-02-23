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
        System.out.println(countSubstringSlidingWindow(s));
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


    /*
    THE REAR-VIEW MIRROR MENTAL MODEL
    Imagine you are driving forward (index i) and looking in your rear-view mirror
    to keep track of the last time you saw three specific landmarks: 'a', 'b', and 'c'.

    THE PROBLEM
    We need to count all substrings that contain at least one of each.
    Checking every single substring is too slow.

    THE LOGIC
    At every step 'i', we update the "Last Seen" position of the current character.
    If we have seen all three at least once, we look for the "Weakest Link."

    THE WEAKEST LINK (The Anchor)
    The Weakest Link is the landmark that is FURTHEST behind us (Math.min of lastSeen).
    Let's call this position 'anchor'.

    THE AHA! MOMENT
    If the window from 'anchor' to 'i' contains all three, then any substring that
    starts BEFORE the anchor and ends at 'i' MUST also contain all three.
    Example: if anchor is at index 2, then substrings starting at 0, 1, and 2
    (ending at i) are all valid.
    Total valid starts = anchor + 1.

    SLIDING WINDOW CONNECTION
    In a normal sliding window, you "push" the left pointer forward.
    In this model, the 'anchor' IS your left pointer. It's the rightmost possible
    starting point that keeps the window valid.
    */

    /**
     * countSubstringSlidingWindow(String s)
     * <p>
     * What this method does:
     * Counts the number of substrings that contain
     * at least one 'a', one 'b', and one 'c'
     * using an O(n) optimized approach.
     * <p>
     * Core Insight:
     * <p>
     * Instead of generating all substrings,
     * we track the most recent position (last seen index)
     * of each character: 'a', 'b', and 'c'.
     * <p>
     * At every index i:
     * If all three characters have appeared at least once,
     * we can instantly calculate how many valid substrings
     * end at index i.
     * <p>
     * Key Idea:
     * <p>
     * For each position i:
     * <p>
     * lastSeen[0] → last index of 'a'
     * lastSeen[1] → last index of 'b'
     * lastSeen[2] → last index of 'c'
     * <p>
     * If all are not -1,
     * then the earliest occurrence among them determines
     * the smallest valid starting point.
     * <p>
     * Why min(lastSeen) Works:
     * <p>
     * Suppose:
     * lastSeen = [5, 3, 7]
     * <p>
     * The smallest index is 3.
     * <p>
     * That means:
     * - Any substring ending at current index i
     * - And starting from index 0 to 3
     * - Will contain at least one 'a', 'b', and 'c'.
     * <p>
     * Number of such substrings:
     * <p>
     * minLastSeen + 1
     * <p>
     * So at each index i:
     * <p>
     * count += (minLastSeen + 1)
     * <p>
     * Example:
     * <p>
     * s = "abcabc"
     * <p>
     * At i = 2 ("abc"):
     * lastSeen = [0,1,2]
     * min = 0
     * count += 1
     * <p>
     * At i = 3:
     * lastSeen = [3,1,2]
     * min = 1
     * count += 2
     * <p>
     * And so on...
     * <p>
     * Complexity:
     * <p>
     * Time Complexity: O(n)
     * We traverse the string once.
     * <p>
     * Space Complexity: O(1)
     * Only a fixed-size array of length 3 is used.
     * <p>
     * Interview Takeaway:
     * <p>
     * This is a beautiful example of turning
     * a brute-force substring generation problem
     * into a mathematical counting problem.
     * <p>
     * Instead of checking every substring,
     * we count how many valid starting points
     * exist for each ending index.
     * <p>
     * That shift in perspective reduces O(n^3)
     * all the way down to O(n).
     */

    private static int countSubstringSlidingWindow(String s) {
        // Array to store the last seen position of 'a', 'b', and 'c'
        int[] lastSeen = {-1, -1, -1};
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            // Update the last seen position of the current character
            lastSeen[s.charAt(i) - 'a'] = i;

            // The window is only valid if we have seen all three characters at least once
            if (lastSeen[0] != -1 && lastSeen[1] != -1 && lastSeen[2] != -1) {
                // The smallest index among the three is the start of our
                // "shortest" valid substring ending at i.
                int minLastSeen = Math.min(lastSeen[0], Math.min(lastSeen[1], lastSeen[2]));

                // Every index from 0 to minLastSeen can be a starting point
                count += (minLastSeen + 1);
            }
        }
        return count;
    }

}
