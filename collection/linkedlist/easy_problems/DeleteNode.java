package collection.linkedlist.easy_problems;

/**
 237. Delete Node in a Linked List
 There is a singly-linked list head and we want to delete a node node in it.
 You are given the node to be deleted node. You will not be given access to the first node of head.
 All the values of the linked list are unique, and it is guaranteed that the given node node is not the last node in the linked list.
 Delete the given node. Note that by deleting the node, we do not mean removing it from memory. We mean:
The value of the given node should not exist in the linked list.
The number of nodes in the linked list should decrease by one.
All the values before node should be in the same order.
All the values after node should be in the same order.

Custom testing:
For the input, you should provide the entire linked list head and the node to be given node. node should not be the last
 node of the list and should be an actual node in the list.
We will build the linked list and pass the node to your function.
The output will be the entire list after calling your function.
 Example 1:
 Input: head = [4,5,1,9], node = 5
 Output: [4,1,9]
 Explanation: You are given the second node with value 5, the linked list should become 4 -> 1 -> 9 after calling your function.

 Example 2:
 Input: head = [4,5,1,9], node = 1
 Output: [4,5,9]
 Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9 after calling your function\

 Constraints:
            The number of the nodes in the given list is in the range [2, 1000].
            -1000 <= Node.val <= 1000
            The value of each node in the list is unique.
            The node to be deleted is in the list and is not a tail node.
*/


/**
 * Definition for singly-linked list.
 *
 */
public class DeleteNode {

    public static void main(String[] args) {
        deleteNode(new ListNode(5));
    }

    private static class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
 }

    /**
     * What it does:
     * Efficiently deletes the specified node from a singly-linked list, given only access to that node and not the head of the list.
     * The node is removed by overwriting its value and pointer with those of the next node, thus preserving the list order and integrity.
     *
     * <p>
     * Why it works:
     * - Direct access to the node allows us to "remove" it by copying the next node's data and link into it.
     * - This method reduces the need for traversing the list or modifying head pointers, solving the restricted access scenario.
     * - By shifting values and pointers, the removed node's value doesn't exist in the list anymore, and the list length decreases by one.
     *
     * <p>
     * How it works:
     * 1. Obtain the next node after the given node.
     * 2. Copy the value of the next node into the given node.
     * 3. Update the given node’s `next` pointer to reference the next node’s `next`.
     * 4. The next node is effectively skipped, and its value is removed from the list.
     *
     * <p>
     * Edge Cases:
     * - The given node is guaranteed not to be the last node, per constraints, so this process is always valid.
     * - Works for all lists with at least two nodes and unique values.
     *
     * <p>
     * Time Complexity:
     * - O(1): All operations are constant time, with no traversal required.
     *
     * Space Complexity:
     * - O(1): No additional space is allocated; only pointer changes.
     *
     * <p>
     * Output:
     * - The list now excludes the specified node’s original value, maintains order, and is one node shorter.
     * - No return value; the list is modified in place.
     */
    private static void deleteNode(ListNode node) {
        ListNode nextNode = node.next;
        node.val = nextNode.val;
        node.next = nextNode.next;
    }
}
