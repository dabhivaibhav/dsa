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
import java.util.PriorityQueue;

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


    /**
     * <h2>PROBLEM: Merge K Sorted Lists</h2>
     *
     * <h3>REAL-LIFE ANALOGY: The "Bouncer and the Checkout Lanes"</h3>
     * <hr>
     * <ul>
     * <li>Imagine a grocery store with <b>K checkout lanes</b>. In each lane,
     * customers are already sorted by height (shortest in front).</li>
     * <li>We hire a <b>"Bouncer" (Min-Heap)</b> to stand at the front.
     * He can only see the person at the very front of each lane (K people).</li>
     * <li>The Bouncer picks the shortest person of the K people and sends
     * them to the "Master Line" (Result List).</li>
     * <li><b>IMMEDIATELY</b>, the person who was behind the one we just picked
     * steps forward to the front of their lane.</li>
     * <li>The Bouncer now compares this new person with the other K-1
     * people already waiting.</li>
     * <li>This ensures the Bouncer only ever has to manage K people at a
     * time, regardless of how many thousands of people are in the store.</li>
     * </ul>
     *
     * <h3>ALGORITHM STEPS:</h3>
     * <hr>
     * <ol>
     * <li>Initialize a {@code PriorityQueue} (Min-Heap) with a custom
     * Comparator to compare {@code ListNode.val}.</li>
     * <li>Add the <b>head</b> of every non-empty list to the PriorityQueue.</li>
     * <li>Use a <b>'dummy'</b> node to simplify the construction of the result list.</li>
     * <li>While the PriorityQueue is not empty:
     * <ul>
     * <li>Extract (poll) the smallest node.</li>
     * <li>Attach it to the <b>'tail'</b> of the result list.</li>
     * <li>If the extracted node has a <b>'.next'</b>, add that next node
     * to the PriorityQueue to keep the heap size at K.</li>
     * </ul>
     * </li>
     * <li>Return {@code dummy.next}.</li>
     * </ol>
     *
     * <h3>COMPLEXITY:</h3>
     * <hr>
     * <ul>
     * <li><b>Time:</b> {@code O(N log K)} &rarr; N total nodes, log K for each heap operation.</li>
     * <li><b>Space:</b> {@code O(K)} &rarr; The heap only stores one node from each of the K lists.</li>
     * </ul>
     */
    private static ListNode mergeKListsBetter(ListNode[] lists) {
        // Check for empty input (The "No Lanes" edge case)
        if (lists == null || lists.length == 0) return null;

        // Initialize the Bouncer (Min-Heap)
        // We provide a Comparator to tell it to compare the 'val' of nodes
        PriorityQueue<ListNode> bouncer = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Fill the Waiting Room: Add the head of every non-empty lane
        for (ListNode head : lists) {
            if (head != null) {
                bouncer.add(head);
            }
        }

        // Create a "Dummy" node to start our Master Line
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // The Main Loop
        while (!bouncer.isEmpty()) {
            // Pick the shortest person currently at the front
            ListNode shortest = bouncer.poll();

            // Add them to our Master Line
            tail.next = shortest;
            tail = tail.next;

            // "Magic Step": If the person we picked has someone behind them,
            // bring that next person to the front (add to Heap)
            if (shortest.next != null) {
                bouncer.add(shortest.next);
            }
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
