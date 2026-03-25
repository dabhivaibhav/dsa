package heap.hard_problem;

/*
Leetcode 23: Merge k Sorted Lists

You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
Merge all the linked-lists into one sorted linked-list and return it.

Example 1:
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted linked list:
1->1->2->3->4->4->5->6

Example 2:
Input: lists = []
Output: []

Example 3:
Input: lists = [[]]
Output: []

Constraints:
            k == lists.length
            0 <= k <= 10^4
            0 <= lists[i].length <= 500
            -10^4 <= lists[i][j] <= 10^4
            lists[i] is sorted in ascending order.
            The sum of lists[i].length will not exceed 10^4.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeKSortedLists {

    public static void main(String[] args) {
    }

    /**
     * mergeKListsBruteForce(ListNode[] lists)
     * <p>
     * What this method does:
     * <p>
     * Merges k sorted linked lists
     * into one single sorted linked list.
     * <p>
     * This brute-force approach ignores
     * the structure of the lists initially,
     * and instead focuses on collecting
     * and sorting all values.
     * <p>
     * Core Idea:
     * <p>
     * Treat all elements from all lists
     * as one big collection.
     * <p>
     * 1. Extract every value
     * 2. Sort them
     * 3. Rebuild a new sorted linked list
     * <p>
     * This approach sacrifices efficiency
     * for simplicity.
     * <p>
     * Thought Process:
     * <p>
     * Instead of merging lists one-by-one,
     * we flatten all lists into
     * a single array (or list).
     * <p>
     * Once everything is in one place,
     * sorting becomes straightforward.
     * <p>
     * After sorting,
     * we reconstruct the linked list
     * from the sorted values.
     * <p>
     * How the Code Works:
     * <p>
     * Step 1:
     * <p>
     * Create a List (nodes)
     * to store all values.
     * <p>
     * Step 2:
     * <p>
     * Traverse each linked list.
     * <p>
     * For every node:
     * <p>
     * - Add its value to the list
     * <p>
     * Step 3:
     * <p>
     * Sort the collected values
     * using Collections.sort().
     * <p>
     * Step 4:
     * <p>
     * Create a dummy node
     * to help build the result list.
     * <p>
     * Step 5:
     * <p>
     * Iterate through the sorted values:
     * <p>
     * - Create a new node for each value
     * - Attach it to the result list
     * <p>
     * Step 6:
     * <p>
     * Return dummy.next
     * (the head of the merged list).
     * <p>
     * Example 1:
     * <p>
     * Input:
     * <p>
     * [
     * 1→4→5,
     * 1→3→4,
     * 2→6
     * ]
     * <p>
     * Step 1 (Collect values):
     * <p>
     * [1,4,5,1,3,4,2,6]
     * <p>
     * Step 2 (Sort):
     * <p>
     * [1,1,2,3,4,4,5,6]
     * <p>
     * Step 3 (Rebuild):
     * <p>
     * 1→1→2→3→4→4→5→6
     * <p>
     * Output → merged list
     * <p>
     * Example 2:
     * <p>
     * Input: []
     * <p>
     * No values collected.
     * <p>
     * Output → null
     * <p>
     * Example 3:
     * <p>
     * Input: [[]]
     * <p>
     * No values collected.
     * <p>
     * Output → null
     * <p>
     * Complexity:
     * <p>
     * Let:
     * <p>
     * N = total number of nodes
     * across all linked lists
     * <p>
     * Time Complexity:
     * <p>
     * O(N log N)
     * <p>
     * - O(N) to collect all values
     * - O(N log N) to sort them
     * - O(N) to rebuild the list
     * <p>
     * Space Complexity:
     * <p>
     * O(N)
     * <p>
     * - Extra list to store all values
     * <p>
     * Interview Takeaway:
     * <p>
     * This brute-force solution is easy
     * to implement and understand,
     * but it does not utilize the fact
     * that the input lists are already sorted.
     * <p>
     * A more optimal approach uses a
     * Min Heap to merge lists efficiently
     * in O(N log k) time.
     * <p>
     * Always look for ways to leverage
     * existing order in the data
     * to improve performance.
     */
    private static ListNode mergeKListsBruteForce(ListNode[] lists) {
        List<Integer> nodes = new ArrayList<>();

        // Collect all values (The "Big Bucket")
        for (ListNode root : lists) {
            while (root != null) {
                nodes.add(root.val);
                root = root.next;
            }
        }

        // Sort the collection
        Collections.sort(nodes);

        // Rebuild the Linked List
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int value : nodes) {
            current.next = new ListNode(value);
            current = current.next;
        }
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
