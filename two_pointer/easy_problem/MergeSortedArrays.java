package two_pointer.easy_problem;

/*
Leetcode 21. Merge Two Sorted Lists

You are given the heads of two sorted linked lists list1 and list2.
Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
Return the head of the merged linked list.

Example 1:
Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]

Example 2:
Input: list1 = [], list2 = []
Output: []

Example 3:
Input: list1 = [], list2 = [0]
Output: [0]

Constraints:
            The number of nodes in both lists is in the range [0, 50].
            -100 <= Node.val <= 100
            Both list1 and list2 are sorted in non-decreasing order.
 */
public class MergeSortedArrays {

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode result = mergeSortedArrays(list1, list2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }

    /*
    THE "ZIPPER" MENTAL MODEL

    THE SOLUTION:
    1. Use a 'Dummy Node' so you don't have to worry about the first step.
    2. Compare the heads of both lists.
    3. Attach the smaller one to your new 'tail' and move that list's pointer.
    4. If one list runs out, just attach the entire remaining part of the
       other list to the end.

    Click! MOMENT:
    You aren't creating new nodes; you are just "re-wiring" the pointers.
    It's like moving railroad tracks to direct the train down a new path.

    COMPLEXITY:
    Time: O(n + m) — You visit every node exactly once.
    Space: O(1) — You only create a couple of pointers; no extra data structures.
    */
    private static ListNode mergeSortedArrays(ListNode list1, ListNode list2) {
        //Create a "Dummy" node to act as a fake head
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // While both lists have nodes left to compare
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;   // Attach the smaller node
                list1 = list1.next;  // Move the pointer in list1
            } else {
                tail.next = list2;   // Attach the smaller node
                list2 = list2.next;  // Move the pointer in list2
            }
            tail = tail.next;        // Move our result list's tail forward
        }

        // Attach the "Leftovers"
        // If one list is finished, just link the rest of the other list
        if (list1 != null) {
            tail.next = list1;
        } else if (list2 != null) {
            tail.next = list2;
        }

        // Return the real head (ignoring our fake dummy)
        return dummy.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
