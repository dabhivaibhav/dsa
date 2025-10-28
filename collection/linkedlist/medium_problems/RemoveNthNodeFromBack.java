package collection.linkedlist.medium_problems;

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
        diff--;
        while (diff > 0) {
            diff--;
            temp = temp.next;
        }
        System.out.println(temp.val);
        if (temp.next == null) {
            return null;
        }
        temp.next = temp.next.next;
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
