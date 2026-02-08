package collection.stack;

/*
Convert the given postfix expression to prefix expression.
 */

import java.util.Stack;

public class PostfixToPrefix {

    public static void main(String[] args) {
        System.out.println(convertPostFixToPreFix("AB-DE+F*/"));
    }

    /**
     * What it does:
     * Converts a given postfix expression into a prefix expression.
     * Postfix notation places operators after operands,
     * while prefix notation places operators before operands.
     * <p>
     * Why this conversion is needed:
     * Postfix expressions are efficient for machines to evaluate,
     * but prefix expressions are often preferred in recursive
     * or tree based evaluation models.
     * Converting postfix to prefix changes the notation
     * without changing the meaning of the expression.
     * <p>
     * When postfix is used:
     * Postfix notation is commonly used in compilers
     * and stack based evaluation systems because it
     * eliminates the need for precedence handling.
     * <p>
     * When prefix is used:
     * Prefix notation is used in expression trees
     * and recursive evaluation engines where operators
     * naturally appear before operands.
     * <p>
     * Core idea of the algorithm:
     * Use a stack to rebuild the prefix expression.
     * Operands are pushed directly.
     * Operators combine the two most recent operands
     * into a new prefix expression.
     * <p>
     * Explanation of stack usage:
     * The stack stores partial prefix expressions as strings.
     * Each operator reduces two operands into one expression.
     * <p>
     * Step by step explanation of the loop:
     * - Traverse the postfix expression from left to right.
     * - If the character is an operand:
     * Push it onto the stack as a string.
     * - If the character is an operator:
     * Pop the first operand as the right operand.
     * Pop the second operand as the left operand.
     * Combine them into prefix form:
     * operator + leftOperand + rightOperand
     * Push the combined string back onto the stack.
     * <p>
     * Why operand order matters:
     * In postfix notation, the operator applies to
     * the two operands that appear immediately before it.
     * The first popped element is the right operand,
     * and the second popped element is the left operand.
     * <p>
     * Final result:
     * After processing the entire postfix expression,
     * the stack contains exactly one element.
     * That element is the complete prefix expression.
     * <p>
     * Example walkthrough:
     * <p>
     * Postfix: AB-DE+F*
     * <p>
     * Steps:
     * - A, B pushed
     * - '-' creates -AB
     * - D, E pushed
     * - '+' creates +DE
     * - F pushed
     * - '*' creates *+DEF
     * - '/' creates /-AB*+DEF
     * <p>
     * Time complexity:
     * O(n), where n is the length of the postfix expression.
     * Each character is processed once.
     * <p>
     * Space complexity:
     * O(n) due to the stack storing partial expressions.
     * <p>
     * Edge cases handled:
     * - Single operand expressions
     * - Nested expressions
     * - Mixed operators and operands
     * <p>
     * Interview explanation summary:
     * Postfix to prefix conversion uses a stack.
     * Operands are pushed directly.
     * Operators pop two operands, combine them into
     * a prefix expression, and push the result back.
     * The final prefix expression remains on the stack.
     */

    private static String convertPostFixToPreFix(String s) {

        int i = 0;
        Stack<String> stack = new Stack<>();

        while (i < s.length()) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                stack.push(String.valueOf(s.charAt(i)));
            } else {
                String op2 = stack.pop();
                String op1 = stack.pop();
                stack.push(s.charAt(i) + op1 + op2);
            }
            i++;
        }

        return stack.pop();
    }
}
