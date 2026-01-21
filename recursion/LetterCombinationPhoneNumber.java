package recursion;

import java.util.ArrayList;

/*
Leetcode 17: Letter Combinations of a Phone Number

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
Return the answer in any order. A mapping of digits to letters (just like on the telephone buttons) is given below.
Note that 1 does not map to any letters.
[1 - Nothing, 2 - ABC, 3 - DEF, 4 - GHI, 5 - JKL, 6 - MNO, 7 - PQRS, 8 - TUV, 9 - WXYZ]

Example 1:
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]

Example 2:
Input: digits = "2"
Output: ["a","b","c"]

Constraints:
            1 <= digits.length <= 4
            digits[i] is a digit in the range ['2', '9'].
 */
public class LetterCombinationPhoneNumber {
    private static final String[] MAPPING = {
            "", "", "abc", "def", "ghi",
            "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public static void main(String[] args) {

        String[][] predefinedLetters = {{}, {}, {"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}, {"j", "k", "l"},
                {"m", "n", "o"}, {"p", "q", "r", "s"}, {"t", "u", "v"}, {"w", "x", "y", "z"}};
        String digits = "23";
        ArrayList<String> combination = new ArrayList<>();
        letterCombinationsBruteForce(digits, combination);
        System.out.println(combination.toString());
        combination.clear();
        digits = "2";
        letterCombinationsBruteForce(digits, combination);
        System.out.println(combination.toString());
        combination.clear();
        digits = "123";
        letterCombinationsBruteForce(digits, combination);
        System.out.println(combination.toString());
        combination.clear();
        digits = "234";
        letterCombinationsBruteForce(digits, combination);
        System.out.println(combination.toString());
        combination.clear();
        letterCombinationsRecursive(0, digits, combination, new StringBuilder());
        System.out.println(combination.toString());

    }

    /**
     * letterCombinationsBruteForce
     * <p>
     * What it does:
     * Generates all possible letter combinations for a given digit string
     * using an iterative level-by-level expansion approach (similar to BFS).
     * Each digit expands the existing partial combinations by appending
     * all letters mapped to that digit.
     * <p>
     * Core Intuition:
     * - Think of this as building combinations one digit at a time.
     * - Start with an empty string as the base.
     * - For every digit, expand all existing combinations by appending
     *   each possible letter for that digit.
     * - After processing all digits, the list contains all valid combinations.
     * <p>
     * Why this approach works:
     * - Each digit represents one "level" of expansion.
     * - Each existing string branches into multiple new strings
     *   based on the digit’s letter mapping.
     * - This avoids recursion and simulates a breadth-first traversal
     *   of the combination tree.
     * <p>
     * Step-by-step explanation:
     * - If the input string is null or empty, return immediately.
     * - Add an empty string ("") to seed the combination list.
     * - For each digit in the input:
     *   - Convert the digit character to an integer.
     *   - Fetch the corresponding letters from the mapping.
     *   - Skip digits like 0 or 1 (they have no letter mapping).
     *   - Create a temporary list to store expanded combinations.
     *   - For each existing combination:
     *       - Append every possible letter of the current digit.
     *       - Store the newly formed strings in the temporary list.
     *   - Replace the old list with the newly expanded list.
     * <p>
     * Why a temporary list is required:
     * - Prevents mixing partially expanded combinations with new ones.
     * - Ensures clean level-by-level expansion.
     * <p>
     * Example:
     * Input: "23"
     * Start: [""]
     * After '2': ["a","b","c"]
     * After '3': ["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * <p>
     * Time Complexity:
     * - O(4^n), where n is the length of the digit string.
     * - Each digit can map to up to 4 letters (e.g., 7 and 9).
     * <p>
     * Space Complexity:
     * - O(4^n) for storing all combinations.
     * <p>
     * Edge Cases Handled:
     * - Empty input string.
     * - Digits containing 0 or 1 (ignored safely).
     * - Single-digit input.
     * <p>
     * Interview Insight:
     * - This is a non-recursive alternative to backtracking.
     * - Easy to reason about and debug.
     * - Mimics BFS / cartesian product expansion.
     */

    private static void letterCombinationsBruteForce(String digits, ArrayList<String> combination) {
        if (digits == null || digits.isEmpty()) return;

        combination.add(""); // Seed the list with an empty string

        for (int i = 0; i < digits.length(); i++) {
            int digit = digits.charAt(i) - '0';
            String letters = MAPPING[digit];

            // If digit is 0 or 1, they have no letters, so we skip expansion for this digit
            if (letters.isEmpty()) continue;

            ArrayList<String> tempNextLevel = new ArrayList<>();
            for (String alreadyBuilt : combination) {
                for (char c : letters.toCharArray()) {
                    tempNextLevel.add(alreadyBuilt + c);
                }
            }
            combination.clear();
            combination.addAll(tempNextLevel);
        }
    }

    /**
     * letterCombinationsRecursive
     * <p>
     * What it does:
     * Generates all possible letter combinations for a digit string
     * using recursive backtracking.
     * Each recursive call processes one digit and explores all
     * possible letter choices for that digit.
     * <p>
     * Core Intuition:
     * - Each digit corresponds to a "decision point".
     * - For each digit, try all possible letters.
     * - Build the combination step-by-step.
     * - Once all digits are processed, record the built string.
     * <p>
     * Why backtracking works here:
     * - The problem naturally forms a decision tree:
     *   each digit branches into multiple letters.
     * - Recursion explores all paths from root to leaf.
     * - Backtracking ensures we undo choices before trying new ones.
     * <p>
     * Step-by-step explanation:
     * - `index` represents the current digit being processed.
     * - `currStr` stores the partially built combination.
     * - Base Case:
     *   - When `index == digits.length()`, all digits are processed.
     *   - Add the current string to the result list.
     * - Recursive Case:
     *   - Fetch the letters mapped to the current digit.
     *   - For each letter:
     *       - Append it to `currStr`.
     *       - Recurse to process the next digit.
     *       - Remove the last character (backtracking step).
     * <p>
     * Why StringBuilder is used:
     * - Efficient mutable string construction.
     * - Avoids creating new string objects at every recursion step.
     * <p>
     * Example:
     * Input: "23"
     * Recursive paths:
     * a → d → "ad"
     * a → e → "ae"
     * ...
     * c → f → "cf"
     * <p>
     * Time Complexity:
     * - O(4^n), where n is the number of digits.
     * <p>
     * Space Complexity:
     * - O(n) for recursion stack depth.
     * - O(4^n) for storing the output combinations.
     * <p>
     * Edge Cases Handled:
     * - Single-digit input.
     * - Digits with no letters (0 or 1 produce no branches).
     * <p>
     * Interview Insight:
     * - This is the canonical solution expected in interviews.
     * - Demonstrates clean recursion + backtracking.
     * - Easier to generalize for similar problems (permutations, combinations).
     */

    private static void letterCombinationsRecursive(int index, String digits, ArrayList<String> combination, StringBuilder currStr) {

        if (index == digits.length()) {
            combination.add(currStr.toString());
            return;
        }
        String letters = MAPPING[digits.charAt(index) - '0'];
        for (char c : letters.toCharArray()) {
            currStr.append(c);
            letterCombinationsRecursive(index + 1, digits, combination, currStr);
            currStr.deleteCharAt(currStr.length() - 1);
        }
    }

}
