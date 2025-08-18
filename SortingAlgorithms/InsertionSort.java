package SortingAlgorithms;


import java.util.Arrays;

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
