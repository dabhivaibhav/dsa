package array.easy_problems;

import java.util.Arrays;
import java.util.Scanner;

public class ReverseArray {

    /**
     * Code to take the size of the array from the
     * user
     */
    public static int[] arrayCreation() {
        System.out.println("Enter the size of the array");
        Scanner sc = new Scanner(System.in);
        int arraySize = sc.nextInt();
        int[] arr = new int[arraySize];
        System.out.println("Enter the elements of the array");
        for (int i = 0; i < arraySize; i++) {
            arr[i] = sc.nextInt();
        }
        return arr;
    }

    /**
     * This method prints the array from last element to the first element
     */
    public static void reverseArray1(int[] arr) {
        System.out.println("Reversed Array");

        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.println(arr[i] + " ");
        }
    }

    /**
     * In this method I have divided the array in two parts
     * and used the swapping method to replace the elements in array
     */
    public static int[] reverseArray2(int[] arr) {

        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }

        return arr;
    }

    public static void main(String[] args) {

        int[] arr = arrayCreation();

        reverseArray1(arr);

        System.out.println(Arrays.toString(reverseArray2(arr)));
        ;
    }
}
