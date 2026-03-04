package heap;


import java.util.Collections;
import java.util.PriorityQueue;

public class HeapImplPriorityQueue {

    public static void main(String[] args) {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b - a);
        //OR Collections.reverseOrder();

        minHeap.add(10);
        minHeap.add(5);
        minHeap.add(15);
        minHeap.add(20);
        minHeap.add(1);
        minHeap.add(12);

        maxHeap.add(10);
        maxHeap.add(5);
        maxHeap.add(15);
        maxHeap.add(20);
        maxHeap.add(1);
        maxHeap.add(12);

        System.out.println("Min Heap: " + minHeap);
        System.out.println("Max Heap: " + maxHeap);

        System.out.println();

        System.out.println("Removed element from min heap: " + minHeap.poll());
        System.out.println("Removed element from max heap: " + maxHeap.poll());

        System.out.println();

        System.out.println("Min Heap: " + minHeap);
        System.out.println("Max Heap: " + maxHeap);

        System.out.println();

        System.out.println("Top element of min heap: " + minHeap.peek());
        System.out.println("Top element of max heap: " + maxHeap.peek());
    }
}
