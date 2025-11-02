package linkedlists.medium_problems;

/*
2. Add Two Numbers

You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order,
and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example 1:
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.

Example 2:
Input: l1 = [0], l2 = [0]
Output: [0]

Example 3:
Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]

Constraints:
            The number of nodes in each linked list is in the range [1, 100].
            0 <= Node.val <= 9
            It is guaranteed that the list represents a number that does not have leading zeros.
 */
public class AddTwoNumbersLinkedlist {
    private Node head;
    private Node tail;
    private int size;

    public AddTwoNumbersLinkedlist() {
        this.size = 0;
    }

    public static void main(String[] args) {
        AddTwoNumbersLinkedlist list = new AddTwoNumbersLinkedlist();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        AddTwoNumbersLinkedlist list1 = new AddTwoNumbersLinkedlist();
        list1.insert(1);
        list1.insert(2);

        System.out.println(addTwoNumbers(list.head, list1.head));


    }

    /**
     * addTwoNumbers
     * <p>
     * What it does:
     * Adds two non-negative integers represented by two singly linked lists, where each node contains a single digit.
     * The digits are stored in reverse order (the least significant digit comes first). The method iterates through both
     * lists simultaneously, adds corresponding digits along with any carry from the previous addition, and builds a new
     * linked list that represents the sum. If a carry remains after processing both lists, it appends an extra node.
     * <p>
     * Intuition:
     * - When numbers are stored in reverse order (e.g., 342 is stored as 2 → 4 → 3), addition becomes easy because we can
     * start from the head (which represents the ones place).
     * - At every step, we add: (digit from list 1) + (digit from list 2) + (carry).
     * - If one list is shorter than the other, we treat the missing digits as 0.
     * - A dummy node is used to simplify list construction and avoid special handling for the head of the result list.
     * <p>
     * Why each line matters:
     * - Node dummynode = new Node(0);
     * Creates a dummy starter node so we can always attach the next computed digit without checking if it's the first node.
     * - Node temp = dummynode;
     * temp is the moving pointer used to build the result list while keeping a reference to the dummy head.
     * - int carry = 0;
     * Initializes carry to 0 because there is no previous addition at the start.
     * - while (head != null || head1 != null) {
     * Loops as long as at least one list has remaining digits; supports different-length lists.
     * - int sum = carry;
     * Start this digit's sum with the previous carry.
     * - if (head != null) { sum += head.val; head = head.next; }
     * Adds the current digit from the first list (if present) and moves that list forward.
     * - if (head1 != null) { sum += head1.val; head1 = head1.next; }
     * Adds the current digit from the second list (if present) and moves that list forward.
     * - carry = sum / 10;
     * Computes the new carry (e.g., 17 / 10 = 1) to be used in the next iteration.
     * - temp.next = new Node(sum % 10);
     * Creates a node for the current digit of the result (e.g., 17 % 10 = 7) and links it.
     * - temp = temp.next;
     * Advances the result pointer to the newly added node to continue building the list.
     * - if (carry > 0) { temp.next = new Node(carry); }
     * After the loop, if there's still a carry (e.g., 9 → 9 + 1 → 0 with carry 1), append it as a new digit.
     * - return dummynode.next;
     * Returns the actual head of the result list, skipping the dummy node.
     * <p>
     * Edge Cases Handled:
     * - Different-length lists: Works when one list runs out earlier (e.g., 9 → 9 and 1 → 0 → 0).
     * - Final carry: Correctly appends an extra node when the last addition produces a carry (e.g., 9 → 9 → 9 + 1 → 0 → 0 → 0).
     * - One list is null: Effectively returns the other list since sum will just be digit + carry.
     * - All zeros: Adding 0 → 0 → 0 and 0 → 0 produces 0 → 0 → 0 (no extra nodes added unnecessarily).
     * <p>
     * Example:
     * Input:
     * head   = 2 → 4 → 3    (represents 342)
     * head1  = 5 → 6 → 4    (represents 465)
     * Output:
     * 7 → 0 → 8             (represents 807, because 342 + 465 = 807)
     * <p>
     * Another Example (with carry at the end):
     * Input:
     * head   = 9 → 9        (represents 99)
     * head1  = 1            (represents 1)
     * Output:
     * 0 → 0 → 1             (represents 100)
     * <p>
     * Time Complexity:
     * - O(max(m, n)): We traverse both lists once, where m and n are the lengths of the two input lists.
     * <p>
     * Space Complexity:
     * - O(max(m, n)): We create a new list whose length is at most one node longer than the longer input list (for the final carry).
     */
    private static Node addTwoNumbers(Node head, Node head1) {
        Node dummynode = new Node(0);
        Node temp = dummynode;
        int carry = 0;
        while (head != null || head1 != null) {
            int sum = carry;
            if (head != null) {
                sum += head.val;
                head = head.next;
            }
            if (head1 != null) {
                sum += head1.val;
                head1 = head1.next;
            }
            carry = sum / 10;
            temp.next = new Node(sum % 10);
            temp = temp.next;
        }

        if (carry > 0) {
            temp.next = new Node(carry);
        }
        return dummynode.next;
    }

    private void showList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
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
