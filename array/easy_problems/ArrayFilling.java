package array.easy_problems;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Write a program that fills an array with n integers
 * entered by the user. Suppose that the user can enter at least 1 number
 * and at most 20 numbers.
 */
public class ArrayFilling {

    public static void main(String[] args) {
        System.out.print("Enter the size of the array: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while (n > 20 || n <= 0) {
            System.out.println("Enter the correct size between 1 and 20");
            n = sc.nextInt();
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println("Elements in an array are: " + Arrays.toString(arr));
    }
}
