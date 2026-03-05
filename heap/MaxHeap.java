package heap;

import java.util.Arrays;

/*
Implement Max Heap
 */
public class MaxHeap {

    private int[] heap;
    private int size;
    private int capacity;

    public MaxHeap() {
        this.capacity = 10;
        this.heap = new int[capacity];
        this.size = 0;
    }

    public static void main(String[] args) {

        MaxHeap maxHeap = new MaxHeap();
        maxHeap.insert(10);
        maxHeap.insert(5);
        maxHeap.insert(15);
        maxHeap.insert(20);
        maxHeap.insert(1);
        maxHeap.printHeap();
        System.out.println("Peek: " + maxHeap.peek());
        System.out.println("Pop: " + maxHeap.pop());
        maxHeap.printHeap();
    }


    /**
     * insert(int value)
     * <p>
     * What this method does:
     * <p>
     * Inserts a new value into the Max Heap.
     * <p>
     * Core Idea:
     * <p>
     * New elements are always inserted
     * at the next available index (end of array)
     * to maintain the complete binary tree structure.
     * <p>
     * After insertion, the heap property may be violated,
     * so heapifyUp is called to restore order.
     * <p>
     * Steps:
     * <p>
     * 1. Ensure there is enough capacity.
     * 2. Place value at heap[size].
     * 3. Increase size.
     * 4. Call heapifyUp(size - 1).
     * <p>
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public void insert(int value) {
        ensureCapacity();
        heap[size] = value;
        size++;
        heapifyUp(size - 1);

    }

    /**
     * pop()
     * <p>
     * What this method does:
     * <p>
     * Removes and returns the maximum element
     * (the root of the heap).
     * <p>
     * Core Idea:
     * <p>
     * The root holds the largest value.
     * After removing it, the last element
     * replaces the root.
     * <p>
     * heapifyDown is used to restore
     * the Max Heap property.
     * <p>
     * Steps:
     * <p>
     * 1. Store heap[0] as rootValue.
     * 2. Move last element to index 0.
     * 3. Decrease size.
     * 4. Call heapifyDown(0).
     * 5. Return rootValue.
     * <p>
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int pop() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        int rootValue = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return rootValue;
    }

    /**
     * heapifyUp(int index)
     * <p>
     * What this method does:
     * <p>
     * Restores the Max Heap property
     * by moving a node upward
     * until its parent is larger (or it becomes root).
     * <p>
     * <p>
     * When is it used?
     * <p>
     * Immediately after insertion,
     * because the new element is placed
     * at the end of the array.
     * <p>
     * <p>
     * Index Explanation (Very Important):
     * <p>
     * Parent index = (index - 1) / 2
     * <p>
     * Why (index - 1)?
     * <p>
     * Because of how a complete binary tree
     * is stored in an array (level order).
     * <p>
     * Example index layout:
     * <p>
     * Index:  0   1   2   3   4   5   6
     * <p>
     * Tree:
     *       0
     *    /     \
     *   1       2
     *  / \     / \
     * 3   4   5   6
     * <p>
     * Look at index 1 and 2:
     * <p>
     * Parent of 1 = (1 - 1) / 2 = 0
     * Parent of 2 = (2 - 1) / 2 = 0
     * <p>
     * Look at index 3 and 4:
     * <p>
     * Parent of 3 = (3 - 1) / 2 = 1
     * Parent of 4 = (4 - 1) / 2 = 1
     * <p>
     * The "-1" shifts the index backward
     * before dividing by 2,
     * ensuring both left and right children
     * map correctly to the same parent.
     * <p>
     * Without "-1",
     * right children would map incorrectly.
     * <p>
     * Integer division automatically floors the value,
     * which gives the correct parent index.
     * <p>
     * <p>
     * How it works:
     * <p>
     * While:
     * index > 0
     * AND heap[index] > heap[parent]
     * <p>
     * 1. Compute parent = (index - 1) / 2
     * 2. Swap current element with parent
     * 3. Move index to parent
     * <p>
     * Stop when:
     * - We reach root (index == 0), OR
     * - Parent is larger (heap property restored)
     * <p>
     * <p>
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public void heapifyUp(int index) {
        while (index > 0 && heap[index] > heap[(index - 1) / 2]) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * heapifyDown(int index)
     * <p>
     * What this method does:
     * <p>
     * Restores the Max Heap property
     * by moving an element downward.
     * <p>
     * When is it used?
     * <p>
     * After removing the root.
     * <p>
     * Index Explanation:
     * <p>
     * Left child  = 2*index + 1
     * Right child = 2*index + 2
     * <p>
     * Compare the current element
     * with its children.
     * <p>
     * Swap with the larger child
     * if necessary.
     * <p>
     * Continue until heap property is restored.
     * <p>
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public void heapifyDown(int index) {
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size && heap[left] > heap[largest]) {
                largest = left;
            }
            if (right < size && heap[right] > heap[largest]) {
                largest = right;
            }
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    /**
     * peek()
     * <p>
     * What this method does:
     * <p>
     * Returns the maximum element
     * without removing it.
     * <p>
     * Since the root always holds
     * the largest value,
     * simply return heap[0].
     * <p>
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public int peek() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        return heap[0];
    }

    /**
     * ensureCapacity()
     * <p>
     * What this method does:
     * <p>
     * Doubles the heap capacity
     * when the array is full.
     * <p>
     * Core Idea:
     * <p>
     * If size == capacity,
     * create a new array with double capacity
     * and copy elements.
     * <p>
     * Time Complexity: O(n) (only when resizing)
     * Amortized insertion remains O(log n).
     */
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
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}

