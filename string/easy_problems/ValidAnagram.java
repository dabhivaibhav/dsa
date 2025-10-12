package string.easy_problems;

/*
242. Valid Anagram
Given two strings s and t, return true if t is an anagram of s, and false otherwise.

Example 1:
Input: s = "anagram", t = "nagaram"
Output: true

Example 2:
Input: s = "rat", t = "car"
Output: false

Constraints:
            1 <= s.length, t.length <= 5 * 10^4
            s and t consist of lowercase English letters.
 */
public class ValidAnagram {

    public static void main(String[] args) {

        String s = "anagram";
        String t = "nagaram";
        String s1 = "rat";
        String t1 = "car";
        String s2 = "a";
        String t2 = "ab";

        System.out.println(validAnagramBruteForce(s,t));
        System.out.println(validAnagramBruteForce(s1,t1));
        System.out.println(validAnagramBruteForce(s2,t2));
    }

    /**
     * What it does:
     * Checks whether two strings, `s` and `t`, are anagrams of each other.
     * Two strings are anagrams if they contain exactly the same characters
     * in the same frequency, but possibly in a different order.
     *
     * Why it works:
     * - By using a frequency array of size 26 (for lowercase English letters),
     *   we can track how many times each character appears in both strings.
     * - For every character in `s`, we increment its frequency.
     *   For every character in `t`, we decrement its frequency.
     * - If both strings are true anagrams, the frequency of every character
     *   will end up being zero.
     *
     * How it works:
     * 1. If the lengths of `s` and `t` are not equal → return false immediately.
     *    (They can’t be anagrams if they have different lengths.)
     * 2. Create an integer array `freq` of size 26 initialized to 0.
     * 3. Loop through each character position `i` in both strings:
     *    - Increment the count for `s.charAt(i)` → `freq[s.charAt(i) - 'a']++`
     *    - Decrement the count for `t.charAt(i)` → `freq[t.charAt(i) - 'a']--`
     * 4. After the loop, check all values in `freq`:
     *    - If any value is non-zero, the strings differ in character frequency → return false.
     * 5. If all values are zero → the strings are anagrams → return true.
     *
     * Example:
     * - s = "anagram", t = "nagaram"
     *   - After processing, all frequencies balance to zero → true.
     *
     * - s = "rat", t = "car"
     *   - The count for 'r', 'a', 't', 'c' won’t all cancel → false.
     *
     * Time Complexity:
     * - O(n): We make one pass through both strings and one pass through the frequency array.
     *
     * Space Complexity:
     * - O(1): The `freq` array size is constant (26 for lowercase letters).
     *
     * Output:
     * - Returns true if `s` and `t` are anagrams, false otherwise.
     */

    private static boolean validAnagramBruteForce(String s, String t) {
        int[] freq = new int[26];
        if(s.length() != t.length()) return false;
        for(int i = 0 ; i < s.length(); i++){
            freq[s.charAt(i) - 'a']++;
            freq[t.charAt(i) - 'a']--;
        }
        for(int i : freq){
            if(i != 0) return false;
        }
        return true;
    }
}
