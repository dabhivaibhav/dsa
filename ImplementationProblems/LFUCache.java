package ImplementationProblems;

import java.util.HashMap;
import java.util.Map;

/*
Leetcode 460. LFU Cache

Design and implement a data structure for a Least Frequently Used (LFU) cache.

Implement the LFUCache class:

LFUCache(int capacity) Initializes the object with the capacity of the data structure.
int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
void put(int key, int value) Update the value of the key if present, or inserts the key if not already present.
When the cache reaches its capacity, it should invalidate and remove the least frequently used key before inserting a new item.
For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least recently used key would be invalidated.
To determine the least frequently used key, a use counter is maintained for each key in the cache.
The key with the smallest use counter is the least frequently used key.

When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation).
The use counter for a key in the cache is incremented either a get or put operation is called on it.
The functions get and put must each run in O(1) average time complexity.

Example 1:

Input
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]

Explanation
// cnt(x) = the use counter for key x
// cache=[] will show the last used order for tiebreakers (leftmost element is  most recent)
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // return 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // return 4
                 // cache=[4,3], cnt(4)=2, cnt(3)=3


Constraints:
            1 <= capacity <= 10^4
            0 <= key <= 10^5
            0 <= value <= 10^9
            At most 2 * 10^5 calls will be made to get and put.
 */
public class LFUCache {
    private final int capacity;
    private int curSize;
    private int minFreq; // Tracks the global minimum frequency for eviction
    private Map<Integer, Node> cache; // key -> node
    private Map<Integer, DoubleLinkedList> freqMap; // frequency -> list of nodes
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.curSize = 0;
        this.minFreq = 0;
        this.cache = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    /*
     * THE LFU CACHE (Least Frequently Used): THE INTUITION
     *
     * 1. THE CHALLENGE: LRU VS LFU
     * - LRU (Least Recently Used) only cares about "How long ago was this used?"
     * - LFU (Least Frequently Used) cares about "How many times has this been used?"
     * - If there is a tie in frequency, LFU uses LRU as a tie-breaker.
     *
     * 2. THE DATA STRUCTURE: WHY THREE HASHMAPS?
     * To achieve O(1) for 'get' and 'put', we need a complex hybrid structure:
     * - HashMap<Integer, Node> cache: Instant access to a node by its key.
     * - HashMap<Integer, DoubleLinkedList> freqMap: Groups nodes by their frequency.
     * (e.g., Freq 1 -> [NodeA, NodeB], Freq 2 -> [NodeC])
     * - minFreq: A variable to track the lowest existing frequency for O(1) eviction.
     *
     * 3. THE MENTAL MODEL: "THE POPULARITY SHELVES"
     * Imagine a library where books are placed on shelves labeled by their popularity:
     * - Shelf 1: Books read once.
     * - Shelf 2: Books read twice.
     * - Every time a book is read, it moves UP one shelf and to the very FRONT of that shelf.
     * - If the library is full, we go to the LOWEST shelf and remove the book at the BACK.
     *
     * 4. INTERVIEW TAKEAWAY
     * - "LFU is harder than LRU because one frequency change affects the minFreq variable."
     * - "The O(1) complexity is guaranteed because we never iterate; we only move nodes
     * between pre-existing frequency lists using pointers."
     * - "Tie-breaking with LRU is naturally handled by using a Doubly Linked List for
     * each frequency group—newest at the head, oldest at the tail."
     */
    /**
     * get(key): Retrieves value and increments its frequency
     */
    public int get(int key) {
        // Check if the key exists in our primary lookup map
        Node node = cache.get(key);
        // If not found, simply return -1
        if (node == null) return -1;
        // Since it's accessed, update its frequency position
        updateFreq(node);
        // Return the stored value
        return node.value;
    }

    /**
     * put(key, value): Adds or updates a key-value pair
     */
    public void put(int key, int value) {
        // Edge case - capacity 0 cannot store anything
        if (capacity == 0) return;

        // If key exists, update value and bump frequency
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            updateFreq(node);
        } else {
            // If capacity is full, we must evict
            if (curSize == capacity) {
                // Get the list of Least Frequently Used items
                DoubleLinkedList minFreqList = freqMap.get(minFreq);
                // Remove the oldest item (tail) from that frequency list
                Node nodeToEvict = minFreqList.removeTail();
                // Clean up that item from the primary lookup map
                cache.remove(nodeToEvict.key);
                curSize--;
            }

            // Create the new item
            Node newNode = new Node(key, value);
            // Add to primary lookup map
            cache.put(key, newNode);
            // Line 9: Since it's new, the minimum frequency in the cache is now 1
            minFreq = 1;
            // Add the new node to the Frequency 1 list
            freqMap.computeIfAbsent(1, k -> new DoubleLinkedList()).addNode(newNode);
            curSize++;
        }
    }

    /**
     * updateFreq(node): Moves a node from its current frequency list to the next level
     */
    private void updateFreq(Node node) {
        // Identify the current frequency of the node
        int oldFreq = node.freq;
        // Get the list associated with that old frequency
        DoubleLinkedList oldList = freqMap.get(oldFreq);
        // Remove the node from the old frequency list
        oldList.removeNode(node);

        // If this was the minFreq list and it's now empty, increment global minFreq
        if (oldFreq == minFreq && oldList.size == 0) {
            minFreq++;
        }

        // Increment the node's individual counter
        node.freq++;
        // Move the node to the head of the new (higher) frequency list
        freqMap.computeIfAbsent(node.freq, k -> new DoubleLinkedList()).addNode(node);
    }

    // A Node represents a single entry in the cache
    static class Node {
        int key, value, freq;
        Node prev, next;

        Node(int k, int v) {
            this.key = k;
            this.value = v;
            this.freq = 1; // All new items start with frequency 1
        }
    }

    // A DoublyLinkedList specifically for a single frequency group
    static class DoubleLinkedList {
        Node head, tail;
        int size;

        DoubleLinkedList() {
            head = new Node(0, 0); // Dummy Head
            tail = new Node(0, 0); // Dummy Tail
            head.next = tail;
            tail.prev = head;
        }

        // Adds node right after dummy head (making it the Most Recently Used)
        void addNode(Node node) {
            node.next = head.next;
            node.next.prev = node;
            head.next = node;
            node.prev = head;
            size++;
        }

        // Removes a specific node from this frequency group
        void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        // Pops the Least Recently Used node (the one right before dummy tail)
        Node removeTail() {
            if (size > 0) {
                Node node = tail.prev;
                removeNode(node);
                return node;
            }
            return null;
        }
    }
}
