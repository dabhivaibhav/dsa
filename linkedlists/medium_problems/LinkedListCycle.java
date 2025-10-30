package linkedlists.medium_problems;

import java.util.HashMap;
/*
Leetcode: 141. Linked List Cycle
Given head, the head of a linked list, determine if the linked list has a cycle in it.
There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following
the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to.
Note that pos is not passed as a parameter. Return true if there is a cycle in the linked list. Otherwise, return false.

Example 1:
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).

Example 2:
Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.

Example 3:
Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.

Constraints:
            The number of the nodes in the list is in the range [0, 104].
            -10^5 <= Node.val <= 10^5

pos is -1 or a valid index in the linked-list.
Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */
public class LinkedListCycle {

    private Node head;
    private Node tail;
    private int size;

    LinkedListCycle() {
        this.size = 0;
    }

    public static void main(String[] args) {

        LinkedListCycle list = new LinkedListCycle();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        list.tail.next = list.head;
        LinkedListCycle list1 = new LinkedListCycle();
        list1.insert(10);
        System.out.println(detectCycleOptimal(list.head));
        System.out.println(detectCycleBruteForce(list.head));
        System.out.println(detectCycleOptimal(list1.head));
        System.out.println(detectCycleBruteForce(list1.head));
    }

    /**
     * detectCycleBruteForce
     * <p>
     * What it does:
     * Checks if a singly linked list has a cycle (loop), using a HashMap to record all visited nodes.
     * If any node is seen again (visited twice), the list contains a cycle.
     * <p>
     * Intuition:
     * - If you ever visit the same node more than once while walking through a linked list,
     * it's because the nodes form a loop—you'll keep circling forever.
     * - Recording every node you see allows you to detect a revisit quickly.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return false;
     *   An empty list or a single-node list (with no loop) cannot form a cycle.
     * - HashMap<Node, Integer> visited = new HashMap<>();
     *   Use a map to keep track of which nodes you have already seen during the traversal.
     * - while (temp != null) { ... }
     *   Walk through the linked list node by node.
     * - if (visited.containsKey(temp)) return true;
     *   The moment you see a node again, you have found a cycle—return true at once.
     * - else { visited.put(temp, 1); }
     *   Mark every new node as seen.
     * - temp = temp.next;
     *   Move forward to the next node for the next loop iteration.
     * - return false;
     *   If you reach the end of the list without revisiting any node, there is no cycle.
     * <p>
     * Example:
     * For list: 1 -> 2 -> 3 -> 4 -> 2 (cycle to 2), you will see node 2 again and return true.
     * For list: 1 -> 2 -> 3 -> 4 -> 5 (no cycle), all nodes are unique and you return false.
     * <p>
     * Time Complexity:
     * - O(n): Each node is checked at most once, but lookup and storage make this less efficient for large lists.
     * <p>
     * Space Complexity:
     * - O(n): Uses memory to store all visited nodes.
     */
    private static boolean detectCycleBruteForce(Node head) {
        if (head == null || head.next == null) {
            return false;
        }
        HashMap<Node, Integer> visited = new HashMap<>();
        Node temp = head;
        while (temp != null) {
            if (visited.containsKey(temp)) {
                return true;
            } else {
                visited.put(temp, 1);
            }
            temp = temp.next;
        }
        return false;
    }

    /**
     * detectCycleOptimal
     * <p>
     * What it does:
     * Checks for a cycle (loop) in a singly linked list using two pointers moving at different speeds, called "Floyd's Tortoise and Hare" algorithm.
     * Returns true if a cycle is detected, false otherwise.
     * <p>
     * Intuition:
     * - Use two pointers: slow (moves one step) and fast (moves two steps at a time).
     * - If a cycle exists, fast and slow will eventually meet inside the circle (like runners on a track); if there's no cycle, fast will reach the end.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return false;
     *   No nodes or only one node: can't have a loop.
     * - Node slow = head; Node fast = head;
     *   Both pointers start at the beginning.
     * - while (fast != null && fast.next != null) { ... }
     *   Continue as long as there are nodes ahead for both pointers.
     * - slow = slow.next;
     *   Move slow pointer by one node.
     * - fast = fast.next.next;
     *   Move fast pointer by two nodes.
     * - if (slow == fast) { result = true; break; }
     *   If both pointers meet, a cycle is confirmed—stop and return true.
     * - return result;
     *   Return true if cycle found; otherwise false if loop terminates.
     * <p>
     * Example:
     * - For list: 1 -> 2 -> 3 -> 4 -> 2 (cycle to 2), slow and fast pointers will eventually land on the same node after a few passes.
     * - For list: 1 -> 2 -> 3 -> 4 -> 5 (no cycle), fast pointer reaches null and loop exits.
     * <p>
     * Time Complexity:
     * - O(n): Each node is visited at most a few times.
     * <p>
     * Space Complexity:
     * - O(1): Uses just two pointers, not extra memory for all nodes.
     */
    private static boolean detectCycleOptimal(Node head) {
        if (head == null || head.next == null) {
            return false;
        }
        boolean result = false;
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                result = true;
                break;
            }
        }
        return result;
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
