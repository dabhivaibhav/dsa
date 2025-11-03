package linkedlists.medium_problems;

/*
Remove duplicated from sorted DLL

Given the head of a doubly linked list with its values sorted in non-decreasing order. Remove all duplicate occurrences
of any value in the list so that only distinct values are present in the list.
Return the head of the modified linked list.

Examples:
Input: head -> 1 <-> 1 <-> 3 <-> 3 <-> 4 <-> 5
Output: head -> 1 <-> 3 <-> 4 <-> 5
Explanation: head -> 1 <-> 1 <-> 3 <-> 3 <-> 4 <-> 5
The underlined nodes were deleted to get the desired result.

Input: head -> 1 <-> 1 <-> 1 <-> 1 <-> 1 <-> 2
Output: head -> 1 <-> 2
Explanation: head -> 1 <-> 1 <-> 1 <-> 1 <-> 1 <-> 2
The underlined nodes were deleted to get the desired result.

Constraints:
            1 <= number of nodes in the linked list <= 10^5
            -10^4 <= ListNode.val <= 10^4
            Values of nodes are sorted in non-decreasing order.
 */
public class RemoveDuplicatesSortedDLL {

    private Node head;
    private Node tail;
    private int size;

    RemoveDuplicatesSortedDLL() {
        this.size = 0;
    }


    public static void main(String[] args) {

        RemoveDuplicatesSortedDLL list = new RemoveDuplicatesSortedDLL();
        list.insert(10);
        list.insert(10);
        list.insert(20);
        list.insert(20);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(40);
        list.showList();
        list.removeDuplicates(list.head);
        list.showList();
    }

    /**
     * removeDuplicates
     * <p>
     * What it does:
     * Removes all duplicate nodes from a sorted doubly linked list in-place and returns the updated head.
     * Since the list is sorted, any duplicates will appear consecutively. The method iterates through the list once,
     * reconnecting pointers to skip over duplicate nodes without creating new nodes or using extra memory.
     * <p>
     * Intuition:
     * - In a sorted doubly linked list, duplicates of a given value always appear together.
     * - We can use a pointer `current` to traverse the list, and for each distinct value,
     * skip all consecutive nodes having the same data value.
     * - By adjusting the `next` and `prev` pointers, we effectively “unlink” duplicates from the list.
     * - The operation happens in-place, maintaining O(1) space and O(n) time complexity.
     * <p>
     * Why each line matters:
     * - `if (head == null) return null;`
     * Handles the edge case where the list is empty — nothing to process.
     * - `Node current = head;`
     * Starts traversal from the head of the list.
     * - `while (current != null && current.next != null) { ... }`
     * Iterates until the end of the list, ensuring we can always look at `current.next` safely.
     * - `Node nextNode = current.next;`
     * Creates a secondary pointer to explore ahead for duplicates of `current.data`.
     * - `while (nextNode != null && nextNode.data == current.data) { nextNode = nextNode.next; }`
     * Skips over all consecutive nodes that contain the same data as `current`.
     * - `current.next = nextNode;`
     * Re-links the list by making `current` point directly to the next distinct node, effectively removing duplicates.
     * - `if (nextNode != null) { nextNode.prev = current; }`
     * Updates the backward link of the next distinct node to maintain doubly linked structure integrity.
     * - `current = current.next;`
     * Moves forward to process the next unique value.
     * <p>
     * Edge Cases Handled:
     * - Empty list → returns null.
     * - Single-node list → no duplicates, returned as-is.
     * - All nodes with same value → retains only one instance.
     * - No duplicates → list remains unchanged.
     * - Properly maintains `prev` and `next` links even after removing middle or tail duplicates.
     * <p>
     * Example:
     * head = [1, 1, 2, 3, 3, 3, 4]
     * Iteration steps:
     * current = 1 → skip 1s → link to 2
     * current = 2 → unique → move on
     * current = 3 → skip 3s → link to 4
     * current = 4 → done
     * Resulting list = [1, 2, 3, 4]
     * <p>
     * Time Complexity:
     * - O(n), where n is the number of nodes, since each node is visited at most once.
     * <p>
     * Space Complexity:
     * - O(1), as no extra data structures are used; all operations are performed in-place.
     */
    public Node removeDuplicates(Node head) {
        if (head == null) return null;
        Node current = head;
        while (current != null && current.next != null) {
            Node nextNode = current.next;
            while (nextNode != null && nextNode.data == current.data) {
                nextNode = nextNode.next;
            }
            current.next = nextNode;
            if (nextNode != null) {
                nextNode.prev = current;
            }
            current = current.next;
        }
        return head;
    }

    public void insert(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    public void showList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    private static class Node {
        int data;
        Node next;
        Node prev;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }


}