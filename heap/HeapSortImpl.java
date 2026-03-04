package heap;

/*
Implement Heap Sort
 */
public class HeapSortImpl {

    public static void main(String[] args) {

        int[] arr = {10, 7, 8, 9, 1, 5};
        heapSort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Build the Max-Heap (Reorganize array)
        // We start from the last non-leaf node and work up to the root
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements one by one from the heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root (the maximum) to the end of the array
            swap(arr, 0, i);

            // Call heapify on the reduced heap (size is now 'i')
            // This sinks the new root to its correct spot
            heapify(arr, i, 0);
        }

    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void heapify(int[] arr, int size, int i) {

        while (i < size) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            // If left child is larger than root
            if (left < size && arr[left] > arr[largest]) {
                largest = left;
            }

            // If right child is larger than largest so far
            if (right < size && arr[right] > arr[largest]) {
                largest = right;
            }

            // If largest is not root, swap and continue sinking
            if (largest != i) {
                swap(arr, i, largest);
                // Update 'i' to the child's index. The loop will
                // now restart from this new 'i' (the next floor down).
                i = largest;
            } else {
                // If parent is already largest, the "stone" has hit bottom.
                break;
            }
        }
    }
}
