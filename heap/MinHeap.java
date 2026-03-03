package heap;

import java.util.Arrays;

/*
Problem: Implement Min Heap
You need to implement the Min Heap with the following given methods:

insert (x) -> insert value x to the min heap
getMin -> Output the minimum value from min heap
exctractMin -> Remove the minimum element from the heap
heapSize -> return the current size of the heap
isEmpty -> returns if heap is empty or not
changeKey (ind, val) -> update the value at given index to val (index will be given 0-based indexing)
initializeHeap -> Initialize the heap

Example 1:
Input : operation = [ "initializeheap", "insert", "insert", "insert", "getMin", "heapSize", "isEmpty", "extractMin", "changeKey" , "getMin" ]
nums = [ [4], [1], [10], [0, 16] ]
Output : [ null, null, null, null, 1, 3, 0, null, null, 10 ]

Explanation : In 1st operation we initialize the heap to empty heap.
In 2nd, 3rd, 4th operation we insert 4, 1, 10 to the heap respectively. The heap after 4th operation will be -> [1, 4, 10].
In 5th operation we output the minimum element from the heap i.e. 1.
In 6th operation we output the size of the current heap i.e. 3.
In 7th operation we output whether the heap is empty or not i.e. false (0).
In 8th operation we remove the minimum element from heap. So the ne heap becomes -> [4, 10].
In 9th operation we change the 0th index element to 16. So new heap becomes -> [16, 10]. After heapify -> [10, 16].
In 10th operation we output the minimum element of the heap i.e. 10.


Example 2
Input : operation = [ "initializeheap", "insert", "insert", "extractMin", "getMin", "insert", "heapSize", "isEmpty", "extractMin", "changeKey" , "getMin" ]
nums = [ [4], [1], [1], [0, 2] ]
Output : [ null, null, null, null, 4, null, 2, 0, null, null, 2 ]

Explanation : In 1st operation we initialize the heap to empty heap.
In 2nd, 3rd operation we insert 4, 1 to the heap respectively. The heap after 4th operation will be -> [1, 4].
In 4th operation we remove the minimum element from heap. So the ne heap becomes -> [4].
In 5th operation we output the minimum element of the heap i.e. 4.
In 6th operation we operation we insert 1 to the heap. The heap after 6th operation will be -> [1, 4].
In 7th operation we output the size of the current heap i.e. 2.
In 8th operation we output whether the heap is empty or not i.e. false (0).
In 9th operation we remove the minimum element from heap. So the ne heap becomes -> [4].
In 10th operation we change the 0th index element to 2. So new heap becomes -> [2].
In 11th operation we output the minimum element of the heap i.e. 2.

Constraints:
            1 <= n <= 10^5
            -10^5 <= nums[i] <= 10^5
 */
public class MinHeap {
    private int[] heap;
    private int size;
    private int capacity;


    // Constructor: Start with a default capacity of 10
    public MinHeap() {
        this.capacity = 10;
        this.heap = new int[capacity];
        this.size = 0;
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(10);
        minHeap.insert(5);
        minHeap.insert(15);
        minHeap.insert(20);
        minHeap.insert(1);
        minHeap.insert(12);
        minHeap.insert(18);
        minHeap.insert(13);
        minHeap.insert(17);
        System.out.print("Current Heap: ");
        minHeap.printHeap();

        System.out.println("Peek: " + minHeap.peek());
        System.out.println("Delete: " + minHeap.delete());
        System.out.print("Current Heap: ");
        minHeap.printHeap();


        minHeap.insert(0);
        System.out.print("Current Heap: ");
        minHeap.printHeap();

        System.out.println("Peek: " + minHeap.peek());
        System.out.println("Delete: " + minHeap.delete());
    }

    /**
     * Adds a new value to the heap.
     * Time Complexity: O(log N)
     */
    public void insert(int value) {
        ensureCapacity();
        // Put the new value at the very end
        heap[size] = value;
        // "Bubble Up" to fix the heap property
        heapifyUp(size);
        size++;
    }

    /**
     * Removes and returns the minimum (top) element.
     * Time Complexity: O(log N)
     */
    public int delete() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");

        int rootValue = heap[0];
        // Replace root with the last element
        heap[0] = heap[size - 1];
        size--;
        // "Bubble Down" the new root to its correct spot
        heapifyDown(0);

        return rootValue;
    }

    /**
     * Just looks at the minimum element without removing it.
     * Time Complexity: O(1)
     */
    public int peek() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        return heap[0];
    }

    private void heapifyUp(int index) {
        // While we aren't at the root and the parent is larger than us...
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index] < heap[parentIndex]) {
                swap(index, parentIndex);
                index = parentIndex; // Move our focus to the parent's spot
            } else {
                break; // Everything is correct
            }
        }
    }

    private void heapifyDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            // Check if left child exists and is smaller
            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }
            // Check if right child exists and is smaller than the smallest so far
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }

            // If we found a smaller child, swap and keep sinking
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break; // Node is smaller than both its children
            }
        }
    }

    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void printHeap() {
        System.out.println(Arrays.toString(heap));
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}