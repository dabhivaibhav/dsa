package linkedlists.hard_problems;

import java.util.ArrayList;
import java.util.Collections;

/*
Given a special linked list containing n head nodes where every node in the linked list contains two pointers:
‘Next’ points to the next node in the list
‘Child’ pointer to a linked list where the current node is the head
Each of these child linked lists is in sorted order and connected by a 'child' pointer.
Flatten this linked list such that all nodes appear in a single sorted layer connected by the 'child' pointer and return
the head of the modified list.

Examples:
Input:

head → 3 → 2 → 1 → 4 → 5 → NULL
       ↓   ↓   ↓   ↓   ↓
     NULL  10  7   9   6
           ↓   ↓   ↓   ↓
          NULL 11 NULL 8
               ↓       ↓
              12     NULL
               ↓
              NULL

Output: head -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 11 -> 12
Explanation: All the linked lists are joined together and sorted in a single level through the child pointer.

Input:

head → 4 → 2 → 12 → NULL
       ↓   ↓   ↓
      10   5   13
       ↓   ↓   ↓
      NULL 20  16
           ↓    ↓
          NULL 17
                ↓
                NULL

Output: head -> 2 -> 4 -> 5 -> 10 -> 12 -> 13 -> 16 -> 17 -> 20
Explanation: All the linked lists are joined together and sorted in a single level through the child pointer.

Constraints:
            n == Number of head nodes
            1 <= n <= 100
            1 <= Number of nodes in each child linked list <= 100
            0 <= ListNode.val <= 1000
            All child linked lists are sorted in non-decreasing order
 */
@SuppressWarnings("ALL")
public class FlattenLinkedList {


    public static void main(String[] args) {
        Node head = new Node(5);
        head.child = new Node(14);

        head.next = new Node(10);
        head.next.child = new Node(4);

        head.next.next = new Node(12);
        head.next.next.child = new Node(20);
        head.next.next.child.child = new Node(13);

        head.next.next.next = new Node(7);
        head.next.next.next.child = new Node(17);

        System.out.println("Original linked list:");
        printOriginalLinkedList(head, 0);

        Node flattened = flattenLinkedList(head);
        System.out.println("\nFlattened linked list: ");
        printLinkedList(flattened);
    }

    /**
     * flattenLinkedList
     *
     * What it does:
     * Flattens a multilevel linked list—where each node has both a 'next' and 'child' pointer—into a single-level
     * sorted list connected only through 'child' pointers. The method extracts all node values, sorts them, and
     * reconstructs a new linked list containing all values in non-decreasing order.
     *
     * Intuition:
     * - Each head node and its 'child' chain represent a sorted sublist.
     * - By traversing both 'next' and 'child' levels, we can collect all node values into a single list.
     * - Sorting this list ensures that the final flattened linked list preserves global sorted order.
     *
     * Why each line matters:
     * - `if (head == null)` prevents null pointer exceptions when list is empty.
     * - `ArrayList<Integer> list = new ArrayList<>();` stores all node values for sorting.
     * - Nested while loops traverse all ‘next’ and ‘child’ pointers to gather every value.
     * - `Collections.sort(list);` ensures final list is in ascending order.
     * - `convertArrToLinkedList(list);` reconstructs the flattened linked list.
     *
     * Edge Cases Handled:
     * - Empty linked list (`head == null`).
     * - Nodes having no child lists.
     * - Multiple heads with child lists of varying lengths.
     *
     * Example:
     * Input:
     *   head → 4 → 2 → 12 → NULL
     *          ↓   ↓   ↓
     *         10   5   13
     *              ↓     ↓
     *              20    16
     *                     ↓
     *                     17
     *
     * Output:
     *   2 → 4 → 5 → 10 → 12 → 13 → 16 → 17 → 20
     *
     * Time Complexity: O(n log n) — due to sorting all nodes (n = total nodes in list)
     * Space Complexity: O(n) — for storing values in ArrayList and rebuilding the list
     */
    private static Node flattenLinkedList(Node head) {
        if (head == null) {
            return null;
        }

        ArrayList<Integer> list = new ArrayList<>();

        while (head != null) {

            Node childNode = head;

            while (childNode != null) {
                list.add(childNode.val);
                childNode = childNode.child;
            }
            head = head.next;
        }
        Collections.sort(list);

        return convertArrToLinkedList(list);
    }

    /**
     * convertArrToLinkedList
     *
     * What it does:
     * Converts an ArrayList of integers into a singly linked list where nodes are connected using the 'child' pointer.
     * A dummy node is used to simplify head creation and connection of nodes.
     *
     * Intuition:
     * - Using a dummy node avoids handling special cases for the head.
     * - Iterating through the ArrayList and attaching each element as a new node constructs the list efficiently.
     *
     * Why each line matters:
     * - `Node dummyNode = new Node(-1);` acts as a temporary placeholder for list construction.
     * - `temp.child = new Node(arr.get(i));` links new nodes through the 'child' pointer.
     * - `return dummyNode.child;` returns the actual head (skipping dummy).
     *
     * Edge Cases Handled:
     * - Empty ArrayList results in an empty linked list (returns null).
     *
     * Example:
     * Input: [2, 4, 5, 10]
     * Output: 2 → 4 → 5 → 10 (linked by child pointers)
     *
     * Time Complexity: O(n) — iterates once through the ArrayList
     * Space Complexity: O(n) — creates n new nodes
     */
    static Node convertArrToLinkedList(ArrayList<Integer> arr) {
        Node dummyNode = new Node(-1);
        Node temp = dummyNode;
        for (int i = 0; i < arr.size(); i++) {
            temp.child = new Node(arr.get(i));
            temp = temp.child;
        }
        return dummyNode.child;
    }

    private static void printOriginalLinkedList(Node head, int depth) {
        while (head != null) {
            System.out.print(head.val);
            if (head.child != null) {
                System.out.print(" -> ");
                printOriginalLinkedList(head.child, depth + 1);
            }
            if (head.next != null) {
                System.out.println();
                for (int i = 0; i < depth; ++i) {
                    System.out.print("| ");
                }
            }
            head = head.next;
        }
    }

    private static void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.child;
        }
        System.out.println();
    }

    private static class Node {
        int val;
        Node next;
        Node child;

        Node() {
            this.val = 0;
            next = null;
            child = null;
        }

        Node(int val) {
            this.val = val;
            next = null;
            child = null;
        }

        Node(int val, Node next, Node child) {
            this.val = val;
            this.next = next;
            this.child = child;
        }
    }
}
