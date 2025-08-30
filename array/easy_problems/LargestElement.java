package array.easy_problems;

import java.util.Scanner;

public class LargestElement {

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
     * Logic to check the largest element by
     * iterating all over the array
     */
    public static void findLargestElement(int[] arr) {
        int largestElement = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > largestElement) {
                largestElement = arr[i];
            }
        }
        System.out.println("Largest Element in the array: " + largestElement);
    }

    public static void main(String[] args) {

        int[] arr = arrayCreation();

        findLargestElement(arr);
    }
}
