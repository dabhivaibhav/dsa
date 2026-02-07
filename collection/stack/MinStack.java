package collection.stack;

import java.util.Stack;

/*
Leetcode 155. Min Stack

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
Implement the MinStack class:
MinStack() initializes the stack object.
void push(int val) pushes the element val onto the stack.
void pop() removes the element on the top of the stack.
int top() gets the top element of the stack.
int getMin() retrieves the minimum element in the stack.
You must implement a solution with O(1) time complexity for each function.

Example 1:
Input
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]

Output
[null,null,null,null,-3,null,0,-2]
Explanation
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2


Constraints:
            -2^31 <= val <= 2^31 - 1
            Methods pop, top and getMin operations will always be called on non-empty stacks.
            At most 3 * 104 calls will be made to push, pop, top, and getMin.
 */
public class MinStack {


    public static void main(String[] args) {
        CustomizedStack s = new CustomizedStack();
        s.push(-2);
        s.push(0);
        s.push(-3);
        System.out.print(s.getMin() + " ");
        s.pop();
        System.out.print(s.top() + " ");
        s.pop();
        System.out.print(s.getMin());
        System.out.println();
        OptimizedStack s1 = new OptimizedStack();
        s1.push(-2);
        s1.push(0);
        s1.push(-3);
        System.out.print(s1.getMin() + " ");
        s1.pop();
        System.out.print(s1.top() + " ");
        s1.pop();
        System.out.print(s1.getMin());

    }

    /**
     * CustomizedStack implements a Min Stack using a single stack and one variable
     * to track the minimum element.
     * <p>
     * Core idea:
     * Instead of storing every minimum separately, this stack encodes information
     * inside the stack whenever a new minimum is pushed.
     * <p>
     * Encoding rule:
     * When a value smaller than the current minimum is pushed, the value is not
     * stored directly. Instead, a modified value is pushed using:
     * <p>
     * encodedValue = 2 * newValue - oldMinimum
     * <p>
     * This encoded value is always smaller than the new minimum, which acts as a
     * signal that the value represents a minimum update.
     * <p>
     * How push works:
     * - If the stack is empty, set the minimum to the value and push it normally.
     * - If the value is greater than or equal to the current minimum, push it normally.
     * - If the value is smaller than the current minimum, push the encoded value
     * and update the minimum to the new value.
     * <p>
     * How pop works:
     * - Pop the top element.
     * - If the popped value is greater than or equal to the current minimum,
     * it is a normal value.
     * - If the popped value is smaller than the current minimum,
     * it indicates the current minimum is being removed.
     * The previous minimum is restored using:
     * <p>
     * previousMinimum = 2 * currentMinimum - encodedValue
     * <p>
     * How top works:
     * - If the top value is greater than or equal to the minimum, return it directly.
     * - If the top value is encoded, the actual top element is the current minimum.
     * <p>
     * How getMin works:
     * - Always returns the current minimum stored in the variable.
     * <p>
     * Why Long is used:
     * The encoding calculation can overflow an int.
     * Using Long prevents overflow and makes the implementation safe.
     * <p>
     * Time complexity:
     * push, pop, top, and getMin all run in O(1).
     * <p>
     * Space complexity:
     * O(2*n), where n is the number of elements in the stack and at each element we are storing a pair.
     * <p>
     * Interview takeaway:
     * This approach stores historical minimum information implicitly using
     * mathematical encoding, allowing constant time minimum retrieval
     * without using an extra stack.
     */

    private static class CustomizedStack {
        private final Stack<Long> st;
        private Long mini;

        public CustomizedStack() {
            st = new Stack<>();
        }

        public void push(int val) {
            Long value = (long) val;
            if (st.isEmpty()) {
                mini = value;
                st.push(value);
            } else if (value >= mini) {
                st.push(value);
            } else {
                // Encode the previous mini
                st.push(2 * value - mini);
                mini = value;
            }
        }

        public void pop() {
            if (st.isEmpty()) return;

            Long x = st.pop();
            // If x < mini, it's a flag that we are popping the current minimum
            if (x < mini) {
                mini = 2 * mini - x; // Decode the previous minimum
            }
        }

        public int top() {
            if (st.isEmpty()) return -1;

            Long x = st.peek();
            if (x >= mini) {
                return x.intValue();
            } else {
                // If x < mini, the actual top is the current mini
                return mini.intValue();
            }
        }

        public int getMin() {
            return mini.intValue();
        }
    }


    /**
     * OptimizedStack implements a Min Stack using the same encoding technique
     * as CustomizedStack but with integer values.
     * <p>
     * Core idea:
     * Maintain the current minimum in a variable and encode values when a new
     * minimum is pushed, so previous minimum values can be restored later.
     * <p>
     * Encoding rule:
     * When pushing a value smaller than the current minimum:
     * <p>
     * encodedValue = 2 * newValue - oldMinimum
     * <p>
     * The encoded value is always smaller than the new minimum and acts as a marker.
     * <p>
     * How push works:
     * - If the stack is empty, initialize minimum and push the value.
     * - If the value is greater than the current minimum, push normally.
     * - If the value is smaller than the current minimum, push the encoded value
     * and update the minimum.
     * <p>
     * How pop works:
     * - Pop the top element.
     * - If the popped value is less than the current minimum, it indicates
     * the minimum is being removed.
     * - Restore the previous minimum using:
     * <p>
     * previousMinimum = 2 * currentMinimum - encodedValue
     * <p>
     * How top works:
     * - If the top value is greater than the minimum, return it.
     * - If the top value is encoded, return the current minimum.
     * <p>
     * How getMin works:
     * - Returns the current minimum in constant time.
     * <p>
     * Important note:
     * This version uses int and assumes values remain within safe integer bounds.
     * For extreme values, the Long-based version is safer.
     * <p>
     * Time complexity:
     * All operations run in O(1).
     * <p>
     * Space complexity:
     * O(n) for the stack.
     * <p>
     * Interview takeaway:
     * This solution achieves constant time minimum retrieval using a clever
     * encoding trick, without any extra data structures.
     */

    private static class OptimizedStack {
        private Stack<Integer> st;
        private int mini;

        public OptimizedStack() {
            st = new Stack<>();
        }

        public void push(int value) {
            if (st.isEmpty()) {
                mini = value;
                st.push(value);
                return;
            }

            // If the value is greater than the minimum
            if (value > mini) {
                st.push(value);
            } else {
                // Add the modified value to stack
                st.push(2 * value - mini);
                // Update the minimum
                mini = value;
            }
        }

        public void pop() {
            if (st.isEmpty()) return;
            int x = st.pop();

            // If the modified value was added to stack
            if (x < mini) {
                // Update the minimum
                mini = 2 * mini - x;
            }
        }

        public int top() {
            if (st.isEmpty()) return -1;
            int x = st.peek();

            // Return top if minimum is less than the top
            if (mini < x) return x;

            // Otherwise return mini
            return mini;
        }

        public int getMin() {
            return mini;
        }
    }

}
