package linkedlists.medium_problems;

import java.util.ArrayList;
import java.util.HashMap;

/*
Find Pairs with Given Sum in Doubly Linked List

Given the head of a sorted doubly linked list of positive distinct integers, and a target integer, return a 2D array
containing all unique pairs of nodes (a, b) such that a + b == target.

Each pair should be returned as a 2-element array [a, b] with a < b. The list is sorted in ascending order. If there are
no such pairs, return an empty list.

Examples:
Input: head = [1, 2, 4, 5, 6, 8, 9], target = 7
Output: [[1, 6], [2, 5]]
Explanation: 1 + 6 = 7 and 2 + 5 = 7 are the valid pairs.

Input: head = [1, 5, 6], target = 6
Output: [[1, 5]]
Explanation: 1 + 5 = 6 is the only valid pair.

Constraints:
            1 <= number of nodes <= 10^5
            1 <= Node.val <= 10^5
            1 <= target <= 10^5
            The linked list is sorted in strictly increasing order
            The linked list is contains distinct values

 */
public class FindPairsWithSumLinkedList {

    private Node head;
    private Node tail;
    private int size;

    FindPairsWithSumLinkedList() {
        this.size = 0;
    }

    public static void main(String[] args) {

        FindPairsWithSumLinkedList list = new FindPairsWithSumLinkedList();
        list.insert(1);
        list.insert(2);
        list.insert(4);
        list.insert(5);
        list.insert(6);
        list.insert(8);
        list.insert(9);
        int target = 7;
        System.out.println("Pairs with sum " + target + " are: " + list.findPairsBruteForce(list.head, target));
        System.out.println("Pairs with sum " + target + " are: " + list.findPairsOptimal(list.head, list.tail, target));
    }

    /**
     * findPairsBruteForceApproach
     * <p>
     * What it does:
     * Finds all unique pairs of node values in a sorted doubly linked list whose sum equals the given target.
     * This approach uses a HashMap to store visited node values while traversing the list once.
     * For each node, it checks whether the complement (target - current value) has already been seen.
     * If it has, a valid pair is recorded. Otherwise, the current value is added to the map.
     * <p>
     * Intuition:
     * - The problem is similar to the classic "Two Sum" approach using a hash table.
     * - For each node’s value `x`, we need another value `y` such that `x + y = target`.
     * - As we traverse, we store each value we encounter in a HashMap.
     * - When we later find a node whose complement is already in the map, we’ve discovered a valid pair.
     * - Because the linked list is sorted and contains distinct positive integers, each pair `[a, b]` will be unique.
     * <p>
     * Why each line matters:
     * - `ArrayList<ArrayList<Integer>> result = new ArrayList<>();`
     * Prepares a list to store all valid (a, b) pairs.
     * - `HashMap<Integer, Integer> visited = new HashMap<>();`
     * Keeps track of all node values we’ve seen so far for quick O(1) lookup.
     * - `while (temp != null) { ... }`
     * Iterates over the entire linked list from head to tail.
     * - `if (visited.containsKey(target - temp.val)) { ... }`
     * Checks if the complement of the current value has already been seen — if yes, it forms a valid pair.
     * - `pair.add(visited.get(target - temp.val)); pair.add(temp.val);`
     * Constructs the 2-element list `[a, b]` where `a + b == target`.
     * - `visited.put(temp.val, temp.val);`
     * Marks the current value as visited so future nodes can match with it.
     * <p>
     * Edge Cases Handled:
     * - Empty or single-node list → loop never runs, returns an empty result.
     * - No pairs found → returns an empty list.
     * - Distinct values ensure no duplicate pairs appear in the result.
     * - Handles large lists efficiently in O(n) time while using O(n) space for the HashMap.
     * <p>
     * Example:
     * head = [1, 2, 4, 5, 6, 8, 9], target = 7
     * visited = {}
     * temp = 1 → complement = 6 (not found) → store 1
     * temp = 2 → complement = 5 (not found) → store 2
     * temp = 4 → complement = 3 (not found) → store 4
     * temp = 5 → complement = 2 (found) → add [2, 5]
     * temp = 6 → complement = 1 (found) → add [1, 6]
     * temp = 8, 9 → complements not found
     * result = [[2, 5], [1, 6]]
     * <p>
     * Time Complexity:
     * - O(n), since each node is processed once and map operations are O(1) on average.
     * <p>
     * Space Complexity:
     * - O(n), due to the additional HashMap used to store visited node values.
     */
    public ArrayList<ArrayList<Integer>> findPairsBruteForce(Node head, int target) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        HashMap<Integer, Integer> visited = new HashMap<>();
        Node temp = head;
        while (temp != null) {
            if (visited.containsKey(target - temp.val)) {
                ArrayList<Integer> pair = new ArrayList<>();
                pair.add(visited.get(target - temp.val));
                pair.add(temp.val);
                result.add(pair);
            }
            visited.put(temp.val, temp.val);
            temp = temp.next;
        }
        return result;
    }

    /**
     * findPairsTwoPointer
     * <p>
     * What it does:
     * Finds all unique pairs of node values in a sorted doubly linked list whose sum equals the given target,
     * using two pointers (one from the start and one from the end) to achieve O(1) extra space. Returns the
     * pairs in ascending order inside an ArrayList.
     * <p>
     * Intuition:
     * - The list is sorted in strictly increasing order, so smaller values are on the left and larger values are on the right.
     * - If we pick the leftmost and rightmost elements, their sum tells us in which direction to move:
     * - If the sum is too small, we must move the left pointer to a bigger value.
     * - If the sum is too big, we must move the right pointer to a smaller value.
     * - If the sum matches the target, we record that pair and move both pointers inward to search for more.
     * - This is the same logic as the classic 2-sum in a sorted array, but here we use `next` and `prev` instead of indexes.
     * <p>
     * Why each line matters:
     * - `ArrayList<ArrayList<Integer>> result = new ArrayList<>();`
     * Creates the output container. This is the only unavoidable space besides the answer itself.
     * - `Node left = head; Node right = tail;`
     * Initialize the two pointers at the extremes of the sorted DLL.
     * - `while (left != null && right != null && left != right && left.prev != right) { ... }`
     * Loop until pointers meet or cross. `left.prev != right` prevents reusing the same pair in reverse.
     * - `int sum = left.val + right.val;`
     * Current candidate pair.
     * - `if (sum == target) { ... }`
     * We found a valid pair, so store it and move both pointers inward to avoid duplicates.
     * - `else if (sum < target) left = left.next;`
     * Sum too small → move to a bigger value.
     * - `else right = right.prev;`
     * Sum too large → move to a smaller value.
     * <p>
     * Edge Cases Handled:
     * - Empty list or single node list → loop won't run, returns empty result.
     * - No pair sums to target → returns empty list.
     * - Strictly increasing input → guarantees no duplicate pairs.
     * - Target smaller than smallest+secondSmallest or larger than largest+secondLargest → returns empty.
     * <p>
     * Example:
     * head = [1, 2, 4, 5, 6, 8, 9], target = 7
     * left=1, right=9 → 10 > 7 → move right
     * left=1, right=8 → 9 > 7 → move right
     * left=1, right=6 → 7 == 7 → add [1,6], move both
     * left=2, right=5 → 7 == 7 → add [2,5], move both
     * left=4, right=4 → stop
     * result = [[1, 6], [2, 5]]
     * <p>
     * Time Complexity:
     * - O(n), where n is the number of nodes, because each pointer moves at most n steps.
     * <p>
     * Space Complexity:
     * - O(1) extra space (ignoring the space needed to store the output pairs), because we only use two pointers.
     */
    public ArrayList<ArrayList<Integer>> findPairsOptimal(Node head, Node tail, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        Node left = head;
        Node right = tail;
        while (left != null && right != null && left != right && left.prev != right) {
            int sum = left.val + right.val;
            if (sum == target) {
                ArrayList<Integer> pair = new ArrayList<>();
                pair.add(left.val);
                pair.add(right.val);
                result.add(pair);
                left = left.next;
                right = right.prev;
            } else if (sum < target) {
                left = left.next;
            } else {
                right = right.prev;
            }
        }
        return result;
    }

    public void showList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
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
    private static class Node {
        int val;
        Node next;
        Node prev;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node next, Node prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
}



}
