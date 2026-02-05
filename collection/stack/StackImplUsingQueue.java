package collection.stack;

import java.util.LinkedList;
import java.util.Queue;

/*
Leetcode 225. Implement Stack using Queues
Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions
 of a normal stack (push, top, pop, and empty).

Implement the MyStack class:
void push(int x) Pushes element x to the top of the stack.
int pop() Removes the element on the top of the stack and returns it.
int top() Returns the element on the top of the stack.
boolean empty() Returns true if the stack is empty, false otherwise.
Notes:
You must use only standard operations of a queue, which means that only push to back, peek/pop from front, size and is
empty operations are valid. Depending on your language, the queue may not be supported natively. You may simulate a queue
using a list or deque (double-ended queue) as long as you use only a queue's standard operations.


Example 1:
Input:
["MyStack", "push", "push", "top", "pop", "empty"]
[[], [1], [2], [], [], []]
Output:
[null, null, null, 2, 2, false]

Explanation
MyStack myStack = new MyStack();
myStack.push(1);
myStack.push(2);
myStack.top(); // return 2
myStack.pop(); // return 2
myStack.empty(); // return False

Constraints:
            1 <= x <= 9
            At most 100 calls will be made to push, pop, top, and empty.
            All the calls to pop and top are valid.
 */
public class StackImplUsingQueue {


    public static void main(String[] args) {

        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        myStack.print();
        System.out.println("\nRemoved element from the queue: " + myStack.pop());
        System.out.println("Removed element from the queue: " + myStack.pop());
        myStack.print();

    }


    /**
     * What it does:
     * Implements a stack using a single queue.
     * The stack follows the LIFO rule, meaning the last inserted element
     * is the first element removed.
     *
     * Why a queue can simulate a stack:
     * A queue naturally works in FIFO order, so to simulate a stack we must
     * rearrange elements after every push so that the most recently added element
     * always stays at the front of the queue.
     * Then pop and top become the same as removing or viewing the front.
     *
     * Core idea of the algorithm:
     * Every time a new element is pushed, it is first added to the queue.
     * Then all the previous elements are moved behind it by rotating the queue.
     * After this rotation, the new element becomes the front of the queue.
     * This ensures that the front always behaves like the top of a stack.
     *
     * Explanation of push operation:
     * - Store the current size of the queue before insertion.
     *   This size tells how many elements existed before adding the new element.
     * - Add the new element to the queue.
     * - Rotate the queue size times:
     *   In each rotation, remove the front element and add it to the rear.
     *   This moves all older elements behind the newly added element.
     * - After rotations, the newest element is at the front and behaves like stack top.
     *
     * Explanation of pop operation:
     * - If the queue is empty, pop is not possible and returns a sentinel value.
     * - Otherwise remove and return the front element.
     * Since the newest element is always at the front, this matches stack pop.
     *
     * Explanation of top operation:
     * - Returns the front element without removing it.
     * - Must check emptiness first to avoid errors when the queue is empty.
     *
     * Explanation of isEmpty operation:
     * - Returns whether the queue has no elements.
     *
     * Explanation of print operation:
     * - Iterates through the queue and prints elements from front to rear.
     * - Because the newest element is kept at the front, printing shows the stack
     *   from top to bottom.
     *
     * Time complexity:
     * Push: O(n) because it rotates the queue to move older elements behind the new one.
     * Pop: O(1) because it removes from the front.
     * Top: O(1) because it reads the front element.
     * isEmpty: O(1)
     * Print: O(n)
     *
     * Space complexity:
     * O(n) for storing n elements in the queue.
     *
     * Advantages:
     * - Uses only one queue to implement a stack.
     * - Pop operation is efficient and constant time.
     * - Simple logic once the rotation idea is understood.
     *
     * Disadvantages:
     * - Push becomes linear time because each push rearranges the queue.
     * - Returning -1 for pop and top can be ambiguous if -1 is a valid value.
     * - Needs an empty check in top to avoid null unboxing errors.
     *
     * Interview explanation summary:
     * This is a one queue stack implementation where push is made expensive.
     * Each push rotates older elements behind the new element so that the newest element
     * always remains at the front. Then pop and top are simply queue poll and peek.
     */
    private static class MyStack {
        private final Queue<Integer> queue = new LinkedList<>();

        public void push(int x) {
            int size = queue.size();
            queue.add(x);
            for (int i = 0; i < size; i++) {
                queue.add(queue.poll());
            }
            System.out.println("Added element to the queue: " + x);
        }


        public int pop() {
            if (queue.isEmpty()) return -1;
            return queue.poll();
        }


        public int top() {
            if (queue.isEmpty()) return -1;
            return queue.peek();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public void print() {
            for (Integer item : queue) {
                System.out.print(item + " ");
            }
        }
    }
}
