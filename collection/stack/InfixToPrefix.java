package collection.stack;

import java.util.Stack;

/*
Convert the given infix expression to prefix expression.
 */
public class InfixToPrefix {

    public static void main(String[] args) {
        String s = "(a+b)*c";
        System.out.println(infixToPrefix(s));
        System.out.println(infixToPrefix("a-b-c"));
        System.out.println(infixToPrefix("a^b^c"));
    }

    /**
     * What it does:
     * Converts a given infix expression into a prefix expression.
     * Infix notation places operators between operands, while prefix notation
     * places operators before operands.
     * <p>
     * Why prefix notation is useful:
     * Prefix expressions remove ambiguity during evaluation.
     * There is no need to manage operator precedence or parentheses when evaluating
     * a prefix expression using a stack or recursion.
     * <p>
     * When infix is used:
     * Infix is commonly used by humans because it is intuitive and readable.
     * Most programming languages and mathematical expressions use infix notation.
     * <p>
     * When prefix is used:
     * Prefix is used internally by compilers and expression evaluators.
     * It is also useful in stack based and recursive evaluation models.
     * <p>
     * Why infix to prefix conversion is required:
     * Infix expressions require handling precedence rules, associativity,
     * and parentheses during evaluation.
     * Prefix expressions encode the evaluation order directly.
     * <p>
     * Core idea of the algorithm:
     * The infix to prefix conversion reuses the infix to postfix logic.
     * The steps followed are:
     * - Reverse the infix expression.
     * - Swap opening and closing parentheses.
     * - Convert the modified expression into postfix.
     * - Reverse the postfix result to get the prefix expression.
     * <p>
     * Explanation of expression reversal:
     * Reversing the expression allows us to process it from right to left
     * while still scanning left to right in code.
     * Swapping parentheses preserves correct grouping after reversal.
     * <p>
     * Explanation of operator handling:
     * Operators are pushed to a stack and popped based on precedence rules.
     * The algorithm carefully handles operator associativity.
     * <p>
     * Associativity handling:
     * - The exponent operator '^' is right associative.
     * - Other operators like +, -, *, / are left associative.
     * - Different comparison rules are applied to ensure correct ordering.
     * <p>
     * Precedence comparison logic:
     * - If the operator is '^', pop operators with greater or equal precedence.
     * - If the operator is not '^', pop operators with strictly greater precedence.
     * This prevents incorrect evaluation order for expressions like a-b-c and a^b^c.
     * <p>
     * Explanation of the main loop:
     * - Operands are appended directly to the result.
     * - Opening parentheses are pushed onto the stack.
     * - Closing parentheses trigger popping until the matching opening parenthesis.
     * - Operators are pushed or popped based on precedence and associativity.
     * <p>
     * Final step:
     * After processing all characters, any remaining operators are popped.
     * The final string is reversed to produce the prefix expression.
     * <p>
     * Examples:
     * (a+b)*c  -> *+abc
     * a-b-c    -> --abc
     * a^b^c    -> ^a^bc
     * <p>
     * Time complexity:
     * O(n), where n is the length of the expression.
     * Each character is processed a constant number of times.
     * <p>
     * Space complexity:
     * O(n) due to the operator stack and intermediate result storage.
     * <p>
     * Interview explanation summary:
     * This solution converts infix to prefix by reversing the expression,
     * applying postfix conversion rules with correct associativity handling,
     * and reversing the result.
     * The stack ensures correct operator precedence and nesting.
     */
    private static String infixToPrefix(String s) {


        StringBuilder newStr = new StringBuilder(s);
        newStr.reverse();


        for (int i = 0; i < newStr.length(); i++) {
            if (newStr.charAt(i) == '(')
                newStr.setCharAt(i, ')');
            else if (newStr.charAt(i) == ')')
                newStr.setCharAt(i, '(');
        }
        int i = 0;
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();


        while (i < newStr.length()) {
            char c = newStr.charAt(i);

            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                sb.append(c);

            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    sb.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop();
            } else {
                while (!stack.isEmpty() && (
                        (c == '^' && prec(c) <= prec(stack.peek())) ||
                                (c != '^' && prec(c) < prec(stack.peek()))
                )) {
                    sb.append(stack.pop());
                }
                stack.push(c);
            }
            i++;
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    private static int prec(char c) {
        if (c == '^') return 3;
        if (c == '*' || c == '/') return 2;
        if (c == '+' || c == '-') return 1;
        return -1;
    }

}
