package collection.stack;

import java.util.Stack;

/*
Given expression in postfix form, convert it to infix form.
 */
public class PostfixToInfix {

    public static void main(String[] args) {
        System.out.println(postfixToInfix("AB-DE+f*/"));
    }


    /**
     * What it does:
     * Converts a given postfix expression into an infix expression.
     * Postfix notation places operators after operands, while infix notation
     * places operators between operands.
     * <p>
     * Why this conversion is needed:
     * Postfix expressions are easy for machines to evaluate using a stack,
     * but they are not human friendly.
     * Converting postfix to infix makes the expression readable and easier
     * for humans to understand.
     * <p>
     * When postfix is used:
     * Postfix is commonly used in compilers and expression evaluators
     * because it eliminates the need for precedence rules during evaluation.
     * <p>
     * When infix is used:
     * Infix is used for readability and standard mathematical representation.
     * <p>
     * Core idea of the algorithm:
     * Use a stack to rebuild the infix expression from postfix.
     * Operands are pushed onto the stack.
     * When an operator is encountered, two operands are popped,
     * combined into an infix expression, and pushed back.
     * <p>
     * Explanation of stack usage:
     * The stack stores partial infix expressions as strings.
     * Each time an operator is processed, a larger valid infix expression
     * is constructed and pushed back onto the stack.
     * <p>
     * Step by step explanation of the loop:
     * - Traverse the postfix string from left to right.
     * - If the character is an operand, push it onto the stack as a string.
     * - If the character is an operator:
     * - Pop the top element as the right operand.
     * - Pop the next top element as the left operand.
     * - Combine them in infix form using parentheses:
     * (leftOperand operator rightOperand)
     * - Push the resulting string back onto the stack.
     * <p>
     * Why parentheses are added:
     * Parentheses ensure the correct evaluation order in the resulting infix expression.
     * This avoids ambiguity regardless of operator precedence.
     * <p>
     * Final result:
     * After processing the entire postfix expression, the stack will contain
     * exactly one element, which is the complete infix expression.
     * <p>
     * Example:
     * Postfix: AB-DE+f
     * Processing builds subexpressions step by step.
     * Final infix: ((A - B) / ((D + E) * F))
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
     * - Nested operations
     * - Mixed operators and operands
     * <p>
     * Interview explanation summary:
     * Postfix to infix conversion uses a stack to reconstruct expressions.
     * Operands are pushed directly.
     * Operators combine the two most recent operands into a new infix expression,
     * which is pushed back until the final expression is formed.
     */
    private static String postfixToInfix(String s) {
        int i = 0;
        Stack<String> stack = new Stack<>();

        while (i < s.length()) {
            if ((s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
                stack.push(String.valueOf(s.charAt(i)));
            } else {
                String op1 = stack.pop();
                String op2 = stack.pop();
                stack.push("(" + op2 + s.charAt(i) + op1 + ")");
            }
            i++;
        }
        return stack.pop();
    }
}
