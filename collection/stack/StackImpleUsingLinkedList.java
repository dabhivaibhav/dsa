package collection.stack;

/**
 * What it does:
 * Implements a stack using a singly linked list.
 * The stack follows the LIFO rule, meaning the last inserted element
 * is the first one removed.
 * <p>
 * Why a linked list is used:
 * A linked list based stack does not have a fixed capacity like an array stack.
 * It grows and shrinks dynamically based on how many elements are pushed and popped.
 * This makes it a good choice when the maximum size is unknown.
 * <p>
 * Explanation of instance variables:
 * - top stores a reference to the node at the top of the stack.
 * If top is null, the stack is empty.
 * - size stores the number of elements currently present in the stack.
 * <p>
 * Explanation of the Node structure:
 * Each Node stores two things:
 * - data, which is the actual value pushed into the stack
 * - next, which points to the node below the current node
 * This forms a chain of nodes where top always points to the first node.
 * <p>
 * Explanation of push operation:
 * - A new node is created for the incoming data.
 * - The new node next pointer is set to the current top.
 * This links the new node on top of the existing stack.
 * - top is updated to this new node.
 * - size is incremented.
 * This works in O(1) time because it only updates a few pointers.
 * <p>
 * Explanation of pop operation:
 * - First, it checks if top is null.
 * If yes, the stack is empty and pop is not possible, so it returns a sentinel value.
 * - Otherwise, it stores the top node data as the result.
 * - oldTop temporarily holds the current top node.
 * - top is moved to the next node, effectively removing the current top node.
 * - oldTop next is set to null to break the link immediately.
 * This is not required in Java for correctness, but it is a clean practice.
 * - size is decremented and the popped value is returned.
 * This also runs in O(1) time.
 * <p>
 * Explanation of peek operation:
 * - Peek returns the value currently at the top of the stack without removing it.
 * - If the stack is empty, it returns a sentinel value.
 * This runs in O(1) time.
 * <p>
 * Explanation of size operation:
 * Returns the size variable, which tracks how many elements are currently stored.
 * This is O(1) because it does not traverse the list.
 * <p>
 * Explanation of isEmpty operation:
 * Returns true if top is null, meaning no elements exist in the stack.
 * This is O(1).
 * <p>
 * Explanation of print operation:
 * - If the stack is empty, it prints a message and stops.
 * - Otherwise, it starts from top and traverses using next pointers.
 * - It prints each node data until it reaches the end.
 * This runs in O(n) time because it visits every node once.
 * <p>
 * Time complexity:
 * Push:  O(1)
 * Pop:   O(1)
 * Peek:  O(1)
 * Size:  O(1)
 * isEmpty: O(1)
 * Print: O(n)
 * <p>
 * Space complexity:
 * O(n), where n is the number of elements stored in the stack.
 * Each element requires one node in memory.
 * <p>
 * Advantages:
 * - No fixed capacity, so it grows dynamically.
 * - Push and pop operations are always O(1).
 * - No wasted array capacity because memory is allocated only when needed.
 * <p>
 * Disadvantages:
 * - Extra memory overhead because each element stores a next pointer.
 * - Not cache friendly compared to array implementations since nodes are scattered in memory.
 * - Returning -1 for empty pop or peek can be ambiguous if -1 is a valid stack value.
 * In production code, throwing an exception or returning an optional type is often preferred.
 * <p>
 * Interview explanation summary:
 * This stack uses a linked list where top points to the head node.
 * Push inserts at the head, pop removes from the head, and peek reads from the head.
 * Because all operations modify or access only the head pointer,
 * push, pop, and peek run in constant time.
 */

public class StackImpleUsingLinkedList {

    private Node top;
    private int size;

    public StackImpleUsingLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {
        StackImpleUsingLinkedList stack = new StackImpleUsingLinkedList();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.print();
        System.out.println("Popped element is: " + stack.pop());
        System.out.println("Popped element is: " + stack.pop());
        stack.push(4);
        System.out.println("Popped element is: " + stack.pop());
        System.out.println("Peek element is: " + stack.peek());
        System.out.println("Size of stack is: " + stack.size());
        stack.print();

    }

    private void push(int data) {
        Node node = new Node(data);
        node.next = top;
        top = node;
        size++;
    }

    private int pop() {
        if (top == null) {
            System.out.println("Stack is empty");
            return -1;
        }
        int data = top.data;
        Node oldTop = top;
        top = top.next;
        oldTop.next = null;
        size--;
        return data;
    }

    private int peek() {
        if (top == null) {
            System.out.println("Stack is empty");
            return -1;
        }
        return top.data;
    }

    private int size() {
        return size;
    }

    private boolean isEmpty() {
        return top == null;
    }

    private void print() {
        if (top == null) {
            System.out.println("Stack is empty");
            return;
        }
        Node temp = top;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    private class Node {
        public Node next;
        private int data;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public String toString() {
            return data + "";
        }
    }


}
