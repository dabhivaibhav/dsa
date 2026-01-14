package recursion;

import java.util.ArrayList;
import java.util.List;

/*
Generate Binary Strings Without Consecutive 1s

Given an integer n, return all binary strings of length n that do not contain consecutive 1s. Return the result in lexicographically increasing order.
A binary string is a string consisting only of characters '0' and '1'.

Example 1
Input: n = 3
Output: ["000", "001", "010", "100", "101"]
Explanation: All strings are of length 3 and do not contain consecutive 1s.

Example 2
Input: n = 2
Output: ["00", "01", "10"]

Constraints:
            1 <= n <= 20
 */
public class GenerateAllBinaryString {

    public static void main(String[] args) {

        System.out.println(generateBinaryStringsBruteForce(3));
        System.out.println(generateBinaryStringOptimal(3));
    }

    /**
     * generateBinaryStringsBruteForce
     * <p>
     * What it does:
     * Generates all binary strings of length `n` using a brute-force approach and
     * returns only those strings that do NOT contain consecutive `'1'` characters.
     * The method systematically enumerates all possible `2^n` binary combinations,
     * filters invalid ones, and collects the valid results.
     * <p>
     * Intuition (step-by-step explanation):
     * - A binary string of length `n` has exactly `2^n` possible combinations.
     * - Instead of building strings recursively, this method treats each number
     * from `0` to `2^n - 1` as a binary representation.
     * - Each number is converted into its binary form using `Integer.toBinaryString`.
     * - Since binary representations may be shorter than `n`, leading zeros are added
     * to ensure every string has exactly length `n`.
     * - Once a binary string is formed, it is scanned character-by-character to check
     * whether it contains consecutive `'1'`s.
     * - Only strings that pass this validation are added to the result list.
     * <p>
     * Why each line matters:
     * - `limit *= 2`: Computes `2^n`, which represents the total number of binary strings.
     * - `Integer.toBinaryString(i)`: Converts the current number into its binary form.
     * - Left-padding with zeros: Ensures uniform string length of `n`, which is required
     * for consistent validation.
     * - The validation loop checks adjacent characters to detect `"11"` patterns.
     * - `valid` flag allows early termination of checking when an invalid pattern is found.
     * <p>
     * Edge Cases Handled:
     * - `n = 0`: Returns a list containing a single empty string.
     * - `n = 1`: Returns `["0", "1"]` since no consecutive ones are possible.
     * - Small `n`: Works correctly due to fixed-length padding logic.
     * <p>
     * Example:
     * Input: n = 3
     * All possible strings: ["000", "001", "010", "011", "100", "101", "110", "111"]
     * Valid output: ["000", "001", "010", "100", "101"]
     * <p>
     * Time Complexity:
     * - O(2^n * n):
     * - There are `2^n` binary numbers.
     * - Each number requires up to `n` operations for padding and validation.
     * <p>
     * Space Complexity:
     * - O(2^n * n) in the worst case:
     * - Stores up to `2^n` strings, each of length `n`.
     * <p>
     * Limitations:
     * - Inefficient for large `n` due to exponential growth.
     * - Generates many invalid strings that are later discarded.
     * - A recursive backtracking approach would be more optimal.
     */
    public static List<String> generateBinaryStringsBruteForce(int n) {
        List<String> result = new ArrayList<>();
        int limit = 1;
        for (int i = 0; i < n; i++) {
            limit *= 2;
        }

        for (int i = 0; i < limit; i++) {
            String binary = Integer.toBinaryString(i);

            // Left-pad with zeros to make length exactly n
            if (binary.length() < n) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < n - binary.length(); k++) sb.append('0');
                sb.append(binary);
                binary = sb.toString();
            }

            // Filter out strings containing consecutive '1's
            boolean valid = true;
            for (int j = 0; j < n - 1; j++) {
                if (binary.charAt(j) == '1' && binary.charAt(j + 1) == '1') {
                    valid = false;
                    break;
                }
            }
            if (valid) result.add(binary);
        }
        return result;
    }

    /**
     * Generates all binary strings of length `target` that do NOT contain consecutive '1's,
     * using an optimized recursive (backtracking) approach.
     *
     * Initial thinking (brute force):
     * - Generate all 2^n binary strings.
     * - Pad them to length n.
     * - Scan each string and discard those containing "11".
     *
     * Problem with brute force:
     * - Many invalid strings are generated and then thrown away.
     * - Extra work is done in padding and filtering.
     *
     * Key improvement insight:
     * - The constraint "no consecutive 1s" is local and depends only on the previous character.
     * - Invalid strings can be avoided entirely instead of being removed later.
     *
     * Technique chosen:
     * - Backtracking / recursion to build strings character by character.
     * - Prune any branch that would create consecutive '1's.
     *
     * How recursion works:
     * - Build the string from left to right.
     * - At each position:
     *   - '0' can always be added.
     *   - '1' can be added only if the previous character was not '1'.
     *
     * Lexicographic order:
     * - Always explore '0' before '1', which naturally produces sorted output.
     *
     * Edge cases handled:
     * - target = 1 produces ["0", "1"].
     * - No string ever contains "11" because invalid paths are skipped.
     *
     * Time Complexity:
     * - O(V * target), where V is the number of valid strings generated.
     *
     * Space Complexity:
     * - O(target) recursion depth, plus space to store the result.
     */
    private static List<String> generateBinaryStringOptimal(int target) {
        List<String> result = new ArrayList<>();
        generateBinaryStringRecursive(target, 0, false, new StringBuilder(), result);
        return result;
    }

    /**
     * Recursive helper function that constructs valid binary strings.
     *
     * Parameters:
     * - target: required length of the binary string.
     * - position: number of characters built so far.
     * - lastWasOne: true if the previously added character was '1'.
     * - currentString: mutable string being built.
     * - result: list that stores completed valid strings.
     *
     * Recursion flow:
     * - If position equals target, the string is complete and added to result.
     * - Append '0', recurse, then backtrack.
     * - Append '1' only if lastWasOne is false, recurse, then backtrack.
     *
     * Backtracking ensures the same StringBuilder is reused efficiently
     * without creating unnecessary intermediate strings.
     */
    private static void generateBinaryStringRecursive(int target, int position, boolean lastWasOne, StringBuilder currentString, List<String> result) {
        if (position == target) {
            result.add(currentString.toString());
            return;
        }
        currentString.append("0");
        generateBinaryStringRecursive(target, position + 1, false, currentString, result);
        currentString.deleteCharAt(currentString.length() - 1);

        if (!lastWasOne) {
            currentString.append("1");
            generateBinaryStringRecursive(target, position + 1, true, currentString, result);
            currentString.deleteCharAt(currentString.length() - 1);
        }

    }
}
