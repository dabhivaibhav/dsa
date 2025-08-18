package SortingAlgorithms;


/*
You are given an array of integers. You need to sort the array in ascending order using the Selection Sort algorithm.
Selection Sort works by repeatedly finding the minimum element from the unsorted part of the array and swapping it with
the first unsorted element. The process continues until the entire array is sorted.
 */

import java.util.Arrays;
import java.util.Scanner;

public class SelectionSortAlgo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size of the array: ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        selectionSortMethod(arr,n);
        System.out.println("Sorted array using Selection Sort:"+ Arrays.toString(arr));
    }

    private static void selectionSortMethod(int[] arr, int n) {

        for(int i = 0 ; i < n-2; i++) {
            int minimum = i; // Assume the first unsorted element is the minimum
            for(int j = i; j < n-1; j++) {
                if(arr[j] < arr[minimum]) {
                    minimum = j; // Update minimum if a smaller element is found
                }
            }
            // Swap the found minimum element with the first unsorted element
            int temp = arr[minimum];
            arr[minimum] = arr[i];
            arr[i] = temp;
        }
    }

}
