package collection.stack;

/**
 * What it does:
 * <p>
 * Implements a Stack data structure using a fixed-size array.
 * The stack follows the LIFO principle, which means
 * Last In First Out.
 * <p>
 * Core idea of stack:
 * A stack allows insertion and deletion from only one end,
 * called the top of the stack. The main operations supported are push, pop, peek, size, and isEmpty.
 * <p>
 * Why an array is used:
 * An array provides constant time access to elements using an index.
 * By keeping track of the top index, we can simulate stack behavior efficiently.
 * <p>
 * Explanation of instance variables:
 * <p>
 * - stack array stores the actual stack elements.
 * <p>
 * - size represents the current number of elements in the stack.
 * <p>
 * - top stores the index of the top element in the stack.
 * <p>
 * It starts from -1 to indicate that the stack is initially empty.
 * <p>
 * Constructor explanation:
 * The constructor initializes the stack array with a fixed capacity.
 * This capacity decides the maximum number of elements the stack can hold.
 * <p>
 * Explanation of push operation:
 * <p>
 * - Push inserts an element at the top of the stack.
 * <p>
 * - First, it checks whether the stack is full.
 * <p>
 * - If the stack is full, stack overflow occurs and insertion is not allowed.
 * <p>
 * - Otherwise, top is incremented and the new value is placed at that index.
 * <p>
 * - Size is incremented to reflect the new element.
 * <p>
 * Explanation of pop operation:
 * - Pop removes and returns the top element of the stack.
 * <p>
 * - First, it checks whether the stack is empty.
 * <p>
 * - If the stack is empty, stack underflow occurs.
 * <p>
 * - Otherwise, the value at the top index is returned.
 * <p>
 * - Top is decremented and size is reduced.
 * <p>
 * Explanation of top operation:
 * <p>
 * - Returns the element at the top of the stack without removing it.
 * <p>
 * - If the stack is empty, an error message is shown.
 * <p>
 * Explanation of size operation: Returns the current number of elements present in the stack.
 * <p>
 * Explanation of isEmpty operation: Checks whether the stack has zero elements. Returns true if size is zero, otherwise false.
 * <p>
 * Time complexity of operations:
 * Push operation runs in O(1) time.
 * Pop operation runs in O(1) time.
 * Top operation runs in O(1) time.
 * Size operation runs in O(1) time.
 * isEmpty operation runs in O(1) time.
 * <p>
 * Space complexity:
 * O(n), where n is the fixed size of the stack array.
 * <p>
 * Advantages of stack using array:
 * - Simple and easy to implement.
 * - All operations run in constant time.
 * - Memory usage is predictable because the size is fixed.
 * - Cache friendly due to contiguous memory allocation.
 * <p>
 * Disadvantages of stack using array:
 * - Fixed size leads to stack overflow if capacity is exceeded.
 * - Memory can be wasted if the allocated size is large but usage is small.
 * - Resizing the stack is not supported in this implementation.
 * <p>
 * Interview explanation summary:
 * This implementation uses an array and a top pointer to simulate stack behavior.
 * Push and pop operations move the top pointer accordingly.
 * The main limitation is fixed capacity, which can be solved using a dynamic array
 * or linked list based stack.
 */

public class StackImplUsingArray {

    private int[] stack;
    private int size;
    private int top = -1;

    public StackImplUsingArray(int size) {

        stack = new int[size];
    }

    public static void main(String[] args) {

        StackImplUsingArray stack = new StackImplUsingArray(5);
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        System.out.println(stack.top());
        System.out.println(stack.pop());
        System.out.println(stack.isEmpty());
        System.out.println(stack.size());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.top());

    }

    private void push(int val) {
        if (top >= size) {
            System.out.println("Stack Overflow");
            return;
        }
        stack[++top] = val;
        size++;
    }

    private int pop() {
        if (top < 0) {
            System.out.println("Stack Underflow");
            return -1;
        }
        size--;
        return stack[top--];
    }

    private int top() {
        if (top < 0) {
            System.out.println("The stack is empty.");
            return -1;
        }
        return stack[top];
    }

    private int size() {
        return size;
    }

    private boolean isEmpty() {
        return size == 0;
    }
}
