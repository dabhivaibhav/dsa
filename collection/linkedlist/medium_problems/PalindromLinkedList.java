package collection.linkedlist.medium_problems;

import java.util.Stack;

/*
234. Palindrome Linked List

Given the head of a singly linked list, return true if it is a palindrome or false otherwise.

Example 1:
Input: head = [1,2,2,1]
Output: true

Example 2:
Input: head = [1,2]
Output: false

Constraints:
            The number of nodes in the list is in the range [1, 105].
            0 <= Node.data <= 9

Follow up: Could you do it in O(n) time and O(1) space?
 */
public class PalindromLinkedList {
    private Node head;
    private Node tail;
    private int size;

    PalindromLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {
        PalindromLinkedList list = new PalindromLinkedList();
        list.insert(1);
        list.insert(2);
        list.insert(2);
        list.insert(1);
        PalindromLinkedList list1 = new PalindromLinkedList();
        list1.insert(1);
        list1.insert(2);
        PalindromLinkedList list2 = new PalindromLinkedList();
        list2.insert(1);
        list2.insert(2);
        list2.insert(2);
        list2.insert(1);
        PalindromLinkedList list3 = new PalindromLinkedList();
        list3.insert(1);
        list3.insert(2);
        System.out.println(checkPalindromLinkedListBruteForce(list));
        System.out.println(checkPalindromLinkedListBruteForce(list1));
        System.out.println(checkPalindromLinkedListBruteForce1(list2.head));
        System.out.println(checkPalindromLinkedListBruteForce1(list3.head));
        System.out.println(checkPalindromLinkedListOptimal(list.head));
        System.out.println(checkPalindromLinkedListOptimal(list1.head));
    }

    /**
     * checkPalindromLinkedListBruteForce
     * <p>
     * What it does:
     * Checks whether the given singly linked list is a palindrome (reads the same forward and backward).
     * Returns true if it is a palindrome, false otherwise.
     * <p>
     * Intuition:
     * - Since regular linked lists can't be read backwards, we create a reversed copy of the original list.
     * - Walk both lists side-by-side, comparing node values at each position.
     * - If every node matches, the list is a palindrome.
     * <p>
     * Why each line matters:
     * - PalindromLinkedList list1 = new PalindromLinkedList();
     * Create a new list to hold a copy of the original, which will be reversed.
     * - Node temp = list.head; while (temp != null) { list1.insert(temp.data); temp = temp.next; }
     * Copy each node's data from the original into the new list.
     * - reverseLinkedListOptimal(list1.head, list1);
     * Reverse the new list so its nodes are in the opposite order.
     * - temp = list.head; Node temp1 = list1.head; while (temp != null && temp1 != null) { ... }
     * Start from the beginning of both lists, and walk forward together.
     * - if (temp.data == temp1.data) { ... } else { return false; }
     * If current values match, move to the next node in both lists. If not, return false immediately.
     * - return true;
     * Reached the end without a mismatch—list is a palindrome!
     * <p>
     * Example:
     * Input: 1 → 2 → 2 → 1
     * - Copy: 1 → 2 → 2 → 1
     * - After reversal: 1 → 2 → 2 → 1
     * - All nodes match, returns true.
     * Input: 1 → 2
     * - Copy: 1 → 2
     * - After reversal: 2 → 1
     * - 1 != 2, so returns false.
     * <p>
     * Time Complexity:
     * - O(n): One scan to copy, one scan to reverse, one scan to compare.
     * <p>
     * Space Complexity:
     * - O(n): Uses a second list to hold copied/reversed values.
     */
    private static boolean checkPalindromLinkedListBruteForce(PalindromLinkedList list) {

        PalindromLinkedList list1 = new PalindromLinkedList();
        Node temp = list.head;
        while (temp != null) {
            list1.insert(temp.data);
            temp = temp.next;
        }
        reverseLinkedListOptimal(list1.head, list1);
        temp = list.head;
        Node temp1 = list1.head;
        while (temp != null && temp1 != null) {

            if (temp.data == temp1.data) {
                temp = temp.next;
                temp1 = temp1.next;

            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * checkPalindromLinkedListBruteForce1
     * <p>
     * What it does:
     * Determines if a singly linked list is a palindrome by using a stack to reverse the order of elements for comparison.
     * Returns true if the list is a palindrome, false otherwise.
     * <p>
     * Intuition:
     * - Stack stores elements in LIFO (last-in-first-out) order, so if you push all elements from the head,
     * popping from the stack gives the elements in reverse order.
     * - Compare the original order (walking from head) to the reversed order (from stack) node by node.
     * - If all elements match, the list is a palindrome.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return false;
     * For empty or single-node lists, returns false (could be argued to return true, but this is the author's policy).
     * - Stack<Integer> stack = new Stack<>();
     * Prepare a stack to store all node data.
     * - while (temp != null) { stack.push(temp.data); temp = temp.next; }
     * Push each data value from the list into the stack; when done, stack has the values in order from front to back.
     * - temp = head;
     * Reset pointer to beginning for comparison.
     * - while (!stack.isEmpty()) { ... }
     * Compare each element popped from stack (last-in) to the current node in the original list (front).
     * - if (stack.pop() != temp.data) { return false; }
     * If values differ at any step, it's not a palindrome—exit immediately.
     * - temp = temp.next;
     * Continue moving through the list.
     * - return true;
     * If all elements match, return true—it's a palindrome!
     * <p>
     * Example:
     * Input: 1 → 2 → 2 → 1
     * - Stack after push: [1, 2, 2, 1]
     * - Compare: 1 vs 1, 2 vs 2, 2 vs 2, 1 vs 1 — all match, so returns true.
     * Input: 1 → 2 → 3
     * - Stack after push: [1, 2, 3]
     * - Compare: 1 vs 3 — mismatch, so returns false.
     * <p>
     * Time Complexity:
     * - O(n): Each node is visited twice (once to push, once to compare).
     * <p>
     * Space Complexity:
     * - O(n): Stack stores all node values.
     */
    private static boolean checkPalindromLinkedListBruteForce1(Node head) {
        if (head == null || head.next == null) return false;
        Stack<Integer> stack = new Stack<>();
        Node temp = head;
        while (temp != null) {
            stack.push(temp.data);
            temp = temp.next;
        }
        temp = head;
        while (!stack.isEmpty()) {
            if (stack.pop() != temp.data) {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    /**
     * checkPalindromLinkedListOptimal
     * <p>
     * What it does:
     * Efficiently checks whether a singly linked list is a palindrome using O(n) time and O(1) extra space.
     * Finds the midpoint, reverses the second half, and compares the two halves node by node.
     * Returns true if the list is a palindrome, false otherwise.
     * <p>
     * Intuition:
     * - To check for palindrome, we need to compare mirror positions from the front and back.
     * - We can't walk backward in a singly linked list, so we reverse the second half (after the middle)
     *   to enable direct left-to-right comparison with the first half.
     * - Only O(1) extra space is required since we manipulate pointers, not extra memory.
     * <p>
     * Why each line matters:
     * - if (head == null) return false;
     *   No data means not a palindrome by definition (could also be true, but author’s choice).
     * - if (head.next == null) return true;
     *   One node always reads the same forwards and backwards.
     * - Node slow = head; Node fast = head;
     *   Two pointers for finding the halfway point (midpoint) using Tortoise and Hare (Floyd’s).
     * - while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
     *   Move slow one step and fast two steps; when fast reaches end, slow is at the middle.
     * - Node newHead = reverseList(slow);
     *   Reverse the second half of the list starting from midpoint (slow).
     * - Node oldHead = head;
     *   Keep pointer for comparing from the start.
     * - while (oldHead.next != null) { ... }
     *   Walk both halves together, comparing corresponding nodes.
     * - if (newHead.data != oldHead.data) return false;
     *   If any node differs, it's not a palindrome.
     * - newHead = newHead.next; oldHead = oldHead.next;
     *   Step forward in both halves.
     * - return true;
     *   If all nodes match, the linked list is a palindrome.
     * <p>
     * Example:
     * Input: 1 → 2 → 2 → 1
     * - Find midpoint (slow at second node '2')
     * - Reverse second half: 2 → 1 becomes 1 → 2
     * - Compare: 1 vs 1, 2 vs 2 — all match, returns true.
     * Input: 1 → 2 → 3
     * - Find midpoint (slow at '2')
     * - Reverse: 3
     * - Compare: 1 vs 3 — mismatch, returns false.
     * <p>
     * Time Complexity:
     * - O(n): Linear scan (find middle, reverse, compare).
     * <p>
     * Space Complexity:
     * - O(1): Only pointers used; no extra structures.
     */
    private static boolean checkPalindromLinkedListOptimal(Node head) {
        if (head == null) return false;
        if (head.next == null) return true;
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node newHead = reverseList(slow);
        Node oldHead = head;

        while (oldHead.next != null) {
            if (newHead.data != oldHead.data) {
                return false;
            }
            newHead = newHead.next;
            oldHead = oldHead.next;
        }
        return true;
    }

    private static void reverseLinkedListOptimal(Node head, PalindromLinkedList list) {

        Node temp = head;
        Node previous = null;
        while (temp != null) {
            Node front = temp.next;
            temp.next = previous;
            previous = temp;
            temp = front;
        }
        list.head = previous;
    }

    public static  Node reverseList(Node head) {
        Node prev = null;
        Node temp = head;

        if (head == null || head.next == null) return head;
        Node newHead = reverseList(head.next);
        Node front = head.next;
        front.next = head;
        head.next = null;
        return newHead;
    }

    public int size() {
        return size;
    }

    public void insert(int data) {
        Node node = new Node(data);

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
