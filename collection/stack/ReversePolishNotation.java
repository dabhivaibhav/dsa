package collection.stack;

import java.util.Set;
import java.util.Stack;

/*
Leetcode 150. Evaluate Reverse Polish Notation

You are given an array of strings tokens that represents an arithmetic expression in a Reverse Polish Notation.
Evaluate the expression. Return an integer that represents the value of the expression.

Note that:
The valid operators are '+', '-', '*', and '/'.
Each operand may be an integer or another expression.
The division between two integers always truncates toward zero.
There will not be any division by zero.
The input represents a valid arithmetic expression in a reverse polish notation.
The answer and all the intermediate calculations can be represented in a 32-bit integer.


Example 1:
Input: tokens = ["2","1","+","3","*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9

Example 2:
Input: tokens = ["4","13","5","/","+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6

Example 3:
Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
Output: 22
Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 17 + 5
= 22


Constraints:
            1 <= tokens.length <= 10^4
            tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the range [-200, 200].
 */
public class ReversePolishNotation {

    public static void main(String[] args){

        String[] set1 = {"4","13","5","/","+"};
        String[] set2 = {"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        String[] set3 = {"2","1","+","3","*"};
        System.out.println(evalRPN(set1));
        System.out.println(evalRPN(set2));
        System.out.println(evalRPN(set3));
    }

    /*
     * Intuition:
     *   RPN guarantees that when you hit an operator, the two most recent
     *   values are its operands. "Most recent" = LIFO = stack.
     *   Numbers get pushed. Operators pop two, compute, push the result back.
     *   After the full pass, one value remains on the stack: the answer.
     *
     * How I identified the pattern:
     *   Hand-simulated a small expression and watched my own process:
     *   I kept "holding" values and grabbing the last two when I saw an operator.
     *   "Grab the most recent thing I stored" = stack.
     *
     * Why not a queue:
     *   Tested with ["4","13","5","/","+"]. Queue grabs 4 and 13 for the "/",
     *   but the correct operands are 13 and 5 (the two nearest). Disproved it
     *   with one example.
     *
     * Design choices:
     *   - First pop = right operand, second pop = left. Order matters for - and /.
     *   - Switch on operator with default handling division. Numbers go through
     *     the if-branch via parseInt.
     *   - Enhanced for-loop since index is never needed directly.
     *
     * Mistakes made:
     *   - Initially used && instead of || when checking operators (a string can't
     *     equal four different things simultaneously).
     *   - Had the logic inverted: was pushing on operators and popping on numbers.
     *   - Forgot to convert String to Integer before pushing.
     *
     * Complexity:
     *   Time:  O(n) - single pass through tokens array
     *   Space: O(n) - stack holds at most n/2 operands at peak
     */
    private static int evalRPN(String[] tokens) {
        Stack<Integer> operandStack = new Stack<>();
        Set<String> operators = Set.of("+", "-", "*", "/");

        for (String token : tokens) {
            if (!operators.contains(token)) {
                operandStack.push(Integer.parseInt(token));
            } else {
                int rightOperand = operandStack.pop();
                int leftOperand = operandStack.pop();

                switch (token) {
                    case "+":
                        operandStack.push(leftOperand + rightOperand);
                        break;
                    case "-":
                        operandStack.push(leftOperand - rightOperand);
                        break;
                    case "*":
                        operandStack.push(leftOperand * rightOperand);
                        break;
                    default:
                        operandStack.push(leftOperand / rightOperand);
                        break;
                }
            }
        }

        return operandStack.pop();
    }
}
