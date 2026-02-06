package collection.stack;

import java.util.Stack;

/*
Leetcode 20. Valid Parentheses
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
An input string is valid if:
Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Every close bracket has a corresponding open bracket of the same type.

Example 1:
Input: s = "()"
Output: true

Example 2:
Input: s = "()[]{}"
Output: true

Example 3:
Input: s = "(]"
Output: false

Example 4:
Input: s = "([])"
Output: true

Example 5:
Input: s = "([)]"
Output: false

Constraints:
            1 <= s.length <= 10^4
            s consists of parentheses only '()[]{}'.
 */
public class ValidParenthesis {

    public static void main(String[] args) {

        String s = "([])";
        System.out.println(isValid(s));
        String s1 = "([)]";
        System.out.println(isValid(s1));
        String s2 = "{[]}";
        System.out.println(isValid(s2));
        String s3 = "()[]{}";
        System.out.println(isValid(s3));

    }


    /**
     * What it does:
     * Checks whether a string of brackets is valid.
     * A valid string means every opening bracket is closed by the same type of bracket
     * and in the correct order.
     * <p>
     * Why a stack is used:
     * Brackets follow a nested structure.
     * The most recently opened bracket must be closed first.
     * This last in first out behavior matches a stack perfectly.
     * <p>
     * High level idea:
     * - Traverse the string from left to right.
     * - When an opening bracket is found, remember what closing bracket is expected.
     * - When a closing bracket is found, verify it matches the most recent expectation.
     * - If at any point the order or type is wrong, the string is invalid.
     * <p>
     * Explanation of early checks:
     * - If the string length is odd, it can never be valid because brackets come in pairs.
     * - If the first character is a closing bracket, the string is invalid immediately
     * because there is no opening bracket before it.
     * <p>
     * Explanation of stack usage:
     * Instead of pushing opening brackets, this solution pushes the expected closing bracket.
     * This simplifies comparison later.
     * <p>
     * Step by step explanation of the loop:
     * - Iterate through each character in the string.
     * - If the character is an opening bracket:
     * - '(' means we expect ')' later, so push ')'.
     * - '[' means we expect ']' later, so push ']'.
     * - '{' means we expect '}' later, so push '}'.
     * - If the character is a closing bracket:
     * - First check if the stack is empty.
     * An empty stack means there is no matching opening bracket.
     * - Pop the top element from the stack.
     * - If the popped value does not match the current character,
     * then the brackets are mismatched and the string is invalid.
     * <p>
     * Why pushing expected closing brackets works:
     * The stack always stores what closing bracket should appear next.
     * When a closing bracket is encountered, it must match the top of the stack.
     * This guarantees both correct type and correct nesting order.
     * <p>
     * Final validation:
     * After processing the entire string, the stack must be empty.
     * If it is not empty, it means some opening brackets were never closed.
     * <p>
     * Example walkthrough:
     * Input: "([])"
     * '(' -> push ')'
     * '[' -> push ']'
     * ']' -> pop ']' and match
     * ')' -> pop ')' and match
     * Stack is empty -> valid
     * <p>
     * Example failure:
     * Input: "([)]"
     * '(' -> push ')'
     * '[' -> push ']'
     * ')' -> expected ']' but got ')'
     * Mismatch -> invalid
     * <p>
     * Time complexity:
     * O(n) where n is the length of the string.
     * Each character is processed once.
     * <p>
     * Space complexity:
     * O(n) in the worst case when all characters are opening brackets.
     * <p>
     * Edge cases handled:
     * - Odd length strings
     * - Strings starting with a closing bracket
     * - Nested brackets
     * - Incorrect ordering
     * - Extra opening brackets left at the end
     * <p>
     * Interview explanation summary:
     * This problem uses a stack because brackets are nested.
     * By pushing expected closing brackets, we simplify comparison logic.
     * Any mismatch or leftover elements in the stack means the string is invalid.
     */
    private static boolean isValid(String s) {

        //if we have a string of odd length, it cannot be valid so it should return false
        if (s.length() % 2 != 0) return false;

        if (s.charAt(0) == ')' || s.charAt(0) == ']' || s.charAt(0) == '}') return false;

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {

            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else {
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
