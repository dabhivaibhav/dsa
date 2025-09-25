package string.easy_problems;

/*
14. Longest Common Prefix
Write a function to find the longest common prefix string amongst an array of strings.
If there is no common prefix, return an empty string "".

Example 1:
Input: strs = ["flower","flow","flight"]
Output: "fl"

Example 2:
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.

Constraints:
            1 <= strs.length <= 200
            0 <= strs[i].length <= 200
            strs[i] consists of only lowercase English letters if it is non-empty.
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {

        String[] strs = {"flower", "flow", "flight"};
        String[] strs1 = {"dog", "racecar", "car"};
        String[] strs2 = {"aaa", "aaa"};

        System.out.println(longestCommonPrefixBruteForce(strs));


    }

    /**
     * What it does:
     * Finds the longest common prefix among all strings in the array by
     * taking the first string as a candidate prefix and progressively
     * shrinking it until it is a prefix of every other string.
     *
     * <p>
     * Why it works:
     * - Any common prefix must be a prefix of the first string.
     * - If the current candidate prefix is not at the start of some string,
     * removing its last character is the only way to move toward a valid
     * common prefix.
     * - Repeating this check-and-shrink process across all strings yields the
     * longest prefix shared by all of them.
     *
     * <p>
     * How it works:
     * 1) Edge case: if the array is empty, return "".
     * 2) Set `prefix = strs[0]`.
     * 3) For each subsequent string `strs[i]`:
     * - While `strs[i].indexOf(prefix) != 0` (i.e., `prefix` is not a prefix of `strs[i]`):
     * * Shrink `prefix` by one character from the end: `prefix = prefix.substring(0, prefix.length() - 1)`.
     * * If `prefix` becomes empty, return "" immediately.
     * 4) After checking all strings, return `prefix`.
     *
     * <p>
     * Examples:
     * - ["flower","flow","flight"] → "fl"
     * Start "flower" → not a prefix of "flow" → shrink to "flow" → OK;
     * compare with "flight": shrink "flow"→"flo"→"fl" → OK → answer "fl".
     * - ["dog","racecar","car"] → ""
     * "dog" is not a prefix of "racecar": shrink "do"→"d"→"" → return "".
     *
     * <p>
     * Time Complexity:
     * - O(S), where S is the total number of characters across all strings.
     * The candidate prefix only ever shrinks, and each character is considered
     * at most a constant number of times across the loop.
     * <p>
     * Space Complexity:
     * - O(1) auxiliary space (ignoring the transient String objects created
     * by substring); only a few variables are used.
     *
     * <p>
     * Output:
     * Returns the longest common prefix string shared by all input strings,
     * or the empty string if no common prefix exists.
     */

    private static String longestCommonPrefixBruteForce(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                System.out.println("Prefix: " + prefix + " index of prefix " + strs[i].indexOf(prefix));
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }


}
