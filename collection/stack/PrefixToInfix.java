package collection.stack;

import java.util.Stack;

/*
Convert the given prefix expression to infix expression.
 */
public class PrefixToInfix {

    public static void main(String[] args) {
        System.out.println(prefixToInfix("*+PQ-MN"));
    }


    /**
     * What it does:
     * Converts a given prefix expression into an infix expression.
     * Prefix notation places operators before operands, while infix notation
     * places operators between operands.
     * <p>
     * Why this conversion is needed:
     * Prefix expressions are efficient for machines and compilers to evaluate,
     * but they are not easy for humans to read.
     * Converting prefix to infix restores the familiar mathematical form
     * with explicit operators between operands.
     * <p>
     * When prefix is used:
     * Prefix notation is commonly used internally in compilers,
     * expression trees, and recursive evaluation systems.
     * It removes ambiguity because the order of evaluation is fixed.
     * <p>
     * When infix is used:
     * Infix notation is used for human readability.
     * Most mathematical expressions and programming languages use infix form.
     * <p>
     * Core idea of the algorithm:
     * Prefix expressions are evaluated from right to left.
     * A stack is used to rebuild the infix expression step by step.
     * Operands are pushed onto the stack.
     * When an operator is encountered, two operands are popped,
     * combined into an infix expression, and pushed back.
     * <p>
     * Explanation of traversal direction:
     * Unlike postfix, prefix expressions must be scanned from right to left.
     * This ensures operands are encountered before their operator,
     * which is necessary for correct stack processing.
     * <p>
     * Explanation of stack usage:
     * The stack stores partial infix expressions as strings.
     * Each time an operator is processed, a larger valid infix expression
     * is constructed and pushed back onto the stack.
     * <p>
     * Step by step explanation of the loop:
     * - Start scanning the prefix expression from the last character.
     * - If the character is an operand:
     * Push it onto the stack as a string.
     * - If the character is an operator:
     * Pop the first operand from the stack.
     * Pop the second operand from the stack.
     * Combine them into an infix expression in the form:
     * (operand1 operator operand2)
     * Push the combined expression back onto the stack.
     * <p>
     * Why parentheses are added:
     * Parentheses preserve the correct evaluation order.
     * This avoids ambiguity regardless of operator precedence.
     * <p>
     * Final result:
     * After the entire prefix expression is processed,
     * the stack contains exactly one element.
     * That element is the complete infix expression.
     * <p>
     * Example walkthrough:
     * Prefix: *+PQ-MN
     * Processed from right to left:
     * - Push P, push Q
     * - '+' combines them into (P+Q)
     * - Push M, push N
     * - '-' combines them into (M-N)
     * - '*' combines (P+Q) and (M-N) into ((P+Q)*(M-N))
     * <p>
     * Time complexity:
     * O(n), where n is the length of the prefix expression.
     * Each character is processed once.
     * <p>
     * Space complexity:
     * O(n) due to the stack storing partial expressions.
     * <p>
     * Edge cases handled:
     * - Single operand expressions
     * - Nested expressions
     * - Mixed operands and operators
     * <p>
     * Interview explanation summary:
     * Prefix to infix conversion works by scanning from right to left.
     * Operands are pushed onto a stack.
     * Operators combine the two most recent operands into a new infix expression.
     * The final expression remains on the stack after processing completes.
     */
    private static String prefixToInfix(String s) {

        int i = s.length() - 1;
        Stack<String> stack = new Stack<>();

        while (i >= 0) {
            // This time I have replaced my if conditions with isLetterOrDigit method of Character
            if (Character.isLetterOrDigit(s.charAt(i))) {
                stack.push(String.valueOf(s.charAt(i)));
            } else {
                String op1 = stack.pop();
                String op2 = stack.pop();
                stack.push("(" + op1 + s.charAt(i) + op2 + ")");
            }

            i--;
        }

        return stack.pop();
    }
}
