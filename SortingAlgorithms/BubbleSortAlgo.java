package SortingAlgorithms;

/*
You are given an array of integers. You need to sort the array in ascending order using the Bubble Sort algorithm.
Bubble Sort works by repeatedly stepping through the list, comparing adjacent elements and swapping them if they are in
the wrong order. The process is repeated until the list is sorted. To make it optimal, we can stop the algorithm if no
swaps are made in a pass, indicating that the array is already sorted.
 */
public class BubbleSortAlgo {

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort(arr);
        System.out.println("Sorted array using Bubble Sort: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }


    }

    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped in the inner loop, then the array is sorted
            if (!swapped) break;
        }
    }
}
