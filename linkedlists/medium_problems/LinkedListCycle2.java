package linkedlists.medium_problems;

import java.util.HashMap;

/*
Leetcode: 142. Linked List Cycle II

Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following
the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to
(0-indexed). It is -1 if there is no cycle. Note that pos is not passed as a parameter. Do not modify the linked list.

Example 1:
Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.

Example 2:
Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.

Example 3:
Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.

Constraints:
            The number of the nodes in the list is in the range [0, 104].
            -10^5 <= Node.val <= 10^5
pos is -1 or a valid index in the linked-list.

Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */
public class LinkedListCycle2 {
    private Node head;
    private Node tail;
    private int size;

    LinkedListCycle2() {
        this.size = 0;
    }

    public static void main(String[] args) {

        LinkedListCycle2 list = new LinkedListCycle2();
        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insert(40);
        list.insert(50);
        list.tail.next = list.head;
        LinkedListCycle2 list1 = new LinkedListCycle2();
        list1.insert(1);
        list1.insert(2);
        System.out.println(detectStartingNodeBruteForce(list.head));
        System.out.println(detectStartingNodeOptimal(list.head));
        System.out.println(detectStartingNodeBruteForce(list1.head));
        System.out.println(detectStartingNodeOptimal(list1.head));
    }

    /**
     * detectStartingNodeBruteForce
     * <p>
     * What it does:
     * Detects if there is a cycle in a singly linked list and returns the node where the cycle starts, using a HashMap to remember all visited nodes.
     * If no cycle is present, returns null.
     * <p>
     * Intuition:
     * - When traversing a linked list, if you ever visit the same node more than once, you know the list loops back (forms a cycle).
     * - By remembering every visited node, you can immediately spot when you hit a cycle by checking if a node is already in the map.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return null;
     *   If the list is empty or has only one node, a cycle is not possible (returns null directly).
     * - HashMap<Node, Integer> visited = new HashMap<>();
     *   Creates a map to track every node that has been seen during scanning.
     * - while (temp != null) { ... }
     *   Walk through the entire list, node by node.
     * - if (visited.containsKey(temp)) return temp;
     *   If the current node is already in the map, a cycle is found and this node is where it starts/repeats.
     * - else { visited.put(temp, 1); }
     *   Adds new, unvisited nodes to the map for future lookups.
     * - temp = temp.next;
     *   Move to the next node.
     * - return null;
     *   If all nodes are unique (the walk finishes), there's no cycle, so returns null.
     * <p>
     * Example:
     * Input: 3 → 2 → 0 → -4 → 2 (cycle back to the second node)
     * - The first time node 2 is visited, it's not in the map.
     * - When -4 points to node 2 again, node 2 is already present, so it's returned.
     * <p>
     * Time Complexity:
     * - O(n): Every node is traversed at most once.
     * <p>
     * Space Complexity:
     * - O(n): The HashMap may need to store every node if there is no cycle.
     */
    private static Node detectStartingNodeBruteForce(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        HashMap<Node, Integer> visited = new HashMap<>();
        Node temp = head;
        while (temp != null) {
            if (visited.containsKey(temp)) {
                return temp;
            } else {
                visited.put(temp, 1);
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * detectStartingNodeOptimal
     * <p>
     * What it does:
     * Identifies the node where a cycle begins in a singly linked list using Floyd's Tortoise and Hare algorithm, with constant memory.
     * If no cycle exists, returns null.
     * <p>
     * Intuition:
     * - Uses two pointers (slow and fast): slow moves one node at a time, fast moves two.
     * - If there is a cycle, slow and fast will meet at some node in the loop.
     * - To find the cycle's start, reset one pointer to head and move both pointers one step at a time; the node where they meet is the start of the cycle.
     * <p>
     * Why each line matters:
     * - if (head == null || head.next == null) return null;
     *   Can't have a cycle if list is empty or single node.
     * - Node slow = head; Node fast = head;
     *   Start both pointers from the beginning.
     * - while (fast != null && fast.next != null) { ... }
     *   Walk until pointers meet or fast pointer reaches the end.
     * - slow = slow.next; fast = fast.next.next;
     *   Move slow by one and fast by two steps.
     * - if (slow == fast) { ... }
     *   Meeting point confirms a cycle exists.
     * - slow = head; while (slow != fast) { slow = slow.next; fast = fast.next; }
     *   Reset slow to the start and advance both one step at a time; where they meet is the cycle’s entry point.
     * - return fast;
     *   Returns the node where cycle begins, or null if no cycle.
     * <p>
     * Example:
     * Input: 3 → 2 → 0 → -4 → 2 (cycle back to second node)
     * - Slow and fast meet at some node in the loop, then both pointers catch the start of the cycle synchronously.
     * <p>
     * Time Complexity:
     * - O(n): Both detection and finding the start are O(n).
     * <p>
     * Space Complexity:
     * - O(1): Only a fixed number of pointers are used.
     */
    public static Node detectStartingNodeOptimal(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return fast;
            }
        }
        return null;
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
