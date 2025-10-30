package collection.linkedlist.medium_problems;

/*
160. Intersection of two linked lists

Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two
linked lists have no intersection at all, return null.

For example, the following two linked lists begin to intersect at node c1:
The test cases are generated such that there are no cycles anywhere in the entire linked structure.
Note that the linked lists must retain their original structure after the function returns.

Custom Judge:

The inputs to the judge are given as follows (your program is not given these inputs):

intersectVal - The value of the node where the intersection occurs. This is 0 if there is no intersected node.
listA - The first linked list.
listB - The second linked list.
skipA - The number of nodes to skip ahead in listA (starting from the head) to get to the intersected node.
skipB - The number of nodes to skip ahead in listB (starting from the head) to get to the intersected node.
The judge will then create the linked structure based on these inputs and pass the two heads, headA and headB to your program.
If you correctly return the intersected node, then your solution will be accepted.



Example 1:
Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
Output: Intersected at '8'
Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect).
From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5]. There are 2 nodes before the
intersected node in A; There are 3 nodes before the intersected node in B.
Note that the intersected node's value is not 1 because the nodes with value 1 in A and B (2nd node in A and 3rd node in B)
are different node references. In other words, they point to two different locations in memory, while the nodes with value
8 in A and B (3rd node in A and 4th node in B) point to the same location in memory.

Example 2:
Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
Output: Intersected at '2'
Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect).
From the head of A, it reads as [1,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the
intersected node in A; There are 1 node before the intersected node in B.

Example 3:
Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
Output: No intersection
Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not
intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
Explanation: The two lists do not intersect, so return null.

Constraints:
            The number of nodes of listA is in the m.
            The number of nodes of listB is in the n.
            1 <= m, n <= 3 * 10^4
            1 <= Node.val <= 10^5
            0 <= skipA <= m
            0 <= skipB <= n
            intersectVal is 0 if listA and listB do not intersect.
            intersectVal == listA[skipA] == listB[skipB] if listA and listB intersect.

Follow up: Could you write a solution that runs in O(m + n) time and use only O(1) memory?
 */
public class InterSectionOfLinkedLists {
    private Node head;
    private Node tail;
    private int size;

    public InterSectionOfLinkedLists() {
        this.size = 0;
    }

    public static void main(String[] args) {


    }

    /**
     * getInterSectionNodeBruteForce
     * <p>
     * What it does:
     * Finds the intersection node of two singly linked lists using a brute-force approach.
     * Compares every node of list B with every node of list A and returns the first common node reference (by object identity).
     *
     * Intuition:
     * - The intersection node of two linked lists is where both lists “meet” (share the same node reference).
     * - By checking every node in one list against every node in the other,
     *   you’re guaranteed to find the intersection if there is one.
     *
     * Why each line matters:
     * - while (headB != null):
     *     Iterates through every node in list B.
     * - Node temp = headA; while (temp != null):
     *     For each node in B, iterates through every node in A.
     * - if (temp == headB) return headB;
     *     Checks for object identity (reference equality); returns the intersection node immediately if found.
     * - temp = temp.next; headB = headB.next;
     *     Advances through both lists.
     *
     * Edge Cases Handled:
     * - If either list is empty, returns null.
     * - If there is no intersection, returns null.
     * - If lists intersect at any node, returns the first intersection.
     *
     * Example:
     * For lists: A: 1 → 2 → 3 → 4 → 5
     *            B: 9 → 8 → 4 → 5
     * Returns node with value 4, the intersection point.
     *
     * Time Complexity:
     * - O(m * n), where m and n are the lengths of the two lists (slow for large lists).
     *
     * Space Complexity:
     * - O(1), constant space, since only pointers are used.
     */
    private static Node getInterSectionNodeBruteForce(Node headA, Node headB) {
        while (headB != null) {
            Node temp = headA;
            while (temp != null) {
                if (temp == headB) return headB;
                temp = temp.next;
            }
            headB = headB.next;
        }
        return null;
    }


    /**
     * getInterSectionNode
     * <p>
     * What it does:
     * Efficiently finds the intersection node of two singly linked lists using the length difference approach.
     * It first computes the lengths of both lists, then advances the longer list by the difference in lengths,
     * finally moving both list pointers in tandem to find the first intersecting node by reference.
     *
     * Intuition:
     * - If two linked lists intersect, the nodes after the intersection are shared.
     * - By aligning both lists at the same distance from the end, you can check for reference equality in one pass.
     * - This eliminates redundant checks and ensures an optimal solution.
     *
     * Why each line matters:
     * - if (headA == null || headB == null) return null;
     *     Handles edge case where either list is empty; no intersection possible.
     * - Calculates lengths len1 (A) and len2 (B) by traversing both lists.
     * - Sets pointers a and b back to heads.
     * - Calculates length difference diff.
     * - Advances pointer on longer list by diff steps so both pointers
     *   are equally far from the end.
     * - while (a != null && b != null): if (a == b) return a;
     *     Moves both pointers together, checking for reference equality.
     *
     * Edge Cases Handled:
     * - Empty lists: Returns null.
     * - Lists of different lengths: Correctly aligns pointers.
     * - No intersection: Returns null.
     * - Intersection anywhere in the lists: Returns the first intersecting node.
     *
     * Example:
     * For lists: A: 1 → 2 → 3 → 4 → 5
     *            B: 9 → 8 → 4 → 5
     * Returns node with value 4, the intersection point.
     *
     * Time Complexity:
     * - O(m + n): Single pass to find lengths, single pass for intersection.
     *
     * Space Complexity:
     * - O(1): Constant space; uses only pointer variables.
     */
    public static Node getInterSectionNode(Node headA, Node headB) {
        if (headA == null || headB == null) return null;
        int len1 = 0;
        int len2 = 0;
        Node a = headA;
        Node b = headB;
        while (a != null || b != null) {
            if (a != null) {
                len1++;
                a = a.next;
            }
            if (b != null) {
                len2++;
                b = b.next;
            }
        }
        a = headA;
        b = headB;
        int diff = len1 - len2;
        if (diff < 0) {

            diff = -diff;
            while (diff-- > 0 && b != null) {
                b = b.next;
            }
        } else {
            while (diff-- > 0 && a != null) {
                a = a.next;
            }
        }
        while (a != null && b != null) {
            if (a == b) return a;
            a = a.next;
            b = b.next;
        }
        return null;
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
