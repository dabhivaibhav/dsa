package array;

import java.util.Scanner;

public class SumOfArray {

    /**
     * Method to take the size of the array from the user
     */
    public static int[] arrayCreation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of array");
        int arrSize = sc.nextInt();
        int[] arr = new int[arrSize];
        System.out.println("Enter the elements of array");
        for (int i = 0; i < arrSize; i++) {
            arr[i] = sc.nextInt();
        }
        return arr;
    }

    /**
     * method to sum all the elements of an array
     */
    public static void sumOfArray(int[] arr) {
        int sum = 0;
        for (int j : arr) {
            sum += j;
        }
        System.out.println("The sum of the given array is: " + sum);
    }

    public static void main(String[] args) {
        int[] arr = arrayCreation();
        sumOfArray(arr);
    }
}
