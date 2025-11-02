package linkedlists.medium_problems;

/*
Delete all occurrences of a key in DLL
Given the head of a doubly linked list and an integer target. Delete all nodes in the linked list with the value target
and return the head of the modified linked list.

Examples:
Input: head -> 1 <-> 2 <-> 3 <-> 1 <-> 4, target = 1
Output: head -> 2 <-> 3 <-> 4
Explanation: All nodes with the value 1 were removed.

Input: head -> 2 <-> 3 <-> -1 <-> 4 <-> 2, target = 2
Output: head -> 3 <-> -1 <-> 4
Explanation: All nodes with the value 2 were removed.
Note that the value of head is changed.

Constraints:
            0 <= number of nodes in the linked list <= 10^5
            -10^4 <= ListNode.val <= 10^4
            -10^4 <= target <= 10^4
 */
public class DeleteKeyInDLL {

    private Node head;
    private Node tail;
    private int size;

    public DeleteKeyInDLL() {
        this.size = 0;
    }

    public static void main(String[] args) {
        DeleteKeyInDLL list = new DeleteKeyInDLL();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(10);
        list.insert(50);
        list.insert(10);
        System.out.print("Original List: ");
        list.showList();
        int target = 10;
        // fixing head and tail after removing the nodes
        list.head = deleteKeyInLinkedList(list.head, target);
        list.tail = list.head;
        while (list.tail != null && list.tail.next != null) {
            list.tail = list.tail.next;
        }
        System.out.print("Modified List: ");
        list.showList();
    }

    /**
     * deleteKeyInLinkedList
     *
     * <p>
     * What it does:
     * Deletes all nodes from a doubly linked list that contain a specific target value.
     * It correctly handles deletion at the head, middle, and tail of the list, and updates
     * the head pointer as needed. If every node matches the target, the method returns null.
     *
     * <p>
     * Intuition:
     * In a doubly linked list, each node knows its previous and next neighbors.
     * To remove a node, we simply bypass it by connecting its previous node to its next node.
     * Special care is needed for the head (no previous) and the tail (no next) to avoid null pointer errors.
     * The method traverses the entire list once, checking and deleting nodes as needed.
     *
     * <p>
     * Why each line matters:
     * - `if (head == null)` ensures we don't dereference a null list.
     * - `while (current != null)` ensures every node, including the last, is processed.
     * - `if (current.data == target)` checks whether the node should be deleted.
     * - `if (previousNode == null)` identifies the head node; deleting it updates the head.
     * - `if (nextNode != null)` prevents null pointer access when deleting the tail.
     * - `current = nextNode` advances traversal safely after a deletion.
     *
     * <p>
     * Edge Cases Handled:
     * - Empty list (head == null)
     * - Single-node list (head == tail)
     * - All nodes have the target value (entire list deleted)
     * - Target not present (list remains unchanged)
     * - Deleting nodes at head, middle, or tail positions
     *
     * <p>
     * Example:
     * Input:  head → 10 ↔ 20 ↔ 30 ↔ 10 ↔ 40, target = 10
     * Output: head → 20 ↔ 30 ↔ 40
     *
     * <p>
     * Time Complexity: O(n) — each node is visited once.
     * Space Complexity: O(1) — deletion is done in-place.
     */
    private static Node deleteKeyInLinkedList(Node head, int target) {
        if (head == null) {
            return null;
        }

        Node current = head;

        // process EVERY node
        while (current != null) {
            if (current.data == target) {
                Node nextNode = current.next;
                Node previousNode = current.previous;

                // deleting head
                if (previousNode == null) {
                    head = nextNode;
                } else {
                    previousNode.next = nextNode;
                }

                // fix back link only if next exists
                if (nextNode != null) {
                    nextNode.previous = previousNode;
                }

                // continue forward
                current = nextNode;
            } else {
                current = current.next;
            }
        }
        return head;
    }

    public void showList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public void insert(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.previous = tail;
        }
        tail = node;
        size++;
    }

    private static class Node {

        int data;
        Node next;
        Node previous;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

    }
}
