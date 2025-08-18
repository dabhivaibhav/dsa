package SortingAlgorithms;


import java.util.Arrays;

/*
You are given an array of integers. You need to sort the array in ascending order using the Insertion Sort algorithm.
Insertion Sort works by building a sorted array one element at a time. It takes each element from the input array and
inserts it into its correct position in the already sorted part of the array. The process continues until the entire
array is sorted.
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void insertionSort(int[] arr) {

        int n = arr.length;
        for(int i =0 ; i <=n-1; i++) {
            int j = i;
            while(j > 0 && arr[j] < arr[j-1]) {
                // Swap arr[j] and arr[j-1]
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }
}
