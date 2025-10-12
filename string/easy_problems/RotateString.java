package string.easy_problems;

/*
Leetcode: 796. Rotate String
Given two strings s and goal, return true if and only if s can become goal after some number of shifts on s.
A shift on s consists of moving the leftmost character of s to the rightmost position.
For example, if s = "abcde", then it will be "bcdea" after one shift.

Example 1:
Input: s = "abcde", goal = "cdeab"
Output: true

Example 2:
Input: s = "abcde", goal = "abced"
Output: false


Constraints:
            1 <= s.length, goal.length <= 100
            s and goal consist of lowercase English letters.
 */
public class RotateString {

    public static void main(String[] args) {

        String s = "abcde";
        String goal = "cdeab";
        String s1 = "abcde";
        String goal1 = "abced";

        System.out.println(checkRotateStringBruteForce(s,goal));
        System.out.println(checkRotateStringBruteForce(s1,goal1));

        System.out.println(checkRotateStringOptimal(s,goal));
        System.out.println(checkRotateStringOptimal(s1,goal1));
    }

    /**
     * What it does:
     * Determines whether one string (`goal`) is a rotation of another string (`original`).
     * A rotation means that by shifting characters of `original` circularly,
     * you can obtain `goal`.
     *
     * Why it works:
     * - A rotated string is formed by moving the first `i` characters of `original`
     *   to the end while maintaining their order.
     * - For example, rotating `"abcde"` by 2 positions results in `"cdeab"`.
     * - To verify if `goal` can be formed by any such rotation, we simulate
     *   every possible rotation of `original` and compare it with `goal`.
     *
     * How it works:
     * 1. Loop through all possible rotation points `i` (from 0 to `original.length() - 1`).
     * 2. For each `i`, create a rotated version of the string:
     *    - `temp = original.substring(i) + original.substring(0, i)`
     *      → moves the first `i` characters to the end.
     * 3. Compare the rotated string (`temp`) with `goal`.
     *    - If any match is found, return `true`.
     * 4. If the loop completes with no match, return `false`.
     *
     * Example:
     * - `original = "abcde"`, `goal = "cdeab"`
     *   - Rotation 1: `"bcdea"` → no match
     *   - Rotation 2: `"cdeab"` → matches → return true
     *
     * - `original = "abc"`, `goal = "acb"`
     *   - No rotation matches → return false
     *
     * Time Complexity:
     * - O(n²): For each rotation (O(n)), a new string is built (O(n)) and compared.
     *
     * Space Complexity:
     * - O(n): Temporary string (`temp`) created during each rotation.
     *
     * Output:
     * - Returns true if `goal` is a rotation of `original`, false otherwise.
     */

    private static boolean checkRotateStringBruteForce(String original, String goal) {

        for(int i = 0 ; i < original.length(); i++) {
            String temp = original.substring(i) + original.substring(0,i);
            if(temp.equals(goal)) return true;
        }

        return false;
    }

    /**
     * What it does:
     * Checks whether the string `goal` is a rotation of `original` using a
     * constant-time (amortized) string-containment trick: if `goal` is a rotation
     * of `original`, then `goal` must appear as a substring of `original + original`.
     *
     * <p>
     * Why it works:
     * - A rotation moves some prefix of `original` to its end while preserving order.
     *   For example, rotating "abcde" by 2 produces "cdeab".
     * - If you concatenate `original` with itself ("abcdeabcde"), every possible
     *   rotation of `original` appears as a contiguous substring of that doubled string.
     * - Therefore, `goal` is a rotation of `original` ⇔ `goal` is contained in `original + original`.
     * - We must also require the two strings have equal length (rotations don't change length).
     *
     * <p>
     * How it works:
     * 1. Check lengths: if `original.length() != goal.length()`, return false.
     * 2. Form the doubled string: `original + original`.
     * 3. Use the standard `String.contains` to test whether `goal` appears in the doubled string.
     *    - If yes → `goal` is a rotation → return true.
     *    - Otherwise → return false.
     *
     * <p>
     * Examples:
     * - original = "abcde", goal = "cdeab"
     *   - "abcdeabcde".contains("cdeab") → true
     * - original = "abc", goal = "acb"
     *   - "abcabc".contains("acb") → false
     *
     * <p>
     * Time Complexity:
     * - O(n) average (depends on the underlying substring search implementation;
     *   Java's `contains` typically uses a linear-time algorithm such as Knuth–Morris–Pratt or similar,
     *   so the containment check across a length-2n string is O(n) in practice).
     *
     * Space Complexity:
     * - O(n): due to creating `original + original` (a new string of length 2n).
     *
     * <p>
     * How to arrive at this idea after the brute-force solution:
     * - Brute force simulates every rotation explicitly by slicing and comparing:
     *   `substring(i) + substring(0,i)` for i = 0..n-1.
     * - Observing those rotations shows they are *all* substrings of `original + original`.
     *   Example: for original="abcde", the rotations "abcde","bcdea","cdeab",... are inside "abcdeabcde".
     * - That observation removes the need to build each rotated string individually:
     *   just test containment once on the doubled string, after confirming lengths match.
     * - In short: brute force reveals the pattern; the pattern suggests the concatenation trick.
     *
     * <p>
     * Output:
     * - Returns true if `goal` is a rotation of `original`, false otherwise.
     */
    private static boolean checkRotateStringOptimal(String original, String goal) {
        return   (original.length() == goal.length() &&  (original + original).contains(goal));
    }
}
