package linkedlists.hard_problems;

import java.util.HashMap;

/*
Leetcode: 138. Copy List with Random Pointer

A linked list of length n is given such that each node contains an additional random pointer, which could point to any
node in the list, or null. Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes,
where each new node has its value set to the value of its corresponding original node. Both the next and random pointer
of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list
represent the same list state. None of the pointers in the new list should point to nodes in the original list.

For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two
nodes x and y in the copied list, x.random --> y. Return the head of the copied linked list.

The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of
[val, random_index] where:
                        val: an integer representing Node.val
                        random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or
                        null if it does not point to any node.

Your code will only be given the head of the original linked list.

Example 1:
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Example 2:
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]

Example 3:
Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]

Constraints:
            0 <= n <= 1000
            -10^4 <= Node.val <= 10^4
            Node.random is null or is pointing to some node in the linked list.
 */
public class CloneLinkedList {

    public static void main(String[] args) {
        Node head = new Node(7);
        head.next = new Node(14);
        head.next.next = new Node(21);
        head.next.next.next = new Node(28);

        head.random = head.next.next;
        head.next.random = head;
        head.next.next.random = head.next.next.next;
        head.next.next.next.random = head.next;

        System.out.println("Original Linked List with Random Pointers:");
        printClonedLinkedList(head);
        Node clonedList = cloneLL(head);

        System.out.println("\nCloned Linked List with Random Pointers:");
        printClonedLinkedList(clonedList);
    }

    /**
     * cloneLL
     * <p>
     * What it does:
     * Creates a deep copy of a linked list where each node contains both a 'next' and a 'random' pointer.
     * Every node in the new list is a completely new object with identical data values and identical
     * pointer relationships (both 'next' and 'random') to the corresponding nodes in the original list.
     * The copied list is entirely independent—no pointers in the cloned list reference nodes from the original.
     * <p>
     * Intuition:
     * The challenge lies in handling the random pointers, which may point arbitrarily to any node.
     * A direct one-pass copy can’t be done because random links may refer to nodes not yet created.
     * By using a HashMap to store a mapping between original nodes and their clones, we can ensure
     * that both the `next` and `random` pointers are correctly replicated after all nodes are created.
     * <p>
     * Why each line matters:
     * - `HashMap<Node, Node> map = new HashMap<>();` → Tracks the relationship between each original node
     * and its corresponding clone, enabling accurate pointer reconstruction.
     * - First `while (temp != null)` loop → Creates a shallow copy (only data) of every node and stores it in the map.
     * - Second `while (temp != null)` loop → Uses the map to assign proper `next` and `random` links
     * for each cloned node, completing the deep copy.
     * - `return map.get(head);` → Returns the new head node corresponding to the original head.
     * <p>
     * Edge Cases Handled:
     * - Empty list (head == null) → Returns null since there’s nothing to copy.
     * - Random pointers that are null → Properly handled since `map.get(null)` naturally returns null.
     * - Random pointers forming cycles → Still works because the map ensures consistent node references.
     * <p>
     * Example:
     * Original:
     * A (data=7) → B (data=14) → C (data=21)
     * A.random = C, B.random = A, C.random = B
     * Cloned:
     * a (data=7) → b (data=14) → c (data=21)
     * a.random = c, b.random = a, c.random = b
     * <p>
     * Time Complexity: O(n)
     * Each node is visited twice — once to create the copy, and once to assign pointers.
     * <p>
     * Space Complexity: O(n)
     * The HashMap stores one entry per node to maintain the mapping between original and copied nodes.
     */
    public static Node cloneLL(Node head) {
        Node temp = head;
        HashMap<Node, Node> map = new HashMap<>();
        while (temp != null) {
            Node newNode = new Node(temp.data);
            map.put(temp, newNode);
            temp = temp.next;
        }
        temp = head;
        while (temp != null) {
            Node copyNode = map.get(temp);
            copyNode.next = map.get(temp.next);
            copyNode.random = map.get(temp.random);
            temp = temp.next;
        }
        return map.get(head);
    }

    public static void printClonedLinkedList(Node head) {
        while (head != null) {
            System.out.print("Data: " + head.data);
            if (head.random != null) {
                System.out.print(", Random: " + head.random.data);
            } else {
                System.out.print(", Random: nullptr");
            }
            System.out.println();
            head = head.next;
        }
    }

    static class Node {
        int data;
        Node next;
        Node random;

        Node() {
            this.data = 0;
            this.next = null;
            this.random = null;
        }

        Node(int x) {
            this.data = x;
            this.next = null;
            this.random = null;
        }

        Node(int x, Node nextNode, Node randomNode) {
            this.data = x;
            this.next = nextNode;
            this.random = randomNode;
        }
    }
}
