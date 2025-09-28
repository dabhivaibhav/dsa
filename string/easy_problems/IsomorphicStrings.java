package string.easy_problems;

import java.util.HashMap;

/*
205. Isomorphic Strings

Given two strings s and t, determine if they are isomorphic.
Two strings s and t are isomorphic if the characters in s can be replaced to get t.
All occurrences of a character must be replaced with another character while preserving the order of characters.
No two characters may map to the same character, but a character may map to itself.

Example 1:
Input: s = "egg", t = "add"
Output: true
Explanation:The strings s and t can be made identical by:

Mapping 'e' to 'a'.
Mapping 'g' to 'd'.

Example 2:
Input: s = "foo", t = "bar"
Output: false
Explanation: The strings s and t can not be made identical as 'o' needs to be mapped to both 'a' and 'r'.

Example 3:
Input: s = "paper", t = "title"
Output: true

Constraints:
            1 <= s.length <= 5 * 10^4
            t.length == s.length
            s and t consist of any valid ascii character.
 */
public class IsomorphicStrings {


    public static void main(String[] args) {
        String s = "egg";
        String t = "add";
        String s1 = "foo";
        String t1 = "bar";

        System.out.println(ismorphicStringBruteforce(s, t));
        System.out.println(ismorphicStringBruteforce(s1, t1));
    }

    /**
     * What it does:
     * Determines whether two strings are isomorphic, meaning there exists a one-to-one
     * mapping between characters in the first string and characters in the second
     * string such that the original order of characters is preserved.
     *
     * <p>
     * Why it works:
     * - Two strings are isomorphic if:
     *   1. Each character in the first string maps to a single, consistent character in the second string.
     *   2. No two different characters map to the same character.
     * - Using two hash maps (`sToT` and `tToS`) ensures that the mapping is both
     *   consistent (no conflicts in mapping) and bijective (one-to-one).
     *
     * <p>
     * How it works:
     * 1. If the strings have different lengths, they cannot be isomorphic — return false immediately.
     * 2. Iterate through both strings simultaneously:
     *    - For each character pair `(c1, c2)`, check if `c1` is already mapped:
     *      - If yes, ensure it maps to `c2`. If not, the strings are not isomorphic.
     *      - If not mapped, store the mapping `c1 -> c2`.
     *    - Perform the reverse check for `c2 -> c1` to ensure the mapping is one-to-one.
     * 3. If no inconsistencies are found after the loop, return true.
     *
     * <p>
     * Example:
     * - s = "egg", t = "add"
     *   - 'e' → 'a', 'g' → 'd'
     *   - All mappings are consistent → true
     *
     * - s = "foo", t = "bar"
     *   - 'f' → 'b', 'o' → 'a', but second 'o' ≠ 'a' → false
     *
     * <p>
     * Time Complexity:
     * - O(n): Each character is processed once.
     *
     * Space Complexity:
     * - O(1) or O(k): At most 256 mappings (constant) since the input is limited to characters.
     *
     * <p>
     * Output:
     * - Returns true if the strings are isomorphic, false otherwise.
     */

    private static boolean ismorphicStringBruteforce(String s, String t) {
        if (s.length() != t.length()) return false;

        HashMap<Character, Character> sToT = new HashMap<>();
        HashMap<Character, Character> tToS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            // If s already mapped, check consistency
            if (sToT.containsKey(c1)) {
                if (sToT.get(c1) != c2) return false;
            } else {
                sToT.put(c1, c2);
            }

            // If t already mapped, check consistency
            if (tToS.containsKey(c2)) {
                if (tToS.get(c2) != c1) return false;
            } else {
                tToS.put(c2, c1);
            }
        }
        return true;
    }
}
