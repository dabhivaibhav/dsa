package collection.queue;

import java.util.Stack;

/*
Optimized version of Leetcode problem 232.
 */
public class QueueImpleUsingStackOptimized {


    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();

        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        myQueue.push(4);

        System.out.println();
        System.out.println("Removed element from queue is: " + myQueue.pop());
        System.out.println();
        System.out.println("Top element is: " + myQueue.peek());
        System.out.println("Removed element from queue is: " + myQueue.pop());
        System.out.println("Removed element from queue is: " + myQueue.pop());

        System.out.println();
        System.out.println("Top element is: " + myQueue.peek());
        System.out.println("Removed element from queue is: " + myQueue.pop());
        myQueue.pop();
        myQueue.peek();

    }

    /**
     * What it does:
     * Implements a queue using two stacks such that:
     * push runs in O(1)
     * pop runs in amortized O(1)
     * peek runs in amortized O(1)
     * empty runs in O(1)
     * <p>
     * Core idea:
     * Use two stacks:
     * - inputStack stores newly pushed elements
     * - outputStack provides the queue order for popping and peeking
     * <p>
     * inputStack maintains elements in the order they were inserted.
     * outputStack maintains elements in reverse order so the oldest element
     * becomes accessible at the top.
     * <p>
     * How the algorithm works:
     * Push:
     * - Always push into inputStack.
     * - This is O(1) because it is a single stack push.
     * <p>
     * Pop and peek:
     * - If outputStack is not empty, the front of the queue is at its top.
     * So pop and peek can be done directly from outputStack.
     * - If outputStack is empty, transfer all elements from inputStack to outputStack.
     * This reversal makes the oldest element appear at the top of outputStack.
     * After transfer, pop or peek is done from outputStack.
     * <p>
     * Why this gives amortized O(1):
     * Each element is pushed once into inputStack.
     * Each element is transferred at most once from inputStack to outputStack.
     * Each element is popped once from outputStack.
     * So across many operations, the total work per element is constant.
     * A single pop may trigger a full transfer which is O(n),
     * but that transfer work is spread out over many operations.
     * <p>
     * Edge cases handled:
     * - pop on empty queue returns -1.
     * - peek on empty queue returns -1.
     * - empty checks both stacks.
     * <p>
     * Complexity:
     * Time:
     * push: O(1)
     * pop: amortized O(1), worst case O(n) when transfer happens
     * peek: amortized O(1), worst case O(n) when transfer happens
     * empty: O(1)
     * Space: O(n)
     * <p>
     * Interview explanation summary:
     * I keep pushing into inputStack in constant time.
     * For pop and peek, I only transfer elements when outputStack is empty.
     * That one transfer reverses the order so the oldest element becomes accessible.
     * Each element moves stacks at most once, so the average cost is constant.
     */
    private static class MyQueue {

        private final Stack<Integer> inputStack = new Stack<>();
        private final Stack<Integer> outputStack = new Stack<>();

        public void push(int x) {
            inputStack.push(x);
        }

        public int pop() {
            if (empty()) {
                System.out.println("Queue is empty");
                return -1;
            }
            shiftIfNeeded();
            return outputStack.pop();
        }

        public int peek() {
            if (empty()) {
                System.out.println("Queue is empty");
                return -1;
            }
            shiftIfNeeded();
            return outputStack.peek();
        }

        public boolean empty() {
            return inputStack.isEmpty() && outputStack.isEmpty();
        }

        private void shiftIfNeeded() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
        }

    }

}
