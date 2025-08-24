package SortingAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;

/*
You are given an array of integers. You need to sort the array in ascending order using the Merge Sort algorithm.
Merge Sort is a divide-and-merge algorithm that divides the array into two halves, sorts each
half recursively, and then merges the sorted halves back together. The process continues until the entire array is sorted.
 */
public class MergeSortAlgo {

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6};
        int low = 0;
        int high = arr.length - 1;
        mergeSort(arr, low, high);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr, int low, int high) {
        if (low < high) {

            //Find the mid point
            // This is the point where the array will be divided into two halves
            // low is the starting index, high is the ending index
            // mid is the middle index
            int mid = (low + high) / 2;

            // Sort the first half
            mergeSort(arr, low, mid);
            // Sort the second half
            mergeSort(arr, mid + 1, high);
            // Merge the sorted halves
            mergeArr(arr, low, mid, high);
        }
    }

    private static void mergeArr(int[] arr, int low, int mid, int high) {
        //tempArray to store the merger array
        ArrayList<Integer> list = new ArrayList<>();
        int left = low; // Starting index of the left subarray
        int right = mid + 1; // Starting index of the right subarray

        //here it will compare the left and right subarray and add the smaller element to the list
        while(left <= mid && right <= high) {
            if(arr[left] <= arr[right]) {
                list.add(arr[left]);
                left++;
            } else {
                list.add(arr[right]);
                right++;
            }
        }

        //here it will add the remaining elements of the left subarray to the list
        while(left <= mid) {
            list.add(arr[left]);
            left++;
        }

        //here it will add the remaining elements of the right subarray to the list
        while(right <= high) {
            list.add(arr[right]);
            right++;
        }

        //here it will copy the elements of the list to the original array
        for(int i = low; i <= high; i++) {
            arr[i] = list.get(i - low);
        }
    }
}
