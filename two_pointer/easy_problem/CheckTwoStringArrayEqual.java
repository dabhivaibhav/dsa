package two_pointer.easy_problem;

/*
Leetcode: 1662. Check If Two String Arrays are Equivalent

Given two string arrays word1 and word2, return true if the two arrays represent the same string, and false otherwise.
A string is represented by an array if the array elements concatenated in order forms the string.

Example 1:
Input: word1 = ["ab", "c"], word2 = ["a", "bc"]
Output: true
Explanation:
word1 represents string "ab" + "c" -> "abc"
word2 represents string "a" + "bc" -> "abc"
The strings are the same, so return true.

Example 2:
Input: word1 = ["a", "cb"], word2 = ["ab", "c"]
Output: false

Example 3:
Input: word1  = ["abc", "d", "defg"], word2 = ["abcddefg"]
Output: true

Constraints:
            1 <= word1.length, word2.length <= 10^3
            1 <= word1[i].length, word2[i].length <= 10^3
            1 <= sum(word1[i].length), sum(word2[i].length) <= 10^3
            word1[i] and word2[i] consist of lowercase letters.
 */

public class CheckTwoStringArrayEqual {

    public static void main(String[] args) {

        String[] word1 = {"ab", "c"};
        String[] word2 = {"a", "bc"};
        System.out.println(arrayStringsAreEqualBruteForce(word1, word2));
        System.out.println(arrayStringAreEqualOptimized(word1, word2));

        String[] word11 = {"a", "cb"};
        String[] word22 = {"ab", "c"};
        System.out.println(arrayStringsAreEqualBruteForce(word11, word22));
        System.out.println(arrayStringAreEqualOptimized(word11, word22));

        String[] word111 = {"abc", "d", "defg"};
        String[] word222 = {"abcddefg"};
        System.out.println(arrayStringsAreEqualBruteForce(word111, word222));
        System.out.println(arrayStringAreEqualOptimized(word111, word222));

    }

    /**
     * arrayStringsAreEqualBruteForce(String[] word1, String[] word2)
     * <p>
     * What this method does:
     * <p>
     * Checks if two string arrays represent the same string.
     * <p>
     * It concatenates all elements of each array into a single string
     * and compares the resulting strings for equality.
     * <p>
     * Core Idea:
     * <p>
     * If joining all strings in order from word1 produces the same string
     * as joining all strings from word2, the arrays are equivalent.
     * <p>
     * Thought Process:
     * <p>
     * Two arrays are equivalent if:
     * <p>
     * concat(word1) == concat(word2)
     * <p>
     * Brute force approach:
     * <p>
     * 1. Use a StringBuilder to append all elements of word1.
     * 2. Use a StringBuilder to append all elements of word2.
     * 3. Convert both StringBuilders to String.
     * 4. Compare the two Strings using equals().
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Build string for word1
     * <p>
     * StringBuilder sb1 = new StringBuilder();
     * <p>
     * for each str in word1 → sb1.append(str)
     * <p>
     * Step 2: Build string for word2
     * <p>
     * StringBuilder sb2 = new StringBuilder();
     * <p>
     * for each str in word2 → sb2.append(str)
     * <p>
     * Step 3: Compare
     * <p>
     * sb1.toString().equals(sb2.toString())
     * <p>
     * Returns true if identical, false otherwise.
     * <p>
     * Example:
     * <p>
     * word1 = ["ab", "c"]
     * word2 = ["a", "bc"]
     * <p>
     * concat(word1) = "abc"
     * concat(word2) = "abc"
     * <p>
     * Returns true
     * <p>
     * Complexity:
     * <p>
     * Let n = sum of lengths of word1 strings
     * Let m = sum of lengths of word2 strings
     * <p>
     * Time Complexity: O(n + m)
     * <p>
     * Space Complexity: O(n + m)
     * (new strings created for concatenation)
     * <p>
     * Interview Takeaway:
     * <p>
     * This solution is simple and intuitive,
     * but it requires extra memory for concatenation.
     * <p>
     * Optimizations are possible by comparing characters
     * one by one without creating new strings.
     *
     */
    private static boolean arrayStringsAreEqualBruteForce(String[] word1, String[] word2) {
        StringBuilder sb1 = new StringBuilder();
        for (String str : word1) {
            sb1.append(str);
        }
        StringBuilder sb2 = new StringBuilder();
        for (String str : word2) {
            sb2.append(str);
        }

        return sb1.toString().equals(sb2.toString());
    }

    /**
     * arrayStringAreEqualOptimized(String[] word1, String[] word2)
     * <p>
     * What this method does:
     * <p>
     * Checks if two string arrays represent the same string
     * without concatenating the arrays into full strings.
     * <p>
     * Compares characters one by one using multiple pointers
     * to save memory and achieve efficient linear scan.
     * <p>
     * Core Idea:
     * <p>
     * Instead of joining the arrays into full strings,
     * iterate through characters of both arrays simultaneously.
     * <p>
     * If at any position the characters differ → return false.
     * <p>
     * If we reach the end of both arrays together → return true.
     * <p>
     * Thought Process:
     * <p>
     * Use four pointers:
     * <p>
     * w1 → index of current string in word1
     * i  → index of current character in word1[w1]
     * w2 → index of current string in word2
     * j  → index of current character in word2[w2]
     * <p>
     * While both arrays have characters:
     * <p>
     * 1. Compare word1[w1][i] with word2[w2][j]
     * 2. If not equal → return false
     * 3. Advance character pointers
     * 4. If pointer reaches end of current string → move to next string
     * <p>
     * At the end, check both arrays finished simultaneously.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1: Initialize pointers
     * <p>
     * w1 = 0, i = 0
     * w2 = 0, j = 0
     * <p>
     * Step 2: Traverse arrays
     * <p>
     * while (w1 < word1.length AND w2 < word2.length):
     * <p>
     * if word1[w1].charAt(i) != word2[w2].charAt(j):
     * return false
     * <p>
     * Advance i and j
     * <p>
     * If i reaches end of word1[w1] → w1++, i = 0
     * If j reaches end of word2[w2] → w2++, j = 0
     * <p>
     * Step 3: Final check
     * <p>
     * Both arrays must be fully traversed:
     * <p>
     * return w1 == word1.length && w2 == word2.length
     * <p>
     * Example:
     * <p>
     * word1 = ["abc", "d", "defg"]
     * word2 = ["abcddefg"]
     * <p>
     * Iteration:
     * 'a' vs 'a' → match
     * 'b' vs 'b' → match
     * 'c' vs 'c' → match
     * 'd' vs 'd' → match
     * 'd' vs 'd' → match
     * 'e' vs 'e' → match
     * 'f' vs 'f' → match
     * 'g' vs 'g' → match
     * <p>
     * All characters match → return true
     * <p>
     * Complexity:
     * <p>
     * Let n = total characters in word1
     * Let m = total characters in word2
     * <p>
     * Time Complexity: O(n + m)
     * <p>
     * Space Complexity: O(1)
     * (no extra strings created)
     * <p>
     * Interview Takeaway:
     * <p>
     * This optimized approach avoids building large strings,
     * saving memory for large inputs.
     * <p>
     * Pointer-based character comparison is a common technique
     * for array/string equivalence problems.
     * <p>
     * It demonstrates careful traversal and edge-case handling
     * in interview settings.
     *
     */
    private static boolean arrayStringAreEqualOptimized(String[] word1, String[] word2) {

        int w1 = 0, i = 0; // Word index and Char index for word1
        int w2 = 0, j = 0; // Word index and Char index for word2

        while (w1 < word1.length && w2 < word2.length) {
            // Compare current characters
            if (word1[w1].charAt(i) != word2[w2].charAt(j)) {
                return false;
            }

            // Move pointers for word1
            i++;
            if (i == word1[w1].length()) {
                w1++; // Move to next string
                i = 0; // Reset char index
            }

            // Move pointers for word2
            j++;
            if (j == word2[w2].length()) {
                w2++; // Move to next string
                j = 0; // Reset char index
            }
        }
        // Both should reach the end of their arrays at the same time
        return w1 == word1.length && w2 == word2.length;
    }
}
