package collection.linkedlist.medium_problems;

import java.util.ArrayList;
import java.util.Collections;

/*
148. Sort List
Given the head of a linked list, return the list after sorting it in ascending order.

Example 1:
Input: head = [4,2,1,3]
Output: [1,2,3,4]

Example 2:
Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]

Example 3:
Input: head = []
Output: []

Constraints:
            The number of nodes in the list is in the range [0, 5 * 10^4].
            -10^5 <= Node.val <= 10^5

Follow up: Can you sort the linked list in O(n logn) time and O(1) memory (i.e. constant space)?
 */
public class SortLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public SortLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {
        SortLinkedList list = new SortLinkedList();
        list.insert(10);
        list.insert(90);
        list.insert(80);
        list.insert(70);
        list.insert(60);
        System.out.print("Before sorting: ");
        list.showList();
        System.out.print("After sorting: ");
        sortLinkedListBruteForce(list.head);
        list.showList();

    }


    /**
     * sortLinkedListBruteForce
     * <p>
     * What it does:
     * Sorts a singly linked list in ascending order using a brute-force approach.
     * Extracts all the node values into an ArrayList, sorts the ArrayList,
     * then overwrites the node values in the original linked list with the sorted values.
     * <p>
     * Intuition:
     * - Linked lists do not support random access, making comparison-based sorting tricky.
     * - Brute-force solution: Use the random-access ArrayList to collect all node values,
     * sort them easily, then use a second pass to update the nodes.
     * - This sidesteps pointer rearrangement but uses extra space proportional to the list size.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return;
     * Handles the edge cases of an empty or single-node list; nothing to sort.
     * - ArrayList<Integer> arr = new ArrayList<>();
     * Stores all node values, enables efficient sorting.
     * - Node temp = head; while (temp != null) { arr.add(temp.val); temp = temp.next; }
     * Linear pass through the entire list to copy values.
     * - Collections.sort(arr);
     * Sorts the collected values in ascending order (uses efficient Java sort).
     * - temp = head; for (int i = 0; i < arr.size(); i++) { temp.val = arr.get(i); temp = temp.next; }
     * Overwrites the current list nodes' values with sorted values, one by one.
     * <p>
     * Edge Cases Handled:
     * - Empty list: Returns immediately, nothing to sort.
     * - Single-node list: Returns immediately, already sorted.
     * - Duplicates: All duplicate values will be sorted and preserved.
     * - Negative and positive values: All are sorted correctly.
     * <p>
     * Example:
     * Input: 5 → 1 → 3 → 1 → 2
     * Output: 1 → 1 → 2 → 3 → 5
     * <p>
     * Time Complexity:
     * - O(n): To copy all values into the array (first pass)
     * - O(n log n): To sort the array (Java’s sort is O(n log n))
     * - O(n): To overwrite node values (second pass)
     * - Overall: O(n log n)
     * <p>
     * Space Complexity:
     * - O(n): Due to ArrayList storing all node values
     */
    private static void sortLinkedListBruteForce(Node head) {
        if (head == null || head.next == null) {
            return;
        }
        ArrayList<Integer> arr = new ArrayList<>();
        Node temp = head;
        while (temp != null) {
            arr.add(temp.val);
            temp = temp.next;
        }
        Collections.sort(arr);
        temp = head;
        for (int i = 0; i < arr.size(); i++) {
            temp.val = arr.get(i);
            temp = temp.next;
        }
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
