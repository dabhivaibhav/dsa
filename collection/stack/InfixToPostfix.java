package collection.stack;


import java.util.Stack;

/*
Convert the given infix expression to postfix expression.
 */
public class InfixToPostfix {

    public static void main(String[] args) {
        String exp = "(p+q)*(m-n)";  // Infix expression
        System.out.println("Infix expression: " + exp);
        System.out.println(infixToPostfix(exp));
    }

    /**
     * What it does:
     * Converts an infix expression into a postfix expression.
     * Infix means operators are written between operands, like p+q.
     * Postfix means operators are written after operands, like pq+.
     * <p>
     * Why this conversion is useful:
     * Infix is easy for humans to read because it matches standard math notation,
     * but it is harder for a program to evaluate directly because it must handle:
     * operator precedence, parentheses, and associativity.
     * <p>
     * Postfix is easy for a computer to evaluate because it removes parentheses
     * and removes the need to apply precedence rules during evaluation.
     * A postfix expression can be evaluated in one left to right pass using a stack.
     * <p>
     * When infix is used:
     * - In everyday mathematics and most programming languages.
     * - When humans write expressions.
     * - When readability is more important than evaluation simplicity.
     * <p>
     * When postfix is used:
     * - In compilers and expression evaluators.
     * - In stack based evaluation engines.
     * - When we want unambiguous evaluation order without parentheses.
     * - When we want a simple single pass evaluation algorithm.
     * <p>
     * Reason behind converting infix to postfix:
     * Infix expressions require the evaluator to constantly decide which operator
     * should be applied first based on precedence and parentheses.
     * Postfix expressions encode the evaluation order directly in their sequence.
     * That makes evaluation simpler, faster to implement, and less error prone.
     * <p>
     * Core idea of the algorithm:
     * - Use a stack to temporarily store operators and parentheses.
     * - Output operands immediately.
     * - Output operators only when it is safe to do so based on precedence.
     * - Parentheses control when operators must be popped.
     * <p>
     * Explanation of variables:
     * - i is the pointer used to scan the infix string from left to right.
     * - stack stores operators and opening parentheses.
     * - sb builds the final postfix result.
     * <p>
     * Step by step explanation of the loop:
     * For each character c in the infix expression:
     * <p>
     * 1) If c is an operand:
     * Append it directly to sb.
     * Operands keep their relative order in postfix.
     * <p>
     * 2) If c is an opening parenthesis:
     * Push it onto the stack.
     * This acts as a boundary that prevents popping operators across it.
     * <p>
     * 3) If c is a closing parenthesis:
     * Pop operators from the stack and append them to sb
     * until an opening parenthesis is found.
     * Then pop the opening parenthesis and discard it.
     * This effectively removes parentheses from the final postfix form.
     * <p>
     * 4) If c is an operator:
     * While the stack is not empty and the operator on top of the stack has
     * higher or equal precedence compared to c, pop it and append it to sb.
     * Then push c onto the stack.
     * This ensures that higher precedence operators appear earlier in postfix,
     * and lower precedence operators wait until later.
     * <p>
     * Why precedence comparison works:
     * The algorithm guarantees that operators are output in the same order
     * they would be applied in a normal infix evaluation.
     * Operators with higher precedence must be applied first, so they must appear
     * earlier in postfix, which is achieved by popping them before pushing a
     * lower precedence operator.
     * <p>
     * Explanation of the prec method:
     * Returns a numeric value representing operator precedence.
     * Higher number means higher precedence.
     * This method allows the main loop to compare operators consistently.
     * <p>
     * Final cleanup:
     * After scanning the whole expression, any remaining operators in the stack
     * are popped and appended to the result.
     * This finishes the postfix expression.
     * <p>
     * Example:
     * Infix: (p+q)*(m-n)
     * Output operands as they appear.
     * Use the stack to manage operators and parentheses.
     * Final postfix: pq+mn-*
     * <p>
     * Time complexity:
     * O(n), where n is the length of the expression.
     * Each character is pushed and popped at most one time.
     * <p>
     * Space complexity:
     * O(n) in the worst case due to the operator stack.
     * <p>
     * Interview explanation summary:
     * Infix is human friendly but needs precedence and parentheses handling.
     * Postfix is machine friendly and easy to evaluate using a stack.
     * This algorithm uses a stack to reorder operators based on precedence
     * and to remove parentheses, producing a postfix expression that can be
     * evaluated in a single pass.
     */

    private static String infixToPostfix(String infix) {
        int i = 0;
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        while (i < infix.length()) {
            char c = infix.charAt(i);

            // If operand, add to result
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
            // If '(', push to stack
            else if (c == '(') {
                stack.push(c);
            }
            // If ')', pop until '(' is found
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    sb.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // Pop the '('
            }
            // If operator
            else {
                while (!stack.isEmpty() && prec(c) <= prec(stack.peek())) {
                    sb.append(stack.pop());
                }
                stack.push(c);
            }
            i++; // Increment i for all cases
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    public static int prec(char c) {
        if (c == '^') return 3;
        if (c == '*' || c == '/') return 2;
        if (c == '+' || c == '-') return 1;
        return -1;
    }
}
