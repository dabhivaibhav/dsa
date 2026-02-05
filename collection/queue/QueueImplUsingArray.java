package collection.queue;

/**
 * What it does:
 * Implements a queue data structure using a fixed-size array
 * with the circular queue technique.
 * The queue follows the FIFO principle, meaning First In First Out.
 * <p>
 * Core idea of the queue:
 * A queue allows insertion only at the rear end
 * and removal only from the front end.
 * The element inserted first is removed first.
 * <p>
 * Why circular queue is used:
 * In a normal array-based queue, once elements are removed from the front,
 * the freed space at the beginning cannot be reused.
 * A circular queue solves this problem by reusing array positions
 * using modulo arithmetic, allowing efficient use of space.
 * <p>
 * Explanation of instance variables:
 * - queue stores the elements of the queue.
 * - front points to the index of the front element.
 * - rear points to the index where the next element is inserted.
 * - currentSize tracks how many elements are currently in the queue.
 * <p>
 * Initial state meaning:
 * - front equals -1 and rear equals -1 indicate an empty queue.
 * - currentSize equals 0 confirms no elements are present.
 * <p>
 * Constructor explanation:
 * The constructor initializes the queue with a fixed capacity.
 * If the given size is less than or equal to zero,
 * the queue is considered invalid and initialization is stopped.
 * <p>
 * Explanation of push operation:
 * - First checks whether the queue is full using currentSize.
 * - If the queue is full, insertion is not allowed.
 * - If inserting the first element, front is set to 0.
 * - rear is moved forward using circular increment.
 * - The value is stored at the rear index.
 * - currentSize is incremented.
 * <p>
 * Explanation of pop operation:
 * - First checks whether the queue is empty.
 * - If empty, removal is not allowed.
 * - The value at the front index is returned.
 * - front is moved forward using circular increment.
 * - currentSize is decremented.
 * - If the queue becomes empty after removal,
 * front and rear are reset to -1 to restore the initial state.
 * <p>
 * Explanation of top operation:
 * - Returns the front element without removing it.
 * - If the queue is empty, a safe message is printed and a sentinel value is returned.
 * <p>
 * Explanation of size operation:
 * - Returns the number of elements currently present in the queue.
 * <p>
 * Explanation of isEmpty and isFull:
 * - isEmpty returns true when currentSize is zero.
 * - isFull returns true when currentSize equals queue capacity.
 * <p>
 * Explanation of printQueue:
 * - Prints only the logical elements of the queue.
 * - Starts from front and prints exactly currentSize elements.
 * - Uses circular traversal to maintain correct order.
 * - This avoids printing stale or unused array values.
 * <p>
 * Time complexity:
 * Push operation runs in O(1).
 * Pop operation runs in O(1).
 * Top operation runs in O(1).
 * Size operation runs in O(1).
 * isEmpty and isFull run in O(1).
 * <p>
 * Space complexity:
 * O(n), where n is the fixed capacity of the queue.
 * <p>
 * Advantages of this implementation:
 * - Efficient use of array space due to circular behavior.
 * - All operations execute in constant time.
 * - Simple and predictable memory usage.
 * - No shifting of elements is required.
 * <p>
 * Disadvantages:
 * - Fixed size, so the queue cannot grow dynamically.
 * - Requires careful index management to avoid logical errors.
 * <p>
 * Interview explanation summary:
 * This is a circular queue implemented using an array.
 * front tracks removal, rear tracks insertion, and modulo allows wrap-around.
 * Resetting front and rear after the last removal keeps the state clean.
 * This design avoids wasted space and supports constant-time operations.
 */

public class QueueImplUsingArray {

    private int[] queue;
    private int front = -1;
    private int rear = -1;
    private int currentSize;

    public QueueImplUsingArray(int size) {
        if (size <= 0) {
            System.out.println("Invalid size");
            return;
        }
        queue = new int[size];
    }

    public static void main(String[] args) {
        QueueImplUsingArray queue = new QueueImplUsingArray(4);
        queue.push(3);
        queue.push(2);
        queue.push(4);
        queue.printQueue();
        System.out.println("\nTop: " + queue.top());
        System.out.println("Popped value: " + queue.pop());
        System.out.println("Popped value: " + queue.pop());
        queue.printQueue();
        System.out.println();
        queue.push(1);
        queue.push(5);
        queue.printQueue();
        System.out.println("\nPopped value: " + queue.pop());
        System.out.println("Popped value: " + queue.pop());
        System.out.println("Popped value: " + queue.pop());
        System.out.println("Popped value: " + queue.pop());
        System.out.println(queue.size());
    }

    private void push(int val) {
        if (currentSize == queue.length) {
            System.out.println("Queue is full");
            return;
        }

        if (front == -1) {
            front = 0;
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = val;
        currentSize++;
    }

    private int pop() {
        if (currentSize == 0) {
            System.out.println("Queue is empty");
            return -1;
        }
        int value = queue[front];
        front = (front + 1) % queue.length;
        currentSize--;
        if (currentSize == 0) {
            front = rear = -1;
        }
        return value;
    }

    private int size() {
        System.out.println("Size of queue is: " + currentSize);
        return currentSize;
    }

    private boolean isEmpty() {
        return currentSize == 0;
    }

    private boolean isFull() {
        return currentSize == queue.length;
    }

    private int top() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return -1;
        }
        return queue[front];
    }

    private void printQueue() {
        if (currentSize == 0) {
            System.out.println("Queue is empty");
            return;
        }
        int index = front;
        for (int i = 0; i < currentSize; i++) {
            System.out.print(queue[index] + " ");
            index = (index + 1) % queue.length;
        }
    }
}
