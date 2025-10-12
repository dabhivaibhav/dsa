package string.medium_problems;

import java.util.*;

/*
451. Sort Characters By Frequency
Given a string s, sort it in decreasing order based on the frequency of the characters. The frequency of a character is
the number of times it appears in the string.
Return the sorted string. If there are multiple answers, return any of them.

Example 1:
Input: s = "tree"
Output: "eert"
Explanation: 'e' appears twice while 'r' and 't' both appear once.
So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.

Example 2:
Input: s = "cccaaa"
Output: "aaaccc"
Explanation: Both 'c' and 'a' appear three times, so both "cccaaa" and "aaaccc" are valid answers.
Note that "cacaca" is incorrect, as the same characters must be together.

Example 3:
Input: s = "Aabb"
Output: "bbAa"
Explanation: "bbaA" is also a valid answer, but "Aabb" is incorrect.
Note that 'A' and 'a' are treated as two different characters.

Constraints:
            1 <= s.length <= 5 * 10^5
            s consists of uppercase and lowercase English letters and digits.
 */
public class SortCharacterByFrequency {

    public static void main(String[] args) {
        String s = "tree";
        String s1 = "cccaaa";
        String s2 = "Aabb";

        System.out.println(frequencySort(s).toString());
        System.out.println(frequencySort(s1).toString());
        System.out.println(frequencySort(s2).toString());
    }

    /**
     * What it does:
     * Rearranges the characters in the given string `s` so that they appear in descending order of frequency.
     * Characters that occur more frequently appear earlier in the output string.
     *
     * <p>
     * Why it works:
     * - To sort characters by their frequency, we first need to know how often each character occurs.
     * - Once we know the frequency, we can sort the characters based on their counts in descending order.
     * - Finally, we rebuild the string by repeating each character according to its frequency.
     *
     * <p>
     * How it works:
     * 1. **Count frequencies**:
     *    - Use a `HashMap<Character, Integer>` to store the frequency of each character.
     *    - For every character `ch` in the string, increment its count in the map.
     *
     * 2. **Sort by frequency (descending)**:
     *    - Convert the map’s entries into a list using `entrySet()`.
     *    - Sort the list using a comparator that orders entries by their value (frequency) in descending order.
     *
     * 3. **Build the result string**:
     *    - Create a `StringBuilder`.
     *    - For each entry in the sorted list, append the character repeated `frequency` times.
     *    - This ensures that characters with higher frequency appear first in the result.
     *
     * 4. Return the final string.
     *
     * <p>
     * Example:
     * Input: `"tree"`
     * Step 1: Frequency map → `{t=1, r=1, e=2}`
     * Step 2: Sorted by frequency → `[e=2, t=1, r=1]`
     * Step 3: Build → `"eetr"` or `"eert"` (both valid depending on order stability)
     * Output: `"eetr"`
     *
     * <p>
     * Time Complexity:
     * - O(n log n):
     *   - Counting characters → O(n)
     *   - Sorting based on frequency → O(k log k), where `k` ≤ number of unique characters (≤ n).
     *   - Overall dominated by sorting step.
     *
     * Space Complexity:
     * - O(n):
     *   - For the frequency map and result builder.
     *
     * <p>
     * Output:
     * - Returns a string with characters sorted by frequency in descending order.
     */
    private static String frequencySort(String s) {
        // Step 1: Count character frequencies
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : s.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }

        // Step 2: Sort by frequency (descending)
        List<Map.Entry<Character, Integer>> sortedEntries = freqMap.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .toList();

        // Step 3: Build result string
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : sortedEntries) {
            sb.append(String.valueOf(entry.getKey()).repeat(entry.getValue()));
        }

        return sb.toString();
    }


}
