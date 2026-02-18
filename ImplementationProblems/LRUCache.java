package ImplementationProblems;

import java.util.HashMap;
import java.util.Map;

/*
LRU Cache

Design a data structure that follows the constraints of Least Recently Used (LRU) cache.

Implement the LRUCache class:
LRUCache(int capacity): We need to initialize the LRU cache with positive size capacity.
int get(int key): Returns the value of the key if the key exists, otherwise return -1.
void put(int key,int value): Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache.
If the number of keys exceeds the capacity from this operation, evict the least recently used key.
The functions get and put must each run in O(1) average time complexity.

Note : In Input is provided in 2D array format where the first number in each array denotes the operation (1-put, 2-get) to perform.
The next integers are the values used for the operation.


Example 1

Input:
Capacity = 2,
nums = [ [1, 1, 1], [1, 2, 2], [2, 1], [1, 3, 3], [2, 2], [1, 4, 4], [2, 1], [2, 3], [2, 4] ]
Output: [null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation:
LRUCache lRUCache = new LRUCache(2);
1st entry of nums is (1, 1, 1). 1 - represents put call. lRUCache.put(1, 1); // cache is {1=1}
2nd entry of nums is (1, 2, 2). 1 - represents put call. lRUCache.put(2, 2); // cache is {1=1, 2=2}
3rd entry of nums is (2, 1). 2 - represents get call. lRUCache.get(1);  // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);  // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);  // return -1 (not found)
lRUCache.get(3);  // return 3
lRUCache.get(4);  // return 4

Example 2
Input: Capacity = 1,
nums = [[1, 1, 1], [1, 2, 2], [2, 1], [1, 3, 3], [2, 2], [1, 4, 4], [2, 3]]
Output: [null, null, null, -1, null, -1, null, -1]

Explanation:
LRUCache lRUCache = new LRUCache(1);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // evicts key 1, cache is {2=2}
lRUCache.get(1);  // returns -1 (not found)
lRUCache.put(3, 3); // evicts key 2, cache is {3=3}
lRUCache.get(2);  // returns -1 (not found)
lRUCache.put(4, 4); // evicts key 3, cache is {4=4}
lRUCache.get(3);  // returns -1 (not found)

Constraints:
            1 <= capacity <= 1000
            0 <= key <= 10^4
            0 <= value <= 10^5
            At most 105 calls will be made to get and put.
 */
public class LRUCache {


    // Class Variables
    private final int capacity;

    /*
     * 1. Why did I choose that data structure?
     * We need O(1) for both 'get' and 'put'.
     * - A HashMap gives us O(1) access to any value if we have the key.
     * But maps don't remember the "order" of usage effectively.
     * - A LinkedList remembers "order" (Front is new, Back is old).
     * But searching for an item in a list is O(N).
     * * THE SOLUTION: A "Hooked" Hybrid.
     * We use a HashMap where the 'Value' is actually a pointer (a hook) to a
     * Node in a Doubly Linked List (DLL).
     * - HashMap: For instant lookup.
     * - DLL: For instant re-ordering (plucking a node and moving it to the front).
     * --------------------------------------------------------------------------------
     * 2. THE MENTAL MODEL: "THE DESK PILE"
     * Imagine a pile of papers on your desk:
     * - When you use a paper, you put it on TOP (Head).
     * - When a new paper arrives, it goes on TOP (Head).
     * - If the pile is too high (Capacity), you throw away the paper at the BOTTOM (Tail).
     * - The HashMap is a Post-it note on your screen telling you exactly where
     * in the pile a specific paper is so you can pull it out instantly.
     *
     * --------------------------------------------------------------------------------
     * 3. METHOD LOGIC (STEP-BY-STEP)
     *
     * A. get(key):
     * 1. Check the Map. If key doesn't exist, return -1.
     * 2. If it exists, find the Node via the Map.
     * 3. "Refresh" it: Remove the node from its current spot in the DLL and
     * insert it at the Head (Most Recently Used).
     * 4. Return the value.
     *
     * B. put(key, value):
     * 1. If key exists: Update the value, remove the old node, insert at Head.
     * 2. If key is new:
     * a. If at Capacity: Find the node at the Tail (Least Recently Used),
     * remove it from the Map AND the DLL.
     * b. Create new node, add to Map, and insert at Head.
     * --------------------------------------------------------------------------------
     * 4. EXAMPLE WALKTHROUGH (Capacity = 2)
     * * Operation: put(1, 1) -> Map: {1: Node1}, DLL: Head <-> Node1 <-> Tail
     * Operation: put(2, 2) -> Map: {1: Node1, 2: Node2}, DLL: Head <-> Node2 <-> Node1 <-> Tail
     * Operation: get(1)    -> Node1 is moved to front. DLL: Head <-> Node1 <-> Node2 <-> Tail. Return 1.
     * Operation: put(3, 3) -> Capacity full! Evict Tail.prev (Node2).
     * Map: {1: Node1, 3: Node3}, DLL: Head <-> Node3 <-> Node1 <-> Tail.
     * --------------------------------------------------------------------------------
     * 5. INTERVIEW TAKEAWAYS (What to tell the interviewer)
     *
     * I used a Doubly Linked List because it allows O(1) removal of a node from the
     * middle if we have a reference to it.
     * I used Dummy Head and Tail nodes to eliminate edge cases like null-pointer
     * checks during insertion/deletion.
     * The core trade-off here is Space for Time: We use extra memory (the Map and
     * the DLL pointers) to achieve perfect O(1) performance.
     * The Map and DLL must always stay synchronized; if an item is removed from
     * the list, it must be removed from the map simultaneously.
     * --------------------------------------------------------------------------------
     */
    private final Map<Integer, Node> map;
    private final Node head;
    private final Node tail;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        // Initialize Dummy Nodes - They act as permanent boundaries
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public static void main(String[] args) {

        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
    }

    // 3. The GET Operation: O(1)
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        Node node = map.get(key);
        // "Refresh" the node: Move it to the Most Recently Used (Head) position
        remove(node);
        insertAtHead(node);

        return node.value;
    }

    // 4. The PUT Operation: O(1)
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // Update existing value and refresh position
            Node existingNode = map.get(key);
            existingNode.value = value;
            remove(existingNode);
            insertAtHead(existingNode);
        } else {
            // Check if we are at capacity before adding new
            if (map.size() == capacity) {
                // Evict the Least Recently Used (the one before the Dummy Tail)
                Node lru = tail.prev;
                map.remove(lru.key);
                remove(lru);
            }

            // Add new node
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            insertAtHead(newNode);
        }
    }

    // Plucks a node out of the line by connecting its neighbors to each other
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Always adds the node right after the Dummy Head (Most Recently Used position)
    private void insertAtHead(Node node) {
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    // Define the Doubly Linked List Node
    static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
