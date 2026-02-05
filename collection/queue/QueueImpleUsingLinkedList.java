package collection.queue;

/**
 * What it does:
 * Implements a queue using a singly linked list.
 * The queue follows the FIFO rule, meaning the first element inserted
 * is the first element removed.
 * <p>
 * Why a linked list is used:
 * A linked list allows the queue to grow dynamically without any fixed capacity.
 * Unlike an array based queue, there is no risk of overflow due to limited size.
 * <p>
 * Explanation of instance variables:
 * - top represents the front of the queue.
 * This is the element that will be removed next.
 * - end represents the rear of the queue.
 * This is where new elements are inserted.
 * - size keeps track of how many elements are currently in the queue.
 * <p>
 * Explanation of push operation:
 * - A new node is created for the value to be inserted.
 * - If the queue is empty, both top and end are set to the new node.
 * - If the queue is not empty, the new node is linked to the current end,
 * and end is updated to point to the new node.
 * - Size is incremented after insertion.
 * This ensures insertion happens in constant time.
 * <p>
 * Explanation of pop operation:
 * - If the queue is empty, removal is not possible and a message is printed.
 * - Otherwise, the value at the front is stored as the removed element.
 * - top is moved to the next node, removing the front element from the queue.
 * - The reference of the removed node is cleared.
 * - Size is decremented.
 * - If the queue becomes empty after removal, end is reset to null.
 * This ensures removal also happens in constant time.
 * <p>
 * Explanation of peek operation:
 * - Returns the front element without removing it.
 * - If the queue is empty, a message is printed and a sentinel value is returned.
 * <p>
 * Explanation of size and isEmpty operations:
 * - size returns the number of elements currently present in the queue.
 * - isEmpty returns true if size is zero.
 * <p>
 * Explanation of print operation:
 * - Traverses the queue from front to rear using next pointers.
 * - Prints each element in insertion order.
 * This traversal takes linear time.
 * <p>
 * Time complexity:
 * Push runs in O(1).
 * Pop runs in O(1).
 * Peek runs in O(1).
 * Size runs in O(1).
 * Print runs in O(n).
 * <p>
 * Space complexity:
 * O(n), where n is the number of elements stored in the queue.
 * <p>
 * Advantages:
 * - Dynamic size with no fixed capacity.
 * - All queue operations except print run in constant time.
 * - Simple and intuitive implementation.
 * <p>
 * Disadvantages:
 * - Uses extra memory for node pointers.
 * - Not cache friendly compared to array based implementations.
 * - Returning -1 for peek on empty queue can be ambiguous if -1 is a valid value.
 * <p>
 * Interview explanation summary:
 * This queue uses a linked list with front and rear pointers.
 * Enqueue inserts at the rear, dequeue removes from the front,
 * and both operations are O(1) because no shifting is required.
 */

public class QueueImpleUsingLinkedList {

    private Node top;
    private Node end;
    private int size;

    public QueueImpleUsingLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {
        QueueImpleUsingLinkedList queue = new QueueImpleUsingLinkedList();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.print();
        System.out.println();
        queue.pop();
        System.out.println("Top element is: " + queue.peek());
        System.out.println("Size is: " + queue.size());
        queue.push(1);
        queue.print();
        System.out.println();
        System.out.println("Size is: " + queue.size());
    }

    private void push(int val) {
        Node node = new Node(val);
        if (top == null) {
            top = node;
            end = node;
        } else {
            end.next = node;
            end = node;
        }
        System.out.println("Added element is: " + val);
        size++;
    }

    private void pop() {
        if (top == null) {
            System.out.println("Queue is empty");
            return;
        }
        int data = top.data;
        Node oldTop = top;
        top = top.next;
        oldTop.next = null;
        size--;
        if (size == 0) {
            end = null;
        }
        System.out.println("Removed element is: " + data);
    }

    private int peek() {
        if (top == null) {
            System.out.println("Queue is empty");
            return -1;
        }
        return top.data;
    }

    private int size() {
        return size;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private void print() {
        Node node = top;
        while (node != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }

    private static class Node {
        private final int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }
}
