package linkedlists.medium_problems;

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

        SortLinkedList list1 = new SortLinkedList();
        list1.insert(10);
        list1.insert(90);
        list1.insert(80);
        list1.insert(70);
        list1.insert(60);
        System.out.print("Before sorting: ");
        list1.showList();
        System.out.print("After sorting: ");
        sortLinkedListBruteForce(list1.head);
        list1.showList();

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

    /**
     * sortLinkedListOptimal
     * <p>
     * What it does:
     * Sorts a singly linked list in ascending order using the merge sort algorithm.
     * The method recursively splits the list into halves, sorts each half, and merges the sorted halves to produce a fully sorted linked list.
     * This approach guarantees O(n log n) time complexity and uses O(log n) extra space due to recursion.
     * <p>
     * Intuition:
     * - Linked lists lack random access, so algorithms like quicksort are not ideal.
     * - Merge sort works efficiently with linked lists because splitting and merging only involve pointer updates.
     * - The list is divided in half using the fast and slow pointer technique, recursively sorted, then merged.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return head;
     * Base case: an empty or single-node list is already sorted.
     * - Node middle = findMiddle(head);
     * Identifies the midpoint of the list using two pointers for even splitting.
     * - Node right = middle.next; middle.next = null;
     * Splits the list into two halves at the middle node.
     * - left = sortLinkedListOptimal(left); right = sortLinkedListOptimal(right);
     * Recursively sorts both halves of the list.
     * - return mergeTwoSortedLinkedLists(left, right);
     * Merges two sorted lists into one sorted list.
     * <p>
     * Helper Method: mergeTwoSortedLinkedLists
     * - Merges two sorted singly linked lists into a single sorted linked list.
     * - Uses a dummy node so pointer manipulation is consistent, avoiding edge cases for the head.
     * - Iterates through both lists, attaching the smaller node at each step to the result.
     * - If either list ends, attaches the remaining nodes from the other list.
     * <p>
     * Helper Method: findMiddle
     * - Finds the middle node of a linked list using the fast and slow pointer technique.
     * - This ensures the list is split as evenly as possible, which is important for merge sort efficiency.
     * <p>
     * Edge Cases Handled:
     * - Empty list: Returns null.
     * - Single-node list: Returns the node.
     * - Duplicate, negative, and positive values: All are handled and sorted correctly.
     * - Works for lists of any length, including odd and even sizes.
     * <p>
     * Example:
     * Input: 5 → 1 → 3 → 1 → 2
     * Output: 1 → 1 → 2 → 3 → 5
     * <p>
     * Time Complexity:
     * - O(n log n): Due to recursive splitting and merging steps. Each split divides the list in half, and each merge is O(n).
     * <p>
     * Space Complexity:
     * - O(log n): Due to the recursion stack in merge sort.
     * - O(1) additional explicit space. No arrays or auxiliary structures are used; only pointers.
     */
    private static Node sortLinkedListOptimal(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node middle = findMiddle(head);
        Node right = middle.next;
        middle.next = null;
        Node left = head;
        left = sortLinkedListOptimal(left);
        right = sortLinkedListOptimal(right);
        return mergeTwoSortedLinkedLists(left, right);
    }

    public static Node mergeTwoSortedLinkedLists(Node list1, Node list2) {
        Node dummyNode = new Node(-1, null);
        Node temp = dummyNode;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                temp.next = list1;
                list1 = list1.next;
            } else {
                temp.next = list2;
                list2 = list2.next;
            }
            temp = temp.next;
        }
        if (list1 != null) {
            temp.next = list1;
        } else {
            temp.next = list2;
        }
        return dummyNode.next;
    }

    private static Node findMiddle(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
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
