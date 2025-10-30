package linkedlists.medium_problems;


/*
206. Reverse Linked List
Given the head of a singly linked list, reverse the list, and return the reversed list.

Example 1:
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]

Example 2:
Input: head = [1,2]
Output: [2,1]

Example 3:
Input: head = []
Output: []

Constraints:
            The number of nodes in the list is the range [0, 5000].
            -5000 <= Node.val <= 5000

Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
 */

import java.util.LinkedList;
import java.util.Stack;

public class ReverseSinglyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public ReverseSinglyLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {
        ReverseSinglyLinkedList list = new ReverseSinglyLinkedList();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.insert(4);
        list.insert(5);
        ReverseSinglyLinkedList list1 = new ReverseSinglyLinkedList();
        list1.insert(1);
        list1.insert(2);
        list1.insert(3);
        list1.insert(4);
        list1.insert(5);
        ReverseSinglyLinkedList list2 = new ReverseSinglyLinkedList();
        list2.insert(1);
        list2.insert(2);
        list2.insert(3);
        list2.insert(4);
        list2.insert(5);
        System.out.println(reverseLinkedListBruteForce(list.head));
        System.out.println(reverseLinkedListOptimal(list1.head));
        System.out.println(reverseLinkedListIterative(list2.head));


    }

    /**
     * reverseLinkedListBruteForce
     * <p>
     * What it does:
     * Reverses a singly linked list by pushing every node’s value onto a stack and then popping to rewrite the nodes in reverse order.
     * Returns a LinkedList containing reversed values.
     * <p>
     * Intuition:
     * - Using a stack exploits LIFO order: first node pushed will be the last node popped, perfectly reversing the order.
     * - We mutate the linked list by overwriting node values, not by changing pointers.
     * <p>
     * Why each line matters:
     * - Stack<Integer> stack = new Stack<>();
     *   Temporary structure to hold values for reversal.
     * - First while loop: push all values to stack.
     *   Walk through the list from head, accumulate values in stack.
     * - Second while loop: pop from stack and assign to linked list nodes.
     *   Each pop gives the next reversed value; store in LinkedList for result.
     * <p>
     * Example:
     * Input: 1 → 2 → 3 → 4 → 5
     * After both passes: 5 → 4 → 3 → 2 → 1
     * <p>
     * Time Complexity:
     * - O(n): Two linear passes through the list.
     * <p>
     * Space Complexity:
     * - O(n): Stack and LinkedList both grow with input size.
     */

    private static LinkedList<Integer> reverseLinkedListBruteForce(Node head) {
        Stack<Integer> stack = new Stack<>();
        Node temp = head;
        while (temp != null) {
            stack.push(temp.data);
            temp = temp.next;
        }

        LinkedList<Integer> reversed = new LinkedList<>();
        temp = head;
        while (temp != null) {
            temp.data = stack.pop();
            reversed.add(temp.data);
            temp = temp.next;
        }
        return reversed;
    }

    /**
     * reverseLinkedListOptimal
     * <p>
     * What it does:
     * Reverses a singly linked list in-place using iterative pointer manipulation.
     * Returns new head of the reversed list.
     * <p>
     * Intuition:
     * - The best way to reverse pointers is to walk through the list,
     * redirecting each node’s next pointer to the previous node (instead of the next).
     * - After iteration, previous will point to the new head.
     * <p>
     * Why each line matters:
     * - Node previous = null;
     *   Previous node begins as null (end of reversed list).
     * - while (temp != null) { ... }
     *   Iterate through every node in original order.
     * - Node front = temp.next;
     *   Save reference to next node before reversal.
     * - temp.next = previous;
     *   Point current node backwards (essential reversal step).
     * - previous = temp; temp = front;
     *   Advance for next iteration.
     * <p>
     * Example:
     * Input: 1 → 2 → 3 → 4 → 5
     * Steps: 1 points to null, 2 to 1, 3 to 2, 4 to 3, 5 to 4; previous will be 5 (new head).
     * <p>
     * Time Complexity:
     * - O(n): Single pass through the list.
     * <p>
     * Space Complexity:
     * - O(1): All reversal done via constant extra space.
     */
    private static Node reverseLinkedListOptimal(Node head) {

        Node temp = head;
        Node previous = null;
        while (temp != null) {
            Node front = temp.next;
            temp.next = previous;
            previous = temp;
            temp = front;
        }
        return previous;
    }

    /**
     * reverseLinkedListIterative
     * <p>
     * What it does:
     * Recursively reverses a singly linked list: each call reverses the rest of the list and adjusts pointers.
     * Returns new head of the reversed list.
     * <p>
     * Intuition:
     * - Recursive approach: reverse everything after the current node first,
     * then set the next node’s next pointer to current, breaking original link.
     * - Build up the new head from the deepest recursion stack frame.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return head;
     *   Base case: empty or single-node list, no change needed.
     * - Node newHead = reverseLinkedListIterative(head.next);
     *   Recursion: get the head of reversed sub-list.
     * - Node front = head.next; front.next = head;
     *   Rewire: next node’s next points back to current node.
     * - head.next = null;
     *   Remove old link (otherwise cycles).
     * - return newHead;
     *   Propagate new head upward.
     * <p>
     * Example:
     * Input: 1 → 2 → 3 → 4 → 5
     * Recursive return: 5 → 4 → 3 → 2 → 1
     * <p>
     * Time Complexity:
     * - O(n): Each node processed once.
     * <p>
     * Space Complexity:
     * - O(n): Recursive call stack depth.
     */
    private static Node reverseLinkedListIterative(Node head) {

        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = reverseLinkedListIterative(head.next);
        Node front = head.next;
        front.next = head;
        head.next = null;
        return newHead;

    }

    public int size() {
        return size;
    }

    public void insert(int val) {
        Node node = new Node(val);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size += 1;
    }

    private static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }

        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }


        @Override
        public String toString() {
            return data + "";
        }
    }
}
