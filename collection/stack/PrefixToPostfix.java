package collection.stack;

import java.util.Stack;

/*
Convert the given prefix expression to postfix expression.
 */
public class PrefixToPostfix {

    public static void main(String[] args) {

        String s = "/-AB*+DEF";
        System.out.println(convertPreFixToPostFix(s));
    }

    /**
     * What it does:
     * Converts a given prefix expression into a postfix expression.
     * Prefix notation places operators before operands,
     * while postfix notation places operators after operands.
     * <p>
     * Why this conversion is useful:
     * Prefix and postfix are both machine friendly notations.
     * Converting between them is often required when building
     * expression trees or implementing compilers and interpreters.
     * <p>
     * When prefix is used:
     * Prefix is commonly used in recursive evaluation
     * and expression tree representations.
     * <p>
     * When postfix is used:
     * Postfix is used in stack based evaluation systems
     * because it can be evaluated in a single left to right pass.
     * <p>
     * Core idea of the algorithm:
     * Prefix expressions must be processed from right to left.
     * A stack is used to build partial postfix expressions.
     * Operands are pushed directly.
     * Operators combine two operands into a postfix expression.
     * <p>
     * Explanation of traversal direction:
     * Prefix expressions place operators before operands,
     * so scanning from right to left ensures operands are
     * encountered before their corresponding operator.
     * <p>
     * Explanation of stack usage:
     * The stack stores partial postfix expressions as strings.
     * Each operator reduces two operands into one postfix expression.
     * <p>
     * Step by step explanation of the loop:
     * - Start scanning the prefix expression from the last character.
     * - If the character is an operand:
     * Push it onto the stack as a string.
     * - If the character is an operator:
     * Pop the first operand from the stack.
     * Pop the second operand from the stack.
     * Combine them in postfix form:
     * operand1 operand2 operator
     * Push the combined string back onto the stack.
     * <p>
     * Why operand order matters:
     * In prefix notation, the operator applies to the
     * two operands that immediately follow it.
     * When scanning from right to left, the first popped
     * element becomes the left operand and the second popped
     * element becomes the right operand.
     * <p>
     * Final result:
     * After the entire prefix expression is processed,
     * the stack contains exactly one element.
     * That element is the final postfix expression.
     * <p>
     * Example walkthrough:
     * Prefix: /-AB*+DEF
     * Conversion steps produce:
     * Postfix: AB-DE+F*'/' -< Here I have attached single quote around division operator.
     * <p>
     * Time complexity:
     * O(n), where n is the length of the prefix expression.
     * Each character is processed once.
     * <p>
     * Space complexity:
     * O(n) due to the stack storing intermediate expressions.
     * <p>
     * Interview explanation summary:
     * Prefix to postfix conversion uses a stack and
     * reverse traversal.
     * Operands are pushed directly.
     * Operators pop two operands, combine them,
     * and push the result back.
     */
    private static String convertPreFixToPostFix(String s) {
        int i = s.length() - 1;
        Stack<String> stack = new Stack<>();

        while (i >= 0) {

            if (Character.isLetterOrDigit(s.charAt(i))) {
                stack.push(s.charAt(i) + "");
            } else {
                String op1 = stack.pop();
                String op2 = stack.pop();
                stack.push(op1 + op2 + s.charAt(i));
            }
            i--;
        }
        return stack.pop();
    }
}
