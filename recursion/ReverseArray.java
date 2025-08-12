package recursion;

import java.util.Arrays;

/*
You are given an array of integers. You need to reverse the array using recursion.
 */
public class ReverseArray {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};

        System.out.println("Original Array: " + Arrays.toString(arr));
        reverseArray(arr, 0, arr.length - 1);
    }

    private static void reverseArray(int[] arr, int i, int i1) {

        if (i >= i1) {
            System.out.println("Reversed Array: " + Arrays.toString(arr));
            return;
        }

        // Swap elements at indices i and i1
        int temp = arr[i];
        arr[i] = arr[i1];
        arr[i1] = temp;

        // Recursive call with next indices
        reverseArray(arr, i + 1, i1 - 1);
    }

}
