package linkedlists.medium_problems;

/*
Length of loop in LinkedList

Given the head of a singly linked list, find the length of the loop in the linked list if it exists. Return the length
of the loop if it exists; otherwise, return 0.
A loop exists in a linked list if some node in the list can be reached again by continuously following the next pointer.
Internally, pos is used to denote the index (0-based) of the node from where the loop starts.
Note that pos is not passed as a parameter.

Examples:
Input: head -> 1 -> 2 -> 3 -> 4 -> 5 -> 1, pos = 1
Output: 4
Explanation: 2 -> 3 -> 4 -> 5 - >2, length of loop = 4.
Input: head -> 1 -> 3 -> 7 -> 4, pos = -1
Output: 0
Explanation: No loop is present in the linked list.

Constraints:
            0 <= number of nodes in the cycle <= 10^5
            0 <= ListNode.val <= 10^4
            pos is -1 or a valid index in the linked list
 */
public class LengthOfLoopLinkedList {
    private Node head;
    private Node tail;
    private int size;

    LengthOfLoopLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {

        LengthOfLoopLinkedList list = new LengthOfLoopLinkedList();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        list.tail.next = list.head;
        LengthOfLoopLinkedList list1 = new LengthOfLoopLinkedList();
        list1.insert(1);
        list1.insert(2);
        System.out.println(findLengthOfLoop(list.head));
        System.out.println(findLengthOfLoop(list1.head));
    }

    /**
     * findLengthOfLoop
     * <p>
     * What it does:
     * Determines the length of the cycle (loop) in a singly linked list, if one exists. If no loop is present, returns 0.
     * Uses Floyd's Tortoise and Hare two-pointer algorithm to first detect the presence of a cycle and then compute its length.
     * <p>
     * Intuition:
     * - If there’s a cycle in the list, slow and fast pointers (moving at different speeds) will eventually meet inside the loop.
     * - Once a meeting point is found, traverse the loop starting from this node until you return to it, counting the number of nodes along the way. This is the length of the loop.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return 0;
     *   No loop possible in an empty or single-node list (without a self-loop); return 0.
     * - Node slow = head, fast = head;
     *   Initialize both pointers to the start of the list.
     * - while (fast != null && fast.next != null) { ... }
     *   This loop runs until the pointers meet or we hit the end (confirming there’s no cycle).
     * - slow = slow.next, fast = fast.next.next;
     *   Move slow by one step and fast by two steps on each pass.
     * - if (slow == fast)
     *   Pointers meet, which confirms the presence of a loop.
     * - int length = 1; slow = slow.next; while (slow != fast) { length++; slow = slow.next; }
     *   Move slow through the cycle, counting nodes until it returns to the meeting node.
     *   Each step in the loop increments the length.
     * - return length;
     *   Returns the number of nodes in the cycle.
     * - return 0;
     *   If no cycle present, the function reaches here and returns 0.
     * <p>
     * Example:
     * Input: 1 → 2 → 3 → 4 → 5 → 2 (cycle back to node with value 2)
     * - The pointers collide inside the loop.
     * - Moving from the meeting point (2) through 3 → 4 → 5 → (back to 2) gives a loop length of 4.
     * Input: 1 → 3 → 7 → 4 (no cycle)
     * - The pointers reach null; the function returns 0.
     * <p>
     * Time Complexity:
     * - O(n): Linear scan for detection plus O(loop length) pass for counting.
     * <p>
     * Space Complexity:
     * - O(1): Only two pointers and a counter; no extra space needed.
     */
    public static int findLengthOfLoop(Node head) {
        if (head == null || head.next == null) {
            return 0;
        }
        int counter = 0;
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                int length = 1;
                slow = slow.next;
                while (slow != fast) {
                    length++;
                    slow = slow.next;
                }
                return length;
            }
        }
        return 0;
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
