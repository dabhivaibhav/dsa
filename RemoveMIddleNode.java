/*
Leetcode: 2095. Delete the Middle Node of a Linked List
You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.
The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing, where ⌊x⌋ denotes the largest integer less than or equal to x.

For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.


Example 1: 1 -> 3 -> 4 -> 7 -> 1 -> 2 -> 6
Input: head = [1,3,4,7,1,2,6]
Output: [1,3,4,1,2,6]
Explanation:
The above figure represents the given linked list. The indices of the nodes are written below.
Since n = 7, node 3 with value 7 is the middle node, which is marked in red.
We return the new list after removing this node.

Example 2: 1 -> 2 -> 3 -> 4
Input: head = [1,2,3,4]
Output: [1,2,4]
Explanation:
The above figure represents the given linked list.
For n = 4, node 2 with value 3 is the middle node, which is marked in red.

Example 3: 2 -> 1
Input: head = [2,1]
Output: [2]
Explanation:
The above figure represents the given linked list.
For n = 2, node 1 with value 1 is the middle node, which is marked in red.
Node 0 with value 2 is the only node remaining after removing node 1.

Constraints:
            The number of nodes in the list is in the range [1, 10^5].
            1 <= Node.val <= 10^5
 */
public class RemoveMIddleNode {
    private Node head;
    private Node tail;
    private int size;

    public RemoveMIddleNode() {
        this.size = 0;
    }

    public static void main(String[] args) {
        RemoveMIddleNode list = new RemoveMIddleNode();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        System.out.println(removeMiddleNodeBruteForce(list.head));
        list.showList();
    }

    /**
     * removeMiddleNode
     * <p>
     * What it does:
     * Deletes the middle node from a singly linked list, as defined by the floor of n/2 (0-based index).
     * Returns head of the modified list; if there is only one node, returns null.
     * <p>
     * Intuition:
     * - Uses a slow and fast pointer (tortoise and hare). The slow moves one step, fast two steps,
     * so when fast reaches end, slow is at middle node's predecessor.
     * - Handles all edge cases using a dummy node for consistent head reference.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return null;
     * Handles empty or single-node lists, which become empty after deletion.
     * - Dummy node initialization: Guarantees the head is not lost,
     * allowing elegant middle deletion even if the head itself must be removed.
     * - while (fast != null && fast.next != null):
     * Advance pointers so slow lands before the middle node.
     * - slow.next = slow.next.next:
     * Removes the middle node by bypassing it.
     * - return dummy.next:
     * Returns correct head, especially if deletion occurs at head.
     * <p>
     * Edge Cases Handled:
     * - Empty or single-node list (returns null)
     * - Two-node list (removes second node, returns head)
     * - Even-length lists (removes second middle)
     * - Odd-length lists (removes exact middle)
     * <p>
     * Time Complexity: O(n). Single full list pass.
     * Space Complexity: O(1). Only constant extra pointers.
     */
    private static Node removeMiddleNodeBruteForce(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node slow = head, fast = head;
        fast = fast.next.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        slow.next = slow.next.next;
        return head ;
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
