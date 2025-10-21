package collection.linkedlist.medium_problems;

import collection.linkedlist.MyLinkedList;

/*
Leetcode 876: Middle of the Linked List

Given the head of a singly linked list, return the middle node of the linked list.
If there are two middle nodes, return the second middle node.

Example 1:
Input: head = [1,2,3,4,5]
Output: [3,4,5]
Explanation: The middle node of the list is node 3.

Example 2:
Input: head = [1,2,3,4,5,6]
Output: [4,5,6]
Explanation: Since the list has two middle nodes with values 3 and 4, we return the second one.

Constraints:
            The number of nodes in the list is in the range [1, 100].
            1 <= Node.val <= 100
 */
public class MiddleOfLinkedList {

    private static Node head;
    private Node tail;
    private int size;
    public MiddleOfLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {

        MiddleOfLinkedList list = new MiddleOfLinkedList();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        list.insert(60);
        System.out.println(findMiddleofLinkedList().data);

    }

    /**
     * What it does:
     * Efficiently finds the middle node of a singly linked list using the fast-and-slow pointer technique.
     * If the list contains an even number of nodes, it returns the second of the two middle nodes.
     *
     * <p>
     * Why it works:
     * - By advancing the 'fast' pointer two steps for every one step of the 'slow' pointer, 'slow' reaches the middle when 'fast' reaches the end.
     * - This ensures that for even-numbered lists, the second middle node is returned, matching common interview requirements.
     *
     * <p>
     * How it works:
     * 1. Initialize three pointers: 'temp', 'slow', and 'fast' to the head of the linked list.
     * 2. While both 'fast' and 'fast.next' are not null:
     *    - Advance 'slow' by one node.
     *    - Advance 'fast' by two nodes.
     * 3. When the loop ends, 'slow' will point to the middle node (second middle for even-size lists).
     * 4. Return the 'slow' node.
     *
     * <p>
     * Edge Cases:
     * - Returns the correct node for lists with only one node.
     * - Correctly handles both odd- and even-length lists by always returning the second middle node for even.
     *
     * <p>
     * Time Complexity:
     * - O(n): Traverses at most the full length of the list.
     *
     * Space Complexity:
     * - O(1): Uses only three pointers, no extra memory proportional to the input size.
     *
     * <p>
     * Output:
     * - Returns a reference to the middle node of the linked list. If the list has an even number of nodes, returns the second middle node.
     */
    public static Node findMiddleofLinkedList() {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //insert a node in order
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

    public static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }

}
