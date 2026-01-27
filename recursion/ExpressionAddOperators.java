package recursion;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 282. Expression Add Operators

Given a string num that contains only digits and an integer target, return all possibilities to insert the binary
operators '+', '-', and/or '*' between the digits of num so that the resultant expression evaluates to the target value.
Note that operands in the returned expressions should not contain leading zeros.
Note that a number can contain multiple digits.

Example 1:
Input: num = "123", target = 6
Output: ["1*2*3","1+2+3"]
Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.

Example 2:
Input: num = "232", target = 8
Output: ["2*3+2","2+3*2"]
Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.

Example 3:
Input: num = "3456237490", target = 9191
Output: []
Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.

Constraints:
            1 <= num.length <= 10
            num consists of only digits.
            -2^31 <= target <= 2^31 - 1
 */
public class ExpressionAddOperators {

    public static void main(String[] args) {
        String[] operators = {"+", "-", "*"};
        List<String> result = new ArrayList<>();
        String num = "123";
        int target = 6;
        addOperators(num, target, 0, 0, 0, "", result);
        ;
        System.out.println(result);
    }

    /**
     * addOperators (Expression Add Operators - Backtracking)
     * <p>
     * What it does:
     * <p>
     * Given a string of digits and a target integer, this method finds all possible
     * ways to insert binary operators ('+', '-', or '*') between the digits so
     * that the resulting expression evaluates to the target value.
     * <p>
     * Core Intuition:
     * We process the string by "cutting" it into various lengths to form numbers.
     * For each number we form, we try all three operators: Addition, Subtraction,
     * and Multiplication.
     * Because multiplication has higher precedence than addition and subtraction,
     * we need a special trick to "undo" the previous operation if a '*' appears.
     * <p>
     * Why this is a backtracking problem:
     * At every step, we make a decision: "Where should I cut the string?" and
     * "Which operator should I place?"
     * We explore that path to completion. If the expression doesn't hit the target,
     * we backtrack and try a different operator or a different number length.
     * <p>
     * Step-by-step explanation:
     * <p>
     * num: The input string of digits.
     * <p>
     * target: The value we are trying to reach.
     * <p>
     * start: The current index in the string we are starting from.
     * <p>
     * current_value: The current total of the expression evaluated so far.
     * <p>
     * last_operand: The value of the very last number added or subtracted
     * (Crucial for the Multiplication trick).
     * <p>
     * expression: The string representation of the path taken so far.
     * <p>
     * The Multiplication Trick (The "Why" behind the logic):
     * When we encounter a '*', we cannot simply multiply the current_value.
     * Example: 2 + 3 * 2
     * <p>
     * Process 2: current_value = 2, last_operand = 2.
     * <p>
     * Process +3: current_value = 5, last_operand = 3.
     * <p>
     * Process *2: If we just did 5 * 2 = 10, that's wrong (should be 8).
     * <p>
     * Solution: We "undo" the last addition: (5 - 3) + (3 * 2) = 2 + 6 = 8.
     * <p>
     * This is why the code uses: current_value - last_operand + (last_operand * current_num_val).
     * <p>
     * Edge Case: Leading Zeros
     * <p>
     * If a number has more than one digit and starts with '0' (e.g., "05"), it is
     * invalid in this problem. The code handles this with:
     * <p>
     * if (i > start && num.charAt(start) == '0') return;
     * <p>
     * Recursive Process:
     * <p>
     * Base Case: If start == num.length, check if current_value == target.
     * <p>
     * Loop: Iterate from 'start' to the end of the string to pick the next number.
     * <p>
     * First Number: If start == 0, we just take the number (no operator needed).
     * <p>
     * Operators: Otherwise, try adding '+', '-', and '*' to the expression
     * and recurse.
     * <p>
     * Examples:
     * <p>
     * Input: num = "123", target = 6
     * <p>
     * "1+2+3" = 6 (Success)
     * <p>
     * "123" = 6 (Success)
     * <p>
     * "12-3" = 9 (Fail)
     * <p>
     * Complexity:
     * <p>
     * Time: O(4^N) - At each of the N-1 spaces, we have 4 choices: +, -, *, or
     * no operator (joining digits).
     * <p>
     * Space: O(N) - The depth of the recursion stack is the length of the string.
     *
     */
    public static void addOperators(String num, int target, int start, long current_value, long last_operand, String expression, List<String> result) {

        if (num.isEmpty()) {
            return;
        }
        if (num.length() == 1) {
            result.add(num);
            return;
        }

        if (start == num.length()) {
            if (current_value == target) {
                result.add(expression);
            }
            return;
        }
        for (int i = start; i < num.length(); i++) {
            if (i > start && num.charAt(start) == '0') return;

            String current_num = num.substring(start, i + 1);
            long current_num_val = Long.parseLong(current_num);

            if (start == 0) {
                addOperators(num, target, i + 1, current_num_val, current_num_val, current_num, result);
            } else {
                addOperators(num, target, i + 1, current_value + current_num_val, current_num_val, expression + "+" + current_num, result);
                addOperators(num, target, i + 1, current_value - current_num_val, -current_num_val, expression + "-" + current_num, result);
                addOperators(num, target, i + 1, current_value - last_operand + last_operand * current_num_val, last_operand * current_num_val, expression + "*" + current_num, result);
            }
        }
    }
}



