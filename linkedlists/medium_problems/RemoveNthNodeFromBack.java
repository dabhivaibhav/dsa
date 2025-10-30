package linkedlists.medium_problems;

/*
19. Remove Nth Node From End of List
Given the head of a linked list, remove the nth node from the end of the list and return its head.

Example 1:
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]

Example 2:
Input: head = [1], n = 1
Output: []

Example 3:
Input: head = [1,2], n = 1
Output: [1]

Constraints:
            The number of nodes in the list is sz.
            1 <= sz <= 30
            0 <= Node.val <= 100
            1 <= n <= sz
Follow up: Could you do this in one pass?
 */
public class RemoveNthNodeFromBack {

    private Node head;
    private Node tail;
    private int size;

    public RemoveNthNodeFromBack() {
        this.size = 0;
    }

    public static void main(String[] args) {

        RemoveNthNodeFromBack list = new RemoveNthNodeFromBack();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        System.out.println(removeNthFromEndBruteForce(list.head, 2));
        list.showList();
        System.out.println(removeNthFromEndOptimal(list.head, 2));
        list.showList();
    }

    /**
     * removeNthFromEndBruteForce
     * <p>
     * What it does:
     * Removes the nth node from the end of a singly linked list and returns the head of the modified list.
     * <p>
     * Intuition:
     * - First, counts the number of nodes to figure out the position from the front to remove.
     * - Traverses again to the node just before the target one, then re-links `next` pointers to skip the node to remove.
     * <p>
     * Why each line matters:
     * - if (head == null) return null;
     * Handles empty input gracefully.
     * - First while loop: Counts total number of nodes in the list.
     * - if (count == n) return head.next;
     * Special case: removing the head (nth from end is first node).
     * - Computes ‘diff’, the position of the node to remove (from front).
     * - Second while loop: Traverses to node just before the target node.
     * - temp.next = temp.next.next;
     * Relinks to skip the nth node from the end.
     * - return head;
     * Returns the head of the new list.
     * <p>
     * Example:
     * Input: 1 → 2 → 3 → 4 → 5, n = 2
     * Output: 1 → 2 → 3 → 5 (removes '4', the second from end).
     * <p>
     * Time Complexity:
     * - O(n): Passes through the list twice.
     * <p>
     * Space Complexity:
     * - O(1): Only pointers and counters used, no extra storage.
     */
    private static Node removeNthFromEndBruteForce(Node head, int n) {
        if (head == null) return null;
        Node temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        if (count == n) return head.next;
        int diff = count - n;
        temp = head;
        while (temp != null) {
            diff--;
            if (diff == 0) {
                break;
            }
            temp = temp.next;
        }
        if (temp.next == null) {
            return null;
        }
        temp.next = temp.next.next;
        return head;
    }

    /**
     * removeNthFromEndOptimal
     * <p>
     * What it does:
     * Removes the nth node from the end of a singly linked list in one pass using the two-pointer technique.
     * Safely handles edge cases such as when n is equal to the length of the list (removing the head),
     * when n is greater than the list length, or when the list is empty.
     * <p>
     * Intuition:
     * - Use two pointers (fast and slow) starting at the head.
     * - Move the fast pointer n steps ahead to create a gap of n nodes between fast and slow.
     * - Then move both pointers simultaneously until fast reaches the last node.
     * - Slow will then point to the node before the node to remove.
     * - Update links to skip the target node.
     * <p>
     * Why each line matters:
     * - if (head == null || n <= 0) return head;
     * Handles empty list or invalid values of n by returning the original list.
     * - Node fast = head, slow = head;
     * Initialize two pointers at the start of the list.
     * - for (int i = 0; i < n; i++) { ... }
     * Advances fast pointer n steps, checking for the case when n exceeds list length.
     * - if (fast.next == null) return head;
     * Early return if n is larger than the list size, no removal needed.
     * - while (fast.next != null) { ... }
     * Moves both pointers in tandem until fast reaches the list end.
     * - slow.next = slow.next.next;
     * Bypasses the nth node from the end by linking slow directly to its next's next node.
     * - return head;
     * Returns the possibly modified head of the list.
     * <p>
     * Edge Cases Handled:
     * - Empty list: Returns null immediately.
     * - Single-node list with n=1: Removes the node returning null.
     * - Removing the head node: When n equals the list length.
     * - Invalid n values: When n is zero, negative, or exceeds list length.
     * <p>
     * Example:
     * Input: 1 → 2 → 3 → 4 → 5, n=2
     * After processing, output is 1 → 2 → 3 → 5 (4th node from the end removed).
     * <p>
     * Time Complexity:
     * - O(L), where L is the length of the linked list, as it only traverses the list once.
     * <p>
     * Space Complexity:
     * - O(1), using constant extra space for pointers.
     */
    private static Node removeNthFromEndOptimal(Node head, int n) {

        if (head == null || n <= 0) {
            return head;
        }

        Node fast = head;
        Node slow = head;

        for (int i = 0; i < n; i++) {
            if (fast.next == null) {
                // n is greater than list length
                return head;
            }
            fast = fast.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return head;
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
