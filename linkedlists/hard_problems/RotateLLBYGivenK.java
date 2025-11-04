package linkedlists.hard_problems;

/*
61. Rotate List

Given the head of a linked list, rotate the list to the right by k places.

Example 1:
Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]

Example 2:
Input: head = [0,1,2], k = 4
Output: [2,0,1]

Constraints:
            The number of nodes in the list is in the range [0, 500].
            -100 <= Node.val <= 100
            0 <= k <= 2 * 10^9
 */
public class RotateLLBYGivenK {

    private Node head;
    private Node tail;
    private int size;

    RotateLLBYGivenK() {
        this.size = 0;
    }

    public static void main(String[] args) {
        RotateLLBYGivenK list = new RotateLLBYGivenK();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.showList();
    }

    /**
     * rotateList
     * <p>
     * What it does:
     * Rotates a singly linked list to the right by `k` positions. Each rotation moves the last node of the list
     * to the front. For example, rotating [1, 2, 3, 4, 5] by k = 2 results in [4, 5, 1, 2, 3]. The method handles
     * large values of `k` efficiently by reducing redundant rotations using modulo.
     * <p>
     * Intuition:
     * Rotating a list by its length `n` times results in the same list, so we only need to perform `k % n` rotations.
     * To achieve rotation efficiently:
     * 1. Connect the tail to the head temporarily, forming a circular list.
     * 2. Find the new tail at position `(n - k - 1)` from the start.
     * 3. Break the circle after the new tail and return the new head.
     * <p>
     * Why each line matters:
     * - `if (head == null || head.next == null || k == 0)` prevents unnecessary work for empty or single-node lists.
     * - The first `while` loop counts nodes and finds the tail in O(n) time.
     * - `k = k % n` ensures only essential rotations are done, optimizing performance for large `k`.
     * - The `for` loop moves to the new tail node before the rotation pivot.
     * - The `newHead` marks the new starting point, and breaking the link (`newTail.next = null`) finalizes rotation.
     * <p>
     * Edge Cases Handled:
     * - Empty list (head == null)
     * - Single node list (head.next == null)
     * - k = 0 or k multiple of list length (no rotation needed)
     * - Very large k (handled using modulo)
     * <p>
     * Example:
     * Input: head = [1, 2, 3, 4, 5], k = 2
     * Steps:
     * - n = 5 → k % n = 2
     * - New tail = node at index 2 (value = 3)
     * - New head = node at index 3 (value = 4)
     * Output: [4, 5, 1, 2, 3]
     * <p>
     * Time Complexity: O(n) — single traversal to find length + one more to find new tail.
     * Space Complexity: O(1) — in-place rotation with constant extra memory.
     */
    private Node rotateList(Node head, int k) {
        if (head == null || head.next == null || k == 0) return head;
        Node tail = head;
        int n = 1;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }
        k = k % n;
        if (k == 0) return head;

        Node newTail = head;
        for (int i = 0; i < n - k - 1; i++) {
            newTail = newTail.next;
        }
        Node newHead = newTail.next;
        newTail.next = null;
        tail.next = head;
        head = newHead;
        return head;
    }

    private void insert(int val) {
        Node node = new Node(val);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    private void showList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    private static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
            next = null;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
}
