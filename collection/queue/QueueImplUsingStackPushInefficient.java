package collection.queue;


import java.util.Stack;

/*
Leetcode 232. Implement Queue using Stacks
Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the functions
of a normal queue (push, peek, pop, and empty).

Implement the MyQueue class:
void push(int x) Pushes element x to the back of the queue.
int pop() Removes the element from the front of the queue and returns it.
int peek() Returns the element at the front of the queue.
boolean empty() Returns true if the queue is empty, false otherwise.
Notes:
You must use only standard operations of a stack, which means only push to top, peek/pop from top, size, and is empty
operations are valid. Depending on your language, the stack may not be supported natively. You may simulate a stack using
a list or deque (double-ended queue) as long as you use only a stack's standard operations.

Example 1:
Input
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]
Output
[null, null, null, 1, 1, false]

Explanation
MyQueue myQueue = new MyQueue();
myQueue.push(1); // queue is: [1]
myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
myQueue.peek(); // return 1
myQueue.pop(); // return 1, queue is [2]
myQueue.empty(); // return false

Constraints:
            1 <= x <= 9
            At most 100 calls will be made to push, pop, peek, and empty.
            All the calls to pop and peek are valid.


Follow-up: Can you implement the queue such that each operation is amortized O(1) time complexity? In other words,
performing n operations will take overall O(n) time even if one of those operations may take longer.

 */
public class QueueImplUsingStackPushInefficient {

    public static void main(String[] args) {
        MyStack myStack = new MyStack();

        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        myStack.printQueue();
        System.out.println();
        System.out.println("Removed element from queue is: " + myStack.pop());
        myStack.printQueue();
        System.out.println();
        System.out.println("Top element is: " + myStack.peek());
        System.out.println("Removed element from queue is: " + myStack.pop());
        System.out.println("Removed element from queue is: " + myStack.pop());
        myStack.printQueue();
        System.out.println();
        System.out.println("Top element is: " + myStack.peek());
        System.out.println("Removed element from queue is: " + myStack.pop());
        myStack.pop();
        myStack.peek();
        myStack.printQueue();

    }

    /**
     * What it does:
     * Implements a queue using two stacks where push is the expensive operation.
     * The queue follows FIFO, meaning the first inserted element is the first removed.
     * <p>
     * Why stacks can simulate a queue:
     * A stack is LIFO, so one stack alone cannot directly behave like a queue.
     * By using two stacks and carefully moving elements between them during insertion,
     * we can maintain FIFO order.
     * <p>
     * Core idea of this version:
     * This implementation keeps the front of the queue at the top of stack1 at all times.
     * That means pop and peek can be done directly from stack1 in constant time.
     * To maintain this order, every push rearranges the elements.
     * <p>
     * Explanation of push operation:
     * - Move all elements from stack1 to stack2.
     * This temporarily clears stack1 and reverses the order into stack2.
     * - Push the new element into stack1.
     * Since stack1 is empty now, the new element goes to the bottom of the final queue order.
     * - Move all elements back from stack2 into stack1.
     * This restores the previous elements above the new element.
     * After this process, the oldest element is again at the top of stack1.
     * <p>
     * Why this preserves FIFO:
     * The oldest element is always kept on top of stack1.
     * So when we pop, we remove the oldest element first, which matches queue behavior.
     * <p>
     * Explanation of pop operation:
     * - If stack1 is empty, the queue is empty and pop is not possible.
     * - Otherwise, pop the top of stack1.
     * This returns the front of the queue in O(1).
     * <p>
     * Explanation of peek operation:
     * - If stack1 is empty, the queue is empty and peek is not possible.
     * - Otherwise, return the top of stack1 without removing it.
     * This gives the front element in O(1).
     * <p>
     * Explanation of empty operation:
     * Returns true if stack1 has no elements, meaning the queue is empty.
     * <p>
     * Explanation of printQueue operation:
     * - Prints elements in the order stored in stack1.
     * - Because the front of the queue is maintained at the top of stack1,
     * iterating over stack1 prints the queue in FIFO order.
     * <p>
     * Time complexity:
     * Push: O(n) because it moves all elements twice between stacks.
     * Pop: O(1) because it directly pops from stack1.
     * Peek: O(1) because it directly peeks from stack1.
     * Empty: O(1)
     * Print: O(n)
     * <p>
     * Space complexity:
     * O(n) for storing n elements across the two stacks.
     * <p>
     * Advantages:
     * - Pop and peek are fast and constant time.
     * - The queue front is always accessible at the top of stack1.
     * <p>
     * Disadvantages:
     * - Push is slow because it rearranges the full structure every time.
     * - Uses extra stack storage.
     * - Returning -1 for empty pop and peek can be ambiguous if -1 is a valid value.
     * <p>
     * Interview explanation summary:
     * This is the push expensive queue implementation.
     * I maintain the front element at the top of stack1 so that pop and peek are O(1).
     * To do that, each push moves all existing elements out, inserts the new element,
     * then restores the previous elements back on top.
     */

    private static class MyStack {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();


        public void push(int x) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            stack1.push(x);

            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
        }

        public int pop() {
            if (stack1.isEmpty()) {
                System.out.println("Queue is empty");
                return -1;
            }
            return stack1.pop();
        }

        public int peek() {
            if (stack1.isEmpty()) {
                System.out.println("Queue is empty");
                return -1;
            }
            return stack1.peek();
        }

        public boolean empty() {
            return stack1.isEmpty();
        }

        public void printQueue() {
            if (stack1.isEmpty()) {
                System.out.println("Queue is empty");
                return;
            }
            System.out.print("Queue: ");
            for (Integer i : stack1) {
                System.out.print(i + " ");
            }
        }
    }
}
