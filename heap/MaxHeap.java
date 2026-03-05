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


    public void insert(int value) {
        ensureCapacity();
        heap[size] = value;
        size++;
        heapifyUp(size - 1);

    }

    public int pop() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        int rootValue = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return rootValue;
    }

    public void heapifyUp(int index) {
        while (index > 0 && heap[index] > heap[(index - 1) / 2]) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

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

    public int peek() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        return heap[0];
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

