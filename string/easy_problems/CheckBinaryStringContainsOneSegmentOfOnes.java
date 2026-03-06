package string.easy_problems;

/*
Leetcode: 1784. Check if Binary String Has at Most One Segment of Ones

Given a binary string s without leading zeros, return true if s contains at most one contiguous segment of ones.
Otherwise, return false.

Example 1:
Input: s = "1001"
Output: false
Explanation: The ones do not form a contiguous segment.

Example 2:
Input: s = "110"
Output: true

Constraints:
            1 <= s.length <= 100
            s[i] is either '0' or '1'.
            s[0] is '1'.
 */
public class CheckBinaryStringContainsOneSegmentOfOnes {

    public static void main(String[] args) {
        String s = "1001";
        System.out.println(checkOnesSegment(s));
        String s1 = "110";
        System.out.println(checkOnesSegment(s1));
    }

    /*
    THE "ISLAND DETECTOR" PATTERN

    THE CONCEPT:
    If a string starts with a '1' and can only have ONE
    segment of ones, it means we can NEVER transition
    from a '0' back to a '1'.

    The sequence "01" is the smoking gun.
    If it exists, you have at least two segments.
    If it doesn't, you only have the initial one.

    EFFICIENCY:
    Your manual loop and the .contains("01") both run
    in O(N) time. Your manual loop is actually slightly
    faster because it stops the moment it finds a '1'
    after a '0'.
    */

    /**
     * checkOnesSegment(String s)
     * <p>
     * What this method does:
     * <p>
     * Determines whether the binary string contains
     * at most one contiguous segment of '1's.
     * <p>
     * If the string contains more than one group
     * of consecutive ones, the method returns false.
     * Otherwise, it returns true.
     * <p>
     * Core Idea:
     * <p>
     * Since the string starts with '1' (given by the constraint),
     * a valid string can only look like:
     * <p>
     * 111...11000...0
     * <p>
     * That means:
     * <p>
     * - Ones may appear first
     * - Zeros may follow
     * - But once zeros begin, ones must never appear again
     * <p>
     * If we ever see a '1' after a '0',
     * it means a second segment of ones exists.
     * <p>
     * Thought Process:
     * <p>
     * Traverse the string from left to right.
     * <p>
     * Track whether we have already encountered
     * a '0' in the string.
     * <p>
     * If we see:
     * <p>
     * 1 → continue normally
     * <p>
     * When the first '0' appears,
     * mark that a zero segment has started.
     * <p>
     * If any '1' appears after that,
     * it means a second segment of ones exists,
     * so we return false.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Initialize a boolean variable:
     * <p>
     * previousZero = false
     * <p>
     * This tells us whether we have seen a zero.
     * <p>
     * Step 2:
     * <p>
     * Traverse the string character by character.
     * <p>
     * Step 3:
     * <p>
     * If we encounter a '0',
     * set previousZero = true.
     * <p>
     * Step 4:
     * <p>
     * If previousZero is true
     * and we encounter a '1',
     * this means a second segment of ones exists.
     * <p>
     * Return false immediately.
     * <p>
     * Step 5:
     * <p>
     * If the loop completes without finding
     * a '1' after a '0',
     * then the string contains at most
     * one segment of ones.
     * <p>
     * Return true.
     * <p>
     * Example 1:
     * <p>
     * s = "1001"
     * <p>
     * Traverse:
     * <p>
     * 1 → OK
     * 0 → zero encountered
     * 0 → still zero segment
     * 1 → one appears after zero
     * <p>
     * This means two segments of ones exist.
     * <p>
     * Output → false
     * <p>
     * Example 2:
     * <p>
     * s = "110"
     * <p>
     * Traverse:
     * <p>
     * 1 → OK
     * 1 → still first segment
     * 0 → zeros begin
     * <p>
     * No ones appear after the zero.
     * <p>
     * Output → true
     * <p>
     * <p>
     * Complexity:
     * <p>
     * Let n = length of the string.
     * <p>
     * Time Complexity:
     * <p>
     * O(n)
     * <p>
     * We scan the string once.
     * <p>
     * Space Complexity:
     * <p>
     * O(1)
     * <p>
     * Only a boolean variable is used.
     * <p>
     * Interview Takeaway:
     * <p>
     * When a problem asks about
     * contiguous segments in a binary string,
     * look for forbidden transitions.
     * <p>
     * Here the illegal transition is:
     * <p>
     * 0 → 1
     * <p>
     * Detecting that single pattern
     * is enough to determine the answer.
     */
    private static boolean checkOnesSegment(String s) {
        boolean previousZero = false;
        for (int i = 0; i < s.length(); i++) {
            if (!previousZero && s.charAt(i) == '1') continue;

            if (s.charAt(i) == '0') {
                previousZero = true;
            }
            if (previousZero && s.charAt(i) == '1') {
                return false;
            }
        }
        return true;
    }
}
