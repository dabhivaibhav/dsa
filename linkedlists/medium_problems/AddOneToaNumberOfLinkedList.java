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
        list.addOneNumberBruteForce(list.head);

        AddOneToaNumberOfLinkedList list1 = new AddOneToaNumberOfLinkedList();
        list1.insert(1);
        list1.insert(2);
        list1.insert(3);
        list1.addOneNumberOptimalApproach(list1.head);
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
     * <p>
     * Intuition:
     * - Since the least significant digit is at the tail of the list, direct addition requires reaching the end first.
     * Reversing the list brings the least significant digit to the head, making it easy to process.
     * - Adding one may propagate a carry across multiple nodes (e.g., 1→9→9 → 2→0→0).
     * - After performing the addition, reversing the list again restores the original digit order.
     * <p>
     * Why each line matters:
     * - head = reverseLinkedList(head);
     * Reverses the list so addition can start from the least significant digit.
     * - int carry = 1;
     * Initializes carry as 1 to perform the “+1” operation.
     * - while (temp != null):
     * Iterates through each node, updating digits and managing carry.
     * - temp.val = (temp.val + carry) % 10;
     * Updates the current digit after addition.
     * - carry = (temp.val + carry) / 10;
     * Calculates carry for the next digit.
     * - if (temp.next == null && carry > 0):
     * Appends a new node if a carry remains after processing the last digit (e.g., 9→9 → 1→0→0).
     * - head = reverseLinkedList(head);
     * Restores the list to its original order after completing the addition.
     * - this.head = head;
     * Updates the main list’s head reference with the modified list.
     * <p>
     * Edge Cases Handled:
     * - Empty list: No operation performed.
     * - Carry propagation: Correctly adds new node when carry remains (e.g., 9→9→9 → 1→0→0→0).
     * - Single-node list: Works for both small and large values (e.g., 5 → 6, 9 → 1→0).
     * <p>
     * Example:
     * Input:  head → 1 → 2 → 3
     * Output: head → 1 → 2 → 4
     * Explanation: The number 123 becomes 124 after adding one.
     * <p>
     * Input:  head → 9 → 9
     * Output: head → 1 → 0 → 0
     * Explanation: The number 99 becomes 100 after adding one.
     * <p>
     * Time Complexity:
     * - O(n): Each node is visited a constant number of times during two reversals and one addition pass.
     * <p>
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

    /**
     * addOneNumberOptimalApproach
     * <p>
     * What it does:
     * Adds one to the number represented by a singly linked list using a recursive approach.
     * Each node contains a single digit, with the most significant digit at the head.
     * The recursion travels to the end of the list (least significant digit), adds one,
     * and then propagates the carry backward through the recursive call stack.
     * If a carry remains after processing the most significant digit, a new node is added at the front.
     *
     * Intuition:
     * - The challenge in adding one is that the least significant digit lies at the end of the list.
     *   Instead of reversing the list, recursion naturally reaches the tail first and returns upward,
     *   mimicking right-to-left addition.
     * - Each recursive return step handles carry propagation cleanly, updating node values in place.
     * - If the final carry after recursion is non-zero (e.g., all digits were 9),
     *   a new head node is added to represent the overflow (e.g., 9→9→9 → 1→0→0→0).
     *
     * Why each line matters:
     * - int carry = addOneHelper(head);
     *     Starts the recursion from the head and receives the carry propagated back from the tail.
     * - if (carry != 0):
     *     Checks if an extra carry remains after processing all digits.
     * - Node newHead = new Node(carry);
     *     Creates a new node for the overflow carry (if needed).
     * - newHead.next = head; head = newHead;
     *     Prepends the new node to the front of the list to form the updated number.
     *
     * Inner Helper Method (addOneHelper):
     * - if (node == null) return 1;
     *     Base case: when recursion reaches beyond the last node, returns 1 to represent "+1".
     * - int carry = addOneHelper(node.next);
     *     Recursively processes the next node, returning the carry from deeper calls.
     * - int sum = node.val + carry;
     *     Adds the carry to the current digit.
     * - node.val = sum % 10;
     *     Updates the current node’s digit after addition.
     * - return sum / 10;
     *     Returns the new carry to be added to the previous node.
     *
     * Edge Cases Handled:
     * - Empty list: Returns a single node with value 1.
     * - All digits are 9: Creates a new head node to handle overflow (e.g., 9→9→9 → 1→0→0→0).
     * - Single node: Correctly handles both with and without carry (e.g., 4 → 5, 9 → 1→0).
     *
     * Example:
     * Input:  head → 1 → 2 → 9
     * Output: head → 1 → 3 → 0
     * Explanation: The number 129 becomes 130.
     *
     * Input:  head → 9 → 9
     * Output: head → 1 → 0 → 0
     * Explanation: The number 99 becomes 100.
     *
     * Time Complexity:
     * - O(n): Each node is visited exactly once during recursion.
     *
     * Space Complexity:
     * - O(n): Due to the recursion call stack (one frame per node).
     */
    private void addOneNumberOptimalApproach(Node head) {
        int carry = addOneHelper(head);

        if (carry != 0) {
            Node newHead = new Node(carry);
            newHead.next = head;
            head = newHead;
        }

    }

    int addOneHelper(Node node) {
        if (node == null) return 1;
        int carry = addOneHelper(node.next);
        int sum = node.val + carry;
        node.val = sum % 10;
        return sum / 10;
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
