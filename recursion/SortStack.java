package recursion;

import java.util.Stack;

/*
Sort a Stack

You are given a stack of integers. Your task is to sort the stack in descending order using recursion, such that the top
of the stack contains the greatest element. You are not allowed to use any loop-based sorting methods (e.g., quicksort,
mergesort). You may only use recursive operations and the standard stack operations (push, pop, peek/top, and isEmpty).

Examples:

Input: stack = [4, 1, 3, 2]
Output: [4, 3, 2, 1]
Explanation: After sorting, the largest element (4) is at the top, and the smallest (1) is at the bottom.

Input: stack = [1]
Output: [1]
Explanation: A single-element stack is already sorted.


Constraints:
            1 <= N <= 100 (where N is the number of elements in the stack)
            Use recursion to implement the sorting logic.
            You may use auxiliary space up to O(N) (call stack).
 */
public class SortStack {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(1);
        stack.push(3);
        stack.push(2);
        sortStackBruteForce(stack);
        System.out.println(stack);

    }

    /**
     * sortStackBruteForce
     * <p>
     * What it does:
     * Sorts a given stack of integers in **descending order** (largest items on top, smallest on bottom)
     * using two stacks and only stack operations. After execution, the provided stack will have its elements
     * arranged in decreasing order without using built-in sorting or iterative sorting algorithms.
     * <p>
     * Intuition (step-by-step explanation):
     * - The idea is to use a second (temporary) stack to help insert each element at the correct position.
     * - For every element in the input stack:
     * - Remove (pop) it from the original stack.
     * - Move elements from the temporary stack back to the original stack if they are greater than the current element
     * (this way, the biggest elements stay closer to the top in temp).
     * - Insert (push) the current element onto the temporary stack at the right position.
     * - Repeat this process until the original stack is empty.
     * - At the end, the temporary stack contains the elements in descending order.
     * - Move the elements back from the temporary stack to the original to preserve the descending order in-place.
     * <p>
     * Why each line matters:
     * - `Stack<Integer> temp = new Stack<>();` – Temporary stack used for sorting.
     * - `while(!stack.isEmpty()) {` – Processes every element in the original stack.
     * - `int top = stack.pop();` – Takes the next element to be inserted in sorted order.
     * - Inner while-loop: Moves back larger elements until the correct place is found.
     * - `temp.push(top);` – Inserts the element into its sorted place.
     * - Final while-loop: Puts everything back from temp to stack in descending order.
     * <p>
     * Edge Cases Handled:
     * - A single-element stack: Already sorted, code still works.
     * - An empty stack: Nothing happens, code is safe.
     * - Duplicate values: Maintains all occurrences and preserves sorting.
     * <p>
     * Example:
     * Input: [4, 1, 3, 2]
     * Steps: Moves items between stacks so temp stack is always sorted descending.
     * Output: [4, 3, 2, 1] (with 4 at the top, 1 at the bottom)
     * <p>
     * Time Complexity:
     * - O(N^2): For N elements, each may require moving almost all items in/out of the temp stack.
     * <p>
     * Space Complexity:
     * - O(N): Uses one auxiliary stack, which can hold all items in the worst case.
     * <p>
     * Limitations:
     * - Code uses loops, not recursion. This does not fulfill the pure recursion constraint, but demonstrates stack-based
     * insertion sorting logic using only stack operations.
     */
    private static void sortStackBruteForce(Stack<Integer> stack) {
        Stack<Integer> temp = new Stack<>();
        while (!stack.isEmpty()) {
            int top = stack.pop();
            while (!temp.isEmpty() && temp.peek() > top) {
                stack.push(temp.pop());
            }
            temp.push(top);
        }
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
    }

    /**
     * sortStack
     * <p>
     * What it does:
     * Recursively sorts a stack of integers in descending order (largest at the top, smallest at the bottom),
     * using only stack operations (push, pop, peek, isEmpty) and recursion—**no loops, no extra stack**.
     * The sorting happens by recursively popping the entire stack, then inserting each element back into
     * its sorted position (using the insert helper method) as recursion unwinds.
     * <p>
     * Intuition (beginner-friendly, step-by-step explanation):
     * - The main idea is to sort the stack by leveraging recursion to break it down into smaller sub-problems:
     * 1. Remove (pop) the top element.
     * 2. Recursively sort the rest of the stack.
     * 3. Insert the removed element back into the sorted stack
     * (using the insert helper, which itself uses recursion).
     * - The insert method finds the correct spot for the popped element within the sorted portion of the stack:
     * - If stack is empty or the top of stack is <= the element you want to place, push it on top.
     * - Otherwise, pop the next value off, recursively call insert,
     * then place the popped value back (preserving order).
     * - Recursion handles all movement; the stack is restored with elements in the correct order as the calls return.
     * <p>
     * Why each line matters:
     * - `if (!stack.isEmpty()) { ... }`: Base case—when stack is empty, recursion stops.
     * - `int temp = stack.pop();`: Pops the current element to be inserted later.
     * - `sortStack(stack);`: Recursively sorts the remaining stack.
     * - `insert(stack, temp);`: Inserts the element back into its sorted place recursively.
     * - In insert: places `temp` at the right spot, or recurses deeper if necessary.
     * <p>
     * Edge Cases Handled:
     * - Empty stack: Nothing happens, code is safe.
     * - One-element stack: Already sorted, recursion returns immediately.
     * - Duplicate values: All instances are preserved and sorted correctly.
     * <p>
     * Example:
     * Input: [4, 1, 3, 2] (with 4 at top)
     * Output: [4, 3, 2, 1] (with 4 at top)
     * <p>
     * Time Complexity:
     * - O(N^2): Each insertion may pop/push nearly all N elements in the worst case.
     * <p>
     * Space Complexity:
     * - O(N): Due to recursion call stack.
     * <p>
     * Limitations:
     * - Must not use loops or extra stacks—pure recursion and only provided stack operations.
     * - For large stacks (N near 100), recursion depth is acceptable, but deeper stacks may hit call stack limits.
     */

    public void sortStack(Stack<Integer> stack) {
        if (!stack.isEmpty()) {
            int temp = stack.pop();
            sortStack(stack);
            insert(stack, temp);
        }
    }

    public void insert(Stack<Integer> stack, int temp) {
        if (stack.isEmpty() || stack.peek() <= temp) {
            stack.push(temp);
            return;
        }

        // Pop the top element and recursively insert
        int val = stack.pop();
        insert(stack, temp);

        // Push the popped element back
        stack.push(val);
    }


}
