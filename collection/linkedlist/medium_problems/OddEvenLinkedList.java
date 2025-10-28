package collection.linkedlist.medium_problems;


/*
Leetcode: 328. Odd Even Linked List

Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with even
indices, and return the reordered list.
The first node is considered odd, and the second node is even, and so on.
Note that the relative order inside both the even and odd groups should remain as it was in the input.
You must solve the problem in O(1) extra space complexity and O(n) time complexity.

Example 1:
Input: head = [1,2,3,4,5]
Output: [1,3,5,2,4]

Example 2:
Input: head = [2,1,3,5,6,4,7]
Output: [2,3,6,7,1,5,4]

Constraints:
            The number of nodes in the linked list is in the range [0, 104].
            -10^6 <= Node.val <= 10^6
 */
public class OddEvenLinkedList {
    private Node head;
    private Node tail;
    private int size;

    OddEvenLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {

        OddEvenLinkedList list = new OddEvenLinkedList();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        System.out.println(oddEvenLinkedListBruteForce(list.head, list.size));
        printList(list.head);
    }

    /**
     * oddEvenLinkedListBruteForce
     * <p>
     * What it does:
     * Rearranges a singly linked list so that all nodes at odd indices come first, followed by all nodes at even indices,
     * maintaining original relative order in both groups.
     * <p>
     * Intuition:
     * - Extract odd-indexed and even-indexed node data into an array (using two passes).
     * - Overwrite the linked list node values with values from the array in the new order.
     * - This is a "brute-force" method: first gather, then rewrite.
     * <p>
     * Why each line matters:
     * - int[] arr = new int[size]; int count = 0;
     * Create an auxiliary array to store node data by position.
     * - while (temp != null && temp.next != null) { ... }
     * Collect odd-indexed node data.
     * - if (temp != null) { ... }
     * Handles lists of odd length, collecting the last element.
     * - temp = head.next; ... { ... }
     * Collect even-indexed node data.
     * - Overwrite the linked list node values from arr, front to back.
     * <p>
     * Example:
     * Input: 10 → 20 → 30 → 40 → 50
     * Output: 10 → 30 → 50 → 20 → 40
     * <p>
     * Time Complexity:
     * - O(n): Three passes, but each is linear.
     * <p>
     * Space Complexity:
     * - O(n): Uses a size-n array for temporary storage.
     */
    private static Node oddEvenLinkedListBruteForce(Node head, int size) {

        if (head == null || head.next == null) return head;
        int[] arr = new int[size];
        int count = 0;

        //Extracting odd elements
        Node temp = head;
        while (temp != null && temp.next != null) {
            arr[count] = temp.data;
            temp = temp.next.next;
            count++;
        }
        if (temp != null) {
            arr[count] = temp.data;
            count++;
        }

        //extracting even elements
        temp = head.next;
        while (temp != null && temp.next != null) {
            arr[count] = temp.data;
            temp = temp.next.next;
            count++;
        }
        if (temp != null) {
            arr[count] = temp.data;
        }

        temp = head;
        count = 0;
        while (temp != null) {
            temp.data = arr[count];
            temp = temp.next;
            count++;
        }
        return head;
    }

    private static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
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
