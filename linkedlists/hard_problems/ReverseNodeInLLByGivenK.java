package linkedlists.hard_problems;

/*
Leetcode: 25. Reverse Nodes in k-Group

Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.
k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a
multiple of k then left-out nodes, in the end, should remain as it is. You may not alter the values in the list's nodes,
only nodes themselves may be changed.

Example 1:
Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]

Example 2:
Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]

Constraints:
            The number of nodes in the list is n.
            1 <= k <= n <= 5000
            0 <= Node.val <= 1000

Follow-up: Can you solve the problem in O(1) extra memory space?

 */
public class ReverseNodeInLLByGivenK {

    private Node head;
    private Node tail;
    private int size;

    public ReverseNodeInLLByGivenK() {
        this.size = 0;
    }

    public static void main(String[] args) {
        ReverseNodeInLLByGivenK list = new ReverseNodeInLLByGivenK();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        list.insert(60);
        list.showList();
        int k = 2;
        list.head = kReverse(list.head, k);

        list.showList();
    }

    /**
     * kReverse
     * <p>
     * What it does:
     * Reverses nodes of a singly (or doubly) linked list in groups of size k and returns the new head.
     * If the number of nodes is not a multiple of k, the remaining nodes at the end are left unchanged.
     * The algorithm modifies the links between nodes without changing their values and works in-place
     * with O(1) extra space (apart from recursion stack or helper variables).
     * <p>
     * Intuition:
     * - To reverse in groups of k, we first locate the kth node from the current segment start.
     * - If there are fewer than k nodes remaining, we stop and connect the previous segment to the remainder.
     * - Otherwise, we temporarily detach the k-node segment, reverse it, and connect it to the previously reversed portion.
     * - The key trick is to track both the end of the previous reversed segment (`prevLast`)
     * and the next segment’s start (`nextNode`) to maintain continuity in the list.
     * <p>
     * Why each line matters:
     * - `Node temp = head;`
     * Begins traversal from the start of the list to process each k-sized block.
     * <p>
     * - `Node prevLast = null;`
     * Keeps a reference to the last node of the previous reversed block so it can be linked
     * to the head of the newly reversed segment.
     * <p>
     * - `while (temp != null) { ... }`
     * Iterates through the list, processing one k-sized segment at a time.
     * <p>
     * - `Node kThNode = getKthNode(temp, k);`
     * Finds the kth node from `temp`. If this is null, the current segment has fewer than k nodes.
     * <p>
     * - `if (kThNode == null) { if (prevLast != null) prevLast.next = temp; break; }`
     * If fewer than k nodes remain, attach the untouched remaining list to the previous segment and stop.
     * <p>
     * - `Node nextNode = kThNode.next;`
     * Temporarily stores the pointer to the node after the kth node, marking the start of the next segment.
     * <p>
     * - `kThNode.next = null;`
     * Detaches the current k-sized block from the rest of the list for isolated reversal.
     * <p>
     * - `reverseLinkedList(temp);`
     * Reverses the current k-sized block in place.
     * <p>
     * - `if (temp == head) { head = kThNode; } else { prevLast.next = kThNode; }`
     * Updates the head after reversing the first block, or connects the previous reversed block to the current one.
     * <p>
     * - `prevLast = temp;`
     * After reversal, `temp` becomes the last node of the reversed block; store it for future linking.
     * <p>
     * - `temp = nextNode;`
     * Moves to the next segment for processing.
     * <p>
     * Edge Cases Handled:
     * - Empty list (`head == null`) → returns null safely.
     * - List with fewer than k nodes → no reversal occurs.
     * - k = 1 → list remains unchanged.
     * - k equals list size → entire list reversed once.
     * - Handles exact multiples of k and leftovers seamlessly.
     * <p>
     * Example:
     * head = [1, 2, 3, 4, 5], k = 2
     * Step 1: Reverse [1,2] → [2,1], connect to next group start (3)
     * Step 2: Reverse [3,4] → [4,3], connect to previous segment
     * Step 3: Remaining [5] has less than k nodes → left as is
     * Result: [2,1,4,3,5]
     * <p>
     * Time Complexity:
     * - O(n), where n is the number of nodes, since each node is visited and reversed once.
     * <p>
     * Space Complexity:
     * - O(1) auxiliary space (in-place), ignoring recursion stack for helper calls.
     */

    private static Node kReverse(Node head, int k) {
        Node temp = head;
        Node prevLast = null;
        while (temp != null) {
            Node kThNode = getKthNode(temp, k);
            if (kThNode == null) {
                if (prevLast != null) {
                    prevLast.next = temp;
                }
                break;
            }

            Node nextNode = kThNode.next;
            kThNode.next = null;
            reverseLinkedList(temp);

            if (temp == head) {
                head = kThNode;
            } else {
                prevLast.next = kThNode;
            }
            prevLast = temp;
            temp = nextNode;
        }
        return head;
    }

    private static void reverseLinkedList(Node head) {

        Node temp = head;


        Node prev = null;


        while (temp != null) {
            Node front = temp.next;
            temp.next = prev;
            prev = temp;
            temp = front;
        }
    }

    private static Node getKthNode(Node temp, int k) {
        k -= 1;
        while (temp != null && k > 0) {
            k--;
            temp = temp.next;
        }
        return temp;
    }

    private void insert(int data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.previous = tail;
        }
        tail = node;
        size++;
    }

    public void showList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public int getSize() {
        return size;
    }

    private static class Node {
        int val;
        Node next;
        Node previous;

        public Node(int data) {
            this.val = data;
        }

        public Node(int data, Node next, Node previous) {
            this.val = data;
            this.next = next;
            this.previous = previous;
        }
    }
}
