package recursion;

import java.util.Stack;

/*
Reverse a Stack

You are given a stack of integers. Your task is to reverse the stack using recursion. You may only use standard stack
operations (push, pop, top/peek, isEmpty). You are not allowed to use any loop constructs or additional data structures
like arrays or queues.

Your solution must modify the input stack in-place to reverse the order of its elements.

Examples:
Input: stack = [4, 1, 3, 2]
Output: [2, 3, 1, 4]

Input: stack = [10, 20, -5, 7, 15]
Output: [15, 7, -5, 20, 10]

Constraints:
            1 <= N <= 100 (where N is the number of elements in the stack)
            Must use only recursion (no loops or built-in reverse methods)
            Auxiliary space allowed: O(N) (due to recursion stack)
 */
public class ReverseStack {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(1);
        stack.push(3);
        stack.push(2);
        System.out.println(stack.peek());
        System.out.println(stack);
        reverseStack(stack);
        System.out.println(stack.peek());
        System.out.println(stack);
    }


    /**
     * reverseStack
     * <p>
     * What it does:
     * Recursively reverses the order of elements in a given stack of integers, so that the original top becomes the bottom and the original bottom becomes the top.
     * Only uses stack operations (`push`, `pop`, `peek`, `isEmpty`) and recursion—no loops and no extra data structures—modifying the input stack in place.
     * <p>
     * Intuition (step-by-step explanation):
     * - The main idea is to use recursion to "peel off" each element from the top of the stack, reaching the base (empty stack).
     * - After recursion has popped everything off (reaching the bottom), you then insert each popped element back at the very bottom as recursion unwinds.
     * - This is accomplished using the `insert` helper function, which recursively pops items until the stack is empty, then places the current value at the bottom, and restores the others above it.
     * - By repeatedly removing the top and putting it back at the bottom, all original stack elements are reversed.
     * <p>
     * Why each line matters:
     * - `if (stack.isEmpty()) return;`: Base case—if the stack is empty, there's nothing to reverse.
     * - `int temp = stack.pop();`: Pops the top item, storing it for later insertion.
     * - `reverseStack(stack);`: Recursively reverses the remaining items.
     * - `insert(stack, temp);`: Inserts the just-popped item at the very bottom of the stack.
     * - In `insert`, recursive pops are made until the stack is empty (finding the true bottom), temp is pushed, and the earlier popped values are restored on top.
     * <p>
     * Edge Cases Handled:
     * - Empty stack: Returns immediately; nothing changes.
     * - Single-element stack: Already reversed; recursion terminates right away.
     * <p>
     * Example:
     * Input: [4, 1, 3, 2] (4 is top)
     * After reverseStack: [2, 3, 1, 4] (2 is top)
     * <p>
     * Time Complexity:
     * - O(N^2): Each insertion at bottom takes O(N) time, called N times in total.
     * <p>
     * Space Complexity:
     * - O(N): Due to the recursion call stack.
     * <p>
     * Limitations:
     * - Cannot use loops or extra data structures (arrays, queues).
     * - Recursion stack space is required, but fits the problem constraints.
     */

    private static void reverseStack(Stack<Integer> stack) {


        if (stack.isEmpty()) return;
        int temp = stack.pop();
        reverseStack(stack);
        insert(stack, temp);

    }

    private static void insert(Stack<Integer> stack, int temp) {
        if (stack.isEmpty()) {
            stack.push(temp);
            return;
        }

        int val = stack.pop();
        insert(stack, temp);


        stack.push(val);

    }
}
