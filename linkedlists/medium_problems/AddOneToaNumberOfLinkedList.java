package linkedlists.medium_problems;

/*
Add one to a number represented by LL
Given the head of a singly linked list representing a positive integer number. Each node of the linked list represents a
digit of the number, with the 1st node containing the leftmost digit of the number and so on. The task is to add one to
the value represented by the linked list and return the head of a linked list containing the final value.
The number will contain no leading zeroes except when the value represented is zero itself.

Examples:
Input: head -> 1 -> 2 -> 3
Output: head -> 1 -> 2 -> 4
Explanation: The number represented by the linked list = 123.
123 + 1 = 124.

Input: head -> 9 -> 9
Output: head -> 1 -> 0 -> 0
Explanation: The number represented by the linked list = 99.
99 + 1 = 100.

Constraints:
            0 <= number of nodes in the Linked List <= 10^5
            0 <= ListNode.val <= 9
            No leading zeroes in the value represented.
 */


public class AddOneToaNumberOfLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public AddOneToaNumberOfLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {
        AddOneToaNumberOfLinkedList list = new AddOneToaNumberOfLinkedList();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.showList();
        list.addOneNumberBruteForce(list.head);
        list.showList();
    }

    /**
     * addOneNumberBruteForce
     * <p>
     * What it does:
     * Adds one to the number represented by a singly linked list, where each node contains a single digit.
     * The digits are stored in normal order (most significant digit at the head). To simplify addition, the list is
     * first reversed so the least significant digit comes first, allowing traversal in the natural addition direction.
     * The method then increments the number by one, handling any carry propagation, and reverses the list again to
     * restore the original order.
     *
     * Intuition:
     * - Since the least significant digit is at the tail of the list, direct addition requires reaching the end first.
     *   Reversing the list brings the least significant digit to the head, making it easy to process.
     * - Adding one may propagate a carry across multiple nodes (e.g., 1→9→9 → 2→0→0).
     * - After performing the addition, reversing the list again restores the original digit order.
     *
     * Why each line matters:
     * - head = reverseLinkedList(head);
     *     Reverses the list so addition can start from the least significant digit.
     * - int carry = 1;
     *     Initializes carry as 1 to perform the “+1” operation.
     * - while (temp != null):
     *     Iterates through each node, updating digits and managing carry.
     * - temp.val = (temp.val + carry) % 10;
     *     Updates the current digit after addition.
     * - carry = (temp.val + carry) / 10;
     *     Calculates carry for the next digit.
     * - if (temp.next == null && carry > 0):
     *     Appends a new node if a carry remains after processing the last digit (e.g., 9→9 → 1→0→0).
     * - head = reverseLinkedList(head);
     *     Restores the list to its original order after completing the addition.
     * - this.head = head;
     *     Updates the main list’s head reference with the modified list.
     *
     * Edge Cases Handled:
     * - Empty list: No operation performed.
     * - Carry propagation: Correctly adds new node when carry remains (e.g., 9→9→9 → 1→0→0→0).
     * - Single-node list: Works for both small and large values (e.g., 5 → 6, 9 → 1→0).
     *
     * Example:
     * Input:  head → 1 → 2 → 3
     * Output: head → 1 → 2 → 4
     * Explanation: The number 123 becomes 124 after adding one.
     *
     * Input:  head → 9 → 9
     * Output: head → 1 → 0 → 0
     * Explanation: The number 99 becomes 100 after adding one.
     *
     * Time Complexity:
     * - O(n): Each node is visited a constant number of times during two reversals and one addition pass.
     *
     * Space Complexity:
     * - O(1): Performs all operations in place using constant extra space.
     */
    private void addOneNumberBruteForce(Node head) {

        head = reverseLinkedList(head);

        Node temp = head;
        int carry = 1;


        while (temp != null) {
            int sum = temp.val + carry;
            temp.val = sum % 10;
            carry = sum / 10;

            if (temp.next == null && carry > 0) {
                temp.next = new Node(carry);
                carry = 0;
            }

            temp = temp.next;
        }
        head = reverseLinkedList(head);
        this.head = head;
    }

    private void showList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    private Node reverseLinkedList(Node head) {
        Node temp = head;
        Node prev = null;

        while (temp != null) {
            Node front = temp.next;
            temp.next = prev;
            prev = temp;
            temp = front;
        }

        return prev;
    }

    private void insert(int val) {
        Node node = new Node(val);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    private static class Node {
        int val;
        Node next;

        Node(int x) {
            val = x;
            next = null;
        }

        Node(int x, Node next) {
            val = x;
            this.next = next;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }
}
